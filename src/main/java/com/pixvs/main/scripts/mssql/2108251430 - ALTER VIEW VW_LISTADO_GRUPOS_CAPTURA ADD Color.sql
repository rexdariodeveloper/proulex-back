SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[VW_LISTADO_GRUPOS_CAPTURA]
AS
	SELECT * FROM [dbo].[VW_LISTADO_GRUPOS]
	INNER JOIN
	(
		SELECT 
			PROG_Codigo programaCodigo,
			FORMAT(PROGRU_Grupo,'00') grupo,
			PROGRU_Cupo cupo,
			COUNT(INS_InscripcionId) inscritos,
			PAMOD_Color colorModalidad,
			CASE WHEN CMM_Color IS NULL OR SUBSTRING(CMM_Color,1,7) = '' THEN '#4D6EB5' ELSE SUBSTRING(CMM_Color,1,7) END colorPrimario,
			CASE WHEN CMM_Color IS NULL OR SUBSTRING(CMM_Color,9,16) = '' THEN '#99CEB0' ELSE SUBSTRING(CMM_Color,9,16) END colorSecundario,

			PROGRU_GrupoId grupoId,
			PROGRU_EMP_EmpleadoId profesorId
		FROM 
			[dbo].[ProgramasGrupos] 
			INNER JOIN [dbo].[ProgramasIdiomas] ON [PROGRU_PROGI_ProgramaIdiomaId] = [PROGI_ProgramaIdiomaId]
			INNER JOIN [dbo].[Programas] ON [PROGI_PROG_ProgramaId] = [PROG_ProgramaId]
			INNER JOIN [dbo].[PAModalidades] ON [PROGRU_PAMOD_ModalidadId] = [PAMOD_ModalidadId]
			LEFT JOIN [dbo].[Inscripciones] ON [PROGRU_GrupoId] = [INS_PROGRU_GrupoId] AND [INS_CMM_EstatusId] IN (2000510,2000511)
			INNER JOIN [dbo].[ControlesMaestrosMultiples] ON [CMM_ControlId] = [PROGI_CMM_Idioma]
		GROUP BY
			[PROG_Codigo],[PROGRU_Grupo],[PROGRU_Cupo],[PAMOD_Color],[PROGRU_GrupoId],[PROGRU_SUC_SucursalId],[PROGRU_EMP_EmpleadoId],[CMM_Color]
	)t  ON id = grupoId
GO