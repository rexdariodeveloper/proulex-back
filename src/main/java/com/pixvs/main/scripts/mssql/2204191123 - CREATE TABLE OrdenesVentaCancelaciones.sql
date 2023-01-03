/**
* Created by Angel Daniel Hernández Silva on 23/03/2022.
* Object: CREATE TABLE OrdenesVentaCancelaciones
*/

/*************************************/
/***** OrdenesVentaCancelaciones *****/
/*************************************/

CREATE TABLE [dbo].[OrdenesVentaCancelaciones](
	[OVC_OrdenVentaCancelacionId] [int] IDENTITY(1,1) NOT NULL,
    [OVC_Codigo] [varchar](15) NOT NULL,
	[OVC_FechaCancelacion] [date] NOT NULL,
    [OVC_CMM_MotivoCancelacionId] [int] NOT NULL,
    [OVC_Banco] [varchar](255) NOT NULL,
    [OVC_Beneficiario] [varchar](255) NOT NULL,
    [OVC_NumeroCuenta] [varchar](255) NOT NULL,
    [OVC_CLABE] [varchar](255) NOT NULL,
    [OVC_TelefonoContacto] [varchar](255) NOT NULL,
	[OVC_ImporteReembolsar] [decimal](28,6) NOT NULL,
    [OVC_CMM_TipoCancelacionId] [int] NOT NULL,
    [OVC_CMM_EstatusId] [int] NOT NULL,
	[OVC_FechaCreacion] [datetime2](7) NOT NULL,
	[OVC_FechaModificacion] [datetime2](7) NULL,
	[OVC_USU_CreadoPorId] [int] NULL,
	[OVC_USU_ModificadoPorId] [int] NULL,
    PRIMARY KEY CLUSTERED(
        [OVC_OrdenVentaCancelacionId] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

-- FK_OVC_CMM_MotivoCancelacionId

ALTER TABLE [dbo].[OrdenesVentaCancelaciones]  WITH CHECK ADD  CONSTRAINT [FK_OVC_CMM_MotivoCancelacionId] FOREIGN KEY([OVC_CMM_MotivoCancelacionId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[OrdenesVentaCancelaciones] CHECK CONSTRAINT [FK_OVC_CMM_MotivoCancelacionId]
GO

-- FK_OVC_CMM_TipoCancelacionId

ALTER TABLE [dbo].[OrdenesVentaCancelaciones]  WITH CHECK ADD  CONSTRAINT [FK_OVC_CMM_TipoCancelacionId] FOREIGN KEY([OVC_CMM_TipoCancelacionId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[OrdenesVentaCancelaciones] CHECK CONSTRAINT [FK_OVC_CMM_TipoCancelacionId]
GO

-- FK_OVC_CMM_EstatusId

ALTER TABLE [dbo].[OrdenesVentaCancelaciones]  WITH CHECK ADD  CONSTRAINT [FK_OVC_CMM_EstatusId] FOREIGN KEY([OVC_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[OrdenesVentaCancelaciones] CHECK CONSTRAINT [FK_OVC_CMM_EstatusId]
GO

-- FK_OVC_USU_CreadoPorId

ALTER TABLE [dbo].[OrdenesVentaCancelaciones]  WITH CHECK ADD  CONSTRAINT [FK_OVC_USU_CreadoPorId] FOREIGN KEY([OVC_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[OrdenesVentaCancelaciones] CHECK CONSTRAINT [FK_OVC_USU_CreadoPorId]
GO

-- FK_OVC_USU_ModificadoPorId

ALTER TABLE [dbo].[OrdenesVentaCancelaciones]  WITH CHECK ADD  CONSTRAINT [FK_OVC_USU_ModificadoPorId] FOREIGN KEY([OVC_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[OrdenesVentaCancelaciones] CHECK CONSTRAINT [FK_OVC_USU_ModificadoPorId]
GO

/*********************************************/
/***** OrdenesVentaCancelacionesDetalles *****/
/*********************************************/

CREATE TABLE [dbo].[OrdenesVentaCancelacionesDetalles](
    [OVCD_OrdenVentaCancelacionDetalleId] [int] IDENTITY(1,1) NOT NULL,
    [OVCD_OVC_OrdenVentaCancelacionId] [int] NOT NULL,
    [OVCD_OVD_OrdenVentaDetalleId] [int] NOT NULL,
    [OVCD_RegresoLibro] [bit] NULL,
    PRIMARY KEY CLUSTERED(
        [OVCD_OrdenVentaCancelacionDetalleId] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
)
GO

-- FK_OVCD_OVC_OrdenVentaCancelacionId

ALTER TABLE [dbo].[OrdenesVentaCancelacionesDetalles]  WITH CHECK ADD  CONSTRAINT [FK_OVCD_OVC_OrdenVentaCancelacionId] FOREIGN KEY([OVCD_OVC_OrdenVentaCancelacionId])
REFERENCES [dbo].[OrdenesVentaCancelaciones] ([OVC_OrdenVentaCancelacionId])
GO

ALTER TABLE [dbo].[OrdenesVentaCancelacionesDetalles] CHECK CONSTRAINT [FK_OVCD_OVC_OrdenVentaCancelacionId]
GO

-- FK_OVCD_OVD_OrdenVentaDetalleId

ALTER TABLE [dbo].[OrdenesVentaCancelacionesDetalles]  WITH CHECK ADD  CONSTRAINT [FK_OVCD_OVD_OrdenVentaDetalleId] FOREIGN KEY([OVCD_OVD_OrdenVentaDetalleId])
REFERENCES [dbo].[OrdenesVentaDetalles] ([OVD_OrdenVentaDetalleId])
GO

ALTER TABLE [dbo].[OrdenesVentaCancelacionesDetalles] CHECK CONSTRAINT [FK_OVCD_OVD_OrdenVentaDetalleId]
GO

/**************************/
/***** Menú principal *****/
/**************************/

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
) VALUES (
	1, -- [MP_Activo]
	GETDATE(), -- [MP_FechaCreacion]
	N'assignment', -- [MP_Icono]
	(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Ventas' AND MP_Orden = 11), -- [MP_NodoPadreId]
	8, -- [MP_Orden]
	1000021, -- [MP_CMM_SistemaAccesoId]
	N'Cancelación de nota de venta', -- [MP_Titulo]
	N'Cancellation of sales note', -- [MP_TituloEN]
	N'item', -- [MP_Tipo]
	N'/app/ventas/cancelacion-nota-venta' -- [MP_URL]
)
GO

/**********************************************/
/***** CMM - Estatus de cancelación de OV *****/
/**********************************************/

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
	/* [CMM_ControlId] */ 2000720,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OVC_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Aprobada'
),(
	/* [CMM_ControlId] */ 2000721,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OVC_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Borrada'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

/********************************************/
/***** CMM - Tipos de cancelación de OV *****/
/********************************************/

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
	/* [CMM_ControlId] */ 2000730,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OVC_TiposCancelacion',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'100%'
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
           ('Cancelación de orden de venta'
           ,'NC'
           ,1
           ,6
           ,1)
GO

/****************************************/
/***** CMM - Tipos de documento OVC *****/
/****************************************/

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
	/* [CMM_ControlId] */ 2000740,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OVC_TiposDocumento',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Nota de venta o factura'
),(
	/* [CMM_ControlId] */ 2000741,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OVC_TiposDocumento',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Número de corte'
),(
	/* [CMM_ControlId] */ 2000742,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OVC_TiposDocumento',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Comprobante de pago'
),(
	/* [CMM_ControlId] */ 2000743,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OVC_TiposDocumento',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Identificación oficial de la persona que recibe la transferencia'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

/*********************************************/
/***** OrdenesVentaCancelacionesArchivos *****/
/*********************************************/

CREATE TABLE [dbo].[OrdenesVentaCancelacionesArchivos](
    [OVCA_OrdenVentaCancelacionArchivoId] [int] IDENTITY(1,1) NOT NULL,
    [OVCA_OVC_OrdenVentaCancelacionId] [int] NOT NULL,
    [OVCA_ARC_ArchivoId] [int] NOT NULL,
    [OVCA_CMM_TipoDocumentoId] [int] NOT NULL,
    [OVCA_Valor] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED(
        [OVCA_OrdenVentaCancelacionArchivoId] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
)
GO

-- FK_OVCA_OVC_OrdenVentaCancelacionId

ALTER TABLE [dbo].[OrdenesVentaCancelacionesArchivos]  WITH CHECK ADD  CONSTRAINT [FK_OVCA_OVC_OrdenVentaCancelacionId] FOREIGN KEY([OVCA_OVC_OrdenVentaCancelacionId])
REFERENCES [dbo].[OrdenesVentaCancelaciones] ([OVC_OrdenVentaCancelacionId])
GO

ALTER TABLE [dbo].[OrdenesVentaCancelacionesArchivos] CHECK CONSTRAINT [FK_OVCA_OVC_OrdenVentaCancelacionId]
GO

-- FK_OVCA_ARC_ArchivoId

ALTER TABLE [dbo].[OrdenesVentaCancelacionesArchivos]  WITH CHECK ADD  CONSTRAINT [FK_OVCA_ARC_ArchivoId] FOREIGN KEY([OVCA_ARC_ArchivoId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[OrdenesVentaCancelacionesArchivos] CHECK CONSTRAINT [FK_OVCA_ARC_ArchivoId]
GO

-- FK_OVCA_CMM_TipoDocumentoId

ALTER TABLE [dbo].[OrdenesVentaCancelacionesArchivos]  WITH CHECK ADD  CONSTRAINT [FK_OVCA_CMM_TipoDocumentoId] FOREIGN KEY([OVCA_CMM_TipoDocumentoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[OrdenesVentaCancelacionesArchivos] CHECK CONSTRAINT [FK_OVCA_CMM_TipoDocumentoId]
GO

/**************************************/
/***** ArchivosEstructuraCarpetas *****/
/**************************************/

SET IDENTITY_INSERT [dbo].[ArchivosEstructuraCarpetas] ON 
GO

INSERT [dbo].[ArchivosEstructuraCarpetas] 
	([AEC_EstructuraId],[AEC_AEC_EstructuraReferenciaId],[AEC_Descripcion],[AEC_NombreCarpeta],[AEC_Activo],[AEC_USU_CreadoPorId],[AEC_FechaCreacion]) 
VALUES 
	(21,	NULL,	'Órdenes de venta',	'ordenesVenta',		1,	(SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'pixvs.server@gmail.com'),	GETDATE()),
	(22,	21,		'Cancelaciones',	'cancelaciones',	1,	(SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'pixvs.server@gmail.com'),	GETDATE())
GO

SET IDENTITY_INSERT [dbo].[ArchivosEstructuraCarpetas] OFF
GO