/**
* Created by Angel Daniel Hernández Silva on 28/09/2021.
* Object: UPDATE ListadosPreciosDetallesCursos - Eliminar duplicados
*/

UPDATE ListadosPreciosDetallesCursos SET LIPREDC_Precio = -1
FROM(
	SELECT MIN(LIPREDC_ListadoPrecioDetalleCursoId) AS idOmitir, LIPREDC_LIPRED_ListadoPrecioDetalleId AS detalleId, LIPREDC_ART_ArticuloId AS articuloId
	FROM ListadosPreciosDetallesCursos
	GROUP BY LIPREDC_LIPRED_ListadoPrecioDetalleId, LIPREDC_ART_ArticuloId
	HAVING COUNT(*) > 1
) Q
WHERE
	LIPREDC_ListadoPrecioDetalleCursoId != idOmitir
	AND LIPREDC_LIPRED_ListadoPrecioDetalleId = detalleId
	AND LIPREDC_ART_ArticuloId = articuloId
GO

DELETE FROM ListadosPreciosDetallesCursos WHERE LIPREDC_Precio < 0
GO

ALTER TABLE [dbo].[ListadosPreciosDetallesCursos] WITH CHECK ADD CONSTRAINT [UNQ_LIPREDC_Duplicidad] UNIQUE ([LIPREDC_LIPRED_ListadoPrecioDetalleId],[LIPREDC_ART_ArticuloId])
GO