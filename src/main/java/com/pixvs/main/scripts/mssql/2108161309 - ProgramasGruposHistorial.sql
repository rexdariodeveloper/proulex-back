/**
 * Created by Hernandez Soto Cesar on 10/08/2021.
 * Object:  Table [dbo].[ProgramasGruposHistorial]
 */

/**************************************************/
/***** ProgramasGruposHistorial *****/
/**************************************************/

CREATE TABLE [dbo].[ProgramasGruposHistorial](
	[PROGRUH_ProgramaGrupoHistorialId] [int] IDENTITY(1,1) NOT NULL,
	[PROGRUH_INS_InscripcionId] [int] NOT NULL,
    [PROGRUH_Comentario] [varchar](500) NOT NULL,
	[PROGRUH_Activo] [bit] NOT NULL,
	[PROGRUH_FechaCreacion] [datetime2](7) NOT NULL,
	[PROGRUH_FechaModificacion] [datetime2](7) NULL,
	[PROGRUH_USU_CreadoPorId] [int] NULL,
	[PROGRUH_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[PROGRUH_ProgramaGrupoHistorialId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

-- Constraints FK
ALTER TABLE [dbo].[ProgramasGruposHistorial]  WITH CHECK ADD  CONSTRAINT [FK_PROGRUH_INS_InscripcionId] FOREIGN KEY([PROGRUH_INS_InscripcionId])
REFERENCES [dbo].[Inscripciones] ([INS_InscripcionId])
GO

ALTER TABLE [dbo].[ProgramasGruposHistorial] CHECK CONSTRAINT [FK_PROGRUH_INS_InscripcionId]
GO

ALTER TABLE [dbo].[ProgramasGruposHistorial]  WITH CHECK ADD  CONSTRAINT [FK_PROGRUH_USU_CreadoPorId] FOREIGN KEY([PROGRUH_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasGruposHistorial] CHECK CONSTRAINT [FK_PROGRUH_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[ProgramasGruposHistorial]  WITH CHECK ADD  CONSTRAINT [FK_PROGRUH_USU_ModificadoPorId] FOREIGN KEY([PROGRUH_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasGruposHistorial] CHECK CONSTRAINT [FK_PROGRUH_USU_ModificadoPorId]
GO