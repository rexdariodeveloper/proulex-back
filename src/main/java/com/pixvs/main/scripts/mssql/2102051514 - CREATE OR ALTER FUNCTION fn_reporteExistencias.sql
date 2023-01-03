SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_reporteExistencias] ( @fechaFin DATETIME, @articulos NVARCHAR(MAX), @localidades NVARCHAR(MAX), @ceros BIT, @idUsuario INT)
RETURNS TABLE 
AS
RETURN 
(
	-- TODO: Mostrar valor de inventario por permiso de rol
	SELECT
		ART_CodigoArticulo codigo,
		ART_CodigoBarras isbn,
		editorial.CMM_Valor editorial,
		ART_NombreArticulo nombre,
		UM_Nombre um,
		CONCAT(ALM_CodigoAlmacen,' - ',ALM_Nombre) almacen,
		CONCAT(LOC_CodigoLocalidad,' - ',LOC_Nombre) localidad,
		COALESCE(existencias.cantidad, 0) existencia,
		ART_CostoPromedio costo,
		COALESCE(existencias.cantidad, 0) * ART_CostoPromedio total
		-- ids
		/**
			ART_ArticuloId articuloId,
			ALM_AlmacenId almacenId,
			LOC_LocalidadId localidadId,
		**/
	FROM
		Almacenes 
		/* Filter by user-warehouse assignment */
		INNER JOIN UsuariosAlmacenes ON ALM_AlmacenId = USUA_ALM_AlmacenId AND USUA_USU_UsuarioId = @idUsuario
		INNER JOIN Localidades ON ALM_AlmacenId = LOC_ALM_AlmacenId
		INNER JOIN LocalidadesArticulos ON LOC_LocalidadId = LOCA_LOC_LocalidadId
		INNER JOIN Articulos ON LOCA_ART_ArticuloId = ART_ArticuloId
		INNER JOIN ControlesMaestrosMultiples editorial ON ART_CMM_EditorialId = CMM_ControlId
		INNER JOIN UnidadesMedidas ON ART_UM_UnidadMedidaInventarioId = UM_UnidadMedidaId
		LEFT JOIN 
		(
			SELECT 
				IM_ART_ArticuloId articuloId, 
				IM_LOC_LocalidadId localidadId, 
				SUM(IM_Cantidad) cantidad 
			FROM 
				InventariosMovimientos 
			WHERE
				IM_FechaCreacion < ISNULL(DATEADD(DAY, 1, @fechaFin), GETDATE())
			GROUP BY 
				IM_ART_ArticuloId, IM_LOC_LocalidadId
		) existencias ON existencias.articuloId = ART_ArticuloId AND existencias.localidadId = LOC_LocalidadId
	WHERE
		ART_Activo = 1
		AND ART_Inventariable = 1
		AND ALM_Activo = 1
		AND LOC_Activo = 1
		/* Custom filters */
		AND ISNULL(@localidades, '|'+CAST(LOC_LocalidadId AS NVARCHAR(10))+'|') LIKE '%|'+CAST(LOC_LocalidadId AS NVARCHAR(10))+'|%'
		AND ISNULL(@articulos, '|'+CAST(ART_ArticuloId AS NVARCHAR(10))+'|') LIKE '%|'+CAST(ART_ArticuloId AS NVARCHAR(10))+'|%'
		AND (@ceros = 1 OR existencias.cantidad > 0)
)