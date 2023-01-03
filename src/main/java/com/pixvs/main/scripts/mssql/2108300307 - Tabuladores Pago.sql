/**
 * Created by Hernandez Soto Cesar on 27/08/2021.
 * Object:  Table [dbo].[Tabuladores]
 */

/**************************************************/
/***** Tabuladores *****/
/**************************************************/

CREATE TABLE [dbo].[Tabuladores](
	[TAB_TabuladorId] [int] IDENTITY(1,1) NOT NULL,
    [TAB_Codigo] [varchar](20) NOT NULL,
	[TAB_Descripcion] [varchar](500) NOT NULL,
	[TAB_Activo] [bit] NOT NULL,
	[TAB_FechaCreacion] [datetime2](7) NOT NULL,
	[TAB_FechaModificacion] [datetime2](7) NULL,
	[TAB_USU_CreadoPorId] [int] NULL,
	[TAB_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[TAB_TabuladorId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Tabuladores] ADD  CONSTRAINT [DF_TAB_FechaCreacion]  DEFAULT (getdate()) FOR [TAB_FechaCreacion]
GO

ALTER TABLE [dbo].[Tabuladores]  WITH CHECK ADD  CONSTRAINT [FK_TAB_USU_CreadoPorId] FOREIGN KEY([TAB_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Tabuladores] CHECK CONSTRAINT [FK_TAB_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[Tabuladores]  WITH CHECK ADD  CONSTRAINT [FK_TAB_USU_ModificadoPorId] FOREIGN KEY([TAB_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Tabuladores] CHECK CONSTRAINT [FK_TAB_USU_ModificadoPorId]
GO

/**************************************************/
/***** Tabuladores Detalles *****/
/**************************************************/

CREATE TABLE [dbo].[TabuladoresDetalles](
	[TABD_TabuladorDetalleId] [int] IDENTITY(1,1) NOT NULL,
	[TABD_TAB_TabuladorId] [int] NOT NULL,
	[TABD_PAPC_ProfesorCategoriaId] [int] NOT NULL,
    [TABD_Sueldo] [decimal](10,2) NOT NULL,
	[TABD_Activo] [bit] NOT NULL
PRIMARY KEY CLUSTERED 
(
	[TABD_TabuladorDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[TabuladoresDetalles]  WITH CHECK ADD  CONSTRAINT [FK_TABD_TAB_TabuladorId] FOREIGN KEY([TABD_TAB_TabuladorId])
REFERENCES [dbo].[Tabuladores] ([TAB_TabuladorId])
GO

ALTER TABLE [dbo].[TabuladoresDetalles] CHECK CONSTRAINT [FK_TABD_TAB_TabuladorId]
GO

ALTER TABLE [dbo].[TabuladoresDetalles]  WITH CHECK ADD  CONSTRAINT [FK_TABD_PAPC_ProfesorCategoriaId] FOREIGN KEY([TABD_PAPC_ProfesorCategoriaId])
REFERENCES [dbo].[PAProfesoresCategorias] ([PAPC_ProfesorCategoriaId])
GO

ALTER TABLE [dbo].[TabuladoresDetalles] CHECK CONSTRAINT [FK_TABD_PAPC_ProfesorCategoriaId]
GO