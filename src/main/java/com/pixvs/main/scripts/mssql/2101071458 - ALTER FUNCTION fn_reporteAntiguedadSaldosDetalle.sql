SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER FUNCTION [dbo].[fn_reporteAntiguedadSaldosDetalle] (@proveedoresIds varchar(max), @facturasIds varchar(max), @monedaId int, @diasAgrupados int, @mostrarVencidos bit, @mostrarPorVencer bit)
RETURNS @tablaTMP table(
	facturaId int,
	proveedorId int,
	proveedorCodigo varchar(80),
	proveedorNombre varchar(250),
	codigoRegistro varchar(130),
	fechaRegistro datetime,
	fechaVencimiento datetime,
	diasVencimiento int,
	montoRegistro decimal(28,2),
	montoActual decimal(28,2),
	montoP1 decimal(28,2),
	montoP2 decimal(28,2),
	montoP3 decimal(28,2),
	montoP4 decimal(28,2)
)

AS BEGIN

	INSERT @tablaTMP
	SELECT CXPF_CXPFacturaId AS facturaId,
		   PRO_ProveedorId AS proveedorId, 
		   PRO_Codigo AS proveedorCodigo, 
		   PRO_Nombre AS proveedorNombre, 
		   CXPF_CodigoRegistro AS codigoRegistro, 
		   CXPF_FechaRegistro AS fechaRegistro, 
		   DATEADD(day, CXPF_DiasCredito, CXPF_FechaRegistro) AS fechaVencimiento, 
		   CASE
			   WHEN DATEDIFF(day, DATEADD(day, CXPF_DiasCredito, CXPF_FechaRegistro), GETDATE()) > 0
			   THEN DATEDIFF(day, DATEADD(day, CXPF_DiasCredito, CXPF_FechaRegistro), GETDATE())
			   ELSE 0
		   END AS diasVencimiento,
		   CXPF_MontoRegistro AS montoRegistro, 
		   CXPF_MontoRegistro - COALESCE(SUM(CXPPD_MontoAplicado), 0) AS montoActual,
		   CASE
			   WHEN DATEDIFF(day, DATEADD(day, CXPF_DiasCredito, CXPF_FechaRegistro), GETDATE()) > 0 AND (DATEDIFF(day, DATEADD(day, CXPF_DiasCredito, CXPF_FechaRegistro), GETDATE())/@diasAgrupados) = 0
			   THEN CXPF_MontoRegistro - COALESCE(SUM(CXPPD_MontoAplicado), 0)
			   ELSE 0
		   END AS montoP1,
		   CASE
			   WHEN (DATEDIFF(day, DATEADD(day, CXPF_DiasCredito, CXPF_FechaRegistro), GETDATE())/@diasAgrupados) = 1
			   THEN CXPF_MontoRegistro - COALESCE(SUM(CXPPD_MontoAplicado), 0)
			   ELSE 0
		   END AS montoP2,
		   CASE
			   WHEN (DATEDIFF(day, DATEADD(day, CXPF_DiasCredito, CXPF_FechaRegistro), GETDATE())/@diasAgrupados) = 2
			   THEN CXPF_MontoRegistro - COALESCE(SUM(CXPPD_MontoAplicado), 0)
			   ELSE 0
		   END AS montoP3,
		   CASE
			   WHEN (DATEDIFF(day, DATEADD(day, CXPF_DiasCredito, CXPF_FechaRegistro), GETDATE())/@diasAgrupados) > 2
			   THEN CXPF_MontoRegistro - COALESCE(SUM(CXPPD_MontoAplicado), 0)
			   ELSE 0
		   END AS montoP4
	FROM CXPFacturas
		 INNER JOIN Proveedores ON PRO_ProveedorId = CXPF_PRO_ProveedorId
		 LEFT JOIN CXPPagosDetalles ON CXPPD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		 LEFT JOIN CXPPagos ON CXPP_CXPPagoId = CXPPD_CXPP_CXPPagoId
	WHERE (
			  CXPPD_CXPPagoDetalleId IS NULL OR
			  CXPP_CMM_EstatusId != 2000173 -- Pagos que no sean cancelados
		  )
		  AND (
			  @proveedoresIds IS NULL
			  OR @proveedoresIds LIKE ('%' + CAST(PRO_ProveedorId AS varchar(max)) + '%')
		  )
		  AND (
			  @facturasIds IS NULL
			  OR @facturasIds LIKE ('%' + CAST(CXPF_CXPFacturaId AS varchar(max)) + '%')
		  )
		  AND (
			  @monedaId IS NULL
			  OR @monedaId = CXPF_MON_MonedaId
		  )
		  AND (
			  @mostrarVencidos = 1
			  OR DATEDIFF(day, DATEADD(day, CXPF_DiasCredito, CXPF_FechaRegistro), GETDATE()) <= 0
		  )
		  AND (
			  @mostrarPorVencer = 1
			  OR DATEDIFF(day, DATEADD(day, CXPF_DiasCredito, CXPF_FechaRegistro), GETDATE()) > 0
		  )
		  AND CXPF_CMM_EstatusId != 2000114
	GROUP BY CXPF_CXPFacturaId, 
			 PRO_ProveedorId, 
			 PRO_Codigo, 
			 PRO_Nombre, 
			 CXPF_CodigoRegistro, 
			 CXPF_FechaRegistro, 
			 CXPF_MontoRegistro,
			 CXPF_DiasCredito
	HAVING (CXPF_MontoRegistro - COALESCE(SUM(CXPPD_MontoAplicado), 0)) > 0;
RETURN;
END
GO