
/****** Object:  Table [dbo].[InventariosMovimientos]    Script Date: 30/07/2020 02:13:51 p. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[InventariosMovimientos](
	[IM_InventarioMovimientoId] [int] IDENTITY(1,1) NOT NULL,
	[IM_ART_ArticuloId] [int] NOT NULL,
	[IM_LOC_LocalidadId] [int] NOT NULL,
	[IM_Cantidad] [numeric](19, 6) NOT NULL,
	[IM_CostoUnitario] [numeric](19, 2) NOT NULL,
	[IM_PrecioUnitario] [numeric](19, 2) NULL,
	[IM_Razon] [varchar](1000) NOT NULL,
	[IM_Referencia] [varchar](150) NOT NULL,
	[IM_ReferenciaMovtoId] [int] NULL,
	[IM_UM_UnidadMedidaId] [int] NOT NULL,
	[IM_CMM_TipoCostoId] [int] NOT NULL,
	[IM_CMM_TipoMovimientoId] [int] NOT NULL,	
	[IM_USU_CreadoPorId] [int] NULL,
	[IM_USU_ModificadoPorId] [int] NULL,
	[IM_FechaCreacion] [datetime2](7) NOT NULL,
	[IM_FechaModificacion] [datetime2](7) NULL,

PRIMARY KEY CLUSTERED 
(
	[IM_InventarioMovimientoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[InventariosMovimientos]  WITH CHECK ADD  CONSTRAINT [FK_IM_USU_ModificadoPorIdIM_USU_ModificadoPorId] FOREIGN KEY([IM_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[InventariosMovimientos] CHECK CONSTRAINT [FK_IM_USU_ModificadoPorIdIM_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[InventariosMovimientos]  WITH CHECK ADD  CONSTRAINT [FK_IM_CMM_TipoCostoId] FOREIGN KEY([IM_CMM_TipoCostoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[InventariosMovimientos] CHECK CONSTRAINT [FK_IM_CMM_TipoCostoId]
GO

ALTER TABLE [dbo].[InventariosMovimientos]  WITH CHECK ADD  CONSTRAINT [FK_IM_CMM_TipoMovimientoId] FOREIGN KEY([IM_CMM_TipoMovimientoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[InventariosMovimientos] CHECK CONSTRAINT [FK_IM_CMM_TipoMovimientoId]
GO

ALTER TABLE [dbo].[InventariosMovimientos]  WITH CHECK ADD  CONSTRAINT [FK_IM_ART_ArticuloId] FOREIGN KEY([IM_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[InventariosMovimientos] CHECK CONSTRAINT [FK_IM_ART_ArticuloId]
GO

ALTER TABLE [dbo].[InventariosMovimientos]  WITH CHECK ADD  CONSTRAINT [FK_IM_USU_CreadoPorId] FOREIGN KEY([IM_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[InventariosMovimientos] CHECK CONSTRAINT [FK_IM_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[InventariosMovimientos]  WITH CHECK ADD  CONSTRAINT [FK_IM_LOC_LocalidadId] FOREIGN KEY([IM_LOC_LocalidadId])
REFERENCES [dbo].[Localidades] ([LOC_LocalidadId])
GO

ALTER TABLE [dbo].[InventariosMovimientos] CHECK CONSTRAINT [FK_IM_LOC_LocalidadId]
GO




/****** Object:  Table [dbo].[LocalidadesArticulosAcumulados]    Script Date: 30/07/2020 02:19:48 p. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[LocalidadesArticulosAcumulados](
	[LOCAA_LocalidadArticuloId] [int] IDENTITY(1,1) NOT NULL,
	[LOCAA_ART_ArticuloId] [int] NOT NULL,	
	[LOCAA_LOC_LocalidadId] [int] NOT NULL,
	[LOCAA_Cantidad] [numeric](19, 6) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[LOCAA_LocalidadArticuloId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[LocalidadesArticulosAcumulados]  WITH CHECK ADD  CONSTRAINT [FKejv56k97mfs7tadrulu1avl8m] FOREIGN KEY([LOCAA_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[LocalidadesArticulosAcumulados] CHECK CONSTRAINT [FKejv56k97mfs7tadrulu1avl8m]
GO

ALTER TABLE [dbo].[LocalidadesArticulosAcumulados]  WITH CHECK ADD  CONSTRAINT [FKptpr8ks73iuytqx0wddcf3p44] FOREIGN KEY([LOCAA_LOC_LocalidadId])
REFERENCES [dbo].[Localidades] ([LOC_LocalidadId])
GO

ALTER TABLE [dbo].[LocalidadesArticulosAcumulados] CHECK CONSTRAINT [FKptpr8ks73iuytqx0wddcf3p44]
GO



SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON 
GO

INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor]) 
VALUES (2000011, 1, N'CMM_IM_TipoMovimiento', NULL, CAST(N'2020-06-29T05:50:50.0633333' AS DateTime2), NULL, NULL, NULL, 1, N'Ajuste Inventario')
GO
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor]) 
VALUES (2000012, 1, N'CMM_IM_TipoMovimiento', NULL, CAST(N'2020-06-29T05:50:50.0633333' AS DateTime2), NULL, NULL, NULL, 1, N'Transferencia')
GO
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor]) 
VALUES (2000013, 1, N'CMM_IM_TipoMovimiento', NULL, CAST(N'2020-06-29T05:50:50.0633333' AS DateTime2), NULL, NULL, NULL, 1, N'Recibo OC')
GO
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor]) 
VALUES (2000014, 1, N'CMM_IM_TipoMovimiento', NULL, CAST(N'2020-06-29T05:50:50.0633333' AS DateTime2), NULL, NULL, NULL, 1, N'Devolución OC')
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO
