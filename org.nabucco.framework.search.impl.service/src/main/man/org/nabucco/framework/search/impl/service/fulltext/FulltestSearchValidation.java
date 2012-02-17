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

import org.nabucco.framework.base.facade.datatype.extension.schema.property.PropertyExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;
import org.nabucco.framework.base.facade.datatype.validation.ValidationType;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.impl.service.aspect.validating.ValidatingAspect;
import org.nabucco.framework.base.impl.service.aspect.validating.ValidatingException;
import org.nabucco.framework.search.facade.message.SearchMsg;

/**
 * Validation of a search request
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class FulltestSearchValidation implements ValidatingAspect {

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            FulltestSearchValidation.class);

    @Override
    public void configure(PropertyExtension properties) {
    }

    @Override
    public void validateBefore(ServiceMessage request) throws ValidatingException {

        logger.debug("Validating the search request message");

        try {
            SearchMsg message = (SearchMsg) request;

            ValidationResult vr = new ValidationResult();
            message.validate(vr, ValidationType.DEEP);
        } catch (Exception e) {
            throw new ValidatingException("Validation of SearchMsg failed", e);
        }

    }

    @Override
    public void validateAfter(ServiceMessage request, ServiceMessage response) throws ValidatingException {
    }

}
