package com.pixvs.main.dao;

import com.pixvs.spring.models.Departamento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 25/03/2021.
 */
public interface DepartamentoMainDao extends CrudRepository<Departamento, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[fn_getJerarquiaDepartamentosUsuario](:usuarioId)")
    List<Integer> fn_getJerarquiaDepartamentosUsuario(@Param("usuarioId") Integer usuarioId);

}
