ALTER TABLE [dbo].[Sucursales] ADD [SUC_CMM_TipoSucursalId]  [int]  NULL
GO

ALTER TABLE [dbo].[Sucursales]  WITH CHECK ADD  CONSTRAINT [FK_SUC_CMM_TipoSucursalId] FOREIGN KEY([SUC_CMM_TipoSucursalId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Sucursales] CHECK CONSTRAINT [FK_SUC_CMM_TipoSucursalId]
GO


SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON 
GO

INSERT [dbo].[ControlesMaestrosMultiples] (
	[CMM_ControlId],
	[CMM_Activo],
	[CMM_Control],
	[CMM_FechaCreacion],
	[CMM_Referencia],
	[CMM_Sistema],
	[CMM_Valor]
) VALUES (
	/* [CMM_ControlId] */ 2000211,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_SUC_TipoSucursal',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'ZMG'
),(
	/* [CMM_ControlId] */ 2000212,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_SUC_TipoSucursal',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Foránea'
),(
	/* [CMM_ControlId] */ 2000213,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_SUC_TipoSucursal',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Alliance'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO