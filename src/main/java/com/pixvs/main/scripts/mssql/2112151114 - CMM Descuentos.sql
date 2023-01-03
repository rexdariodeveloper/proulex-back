ALTER TABLE [PADescuentos]
ADD [PADESC_CLI_ClienteId] [int] null
GO

ALTER TABLE [PADescuentos]
ADD [PADESC_PrioridadEvaluacion] [int] null
GO

ALTER TABLE [PADescuentos]
ADD [PADESC_CMM_TipoId] [int] null
GO

---TipoId
ALTER TABLE [dbo].[PADescuentos]  WITH CHECK ADD  CONSTRAINT [FK_PADESC_CMM_TipoId] FOREIGN KEY([PADESC_CMM_TipoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[PADescuentos] CHECK CONSTRAINT [FK_PADESC_CMM_TipoId]
GO

---Clientes
ALTER TABLE [dbo].[PADescuentos]  WITH CHECK ADD  CONSTRAINT [FK_PADESC_CLI_ClienteId] FOREIGN KEY([PADESC_CLI_ClienteId])
REFERENCES [dbo].[Clientes] ([CLI_ClienteId])
GO

ALTER TABLE [dbo].[PADescuentos] CHECK CONSTRAINT [FK_PADESC_CLI_ClienteId]
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON 
GO

UPDATE PADescuentos
SET PADESC_CMM_TipoId = 2000700
WHERE PADESC_CMM_TipoId IS NULL
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
) VALUES  (
	/* [CMM_ControlId] */ 2000700,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_PADES_Tipo',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ 'Automatico'
), (
	/* [CMM_ControlId] */ 2000701,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_PADES_Tipo',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ 'Bajo Demanda'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

ALTER TABLE [dbo].[PADescuentos]
ALTER COLUMN [PADESC_FechaInicio][date] null
GO

ALTER TABLE [dbo].[PADescuentos]
ALTER COLUMN [PADESC_FechaFin][date] null
GO