package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "ProgramasIdiomasDescuentosDetalles")
public class ProgramaIdiomaDescuentoDetalle {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PIDD_DescuentoDetalleCursoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PIDD_PADESCD_DescuentoDetalleId", nullable = true, insertable = false, updatable = false)
    private Integer descuentoDetalleId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PIDD_PROGI_ProgramaIdiomaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PROGI_ProgramaIdiomaId")
    private ProgramaIdioma programaIdioma;

    @Column(name = "PIDD_PROGI_ProgramaIdiomaId", nullable = true, insertable = true, updatable = true)
    private Integer programaIdiomaId;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDescuentoDetalleId() {
        return descuentoDetalleId;
    }

    public void setDescuentoDetalleId(Integer descuentoDetalleId) {
        this.descuentoDetalleId = descuentoDetalleId;
    }

    public ProgramaIdioma getProgramaIdioma() {
        return programaIdioma;
    }

    public void setProgramaIdioma(ProgramaIdioma programaIdioma) {
        this.programaIdioma = programaIdioma;
    }

    public Integer getProgramaIdiomaId() {
        return programaIdiomaId;
    }

    public void setProgramaIdiomaId(Integer programaIdiomaId) {
        this.programaIdiomaId = programaIdiomaId;
    }
}
