CREATE OR ALTER VIEW [dbo].[VW_Listado_CXCPagosFacturas]
AS
	SELECT CONVERT(BIT, 0) AS Seleccionado,
		   CXCF_FacturaId AS FacturaId,
		   SUC_SucursalId AS SedeId,
		   SUC_Nombre AS Sede,
		   CXCF_Serie + ' ' + CXCF_Folio AS Folio,
		   CXCF_Fecha AS Fecha,
		   NoParcialidadSiguiente AS NoParcialidad,
		   saldo.Monto AS Monto,
		   saldo.Saldo AS Saldo,
		   NULL AS MontoPago,
		   CMM_Referencia AS MetodoPago,
		   ISNULL(1000000 + ALU_AlumnoId, 2000000 + CLI_ClienteId) AS AlumnoClienteId,
		   CXCF_DF_DatosFacturacionId AS DatosFacturacionId
	FROM CXCFacturas
		 INNER JOIN dbo.fn_getSaldoPagosFacturacion() AS saldo ON CXCF_FacturaId = saldo.Facturaid AND saldo.Saldo > 0
		 INNER JOIN Sucursales ON CXCF_SUC_SucursalId = SUC_SucursalId
		 INNER JOIN ControlesMaestrosMultiples ON CXCF_CMM_MetodoPagoId = CMM_ControlId
		 INNER JOIN DatosFacturacion ON CXCF_DF_DatosFacturacionId = DF_DatosFacturacionId
		 LEFT JOIN AlumnosDatosFacturacion ON DF_DatosFacturacionId = ADF_DF_DatosFacturacionId
		 LEFT JOIN Alumnos ON ADF_ALU_AlumnoId = ALU_AlumnoId
		 LEFT JOIN ClientesDatosFacturacion ON DF_DatosFacturacionId = CDF_DF_DatosFacturacionId
		 LEFT JOIN Clientes ON CDF_CLI_ClienteId = CLI_ClienteId
	WHERE CXCF_CMM_EstatusId IN(2000491, 2000494) -- Abierta o Pago Parcial
GO