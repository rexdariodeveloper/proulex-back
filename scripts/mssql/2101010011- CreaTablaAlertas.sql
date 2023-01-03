/****** Object:  Table [dbo].[Alertas]    Script Date: 05/01/2021 07:48:48 a. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Alertas](
	[ALE_AlertaId] [int] IDENTITY(1,1) NOT NULL,
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
	[StartTime] [datetime2](7) GENERATED ALWAYS AS ROW START NOT NULL,
	[EndTime] [datetime2](7) GENERATED ALWAYS AS ROW END NOT NULL,
	[ALE_ACE_AlertaConfiguracionEtapaId] [int] NOT NULL,
 CONSTRAINT [PK_Alertas] PRIMARY KEY NONCLUSTERED 
(
	[ALE_AlertaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
	PERIOD FOR SYSTEM_TIME ([StartTime], [EndTime])
) ON [PRIMARY]
WITH
(
SYSTEM_VERSIONING = ON ( HISTORY_TABLE = [dbo].[Alertas_History] )
)
GO

ALTER TABLE [dbo].[Alertas] ADD  CONSTRAINT [DF_Alertas_ALE_FechaInicio]  DEFAULT (getdate()) FOR [ALE_FechaInicio]
GO

ALTER TABLE [dbo].[Alertas] ADD  DEFAULT (getutcdate()) FOR [StartTime]
GO

ALTER TABLE [dbo].[Alertas] ADD  DEFAULT (CONVERT([datetime2],'9999-12-31 23:59:59.9999999')) FOR [EndTime]
GO