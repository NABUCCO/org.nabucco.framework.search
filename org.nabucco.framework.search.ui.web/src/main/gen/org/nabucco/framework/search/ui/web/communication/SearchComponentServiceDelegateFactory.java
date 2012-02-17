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
package org.nabucco.framework.search.ui.web.communication;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateFactorySupport;
import org.nabucco.framework.search.facade.component.SearchComponent;
import org.nabucco.framework.search.facade.component.SearchComponentLocator;
import org.nabucco.framework.search.ui.web.communication.fulltext.FulltextIndexDelegate;
import org.nabucco.framework.search.ui.web.communication.fulltext.FulltextSearchDelegate;
import org.nabucco.framework.search.ui.web.communication.query.MaintainQueryDelegate;

/**
 * ServiceDelegateFactoryTemplate<p/>Component for searching data in nabucco components and fulltext searches.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public class SearchComponentServiceDelegateFactory extends ServiceDelegateFactorySupport<SearchComponent> {

    private static SearchComponentServiceDelegateFactory instance = new SearchComponentServiceDelegateFactory();

    private FulltextIndexDelegate fulltextIndexDelegate;

    private FulltextSearchDelegate fulltextSearchDelegate;

    private MaintainQueryDelegate maintainQueryDelegate;

    /** Constructs a new SearchComponentServiceDelegateFactory instance. */
    private SearchComponentServiceDelegateFactory() {
        super(SearchComponentLocator.getInstance());
    }

    /**
     * Getter for the FulltextIndex.
     *
     * @return the FulltextIndexDelegate.
     * @throws ClientException
     */
    public FulltextIndexDelegate getFulltextIndex() throws ClientException {
        try {
            if ((this.fulltextIndexDelegate == null)) {
                this.fulltextIndexDelegate = new FulltextIndexDelegate(this.getComponent().getFulltextIndex());
            }
            return this.fulltextIndexDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: FulltextIndex", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the FulltextSearch.
     *
     * @return the FulltextSearchDelegate.
     * @throws ClientException
     */
    public FulltextSearchDelegate getFulltextSearch() throws ClientException {
        try {
            if ((this.fulltextSearchDelegate == null)) {
                this.fulltextSearchDelegate = new FulltextSearchDelegate(this.getComponent().getFulltextSearch());
            }
            return this.fulltextSearchDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: FulltextSearch", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the MaintainQuery.
     *
     * @return the MaintainQueryDelegate.
     * @throws ClientException
     */
    public MaintainQueryDelegate getMaintainQuery() throws ClientException {
        try {
            if ((this.maintainQueryDelegate == null)) {
                this.maintainQueryDelegate = new MaintainQueryDelegate(this.getComponent().getMaintainQuery());
            }
            return this.maintainQueryDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: MaintainQuery", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the Instance.
     *
     * @return the SearchComponentServiceDelegateFactory.
     */
    public static SearchComponentServiceDelegateFactory getInstance() {
        return instance;
    }
}
