package com.pixvs.main.models;

import com.pixvs.spring.models.AbstractControlMaestroMultiple;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ControlesMaestrosMultiples")
public class ControlMaestroMultipleDatosAdicionales extends AbstractControlMaestroMultiple {

    @ManyToMany
    @JoinTable(name = "ControlesMaestrosMultiplesResponsables", joinColumns = {@JoinColumn(name = "CUR_CMM_ControlId")}, inverseJoinColumns = {@JoinColumn(name = "CUR_USU_ResponsableId")})
    private Set<Usuario> responsables = new HashSet<>();

    public Set<Usuario> getResponsables() {
        return responsables;
    }

    public void setResponsables(Set<Usuario> responsables) {
        this.responsables = responsables;
    }
}
