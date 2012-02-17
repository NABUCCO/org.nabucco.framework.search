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
package org.nabucco.framework.search.ui.web.communication.query;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.framework.search.facade.exception.SearchException;
import org.nabucco.framework.search.facade.message.OrgMsg;
import org.nabucco.framework.search.facade.service.query.MaintainQuery;

/**
 * MaintainQueryDelegate<p/>service for storing a query for later reuse<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-22
 */
public class MaintainQueryDelegate extends ServiceDelegateSupport {

    private MaintainQuery service;

    /**
     * Constructs a new MaintainQueryDelegate instance.
     *
     * @param service the MaintainQuery.
     */
    public MaintainQueryDelegate(MaintainQuery service) {
        super();
        this.service = service;
    }

    /**
     * Maintain.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the OrgMsg.
     * @return the EmptyServiceMessage.
     * @throws SearchException
     */
    public EmptyServiceMessage maintain(OrgMsg message, NabuccoSession session, ServiceSubContext... subContexts)
            throws SearchException {
        ServiceRequest<OrgMsg> request = new ServiceRequest<OrgMsg>(super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);
        ServiceResponse<EmptyServiceMessage> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.maintain(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(MaintainQuery.class, "maintain", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new SearchException("Cannot execute service operation: MaintainQuery.maintain");
    }
}
