/****** Object:  Table [dbo].[Paises]    Script Date: 16/07/2020 04:38:48 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Paises](
	[PAI_PaisId] [smallint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[PAI_Nombre] [nvarchar](100) NOT NULL,
	[PAI_Clave] [nvarchar](5) NULL,
	[PAI_Nacionalidad] [nvarchar](100) NULL,
 CONSTRAINT [PK_Paises] PRIMARY KEY CLUSTERED 
(
	[PAI_PaisId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


/****** Object:  Table [dbo].[Estados]    Script Date: 16/07/2020 04:38:48 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Estados](
	[EST_EstadoId] [int] IDENTITY(1,1) NOT NULL,
	[EST_PAI_PaisId] [smallint] NOT NULL,
	[EST_Nombre] [nvarchar](150) NOT NULL,
	[EST_ClaveEntidad] [smallint] NULL,
 CONSTRAINT [PK_Estados] PRIMARY KEY CLUSTERED 
(
	[EST_EstadoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Estados]  WITH CHECK ADD  CONSTRAINT [FK_EST_PAI_PaisId] FOREIGN KEY([EST_PAI_PaisId])
REFERENCES [dbo].[Paises] (PAI_PaisId)
GO

ALTER TABLE [dbo].[Estados] CHECK CONSTRAINT [FK_EST_PAI_PaisId]
GO



SET IDENTITY_INSERT [dbo].[Paises] ON 
GO
INSERT [dbo].[Paises] ([PAI_PaisId], [PAI_Nombre], [PAI_Clave], [PAI_Nacionalidad]) VALUES (1, N'México', N'MEX', N'Mexicano')
GO
INSERT [dbo].[Paises] ([PAI_PaisId], [PAI_Nombre], [PAI_Clave], [PAI_Nacionalidad]) VALUES (2, N'Estados Unidos', N'EUA', N'Estadounidense')
GO
SET IDENTITY_INSERT [dbo].[Paises] OFF
GO


SET IDENTITY_INSERT [dbo].[Estados] ON 
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (1, 1, N'Aguascalientes', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (2, 1, N'Baja California', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (3, 1, N'Baja California Sur', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (4, 1, N'Campeche', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (5, 1, N'Chiapas', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (6, 1, N'Chihuahua', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (7, 1, N'Coahuila', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (8, 1, N'Colima', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (9, 1, N'Ciudad de México', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (10, 1, N'Durango', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (11, 1, N'Guanajuato', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (12, 1, N'Guerrero', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (13, 1, N'Hidalgo', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (14, 1, N'Jalisco', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (15, 1, N'Estado de México', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (16, 1, N'Michoacán', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (17, 1, N'Morelos', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (18, 1, N'Nayarit', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (19, 1, N'Nuevo León', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (20, 1, N'Oaxaca', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (21, 1, N'Puebla', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (22, 1, N'Querétaro', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (23, 1, N'Quintana Roo', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (24, 1, N'San Luis Potosí', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (25, 1, N'Sinaloa', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (26, 1, N'Sonora', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (27, 1, N'Tabasco', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (28, 1, N'Tamaulipas', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (29, 1, N'Tlaxcala', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (30, 1, N'Veracruz', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (31, 1, N'Yucatán', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (32, 1, N'Zacatecas', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (33, 2, N'Alabama', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (34, 2, N'Alaska', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (35, 2, N'Arizona', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (36, 2, N'Arkansas', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (37, 2, N'California', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (38, 2, N'Carolina del Norte', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (39, 2, N'Carolina del Sur', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (40, 2, N'Colorado', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (41, 2, N'Connecticut', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (42, 2, N'Dakota del Norte', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (43, 2, N'Dakota del Sur', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (44, 2, N'Delaware', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (45, 2, N'Florida', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (46, 2, N'Georgia', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (47, 2, N'Hawái', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (48, 2, N'Idaho', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (49, 2, N'Illinois', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (50, 2, N'Indiana', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (51, 2, N'Iowa', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (52, 2, N'Kansas', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (53, 2, N'Kentucky', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (54, 2, N'Luisiana', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (55, 2, N'Maine', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (56, 2, N'Maryland', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (57, 2, N'Massachusetts', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (58, 2, N'Míchigan', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (59, 2, N'Minnesota', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (60, 2, N'Misisipi', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (61, 2, N'Misuri', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (62, 2, N'Montana', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (63, 2, N'Nebraska', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (64, 2, N'Nevada', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (65, 2, N'Nueva Jersey', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (66, 2, N'Nueva York', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (67, 2, N'Nuevo Hampshire', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (68, 2, N'Nuevo México', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (69, 2, N'Ohio', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (70, 2, N'Oklahoma', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (71, 2, N'Oregón', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (72, 2, N'Pensilvania', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (73, 2, N'Rhode Island', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (74, 2, N'Tennessee', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (75, 2, N'Texas', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (76, 2, N'Utah', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (77, 2, N'Vermont', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (78, 2, N'Virginia', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (79, 2, N'Virginia Occidental', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (80, 2, N'Washington', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (81, 2, N'Wisconsin', 1)
GO
INSERT [dbo].[Estados] ([EST_EstadoId], [EST_PAI_PaisId], [EST_Nombre], [EST_ClaveEntidad]) VALUES (82, 2, N'Wyoming', 1)
GO
SET IDENTITY_INSERT [dbo].[Estados] OFF
GO

