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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.search.facade.exception.SearchException;
import org.nabucco.framework.search.facade.message.SearchMsg;
import org.nabucco.framework.search.facade.message.SearchRangeMsg;
import org.nabucco.framework.search.facade.message.SearchResultMsg;
import org.nabucco.framework.search.facade.service.fulltext.FulltextSearch;

/**
 * FulltextSearchImpl<p/>service for executing the fulltext search<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public class FulltextSearchImpl extends ServiceSupport implements FulltextSearch {

    private static final long serialVersionUID = 1L;

    private static final String ID = "FulltextSearch";

    private static Map<String, String[]> ASPECTS;

    private FulltextSearchServiceHandler fulltextSearchServiceHandler;

    private FulltextRangeSearchServiceHandler fulltextRangeSearchServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new FulltextSearchImpl instance. */
    public FulltextSearchImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.fulltextSearchServiceHandler = injector.inject(FulltextSearchServiceHandler.getId());
        if ((this.fulltextSearchServiceHandler != null)) {
            this.fulltextSearchServiceHandler.setPersistenceManager(persistenceManager);
            this.fulltextSearchServiceHandler.setLogger(super.getLogger());
        }
        this.fulltextRangeSearchServiceHandler = injector.inject(FulltextRangeSearchServiceHandler.getId());
        if ((this.fulltextRangeSearchServiceHandler != null)) {
            this.fulltextRangeSearchServiceHandler.setPersistenceManager(persistenceManager);
            this.fulltextRangeSearchServiceHandler.setLogger(super.getLogger());
        }
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
    }

    @Override
    public String[] getAspects(String operationName) {
        if ((ASPECTS == null)) {
            ASPECTS = new HashMap<String, String[]>();
            ASPECTS.put("fulltextSearch",
                    new String[] { "org.nabucco.aspect.validating", "org.nabucco.aspect.caching" });
            ASPECTS.put("fulltextRangeSearch", new String[] { "org.nabucco.aspect.validating",
                    "org.nabucco.aspect.caching" });
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<SearchResultMsg> fulltextSearch(ServiceRequest<SearchMsg> rq) throws SearchException {
        if ((this.fulltextSearchServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for fulltextSearch().");
            throw new InjectionException("No service implementation configured for fulltextSearch().");
        }
        ServiceResponse<SearchResultMsg> rs;
        this.fulltextSearchServiceHandler.init();
        rs = this.fulltextSearchServiceHandler.invoke(rq);
        this.fulltextSearchServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<SearchResultMsg> fulltextRangeSearch(ServiceRequest<SearchRangeMsg> rq)
            throws SearchException {
        if ((this.fulltextRangeSearchServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for fulltextRangeSearch().");
            throw new InjectionException("No service implementation configured for fulltextRangeSearch().");
        }
        ServiceResponse<SearchResultMsg> rs;
        this.fulltextRangeSearchServiceHandler.init();
        rs = this.fulltextRangeSearchServiceHandler.invoke(rq);
        this.fulltextRangeSearchServiceHandler.finish();
        return rs;
    }
}
