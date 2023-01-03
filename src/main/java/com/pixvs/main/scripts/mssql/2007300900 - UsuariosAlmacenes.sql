/****** Object:  Table [dbo].[UsuariosAlmacenes]    Script Date: 29/07/2020 01:07:00 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UsuariosAlmacenes](
	[USUA_USU_UsuarioId] [int] NOT NULL,
	[USUA_ALM_AlmacenId] [int] NOT NULL,
 CONSTRAINT [PK_UsuariosAlmacenes] PRIMARY KEY CLUSTERED 
(
	[USUA_USU_UsuarioId] ASC,
	[USUA_ALM_AlmacenId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[UsuariosAlmacenes]  WITH CHECK ADD  CONSTRAINT [FK_UsuariosAlmacenes_Usuarios] FOREIGN KEY([USUA_USU_UsuarioId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[UsuariosAlmacenes] CHECK CONSTRAINT [FK_UsuariosAlmacenes_Usuarios]
GO
ALTER TABLE [dbo].[UsuariosAlmacenes]  WITH CHECK ADD  CONSTRAINT [FK_UsuariosAlmacenes_Almacenes] FOREIGN KEY([USUA_ALM_AlmacenId])
REFERENCES [dbo].[Almacenes] ([ALM_AlmacenId])
GO
ALTER TABLE [dbo].[UsuariosAlmacenes] CHECK CONSTRAINT [FK_UsuariosAlmacenes_Almacenes]
GO