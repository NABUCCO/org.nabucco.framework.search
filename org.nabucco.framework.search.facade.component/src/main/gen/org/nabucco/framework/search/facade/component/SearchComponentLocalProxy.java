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
package org.nabucco.framework.search.facade.component;

import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.facade.service.queryfilter.QueryFilterService;
import org.nabucco.framework.search.facade.component.SearchComponent;
import org.nabucco.framework.search.facade.service.fulltext.FulltextIndex;
import org.nabucco.framework.search.facade.service.fulltext.FulltextSearch;
import org.nabucco.framework.search.facade.service.query.MaintainQuery;

/**
 * SearchComponentLocalProxy<p/>Component for searching data in nabucco components and fulltext searches.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public class SearchComponentLocalProxy implements SearchComponent {

    private static final long serialVersionUID = 1L;

    private final SearchComponentLocal delegate;

    /**
     * Constructs a new SearchComponentLocalProxy instance.
     *
     * @param delegate the SearchComponentLocal.
     */
    public SearchComponentLocalProxy(SearchComponentLocal delegate) {
        super();
        if ((delegate == null)) {
            throw new IllegalArgumentException("Cannot create local proxy for component [null].");
        }
        this.delegate = delegate;
    }

    @Override
    public String getId() {
        return this.delegate.getId();
    }

    @Override
    public String getName() {
        return this.delegate.getName();
    }

    @Override
    public String getJndiName() {
        return this.delegate.getJndiName();
    }

    @Override
    public ComponentRelationService getComponentRelationService() throws ServiceException {
        return this.delegate.getComponentRelationServiceLocal();
    }

    @Override
    public QueryFilterService getQueryFilterService() throws ServiceException {
        return this.delegate.getQueryFilterServiceLocal();
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }

    @Override
    public FulltextIndex getFulltextIndex() throws ServiceException {
        return this.delegate.getFulltextIndexLocal();
    }

    @Override
    public FulltextSearch getFulltextSearch() throws ServiceException {
        return this.delegate.getFulltextSearchLocal();
    }

    @Override
    public MaintainQuery getMaintainQuery() throws ServiceException {
        return this.delegate.getMaintainQueryLocal();
    }
}
