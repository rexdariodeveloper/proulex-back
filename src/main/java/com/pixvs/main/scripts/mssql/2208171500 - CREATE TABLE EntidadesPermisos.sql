SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[EntidadesPermisos](
	[ENTP_EntidadPermisoId] [int] IDENTITY(1,1) NOT NULL,
	[ENTP_ENT_EntidadId] [int] NOT NULL,
	[ENTP_MP_NodoId] [int] NOT NULL,
	[ENTP_CMM_TipoPermisoId] [int] NOT NULL,
	[ENTP_Campo] [nvarchar](150) NOT NULL,
 CONSTRAINT [PK_EntidadesPermisos] PRIMARY KEY CLUSTERED
(
	[ENTP_ENT_EntidadId] ASC,
	[ENTP_CMM_TipoPermisoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[EntidadesPermisos]  WITH CHECK ADD  CONSTRAINT [FK_EntidadesPermisos_Entidades] FOREIGN KEY([ENTP_ENT_EntidadId])
REFERENCES [dbo].[Entidades] ([ENT_EntidadId])
GO

ALTER TABLE [dbo].[EntidadesPermisos] CHECK CONSTRAINT [FK_EntidadesPermisos_Entidades]
GO

ALTER TABLE [dbo].[EntidadesPermisos]  WITH CHECK ADD  CONSTRAINT [FK_EntidadesPermisos_MenuPrincipal] FOREIGN KEY([ENTP_MP_NodoId])
REFERENCES [dbo].[MenuPrincipal] ([MP_NodoId])
GO

ALTER TABLE [dbo].[EntidadesPermisos] CHECK CONSTRAINT [FK_EntidadesPermisos_MenuPrincipal]
GO

ALTER TABLE [dbo].[EntidadesPermisos]  WITH CHECK ADD  CONSTRAINT [FK_EntidadesPermisos_ControlesMaestrosMultiples] FOREIGN KEY([ENTP_CMM_TipoPermisoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[EntidadesPermisos] CHECK CONSTRAINT [FK_EntidadesPermisos_ControlesMaestrosMultiples]
GO