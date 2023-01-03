SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[MenuPrincipalPermisos](
	[MPP_MenuPrincipalPermisoId] [INT] IDENTITY(1,1) NOT NULL,
	[MPP_MP_NodoId] [INT] NOT NULL,
	[MPP_Nombre] [NVARCHAR](50) NOT NULL,
	[MPP_Activo] [BIT] NOT NULL,

 	CONSTRAINT [PK_MenuPrincipalPermisos] PRIMARY KEY CLUSTERED 
	(
		[MPP_MenuPrincipalPermisoId] ASC
	) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[MenuPrincipalPermisos]  WITH CHECK ADD  CONSTRAINT [FK_MPP_MP_NodoId] FOREIGN KEY([MPP_MP_NodoId])
REFERENCES [dbo].[MenuPrincipal] ([MP_NodoId])
GO

ALTER TABLE [dbo].[MenuPrincipalPermisos] CHECK CONSTRAINT [FK_MPP_MP_NodoId]
GO