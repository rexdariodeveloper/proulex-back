package com.pixvs.main.models;

import com.pixvs.spring.models.ControlMaestroMultiple;

import javax.persistence.*;

/**
 * Created by Cesar Hernandez on 01/12/2021.
 */
@Entity
@Table(name = "EmpleadosBeneficiarios")
public class EmpleadoBeneficiario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPBE_EmpleadoBeneficiarioId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "EMPBE_EMP_EmpleadoId", nullable = true, insertable = false, updatable = false)
    private Integer empleadoId;

    @Column(name = "EMPBE_Nombre", nullable = false, insertable = true, updatable = true)
    private String nombre;

    @Column(name = "EMPBE_PrimerApellido", nullable = false, insertable = true, updatable = true)
    private String primerApellido;

    @Column(name = "EMPBE_SegundoApellido", nullable = true, insertable = true, updatable = true)
    private String segundoApellido;

    //Parentesco
    @OneToOne
    @JoinColumn(name = "EMPBE_CMM_ParentescoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple parentesco;

    @Column(name = "EMPBE_CMM_ParentescoId", nullable = false, insertable = true, updatable = true)
    private Integer parentescoId;

    @Column(name = "EMPBE_Porcentaje", nullable = false, insertable = true, updatable = true)
    private Integer porcentaje;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
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

    public Integer getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }
}
