/**
 * Created by Javier Elías on 27/01/2022.
 */
 SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER FUNCTION [dbo].[fn_getDatosFacturacionGlobalImpuestos](@ordenVentaId INT)
RETURNS TABLE
AS
RETURN
(
	SELECT * 
	FROM (
		SELECT OVD_OrdenVentaDetalleId AS Id,
			   ClaveIVA AS Clave,
			   NombreIVA AS Nombre,
			   TipoFactorIVA AS TipoFactor,
			   BaseIVA AS Base,
			   TasaOCuotaIVA AS TasaOCuota,
			   ImporteIVA AS Importe,
			   OVD_OV_OrdenVentaId AS OrdenVentaId
		FROM OrdenesVentaDetalles
			 CROSS APPLY dbo.fn_getMontosFacturacionDetalleOV(OVD_OrdenVentaDetalleId) AS montos
		WHERE OVD_OV_OrdenVentaId = @ordenVentaId
				AND OVD_OVD_DetallePadreId IS NULL 

		UNION ALL

		SELECT OVD_OrdenVentaDetalleId AS Id,
			   ClaveIEPS AS Clave,
			   NombreIEPS AS Nombre,
			   TipoFactorIEPS AS TipoFactor,
			   BaseIEPS AS Base,
			   TasaOCuotaIEPS AS TasaOCuota,
			   ImporteIVA AS Importe,
			   OVD_OV_OrdenVentaId AS OrdenVentaId
		FROM OrdenesVentaDetalles
			 CROSS APPLY dbo.fn_getMontosFacturacionDetalleOV(OVD_OrdenVentaDetalleId) AS montos
		WHERE OVD_OV_OrdenVentaId = @ordenVentaId
				AND OVD_OVD_DetallePadreId IS NULL
				AND montos.ClaveIEPS IS NOT NULL 
	) AS todo
)