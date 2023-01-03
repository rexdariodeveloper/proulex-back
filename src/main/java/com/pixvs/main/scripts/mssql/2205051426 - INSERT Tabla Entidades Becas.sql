SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[EntidadesBecas](
	[ENBE_EntidadBecaId] [int] IDENTITY(1,1) NOT NULL,
	[ENBE_Codigo] [varchar](10) NOT NULL,
	[ENBE_Nombre] [varchar](50) NOT NULL,
	[ENBE_PrecioAnual] [decimal](10,2) NOT NULL,
	[ENBE_Activo] [bit] NOT NULL,
	[ENBE_FechaCreacion] [datetime2](7) NOT NULL,
	[ENBE_USU_CreadoPorId] [int] NOT NULL,
	[ENBE_FechaUltimaModificacion] [datetime2](7) NULL,
	[ENBE_USU_ModificadoPorId] [int] NULL,
 CONSTRAINT [PK_EntidadesBecas] PRIMARY KEY CLUSTERED
(
	[ENBE_EntidadBecaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[EntidadesBecas]  WITH CHECK ADD  CONSTRAINT [FK_ENBE_USU_CreadoPorId] FOREIGN KEY([ENBE_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[EntidadesBecas] CHECK CONSTRAINT [FK_ENBE_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[EntidadesBecas]  WITH CHECK ADD  CONSTRAINT [FK_ENBE_USU_ModificadoPorId] FOREIGN KEY([ENBE_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[EntidadesBecas] CHECK CONSTRAINT [FK_ENBE_USU_ModificadoPorId]
GO

INSERT INTO [dbo].[EntidadesBecas]
           ([ENBE_Codigo]
           ,[ENBE_Nombre]
           ,[ENBE_PrecioAnual]
           ,[ENBE_Activo]
           ,[ENBE_FechaCreacion]
           ,[ENBE_USU_CreadoPorId])
     VALUES
           ('SUTUDEG'
           ,'SUTUDEG'
           ,0
           ,1
           ,GETDATE()
           ,1),
		   ('STAUDEG'
           ,'STAUDEG'
           ,0
           ,1
           ,GETDATE()
           ,1)
GO

INSERT INTO [dbo].[EntidadesBecas]
           ([ENBE_Codigo]
           ,[ENBE_Nombre]
           ,[ENBE_PrecioAnual]
           ,[ENBE_Activo]
           ,[ENBE_FechaCreacion]
           ,[ENBE_USU_CreadoPorId])
Select CMM_Referencia,CMM_Valor,0,1,GETDATE(),1 
from ControlesMaestrosMultiples where CMM_Control='CMM_BEC_EntidadBeca'
GO