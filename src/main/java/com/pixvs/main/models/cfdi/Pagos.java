
package com.pixvs.main.models.cfdi;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para anonymous complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Totales">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="TotalRetencionesIVA" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
 *                 &lt;attribute name="TotalRetencionesISR" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
 *                 &lt;attribute name="TotalRetencionesIEPS" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
 *                 &lt;attribute name="TotalTrasladosBaseIVA16" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
 *                 &lt;attribute name="TotalTrasladosImpuestoIVA16" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
 *                 &lt;attribute name="TotalTrasladosBaseIVA8" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
 *                 &lt;attribute name="TotalTrasladosImpuestoIVA8" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
 *                 &lt;attribute name="TotalTrasladosBaseIVA0" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
 *                 &lt;attribute name="TotalTrasladosImpuestoIVA0" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
 *                 &lt;attribute name="TotalTrasladosBaseIVAExento" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
 *                 &lt;attribute name="MontoTotalPagos" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Pago" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DoctoRelacionado" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ImpuestosDR" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="RetencionesDR" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="RetencionDR" maxOccurs="unbounded">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;attribute name="BaseDR" use="required">
 *                                                           &lt;simpleType>
 *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                               &lt;minInclusive value="0.000001"/>
 *                                                               &lt;fractionDigits value="6"/>
 *                                                               &lt;whiteSpace value="collapse"/>
 *                                                             &lt;/restriction>
 *                                                           &lt;/simpleType>
 *                                                         &lt;/attribute>
 *                                                         &lt;attribute name="ImpuestoDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
 *                                                         &lt;attribute name="TipoFactorDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_TipoFactor" />
 *                                                         &lt;attribute name="TasaOCuotaDR" use="required">
 *                                                           &lt;simpleType>
 *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                               &lt;minInclusive value="0.000000"/>
 *                                                               &lt;fractionDigits value="6"/>
 *                                                               &lt;whiteSpace value="collapse"/>
 *                                                             &lt;/restriction>
 *                                                           &lt;/simpleType>
 *                                                         &lt;/attribute>
 *                                                         &lt;attribute name="ImporteDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="TrasladosDR" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="TrasladoDR" maxOccurs="unbounded">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;attribute name="BaseDR" use="required">
 *                                                           &lt;simpleType>
 *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                               &lt;minInclusive value="0.000001"/>
 *                                                               &lt;fractionDigits value="6"/>
 *                                                               &lt;whiteSpace value="collapse"/>
 *                                                             &lt;/restriction>
 *                                                           &lt;/simpleType>
 *                                                         &lt;/attribute>
 *                                                         &lt;attribute name="ImpuestoDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
 *                                                         &lt;attribute name="TipoFactorDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_TipoFactor" />
 *                                                         &lt;attribute name="TasaOCuotaDR">
 *                                                           &lt;simpleType>
 *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                               &lt;minInclusive value="0.000000"/>
 *                                                               &lt;fractionDigits value="6"/>
 *                                                               &lt;whiteSpace value="collapse"/>
 *                                                             &lt;/restriction>
 *                                                           &lt;/simpleType>
 *                                                         &lt;/attribute>
 *                                                         &lt;attribute name="ImporteDR" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="IdDocumento" use="required">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;minLength value="16"/>
 *                                 &lt;maxLength value="36"/>
 *                                 &lt;whiteSpace value="collapse"/>
 *                                 &lt;pattern value="([a-f0-9A-F]{8}-[a-f0-9A-F]{4}-[a-f0-9A-F]{4}-[a-f0-9A-F]{4}-[a-f0-9A-F]{12})|([0-9]{3}-[0-9]{2}-[0-9]{9})"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                           &lt;attribute name="Serie">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;minLength value="1"/>
 *                                 &lt;maxLength value="25"/>
 *                                 &lt;whiteSpace value="collapse"/>
 *                                 &lt;pattern value="[^|]{1,25}"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                           &lt;attribute name="Folio">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;minLength value="1"/>
 *                                 &lt;maxLength value="40"/>
 *                                 &lt;whiteSpace value="collapse"/>
 *                                 &lt;pattern value="[^|]{1,40}"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                           &lt;attribute name="MonedaDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Moneda" />
 *                           &lt;attribute name="EquivalenciaDR">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                 &lt;fractionDigits value="6"/>
 *                                 &lt;minInclusive value="0.000001"/>
 *                                 &lt;whiteSpace value="collapse"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                           &lt;attribute name="NumParcialidad" use="required">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                 &lt;whiteSpace value="collapse"/>
 *                                 &lt;pattern value="[1-9][0-9]{0,2}"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                           &lt;attribute name="ImpSaldoAnt" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
 *                           &lt;attribute name="ImpPagado" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
 *                           &lt;attribute name="ImpSaldoInsoluto" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
 *                           &lt;attribute name="ObjetoImpDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_ObjetoImp" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="ImpuestosP" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="RetencionesP" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="RetencionP" maxOccurs="unbounded">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;attribute name="ImpuestoP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
 *                                               &lt;attribute name="ImporteP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="TrasladosP" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="TrasladoP" maxOccurs="unbounded">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;attribute name="BaseP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
 *                                               &lt;attribute name="ImpuestoP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
 *                                               &lt;attribute name="TipoFactorP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_TipoFactor" />
 *                                               &lt;attribute name="TasaOCuotaP">
 *                                                 &lt;simpleType>
 *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                     &lt;fractionDigits value="6"/>
 *                                                     &lt;minInclusive value="0.000000"/>
 *                                                     &lt;whiteSpace value="collapse"/>
 *                                                   &lt;/restriction>
 *                                                 &lt;/simpleType>
 *                                               &lt;/attribute>
 *                                               &lt;attribute name="ImporteP" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="FechaPago" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_FechaH" />
 *                 &lt;attribute name="FormaDePagoP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_FormaPago" />
 *                 &lt;attribute name="MonedaP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Moneda" />
 *                 &lt;attribute name="TipoCambioP">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                       &lt;fractionDigits value="6"/>
 *                       &lt;minInclusive value="0.000001"/>
 *                       &lt;whiteSpace value="collapse"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="Monto" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
 *                 &lt;attribute name="NumOperacion">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;minLength value="1"/>
 *                       &lt;maxLength value="100"/>
 *                       &lt;pattern value="[^|]{1,100}"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="RfcEmisorCtaOrd">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;minLength value="12"/>
 *                       &lt;maxLength value="13"/>
 *                       &lt;whiteSpace value="collapse"/>
 *                       &lt;pattern value="XEXX010101000|[A-Z&amp;�]{3}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[A-Z0-9]{2}[0-9A]"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="NomBancoOrdExt">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;minLength value="1"/>
 *                       &lt;maxLength value="300"/>
 *                       &lt;whiteSpace value="collapse"/>
 *                       &lt;pattern value="[^|]{1,300}"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="CtaOrdenante">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;minLength value="10"/>
 *                       &lt;maxLength value="50"/>
 *                       &lt;whiteSpace value="collapse"/>
 *                       &lt;pattern value="[A-Z0-9_]{10,50}"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="RfcEmisorCtaBen" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_RFC_PM" />
 *                 &lt;attribute name="CtaBeneficiario">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;minLength value="10"/>
 *                       &lt;maxLength value="50"/>
 *                       &lt;whiteSpace value="collapse"/>
 *                       &lt;pattern value="[A-Z0-9_]{10,50}"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="TipoCadPago" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos/Pagos}c_TipoCadenaPago" />
 *                 &lt;attribute name="CertPago">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}base64Binary">
 *                       &lt;whiteSpace value="collapse"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="CadPago">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;minLength value="1"/>
 *                       &lt;maxLength value="8192"/>
 *                       &lt;whiteSpace value="collapse"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="SelloPago">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}base64Binary">
 *                       &lt;whiteSpace value="collapse"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="Version" use="required" fixed="2.0">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;whiteSpace value="collapse"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "totales",
    "pago"
})
@XmlRootElement(name = "Pagos", namespace = "http://www.sat.gob.mx/Pagos20")
public class Pagos {

