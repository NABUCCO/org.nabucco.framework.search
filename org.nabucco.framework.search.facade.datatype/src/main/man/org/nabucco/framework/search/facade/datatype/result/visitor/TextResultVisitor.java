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

import org.nabucco.framework.base.facade.datatype.BasetypeType;
import org.nabucco.framework.base.facade.datatype.search.fulltext.FulltextField;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * TextResultVisitor
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class TextResultVisitor extends SearchResultVisitor {

    public TextResultVisitor(String index, StringBuilder buffer) {
        super(index, buffer);
    }

    @Override
    @SuppressWarnings("unused")
    public void visit(FulltextField field) throws VisitorException {

        String fieldname = field.getFieldName().getValue();
        String fieldValue = field.getFieldValue().getValue();
        BasetypeType fieldType = field.getFieldType();

        buffer.append(fieldValue).append(" ");

    }

}
