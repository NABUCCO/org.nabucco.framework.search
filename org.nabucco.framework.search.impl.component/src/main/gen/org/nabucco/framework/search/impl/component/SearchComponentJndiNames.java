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

/**
 * SearchComponentJndiNames<p/>Component for searching data in nabucco components and fulltext searches.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public interface SearchComponentJndiNames {

    final String COMPONENT_RELATION_SERVICE_LOCAL = "nabucco/org.nabucco.framework.search/org.nabucco.framework.search.facade.component.ComponentRelationService/local";

    final String COMPONENT_RELATION_SERVICE_REMOTE = "nabucco/org.nabucco.framework.search/org.nabucco.framework.search.facade.component.ComponentRelationService/remote";

    final String QUERY_FILTER_SERVICE_LOCAL = "nabucco/org.nabucco.framework.search/org.nabucco.framework.search.facade.component.QueryFilterService/local";

    final String QUERY_FILTER_SERVICE_REMOTE = "nabucco/org.nabucco.framework.search/org.nabucco.framework.search.facade.component.QueryFilterService/remote";

    final String FULLTEXT_INDEX_LOCAL = "nabucco/org.nabucco.framework.search/org.nabucco.framework.search.facade.service.fulltext.FulltextIndex/local";

    final String FULLTEXT_INDEX_REMOTE = "nabucco/org.nabucco.framework.search/org.nabucco.framework.search.facade.service.fulltext.FulltextIndex/remote";

    final String FULLTEXT_SEARCH_LOCAL = "nabucco/org.nabucco.framework.search/org.nabucco.framework.search.facade.service.fulltext.FulltextSearch/local";

    final String FULLTEXT_SEARCH_REMOTE = "nabucco/org.nabucco.framework.search/org.nabucco.framework.search.facade.service.fulltext.FulltextSearch/remote";

    final String MAINTAIN_QUERY_LOCAL = "nabucco/org.nabucco.framework.search/org.nabucco.framework.search.facade.service.query.MaintainQuery/local";

    final String MAINTAIN_QUERY_REMOTE = "nabucco/org.nabucco.framework.search/org.nabucco.framework.search.facade.service.query.MaintainQuery/remote";
}
