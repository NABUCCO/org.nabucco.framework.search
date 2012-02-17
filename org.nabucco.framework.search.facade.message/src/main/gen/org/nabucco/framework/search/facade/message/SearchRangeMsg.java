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
import org.nabucco.framework.base.facade.datatype.geo.Distance;
import org.nabucco.framework.base.facade.datatype.geo.GeoLocation;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.search.query.FulltextQuery;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * SearchRangeMsg<p/>Searches for an index in a specific geo area.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-04-28
 */
public class SearchRangeMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "l0,n;u0,n;m1,1;" };

    public static final String QUERY = "query";

    public static final String LOCATION = "location";

    public static final String DISTANCE = "distance";

    /** The query for the fulltext engine. */
    public FulltextQuery query;

    /** The central geo location for this search. */
    public GeoLocation location;

    /** The distance of the search range. */
    public Distance distance;

    /** Constructs a new SearchRangeMsg instance. */
    public SearchRangeMsg() {
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
        propertyMap.put(QUERY, PropertyDescriptorSupport.createDatatype(QUERY, FulltextQuery.class, 0,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPONENT));
        propertyMap.put(LOCATION, PropertyDescriptorSupport.createDatatype(LOCATION, GeoLocation.class, 1,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPONENT));
        propertyMap.put(DISTANCE,
                PropertyDescriptorSupport.createBasetype(DISTANCE, Distance.class, 2, PROPERTY_CONSTRAINTS[2], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(SearchRangeMsg.getPropertyDescriptor(QUERY), this.getQuery()));
        properties.add(super.createProperty(SearchRangeMsg.getPropertyDescriptor(LOCATION), this.getLocation()));
        properties.add(super.createProperty(SearchRangeMsg.getPropertyDescriptor(DISTANCE), this.distance));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(QUERY) && (property.getType() == FulltextQuery.class))) {
            this.setQuery(((FulltextQuery) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LOCATION) && (property.getType() == GeoLocation.class))) {
            this.setLocation(((GeoLocation) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DISTANCE) && (property.getType() == Distance.class))) {
            this.setDistance(((Distance) property.getInstance()));
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
        final SearchRangeMsg other = ((SearchRangeMsg) obj);
        if ((this.query == null)) {
            if ((other.query != null))
                return false;
        } else if ((!this.query.equals(other.query)))
            return false;
        if ((this.location == null)) {
            if ((other.location != null))
                return false;
        } else if ((!this.location.equals(other.location)))
            return false;
        if ((this.distance == null)) {
            if ((other.distance != null))
                return false;
        } else if ((!this.distance.equals(other.distance)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.query == null) ? 0 : this.query.hashCode()));
        result = ((PRIME * result) + ((this.location == null) ? 0 : this.location.hashCode()));
        result = ((PRIME * result) + ((this.distance == null) ? 0 : this.distance.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * The query for the fulltext engine.
     *
     * @return the FulltextQuery.
     */
    public FulltextQuery getQuery() {
        return this.query;
    }

    /**
     * The query for the fulltext engine.
     *
     * @param query the FulltextQuery.
     */
    public void setQuery(FulltextQuery query) {
        this.query = query;
    }

    /**
     * The central geo location for this search.
     *
     * @return the GeoLocation.
     */
    public GeoLocation getLocation() {
        return this.location;
    }

    /**
     * The central geo location for this search.
     *
     * @param location the GeoLocation.
     */
    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    /**
     * The distance of the search range.
     *
     * @return the Distance.
     */
    public Distance getDistance() {
        return this.distance;
    }

    /**
     * The distance of the search range.
     *
     * @param distance the Distance.
     */
    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SearchRangeMsg.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SearchRangeMsg.class).getAllProperties();
    }
}
