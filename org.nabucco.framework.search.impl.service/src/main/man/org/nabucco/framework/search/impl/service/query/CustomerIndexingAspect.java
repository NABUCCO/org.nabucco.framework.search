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

import java.util.List;

import org.nabucco.framework.base.facade.datatype.BasetypeType;
import org.nabucco.framework.base.facade.datatype.business.organization.Organization;
import org.nabucco.framework.base.facade.datatype.extension.schema.property.PropertyExtension;
import org.nabucco.framework.base.facade.datatype.search.fulltext.FulltextDocument;
import org.nabucco.framework.base.facade.datatype.search.fulltext.FulltextField;
import org.nabucco.framework.base.facade.datatype.search.index.IndexContext;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.impl.service.aspect.indexing.IndexingAspect;
import org.nabucco.framework.base.impl.service.aspect.indexing.IndexingAspectSupport;
import org.nabucco.framework.base.impl.service.aspect.indexing.IndexingException;
import org.nabucco.framework.search.facade.message.OrgMsg;

/**
 * CustomerIndexingAspect
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class CustomerIndexingAspect extends IndexingAspectSupport implements IndexingAspect {

    @Override
    public void configure(PropertyExtension arg0) {
    }

    @Override
    public void indexBefore(ServiceMessage requestMsg) throws IndexingException {

        Organization org = ((OrgMsg) requestMsg).getOrg();

        try {
            IndexContext ctx = new IndexContext();
            ctx.setComponent("SEAR");
            ctx.setDatatype(org.getClass().getName());
            ctx.setIdentifier(org.getId());
            ctx.setIndexName("KundenIndex");
            ctx.setTable("sear_customer");

            FulltextDocument doc = new FulltextDocument();
            List<FulltextField> fieldList = doc.getFieldList();
            {
                FulltextField name = new FulltextField();
                name.setFieldName("Kundenname");
                name.setFieldType(BasetypeType.STRING);
                name.setFieldValue(org.getName().getValue());
                fieldList.add(name);
            }
            {
                FulltextField ort = new FulltextField();
                ort.setFieldName("Ort");
                ort.setFieldType(BasetypeType.STRING);
                ort.setFieldValue("Frankfurt am Main");
                fieldList.add(ort);
            }

            super.index(ctx, doc);

        } catch (Exception e) {
            throw new IndexingException(e);
        }

    }

    @Override
    public void indexAfter(ServiceMessage requestMsg, ServiceMessage responseMsg) throws IndexingException {

    }

}
