package com.pixvs.spring.models;

import com.pixvs.main.models.Usuario;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "UsuariosRecuperaciones")
public class UsuarioRecuperacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USUR_RecuperacionId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "USUR_USU_UsuarioId", referencedColumnName = "USU_UsuarioId", updatable = false)
    private Usuario usuario;

    @Column(name = "USUR_Token", nullable = false, updatable = false)
    private String token;

    @Column(name = "USUR_FechaSolicitud", nullable = false, updatable = false)
    private Date fechaSolicitud;

    @Column(name = "USUR_FechaExpiracion", nullable = false, updatable = false)
    private Date fechaExpiracion;

    @Column(name = "USUR_CMM_EstatusId", nullable = false)
    private Integer estatus;

    @UpdateTimestamp
    @Column(name = "USUR_FechaModificacion", nullable = true, insertable = true, updatable = true)
    private Date fechaUltimaModificacion;

    public UsuarioRecuperacion() {
    }

    public UsuarioRecuperacion(Integer usuarioId, String token) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        this.setUsuario(usuario);
        this.setToken(token);
        this.fechaExpiracion = new Timestamp(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000));
        this.setEstatus(ControlesMaestrosMultiples.CMM_USUR_EstatusUsuarioRecuperacion.SOLICITADO);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Date getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }
}
