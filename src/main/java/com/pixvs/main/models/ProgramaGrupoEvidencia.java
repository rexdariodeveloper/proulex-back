package com.pixvs.main.models;

import com.pixvs.spring.models.Archivo;

import javax.persistence.*;

@Entity
@Table(name = "ProgramasGruposEvidencias")
public class ProgramaGrupoEvidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROGRUE_ProgramaGrupoEvidenciaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PROGRUE_PROGRU_GrupoId", nullable = false, insertable = false, updatable = false)
    private int grupoId;

    @Column(name = "PROGRUE_ARC_ArchivoId", nullable = false)
    private int archivoId;

    @OneToOne
    @JoinColumn(name = "PROGRUE_ARC_ArchivoId", insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo archivo;

    @Column(name = "PROGRUE_Nombre", nullable = false)
    private String nombre;

    @Column(name = "PROGRUE_Vigente", nullable = false)
    private boolean vigente;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(int grupoId) {
        this.grupoId = grupoId;
    }

    public int getArchivoId() {
        return archivoId;
    }

    public void setArchivoId(int archivoId) {
        this.archivoId = archivoId;
    }

    public Archivo getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isVigente() {
        return vigente;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }
}
