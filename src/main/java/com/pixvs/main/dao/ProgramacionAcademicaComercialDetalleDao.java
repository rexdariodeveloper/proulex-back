package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramacionAcademicaComercialDetalle;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercialDetalle.ProgramacionAcademicaComercialDetalleMetaListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 11/05/2021.
 */
public interface ProgramacionAcademicaComercialDetalleDao extends CrudRepository<ProgramacionAcademicaComercialDetalle, String> {

    // Modelo
    List<ProgramacionAcademicaComercialDetalle> findByProgramacionAcademicaComercialIdAndPaModalidadIdAndFechaInicio(@Param("programacionAcademicaComercialId") Integer programacionAcademicaComercialId, @Param("paModalidadId") Integer paModalidadId, @Param("fechaInicio") Date fechaInicio);

    // MetaListadoProjection
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[VW_MetaListado_ProgramacionAcademicaComercialDetalle] \n" +
            "WHERE programacionAcademicaComercialId = :programacionAcademicaComercialId")
    List<ProgramacionAcademicaComercialDetalleMetaListadoProjection> findProjectedMetaListadoAllByProgramacionAcademicaComercialId(@Param("programacionAcademicaComercialId") Integer programacionAcademicaComercialId);

}
