SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_reporteAntiguedadSaldosDetalle_] (@proveedoresIds varchar(max), @facturasIds varchar(max), @monedaId int, @diasAgrupados int)
RETURNS TABLE
AS 
RETURN
(
	SELECT
		codigoProveedor proveedorCodigo,
		nombreProveedor proveedorNombre,
		folio codigoFactura,
		fechaFactura,
		fechaVencimiento,
		CASE WHEN DATEDIFF(day,fechaVencimiento,GETDATE()) > 0 THEN DATEDIFF(day,fechaVencimiento,GETDATE()) ELSE 0 END diasVencimiento,
		monto montoOriginal,
		-- Saldos vencidos
		CASE WHEN DATEDIFF(day,fechaVencimiento,GETDATE()) > 0 THEN (monto - montoPagado) ELSE 0 END montoVencidoTotal,
		CASE WHEN DATEDIFF(day,fechaVencimiento,GETDATE()) > 0 AND (DATEDIFF(day,fechaVencimiento,GETDATE()) / @diasAgrupados) = 0 THEN (monto - montoPagado) ELSE 0 END montoVencido1,
		CASE WHEN DATEDIFF(day,fechaVencimiento,GETDATE()) > 0 AND (DATEDIFF(day,fechaVencimiento,GETDATE()) / @diasAgrupados) = 1 THEN (monto - montoPagado) ELSE 0 END montoVencido2,
		CASE WHEN DATEDIFF(day,fechaVencimiento,GETDATE()) > 0 AND (DATEDIFF(day,fechaVencimiento,GETDATE()) / @diasAgrupados) = 2 THEN (monto - montoPagado) ELSE 0 END montoVencido3,
		CASE WHEN DATEDIFF(day,fechaVencimiento,GETDATE()) > 0 AND (DATEDIFF(day,fechaVencimiento,GETDATE()) / @diasAgrupados) > 2 THEN (monto - montoPagado) ELSE 0 END montoVencido4,
		-- Saldos por vencer
		CASE WHEN DATEDIFF(day,fechaVencimiento,GETDATE()) <= 0 THEN (monto - montoPagado) ELSE 0 END montoVencerTotal,
		CASE WHEN DATEDIFF(day,fechaVencimiento,GETDATE()) <= 0 AND (ABS(DATEDIFF(day,fechaVencimiento,GETDATE())) / @diasAgrupados) = 0 THEN (monto - montoPagado) ELSE 0 END montoVencer1,
		CASE WHEN DATEDIFF(day,fechaVencimiento,GETDATE()) <= 0 AND (ABS(DATEDIFF(day,fechaVencimiento,GETDATE())) / @diasAgrupados) = 1 THEN (monto - montoPagado) ELSE 0 END montoVencer2,
		CASE WHEN DATEDIFF(day,fechaVencimiento,GETDATE()) <= 0 AND (ABS(DATEDIFF(day,fechaVencimiento,GETDATE())) / @diasAgrupados) = 2 THEN (monto - montoPagado) ELSE 0 END montoVencer3,
		CASE WHEN DATEDIFF(day,fechaVencimiento,GETDATE()) <= 0 AND (ABS(DATEDIFF(day,fechaVencimiento,GETDATE())) / @diasAgrupados) > 2 THEN (monto - montoPagado) ELSE 0 END montoVencer4,

		(monto - montoPagado) montoTotal,
		montoProgramado,
		codigos,
		-- ids
		id facturaId,
		idProveedor proveedorId,
		idMoneda
	FROM
		VW_CXPFacturas
		LEFT JOIN
		(
			SELECT 
				string_agg(CXPS_CodigoSolicitud, ', ') codigos,
				CXPSD_CXPF_CXPFacturaId facturaId, 
				SUM(CXPSD_MontoProgramado) montoProgramado
			FROM 
				CXPSolicitudesPagosDetalles 
				INNER JOIN CXPSolicitudesPagos ON CXPS_CXPSolicitudPagoId = CXPSD_CXPS_CXPSolicitudPagoId
			WHERE 
				CXPSD_CMM_EstatusId = 2000161
			GROUP BY
				CXPSD_CXPF_CXPFacturaId
		)programados ON id = facturaId
	WHERE
		idEstatus <> 2000114
		AND (monto - montoPagado) > 0
		AND (@proveedoresIds IS NULL OR @proveedoresIds LIKE ('%|' + CAST(idProveedor AS varchar(max)) + '|%'))
		AND (@facturasIds IS NULL OR @facturasIds LIKE ('%|' + CAST(id AS varchar(max)) + '|%'))
		AND (@monedaId IS NULL OR @monedaId = idMoneda)
)
GO