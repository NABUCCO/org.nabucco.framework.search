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

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.context.ServiceContextFactory;
import org.nabucco.framework.base.impl.service.timer.Timer;
import org.nabucco.framework.base.impl.service.timer.TimerExecutionException;
import org.nabucco.framework.base.impl.service.timer.TimerPriority;
import org.nabucco.framework.search.facade.component.SearchComponentLocator;
import org.nabucco.framework.search.facade.service.fulltext.FulltextIndex;

/**
 * IndexingTimer
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class IndexingTimer extends Timer {

	private static final long serialVersionUID = 1L;

	public static final String INDEXING_TIMER = "IndexingTimer";

	private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(IndexingTimer.class);

	private FulltextIndex indexService;
	
	public IndexingTimer() {
		super(INDEXING_TIMER, TimerPriority.MEDIUM);
		
		//configure();
	}

	@Override
	public void execute() throws TimerExecutionException {

		if (indexService == null) {
			configure();
		}
		
		try {

			ServiceRequest<EmptyServiceMessage> rq = new ServiceRequest<EmptyServiceMessage>(ServiceContextFactory.getInstance().newServiceMessageContext());
			rq.setRequestMessage(new EmptyServiceMessage());
			indexService.runIndexing(rq);
			
			
		} catch (Exception e) {
			logger.warning(e, "Cannot execute indexing, trying to use new FulltextIndex Service instance");
			configure();
		}

	}

	private void configure() {
		
		try {

			SearchComponentLocator locator =  SearchComponentLocator.getInstance();
			indexService = locator.getComponent().getFulltextIndex();
			
			
		} catch (Exception e) {
			logger.error(e, "Error while getting FulltextIndex Service");
		}
		
		
	}
}
