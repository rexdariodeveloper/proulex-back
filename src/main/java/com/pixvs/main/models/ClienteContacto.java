package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ClientesContactos")
public class ClienteContacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIC_ClienteContactoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "CLIC_CLI_ClienteId", insertable = false, updatable = false, nullable = false)
    private int clienteId;

    @OneToOne
    @JoinColumn(name = "CLIC_CLI_ClienteId", insertable = false, updatable = false, referencedColumnName = "CLI_ClienteId")
    private Cliente cliente;

    @Column(name = "CLIC_Nombre", nullable = false)
    private String nombre;

    @Column(name = "CLIC_PrimerApellido", nullable = false)
    private String primerApellido;

    @Column(name = "CLIC_SegundoApellido", nullable = false)
    private String segundoApellido;

    @Column(name = "CLIC_Departamento", nullable = false)
    private String departamento;

    @Column(name = "CLIC_Telefono", nullable = false)
    private String telefono;

    @Column(name = "CLIC_Extension")
    private String extension;

    @Column(name = "CLIC_CorreoElectronico", nullable = false)
    private String correoElectronico;

    @Column(name = "CLIC_HorarioAtencion", nullable = false)
    private String horarioAtencion;

    @Column(name = "CLIC_Predeterminado", nullable = false)
    private boolean predeterminado;

    @Column(name = "CLIC_Activo")
    private boolean activo;

    @CreationTimestamp
    @Column(name = "CLIC_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "CLIC_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @Column(name = "CLIC_USU_CreadoPorId", updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "CLIC_USU_CreadoPorId", insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "CLIC_USU_ModificadoPorId")
    private Integer modificadoPorId;

    @OneToOne
    @JoinColumn(name = "CLIC_USU_ModificadoPorId", insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(String horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    public boolean isPredeterminado() {
        return predeterminado;
    }

    public void setPredeterminado(boolean predeterminado) {
        this.predeterminado = predeterminado;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Integer getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(Integer creadoPorId) {
        this.creadoPorId = creadoPorId;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Integer getModificadoPorId() {
        return modificadoPorId;
    }

    public void setModificadoPorId(Integer modificadoPorId) {
        this.modificadoPorId = modificadoPorId;
    }

    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }
}
