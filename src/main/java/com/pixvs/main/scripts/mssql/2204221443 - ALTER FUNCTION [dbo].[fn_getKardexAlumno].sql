SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/**
* Created by Angel Daniel Hernández Silva on 07/10/2021.
* Object: CREATE FUNCTION [dbo].[fn_getKardexAlumno]
*/

ALTER FUNCTION [dbo].[fn_getKardexAlumno](@alumnoId int)RETURNS @tbl TABLE(
	alumnoId int,
	alumnoCodigo varchar(150),
	alumnoNombre varchar(200),
	alumnoFechaIngreso varchar(10),
	alumnoSucursalIngreso varchar(100),
	alumnoUltimaSucursal varchar(100),
	alumnoUltimaFechaInscripcion varchar(10),
	alumnoEsAlumnoJOBS bit,
	alumnoEsJOBSSEMS bit,
	alumnoCodigoAlumnoUDG varchar(15),
	alumnoCentroUniversitario varchar(255),
	alumnoCarrera varchar(255),
	alumnoPreparatoria varchar(255),
	alumnoBachilleratoTecnologico varchar(255),
	alumnoTotalInscripciones int,
	alumnoEstatus varchar(255),
	alumnoOrdenVenta varchar(150),

	idiomaId int,
	idiomaNombre varchar(255),
	idiomaOrden int,

	programaId int,
	programaCodigo varchar(10),

	grupoId int,
	grupoCodigo varchar(25),
	grupoNivel int,
	grupoFechaInicio date,
	grupoFechaFin date,
	grupoFechaInicioStr varchar(10),
	grupoFechaFinStr varchar(10),
	grupoCalificacion varchar(150)
) AS BEGIN

	INSERT INTO @tbl
	SELECT
		ALU_AlumnoId AS alumnoId,
		ALU_Codigo AS alumnoCodigo,
		CONCAT(ALU_Nombre,' ' + ALU_PrimerApellido,' ' + ALU_SegundoApellido) AS alumnoNombre,
		FORMAT(ALU_FechaCreacion,'dd/MM/yyyy') AS alumnoFechaIngreso,
		SUC_Nombre AS alumnoSucursalIngreso,
		ultimaSucursal AS alumnoUltimaSucursal,
		FORMAT(fechaUltimaInscripcion,'dd/MM/yyyy') AS alumnoUltimaFechaInscripcion,
		ALU_AlumnoJOBS AS alumnoEsAlumnoJOBS,
		CAST(CASE WHEN ALU_CMM_ProgramaJOBSId = 2000531 THEN 1 ELSE 0 END AS bit) AS alumnoEsJOBSSEMS,
		ALU_CodigoAlumnoUDG AS alumnoCodigoAlumnoUDG,
		CentroUniversitario.CMM_Valor AS alumnoCentroUniversitario,
		Carrera.CMM_Valor AS alumnoCarrera,
		Preparatoria.CMM_Valor AS alumnoPreparatoria,
		ALU_BachilleratoTecnologico AS alumnoBachilleratoTecnologico,
		COUNT(INS_InscripcionId) OVER(PARTITION BY ALU_AlumnoId) AS alumnoTotalInscripciones,
		COALESCE(estatus.CMM_Valor, 'Activo') AS alumnoEstatus,
		COALESCE(OV_Codigo, '') AS alumnoOrdenVenta,

		Idioma.CMM_ControlId AS idiomaId,
		Idioma.CMM_Valor AS idiomaNombre,
		Idioma.CMM_Orden AS  idiomaOrden,

		PROG_ProgramaId AS programaId,
		PROG_Codigo AS programaCodigo,

		PROGRU_GrupoId AS grupoId,
		PROGRU_Codigo AS grupoCodigo,
		PROGRU_Nivel AS grupoNivel,
		PROGRU_FechaInicio AS grupoFechaInicio,
		PROGRU_FechaFin AS grupoFechaFin,
		FORMAT(PROGRU_FechaInicio,'dd/MM/yyyy') AS grupoFechaInicioStr,
		FORMAT(PROGRU_FechaFin,'dd/MM/yyyy') AS grupoFechaFinStr,
		CASE WHEN PROGRU_FechaFin > GETDATE() OR ALUG_CMM_EstatusId = 2000677 THEN '' ELSE CAST(CAST(COALESCE(ALUG_CalificacionFinal, 0) AS INT) AS VARCHAR(3)) END AS grupoCalificacion
		-- CASE WHEN PROGRU_FechaFin > GETDATE() THEN '' ELSE CAST(CAST(COALESCE(calificacion,0) AS int) AS varchar(3)) END AS grupoCalificacion
	FROM Alumnos
	INNER JOIN Sucursales ON SUC_SucursalId = ALU_SUC_SucursalId
	LEFT JOIN (
		SELECT
			ultimoGrupoAlumnoId,
			SUC_Nombre AS ultimaSucursal,
			INS_FechaCreacion AS fechaUltimaInscripcion
		FROM(
			SELECT
				ALU_AlumnoId AS ultimoGrupoAlumnoId,
				MAX(INS_InscripcionId) AS ultimaInscripcionId
			FROM Alumnos
			INNER JOIN Inscripciones ON INS_ALU_AlumnoId = ALU_AlumnoId
			GROUP BY ALU_AlumnoId
		) AS UltimaInscripcion
		INNER JOIN Inscripciones ON INS_InscripcionId = ultimaInscripcionId
		INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
		INNER JOIN Sucursales ON SUC_SucursalId = PROGRU_SUC_SucursalId
	) UltimoGrupo ON ultimoGrupoAlumnoId = ALU_AlumnoId
	LEFT JOIN ControlesMaestrosMultiples AS CentroUniversitario ON CentroUniversitario.CMM_ControlId = ALU_CMM_CentroUniversitarioJOBSId
	LEFT JOIN ControlesMaestrosMultiples AS Carrera ON Carrera.CMM_ControlId = ALU_CMM_CarreraJOBSId
	LEFT JOIN ControlesMaestrosMultiples AS Preparatoria ON Preparatoria.CMM_ControlId = ALU_CMM_PreparatoriaJOBSId
	LEFT JOIN Inscripciones ON INS_ALU_AlumnoId = ALU_AlumnoId
	LEFT JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
	LEFT JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	LEFT JOIN ControlesMaestrosMultiples AS Idioma ON Idioma.CMM_ControlId = PROGI_CMM_Idioma
	LEFT JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
	-- LEFT JOIN VW_GRUPOS_ALUMNOS_CALIFICACION ON alumnoId = ALU_AlumnoId AND grupoId = PROGRU_GrupoId
	LEFT JOIN AlumnosGrupos ON ALUG_ALU_AlumnoId = ALU_AlumnoId AND ALUG_PROGRU_GrupoId = PROGRU_GrupoId
	LEFT JOIN OrdenesVentaDetalles ON INS_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
	LEFT JOIN OrdenesVenta ON OVD_OV_OrdenVentaId = OV_OrdenVentaId
	LEFT JOIN ControlesMaestrosMultiples estatus ON ALUG_CMM_EstatusId = estatus.CMM_ControlId
	WHERE 
		(INS_CMM_EstatusId IS NULL OR INS_CMM_EstatusId <> 2000512)
		AND ALU_AlumnoId = @alumnoId
	ORDER BY Idioma.CMM_Orden, PROGRU_FechaInicio

RETURN
END