    @XmlElement(name = "Totales", namespace = "http://www.sat.gob.mx/Pagos20", required = true)
    protected Pagos.Totales totales;
    @XmlElement(name = "Pago", namespace = "http://www.sat.gob.mx/Pagos20", required = true)
    protected List<Pagos.Pago> pago;
    @XmlAttribute(name = "Version", required = true)
    protected String version;

    /**
     * Obtiene el valor de la propiedad totales.
     *
     * @return
     *     possible object is
     *     {@link Pagos.Totales }
     *
     */
    public Pagos.Totales getTotales() {
        return totales;
    }

    /**
     * Define el valor de la propiedad totales.
     *
     * @param value
     *     allowed object is
     *     {@link Pagos.Totales }
     *
     */
    public void setTotales(Pagos.Totales value) {
        this.totales = value;
    }

    /**
     * Gets the value of the pago property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pago property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPago().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Pagos.Pago }
     *
     *
     */
    public List<Pagos.Pago> getPago() {
        if (pago == null) {
            pago = new ArrayList<Pagos.Pago>();
        }
        return this.pago;
    }

    /**
     * Obtiene el valor de la propiedad version.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getVersion() {
        if (version == null) {
            return "2.0";
        } else {
            return version;
        }
    }

    /**
     * Define el valor de la propiedad version.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setVersion(String value) {
        this.version = value;
    }


    /**
     * <p>Clase Java para anonymous complex type.
     *
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="DoctoRelacionado" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ImpuestosDR" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="RetencionesDR" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="RetencionDR" maxOccurs="unbounded">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;attribute name="BaseDR" use="required">
     *                                                 &lt;simpleType>
     *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                     &lt;minInclusive value="0.000001"/>
     *                                                     &lt;fractionDigits value="6"/>
     *                                                     &lt;whiteSpace value="collapse"/>
     *                                                   &lt;/restriction>
     *                                                 &lt;/simpleType>
     *                                               &lt;/attribute>
     *                                               &lt;attribute name="ImpuestoDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
     *                                               &lt;attribute name="TipoFactorDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_TipoFactor" />
     *                                               &lt;attribute name="TasaOCuotaDR" use="required">
     *                                                 &lt;simpleType>
     *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                     &lt;minInclusive value="0.000000"/>
     *                                                     &lt;fractionDigits value="6"/>
     *                                                     &lt;whiteSpace value="collapse"/>
     *                                                   &lt;/restriction>
     *                                                 &lt;/simpleType>
     *                                               &lt;/attribute>
     *                                               &lt;attribute name="ImporteDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="TrasladosDR" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="TrasladoDR" maxOccurs="unbounded">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;attribute name="BaseDR" use="required">
     *                                                 &lt;simpleType>
     *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                     &lt;minInclusive value="0.000001"/>
     *                                                     &lt;fractionDigits value="6"/>
     *                                                     &lt;whiteSpace value="collapse"/>
     *                                                   &lt;/restriction>
     *                                                 &lt;/simpleType>
     *                                               &lt;/attribute>
     *                                               &lt;attribute name="ImpuestoDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
     *                                               &lt;attribute name="TipoFactorDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_TipoFactor" />
     *                                               &lt;attribute name="TasaOCuotaDR">
     *                                                 &lt;simpleType>
     *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                     &lt;minInclusive value="0.000000"/>
     *                                                     &lt;fractionDigits value="6"/>
     *                                                     &lt;whiteSpace value="collapse"/>
     *                                                   &lt;/restriction>
     *                                                 &lt;/simpleType>
     *                                               &lt;/attribute>
     *                                               &lt;attribute name="ImporteDR" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="IdDocumento" use="required">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;minLength value="16"/>
     *                       &lt;maxLength value="36"/>
     *                       &lt;whiteSpace value="collapse"/>
     *                       &lt;pattern value="([a-f0-9A-F]{8}-[a-f0-9A-F]{4}-[a-f0-9A-F]{4}-[a-f0-9A-F]{4}-[a-f0-9A-F]{12})|([0-9]{3}-[0-9]{2}-[0-9]{9})"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *                 &lt;attribute name="Serie">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;minLength value="1"/>
     *                       &lt;maxLength value="25"/>
     *                       &lt;whiteSpace value="collapse"/>
     *                       &lt;pattern value="[^|]{1,25}"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *                 &lt;attribute name="Folio">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;minLength value="1"/>
     *                       &lt;maxLength value="40"/>
     *                       &lt;whiteSpace value="collapse"/>
     *                       &lt;pattern value="[^|]{1,40}"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *                 &lt;attribute name="MonedaDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Moneda" />
     *                 &lt;attribute name="EquivalenciaDR">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                       &lt;fractionDigits value="6"/>
     *                       &lt;minInclusive value="0.000001"/>
     *                       &lt;whiteSpace value="collapse"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *                 &lt;attribute name="NumParcialidad" use="required">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                       &lt;whiteSpace value="collapse"/>
     *                       &lt;pattern value="[1-9][0-9]{0,2}"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *                 &lt;attribute name="ImpSaldoAnt" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
     *                 &lt;attribute name="ImpPagado" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
     *                 &lt;attribute name="ImpSaldoInsoluto" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
     *                 &lt;attribute name="ObjetoImpDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_ObjetoImp" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="ImpuestosP" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="RetencionesP" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="RetencionP" maxOccurs="unbounded">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;attribute name="ImpuestoP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
     *                                     &lt;attribute name="ImporteP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="TrasladosP" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="TrasladoP" maxOccurs="unbounded">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;attribute name="BaseP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
     *                                     &lt;attribute name="ImpuestoP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
     *                                     &lt;attribute name="TipoFactorP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_TipoFactor" />
     *                                     &lt;attribute name="TasaOCuotaP">
     *                                       &lt;simpleType>
     *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                           &lt;fractionDigits value="6"/>
     *                                           &lt;minInclusive value="0.000000"/>
     *                                           &lt;whiteSpace value="collapse"/>
     *                                         &lt;/restriction>
     *                                       &lt;/simpleType>
     *                                     &lt;/attribute>
     *                                     &lt;attribute name="ImporteP" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="FechaPago" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_FechaH" />
     *       &lt;attribute name="FormaDePagoP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_FormaPago" />
     *       &lt;attribute name="MonedaP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Moneda" />
     *       &lt;attribute name="TipoCambioP">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *             &lt;fractionDigits value="6"/>
     *             &lt;minInclusive value="0.000001"/>
     *             &lt;whiteSpace value="collapse"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="Monto" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
     *       &lt;attribute name="NumOperacion">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;minLength value="1"/>
     *             &lt;maxLength value="100"/>
     *             &lt;pattern value="[^|]{1,100}"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="RfcEmisorCtaOrd">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;minLength value="12"/>
     *             &lt;maxLength value="13"/>
     *             &lt;whiteSpace value="collapse"/>
     *             &lt;pattern value="XEXX010101000|[A-Z&amp;�]{3}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[A-Z0-9]{2}[0-9A]"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="NomBancoOrdExt">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;minLength value="1"/>
     *             &lt;maxLength value="300"/>
     *             &lt;whiteSpace value="collapse"/>
     *             &lt;pattern value="[^|]{1,300}"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="CtaOrdenante">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;minLength value="10"/>
     *             &lt;maxLength value="50"/>
     *             &lt;whiteSpace value="collapse"/>
     *             &lt;pattern value="[A-Z0-9_]{10,50}"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="RfcEmisorCtaBen" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_RFC_PM" />
     *       &lt;attribute name="CtaBeneficiario">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;minLength value="10"/>
     *             &lt;maxLength value="50"/>
     *             &lt;whiteSpace value="collapse"/>
     *             &lt;pattern value="[A-Z0-9_]{10,50}"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="TipoCadPago" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos/Pagos}c_TipoCadenaPago" />
     *       &lt;attribute name="CertPago">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}base64Binary">
     *             &lt;whiteSpace value="collapse"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="CadPago">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;minLength value="1"/>
     *             &lt;maxLength value="8192"/>
     *             &lt;whiteSpace value="collapse"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="SelloPago">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}base64Binary">
     *             &lt;whiteSpace value="collapse"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "doctoRelacionado",
        "impuestosP"
    })
    public static class Pago {

        @XmlElement(name = "DoctoRelacionado", namespace = "http://www.sat.gob.mx/Pagos20", required = true)
        protected List<Pagos.Pago.DoctoRelacionado> doctoRelacionado;
        @XmlElement(name = "ImpuestosP", namespace = "http://www.sat.gob.mx/Pagos20")
        protected Pagos.Pago.ImpuestosP impuestosP;
        @XmlAttribute(name = "FechaPago", required = true)
        protected XMLGregorianCalendar fechaPago;
        @XmlAttribute(name = "FormaDePagoP", required = true)
        protected String formaDePagoP;
        @XmlAttribute(name = "MonedaP", required = true)
        protected CMoneda monedaP;
        @XmlAttribute(name = "TipoCambioP")
        protected BigDecimal tipoCambioP;
        @XmlAttribute(name = "Monto", required = true)
        protected BigDecimal monto;
        @XmlAttribute(name = "NumOperacion")
        protected String numOperacion;
        @XmlAttribute(name = "RfcEmisorCtaOrd")
        protected String rfcEmisorCtaOrd;
        @XmlAttribute(name = "NomBancoOrdExt")
        protected String nomBancoOrdExt;
        @XmlAttribute(name = "CtaOrdenante")
        protected String ctaOrdenante;
        @XmlAttribute(name = "RfcEmisorCtaBen")
        protected String rfcEmisorCtaBen;
        @XmlAttribute(name = "CtaBeneficiario")
        protected String ctaBeneficiario;
        @XmlAttribute(name = "TipoCadPago")
        protected String tipoCadPago;
        @XmlAttribute(name = "CertPago")
        protected byte[] certPago;
        @XmlAttribute(name = "CadPago")
        protected String cadPago;
        @XmlAttribute(name = "SelloPago")
        protected byte[] selloPago;

        /**
         * Gets the value of the doctoRelacionado property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the doctoRelacionado property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDoctoRelacionado().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Pagos.Pago.DoctoRelacionado }
         *
         *
         */
        public List<Pagos.Pago.DoctoRelacionado> getDoctoRelacionado() {
            if (doctoRelacionado == null) {
                doctoRelacionado = new ArrayList<Pagos.Pago.DoctoRelacionado>();
            }
            return this.doctoRelacionado;
        }

