DROP TABLE IF EXISTS DocumentosConfiguracionRHDetalle
GO

DROP TABLE IF EXISTS DocumentosConfiguracionRH
GO

-- ==========================================================
-- Author: Rene Carrillo
-- Create date: 06/03/2022
-- Modified date:
-- Description:	Creamos la tabla para Configuracion Documentos RH
-- ==========================================================

CREATE TABLE [dbo].[DocumentosConfiguracionRH](
	[DCRH_DocumentosConfiguracionRHId] [int] IDENTITY(1,1) NOT NULL,
	[DCRH_CMM_TipoProcesoRHId] [int] NOT NULL,
	[DCRH_CMM_TipoDocumentoId] [int] NOT NULL,
	[DCRH_CMM_TipoContratoId] [int] NOT NULL,
	[DCRH_CMM_TipoOpcionId] [int] NOT NULL,
	[DCRH_CMM_TipoVigenciaId] [int] NULL,
	[DCRH_CMM_TipoTiempoId] [int] NULL,
	[DCRH_VigenciaCantidad] [decimal](19,2) NULL,
	[DCRH_FechaCreacion] [datetime2](7) NOT NULL,
	[DCRH_USU_CreadoPorId] [int] NOT NULL,
	[DCRH_FechaUltimaModificacion] [datetime2](7) NULL,
	[DCRH_USU_ModificadoPorId] [int] NULL
 CONSTRAINT [PK_DocumentosConfiguracionRH] PRIMARY KEY CLUSTERED
(
	[DCRH_DocumentosConfiguracionRHId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[DocumentosConfiguracionRH]  WITH CHECK ADD  CONSTRAINT [FK_DCRH_CMM_TipoProcesoRHId] FOREIGN KEY([DCRH_CMM_TipoProcesoRHId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[DocumentosConfiguracionRH] CHECK CONSTRAINT [FK_DCRH_CMM_TipoProcesoRHId]
GO

ALTER TABLE [dbo].[DocumentosConfiguracionRH]  WITH CHECK ADD  CONSTRAINT [FK_DCRH_CMM_TipoDocumentoId] FOREIGN KEY([DCRH_CMM_TipoDocumentoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[DocumentosConfiguracionRH] CHECK CONSTRAINT [FK_DCRH_CMM_TipoDocumentoId]
GO

ALTER TABLE [dbo].[DocumentosConfiguracionRH]  WITH CHECK ADD  CONSTRAINT [FK_DCRH_CMM_TipoContratoId] FOREIGN KEY([DCRH_CMM_TipoContratoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[DocumentosConfiguracionRH] CHECK CONSTRAINT [FK_DCRH_CMM_TipoContratoId]
GO

ALTER TABLE [dbo].[DocumentosConfiguracionRH]  WITH CHECK ADD  CONSTRAINT [FK_DCRH_CMM_TipoOpcionId] FOREIGN KEY([DCRH_CMM_TipoOpcionId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[DocumentosConfiguracionRH] CHECK CONSTRAINT [FK_DCRH_CMM_TipoOpcionId]
GO

ALTER TABLE [dbo].[DocumentosConfiguracionRH]  WITH CHECK ADD  CONSTRAINT [FK_DCRH_CMM_TipoVigenciaId] FOREIGN KEY([DCRH_CMM_TipoVigenciaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[DocumentosConfiguracionRH] CHECK CONSTRAINT [FK_DCRH_CMM_TipoVigenciaId]
GO

ALTER TABLE [dbo].[DocumentosConfiguracionRH]  WITH CHECK ADD  CONSTRAINT [FK_DCRH_CMM_TipoTiempoId] FOREIGN KEY([DCRH_CMM_TipoTiempoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[DocumentosConfiguracionRH] CHECK CONSTRAINT [FK_DCRH_CMM_TipoTiempoId]
GO

ALTER TABLE [dbo].[DocumentosConfiguracionRH] ADD CONSTRAINT [DF_DocumentosConfiguracionRH_DCRH_FechaCreacion]  DEFAULT (getdate()) FOR [DCRH_FechaCreacion]
GO

ALTER TABLE [dbo].[DocumentosConfiguracionRH]  WITH CHECK ADD  CONSTRAINT [FK_DCRH_USU_CreadoPorId] FOREIGN KEY([DCRH_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[DocumentosConfiguracionRH] CHECK CONSTRAINT [FK_DCRH_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[DocumentosConfiguracionRH]  WITH CHECK ADD  CONSTRAINT [FK_DCRH_USU_ModificadoPorId] FOREIGN KEY([DCRH_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[DocumentosConfiguracionRH] CHECK CONSTRAINT [FK_DCRH_USU_ModificadoPorId]
GO