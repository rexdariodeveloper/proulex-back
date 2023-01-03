-- Agregamos los nuevos campos
ALTER TABLE [dbo].[Departamentos] ADD DEP_NumeroVacantes INTEGER DEFAULT NULL;
ALTER TABLE [dbo].[Departamentos] ADD DEP_PropositoPuesto VARCHAR(250) DEFAULT NULL;
ALTER TABLE [dbo].[Departamentos] ADD DEP_Observaciones VARCHAR(1000) DEFAULT NULL;


SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[DepartamentoResposabilidadHabilidad](
	[DEPREHA_DepartamentoResposabilidadHabilidadId] [int] IDENTITY(1,1) NOT NULL,
	[DEPREHA_DEP_DepartamentoId] [int] NOT NULL,
	[DEPREHA_Descripcion] [varchar](100) NOT NULL,
	[DEPREHA_EsResponsabilidad] [bit] NOT NULL
) ON [PRIMARY]
GO