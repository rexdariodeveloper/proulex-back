package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaGrupoIncompany;
import com.pixvs.main.models.ProgramaIdioma;
import com.pixvs.main.models.projections.ProgramaGrupoIncompany.ProgramaGrupoIncompanyEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompany.ProgramaGrupoIncompanyListadoProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ProgramaGrupoIncompanyDao extends CrudRepository<ProgramaGrupoIncompany, String> {
    ProgramaGrupoIncompanyEditarProjection findProjectedEditarById(Integer id);
    ProgramaGrupoIncompany findById(Integer id);
    //List<ProgramaIdiomaComboProjection> findAllByActivoIsTrueOrderByCodigo();
    //List<ProgramaGrupoIncompanyListadoProjection> findProjectedListadoAllBy();
    void deleteById(Integer id);

    @Modifying
    @Query(value = "UPDATE ProgramasGruposIncompany SET PGINC_Activo = :activo WHERE PGINC_ProgramaIncompanyId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

    @Query(nativeQuery = true, value ="Select CASE\n" +
            "WHEN (Select EMPDNL_EmpresaDiaNoLaboralId from EmpresaDiasNoLaborales where EMPDNL_Fecha = dbo.getDiaAplicacion(:modalidad,:diasAplica,:fechaInicio)) is not null then DATEADD(day,1,dbo.getDiaAplicacion(:modalidad,:diasAplica,:fechaInicio))\n" +
            "WHEN (Select EMPDNLF_EmpresaDiaNoLaboralId from EmpresaDiasNoLaboralesFijos where EMPDNLF_Dia=DAY(dbo.getDiaAplicacion(:modalidad,:diasAplica,:fechaInicio)) and EMPDNLF_Mes=MONTH(dbo.getDiaAplicacion(:modalidad,:diasAplica,:fechaInicio))) is not null then DATEADD(day,1,dbo.getDiaAplicacion(:modalidad,:diasAplica,:fechaInicio))\n" +
            "ELSE dbo.getDiaAplicacion(:modalidad,:diasAplica,:fechaInicio)\n" +
            "END")
    Date getFechaAplicacion(@Param("fechaInicio") Date fechaInicio, @Param("diasAplica") Integer diasAplica, @Param("modalidad") Integer modalidad);

    @Query(nativeQuery = true, value ="Select CASE\n" +
            "WHEN (Select EMPDNL_EmpresaDiaNoLaboralId from EmpresaDiasNoLaborales where EMPDNL_Fecha = dbo.getDiaAplicacionPersonalizada(:lunes,:martes,:miercoles,:jueves,:viernes,:sabado,:diasAplica,:fechaInicio)) is not null then DATEADD(day,1,dbo.getDiaAplicacionPersonalizada(:lunes,:martes,:miercoles,:jueves,:viernes,:sabado,:diasAplica,:fechaInicio))\n" +
            "WHEN (Select EMPDNLF_EmpresaDiaNoLaboralId from EmpresaDiasNoLaboralesFijos where EMPDNLF_Dia=DAY(dbo.getDiaAplicacionPersonalizada(:lunes,:martes,:miercoles,:jueves,:viernes,:sabado,:diasAplica,:fechaInicio)) and EMPDNLF_Mes=MONTH(dbo.getDiaAplicacionPersonalizada(:lunes,:martes,:miercoles,:jueves,:viernes,:sabado,:diasAplica,:fechaInicio))) is not null then DATEADD(day,1,dbo.getDiaAplicacionPersonalizada(:lunes,:martes,:miercoles,:jueves,:viernes,:sabado,:diasAplica,:fechaInicio))\n" +
            "ELSE dbo.getDiaAplicacionPersonalizada(:lunes,:martes,:miercoles,:jueves,:viernes,:sabado,:diasAplica,:fechaInicio)\n" +
            "END")
    Date getFechaAplicacionPersonalizado(@Param("fechaInicio") Date fechaInicio, @Param("diasAplica") Integer diasAplica, @Param("lunes") Boolean lunes, @Param("martes") Boolean martes, @Param("miercoles") Boolean miercoles, @Param("jueves") Boolean jueves, @Param("viernes") Boolean viernes, @Param("sabado") Boolean sabado );

    @Query(nativeQuery = true, value ="SELECT\n" +
            "STRING_AGG(CMM_Valor,', ') as curso,\n" +
            "PGINC_ProgramaIncompanyId as id,\n" +
            "PGINC_Codigo as codigo,\n" +
            "CLI_Nombre as cliente,\n" +
            "SUC_Nombre as sucursal,\n" +
            "(Select Count(*) FROM ProgramasGrupos WHERE PROGRU_PGINCG_ProgramaIncompanyId = PGINC_ProgramaIncompanyId AND PROGRU_CMM_EstatusId = 2000620 ) as grupos,\n" +
            "null as alumnos,\n" +
            "PGINC_Activo as activo\n" +
            "FROM \n" +
            "(\n" +
            "SELECT CMM_Valor,PGINC_ProgramaIncompanyId,PGINC_Codigo,CLI_Nombre,SUC_Nombre,PROGRU_PGINCG_ProgramaIncompanyId,PGINC_Activo FROM\n" +
            "ProgramasGrupos\n" +
            "INNER JOIN ProgramasGruposIncompany on PGINC_ProgramaIncompanyId = PROGRU_PGINCG_ProgramaIncompanyId\n" +
            "INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId\n" +
            "INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = PROGI_CMM_Idioma\n" +
            "INNER JOIN Clientes ON CLI_ClienteId = PGINC_CLI_ClienteId\n" +
            "INNER JOIN Sucursales ON SUC_SucursalId = PGINC_SUC_SucursalId\n" +
            "GROUP BY CMM_Valor,PGINC_ProgramaIncompanyId,PGINC_Codigo,CLI_Nombre,SUC_Nombre,PROGRU_PGINCG_ProgramaIncompanyId,PGINC_Activo\n" +
            ") as T GROUP BY T.PGINC_ProgramaIncompanyId,T.PGINC_Codigo,T.CLI_Nombre,T.SUC_Nombre,T.PROGRU_PGINCG_ProgramaIncompanyId,T.PGINC_Activo")
    List<ProgramaGrupoIncompanyListadoProjection> getAllListadosGrupos();

    @Query("\n" +
            "SELECT pginc.id \n" +
            "FROM ProgramaGrupoIncompany pginc \n" +
            "WHERE \n" +
            "   pginc.activo = true \n" +
            "   AND pginc.sucursalId = :sucursalId \n" +
            "   AND pginc.clienteId = :clienteId \n" +
            "")
    List<Integer> getIdsBySucursalIdAndClienteId(@Param("sucursalId") Integer sucursalId, @Param("clienteId") Integer clienteId);


}