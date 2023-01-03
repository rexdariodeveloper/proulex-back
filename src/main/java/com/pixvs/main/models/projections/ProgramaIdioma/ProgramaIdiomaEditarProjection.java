package com.pixvs.main.models.projections.ProgramaIdioma;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Programa;
import com.pixvs.main.models.ProgramaIdioma;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacion.ProgramaIdiomaCertificacionEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaLibroMaterial.ProgramaIdiomaLibroMaterialEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaModalidad.ProgramaIdiomaModalidadEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaNivel.ProgramaIdiomaNivelEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaSucursal.ProgramaIdiomaSucursalEditarProjection;
import com.pixvs.main.models.projections.Tabulador.TabuladorComboProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaIdioma.class})
public interface ProgramaIdiomaEditarProjection {


    Integer getId();
    String getCodigo();
    String getNombre();
    Integer getProgramaId();

    ProgramaComboProjection  getPrograma();

    ControlMaestroMultipleComboProjection getIdioma();
    ControlMaestroMultipleComboProjection getTipoWorkshop();
    Integer getTipoWorkshopId();
    Integer getHorasTotales();

    Integer getNumeroNiveles();

    BigDecimal getCalificacionMinima();

    String getMcer();

    UnidadMedidaComboProjection getUnidadMedida();

    String getClave();

    Boolean getExamenEvaluacion();

    String getDescripcion();

    Boolean getAgruparListadosPreciosPorTipoGrupo();

    Boolean getActivo();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    List<ProgramaIdiomaCertificacionEditarProjection> getCertificaciones();

    List<ProgramaIdiomaLibroMaterialEditarProjection> getLibrosMateriales();

    List<ProgramaIdiomaModalidadEditarProjection> getModalidades();

    List<ProgramaIdiomaSucursalEditarProjection> getSucursales();

    ControlMaestroMultipleComboProjection getPlataforma();

    Integer getIva();

    BigDecimal getFaltasPermitidas();

    Boolean getIvaExento();

    Integer getIeps();

    Boolean getCuotaFija();

    List<ProgramaIdiomaNivelEditarProjection> getNiveles();

    ControlMaestroMultipleComboProjection getObjetoImpuesto();
}