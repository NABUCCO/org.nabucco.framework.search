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
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.impl.service.timer.TimerService;

/**
 * SearchComponentPostConstructHandler
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class SearchComponentPostConstructHandler extends PostConstructHandler {

    private static final long serialVersionUID = 1L;

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            SearchComponentPostConstructHandler.class);

    private static boolean inited = false;

    @Override
    public synchronized void invoke() {

        if (!inited) {
            logger.debug("Registering IndexTimer on TimerService");
            try {
                TimerService ts = TimerService.getInstance();
                IndexingTimer timer = new IndexingTimer();
                ts.schedule(timer);

            } catch (Exception e) {
                logger.error(e, "Cannot configure IndexingTimer");
            }
        }
        inited = true;

    }

}
