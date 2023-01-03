
/****** Object:  Table [dbo].[EstadisticasPaginas]    Script Date: 07/07/2020 10:31:17 a. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/****** Object:  Table [dbo].[EstadisticasPaginas]    Script Date: 08/07/2020 12:58:04 p. m. ******/
IF OBJECT_ID('dbo.EstadisticasPaginas', 'U') IS NOT NULL 
  DROP TABLE  [dbo].[EstadisticasPaginas]; 
GO

CREATE TABLE [dbo].[EstadisticasPaginas](
	[EP_EstadisticaPaginaId] [int] IDENTITY(1,1) NOT NULL,
	[EP_URL] [varchar](1000) NOT NULL,
	[EP_CMM_TipoId] [int] NOT NULL,
	[EP_IP] [varchar](100) NULL,
	[EP_Dispositivo] [varchar](100) NULL,
	[EP_OS] [varchar](100) NULL,
	[EP_OS_Version] [varchar](200) NULL,
	[EP_Browser] [varchar](200) NULL,
	[EP_FechaCreacion] [datetime2](7) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[EP_EstadisticaPaginaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


ALTER TABLE [dbo].[Archivos]  WITH CHECK ADD  CONSTRAINT [FK_EP_CMM_TipoId] FOREIGN KEY([ARC_CMM_TipoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Archivos] CHECK CONSTRAINT [FK_EP_CMM_TipoId]
GO




SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON 
GO


INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor]) 
VALUES (1000051, 1, N'CMM_EP_TipoEstadisticaPagina', NULL, CAST(N'2020-06-29T05:50:50.0633333' AS DateTime2), NULL, NULL, NULL, 1, N'Sección')
GO
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor]) 
VALUES (1000052, 1, N'CMM_EP_TipoEstadisticaPagina', NULL, CAST(N'2020-06-29T05:50:50.0633333' AS DateTime2), NULL, NULL, NULL, 1, N'Contenido')
GO
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor]) 
VALUES (1000053, 1, N'CMM_EP_TipoEstadisticaPagina', NULL, CAST(N'2020-06-29T05:50:50.0633333' AS DateTime2), NULL, NULL, NULL, 1, N'Enlace')
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO


SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE   VIEW [dbo].[VW_REPORTE_ESTADISTICAS_PAGINAS] AS

SELECT CMM_Valor AS "Tipo", SUBSTRING( EP_URL , CHARINDEX('/app/', EP_URL) + len('/app/')  , len(EP_URL)) AS "URL_SECCION", EP_URL AS "URL" 
	,EP_IP AS "IP", EP_Dispositivo AS "Dispositivo", EP_OS AS "OS", EP_OS_Version AS "OS Version", EP_Browser AS "Browser", EP_FechaCreacion AS "Fecha"
FROM EstadisticasPaginas
LEFT JOIN ControlesMaestrosMultiples ON EP_CMM_TipoId = CMM_ControlId

GO


