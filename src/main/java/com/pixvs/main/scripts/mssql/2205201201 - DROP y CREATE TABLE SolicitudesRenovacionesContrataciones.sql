DROP TABLE IF EXISTS SolicitudesRenovacionesContrataciones
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/* ****************************************************************
 * Descripción: Crear la tabla de SolicitudesRenovacionesContrataciones
 * Autor: Rene Carrillo
 * Fecha: 21.04.2022
 * Versión: 1.0.0
 *****************************************************************
*/

CREATE TABLE [dbo].[SolicitudesRenovacionesContrataciones](
	[SRC_SolicitudRenovacionContratacionId] [int] IDENTITY(1,1) NOT NULL,
	[SRC_Codigo] [nvarchar] (50) NOT NULL,
	[SRC_FechaInicio] [date] NOT NULL,
	[SRC_FechaFin] [date] NOT NULL,
	[SRC_CMM_EstatusId] [int] NOT NULL,
	[SRC_FechaCreacion] [datetime2](7) NOT NULL,
	[SRC_USU_CreadoPorId] [int] NOT NULL,
	[SRC_FechaUltimaModificacion] [datetime2](7) NULL,
	[SRC_USU_ModificadoPorId] [int] NULL
 CONSTRAINT [PK_SolicitudesRenovacionesContrataciones] PRIMARY KEY CLUSTERED
(
	[SRC_SolicitudRenovacionContratacionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[SolicitudesRenovacionesContrataciones] ADD  CONSTRAINT [DF_SolicitudesRenovacionesContrataciones_SRC_FechaCreacion]  DEFAULT (getdate()) FOR [SRC_FechaCreacion]
GO

ALTER TABLE [dbo].[SolicitudesRenovacionesContrataciones]  WITH CHECK ADD  CONSTRAINT [FK_SRC_CMM_EstatusId] FOREIGN KEY([SRC_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[SolicitudesRenovacionesContrataciones] CHECK CONSTRAINT [FK_SRC_CMM_EstatusId]
GO

ALTER TABLE [dbo].[SolicitudesRenovacionesContrataciones]  WITH CHECK ADD  CONSTRAINT [FK_SRC_USU_CreadoPorId] FOREIGN KEY([SRC_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[SolicitudesRenovacionesContrataciones] CHECK CONSTRAINT [FK_SRC_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[SolicitudesRenovacionesContrataciones]  WITH CHECK ADD  CONSTRAINT [FK_SRC_USU_ModificadoPorId] FOREIGN KEY([SRC_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[SolicitudesRenovacionesContrataciones] CHECK CONSTRAINT [FK_SRC_USU_ModificadoPorId]
GO