/****** Object:  Table [dbo].[Entidades]    Script Date: 21/02/2022 16:18:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Entidades](
	[ENT_EntidadId] [int] IDENTITY(1,1) NOT NULL,
	[ENT_Codigo] [nvarchar](20) NOT NULL,
	[ENT_Prefijo] [nvarchar](20) NOT NULL,
	[ENT_Nombre] [nvarchar](150) NOT NULL,
	[ENT_RazonSocial] [nvarchar](150) NULL,
	[ENT_NombreComercial] [nvarchar](150) NULL,
	[ENT_EMP_Director] [int] NULL,
	[ENT_EMP_Coordinador] [int] NULL,
	[ENT_EMP_JefeUnidadAF] [int] NULL,
	[ENT_Domicilio] [nvarchar](250) NULL,
	[ENT_Colonia] [nvarchar](250) NULL,
	[ENT_CP] [nvarchar](10) NULL,
	[ENT_PAI_PaisId] [smallint] NULL,
	[ENT_AplicaSedes] [bit] NOT NULL,
	[ENT_EST_EstadoId] [int] NULL,
	[ENT_Ciudad] [nvarchar](100) NULL,
	[ENT_EntidadDependienteId] [int] NULL,
	[ENT_USU_CreadoPorId] [int] NULL,
	[ENT_FechaCreacion] [datetime2](7) NOT NULL,
	[ENT_USU_ModificadoPorId] [int] NULL,
	[ENT_FechaModificacion] [datetime2](7) NULL,
	[ENT_Activo] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ENT_EntidadId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Entidades] ON 

INSERT [dbo].[Entidades] ([ENT_EntidadId], [ENT_Codigo], [ENT_Prefijo], [ENT_Nombre], [ENT_RazonSocial], [ENT_NombreComercial], [ENT_EMP_Director], [ENT_EMP_Coordinador], [ENT_EMP_JefeUnidadAF], [ENT_Domicilio], [ENT_Colonia], [ENT_CP], [ENT_PAI_PaisId], [ENT_AplicaSedes], [ENT_EST_EstadoId], [ENT_Ciudad], [ENT_EntidadDependienteId], [ENT_USU_CreadoPorId], [ENT_FechaCreacion], [ENT_USU_ModificadoPorId], [ENT_FechaModificacion], [ENT_Activo]) VALUES (1, N'CCU', N'OPCCU', N'OPERADORA CENTRO CULTURAL UNIVERSITARIO', N'OPERADORA CENTRO CULTURAL UNIVERSITARIO', N'OPERADORA CENTRO CULTURAL UNIVERSITARIO', NULL, NULL, NULL, N'Periférico Norte número 1695, Piso 6', N'', N'', 1, 0, 14, N'Zapopan', NULL, 1, CAST(N'2021-12-09T10:35:13.8166667' AS DateTime2), 1, CAST(N'2021-12-09T10:35:13.8166667' AS DateTime2), 1)
INSERT [dbo].[Entidades] ([ENT_EntidadId], [ENT_Codigo], [ENT_Prefijo], [ENT_Nombre], [ENT_RazonSocial], [ENT_NombreComercial], [ENT_EMP_Director], [ENT_EMP_Coordinador], [ENT_EMP_JefeUnidadAF], [ENT_Domicilio], [ENT_Colonia], [ENT_CP], [ENT_PAI_PaisId], [ENT_AplicaSedes], [ENT_EST_EstadoId], [ENT_Ciudad], [ENT_EntidadDependienteId], [ENT_USU_CreadoPorId], [ENT_FechaCreacion], [ENT_USU_ModificadoPorId], [ENT_FechaModificacion], [ENT_Activo]) VALUES (2, N'CIA', N'CIATC', N'CENTRO INTERNACIONAL DE ANIMACIÓN ', N'CENTRO INTERNACIONAL DE ANIMACIÓN ', N'TALLER DEL CHUCHO', NULL, NULL, NULL, N'Ing. Hugo Vázquez Reyes 40-A', N'', N'', 1, 0, 14, N'Zapopan', NULL, 1, CAST(N'2021-12-09T10:35:13.8300000' AS DateTime2), 1, CAST(N'2021-12-09T10:35:13.8300000' AS DateTime2), 1)
INSERT [dbo].[Entidades] ([ENT_EntidadId], [ENT_Codigo], [ENT_Prefijo], [ENT_Nombre], [ENT_RazonSocial], [ENT_NombreComercial], [ENT_EMP_Director], [ENT_EMP_Coordinador], [ENT_EMP_JefeUnidadAF], [ENT_Domicilio], [ENT_Colonia], [ENT_CP], [ENT_PAI_PaisId], [ENT_AplicaSedes], [ENT_EST_EstadoId], [ENT_Ciudad], [ENT_EntidadDependienteId], [ENT_USU_CreadoPorId], [ENT_FechaCreacion], [ENT_USU_ModificadoPorId], [ENT_FechaModificacion], [ENT_Activo]) VALUES (3, N'EDTUDG', N'EDTUG', N'EDITORIAL UNIVERSITARIA DE LA UNIVERSIDAD DE GUADALAJARA', N'EDITORIAL UNIVERSITARIA DE LA UNIVERSIDAD DE GUADALAJARA', N'EDITORIAL UNIVERSITARIA DE LA UNIVERSIDAD DE GUADALAJARA', NULL, NULL, NULL, N'José Bonifacio Andrada 2679', N'', N'', 1, 0, 14, N'Zapopan', NULL, 1, CAST(N'2021-12-09T10:35:13.8333333' AS DateTime2), 1, CAST(N'2021-12-09T10:35:13.8333333' AS DateTime2), 1)
INSERT [dbo].[Entidades] ([ENT_EntidadId], [ENT_Codigo], [ENT_Prefijo], [ENT_Nombre], [ENT_RazonSocial], [ENT_NombreComercial], [ENT_EMP_Director], [ENT_EMP_Coordinador], [ENT_EMP_JefeUnidadAF], [ENT_Domicilio], [ENT_Colonia], [ENT_CP], [ENT_PAI_PaisId], [ENT_AplicaSedes], [ENT_EST_EstadoId], [ENT_Ciudad], [ENT_EntidadDependienteId], [ENT_USU_CreadoPorId], [ENT_FechaCreacion], [ENT_USU_ModificadoPorId], [ENT_FechaModificacion], [ENT_Activo]) VALUES (4, N'ESTGOU', N'ESGOU', N'ESTACIÓN GOURMET', N'ESTACIÓN GOURMET', N'ESTACIÓN GOURMET', NULL, NULL, NULL, N'Ignacio Jacobo Número 39', N'', N'', 1, 0, 14, N'Zapopan', NULL, 1, CAST(N'2021-12-09T10:35:13.8333333' AS DateTime2), 1, CAST(N'2021-12-09T10:35:13.8333333' AS DateTime2), 1)
INSERT [dbo].[Entidades] ([ENT_EntidadId], [ENT_Codigo], [ENT_Prefijo], [ENT_Nombre], [ENT_RazonSocial], [ENT_NombreComercial], [ENT_EMP_Director], [ENT_EMP_Coordinador], [ENT_EMP_JefeUnidadAF], [ENT_Domicilio], [ENT_Colonia], [ENT_CP], [ENT_PAI_PaisId], [ENT_AplicaSedes], [ENT_EST_EstadoId], [ENT_Ciudad], [ENT_EntidadDependienteId], [ENT_USU_CreadoPorId], [ENT_FechaCreacion], [ENT_USU_ModificadoPorId], [ENT_FechaModificacion], [ENT_Activo]) VALUES (5, N'FIL', N'FILUG', N'FERIA INTERNACIONAL DEL LIBRO DE GUADALAJARA', N'FERIA INTERNACIONAL DEL LIBRO DE GUADALAJARA', N'FIL', NULL, NULL, NULL, N'Av. Alemania 1370', N'', N'', 1, 0, 14, N'Zapopan', NULL, 1, CAST(N'2021-12-09T10:35:13.8366667' AS DateTime2), 1, CAST(N'2021-12-09T10:35:13.8366667' AS DateTime2), 1)
INSERT [dbo].[Entidades] ([ENT_EntidadId], [ENT_Codigo], [ENT_Prefijo], [ENT_Nombre], [ENT_RazonSocial], [ENT_NombreComercial], [ENT_EMP_Director], [ENT_EMP_Coordinador], [ENT_EMP_JefeUnidadAF], [ENT_Domicilio], [ENT_Colonia], [ENT_CP], [ENT_PAI_PaisId], [ENT_AplicaSedes], [ENT_EST_EstadoId], [ENT_Ciudad], [ENT_EntidadDependienteId], [ENT_USU_CreadoPorId], [ENT_FechaCreacion], [ENT_USU_ModificadoPorId], [ENT_FechaModificacion], [ENT_Activo]) VALUES (6, N'HCDUDG', N'HCDUG', N'HOTELES Y CLUB DEPORTIVO UNIVERSIDAD DE GUADALAJARA', N'HOTELES Y CLUB DEPORTIVO UNIVERSIDAD DE GUADALAJARA', N'HOTELES Y CLUB DEPORTIVO UNIVERSIDAD DE GUADALAJARA', NULL, NULL, NULL, N'Ignacio Jacobo número 35', N'', N'', 1, 0, 14, N'Zapopan', NULL, 1, CAST(N'2021-12-09T10:35:13.8400000' AS DateTime2), 1, CAST(N'2021-12-09T10:35:13.8400000' AS DateTime2), 1)
INSERT [dbo].[Entidades] ([ENT_EntidadId], [ENT_Codigo], [ENT_Prefijo], [ENT_Nombre], [ENT_RazonSocial], [ENT_NombreComercial], [ENT_EMP_Director], [ENT_EMP_Coordinador], [ENT_EMP_JefeUnidadAF], [ENT_Domicilio], [ENT_Colonia], [ENT_CP], [ENT_PAI_PaisId], [ENT_AplicaSedes], [ENT_EST_EstadoId], [ENT_Ciudad], [ENT_EntidadDependienteId], [ENT_USU_CreadoPorId], [ENT_FechaCreacion], [ENT_USU_ModificadoPorId], [ENT_FechaModificacion], [ENT_Activo]) VALUES (7, N'LICAFU', N'LCFUG', N'LIBRERÍA CARLOS FUENTES ', N'LIBRERÍA CARLOS FUENTES ', N'LIBRERÍA CARLOS FUENTES ', NULL, NULL, NULL, N'Av. Periférico Norte No 1695-PB', N'', N'', 1, 0, 14, N'Zapopan', NULL, 1, CAST(N'2021-12-09T10:35:13.8400000' AS DateTime2), 1, CAST(N'2021-12-09T10:35:13.8400000' AS DateTime2), 1)
INSERT [dbo].[Entidades] ([ENT_EntidadId], [ENT_Codigo], [ENT_Prefijo], [ENT_Nombre], [ENT_RazonSocial], [ENT_NombreComercial], [ENT_EMP_Director], [ENT_EMP_Coordinador], [ENT_EMP_JefeUnidadAF], [ENT_Domicilio], [ENT_Colonia], [ENT_CP], [ENT_PAI_PaisId], [ENT_AplicaSedes], [ENT_EST_EstadoId], [ENT_Ciudad], [ENT_EntidadDependienteId], [ENT_USU_CreadoPorId], [ENT_FechaCreacion], [ENT_USU_ModificadoPorId], [ENT_FechaModificacion], [ENT_Activo]) VALUES (8, N'OPR', N'OPRUG', N'OPERADORA DE PREVENCIÓN DE RIESGOS', N'OPERADORA DE PREVENCIÓN DE RIESGOS', N'OPERADORA DE PREVENCIÓN DE RIESGOS', NULL, NULL, NULL, N'Ignacio Jacobo No. 27', N'', N'', 1, 0, 14, N'Zapopan', NULL, 1, CAST(N'2021-12-09T10:35:13.8400000' AS DateTime2), 1, CAST(N'2021-12-09T10:35:13.8400000' AS DateTime2), 1)
INSERT [dbo].[Entidades] ([ENT_EntidadId], [ENT_Codigo], [ENT_Prefijo], [ENT_Nombre], [ENT_RazonSocial], [ENT_NombreComercial], [ENT_EMP_Director], [ENT_EMP_Coordinador], [ENT_EMP_JefeUnidadAF], [ENT_Domicilio], [ENT_Colonia], [ENT_CP], [ENT_PAI_PaisId], [ENT_AplicaSedes], [ENT_EST_EstadoId], [ENT_Ciudad], [ENT_EntidadDependienteId], [ENT_USU_CreadoPorId], [ENT_FechaCreacion], [ENT_USU_ModificadoPorId], [ENT_FechaModificacion], [ENT_Activo]) VALUES (9, N'OSI', N'OSIUG', N'OPERADORA DE SERVICIOS INTEGRALES', N'OPERADORA DE SERVICIOS INTEGRALES', N'OPERADORA DE SERVICIOS INTEGRALES', NULL, NULL, NULL, N'Ing. Hugo Vázquez Reyes Número 39 interior N°35 y 36 ', N'', N'', 1, 0, 14, N'Zapopan', NULL, 1, CAST(N'2021-12-09T10:35:13.8433333' AS DateTime2), 1, CAST(N'2021-12-09T10:35:13.8433333' AS DateTime2), 1)
INSERT [dbo].[Entidades] ([ENT_EntidadId], [ENT_Codigo], [ENT_Prefijo], [ENT_Nombre], [ENT_RazonSocial], [ENT_NombreComercial], [ENT_EMP_Director], [ENT_EMP_Coordinador], [ENT_EMP_JefeUnidadAF], [ENT_Domicilio], [ENT_Colonia], [ENT_CP], [ENT_PAI_PaisId], [ENT_AplicaSedes], [ENT_EST_EstadoId], [ENT_Ciudad], [ENT_EntidadDependienteId], [ENT_USU_CreadoPorId], [ENT_FechaCreacion], [ENT_USU_ModificadoPorId], [ENT_FechaModificacion], [ENT_Activo]) VALUES (10, N'PIXVS', N'PIXVS', N'DESARROLLADORA DE SOFTWARE EMPRESARIAL Y DE NEGOCIOS', N'DESARROLLADORA DE SOFTWARE EMPRESARIAL Y DE NEGOCIOS', N'PIXVS', 793, NULL, NULL, N'Hugo Vázquez Reyes número 20', N'', N'', 1, 0, 14, N'Zapopan', NULL, 1, CAST(N'2021-12-09T10:35:13.8433333' AS DateTime2), 2706, CAST(N'2021-12-10T10:39:34.3530000' AS DateTime2), 1)
INSERT [dbo].[Entidades] ([ENT_EntidadId], [ENT_Codigo], [ENT_Prefijo], [ENT_Nombre], [ENT_RazonSocial], [ENT_NombreComercial], [ENT_EMP_Director], [ENT_EMP_Coordinador], [ENT_EMP_JefeUnidadAF], [ENT_Domicilio], [ENT_Colonia], [ENT_CP], [ENT_PAI_PaisId], [ENT_AplicaSedes], [ENT_EST_EstadoId], [ENT_Ciudad], [ENT_EntidadDependienteId], [ENT_USU_CreadoPorId], [ENT_FechaCreacion], [ENT_USU_ModificadoPorId], [ENT_FechaModificacion], [ENT_Activo]) VALUES (11, N'TELMEX', N'AUTUG', N'OPERADORA AUDITORIO METROPOLITANO', N'OPERADORA AUDITORIO METROPOLITANO', N'AUDITORIO TELMEX', NULL, NULL, NULL, N'Obreros de Cananea número 747', N'', N'', 1, 0, 14, N'Zapopan', NULL, 1, CAST(N'2021-12-09T10:35:13.8466667' AS DateTime2), 1, CAST(N'2021-12-09T10:35:13.8466667' AS DateTime2), 1)
INSERT [dbo].[Entidades] ([ENT_EntidadId], [ENT_Codigo], [ENT_Prefijo], [ENT_Nombre], [ENT_RazonSocial], [ENT_NombreComercial], [ENT_EMP_Director], [ENT_EMP_Coordinador], [ENT_EMP_JefeUnidadAF], [ENT_Domicilio], [ENT_Colonia], [ENT_CP], [ENT_PAI_PaisId], [ENT_AplicaSedes], [ENT_EST_EstadoId], [ENT_Ciudad], [ENT_EntidadDependienteId], [ENT_USU_CreadoPorId], [ENT_FechaCreacion], [ENT_USU_ModificadoPorId], [ENT_FechaModificacion], [ENT_Activo]) VALUES (12, N'UNITERRA', N'INUUG', N'INMOBILIARIA UNIVERSITARIA', N'INMOBILIARIA UNIVERSITARIA', N'INMOBILIARIA UNIVERSITARIA', NULL, NULL, NULL, N'Calle Día #2669', N'', N'', 1, 0, 14, N'Zapopan', NULL, 1, CAST(N'2021-12-09T10:35:13.8500000' AS DateTime2), 1, CAST(N'2021-12-09T10:35:13.8500000' AS DateTime2), 1)
INSERT [dbo].[Entidades] ([ENT_EntidadId], [ENT_Codigo], [ENT_Prefijo], [ENT_Nombre], [ENT_RazonSocial], [ENT_NombreComercial], [ENT_EMP_Director], [ENT_EMP_Coordinador], [ENT_EMP_JefeUnidadAF], [ENT_Domicilio], [ENT_Colonia], [ENT_CP], [ENT_PAI_PaisId], [ENT_AplicaSedes], [ENT_EST_EstadoId], [ENT_Ciudad], [ENT_EntidadDependienteId], [ENT_USU_CreadoPorId], [ENT_FechaCreacion], [ENT_USU_ModificadoPorId], [ENT_FechaModificacion], [ENT_Activo]) VALUES (13, N'HCDCB', N'HCDCB', N'HOTELES Y CLUB DEPORTIVO UNIVERSIDAD DE GUADALAJARA-CLUB DEPORTIVO', N'HOTELES Y CLUB DEPORTIVO UNIVERSIDAD DE GUADALAJARA-CLUB DEPORTIVO', N'CLUB DEPORTIVO', NULL, NULL, NULL, N'Kilómetro 24 de la carretera libre a Nogales 7 kilómetros adentro (Av. Universidad de Guadalajara s/n) partiendo del kilómetro 24 de la carretera libre a Nogales', N'', N'', 1, 0, 14, N'Zapopan', NULL, 1, CAST(N'2021-12-09T10:35:13.8500000' AS DateTime2), 1, CAST(N'2021-12-09T10:35:13.8500000' AS DateTime2), 1)
INSERT [dbo].[Entidades] ([ENT_EntidadId], [ENT_Codigo], [ENT_Prefijo], [ENT_Nombre], [ENT_RazonSocial], [ENT_NombreComercial], [ENT_EMP_Director], [ENT_EMP_Coordinador], [ENT_EMP_JefeUnidadAF], [ENT_Domicilio], [ENT_Colonia], [ENT_CP], [ENT_PAI_PaisId], [ENT_AplicaSedes], [ENT_EST_EstadoId], [ENT_Ciudad], [ENT_EntidadDependienteId], [ENT_USU_CreadoPorId], [ENT_FechaCreacion], [ENT_USU_ModificadoPorId], [ENT_FechaModificacion], [ENT_Activo]) VALUES (14, N'HCDVP', N'HCDVP', N'HOTELES Y CLUB DEPORTIVO UNIVERSIDAD DE GUADALAJARA-VILLA PRIMAVERA', N'HOTELES Y CLUB DEPORTIVO UNIVERSIDAD DE GUADALAJARA-VILLA PRIMAVERA', N'HOTEL VILLA PRIMAVERA', NULL, NULL, NULL, N'Kilómetro 24 de la carretera libre a Nogales 7 kilómetros adentro (Av. Universidad de Guadalajara s/n) partiendo del kilómetro 24 de la carretera libre a Nogales', N'', N'', 1, 0, 14, N'Zapopan', NULL, 1, CAST(N'2021-12-09T10:35:13.8533333' AS DateTime2), 1, CAST(N'2021-12-09T10:35:13.8533333' AS DateTime2), 1)
INSERT [dbo].[Entidades] ([ENT_EntidadId], [ENT_Codigo], [ENT_Prefijo], [ENT_Nombre], [ENT_RazonSocial], [ENT_NombreComercial], [ENT_EMP_Director], [ENT_EMP_Coordinador], [ENT_EMP_JefeUnidadAF], [ENT_Domicilio], [ENT_Colonia], [ENT_CP], [ENT_PAI_PaisId], [ENT_AplicaSedes], [ENT_EST_EstadoId], [ENT_Ciudad], [ENT_EntidadDependienteId], [ENT_USU_CreadoPorId], [ENT_FechaCreacion], [ENT_USU_ModificadoPorId], [ENT_FechaModificacion], [ENT_Activo]) VALUES (15, N'HCDVM', N'HCDVM', N'HOTELES Y CLUB DEPORTIVO UNIVERSIDAD DE GUADALAJARA-VILLA MONTECARLO', N'HOTELES Y CLUB DEPORTIVO UNIVERSIDAD DE GUADALAJARA-VILLA MONTECARLO', N'HOTEL VILLA MONTECARLO', NULL, NULL, NULL, N'Calle Hidalgo número 296', N'', N'', 1, 0, 14, N'Zapopan', NULL, 1, CAST(N'2021-12-09T10:35:13.8533333' AS DateTime2), 1, CAST(N'2021-12-09T10:35:13.8533333' AS DateTime2), 1)
SET IDENTITY_INSERT [dbo].[Entidades] OFF
ALTER TABLE [dbo].[Entidades]  WITH CHECK ADD  CONSTRAINT [FK_Entidades_Empleados] FOREIGN KEY([ENT_EMP_Director])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO
ALTER TABLE [dbo].[Entidades] CHECK CONSTRAINT [FK_Entidades_Empleados]
GO
ALTER TABLE [dbo].[Entidades]  WITH CHECK ADD  CONSTRAINT [FK_Entidades_Empleados1] FOREIGN KEY([ENT_EMP_Coordinador])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO
ALTER TABLE [dbo].[Entidades] CHECK CONSTRAINT [FK_Entidades_Empleados1]
GO
ALTER TABLE [dbo].[Entidades]  WITH CHECK ADD  CONSTRAINT [FK_Entidades_Empleados2] FOREIGN KEY([ENT_EMP_JefeUnidadAF])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO
ALTER TABLE [dbo].[Entidades] CHECK CONSTRAINT [FK_Entidades_Empleados2]
GO
ALTER TABLE [dbo].[Entidades]  WITH CHECK ADD  CONSTRAINT [FK_Entidades_Estados] FOREIGN KEY([ENT_EST_EstadoId])
REFERENCES [dbo].[Estados] ([EST_EstadoId])
GO
ALTER TABLE [dbo].[Entidades] CHECK CONSTRAINT [FK_Entidades_Estados]
GO
ALTER TABLE [dbo].[Entidades]  WITH CHECK ADD  CONSTRAINT [FK_Entidades_Paises] FOREIGN KEY([ENT_PAI_PaisId])
REFERENCES [dbo].[Paises] ([PAI_PaisId])
GO
ALTER TABLE [dbo].[Entidades] CHECK CONSTRAINT [FK_Entidades_Paises]
GO
ALTER TABLE [dbo].[Entidades]  WITH CHECK ADD  CONSTRAINT [FK_Entidades_Usuarios] FOREIGN KEY([ENT_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[Entidades] CHECK CONSTRAINT [FK_Entidades_Usuarios]
GO
ALTER TABLE [dbo].[Entidades]  WITH CHECK ADD  CONSTRAINT [FK_Entidades_Usuarios1] FOREIGN KEY([ENT_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[Entidades] CHECK CONSTRAINT [FK_Entidades_Usuarios1]
GO
