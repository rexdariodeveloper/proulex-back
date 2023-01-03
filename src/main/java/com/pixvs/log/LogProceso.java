package com.pixvs.log;

import javax.persistence.*;

/**
 * Created by David Arroyo Sánchez on 15/04/2020.
 */
@Entity
@Table(name = "LogsProcesos")
public class LogProceso {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOGP_LogProcesoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "LOGP_Nombre", length = 150, nullable = false, insertable = true, updatable = true)
    private String nombre;

    @Column(name = "LOGP_Icono", length = 50, nullable = false, insertable = true, updatable = true)
    private String icono;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }
}
