package com.pixvs.main.models.projections.OrdenVentaCancelacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.OrdenVentaCancelacion;
import com.pixvs.main.models.projections.OrdenVentaCancelacionArchivo.OrdenVentaCancelacionArchivoEditarProjection;
import com.pixvs.main.models.projections.OrdenVentaCancelacionDetalle.OrdenVentaCancelacionDetalleEditarProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboSimpleProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 04/04/2022.
 */
@Projection(types = {OrdenVentaCancelacion.class})
public interface OrdenVentaCancelacionEditarProjection {

    Integer getId();

    String getCodigo();

    ControlMaestroMultipleComboSimpleProjection getTipoMovimiento();

    Date getFechaDevolucion();

    ControlMaestroMultipleComboSimpleProjection getMotivoDevolucion();

    Date getFechaCancelacion();

    ControlMaestroMultipleComboSimpleProjection getMotivoCancelacion();

    String getBanco();

    String getBeneficiario();

    String getNumeroCuenta();

    String getClabe();

    String getTelefonoContacto();

    BigDecimal getImporteReembolsar();

    ControlMaestroMultipleComboSimpleProjection getTipoCancelacion();

    ControlMaestroMultipleComboSimpleProjection getEstatus();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    List<OrdenVentaCancelacionDetalleEditarProjection> getDetalles();

    List<OrdenVentaCancelacionArchivoEditarProjection> getArchivos();
}