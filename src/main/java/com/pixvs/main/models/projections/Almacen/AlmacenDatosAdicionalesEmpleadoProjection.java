package com.pixvs.main.models.projections.Almacen;

public interface AlmacenDatosAdicionalesEmpleadoProjection {

    Integer getSucursalId();

    String getSucursalCodigo();

    String getSucursalNombre();

    String getSucursalPrefijo();

    Integer getAlmacenId();

    String getAlmacenCodigo();

    String getAlmacenNombre();

    boolean isSeleccionado();

    int getOrden();
}
