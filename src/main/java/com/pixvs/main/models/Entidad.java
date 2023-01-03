package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "Entidades")
public class Entidad {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENT_EntidadId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "ENT_Codigo", length = 20, nullable = false, insertable = true, updatable = true)
    private String codigo;

    @Column(name = "ENT_Prefijo", length = 20, nullable = false, insertable = true, updatable = true)
    private String prefijo;

    @Column(name = "ENT_Nombre", length = 150, nullable = false, insertable = true, updatable = true)
    private String nombre;

    @Column(name = "ENT_RazonSocial", length = 150, nullable = true, insertable = true, updatable = true)
    private String razonSocial;

    @Column(name = "ENT_NombreComercial", length = 150, nullable = true, insertable = true, updatable = true)
    private String nombreComercial;

    //Director
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "ENT_EMP_Director", nullable = true, insertable = false, updatable = false, referencedColumnName = "EMP_EmpleadoId")
    private Empleado director;

    @Column(name = "ENT_EMP_Director")
    private Integer directorId;

    //Coordinador
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "ENT_EMP_Coordinador", nullable = true, insertable = false, updatable = false, referencedColumnName = "EMP_EmpleadoId")
    private Empleado coordinador;

    @Column(name = "ENT_EMP_Coordinador")
    private Integer coordinadorId;

    //Jefe Unidad AF
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "ENT_EMP_JefeUnidadAF", nullable = true, insertable = false, updatable = false, referencedColumnName = "EMP_EmpleadoId")
    private Empleado jefeUnidadAF;

    @Column(name = "ENT_EMP_JefeUnidadAF")
    private Integer jefeUnidadAFId;

    @Column(name = "ENT_Domicilio", length = 250, nullable = true, insertable = true, updatable = true)
    private String domicilio;

    @Column(name = "ENT_Colonia", length = 250, nullable = true, insertable = true, updatable = true)
    private String colonia;

    @Column(name = "ENT_CP", length = 10, nullable = true, insertable = true, updatable = true)
    private String cp;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "ENT_PAI_PaisId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAI_PaisId")
    private Pais pais;

    @Column(name = "ENT_PAI_PaisId", nullable = true, insertable = true, updatable = true)
    private Integer paisId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "ENT_EST_EstadoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "EST_EstadoId")
    private Estado estado;

    @Column(name = "ENT_EST_EstadoId", nullable = true, insertable = true, updatable = false)
    private Integer estadoId;

    @Column(name = "ENT_Ciudad", length = 100, nullable = true, insertable = true, updatable = true)
    private String ciudad;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "ENT_EntidadDependienteId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ENT_EntidadId")
    private Entidad entidadIndependiente;

    @Column(name = "ENT_EntidadDependienteId", nullable = true, insertable = true, updatable = true)
    private Integer entidadIndependienteId;

    @Column(name = "ENT_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @Column(name = "ENT_AplicaSedes", nullable = false, insertable = true, updatable = true)
    private Boolean aplicaSedes;

    @CreationTimestamp
    @Column(name = "ENT_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "ENT_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "ENT_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "ENT_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "ENT_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "ENT_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "ENTC_ENT_EntidadId", referencedColumnName = "ENT_EntidadId", nullable = true, insertable = true, updatable = true)
    private List<EntidadContrato> contratos;

    @Column(name = "ENT_Nombre_BD", length = 100, nullable = true, insertable = true, updatable = true)
    private String nombreBd;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public Empleado getDirector() {
        return director;
    }

    public void setDirector(Empleado director) {
        this.director = director;
    }

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    public Empleado getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(Empleado coordinador) {
        this.coordinador = coordinador;
    }

    public Integer getCoordinadorId() {
        return coordinadorId;
    }

    public void setCoordinadorId(Integer coordinadorId) {
        this.coordinadorId = coordinadorId;
    }

    public Empleado getJefeUnidadAF() {
        return jefeUnidadAF;
    }

    public void setJefeUnidadAF(Empleado jefeUnidadAF) {
        this.jefeUnidadAF = jefeUnidadAF;
    }

    public Integer getJefeUnidadAFId() {
        return jefeUnidadAFId;
    }

    public void setJefeUnidadAFId(Integer jefeUnidadAFId) {
        this.jefeUnidadAFId = jefeUnidadAFId;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Integer getPaisId() {
        return paisId;
    }

    public void setPaisId(Integer paisId) {
        this.paisId = paisId;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Integer getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Integer getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(Integer creadoPorId) {
        this.creadoPorId = creadoPorId;
    }

    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public Integer getModificadoPorId() {
        return modificadoPorId;
    }

    public void setModificadoPorId(Integer modificadoPorId) {
        this.modificadoPorId = modificadoPorId;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Entidad getEntidadIndependiente() {
        return entidadIndependiente;
    }

    public void setEntidadIndependiente(Entidad entidadIndependiente) {
        this.entidadIndependiente = entidadIndependiente;
    }

    public Integer getEntidadIndependienteId() {
        return entidadIndependienteId;
    }

    public void setEntidadIndependienteId(Integer entidadIndependienteId) {
        this.entidadIndependienteId = entidadIndependienteId;
    }

    public List<EntidadContrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<EntidadContrato> contratos) {
        this.contratos = contratos;
    }

    public Boolean getAplicaSedes() {
        return aplicaSedes;
    }

    public void setAplicaSedes(Boolean aplicaSedes) {
        this.aplicaSedes = aplicaSedes;
    }

    public String getNombreBd() {
        return nombreBd;
    }

    public void setNombreBd(String nombreBd) {
        this.nombreBd = nombreBd;
    }
}