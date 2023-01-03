SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ==============================================
-- Author:		Javier Elías
-- Create date: 27/01/2022
-- Modified date: 24/11/2022
-- Description:	Función para obtener los impuestos de
--						los datos de las OV para la Factura
-- ==============================================
CREATE OR ALTER FUNCTION [dbo].[fn_getDatosFacturacionGlobalImpuestos](@ordenVentaId INT)
RETURNS TABLE WITH SCHEMABINDING
AS
RETURN
(
	SELECT Id,
		Clave,
		Nombre,
		TipoFactor,
		Base,
		TasaOCuota,
		Importe,
		OrdenVentaId
	FROM (
		SELECT OVD_OrdenVentaDetalleId AS Id,
			   ClaveIVA AS Clave,
			   NombreIVA AS Nombre,
			   TipoFactorIVA AS TipoFactor,
			   BaseIVA AS Base,
			   TasaOCuotaIVA AS TasaOCuota,
			   ImporteIVA AS Importe,
			   OVD_OV_OrdenVentaId AS OrdenVentaId
		FROM dbo.OrdenesVentaDetalles
			 CROSS APPLY dbo.fn_getMontosFacturacionDetalleOV(OVD_OrdenVentaDetalleId) AS montos
			 LEFT JOIN dbo.OrdenesVentaCancelacionesDetalles ON OVD_OrdenVentaDetalleId = OVCD_OVD_OrdenVentaDetalleId
		WHERE OVCD_OrdenVentaCancelacionDetalleId IS NULL
				AND OVD_OV_OrdenVentaId = @ordenVentaId
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
		FROM dbo.OrdenesVentaDetalles
			 CROSS APPLY dbo.fn_getMontosFacturacionDetalleOV(OVD_OrdenVentaDetalleId) AS montos
			 LEFT JOIN dbo.OrdenesVentaCancelacionesDetalles ON OVD_OrdenVentaDetalleId = OVCD_OVD_OrdenVentaDetalleId
		WHERE OVCD_OrdenVentaCancelacionDetalleId IS NULL
				AND OVD_OV_OrdenVentaId = @ordenVentaId
				AND OVD_OVD_DetallePadreId IS NULL
				AND montos.ClaveIEPS IS NOT NULL 
	) AS todo
)
GO