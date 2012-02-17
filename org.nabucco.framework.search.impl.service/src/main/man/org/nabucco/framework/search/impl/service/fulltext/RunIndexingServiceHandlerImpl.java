/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.search.impl.service.fulltext;

import java.util.List;

import org.nabucco.adapter.lucene.facade.adapter.LuceneAdapter;
import org.nabucco.adapter.lucene.facade.adapter.LuceneAdapterLocator;
import org.nabucco.adapter.lucene.facade.exception.LuceneException;
import org.nabucco.adapter.lucene.facade.message.LuceneGeoIndexRq;
import org.nabucco.adapter.lucene.facade.message.LuceneIndexRq;
import org.nabucco.adapter.lucene.facade.service.index.IndexLucene;
import org.nabucco.framework.base.facade.component.connection.Connection;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.connection.ConnectionFactory;
import org.nabucco.framework.base.facade.datatype.BasetypeType;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.geo.GeoLocation;
import org.nabucco.framework.base.facade.datatype.geo.Latitude;
import org.nabucco.framework.base.facade.datatype.geo.Longitude;
import org.nabucco.framework.base.facade.datatype.search.fulltext.FulltextDocument;
import org.nabucco.framework.base.facade.datatype.search.fulltext.FulltextField;
import org.nabucco.framework.base.facade.datatype.serialization.DeserializationData;
import org.nabucco.framework.base.facade.datatype.serialization.xml.XmlSerializer;
import org.nabucco.framework.base.facade.exception.search.IndexException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.search.facade.datatype.IndexQueue;
import org.nabucco.framework.search.facade.exception.SearchException;

/**
 * RunIndexingServiceHandlerImpl
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class RunIndexingServiceHandlerImpl extends RunIndexingServiceHandler {

    private static final String FIELD_PK = "PK";

    private static final String FIELD_TYPE = "Datatype";

    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_MAX_RESULTS = 100;

    @Override
    protected EmptyServiceMessage runIndexing(EmptyServiceMessage msg) throws IndexException {

        try {
            NabuccoQuery<IndexQueue> query = super.getPersistenceManager().createQuery("FROM IndexQueue");

            // TODO: Configuration per Extension Point!
            query.setMaxResults(DEFAULT_MAX_RESULTS);

            List<IndexQueue> result = query.getResultList();

            for (IndexQueue queueEntry : result) {
                this.doIndexing(queueEntry);
                queueEntry.setDatatypeState(DatatypeState.DELETED);
                super.getPersistenceManager().persist(queueEntry);
            }

        } catch (Exception pe) {
            getLogger().error(pe, "Error during indexing.");
            throw new IndexException("Error during indexing.", pe);
        }

        return new EmptyServiceMessage();
    }

    /**
     * Perform the actual indexing process.
     * 
     * @param queueEntry
     *            the index queue entry
     * 
     * @throws Exception
     *             when the indexing fails.
     */
    private void doIndexing(IndexQueue queueEntry) throws Exception {

        String owner = queueEntry.getOwner().getValue();
        String datatypeRef = queueEntry.getDatatypeRef().getValue();
        Long identifier = queueEntry.getIdentifierRef().getValue();
        String index = queueEntry.getIndexName().getValue();

        // String componentRef = queueEntry.getComponentRef().getValue();
        // String table = queueEntry.getTableRef().getValue();

        XmlSerializer xs = new XmlSerializer();
        List<Datatype> sResult = xs.deserialize(new DeserializationData(queueEntry.getFulltextData().getValue()));

        FulltextDocument doc = (FulltextDocument) sResult.get(0);

        // Field Type
        {
            FulltextField field = new FulltextField();
            field.setFieldName(FIELD_TYPE);
            field.setFieldType(BasetypeType.STRING);
            field.setFieldValue(datatypeRef);
            doc.getFieldList().add(field);
        }

        // Field ID
        {
            FulltextField field = new FulltextField();
            field.setFieldName(FIELD_PK);
            field.setFieldType(BasetypeType.LONG);
            field.setFieldValue(String.valueOf(identifier));
            doc.getFieldList().add(field);
        }

        Latitude latitude = queueEntry.getLatitude();
        Longitude longitude = queueEntry.getLongitude();

        if (latitude == null || latitude.getValue() == null || longitude == null || longitude.getValue() == null) {
            getLogger().info("Indexing fulltext document owner [" + owner + "], id [" + identifier + "].");
            this.indexDocument(index, doc);
        } else {
            getLogger().info("Indexing fulltext document owner [", owner, "] id [", String.valueOf(identifier),
                    "], geo [", latitude.getValueAsString(), longitude.getValueAsString(), "].");

            GeoLocation location = new GeoLocation();
            location.setLatitude(latitude);
            location.setLongitude(longitude);

            this.indexDocument(index, doc, location);
        }
    }

    /**
     * Search in the Lucene Adapter.
     * 
     * @param query
     *            the query to execute
     * 
     * @return the search result
     * 
     * @throws SearchException
     *             when Lucene cannot be aquired appropriately
     */
    private void indexDocument(String index, FulltextDocument document) throws SearchException {

        try {
            ServiceRequest<LuceneIndexRq> rq = new ServiceRequest<LuceneIndexRq>(super.getContext());
            LuceneIndexRq msg = new LuceneIndexRq();
            rq.setRequestMessage(msg);

            msg.setIndexName(new Name(index));
            msg.setDocument(document);

            Connection connection = ConnectionFactory.getInstance().createLocalConnection();
            LuceneAdapter adapter = LuceneAdapterLocator.getInstance().getAdapter(connection);
            IndexLucene indexLucene = adapter.getIndexLucene();

            indexLucene.index(rq);

        } catch (LuceneException le) {
            throw new SearchException("Error indexing to Lucene Adapter.", le);
        } catch (ServiceException se) {
            throw new SearchException("Error indexing to Lucene Adapter.", se);
        } catch (ConnectionException ce) {
            throw new SearchException("Cannot establish connection to Lucene Adapter.", ce);
        } catch (Exception e) {
            throw new SearchException("Error during indexing to Lucene Adapter.", e);
        }
    }

    /**
     * Search in the Lucene Adapter.
     * 
     * @param query
     *            the query to execute
     * @param location
     *            the geo location
     * 
     * @return the search result
     * 
     * @throws SearchException
     *             when Lucene cannot be aquired appropriately
     */
    private void indexDocument(String index, FulltextDocument document, GeoLocation location) throws SearchException {

        try {
            ServiceRequest<LuceneGeoIndexRq> rq = new ServiceRequest<LuceneGeoIndexRq>(super.getContext());
            LuceneGeoIndexRq msg = new LuceneGeoIndexRq();
            rq.setRequestMessage(msg);

            msg.setIndexName(new Name(index));
            msg.setDocument(document);
            msg.setLocation(location);

            Connection connection = ConnectionFactory.getInstance().createLocalConnection();
            LuceneAdapter adapter = LuceneAdapterLocator.getInstance().getAdapter(connection);
            IndexLucene indexLucene = adapter.getIndexLucene();

            indexLucene.indexGeo(rq);

        } catch (LuceneException le) {
            throw new SearchException("Error indexing to Lucene Adapter.", le);
        } catch (ServiceException se) {
            throw new SearchException("Error indexing to Lucene Adapter.", se);
        } catch (ConnectionException ce) {
            throw new SearchException("Cannot establish connection to Lucene Adapter.", ce);
        } catch (Exception e) {
            throw new SearchException("Error during indexing to Lucene Adapter.", e);
        }
    }
}
