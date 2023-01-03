package com.pixvs.main.models;

import com.pixvs.spring.models.ControlMaestroMultiple;

import javax.persistence.*;

/**
 * Created by Rene Carrillo on 21/04/2022.
 */
@Entity
@Table(name = "SolicitudesNuevasContratacionesDetalles")
public class SolicitudNuevaContratacionDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SNCD_SolicitudNuevaContratacionDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "SNCD_SNC_SolicitudNuevaContratacionId", nullable = true, insertable = false, updatable = false)
    private Integer solicitudNuevaContratacionId;

    @Column(name = "SNCD_EMP_EmpleadoId", nullable = false, insertable = true, updatable = false)
    private Integer empleadoId;

    @OneToOne
    @JoinColumn(name = "SNCD_EMP_EmpleadoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "EMP_EmpleadoId")
    private Empleado empleado;

    // Estatus
    @Column(name = "SNCD_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @OneToOne
    @JoinColumn(name = "SNCD_CMM_EstatusId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSolicitudNuevaContratacionId() {
        return solicitudNuevaContratacionId;
    }

    public void setSolicitudNuevaContratacionId(Integer solicitudNuevaContratacionId) {
        this.solicitudNuevaContratacionId = solicitudNuevaContratacionId;
    }

    public Integer getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Integer getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
    }

    public ControlMaestroMultiple getEstatus() {
        return estatus;
    }

    public void setEstatus(ControlMaestroMultiple estatus) {
        this.estatus = estatus;
    }
}
