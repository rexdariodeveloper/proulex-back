SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ProgramasGruposIncompanyComisiones](
	[PGINCC_ProgramaGrupoIncompanyComisionId] [int] IDENTITY(1,1) NOT NULL,
	[PGINCC_PROGRU_GrupoId] [int] NOT NULL,
	[PGINCC_USU_UsuarioId] [int] NOT NULL,
	[PGINCC_Porcentaje] [decimal](10,2) NOT NULL,
	[PGINCC_MontoComision] [decimal](10,2) NOT NULL,
	[PGINCC_Activo] [bit] NOT NULL,
	[PGINCC_FechaCreacion] [datetime2](7) NOT NULL,
	[PGINCC_USU_CreadoPorId] [int] NOT NULL,
	[PGINCC_FechaUltimaModificacion] [datetime2](7) NULL,
	[PGINCC_USU_ModificadoPorId] [int] NULL,
 CONSTRAINT [PK_ProgramasGruposIncompanyComisiones] PRIMARY KEY CLUSTERED
(
	[PGINCC_ProgramaGrupoIncompanyComisionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyComisiones] ADD  CONSTRAINT [DF_ProgramasGruposIncompanyComisiones_PGINCC_FechaCreacion]  DEFAULT (getdate()) FOR [PGINCC_FechaCreacion]
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyComisiones]  WITH CHECK ADD  CONSTRAINT [FK_PGINCC_PROGRU_GrupoId] FOREIGN KEY([PGINCC_PROGRU_GrupoId])
REFERENCES [dbo].[ProgramasGrupos] ([PROGRU_GrupoId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyComisiones] CHECK CONSTRAINT [FK_PGINCC_PROGRU_GrupoId]
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyComisiones]  WITH CHECK ADD  CONSTRAINT [FK_PGINCC_USU_UsuarioId] FOREIGN KEY([PGINCC_USU_UsuarioId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyComisiones] CHECK CONSTRAINT [FK_PGINCC_USU_UsuarioId]
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyComisiones]  WITH CHECK ADD  CONSTRAINT [FK_PGINCC_USU_CreadoPorId] FOREIGN KEY([PGINCC_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyComisiones] CHECK CONSTRAINT [FK_PGINCC_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyComisiones]  WITH CHECK ADD  CONSTRAINT [FK_PGINCC_USU_ModificadoPorId] FOREIGN KEY([PGINCC_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyComisiones] CHECK CONSTRAINT [FK_PGINCC_USU_ModificadoPorId]
GO
