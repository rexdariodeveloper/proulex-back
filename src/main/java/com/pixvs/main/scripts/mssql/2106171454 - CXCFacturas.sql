/**
 * Created by Angel Daniel Hernández Silva on 16/06/2021.
 * Object:  Table [dbo].[ClientesRemisiones]
 */

/***********************/
/***** CXCFacturas *****/
/***********************/

CREATE TABLE [dbo].[CXCFacturas](
	[CXCF_CXCFacturaId] [int] IDENTITY(1,1) NOT NULL,
	[CXCF_CodigoRegistro] [varchar](130) NOT NULL,
	[CXCF_CMM_TipoRegistroId] [int] NOT NULL,
	[CXCF_CLI_ClienteId] [int] NULL,
	[CXCF_FechaRegistro] [datetime] NOT NULL,
	[CXCF_FechaEmbarque] [datetime] NOT NULL,
	[CXCF_MON_MonedaId] [smallint] NULL,
	[CXCF_ParidadOficial] [decimal](10, 4) NULL,
	[CXCF_ParidadUsuario] [decimal](10, 4) NULL,
	[CXCF_MontoRegistro] [decimal](28, 2) NULL,
	[CXCF_FechaPago] [datetime] NULL,
	[CXCF_Comentarios] [varchar](255) NULL,
	[CXCF_CMM_TipoPagoId] [int] NULL,
	[CXCF_CMM_MetodoPagoId] [int] NULL,
	[CXCF_CMM_UsoCFDIId] [int] NULL,
	[CXCF_UUID] [uniqueidentifier] NULL,
	[CXCF_CMM_EstatusId] [int] NOT NULL,
	[CXCF_ARC_FacturaXMLId] [int] NULL,
	[CXCF_ARC_FacturaPDFId] [int] NULL,
    [CXCF_DiasCredito] [int] NOT NULL,
	[CXCF_FechaCancelacion] [datetime] NULL,
	[CXCF_MotivoCancelacion] [varchar](500) NULL,
	[CXCF_FechaCreacion] [datetime2](7) NOT NULL,
	[CXCF_FechaModificacion] [datetime2](7) NULL,
	[CXCF_USU_CreadoPorId] [int] NULL,
	[CXCF_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[CXCF_CXCFacturaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[CXCFacturas] ADD  CONSTRAINT [DF_CXCFacturas_CXCF_MontoRegistro]  DEFAULT ((0)) FOR [CXCF_MontoRegistro]
GO

ALTER TABLE [dbo].[CXCFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXCF_ARC_FacturaPDFId] FOREIGN KEY([CXCF_ARC_FacturaPDFId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[CXCFacturas] CHECK CONSTRAINT [FK_CXCF_ARC_FacturaPDFId]
GO

ALTER TABLE [dbo].[CXCFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXCF_ARC_FacturaXMLId] FOREIGN KEY([CXCF_ARC_FacturaXMLId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[CXCFacturas] CHECK CONSTRAINT [FK_CXCF_ARC_FacturaXMLId]
GO

ALTER TABLE [dbo].[CXCFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXCF_CMM_EstatusId] FOREIGN KEY([CXCF_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXCFacturas] CHECK CONSTRAINT [FK_CXCF_CMM_EstatusId]
GO

ALTER TABLE [dbo].[CXCFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXCF_CMM_TipoPagoId] FOREIGN KEY([CXCF_CMM_TipoPagoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXCFacturas] CHECK CONSTRAINT [FK_CXCF_CMM_TipoPagoId]
GO

ALTER TABLE [dbo].[CXCFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXCF_CMM_TipoRegistroId] FOREIGN KEY([CXCF_CMM_TipoRegistroId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXCFacturas] CHECK CONSTRAINT [FK_CXCF_CMM_TipoRegistroId]
GO

ALTER TABLE [dbo].[CXCFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXCF_CMM_MetodoPagoId] FOREIGN KEY([CXCF_CMM_MetodoPagoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXCFacturas] CHECK CONSTRAINT [FK_CXCF_CMM_MetodoPagoId]
GO

ALTER TABLE [dbo].[CXCFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXCF_CMM_UsoCFDIId] FOREIGN KEY([CXCF_CMM_UsoCFDIId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXCFacturas] CHECK CONSTRAINT [FK_CXCF_CMM_UsoCFDIId]
GO

ALTER TABLE [dbo].[CXCFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXCF_MON_MonedaId] FOREIGN KEY([CXCF_MON_MonedaId])
REFERENCES [dbo].[Monedas] ([MON_MonedaId])
GO

ALTER TABLE [dbo].[CXCFacturas] CHECK CONSTRAINT [FK_CXCF_MON_MonedaId]
GO

ALTER TABLE [dbo].[CXCFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXCF_CLI_ClienteId] FOREIGN KEY([CXCF_CLI_ClienteId])
REFERENCES [dbo].[Clientes] ([CLI_ClienteId])
GO

ALTER TABLE [dbo].[CXCFacturas] CHECK CONSTRAINT [FK_CXCF_CLI_ClienteId]
GO

ALTER TABLE [dbo].[CXCFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXCF_USU_CreadoPorId] FOREIGN KEY([CXCF_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[CXCFacturas] CHECK CONSTRAINT [FK_CXCF_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[CXCFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXCF_USU_ModificadoPorId] FOREIGN KEY([CXCF_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[CXCFacturas] CHECK CONSTRAINT [FK_CXCF_USU_ModificadoPorId]
GO

/*******************************/
/***** CXCFacturasDetalles *****/
/*******************************/

CREATE TABLE [dbo].[CXCFacturasDetalles](
	[CXCFD_CXCFacturadetalleId] [int] IDENTITY(1,1) NOT NULL,
	[CXCFD_CXCF_CXCFacturaId] [int] NOT NULL,
	[CXCFD_CLIRD_ClienteRemisionDetalleId] [int] NULL,
	[CXCFD_NumeroLinea] [smallint] NOT NULL,
	[CXCFD_Descripcion] [varchar](1000) NULL,
	[CXCFD_Cantidad] [decimal](28, 6) NULL,
	[CXCFD_PrecioUnitario] [decimal](10, 2) NOT NULL,
	[CXCFD_IVA] [decimal](3, 2) NULL,
	[CXCFD_IVAExento] [bit] NULL,
	[CXCFD_IEPS] [decimal](3, 2) NULL,
	[CXCFD_IEPSCuotaFija] [decimal](28, 2) NULL,
	[CXCFD_CMM_TipoRegistroId] [int] NULL,
	[CXCFD_Descuento] [decimal](10, 2) NOT NULL,
	[CXCFD_CMM_TipoRetencionId] [int] NULL,
	[CXCFD_ART_ArticuloId] [int] NULL,
	[CXCFD_UM_UnidadMedidaId] [smallint] NULL,
PRIMARY KEY CLUSTERED 
(
	[CXCFD_CXCFacturadetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[CXCFacturasDetalles] ADD  CONSTRAINT [DF_CXCFacturasDetalles_CXCFD_Descuento]  DEFAULT ((0)) FOR [CXCFD_Descuento]
GO

ALTER TABLE [dbo].[CXCFacturasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXCFD_ART_ArticuloId] FOREIGN KEY([CXCFD_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[CXCFacturasDetalles] CHECK CONSTRAINT [FK_CXCFD_ART_ArticuloId]
GO

ALTER TABLE [dbo].[CXCFacturasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXCFD_CMM_TipoRegistroId] FOREIGN KEY([CXCFD_CMM_TipoRegistroId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXCFacturasDetalles] CHECK CONSTRAINT [FK_CXCFD_CMM_TipoRegistroId]
GO

ALTER TABLE [dbo].[CXCFacturasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXCFD_CMM_TipoRetencionId] FOREIGN KEY([CXCFD_CMM_TipoRetencionId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXCFacturasDetalles] CHECK CONSTRAINT [FK_CXCFD_CMM_TipoRetencionId]
GO

ALTER TABLE [dbo].[CXCFacturasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXCFD_CXCF_CXCFacturaId] FOREIGN KEY([CXCFD_CXCF_CXCFacturaId])
REFERENCES [dbo].[CXCFacturas] ([CXCF_CXCFacturaId])
GO

ALTER TABLE [dbo].[CXCFacturasDetalles] CHECK CONSTRAINT [FK_CXCFD_CXCF_CXCFacturaId]
GO

ALTER TABLE [dbo].[CXCFacturasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXCFD_CLIRD_ClienteRemisionDetalleId] FOREIGN KEY([CXCFD_CLIRD_ClienteRemisionDetalleId])
REFERENCES [dbo].[ClientesRemisionesDetalles] ([CLIRD_ClienteRemisionDetalleId])
GO

ALTER TABLE [dbo].[CXCFacturasDetalles] CHECK CONSTRAINT [FK_CXCFD_CLIRD_ClienteRemisionDetalleId]
GO

ALTER TABLE [dbo].[CXCFacturasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXCFD_UM_UnidadMedidaId] FOREIGN KEY([CXCFD_UM_UnidadMedidaId])
REFERENCES [dbo].[UnidadesMedidas] ([UM_UnidadMedidaId])
GO

ALTER TABLE [dbo].[CXCFacturasDetalles] CHECK CONSTRAINT [FK_CXCFD_UM_UnidadMedidaId]
GO

ALTER TABLE [dbo].[CXCFacturasDetalles]  WITH CHECK ADD  CONSTRAINT [CHK_CXCFD_IEPS] CHECK  (([CXCFD_IEPS]>=(0) AND [CXCFD_IEPS]<=(1)))
GO

ALTER TABLE [dbo].[CXCFacturasDetalles] CHECK CONSTRAINT [CHK_CXCFD_IEPS]
GO

ALTER TABLE [dbo].[CXCFacturasDetalles]  WITH CHECK ADD  CONSTRAINT [CHK_CXCFD_IVA] CHECK  (([CXCFD_IVA]>=(0) AND [CXCFD_IVA]<=(1)))
GO

ALTER TABLE [dbo].[CXCFacturasDetalles] CHECK CONSTRAINT [CHK_CXCFD_IVA]
GO

/******************************/
/***** CMM - TipoRegistro *****/
/******************************/

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
	/* [CMM_ControlId] */ 2000470,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXCF_TipoRegistro',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Nota de debito'
),(
	/* [CMM_ControlId] */ 2000471,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXCF_TipoRegistro',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Factura CXC'
),(
	/* [CMM_ControlId] */ 2000472,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXCF_TipoRegistro',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Factura servicio CXC'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

/*******************************/
/***** CMM - TipoRetencion *****/
/*******************************/

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
	/* [CMM_ControlId] */ 2000480,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXCF_TipoRetencion',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'ISR'
),(
	/* [CMM_ControlId] */ 2000481,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXCF_TipoRetencion',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'IVA'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

/***********************************/
/***** CMM - EstatusFacturaCXC *****/
/***********************************/

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
	/* [CMM_ControlId] */ 2000490,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXCF_EstatusFactura',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Borrada'
),(
	/* [CMM_ControlId] */ 2000491,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXCF_EstatusFactura',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Abierta'
),(
	/* [CMM_ControlId] */ 2000492,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXCF_EstatusFactura',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Cerrada'
),(
	/* [CMM_ControlId] */ 2000493,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXCF_EstatusFactura',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Cancelada'
),(
	/* [CMM_ControlId] */ 2000494,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXCF_EstatusFactura',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pago Parcial'
),(
	/* [CMM_ControlId] */ 2000495,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXCF_EstatusFactura',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pagada'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

/**********************************************/
/***** Vista VW_REPORTE_EXCEL_CXCFACTURAS *****/
/**********************************************/

CREATE OR ALTER VIEW [dbo].[VW_REPORTE_EXCEL_CXCFACTURAS] AS

    SELECT
		"Folio",
		"Cliente",
		"RFC",
		"Fecha",
		"Moneda",
		SUM("Monto") AS "Monto"
	FROM(
		SELECT
			CXCF_CodigoRegistro AS "Folio",
			COALESCE(CLI_Nombre,'') AS "Cliente",
			COALESCE(CLI_RFC,'') AS "RFC",
			FORMAT(CXCF_FechaRegistro,'dd/MM/yyyy') AS "Fecha",
			MON_Nombre AS "Moneda",
			(select Total from  fn_getImpuestosArticulo(CXCFD_Cantidad,CXCFD_PrecioUnitario,CXCFD_Descuento,CXCFD_IVA,CXCFD_IEPS,CXCFD_IEPSCuotaFija)) AS "Monto"
		FROM CXCFacturas
		LEFT JOIN Clientes ON CLI_ClienteId = CXCF_CLI_ClienteId
		INNER JOIN Monedas ON MON_MonedaId = CXCF_MON_MonedaId
		INNER JOIN CXCFacturasDetalles ON CXCFD_CXCF_CXCFacturaId = CXCF_CXCFacturaId
	) CXCFD
	GROUP BY "Folio", "Cliente", "RFC", "Fecha", "Moneda"

GO

/****************************************/
/***** Vista VW_Listado_CXCFacturas *****/
/****************************************/

CREATE OR ALTER VIEW [dbo].[VW_Listado_CXCFacturas] AS

    SELECT
		id,
		codigoRegistro,
		clienteNombre,
		clienteRFC,
		fecha,
		monedaNombre,
		SUM(monto) AS monto
	FROM(
		SELECT
			CXCF_CXCFacturaId AS id,
			CXCF_CodigoRegistro AS codigoRegistro,
			COALESCE(CLI_Nombre,'') AS clienteNombre,
			COALESCE(CLI_RFC,'') AS clienteRFC,
			CXCF_FechaRegistro AS fecha,
			MON_Nombre AS monedaNombre,
			(select Total from  fn_getImpuestosArticulo(CXCFD_Cantidad,CXCFD_PrecioUnitario,CXCFD_Descuento,CXCFD_IVA,CXCFD_IEPS,CXCFD_IEPSCuotaFija)) AS monto
		FROM CXCFacturas
		LEFT JOIN Clientes ON CLI_ClienteId = CXCF_CLI_ClienteId
		INNER JOIN Monedas ON MON_MonedaId = CXCF_MON_MonedaId
		INNER JOIN CXCFacturasDetalles ON CXCFD_CXCF_CXCFacturaId = CXCF_CXCFacturaId
	) CXCFD
	GROUP BY id, codigoRegistro, clienteNombre, clienteRFC, fecha, monedaNombre

GO

/************************************************/
/***** Vista VW_Facturar_ClientesRemisiones *****/
/************************************************/

CREATE OR ALTER VIEW [dbo].[VW_Facturar_ClientesRemisiones] AS

    SELECT
		id,
		clienteId,
		fecha,
		codigo,
		SUM(detalleMonto) AS monto,
		SUM(detalleMontoPendiente) AS montoPorRelacionar
	FROM(
		SELECT
			CLIR_ClienteRemisionId AS id,
			CLIR_CLI_ClienteId AS clienteId,
			CLIR_Fecha AS fecha,
			CLIR_Codigo AS codigo,
			(select Total from fn_getImpuestosArticulo(CLIRD_Cantidad,LIPRED_PrecioVenta,0,ART_IVA,ART_IEPS,ART_IEPSCuotaFija)) AS detalleMonto,
			(select Total from fn_getImpuestosArticulo(CLIRD_Cantidad-COALESCE(SUM(CXCFD_Cantidad),0),LIPRED_PrecioVenta,0,ART_IVA,ART_IEPS,ART_IEPSCuotaFija)) AS detalleMontoPendiente
		FROM ClientesRemisiones
		INNER JOIN ClientesRemisionesDetalles ON CLIRD_CLIR_ClienteRemisionId = CLIR_ClienteRemisionId
		INNER JOIN Clientes ON CLI_ClienteId = CLIR_CLI_ClienteId
		INNER JOIN ListadosPreciosDetalles ON LIPRED_ART_ArticuloId = CLIRD_ART_ArticuloId AND LIPRED_LIPRE_ListadoPrecioId = CLI_LIPRE_ListadoPrecioId
		INNER JOIN Articulos ON ART_ArticuloId = CLIRD_ART_ArticuloId
		LEFT JOIN CXCFacturasDetalles ON CXCFD_CLIRD_ClienteRemisionDetalleId = CLIRD_ClienteRemisionDetalleId
		GROUP BY CLIR_ClienteRemisionId, CLIR_CLI_ClienteId, CLIR_Fecha, CLIR_Codigo, CLIRD_ClienteRemisionDetalleId, CLIRD_Cantidad, LIPRED_PrecioVenta, ART_IVA, ART_IEPS, ART_IEPSCuotaFija
	) CLIRD
	GROUP BY id, clienteId, fecha, codigo

GO

/********************************************************/
/***** Vista VW_Facturar_ClientesRemisionesDetalles *****/
/********************************************************/

CREATE OR ALTER VIEW [dbo].[VW_Facturar_ClientesRemisionesDetalles] AS

    SELECT
		CLIRD_ClienteRemisionDetalleId AS id,
		CLIR_ClienteRemisionId AS clienteRemisionId,
		CLIR_Codigo AS codigoRemision,
		ART_CodigoArticulo AS articuloCodigo,
		ART_NombreArticulo AS articuloNombre,
		UM_Nombre AS unidadMedidaNombre,
		CLIRD_Cantidad-COALESCE(SUM(CXCFD_Cantidad),0) AS cantidad,
		LIPRED_PrecioVenta AS precioUnitario
	FROM ClientesRemisiones
	INNER JOIN ClientesRemisionesDetalles ON CLIRD_CLIR_ClienteRemisionId = CLIR_ClienteRemisionId
	INNER JOIN Clientes ON CLI_ClienteId = CLIR_CLI_ClienteId
	INNER JOIN ListadosPreciosDetalles ON LIPRED_ART_ArticuloId = CLIRD_ART_ArticuloId AND LIPRED_LIPRE_ListadoPrecioId = CLI_LIPRE_ListadoPrecioId
	INNER JOIN Articulos ON ART_ArticuloId = CLIRD_ART_ArticuloId
	LEFT JOIN CXCFacturasDetalles ON CXCFD_CLIRD_ClienteRemisionDetalleId = CLIRD_ClienteRemisionDetalleId
	INNER JOIN UnidadesMedidas ON UM_UnidadMedidaId = ART_UM_UnidadMedidaInventarioId
	GROUP BY CLIRD_ClienteRemisionDetalleId, CLIR_ClienteRemisionId, CLIR_Codigo, ART_CodigoArticulo, ART_NombreArticulo, UM_Nombre, CLIRD_Cantidad, LIPRED_PrecioVenta

GO

/***********************************************/
/***** MenuListadosGenerales - MetodosPago *****/
/***********************************************/

INSERT [dbo].[MenuListadosGenerales] (
	-- [MLG_ListadoGeneralNodoId],
	[MLG_NodoPadreId],
	[MLG_Titulo],
	[MLG_TituloEN],
	[MLG_Activo],
	[MLG_Icono],
	[MLG_Orden],
	[MLG_CMM_TipoNodoId],
	[MLG_NombreTablaCatalogo],
	[MLG_CMM_ControlCatalogo],
	[MLG_PermiteBorrar],
	[MLG_UrlAPI],
	[MLG_FechaCreacion]
) VALUES (
	-- /* [MLG_ListadoGeneralNodoId] */ 8,
	/* [MLG_NodoPadreId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='VENTAS' AND MLG_NodoPadreId IS NULL),
	/* [MLG_Titulo] */ 'Métodos de pago',
	/* [MLG_TituloEN] */ 'Payment methods',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'attach_money',
	/* [MLG_Orden] */ 1,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_CXC_MetodoPago',
	/* [MLG_PermiteBorrar] */ 0,
	/* [MLG_UrlAPI] */ '/api/v1/cmm',
	/* [MLG_FechaCreacion] */ GETDATE()
)
GO

INSERT [dbo].[MenuListadosGeneralesDetalles] (
	[MLGD_MLG_ListadoGeneralNodoId],
	[MLGD_JsonConfig],
	[MLGD_JsonListado],
	[MLGD_CampoTabla],
	[MLGD_CampoModelo]
) VALUES (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Métodos de pago' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='VENTAS' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Código",' +
    '"inputType": "text",' +
    '"name": "referencia",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Código requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(5)","message": "El código no debe sobrepasar los 5 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "referencia",' +
    '"title": "Código",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Referencia',
	/* [MLGD_CampoModelo] */ 'referencia'
), (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Métodos de pago' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='VENTAS' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Descripción",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Descripción requerida"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "La descripción no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Descripción",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
)
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_MetodoPago'
  ,'Pago en una sola exhibición' -- valor
  ,'PUE' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_MetodoPago'
  ,'Pago en parcialidades o diferido' -- valor
  ,'PPD' -- referencia
  ,NULL
GO

/********************************************/
/***** MenuListadosGenerales - UsosCFDI *****/
/********************************************/

INSERT [dbo].[MenuListadosGenerales] (
	-- [MLG_ListadoGeneralNodoId],
	[MLG_NodoPadreId],
	[MLG_Titulo],
	[MLG_TituloEN],
	[MLG_Activo],
	[MLG_Icono],
	[MLG_Orden],
	[MLG_CMM_TipoNodoId],
	[MLG_NombreTablaCatalogo],
	[MLG_CMM_ControlCatalogo],
	[MLG_PermiteBorrar],
	[MLG_UrlAPI],
	[MLG_FechaCreacion]
) VALUES (
	-- /* [MLG_ListadoGeneralNodoId] */ 8,
	/* [MLG_NodoPadreId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='VENTAS' AND MLG_NodoPadreId IS NULL),
	/* [MLG_Titulo] */ 'Uso de CFDI',
	/* [MLG_TituloEN] */ 'Uso de CFDI',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'attach_money',
	/* [MLG_Orden] */ 1,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_CXC_UsoCFDI',
	/* [MLG_PermiteBorrar] */ 0,
	/* [MLG_UrlAPI] */ '/api/v1/cmm',
	/* [MLG_FechaCreacion] */ GETDATE()
)
GO

INSERT [dbo].[MenuListadosGeneralesDetalles] (
	[MLGD_MLG_ListadoGeneralNodoId],
	[MLGD_JsonConfig],
	[MLGD_JsonListado],
	[MLGD_CampoTabla],
	[MLGD_CampoModelo]
) VALUES (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Uso de CFDI' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='VENTAS' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Código",' +
    '"inputType": "text",' +
    '"name": "referencia",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Código requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(5)","message": "El código no debe sobrepasar los 5 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "referencia",' +
    '"title": "Código",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Referencia',
	/* [MLGD_CampoModelo] */ 'referencia'
), (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Uso de CFDI' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='VENTAS' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Descripción",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Descripción requerida"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "La descripción no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Descripción",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
)
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Adquisición de mercancias' -- valor
  ,'G01' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Devoluciones, descuentos o bonificaciones' -- valor
  ,'G02' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Gastos en general' -- valor
  ,'G03' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Construcciones' -- valor
  ,'I01' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Mobilario y equipo de oficina por inversiones' -- valor
  ,'I02' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Equipo de transporte' -- valor
  ,'I03' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Equipo de computo y accesorios' -- valor
  ,'I04' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Dados, troqueles, moldes, matrices y herramental' -- valor
  ,'I05' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Comunicaciones telefónicas' -- valor
  ,'I06' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Comunicaciones satelitales' -- valor
  ,'I07' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Otra maquinaria y equipo' -- valor
  ,'I08' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Honorarios médicos, dentales y gastos hospitalarios.' -- valor
  ,'D01' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Gastos médicos por incapacidad o discapacidad' -- valor
  ,'D02' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Gastos funerales.' -- valor
  ,'D03' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Donativos.' -- valor
  ,'D04' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Intereses reales efectivamente pagados por créditos hipotecarios (casa habitación).' -- valor
  ,'D05' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Aportaciones voluntarias al SAR.' -- valor
  ,'D06' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Primas por seguros de gastos médicos.' -- valor
  ,'D07' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Gastos de transportación escolar obligatoria.' -- valor
  ,'D08' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Depósitos en cuentas para el ahorro, primas que tengan como base planes de pensiones.' -- valor
  ,'D09' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Pagos por servicios educativos (colegiaturas)' -- valor
  ,'D10' -- referencia
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CXC_UsoCFDI'
  ,'Por definir' -- valor
  ,'P01' -- referencia
  ,NULL
GO

/*************************/
/***** MenuPrincipal *****/
/*************************/

INSERT [dbo].[MenuPrincipal] (
    [MP_Activo],
    [MP_FechaCreacion],
    [MP_Icono],
    [MP_NodoPadreId],
    [MP_Orden],
    [MP_CMM_SistemaAccesoId],
    [MP_Titulo],
    [MP_TituloEN],
    [MP_Tipo],
    [MP_URL]
) 
VALUES (
    1, -- [MP_Activo]
    GETDATE(), -- [MP_FechaCreacion]
    N'assignment', -- [MP_Icono]
    (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Ventas'), -- [MP_NodoPadreId]
    6, -- [MP_Orden]
    1000021, -- [MP_CMM_SistemaAccesoId]
    N'Facturación', -- [MP_Titulo]
    N'Invoice', -- [MP_TituloEN]
    N'item', -- [MP_Tipo]
    N'/app/ventas/facturacion' -- [MP_URL]
)
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Facturación' and MP_Icono = 'assignment' and MP_Orden = 6)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
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
           ('CXCFacturas'
           ,'CXCF'
           ,1
           ,6
           ,1)
GO