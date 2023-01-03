package com.pixvs.main.dao;

import com.pixvs.main.models.BecaUDG;
import com.pixvs.main.models.projections.BecaUDG.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 15/08/2021.
 */
public interface BecaUDGDao extends CrudRepository<BecaUDG, String> {

    // Modelo
    BecaUDG findById(Integer id);

    // ConsultaProjection
    BecaUDGConsultaProjection findProjectedConsultaByCodigoBeca(String codigoBeca);

    //Find Editar
    BecaUDGEditarProjection findEditarById(Integer id);

    // AplicadaProjection
    @Query("" +
            "SELECT \n" +
            "   becu.id AS id, \n" +
            "   ins.grupo.sucursal.nombre AS sede, \n" +
            "   ins.grupo.nivel AS nivel, \n" +
            "   ins.grupo.modalidadHorario.nombre AS horario, \n" +
            "   ins.grupo.grupo AS grupo, \n" +
            "   CONCAT(ins.grupo.programaIdioma.programa.codigo,' ',ins.grupo.programaIdioma.idioma.valor) AS curso, \n" +
            "   ins.grupo.fechaInicio AS fechaInicio, \n" +
            "   ins.grupo.fechaFin AS fechaFin, \n" +
            "   ins.fechaCreacion AS fechaAplicacion, \n" +
            "   ins.ordenVentaDetalle.ordenVenta.codigo AS folio \n" +
            "FROM BecaUDG becu \n" +
            "INNER JOIN Inscripcion ins ON ins.becaUDGId = becu.id \n" +
            "WHERE becu.codigoBeca LIKE CONCAT('%',:codigoBeca,'%') \n" +
            "")
    List<BecaUDGAplicadaProjection> findProjectedAplicadaByCodigoBecaLike(@Param("codigoBeca") String codigoBeca);
    @Query("" +
            "SELECT \n" +
            "   becu.id AS id, \n" +
            "   ins.grupo.sucursal.nombre AS sede, \n" +
            "   ins.grupo.nivel AS nivel, \n" +
            "   ins.grupo.modalidadHorario.nombre AS horario, \n" +
            "   ins.grupo.grupo AS grupo, \n" +
            "   CONCAT(ins.grupo.programaIdioma.programa.codigo,' ',ins.grupo.programaIdioma.idioma.valor) AS curso, \n" +
            "   ins.grupo.fechaInicio AS fechaInicio, \n" +
            "   ins.grupo.fechaFin AS fechaFin, \n" +
            "   ins.fechaCreacion AS fechaAplicacion, \n" +
            "   ins.ordenVentaDetalle.ordenVenta.codigo AS folio \n" +
            "FROM BecaUDG becu \n" +
            "INNER JOIN Inscripcion ins ON ins.becaUDGId = becu.id \n" +
            "WHERE \n" +
            "   ins.fechaCreacion >= :fechaInicio \n" +
            "   AND ins.fechaCreacion < :fechaFin \n" +
            "")
    List<BecaUDGAplicadaProjection> findProjectedAplicadaByFechaAplicacionGreaterThanEqualAndFechaAplicacionLessThan(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    // ListadoProjection
    @Query(nativeQuery = true, value = "\n" +
            "SELECT *  FROM [dbo].[VW_Listado_BecasUDG] \n" +
            "WHERE CONCAT(COALESCE(codigoProulex,''),'|',codigoBeca,'|',codigoEmpleado,'|',nombre,' ',primerApellido,' ' + segundoApellido,' ',nombre,'|',curso,'|',modalidad) LIKE CONCAT('%',:filtro,'%') \n" +
            "ORDER BY id \n" +
            "OFFSET :offset ROWS FETCH NEXT :top ROWS ONLY \n" +
            "")
    List<BecaUDGListadoProjection> findProjectedListadoByFiltro(@Param("filtro") String filtro, @Param("offset") Integer offset, @Param("top") Integer top);
    @Query(nativeQuery = true, value = "\n" +
            "SELECT *  FROM [dbo].[VW_Listado_BecasUDG] \n" +
            "WHERE \n" +
            "   alumnoId = :alumnoId \n" +
            "   AND programaId = :programaId \n" +
            "   AND idiomaId = :idiomaId \n" +
            "   AND modalidadId = :modalidadId \n" +
            "   AND nivel = :nivel \n" +
            "   AND tipoId IN (2000580,2000581) \n" +
            "ORDER BY id \n" +
            "OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY \n" +
            "")
    BecaUDGListadoProjection findProjectedListadoByAlumnoIdAndProgramaIdiomaIdAndModalidadIdAndNivelAndTipoSindicato(
            @Param("alumnoId") Integer alumnoId,
            @Param("programaId") Integer programaId,
            @Param("idiomaId") Integer idiomaId,
            @Param("modalidadId") Integer modalidadId,
            @Param("nivel") Integer nivel
    );
    @Query(nativeQuery = true, value = "\n" +
            "SELECT *  FROM [dbo].[VW_Listado_BecasUDG] \n" +
            "WHERE \n" +
            "   alumnoId = :alumnoId \n" +
            "   AND programaId = :programaId \n" +
            "   AND idiomaId = :idiomaId \n" +
            "   AND modalidadId = :modalidadId \n" +
            "   AND nivel = :nivel \n" +
            "   AND tipoId IN (2000582) \n" +
            "ORDER BY id \n" +
            "OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY \n" +
            "")
    BecaUDGListadoProjection findProjectedListadoByAlumnoIdAndProgramaIdiomaIdAndModalidadIdAndNivelAndTipoProulex(
            @Param("alumnoId") Integer alumnoId,
            @Param("programaId") Integer programaId,
            @Param("idiomaId") Integer idiomaId,
            @Param("modalidadId") Integer modalidadId,
            @Param("nivel") Integer nivel
    );
    @Query(nativeQuery = true, value = "\n" +
            "SELECT *  FROM [dbo].[VW_Listado_BecasUDG] \n" +
            "WHERE \n" +
            "   tipoId IN :tiposIds \n" +
            "   AND CONCAT(COALESCE(codigoProulex,''),'|',codigoBeca,'|',codigoEmpleado,'|',nombre,' ',primerApellido,' ' + segundoApellido,' ',nombre,'|',curso,'|',modalidad) LIKE CONCAT('%',:filtro,'%') \n" +
            "ORDER BY id \n" +
            "OFFSET :offset ROWS FETCH NEXT :top ROWS ONLY OPTION(RECOMPILE) \n" +
            "")
    List<BecaUDGListadoProjection> findProjectedListadoByTipoIdInAndFiltro(@Param("tiposIds") List<Integer> tiposIds, @Param("filtro") String filtro, @Param("offset") Integer offset, @Param("top") Integer top);

    @Query(nativeQuery = true, value =
            "SELECT *\n" +
            "FROM VW_RPT_BecasUDG\n" +
            "WHERE (COALESCE(:clientesId, 0) = 0 OR clienteId IN(:clientesId))\n" +
            "       AND (COALESCE(:sedesId, 0) = 0 OR sedeId IN(:sedesId))\n" +
            "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))\n" +
            "       AND (COALESCE(:fechas, '') = '' OR fechaFormato IN (:fechas))\n" +
            "ORDER BY codigoPlic, codigoBecaUdg\n" +
            "OPTION(RECOMPILE)")
    List<BecaUDGReporteProjection> getReporteBecas(@Param("clientesId") List<Integer> clientesId,
                                                   @Param("sedesId") List<Integer> sedesId,
                                                   @Param("modalidadesId") List<Integer> modalidadesId,
                                                   @Param("fechas") List<String> fechas);

    @Query(nativeQuery = true, value =
            "SELECT DISTINCT\n" +
            "       sedeId,\n" +
            "       clienteId,\n" +
            "       cienPorciento,\n" +
            "       CONCAT(sede, ' ', cliente, ' ', CASE WHEN cienPorciento = 1 THEN descuentoUDG ELSE '' END) AS nombre\n" +
            "FROM VW_RPT_BecasUDG\n" +
            "WHERE (COALESCE(:clientesId, 0) = 0 OR clienteId IN(:clientesId))\n" +
            "       AND (COALESCE(:sedesId, 0) = 0 OR sedeId IN(:sedesId))\n" +
            "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))\n" +
            "       AND (COALESCE(:fechas, '') = '' OR fechaFormato IN (:fechas))\n" +
            "OPTION(RECOMPILE)")
    List<BecaUDGReporteFiltrosProjection> getReporteBecasFiltros(@Param("clientesId") List<Integer> clientesId,
                                                                 @Param("sedesId") List<Integer> sedesId,
                                                                 @Param("modalidadesId") List<Integer> modalidadesId,
                                                                 @Param("fechas") List<String> fechas);

    List<BecaUDG> findAllByFechaExpiracionIsLessThanAndEstatusId(@Param("fecha") Date fecha, @Param("estatusId") Integer estatusId);

    @Query(nativeQuery = true, value = "\n" +
            "Select DISTINCT PROGRU_FechaInicio from ProgramasGrupos\n" +
            "WHERE PROGRU_CMM_EstatusId <> 2000622 \n" +
            "AND PROGRU_PAC_ProgramacionAcademicaComercialId =:programacionId \n" +
            "AND PROGRU_SUC_SucursalId IN (:idsSucursal)\n" +
            "AND PROGRU_PAMOD_ModalidadId IN (:idsModalidades)" +
            "")
    List<String> findFechasBySucursalAndModalidadAndProgramacion(
            @Param("idsSucursal") List<Integer> idsSucursal,
            @Param("programacionId") Integer programacionId,
            @Param("idsModalidades") List<Integer> idsModalidades
    );

    @Modifying
    @Query(nativeQuery = true, value =
            "UPDATE BECAS_ALUMNOS_RH\n" +
            "  SET\n" +
            "      STATUSPLX = :estatus,\n" +
            "      SEDEPLX = :sede,\n" +
            "      NIVELPLX = :nivel,\n" +
            "      HORARIOPLX = :horario,\n" +
            "      GRUPOPLX = :grupo,\n" +
            "      CODCURPLX = :codigo,\n" +
            "      FECHAINIPLX = CASE WHEN :sinGrupo = 1 THEN NULL ELSE :fechaInicio END,\n" +
            "      FECHAFINPLX = CASE WHEN :sinGrupo = 1 THEN NULL ELSE :fechaFin END,\n" +
            "      FECHAAPLICACIONPLX = :fecha,\n" +
            "      FOLIOSIAPPLX = :ov\n" +
            "WHERE ID = :id")
    void actualizarBecaSIAP(@Param("estatus") String estatus,
                            @Param("sede") String sede,
                            @Param("nivel") String nivel,
                            @Param("horario") String horario,
                            @Param("grupo") String grupo,
                            @Param("codigo") String codigo,
                            @Param("fechaInicio") String fechaInicio,
                            @Param("fechaFin") String fechaFin,
                            @Param("fecha") String fecha,
                            @Param("ov") String ov,
                            @Param("id") Integer id,
                            @Param("sinGrupo") boolean sinGrupo);

    @Modifying
    @Query(nativeQuery = true, value =
            "UPDATE rh\n" +
            "  SET\n" +
            "      CODIGOPLX = NULL,\n" +
            "      SEDEPLX = NULL,\n" +
            "      NIVELPLX = NULL,\n" +
            "      HORARIOPLX = NULL,\n" +
            "      GRUPOPLX = NULL,\n" +
            "      CODCURPLX = NULL,\n" +
            "      FECHAINIPLX = NULL,\n" +
            "      FECHAFINPLX = NULL,\n" +
            "      FECHAAPLICACIONPLX = NULL,\n" +
            "      FOLIOSIAPPLX = NULL,\n" +
            "      CALPLX = NULL,\n" +
            "      FECHACALPLX = NULL,\n" +
            "      STATUSPLX = NULL,\n" +
            "      CODIGOESTATUSPLX = NULL\n" +
            "FROM BECAS_ALUMNOS_RH AS rh\n" +
            "     INNER JOIN BecasUDG AS becas ON rh.ID = becas.BECU_SIAPId AND becas.BECU_BecaId = :becaId")
    int limpiarDatosBecaRH(@Param("becaId") Integer becaId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE BECAS_ALUMNOS_RH " +
            "SET SEDEPLX = SUC_CodigoSucursal, " +
            "    NIVELPLX = PROGRU_Nivel, " +
            "    HORARIOPLX = REPLACE(PAMODH_Horario,' ',''), " +
            "    GRUPOPLX = CAST(PROGRU_Grupo AS NVARCHAR(2)), " +
            "    CODCURPLX = PROGRU_Codigo, " +
            "    FECHAINIPLX = PROGRU_FechaInicio, " +
            "    FECHAFINPLX = PROGRU_FechaFin, " +
            "    CALPLX = FLOOR(ALUG_CalificacionFinal), " +
            "    FECHACALPLX = GETDATE() " +
            "FROM BECAS_ALUMNOS_RH " +
            "INNER JOIN BecasUDG ON ID = BECU_SIAPId " +
            "INNER JOIN Inscripciones ON INS_BECU_BecaId = BECU_BecaId " +
            "INNER JOIN ProgramasGrupos ON INS_PROGRU_GrupoId = PROGRU_GrupoId " +
            "INNER JOIN PAModalidadesHorarios ON PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId " +
            "INNER JOIN Sucursales ON PROGRU_SUC_SucursalId = SUC_SucursalId " +
            "INNER JOIN AlumnosGrupos ON INS_InscripcionId = ALUG_INS_InscripcionId " +
            "WHERE BECU_CMM_EstatusId IN (2000570, 2000571) AND INS_InscripcionId = :inscripcionId")
    Integer finalizarBecaRHByInscripcionId(@Param("inscripcionId") Integer inscripcionId);
}