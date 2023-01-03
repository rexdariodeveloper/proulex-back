package com.pixvs.spring.models;

import com.pixvs.main.models.Usuario;

import javax.persistence.*;

@Entity
@Table(name = "AlertasConfigEtapaAprobadores")
public class AlertaConfigEtapaAprobadores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACEA_AlertaEtapaAprobadorId",nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "ACEA_Orden", nullable = false, insertable = true, updatable = true)
    private Integer orden;

    @Column(name = "ACEA_USU_AprobadorId", nullable = true, insertable = true, updatable = true)
    private Integer aprobadorId;

    @Column(name = "ACEA_DEP_DepartamentoId", nullable = true, insertable = true, updatable = true)
    private Integer departamentoId;

   @Column(name = "ACEA_ACE_AlertaConfiguracionEtapaId", nullable = false, insertable = false, updatable = false)
    private Integer etapaId;

    @OneToOne
    @JoinColumn(name = "ACEA_USU_AprobadorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario aprobador;

    @Column(name = "ACEA_Activo", nullable = true, insertable = true, updatable = true)
    private Boolean activo;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }

    public Integer getAprobadorId() { return aprobadorId; }
    public void setAprobadorId(Integer aprobadorId) { this.aprobadorId = aprobadorId; }

    public Usuario getAprobador() { return aprobador; }
    public void setAprobador(Usuario aprobador) { this.aprobador = aprobador; }

    public Integer getDepartamentoId() { return departamentoId;  }
    public void setDepartamentoId(Integer departamentoId) {  this.departamentoId = departamentoId; }

    public Boolean getActivo() { return activo;}
    public void setActivo(Boolean activo) { this.activo = activo;}

    public Integer getEtapaId() {return etapaId;}
    public void setEtapaId(Integer etapaId) { this.etapaId = etapaId;}
}

