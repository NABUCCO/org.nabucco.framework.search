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
package org.nabucco.framework.search.impl.component;

import org.nabucco.framework.base.facade.component.handler.PostConstructHandler;
import org.nabucco.framework.base.facade.component.handler.PreDestroyHandler;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.facade.service.queryfilter.QueryFilterService;
import org.nabucco.framework.base.impl.component.ComponentSupport;
import org.nabucco.framework.search.facade.component.SearchComponentLocal;
import org.nabucco.framework.search.facade.component.SearchComponentRemote;
import org.nabucco.framework.search.facade.service.fulltext.FulltextIndex;
import org.nabucco.framework.search.facade.service.fulltext.FulltextSearch;
import org.nabucco.framework.search.facade.service.query.MaintainQuery;

/**
 * SearchComponentImpl<p/>Component for searching data in nabucco components and fulltext searches.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public class SearchComponentImpl extends ComponentSupport implements SearchComponentLocal, SearchComponentRemote {

    private static final long serialVersionUID = 1L;

    private static final String ID = "SearchComponent";

    /** Constructs a new SearchComponentImpl instance. */
    public SearchComponentImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PostConstructHandler handler = injector.inject(PostConstructHandler.getId());
        if ((handler == null)) {
            if (super.getLogger().isDebugEnabled()) {
                super.getLogger().debug("No post construct handler configured for \'", ID, "\'.");
            }
            return;
        }
        handler.setLocatable(this);
        handler.setLogger(super.getLogger());
        handler.invoke();
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PreDestroyHandler handler = injector.inject(PreDestroyHandler.getId());
        if ((handler == null)) {
            if (super.getLogger().isDebugEnabled()) {
                super.getLogger().debug("No pre destroy handler configured for \'", ID, "\'.");
            }
            return;
        }
        handler.setLocatable(this);
        handler.setLogger(super.getLogger());
        handler.invoke();
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return COMPONENT_NAME;
    }

    @Override
    public String getJndiName() {
        return JNDI_NAME;
    }

    @Override
    public ComponentRelationService getComponentRelationService() throws ServiceException {
        return super.lookup(SearchComponentJndiNames.COMPONENT_RELATION_SERVICE_REMOTE, ComponentRelationService.class);
    }

    @Override
    public ComponentRelationService getComponentRelationServiceLocal() throws ServiceException {
        return super.lookup(SearchComponentJndiNames.COMPONENT_RELATION_SERVICE_LOCAL, ComponentRelationService.class);
    }

    @Override
    public QueryFilterService getQueryFilterService() throws ServiceException {
        return super.lookup(SearchComponentJndiNames.QUERY_FILTER_SERVICE_REMOTE, QueryFilterService.class);
    }

    @Override
    public QueryFilterService getQueryFilterServiceLocal() throws ServiceException {
        return super.lookup(SearchComponentJndiNames.QUERY_FILTER_SERVICE_LOCAL, QueryFilterService.class);
    }

    @Override
    public FulltextIndex getFulltextIndexLocal() throws ServiceException {
        return super.lookup(SearchComponentJndiNames.FULLTEXT_INDEX_LOCAL, FulltextIndex.class);
    }

    @Override
    public FulltextIndex getFulltextIndex() throws ServiceException {
        return super.lookup(SearchComponentJndiNames.FULLTEXT_INDEX_REMOTE, FulltextIndex.class);
    }

    @Override
    public FulltextSearch getFulltextSearchLocal() throws ServiceException {
        return super.lookup(SearchComponentJndiNames.FULLTEXT_SEARCH_LOCAL, FulltextSearch.class);
    }

    @Override
    public FulltextSearch getFulltextSearch() throws ServiceException {
        return super.lookup(SearchComponentJndiNames.FULLTEXT_SEARCH_REMOTE, FulltextSearch.class);
    }

    @Override
    public MaintainQuery getMaintainQueryLocal() throws ServiceException {
        return super.lookup(SearchComponentJndiNames.MAINTAIN_QUERY_LOCAL, MaintainQuery.class);
    }

    @Override
    public MaintainQuery getMaintainQuery() throws ServiceException {
        return super.lookup(SearchComponentJndiNames.MAINTAIN_QUERY_REMOTE, MaintainQuery.class);
    }
}
