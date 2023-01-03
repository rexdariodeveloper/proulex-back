package com.pixvs.main.models.projections.PrecioIncompany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.PrecioIncompany;
import com.pixvs.main.models.PrecioIncompanyDetalle;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.PrecioIncompanyDetalle.PrecioIncompanyDetalleEditarProjection;
import com.pixvs.main.models.projections.PrecioIncompanySucursal.PrecioIncompanySucursalEditarProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {PrecioIncompany.class})
public interface PrecioIncompanyEditarProjection {


    Integer getId();

    String getCodigo();

    String getNombre();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaFin();

    ControlMaestroMultipleComboProjection getEstatus();

    Boolean getIndeterminado();

    /*@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaUltimaModificacion();*/

    //UsuarioComboProjection getModificadoPor();

    List<PrecioIncompanyDetalleEditarProjection> getDetalles();

    List<PrecioIncompanySucursalEditarProjection> getSucursales();
}