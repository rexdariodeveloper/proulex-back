/**
 * Created by Angel Daniel Hernández Silva on 30/06/2021.
 * Object:  Table [dbo].[OrdenesVenta]
 */

/************************/
/***** OrdenesVenta *****/
/************************/

CREATE TABLE [dbo].[OrdenesVenta](
	[OV_OrdenVentaId] [int] IDENTITY(1,1) NOT NULL,
	[OV_Codigo] [varchar](150) NOT NULL,
	[OV_SUC_SucursalId] [int] NOT NULL,
	[OV_CLI_ClienteId] [int] NULL,
	[OV_FechaOV] [date] NOT NULL,
	[OV_FechaRequerida] [date] NOT NULL,
	[OV_DireccionOV] [varchar](150) NULL,
	[OV_EnviarA] [varchar](150) NULL,
	[OV_MON_MonedaId] [smallint] NOT NULL,
	[OV_DiazCredito] [int] NOT NULL,
	[OV_MPPV_MedioPagoPVId] [int] NOT NULL,
	[OV_ReferenciaPago] [varchar](50) NOT NULL,
	[OV_Comentario] [varchar](3000) NULL,
	[OV_CMM_EstatusId] [int] NOT NULL,
	[OV_FechaCreacion] [datetime2](7) NOT NULL,
	[OV_FechaModificacion] [datetime2](7) NULL,
	[OV_USU_CreadoPorId] [int] NULL,
	[OV_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[OV_OrdenVentaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

-- Constraints FK

ALTER TABLE [dbo].[OrdenesVenta]  WITH CHECK ADD  CONSTRAINT [FK_OV_SUC_SucursalId] FOREIGN KEY([OV_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO

ALTER TABLE [dbo].[OrdenesVenta] CHECK CONSTRAINT [FK_OV_SUC_SucursalId]
GO

ALTER TABLE [dbo].[OrdenesVenta]  WITH CHECK ADD  CONSTRAINT [FK_OV_CMM_EstatusId] FOREIGN KEY([OV_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[OrdenesVenta] CHECK CONSTRAINT [FK_OV_CMM_EstatusId]
GO

ALTER TABLE [dbo].[OrdenesVenta]  WITH CHECK ADD  CONSTRAINT [FK_OV_MPPV_MedioPagoPVId] FOREIGN KEY([OV_MPPV_MedioPagoPVId])
REFERENCES [dbo].[MediosPagoPV] ([MPPV_MedioPagoPVId])
GO

ALTER TABLE [dbo].[OrdenesVenta] CHECK CONSTRAINT [FK_OV_MPPV_MedioPagoPVId]
GO

ALTER TABLE [dbo].[OrdenesVenta]  WITH CHECK ADD  CONSTRAINT [FK_OV_MON_MonedaId] FOREIGN KEY([OV_MON_MonedaId])
REFERENCES [dbo].[Monedas] ([MON_MonedaId])
GO

ALTER TABLE [dbo].[OrdenesVenta] CHECK CONSTRAINT [FK_OV_MON_MonedaId]
GO

ALTER TABLE [dbo].[OrdenesVenta]  WITH CHECK ADD  CONSTRAINT [FK_OV_CLI_ClienteId] FOREIGN KEY([OV_CLI_ClienteId])
REFERENCES [dbo].[Clientes] ([CLI_ClienteId])
GO

ALTER TABLE [dbo].[OrdenesVenta] CHECK CONSTRAINT [FK_OV_CLI_ClienteId]
GO

ALTER TABLE [dbo].[OrdenesVenta]  WITH CHECK ADD  CONSTRAINT [FK_OV_USU_CreadoPorId] FOREIGN KEY([OV_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[OrdenesVenta] CHECK CONSTRAINT [FK_OV_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[OrdenesVenta]  WITH CHECK ADD  CONSTRAINT [FK_OV_USU_ModificadoPorId] FOREIGN KEY([OV_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[OrdenesVenta] CHECK CONSTRAINT [FK_OV_USU_ModificadoPorId]
GO

/*********************************/
/***** OrdenesVentaDetalles *****/
/*********************************/

CREATE TABLE [dbo].[OrdenesVentaDetalles](
	[OVD_OrdenVentaDetalleId] [int] IDENTITY(1,1) NOT NULL,
	[OVD_OV_OrdenVentaId] [int] NOT NULL,
	[OVD_ART_ArticuloId] [int] NOT NULL,
	[OVD_UM_UnidadMedidaId] [smallint] NOT NULL,
	[OVD_FactorConversion] [decimal](28, 6) NOT NULL,
	[OVD_Cantidad] [decimal](28, 6) NOT NULL,
	[OVD_Precio] [decimal](10, 2) NOT NULL,
	[OVD_Descuento] [decimal](10, 2) NULL,
	[OVD_IVA] [decimal](3, 2) NULL,
	[OVD_IVAExento] [bit] NULL,
	[OVD_IEPS] [decimal](3, 2) NULL,
	[OVD_IEPSCuotaFija] [decimal](28, 2) NULL,
    [OVD_CuentaOV] [varchar](18) NULL,
	[OVD_Comentarios] [varchar](255) NULL,
	[OVD_FechaCreacion] [datetime2](7) NOT NULL,
	[OVD_FechaModificacion] [datetime2](7) NULL,
	[OVD_USU_CreadoPorId] [int] NULL,
	[OVD_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[OVD_OrdenVentaDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[OrdenesVentaDetalles]  WITH CHECK ADD  CONSTRAINT [FK_OVD_ART_ArticuloId] FOREIGN KEY([OVD_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[OrdenesVentaDetalles] CHECK CONSTRAINT [FK_OVD_ART_ArticuloId]
GO

ALTER TABLE [dbo].[OrdenesVentaDetalles]  WITH CHECK ADD  CONSTRAINT [FK_OVD_OV_OrdenVentaId] FOREIGN KEY([OVD_OV_OrdenVentaId])
REFERENCES [dbo].[OrdenesVenta] ([OV_OrdenVentaId])
GO

ALTER TABLE [dbo].[OrdenesVentaDetalles] CHECK CONSTRAINT [FK_OVD_OV_OrdenVentaId]
GO

ALTER TABLE [dbo].[OrdenesVentaDetalles]  WITH CHECK ADD  CONSTRAINT [FK_OVD_UM_UnidadMedidaId] FOREIGN KEY([OVD_UM_UnidadMedidaId])
REFERENCES [dbo].[UnidadesMedidas] ([UM_UnidadMedidaId])
GO

ALTER TABLE [dbo].[OrdenesVentaDetalles] CHECK CONSTRAINT [FK_OVD_UM_UnidadMedidaId]
GO

ALTER TABLE [dbo].[OrdenesVentaDetalles]  WITH CHECK ADD  CONSTRAINT [FK_OVD_USU_CreadoPorId] FOREIGN KEY([OVD_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[OrdenesVentaDetalles] CHECK CONSTRAINT [FK_OVD_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[OrdenesVentaDetalles]  WITH CHECK ADD  CONSTRAINT [FK_OVD_USU_ModificadoPorId] FOREIGN KEY([OVD_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[OrdenesVentaDetalles] CHECK CONSTRAINT [FK_OVD_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[OrdenesVentaDetalles]  WITH CHECK ADD  CONSTRAINT [CHK_OVD_IEPS] CHECK  (([OVD_IEPS]>=(0) AND [OVD_IEPS]<=(1)))
GO

ALTER TABLE [dbo].[OrdenesVentaDetalles] CHECK CONSTRAINT [CHK_OVD_IEPS]
GO

ALTER TABLE [dbo].[OrdenesVentaDetalles]  WITH CHECK ADD  CONSTRAINT [CHK_OVD_IVA] CHECK  (([OVD_IVA]>=(0) AND [OVD_IVA]<=(1)))
GO

ALTER TABLE [dbo].[OrdenesVentaDetalles] CHECK CONSTRAINT [CHK_OVD_IVA]
GO

/***************************/
/***** CMM - EstatusOV *****/
/***************************/

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
	/* [CMM_ControlId] */ 2000500,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OV_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'En edición'
)
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
	/* [CMM_ControlId] */ 2000501,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OV_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Borrada'
)
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
	/* [CMM_ControlId] */ 2000502,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OV_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Abierta'
)
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
	/* [CMM_ControlId] */ 2000503,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OV_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Cerrada'
)
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
	/* [CMM_ControlId] */ 2000504,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OV_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Cancelada'
)
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
	/* [CMM_ControlId] */ 2000505,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OV_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Embarque parcial'
)
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
	/* [CMM_ControlId] */ 2000506,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OV_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Embarcada'
)
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
	/* [CMM_ControlId] */ 2000507,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OV_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Facturada'
)
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
	/* [CMM_ControlId] */ 2000508,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OV_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pagada'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

/*************************/
/***** Autonumericos *****/
/*************************/

INSERT INTO [dbo].[Autonumericos]
           ([AUT_Nombre]
           ,[AUT_Prefijo]
           ,[AUT_Siguiente]
           ,[AUT_Digitos]
           ,[AUT_Activo])
     VALUES
           ('OrdenesVenta'
           ,'OV'
           ,1
           ,6
           ,1)
GO