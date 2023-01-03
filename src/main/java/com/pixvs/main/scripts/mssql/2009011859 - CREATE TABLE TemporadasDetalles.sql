SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

DROP TABLE IF EXISTS [TemporadasDetalles]
GO

CREATE TABLE [dbo].[TemporadasDetalles](
	[TEMD_TemporadaDetalleId] 	[int] IDENTITY(1,1) NOT NULL,
	[TEMD_TEM_TemporadaId] 		[int] NOT NULL,
	[TEMD_ART_ArticuloId] 		[int] NOT NULL,
	[TEMD_Minimo] 				[decimal](28, 6) NOT NULL,
	[TEMD_Maximo] 				[decimal](28, 6) NOT NULL,
	[TEMD_CMM_CriterioId] 		[int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[TEMD_TemporadaDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[TemporadasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_TEMD_TEM_TemporadaId] FOREIGN KEY([TEMD_TEM_TemporadaId])
REFERENCES [dbo].[Temporadas] ([TEM_TemporadaId])
GO

ALTER TABLE [dbo].[TemporadasDetalles] CHECK CONSTRAINT [FK_TEMD_TEM_TemporadaId]
GO

ALTER TABLE [dbo].[TemporadasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_TEMD_ART_ArticuloId] FOREIGN KEY([TEMD_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[TemporadasDetalles] CHECK CONSTRAINT [FK_TEMD_ART_ArticuloId]
GO

ALTER TABLE [dbo].[TemporadasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_TEMD_CMM_CriterioId] FOREIGN KEY([TEMD_CMM_CriterioId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[TemporadasDetalles] CHECK CONSTRAINT [FK_TEMD_CMM_CriterioId]
GO

ALTER TABLE [dbo].[TemporadasDetalles] ADD  CONSTRAINT [DF_TEMD_Minimo]  DEFAULT (0) FOR [TEMD_Minimo]
GO

ALTER TABLE [dbo].[TemporadasDetalles] ADD  CONSTRAINT [DF_TEMD_Maximo]  DEFAULT (0) FOR [TEMD_Maximo]
GO