/**
 * Created by Angel Daniel Hernández Silva on 13/09/2021.
 * Object: Table [dbo].[ListadosPreciosDetallesCursos]
 */

/*****************************************/
/***** ListadosPreciosDetallesCursos *****/
/*****************************************/

CREATE TABLE [dbo].[ListadosPreciosDetallesCursos](
	[LIPREDC_ListadoPrecioDetalleCursoId] [int] IDENTITY(1,1) NOT NULL,
    [LIPREDC_LIPRED_ListadoPrecioDetalleId] [int] NOT NULL,
    [LIPREDC_Precio] [decimal](28,6) NOT NULL,
    [LIPREDC_ART_ArticuloId] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[LIPREDC_ListadoPrecioDetalleCursoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

-- Constraints FK

ALTER TABLE [dbo].[ListadosPreciosDetallesCursos]  WITH CHECK ADD  CONSTRAINT [FK_LIPREDC_LIPRED_ListadoPrecioDetalleId] FOREIGN KEY([LIPREDC_LIPRED_ListadoPrecioDetalleId])
REFERENCES [dbo].[ListadosPreciosDetalles] ([LIPRED_ListadoPrecioDetalleId])
GO

ALTER TABLE [dbo].[ListadosPreciosDetallesCursos] CHECK CONSTRAINT [FK_LIPREDC_LIPRED_ListadoPrecioDetalleId]
GO

ALTER TABLE [dbo].[ListadosPreciosDetallesCursos]  WITH CHECK ADD  CONSTRAINT [FK_LIPREDC_ART_ArticuloId] FOREIGN KEY([LIPREDC_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[ListadosPreciosDetallesCursos] CHECK CONSTRAINT [FK_LIPREDC_ART_ArticuloId]
GO

/*********************************/
/***** UPDATE Precios Cursos *****/
/*********************************/

UPDATE ListadosPreciosDetalles SET LIPRED_Precio = precio
FROM (
	SELECT
		COALESCE(LIPRED_ListadoPrecioPadreId,LIPRED_ListadoPrecioDetalleId) AS id,
		SUM(LIPRED_Precio) AS precio
	FROM ListadosPreciosDetalles
	GROUP BY COALESCE(LIPRED_ListadoPrecioPadreId,LIPRED_ListadoPrecioDetalleId)
) AcumuladoPrecio
WHERE LIPRED_ListadoPrecioDetalleId = id
GO

/************************************************/
/***** INSERT ListadosPreciosDetallesCursos *****/
/************************************************/

INSERT INTO [dbo].[ListadosPreciosDetallesCursos](
    [LIPREDC_LIPRED_ListadoPrecioDetalleId],
    [LIPREDC_Precio],
    [LIPREDC_ART_ArticuloId]
)
SELECT
	LIPRED_ListadoPrecioPadreId AS LIPREDC_LIPRED_ListadoPrecioDetalleId,
	LIPRED_Precio AS LIPREDC_Precio,
	LIPRED_ART_ArticuloId AS LIPREDC_ART_ArticuloId
FROM ListadosPreciosDetalles
WHERE LIPRED_ListadoPrecioPadreId IS NOT NULL
GO

DELETE FROM ListadosPreciosDetalles
WHERE LIPRED_ListadoPrecioPadreId IS NOT NULL
GO