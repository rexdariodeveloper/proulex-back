CREATE OR ALTER FUNCTION [dbo].[fn_getProyeccionGrupoFechaFin](@fechaInicio Date, @diasFinInscripciones INT, @modalidadId INT)
RETURNS DATE
AS BEGIN 
DECLARE @fechaFinInscripciones DATE = null;
	SELECT TOP 1
		@fechaFinInscripciones = fecha
	FROM
		[dbo].[PAModalidades] OUTER APPLY [dbo].[fn_getFechaPorModalidad](@fechaInicio, @diasFinInscripciones, PAMOD_Domingo, PAMOD_Lunes, PAMOD_Martes, PAMOD_Miercoles, PAMOD_Jueves, PAMOD_Viernes, PAMOD_Sabado)
	WHERE
		PAMOD_ModalidadId = @modalidadId AND fecha IS NOT NULL
	ORDER BY fecha DESC
RETURN @fechaFinInscripciones;
END
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getProyeccionUltimoGrupoCreado](@sedeId INT, @programaIdiomaId INT, @modalidadId INT, @nivel INT, @fechaInicio DATE)
RETURNS INT
WITH SCHEMABINDING
AS BEGIN 
DECLARE @maxGrupo INT = NULL;
	SELECT @maxGrupo = MAX([PROGRU_Grupo]) FROM [dbo].[ProgramasGrupos]
	WHERE 
		[PROGRU_SUC_SucursalId] = @sedeId 
		AND [PROGRU_PROGI_ProgramaIdiomaId] = @programaIdiomaId 
		AND [PROGRU_PAMOD_ModalidadId] = @modalidadId 
		AND [PROGRU_Nivel] = @nivel + 1
		AND [PROGRU_FechaInicio] = @fechaInicio
		AND [PROGRU_CMM_EstatusId] <> 2000622;
RETURN @maxGrupo;
END
GO



CREATE OR ALTER FUNCTION [dbo].[fn_getProyeccionGrupoCodigo](@grupoId INT, @maxGrupo INT,  @nivel INT)
RETURNS NVARCHAR(50)
WITH SCHEMABINDING
AS BEGIN 
	DECLARE @codigo NVARCHAR(50) = NULL;
	SELECT
		@codigo = CONCAT(LEFT([SUC_Prefijo],3),
				LEFT([SP_Codigo],3),
				LEFT([PROG_Codigo],3),
				LEFT([CMM_Referencia],3),
				LEFT([PAMOD_Codigo],3),
				FORMAT(@nivel + 1,'00'),
				LEFT([PAMODH_Codigo],3),
				FORMAT(COALESCE(@maxGrupo, 0) + 1,'00'))
		FROM [dbo].[ProgramasGrupos] 
		INNER JOIN [dbo].[Sucursales] ON [PROGRU_SUC_SucursalId] = [SUC_SucursalId]
		LEFT JOIN [dbo].[SucursalesPlanteles] ON [PROGRU_SP_SucursalPlantelId] = [SP_SucursalPlantelId]
		INNER JOIN [dbo].[ProgramasIdiomas] ON [PROGRU_PROGI_ProgramaIdiomaId] = [PROGI_ProgramaIdiomaId]
		INNER JOIN [dbo].[Programas] ON [PROGI_PROG_ProgramaId] = [PROG_ProgramaId]
		INNER JOIN [dbo].[ControlesMaestrosMultiples] ON [PROGI_CMM_Idioma] = [CMM_ControlId]
		INNER JOIN [dbo].[PAModalidades] ON [PROGRU_PAMOD_ModalidadId] = [PAMOD_ModalidadId]
		INNER JOIN [dbo].[PAModalidadesHorarios] ON [PROGRU_PAMODH_PAModalidadHorarioId] = [PAMODH_PAModalidadHorarioId]
		WHERE [PROGRU_GrupoId] = @grupoId
RETURN @codigo;
END
GO

CREATE NONCLUSTERED INDEX [IX_PROGRU_SUC_IDI_MOD_NIV]
ON [dbo].[ProgramasGrupos] ([PROGRU_SUC_SucursalId],[PROGRU_PROGI_ProgramaIdiomaId],[PROGRU_PAMOD_ModalidadId],[PROGRU_FechaInicio],[PROGRU_Nivel],[PROGRU_CMM_EstatusId])
INCLUDE ([PROGRU_Grupo])
GO

CREATE NONCLUSTERED INDEX [IX_INS_EST_PROGRU]
ON [dbo].[Inscripciones] ([INS_CMM_EstatusId])
INCLUDE ([INS_ALU_AlumnoId],[INS_PROGRU_GrupoId],[INS_OVD_OrdenVentaDetalleId])
GO

CREATE NONCLUSTERED INDEX [IX_OVD_OV]
ON [dbo].[OrdenesVentaDetalles] ([OVD_OV_OrdenVentaId])

GO