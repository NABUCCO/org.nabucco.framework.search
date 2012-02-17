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
package org.nabucco.framework.search.facade.datatype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.ComponentRef;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeRef;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.TableRef;
import org.nabucco.framework.base.facade.datatype.geo.Latitude;
import org.nabucco.framework.base.facade.datatype.geo.Longitude;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.search.index.IndexName;
import org.nabucco.framework.search.facade.datatype.FulltextData;

/**
 * IndexQueue<p/>A queue for indexing Fulltext search documents.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-01
 */
public class IndexQueue extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;", "l3,255;u0,n;m1,1;", "l4,4;u0,n;m1,1;",
            "l3,255;u0,n;m1,1;", "l6,255;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l0,n;u0,n;m0,1;",
            "l0,n;u0,n;m0,1;" };

    public static final String OWNER = "owner";

    public static final String INDEXNAME = "indexName";

    public static final String COMPONENTREF = "componentRef";

    public static final String DATATYPEREF = "datatypeRef";

    public static final String TABLEREF = "tableRef";

    public static final String IDENTIFIERREF = "identifierRef";

    public static final String FULLTEXTDATA = "fulltextData";

    public static final String LATITUDE = "latitude";

    public static final String LONGITUDE = "longitude";

    /** The owner of the data to index. */
    private Owner owner;

    /** The name of the index (Datasource JNDI name) */
    private IndexName indexName;

    /** The component reference. */
    private ComponentRef componentRef;

    /** The datatype reference. */
    private DatatypeRef datatypeRef;

    /** The table reference. */
    private TableRef tableRef;

    /** The identifier of the object. */
    private Identifier identifierRef;

    /** The data (as xml) of the fulltext document. */
    private FulltextData fulltextData;

    /** Optional latitude geo information for the indexing. */
    private Latitude latitude;

    /** Optional longitude geo information for the indexing. */
    private Longitude longitude;

    /** Constructs a new IndexQueue instance. */
    public IndexQueue() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the IndexQueue.
     */
    protected void cloneObject(IndexQueue clone) {
        super.cloneObject(clone);
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getIndexName() != null)) {
            clone.setIndexName(this.getIndexName().cloneObject());
        }
        if ((this.getComponentRef() != null)) {
            clone.setComponentRef(this.getComponentRef().cloneObject());
        }
        if ((this.getDatatypeRef() != null)) {
            clone.setDatatypeRef(this.getDatatypeRef().cloneObject());
        }
        if ((this.getTableRef() != null)) {
            clone.setTableRef(this.getTableRef().cloneObject());
        }
        if ((this.getIdentifierRef() != null)) {
            clone.setIdentifierRef(this.getIdentifierRef().cloneObject());
        }
        if ((this.getFulltextData() != null)) {
            clone.setFulltextData(this.getFulltextData().cloneObject());
        }
        if ((this.getLatitude() != null)) {
            clone.setLatitude(this.getLatitude().cloneObject());
        }
        if ((this.getLongitude() != null)) {
            clone.setLongitude(this.getLongitude().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(OWNER,
                PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap
                .put(INDEXNAME, PropertyDescriptorSupport.createBasetype(INDEXNAME, IndexName.class, 4,
                        PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(COMPONENTREF, PropertyDescriptorSupport.createBasetype(COMPONENTREF, ComponentRef.class, 5,
                PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(DATATYPEREF, PropertyDescriptorSupport.createBasetype(DATATYPEREF, DatatypeRef.class, 6,
                PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(TABLEREF,
                PropertyDescriptorSupport.createBasetype(TABLEREF, TableRef.class, 7, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(IDENTIFIERREF, PropertyDescriptorSupport.createBasetype(IDENTIFIERREF, Identifier.class, 8,
                PROPERTY_CONSTRAINTS[5], false));
        propertyMap.put(FULLTEXTDATA, PropertyDescriptorSupport.createBasetype(FULLTEXTDATA, FulltextData.class, 9,
                PROPERTY_CONSTRAINTS[6], false));
        propertyMap.put(LATITUDE,
                PropertyDescriptorSupport.createBasetype(LATITUDE, Latitude.class, 10, PROPERTY_CONSTRAINTS[7], false));
        propertyMap.put(LONGITUDE, PropertyDescriptorSupport.createBasetype(LONGITUDE, Longitude.class, 11,
                PROPERTY_CONSTRAINTS[8], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(IndexQueue.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(IndexQueue.getPropertyDescriptor(INDEXNAME), this.indexName, null));
        properties.add(super.createProperty(IndexQueue.getPropertyDescriptor(COMPONENTREF), this.componentRef, null));
        properties.add(super.createProperty(IndexQueue.getPropertyDescriptor(DATATYPEREF), this.datatypeRef, null));
        properties.add(super.createProperty(IndexQueue.getPropertyDescriptor(TABLEREF), this.tableRef, null));
        properties.add(super.createProperty(IndexQueue.getPropertyDescriptor(IDENTIFIERREF), this.identifierRef, null));
        properties.add(super.createProperty(IndexQueue.getPropertyDescriptor(FULLTEXTDATA), this.fulltextData, null));
        properties.add(super.createProperty(IndexQueue.getPropertyDescriptor(LATITUDE), this.latitude, null));
        properties.add(super.createProperty(IndexQueue.getPropertyDescriptor(LONGITUDE), this.longitude, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(INDEXNAME) && (property.getType() == IndexName.class))) {
            this.setIndexName(((IndexName) property.getInstance()));
            return true;
        } else if ((property.getName().equals(COMPONENTREF) && (property.getType() == ComponentRef.class))) {
            this.setComponentRef(((ComponentRef) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DATATYPEREF) && (property.getType() == DatatypeRef.class))) {
            this.setDatatypeRef(((DatatypeRef) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TABLEREF) && (property.getType() == TableRef.class))) {
            this.setTableRef(((TableRef) property.getInstance()));
            return true;
        } else if ((property.getName().equals(IDENTIFIERREF) && (property.getType() == Identifier.class))) {
            this.setIdentifierRef(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FULLTEXTDATA) && (property.getType() == FulltextData.class))) {
            this.setFulltextData(((FulltextData) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LATITUDE) && (property.getType() == Latitude.class))) {
            this.setLatitude(((Latitude) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LONGITUDE) && (property.getType() == Longitude.class))) {
            this.setLongitude(((Longitude) property.getInstance()));
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
        final IndexQueue other = ((IndexQueue) obj);
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.indexName == null)) {
            if ((other.indexName != null))
                return false;
        } else if ((!this.indexName.equals(other.indexName)))
            return false;
        if ((this.componentRef == null)) {
            if ((other.componentRef != null))
                return false;
        } else if ((!this.componentRef.equals(other.componentRef)))
            return false;
        if ((this.datatypeRef == null)) {
            if ((other.datatypeRef != null))
                return false;
        } else if ((!this.datatypeRef.equals(other.datatypeRef)))
            return false;
        if ((this.tableRef == null)) {
            if ((other.tableRef != null))
                return false;
        } else if ((!this.tableRef.equals(other.tableRef)))
            return false;
        if ((this.identifierRef == null)) {
            if ((other.identifierRef != null))
                return false;
        } else if ((!this.identifierRef.equals(other.identifierRef)))
            return false;
        if ((this.fulltextData == null)) {
            if ((other.fulltextData != null))
                return false;
        } else if ((!this.fulltextData.equals(other.fulltextData)))
            return false;
        if ((this.latitude == null)) {
            if ((other.latitude != null))
                return false;
        } else if ((!this.latitude.equals(other.latitude)))
            return false;
        if ((this.longitude == null)) {
            if ((other.longitude != null))
                return false;
        } else if ((!this.longitude.equals(other.longitude)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.indexName == null) ? 0 : this.indexName.hashCode()));
        result = ((PRIME * result) + ((this.componentRef == null) ? 0 : this.componentRef.hashCode()));
        result = ((PRIME * result) + ((this.datatypeRef == null) ? 0 : this.datatypeRef.hashCode()));
        result = ((PRIME * result) + ((this.tableRef == null) ? 0 : this.tableRef.hashCode()));
        result = ((PRIME * result) + ((this.identifierRef == null) ? 0 : this.identifierRef.hashCode()));
        result = ((PRIME * result) + ((this.fulltextData == null) ? 0 : this.fulltextData.hashCode()));
        result = ((PRIME * result) + ((this.latitude == null) ? 0 : this.latitude.hashCode()));
        result = ((PRIME * result) + ((this.longitude == null) ? 0 : this.longitude.hashCode()));
        return result;
    }

    @Override
    public IndexQueue cloneObject() {
        IndexQueue clone = new IndexQueue();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The owner of the data to index.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * The owner of the data to index.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * The owner of the data to index.
     *
     * @param owner the String.
     */
    public void setOwner(String owner) {
        if ((this.owner == null)) {
            if ((owner == null)) {
                return;
            }
            this.owner = new Owner();
        }
        this.owner.setValue(owner);
    }

    /**
     * The name of the index (Datasource JNDI name)
     *
     * @return the IndexName.
     */
    public IndexName getIndexName() {
        return this.indexName;
    }

    /**
     * The name of the index (Datasource JNDI name)
     *
     * @param indexName the IndexName.
     */
    public void setIndexName(IndexName indexName) {
        this.indexName = indexName;
    }

    /**
     * The name of the index (Datasource JNDI name)
     *
     * @param indexName the String.
     */
    public void setIndexName(String indexName) {
        if ((this.indexName == null)) {
            if ((indexName == null)) {
                return;
            }
            this.indexName = new IndexName();
        }
        this.indexName.setValue(indexName);
    }

    /**
     * The component reference.
     *
     * @return the ComponentRef.
     */
    public ComponentRef getComponentRef() {
        return this.componentRef;
    }

    /**
     * The component reference.
     *
     * @param componentRef the ComponentRef.
     */
    public void setComponentRef(ComponentRef componentRef) {
        this.componentRef = componentRef;
    }

    /**
     * The component reference.
     *
     * @param componentRef the String.
     */
    public void setComponentRef(String componentRef) {
        if ((this.componentRef == null)) {
            if ((componentRef == null)) {
                return;
            }
            this.componentRef = new ComponentRef();
        }
        this.componentRef.setValue(componentRef);
    }

    /**
     * The datatype reference.
     *
     * @return the DatatypeRef.
     */
    public DatatypeRef getDatatypeRef() {
        return this.datatypeRef;
    }

    /**
     * The datatype reference.
     *
     * @param datatypeRef the DatatypeRef.
     */
    public void setDatatypeRef(DatatypeRef datatypeRef) {
        this.datatypeRef = datatypeRef;
    }

    /**
     * The datatype reference.
     *
     * @param datatypeRef the String.
     */
    public void setDatatypeRef(String datatypeRef) {
        if ((this.datatypeRef == null)) {
            if ((datatypeRef == null)) {
                return;
            }
            this.datatypeRef = new DatatypeRef();
        }
        this.datatypeRef.setValue(datatypeRef);
    }

    /**
     * The table reference.
     *
     * @return the TableRef.
     */
    public TableRef getTableRef() {
        return this.tableRef;
    }

    /**
     * The table reference.
     *
     * @param tableRef the TableRef.
     */
    public void setTableRef(TableRef tableRef) {
        this.tableRef = tableRef;
    }

    /**
     * The table reference.
     *
     * @param tableRef the String.
     */
    public void setTableRef(String tableRef) {
        if ((this.tableRef == null)) {
            if ((tableRef == null)) {
                return;
            }
            this.tableRef = new TableRef();
        }
        this.tableRef.setValue(tableRef);
    }

    /**
     * The identifier of the object.
     *
     * @return the Identifier.
     */
    public Identifier getIdentifierRef() {
        return this.identifierRef;
    }

    /**
     * The identifier of the object.
     *
     * @param identifierRef the Identifier.
     */
    public void setIdentifierRef(Identifier identifierRef) {
        this.identifierRef = identifierRef;
    }

    /**
     * The identifier of the object.
     *
     * @param identifierRef the Long.
     */
    public void setIdentifierRef(Long identifierRef) {
        if ((this.identifierRef == null)) {
            if ((identifierRef == null)) {
                return;
            }
            this.identifierRef = new Identifier();
        }
        this.identifierRef.setValue(identifierRef);
    }

    /**
     * The data (as xml) of the fulltext document.
     *
     * @return the FulltextData.
     */
    public FulltextData getFulltextData() {
        return this.fulltextData;
    }

    /**
     * The data (as xml) of the fulltext document.
     *
     * @param fulltextData the FulltextData.
     */
    public void setFulltextData(FulltextData fulltextData) {
        this.fulltextData = fulltextData;
    }

    /**
     * The data (as xml) of the fulltext document.
     *
     * @param fulltextData the String.
     */
    public void setFulltextData(String fulltextData) {
        if ((this.fulltextData == null)) {
            if ((fulltextData == null)) {
                return;
            }
            this.fulltextData = new FulltextData();
        }
        this.fulltextData.setValue(fulltextData);
    }

    /**
     * Optional latitude geo information for the indexing.
     *
     * @return the Latitude.
     */
    public Latitude getLatitude() {
        return this.latitude;
    }

    /**
     * Optional latitude geo information for the indexing.
     *
     * @param latitude the Latitude.
     */
    public void setLatitude(Latitude latitude) {
        this.latitude = latitude;
    }

    /**
     * Optional latitude geo information for the indexing.
     *
     * @param latitude the Double.
     */
    public void setLatitude(Double latitude) {
        if ((this.latitude == null)) {
            if ((latitude == null)) {
                return;
            }
            this.latitude = new Latitude();
        }
        this.latitude.setValue(latitude);
    }

    /**
     * Optional longitude geo information for the indexing.
     *
     * @return the Longitude.
     */
    public Longitude getLongitude() {
        return this.longitude;
    }

    /**
     * Optional longitude geo information for the indexing.
     *
     * @param longitude the Longitude.
     */
    public void setLongitude(Longitude longitude) {
        this.longitude = longitude;
    }

    /**
     * Optional longitude geo information for the indexing.
     *
     * @param longitude the Double.
     */
    public void setLongitude(Double longitude) {
        if ((this.longitude == null)) {
            if ((longitude == null)) {
                return;
            }
            this.longitude = new Longitude();
        }
        this.longitude.setValue(longitude);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(IndexQueue.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(IndexQueue.class).getAllProperties();
    }
}
