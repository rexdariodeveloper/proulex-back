SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ======================================================
-- Author:		Javier Elías
-- Create date: 06/10/2022
-- Description:	Vista para obtener el reporte de Pago de Clientes
-- ======================================================
CREATE OR ALTER VIEW [dbo].[VW_ReporteCXCPagos]
AS
	SELECT CXCP_PagoId AS id,
		   CXCF_FacturaId AS facturaId,
		   CXCF_UUID AS uuid,
		   CXCF_FechaCreacion AS fechaRegistro,
		   SUC_SucursalId AS sedeId,
		   SUC_Nombre AS sede,
		   ISNULL(1000000  +  ALU_AlumnoId, 2000000  +  CLI_ClienteId) AS alumnoClienteId,
		   ISNULL(ALU_Codigo, CLI_Codigo) + ' - ' + ISNULL(ALU_Nombre + ISNULL(' ' + ALU_PrimerApellido, '') + ISNULL(' ' + ALU_SegundoApellido, ''), CLI_RazonSocial) AS cliente,
		   ISNULL(CXCF_Serie + ' ', '') + CXCF_Folio AS numeroOperacion,
		   MON_MonedaId AS monedaId,
		   MON_Nombre AS moneda,
		   importePagado AS monto,
		   CXCP_TipoCambio AS tipoCambio,
		   CXCP_Fecha AS fechaPago,
		   FP_FormaPagoId AS formaPagoId,
		   FP_Codigo + ' - ' + FP_Nombre AS formaPago,
		   BAC_CuentaId AS cuentaId,
		   BAC_Codigo + ' - ' + BAC_Descripcion AS cuenta
	FROM CXCFacturas
		 INNER JOIN CXCPagos ON CXCF_FacturaId = CXCP_CXCF_FacturaId
		 INNER JOIN Sucursales ON CXCF_SUC_SucursalId = SUC_SucursalId
		 INNER JOIN DatosFacturacion ON CXCF_DF_DatosFacturacionId = DF_DatosFacturacionId
		 LEFT JOIN AlumnosDatosFacturacion ON DF_DatosFacturacionId = ADF_DF_DatosFacturacionId
		 LEFT JOIN Alumnos ON ADF_ALU_AlumnoId = ALU_AlumnoId
		 LEFT JOIN ClientesDatosFacturacion ON DF_DatosFacturacionId = CDF_DF_DatosFacturacionId
		 LEFT JOIN Clientes ON CDF_CLI_ClienteId = CLI_ClienteId
		 INNER JOIN Monedas ON CXCP_MON_MonedaId = MON_MonedaId
		 INNER JOIN
		 (
				SELECT CXCPD_CXCP_PagoId AS pagoId, SUM(CXCPD_ImportePagado) AS importePagado FROM CXCPagosDetalles GROUP BY CXCPD_CXCP_PagoId
		 ) AS detalles ON CXCP_PagoId = pagoId
		 INNER JOIN FormasPago ON CXCP_FP_FormaPagoId = FP_FormaPagoId
		 LEFT JOIN BancosCuentas ON CXCP_BAC_CuentaBeneficiarioId = BAC_CuentaId
	WHERE CXCF_CMM_EstatusId = 2000495 -- Pagada
GO