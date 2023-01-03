package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaMetaDetalle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 07/05/2021.
 */
public interface ProgramaMetaDetalleDao extends CrudRepository<ProgramaMetaDetalle, String> {

    // JsonStr
    @Query(nativeQuery = true,value = "SELECT metas FROM [dbo].[VW_EditarJson_ProgramasMetasDetalles] WHERE programaMetaId = :programaMetaId")
    String findJsonStrEditarByProgramaMetaId(@Param("programaMetaId") Integer programaMetaId);

}
