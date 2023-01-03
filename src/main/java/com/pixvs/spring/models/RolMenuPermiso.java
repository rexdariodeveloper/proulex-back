package com.pixvs.spring.models;

import javax.persistence.*;

@Entity
@Table(name = "RolesMenusPermisos")
public class RolMenuPermiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLMPP_RolMenuPermisoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "ROLMPP_ROL_RolId", referencedColumnName = "ROL_RolId", nullable = false, insertable = false, updatable = false)
    private Rol rol;

    @Column(name = "ROLMPP_ROL_RolId", nullable = false)
    private Integer rolId;

    @OneToOne
    @JoinColumn(name = "ROLMPP_MPP_MenuPrincipalPermisoId", referencedColumnName = "MPP_MenuPrincipalPermisoId", nullable = false, insertable = false, updatable = false)
    private MenuPrincipalPermiso permiso;

    @Column(name = "ROLMPP_MPP_MenuPrincipalPermisoId", nullable = false)
    private Integer permisoId;

    @Transient
    private String nombrePermiso;

    /** Getters & Setters **/

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public Integer getRolId() { return rolId; }
    public void setRolId(Integer rolId) { this.rolId = rolId; }

    public MenuPrincipalPermiso getPermiso() { return permiso; }
    public void setPermiso(MenuPrincipalPermiso permiso) { this.permiso = permiso; }

    public Integer getPermisoId() { return permisoId; }
    public void setPermisoId(Integer permisoId) { this.permisoId = permisoId; }

    public String getNombrePermiso() { return nombrePermiso; }
    public void setNombrePermiso(String nombrePermiso) { this.nombrePermiso = nombrePermiso; }
}

