CREATE OR ALTER VIEW dbo.VW_LISTADO_ARTICULOS_COMBO
AS
	SELECT 
		ART_ArticuloId id,
		ART_CodigoArticulo codigo,
		ART_NombreArticulo nombre,
		UM_Nombre unidad,
		COALESCE(LOCA_LOC_LocalidadId, LOCAA_LOC_LocalidadId) localidadId,
		LOCAA_Cantidad existencia,
		COALESCE(minimo,ART_MinimoAlmacen,0) minimo,
		COALESCE(maximo,ART_MaximoAlmacen,0) maximo,
		-- TO DO: Cambiar 1 = 1 por una condición de criterio
		CASE WHEN 1 = 1 THEN COALESCE(minimo,ART_MinimoAlmacen,0) ELSE COALESCE(maximo,ART_MaximoAlmacen,0) END criterio
	FROM 
		Articulos 
		INNER JOIN UnidadesMedidas on ART_UM_UnidadMedidaInventarioId = UM_UnidadMedidaId
		LEFT JOIN LocalidadesArticulos AS art_suc ON ART_ArticuloId = art_suc.LOCA_ART_ArticuloId
		LEFT JOIN LocalidadesArticulosAcumulados AS existencia ON ART_ArticuloId = existencia.LOCAA_ART_ArticuloId
		LEFT JOIN (
			SELECT 
				TEMD_ART_ArticuloId articuloId,
				TEMD_Minimo minimo,
				TEMD_Maximo maximo
			FROM 
				Temporadas 
				INNER JOIN TemporadasDetalles ON TEM_TemporadaId = TEMD_TEM_TemporadaId
			WHERE
				TEM_Activo = 1
				AND GETDATE() BETWEEN TEM_FechaInicio AND TEM_FechaFin
		) temporada ON ART_ArticuloId = temporada.articuloId AND ART_PlaneacionTemporadas = 1
	WHERE
		ART_Activo = 1
		AND LOCAA_LOC_LocalidadId = COALESCE(LOCA_LOC_LocalidadId,LOCAA_LOC_LocalidadId)
		AND COALESCE(LOCA_LOC_LocalidadId,LOCAA_LOC_LocalidadId) <> 0
GO