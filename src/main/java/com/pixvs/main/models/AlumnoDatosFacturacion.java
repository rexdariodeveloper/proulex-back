package com.pixvs.main.models;

import javax.persistence.*;

@Entity
@Table(name = "AlumnosDatosFacturacion")
public class AlumnoDatosFacturacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADF_AlumnoDatosFacturacionId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "ADF_ALU_AlumnoId", nullable = false, insertable = false, updatable = false)
    private Integer alumnoId;

    @OneToOne
    @JoinColumn(name = "ADF_ALU_AlumnoId", insertable = false, updatable = false, referencedColumnName = "ALU_AlumnoId")
    private Alumno alumno;

    @Column(name = "ADF_DF_DatosFacturacionId", nullable = false, insertable = false, updatable = false)
    private Integer datosFacturacionId;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "ADF_DF_DatosFacturacionId", referencedColumnName = "DF_DatosFacturacionId", nullable = false)
    private DatosFacturacion datosFacturacion;

    @Column(name = "ADF_Predeterminado", nullable = false)
    private boolean predeterminado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Integer alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
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

    public boolean isPredeterminado() {
        return predeterminado;
    }

    public void setPredeterminado(boolean predeterminado) {
        this.predeterminado = predeterminado;
    }
}