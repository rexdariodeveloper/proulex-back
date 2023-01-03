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
	/* [CMM_ControlId] */ 2000582,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_BECU_Tipo',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Proulex'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

ALTER TABLE [dbo].[BecasUDG]
ADD [BECU_CMM_EntidadId][INT] NULL
GO

ALTER TABLE [dbo].[BecasUDG]  WITH CHECK ADD  CONSTRAINT [FK_BECU_CMM_EntidadId] FOREIGN KEY([BECU_CMM_EntidadId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[BecasUDG] CHECK CONSTRAINT [FK_BECU_CMM_EntidadId]
GO