package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.AbstractPuesto;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Benjamin Osorio on 25/09/2022.
 */
@Entity
@Table(name = "Puestos")
public class Puesto extends AbstractPuesto {

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="PUEHR_PUE_PuestoId", referencedColumnName = "PUE_PuestoId", nullable = false)
    private List<PuestoHabilidadResponsabilidad> habilidadesResponsabilidades;

    public List<PuestoHabilidadResponsabilidad> getHabilidadesResponsabilidades() { return habilidadesResponsabilidades;}

    public void setHabilidadesResponsabilidades(List<PuestoHabilidadResponsabilidad> habilidadesResponsabilidades) { this.habilidadesResponsabilidades = habilidadesResponsabilidades; }
}
