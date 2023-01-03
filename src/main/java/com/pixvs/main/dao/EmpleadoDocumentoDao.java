package com.pixvs.main.dao;

import com.pixvs.main.models.EmpleadoDocumento;
import com.pixvs.main.models.projections.EmpleadoDocumento.EmpleadoDocumentoEditarProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Rene Carrillo on 24/03/2022.
 */
public interface EmpleadoDocumentoDao extends CrudRepository<EmpleadoDocumento, String> {
    List<EmpleadoDocumentoEditarProjection> findAllByEmpleadoIdAndActivoIsTrue(Integer empleadoId);

    List<EmpleadoDocumentoEditarProjection> findEmpleadoDocumentoEditarProjectionByEmpleadoIdAndTipoProcesoRHIdAndActivoIsTrue(Integer empleadoId, Integer tipoProcesoRHId);
}
