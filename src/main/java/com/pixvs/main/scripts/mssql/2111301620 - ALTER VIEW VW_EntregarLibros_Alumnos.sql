/**
* Created by Angel Daniel Hernández Silva on 30/11/2021.
* Object:  ALTER VIEW [dbo].[VW_EntregarLibros_Alumnos]
*/

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
		grupoCodigo AS grupo,
		STRING_AGG(ART_NombreArticulo, ', ') AS libros,
		EstatusInscripcion.CMM_ControlId AS inscripcionEstatusId,
		EstatusInscripcion.CMM_Valor AS inscripcion,

		SUC_SucursalId AS sucursalId
	FROM Alumnos
	INNER JOIN Inscripciones ON INS_ALU_AlumnoId = ALU_AlumnoId
	INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
	INNER JOIN VW_Codigo_ProgramasGrupos ON grupoId = PROGRU_GrupoId
	INNER JOIN Sucursales ON SUC_SucursalId = PROGRU_SUC_SucursalId
	INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	INNER JOIN ProgramasIdiomasLibrosMateriales ON PROGILM_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND PROGILM_Nivel = PROGRU_Nivel
	LEFT JOIN ProgramasIdiomasLibrosMaterialesReglas ON PROGILMR_PROGILM_ProgramaIdiomaLibroMaterialId = PROGILM_ProgramaIdiomaLibroMaterialId
	INNER JOIN Articulos ON ART_ArticuloId = PROGILM_ART_ArticuloId
	INNER JOIN ControlesMaestrosMultiples AS EstatusInscripcion ON EstatusInscripcion.CMM_ControlId = INS_CMM_EstatusId
	WHERE
		INS_EntregaLibrosPendiente = 1
		AND INS_CMM_EstatusId <> 2000512
		AND (
			PROGILMR_ProgramaIdiomaLibroMaterialReglaId IS NULL
			OR (
				ALU_CMM_CarreraJOBSId IS NOT NULL
				AND PROGILMR_CMM_CarreraId = ALU_CMM_CarreraJOBSId
			)
		)
	GROUP BY 
		ALU_AlumnoId, ALU_ARC_FotoId, ALU_Codigo, ALU_Nombre, ALU_PrimerApellido, ALU_SegundoApellido, PROGRU_GrupoId,
		grupoCodigo, SUC_CodigoSucursal, EstatusInscripcion.CMM_ControlId, EstatusInscripcion.CMM_Valor, SUC_SucursalId,
		ALU_CodigoUDG

GO