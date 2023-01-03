package com.pixvs.main.models;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "ProgramasGruposExamenes")
public class ProgramaGrupoExamen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROGRUE_ProgramaIdiomaExamenId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PROGRUE_PROGRU_GrupoId", nullable = true, insertable = false, updatable = false)
    private Integer grupoId;

    @Column(name = "PROGRUE_Nombre",length = 100,nullable = true, insertable = true, updatable = true)
    private String nombre;

    @Column(name = "PROGRUE_Porcentaje", nullable = true, insertable = true, updatable = true)
    private BigDecimal porcentaje;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "PROGRUED_PROGRUE_ProgramaGrupoExamenId", referencedColumnName = "PROGRUE_ProgramaIdiomaExamenId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaGrupoExamenDetalle> detalles;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getGrupoId() { return grupoId; }
    public void setGrupoId(Integer grupoId) { this.grupoId = grupoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getPorcentaje() { return porcentaje; }
    public void setPorcentaje(BigDecimal porcentaje) { this.porcentaje = porcentaje; }

    public List<ProgramaGrupoExamenDetalle> getDetalles() { return detalles; }
    public void setDetalles(List<ProgramaGrupoExamenDetalle> detalles) { this.detalles = detalles; }
}
