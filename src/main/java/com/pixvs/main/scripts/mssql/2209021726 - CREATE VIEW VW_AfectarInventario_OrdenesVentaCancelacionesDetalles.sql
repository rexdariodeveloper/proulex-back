/**
* Created by Angel Daniel Hernández Silva on 02/09/2022.
*/

CREATE OR ALTER VIEW [dbo].[VW_AfectarInventario_OrdenesVentaCancelacionesDetalles] AS
	SELECT
		OVCD_OrdenVentaCancelacionDetalleId AS id,
		COALESCE(Curso.ART_ArticuloId,Curso.ART_ArticuloId) AS articuloId,
		OV_SUC_SucursalId AS sucursalId,
		COALESCE(OVDLibro.OVD_Cantidad,OVDCurso.OVD_Cantidad) AS cantidad,
		OV_Codigo AS codigoOV,
		COALESCE(OVDLibro.OVD_Precio,OVDCurso.OVD_Precio) AS precio,
		OVC_USU_CreadoPorId AS usuarioId
	FROM OrdenesVentaCancelacionesDetalles
	INNER JOIN OrdenesVentaDetalles AS OVDCurso ON OVDCurso.OVD_OrdenVentaDetalleId = OVCD_OVD_OrdenVentaDetalleId
	LEFT JOIN OrdenesVentaDetalles AS OVDLibro ON OVDLibro.OVD_OVD_DetallePadreId = OVDCurso.OVD_OrdenVentaDetalleId
	INNER JOIN Articulos AS Curso ON Curso.ART_ArticuloId = OVDCurso.OVD_ART_ArticuloId
	LEFT JOIN Articulos AS Libro ON Libro.ART_ArticuloId = OVDLibro.OVD_ART_ArticuloId
	INNER JOIN OrdenesVenta ON OV_OrdenVentaId = OVDCurso.OVD_OV_OrdenVentaId
	INNER JOIN OrdenesVentaCancelaciones ON OVC_OrdenVentaCancelacionId = OVCD_OVC_OrdenVentaCancelacionId
	INNER JOIN ControlesMaestrosMultiples AS CMMSucursalJOBS ON CMMSucursalJOBS.CMM_ControlId = 2000630
	INNER JOIN ControlesMaestrosMultiples AS CMMSucursalJOBSSEMS ON CMMSucursalJOBSSEMS.CMM_ControlId = 2000631
	LEFT JOIN InventariosMovimientos
		ON IM_ReferenciaMovtoId = OVCD_OrdenVentaCancelacionDetalleId
		AND IM_CMM_TipoMovimientoId = 2000439 -- Cancelación de nota de venta
	WHERE
		COALESCE(OVCD_RegresoLibro,0) = 1
		AND CAST(CMMSucursalJOBS.CMM_Valor AS int) != OV_SUC_SucursalId
		AND CAST(CMMSucursalJOBSSEMS.CMM_Valor AS int) != OV_SUC_SucursalId
		AND IM_InventarioMovimientoId IS NULL
GO