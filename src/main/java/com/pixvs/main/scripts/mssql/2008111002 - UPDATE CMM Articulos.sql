
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
	/* [CMM_ControlId] */ 2000031,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_ARTT_TipoArticulo',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Comprado'
), (
	/* [CMM_ControlId] */ 2000032,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_ARTT_TipoArticulo',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Fabricado'
)
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
	/* [CMM_ControlId] */ 2000041,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_ART_TipoCosto',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Último costo'
), (
	/* [CMM_ControlId] */ 2000042,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_ART_TipoCosto',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Costo promedio'
), (
	/* [CMM_ControlId] */ 2000043,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_ART_TipoCosto',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Costo estandar'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

UPDATE [dbo].[ArticulosTipos] SET [ARTT_CMM_TipoId] = 2000031 WHERE [ARTT_CMM_TipoId] = 1000061
GO

UPDATE [dbo].[ArticulosTipos] SET [ARTT_CMM_TipoId] = 2000032 WHERE [ARTT_CMM_TipoId] = 1000062
GO

UPDATE [dbo].[Articulos] SET [ART_CMM_TipoCostoId] = 2000041 WHERE [ART_CMM_TipoCostoId] = 1000071
GO

UPDATE [dbo].[Articulos] SET [ART_CMM_TipoCostoId] = 2000042 WHERE [ART_CMM_TipoCostoId] = 1000072
GO

UPDATE [dbo].[Articulos] SET [ART_CMM_TipoCostoId] = 2000043 WHERE [ART_CMM_TipoCostoId] = 1000073
GO

UPDATE [dbo].[InventariosMovimientos] SET [IM_CMM_TipoCostoId] = 2000041 WHERE [IM_CMM_TipoCostoId] = 1000071
GO

UPDATE [dbo].[InventariosMovimientos] SET [IM_CMM_TipoCostoId] = 2000042 WHERE [IM_CMM_TipoCostoId] = 1000072
GO

UPDATE [dbo].[InventariosMovimientos] SET [IM_CMM_TipoCostoId] = 2000043 WHERE [IM_CMM_TipoCostoId] = 1000073
GO

DELETE FROM [dbo].[ControlesMaestrosMultiples] WHERE [CMM_ControlId] IN (1000061,1000062,1000071,1000072,1000073)
GO