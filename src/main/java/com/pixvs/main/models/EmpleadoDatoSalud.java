package com.pixvs.main.models;

import com.pixvs.spring.models.ControlMaestroMultiple;

import javax.persistence.*;

/**
 * Created by Ing. Rene Carrillo on 22/03/2022.
 */
@Entity
@Table(name = "EmpleadosDatosSalud")
public class EmpleadoDatoSalud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPDS_EmpleadoDatoSaludId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "EMPDS_EMP_EmpleadoId", nullable = true, insertable = false, updatable = false)
    private Integer empleadoId;

    @Column(name = "EMPDS_Alergias", nullable = true, insertable = true, updatable = true)
    private String alergias;

    @Column(name = "EMPDS_Padecimientos", nullable = true, insertable = true, updatable = true)
    private String padecimientos;

    @Column(name = "EMPDS_InformacionAdicional", nullable = true, insertable = true, updatable = true)
    private String informacionAdicional;

    // Tipo Sangre
    @Column(name = "EMPDS_CMM_TipoSangreId", nullable = false, insertable = true, updatable = true)
    private Integer tipoSangreId;

    @OneToOne
    @JoinColumn(name = "EMPDS_CMM_TipoSangreId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoSangre;

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

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getPadecimientos() {
        return padecimientos;
    }

    public void setPadecimientos(String padecimientos) {
        this.padecimientos = padecimientos;
    }

    public String getInformacionAdicional() {
        return informacionAdicional;
    }

    public void setInformacionAdicional(String informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    public Integer getTipoSangreId() {
        return tipoSangreId;
    }

    public void setTipoSangreId(Integer tipoSangreId) {
        this.tipoSangreId = tipoSangreId;
    }

    public ControlMaestroMultiple getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(ControlMaestroMultiple tipoSangre) {
        this.tipoSangre = tipoSangre;
    }
}
