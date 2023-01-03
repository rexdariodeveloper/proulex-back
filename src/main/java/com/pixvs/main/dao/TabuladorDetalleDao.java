package com.pixvs.main.dao;

import com.pixvs.main.models.Tabulador;
import com.pixvs.main.models.TabuladorDetalle;
import com.pixvs.main.models.projections.Tabulador.TabuladorComboProjection;
import com.pixvs.main.models.projections.Tabulador.TabuladorEditarProjection;
import com.pixvs.main.models.projections.Tabulador.TabuladorListadoProjection;
import com.pixvs.main.models.projections.TabuladorDetalle.TabuladorDetalleEmpleadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
public interface TabuladorDetalleDao extends CrudRepository<TabuladorDetalle, String> {
    TabuladorDetalle findByTabuladorIdAndProfesorCategoriaIdAndActivoIsTrue(Integer tabuladorId, Integer categoriaId);

    @Query(nativeQuery = true, value = "Select DISTINCT TOP 1 TAB_TabuladorId as id, TABD_Sueldo as sueldo, PAPC_Categoria as categoria from Tabuladores\n" +
            "INNER JOIN TabuladoresCursos on TABC_TAB_TabuladorId = TAB_TabuladorId\n" +
            "INNER JOIN TabuladoresDetalles on TABD_TAB_TabuladorId = TABC_TAB_TabuladorId\n" +
            "INNER JOIN PAProfesoresCategorias on TABD_PAPC_ProfesorCategoriaId = PAPC_ProfesorCategoriaId\n" +
            "INNER JOIN EmpleadosCategorias on EMPCA_PAPC_ProfesorCategoriaId = TABD_PAPC_ProfesorCategoriaId \n" +
            "INNER JOIN ProgramasGrupos on PROGRU_PAMODH_PAModalidadHorarioId = TABC_PAMODH_PAModalidadHorarioId\n" +
            "WHERE TABD_Activo=1 AND EMPCA_Activo=1 AND TABC_Activo=1 \n" +
            "AND TABC_PROGI_ProgramaIdiomaId=:idCurso AND PROGRU_GrupoId=:idGrupo AND EMPCA_EMP_EmpleadoId=:idEmpleado")
    TabuladorDetalleEmpleadoProjection findDatosEmpleadoSueldo(@Param("idCurso") Integer idCurso, @Param("idEmpleado") Integer idEmpleado, @Param("idGrupo") Integer idGrupo);

    @Query(nativeQuery = true, value = "Select DISTINCT TOP 1 TAB_TabuladorId as id, TABD_Sueldo as sueldo, PAPC_Categoria as categoria from Tabuladores\n" +
            "INNER JOIN TabuladoresCursos on TABC_TAB_TabuladorId = TAB_TabuladorId\n" +
            "INNER JOIN TabuladoresDetalles on TABD_TAB_TabuladorId = TABC_TAB_TabuladorId\n" +
            "INNER JOIN PAProfesoresCategorias on TABD_PAPC_ProfesorCategoriaId = PAPC_ProfesorCategoriaId\n" +
            "INNER JOIN EmpleadosCategorias on EMPCA_PAPC_ProfesorCategoriaId = TABD_PAPC_ProfesorCategoriaId \n" +
            "INNER JOIN ProgramasGrupos on PROGRU_PAMODH_PAModalidadHorarioId = TABC_PAMODH_PAModalidadHorarioId\n" +
            "WHERE TABD_Activo=1 AND EMPCA_Activo=1 AND TABC_Activo=1 \n" +
            "AND TABC_PROGI_ProgramaIdiomaId=:idCurso AND PROGRU_PAMODH_PAModalidadHorarioId=:idHorario AND EMPCA_EMP_EmpleadoId=:idEmpleado")
    TabuladorDetalleEmpleadoProjection findDatosEmpleadoSueldoIncompany(@Param("idCurso") Integer idCurso, @Param("idEmpleado") Integer idEmpleado, @Param("idHorario") Integer idHorario);

    @Query(nativeQuery = true, value = "SELECT TOP 1 TAB.TAB_TabuladorId as id, TABD.TABD_Sueldo as sueldo\n" +
            "FROM TabuladoresDetalles TABD\n" +
            "LEFT JOIN Tabuladores TAB on TAB.TAB_TabuladorId = TABD.TABD_TAB_TabuladorId\n" +
            "LEFT JOIN PAProfesoresCategorias PAP on PAP.PAPC_ProfesorCategoriaId = TABD.TABD_PAPC_ProfesorCategoriaId\n" +
            "LEFT JOIN EmpleadosCategorias EMPCA on EMPCA.EMPCA_PAPC_ProfesorCategoriaId = PAP.PAPC_ProfesorCategoriaId\n" +
            "LEFT JOIN Empleados EMP on EMP.EMP_EmpleadoId = EMPCA.EMPCA_EMP_EmpleadoId\n" +
            "LEFT JOIN DeduccionesPercepciones DEPE on DEPE.DEDPER_TAB_TabuladorId = TAB.TAB_TabuladorId\n" +
            "WHERE EMP.EMP_EmpleadoId=:idEmpleado AND DEPE.DEDPER_DeduccionPercepcionId=:idDeduccionPercepcion")
    TabuladorDetalleEmpleadoProjection findDatosEmpleadoSueldoByDeduccionPercepcion(@Param("idEmpleado") Integer idEmpleado, @Param("idDeduccionPercepcion") Integer idDeduccionPercepcion);
}
