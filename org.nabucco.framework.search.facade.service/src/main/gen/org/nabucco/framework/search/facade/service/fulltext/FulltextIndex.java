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

import org.nabucco.framework.base.facade.exception.search.IndexException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;

/**
 * FulltextIndex<p/>service for indexing the fulltext search<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public interface FulltextIndex extends Service {

    /**
     * Runs the indexing, using all available fulltext documents from the queue
     *
     * @param rq the ServiceRequest<EmptyServiceMessage>.
     * @return the ServiceResponse<EmptyServiceMessage>.
     * @throws IndexException
     */
    ServiceResponse<EmptyServiceMessage> runIndexing(ServiceRequest<EmptyServiceMessage> rq) throws IndexException;
}