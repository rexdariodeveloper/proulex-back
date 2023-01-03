package com.pixvs.spring.models.projections.Departamento;

public interface DepartamentoDatosAdicionalesEmpleadoProjection {

    Integer getId();

    Integer getDepartamentoPadre();

    String getPrefijo();

    String getNombre();

    boolean isSelected();

    String getOrden();
}