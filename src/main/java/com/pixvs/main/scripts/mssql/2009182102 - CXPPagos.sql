/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 * Object:  Table [dbo].[CXPPagos]
 */


CREATE TABLE [dbo].[CXPPagos](
	[CXPP_CXPPagoId] [int] IDENTITY(1,1) NOT NULL ,
	[CXPP_PRO_ProveedorId] [int]  NULL ,
	[CXPP_RemitirA] [varchar]  (250) NULL ,
	[CXPP_CuentaBancaria] [varchar]  (60) NULL ,
	[CXPP_CMM_FormaPagoId] [int]  NULL ,
	[CXPP_FechaPago] [datetime]  NULL ,
	[CXPP_IdentificacionPago] [varchar] (100) NULL ,
	[CXPP_MontoPago] [decimal]  (28,4) NOT NULL ,
	[CXPP_MON_MonedaId] [smallint]  NOT NULL ,
	[CXPP_ParidadOficial] [decimal]  (28,4) NULL ,
	[CXPP_ParidadUsuario] [decimal]  (28,4) NULL ,
	[CXPP_CMM_EstatusId] [int]  NULL ,
	[CXPP_FechaCreacion] [datetime2](7) NOT NULL,
	[CXPP_FechaModificacion] [datetime2](7) NULL,
	[CXPP_USU_CreadoPorId] [int] NULL,
	[CXPP_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[CXPP_CXPPagoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[CXPPagos]  WITH CHECK ADD  CONSTRAINT [FK_CXPP_PRO_ProveedorId] FOREIGN KEY([CXPP_PRO_ProveedorId])
REFERENCES [dbo].[Proveedores] ([PRO_ProveedorId])
GO

ALTER TABLE [dbo].[CXPPagos] CHECK CONSTRAINT [FK_CXPP_PRO_ProveedorId]
GO

ALTER TABLE [dbo].[CXPPagos]  WITH CHECK ADD  CONSTRAINT [FK_CXPP_CMM_FormaPagoId] FOREIGN KEY([CXPP_CMM_FormaPagoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXPPagos] CHECK CONSTRAINT [FK_CXPP_CMM_FormaPagoId]
GO

ALTER TABLE [dbo].[CXPPagos]  WITH CHECK ADD  CONSTRAINT [FK_CXPP_MON_MonedaId] FOREIGN KEY([CXPP_MON_MonedaId])
REFERENCES [dbo].[Monedas] ([MON_MonedaId])
GO

ALTER TABLE [dbo].[CXPPagos] CHECK CONSTRAINT [FK_CXPP_MON_MonedaId]
GO

ALTER TABLE [dbo].[CXPPagos]  WITH CHECK ADD  CONSTRAINT [FK_CXPP_CMM_EstatusId] FOREIGN KEY([CXPP_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXPPagos] CHECK CONSTRAINT [FK_CXPP_CMM_EstatusId]
GO


ALTER TABLE [dbo].[CXPPagos]  WITH CHECK ADD  CONSTRAINT [FK_CXPP_USU_ModificadoPorId] FOREIGN KEY([CXPP_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[CXPPagos] CHECK CONSTRAINT [FK_CXPP_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[CXPPagos]  WITH CHECK ADD  CONSTRAINT [FK_CXPP_USU_CreadoPorId] FOREIGN KEY([CXPP_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[CXPPagos] CHECK CONSTRAINT [FK_CXPP_USU_CreadoPorId]
GO

















CREATE TABLE [dbo].[CXPPagosDetalles](
	[CXPPD_CXPPagoDetalleId] [int] IDENTITY(1,1) NOT NULL ,
	[CXPPD_CXPP_CXPPagoId] [int]  NOT NULL ,
	[CXPPD_CXPF_CXPFacturaId] [int]  NULL ,
	[CXPPD_MontoAplicado] [decimal] (28,4) NULL,
	[CXPPD_Comentario] [varchar] (250) NULL
PRIMARY KEY CLUSTERED 
(
	[CXPPD_CXPPagoDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO


ALTER TABLE [dbo].[CXPPagosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXPPD_CXPP_CXPPagoId] FOREIGN KEY([CXPPD_CXPP_CXPPagoId])
REFERENCES [dbo].[CXPPagos] ([CXPP_CXPPagoId])
GO

ALTER TABLE [dbo].[CXPPagosDetalles] CHECK CONSTRAINT [FK_CXPPD_CXPP_CXPPagoId]
GO

ALTER TABLE [dbo].[CXPPagosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXPPD_CXPF_CXPFacturaId] FOREIGN KEY([CXPPD_CXPF_CXPFacturaId])
REFERENCES [dbo].[CXPFacturas] ([CXPF_CXPFacturaId])
GO

ALTER TABLE [dbo].[CXPPagosDetalles] CHECK CONSTRAINT [FK_CXPPD_CXPF_CXPFacturaId]
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
	/* [CMM_ControlId] */ 2000171,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPP_EstatusPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pago parcial'
),(
	/* [CMM_ControlId] */ 2000172,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPP_EstatusPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pagado'
),(
	/* [CMM_ControlId] */ 2000173,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPP_EstatusPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pago cancelado'
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
	/* [CMM_ControlId] */ 2000181,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPP_FormaPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Transferencia'
),(
	/* [CMM_ControlId] */ 2000182,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPP_FormaPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Efectivo'
),(
	/* [CMM_ControlId] */ 2000183,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPP_FormaPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Saldo a favor'
),(
	/* [CMM_ControlId] */ 2000184,
	/* [CMM_Activo] */ 0,
	/* [CMM_Control] */ N'CMM_CXPP_FormaPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Nota de débito'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO











INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, GETDATE(), N'payment', (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Compras'), 6, 1000021, N'Pago a proveedores', N'Provider payment', N'item', N'/app/compras/pago-proveedores')
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Pago a proveedores' and MP_Icono = 'payment' and MP_Orden = 6)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO