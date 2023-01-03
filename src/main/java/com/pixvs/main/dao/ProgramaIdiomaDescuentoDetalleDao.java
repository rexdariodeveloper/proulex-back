package com.pixvs.main.dao;

import com.pixvs.main.models.Programa;
import com.pixvs.main.models.ProgramaIdiomaDescuentoDetalle;
import com.pixvs.main.models.projections.Programa.ProgramaCalcularDiasProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaEditarProjection;
import com.pixvs.main.models.projections.Programa.ProgramaListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ProgramaIdiomaDescuentoDetalleDao extends CrudRepository<ProgramaIdiomaDescuentoDetalle, String> {
    void deleteByDescuentoDetalleId(Integer descuentoDetalleId);
}