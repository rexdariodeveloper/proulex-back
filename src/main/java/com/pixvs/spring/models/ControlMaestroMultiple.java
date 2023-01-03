package com.pixvs.spring.models;


import com.pixvs.main.models.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ControlesMaestrosMultiples")
public class ControlMaestroMultiple extends AbstractControlMaestroMultiple {

    public ControlMaestroMultiple() {
    }

    public ControlMaestroMultiple(Integer id) {
        this.setId(id);
    }

}
