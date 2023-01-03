package com.pixvs.main.models.projections.Almacen;

import com.pixvs.main.models.Almacen;

import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.Estado.EstadoComboProjection;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
@Projection(types = {Almacen.class})
public interface AlmacenComboDomicilioProjection {

    Integer getId();
    String getCodigoAlmacen();
    String getNombre();
    Boolean getEsCedi();
    SucursalComboProjection getSucursal();
    @Value("#{(target.mismaDireccionSucursal != null && target.mismaDireccionSucursal == true)" +
            "   ? target.sucursal.domicilio" +
            "   : (((target.mismaDireccionCliente != null && target.mismaDireccionCliente == true))" +
            "       ? target.cliente.domicilio" +
            "       : target.domicilio" +
            "   ) }")
    String getDomicilio();
    @Value("#{(target.mismaDireccionSucursal != null && target.mismaDireccionSucursal == true)" +
            "   ? target.sucursal.colonia" +
            "   : (((target.mismaDireccionCliente != null && target.mismaDireccionCliente == true))" +
            "       ? target.cliente.colonia" +
            "       : target.colonia" +
            "   ) }")
    String getColonia();
    @Value("#{(target.mismaDireccionSucursal != null && target.mismaDireccionSucursal == true)" +
            "   ? target.sucursal.cp" +
            "   : (((target.mismaDireccionCliente != null && target.mismaDireccionCliente == true))" +
            "       ? target.cp.colonia" +
            "       : target.cp" +
            "   ) }")
    String getCp();
    @Value("#{(target.mismaDireccionSucursal != null && target.mismaDireccionSucursal == true)" +
            "   ? target.sucursal.ciudad" +
            "   : (((target.mismaDireccionCliente != null && target.mismaDireccionCliente == true))" +
            "       ? target.ciudad.colonia" +
            "       : target.ciudad" +
            "   ) }")
    String getCiudad();
    @Value("#{(target.mismaDireccionSucursal != null && target.mismaDireccionSucursal == true)" +
            "   ? target.sucursal.estado" +
            "   : (((target.mismaDireccionCliente != null && target.mismaDireccionCliente == true))" +
            "       ? target.estado.colonia" +
            "       : target.estado" +
            "   ) }")
    EstadoComboProjection getEstado();
    @Value("#{" +
            "   (target.mismaDireccionSucursal != null && target.mismaDireccionSucursal == true)" +
            "       ? target.sucursal.pais" +
            "       : (" +
            "           (target.mismaDireccionCliente != null && target.mismaDireccionCliente == true)" +
            "               ? target.cliente.pais" +
            "               : target.pais" +
            "       )" +
            "}")
    PaisComboProjection getPais();
    @Value("#{(target.mismaDireccionSucursal != null && target.mismaDireccionSucursal == true)" +
            "   ? (target.sucursal.domicilio + ', ' + target.sucursal.colonia + ', ' + target.sucursal.cp + ', ' + target.sucursal.ciudad + ', ' + (target.sucursal.estado != null ? target.sucursal.estado.nombre : '') + ', ' + (target.sucursal.pais != null ? target.sucursal.pais.nombre : ''))" +
            "   : (((target.mismaDireccionCliente != null && target.mismaDireccionCliente == true))" +
            "       ? (target.cliente.domicilio + ', ' + target.cliente.colonia + ', ' + target.cliente.cp + ', ' + target.cliente.ciudad + ', ' + (target.cliente.estado != null ? target.cliente.estado.nombre : '') + ', ' + (target.cliente.pais != null ? target.cliente.pais.nombre : ''))" +
            "       : (target.domicilio + ', ' + target.colonia + ', ' + target.cp + ', ' + target.ciudad + ', ' + (target.estado != null ? target.estado.nombre : '') + ', ' + (target.pais != null ? target.pais.nombre : ''))" +
            "   ) }")
    String getDireccionCompleta();

}
