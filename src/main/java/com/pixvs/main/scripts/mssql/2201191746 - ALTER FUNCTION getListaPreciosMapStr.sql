/**
 * Created by Angel Daniel Hernández Silva on 14/01/2022.
 * Object:  ALTER FUNCTION [dbo].[getListaPreciosMapStr]
 */

CREATE OR ALTER FUNCTION [dbo].[getListaPreciosMapStr] ( @listaPreciosId int, @sucursalId int )
RETURNS varchar(max)
AS
BEGIN

	DECLARE @listaPreciosMapStr varchar(max) = '{}';
	
	SELECT
		@listaPreciosMapStr = CONCAT('{',STRING_AGG(CONCAT('"',articuloId,'": ',precioVenta * (100 -porcentajeDescuento) / 100),','),'}')
	FROM (
		SELECT
			LIPRED_ListadoPrecioDetalleId AS detalleId,
			LIPRED_ART_ArticuloId AS articuloId,
			CASE WHEN CMA_Valor = '0' THEN LIPRED_Precio ELSE (SELECT Total FROM [dbo].[fn_getImpuestosArticulo](1,LIPRED_Precio,0,CASE WHEN ART_IVAExento = 1 THEN 0 ELSE ART_IVA END,ART_IEPS,ART_IEPSCuotaFija)) END AS precioVenta,
			COALESCE(porcentajeDescuento,0) AS porcentajeDescuento
		FROM ListadosPrecios
		INNER JOIN ListadosPreciosDetalles ON LIPRED_LIPRE_ListadoPrecioId = LIPRE_ListadoPrecioId
		INNER JOIN Articulos ON ART_ArticuloId = LIPRED_ART_ArticuloId
		INNER JOIN ControlesMaestros ON CMA_Nombre = 'CM_CALCULO_PRECIO_UNITARIO'
		LEFT JOIN (
			SELECT
				ART_ArticuloId AS articuloId,
				MAX(PADESC_PorcentajeDescuento) AS porcentajeDescuento
			FROM(
				SELECT
					ART_ArticuloId,
					CASE WHEN MIN(PADESC_FechaCreacion) OVER(PARTITION BY ART_ArticuloId) = PADESC_FechaCreacion THEN PADESC_PorcentajeDescuento ELSE 0 END AS PADESC_PorcentajeDescuento
				FROM PADescuentosArticulos
				INNER JOIN Articulos ON ART_ArticuloId = PADESCA_ART_ArticuloId
				INNER JOIN PADescuentos ON PADESC_DescuentoId = PADESCA_PADESC_DescuentoId
				INNER JOIN PADescuentosSucursales ON PADESCS_PADESC_DescuentoId = PADESC_DescuentoId
				WHERE
					PADESCA_Activo = 1
					AND ART_Activo = 1
					AND PADESC_Activo = 1
					AND PADESC_FechaInicio <= GETDATE()
					AND PADESC_FechaFin >= GETDATE()
					AND PADESCS_SUC_SucursalId = @sucursalId
			) DescuentosArticulos
			GROUP BY ART_ArticuloId

			UNION ALL

			SELECT
				ART_ArticuloId AS articuloId,
				MAX(PADESC_PorcentajeDescuento) AS porcentajeDescuento
			FROM(
				SELECT
					ART_ArticuloId,
					CASE WHEN MIN(PADESC_FechaCreacion) OVER(PARTITION BY ART_ArticuloId) = PADESC_FechaCreacion THEN PADESC_PorcentajeDescuento ELSE 0 END AS PADESC_PorcentajeDescuento
				FROM PADescuentosDetalles
				INNER JOIN ProgramasIdiomasDescuentosDetalles ON PIDD_PADESCD_DescuentoDetalleId = PADESCD_DescuentoDetalleId
				INNER JOIN Articulos
					ON ART_PROGI_ProgramaIdiomaId = PIDD_PROGI_ProgramaIdiomaId
					AND ART_PAMOD_ModalidadId = PADESCD_PAMOD_ModalidadId
					AND ART_ARTT_TipoArticuloId = 5 -- TIPO SISTEMA
				INNER JOIN PADescuentos ON PADESC_DescuentoId = PADESCD_PADESC_DescuentoId
				INNER JOIN PADescuentosSucursales ON PADESCS_PADESC_DescuentoId = PADESC_DescuentoId
				WHERE
					ART_Activo = 1
					AND PADESC_Activo = 1
					AND PADESC_FechaInicio <= GETDATE()
					AND PADESC_FechaFin >= GETDATE()
					AND PADESCS_SUC_SucursalId = @sucursalId
			) DescuentosCursos
			GROUP BY ART_ArticuloId
		) Descuentos ON ART_ArticuloId = articuloId
		WHERE LIPRE_ListadoPrecioId = @listaPreciosId
	)Precios
    
	return @listaPreciosMapStr
END
GO