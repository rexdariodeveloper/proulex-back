package com.pixvs.log;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by David Arroyo Sánchez on 15/04/2020.
 */
@Entity
@Table(name = "LogsTipos")
public class LogTipo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOGT_LogTipoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "LOGT_Nombre", length = 150, nullable = false, insertable = true, updatable = true)
    private String nombre;

    @Column(name = "LOGT_Icono", length = 50, nullable = false, insertable = true, updatable = true)
    private String icono;

    @Column(name = "LOGT_ColorFondo", length = 15, nullable = false, insertable = true, updatable = true)
    private String colorFondo;

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

    public String getColorFondo() {
        return colorFondo;
    }

    public void setColorFondo(String colorFondo) {
        this.colorFondo = colorFondo;
    }
}
