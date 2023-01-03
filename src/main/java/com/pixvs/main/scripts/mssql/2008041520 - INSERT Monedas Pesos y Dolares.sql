ALTER TABLE [dbo].[Monedas] ADD [MON_Sistema] [bit] NOT NULL
GO

ALTER TABLE [dbo].[Monedas] WITH CHECK ADD CONSTRAINT [DF_Monedas_MON_Sistema]  DEFAULT (0) FOR [MON_Sistema]
GO

SET IDENTITY_INSERT [dbo].[Monedas] ON 
GO

INSERT [dbo].[Monedas] (
	[MON_MonedaId],
	[MON_Codigo],
	[MON_Nombre],
	[MON_Simbolo],
	[MON_Predeterminada],
	[MON_Activo],
	[MON_Sistema],
	[MON_FechaCreacion],
	[MON_USU_CreadoPorId]
) VALUES (
	/* [MON_MonedaId] */ 1,
	/* [MON_Codigo] */ 'MXN',
	/* [MON_Nombre] */ 'Peso',
	/* [MON_Simbolo] */ '$',
	/* [MON_Predeterminada] */ 1,
	/* [MON_Activo] */ 1,
	/* [MON_Sistema] */ 1,
	/* [MON_FechaCreacion] */ GETDATE(),
	/* [MON_USU_CreadoPorId] */ 1
), (
	/* [MON_MonedaId] */ 2,
	/* [MON_Codigo] */ 'USD',
	/* [MON_Nombre] */ 'Dólar',
	/* [MON_Simbolo] */ '$',
	/* [MON_Predeterminada] */ 0,
	/* [MON_Activo] */ 1,
	/* [MON_Sistema] */ 1,
	/* [MON_FechaCreacion] */ GETDATE(),
	/* [MON_USU_CreadoPorId] */ 1
)

SET IDENTITY_INSERT [dbo].[Monedas] OFF
GO