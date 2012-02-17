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

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.locator.ComponentLocator;
import org.nabucco.framework.base.facade.component.locator.ComponentLocatorSupport;

/**
 * Locator for SearchComponent.
 *
 * @author NABUCCO Generator, PRODYNA AG
 */
public class SearchComponentLocator extends ComponentLocatorSupport<SearchComponent> implements
        ComponentLocator<SearchComponent> {

    private static SearchComponentLocator instance;

    /**
     * Constructs a new SearchComponentLocator instance.
     *
     * @param component the Class<SearchComponent>.
     * @param jndiName the String.
     */
    private SearchComponentLocator(String jndiName, Class<SearchComponent> component) {
        super(jndiName, component);
    }

    @Override
    public SearchComponent getComponent() throws ConnectionException {
        SearchComponent component = super.getComponent();
        if ((component instanceof SearchComponentLocal)) {
            return new SearchComponentLocalProxy(((SearchComponentLocal) component));
        }
        return component;
    }

    /**
     * Getter for the Instance.
     *
     * @return the SearchComponentLocator.
     */
    public static SearchComponentLocator getInstance() {
        if ((instance == null)) {
            instance = new SearchComponentLocator(SearchComponent.JNDI_NAME, SearchComponent.class);
        }
        return instance;
    }
}
