
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/* ****************************************************************
 * VW_RPT_GRUPOS_RESUMEN
 * ****************************************************************
 * Descripción: Obtenemos la vista de reporte de grupo resumen.
 * Autor:
 * Modificado Por: Rene Carrillo
 * Fecha:
 * Modificado Fecha: 09/06/2022
 * Versión: 1.0.1
 *****************************************************************
*/

ALTER VIEW [dbo].[VW_RPT_GRUPOS_RESUMEN]
AS
	SELECT
		/* Header fields */
		UPPER([Programas].[PROG_Codigo]) programa,
		UPPER([Idiomas].[CMM_Valor]) idioma,
		UPPER([PAModalidades].[PAMOD_Nombre]) modalidad,
		UPPER([TiposGrupo].[CMM_Valor]) tipoGrupo,
		[ProgramasGrupos].[PROGRU_FechaInicio] fechaInicio,
		[ProgramasGrupos].[PROGRU_FechaFin] fechaFin,
		FORMAT([ProgramasGrupos].[PROGRU_Nivel], N'00') nivel,
		[PAModalidadesHorarios].[PAMODH_Horario] horario,
		FORMAT([ProgramasGrupos].[PROGRU_Grupo], N'00') grupo,
		ISNULL([ProgramasGrupos].[PROGRU_Aula], '00') aula,
		UPPER(CONCAT_WS(N' ',[Empleados].[EMP_PrimerApellido],[Empleados].[EMP_SegundoApellido],[Empleados].[EMP_Nombre])) profesor,
		UPPER([Sucursales].[SUC_Nombre]) sede,
		GETDATE() fecha,
		/* Detail fields */
		UPPER(CONCAT_WS(N' ',[Alumnos].[ALU_PrimerApellido],[Alumnos].[ALU_SegundoApellido],[Alumnos].[ALU_Nombre])) alumno,
		[Alumnos].[ALU_Codigo] codigo,
		[OrdenesVenta].[OV_Codigo] recibo,
		[Estatus].[CMM_Valor] estatus,
		/* Query fields */
		[ProgramasGrupos].[PROGRU_GrupoId] grupoId
	FROM
		[dbo].[ProgramasGrupos]
		INNER JOIN [dbo].[ProgramasIdiomas] ON [PROGRU_PROGI_ProgramaIdiomaId] = [PROGI_ProgramaIdiomaId]
		INNER JOIN [dbo].[Programas] ON [PROGI_PROG_ProgramaId] = [PROG_ProgramaId]
		INNER JOIN [dbo].[ControlesMaestrosMultiples] [Idiomas] ON [PROGI_CMM_Idioma] = [Idiomas].[CMM_ControlId]
		INNER JOIN [dbo].[PAModalidades] ON [PROGRU_PAMOD_ModalidadId] = [PAMOD_ModalidadId]
		INNER JOIN [dbo].[ControlesMaestrosMultiples] [TiposGrupo] ON [PROGRU_CMM_TipoGrupoId] = [TiposGrupo].[CMM_ControlId]
		INNER JOIN [dbo].[PAModalidadesHorarios] ON [PROGRU_PAMODH_PAModalidadHorarioId] = [PAMODH_PAModalidadHorarioId]
		LEFT JOIN  [dbo].[Empleados] ON [PROGRU_EMP_EmpleadoId] = [EMP_EmpleadoId]
		INNER JOIN [dbo].[Sucursales] ON [PROGRU_SUC_SucursalId] = [SUC_SucursalId]
		INNER JOIN [dbo].[Inscripciones] ON [PROGRU_GrupoId] = [INS_PROGRU_GrupoId]
		INNER JOIN [dbo].[OrdenesVentaDetalles] ON [INS_OVD_OrdenVentaDetalleId] = [OVD_OrdenVentaDetalleId]
		INNER JOIN [dbo].[OrdenesVenta] ON [OVD_OV_OrdenVentaId] = [OV_OrdenVentaId]
		INNER JOIN [dbo].[Alumnos] ON [INS_ALU_AlumnoId] = [ALU_AlumnoId]
		INNER JOIN [dbo].[ControlesMaestrosMultiples] [Estatus] ON [INS_CMM_EstatusId] = [Estatus].[CMM_ControlId]
GO