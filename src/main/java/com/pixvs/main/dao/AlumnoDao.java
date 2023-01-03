package com.pixvs.main.dao;

import com.pixvs.main.models.Alumno;
import com.pixvs.main.models.projections.Alumno.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 31/05/2021.
 */
public interface AlumnoDao extends CrudRepository<Alumno, String> {

    // Modelo
    Alumno findById(Integer id);
    Alumno findByCurp(String curp);
    Alumno findByCurpAndIdNot(String curp, Integer id);
    Alumno findByCodigo(String codigo);
    Alumno findByNombreAndPrimerApellidoAndSegundoApellido(String nombre, String primerApellido, String segundoApellido);

    // ListadoProjection
    @Query(nativeQuery = true,value = "SELECT * FROM [dbo].[VW_Listado_Alumnos]")
    List<AlumnoListadoProjection> findProjectedListadoAllBy();

    // EditarProjection
    AlumnoEditarProjection findProjectedEditarById(Integer id);

    @Query("\n" +
            "SELECT \n" +
            "   alu.id AS id, \n" +
            "   alu.codigo AS codigo, \n" +
            "   alu.nombre AS nombre, \n" +
            "   alu.primerApellido AS primerApellido, \n" +
            "   alu.segundoApellido AS segundoApellido, \n" +
            "   alu.fechaNacimiento AS fechaNacimiento \n" +
            "FROM Alumno alu \n" +
            "WHERE \n" +
            "   alu.nombre = :nombre \n" +
            "   AND alu.primerApellido = :primerApellido \n" +
            "   AND COALESCE(alu.segundoApellido,'') = COALESCE(:segundoApellido,'') \n" +
            "   AND alu.fechaNacimiento = :fechaNacimiento \n" +
            "")
    List<AlumnoEditarProjection> findProjectedEditarAllByIdRegistroRepetido(@Param("nombre") String nombre, @Param("primerApellido") String primerApellido, @Param("segundoApellido") String segundoApellido, @Param("fechaNacimiento") Date fechaNacimiento);

    // ComboProjection
    @Query(nativeQuery = true, value = "" +
            "SELECT \n" +
            "    id AS id, \n" +
            "    codigo AS codigo, \n" +
            "    nombre AS nombre, \n" +
            "    primerApellido AS primerApellido, \n" +
            "    segundoApellido AS segundoApellido \n" +
            "FROM [dbo].[VW_Combo_Alumnos] \n" +
            "")
    List<AlumnoComboProjection> findProjectedComboAllBy();
    @Query(nativeQuery = true, value = "" +
            "SELECT TOP 25 \n" +
            "    id AS id, \n" +
            "    codigo AS codigo, \n" +
            "    nombre AS nombre, \n" +
            "    primerApellido AS primerApellido, \n" +
            "    segundoApellido AS segundoApellido \n" +
            "FROM [dbo].[VW_Combo_Alumnos] \n" +
            "ORDER BY codigo ASC \n" +
            "")
    List<AlumnoComboProjection> findProjectedComboAllByTop25();
    @Query(nativeQuery = true, value = "" +
            "SELECT TOP 250\n" +
            "    id AS id, \n" +
            "    codigo AS codigo, \n" +
            "    nombre AS nombre, \n" +
            "    primerApellido AS primerApellido, \n" +
            "    segundoApellido AS segundoApellido \n" +
            "FROM [dbo].[VW_Combo_Alumnos] \n" +
            "WHERE \n" +
            "   CONCAT(codigo,' - ',nombre,' ',primerApellido,CASE WHEN segundoApellido IS null THEN '' ELSE CONCAT(' ',segundoApellido) END) LIKE CONCAT('%',:nombreBuscar,'%')" +
            "")
    List<AlumnoComboProjection> findProjectedComboAllByNombreCompletoCoincide(@Param("nombreBuscar") String nombreBuscar);
    @Query(nativeQuery = true, value = "" +
            "SELECT \n" +
            "    id AS id, \n" +
            "    codigo AS codigo, \n" +
            "    nombre AS nombre, \n" +
            "    primerApellido AS primerApellido, \n" +
            "    segundoApellido AS segundoApellido \n" +
            "FROM [dbo].[VW_Combo_Alumnos] \n" +
            "WHERE id = :id \n" +
            "")
    AlumnoComboProjection findProjectedComboById(@Param("id") Integer id);
    @Query(nativeQuery = true, value = "" +
            "SELECT \n" +
            "    id AS id, \n" +
            "    codigo AS codigo, \n" +
            "    nombre AS nombre, \n" +
            "    primerApellido AS primerApellido, \n" +
            "    segundoApellido AS segundoApellido \n" +
            "FROM [dbo].[VW_Combo_Alumnos] \n" +
            "WHERE codigo = :codigo \n" +
            "")
    AlumnoComboProjection findComboByCodigo(@Param("codigo") String codigo);

