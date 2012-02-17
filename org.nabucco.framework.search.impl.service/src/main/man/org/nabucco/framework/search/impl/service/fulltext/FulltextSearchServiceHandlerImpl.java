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
import org.nabucco.adapter.lucene.facade.message.LuceneSearchRq;
import org.nabucco.adapter.lucene.facade.message.LuceneSearchRs;
import org.nabucco.adapter.lucene.facade.service.search.SearchLucene;
import org.nabucco.framework.base.facade.component.connection.Connection;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.connection.ConnectionFactory;
import org.nabucco.framework.base.facade.datatype.search.fulltext.FulltextDocument;
import org.nabucco.framework.base.facade.datatype.search.query.FulltextQuery;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.search.facade.exception.SearchException;
import org.nabucco.framework.search.facade.message.SearchMsg;
import org.nabucco.framework.search.facade.message.SearchResultMsg;

/**
 * FulltextSearchServiceHandlerImpl
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class FulltextSearchServiceHandlerImpl extends FulltextSearchServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected SearchResultMsg fulltextSearch(SearchMsg msg) throws SearchException {

        try {
            FulltextQuery query = msg.getQuery();
            List<FulltextDocument> result = this.search(query);

            SearchResultMsg response = new SearchResultMsg();
            response.getResultList().addAll(result);
            return response;

        } catch (Exception e) {
            throw new SearchException(e);
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
    private List<FulltextDocument> search(FulltextQuery query) throws SearchException {

        try {
            ServiceRequest<LuceneSearchRq> rq = new ServiceRequest<LuceneSearchRq>(super.getContext());
            LuceneSearchRq msg = new LuceneSearchRq();
            rq.setRequestMessage(msg);

            msg.setQuery(query);

            Connection connection = ConnectionFactory.getInstance().createLocalConnection();
            LuceneAdapter adapter = LuceneAdapterLocator.getInstance().getAdapter(connection);
            SearchLucene searchLucene = adapter.getSearchLucene();

            ServiceResponse<LuceneSearchRs> rs = searchLucene.search(rq);
            List<FulltextDocument> result = rs.getResponseMessage().getDocumentList();

            return result;

        } catch (LuceneException le) {
            throw new SearchException("Error searching in Lucene Adapter.", le);
        } catch (ServiceException se) {
            throw new SearchException("Error searching in Lucene Adapter.", se);
        } catch (ConnectionException ce) {
            throw new SearchException("Cannot establish connection to Lucene Adapter.", ce);
        } catch (Exception e) {
            throw new SearchException("Error during search in Lucene Adapter.", e);
        }
    }

}
