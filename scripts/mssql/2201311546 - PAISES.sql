SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Paises')
BEGIN
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
END
GO

SET IDENTITY_INSERT [dbo].[Paises] ON
GO
IF((SELECT COUNT(*) FROM Paises) = 0 )
BEGIN
INSERT [dbo].[Paises] ([PAI_PaisId], [PAI_Nombre], [PAI_Clave], [PAI_Nacionalidad]) VALUES
(1, N'México', N'MEX', N'Mexicano'),
(2, N'Estados Unidos', N'EUA', N'Estadounidense'),
(3, N'Alemania', N'DEU', N'Aleman'),
(4, N'Francia', N'FRA', N'Frances'),
(255, N'Otro', N'XX', N'-')
END
SET IDENTITY_INSERT [dbo].[Paises] OFF
GO