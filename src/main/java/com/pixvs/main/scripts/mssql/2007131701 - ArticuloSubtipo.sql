/**
 * Created by Angel Daniel Hernández Silva on 02/07/2020.
 * Object:  Table [dbo].[ArticulosSubtipos]
 */


CREATE TABLE [dbo].[ArticulosSubtipos](
	[ARTST_ArticuloSubtipoId] [int] IDENTITY(1,1) NOT NULL ,
	[ARTST_ARTT_ArticuloTipoId] [int]  NOT NULL ,
	[ARTST_Descripcion] [varchar]  (1000) NOT NULL ,
	[ARTST_Activo] [bit]  NOT NULL 
PRIMARY KEY CLUSTERED 
(
	[ARTST_ArticuloSubtipoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ArticulosSubtipos]  WITH CHECK ADD  CONSTRAINT [FK_ARTST_ARTT_ArticuloTipoId] FOREIGN KEY([ARTST_ARTT_ArticuloTipoId])
REFERENCES [dbo].[ArticulosTipos] ([ARTT_ArticuloTipoId])
GO

ALTER TABLE [dbo].[ArticulosSubtipos] CHECK CONSTRAINT [FK_ARTST_ARTT_ArticuloTipoId]
GO

ALTER TABLE [dbo].[ArticulosSubtipos] WITH CHECK ADD CONSTRAINT [DF_ArticulosSubtipos_ARTST_Activo]  DEFAULT (1) FOR [ARTST_Activo]
GO


CREATE   VIEW [dbo].[VW_LISTADO_ARTICULOS_SUBTIPOS] AS

SELECT
	ARTST_Descripcion AS "Descripción" 
FROM ArticulosSubtipos 

GO