        /**
         * Obtiene el valor de la propiedad impuestosP.
         *
         * @return
         *     possible object is
         *     {@link Pagos.Pago.ImpuestosP }
         *
         */
        public Pagos.Pago.ImpuestosP getImpuestosP() {
            return impuestosP;
        }

        /**
         * Define el valor de la propiedad impuestosP.
         *
         * @param value
         *     allowed object is
         *     {@link Pagos.Pago.ImpuestosP }
         *
         */
        public void setImpuestosP(Pagos.Pago.ImpuestosP value) {
            this.impuestosP = value;
        }

        /**
         * Obtiene el valor de la propiedad fechaPago.
         *
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *
         */
        public XMLGregorianCalendar getFechaPago() {
            return fechaPago;
        }

        /**
         * Define el valor de la propiedad fechaPago.
         *
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *
         */
        public void setFechaPago(XMLGregorianCalendar value) {
            this.fechaPago = value;
        }

        /**
         * Obtiene el valor de la propiedad formaDePagoP.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getFormaDePagoP() {
            return formaDePagoP;
        }

        /**
         * Define el valor de la propiedad formaDePagoP.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setFormaDePagoP(String value) {
            this.formaDePagoP = value;
        }

        /**
         * Obtiene el valor de la propiedad monedaP.
         *
         * @return
         *     possible object is
         *     {@link CMoneda }
         *
         */
        public CMoneda getMonedaP() {
            return monedaP;
        }

        /**
         * Define el valor de la propiedad monedaP.
         *
         * @param value
         *     allowed object is
         *     {@link CMoneda }
         *
         */
        public void setMonedaP(CMoneda value) {
            this.monedaP = value;
        }

        /**
         * Obtiene el valor de la propiedad tipoCambioP.
         *
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *
         */
        public BigDecimal getTipoCambioP() {
            return tipoCambioP;
        }

        /**
         * Define el valor de la propiedad tipoCambioP.
         *
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *
         */
        public void setTipoCambioP(BigDecimal value) {
            this.tipoCambioP = value;
        }

        /**
         * Obtiene el valor de la propiedad monto.
         *
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *
         */
        public BigDecimal getMonto() {
            return monto;
        }

        /**
         * Define el valor de la propiedad monto.
         *
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *
         */
        public void setMonto(BigDecimal value) {
            this.monto = value;
        }

        /**
         * Obtiene el valor de la propiedad numOperacion.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getNumOperacion() {
            return numOperacion;
        }

        /**
         * Define el valor de la propiedad numOperacion.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setNumOperacion(String value) {
            this.numOperacion = value;
        }

        /**
         * Obtiene el valor de la propiedad rfcEmisorCtaOrd.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getRfcEmisorCtaOrd() {
            return rfcEmisorCtaOrd;
        }

        /**
         * Define el valor de la propiedad rfcEmisorCtaOrd.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setRfcEmisorCtaOrd(String value) {
            this.rfcEmisorCtaOrd = value;
        }

        /**
         * Obtiene el valor de la propiedad nomBancoOrdExt.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getNomBancoOrdExt() {
            return nomBancoOrdExt;
        }

        /**
         * Define el valor de la propiedad nomBancoOrdExt.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setNomBancoOrdExt(String value) {
            this.nomBancoOrdExt = value;
        }

        /**
         * Obtiene el valor de la propiedad ctaOrdenante.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getCtaOrdenante() {
            return ctaOrdenante;
        }

        /**
         * Define el valor de la propiedad ctaOrdenante.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setCtaOrdenante(String value) {
            this.ctaOrdenante = value;
        }

        /**
         * Obtiene el valor de la propiedad rfcEmisorCtaBen.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getRfcEmisorCtaBen() {
            return rfcEmisorCtaBen;
        }

        /**
         * Define el valor de la propiedad rfcEmisorCtaBen.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setRfcEmisorCtaBen(String value) {
            this.rfcEmisorCtaBen = value;
        }

        /**
         * Obtiene el valor de la propiedad ctaBeneficiario.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getCtaBeneficiario() {
            return ctaBeneficiario;
        }

        /**
         * Define el valor de la propiedad ctaBeneficiario.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setCtaBeneficiario(String value) {
            this.ctaBeneficiario = value;
        }

        /**
         * Obtiene el valor de la propiedad tipoCadPago.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getTipoCadPago() {
            return tipoCadPago;
        }

        /**
         * Define el valor de la propiedad tipoCadPago.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setTipoCadPago(String value) {
            this.tipoCadPago = value;
        }

        /**
         * Obtiene el valor de la propiedad certPago.
         *
         * @return
         *     possible object is
         *     byte[]
         */
        public byte[] getCertPago() {
            return certPago;
        }

        /**
         * Define el valor de la propiedad certPago.
         *
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setCertPago(byte[] value) {
            this.certPago = value;
        }

        /**
         * Obtiene el valor de la propiedad cadPago.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getCadPago() {
            return cadPago;
        }

        /**
         * Define el valor de la propiedad cadPago.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setCadPago(String value) {
            this.cadPago = value;
        }

        /**
         * Obtiene el valor de la propiedad selloPago.
         *
         * @return
         *     possible object is
         *     byte[]
         */
        public byte[] getSelloPago() {
            return selloPago;
        }

