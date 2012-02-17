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
package org.nabucco.framework.search.facade.datatype.result.visitor;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionResolver;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.SearchIndexExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.search.fulltext.FulltextDocument;
import org.nabucco.framework.base.facade.datatype.search.fulltext.FulltextField;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * Abstract visitor for producing a human readable result for a fulltext search result.
 * 
 * @author Frank Ratschinski
 *
 */
public abstract class SearchResultVisitor extends DatatypeVisitor {

	protected static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
			SearchResultVisitor.class);

	protected StringBuilder buffer = null;

	protected SearchIndexExtension indexConfiguration = null;

	public SearchResultVisitor(String index, StringBuilder buffer) {

		this.buffer = buffer;

		try {
			ExtensionResolver resolver = new ExtensionResolver();
			indexConfiguration = (SearchIndexExtension) resolver.resolveExtension(
					ExtensionPointType.ORG_NABUCCO_FRAMEWORK_SEARCH_INDEX, index);

		} catch (Exception e) {
			logger.error(e, "Unable to reolve extension point[" + ExtensionPointType.ORG_NABUCCO_FRAMEWORK_SEARCH_INDEX
					+ "] + for id[" + index + "]");
		}
	}

	public void visit(FulltextDocument document) throws VisitorException {

		List<FulltextField> fields = document.getFieldList();
		for (FulltextField field : fields) {
			visit(field);
		}

	}

	public abstract void visit(FulltextField field) throws VisitorException;

}
