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
package org.nabucco.framework.search.impl.service.query;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.search.facade.exception.SearchException;
import org.nabucco.framework.search.facade.message.OrgMsg;
import org.nabucco.framework.search.facade.service.query.MaintainQuery;

/**
 * MaintainQueryImpl<p/>service for storing a query for later reuse<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-22
 */
public class MaintainQueryImpl extends ServiceSupport implements MaintainQuery {

    private static final long serialVersionUID = 1L;

    private static final String ID = "MaintainQuery";

    private static Map<String, String[]> ASPECTS;

    private MaintainServiceHandler maintainServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new MaintainQueryImpl instance. */
    public MaintainQueryImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.maintainServiceHandler = injector.inject(MaintainServiceHandler.getId());
        if ((this.maintainServiceHandler != null)) {
            this.maintainServiceHandler.setPersistenceManager(persistenceManager);
            this.maintainServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("maintain", new String[] { "org.nabucco.aspect.indexing" });
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<EmptyServiceMessage> maintain(ServiceRequest<OrgMsg> rq) throws SearchException {
        if ((this.maintainServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for maintain().");
            throw new InjectionException("No service implementation configured for maintain().");
        }
        ServiceResponse<EmptyServiceMessage> rs;
        this.maintainServiceHandler.init();
        rs = this.maintainServiceHandler.invoke(rq);
        this.maintainServiceHandler.finish();
        return rs;
    }
}