        /**
         * Define el valor de la propiedad selloPago.
         *
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setSelloPago(byte[] value) {
            this.selloPago = value;
        }


        /**
         * <p>Clase Java para anonymous complex type.
         *
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="ImpuestosDR" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="RetencionesDR" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="RetencionDR" maxOccurs="unbounded">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;attribute name="BaseDR" use="required">
         *                                       &lt;simpleType>
         *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                           &lt;minInclusive value="0.000001"/>
         *                                           &lt;fractionDigits value="6"/>
         *                                           &lt;whiteSpace value="collapse"/>
         *                                         &lt;/restriction>
         *                                       &lt;/simpleType>
         *                                     &lt;/attribute>
         *                                     &lt;attribute name="ImpuestoDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
         *                                     &lt;attribute name="TipoFactorDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_TipoFactor" />
         *                                     &lt;attribute name="TasaOCuotaDR" use="required">
         *                                       &lt;simpleType>
         *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                           &lt;minInclusive value="0.000000"/>
         *                                           &lt;fractionDigits value="6"/>
         *                                           &lt;whiteSpace value="collapse"/>
         *                                         &lt;/restriction>
         *                                       &lt;/simpleType>
         *                                     &lt;/attribute>
         *                                     &lt;attribute name="ImporteDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="TrasladosDR" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="TrasladoDR" maxOccurs="unbounded">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;attribute name="BaseDR" use="required">
         *                                       &lt;simpleType>
         *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                           &lt;minInclusive value="0.000001"/>
         *                                           &lt;fractionDigits value="6"/>
         *                                           &lt;whiteSpace value="collapse"/>
         *                                         &lt;/restriction>
         *                                       &lt;/simpleType>
         *                                     &lt;/attribute>
         *                                     &lt;attribute name="ImpuestoDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
         *                                     &lt;attribute name="TipoFactorDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_TipoFactor" />
         *                                     &lt;attribute name="TasaOCuotaDR">
         *                                       &lt;simpleType>
         *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                           &lt;minInclusive value="0.000000"/>
         *                                           &lt;fractionDigits value="6"/>
         *                                           &lt;whiteSpace value="collapse"/>
         *                                         &lt;/restriction>
         *                                       &lt;/simpleType>
         *                                     &lt;/attribute>
         *                                     &lt;attribute name="ImporteDR" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="IdDocumento" use="required">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;minLength value="16"/>
         *             &lt;maxLength value="36"/>
         *             &lt;whiteSpace value="collapse"/>
         *             &lt;pattern value="([a-f0-9A-F]{8}-[a-f0-9A-F]{4}-[a-f0-9A-F]{4}-[a-f0-9A-F]{4}-[a-f0-9A-F]{12})|([0-9]{3}-[0-9]{2}-[0-9]{9})"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="Serie">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;minLength value="1"/>
         *             &lt;maxLength value="25"/>
         *             &lt;whiteSpace value="collapse"/>
         *             &lt;pattern value="[^|]{1,25}"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="Folio">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;minLength value="1"/>
         *             &lt;maxLength value="40"/>
         *             &lt;whiteSpace value="collapse"/>
         *             &lt;pattern value="[^|]{1,40}"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="MonedaDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Moneda" />
         *       &lt;attribute name="EquivalenciaDR">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *             &lt;fractionDigits value="6"/>
         *             &lt;minInclusive value="0.000001"/>
         *             &lt;whiteSpace value="collapse"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="NumParcialidad" use="required">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *             &lt;whiteSpace value="collapse"/>
         *             &lt;pattern value="[1-9][0-9]{0,2}"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="ImpSaldoAnt" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
         *       &lt;attribute name="ImpPagado" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
         *       &lt;attribute name="ImpSaldoInsoluto" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
         *       &lt;attribute name="ObjetoImpDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_ObjetoImp" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "impuestosDR"
        })
        public static class DoctoRelacionado {

            @XmlElement(name = "ImpuestosDR", namespace = "http://www.sat.gob.mx/Pagos20")
            protected Pagos.Pago.DoctoRelacionado.ImpuestosDR impuestosDR;
            @XmlAttribute(name = "IdDocumento", required = true)
            protected String idDocumento;
            @XmlAttribute(name = "Serie")
            protected String serie;
            @XmlAttribute(name = "Folio")
            protected String folio;
            @XmlAttribute(name = "MonedaDR", required = true)
            protected CMoneda monedaDR;
            @XmlAttribute(name = "EquivalenciaDR")
            protected BigDecimal equivalenciaDR;
            @XmlAttribute(name = "NumParcialidad", required = true)
            protected BigInteger numParcialidad;
            @XmlAttribute(name = "ImpSaldoAnt", required = true)
            protected BigDecimal impSaldoAnt;
            @XmlAttribute(name = "ImpPagado", required = true)
            protected BigDecimal impPagado;
            @XmlAttribute(name = "ImpSaldoInsoluto", required = true)
            protected BigDecimal impSaldoInsoluto;
            @XmlAttribute(name = "ObjetoImpDR", required = true)
            protected String objetoImpDR;
            @XmlAttribute(name = "MetodoDePagoDR", required = true)
            protected CMetodoPago metodoDePagoDR;

            /**
             * Obtiene el valor de la propiedad impuestosDR.
             *
             * @return
             *     possible object is
             *     {@link Pagos.Pago.DoctoRelacionado.ImpuestosDR }
             *
             */
            public Pagos.Pago.DoctoRelacionado.ImpuestosDR getImpuestosDR() {
                return impuestosDR;
            }

            /**
             * Define el valor de la propiedad impuestosDR.
             *
             * @param value
             *     allowed object is
             *     {@link Pagos.Pago.DoctoRelacionado.ImpuestosDR }
             *
             */
            public void setImpuestosDR(Pagos.Pago.DoctoRelacionado.ImpuestosDR value) {
                this.impuestosDR = value;
            }

            /**
             * Obtiene el valor de la propiedad idDocumento.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getIdDocumento() {
                return idDocumento;
            }

            /**
             * Define el valor de la propiedad idDocumento.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setIdDocumento(String value) {
                this.idDocumento = value;
            }

            /**
             * Obtiene el valor de la propiedad serie.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getSerie() {
                return serie;
            }

            /**
             * Define el valor de la propiedad serie.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setSerie(String value) {
                this.serie = value;
            }

            /**
             * Obtiene el valor de la propiedad folio.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getFolio() {
                return folio;
            }

            /**
             * Define el valor de la propiedad folio.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setFolio(String value) {
                this.folio = value;
            }

            /**
             * Obtiene el valor de la propiedad monedaDR.
             *
             * @return
             *     possible object is
             *     {@link CMoneda }
             *
             */
            public CMoneda getMonedaDR() {
                return monedaDR;
            }

            /**
             * Define el valor de la propiedad monedaDR.
             *
             * @param value
             *     allowed object is
             *     {@link CMoneda }
             *
             */
            public void setMonedaDR(CMoneda value) {
                this.monedaDR = value;
            }

            /**
             * Obtiene el valor de la propiedad equivalenciaDR.
             *
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *
             */
            public BigDecimal getEquivalenciaDR() {
                return equivalenciaDR;
            }

            /**
             * Define el valor de la propiedad equivalenciaDR.
             *
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *
             */
            public void setEquivalenciaDR(BigDecimal value) {
                this.equivalenciaDR = value;
            }

            /**
             * Obtiene el valor de la propiedad numParcialidad.
             *
             * @return
             *     possible object is
             *     {@link BigInteger }
             *
             */
            public BigInteger getNumParcialidad() {
                return numParcialidad;
            }

            /**
             * Define el valor de la propiedad numParcialidad.
             *
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *
             */
            public void setNumParcialidad(BigInteger value) {
                this.numParcialidad = value;
            }

            /**
             * Obtiene el valor de la propiedad impSaldoAnt.
             *
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *
             */
            public BigDecimal getImpSaldoAnt() {
                return impSaldoAnt;
            }

            /**
             * Define el valor de la propiedad impSaldoAnt.
             *
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *
             */
            public void setImpSaldoAnt(BigDecimal value) {
                this.impSaldoAnt = value;
            }

            /**
             * Obtiene el valor de la propiedad impPagado.
             *
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *
             */
            public BigDecimal getImpPagado() {
                return impPagado;
            }

            /**
             * Define el valor de la propiedad impPagado.
             *
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *
             */
            public void setImpPagado(BigDecimal value) {
                this.impPagado = value;
            }

            /**
             * Obtiene el valor de la propiedad impSaldoInsoluto.
             *
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *
             */
            public BigDecimal getImpSaldoInsoluto() {
                return impSaldoInsoluto;
            }

            /**
             * Define el valor de la propiedad impSaldoInsoluto.
             *
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *
             */
            public void setImpSaldoInsoluto(BigDecimal value) {
                this.impSaldoInsoluto = value;
            }

