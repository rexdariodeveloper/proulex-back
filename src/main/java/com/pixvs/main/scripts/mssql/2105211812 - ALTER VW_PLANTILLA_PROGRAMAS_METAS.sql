CREATE OR ALTER VIEW [dbo].[VW_PLANTILLA_PROGRAMAS_METAS] AS

	SELECT DISTINCT
		PAC_ProgramacionAcademicaComercialId AS programacionAcademicaComercialId,
        PACD_FechaInicio AS fechaInicio,
		PAC_Codigo AS "CÓDIGO_PROGRAMACIÓN_COMERCIAL",
		PAC_Nombre AS "NOMBRE_PROGRAMACIÓN_COMERCIAL",
		SUC_CodigoSucursal AS "CÓDIGO_SEDE",
		SUC_Nombre AS "NOMBRE_SEDE",
		PAMOD_Codigo AS "CÓDIGO_MODALIDAD",
		PAMOD_Nombre AS "NOMBRE_MODALIDAD",
		PROG_Codigo AS "CÓDIGO_PROGRAMA",
		PROG_Nombre AS "NOMBRE_PROGRAMA",
		FORMAT(PACD_FechaInicio,'dd/MM/yyyy') AS "FECHA_INICIO_CURSO",
		'' AS "META"
	FROM ProgramacionAcademicaComercial
	INNER JOIN Sucursales ON SUC_Activo = 1
	INNER JOIN ProgramacionAcademicaComercialDetalles ON PACD_PAC_ProgramacionAcademicaComercialId = PAC_ProgramacionAcademicaComercialId
	INNER JOIN PAModalidades ON PAMOD_ModalidadId = PACD_PAMOD_ModalidadId
	INNER JOIN ProgramacionAcademicaComercialDetallesProgramas ON PACDP_PACD_ProgramacionAcademicaComercialDetalleId = PACD_ProgramacionAcademicaComercialDetalleId
	INNER JOIN Programas ON PROG_ProgramaId = PACD_PROG_ProgramaId

GO