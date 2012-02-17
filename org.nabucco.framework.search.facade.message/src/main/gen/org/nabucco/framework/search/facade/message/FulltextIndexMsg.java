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
package org.nabucco.framework.search.facade.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.geo.GeoLocation;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.search.fulltext.FulltextDocument;
import org.nabucco.framework.base.facade.datatype.search.index.IndexContext;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * FulltextIndexMsg<p/>A message containing the data for indexing<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public class FulltextIndexMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m0,1;" };

    public static final String CONTEXT = "context";

    public static final String DOCUMENT = "document";

    public static final String LOCATION = "location";

    /** Common context data, needed for re-materializing the Documents data. */
    private IndexContext context;

    /** The document for indexing. */
    private FulltextDocument document;

    /** The geo location for this index document. */
    private GeoLocation location;

    /** Constructs a new FulltextIndexMsg instance. */
    public FulltextIndexMsg() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(CONTEXT, PropertyDescriptorSupport.createDatatype(CONTEXT, IndexContext.class, 0,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPONENT));
        propertyMap.put(DOCUMENT, PropertyDescriptorSupport.createDatatype(DOCUMENT, FulltextDocument.class, 1,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPONENT));
        propertyMap.put(LOCATION, PropertyDescriptorSupport.createDatatype(LOCATION, GeoLocation.class, 2,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPONENT));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(FulltextIndexMsg.getPropertyDescriptor(CONTEXT), this.getContext()));
        properties.add(super.createProperty(FulltextIndexMsg.getPropertyDescriptor(DOCUMENT), this.getDocument()));
        properties.add(super.createProperty(FulltextIndexMsg.getPropertyDescriptor(LOCATION), this.getLocation()));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(CONTEXT) && (property.getType() == IndexContext.class))) {
            this.setContext(((IndexContext) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DOCUMENT) && (property.getType() == FulltextDocument.class))) {
            this.setDocument(((FulltextDocument) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LOCATION) && (property.getType() == GeoLocation.class))) {
            this.setLocation(((GeoLocation) property.getInstance()));
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if ((this == obj)) {
            return true;
        }
        if ((obj == null)) {
            return false;
        }
        if ((this.getClass() != obj.getClass())) {
            return false;
        }
        if ((!super.equals(obj))) {
            return false;
        }
        final FulltextIndexMsg other = ((FulltextIndexMsg) obj);
        if ((this.context == null)) {
            if ((other.context != null))
                return false;
        } else if ((!this.context.equals(other.context)))
            return false;
        if ((this.document == null)) {
            if ((other.document != null))
                return false;
        } else if ((!this.document.equals(other.document)))
            return false;
        if ((this.location == null)) {
            if ((other.location != null))
                return false;
        } else if ((!this.location.equals(other.location)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.context == null) ? 0 : this.context.hashCode()));
        result = ((PRIME * result) + ((this.document == null) ? 0 : this.document.hashCode()));
        result = ((PRIME * result) + ((this.location == null) ? 0 : this.location.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Common context data, needed for re-materializing the Documents data.
     *
     * @return the IndexContext.
     */
    public IndexContext getContext() {
        return this.context;
    }

    /**
     * Common context data, needed for re-materializing the Documents data.
     *
     * @param context the IndexContext.
     */
    public void setContext(IndexContext context) {
        this.context = context;
    }

    /**
     * The document for indexing.
     *
     * @return the FulltextDocument.
     */
    public FulltextDocument getDocument() {
        return this.document;
    }

    /**
     * The document for indexing.
     *
     * @param document the FulltextDocument.
     */
    public void setDocument(FulltextDocument document) {
        this.document = document;
    }

    /**
     * The geo location for this index document.
     *
     * @return the GeoLocation.
     */
    public GeoLocation getLocation() {
        return this.location;
    }

    /**
     * The geo location for this index document.
     *
     * @param location the GeoLocation.
     */
    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(FulltextIndexMsg.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(FulltextIndexMsg.class).getAllProperties();
    }
}
