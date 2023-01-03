CREATE OR ALTER VIEW [dbo].[VW_MetaListado_ProgramacionAcademicaComercialDetalle] AS

	SELECT
		PACD_PAC_ProgramacionAcademicaComercialId AS programacionAcademicaComercialId,
		MAX(PACD_ProgramacionAcademicaComercialDetalleId) AS id,
		PAMOD_ModalidadId AS paModalidadId,
		PAMOD_Nombre AS paModalidadNombre,
		PACD_FechaInicio AS fechaInicio,
		PACD_FechaFin AS fechaFin,
		PROG_ProgramaId AS programaId,
		PROG_Nombre AS programaNombre
	FROM ProgramacionAcademicaComercialDetalles
	INNER JOIN PAModalidades ON PAMOD_ModalidadId = PACD_PAMOD_ModalidadId
	INNER JOIN ProgramacionAcademicaComercialDetallesProgramas ON PACDP_PACD_ProgramacionAcademicaComercialDetalleId = PACD_ProgramacionAcademicaComercialDetalleId
	INNER JOIN Programas ON PROG_ProgramaId = PACD_PROG_ProgramaId
	GROUP BY PACD_PAC_ProgramacionAcademicaComercialId, PAMOD_ModalidadId, PAMOD_Nombre, PACD_FechaInicio, PACD_FechaFin, PROG_ProgramaId, PROG_Nombre

GO