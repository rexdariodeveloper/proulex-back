package com.pixvs.main.models.projections.Requisicion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Requisicion;
import com.pixvs.main.models.projections.Almacen.AlmacenComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by Angel Daniel Hernández Silva on 21/10/2020.
 */
@Projection(types = {Requisicion.class})
public interface RequisicionListadoProjection {

    Integer getId();
    String getCodigo();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFecha();
    DepartamentoComboProjection getDepartamento();
    @Value("#{target.almacen.sucursal}")
    SucursalComboProjection getSucursal();
    AlmacenComboProjection getAlmacen();
    UsuarioComboProjection getCreadoPor();
    ControlMaestroMultipleComboProjection getEstadoRequisicion();

}