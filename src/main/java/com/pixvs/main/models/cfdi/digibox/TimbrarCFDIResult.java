package com.pixvs.main.models.cfdi.digibox;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="UUID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="FechaTimbrado" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="selloCFD" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="noCertificadoSAT" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="selloSAT" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "TimbrarCFDIResult", namespace = "http://www.sat.gob.mx/TimbreFiscalDigital")
public class TimbrarCFDIResult {

    @XmlAttribute
    protected String version;
    @XmlAttribute(name = "UUID")
    protected String uuid;
    @XmlAttribute(name = "FechaTimbrado", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaTimbrado;
    @XmlAttribute
    protected String selloCFD;
    @XmlAttribute
    protected String noCertificadoSAT;
    @XmlAttribute
    protected String selloSAT;

    /**
     * Gets the value of the version property.
     *
     * @return possible object is {@link String }
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value allowed object is {@link String }
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the uuid property.
     *
     * @return possible object is {@link String }
     */
    public String getUUID() {
        return uuid;
    }

    /**
     * Sets the value of the uuid property.
     *
     * @param value allowed object is {@link String }
     */
    public void setUUID(String value) {
        this.uuid = value;
    }

    /**
     * Gets the value of the fechaTimbrado property.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getFechaTimbrado() {
        return fechaTimbrado;
    }

    /**
     * Sets the value of the fechaTimbrado property.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     */
    public void setFechaTimbrado(XMLGregorianCalendar value) {
        this.fechaTimbrado = value;
    }

    /**
     * Gets the value of the selloCFD property.
     *
     * @return possible object is {@link String }
     */
    public String getSelloCFD() {
        return selloCFD;
    }

    /**
     * Sets the value of the selloCFD property.
     *
     * @param value allowed object is {@link String }
     */
    public void setSelloCFD(String value) {
        this.selloCFD = value;
    }

    /**
     * Gets the value of the noCertificadoSAT property.
     *
     * @return possible object is {@link String }
     */
    public String getNoCertificadoSAT() {
        return noCertificadoSAT;
    }

    /**
     * Sets the value of the noCertificadoSAT property.
     *
     * @param value allowed object is {@link String }
     */
    public void setNoCertificadoSAT(String value) {
        this.noCertificadoSAT = value;
    }

    /**
     * Gets the value of the selloSAT property.
     *
     * @return possible object is {@link String }
     */
    public String getSelloSAT() {
        return selloSAT;
    }

    /**
     * Sets the value of the selloSAT property.
     *
     * @param value allowed object is {@link String }
     */
    public void setSelloSAT(String value) {
        this.selloSAT = value;
    }
}