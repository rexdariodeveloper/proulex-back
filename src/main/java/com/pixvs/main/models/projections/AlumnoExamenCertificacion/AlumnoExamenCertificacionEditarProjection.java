package com.pixvs.main.models.projections.AlumnoExamenCertificacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.*;
import com.pixvs.main.models.projections.Alumno.AlumnoComboProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloComboSimpleProjection;
import com.pixvs.main.models.projections.OrdenVenta.OrdenVentaSimpleProjection;
import com.pixvs.main.models.projections.OrdenVentaDetalle.OrdenVentaDetalleHistorialPVResumenProjection;
import com.pixvs.main.models.projections.OrdenVentaDetalle.OrdenVentaDetalleReferenciaOVProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboProjection;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboSimpleProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {AlumnoExamenCertificacion.class})
public interface AlumnoExamenCertificacionEditarProjection {

    public Integer getId();
    public AlumnoComboProjection getAlumno();
    public Integer getAlumnoId();
    public ArticuloComboSimpleProjection getArticulo();
    public Integer getArticuloId();
    public OrdenVentaDetalleReferenciaOVProjection getOrdenVentaDetalle();
    public Integer getOrdenVentaDetalleId();
    public ControlMaestroMultipleComboSimpleProjection getTipo();
    public Integer getTipoId();
    public ProgramaIdiomaComboProjection getCurso();
    public Integer getCursoId();
    public ControlMaestroMultipleComboSimpleProjection getEstatus();
    public Integer getEstatusId();
    public BigDecimal getCalificacion();
    public Integer getNivel();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    public Date getFechaCreacion();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    public Date getFechaModificacion();

}
