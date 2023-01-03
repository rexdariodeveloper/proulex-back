package com.pixvs.main.models.projections.ProgramaGrupoIncompany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Empleado;
import com.pixvs.main.models.ProgramaGrupoIncompany;
import com.pixvs.main.models.projections.Cliente.ClienteComboProjection;
import com.pixvs.main.models.projections.Cliente.ClienteEditarProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.ProgramaGrupoEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyArchivo.ProgramaGrupoIncompanyArchivoEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyClaseCancelada.ProgramaGrupoIncompanyClaseCanceladaEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyClaseReprogramada.ProgramaGrupoIncompanyClaseReprogramadaEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyCriterioEvaluacion.ProgramaGrupoIncompanyCriterioEvaluacionEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyGrupo.ProgramaGrupoIncompanyGrupoEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyHorario.ProgramaGrupoIncompanyHorarioEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyMaterial.ProgramaGrupoIncompanyMaterialEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaGrupoIncompany.class})
public interface ProgramaGrupoIncompanyEditarProjection {


    Integer getId();

    String getCodigo();

    SucursalComboProjection getSucursal();

    ClienteComboProjection getCliente();

    Boolean getActivo();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    List<ProgramaGrupoEditarProjection> getGrupos();

    List<ProgramaGrupoIncompanyArchivoEditarProjection> getArchivos();
}