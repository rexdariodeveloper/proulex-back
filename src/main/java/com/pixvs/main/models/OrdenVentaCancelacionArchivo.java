package com.pixvs.main.models;

import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

/**
 * Created by Angel Daniel Hernández Silva on 12/04/2022.
 */
@Entity
@Table(name = "OrdenesVentaCancelacionesArchivos")
public class OrdenVentaCancelacionArchivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OVCA_OrdenVentaCancelacionArchivoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OVCA_OVC_OrdenVentaCancelacionId", nullable = false, insertable = false, updatable = false, referencedColumnName = "OVC_OrdenVentaCancelacionId")
    private OrdenVentaCancelacion ordenVentaCancelacion;

    @Column(name = "OVCA_OVC_OrdenVentaCancelacionId", nullable = false, insertable = false, updatable = false)
    private Integer ordenVentaCancelacionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OVCA_ARC_ArchivoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo archivo;

    @Column(name = "OVCA_ARC_ArchivoId", nullable = false, insertable = true, updatable = false)
    private Integer archivoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OVCA_CMM_TipoDocumentoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoDocumento;

    @Column(name = "OVCA_CMM_TipoDocumentoId", nullable = false, insertable = true, updatable = false)
    private Integer tipoDocumentoId;

    @Column(name = "OVCA_Valor", length = 255, nullable = false, insertable = true, updatable = false)
    private String valor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrdenVentaCancelacion getOrdenVentaCancelacion() {
        return ordenVentaCancelacion;
    }

    public void setOrdenVentaCancelacion(OrdenVentaCancelacion ordenVentaCancelacion) {
        this.ordenVentaCancelacion = ordenVentaCancelacion;
    }

    public Integer getOrdenVentaCancelacionId() {
        return ordenVentaCancelacionId;
    }

    public void setOrdenVentaCancelacionId(Integer ordenVentaCancelacionId) {
        this.ordenVentaCancelacionId = ordenVentaCancelacionId;
    }

    public Archivo getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

    public Integer getArchivoId() {
        return archivoId;
    }

    public void setArchivoId(Integer archivoId) {
        this.archivoId = archivoId;
    }

    public ControlMaestroMultiple getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(ControlMaestroMultiple tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(Integer tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
