package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
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
@Table(name = "ProgramasGruposIncompany")
public class ProgramaGrupoIncompany {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PGINC_ProgramaIncompanyId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PGINC_Codigo", length = 30, nullable = false, insertable = true, updatable = true)
    private String codigo;

    //Sucursal
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINC_SUC_SucursalId", nullable = false, insertable = false, updatable = false, referencedColumnName = "SUC_SucursalId")
    private Sucursal sucursal;

    @Column(name = "PGINC_SUC_SucursalId", nullable = false, insertable = true, updatable = true)
    private Integer sucursalId;


    //Cliente
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINC_CLI_ClienteId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CLI_ClienteId")
    private Cliente cliente;

    @Column(name = "PGINC_CLI_ClienteId", nullable = false, insertable = true, updatable = true)
    private Integer clienteId;

    @Column(name = "PGINC_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @CreationTimestamp
    @Column(name = "PGINC_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "PGINC_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "PGINC_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "PGINC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PGINC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PGINC_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "PROGRU_PGINCG_ProgramaIncompanyId", referencedColumnName = "PGINC_ProgramaIncompanyId", nullable = true, insertable = true, updatable = true)
    private List<ProgramaGrupo> grupos;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "PGINCA_PGINC_ProgramaIncompanyId", referencedColumnName = "PGINC_ProgramaIncompanyId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaGrupoIncompanyArchivo> archivos;

    @Transient
    private Integer alumnos;

    @Transient
    private String curso;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Integer sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
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

    public List<ProgramaGrupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<ProgramaGrupo> grupos) {
        this.grupos = grupos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<ProgramaGrupoIncompanyArchivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<ProgramaGrupoIncompanyArchivo> archivos) {
        this.archivos = archivos;
    }

    public Integer getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(Integer alumnos) {
        this.alumnos = alumnos;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
