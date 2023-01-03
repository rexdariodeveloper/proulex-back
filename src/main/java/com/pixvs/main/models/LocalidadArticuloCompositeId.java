package com.pixvs.main.models;

/**
 * Autor: Benjamin Osorio
 * url: https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/composite-primary-key.html
 * Clave compuesta
 */

import java.io.Serializable;

public class LocalidadArticuloCompositeId implements Serializable {
    private Integer localidadId;
    private Integer articuloId;

    public LocalidadArticuloCompositeId() {}

    public LocalidadArticuloCompositeId(int localidadId, int articuloId) {}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalidadArticuloCompositeId taskId1 = (LocalidadArticuloCompositeId) o;
        if (localidadId != taskId1.localidadId) return false;
        return articuloId == taskId1.articuloId;
    }

}
