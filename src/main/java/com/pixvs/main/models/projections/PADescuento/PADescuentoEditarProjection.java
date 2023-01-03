package com.pixvs.main.models.projections.PADescuento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.PADescuento;
import com.pixvs.main.models.ProgramaIdiomaCertificacion;
import com.pixvs.main.models.projections.Cliente.ClienteComboProjection;
import com.pixvs.main.models.projections.PADescuentoArticulo.PADescuentoArticuloEditarProjection;
import com.pixvs.main.models.projections.PADescuentoDetalle.PADescuentoDetalleEditarProjection;
import com.pixvs.main.models.projections.PADescuentoSucursal.PADescuentoSucursalEditarProjection;
import com.pixvs.main.models.projections.PADescuentoUsuarioAutorizado.PADescuentoUsuarioAutorizadoEditarProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.PAModalidadHorario.PAModalidadHorarioComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaDescuentoDetalle.ProgramaIdiomaDescuentoDetalleEditarProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {PADescuento.class})
public interface PADescuentoEditarProjection {


    Integer getId();

    String getCodigo();

    String getConcepto();

    Integer getPorcentajeDescuento();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaFin();

    Boolean getDescuentoRelacionadoCliente();

    Boolean getActivo();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    List<PADescuentoDetalleEditarProjection> getDetalles();

    List<PADescuentoArticuloEditarProjection> getArticulos();

    List<PADescuentoSucursalEditarProjection> getSucursales();

    List<PADescuentoUsuarioAutorizadoEditarProjection> getUsuariosAutorizados();

    ClienteComboProjection getCliente();

    Integer getPrioridadEvaluacion();

    ControlMaestroMultipleComboProjection getTipo();
}