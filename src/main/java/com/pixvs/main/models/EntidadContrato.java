package com.pixvs.main.models;

import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestroMultiple;

import javax.persistence.*;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "EntidadesContratos")
public class EntidadContrato {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENTC_EntidadContratoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "ENTC_ENT_EntidadId", nullable = true, insertable = false, updatable = false)
    private Integer entidadId;

    //Idioma
    @OneToOne
    @JoinColumn(name = "ENTC_CMM_TipoContratoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoContrato;

    @Column(name = "ENTC_CMM_TipoContratoId", nullable = false, insertable = true, updatable = true)
    private Integer tipoContratoId;

    @OneToOne
    @JoinColumn(name = "ENTC_ARC_DocumentoContratoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo documentoContrato;

    @Column(name = "ENTC_ARC_DocumentoContratoId", nullable = true, insertable = true, updatable = true)
    private Integer documentoContratoId;

    @OneToOne
    @JoinColumn(name = "ENTC_ARC_AdendumContratoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo adendumContrato;

    @Column(name = "ENTC_ARC_AdendumContratoId", nullable = true, insertable = true, updatable = true)
    private Integer adendumContratoId;

    @Column(name = "ENTC_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(Integer entidadId) {
        this.entidadId = entidadId;
    }

    public ControlMaestroMultiple getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(ControlMaestroMultiple tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public Integer getTipoContratoId() {
        return tipoContratoId;
    }

    public void setTipoContratoId(Integer tipoContratoId) {
        this.tipoContratoId = tipoContratoId;
    }

    public Archivo getDocumentoContrato() {
        return documentoContrato;
    }

    public void setDocumentoContrato(Archivo documentoContrato) {
        this.documentoContrato = documentoContrato;
    }

    public Integer getDocumentoContratoId() {
        return documentoContratoId;
    }

    public void setDocumentoContratoId(Integer documentoContratoId) {
        this.documentoContratoId = documentoContratoId;
    }

    public Archivo getAdendumContrato() {
        return adendumContrato;
    }

    public void setAdendumContrato(Archivo adendumContrato) {
        this.adendumContrato = adendumContrato;
    }

    public Integer getAdendumContratoId() {
        return adendumContratoId;
    }

    public void setAdendumContratoId(Integer adendumContratoId) {
        this.adendumContratoId = adendumContratoId;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
