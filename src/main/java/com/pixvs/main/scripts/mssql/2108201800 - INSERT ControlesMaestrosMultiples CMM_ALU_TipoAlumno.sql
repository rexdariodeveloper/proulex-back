SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON 
GO

INSERT [dbo].[ControlesMaestrosMultiples] (
	[CMM_ControlId],
	[CMM_Activo],
	[CMM_Control],
	[CMM_USU_CreadoPorId],
	[CMM_FechaCreacion],
	[CMM_FechaModificacion],
	[CMM_USU_ModificadoPorId],
	[CMM_Referencia],
	[CMM_Sistema],
	[CMM_Valor]
) VALUES (
	/* [CMM_ControlId] */ 2000590,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_ALU_TipoAlumno',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Alumno'
), (
	/* [CMM_ControlId] */ 2000591,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_ALU_TipoAlumno',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Candidato'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

UPDATE [dbo].[Alumnos] SET [ALU_CMM_TipoAlumnoId] = 2000590 WHERE [ALU_CMM_TipoAlumnoId] IS NULL
GO