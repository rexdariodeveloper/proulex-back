package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
@Entity
@Table(name = "TabuladoresDetalles")
public class TabuladorDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TABD_TabuladorDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "TABD_TAB_TabuladorId", nullable = false, insertable = false, updatable = false)
    private Integer tabuladorId;

    @OneToOne
    @JoinColumn(name = "TABD_PAPC_ProfesorCategoriaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PAPC_ProfesorCategoriaId")
    private PAProfesorCategoria profesorCategoria;

    @Column(name = "TABD_PAPC_ProfesorCategoriaId", nullable = false, insertable = true, updatable = true)
    private Integer profesorCategoriaId;

    @Column(name = "TABD_Sueldo", nullable = false, insertable = true, updatable = true)
    private BigDecimal sueldo;

    @Column(name = "TABD_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTabuladorId() {
        return tabuladorId;
    }

    public void setTabuladorId(Integer tabuladorId) {
        this.tabuladorId = tabuladorId;
    }

    public PAProfesorCategoria getProfesorCategoria() {
        return profesorCategoria;
    }

    public void setProfesorCategoria(PAProfesorCategoria profesorCategoria) {
        this.profesorCategoria = profesorCategoria;
    }

    public Integer getProfesorCategoriaId() {
        return profesorCategoriaId;
    }

    public void setProfesorCategoriaId(Integer profesorCategoriaId) {
        this.profesorCategoriaId = profesorCategoriaId;
    }

    public BigDecimal getSueldo() {
        return sueldo;
    }

    public void setSueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
