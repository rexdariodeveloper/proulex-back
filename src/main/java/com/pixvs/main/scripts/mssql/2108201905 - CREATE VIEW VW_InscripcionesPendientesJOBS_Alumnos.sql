/**
* Created by Angel Daniel Hernández Silva on 19/08/2021.
* Object:  ALTER VIEW [dbo].[VW_InscripcionesPendientesJOBS_Alumnos]
*/

CREATE OR ALTER VIEW [dbo].[VW_InscripcionesPendientesJOBS_Alumnos] AS
	SELECT
		ALU_AlumnoId as id,
		ALU_Codigo AS codigo,
		ALU_CodigoAlumnoUDG AS codigoAlumnoUDG,
		ALU_Nombre AS nombre,
		ALU_PrimerApellido AS primerApellido,
		ALU_SegundoApellido AS segundoApellido,
		CentroUniversitario.CMM_Referencia AS centroUniversitario,
		Carrera.CMM_Valor AS carrera,
		PROGRU_GrupoId AS grupoId,
		CONCAT(SUBSTRING(SUC_CodigoSucursal, 1, 3),COALESCE(SP_Codigo,''),SUBSTRING(PROG_Codigo, 1, 3),SUBSTRING(Idioma.CMM_Referencia, 1, 3),SUBSTRING(PAMOD_Codigo, 1, 3),FORMAT(PROGRU_Nivel,'00'),PAMODH_Codigo,FORMAT(PROGRU_Grupo,'00')) AS grupo,
		SUC_SucursalId AS sucursalId
	FROM Alumnos
	INNER JOIN Inscripciones ON INS_ALU_AlumnoId = ALU_AlumnoId
	INNER JOIN ControlesMaestrosMultiples AS CentroUniversitario ON CentroUniversitario.CMM_ControlId = ALU_CMM_CentroUniversitarioJOBSId
	INNER JOIN ControlesMaestrosMultiples AS Carrera ON Carrera.CMM_ControlId = ALU_CMM_CarreraJOBSId
	INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
	INNER JOIN Sucursales ON SUC_SucursalId = PROGRU_SUC_SucursalId
	LEFT JOIN SucursalesPlanteles on SP_SucursalPlantelId = PROGRU_SP_SucursalPlantelId
	INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
	INNER JOIN ControlesMaestrosMultiples AS Idioma ON Idioma.CMM_ControlId = PROGI_CMM_Idioma
	INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
	INNER JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
	INNER JOIN OrdenesVentaDetalles ON OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaId
	INNER JOIN OrdenesVenta ON OV_OrdenVentaId = OVD_OV_OrdenVentaId
	WHERE
		ALU_AlumnoJOBS = 1
		AND ALU_CMM_ProgramaJOBSId = 2000530 -- JOBS
		AND INS_CMM_EstatusId = 2000511 -- PENDIENTES DE PAGO
		AND OV_MPPV_MedioPagoPVId IS NULL
GO