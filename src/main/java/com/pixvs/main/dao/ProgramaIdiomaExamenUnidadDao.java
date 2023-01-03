package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaIdiomaExamenModalidad;
import com.pixvs.main.models.ProgramaIdiomaExamenUnidad;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ProgramaIdiomaExamenUnidadDao extends CrudRepository<ProgramaIdiomaExamenUnidad, String> {
    void deleteByExamenDetalleId(Integer examenDetalleId);
}