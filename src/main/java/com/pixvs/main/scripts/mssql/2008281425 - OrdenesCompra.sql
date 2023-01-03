/**
 * Created by Angel Daniel Hernández Silva on 11/08/2020.
 * Object:  Table [dbo].[OrdenesCompra]
 */


CREATE TABLE [dbo].[OrdenesCompra](
	[OC_OrdenCompraId] [int] IDENTITY(1,1) NOT NULL ,
	[OC_Codigo] [varchar]  (150) NOT NULL ,
	[OC_PRO_ProveedorId] [int]  NULL ,
	[OC_FechaOC] [date]  NOT NULL ,
	[OC_FechaRequerida] [date]  NOT NULL ,
	[OC_DireccionOC] [varchar]  (150) NULL ,
	[OC_RemitirA] [varchar]  (150) NULL ,
	[OC_ALM_RecepcionArticulosAlmacenId] [int]  NOT NULL ,
	[OC_MON_MonedaId] [smallint]  NOT NULL ,
	[OC_TerminoPago] [int]  NOT NULL ,
	[OC_DEP_DepartamentoId] [int]  NOT NULL ,
	[OC_Descuento] [decimal]  (3,2) NULL ,
	[OC_Comentario] [varchar]  (3000) NULL ,
	[OC_CMM_EstatusId] [int]  NOT NULL ,
	[OC_FechaCreacion] [datetime2](7) NOT NULL,
	[OC_FechaModificacion] [datetime2](7) NULL,
	[OC_USU_CreadoPorId] [int] NULL,
	[OC_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[OC_OrdenCompraId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[OrdenesCompra]  WITH CHECK ADD  CONSTRAINT [FK_OC_PRO_ProveedorId] FOREIGN KEY([OC_PRO_ProveedorId])
REFERENCES [dbo].[Proveedores] ([PRO_ProveedorId])
GO

ALTER TABLE [dbo].[OrdenesCompra] CHECK CONSTRAINT [FK_OC_PRO_ProveedorId]
GO

ALTER TABLE [dbo].[OrdenesCompra]  WITH CHECK ADD  CONSTRAINT [FK_OC_ALM_RecepcionArticulosAlmacenId] FOREIGN KEY([OC_ALM_RecepcionArticulosAlmacenId])
REFERENCES [dbo].[Almacenes] ([ALM_AlmacenId])
GO

ALTER TABLE [dbo].[OrdenesCompra] CHECK CONSTRAINT [FK_OC_ALM_RecepcionArticulosAlmacenId]
GO

ALTER TABLE [dbo].[OrdenesCompra]  WITH CHECK ADD  CONSTRAINT [FK_OC_MON_MonedaId] FOREIGN KEY([OC_MON_MonedaId])
REFERENCES [dbo].[Monedas] ([MON_MonedaId])
GO

ALTER TABLE [dbo].[OrdenesCompra] CHECK CONSTRAINT [FK_OC_MON_MonedaId]
GO

ALTER TABLE [dbo].[OrdenesCompra]  WITH CHECK ADD  CONSTRAINT [FK_OC_DEP_DepartamentoId] FOREIGN KEY([OC_DEP_DepartamentoId])
REFERENCES [dbo].[Departamentos] ([DEP_DepartamentoId])
GO

ALTER TABLE [dbo].[OrdenesCompra] CHECK CONSTRAINT [FK_OC_DEP_DepartamentoId]
GO

ALTER TABLE [dbo].[OrdenesCompra]  WITH CHECK ADD  CONSTRAINT [FK_OC_CMM_EstatusId] FOREIGN KEY([OC_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[OrdenesCompra] CHECK CONSTRAINT [FK_OC_CMM_EstatusId]
GO

ALTER TABLE [dbo].[OrdenesCompra]  WITH CHECK ADD  CONSTRAINT [FK_OC_USU_ModificadoPorId] FOREIGN KEY([OC_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[OrdenesCompra] CHECK CONSTRAINT [FK_OC_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[OrdenesCompra]  WITH CHECK ADD  CONSTRAINT [FK_OC_USU_CreadoPorId] FOREIGN KEY([OC_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[OrdenesCompra] CHECK CONSTRAINT [FK_OC_USU_CreadoPorId]

GO


CREATE   VIEW [dbo].[VW_LISTADO_ORDENES_COMPRA] AS

SELECT
	OC_Codigo AS "Código",
	PRO_Nombre AS "Proveedor",
	PRO_RFC AS "RFC",
	OC_FechaOC AS "Fecha OC",
	CMM_Valor AS "Estatus" 
FROM OrdenesCompra
INNER JOIN Proveedores ON PRO_ProveedorId = OC_PRO_ProveedorId
INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = OC_CMM_EstatusId

GO

INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, GETDATE(), N'add_shopping_cart', (select MP_NodoId from MenuPrincipal where MP_Titulo = 'MÓDULOS'), 5, 1000021, N'Compras', N'Purchases', N'collapsable', NULL)
GO

INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, GETDATE(), N'assignment', (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Compras'), 1, 1000021, N'Ordenes Compra', N'Purchase Orders', N'item', N'/app/compras/ordenes-compra')
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Ordenes Compra' and MP_Icono = 'assignment' and MP_Orden = 1)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO







CREATE TABLE [dbo].[OrdenesCompraDetalles](
	[OCD_OrdenCompraDetalleId] [int] IDENTITY(1,1) NOT NULL ,
	[OCD_OC_OrdenCompraId] [int]  NOT NULL ,
	[OCD_ART_ArticuloId] [int]  NOT NULL ,
	[OCD_UM_UnidadMedidaId] [smallint]  NOT NULL ,
	[OCD_FactorConversion] [decimal]  (28,6) NOT NULL ,
	[OCD_Cantidad] [decimal]  (28,6) NOT NULL ,
	[OCD_Precio] [decimal]  (10,2) NOT NULL ,
	[OCD_Descuento] [decimal]  (10,2) NOT NULL ,
	[OCD_IVA] [decimal]  (3,2) NULL ,
	[OCD_IVAExento] [bit]  NULL ,
	[OCD_IEPS] [decimal]  (3,2) NULL ,
	[OCD_IEPSCuotaFija] [decimal]  (28,2) NULL ,
	[OCD_FechaCreacion] [datetime2](7) NOT NULL,
	[OCD_FechaModificacion] [datetime2](7) NULL,
	[OCD_USU_CreadoPorId] [int] NULL,
	[OCD_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[OCD_OrdenCompraDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[OrdenesCompraDetalles]  WITH CHECK ADD  CONSTRAINT [FK_OCD_OC_OrdenCompraId] FOREIGN KEY([OCD_OC_OrdenCompraId])
REFERENCES [dbo].[OrdenesCompra] ([OC_OrdenCompraId])
GO

ALTER TABLE [dbo].[OrdenesCompraDetalles] CHECK CONSTRAINT [FK_OCD_OC_OrdenCompraId]
GO

ALTER TABLE [dbo].[OrdenesCompraDetalles]  WITH CHECK ADD  CONSTRAINT [FK_OCD_ART_ArticuloId] FOREIGN KEY([OCD_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[OrdenesCompraDetalles] CHECK CONSTRAINT [FK_OCD_ART_ArticuloId]
GO

ALTER TABLE [dbo].[OrdenesCompraDetalles]  WITH CHECK ADD  CONSTRAINT [FK_OCD_UM_UnidadMedidaId] FOREIGN KEY([OCD_UM_UnidadMedidaId])
REFERENCES [dbo].[UnidadesMedidas] ([UM_UnidadMedidaId])
GO

ALTER TABLE [dbo].[OrdenesCompraDetalles] CHECK CONSTRAINT [FK_OCD_UM_UnidadMedidaId]
GO

ALTER TABLE [dbo].[OrdenesCompraDetalles]  WITH CHECK ADD  CONSTRAINT [FK_OCD_USU_ModificadoPorId] FOREIGN KEY([OCD_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[OrdenesCompraDetalles] CHECK CONSTRAINT [FK_OCD_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[OrdenesCompraDetalles]  WITH CHECK ADD  CONSTRAINT [FK_OCD_USU_CreadoPorId] FOREIGN KEY([OCD_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[OrdenesCompraDetalles] CHECK CONSTRAINT [FK_OCD_USU_CreadoPorId]

GO




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
	/* [CMM_ControlId] */ 2000061,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OC_EstatusOC',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'En edición'
),(
	/* [CMM_ControlId] */ 2000062,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OC_EstatusOC',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Borrada'
),(
	/* [CMM_ControlId] */ 2000063,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OC_EstatusOC',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Abierta'
),(
	/* [CMM_ControlId] */ 2000064,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OC_EstatusOC',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Cerrada'
),(
	/* [CMM_ControlId] */ 2000065,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OC_EstatusOC',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Cancelada'
),(
	/* [CMM_ControlId] */ 2000066,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OC_EstatusOC',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Recibo parcial'
),(
	/* [CMM_ControlId] */ 2000067,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OC_EstatusOC',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Recibida'
),(
	/* [CMM_ControlId] */ 2000068,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OC_EstatusOC',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Facturada'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO



INSERT INTO [dbo].[Autonumericos]
           ([AUT_Nombre]
           ,[AUT_Prefijo]
           ,[AUT_Siguiente]
           ,[AUT_Digitos]
           ,[AUT_Activo])
     VALUES (
		   'Ordenes de Compra' -- <AUT_Nombre, varchar(50),>
           ,'OC' -- <AUT_Prefijo, varchar(255),>
           ,1 -- <AUT_Siguiente, int,>
           ,6 -- <AUT_Digitos, int,>
           ,1 -- <AUT_Activo, bit,>
	 )
GO

INSERT INTO [dbo].[Autonumericos]
           ([AUT_Nombre]
           ,[AUT_Prefijo]
           ,[AUT_Siguiente]
           ,[AUT_Digitos]
           ,[AUT_Activo])
     VALUES (
		   'Artículos misceláneos' -- <AUT_Nombre, varchar(50),>
           ,'MSC' -- <AUT_Prefijo, varchar(255),>
           ,1 -- <AUT_Siguiente, int,>
           ,6 -- <AUT_Digitos, int,>
           ,1 -- <AUT_Activo, bit,>
	 )
GO

SET IDENTITY_INSERT [dbo].[ArticulosFamilias] ON
GO

INSERT INTO [dbo].[ArticulosFamilias]
           ([AFAM_FamiliaId]
		   ,[AFAM_Nombre]
           ,[AFAM_Activo]
           ,[AFAM_FechaCreacion])
     VALUES (
		   1 -- [AFAM_FamiliaId]
		   ,'Misceláneo' -- <AFAM_Nombre, varchar(50),>
           ,0 -- <AUT_Prefijo, varchar(255),>
           ,GETDATE() -- <AFAM_FechaCreacion, datetime2,>
	 )
GO

SET IDENTITY_INSERT [dbo].[ArticulosFamilias] OFF
GO






INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, GETDATE(), N'input', (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Compras'), 2, 1000021, N'Recibo Órdenes de Compra', N'Purchase Orders Receipt', N'item', N'/app/compras/recibo-oc')
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Recibo Órdenes de Compra' and MP_Icono = 'input' and MP_Orden = 2)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO









CREATE TABLE [dbo].[OrdenesCompraRecibos](
	[OCR_OrdenCompraReciboId] [int] IDENTITY(1,1) NOT NULL ,
	[OCR_OC_OrdenCompraId] [int] NOT NULL,
	[OCR_OCD_OrdenCompraDetalleId] [int] NOT NULL,
	[OCR_OCR_ReciboReferenciaId] int NULL,
	[OCR_FechaRequerida] [date] NOT NULL,
	[OCR_FechaRecibo] [date] NOT NULL,
	[OCR_CantidadRecibo] [float] NOT NULL,
	[OCR_LOC_LocalidadId] [int] NOT NULL,
	[OCR_FechaCreacion] [datetime2](7) NOT NULL,
	[OCR_USU_CreadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[OCR_OrdenCompraReciboId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[OrdenesCompraRecibos]  WITH CHECK ADD  CONSTRAINT [FK_OCR_OC_OrdenCompraId] FOREIGN KEY([OCR_OC_OrdenCompraId])
REFERENCES [dbo].[OrdenesCompra] ([OC_OrdenCompraId])
GO

ALTER TABLE [dbo].[OrdenesCompraRecibos] CHECK CONSTRAINT [FK_OCR_OC_OrdenCompraId]
GO

ALTER TABLE [dbo].[OrdenesCompraRecibos]  WITH CHECK ADD  CONSTRAINT [FK_OCR_OCD_OrdenCompraDetalleId] FOREIGN KEY([OCR_OCD_OrdenCompraDetalleId])
REFERENCES [dbo].[OrdenesCompraDetalles] ([OCD_OrdenCompraDetalleId])
GO

ALTER TABLE [dbo].[OrdenesCompraRecibos] CHECK CONSTRAINT [FK_OCR_OCD_OrdenCompraDetalleId]
GO

ALTER TABLE [dbo].[OrdenesCompraRecibos]  WITH CHECK ADD  CONSTRAINT [FK_OCR_OCR_ReciboReferenciaId] FOREIGN KEY([OCR_OCR_ReciboReferenciaId])
REFERENCES [dbo].[OrdenesCompraRecibos] ([OCR_OrdenCompraReciboId])
GO

ALTER TABLE [dbo].[OrdenesCompraRecibos] CHECK CONSTRAINT [FK_OCR_OCR_ReciboReferenciaId]
GO

ALTER TABLE [dbo].[OrdenesCompraRecibos]  WITH CHECK ADD  CONSTRAINT [FK_OCR_LOC_LocalidadId] FOREIGN KEY([OCR_LOC_LocalidadId])
REFERENCES [dbo].[Localidades] ([LOC_LocalidadId])
GO

ALTER TABLE [dbo].[OrdenesCompraRecibos] CHECK CONSTRAINT [FK_OCR_LOC_LocalidadId]
GO

ALTER TABLE [dbo].[OrdenesCompraRecibos]  WITH CHECK ADD  CONSTRAINT [FK_OCR_USU_CreadoPorId] FOREIGN KEY([OCR_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[OrdenesCompraRecibos] CHECK CONSTRAINT [FK_OCR_USU_CreadoPorId]
GO






INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, GETDATE(), N'assignment_return', (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Compras'), 3, 1000021, N'Devolución Órdenes de Compra', N'Purchase Orders Return', N'item', N'/app/compras/devolucion-oc')
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Devolución Órdenes de Compra' and MP_Icono = 'assignment_return' and MP_Orden = 3)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO