package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "PAModalidadesHorarios")
public class PAModalidadHorario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAMODH_PAModalidadHorarioId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PAMODH_PAMOD_ModalidadId", nullable = true, insertable = false, updatable = false)
    private Integer modalidadId;

    @Column(name = "PAMODH_Horario", length = 100, nullable = false, insertable = true, updatable = true)
    private String nombre;

    @Column(name = "PAMODH_HoraInicio", nullable = true, insertable = true, updatable = true)
    private Time horaInicio;

    @Column(name = "PAMODH_HoraFin", nullable = true, insertable = true, updatable = true)
    private Time horaFin;

    @Column(name = "PAMODH_CentroPagosId", updatable = false, insertable = false)
    private Integer centroPagosId;

//    @CreationTimestamp
//    @Column(name = "PAMODH_FechaCreacion", nullable = false, insertable = true, updatable = false)
//    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "PAMODH_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PAMODH_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PAMODH_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @Column(name = "PAMODH_Codigo", length = 100, nullable = false, insertable = true, updatable = true)
    private String codigo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModalidadId() {
        return modalidadId;
    }

    public void setModalidadId(Integer modalidadId) {
        this.modalidadId = modalidadId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getCentroPagosId() {
        return centroPagosId;
    }

    public void setCentroPagosId(Integer centroPagosId) {
        this.centroPagosId = centroPagosId;
    }

//    public Date getFechaCreacion() {
//        return fechaCreacion;
//    }
//
//    public void setFechaCreacion(Date fechaCreacion) {
//        this.fechaCreacion = fechaCreacion;
//    }
}
