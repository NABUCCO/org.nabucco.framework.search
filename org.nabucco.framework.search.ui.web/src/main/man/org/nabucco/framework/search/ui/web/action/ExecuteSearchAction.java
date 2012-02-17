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
package org.nabucco.framework.search.ui.web.action;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.search.fulltext.FulltextDocument;
import org.nabucco.framework.base.facade.datatype.search.query.FulltextQuery;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.handler.search.ListSearchResultAction;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;
import org.nabucco.framework.search.facade.exception.SearchException;
import org.nabucco.framework.search.facade.message.SearchMsg;
import org.nabucco.framework.search.facade.message.SearchResultMsg;
import org.nabucco.framework.search.ui.web.communication.SearchComponentServiceDelegateFactory;
import org.nabucco.framework.search.ui.web.communication.fulltext.FulltextSearchDelegate;

/**
 * ExecuteSearchAction
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ExecuteSearchAction extends ListSearchResultAction {

    @Override
    protected List<FulltextDocument> search(FulltextQuery query, NabuccoWebSession session) throws ClientException {

        SearchMsg rq = new SearchMsg();
        rq.setQuery(query);

        try {
            SearchComponentServiceDelegateFactory factory = SearchComponentServiceDelegateFactory.getInstance();
            FulltextSearchDelegate searchService = factory.getFulltextSearch();

            SearchResultMsg rs = searchService.fulltextSearch(rq, session);
            return rs.getResultList();

        } catch (SearchException se) {
            throw new ClientException("Error searching in index '" + query.getIndexName() + "'.", se);
        }
    }

}
