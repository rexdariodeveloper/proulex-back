/**
 * Created by Angel Daniel Hernández Silva on 11/06/2021.
 * Object:  Table [dbo].[ClientesRemisiones]
 */


/******************************/
/***** ClientesRemisiones *****/
/******************************/

CREATE TABLE [dbo].[ClientesRemisiones](
    [CLIR_ClienteRemisionId] [int] IDENTITY(1,1) NOT NULL,
    [CLIR_Codigo] [varchar] (150) NOT NULL,
    
    [CLIR_CLI_ClienteId] [int] NOT NULL,
    [CLIR_Fecha] [date] NOT NULL,
    [CLIR_MON_MonedaId] [smallint] NOT NULL,
    [CLIR_ALM_AlmacenOrigenId] [int] NOT NULL,
    [CLIR_ALM_AlmacenDestinoId] [int] NOT NULL,
    [CLIR_Comentario] [varchar] (255) NULL,
    [CLIR_CMM_EstatusId] [int] NOT NULL,

    [CLIR_FechaCreacion] [datetime2](7) NOT NULL,
    [CLIR_USU_CreadoPorId] [int] NULL,
    [CLIR_FechaModificacion] [datetime2](7) NULL,
    [CLIR_USU_ModificadoPorId] [int] NULL
    
    PRIMARY KEY CLUSTERED 
    (
        [CLIR_ClienteRemisionId] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ClientesRemisiones]  WITH CHECK ADD  CONSTRAINT [FK_CLIR_CLI_ClienteId] FOREIGN KEY([CLIR_CLI_ClienteId])
REFERENCES [dbo].[Clientes] ([CLI_ClienteId])
GO

ALTER TABLE [dbo].[ClientesRemisiones] CHECK CONSTRAINT [FK_CLIR_CLI_ClienteId]
GO

ALTER TABLE [dbo].[ClientesRemisiones]  WITH CHECK ADD  CONSTRAINT [FK_CLIR_MON_MonedaId] FOREIGN KEY([CLIR_MON_MonedaId])
REFERENCES [dbo].[Monedas] ([MON_MonedaId])
GO

ALTER TABLE [dbo].[ClientesRemisiones] CHECK CONSTRAINT [FK_CLIR_MON_MonedaId]
GO

ALTER TABLE [dbo].[ClientesRemisiones]  WITH CHECK ADD  CONSTRAINT [FK_CLIR_ALM_AlmacenOrigenId] FOREIGN KEY([CLIR_ALM_AlmacenOrigenId])
REFERENCES [dbo].[Almacenes] ([ALM_AlmacenId])
GO

ALTER TABLE [dbo].[ClientesRemisiones] CHECK CONSTRAINT [FK_CLIR_ALM_AlmacenOrigenId]
GO

ALTER TABLE [dbo].[ClientesRemisiones]  WITH CHECK ADD  CONSTRAINT [FK_CLIR_ALM_AlmacenDestinoId] FOREIGN KEY([CLIR_ALM_AlmacenDestinoId])
REFERENCES [dbo].[Almacenes] ([ALM_AlmacenId])
GO

ALTER TABLE [dbo].[ClientesRemisiones] CHECK CONSTRAINT [FK_CLIR_ALM_AlmacenDestinoId]
GO

ALTER TABLE [dbo].[ClientesRemisiones]  WITH CHECK ADD  CONSTRAINT [FK_CLIR_CMM_EstatusId] FOREIGN KEY([CLIR_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ClientesRemisiones] CHECK CONSTRAINT [FK_CLIR_CMM_EstatusId]
GO

ALTER TABLE [dbo].[ClientesRemisiones]  WITH CHECK ADD  CONSTRAINT [FK_CLIR_USU_CreadoPorId] FOREIGN KEY([CLIR_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ClientesRemisiones] CHECK CONSTRAINT [FK_CLIR_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[ClientesRemisiones]  WITH CHECK ADD  CONSTRAINT [FK_CLIR_USU_ModificadoPorId] FOREIGN KEY([CLIR_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ClientesRemisiones] CHECK CONSTRAINT [FK_CLIR_USU_ModificadoPorId]
GO

/**************************************/
/***** ClientesRemisionesDetalles *****/
/**************************************/

CREATE TABLE [dbo].[ClientesRemisionesDetalles](
    [CLIRD_ClienteRemisionDetalleId] [int] IDENTITY(1,1) NOT NULL,

    [CLIRD_CLIR_ClienteRemisionId] [int] NOT NULL,
    [CLIRD_ART_ArticuloId] [int] NOT NULL,
    [CLIRD_Cantidad] [decimal] (28,6) NOT NULL
    
    PRIMARY KEY CLUSTERED 
    (
        [CLIRD_ClienteRemisionDetalleId] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ClientesRemisionesDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CLIRD_CLIR_ClienteRemisionId] FOREIGN KEY([CLIRD_CLIR_ClienteRemisionId])
REFERENCES [dbo].[ClientesRemisiones] ([CLIR_ClienteRemisionId])
GO

ALTER TABLE [dbo].[ClientesRemisionesDetalles] CHECK CONSTRAINT [FK_CLIRD_CLIR_ClienteRemisionId]
GO

ALTER TABLE [dbo].[ClientesRemisionesDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CLIRD_ART_ArticuloId] FOREIGN KEY([CLIRD_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[ClientesRemisionesDetalles] CHECK CONSTRAINT [FK_CLIRD_ART_ArticuloId]
GO

/*********************************************/
/***** Vista VW_REPORTE_EXCEL_REMISIONES *****/
/*********************************************/

CREATE OR ALTER VIEW [dbo].[VW_REPORTE_EXCEL_REMISIONES] AS

    SELECT
        CLIR_Codigo AS "Código",
        CLI_Nombre AS "Cliente",
        CLI_RFC AS "RFC",
        FORMAT(CLIR_Fecha,'dd/MM/yyyy') AS "Fecha",
        Origen.ALM_Nombre AS "Origen",
        Destino.ALM_Nombre AS "Destino",
        SUM(CLIRD_Cantidad * LIPRED_PrecioVenta) AS "Monto",
        CMM_Valor AS "Estatus"
    FROM ClientesRemisiones
    INNER JOIN Clientes ON CLI_ClienteId = CLIR_CLI_ClienteId
    INNER JOIN Almacenes AS Origen ON Origen.ALM_AlmacenId = CLIR_ALM_AlmacenOrigenId
    INNER JOIN Almacenes AS Destino ON Destino.ALM_AlmacenId = CLIR_ALM_AlmacenDestinoId
    INNER JOIN ClientesRemisionesDetalles ON CLIRD_CLIR_ClienteRemisionId = CLIR_ClienteRemisionId
    INNER JOIN ListadosPreciosDetalles ON LIPRED_LIPRE_ListadoPrecioId = CLI_LIPRE_ListadoPrecioId AND LIPRED_ART_ArticuloId = CLIRD_ART_ArticuloId
    INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = CLIR_CMM_EstatusId
    GROUP BY CLIR_Codigo, CLI_Nombre, CLI_RFC, CLIR_Fecha, Origen.ALM_Nombre, Destino.ALM_Nombre, CMM_Valor

GO

/***************************************/
/***** Vista VW_Listado_Remisiones *****/
/***************************************/

CREATE OR ALTER VIEW [dbo].[VW_Listado_Remisiones] AS

    SELECT
        CLIR_ClienteRemisionId AS id,
		CLIR_Codigo AS codigo,
        CLI_Nombre AS clienteNombre,
        CLI_RFC AS clienteRFC,
        CLIR_Fecha AS fecha,
        Origen.ALM_Nombre AS almacenOrigenNombre,
        Destino.ALM_Nombre AS almacenDestinoNombre,
        SUM(CLIRD_Cantidad * LIPRED_PrecioVenta) AS monto,
        CMM_Valor AS estatusValor
    FROM ClientesRemisiones
    INNER JOIN Clientes ON CLI_ClienteId = CLIR_CLI_ClienteId
    INNER JOIN Almacenes AS Origen ON Origen.ALM_AlmacenId = CLIR_ALM_AlmacenOrigenId
    INNER JOIN Almacenes AS Destino ON Destino.ALM_AlmacenId = CLIR_ALM_AlmacenDestinoId
    INNER JOIN ClientesRemisionesDetalles ON CLIRD_CLIR_ClienteRemisionId = CLIR_ClienteRemisionId
    INNER JOIN ListadosPreciosDetalles ON LIPRED_LIPRE_ListadoPrecioId = CLI_LIPRE_ListadoPrecioId AND LIPRED_ART_ArticuloId = CLIRD_ART_ArticuloId
    INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = CLIR_CMM_EstatusId
    GROUP BY CLIR_ClienteRemisionId, CLIR_Codigo, CLI_Nombre, CLI_RFC, CLIR_Fecha, Origen.ALM_Nombre, Destino.ALM_Nombre, CMM_Valor

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
    4, -- [MP_Orden]
    1000021, -- [MP_CMM_SistemaAccesoId]
    N'Remisiones', -- [MP_Titulo]
    N'Sendings', -- [MP_TituloEN]
    N'item', -- [MP_Tipo]
    N'/app/ventas/remisiones' -- [MP_URL]
)
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Remisiones' and MP_Icono = 'assignment' and MP_Orden = 4)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO

/*********************************/
/***** CMM - EstatusRemision *****/
/*********************************/

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
	/* [CMM_ControlId] */ 2000420,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CLIR_EstatusRemision',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Enviada'
),(
	/* [CMM_ControlId] */ 2000421,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CLIR_EstatusRemision',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Facturada parcial'
),(
	/* [CMM_ControlId] */ 2000422,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CLIR_EstatusRemision',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Facturada'
),(
	/* [CMM_ControlId] */ 2000423,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CLIR_EstatusRemision',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Devuelta parcial'
),(
	/* [CMM_ControlId] */ 2000424,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CLIR_EstatusRemision',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Devuelta'
),(
	/* [CMM_ControlId] */ 2000425,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CLIR_EstatusRemision',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Cancelada'
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
           ('ClientesRemisiones'
           ,'RM'
           ,1
           ,6
           ,1)
GO