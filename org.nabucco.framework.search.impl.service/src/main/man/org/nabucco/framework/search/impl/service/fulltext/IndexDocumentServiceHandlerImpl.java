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

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.search.fulltext.FulltextDocument;
import org.nabucco.framework.base.facade.datatype.search.index.IndexContext;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationResult;
import org.nabucco.framework.base.facade.datatype.serialization.xml.XmlSerializer;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.search.IndexException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.search.facade.datatype.IndexQueue;
import org.nabucco.framework.search.facade.message.FulltextIndexMsg;

/**
 * Queues the FulltextDocument within the Index Queue of the search component.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class IndexDocumentServiceHandlerImpl extends IndexDocumentServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected EmptyServiceMessage indexDocument(FulltextIndexMsg msg) throws IndexException {

        try {

            IndexContext context = msg.getContext();
            FulltextDocument document = msg.getDocument();

            XmlSerializer serializer = new XmlSerializer();
            SerializationResult res = serializer.serialize(document);

            IndexQueue queue = new IndexQueue();
            queue.setOwner(context.getOwner());
            queue.setComponentRef(context.getComponent());
            queue.setDatatypeRef(context.getDatatype());
            queue.setTableRef(context.getTable());
            queue.setIdentifierRef(context.getIdentifier());
            queue.setIndexName(context.getIndexName());
            queue.setFulltextData(res.getContent());

            queue.setDatatypeState(DatatypeState.INITIALIZED);

            super.getPersistenceManager().persist(queue);

        } catch (SerializationException se) {
            getLogger().error(se, "Error while serialization of a FulltextDocument to XML.");
            throw new IndexException("Error while serialization of a FulltextDocument to XML.", se);
        } catch (PersistenceException pe) {
            getLogger().error(pe, "Error while persisting an FulltextDocument to the IndexQueue.");
            throw new IndexException("Error while persisting an FulltextDocument to the IndexQueue.", pe);
        }

        return new EmptyServiceMessage();

    }

}