    @Query(value = "" +
            "Select TOP 1 Alumnos.* from AlumnosExamenesCertificaciones\n" +
            "INNER JOIN Alumnos on ALU_AlumnoId = ALUEC_ALU_AlumnoId\n" +
            "INNER JOIN Inscripciones on ALU_AlumnoId = INS_ALU_AlumnoId \n" +
            "WHERE ALU_AlumnoId = :idAlumno AND INS_PROGRU_GrupoId = :idGrupo AND ALUEC_CMM_TipoId=2000650" +
            "", nativeQuery = true)
    AlumnoComboProjection findAlumnoExamenCertificacion(@Param("idAlumno") Integer idAlumno, @Param("idGrupo") Integer idGrupo);

    // ReinscripcionProjection
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[fn_ReinscripcionProjection_Alumnos](:sucursalId, CONCAT('%',:filtro,'%')) \n" +
            "ORDER BY id \n" +
            "OFFSET :offset ROWS FETCH NEXT :top ROWS ONLY \n" +
            "")
    List<AlumnoReinscripcionProjection> findReinscripcionProjectionAllBySucursalIdAndFiltros(@Param("sucursalId") Integer sucursalId, @Param("filtro") String filtro, @Param("offset") Integer offset, @Param("top") Integer top);
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[fn_ReinscripcionProjection_Alumnos_Reprobados](:sucursalId, '%%') \n" +
            "WHERE \n" +
            "   id = :id \n" +
            "   AND programaId = :programaId \n" +
            "   AND idiomaId = :idiomaId \n" +
            "")
    AlumnoReinscripcionProjection findReinscripcionProjectionByReprobado(@Param("sucursalId") Integer sucursalId, @Param("id") Integer id, @Param("programaId") Integer programaId, @Param("idiomaId") Integer idiomaId);
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[fn_ReinscripcionProjection_Alumnos_Aprobados](:sucursalId, '%%') \n" +
            "WHERE \n" +
            "   id = :id \n" +
            "   AND programaId = :programaId \n" +
            "   AND idiomaId = :idiomaId \n" +
            "")
    AlumnoReinscripcionProjection findReinscripcionProjectionByAprobado(@Param("sucursalId") Integer sucursalId, @Param("id") Integer id, @Param("programaId") Integer programaId, @Param("idiomaId") Integer idiomaId);
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[fn_ReinscripcionProjection_Alumnos_Reprobados_General](:id, :programaId, :idiomaId) \n" +
            "")
    List<AlumnoReinscripcionProjection> findReinscripcionProjectionByReprobadosAndIdAndProgramaIdAndIdiomaId(@Param("id") Integer id, @Param("programaId") Integer programaId, @Param("idiomaId") Integer idiomaId);
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[fn_ReinscripcionProjection_Alumnos_Aprobados_SinProyeccion](:id, :programaId, :idiomaId, :sucursalId) \n" +
            "")
    List<AlumnoReinscripcionProjection> findReinscripcionProjectionByAprobadosSinProyeccion(@Param("id") Integer id, @Param("programaId") Integer programaId, @Param("idiomaId") Integer idiomaId, @Param("sucursalId") Integer sucursalId);
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[fn_ReinscripcionProjection_Alumnos_Aprobados_General](:id, :programaId, :idiomaId) \n" +
            "")
    List<AlumnoReinscripcionProjection> findReinscripcionProjectionByAprobadosGeneral(@Param("id") Integer id, @Param("programaId") Integer programaId, @Param("idiomaId") Integer idiomaId);

    // EntregarLibrosProjection
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[VW_EntregarLibros_Alumnos] \n" +
            "WHERE \n" +
            "   sucursalId = :sucursalId \n" +
            "   AND CONCAT(codigo,'|',codigoUDG,'|',nombre,' ',primerApellido,' ' + segundoApellido,' ',nombre,'|',grupo,'|',libros,'|',inscripcion,'|',ordenVentaCodigo) LIKE CONCAT('%',:filtro,'%') \n" +
            "ORDER BY id, grupoId \n" +
            "OFFSET :offset ROWS FETCH NEXT :top ROWS ONLY \n" +
            "")
    List<AlumnoEntregarLibrosProjection> findProjectedEntregarLibrosAllBySucursalId(@Param("sucursalId") Integer sucursalId, @Param("filtro") String filtro, @Param("offset") Integer offset, @Param("top") Integer top);

    // InscripcionesPendientesJOBSProjection
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[VW_InscripcionesPendientesJOBS_Alumnos] \n" +
            "WHERE \n" +
            "   sucursalId = :sucursalId \n" +
            "   AND CONCAT(codigo,'|',codigoAlumnoUDG,'|',nombre,' ',primerApellido,' ' + segundoApellido,' ',nombre,'|',centroUniversitario,'|',carrera,'|',grupo) LIKE CONCAT('%',:filtro,'%') \n" +
            "ORDER BY id, grupoId \n" +
            "OFFSET :offset ROWS FETCH NEXT :top ROWS ONLY \n" +
            "")
    List<AlumnoInscripcionesPendientesJOBSProjection> findProjectedInscripcionesPendientesJOBSAllBySucursalId(@Param("sucursalId") Integer sucursalId, @Param("filtro") String filtro, @Param("offset") Integer offset, @Param("top") Integer top);

    // InscripcionesPendientesJOBSSEMSProjection
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[VW_InscripcionesPendientesJOBSSEMS_Alumnos] \n" +
            "WHERE \n" +
            "   sucursalId = :sucursalId \n" +
            "   AND textoBusqueda like :filtro \n" +
            "ORDER BY id, grupoId \n" +
            "OFFSET :offset ROWS FETCH NEXT :top ROWS ONLY \n" +
            "")
    List<AlumnoInscripcionesPendientesJOBSSEMSProjection> findProjectedInscripcionesPendientesJOBSSEMSAllBySucursalId(@Param("sucursalId") Integer sucursalId, @Param("filtro") String filtro, @Param("offset") Integer offset, @Param("top") Integer top);

    // AlumnoInscripcionPendientePCPProjection
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[VW_InscripcionesPendientesPCP_Alumnos] \n" +
            "WHERE \n" +
            "   sucursalId = :sucursalId \n" +
            "   AND (COALESCE(:filtro,'\"**\"') = '\"**\"' OR CONTAINS(textoBusqueda, :filtro)) \n" +
            "ORDER BY id, grupoId \n" +
            "OFFSET :offset ROWS FETCH NEXT :top ROWS ONLY \n" +
            "")
    List<AlumnoInscripcionPendientePCPProjection> findProjectedInscripcionPendientePCPAllBySucursalId(@Param("sucursalId") Integer sucursalId, @Param("filtro") String filtro, @Param("offset") Integer offset, @Param("top") Integer top);

    // Integer
    @Query(nativeQuery = true, value = "SELECT [dbo].[fn_getNivelAlumnoPermiteInscripcion] (:alumnoId,:idiomaId,:programaId)")
    Integer getNivelAlumnoPermiteInscripcion(@Param("alumnoId") Integer alumnoId, @Param("idiomaId") Integer idiomaId, @Param("programaId") Integer programaId);
    @Query(nativeQuery = true, value = "SELECT [dbo].[fn_getAlumnoCursandoIdioma] (:alumnoId,:idiomaId,:programaId,:soloInscripcionesPagadas)")
    Boolean getAlumnoCursandoIdioma(@Param("alumnoId") Integer alumnoId, @Param("idiomaId") Integer idiomaId, @Param("programaId") Integer programaId, @Param("soloInscripcionesPagadas") Boolean soloInscripcionesPagadas);

    Alumno findByCodigoAlumnoUDG(@Param("codigo") String codigo);

    @Query(nativeQuery = true, value = "select " +
            "ALU_Codigo codigoAlumno, " +
            "ALU_CodigoAlumnoUDG codigoUDG, " +
            "ALU_Nombre nombre, " +
            "ALU_PrimerApellido primerApellido, " +
            "ALU_SegundoApellido segundoApellido, " +
            "generos.CMM_Valor genero, " +
            "COALESCE(ALU_TelefonoFijo, ALU_TelefonoMovil, ALU_TelefonoTrabajo, ALU_TelefonoMensajeriaInstantanea) telefono, " +
            "ALU_FechaNacimiento fechaNacimiento, " +
            "ALU_CorreoElectronico correoElectronico, " +
            "grupos.codigo codigoGrupo, " +
            "grupos.fechaInicio, " +
            "grupos.fechaFin, " +
            "grupos.nivel, " +
            "grupos.horario " +
            "from " +
            "Inscripciones " +
            "inner join (select * from [dbo].[VW_LISTADO_GRUPOS]) grupos on INS_PROGRU_GrupoId = grupos.id " +
            "inner join Alumnos on INS_ALU_AlumnoId = ALU_AlumnoId " +
            "left join ControlesMaestrosMultiples generos on ALU_CMM_GeneroId = generos.CMM_ControlId  " +
            "where grupos.jobsSems = 1 and INS_CMM_EstatusId IN (2000510, 2000511) and grupos.estatus = 'activo'")
    List<AlumnoOrdenPagoProjection> findAllProjectedOrdenPagoBy();

    @Query(nativeQuery = true, value = "select " +
            "ALU_AlumnoId alumnoId, " +
            "INS_PROGRU_GrupoId grupoId, " +
            "ALU_CodigoAlumnoUDG codigo, " +
            "ALU_PrimerApellido primerApellido, " +
            "ALU_SegundoApellido segundoApellido, " +
            "ALU_Nombre nombre, " +
            "ALU_CorreoElectronico correo, " +
            "COALESCE(ALU_TelefonoFijo, ALU_TelefonoMovil) telefono, " +
            "grado.CMM_Valor semestre, " +
            "INS_Grupo grupo, " +
            "turno.CMM_Valor turno, " +
            "COALESCE(prepa.CMM_Valor, centro.CMM_Valor) institucion, " +
            "COALESCE(carrera.CMM_Valor, INS_Carrera) carrera, " +
            "ALU_Codigo codigoProulex " +
            "from Inscripciones inner join Alumnos on INS_ALU_AlumnoId = ALU_AlumnoId " +
            "left join ControlesMaestrosMultiples grado on ALU_CMM_GradoId = grado.CMM_ControlId " +
            "left join ControlesMaestrosMultiples turno on ALU_CMM_TurnoId = turno.CMM_ControlId " +
            "left join ControlesMaestrosMultiples prepa on ALU_CMM_PreparatoriaJOBSId = prepa.CMM_ControlId " +
            "left join ControlesMaestrosMultiples centro on ALU_CMM_CentroUniversitarioJOBSId = centro.CMM_ControlId " +
            "left join ControlesMaestrosMultiples carrera on ALU_CMM_CarreraJOBSId = carrera.CMM_ControlId " +
            "where ALU_Activo = 1 and INS_CMM_EstatusId <> 2000512 and INS_PROGRU_GrupoId = :grupoId " +
            "order by grupoId, primerApellido, segundoApellido, nombre")
    List<AlumnoCapturaProjection> findAllProjectedCapturaByGrupoId(@Param("grupoId") Integer grupoId);

    @Query(value = "SELECT codigo, primerApellido, segundoApellido, nombre, plantel, clave, grupo, horario, sede, escuela, referencia, poliza, precio, estatus " +
            "FROM [dbo].[VW_RPT_ALUMNOS_PAGOS] " +
            "WHERE sedeId IN (:sedesId) " +
            "AND (:cicloId IS NULL OR cicloId = :cicloId) " +
            "AND (:paId IS NULL OR paId = :paId) " +
            "AND FORMAT(fechaInicio,'dd/MM/yyyy') = :fecha " +
            "AND (COALESCE(:estatusId, NULL) IS NULL OR estatusId IN (:estatusId)) " +
            "ORDER BY primerApellido, segundoApellido, nombre", nativeQuery = true)
    List<AlumnoPagoProjection> getReporteAlumnosPagos(@Param("sedesId") List<Integer> sedesId,
                                        @Param("cicloId") Integer cicloId,
                                        @Param("paId") Integer paId,
                                        @Param("fecha") String fecha,
                                        @Param("estatusId") List<Integer> estatusId);

    @Query(nativeQuery = true, value = "select " +
            "ALU_Codigo codigoAlumno, " +
            "ALU_CodigoAlumnoUDG codigoUDG, " +
            "ALU_Nombre nombre, " +
            "ALU_PrimerApellido primerApellido, " +
            "ALU_SegundoApellido segundoApellido, " +
            "generos.CMM_Valor genero, " +
            "COALESCE(ALU_TelefonoFijo, ALU_TelefonoMovil, ALU_TelefonoTrabajo, ALU_TelefonoMensajeriaInstantanea) telefono, " +
            "ALU_FechaNacimiento fechaNacimiento, " +
            "ALU_CorreoElectronico correoElectronico, " +
            "grupos.codigo codigoGrupo, " +
            "grupos.fechaInicio, " +
            "grupos.fechaFin, " +
            "grupos.nivel, " +
            "grupos.horario " +
            "from " +
            "Inscripciones " +
            "inner join (select * from [dbo].[VW_LISTADO_GRUPOS]) grupos on INS_PROGRU_GrupoId = grupos.id " +
            "inner join Alumnos on INS_ALU_AlumnoId = ALU_AlumnoId " +
            "left join ControlesMaestrosMultiples generos on ALU_CMM_GeneroId = generos.CMM_ControlId  " +
            "where grupos.jobs = 1 and INS_CMM_EstatusId IN (2000510, 2000511) and grupos.estatus = 'activo'")
    List<AlumnoOrdenPagoProjection> findAllProjectedOrdenPagoJOBSBy();

    @Query(nativeQuery = true, value = "SELECT [dbo].[fn_getAlumnoInterferenciaHorario](:alumnoId,:horarioId)")
    Boolean getInterferenciaHorario(@Param("alumnoId") Integer alumnoId, @Param("horarioId") Integer horarioId);
    @Query(nativeQuery = true, value = "SELECT [dbo].[fn_getAlumnoInterferenciaHorarioIgnorandoInscripcion](:alumnoId,:horarioId,:inscripcionIgnorarId)")
    Boolean getInterferenciaHorarioIgnorandoInscripcion(@Param("alumnoId") Integer alumnoId, @Param("horarioId") Integer horarioId, @Param("inscripcionIgnorarId") Integer inscripcionIgnorarId);

    @Query(nativeQuery = true, value = "SELECT " +
            "ALU_Codigo codigoAlumno, " +
            "ALU_CodigoAlumnoUDG codigoUDG, " +
            "ALU_Nombre nombre, " +
            "ALU_PrimerApellido primerApellido, " +
            "ALU_SegundoApellido segundoApellido, " +
            "generos.CMM_Valor genero, " +
            "COALESCE(ALU_TelefonoFijo, ALU_TelefonoMovil, ALU_TelefonoTrabajo, ALU_TelefonoMensajeriaInstantanea) telefono, " +
            "ALU_FechaNacimiento fechaNacimiento, " +
            "ALU_CorreoElectronico correoElectronico, " +
            "grupos.codigo codigoGrupo, " +
            "grupos.fechaInicio, " +
            "grupos.fechaFin, " +
            "grupos.nivel, " +
            "grupos.horario " +
            "FROM " +
            "Inscripciones " +
            "INNER JOIN (SELECT * FROM [dbo].[VW_PlantillaGrupos]) grupos ON INS_PROGRU_GrupoId = grupos.Id " +
                "   AND SucursalId = :sedeId " +
                "   AND (COALESCE(:listaPlantelId, 0) = 0 OR PlantelId IN (:listaPlantelId)) " +
                "   AND (COALESCE(:listaCursoId, 0) = 0 OR CursoId IN (:listaCursoId)) " +
                "   AND (COALESCE(:listaModalidadId, 0) = 0 OR ModalidadId IN (:listaModalidadId)) " +
                "   AND (COALESCE(:listaFecha, '') = '' OR FORMAT(FechaInicio, 'dd/MM/yyyy') IN (:listaFecha)) " +
            "INNER JOIN Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId " +
            "INNER JOIN ControlesMaestrosMultiples generos ON ALU_CMM_GeneroId = generos.CMM_ControlId  " +
            "WHERE grupos.JobsSems = 1 AND INS_CMM_EstatusId IN (2000510, 2000511) AND grupos.EstatusId = 2000620")
    List<AlumnoOrdenPagoProjection> findAlumnoOrdenPagoProjectionAllByEsJobsSems(@Param("sedeId") Integer sedeId,
                                                                @Param("listaPlantelId") List<Integer> listaPlantelId,
                                                                @Param("listaCursoId") List<Integer> listaCursoId,
                                                                @Param("listaModalidadId") List<Integer> listaModalidadId,
                                                                @Param("listaFecha") List<String> listaFecha);

    @Query(nativeQuery = true, value = "SELECT " +
            "ALU_Codigo codigoAlumno, " +
            "ALU_CodigoAlumnoUDG codigoUDG, " +
            "ALU_Nombre nombre, " +
            "ALU_PrimerApellido primerApellido, " +
            "ALU_SegundoApellido segundoApellido, " +
            "generos.CMM_Valor genero, " +
            "COALESCE(ALU_TelefonoFijo, ALU_TelefonoMovil, ALU_TelefonoTrabajo, ALU_TelefonoMensajeriaInstantanea) telefono, " +
            "ALU_FechaNacimiento fechaNacimiento, " +
            "ALU_CorreoElectronico correoElectronico, " +
            "grupos.codigo codigoGrupo, " +
            "grupos.fechaInicio, " +
            "grupos.fechaFin, " +
            "grupos.nivel, " +
            "grupos.horario " +
            "FROM " +
            "Inscripciones " +
            "INNER JOIN (SELECT * FROM [dbo].[VW_PlantillaGrupos]) grupos ON INS_PROGRU_GrupoId = grupos.Id " +
            "   AND SucursalId = :sedeId " +
            "   AND (COALESCE(:listaPlantelId, 0) = 0 OR PlantelId IN (:listaPlantelId)) " +
            "   AND (COALESCE(:listaCursoId, 0) = 0 OR CursoId IN (:listaCursoId)) " +
            "   AND (COALESCE(:listaModalidadId, 0) = 0 OR ModalidadId IN (:listaModalidadId)) " +
            "   AND (COALESCE(:listaFecha, '') = '' OR FORMAT(FechaInicio, 'dd/MM/yyyy') IN (:listaFecha)) " +
            "INNER JOIN Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId " +
            "INNER JOIN ControlesMaestrosMultiples generos ON ALU_CMM_GeneroId = generos.CMM_ControlId  " +
            "WHERE grupos.Jobs = 1 AND INS_CMM_EstatusId IN (2000510, 2000511) AND grupos.EstatusId = 2000620")
    List<AlumnoOrdenPagoProjection> findAlumnoOrdenPagoProjectionAllByEsJobs(@Param("sedeId") Integer sedeId,
                                                                                 @Param("listaPlantelId") List<Integer> listaPlantelId,
                                                                                 @Param("listaCursoId") List<Integer> listaCursoId,
                                                                                 @Param("listaModalidadId") List<Integer> listaModalidadId,
                                                                                 @Param("listaFecha") List<String> listaFecha);
}
