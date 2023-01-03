package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.Archivo;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
@Entity
@Table(name = "CXPSolicitudesPagosRHBecariosDocumentos")
public class CXPSolicitudPagoRHBecarioDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CPXSPRHBD_CXPSolicitudPagoRhBecarioDocumentoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "CPXSPRHBD_CPXSPRH_CXPSolicitudPagoRhId", nullable = true, insertable = false, updatable = false)
    private Integer cpxSolicitudPagoRhId;

    @OneToOne
    @JoinColumn(name = "CPXSPRHBD_ARC_ArchivoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo archivo;

    @Column(name = "CPXSPRHBD_ARC_ArchivoId", nullable = false)
    private Integer archivoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCpxSolicitudPagoRhId() {
        return cpxSolicitudPagoRhId;
    }

    public void setCpxSolicitudPagoRhId(Integer cpxSolicitudPagoRhId) {
        this.cpxSolicitudPagoRhId = cpxSolicitudPagoRhId;
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
}
