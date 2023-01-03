package com.pixvs.main.models;

import com.pixvs.spring.models.AbstractUsuario;
import com.pixvs.spring.models.Departamento;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Usuarios")
public class Usuario extends AbstractUsuario{

    @ManyToMany
    @JoinTable(name = "UsuariosAlmacenes", joinColumns = {@JoinColumn(name = "USUA_USU_UsuarioId")}, inverseJoinColumns = {@JoinColumn(name = "USUA_ALM_AlmacenId")})
    private Set<Almacen> almacenes = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "UsuariosDepartamentos", joinColumns = {@JoinColumn(name = "USUD_USU_UsuarioId")}, inverseJoinColumns = {@JoinColumn(name = "USUD_DEP_DepartamentoId")})
    @JoinColumn()
    private Set<Departamento> departamentosPermisos = new HashSet<>();

    public Set<Almacen> getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(Set<Almacen> almacenes) {
        this.almacenes = almacenes;
    }

    public Set<Departamento> getDepartamentosPermisos() {
        return departamentosPermisos;
    }

    public void setDepartamentosPermisos(Set<Departamento> departamentosPermisos) {
        this.departamentosPermisos = departamentosPermisos;
    }
}

