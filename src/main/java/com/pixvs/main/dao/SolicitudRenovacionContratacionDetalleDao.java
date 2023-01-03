package com.pixvs.main.dao;

import com.pixvs.main.models.SolicitudRenovacionContratacionDetalle;
import org.springframework.data.repository.CrudRepository;

public interface SolicitudRenovacionContratacionDetalleDao extends CrudRepository<SolicitudRenovacionContratacionDetalle, String> {
    boolean existsByEmpleadoIdAndEstatusId(Integer empleadoId, Integer estatusId);
}
