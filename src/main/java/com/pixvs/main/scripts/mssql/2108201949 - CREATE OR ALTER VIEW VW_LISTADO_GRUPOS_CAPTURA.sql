SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW [dbo].[VW_LISTADO_GRUPOS_CAPTURA]
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
			'#4D6EB5' colorPrimario,
			'#99CEB0' colorSecundario,

			PROGRU_GrupoId grupoId,
			PROGRU_EMP_EmpleadoId profesorId
		FROM 
			[dbo].[ProgramasGrupos] 
			INNER JOIN [dbo].[ProgramasIdiomas] ON [PROGRU_PROGI_ProgramaIdiomaId] = [PROGI_ProgramaIdiomaId]
			INNER JOIN [dbo].[Programas] ON [PROGI_PROG_ProgramaId] = [PROG_ProgramaId]
			INNER JOIN [dbo].[PAModalidades] ON [PROGRU_PAMOD_ModalidadId] = [PAMOD_ModalidadId]
			LEFT JOIN [dbo].[Inscripciones] ON [PROGRU_GrupoId] = [INS_PROGRU_GrupoId] AND [INS_CMM_EstatusId] IN (2000510,2000511)
		GROUP BY
			[PROG_Codigo],[PROGRU_Grupo],[PROGRU_Cupo],[PAMOD_Color],[PROGRU_GrupoId],[PROGRU_SUC_SucursalId],[PROGRU_EMP_EmpleadoId]
	)t  ON id = grupoId
GO