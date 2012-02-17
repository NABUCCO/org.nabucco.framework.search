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

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.search.facade.service.fulltext.FulltextIndex;
import org.nabucco.framework.search.facade.service.fulltext.FulltextSearch;
import org.nabucco.framework.search.facade.service.query.MaintainQuery;

/**
 * SearchComponent<p/>Component for searching data in nabucco components and fulltext searches.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public interface SearchComponent extends Component {

    final String COMPONENT_NAME = "org.nabucco.framework.search";

    final String COMPONENT_PREFIX = "sear";

    final String JNDI_NAME = ((((JNDI_PREFIX + "/") + COMPONENT_NAME) + "/") + "org.nabucco.framework.search.facade.component.SearchComponent");

    /**
     * Getter for the FulltextIndex.
     *
     * @return the FulltextIndex.
     * @throws ServiceException
     */
    FulltextIndex getFulltextIndex() throws ServiceException;

    /**
     * Getter for the FulltextSearch.
     *
     * @return the FulltextSearch.
     * @throws ServiceException
     */
    FulltextSearch getFulltextSearch() throws ServiceException;

    /**
     * Getter for the MaintainQuery.
     *
     * @return the MaintainQuery.
     * @throws ServiceException
     */
    MaintainQuery getMaintainQuery() throws ServiceException;
}
