CREATE OR ALTER VIEW [dbo].[VW_EntregarLibros_Alumnos] AS

	SELECT
		ALU_AlumnoId AS id,
		ALU_ARC_FotoId AS fotoId,
		ALU_Codigo AS codigo,
		ALU_CodigoUDG AS codigoUDG,
		ALU_Nombre AS nombre,
		ALU_PrimerApellido AS primerApellido,
		ALU_SegundoApellido AS segundoApellido,
		PROGRU_GrupoId AS grupoId,
		PROGRU_Codigo AS grupo,
		STRING_AGG(ART_NombreArticulo, ', ') AS libros,
		EstatusInscripcion.CMM_ControlId AS inscripcionEstatusId,
		EstatusInscripcion.CMM_Valor AS inscripcion,
		INS_InscripcionId AS inscripcionId,
		NULL AS inscripcionSinGrupoId,

		SUC_SucursalId AS sucursalId
	FROM Alumnos
	INNER JOIN Inscripciones ON INS_ALU_AlumnoId = ALU_AlumnoId
	INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
	INNER JOIN Sucursales ON SUC_SucursalId = PROGRU_SUC_SucursalId
	INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	INNER JOIN ProgramasIdiomasLibrosMateriales ON PROGILM_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND PROGILM_Nivel = PROGRU_Nivel
	LEFT JOIN ProgramasIdiomasLibrosMaterialesReglas ON PROGILMR_PROGILM_ProgramaIdiomaLibroMaterialId = PROGILM_ProgramaIdiomaLibroMaterialId
	INNER JOIN Articulos ON ART_ArticuloId = PROGILM_ART_ArticuloId
	INNER JOIN ControlesMaestrosMultiples AS EstatusInscripcion ON EstatusInscripcion.CMM_ControlId = INS_CMM_EstatusId
	WHERE
		INS_EntregaLibrosPendiente = 1
		AND INS_CMM_EstatusId NOT IN (2000512,2000513)
		AND (
			PROGILMR_ProgramaIdiomaLibroMaterialReglaId IS NULL
			OR (
				ALU_CMM_CarreraJOBSId IS NOT NULL
				AND PROGILMR_CMM_CarreraId = ALU_CMM_CarreraJOBSId
			)
		)
	GROUP BY 
		ALU_AlumnoId, ALU_ARC_FotoId, ALU_Codigo, ALU_Nombre, ALU_PrimerApellido, ALU_SegundoApellido, PROGRU_GrupoId,
		PROGRU_Codigo, SUC_CodigoSucursal, EstatusInscripcion.CMM_ControlId, EstatusInscripcion.CMM_Valor, SUC_SucursalId,
		ALU_CodigoUDG, INS_InscripcionId

	UNION ALL

	SELECT
		ALU_AlumnoId AS id,
		ALU_ARC_FotoId AS fotoId,
		ALU_Codigo AS codigo,
		ALU_CodigoUDG AS codigoUDG,
		ALU_Nombre AS nombre,
		ALU_PrimerApellido AS primerApellido,
		ALU_SegundoApellido AS segundoApellido,
		NULL AS grupoId,
		NULL AS grupo,
		STRING_AGG(ART_NombreArticulo, ', ') AS libros,
		EstatusInscripcion.CMM_ControlId AS inscripcionEstatusId,
		EstatusInscripcion.CMM_Valor AS inscripcion,
		NULL AS inscripcionId,
		INSSG_InscripcionId AS inscripcionSinGrupoId,

		SUC_SucursalId AS sucursalId
	FROM Alumnos
	INNER JOIN InscripcionesSinGrupo ON INSSG_ALU_AlumnoId = ALU_AlumnoId
	INNER JOIN Sucursales ON SUC_SucursalId = INSSG_SUC_SucursalId
	INNER JOIN ProgramasIdiomas ON PROGI_PROG_ProgramaId = INSSG_PROG_ProgramaId AND PROGI_CMM_Idioma = INSSG_CMM_IdiomaId
	INNER JOIN ProgramasIdiomasLibrosMateriales ON PROGILM_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND PROGILM_Nivel = INSSG_Nivel
	LEFT JOIN ProgramasIdiomasLibrosMaterialesReglas ON PROGILMR_PROGILM_ProgramaIdiomaLibroMaterialId = PROGILM_ProgramaIdiomaLibroMaterialId
	INNER JOIN Articulos ON ART_ArticuloId = PROGILM_ART_ArticuloId
	INNER JOIN ControlesMaestrosMultiples AS EstatusInscripcion ON EstatusInscripcion.CMM_ControlId = INSSG_CMM_EstatusId
	WHERE
		INSSG_EntregaLibrosPendiente = 1
		AND INSSG_CMM_EstatusId <> 2000543
		AND (
			PROGILMR_ProgramaIdiomaLibroMaterialReglaId IS NULL
			OR (
				ALU_CMM_CarreraJOBSId IS NOT NULL
				AND PROGILMR_CMM_CarreraId = ALU_CMM_CarreraJOBSId
			)
		)
	GROUP BY 
		ALU_AlumnoId, ALU_ARC_FotoId, ALU_Codigo, ALU_Nombre, ALU_PrimerApellido, ALU_SegundoApellido,
		SUC_CodigoSucursal, EstatusInscripcion.CMM_ControlId, EstatusInscripcion.CMM_Valor, SUC_SucursalId,
		ALU_CodigoUDG, INSSG_InscripcionId

GO