package com.pixvs.main.models;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "ProgramasGruposIncompanyMateriales")
public class ProgramaGrupoIncompanyMaterial {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PGINCM_ProgramaIncompanyMaterialId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PGINCM_PROGRU_GrupoId", nullable = false, insertable = false, updatable = false)
    private Integer grupoId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINCM_ART_ArticuloId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ART_ArticuloId")
    private Articulo articulo;

    @Column(name = "PGINCM_ART_ArticuloId", nullable = false, insertable = true, updatable = true)
    private Integer articuloId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Integer getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Integer articuloId) {
        this.articuloId = articuloId;
    }
}
