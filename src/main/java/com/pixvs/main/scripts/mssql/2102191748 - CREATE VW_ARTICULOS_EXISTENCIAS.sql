CREATE OR ALTER VIEW [dbo].[VW_ARTICULOS_EXISTENCIAS]
AS
	WITH A AS(
		SELECT
			ART_ArticuloId AS articuloId,
			ART_ArticuloId AS articuloRaizId,
			CAST(1 AS decimal(28,6)) AS cantidadComponente
		FROM Articulos
		LEFT JOIN ArticulosComponentes ON ARTC_ART_ComponenteId = ART_ArticuloId
		WHERE ART_ARTT_TipoArticuloId = 4 AND ARTC_ArticuloComponenteId IS NULL

		UNION ALL

		SELECT
			ARTC_ART_ComponenteId AS articuloId,
			articuloRaizId,
			CAST((ARTC_Cantidad * cantidadComponente) AS decimal(28,6)) AS cantidadComponente
		FROM ArticulosComponentes
		INNER JOIN A ON articuloId = ARTC_ART_ArticuloId
	)

	SELECT 
		ART_ArticuloId AS articuloId,
		LOC_LocalidadId AS localidadId,
		ALM_AlmacenId AS almacenId,
		SUC_SucursalId AS sucursalId,
		COALESCE(LOCAA_Cantidad,0) existenciaLocalidad,
		SUM(COALESCE(LOCAA_Cantidad,0)) OVER(PARTITION BY ART_ArticuloId,ALM_AlmacenId) existenciaAlmacen,
		SUM(COALESCE(LOCAA_Cantidad,0)) OVER(PARTITION BY ART_ArticuloId,SUC_SucursalId) existenciaSucursal
	FROM Articulos
	INNER JOIN LocalidadesArticulos ON LOCA_ART_ArticuloId = ART_ArticuloId
	INNER JOIN Localidades ON LOC_LocalidadId = LOCA_LOC_LocalidadId AND LOC_Activo = 1
	INNER JOIN Almacenes ON ALM_AlmacenId = LOC_ALM_AlmacenId AND ALM_Activo = 1
	INNER JOIN Sucursales ON SUC_SucursalId = ALM_SUC_SucursalId AND SUC_Activo = 1
	INNER JOIN LocalidadesArticulosAcumulados ON LOCAA_ART_ArticuloId = ART_ArticuloId AND LOCAA_LOC_LocalidadId = LOC_LocalidadId
	WHERE ART_ARTT_TipoArticuloId != 4

	UNION ALL

	SELECT
		articuloId,
		localidadId,
		ALM_AlmacenId AS almacenId,
		SUC_SucursalId AS sucursalId,
		existenciaLocalidad,
		SUM(existenciaLocalidad) OVER(PARTITION BY articuloId,ALM_AlmacenId) AS existenciaAlmacen,
		SUM(existenciaLocalidad) OVER(PARTITION BY articuloId,SUC_SucursalId) AS existenciaSucursal
	FROM(
		SELECT
			articuloRaizId AS articuloId,
			localidadId,
			MIN(existenciaLocalidadComponente) AS existenciaLocalidad
		FROM(
			SELECT
				articuloRaizId,
				articuloId,
				localidadId,
				CASE WHEN cantidadComponente = 0 THEN existenciaLocalidad ELSE existenciaLocalidad / cantidadComponente END AS existenciaLocalidadComponente
			FROM(
				SELECT
					articuloRaizId,
					articuloId,
					cantidadComponente,
					LOC_LocalidadId AS localidadId,
					SUM(COALESCE(LOCAA_Cantidad,0)) OVER(PARTITION BY articuloId,LOC_LocalidadId) AS existenciaLocalidad
				FROM(
					SELECT
						articuloRaizId,
						articuloId,
						SUM(cantidadComponente) AS cantidadComponente
					FROM A
					GROUP BY articuloRaizId, articuloId
				) B
				INNER JOIN Articulos ON ART_ArticuloId = articuloId AND ART_Activo = 1 AND ART_ARTT_TipoArticuloId != 4
				INNER JOIN Localidades ON LOC_Activo = 1
				LEFT JOIN LocalidadesArticulos ON LOCA_ART_ArticuloId = ART_ArticuloId AND LOCA_LOC_LocalidadId = LOC_LocalidadId
				LEFT JOIN LocalidadesArticulosAcumulados ON LOCAA_ART_ArticuloId = LOCA_ART_ArticuloId AND LOCAA_LOC_LocalidadId = LOCA_LOC_LocalidadId
			) C
		) D
		GROUP BY articuloRaizId, localidadId
	) E
	INNER JOIN Localidades ON LOC_LocalidadId = localidadId
	INNER JOIN Almacenes ON ALM_AlmacenId = LOC_ALM_AlmacenId AND ALM_Activo = 1
	INNER JOIN Sucursales ON SUC_SucursalId = ALM_SUC_SucursalId AND SUC_Activo = 1
GO