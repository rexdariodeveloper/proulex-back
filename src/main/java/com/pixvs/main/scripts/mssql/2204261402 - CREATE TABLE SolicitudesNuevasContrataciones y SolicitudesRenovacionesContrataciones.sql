SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/* ****************************************************************
 * Descripción: Crear la tabla de SolicitudesNuevasContrataciones
 * Autor: Rene Carrillo
 * Fecha: 21.04.2022
 * Versión: 1.0.0
 *****************************************************************
*/

CREATE TABLE [dbo].[SolicitudesNuevasContrataciones](
	[SNC_SolicitudNuevaContratacionId] [int] IDENTITY(1,1) NOT NULL,
	[SNC_Codigo] [nvarchar] (50) NOT NULL,
	[SNC_CMM_EstatusId] [int] NOT NULL,
	[SNC_FechaCreacion] [datetime2](7) NOT NULL,
	[SNC_USU_CreadoPorId] [int] NOT NULL,
	[SNC_FechaUltimaModificacion] [datetime2](7) NULL,
	[SNC_USU_ModificadoPorId] [int] NULL
 CONSTRAINT [PK_SolicitudesNuevasContrataciones] PRIMARY KEY CLUSTERED
(
	[SNC_SolicitudNuevaContratacionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[SolicitudesNuevasContrataciones] ADD  CONSTRAINT [DF_SolicitudesNuevasContrataciones_SNC_FechaCreacion]  DEFAULT (getdate()) FOR [SNC_FechaCreacion]
GO

ALTER TABLE [dbo].[SolicitudesNuevasContrataciones]  WITH CHECK ADD  CONSTRAINT [FK_SNC_CMM_EstatusId] FOREIGN KEY([SNC_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[SolicitudesNuevasContrataciones] CHECK CONSTRAINT [FK_SNC_CMM_EstatusId]
GO

ALTER TABLE [dbo].[SolicitudesNuevasContrataciones]  WITH CHECK ADD  CONSTRAINT [FK_SNC_USU_CreadoPorId] FOREIGN KEY([SNC_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[SolicitudesNuevasContrataciones] CHECK CONSTRAINT [FK_SNC_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[SolicitudesNuevasContrataciones]  WITH CHECK ADD  CONSTRAINT [FK_SNC_USU_ModificadoPorId] FOREIGN KEY([SNC_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[SolicitudesNuevasContrataciones] CHECK CONSTRAINT [FK_SNC_USU_ModificadoPorId]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/* ****************************************************************
 * Descripción: Crear la tabla de SolicitudesNuevasContratacionesDetalles
 * Autor: Rene Carrillo
 * Fecha: 21.04.2022
 * Versión: 1.0.0
 *****************************************************************
*/

CREATE TABLE [dbo].[SolicitudesNuevasContratacionesDetalles](
	[SNCD_SolicitudNuevaContratacionDetalleId] [int] IDENTITY(1,1) NOT NULL,
	[SNCD_SNC_SolicitudNuevaContratacionId] [int] NOT NULL,
	[SNCD_EMP_EmpleadoId] [int] NOT NULL,
	[SNCD_CMM_EstatusId] [int] NOT NULL
 CONSTRAINT [PK_SolicitudesNuevasContratacionesDetalles] PRIMARY KEY CLUSTERED
(
	[SNCD_SolicitudNuevaContratacionDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[SolicitudesNuevasContratacionesDetalles]  WITH CHECK ADD  CONSTRAINT [FK_SNCD_EMP_EmpleadoId] FOREIGN KEY([SNCD_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[SolicitudesNuevasContratacionesDetalles] CHECK CONSTRAINT [FK_SNCD_EMP_EmpleadoId]
GO

ALTER TABLE [dbo].[SolicitudesNuevasContratacionesDetalles]  WITH CHECK ADD  CONSTRAINT [FK_SNCD_CMM_EstatusId] FOREIGN KEY([SNCD_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[SolicitudesNuevasContratacionesDetalles] CHECK CONSTRAINT [FK_SNCD_CMM_EstatusId]
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
	[SRN_SolicitudRenovacionContratacionId] [int] IDENTITY(1,1) NOT NULL,
	[SRN_Codigo] [nvarchar] (50) NOT NULL,
	[SRN_CMM_EstatusId] [int] NOT NULL,
	[SRN_FechaCreacion] [datetime2](7) NOT NULL,
	[SRN_USU_CreadoPorId] [int] NOT NULL,
	[SRN_FechaUltimaModificacion] [datetime2](7) NULL,
	[SRN_USU_ModificadoPorId] [int] NULL
 CONSTRAINT [PK_SolicitudesRenovacionesContrataciones] PRIMARY KEY CLUSTERED
(
	[SRN_SolicitudRenovacionContratacionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[SolicitudesRenovacionesContrataciones] ADD  CONSTRAINT [DF_SolicitudesRenovacionesContrataciones_SRN_FechaCreacion]  DEFAULT (getdate()) FOR [SRN_FechaCreacion]
GO

ALTER TABLE [dbo].[SolicitudesRenovacionesContrataciones]  WITH CHECK ADD  CONSTRAINT [FK_SRN_CMM_EstatusId] FOREIGN KEY([SRN_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[SolicitudesRenovacionesContrataciones] CHECK CONSTRAINT [FK_SRN_CMM_EstatusId]
GO

ALTER TABLE [dbo].[SolicitudesRenovacionesContrataciones]  WITH CHECK ADD  CONSTRAINT [FK_SRN_USU_CreadoPorId] FOREIGN KEY([SRN_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[SolicitudesRenovacionesContrataciones] CHECK CONSTRAINT [FK_SRN_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[SolicitudesRenovacionesContrataciones]  WITH CHECK ADD  CONSTRAINT [FK_SRN_USU_ModificadoPorId] FOREIGN KEY([SRN_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[SolicitudesRenovacionesContrataciones] CHECK CONSTRAINT [FK_SRN_USU_ModificadoPorId]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/* ****************************************************************
 * Descripción: Crear la tabla de SolicitudesRenovacionesContratacionesDetalles
 * Autor: Rene Carrillo
 * Fecha: 21.04.2022
 * Versión: 1.0.0
 *****************************************************************
*/

CREATE TABLE [dbo].[SolicitudesRenovacionesContratacionesDetalles](
	[SRCD_SolicitudRenovacionContratacionDetalleId] [int] IDENTITY(1,1) NOT NULL,
	[SRCD_SRC_SolicitudRenovacionContratacionId] [int] NOT NULL,
	[SRCD_EMP_EmpleadoId] [int] NOT NULL,
	[SRCD_CMM_EstatusId] [int] NOT NULL
 CONSTRAINT [PK_SolicitudesRenovacionesContratacionesDetalles] PRIMARY KEY CLUSTERED
(
	[SRCD_SolicitudRenovacionContratacionDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[SolicitudesRenovacionesContratacionesDetalles]  WITH CHECK ADD  CONSTRAINT [FK_SRCD_EMP_EmpleadoId] FOREIGN KEY([SRCD_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[SolicitudesRenovacionesContratacionesDetalles] CHECK CONSTRAINT [FK_SRCD_EMP_EmpleadoId]
GO

ALTER TABLE [dbo].[SolicitudesRenovacionesContratacionesDetalles]  WITH CHECK ADD  CONSTRAINT [FK_SRCD_CMM_EstatusId] FOREIGN KEY([SRCD_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[SolicitudesRenovacionesContratacionesDetalles] CHECK CONSTRAINT [FK_SRCD_CMM_EstatusId]
GO