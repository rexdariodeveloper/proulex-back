package com.pixvs.main.dao;

import com.pixvs.main.models.Usuario;
import com.pixvs.main.models.projections.Usuario.UsuarioDatosAdicionalesProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 29/07/2020.
 */
public interface UsuarioDatosAdicionalesDao extends CrudRepository<Usuario, String> {

    UsuarioDatosAdicionalesProjection findProjectedDatosAdicionalesById(Integer id);

    @Query("" +
            "SELECT u.id \n" +
            "FROM Usuario u \n" +
            "INNER JOIN u.departamentosPermisos d \n" +
            "WHERE d.id IN :departamentosIds")
    List<Integer> getUsuariosIdsByDepartamentoIdIn(@Param("departamentosIds") List<Integer> departamentosIds);

    @Query("\n" +
            "SELECT CASE WHEN COUNT(cmm) >= 1 THEN true ELSE false END \n" +
            "FROM ControlMaestroMultipleDatosAdicionales cmm \n" +
            "INNER JOIN cmm.responsables usu \n" +
            "WHERE usu.id = :usuarioId AND cmm.control = 'CMM_ALU_CentrosUniversitarios' \n")
    Boolean getUsuarioEsResponsableCentrosUniversitarios(@Param("usuarioId") Integer usuarioId);
    @Query("\n" +
            "SELECT CASE WHEN COUNT(cmm) >= 1 THEN true ELSE false END \n" +
            "FROM ControlMaestroMultipleDatosAdicionales cmm \n" +
            "INNER JOIN cmm.responsables usu \n" +
            "WHERE usu.id = :usuarioId AND cmm.control = 'CMM_ALU_Preparatorias' \n")
    Boolean getUsuarioEsResponsablePreparatorias(@Param("usuarioId") Integer usuarioId);

}
