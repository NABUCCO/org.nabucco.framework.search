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

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.search.IndexException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.search.facade.message.FulltextIndexMsg;

/**
 * IndexDocumentServiceHandler
 * <p/>
 * service for indexing the fulltext search
 * <p/>
 * 
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public abstract class IndexDocumentServiceHandler extends PersistenceServiceHandlerSupport implements
        PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.search.impl.service.fulltext.IndexDocumentServiceHandler";

    /** Constructs a new IndexDocumentServiceHandler instance. */
    public IndexDocumentServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     * 
     * @param rq
     *            the ServiceRequest<FulltextIndexMsg>.
     * @return the ServiceResponse<EmptyServiceMessage>.
     * @throws IndexException
     */
    protected ServiceResponse<EmptyServiceMessage> invoke(ServiceRequest<FulltextIndexMsg> rq) throws IndexException {
        ServiceResponse<EmptyServiceMessage> rs;
        EmptyServiceMessage msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.indexDocument(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<EmptyServiceMessage>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (IndexException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            IndexException wrappedException = new IndexException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new IndexException("Error during service invocation.", e);
        }
    }

    /**
     * Queues a fulltext document.
     * 
     * @param msg
     *            the FulltextIndexMsg.
     * @return the EmptyServiceMessage.
     * @throws IndexException
     */
    protected abstract EmptyServiceMessage indexDocument(FulltextIndexMsg msg) throws IndexException;

    /**
     * Getter for the Id.
     * 
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
