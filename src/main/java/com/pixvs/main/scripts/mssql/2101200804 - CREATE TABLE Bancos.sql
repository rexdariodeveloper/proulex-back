SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Bancos](
	[BAN_BancoId] [int] IDENTITY(1,1) NOT NULL,
	[BAN_Nombre] [nvarchar](150) NOT NULL,
	[BAN_Activo] [bit] NOT NULL,
	[BAN_USU_CreadoPorId] [int] NOT NULL,
	[BAN_USU_ModificadoPorId] [int] NULL,
	[BAN_FechaCreacion] [datetime2](7) NOT NULL,
	[BAN_FechaModificacion] [datetime2](7) NULL,
 CONSTRAINT [PK_Bancos] PRIMARY KEY CLUSTERED 
(
	[BAN_BancoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

select * from Bancos