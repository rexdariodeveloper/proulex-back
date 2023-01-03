package com.pixvs.main.models;

import com.pixvs.spring.models.ControlMaestroMultiple;

import javax.persistence.*;

/**
 * Created by Angel Daniel Hernández Silva on 28/05/2021.
 */
@Entity
@Table(name = "AlumnosContactos")
public class AlumnoContacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALUC_AlumnoContactoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "ALUC_ALU_AlumnoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ALU_AlumnoId")
    private Alumno alumno;

    @Column(name = "ALUC_ALU_AlumnoId", nullable = false, insertable = false, updatable = false)
    private Integer alumnoId;

    @Column(name = "ALUC_Nombre", length = 50, nullable = false, insertable = true, updatable = true)
    private String nombre;

    @Column(name = "ALUC_PrimerApellido", length = 50, nullable = false, insertable = true, updatable = true)
    private String primerApellido;

    @Column(name = "ALUC_SegundoApellido", length = 50, nullable = true, insertable = true, updatable = true)
    private String segundoApellido;

    @OneToOne
    @JoinColumn(name = "ALUC_CMM_ParentescoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple parentesco;

    @Column(name = "ALUC_CMM_ParentescoId", nullable = false, insertable = true, updatable = false)
    private Integer parentescoId;

    @Column(name = "ALUC_TelefonoFijo", length = 50, nullable = true, insertable = true, updatable = true)
    private String telefonoFijo;

    @Column(name = "ALUC_TelefonoMovil", length = 50, nullable = true, insertable = true, updatable = true)
    private String telefonoMovil;

    @Column(name = "ALUC_TelefonoTrabajo", length = 50, nullable = true, insertable = true, updatable = true)
    private String telefonoTrabajo;

    @Column(name = "ALUC_TelefonoTrabajoExtension", length = 10, nullable = true, insertable = true, updatable = true)
    private String telefonoTrabajoExtension;

    @Column(name = "ALUC_TelefonoMensajeriaInstantanea", length = 50, nullable = true, insertable = true, updatable = true)
    private String telefonoMensajeriaInstantanea;

    @Column(name = "ALUC_CorreoElectronico", length = 50, nullable = false, insertable = true, updatable = true)
    private String correoElectronico;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Integer getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Integer alumnoId) {
        this.alumnoId = alumnoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public ControlMaestroMultiple getParentesco() {
        return parentesco;
    }

    public void setParentesco(ControlMaestroMultiple parentesco) {
        this.parentesco = parentesco;
    }

    public Integer getParentescoId() {
        return parentescoId;
    }

    public void setParentescoId(Integer parentescoId) {
        this.parentescoId = parentescoId;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getTelefonoTrabajo() {
        return telefonoTrabajo;
    }

    public void setTelefonoTrabajo(String telefonoTrabajo) {
        this.telefonoTrabajo = telefonoTrabajo;
    }

    public String getTelefonoTrabajoExtension() {
        return telefonoTrabajoExtension;
    }

    public void setTelefonoTrabajoExtension(String telefonoTrabajoExtension) {
        this.telefonoTrabajoExtension = telefonoTrabajoExtension;
    }

    public String getTelefonoMensajeriaInstantanea() {
        return telefonoMensajeriaInstantanea;
    }

    public void setTelefonoMensajeriaInstantanea(String telefonoMensajeriaInstantanea) {
        this.telefonoMensajeriaInstantanea = telefonoMensajeriaInstantanea;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
}
