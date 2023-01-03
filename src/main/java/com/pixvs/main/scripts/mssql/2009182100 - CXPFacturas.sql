/**
 * Created by Angel Daniel Hernández Silva on 08/09/2020.
 * Object:  Table [dbo].[CXPFacturas]
 */


CREATE TABLE [dbo].[CXPFacturas](
	[CXPF_CXPFacturaId] [int] IDENTITY(1,1) NOT NULL ,
	[CXPF_CodigoRegistro] [varchar]  (130) NOT NULL ,
	[CXPF_CMM_TipoRegistroId] [int]  NOT NULL ,
	[CXPF_PRO_ProveedorId] [int]  NULL ,
	[CXPF_FechaRegistro] [datetime]  NOT NULL ,
	[CXPF_FechaReciboRegistro] [datetime]  NOT NULL ,
	[CXPF_MON_MonedaId] [smallint]  NULL ,
	[CXPF_ParidadOficial] [decimal]  (10,4) NULL ,
	[CXPF_ParidadUsuario] [decimal]  (10,4) NULL ,
	[CXPF_CMM_TerminoPagoId] [int]  NULL ,
	[CXPF_RemitirA] [varchar]  (250) NULL ,
	[CXPF_MontoRegistro] [decimal]  (28,2) NULL ,
	[CXPF_FechaPago] [datetime]  NULL ,
	[CXPF_Comentarios] [varchar]  (255) NULL ,
	[CXPF_CMM_TipoPagoId] [int]  NULL ,
	[CXPF_UUID] [uniqueidentifier] NOT NULL ,
	[CXPF_CMM_EstatusId] [int]  NOT NULL ,
	[CXPF_ARC_FacturaXMLId] [int] NOT NULL,
	[CXPF_ARC_FacturaPDFId] [int] NOT NULL,
	[CXPF_FechaCreacion] [datetime2](7) NOT NULL,
	[CXPF_FechaModificacion] [datetime2](7) NULL,
	[CXPF_USU_CreadoPorId] [int] NULL,
	[CXPF_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[CXPF_CXPFacturaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[CXPFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXPF_CMM_TipoRegistroId] FOREIGN KEY([CXPF_CMM_TipoRegistroId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXPFacturas] CHECK CONSTRAINT [FK_CXPF_CMM_TipoRegistroId]
GO

ALTER TABLE [dbo].[CXPFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXPF_PRO_ProveedorId] FOREIGN KEY([CXPF_PRO_ProveedorId])
REFERENCES [dbo].[Proveedores] ([PRO_ProveedorId])
GO

ALTER TABLE [dbo].[CXPFacturas] CHECK CONSTRAINT [FK_CXPF_PRO_ProveedorId]
GO

ALTER TABLE [dbo].[CXPFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXPF_MON_MonedaId] FOREIGN KEY([CXPF_MON_MonedaId])
REFERENCES [dbo].[Monedas] ([MON_MonedaId])
GO

ALTER TABLE [dbo].[CXPFacturas] CHECK CONSTRAINT [FK_CXPF_MON_MonedaId]
GO

ALTER TABLE [dbo].[CXPFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXPF_CMM_TerminoPagoId] FOREIGN KEY([CXPF_CMM_TerminoPagoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXPFacturas] CHECK CONSTRAINT [FK_CXPF_CMM_TerminoPagoId]
GO

ALTER TABLE [dbo].[CXPFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXPF_CMM_TipoPagoId] FOREIGN KEY([CXPF_CMM_TipoPagoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXPFacturas] CHECK CONSTRAINT [FK_CXPF_CMM_TipoPagoId]
GO

ALTER TABLE [dbo].[CXPFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXPF_CMM_EstatusId] FOREIGN KEY([CXPF_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXPFacturas] CHECK CONSTRAINT [FK_CXPF_CMM_EstatusId]
GO

ALTER TABLE [dbo].[CXPFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXPF_ARC_FacturaXMLId] FOREIGN KEY([CXPF_ARC_FacturaXMLId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[CXPFacturas] CHECK CONSTRAINT [FK_CXPF_ARC_FacturaXMLId]
GO

ALTER TABLE [dbo].[CXPFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXPF_ARC_FacturaPDFId] FOREIGN KEY([CXPF_ARC_FacturaPDFId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[CXPFacturas] CHECK CONSTRAINT [FK_CXPF_ARC_FacturaPDFId]
GO


ALTER TABLE [dbo].[CXPFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXPF_USU_ModificadoPorId] FOREIGN KEY([CXPF_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[CXPFacturas] CHECK CONSTRAINT [FK_CXPF_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[CXPFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXPF_USU_CreadoPorId] FOREIGN KEY([CXPF_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[CXPFacturas] CHECK CONSTRAINT [FK_CXPF_USU_CreadoPorId]
GO

-- DEFAULTs

ALTER TABLE [dbo].[CXPFacturas] WITH CHECK ADD CONSTRAINT [DF_CXPFacturas_CXPF_FechaReciboRegistro]  DEFAULT (GETDATE()) FOR [CXPF_FechaReciboRegistro]
GO

ALTER TABLE [dbo].[CXPFacturas] WITH CHECK ADD CONSTRAINT [DF_CXPFacturas_CXPF_MontoRegistro]  DEFAULT (0) FOR [CXPF_MontoRegistro]
GO


















CREATE TABLE [dbo].[CXPFacturasDetalles](
	[CXPFD_CXPFacturadetalleId] [int] IDENTITY(1,1) NOT NULL ,
	[CXPFD_CXPF_CXPFacturaId] [int]  NOT NULL ,
	[CXPFD_OCR_OrdenCompraReciboId] [int]  NULL ,
	[CXPFD_NumeroLinea] [smallint]  NOT NULL ,
	[CXPFD_Descripcion] [varchar]  (255) NULL ,
	[CXPFD_Cantidad] [decimal]  (28,6) NULL ,
	[CXPFD_PrecioUnitario] [decimal]  (10,2) NOT NULL ,
	[CXPFD_IVA] [decimal]  (3,2) NULL ,
	[CXPFD_IVAExento] [bit]  NULL ,
	[CXPFD_IEPS] [decimal]  (3,2) NULL ,
	[CXPFD_IEPSCuotaFija] [decimal]  (28,2) NULL ,
	[CXPFD_CMM_TipoRegistroId] [int]  NULL ,
	[CXPFD_Descuento] [decimal]  (10,2) NOT NULL ,
	[CXPFD_CMM_TipoRetencionId] [int]  NULL 
PRIMARY KEY CLUSTERED 
(
	[CXPFD_CXPFacturadetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO


ALTER TABLE [dbo].[CXPFacturasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXPFD_CXPF_CXPFacturaId] FOREIGN KEY([CXPFD_CXPF_CXPFacturaId])
REFERENCES [dbo].[CXPFacturas] ([CXPF_CXPFacturaId])
GO

ALTER TABLE [dbo].[CXPFacturasDetalles] CHECK CONSTRAINT [FK_CXPFD_CXPF_CXPFacturaId]
GO

ALTER TABLE [dbo].[CXPFacturasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXPFD_OCR_OrdenCompraReciboId] FOREIGN KEY([CXPFD_OCR_OrdenCompraReciboId])
REFERENCES [dbo].[OrdenesCompraRecibos] ([OCR_OrdenCompraReciboId])
GO

ALTER TABLE [dbo].[CXPFacturasDetalles] CHECK CONSTRAINT [FK_CXPFD_OCR_OrdenCompraReciboId]
GO

ALTER TABLE [dbo].[CXPFacturasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXPFD_CMM_TipoRegistroId] FOREIGN KEY([CXPFD_CMM_TipoRegistroId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXPFacturasDetalles] CHECK CONSTRAINT [FK_CXPFD_CMM_TipoRegistroId]
GO

ALTER TABLE [dbo].[CXPFacturasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXPFD_CMM_TipoRetencionId] FOREIGN KEY([CXPFD_CMM_TipoRetencionId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXPFacturasDetalles] CHECK CONSTRAINT [FK_CXPFD_CMM_TipoRetencionId]
GO

-- DEFAULTs

ALTER TABLE [dbo].[CXPFacturasDetalles] WITH CHECK ADD CONSTRAINT [DF_CXPFacturasDetalles_CXPFD_Descuento]  DEFAULT (0) FOR [CXPFD_Descuento]
GO

-- CHECKs

ALTER TABLE [dbo].[CXPFacturasDetalles] WITH CHECK ADD CONSTRAINT [CHK_CXPFD_IVA] CHECK ([CXPFD_IVA] >= 0 AND [CXPFD_IVA] <= 1)
GO

ALTER TABLE [dbo].[CXPFacturasDetalles] WITH CHECK ADD CONSTRAINT [CHK_CXPFD_IEPS] CHECK ([CXPFD_IEPS] >= 0 AND [CXPFD_IEPS] <= 1)
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
	/* [CMM_ControlId] */ 2000111,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPF_EstatusFactura',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Borrado'
),(
	/* [CMM_ControlId] */ 2000112,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPF_EstatusFactura',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Abierta'
),(
	/* [CMM_ControlId] */ 2000113,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPF_EstatusFactura',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Cerrada'
),(
	/* [CMM_ControlId] */ 2000114,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPF_EstatusFactura',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Cancelada'
),(
	/* [CMM_ControlId] */ 2000115,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPF_EstatusFactura',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pago Programado'
),(
	/* [CMM_ControlId] */ 2000116,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPF_EstatusFactura',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pago Programado en Proceso'
),(
	/* [CMM_ControlId] */ 2000117,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPF_EstatusFactura',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pago Parcial'
),(
	/* [CMM_ControlId] */ 2000118,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPF_EstatusFactura',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pagada'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
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
	/* [CMM_ControlId] */ 2000121,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPF_TipoRegistro',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Nota de Debito'
),(
	/* [CMM_ControlId] */ 2000122,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPF_TipoRegistro',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Factura CXP'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
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
	/* [CMM_ControlId] */ 2000131,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPF_TerminosPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ '0',
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Contado'
),(
	/* [CMM_ControlId] */ 2000132,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPF_TerminosPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ '30',
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'30 Días'
),(
	/* [CMM_ControlId] */ 2000133,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPF_TerminosPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ '15',
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'15 Días'
),(
	/* [CMM_ControlId] */ 2000134,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPF_TerminosPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ '7',
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'7 Días'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
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
	/* [CMM_ControlId] */ 2000141,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CCXP_TipoPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'No Pagar'
),(
	/* [CMM_ControlId] */ 2000142,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CCXP_TipoPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pago de Inmediato'
),(
	/* [CMM_ControlId] */ 2000143,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CCXP_TipoPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pago Programado'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
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
	/* [CMM_ControlId] */ 2000151,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPF_TipoRetencion',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'ISR'
),(
	/* [CMM_ControlId] */ 2000152,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPF_TipoRetencion',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'IVA'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO
















SET IDENTITY_INSERT [dbo].[ArchivosEstructuraCarpetas] ON 
GO

INSERT [dbo].[ArchivosEstructuraCarpetas] (
	[AEC_EstructuraId],
	[AEC_AEC_EstructuraReferenciaId],
	[AEC_Descripcion],
	[AEC_NombreCarpeta],
	[AEC_Activo],
	[AEC_USU_CreadoPorId],
	[AEC_FechaCreacion]
) VALUES (
	/* [AEC_EstructuraId] */ 8,
	/* [AEC_AEC_EstructuraReferenciaId] */ NULL,
	/* [AEC_Descripcion] */ 'FacturasCXP',
	/* [AEC_NombreCarpeta] */ 'facturasCXP',
	/* [AEC_Activo] */ 1,
	/* [AEC_USU_CreadoPorId] */ 1,
	/* [AEC_FechaCreacion] */ GETDATE()
)
GO

SET IDENTITY_INSERT [dbo].[ArchivosEstructuraCarpetas] OFF
GO











INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, GETDATE(), N'view_list', (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Compras'), 4, 1000021, N'Gestión de facturas', N'Invoice management', N'item', N'/app/compras/gestion-facturas')
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Gestión de facturas' and MP_Icono = 'view_list' and MP_Orden = 4)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO