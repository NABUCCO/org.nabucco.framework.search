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
import org.nabucco.framework.search.facade.service.fulltext.FulltextIndex;
import org.nabucco.framework.search.facade.service.fulltext.FulltextSearch;
import org.nabucco.framework.search.facade.service.query.MaintainQuery;

/**
 * SearchComponentLocal<p/>Component for searching data in nabucco components and fulltext searches.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public interface SearchComponentLocal extends SearchComponent {

    /**
     * Getter for the ComponentRelationServiceLocal.
     *
     * @return the ComponentRelationService.
     * @throws ServiceException
     */
    ComponentRelationService getComponentRelationServiceLocal() throws ServiceException;

    /**
     * Getter for the QueryFilterServiceLocal.
     *
     * @return the QueryFilterService.
     * @throws ServiceException
     */
    QueryFilterService getQueryFilterServiceLocal() throws ServiceException;

    /**
     * Getter for the FulltextIndexLocal.
     *
     * @return the FulltextIndex.
     * @throws ServiceException
     */
    FulltextIndex getFulltextIndexLocal() throws ServiceException;

    /**
     * Getter for the FulltextSearchLocal.
     *
     * @return the FulltextSearch.
     * @throws ServiceException
     */
    FulltextSearch getFulltextSearchLocal() throws ServiceException;

    /**
     * Getter for the MaintainQueryLocal.
     *
     * @return the MaintainQuery.
     * @throws ServiceException
     */
    MaintainQuery getMaintainQueryLocal() throws ServiceException;
}
