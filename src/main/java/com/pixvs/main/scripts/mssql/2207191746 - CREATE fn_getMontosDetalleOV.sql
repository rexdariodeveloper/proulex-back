SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER FUNCTION [dbo].[fn_getMontosDetalleOV](@ordenVentaDetalleId INT)
RETURNS TABLE
AS
RETURN
(
		SELECT *, ImporteIVA + ISNULL(ImporteIEPS, 0) AS Impuestos, Subtotal - Descuento + ImporteIVA + ISNULL(ImporteIEPS, 0) AS Total
		FROM
		(
			SELECT Cantidad,
				   ValorUnitario,
				   Subtotal,
				   Descuento,
				   ROUND((Subtotal - Descuento) * CASE WHEN IVAExento = 0 THEN IVA ELSE 0 END, 6) AS ImporteIVA,
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
				FROM OrdenesVentaDetalles AS padre
					 LEFT JOIN
					 (
						SELECT OVD_OVD_DetallePadreId AS PadreId,
							   SUM(ROUND(OVD_Cantidad * OVD_Precio, 6)) AS Subtotal,
							   SUM(ISNULL(OVD_Descuento, 0)) AS Descuento
						FROM OrdenesVentaDetalles
						WHERE OVD_OVD_DetallePadreId IS NOT NULL
						GROUP BY OVD_OVD_DetallePadreId
					 ) AS hijos ON OVD_OrdenVentaDetalleId = hijos.PadreId
				WHERE OVD_OrdenVentaDetalleId = @ordenVentaDetalleId
			) AS impuestos
		) AS todo
)