package com.pixvs.main.models.projections.OrdenCompra;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.OrdenCompra;
import com.pixvs.main.models.projections.Almacen.AlmacenComboProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorComboProjection;
import com.pixvs.main.models.projections.RequisicionPartida.RequisicionPartidaOCProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.OrdenCompraDetalle.OrdenCompraDetalleEditarProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorComboVistaProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by Angel Daniel Hernández Silva on 11/08/2020.
 */
@Projection(types = {OrdenCompra.class})
public interface OrdenCompraEditarProjection {


    Integer getId();

    String getCodigo();

    ProveedorComboProjection getProveedor();
    Integer getProveedorId();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaOC();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaRequerida();

    String getDireccionOC();

    String getRemitirA();

    AlmacenComboProjection getRecepcionArticulosAlmacen();
    Integer getRecepcionArticulosAlmacenId();

    MonedaComboProjection getMoneda();
    Integer getMonedaId();

    Integer getTerminoPago();

    DepartamentoComboProjection getDepartamento();
    Integer getDepartamentoId();

    @Value("#{target.descuento == null ? null : (target.descuento * 100)}")
    BigDecimal getDescuento();

    String getComentario();

    ControlMaestroMultipleComboProjection getEstatus();
    Integer getEstatusId();

    UsuarioComboProjection getCreadoPor();

    UsuarioComboProjection getAutorizadoPor();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    List<OrdenCompraDetalleEditarProjection> getDetalles();

}