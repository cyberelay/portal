//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1.2-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.02.26 at 02:05:07 PM CST 
//


package org.cyberelay.portlet.descriptor.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * 
 * 			Caching scope, allowed values are "private" indicating that the content should not be shared
 * 			across users and "public" indicating that the content may be shared across users.
 * 			The default value if not present is "private".
 * 			Used in: portlet
 * 			
 * 
 * <p>Java class for cache-scopeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cache-scopeType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd>string">
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cache-scopeType", propOrder = {
    "value"
})
public class CacheScopeType {

    @XmlValue
    protected String value;

    /**
     * 
     * 			This is a special string datatype that is defined by JavaEE 
     * 			as a base type for defining collapsed strings. When 
     * 			schemas require trailing/leading space elimination as 
     * 			well as collapsing the existing whitespace, this base 
     * 			type may be used.
     * 			
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * 
     * 			This is a special string datatype that is defined by JavaEE 
     * 			as a base type for defining collapsed strings. When 
     * 			schemas require trailing/leading space elimination as 
     * 			well as collapsing the existing whitespace, this base 
     * 			type may be used.
     * 			
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

}
