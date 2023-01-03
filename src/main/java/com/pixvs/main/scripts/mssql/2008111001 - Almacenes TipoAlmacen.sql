
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
	/* [CMM_ControlId] */ 2000051,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_ALM_TipoAlmacen',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Normal'
), (
	/* [CMM_ControlId] */ 2000052,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_ALM_TipoAlmacen',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Transito'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

ALTER TABLE [dbo].[Almacenes] ADD [ALM_CMM_TipoAlmacenId] int NULL
GO

UPDATE [dbo].[Almacenes] SET [ALM_CMM_TipoAlmacenId] = 2000051
GO

ALTER TABLE [dbo].[Almacenes] ALTER COLUMN [ALM_CMM_TipoAlmacenId] int NOT NULL
GO