/**
 * Created by Angel Daniel Hernández Silva on 31/05/2022.
 */

CREATE OR ALTER FUNCTION [dbo].[getDescuentoAutomaticoGrupo] ( @grupoId int, @sucursalId int, @fecha datetime )
RETURNS int
AS
BEGIN

	DECLARE @porcentajeDescuento int = 0;
	
	SELECT @porcentajeDescuento = MAX(porcentajeDescuento)
	FROM(
		SELECT
			PROGRU_GrupoId AS grupoId,
			PADESCS_SUC_SucursalId AS sucursalId,
			CASE WHEN MIN(PADESC_FechaCreacion) OVER(PARTITION BY PROGRU_GrupoId,PADESCS_SUC_SucursalId) = PADESC_FechaCreacion THEN PADESC_PorcentajeDescuento ELSE 0 END AS porcentajeDescuento
		FROM PADescuentos
		INNER JOIN PADescuentosDetalles ON PADESCD_PADESC_DescuentoId = PADESC_DescuentoId
		INNER JOIN PADescuentosSucursales ON PADESCS_PADESC_DescuentoId = PADESC_DescuentoId
		INNER JOIN ProgramasIdiomasDescuentosDetalles ON PIDD_PADESCD_DescuentoDetalleId = PADESCD_DescuentoDetalleId
		INNER JOIN ProgramasGrupos
			ON PROGRU_PROGI_ProgramaIdiomaId = PIDD_PROGI_ProgramaIdiomaId
			AND PROGRU_PAMOD_ModalidadId = PADESCD_PAMOD_ModalidadId
		WHERE
			PADESC_Activo = 1
			AND PADESC_CMM_TipoId = 2000700
			AND PADESCS_Activo = 1
			AND PADESC_FechaInicio <= COALESCE(@fecha,GETDATE())
			AND CAST(CAST(PADESC_FechaFin AS varchar(10)) + ' 23:59:59.999' AS datetime) >= COALESCE(@fecha,GETDATE())
			AND PROGRU_GrupoId = @grupoId
			AND PADESCS_SUC_SucursalId =  @sucursalId
	) AS Descuento
    
	return @porcentajeDescuento
END
GO