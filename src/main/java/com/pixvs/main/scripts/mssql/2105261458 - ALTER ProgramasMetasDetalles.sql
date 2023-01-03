
DELETE FROM [dbo].[ProgramasMetasDetalles]
GO

DELETE FROM [dbo].[ProgramasMetas]
GO


ALTER TABLE [dbo].[ProgramasMetasDetalles] ADD [PMD_PAMOD_ModalidadId] [int] NOT NULL
GO

ALTER TABLE [dbo].[ProgramasMetasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PMD_PAMOD_ModalidadId] FOREIGN KEY([PMD_PAMOD_ModalidadId])
REFERENCES [dbo].[PAModalidades] ([PAMOD_ModalidadId])
GO

ALTER TABLE [dbo].[ProgramasMetasDetalles] CHECK CONSTRAINT [FK_PMD_PAMOD_ModalidadId]
GO

ALTER TABLE [dbo].[ProgramasMetasDetalles] ADD [PMD_FechaInicio] [date] NOT NULL
GO

ALTER TABLE [dbo].[ProgramasMetasDetalles] DROP CONSTRAINT [FK_PMD_PACD_ProgramacionAcademicaComercialDetalleId]
GO

ALTER TABLE [dbo].[ProgramasMetasDetalles] DROP COLUMN [PMD_PACD_ProgramacionAcademicaComercialDetalleId]
GO

ALTER TABLE [dbo].[ProgramasMetasDetalles] DROP CONSTRAINT [FK_PMD_PROG_ProgramaId]
GO

ALTER TABLE [dbo].[ProgramasMetasDetalles] DROP COLUMN [PMD_PROG_ProgramaId]
GO

/**********************************************************************/
/***** Vista VW_MetaListado_ProgramacionAcademicaComercialDetalle *****/
/**********************************************************************/

CREATE OR ALTER VIEW [dbo].[VW_MetaListado_ProgramacionAcademicaComercialDetalle] AS

	SELECT
		PACD_PAC_ProgramacionAcademicaComercialId AS programacionAcademicaComercialId,
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

/******************************************************/
/***** Vista VW_EditarJson_ProgramasMetasDetalles *****/
/******************************************************/

CREATE OR ALTER VIEW [dbo].[VW_EditarJson_ProgramasMetasDetalles] AS

	SELECT
		programaMetaId,
		'{ ' + STRING_AGG('"' + sedeId + '" : ' + metas,', ') + ' }' AS metas
	FROM(
		SELECT
			programaMetaId,
			sedeId,
			'{ ' + STRING_AGG('"' + paModalidadId + '" : ' + metas,', ') + ' }' AS metas
		FROM(
			SELECT
				programaMetaId,
				sedeId,
				paModalidadId,
				'{ ' + STRING_AGG('"' + fechaInicio + '" : ' + meta,', ') + ' }' AS metas
			FROM(
				SELECT
					PMD_PM_ProgramaMetaId AS programaMetaId,
					CAST(PMD_SUC_SucursalId AS varchar(max)) AS sedeId,
					CAST(PMD_PAMOD_ModalidadId AS varchar(max)) AS paModalidadId,
					FORMAT(PMD_FechaInicio,'yyyy-MM-dd') AS fechaInicio,
					CAST(PMD_Meta AS varchar(max)) AS meta
				FROM ProgramasMetasDetalles
			) Metas
			GROUP BY programaMetaId, sedeId, paModalidadId
		) MetasPorFechaInicio
		GROUP BY programaMetaId, sedeId
	) MetasPorPAModalidad
	GROUP BY programaMetaId

GO

/**********************************************/
/***** Vista VW_PLANTILLA_PROGRAMAS_METAS *****/
/**********************************************/

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
		FORMAT(PACD_FechaInicio,'dd/MM/yyyy') AS "FECHA_INICIO_CURSO",
		'' AS "META"
	FROM ProgramacionAcademicaComercial
	INNER JOIN Sucursales ON SUC_Activo = 1
	INNER JOIN ProgramacionAcademicaComercialDetalles ON PACD_PAC_ProgramacionAcademicaComercialId = PAC_ProgramacionAcademicaComercialId
	INNER JOIN PAModalidades ON PAMOD_ModalidadId = PACD_PAMOD_ModalidadId

GO