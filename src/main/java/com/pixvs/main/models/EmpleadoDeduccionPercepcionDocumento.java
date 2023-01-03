package com.pixvs.main.models;

import com.pixvs.spring.models.Archivo;

import javax.persistence.*;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
@Entity
@Table(name = "EmpleadosDeduccionesPercepcionesDocumentos")
public class EmpleadoDeduccionPercepcionDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EDPD_EmpleadoDeduccionPercepcionDocumentoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "EDPD_EDP_EmpleadoDeduccionPercepcionId", nullable = true, insertable = false, updatable = false)
    private Integer empleadoDeduccionpercepcionId;

    @OneToOne
    @JoinColumn(name = "EDPD_ARC_ArchivoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo archivo;

    @Column(name = "EDPD_ARC_ArchivoId", nullable = false)
    private Integer archivoId;

    @Column(name = "EDPD_Activo", nullable = false)
    private Boolean activo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpleadoDeduccionpercepcionId() {
        return empleadoDeduccionpercepcionId;
    }

    public void setEmpleadoDeduccionpercepcionId(Integer empleadoDeduccionpercepcionId) {
        this.empleadoDeduccionpercepcionId = empleadoDeduccionpercepcionId;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
