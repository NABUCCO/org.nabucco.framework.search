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
package org.nabucco.framework.search.ui.rcp.communication.fulltext;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.search.facade.message.SearchMsg;
import org.nabucco.framework.search.facade.message.SearchRangeMsg;
import org.nabucco.framework.search.facade.message.SearchResultMsg;
import org.nabucco.framework.search.facade.service.fulltext.FulltextSearch;

/**
 * FulltextSearchDelegate<p/>service for executing the fulltext search<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public class FulltextSearchDelegate extends ServiceDelegateSupport {

    private FulltextSearch service;

    /**
     * Constructs a new FulltextSearchDelegate instance.
     *
     * @param service the FulltextSearch.
     */
    public FulltextSearchDelegate(FulltextSearch service) {
        super();
        this.service = service;
    }

    /**
     * FulltextSearch.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the SearchMsg.
     * @return the SearchResultMsg.
     * @throws ClientException
     */
    public SearchResultMsg fulltextSearch(SearchMsg message, ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<SearchMsg> request = new ServiceRequest<SearchMsg>(super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<SearchResultMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.fulltextSearch(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(FulltextSearch.class, "fulltextSearch", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: FulltextSearch.fulltextSearch");
    }

    /**
     * FulltextRangeSearch.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the SearchRangeMsg.
     * @return the SearchResultMsg.
     * @throws ClientException
     */
    public SearchResultMsg fulltextRangeSearch(SearchRangeMsg message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<SearchRangeMsg> request = new ServiceRequest<SearchRangeMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<SearchResultMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.fulltextRangeSearch(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(FulltextSearch.class, "fulltextRangeSearch", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: FulltextSearch.fulltextRangeSearch");
    }
}
