/**
 * Created by Angel Daniel Hernández Silva on 19/01/2022.
 * Object:  ALTER FUNCTION [dbo].[getArticuloDescuento]
 */
 
CREATE OR ALTER FUNCTION [dbo].[getArticuloDescuento] (@articuloId int, @sucursalId int)
RETURNS int
AS
BEGIN
	DECLARE @valorRet int = 0;
	
	SELECT @valorRet = COALESCE(MAX(porcentajeDescuento),0)
	FROM(
		SELECT
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
				AND ART_ArticuloId = @articuloId
		) AS DescuentosArticulos

		UNION ALL

		SELECT
			MAX(PADESC_PorcentajeDescuento) AS porcentajeDescuento
		FROM(
			SELECT
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
				AND ART_ArticuloId = @articuloId
		) AS DescuentosCursos
	) AS Descuento

	RETURN @valorRet;
END
GO