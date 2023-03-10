/****** Object:  Table [dbo].[EntidadesContratos]    Script Date: 21/02/2022 16:18:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[EntidadesContratos](
	[ENTC_EntidadContratoId] [int] IDENTITY(1,1) NOT NULL,
	[ENTC_ENT_EntidadId] [int] NOT NULL,
	[ENTC_SUC_SucursalId] [int] NULL,
	[ENTC_CMM_TipoContratoId] [int] NOT NULL,
	[ENTC_ARC_DocumentoContratoId] [int] NULL,
	[ENTC_ARC_AdendumContratoId] [int] NULL,
	[ENTC_Activo] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ENTC_EntidadContratoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[EntidadesContratos] ON 

INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (1, 1, NULL, 2000660, 11855, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (2, 2, NULL, 2000660, 11856, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (3, 3, NULL, 2000660, 11857, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (4, 4, NULL, 2000660, 11858, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (5, 5, NULL, 2000660, 11859, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (6, 6, NULL, 2000660, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (7, 7, NULL, 2000660, 11861, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (8, 8, NULL, 2000660, 11862, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (9, 9, NULL, 2000660, 11863, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (10, 10, NULL, 2000660, 11864, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (11, 11, NULL, 2000660, 11865, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (12, 12, NULL, 2000660, 11866, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (13, 13, NULL, 2000660, 11860, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (14, 14, NULL, 2000660, 11868, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (15, 15, NULL, 2000660, 11869, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (16, 1, NULL, 2000661, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (17, 2, NULL, 2000661, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (18, 3, NULL, 2000661, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (19, 4, NULL, 2000661, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (20, 5, NULL, 2000661, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (21, 6, NULL, 2000661, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (22, 7, NULL, 2000661, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (23, 8, NULL, 2000661, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (24, 9, NULL, 2000661, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (25, 10, NULL, 2000661, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (26, 11, NULL, 2000661, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (27, 12, NULL, 2000661, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (28, 13, NULL, 2000661, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (29, 14, NULL, 2000661, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (30, 15, NULL, 2000661, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (31, 1, NULL, 2000662, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (32, 2, NULL, 2000662, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (33, 3, NULL, 2000662, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (34, 4, NULL, 2000662, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (35, 5, NULL, 2000662, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (36, 6, NULL, 2000662, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (37, 7, NULL, 2000662, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (38, 8, NULL, 2000662, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (39, 9, NULL, 2000662, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (40, 10, NULL, 2000662, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (41, 11, NULL, 2000662, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (42, 12, NULL, 2000662, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (43, 13, NULL, 2000662, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (44, 14, NULL, 2000662, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (45, 15, NULL, 2000662, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (46, 1, NULL, 2000663, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (47, 2, NULL, 2000663, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (48, 3, NULL, 2000663, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (49, 4, NULL, 2000663, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (50, 5, NULL, 2000663, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (51, 6, NULL, 2000663, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (52, 7, NULL, 2000663, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (53, 8, NULL, 2000663, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (54, 9, NULL, 2000663, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (55, 10, NULL, 2000663, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (56, 11, NULL, 2000663, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (57, 12, NULL, 2000663, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (58, 13, NULL, 2000663, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (59, 14, NULL, 2000663, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (60, 15, NULL, 2000663, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (61, 1, NULL, 2000664, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (62, 2, NULL, 2000664, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (63, 3, NULL, 2000664, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (64, 4, NULL, 2000664, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (65, 5, NULL, 2000664, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (66, 6, NULL, 2000664, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (67, 7, NULL, 2000664, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (68, 8, NULL, 2000664, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (69, 9, NULL, 2000664, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (70, 10, NULL, 2000664, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (71, 11, NULL, 2000664, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (72, 12, NULL, 2000664, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (73, 13, NULL, 2000664, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (74, 14, NULL, 2000664, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (75, 15, NULL, 2000664, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (76, 1, NULL, 2000665, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (77, 2, NULL, 2000665, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (78, 3, NULL, 2000665, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (79, 4, NULL, 2000665, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (80, 5, NULL, 2000665, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (81, 6, NULL, 2000665, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (82, 7, NULL, 2000665, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (83, 8, NULL, 2000665, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (84, 9, NULL, 2000665, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (85, 10, NULL, 2000665, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (86, 11, NULL, 2000665, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (87, 12, NULL, 2000665, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (88, 13, NULL, 2000665, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (89, 14, NULL, 2000665, NULL, NULL, 1)
INSERT [dbo].[EntidadesContratos] ([ENTC_EntidadContratoId], [ENTC_ENT_EntidadId], [ENTC_SUC_SucursalId], [ENTC_CMM_TipoContratoId], [ENTC_ARC_DocumentoContratoId], [ENTC_ARC_AdendumContratoId], [ENTC_Activo]) VALUES (90, 15, NULL, 2000665, NULL, NULL, 1)
SET IDENTITY_INSERT [dbo].[EntidadesContratos] OFF
ALTER TABLE [dbo].[EntidadesContratos]  WITH CHECK ADD  CONSTRAINT [FK_EntidadesContratos_Archivos] FOREIGN KEY([ENTC_ARC_DocumentoContratoId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO
ALTER TABLE [dbo].[EntidadesContratos] CHECK CONSTRAINT [FK_EntidadesContratos_Archivos]
GO
ALTER TABLE [dbo].[EntidadesContratos]  WITH CHECK ADD  CONSTRAINT [FK_EntidadesContratos_Archivos1] FOREIGN KEY([ENTC_ARC_AdendumContratoId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO
ALTER TABLE [dbo].[EntidadesContratos] CHECK CONSTRAINT [FK_EntidadesContratos_Archivos1]
GO
ALTER TABLE [dbo].[EntidadesContratos]  WITH CHECK ADD  CONSTRAINT [FK_EntidadesContratos_ControlesMaestrosMultiples] FOREIGN KEY([ENTC_CMM_TipoContratoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[EntidadesContratos] CHECK CONSTRAINT [FK_EntidadesContratos_ControlesMaestrosMultiples]
GO
ALTER TABLE [dbo].[EntidadesContratos]  WITH CHECK ADD  CONSTRAINT [FK_EntidadesContratos_Entidades] FOREIGN KEY([ENTC_ENT_EntidadId])
REFERENCES [dbo].[Entidades] ([ENT_EntidadId])
GO
ALTER TABLE [dbo].[EntidadesContratos] CHECK CONSTRAINT [FK_EntidadesContratos_Entidades]
GO
ALTER TABLE [dbo].[EntidadesContratos]  WITH CHECK ADD  CONSTRAINT [FK_EntidadesContratos_Sucursal] FOREIGN KEY([ENTC_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO
ALTER TABLE [dbo].[EntidadesContratos] CHECK CONSTRAINT [FK_EntidadesContratos_Sucursal]
GO
