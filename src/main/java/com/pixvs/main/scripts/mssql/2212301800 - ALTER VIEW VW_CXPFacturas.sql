SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:
-- Modify Author: Rene Carrillo
-- Create date:
-- Modify date: 30/12/2022
-- Description:	La vista de las facturas
-- Version 1.0.0
-- =============================================


ALTER   VIEW [dbo].[VW_CXPFacturas] AS
SELECT CXPF_CXPFacturaId AS id,
	CXPF_CodigoRegistro AS folio,
	CXPF_FechaRegistro as fechaFactura, CONVERT(DATE, DATEADD(DAY,CXPF_DiasCredito,CXPF_FechaRegistro)) AS fechaVencimiento ,
	CXPF_CMM_TipoRegistroId as idTipoRegistro, cmm_tr.CMM_Valor as tipoRegistro,
	cmm_tp.CMM_ControlId as idTipoPago, cmm_tp.CMM_Valor as tipoPago,
	cmm_es.CMM_ControlId as idEstatus, cmm_es.CMM_Valor as estatus,

	PRO_ProveedorId as idProveedor, PRO_Codigo AS codigoProveedor, PRO_Nombre AS nombreProveedor, PRO_RFC AS proveedorRFC,

	MON_MonedaId as idMoneda, MON_Nombre as moneda,
	COALESCE(CXPF_ParidadOficial,1) as tipoCambioOficial, COALESCE(CXPF_ParidadUsuario,1) as tipoCambio,
	CXPF_MontoRegistro as monto,
	CXPF_MontoRegistro * COALESCE(CXPF_ParidadUsuario,1) as montoMN,
	CXPF_FechaPago as fechaPago, CXPF_Comentarios as comentarios,
	CXPF_UUID as uuid,
	-- CXPF_ARC_FacturaXMLId, CXPF_ARC_FacturaPDFId,
	CXPF_FechaCreacion as fechaCreacion, CXPF_FechaModificacion as fechaModificacion,
	CONCAT(uc.USU_Nombre, ' ' ,uc.USU_PrimerApellido) as usuarioCreador, CONCAT(um.USU_Nombre, ' ' ,um.USU_PrimerApellido) as usuarioModificador,
	CXPF_DiasCredito as diasCredito,
	CXPF_FechaCancelacion as fechaCancelacion, CXPF_MotivoCancelacion as motivoCancelacion,

	PARTIDAS.partidas, PARTIDAS.cantidadPartidas, PARTIDAS.subtotal, PARTIDAS.IVA, PARTIDAS.IEPS, PARTIDAS.descuento, PARTIDAS.total, PARTIDAS.totalMN,

	ISNULL(PAGOS.pagos, 0) as pagos, isnull(PAGOS.montoPagado,0.0) as montoPagado, PAGOS.ultimaFechaPago,
	isnull(PAGOS.montoPagado,0.0) * COALESCE(CXPF_ParidadUsuario,1) as montoPagadoMN

--,*
FROM CXPFacturas
LEFT JOIN Monedas on CXPF_MON_MonedaId = MON_MonedaId
LEFT JOIN ControlesMaestrosMultiples cmm_tr on CXPF_CMM_TipoRegistroId = cmm_tr.CMM_ControlId
LEFT JOIN ControlesMaestrosMultiples cmm_tp on CXPF_CMM_TipoPagoId = cmm_tp.CMM_ControlId
LEFT JOIN ControlesMaestrosMultiples cmm_es on CXPF_CMM_EstatusId = cmm_es.CMM_ControlId
LEFT JOIN Proveedores on CXPF_PRO_ProveedorId = PRO_ProveedorId
LEFT JOIN Usuarios uc on CXPF_USU_CreadoPorId = uc.USU_UsuarioId
LEFT JOIN Usuarios um on CXPF_USU_ModificadoPorId = um.USU_UsuarioId
LEFT JOIN (

	SELECT CXPFD_CXPF_CXPFacturaId,COUNT(CXPFD_CXPFacturadetalleId) as partidas,
			SUM(CXPFD_Cantidad) AS cantidadPartidas,
			SUM(dbo.getSubtotal(CXPFD_Cantidad,CXPFD_PrecioUnitario,CXPFD_Descuento,CXPFD_IVA,CXPFD_IEPS,CXPFD_IEPSCuotaFija,1) ) as subtotal,
			SUM(dbo.getIVA(CXPFD_Cantidad,CXPFD_PrecioUnitario,CXPFD_Descuento,CXPFD_IVA,CXPFD_IEPS,CXPFD_IEPSCuotaFija,1) ) as IVA,
			SUM(dbo.getIEPS(CXPFD_Cantidad,CXPFD_PrecioUnitario,CXPFD_Descuento,CXPFD_IVA,CXPFD_IEPS,CXPFD_IEPSCuotaFija,1) ) as IEPS,
			SUM(dbo.getDescuento(CXPFD_Cantidad,CXPFD_PrecioUnitario,CXPFD_Descuento,CXPFD_IVA,CXPFD_IEPS,CXPFD_IEPSCuotaFija,1) ) as descuento,
			SUM(dbo.getTotal(CXPFD_Cantidad,CXPFD_PrecioUnitario,CXPFD_Descuento,CXPFD_IVA,CXPFD_IEPS,CXPFD_IEPSCuotaFija,1) ) as total,
			SUM(dbo.getTotal(CXPFD_Cantidad,CXPFD_PrecioUnitario,CXPFD_Descuento,CXPFD_IVA,CXPFD_IEPS,CXPFD_IEPSCuotaFija,CXPF_ParidadUsuario) ) as totalMN
		FROM CXPFacturasDetalles
		LEFT JOIN CXPFacturas ON CXPFD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		GROUP BY CXPFD_CXPF_CXPFacturaId

) AS PARTIDAS on CXPF_CXPFacturaId = CXPFD_CXPF_CXPFacturaId
LEFT JOIN (
	SELECT CXPPD_CXPF_CXPFacturaId, COUNT(CXPPD_CXPPagoDetalleId) as pagos, SUM(CXPPD_MontoAplicado) as montoPagado, MAX(CXPP_FechaPago) AS ultimaFechaPago
		FROM CXPPagosDetalles
		LEFT JOIN CXPPagos ON CXPPD_CXPP_CXPPagoId = CXPP_CXPPagoId
		WHERE CXPP_CMM_EstatusId in (2000172, 2000171) /*Pago parcial y Pagado*/
		GROUP BY CXPPD_CXPF_CXPFacturaId

) AS PAGOS on CXPF_CXPFacturaId = CXPPD_CXPF_CXPFacturaId

--ORDER BY CXPF_FechaCreacion

GO