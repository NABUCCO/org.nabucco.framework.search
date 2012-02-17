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
package org.nabucco.framework.search.impl.service.index;

import javax.persistence.EntityManager;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.search.fulltext.FulltextDocument;
import org.nabucco.framework.base.facade.datatype.search.index.IndexContext;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationResult;
import org.nabucco.framework.base.facade.datatype.serialization.xml.XmlSerializer;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.search.IndexRequestMsg;
import org.nabucco.framework.base.facade.service.index.IndexService;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.search.facade.component.SearchComponent;
import org.nabucco.framework.search.facade.component.SearchComponentLocator;
import org.nabucco.framework.search.facade.datatype.IndexQueue;

/**
 * IndexServiceImpl
 * 
 * @author Frank Ratschinksi, PRODYNA AG
 */
public class IndexServiceImpl extends ServiceSupport implements IndexService {

    private static final long serialVersionUID = 1L;

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(IndexServiceImpl.class);

    private EntityManager entityManager;

    @Override
    public synchronized void postConstruct() {
        super.postConstruct();

        try {
            SearchComponent component = SearchComponentLocator.getInstance().getComponent();
            component.getName();
        } catch (Exception e) {
            logger.warning(e, "Cannot start SearchComponent, indexing ist not scheduled");
        }
    }

    @Override
    public ServiceResponse<EmptyServiceMessage> resolveExtension(ServiceRequest<IndexRequestMsg> rq)
            throws SearchException {

        try {

            IndexRequestMsg msg = rq.getRequestMessage();

            IndexContext context = msg.getContext();
            FulltextDocument document = msg.getDocument();

            XmlSerializer serializer = new XmlSerializer();
            SerializationResult res = serializer.serialize(document);

            PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                    this.entityManager, logger);

            IndexQueue queue = new IndexQueue();
            queue.setOwner(context.getOwner());
            queue.setComponentRef(context.getComponent());
            queue.setDatatypeRef(context.getDatatype());
            queue.setTableRef(context.getTable());
            queue.setIdentifierRef(context.getIdentifier());
            queue.setIndexName(context.getIndexName());
            queue.setFulltextData(res.getContent());

            if (context.getLocation() != null) {
                queue.setLatitude(context.getLocation().getLatitude());
                queue.setLongitude(context.getLocation().getLongitude());
            }

            queue.setDatatypeState(DatatypeState.INITIALIZED);

            persistenceManager.persist(queue);

        } catch (SerializationException se) {
            getLogger().error(se, "Error while serialization of a FulltextDocument to XML.");
            throw new SearchException("Error while serialization of a FulltextDocument to XML.", se);
        } catch (PersistenceException pe) {
            getLogger().error(pe, "Error while persisting an FulltextDocument to the IndexQueue.");
            throw new SearchException("Error while persisting an FulltextDocument to the IndexQueue.", pe);
        }

        ServiceResponse<EmptyServiceMessage> rs = new ServiceResponse<EmptyServiceMessage>(rq.getContext());
        rs.setResponseMessage(new EmptyServiceMessage());
        return rs;

    }

}
