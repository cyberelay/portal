//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1.2-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.02.26 at 02:05:07 PM CST 
//


package org.cyberelay.portlet.descriptor.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 * 			The security-role-ref element contains the declaration of a 
 * 			security role reference in the code of the web application. The 
 * 			declaration consists of an optional description, the security 
 * 			role name used in the code, and an optional link to a security 
 * 			role. If the security role is not specified, the Deployer must 
 * 			choose an appropriate security role.
 * 			The value of the role name element must be the String used 
 * 			as the parameter to the 
 * 			EJBContext.isCallerInRole(String roleName) method
 * 			or the HttpServletRequest.isUserInRole(String role) method.
 * 			Used in: portlet
 * 			
 * 
 * <p>Java class for security-role-refType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="security-role-refType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd}descriptionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="role-name" type="{http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd}role-nameType"/>
 *         &lt;element name="role-link" type="{http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd}role-linkType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "security-role-refType", propOrder = {
    "description",
    "roleName",
    "roleLink"
})
public class SecurityRoleRefType {

    protected List<DescriptionType> description;
    @XmlElement(name = "role-name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String roleName;
    @XmlElement(name = "role-link")
    protected RoleLinkType roleLink;
    @XmlAttribute
    protected String id;

    /**
     * Gets the value of the description property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the description property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DescriptionType }
     * 
     * 
     */
    public List<DescriptionType> getDescription() {
        if (description == null) {
            description = new ArrayList<DescriptionType>();
        }
        return this.description;
    }

    /**
     * Gets the value of the roleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Sets the value of the roleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoleName(String value) {
        this.roleName = value;
    }

    /**
     * Gets the value of the roleLink property.
     * 
     * @return
     *     possible object is
     *     {@link RoleLinkType }
     *     
     */
    public RoleLinkType getRoleLink() {
        return roleLink;
    }

    /**
     * Sets the value of the roleLink property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleLinkType }
     *     
     */
    public void setRoleLink(RoleLinkType value) {
        this.roleLink = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
