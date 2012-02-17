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
package org.nabucco.framework.search.facade.service.fulltext;

import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.search.facade.exception.SearchException;
import org.nabucco.framework.search.facade.message.SearchMsg;
import org.nabucco.framework.search.facade.message.SearchRangeMsg;
import org.nabucco.framework.search.facade.message.SearchResultMsg;

/**
 * FulltextSearch<p/>service for executing the fulltext search<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public interface FulltextSearch extends Service {

    /**
     * Searching in fulltext index.
     *
     * @param rq the ServiceRequest<SearchMsg>.
     * @return the ServiceResponse<SearchResultMsg>.
     * @throws SearchException
     */
    ServiceResponse<SearchResultMsg> fulltextSearch(ServiceRequest<SearchMsg> rq) throws SearchException;

    /**
     * Searching in fulltext index with geo location and range.
     *
     * @param rq the ServiceRequest<SearchRangeMsg>.
     * @return the ServiceResponse<SearchResultMsg>.
     * @throws SearchException
     */
    ServiceResponse<SearchResultMsg> fulltextRangeSearch(ServiceRequest<SearchRangeMsg> rq) throws SearchException;
}
