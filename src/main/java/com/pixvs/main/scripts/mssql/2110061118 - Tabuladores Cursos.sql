--------------------- Listados de Precios ---------------------------------------------
CREATE TABLE [dbo].[TabuladoresCursos](
	[TABC_TabuladorCursoId] [int] IDENTITY(1,1) NOT NULL,
	[TABC_TAB_TabuladorId] [int] NOT NULL,
	[TABC_PROG_ProgramaId] [int] NOT NULL,
	[TABC_PAMOD_ModalidadId] [int] NOT NULL,
	[TABC_PAMODH_PAModalidadHorarioId] [int] NOT NULL,
	[TABC_PROGI_ProgramaIdiomaId] [int] NOT NULL,
	[TABC_Activo] [bit] NOT NULL
PRIMARY KEY CLUSTERED 
(
	[TABC_TabuladorCursoId] ASC
),CONSTRAINT [TABC_UNIQUE] UNIQUE NONCLUSTERED
    (
        [TABC_PROG_ProgramaId], [TABC_PAMOD_ModalidadId], [TABC_PROGI_ProgramaIdiomaId], [TABC_PAMODH_PAModalidadHorarioId]
    )
	WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO 

ALTER TABLE [dbo].[TabuladoresCursos]  WITH CHECK ADD  CONSTRAINT [FK_TABC_TAB_TabuladorId] FOREIGN KEY([TABC_TAB_TabuladorId])
REFERENCES [dbo].[Tabuladores] ([TAB_TabuladorId])
GO

ALTER TABLE [dbo].[TabuladoresCursos] CHECK CONSTRAINT [FK_TABC_TAB_TabuladorId]
GO

-----
ALTER TABLE [dbo].[TabuladoresCursos]  WITH CHECK ADD  CONSTRAINT [FK_TABC_PAMOD_ModalidadId] FOREIGN KEY([TABC_PAMOD_ModalidadId])
REFERENCES [dbo].[PAModalidades] ([PAMOD_ModalidadId])
GO

ALTER TABLE [dbo].[TabuladoresCursos] CHECK CONSTRAINT [FK_TABC_PAMOD_ModalidadId]
GO

------

ALTER TABLE [dbo].[TabuladoresCursos]  WITH CHECK ADD  CONSTRAINT [FK_TABC_PAMODH_PAModalidadHorarioId] FOREIGN KEY([TABC_PAMODH_PAModalidadHorarioId])
REFERENCES [dbo].[PAModalidadesHorarios] ([PAMODH_PAModalidadHorarioId])
GO

ALTER TABLE [dbo].[TabuladoresCursos] CHECK CONSTRAINT [FK_TABC_PAMODH_PAModalidadHorarioId]
GO

------

ALTER TABLE [dbo].[TabuladoresCursos]  WITH CHECK ADD  CONSTRAINT [FK_TABC_PROGI_ProgramaIdiomaId] FOREIGN KEY([TABC_PROGI_ProgramaIdiomaId])
REFERENCES [dbo].[ProgramasIdiomas] ([PROGI_ProgramaIdiomaId])
GO

ALTER TABLE [dbo].[TabuladoresCursos] CHECK CONSTRAINT [FK_TABC_PROGI_ProgramaIdiomaId]
GO

