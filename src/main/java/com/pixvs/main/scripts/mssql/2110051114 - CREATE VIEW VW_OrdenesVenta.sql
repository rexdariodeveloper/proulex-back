/**
* Created by Angel Daniel Hernández Silva on 30/09/2021.
* Object: CREATE OR ALTER VIEW [dbo].[VW_OrdenesVenta]
*/

CREATE OR ALTER VIEW [dbo].[VW_OrdenesVenta] AS
	SELECT
		-- Columnas originales
		id,
		OV_Codigo AS codigo,
		OV_SUC_SucursalId AS sucursalId,
		OV_CLI_ClienteId AS clienteId,
		OV_FechaOV AS fechaOV,
		OV_FechaRequerida AS fechaRequerida,
		OV_DireccionOV AS direccionOV,
		OV_EnviarA AS enviarA,
		OV_MON_MonedaId AS monedaId,
		OV_DiazCredito AS diazCredito,
		OV_MPPV_MedioPagoPVId AS medioPagoPVId,
		OV_ReferenciaPago AS referenciaPago,
		OV_Comentario AS comentario,
		OV_CMM_EstatusId AS estatusId,
		OV_FechaCreacion AS fechaCreacion,
		OV_FechaModificacion AS fechaModificacion,
		OV_USU_CreadoPorId AS creadoPorId,
		OV_USU_ModificadoPorId AS modificadoPorId,
		OV_LigaCentroPagos AS ligaCentroPagos,
		OV_SCC_SucursalCorteCajaId AS sucursalCorteCajaId,

		-- Columnas info
		Estatus.CMM_Valor AS estatusValor,

		-- Columnas montos
		montoSubtotal,
		montoIVA,
		montoIEPS,
		montoDescuento,
		montoTotal
	FROM OrdenesVenta
	INNER JOIN ControlesMaestrosMultiples Estatus ON Estatus.CMM_ControlId = OV_CMM_EstatusId
	INNER JOIN (
		SELECT
			id,
			SUM(subtotalDetalle) AS montoSubtotal,
			SUM(ivaDetalle) AS montoIVA,
			SUM(iepsDetalle) AS montoIEPS,
			SUM(descuentoDetalle) AS montoDescuento,
			SUM(totalDetalle) AS montoTotal
		FROM (
			SELECT
				OV_OrdenVentaId AS id,
				(SELECT Subtotal FROM [dbo].[fn_getImpuestosArticulo](OVD_Cantidad,OVD_Precio,OVD_Descuento,CASE WHEN OVD_IVAExento = 1 THEN 0 ELSE OVD_IVA END,OVD_IEPS,OVD_IEPSCuotaFija)) AS subtotalDetalle,
				(SELECT IVA FROM [dbo].[fn_getImpuestosArticulo](OVD_Cantidad,OVD_Precio,OVD_Descuento,CASE WHEN OVD_IVAExento = 1 THEN 0 ELSE OVD_IVA END,OVD_IEPS,OVD_IEPSCuotaFija)) AS ivaDetalle,
				(SELECT IEPS FROM [dbo].[fn_getImpuestosArticulo](OVD_Cantidad,OVD_Precio,OVD_Descuento,CASE WHEN OVD_IVAExento = 1 THEN 0 ELSE OVD_IVA END,OVD_IEPS,OVD_IEPSCuotaFija)) AS iepsDetalle,
				(SELECT Descuento FROM [dbo].[fn_getImpuestosArticulo](OVD_Cantidad,OVD_Precio,OVD_Descuento,CASE WHEN OVD_IVAExento = 1 THEN 0 ELSE OVD_IVA END,OVD_IEPS,OVD_IEPSCuotaFija)) AS descuentoDetalle,
				(SELECT Total FROM [dbo].[fn_getImpuestosArticulo](OVD_Cantidad,OVD_Precio,OVD_Descuento,CASE WHEN OVD_IVAExento = 1 THEN 0 ELSE OVD_IVA END,OVD_IEPS,OVD_IEPSCuotaFija)) AS totalDetalle
			FROM OrdenesVenta
			INNER JOIN OrdenesVentaDetalles ON OVD_OV_OrdenVentaId = OV_OrdenVentaId
		) MontosOVD
		GROUP BY id
	) Montos ON id = OV_OrdenVentaId
GO