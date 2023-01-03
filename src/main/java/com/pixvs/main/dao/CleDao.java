package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaGrupo;
import org.springframework.data.repository.CrudRepository;

public interface CleDao extends CrudRepository<ProgramaGrupo, String> {
/*
    @Query(nativeQuery = true, value = "\n" +
            "SELECT *  FROM [dbo].[VW_Listado_BecasUDG] \n" +
            "WHERE CONCAT(COALESCE(codigoProulex,''),'|',codigoBeca,'|',codigoEmpleado,'|',nombre,' ',primerApellido,' ' + segundoApellido,' ',nombre,'|',curso,'|',modalidad) LIKE CONCAT('%',:filtro,'%') \n" +
            "ORDER BY id \n" +
            "OFFSET :offset ROWS FETCH NEXT :top ROWS ONLY \n" +
            "")
    List<Object> findProjectedListadoByFiltro(@Param("filtro") String filtro, @Param("offset") Integer offset, @Param("top") Integer top);
*/

}
