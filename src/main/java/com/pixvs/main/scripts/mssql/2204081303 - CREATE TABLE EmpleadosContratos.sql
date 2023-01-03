SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- ==========================================================
-- Author: Rene Carrillo
-- Create date: 04/04/2022
-- Modified date:
-- Description:	Creamos la tabla para Empleados Contratos
-- ==========================================================

CREATE TABLE [dbo].[EmpleadosContratos](
	[EMPCO_EmpleadoContratoId] [int] IDENTITY(1,1) NOT NULL,
	[EMPCO_Codigo] [nvarchar](50) NOT NULL,
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