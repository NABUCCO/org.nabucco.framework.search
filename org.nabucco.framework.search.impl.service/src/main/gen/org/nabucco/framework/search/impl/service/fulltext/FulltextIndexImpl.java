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
import org.nabucco.framework.base.facade.exception.search.IndexException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.search.facade.service.fulltext.FulltextIndex;

/**
 * FulltextIndexImpl<p/>service for indexing the fulltext search<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public class FulltextIndexImpl extends ServiceSupport implements FulltextIndex {

    private static final long serialVersionUID = 1L;

    private static final String ID = "FulltextIndex";

    private static Map<String, String[]> ASPECTS;

    private RunIndexingServiceHandler runIndexingServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new FulltextIndexImpl instance. */
    public FulltextIndexImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.runIndexingServiceHandler = injector.inject(RunIndexingServiceHandler.getId());
        if ((this.runIndexingServiceHandler != null)) {
            this.runIndexingServiceHandler.setPersistenceManager(persistenceManager);
            this.runIndexingServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("runIndexing", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<EmptyServiceMessage> runIndexing(ServiceRequest<EmptyServiceMessage> rq)
            throws IndexException {
        if ((this.runIndexingServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for runIndexing().");
            throw new InjectionException("No service implementation configured for runIndexing().");
        }
        ServiceResponse<EmptyServiceMessage> rs;
        this.runIndexingServiceHandler.init();
        rs = this.runIndexingServiceHandler.invoke(rq);
        this.runIndexingServiceHandler.finish();
        return rs;
    }
}
