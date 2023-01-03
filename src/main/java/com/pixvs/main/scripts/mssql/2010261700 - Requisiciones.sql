/**
 * Created by Angel Daniel Hernández Silva on 21/10/2020.
 * Object:  Table [dbo].[Requisiciones]
 */

-- Requisiciones

CREATE TABLE [dbo].[Requisiciones](
	[REQ_RequisicionId] [int] IDENTITY(1,1) NOT NULL ,
	[REQ_Codigo] [varchar]  (150) NOT NULL ,
	[REQ_Fecha] [date]  NOT NULL ,
	[REQ_CMM_EstadoRequisicionId] [int]  NOT NULL ,
	[REQ_Comentarios] [varchar]  (255) NULL ,
	[REQ_DEP_DepartamentoId] [int]  NOT NULL ,
	[REQ_SUC_SucursalId] [int]  NOT NULL ,
	[REQ_USU_EnviadaPorId] [int]  NULL ,
	[REQ_FechaCreacion] [datetime2](7) NOT NULL,
	[REQ_FechaModificacion] [datetime2](7) NULL,
	[REQ_USU_CreadoPorId] [int] NULL,
	[REQ_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[REQ_RequisicionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Requisiciones]  WITH CHECK ADD  CONSTRAINT [FK_REQ_CMM_EstadoRequisicionId] FOREIGN KEY([REQ_CMM_EstadoRequisicionId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Requisiciones] CHECK CONSTRAINT [FK_REQ_CMM_EstadoRequisicionId]
GO

ALTER TABLE [dbo].[Requisiciones]  WITH CHECK ADD  CONSTRAINT [FK_REQ_SUC_SucursalId] FOREIGN KEY([REQ_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO

ALTER TABLE [dbo].[Requisiciones] CHECK CONSTRAINT [FK_REQ_SUC_SucursalId]
GO

ALTER TABLE [dbo].[Requisiciones]  WITH CHECK ADD  CONSTRAINT [FK_REQ_DEP_DepartamentoId] FOREIGN KEY([REQ_DEP_DepartamentoId])
REFERENCES [dbo].[Departamentos] ([DEP_DepartamentoId])
GO

ALTER TABLE [dbo].[Requisiciones] CHECK CONSTRAINT [FK_REQ_DEP_DepartamentoId]
GO

ALTER TABLE [dbo].[Requisiciones]  WITH CHECK ADD  CONSTRAINT [FK_REQ_USU_EnviadaPorId] FOREIGN KEY([REQ_USU_EnviadaPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Requisiciones] CHECK CONSTRAINT [FK_REQ_USU_EnviadaPorId]
GO

ALTER TABLE [dbo].[Requisiciones]  WITH CHECK ADD  CONSTRAINT [FK_REQ_USU_ModificadoPorId] FOREIGN KEY([REQ_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Requisiciones] CHECK CONSTRAINT [FK_REQ_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[Requisiciones]  WITH CHECK ADD  CONSTRAINT [FK_REQ_USU_CreadoPorId] FOREIGN KEY([REQ_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Requisiciones] CHECK CONSTRAINT [FK_REQ_USU_CreadoPorId]

GO

-- RequisicionesPartidas

CREATE TABLE [dbo].[RequisicionesPartidas](
	[REQP_RequisicionpartidaId] [int] IDENTITY(1,1) NOT NULL ,
	[REQP_REQ_RequisicionId] [int]  NOT NULL ,
	[REQP_NumeroPartida] [int]  NOT NULL ,
	[REQP_ART_ArticuloId] [int]  NOT NULL ,
	[REQP_UM_UnidadMedidaId] [smallint]  NOT NULL ,
	[REQP_Comentarios] [varchar]  (255) NULL ,
	[REQP_FechaRequerida] [date]  NOT NULL ,
	[REQP_CantidadRequerida] [numeric]  (28,6) NOT NULL ,
	[REQP_CMM_EstadoPartidaId] [int]  NOT NULL ,
	[REQP_FechaModificacion] [datetime2](7) NULL,
	[REQP_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[REQP_RequisicionpartidaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[RequisicionesPartidas]  WITH CHECK ADD  CONSTRAINT [FK_REQP_REQ_RequisicionId] FOREIGN KEY([REQP_REQ_RequisicionId])
REFERENCES [dbo].[Requisiciones] ([REQ_RequisicionId])
GO

ALTER TABLE [dbo].[RequisicionesPartidas] CHECK CONSTRAINT [FK_REQP_REQ_RequisicionId]
GO

ALTER TABLE [dbo].[RequisicionesPartidas]  WITH CHECK ADD  CONSTRAINT [FK_REQP_ART_ArticuloId] FOREIGN KEY([REQP_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[RequisicionesPartidas] CHECK CONSTRAINT [FK_REQP_ART_ArticuloId]
GO

ALTER TABLE [dbo].[RequisicionesPartidas]  WITH CHECK ADD  CONSTRAINT [FK_REQP_UM_UnidadMedidaId] FOREIGN KEY([REQP_UM_UnidadMedidaId])
REFERENCES [dbo].[UnidadesMedidas] ([UM_UnidadMedidaId])
GO

ALTER TABLE [dbo].[RequisicionesPartidas] CHECK CONSTRAINT [FK_REQP_UM_UnidadMedidaId]
GO

ALTER TABLE [dbo].[RequisicionesPartidas]  WITH CHECK ADD  CONSTRAINT [FK_REQP_CMM_EstadoPartidaId] FOREIGN KEY([REQP_CMM_EstadoPartidaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[RequisicionesPartidas] CHECK CONSTRAINT [FK_REQP_CMM_EstadoPartidaId]
GO

ALTER TABLE [dbo].[RequisicionesPartidas]  WITH CHECK ADD  CONSTRAINT [FK_REQP_USU_ModificadoPorId] FOREIGN KEY([REQP_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[RequisicionesPartidas] CHECK CONSTRAINT [FK_REQP_USU_ModificadoPorId]
GO

-- Vista

CREATE   VIEW [dbo].[VW_LISTADO_REQUISICIONES] AS

SELECT
	REQ_Codigo AS "Código de requisición",
	REQ_Fecha AS "Fecha de requisición",
	SUC_Nombre + ' - ' + DEP_Nombre AS "Sucursal - Departamento",
	USU_Nombre + COALESCE(' ' + USU_PrimerApellido + COALESCE(' ' + USU_SegundoApellido,''),'') AS "Usuario solicitante",
	CMM_Valor AS "Estado requisición"
FROM Requisiciones 
INNER JOIN Sucursales ON SUC_SucursalId = REQ_SUC_SucursalId
INNER JOIN Departamentos ON DEP_DepartamentoId = REQ_DEP_DepartamentoId
INNER JOIN Usuarios ON USU_UsuarioId = REQ_USU_CreadoPorId
INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = REQ_CMM_EstadoRequisicionId

GO

-- Menú

INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, GETDATE(), N'add_shopping_cart', (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Compras'), 5, 1000021, N'Requisiciones', N'Requisitions', N'item', N'/app/compras/requisiciones')
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Requisiciones' and MP_Icono = 'add_shopping_cart' and MP_Orden = 5)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO

-- Cambios a OC

ALTER TABLE [dbo].[OrdenesCompraDetalles] ADD [OCD_REQP_RequisicionpartidaId] [int] NULL
GO

ALTER TABLE [dbo].[OrdenesCompraDetalles]  WITH CHECK ADD  CONSTRAINT [FK_OCD_REQP_RequisicionpartidaId] FOREIGN KEY([OCD_REQP_RequisicionpartidaId])
REFERENCES [dbo].[RequisicionesPartidas] ([REQP_RequisicionpartidaId])
GO

ALTER TABLE [dbo].[OrdenesCompraDetalles] CHECK CONSTRAINT [FK_OCD_REQP_RequisicionpartidaId]
GO

-- CMM

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
	/* [CMM_ControlId] */ 2000191,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_REQ_EstatusRequisicion',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'En Edición'
),(
	/* [CMM_ControlId] */ 2000192,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_REQ_EstatusRequisicion',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'En Proceso'
),(
	/* [CMM_ControlId] */ 2000193,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_REQ_EstatusRequisicion',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Autorizada'
),(
	/* [CMM_ControlId] */ 2000194,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_REQ_EstatusRequisicion',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'En Revisión'
),(
	/* [CMM_ControlId] */ 2000195,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_REQ_EstatusRequisicion',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Rechazada'
),(
	/* [CMM_ControlId] */ 2000196,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_REQ_EstatusRequisicion',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Enviada'
),(
	/* [CMM_ControlId] */ 2000197,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_REQ_EstatusRequisicion',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Convertida'
),(
	/* [CMM_ControlId] */ 2000198,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_REQ_EstatusRequisicion',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Borrada'
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
	/* [CMM_ControlId] */ 2000201,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_REQP_EstatusRequisicionPartida',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Abierta'
),(
	/* [CMM_ControlId] */ 2000202,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_REQP_EstatusRequisicionPartida',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Revisión'
),(
	/* [CMM_ControlId] */ 2000203,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_REQP_EstatusRequisicionPartida',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Rechazado'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO


-- Autonuméricos

INSERT INTO [dbo].[Autonumericos]
           ([AUT_Nombre]
           ,[AUT_Prefijo]
           ,[AUT_Siguiente]
           ,[AUT_Digitos]
           ,[AUT_Activo])
     VALUES (
		   'Requisiciones' -- <AUT_Nombre, varchar(50),>
           ,'REQ' -- <AUT_Prefijo, varchar(255),>
           ,1 -- <AUT_Siguiente, int,>
           ,6 -- <AUT_Digitos, int,>
           ,1 -- <AUT_Activo, bit,>
	 )
GO