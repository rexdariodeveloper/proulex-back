/**
 * Created by Angel Daniel Hernández Silva on 02/07/2020.
 * Object:  Table [dbo].[ArticulosTipos]
 */


CREATE TABLE [dbo].[ArticulosTipos](
	[ARTT_ArticuloTipoId] [int] IDENTITY(1,1) NOT NULL ,
	[ARTT_CMM_TipoId] [int]  NOT NULL ,
	[ARTT_Descripcion] [varchar]  (1000) NOT NULL ,
	[ARTT_Activo] [bit]  NOT NULL 
PRIMARY KEY CLUSTERED 
(
	[ARTT_ArticuloTipoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ArticulosTipos]  WITH CHECK ADD  CONSTRAINT [FK_ARTT_CMM_TipoId] FOREIGN KEY([ARTT_CMM_TipoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ArticulosTipos] CHECK CONSTRAINT [FK_ARTT_CMM_TipoId]
GO

ALTER TABLE [dbo].[ArticulosTipos] WITH CHECK ADD CONSTRAINT [DF_ArticulosTipos_ARTT_Activo]  DEFAULT (1) FOR [ARTT_Activo]
GO


CREATE   VIEW [dbo].[VW_LISTADO_ARTICULOS_TIPOS] AS

SELECT
	ARTT_Descripcion AS "Descripción" 
FROM ArticulosTipos 

GO