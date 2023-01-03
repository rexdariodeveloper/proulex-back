/**
 * Created by Angel Daniel Hernández Silva on 15/07/2021.
 * Object:  Table [dbo].[MediosPagoPV]
 */

/*****************************/
/***** DROP FormasPagoPV *****/
/*****************************/

DROP TABLE [dbo].[FormasPagoPV]
GO

/************************/
/***** MediosPagoPV *****/
/************************/

CREATE TABLE [dbo].[MediosPagoPV](
	[MPPV_MedioPagoPVId] [int] IDENTITY(1,1) NOT NULL,
	[MPPV_Codigo] [varchar](10) NOT NULL,
	[MPPV_Nombre] [varchar](255) NOT NULL,
	[MPPV_Activo] [bit] NOT NULL,
	[MPPV_FechaCreacion] [datetime2](7) NOT NULL,
	[MPPV_FechaModificacion] [datetime2](7) NULL,
	[MPPV_USU_CreadoPorId] [int] NULL,
	[MPPV_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[MPPV_MedioPagoPVId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

-- Constraints FK

ALTER TABLE [dbo].[MediosPagoPV]  WITH CHECK ADD  CONSTRAINT [FK_MPPV_USU_CreadoPorId] FOREIGN KEY([MPPV_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[MediosPagoPV] CHECK CONSTRAINT [FK_MPPV_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[MediosPagoPV]  WITH CHECK ADD  CONSTRAINT [FK_MPPV_USU_ModificadoPorId] FOREIGN KEY([MPPV_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[MediosPagoPV] CHECK CONSTRAINT [FK_MPPV_USU_ModificadoPorId]
GO

/***************************/
/***** CMM - Estatus *****/
/***************************/

SET IDENTITY_INSERT [dbo].[MediosPagoPV] ON
GO

INSERT [dbo].[MediosPagoPV] (
	[MPPV_MedioPagoPVId],
	[MPPV_Codigo],
	[MPPV_Nombre],
	[MPPV_Activo],
	[MPPV_FechaCreacion],
	[MPPV_USU_CreadoPorId]
) VALUES (
	/* [MPPV_MedioPagoPVId] */ 1,
	/* [MPPV_Codigo] */ '01',
	/* [MPPV_Nombre] */ 'Efectivo',
	/* [MPPV_Activo] */ 1,
	/* [MPPV_FechaCreacion] */ GETDATE(),
	/* [MPPV_USU_CreadoPorId] */ 1
),(
	/* [MPPV_MedioPagoPVId] */ 2,
	/* [MPPV_Codigo] */ '03',
	/* [MPPV_Nombre] */ 'Transferencia bancaria',
	/* [MPPV_Activo] */ 1,
	/* [MPPV_FechaCreacion] */ GETDATE(),
	/* [MPPV_USU_CreadoPorId] */ 1
),(
	/* [MPPV_MedioPagoPVId] */ 3,
	/* [MPPV_Codigo] */ '04',
	/* [MPPV_Nombre] */ 'Tarjeta de crédito',
	/* [MPPV_Activo] */ 1,
	/* [MPPV_FechaCreacion] */ GETDATE(),
	/* [MPPV_USU_CreadoPorId] */ 1
),(
	/* [MPPV_MedioPagoPVId] */ 4,
	/* [MPPV_Codigo] */ '28',
	/* [MPPV_Nombre] */ 'Tarjeta de débito',
	/* [MPPV_Activo] */ 1,
	/* [MPPV_FechaCreacion] */ GETDATE(),
	/* [MPPV_USU_CreadoPorId] */ 1
),(
	/* [MPPV_MedioPagoPVId] */ 5,
	/* [MPPV_Codigo] */ '99',
	/* [MPPV_Nombre] */ 'Centro de pagos',
	/* [MPPV_Activo] */ 1,
	/* [MPPV_FechaCreacion] */ GETDATE(),
	/* [MPPV_USU_CreadoPorId] */ 1
)
GO

SET IDENTITY_INSERT [dbo].[MediosPagoPV] OFF
GO

/**********************************/
/***** SucursalesMediosPagoPV *****/
/**********************************/

CREATE TABLE [dbo].[SucursalesMediosPagoPV](
	[SUCMPPV_SUC_SucursalId] [int] NOT NULL,
	[SUCMPPV_MPPV_MedioPagoPVId] [int] NOT NULL,
CONSTRAINT [PK_SucursalesMediosPagoPV] PRIMARY KEY CLUSTERED 
(
	[SUCMPPV_SUC_SucursalId] ASC,
	[SUCMPPV_MPPV_MedioPagoPVId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO