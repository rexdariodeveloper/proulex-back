package com.pixvs.main.models.projections.Almacen;

import com.pixvs.main.models.Almacen;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
@Projection(types = {Almacen.class})
public interface AlmacenComboProjection {

    Integer getId();

    String getCodigoAlmacen();

    String getNombre();

    Boolean getEsCedi();

    SucursalComboProjection getSucursal();

    @Value("#{" +
            "   (target.mismaDireccionSucursal != null && target.mismaDireccionSucursal)" +
            "       ? (target.sucursal.domicilio + ', ' + target.sucursal.colonia + ', ' + target.sucursal.ciudad + ', ' + (target.sucursal.estado == null ? '' : target.sucursal.estado.nombre) + ', ' + (target.sucursal.pais == null ? '' : target.sucursal.pais.nombre))" +
            "       : (" +
            "           (target.mismaDireccionCliente != null && target.mismaDireccionCliente)" +
            "               ? (target.cliente.domicilio + ', ' + target.cliente.colonia + ', ' + target.cliente.ciudad + ', ' + (target.cliente.estado == null ? '' : target.cliente.estado.nombre) + ', ' + (target.cliente.pais == null ? '' : target.cliente.pais.nombre))" +
            "               : (target.domicilio + ', ' + target.colonia + ', ' + target.ciudad + ', ' + (target.estado == null ? '' : target.estado.nombre) + ', ' + (target.pais == null ? '' : target.pais.nombre))" +
            "       )" +
            "}")
    String getDomicilio();
    @Value("#{" +
            "   (target.mismaDireccionSucursal != null && target.mismaDireccionSucursal)" +
            "       ? target.sucursal.cp" +
            "       : (" +
            "           (target.mismaDireccionCliente != null && target.mismaDireccionCliente)" +
            "           ? target.cliente.cp" +
            "           : target.cp" +
            "       )" +
            "}")
    String getCp();

    Boolean getActivo();
}
