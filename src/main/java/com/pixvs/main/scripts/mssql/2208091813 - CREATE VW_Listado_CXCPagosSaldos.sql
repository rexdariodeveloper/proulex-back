CREATE OR ALTER VIEW [dbo].[VW_Listado_CXCPagosSaldos]
AS
	SELECT ISNULL(1000000 + ALU_AlumnoId, 2000000 + CLI_ClienteId) AS AlumnoClienteId,
		   ISNULL(ALU_Codigo, CLI_Codigo) AS Codigo,
		   ISNULL(ALU_Nombre + ISNULL(' ' + ALU_PrimerApellido, '') + ISNULL(' ' + ALU_SegundoApellido, ''), CLI_RazonSocial) AS Nombre,
		   COUNT(FacturaId) AS NoFacturas,
		   SUM(Saldo) AS Saldo
	FROM DatosFacturacion
		 INNER JOIN dbo.fn_getSaldoPagosFacturacion() AS saldo ON DF_DatosFacturacionId = saldo.DatosFacturacionId AND saldo.Saldo > 0
		 LEFT JOIN AlumnosDatosFacturacion ON DF_DatosFacturacionId = ADF_DF_DatosFacturacionId
		 LEFT JOIN Alumnos ON ADF_ALU_AlumnoId = ALU_AlumnoId
		 LEFT JOIN ClientesDatosFacturacion ON DF_DatosFacturacionId = CDF_DF_DatosFacturacionId
		 LEFT JOIN Clientes ON CDF_CLI_ClienteId = CLI_ClienteId
	GROUP BY ISNULL(1000000 + ALU_AlumnoId, 2000000 + CLI_ClienteId),
			 ISNULL(ALU_Codigo, CLI_Codigo),
			 ISNULL(ALU_Nombre + ISNULL(' ' + ALU_PrimerApellido, '') + ISNULL(' ' + ALU_SegundoApellido, ''), CLI_RazonSocial)
GO