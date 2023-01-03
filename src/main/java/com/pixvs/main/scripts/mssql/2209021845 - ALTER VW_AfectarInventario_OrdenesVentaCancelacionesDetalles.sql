/**
* Created by Angel Daniel Hernández Silva on 02/09/2022.
* Modified by Javier Elías on 02/09/2022.
*/

CREATE OR ALTER VIEW [dbo].[VW_AfectarInventario_OrdenesVentaCancelacionesDetalles]
AS
SELECT OVCD_OrdenVentaCancelacionDetalleId AS id,
       ISNULL(hijos.OVD_ART_ArticuloId, padre.OVD_ART_ArticuloId) AS articuloId,
       OV_SUC_SucursalId AS sucursalId,
       ISNULL(hijos.OVD_Cantidad, padre.OVD_Cantidad) AS cantidad,
       OV_Codigo AS codigoOV,
       ISNULL(hijos.OVD_Precio, padre.OVD_Precio) AS precio,
       OVC_USU_CreadoPorId AS usuarioId,
	   OVC_OrdenVentaCancelacionId AS cancelacionId
FROM OrdenesVentaCancelaciones
     INNER JOIN OrdenesVentaCancelacionesDetalles ON OVC_OrdenVentaCancelacionId = OVCD_OVC_OrdenVentaCancelacionId
	 INNER JOIN OrdenesVentaDetalles AS padre ON OVCD_OVD_OrdenVentaDetalleId = padre.OVD_OrdenVentaDetalleId AND padre.OVD_OVD_DetallePadreId IS NULL
	 LEFT JOIN OrdenesVentaDetalles AS hijos ON padre.OVD_OrdenVentaDetalleId = hijos.OVD_OVD_DetallePadreId
	 INNER JOIN OrdenesVenta ON padre.OVD_OV_OrdenVentaId = OV_OrdenVentaId
	 INNER JOIN ControlesMaestrosMultiples AS sucursalJOBS ON sucursalJOBS.CMM_ControlId = 2000630
     INNER JOIN ControlesMaestrosMultiples AS sucursalJOBSSEMS ON sucursalJOBSSEMS.CMM_ControlId = 2000631
	 LEFT JOIN InventariosMovimientos ON OVCD_OrdenVentaCancelacionDetalleId = IM_ReferenciaMovtoId AND ISNULL(hijos.OVD_ART_ArticuloId, padre.OVD_ART_ArticuloId) = IM_ART_ArticuloId AND IM_CMM_TipoMovimientoId = 2000439 -- Cancelación de nota de venta
WHERE ISNULL(OVCD_RegresoLibro, 0) = 1
      AND CAST(sucursalJOBS.CMM_Valor AS INT) != OV_SUC_SucursalId
      AND CAST(sucursalJOBSSEMS.CMM_Valor AS INT) != OV_SUC_SucursalId
      AND IM_InventarioMovimientoId IS NULL
GO