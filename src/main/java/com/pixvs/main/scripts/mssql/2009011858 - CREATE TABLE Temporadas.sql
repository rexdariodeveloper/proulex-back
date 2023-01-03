SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

DROP TABLE IF EXISTS [Temporadas]
GO

CREATE TABLE [dbo].[Temporadas](
	[TEM_TemporadaId]			[int] IDENTITY(1,1) NOT NULL,
	[TEM_Nombre] 				[varchar](100) NOT NULL,
	[TEM_FechaInicio] 			[date] NOT NULL,
	[TEM_FechaFin] 				[date] NOT NULL,
	[TEM_Activo]				[bit] NOT NULL,
	[TEM_USU_CreadoPorId] 		[int] NOT NULL,
	[TEM_USU_ModificadoPorId] 	[int] NULL,
	[TEM_FechaCreacion] 		[datetime2](7) NOT NULL,
	[TEM_FechaModificacion] 	[datetime2](7) NULL,
	
PRIMARY KEY CLUSTERED 
(
	[TEM_TemporadaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Temporadas]  WITH CHECK ADD  CONSTRAINT [FK_TEM_USU_CreadoPorId] FOREIGN KEY([TEM_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Temporadas] CHECK CONSTRAINT [FK_TEM_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[Temporadas]  WITH CHECK ADD  CONSTRAINT [FK_TEM_USU_ModificadoPorId] FOREIGN KEY([TEM_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Temporadas] CHECK CONSTRAINT [FK_TEM_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[Temporadas] ADD  CONSTRAINT [DF_TEM_FechaCreacion]  DEFAULT (GETDATE()) FOR [TEM_FechaCreacion]
GO

ALTER TABLE [dbo].[Temporadas] ADD  CONSTRAINT [DF_TEM_Activo]  DEFAULT (1) FOR [TEM_Activo]
GO