package com.pixvs.main.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CXCFacturasDetallesImpuestos")
public class CXCFacturaDetalleImpuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CXCFDI_FacturaDetalleImpuestoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "CXCFDI_CXCFD_FacturaDetalleId", nullable = false, insertable = false, updatable = false)
    private int facturaDetalleId;

    @OneToOne
    @JoinColumn(name = "CXCFDI_CXCFD_FacturaDetalleId", insertable = false, updatable = false, referencedColumnName = "CXCFD_FacturaDetalleId")
    private CXCFacturaDetalle facturaDetalle;

    @Column(name = "CXCFDI_Clave", nullable = false)
    private String clave;

    @Column(name = "CXCFDI_Nombre", nullable = false)
    private String nombre;

    @Column(name = "CXCFDI_TipoFactor", nullable = false)
    private String tipoFactor;

    @Column(name = "CXCFDI_Base", nullable = false)
    private BigDecimal base;

    @Column(name = "CXCFDI_TasaOCuota", nullable = false)
    private BigDecimal tasaOCuota;

    @Column(name = "CXCFDI_Importe", nullable = false)
    private BigDecimal importe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFacturaDetalleId() {
        return facturaDetalleId;
    }

    public void setFacturaDetalleId(int facturaDetalleId) {
        this.facturaDetalleId = facturaDetalleId;
    }

    public CXCFacturaDetalle getFacturaDetalle() {
        return facturaDetalle;
    }

    public void setFacturaDetalle(CXCFacturaDetalle facturaDetalle) {
        this.facturaDetalle = facturaDetalle;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoFactor() {
        return tipoFactor;
    }

    public void setTipoFactor(String tipoFactor) {
        this.tipoFactor = tipoFactor;
    }

    public BigDecimal getBase() {
        return base;
    }

    public void setBase(BigDecimal base) {
        this.base = base;
    }

    public BigDecimal getTasaOCuota() {
        return tasaOCuota;
    }

    public void setTasaOCuota(BigDecimal tasaOCuota) {
        this.tasaOCuota = tasaOCuota;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }
}
