package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "CXCFacturas")
public class CXCFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CXCF_FacturaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "CXCF_Version", nullable = false)
    private String version;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "CXCF_Fecha", nullable = false)
    private Date fecha;

    @Column(name = "CXCF_Serie")
    private String serie;

    @Column(name = "CXCF_Folio", nullable = false)
    private String folio;

    @Column(name = "CXCF_FP_FormaPagoId")
    private Integer formaPagoId;

    @OneToOne
    @JoinColumn(name = "CXCF_FP_FormaPagoId", insertable = false, updatable = false, referencedColumnName = "FP_FormaPagoId")
    private FormaPago formaPago;

    @Column(name = "CXCF_DiasCredito")
    private Integer diasCredito;

    @Column(name = "CXCF_CondicionesPago")
    private String condicionesPago;

    @Column(name = "CXCF_MON_MonedaId")
    private Integer monedaId;

    @OneToOne
    @JoinColumn(name = "CXCF_MON_MonedaId", insertable = false, updatable = false, referencedColumnName = "MON_MonedaId")
    private Moneda moneda;

    @Column(name = "CXCF_TipoCambio")
    private BigDecimal tipoCambio;

    @Column(name = "CXCF_CMM_MetodoPagoId")
    private Integer metodoPagoId;

    @OneToOne
    @JoinColumn(name = "CXCF_CMM_MetodoPagoId", insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple metodoPago;

    @Column(name = "CXCF_EmisorCP", nullable = false)
    private String emisorCP;

    @Column(name = "CXCF_EmisorRFC", nullable = false)
    private String emisorRFC;

    @Column(name = "CXCF_EmisorRazonSocial", nullable = false)
    private String emisorRazonSocial;

    @Column(name = "CXCF_EmisorRegimenFiscalId", nullable = false)
    private int emisorRegimenFiscalId;

    @OneToOne
    @JoinColumn(name = "CXCF_EmisorRegimenFiscalId", insertable = false, updatable = false, referencedColumnName = "RF_RegimenFiscalId")
    private SATRegimenFiscal emisorRegimenFiscal;

    @Column(name = "CXCF_ReceptorCP", nullable = true)
    private String receptorCP;

    @Column(name = "CXCF_ReceptorRFC", nullable = false)
    private String receptorRFC;

    @Column(name = "CXCF_ReceptorNombre", nullable = true)
    private String receptorNombre;

    @Column(name = "CXCF_ReceptorRegimenFiscalId", nullable = true)
    private Integer receptorRegimenFiscalId;

    @OneToOne
    @JoinColumn(name = "CXCF_ReceptorRegimenFiscalId", insertable = false, updatable = false, referencedColumnName = "RF_RegimenFiscalId")
    private SATRegimenFiscal receptorRegimenFiscal;

    @Column(name = "CXCF_ReceptorUsoCFDIId", nullable = false)
    private int receptorUsoCFDIId;

    @OneToOne
    @JoinColumn(name = "CXCF_ReceptorUsoCFDIId", insertable = false, updatable = false, referencedColumnName = "UCFDI_UsoCFDIId")
    private SATUsoCFDI receptorUsoCFDI;

    @Column(name = "CXCF_UUID")
    private String uuid;

    @Column(name = "CXCF_XML")
    private String xml;

    @Column(name = "CXCF_CMM_TipoRegistroId", nullable = false)
    private int tipoRegistroId;

    @OneToOne
    @JoinColumn(name = "CXCF_CMM_TipoRegistroId", insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoRegistro;

    @Column(name = "CXCF_DF_DatosFacturacionId")
    private Integer datosFacturacionId;

    @OneToOne
    @JoinColumn(name = "CXCF_DF_DatosFacturacionId", insertable = false, updatable = false, referencedColumnName = "DF_DatosFacturacionId")
    private DatosFacturacion datosFacturacion;

    @Column(name = "CXCF_SUC_SucursalId")
    private Integer sucursalId;

    @OneToOne
    @JoinColumn(name = "CXCF_SUC_SucursalId", insertable = false, updatable = false, referencedColumnName = "SUC_SucursalId")
    private Sucursal sucursal;

    @Column(name = "CXCF_PER_PeriodicidadId")
    private Integer periodicidadId;

    @OneToOne
    @JoinColumn(name = "CXCF_PER_PeriodicidadId", insertable = false, updatable = false, referencedColumnName = "PER_PeriodicidadId")
    private SATPeriodicidad periodicidad;

    @Column(name = "CXCF_MES_MesId")
    private Integer mesId;

    @OneToOne
    @JoinColumn(name = "CXCF_MES_MesId", insertable = false, updatable = false, referencedColumnName = "MES_MesId")
    private SATMes mes;

    @Column(name = "CXCF_Anio")
    private Integer anio;

    @Column(name = "CXCF_FacturaRelacionadaId")
    private Integer facturaRelacionadaId;

    @OneToOne
    @JoinColumn(name = "CXCF_FacturaRelacionadaId", insertable = false, updatable = false, referencedColumnName = "CXCF_FacturaId")
    private CXCFactura facturaRelacionada;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "CXCF_FacturaRelacionadaId", referencedColumnName = "CXCF_FacturaId")
    private List<CXCFactura> facturasRelacionadas;

    @Column(name = "CXCF_CMM_TipoRelacionId")
    private Integer tipoRelacionId;

    @OneToOne
    @JoinColumn(name = "CXCF_CMM_TipoRelacionId", insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoRelacion;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "CXCF_FechaCancelacion")
    private Date fechaCancelacion;

    @Column(name = "CXCF_CMM_MotivoCancelacionId")
    private Integer motivoCancelacionId;

    @OneToOne
    @JoinColumn(name = "CXCF_CMM_MotivoCancelacionId", insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple motivoCancelacion;

    @Column(name = "CXCF_CMM_EstatusId", nullable = false)
    private int estatusId;

    @OneToOne
    @JoinColumn(name = "CXCF_CMM_EstatusId", insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @CreationTimestamp
    @Column(name = "CXCF_FechaCreacion", nullable = false, updatable = false)
    private Date fechaCreacion;

    @Column(name = "CXCF_USU_CreadoPorId", updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "CXCF_USU_CreadoPorId", insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "CXCF_FechaModificacion")
    private Date fechaModificacion;

    @Column(name = "CXCF_USU_ModificadoPorId")
    private Integer modificadoPorId;

    @OneToOne
    @JoinColumn(name = "CXCF_USU_ModificadoPorId", insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "CXCFD_CXCF_FacturaId", referencedColumnName = "CXCF_FacturaId", nullable = false)
    private List<CXCFacturaDetalle> detalles;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "CXCF_FacturaId", referencedColumnName = "CXCP_CXCF_FacturaId")
    private CXCPago pago;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "CXCPD_CXCF_DoctoRelacionadoId", insertable = false, updatable = false, referencedColumnName = "CXCF_FacturaId")
    private List<CXCPagoDetalle> pagos;

    @Transient
    private boolean timbrar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Integer getFormaPagoId() {
        return formaPagoId;
    }

    public void setFormaPagoId(Integer formaPagoId) {
        this.formaPagoId = formaPagoId;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Integer getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(Integer diasCredito) {
        this.diasCredito = diasCredito;
    }

    public String getCondicionesPago() {
        return condicionesPago;
    }

    public void setCondicionesPago(String condicionesPago) {
        this.condicionesPago = condicionesPago;
    }

    public Integer getMonedaId() {
        return monedaId;
    }

    public void setMonedaId(Integer monedaId) {
        this.monedaId = monedaId;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public Integer getMetodoPagoId() {
        return metodoPagoId;
    }

    public void setMetodoPagoId(Integer metodoPagoId) {
        this.metodoPagoId = metodoPagoId;
    }

    public ControlMaestroMultiple getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(ControlMaestroMultiple metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEmisorCP() {
        return emisorCP;
    }

    public void setEmisorCP(String emisorCP) {
        this.emisorCP = emisorCP;
    }

    public String getEmisorRFC() {
        return emisorRFC;
    }

    public void setEmisorRFC(String emisorRFC) {
        this.emisorRFC = emisorRFC;
    }

    public String getEmisorRazonSocial() {
        return emisorRazonSocial;
    }

    public void setEmisorRazonSocial(String emisorRazonSocial) {
        this.emisorRazonSocial = emisorRazonSocial;
    }

    public int getEmisorRegimenFiscalId() {
        return emisorRegimenFiscalId;
    }

    public void setEmisorRegimenFiscalId(int emisorRegimenFiscalId) {
        this.emisorRegimenFiscalId = emisorRegimenFiscalId;
    }

    public SATRegimenFiscal getEmisorRegimenFiscal() {
        return emisorRegimenFiscal;
    }

    public void setEmisorRegimenFiscal(SATRegimenFiscal emisorRegimenFiscal) {
        this.emisorRegimenFiscal = emisorRegimenFiscal;
    }

    public String getReceptorCP() {
        return receptorCP;
    }

    public void setReceptorCP(String receptorCP) {
        this.receptorCP = receptorCP;
    }

    public String getReceptorRFC() {
        return receptorRFC;
    }

    public void setReceptorRFC(String receptorRFC) {
        this.receptorRFC = receptorRFC;
    }

    public String getReceptorNombre() {
        return receptorNombre;
    }

    public void setReceptorNombre(String receptorNombre) {
        this.receptorNombre = receptorNombre;
    }

    public Integer getReceptorRegimenFiscalId() {
        return receptorRegimenFiscalId;
    }

    public void setReceptorRegimenFiscalId(Integer receptorRegimenFiscalId) {
        this.receptorRegimenFiscalId = receptorRegimenFiscalId;
    }

    public SATRegimenFiscal getReceptorRegimenFiscal() {
        return receptorRegimenFiscal;
    }

    public void setReceptorRegimenFiscal(SATRegimenFiscal receptorRegimenFiscal) {
        this.receptorRegimenFiscal = receptorRegimenFiscal;
    }

    public int getReceptorUsoCFDIId() {
        return receptorUsoCFDIId;
    }

    public void setReceptorUsoCFDIId(int receptorUsoCFDIId) {
        this.receptorUsoCFDIId = receptorUsoCFDIId;
    }

    public SATUsoCFDI getReceptorUsoCFDI() {
        return receptorUsoCFDI;
    }

    public void setReceptorUsoCFDI(SATUsoCFDI receptorUsoCFDI) {
        this.receptorUsoCFDI = receptorUsoCFDI;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public int getTipoRegistroId() {
        return tipoRegistroId;
    }

    public void setTipoRegistroId(int tipoRegistroId) {
        this.tipoRegistroId = tipoRegistroId;
    }

    public ControlMaestroMultiple getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(ControlMaestroMultiple tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public Integer getDatosFacturacionId() {
        return datosFacturacionId;
    }

    public void setDatosFacturacionId(Integer datosFacturacionId) {
        this.datosFacturacionId = datosFacturacionId;
    }

    public DatosFacturacion getDatosFacturacion() {
        return datosFacturacion;
    }

    public void setDatosFacturacion(DatosFacturacion datosFacturacion) {
        this.datosFacturacion = datosFacturacion;
    }

    public Integer getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Integer sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getPeriodicidadId() {
        return periodicidadId;
    }

    public void setPeriodicidadId(Integer periodicidadId) {
        this.periodicidadId = periodicidadId;
    }

    public SATPeriodicidad getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(SATPeriodicidad periodicidad) {
        this.periodicidad = periodicidad;
    }

    public Integer getMesId() {
        return mesId;
    }

    public void setMesId(Integer mesId) {
        this.mesId = mesId;
    }

    public SATMes getMes() {
        return mes;
    }

    public void setMes(SATMes mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getFacturaRelacionadaId() {
        return facturaRelacionadaId;
    }

    public void setFacturaRelacionadaId(Integer facturaRelacionadaId) {
        this.facturaRelacionadaId = facturaRelacionadaId;
    }

    public CXCFactura getFacturaRelacionada() {
        return facturaRelacionada;
    }

    public void setFacturaRelacionada(CXCFactura facturaRelacionada) {
        this.facturaRelacionada = facturaRelacionada;
    }

    public List<CXCFactura> getFacturasRelacionadas() {
        return facturasRelacionadas;
    }

    public void setFacturasRelacionadas(List<CXCFactura> facturasRelacionadas) {
        this.facturasRelacionadas = facturasRelacionadas;
    }

    public Integer getTipoRelacionId() {
        return tipoRelacionId;
    }

    public void setTipoRelacionId(Integer tipoRelacionId) {
        this.tipoRelacionId = tipoRelacionId;
    }

    public ControlMaestroMultiple getTipoRelacion() {
        return tipoRelacion;
    }

    public void setTipoRelacion(ControlMaestroMultiple tipoRelacion) {
        this.tipoRelacion = tipoRelacion;
    }

    public Date getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public Integer getMotivoCancelacionId() {
        return motivoCancelacionId;
    }

    public void setMotivoCancelacionId(Integer motivoCancelacionId) {
        this.motivoCancelacionId = motivoCancelacionId;
    }

    public ControlMaestroMultiple getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(ControlMaestroMultiple motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }

    public int getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(int estatusId) {
        this.estatusId = estatusId;
    }

    public ControlMaestroMultiple getEstatus() {
        return estatus;
    }

    public void setEstatus(ControlMaestroMultiple estatus) {
        this.estatus = estatus;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(Integer creadoPorId) {
        this.creadoPorId = creadoPorId;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Integer getModificadoPorId() {
        return modificadoPorId;
    }

    public void setModificadoPorId(Integer modificadoPorId) {
        this.modificadoPorId = modificadoPorId;
    }

    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public List<CXCFacturaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<CXCFacturaDetalle> detalles) {
        this.detalles = detalles;
    }

    public CXCPago getPago() {
        return pago;
    }

    public void setPago(CXCPago pago) {
        this.pago = pago;
    }

    public List<CXCPagoDetalle> getPagos() {
        return pagos;
    }

    public void setPagos(List<CXCPagoDetalle> pagos) {
        this.pagos = pagos;
    }

    public boolean isTimbrar() {
        return timbrar;
    }

    public void setTimbrar(boolean timbrar) {
        this.timbrar = timbrar;
    }
}