            /**
             * Obtiene el valor de la propiedad objetoImpDR.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getObjetoImpDR() {
                return objetoImpDR;
            }

            /**
             * Define el valor de la propiedad objetoImpDR.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setObjetoImpDR(String value) {
                this.objetoImpDR = value;
            }

            /**
             * Obtiene el valor de la propiedad metodoDePagoDR.
             *
             * @return
             *     possible object is
             *     {@link CMetodoPago }
             *
             */
            public CMetodoPago getMetodoDePagoDR() {
                return metodoDePagoDR;
            }

            /**
             * Define el valor de la propiedad metodoDePagoDR.
             *
             * @param value
             *     allowed object is
             *     {@link CMetodoPago }
             *
             */
            public void setMetodoDePagoDR(CMetodoPago value) {
                this.metodoDePagoDR = value;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             *
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="RetencionesDR" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="RetencionDR" maxOccurs="unbounded">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;attribute name="BaseDR" use="required">
             *                             &lt;simpleType>
             *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                 &lt;minInclusive value="0.000001"/>
             *                                 &lt;fractionDigits value="6"/>
             *                                 &lt;whiteSpace value="collapse"/>
             *                               &lt;/restriction>
             *                             &lt;/simpleType>
             *                           &lt;/attribute>
             *                           &lt;attribute name="ImpuestoDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
             *                           &lt;attribute name="TipoFactorDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_TipoFactor" />
             *                           &lt;attribute name="TasaOCuotaDR" use="required">
             *                             &lt;simpleType>
             *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                 &lt;minInclusive value="0.000000"/>
             *                                 &lt;fractionDigits value="6"/>
             *                                 &lt;whiteSpace value="collapse"/>
             *                               &lt;/restriction>
             *                             &lt;/simpleType>
             *                           &lt;/attribute>
             *                           &lt;attribute name="ImporteDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="TrasladosDR" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="TrasladoDR" maxOccurs="unbounded">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;attribute name="BaseDR" use="required">
             *                             &lt;simpleType>
             *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                 &lt;minInclusive value="0.000001"/>
             *                                 &lt;fractionDigits value="6"/>
             *                                 &lt;whiteSpace value="collapse"/>
             *                               &lt;/restriction>
             *                             &lt;/simpleType>
             *                           &lt;/attribute>
             *                           &lt;attribute name="ImpuestoDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
             *                           &lt;attribute name="TipoFactorDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_TipoFactor" />
             *                           &lt;attribute name="TasaOCuotaDR">
             *                             &lt;simpleType>
             *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                 &lt;minInclusive value="0.000000"/>
             *                                 &lt;fractionDigits value="6"/>
             *                                 &lt;whiteSpace value="collapse"/>
             *                               &lt;/restriction>
             *                             &lt;/simpleType>
             *                           &lt;/attribute>
             *                           &lt;attribute name="ImporteDR" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "retencionesDR",
                "trasladosDR"
            })
            public static class ImpuestosDR {

                @XmlElement(name = "RetencionesDR", namespace = "http://www.sat.gob.mx/Pagos20")
                protected Pagos.Pago.DoctoRelacionado.ImpuestosDR.RetencionesDR retencionesDR;
                @XmlElement(name = "TrasladosDR", namespace = "http://www.sat.gob.mx/Pagos20")
                protected Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR trasladosDR;

                /**
                 * Obtiene el valor de la propiedad retencionesDR.
                 *
                 * @return
                 *     possible object is
                 *     {@link Pagos.Pago.DoctoRelacionado.ImpuestosDR.RetencionesDR }
                 *
                 */
                public Pagos.Pago.DoctoRelacionado.ImpuestosDR.RetencionesDR getRetencionesDR() {
                    return retencionesDR;
                }

                /**
                 * Define el valor de la propiedad retencionesDR.
                 *
                 * @param value
                 *     allowed object is
                 *     {@link Pagos.Pago.DoctoRelacionado.ImpuestosDR.RetencionesDR }
                 *
                 */
                public void setRetencionesDR(Pagos.Pago.DoctoRelacionado.ImpuestosDR.RetencionesDR value) {
                    this.retencionesDR = value;
                }

                /**
                 * Obtiene el valor de la propiedad trasladosDR.
                 *
                 * @return
                 *     possible object is
                 *     {@link Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR }
                 *
                 */
                public Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR getTrasladosDR() {
                    return trasladosDR;
                }

                /**
                 * Define el valor de la propiedad trasladosDR.
                 *
                 * @param value
                 *     allowed object is
                 *     {@link Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR }
                 *
                 */
                public void setTrasladosDR(Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR value) {
                    this.trasladosDR = value;
                }


                /**
                 * <p>Clase Java para anonymous complex type.
                 *
                 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="RetencionDR" maxOccurs="unbounded">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;attribute name="BaseDR" use="required">
                 *                   &lt;simpleType>
                 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                       &lt;minInclusive value="0.000001"/>
                 *                       &lt;fractionDigits value="6"/>
                 *                       &lt;whiteSpace value="collapse"/>
                 *                     &lt;/restriction>
                 *                   &lt;/simpleType>
                 *                 &lt;/attribute>
                 *                 &lt;attribute name="ImpuestoDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
                 *                 &lt;attribute name="TipoFactorDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_TipoFactor" />
                 *                 &lt;attribute name="TasaOCuotaDR" use="required">
                 *                   &lt;simpleType>
                 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                       &lt;minInclusive value="0.000000"/>
                 *                       &lt;fractionDigits value="6"/>
                 *                       &lt;whiteSpace value="collapse"/>
                 *                     &lt;/restriction>
                 *                   &lt;/simpleType>
                 *                 &lt;/attribute>
                 *                 &lt;attribute name="ImporteDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 *
                 *
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "retencionDR"
                })
                public static class RetencionesDR {

                    @XmlElement(name = "RetencionDR", namespace = "http://www.sat.gob.mx/Pagos20", required = true)
                    protected List<Pagos.Pago.DoctoRelacionado.ImpuestosDR.RetencionesDR.RetencionDR> retencionDR;

                    /**
                     * Gets the value of the retencionDR property.
                     *
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the retencionDR property.
                     *
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getRetencionDR().add(newItem);
                     * </pre>
                     *
                     *
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link Pagos.Pago.DoctoRelacionado.ImpuestosDR.RetencionesDR.RetencionDR }
                     *
                     *
                     */
                    public List<Pagos.Pago.DoctoRelacionado.ImpuestosDR.RetencionesDR.RetencionDR> getRetencionDR() {
                        if (retencionDR == null) {
                            retencionDR = new ArrayList<Pagos.Pago.DoctoRelacionado.ImpuestosDR.RetencionesDR.RetencionDR>();
                        }
                        return this.retencionDR;
                    }


                    /**
                     * <p>Clase Java para anonymous complex type.
                     *
                     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                     *
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;attribute name="BaseDR" use="required">
                     *         &lt;simpleType>
                     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *             &lt;minInclusive value="0.000001"/>
                     *             &lt;fractionDigits value="6"/>
                     *             &lt;whiteSpace value="collapse"/>
                     *           &lt;/restriction>
                     *         &lt;/simpleType>
                     *       &lt;/attribute>
                     *       &lt;attribute name="ImpuestoDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
                     *       &lt;attribute name="TipoFactorDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_TipoFactor" />
                     *       &lt;attribute name="TasaOCuotaDR" use="required">
                     *         &lt;simpleType>
                     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *             &lt;minInclusive value="0.000000"/>
                     *             &lt;fractionDigits value="6"/>
                     *             &lt;whiteSpace value="collapse"/>
                     *           &lt;/restriction>
                     *         &lt;/simpleType>
                     *       &lt;/attribute>
                     *       &lt;attribute name="ImporteDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     *
                     *
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "")
                    public static class RetencionDR {

                        @XmlAttribute(name = "BaseDR", required = true)
                        protected BigDecimal baseDR;
                        @XmlAttribute(name = "ImpuestoDR", required = true)
                        protected String impuestoDR;
                        @XmlAttribute(name = "TipoFactorDR", required = true)
                        protected CTipoFactor tipoFactorDR;
                        @XmlAttribute(name = "TasaOCuotaDR", required = true)
                        protected BigDecimal tasaOCuotaDR;
                        @XmlAttribute(name = "ImporteDR", required = true)
                        protected BigDecimal importeDR;

                        /**
                         * Obtiene el valor de la propiedad baseDR.
                         *
                         * @return
                         *     possible object is
                         *     {@link BigDecimal }
                         *
                         */
                        public BigDecimal getBaseDR() {
                            return baseDR;
                        }

                        /**
                         * Define el valor de la propiedad baseDR.
                         *
                         * @param value
                         *     allowed object is
                         *     {@link BigDecimal }
                         *
                         */
                        public void setBaseDR(BigDecimal value) {
                            this.baseDR = value;
                        }

                        /**
                         * Obtiene el valor de la propiedad impuestoDR.
                         *
                         * @return
                         *     possible object is
                         *     {@link String }
                         *
                         */
                        public String getImpuestoDR() {
                            return impuestoDR;
                        }

                        /**
                         * Define el valor de la propiedad impuestoDR.
                         *
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *
                         */
                        public void setImpuestoDR(String value) {
                            this.impuestoDR = value;
                        }

                        /**
                         * Obtiene el valor de la propiedad tipoFactorDR.
                         *
                         * @return
                         *     possible object is
                         *     {@link CTipoFactor }
                         *
                         */
                        public CTipoFactor getTipoFactorDR() {
                            return tipoFactorDR;
                        }

                        /**
                         * Define el valor de la propiedad tipoFactorDR.
                         *
                         * @param value
                         *     allowed object is
                         *     {@link CTipoFactor }
                         *
                         */
                        public void setTipoFactorDR(CTipoFactor value) {
                            this.tipoFactorDR = value;
                        }

                        /**
                         * Obtiene el valor de la propiedad tasaOCuotaDR.
                         *
                         * @return
                         *     possible object is
                         *     {@link BigDecimal }
                         *
                         */
                        public BigDecimal getTasaOCuotaDR() {
                            return tasaOCuotaDR;
                        }

                        /**
                         * Define el valor de la propiedad tasaOCuotaDR.
                         *
                         * @param value
                         *     allowed object is
                         *     {@link BigDecimal }
                         *
                         */
                        public void setTasaOCuotaDR(BigDecimal value) {
                            this.tasaOCuotaDR = value;
                        }

                        /**
                         * Obtiene el valor de la propiedad importeDR.
                         *
                         * @return
                         *     possible object is
                         *     {@link BigDecimal }
                         *
                         */
                        public BigDecimal getImporteDR() {
                            return importeDR;
                        }

                        /**
                         * Define el valor de la propiedad importeDR.
                         *
                         * @param value
                         *     allowed object is
                         *     {@link BigDecimal }
                         *
                         */
                        public void setImporteDR(BigDecimal value) {
                            this.importeDR = value;
                        }

                    }

                }


                /**
                 * <p>Clase Java para anonymous complex type.
                 *
                 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="TrasladoDR" maxOccurs="unbounded">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;attribute name="BaseDR" use="required">
                 *                   &lt;simpleType>
                 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                       &lt;minInclusive value="0.000001"/>
                 *                       &lt;fractionDigits value="6"/>
                 *                       &lt;whiteSpace value="collapse"/>
                 *                     &lt;/restriction>
                 *                   &lt;/simpleType>
                 *                 &lt;/attribute>
                 *                 &lt;attribute name="ImpuestoDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
                 *                 &lt;attribute name="TipoFactorDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_TipoFactor" />
                 *                 &lt;attribute name="TasaOCuotaDR">
                 *                   &lt;simpleType>
                 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                       &lt;minInclusive value="0.000000"/>
                 *                       &lt;fractionDigits value="6"/>
                 *                       &lt;whiteSpace value="collapse"/>
                 *                     &lt;/restriction>
                 *                   &lt;/simpleType>
                 *                 &lt;/attribute>
                 *                 &lt;attribute name="ImporteDR" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 *
                 *
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "trasladoDR"
                })
                public static class TrasladosDR {

                    @XmlElement(name = "TrasladoDR", namespace = "http://www.sat.gob.mx/Pagos20", required = true)
                    protected List<Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR.TrasladoDR> trasladoDR;

                    /**
                     * Gets the value of the trasladoDR property.
                     *
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the trasladoDR property.
                     *
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getTrasladoDR().add(newItem);
                     * </pre>
                     *
                     *
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR.TrasladoDR }
                     *
                     *
                     */
                    public List<Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR.TrasladoDR> getTrasladoDR() {
                        if (trasladoDR == null) {
                            trasladoDR = new ArrayList<Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR.TrasladoDR>();
                        }
                        return this.trasladoDR;
                    }


                    /**
                     * <p>Clase Java para anonymous complex type.
                     *
                     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                     *
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;attribute name="BaseDR" use="required">
                     *         &lt;simpleType>
                     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *             &lt;minInclusive value="0.000001"/>
                     *             &lt;fractionDigits value="6"/>
                     *             &lt;whiteSpace value="collapse"/>
                     *           &lt;/restriction>
                     *         &lt;/simpleType>
                     *       &lt;/attribute>
                     *       &lt;attribute name="ImpuestoDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
                     *       &lt;attribute name="TipoFactorDR" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_TipoFactor" />
                     *       &lt;attribute name="TasaOCuotaDR">
                     *         &lt;simpleType>
                     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *             &lt;minInclusive value="0.000000"/>
                     *             &lt;fractionDigits value="6"/>
                     *             &lt;whiteSpace value="collapse"/>
                     *           &lt;/restriction>
                     *         &lt;/simpleType>
                     *       &lt;/attribute>
                     *       &lt;attribute name="ImporteDR" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     *
                     *
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "")
                    public static class TrasladoDR {

                        @XmlAttribute(name = "BaseDR", required = true)
                        protected BigDecimal baseDR;
                        @XmlAttribute(name = "ImpuestoDR", required = true)
                        protected String impuestoDR;
                        @XmlAttribute(name = "TipoFactorDR", required = true)
                        protected CTipoFactor tipoFactorDR;
                        @XmlAttribute(name = "TasaOCuotaDR")
                        protected BigDecimal tasaOCuotaDR;
                        @XmlAttribute(name = "ImporteDR")
                        protected BigDecimal importeDR;

                        /**
                         * Obtiene el valor de la propiedad baseDR.
                         *
                         * @return
                         *     possible object is
                         *     {@link BigDecimal }
                         *
                         */
                        public BigDecimal getBaseDR() {
                            return baseDR;
                        }

                        /**
                         * Define el valor de la propiedad baseDR.
                         *
                         * @param value
                         *     allowed object is
                         *     {@link BigDecimal }
                         *
                         */
                        public void setBaseDR(BigDecimal value) {
                            this.baseDR = value;
                        }

                        /**
                         * Obtiene el valor de la propiedad impuestoDR.
                         *
                         * @return
                         *     possible object is
                         *     {@link String }
                         *
                         */
                        public String getImpuestoDR() {
                            return impuestoDR;
                        }

                        /**
                         * Define el valor de la propiedad impuestoDR.
                         *
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *
                         */
                        public void setImpuestoDR(String value) {
                            this.impuestoDR = value;
                        }

                        /**
                         * Obtiene el valor de la propiedad tipoFactorDR.
                         *
                         * @return
                         *     possible object is
                         *     {@link CTipoFactor }
                         *
                         */
                        public CTipoFactor getTipoFactorDR() {
                            return tipoFactorDR;
                        }

                        /**
                         * Define el valor de la propiedad tipoFactorDR.
                         *
                         * @param value
                         *     allowed object is
                         *     {@link CTipoFactor }
                         *
                         */
                        public void setTipoFactorDR(CTipoFactor value) {
                            this.tipoFactorDR = value;
                        }

                        /**
                         * Obtiene el valor de la propiedad tasaOCuotaDR.
                         *
                         * @return
                         *     possible object is
                         *     {@link BigDecimal }
                         *
                         */
                        public BigDecimal getTasaOCuotaDR() {
                            return tasaOCuotaDR;
                        }

                        /**
                         * Define el valor de la propiedad tasaOCuotaDR.
                         *
                         * @param value
                         *     allowed object is
                         *     {@link BigDecimal }
                         *
                         */
                        public void setTasaOCuotaDR(BigDecimal value) {
                            this.tasaOCuotaDR = value;
                        }

                        /**
                         * Obtiene el valor de la propiedad importeDR.
                         *
                         * @return
                         *     possible object is
                         *     {@link BigDecimal }
                         *
                         */
                        public BigDecimal getImporteDR() {
                            return importeDR;
                        }

                        /**
                         * Define el valor de la propiedad importeDR.
                         *
                         * @param value
                         *     allowed object is
                         *     {@link BigDecimal }
                         *
                         */
                        public void setImporteDR(BigDecimal value) {
                            this.importeDR = value;
                        }

                    }

                }

            }

        }


        /**
         * <p>Clase Java para anonymous complex type.
         *
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="RetencionesP" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="RetencionP" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;attribute name="ImpuestoP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
         *                           &lt;attribute name="ImporteP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="TrasladosP" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="TrasladoP" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;attribute name="BaseP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
         *                           &lt;attribute name="ImpuestoP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
         *                           &lt;attribute name="TipoFactorP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_TipoFactor" />
         *                           &lt;attribute name="TasaOCuotaP">
         *                             &lt;simpleType>
         *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                 &lt;fractionDigits value="6"/>
         *                                 &lt;minInclusive value="0.000000"/>
         *                                 &lt;whiteSpace value="collapse"/>
         *                               &lt;/restriction>
         *                             &lt;/simpleType>
         *                           &lt;/attribute>
         *                           &lt;attribute name="ImporteP" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "retencionesP",
            "trasladosP"
        })
        public static class ImpuestosP {

            @XmlElement(name = "RetencionesP", namespace = "http://www.sat.gob.mx/Pagos20")
            protected Pagos.Pago.ImpuestosP.RetencionesP retencionesP;
            @XmlElement(name = "TrasladosP", namespace = "http://www.sat.gob.mx/Pagos20")
            protected Pagos.Pago.ImpuestosP.TrasladosP trasladosP;

            /**
             * Obtiene el valor de la propiedad retencionesP.
             *
             * @return
             *     possible object is
             *     {@link Pagos.Pago.ImpuestosP.RetencionesP }
             *
             */
            public Pagos.Pago.ImpuestosP.RetencionesP getRetencionesP() {
                return retencionesP;
            }

            /**
             * Define el valor de la propiedad retencionesP.
             *
             * @param value
             *     allowed object is
             *     {@link Pagos.Pago.ImpuestosP.RetencionesP }
             *
             */
            public void setRetencionesP(Pagos.Pago.ImpuestosP.RetencionesP value) {
                this.retencionesP = value;
            }

            /**
             * Obtiene el valor de la propiedad trasladosP.
             *
             * @return
             *     possible object is
             *     {@link Pagos.Pago.ImpuestosP.TrasladosP }
             *
             */
            public Pagos.Pago.ImpuestosP.TrasladosP getTrasladosP() {
                return trasladosP;
            }

            /**
             * Define el valor de la propiedad trasladosP.
             *
             * @param value
             *     allowed object is
             *     {@link Pagos.Pago.ImpuestosP.TrasladosP }
             *
             */
            public void setTrasladosP(Pagos.Pago.ImpuestosP.TrasladosP value) {
                this.trasladosP = value;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             *
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="RetencionP" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;attribute name="ImpuestoP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
             *                 &lt;attribute name="ImporteP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "retencionP"
            })
            public static class RetencionesP {

                @XmlElement(name = "RetencionP", namespace = "http://www.sat.gob.mx/Pagos20", required = true)
                protected List<Pagos.Pago.ImpuestosP.RetencionesP.RetencionP> retencionP;

                /**
                 * Gets the value of the retencionP property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the retencionP property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getRetencionP().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Pagos.Pago.ImpuestosP.RetencionesP.RetencionP }
                 *
                 *
                 */
                public List<Pagos.Pago.ImpuestosP.RetencionesP.RetencionP> getRetencionP() {
                    if (retencionP == null) {
                        retencionP = new ArrayList<Pagos.Pago.ImpuestosP.RetencionesP.RetencionP>();
                    }
                    return this.retencionP;
                }


                /**
                 * <p>Clase Java para anonymous complex type.
                 *
                 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;attribute name="ImpuestoP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
                 *       &lt;attribute name="ImporteP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 *
                 *
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "")
                public static class RetencionP {

                    @XmlAttribute(name = "ImpuestoP", required = true)
                    protected String impuestoP;
                    @XmlAttribute(name = "ImporteP", required = true)
                    protected BigDecimal importeP;

                    /**
                     * Obtiene el valor de la propiedad impuestoP.
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getImpuestoP() {
                        return impuestoP;
                    }

                    /**
                     * Define el valor de la propiedad impuestoP.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setImpuestoP(String value) {
                        this.impuestoP = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad importeP.
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getImporteP() {
                        return importeP;
                    }

                    /**
                     * Define el valor de la propiedad importeP.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setImporteP(BigDecimal value) {
                        this.importeP = value;
                    }

                }

            }


            /**
             * <p>Clase Java para anonymous complex type.
             *
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="TrasladoP" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;attribute name="BaseP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
             *                 &lt;attribute name="ImpuestoP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
             *                 &lt;attribute name="TipoFactorP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_TipoFactor" />
             *                 &lt;attribute name="TasaOCuotaP">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                       &lt;fractionDigits value="6"/>
             *                       &lt;minInclusive value="0.000000"/>
             *                       &lt;whiteSpace value="collapse"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *                 &lt;attribute name="ImporteP" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "trasladoP"
            })
            public static class TrasladosP {

                @XmlElement(name = "TrasladoP", namespace = "http://www.sat.gob.mx/Pagos20", required = true)
                protected List<Pagos.Pago.ImpuestosP.TrasladosP.TrasladoP> trasladoP;

                /**
                 * Gets the value of the trasladoP property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the trasladoP property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getTrasladoP().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Pagos.Pago.ImpuestosP.TrasladosP.TrasladoP }
                 *
                 *
                 */
                public List<Pagos.Pago.ImpuestosP.TrasladosP.TrasladoP> getTrasladoP() {
                    if (trasladoP == null) {
                        trasladoP = new ArrayList<Pagos.Pago.ImpuestosP.TrasladosP.TrasladoP>();
                    }
                    return this.trasladoP;
                }


                /**
                 * <p>Clase Java para anonymous complex type.
                 *
                 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;attribute name="BaseP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
                 *       &lt;attribute name="ImpuestoP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_Impuesto" />
                 *       &lt;attribute name="TipoFactorP" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/catalogos}c_TipoFactor" />
                 *       &lt;attribute name="TasaOCuotaP">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *             &lt;fractionDigits value="6"/>
                 *             &lt;minInclusive value="0.000000"/>
                 *             &lt;whiteSpace value="collapse"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *       &lt;attribute name="ImporteP" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_Importe" />
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 *
                 *
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "")
                public static class TrasladoP {

                    @XmlAttribute(name = "BaseP", required = true)
                    protected BigDecimal baseP;
                    @XmlAttribute(name = "ImpuestoP", required = true)
                    protected String impuestoP;
                    @XmlAttribute(name = "TipoFactorP", required = true)
                    protected CTipoFactor tipoFactorP;
                    @XmlAttribute(name = "TasaOCuotaP")
                    protected BigDecimal tasaOCuotaP;
                    @XmlAttribute(name = "ImporteP")
                    protected BigDecimal importeP;

                    /**
                     * Obtiene el valor de la propiedad baseP.
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getBaseP() {
                        return baseP;
                    }

                    /**
                     * Define el valor de la propiedad baseP.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setBaseP(BigDecimal value) {
                        this.baseP = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad impuestoP.
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getImpuestoP() {
                        return impuestoP;
                    }

                    /**
                     * Define el valor de la propiedad impuestoP.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setImpuestoP(String value) {
                        this.impuestoP = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad tipoFactorP.
                     *
                     * @return
                     *     possible object is
                     *     {@link CTipoFactor }
                     *
                     */
                    public CTipoFactor getTipoFactorP() {
                        return tipoFactorP;
                    }

                    /**
                     * Define el valor de la propiedad tipoFactorP.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link CTipoFactor }
                     *
                     */
                    public void setTipoFactorP(CTipoFactor value) {
                        this.tipoFactorP = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad tasaOCuotaP.
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getTasaOCuotaP() {
                        return tasaOCuotaP;
                    }

                    /**
                     * Define el valor de la propiedad tasaOCuotaP.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setTasaOCuotaP(BigDecimal value) {
                        this.tasaOCuotaP = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad importeP.
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getImporteP() {
                        return importeP;
                    }

                    /**
                     * Define el valor de la propiedad importeP.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setImporteP(BigDecimal value) {
                        this.importeP = value;
                    }

                }

            }

        }

    }


    /**
     * <p>Clase Java para anonymous complex type.
     *
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="TotalRetencionesIVA" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
     *       &lt;attribute name="TotalRetencionesISR" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
     *       &lt;attribute name="TotalRetencionesIEPS" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
     *       &lt;attribute name="TotalTrasladosBaseIVA16" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
     *       &lt;attribute name="TotalTrasladosImpuestoIVA16" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
     *       &lt;attribute name="TotalTrasladosBaseIVA8" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
     *       &lt;attribute name="TotalTrasladosImpuestoIVA8" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
     *       &lt;attribute name="TotalTrasladosBaseIVA0" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
     *       &lt;attribute name="TotalTrasladosImpuestoIVA0" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
     *       &lt;attribute name="TotalTrasladosBaseIVAExento" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
     *       &lt;attribute name="MontoTotalPagos" use="required" type="{http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI}t_ImporteMXN" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Totales {

        @XmlAttribute(name = "TotalRetencionesIVA")
        protected BigDecimal totalRetencionesIVA;
        @XmlAttribute(name = "TotalRetencionesISR")
        protected BigDecimal totalRetencionesISR;
        @XmlAttribute(name = "TotalRetencionesIEPS")
        protected BigDecimal totalRetencionesIEPS;
        @XmlAttribute(name = "TotalTrasladosBaseIVA16")
        protected BigDecimal totalTrasladosBaseIVA16;
        @XmlAttribute(name = "TotalTrasladosImpuestoIVA16")
        protected BigDecimal totalTrasladosImpuestoIVA16;
        @XmlAttribute(name = "TotalTrasladosBaseIVA8")
        protected BigDecimal totalTrasladosBaseIVA8;
        @XmlAttribute(name = "TotalTrasladosImpuestoIVA8")
        protected BigDecimal totalTrasladosImpuestoIVA8;
        @XmlAttribute(name = "TotalTrasladosBaseIVA0")
        protected BigDecimal totalTrasladosBaseIVA0;
        @XmlAttribute(name = "TotalTrasladosImpuestoIVA0")
        protected BigDecimal totalTrasladosImpuestoIVA0;
        @XmlAttribute(name = "TotalTrasladosBaseIVAExento")
        protected BigDecimal totalTrasladosBaseIVAExento;
        @XmlAttribute(name = "MontoTotalPagos", required = true)
        protected BigDecimal montoTotalPagos;

        /**
         * Obtiene el valor de la propiedad totalRetencionesIVA.
         *
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *
         */
        public BigDecimal getTotalRetencionesIVA() {
            return totalRetencionesIVA;
        }

        /**
         * Define el valor de la propiedad totalRetencionesIVA.
         *
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *
         */
        public void setTotalRetencionesIVA(BigDecimal value) {
            this.totalRetencionesIVA = value;
        }

        /**
         * Obtiene el valor de la propiedad totalRetencionesISR.
         *
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *
         */
        public BigDecimal getTotalRetencionesISR() {
            return totalRetencionesISR;
        }

        /**
         * Define el valor de la propiedad totalRetencionesISR.
         *
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *
         */
        public void setTotalRetencionesISR(BigDecimal value) {
            this.totalRetencionesISR = value;
        }

        /**
         * Obtiene el valor de la propiedad totalRetencionesIEPS.
         *
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *
         */
        public BigDecimal getTotalRetencionesIEPS() {
            return totalRetencionesIEPS;
        }

        /**
         * Define el valor de la propiedad totalRetencionesIEPS.
         *
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *
         */
        public void setTotalRetencionesIEPS(BigDecimal value) {
            this.totalRetencionesIEPS = value;
        }

        /**
         * Obtiene el valor de la propiedad totalTrasladosBaseIVA16.
         *
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *
         */
        public BigDecimal getTotalTrasladosBaseIVA16() {
            return totalTrasladosBaseIVA16;
        }

        /**
         * Define el valor de la propiedad totalTrasladosBaseIVA16.
         *
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *
         */
        public void setTotalTrasladosBaseIVA16(BigDecimal value) {
            this.totalTrasladosBaseIVA16 = value;
        }

        /**
         * Obtiene el valor de la propiedad totalTrasladosImpuestoIVA16.
         *
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *
         */
        public BigDecimal getTotalTrasladosImpuestoIVA16() {
            return totalTrasladosImpuestoIVA16;
        }

        /**
         * Define el valor de la propiedad totalTrasladosImpuestoIVA16.
         *
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *
         */
        public void setTotalTrasladosImpuestoIVA16(BigDecimal value) {
            this.totalTrasladosImpuestoIVA16 = value;
        }

        /**
         * Obtiene el valor de la propiedad totalTrasladosBaseIVA8.
         *
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *
         */
        public BigDecimal getTotalTrasladosBaseIVA8() {
            return totalTrasladosBaseIVA8;
        }

        /**
         * Define el valor de la propiedad totalTrasladosBaseIVA8.
         *
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *
         */
        public void setTotalTrasladosBaseIVA8(BigDecimal value) {
            this.totalTrasladosBaseIVA8 = value;
        }

        /**
         * Obtiene el valor de la propiedad totalTrasladosImpuestoIVA8.
         *
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *
         */
        public BigDecimal getTotalTrasladosImpuestoIVA8() {
            return totalTrasladosImpuestoIVA8;
        }

        /**
         * Define el valor de la propiedad totalTrasladosImpuestoIVA8.
         *
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *
         */
        public void setTotalTrasladosImpuestoIVA8(BigDecimal value) {
            this.totalTrasladosImpuestoIVA8 = value;
        }

        /**
         * Obtiene el valor de la propiedad totalTrasladosBaseIVA0.
         *
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *
         */
        public BigDecimal getTotalTrasladosBaseIVA0() {
            return totalTrasladosBaseIVA0;
        }

        /**
         * Define el valor de la propiedad totalTrasladosBaseIVA0.
         *
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *
         */
        public void setTotalTrasladosBaseIVA0(BigDecimal value) {
            this.totalTrasladosBaseIVA0 = value;
        }

        /**
         * Obtiene el valor de la propiedad totalTrasladosImpuestoIVA0.
         *
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *
         */
        public BigDecimal getTotalTrasladosImpuestoIVA0() {
            return totalTrasladosImpuestoIVA0;
        }

        /**
         * Define el valor de la propiedad totalTrasladosImpuestoIVA0.
         *
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *
         */
        public void setTotalTrasladosImpuestoIVA0(BigDecimal value) {
            this.totalTrasladosImpuestoIVA0 = value;
        }

        /**
         * Obtiene el valor de la propiedad totalTrasladosBaseIVAExento.
         *
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *
         */
        public BigDecimal getTotalTrasladosBaseIVAExento() {
            return totalTrasladosBaseIVAExento;
        }

        /**
         * Define el valor de la propiedad totalTrasladosBaseIVAExento.
         *
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *
         */
        public void setTotalTrasladosBaseIVAExento(BigDecimal value) {
            this.totalTrasladosBaseIVAExento = value;
        }

        /**
         * Obtiene el valor de la propiedad montoTotalPagos.
         *
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *
         */
        public BigDecimal getMontoTotalPagos() {
            return montoTotalPagos;
        }

        /**
         * Define el valor de la propiedad montoTotalPagos.
         *
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *
         */
        public void setMontoTotalPagos(BigDecimal value) {
            this.montoTotalPagos = value;
        }

    }

}
