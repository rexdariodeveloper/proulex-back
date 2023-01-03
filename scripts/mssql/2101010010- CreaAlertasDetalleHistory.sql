/****** Object:  Table [dbo].[AlertasDetalles_History]    Script Date: 05/01/2021 07:54:32 a. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[AlertasDetalles_History](
	[ALD_AlertaDetalleId] [int] NOT NULL,
	[ALD_ALE_AlertaId] [int] NOT NULL,
	[ALD_ACE_AlertaConfiguracionEtapaId] [int] NOT NULL,
	[ALD_CMM_EstatusId] [int] NULL,
	[ALD_USU_UsuarioId] [int] NOT NULL,
	[ALD_FechaAtendido] [datetime] NULL,
	[ALD_Archivado] [bit] NOT NULL,
	[ALD_Mostrar] [bit] NOT NULL,
	[ALD_Comentario] [nvarchar](2000) NULL,
	[ALD_FechaCreacion] [datetime] NOT NULL,
	[ALD_USU_CreadoPorId] [int] NOT NULL,
	[ALD_FechaUltimaModificacion] [datetime] NULL,
	[ALD_USU_ModificadoPorId] [int] NULL,
	[ALD_Timestamp] [timestamp] NOT NULL,
	[ALD_Visto] [bit] NOT NULL,
	[StartTime] [datetime2](7) NOT NULL,
	[EndTime] [datetime2](7) NOT NULL,
	[ALD_CMM_TipoAlertaId] [int] NOT NULL
) ON [PRIMARY]
GO

