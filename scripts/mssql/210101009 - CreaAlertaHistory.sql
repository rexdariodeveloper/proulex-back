/****** Object:  Table [dbo].[Alertas_History]    Script Date: 05/01/2021 07:52:51 a. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Alertas_History](
	[ALE_AlertaId] [int] NOT NULL,
	[ALE_ALC_AlertaCId] [int] NOT NULL,
	[ALE_ReferenciaProcesoId] [int] NULL,
	[ALE_CodigoTramite] [nvarchar](20) NULL,
	[ALE_CMM_EstatusId] [int] NOT NULL,
	[ALE_TextoRepresentativo] [nvarchar](150) NULL,
	[ALE_FechaInicio] [datetime] NOT NULL,
	[ALE_USU_CreadoPorId] [int] NOT NULL,
	[ALE_FechaUltimaModificacion] [datetime] NULL,
	[ALE_USU_ModificadoPorId] [int] NULL,
	[ALE_Timestamp] [timestamp] NOT NULL,
	[ALE_FechaAutorizacion] [datetime] NULL,
	[StartTime] [datetime2](7) NOT NULL,
	[EndTime] [datetime2](7) NOT NULL,
	[ALE_ACE_AlertaConfiguracionEtapaId] [int] NOT NULL
) ON [PRIMARY]
GO