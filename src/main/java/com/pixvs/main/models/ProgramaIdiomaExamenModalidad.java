package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "ProgramasIdiomasExamenesModalidades")
public class ProgramaIdiomaExamenModalidad {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROGIEM_ProgramaIdiomaExamenDiaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PROGIEM_PROGIED_ProgramaIdiomaExamenDetalleId", nullable = true, insertable = false, updatable = false)
    private Integer examenDetalleId;

    //Idioma
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGIEM_PAMOD_ModalidadId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PAMOD_ModalidadId")
    private PAModalidad modalidad;

    @Column(name = "PROGIEM_PAMOD_ModalidadId", nullable = true, insertable = true, updatable = true)
    private Integer modalidadlId;

    @Column(name = "PROGIEM_Dias", length = 50, nullable = false, insertable = true, updatable = true)
    private String dias;

    @OneToOne
    @JoinColumn(name = "PROGIEM_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PROGIEM_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PROGIEM_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExamenDetalleId() {
        return examenDetalleId;
    }

    public void setExamenDetalleId(Integer examenDetalleId) {
        this.examenDetalleId = examenDetalleId;
    }

    public PAModalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(PAModalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Integer getModalidadlId() {
        return modalidadlId;
    }

    public void setModalidadlId(Integer modalidadlId) {
        this.modalidadlId = modalidadlId;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public Integer getModificadoPorId() {
        return modificadoPorId;
    }

    public void setModificadoPorId(Integer modificadoPorId) {
        this.modificadoPorId = modificadoPorId;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
