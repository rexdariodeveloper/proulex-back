/****** Object:  Table [dbo].[ControlesMaestrosMultiples]    Script Date: 08/05/2020 04:56:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ControlesMaestrosMultiples](
	[CMM_ControlId] [int] IDENTITY(1,1) NOT NULL,
	[CMM_Control] [varchar](255) NOT NULL,
	[CMM_Valor] [varchar](255) NOT NULL,
	[CMM_Activo] [bit] NULL,
	[CMM_Referencia] [varchar](255) NULL,
	[CMM_Sistema] [bit] NULL,
	[CMM_USU_CreadoPorId] [int] NULL,
	[CMM_FechaCreacion] [datetime2](7) NOT NULL,
	[CMM_USU_ModificadoPorId] [int] NULL,
	[CMM_FechaModificacion] [datetime2](7) NULL,		
	
PRIMARY KEY CLUSTERED 
(
	[CMM_ControlId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[MenuPrincipal]    Script Date: 08/05/2020 04:56:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MenuPrincipal](
	[MP_NodoId] [int] IDENTITY(1,1) NOT NULL,
	[MP_NodoPadreId] [int] NULL,
	[MP_Titulo] [varchar](255) NOT NULL,
	[MP_TituloEN] [varchar](255) NOT NULL,
	[MP_Activo] [bit] NULL,
	[MP_Icono] [varchar](255) NULL,
	[MP_Orden] [int] NOT NULL,
	[MP_Tipo] [varchar](255) NOT NULL,
	[MP_URL] [varchar](255) NULL,
	[MP_CMM_SistemaAccesoId] [int] NOT NULL,
	[MP_FechaCreacion] [datetime2](7) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[MP_NodoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Roles]    Script Date: 08/05/2020 04:56:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Roles](
	[ROL_RolId] [int] IDENTITY(1,1) NOT NULL,
	[ROL_Nombre] [varchar](255) NOT NULL,
	[ROL_Activo] [bit] NULL,
	[ROL_USU_CreadoPorId] [int] NULL,
	[ROL_FechaCreacion] [datetime2](7) NOT NULL,
	[ROL_USU_ModificadoPorId] [int] NULL,
	[ROL_FechaModificacion] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[ROL_RolId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[Usuarios]    Script Date: 08/05/2020 04:56:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Usuarios](
	[USU_UsuarioId] [int] IDENTITY(1,1) NOT NULL,	
	[USU_Nombre] [varchar](200) NOT NULL,
	[USU_PrimerApellido] [varchar](255) NOT NULL,
	[USU_SegundoApellido] [varchar](255) NULL,	
	[USU_CorreoElectronico] [varchar](255) NOT NULL,	
	[USU_Contrasenia] [varchar](255) NOT NULL,
	[USU_ROL_RolId] [int] NOT NULL,
	[USU_CMM_EstatusId] [int] NOT NULL,
	[USU_ARC_ArchivoId] [int] NULL,
	[USU_Codigo] [varchar](10) NULL,
	[USU_FechaVencimiento] [datetime2](7) NULL,
	[USU_USU_CreadoPorId] [int] NULL,
	[USU_FechaCreacion] [datetime2](7) NOT NULL,
	[USU_USU_ModificadoPorId] [int] NULL,
	[USU_FechaModificacion] [datetime2](7) NULL,
	[USU_FechaUltimaSesion] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[USU_UsuarioId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[Archivos]    Script Date: 08/05/2020 04:56:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Archivos](
	[ARC_ArchivoId] [int] IDENTITY(1,1) NOT NULL,
	[ARC_NombreFisico] [varchar](255) NOT NULL,
	[ARC_NombreOriginal] [varchar](255) NOT NULL,
	[ARC_RutaFisica] [varchar](255) NOT NULL,
	[ARC_CMM_TipoId] [int] NULL,
	[ARC_Activo] [bit] NOT NULL,
	[ARC_Publico] [bit] NOT NULL,
	[ARC_AEC_EstructuraId] [int] NOT NULL,
	[ARC_USU_CreadoPorId] [int] NOT NULL,
	[ARC_FechaCreacion] [datetime2](7) NOT NULL,

PRIMARY KEY CLUSTERED 
(
	[ARC_ArchivoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ArchivosEstructuraCarpetas]    Script Date: 08/05/2020 04:56:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ArchivosEstructuraCarpetas](
	[AEC_EstructuraId] [int] IDENTITY(1,1) NOT NULL,
	[AEC_AEC_EstructuraReferenciaId] [int] NULL,
	[AEC_Descripcion] [varchar](255) NOT NULL,
	[AEC_NombreCarpeta] [varchar](255) NOT NULL,
	[AEC_Activo] [bit] NOT NULL,
	[AEC_USU_CreadoPorId] [int] NOT NULL,
	[AEC_FechaCreacion] [datetime2](7) NOT NULL,
	
PRIMARY KEY CLUSTERED 
(
	[AEC_EstructuraId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Autonumericos]    Script Date: 08/05/2020 04:56:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Autonumericos](
	[AUT_AutonumericoId] [int] IDENTITY(1,1) NOT NULL,	
	[AUT_Nombre] [varchar](50) NOT NULL,
	[AUT_Prefijo] [varchar](255) NOT NULL,
	[AUT_Siguiente] [int] NOT NULL,
	[AUT_Digitos] [int] NOT NULL,
	[AUT_Activo] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[AUT_AutonumericoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ControlesMaestros]    Script Date: 08/05/2020 04:56:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ControlesMaestros](
	[CMA_Nombre] [nvarchar](100) NOT NULL,
	[CMA_Valor] [nvarchar](max) NOT NULL,
	[CMA_Sistema] [bit] NOT NULL,
	[CMA_FechaModificacion] [datetime] NULL,
	[CMA_Timestamp] [timestamp] NOT NULL,
 CONSTRAINT [PK_ControlesMaestros] PRIMARY KEY CLUSTERED 
(
	[CMA_Nombre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

/****** Object:  Table [dbo].[RolesMenus]    Script Date: 08/05/2020 04:56:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RolesMenus](
	[ROLMP_RolMenuId] [int] IDENTITY(1,1) NOT NULL,
	[ROLMP_USU_CreadoPorId] [int] NULL,
	[ROLMP_Crear] [bit] NOT NULL,
	[ROLMP_Eliminar] [bit] NOT NULL,
	[ROLMP_FechaCreacion] [datetime2](7) NOT NULL,
	[ROLMP_FechaModificacion] [datetime2](7) NULL,
	[ROLMP_Modificar] [bit] NOT NULL,
	[ROLMP_MP_NodoId] [int] NOT NULL,
	[ROLMP_ROL_RolId] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ROLMP_RolMenuId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[UsuariosRecuperaciones]    Script Date: 08/05/2020 04:56:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UsuariosRecuperaciones](
	[USUR_RecuperacionId] [int] IDENTITY(1,1) NOT NULL,
	[USUR_CMM_EstatusId] [int] NOT NULL,
	[USUR_FechaExpiracion] [datetime2](7) NOT NULL,
	[USUR_FechaSolicitud] [datetime2](7) NOT NULL,
	[USUR_FechaModificacion] [datetime2](7) NULL,
	[USUR_Token] [varchar](255) NOT NULL,
	[USUR_USU_UsuarioId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[USUR_RecuperacionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


SET IDENTITY_INSERT [dbo].[ArchivosEstructuraCarpetas] ON 
GO
INSERT [dbo].[ArchivosEstructuraCarpetas] ([AEC_EstructuraId], [AEC_Activo], [AEC_USU_CreadoPorId], [AEC_Descripcion], [AEC_AEC_EstructuraReferenciaId], [AEC_FechaCreacion], [AEC_NombreCarpeta]) 
VALUES (1, 1, 1, N'Usuarios', NULL, CAST(N'2020-01-01T00:00:00.0000000' AS DateTime2), N'usuarios')
GO
INSERT [dbo].[ArchivosEstructuraCarpetas] ([AEC_EstructuraId], [AEC_Activo], [AEC_USU_CreadoPorId], [AEC_Descripcion], [AEC_AEC_EstructuraReferenciaId], [AEC_FechaCreacion], [AEC_NombreCarpeta]) 
VALUES (2, 1, 1, N'Perfiles', 1, CAST(N'2020-01-01T00:00:00.0000000' AS DateTime2), N'perfiles')
GO
SET IDENTITY_INSERT [dbo].[ArchivosEstructuraCarpetas] OFF
GO



INSERT [dbo].[ControlesMaestros] ([CMA_Nombre], [CMA_Valor], [CMA_Sistema], [CMA_FechaModificacion]) 
VALUES (N'CM_NOMBRE_EMPRESA', N'PIXVS', 1, CAST(N'2020-04-05T00:55:27.103' AS DateTime))
GO
INSERT [dbo].[ControlesMaestros] ([CMA_Nombre], [CMA_Valor], [CMA_Sistema], [CMA_FechaModificacion]) 
VALUES (N'CM_SYS_SCRIPTS', N'0', 1, CAST(N'2020-04-05T00:55:27.103' AS DateTime))
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON 
GO
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor]) VALUES (1000001, 1, N'CMM_Estatus', NULL, CAST(N'2020-03-29T05:46:26.1833333' AS DateTime2), NULL, NULL, NULL, 1, N'Activo')
GO
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor]) VALUES (1000002, 1, N'CMM_Estatus', NULL, CAST(N'2020-03-29T05:46:26.1866667' AS DateTime2), NULL, NULL, NULL, 1, N'Inactivo')
GO
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor]) VALUES (1000003, 1, N'CMM_Estatus', NULL, CAST(N'2020-03-29T05:46:26.1900000' AS DateTime2), NULL, NULL, NULL, 1, N'Borrado')
GO
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor]) VALUES (1000010, 1, N'CMM_MP_TipoNodo', NULL, CAST(N'2020-03-29T05:46:26.1833333' AS DateTime2), NULL, NULL, NULL, 1, N'Grupo')
GO
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor]) VALUES (1000011, 1, N'CMM_MP_TipoNodo', NULL, CAST(N'2020-03-29T05:46:26.1866667' AS DateTime2), NULL, NULL, NULL, 1, N'Nodo')
GO
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor]) VALUES (1000012, 1, N'CMM_MP_TipoNodo', NULL, CAST(N'2020-03-29T05:46:26.1900000' AS DateTime2), NULL, NULL, NULL, 1, N'Ficha')
GO
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor]) VALUES (1000021, 1, N'CMM_SYS_SistemaAcceso', NULL, CAST(N'2020-03-29T05:50:50.0600000' AS DateTime2), NULL, NULL, NULL, 1, N'Web')
GO
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor]) VALUES (1000022, 1, N'CMM_SYS_SistemaAcceso', NULL, CAST(N'2020-03-29T05:50:50.0633333' AS DateTime2), NULL, NULL, NULL, 1, N'App')
GO
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor]) VALUES (1000041, 1, N'CMM_USUR_EstatusUsuarioRecuperacion', NULL, CAST(N'2020-03-29T05:50:50.0633333' AS DateTime2), NULL, NULL, NULL, 1, N'Solicitado')
GO
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor]) VALUES (1000042, 1, N'CMM_USUR_EstatusUsuarioRecuperacion', NULL, CAST(N'2020-03-29T05:50:50.0633333' AS DateTime2), NULL, NULL, NULL, 1, N'Expirado')
GO
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor]) VALUES (1000043, 1, N'CMM_USUR_EstatusUsuarioRecuperacion', NULL, CAST(N'2020-03-29T05:50:50.0633333' AS DateTime2), NULL, NULL, NULL, 1, N'Usado')
GO
SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO


SET IDENTITY_INSERT [dbo].[MenuPrincipal] ON 
GO
INSERT [dbo].[MenuPrincipal] ([MP_NodoId], [MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES (1, 1, CAST(N'2020-03-30T23:58:45.3700000' AS DateTime2), NULL, NULL, 1, 1000021, N'MÓDULOS', N'MODULES', N'group', NULL)
GO
INSERT [dbo].[MenuPrincipal] ([MP_NodoId], [MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES (2, 1, CAST(N'2020-03-30T23:58:45.3800000' AS DateTime2), N'folder_open', 1, 2, 1000021, N'Reportes', N'Reports', N'collapsable', NULL)
GO
INSERT [dbo].[MenuPrincipal] ([MP_NodoId], [MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES (3, 1, CAST(N'2020-03-30T23:58:45.3933333' AS DateTime2), N'list', 1, 3, 1000021, N'Catálogos', N'Catalogs', N'collapsable', NULL)
GO
INSERT [dbo].[MenuPrincipal] ([MP_NodoId], [MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES (4, 1, CAST(N'2020-03-30T23:58:45.4033333' AS DateTime2), NULL, NULL, 2, 1000021, N'CONFIGURACIÓN', N'SETTING', N'group', NULL)
GO
INSERT [dbo].[MenuPrincipal] ([MP_NodoId], [MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES (5, 1, CAST(N'2020-03-30T23:58:45.4066667' AS DateTime2), N'person', 4, 1, 1000021, N'Usuarios', N'Users', N'item', N'/config/usuarios')
GO
INSERT [dbo].[MenuPrincipal] ([MP_NodoId], [MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES (6, 1, CAST(N'2020-03-30T23:58:45.4200000' AS DateTime2), N'supervised_user_circle', 4, 2, 1000021, N'Roles', N'Rols', N'item', N'/config/roles')
GO
SET IDENTITY_INSERT [dbo].[MenuPrincipal] OFF
GO


SET IDENTITY_INSERT [dbo].[Usuarios] ON 
GO
INSERT [dbo].[Usuarios] ([USU_UsuarioId], [USU_ARC_ArchivoId], [USU_Contrasenia], [USU_CorreoElectronico], [USU_CMM_EstatusId], [USU_FechaCreacion], [USU_FechaModificacion], [USU_FechaUltimaSesion], [USU_Nombre], [USU_PrimerApellido], [USU_ROL_RolId], [USU_SegundoApellido], [USU_USU_CreadoPorId], [USU_USU_ModificadoPorId], [USU_Codigo], [USU_FechaVencimiento]) 
VALUES (1, null, N'$2a$10$.cmWFNhnBWz3MSapjirpLev6u7MWwHgguUV.eKv9YEStFvkuqtRVm', N'pixvs.server@gmail.com', 1000001, CAST(N'2020-03-29T05:34:52.7940000' AS DateTime2), CAST(N'2020-05-07T00:56:31.4533333' AS DateTime2), CAST(N'2020-05-08T16:15:06.5466667' AS DateTime2), N'Pixvs', N'Admin', 1, NULL, NULL, 1, N'185575', CAST(N'2020-05-07T01:06:09.4166667' AS DateTime2))
GO
SET IDENTITY_INSERT [dbo].[Usuarios] OFF
GO


SET IDENTITY_INSERT [dbo].[Roles] ON 
GO
INSERT [dbo].[Roles] ([ROL_RolId], [ROL_Activo], [ROL_USU_CreadoPorId], [ROL_FechaCreacion], [ROL_FechaModificacion], [ROL_USU_ModificadoPorId], [ROL_Nombre]) 
VALUES (1, 1, NULL, CAST(N'2020-03-29T05:38:00.0000000' AS DateTime2), CAST(N'2020-04-22T15:36:11.0080000' AS DateTime2), null, N'Admin')
GO
SET IDENTITY_INSERT [dbo].[Roles] OFF
GO
SET IDENTITY_INSERT [dbo].[RolesMenus] ON 
GO
INSERT [dbo].[RolesMenus] ([ROLMP_RolMenuId], [ROLMP_USU_CreadoPorId], [ROLMP_Crear], [ROLMP_Eliminar], [ROLMP_FechaCreacion], [ROLMP_FechaModificacion], [ROLMP_Modificar], [ROLMP_MP_NodoId], [ROLMP_ROL_RolId]) 
VALUES (1, NULL, 1, 1, CAST(N'2020-04-22T15:36:10.9400000' AS DateTime2), NULL, 1, 5, 1)
GO
INSERT [dbo].[RolesMenus] ([ROLMP_RolMenuId], [ROLMP_USU_CreadoPorId], [ROLMP_Crear], [ROLMP_Eliminar], [ROLMP_FechaCreacion], [ROLMP_FechaModificacion], [ROLMP_Modificar], [ROLMP_MP_NodoId], [ROLMP_ROL_RolId]) 
VALUES (2, NULL, 1, 1, CAST(N'2020-04-22T15:36:10.9450000' AS DateTime2), NULL, 1, 6, 1)
GO
SET IDENTITY_INSERT [dbo].[RolesMenus] OFF
GO


SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_eqhs9pfpif6vxtmrsqywykfdi]    Script Date: 08/05/2020 04:56:35 p. m. ******/
ALTER TABLE [dbo].[Usuarios] ADD  CONSTRAINT [UK_USU_CorreoElectronico] UNIQUE NONCLUSTERED 
(
	[USU_CorreoElectronico] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Archivos]  WITH CHECK ADD  CONSTRAINT [FK_ARC_USU_CreadoPorId] FOREIGN KEY([ARC_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[Archivos] CHECK CONSTRAINT [FK_ARC_USU_CreadoPorId]
GO
ALTER TABLE [dbo].[Archivos]  WITH CHECK ADD  CONSTRAINT [FK_ARC_CMM_TipoId] FOREIGN KEY([ARC_CMM_TipoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[Archivos] CHECK CONSTRAINT [FK_ARC_CMM_TipoId]
GO
ALTER TABLE [dbo].[Archivos]  WITH CHECK ADD  CONSTRAINT [FK_ARC_AEC_EstructuraId] FOREIGN KEY([ARC_AEC_EstructuraId])
REFERENCES [dbo].[ArchivosEstructuraCarpetas] ([AEC_EstructuraId])
GO
ALTER TABLE [dbo].[Archivos] CHECK CONSTRAINT [FK_ARC_AEC_EstructuraId]
GO
ALTER TABLE [dbo].[ArchivosEstructuraCarpetas]  WITH CHECK ADD  CONSTRAINT [FK_AEC_AEC_EstructuraReferenciaId] FOREIGN KEY([AEC_AEC_EstructuraReferenciaId])
REFERENCES [dbo].[ArchivosEstructuraCarpetas] ([AEC_EstructuraId])
GO
ALTER TABLE [dbo].[ArchivosEstructuraCarpetas] CHECK CONSTRAINT [FK_AEC_AEC_EstructuraReferenciaId]
GO
ALTER TABLE [dbo].[ControlesMaestrosMultiples]  WITH CHECK ADD  CONSTRAINT [FK_CMM_USU_CreadoPorId] FOREIGN KEY([CMM_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[ControlesMaestrosMultiples] CHECK CONSTRAINT [FK_CMM_USU_CreadoPorId]
GO
ALTER TABLE [dbo].[ControlesMaestrosMultiples]  WITH CHECK ADD  CONSTRAINT [FK_CMM_USU_ModificadoPorId] FOREIGN KEY([CMM_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[ControlesMaestrosMultiples] CHECK CONSTRAINT [FK_CMM_USU_ModificadoPorId]
GO


ALTER TABLE [dbo].[Roles]  WITH CHECK ADD  CONSTRAINT [FK_ROL_USU_ModificadoPorId] FOREIGN KEY([ROL_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[Roles] CHECK CONSTRAINT [FK_ROL_USU_ModificadoPorId]
GO
ALTER TABLE [dbo].[Roles]  WITH CHECK ADD  CONSTRAINT [FK_ROL_USU_CreadoPorId] FOREIGN KEY([ROL_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[Roles] CHECK CONSTRAINT [FK_ROL_USU_CreadoPorId]
GO
ALTER TABLE [dbo].[RolesMenus]  WITH CHECK ADD  CONSTRAINT [FK_ROLMP_MP_NodoId] FOREIGN KEY([ROLMP_MP_NodoId])
REFERENCES [dbo].[MenuPrincipal] ([MP_NodoId])
GO
ALTER TABLE [dbo].[RolesMenus] CHECK CONSTRAINT [FK_ROLMP_MP_NodoId]
GO
ALTER TABLE [dbo].[RolesMenus]  WITH CHECK ADD  CONSTRAINT [FK_ROLMP_ROL_RolId] FOREIGN KEY([ROLMP_ROL_RolId])
REFERENCES [dbo].[Roles] ([ROL_RolId])
GO
ALTER TABLE [dbo].[RolesMenus] CHECK CONSTRAINT [FK_ROLMP_ROL_RolId]
GO


ALTER TABLE [dbo].[Usuarios]  WITH CHECK ADD  CONSTRAINT [FK_USU_ARC_ArchivoId] FOREIGN KEY([USU_ARC_ArchivoId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO
ALTER TABLE [dbo].[Usuarios] CHECK CONSTRAINT [FK_USU_ARC_ArchivoId]
GO
ALTER TABLE [dbo].[Usuarios]  WITH CHECK ADD  CONSTRAINT [FK_USU_USU_ModificadoPorId] FOREIGN KEY([USU_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[Usuarios] CHECK CONSTRAINT [FK_USU_USU_ModificadoPorId]
GO
ALTER TABLE [dbo].[Usuarios]  WITH CHECK ADD  CONSTRAINT [FK_USU_CMM_EstatusId] FOREIGN KEY([USU_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[Usuarios] CHECK CONSTRAINT [FK_USU_CMM_EstatusId]
GO
ALTER TABLE [dbo].[Usuarios]  WITH CHECK ADD  CONSTRAINT [FK_USU_USU_CreadoPorId] FOREIGN KEY([USU_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[Usuarios] CHECK CONSTRAINT [FK_USU_USU_CreadoPorId]
GO
ALTER TABLE [dbo].[Usuarios]  WITH CHECK ADD  CONSTRAINT [FK_USU_ROL_RolId] FOREIGN KEY([USU_ROL_RolId])
REFERENCES [dbo].[Roles] ([ROL_RolId])
GO
ALTER TABLE [dbo].[Usuarios] CHECK CONSTRAINT [FK_USU_ROL_RolId]
GO
ALTER TABLE [dbo].[UsuariosRecuperaciones]  WITH CHECK ADD  CONSTRAINT [FK_USUR_USU_UsuarioId] FOREIGN KEY([USUR_USU_UsuarioId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[UsuariosRecuperaciones] CHECK CONSTRAINT [FK_USUR_USU_UsuarioId]
GO



/****** Object:  View [dbo].[VW_LISTADO_ROLES]    Script Date: 08/05/2020 04:56:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   VIEW [dbo].[VW_LISTADO_ROLES] AS

SELECT ROL_Nombre as "Nombre",Rol_FechaCreacion AS "Fecha Creación", m.MP_Orden AS "Orden", m.MP_Titulo AS "Menu"
FROM Roles r
LEFT JOIN RolesMenus rm on  rm.ROLMP_ROL_RolId = r.ROL_RolId
LEFT JOIN MenuPrincipal m ON rm.ROLMP_MP_NodoId = m.MP_NodoId

where m.MP_Activo = 1 and r.ROL_Activo = 1

GO


/****** Object:  View [dbo].[VW_LISTADO_USUARIOS]    Script Date: 08/05/2020 04:56:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   VIEW [dbo].[VW_LISTADO_USUARIOS] AS

SELECT CONCAT(USU_Nombre, ' ',USU_PrimerApellido, USU_SegundoApellido ) as "Nombre", USU_CorreoElectronico AS "Correo Electrónico", USU_FechaCreacion AS "Fecha Creación",
	cmm_estatus.CMM_Valor AS "Estatus", r.ROL_Nombre AS "Rol"
FROM Usuarios u
LEFT JOIN ControlesMaestrosMultiples cmm_estatus on u.USU_CMM_EstatusId = cmm_estatus.CMM_ControlId
LEFT JOIN Roles r on u.USU_ROL_RolId = r.ROL_RolId

where cmm_estatus.CMM_ControlId = 1000001
GO