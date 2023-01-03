
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
	/* [CMM_ControlId] */ 1000061,
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
	/* [CMM_ControlId] */ 1000062,
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

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO


SET IDENTITY_INSERT [dbo].[ArticulosTipos] ON 
GO

INSERT [dbo].[ArticulosTipos] (
	[ARTT_ArticuloTipoId],
	[ARTT_CMM_TipoId],
	[ARTT_Descripcion]
) VALUES (
	/* [ARTT_ArticuloTipoId] */ 1,
	/* [ARTT_CMM_TipoId] */ 1000061,
	/* [ARTT_Descripcion] */ 'PT Comprado'
), (
	/* [ARTT_ArticuloTipoId] */ 2,
	/* [ARTT_CMM_TipoId] */ 1000061,
	/* [ARTT_Descripcion] */ 'MP'
), (
	/* [ARTT_ArticuloTipoId] */ 3,
	/* [ARTT_CMM_TipoId] */ 1000061,
	/* [ARTT_Descripcion] */ 'Misceláneo'
)
GO

INSERT [dbo].[ArticulosTipos] (
	[ARTT_ArticuloTipoId],
	[ARTT_CMM_TipoId],
	[ARTT_Descripcion]
) VALUES (
	/* [ARTT_ArticuloTipoId] */ 4,
	/* [ARTT_CMM_TipoId] */ 1000062,
	/* [ARTT_Descripcion] */ 'PT Fabricado'
)
GO

SET IDENTITY_INSERT [dbo].[ArticulosTipos] OFF
GO

SET IDENTITY_INSERT [dbo].[ArticulosSubtipos] ON 
GO

INSERT [dbo].[ArticulosSubtipos] (
	[ARTST_ArticuloSubtipoId],
	[ARTST_ARTT_ArticuloTipoId],
	[ARTST_Descripcion]
) VALUES (
	/* [ARTST_ArticuloSubtipoId] */ 1,
	/* [ARTST_ARTT_ArticuloTipoId] */ 1,
	/* [ARTST_Descripcion] */ 'General'
), (
	/* [ARTST_ArticuloSubtipoId] */ 2,
	/* [ARTST_ARTT_ArticuloTipoId] */ 1,
	/* [ARTST_Descripcion] */ 'Libro'
)
GO

SET IDENTITY_INSERT [dbo].[ArticulosSubtipos] OFF
GO