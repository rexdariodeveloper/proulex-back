SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/* ****************************************************************
 * Descripción: Crear la tabla de EmpleadosContratos
 * Autor: Rene Carrillo
 * Fecha: 21.04.2022
 * Versión: 1.0.0
 *****************************************************************
*/

CREATE TABLE [dbo].[EmpleadosContratos](
	[EMPCO_EmpleadoContratoId] [int] IDENTITY(1,1) NOT NULL,
	[EMPCO_EMP_EmpleadoId] [int] NOT NULL,
	[EMPCO_Codigo] [varchar](50) NOT NULL,
	[EMPCO_CMM_JustificacionId] [int] NOT NULL,
	[EMPCO_CMM_TipoContratoId] [int] NOT NULL,
	[EMPCO_DEP_DepartamentoId] [int] NOT NULL,
	[EMPCO_IngresosAdicionales] [nvarchar](200) NULL,
	[EMPCO_SueldoMensual] [decimal](10, 2) NOT NULL,
	[EMPCO_FechaInicio] [date] NOT NULL,
	[EMPCO_FechaFin] [date] NOT NULL,
	[EMPCO_CMM_TipoHorarioId] [int] NOT NULL,
	[EMPCO_CantidadHoraSemana] [int] NULL,
	[EMPCO_Domicilio] [nvarchar](255) NOT NULL,
	[EMPCO_CP] [varchar](10) NOT NULL,
	[EMPCO_Colonia] [varchar](100) NOT NULL,
	[EMPCO_PAI_PaisId] [smallint] NOT NULL,
	[EMPCO_EST_EstadoId] [int] NOT NULL,
	[EMPCO_MUN_MunicipioId] [int] NOT NULL,
	[EMPCO_FechaContrato] [datetime] NOT NULL,
	[EMPCO_FechaCancelacion] [datetime] NULL,
	[EMPCO_MotivoCancelacion] [nvarchar](1000) NULL,
	[EMPCO_FechaImpresion] [datetime] NULL,
	[EMPCO_EMP_ImpresoPor] [int] NULL,
	[EMPCO_ReferenciaId] [int] NULL,
	[EMPCO_CadenaDigest] [nvarchar](1000) NULL,
	[EMPCO_CadenaFirma] [nvarchar](1000) NULL,
	[EMPCO_CMM_EstatusId] [int] NOT NULL,
	[EMPCO_FechaCreacion] [datetime2](7) NOT NULL,
	[EMPCO_USU_CreadoPorId] [int] NOT NULL,
	[EMPCO_FechaUltimaModificacion] [datetime2](7) NULL,
	[EMPCO_USU_ModificadoPorId] [int] NULL
 CONSTRAINT [PK_EmpleadosContratos] PRIMARY KEY CLUSTERED
(
	[EMPCO_EmpleadoContratoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[EmpleadosContratos] ADD  CONSTRAINT [DF_EmpleadosContratos_EMPCO_FechaCreacion]  DEFAULT (getdate()) FOR [EMPCO_FechaCreacion]
GO

ALTER TABLE [dbo].[EmpleadosContratos]  WITH CHECK ADD  CONSTRAINT [FK_EMPCO_EMP_EmpleadoId] FOREIGN KEY([EMPCO_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[EmpleadosContratos] CHECK CONSTRAINT [FK_EMPCO_EMP_EmpleadoId]
GO

ALTER TABLE [dbo].[EmpleadosContratos]  WITH CHECK ADD  CONSTRAINT [FK_EMPCO_CMM_JustificacionId] FOREIGN KEY([EMPCO_CMM_JustificacionId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[EmpleadosContratos] CHECK CONSTRAINT [FK_EMPCO_CMM_JustificacionId]
GO

ALTER TABLE [dbo].[EmpleadosContratos]  WITH CHECK ADD  CONSTRAINT [FK_EMPCO_CMM_TipoContratoId] FOREIGN KEY([EMPCO_CMM_TipoContratoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[EmpleadosContratos] CHECK CONSTRAINT [FK_EMPCO_CMM_TipoContratoId]
GO

ALTER TABLE [dbo].[EmpleadosContratos]  WITH CHECK ADD  CONSTRAINT [FK_EMPCO_DEP_DepartamentoId] FOREIGN KEY([EMPCO_DEP_DepartamentoId])
REFERENCES [dbo].[Departamentos] ([DEP_DepartamentoId])
GO

ALTER TABLE [dbo].[EmpleadosContratos] CHECK CONSTRAINT [FK_EMPCO_DEP_DepartamentoId]
GO

ALTER TABLE [dbo].[EmpleadosContratos]  WITH CHECK ADD  CONSTRAINT [FK_EMPCO_CMM_TipoHorarioId] FOREIGN KEY([EMPCO_CMM_TipoHorarioId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[EmpleadosContratos] CHECK CONSTRAINT [FK_EMPCO_CMM_TipoHorarioId]
GO

ALTER TABLE [dbo].[EmpleadosContratos]  WITH CHECK ADD  CONSTRAINT [FK_EMPCO_PAI_PaisId] FOREIGN KEY([EMPCO_PAI_PaisId])
REFERENCES [dbo].[Paises] ([PAI_PaisId])
GO

ALTER TABLE [dbo].[EmpleadosContratos] CHECK CONSTRAINT [FK_EMPCO_PAI_PaisId]
GO

ALTER TABLE [dbo].[EmpleadosContratos]  WITH CHECK ADD  CONSTRAINT [FK_EMPCO_EST_EstadoId] FOREIGN KEY([EMPCO_EST_EstadoId])
REFERENCES [dbo].[Estados] ([EST_EstadoId])
GO

ALTER TABLE [dbo].[EmpleadosContratos] CHECK CONSTRAINT [FK_EMPCO_EST_EstadoId]
GO

ALTER TABLE [dbo].[EmpleadosContratos]  WITH CHECK ADD  CONSTRAINT [FK_EMPCO_MUN_MunicipioId] FOREIGN KEY([EMPCO_MUN_MunicipioId])
REFERENCES [dbo].[Municipios] ([MUN_MunicipioId])
GO

ALTER TABLE [dbo].[EmpleadosContratos] CHECK CONSTRAINT [FK_EMPCO_MUN_MunicipioId]
GO

ALTER TABLE [dbo].[EmpleadosContratos]  WITH CHECK ADD  CONSTRAINT [FK_EMPCO_EMP_ImpresoPor] FOREIGN KEY([EMPCO_EMP_ImpresoPor])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[EmpleadosContratos] CHECK CONSTRAINT [FK_EMPCO_EMP_ImpresoPor]
GO

ALTER TABLE [dbo].[EmpleadosContratos]  WITH CHECK ADD  CONSTRAINT [FK_EMPCO_CMM_EstatusId] FOREIGN KEY([EMPCO_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[EmpleadosContratos] CHECK CONSTRAINT [FK_EMPCO_CMM_EstatusId]
GO

ALTER TABLE [dbo].[EmpleadosContratos]  WITH CHECK ADD  CONSTRAINT [FK_EMPCO_USU_CreadoPorId] FOREIGN KEY([EMPCO_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[EmpleadosContratos] CHECK CONSTRAINT [FK_EMPCO_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[EmpleadosContratos]  WITH CHECK ADD  CONSTRAINT [FK_EMPCO_USU_ModificadoPorId] FOREIGN KEY([EMPCO_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[EmpleadosContratos] CHECK CONSTRAINT [FK_EMPCO_USU_ModificadoPorId]
GO