package com.pixvs.main.models;

import com.pixvs.spring.models.Archivo;

import javax.persistence.*;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "ProgramasGruposIncompanyArchivos")
public class ProgramaGrupoIncompanyArchivo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PGINCA_ProgramaIncompanyArchivoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PGINCA_PGINC_ProgramaIncompanyId", nullable = false, insertable = false, updatable = false)
    private Integer programaIncompanyId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINCA_ARC_ArchivoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo archivo;

    @Column(name = "PGINCA_ARC_ArchivoId", nullable = false, insertable = true, updatable = true)
    private Integer archivoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProgramaIncompanyId() {
        return programaIncompanyId;
    }

    public void setProgramaIncompanyId(Integer programaIncompanyId) {
        this.programaIncompanyId = programaIncompanyId;
    }

    public Archivo getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

    public Integer getArchivoId() {
        return archivoId;
    }

    public void setArchivoId(Integer archivoId) {
        this.archivoId = archivoId;
    }
}
