SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ==============================================
-- Author:		Javier Elías
-- Create date: 21/01/2022
-- Modified date: 24/11/2022
-- Description:	Función para obtener los montos para
--						facturar detalles de las OV
-- ==============================================
CREATE OR ALTER FUNCTION [dbo].[fn_getMontosFacturacionDetalleOV](@ordenVentaDetalleId INT)
RETURNS TABLE WITH SCHEMABINDING
AS
RETURN
(
	SELECT Cantidad,
			ValorUnitario,
			Subtotal,
			Descuento,
			ClaveIVA,
			NombreIVA,
			TipoFactorIVA,
			BaseIVA,
			TasaOCuotaIVA,
			ImporteIVA,
			ClaveIEPS,
			NombreIEPS,
			TipoFactorIEPS,
			BaseIEPS,
			TasaOCuotaIEPS,
			ImporteIEPS,
			Subtotal - Descuento + ImporteIVA + ISNULL(ImporteIEPS, 0) AS Total
	FROM
	(
		SELECT Cantidad,
				ValorUnitario,
				Subtotal,
				Descuento,
				'002' AS ClaveIVA,
				'IVA' AS NombreIVA,
				CASE WHEN IVAExento = 1 THEN 'Exento' ELSE 'Tasa' END AS TipoFactorIVA,
				Subtotal - Descuento AS BaseIVA,
				CASE WHEN IVAExento = 0 THEN IVA ELSE 0 END AS TasaOCuotaIVA,
				ROUND((Subtotal - Descuento) * CASE WHEN IVAExento = 0 THEN IVA ELSE 0 END, 6) AS ImporteIVA,
				CASE WHEN IEPS IS NOT NULL OR IEPSCuotaFija IS NOT NULL THEN '003' END AS ClaveIEPS,
				CASE WHEN IEPS IS NOT NULL OR IEPSCuotaFija IS NOT NULL THEN 'IEPS' END AS NombreIEPS,
				CASE WHEN IEPSCuotaFija IS NOT NULL THEN 'Cuota' ELSE CASE WHEN IEPS IS NOT NULL THEN 'Tasa' END END AS TipoFactorIEPS,
				CASE WHEN IEPS IS NOT NULL OR IEPSCuotaFija IS NOT NULL THEN Subtotal - Descuento END AS BaseIEPS,
				ISNULL(IEPS, IEPSCuotaFija) AS TasaOCuotaIEPS,
				CASE WHEN IEPS IS NOT NULL THEN ROUND((Subtotal - Descuento) * IEPS, 6) ELSE CASE WHEN IEPSCuotaFija IS NOT NULL THEN ROUND(Cantidad * IEPSCuotaFija, 6) END END AS ImporteIEPS
		FROM
		(
			SELECT OVD_Cantidad AS Cantidad,
					OVD_Precio + ISNULL(hijos.Subtotal, 0) AS ValorUnitario,
					ROUND(OVD_Cantidad * (OVD_Precio + ISNULL(hijos.Subtotal, 0)), 6) AS Subtotal,
					ISNULL(OVD_Descuento, 0) + ISNULL(hijos.Descuento, 0) AS Descuento,
					ISNULL(OVD_IVA, 0) AS IVA,
					CASE WHEN ISNULL(OVD_IVA, 0) = 0 THEN 1 ELSE ISNULL(OVD_IVAExento, 0) END AS IVAExento,
					CASE WHEN OVD_IEPS = 0 THEN NULL ELSE OVD_IEPS END AS IEPS,
					CASE WHEN OVD_IEPSCuotaFija = 0 THEN NULL ELSE OVD_IEPSCuotaFija END AS IEPSCuotaFija
			FROM dbo.OrdenesVentaDetalles AS padre
					LEFT JOIN
					(
					SELECT OVD_OVD_DetallePadreId AS PadreId,
							SUM(ROUND(OVD_Cantidad * OVD_Precio, 6)) AS Subtotal,
							SUM(ISNULL(OVD_Descuento, 0)) AS Descuento
					FROM dbo.OrdenesVentaDetalles
							LEFT JOIN dbo.OrdenesVentaCancelacionesDetalles ON OVD_OrdenVentaDetalleId = OVCD_OVD_OrdenVentaDetalleId
					WHERE OVCD_OrdenVentaCancelacionDetalleId IS NULL
							AND OVD_OVD_DetallePadreId IS NOT NULL
					GROUP BY OVD_OVD_DetallePadreId
					) AS hijos ON OVD_OrdenVentaDetalleId = hijos.PadreId
					LEFT JOIN dbo.OrdenesVentaCancelacionesDetalles ON padre.OVD_OrdenVentaDetalleId = OVCD_OVD_OrdenVentaDetalleId
			WHERE OVCD_OrdenVentaCancelacionDetalleId IS NULL
					AND OVD_OrdenVentaDetalleId = @ordenVentaDetalleId
		) AS impuestos
	) AS todo
)
GO