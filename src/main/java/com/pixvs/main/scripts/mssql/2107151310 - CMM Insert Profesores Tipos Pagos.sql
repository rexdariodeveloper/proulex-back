SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON
GO

INSERT [dbo].[ControlesMaestrosMultiples] (
	[CMM_ControlId],
	[CMM_Activo],
	[CMM_Control],
	[CMM_USU_CreadoPorId],
	[CMM_FechaCreacion],
	[CMM_Referencia],
	[CMM_Sistema],
	[CMM_Valor]
) VALUES (
	/* [CMM_ControlId] */ 2000520,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_PROGRULC_FormaPago',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pago suplente'
),(
	/* [CMM_ControlId] */ 2000521,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_PROGRULC_FormaPago',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pago titular'
),(
	/* [CMM_ControlId] */ 2000522,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_PROGRULC_FormaPago',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Con pago'
),(
	/* [CMM_ControlId] */ 2000523,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_PROGRULC_FormaPago',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Sin pago'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

ALTER TABLE [ProgramasGruposListadoClases]
ADD [PROGRULC_CMM_FormaPagoId] [int] null;
GO

ALTER TABLE [dbo].[ProgramasGruposListadoClases]  WITH CHECK ADD  CONSTRAINT [FK_PROGRULC_CMM_FormaPagoId] FOREIGN KEY([PROGRULC_CMM_FormaPagoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProgramasGruposListadoClases] CHECK CONSTRAINT [FK_PROGRULC_CMM_FormaPagoId]
GO