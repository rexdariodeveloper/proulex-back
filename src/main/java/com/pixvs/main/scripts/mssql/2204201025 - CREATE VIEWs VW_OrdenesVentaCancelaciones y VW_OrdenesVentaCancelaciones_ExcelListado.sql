/**
* Created by Angel Daniel Hernández Silva on 20/04/2022.
* Object: CREATE VIEWs VW_OrdenesVentaCancelaciones y VW_OrdenesVentaCancelaciones_ExcelListado
*/

/****************************************/
/***** VW_OrdenesVentaCancelaciones *****/
/****************************************/

CREATE OR ALTER VIEW [dbo].[VW_OrdenesVentaCancelaciones] WITH SCHEMABINDING AS
	SELECT
		id,
		codigo,
		fechaCancelacion,
		motivoCancelacionId,
		motivoCancelacion,
		banco,
		beneficiario,
		numeroCuenta,
		clabe,
		telefonoContacto,
		importeReembolsar,
		tipoCancelacionId,
		tipoCancelacion,
		estatusId,
		estatus,

		ordenVentaId,
		ordenVentaCodigo,
		SUM(montoDetalle) AS monto,

		sucursalId,
		sucursalNombre,
		
		fechaCreacion,
		fechaModificacion,
		creadoPorId,
		creadoPorNombreCompleto,
		modificadoPorId,
		modificadoPorNombreCompleto,

		archivos
	FROM(
		SELECT
			OVC_OrdenVentaCancelacionId AS id,
			OVC_Codigo AS codigo,
			OVC_FechaCancelacion AS fechaCancelacion,
			OVC_CMM_MotivoCancelacionId AS motivoCancelacionId,
			MotivoCancelacion.CMM_Valor AS motivoCancelacion,
			OVC_Banco AS banco,
			OVC_Beneficiario AS beneficiario,
			OVC_NumeroCuenta AS numeroCuenta,
			OVC_CLABE AS clabe,
			OVC_TelefonoContacto AS telefonoContacto,
			OVC_ImporteReembolsar AS importeReembolsar,
			OVC_CMM_TipoCancelacionId AS tipoCancelacionId,
			TipoCancelacion.CMM_Valor AS tipoCancelacion,
			OVC_CMM_EstatusId AS estatusId,
			Estatus.CMM_Valor AS estatus,

			OV_OrdenVentaId AS ordenVentaId,
			OV_Codigo AS ordenVentaCodigo,
			(SELECT Total FROM [dbo].[fn_getTotal](OVD_Cantidad,OVD_Precio,OVD_Descuento,CASE WHEN OVD_IVAExento = 1 THEN 0 ELSE OVD_IVA END,OVD_IEPS,OVD_IEPSCuotaFija,NULL)) AS montoDetalle,

			SUC_SucursalId AS sucursalId,
			SUC_Nombre AS sucursalNombre,
		
			OVC_FechaCreacion AS fechaCreacion,
			OVC_FechaModificacion AS fechaModificacion,
			OVC_USU_CreadoPorId AS creadoPorId,
			CONCAT(UsuarioCreador.USU_Nombre,' ' + UsuarioCreador.USU_PrimerApellido,' ' + UsuarioCreador.USU_SegundoApellido) AS creadoPorNombreCompleto,
			OVC_USU_ModificadoPorId AS modificadoPorId,
			CONCAT(UsuarioModificador.USU_Nombre,' ' + UsuarioModificador.USU_PrimerApellido,' ' + UsuarioModificador.USU_SegundoApellido) AS modificadoPorNombreCompleto,
			COUNT(OVCA_OrdenVentaCancelacionArchivoId) AS archivos
		FROM [dbo].[OrdenesVentaCancelaciones]
		INNER JOIN [dbo].[ControlesMaestrosMultiples] AS MotivoCancelacion ON MotivoCancelacion.CMM_ControlId = OVC_CMM_MotivoCancelacionId
		INNER JOIN [dbo].[ControlesMaestrosMultiples] AS TipoCancelacion ON TipoCancelacion.CMM_ControlId = OVC_CMM_TipoCancelacionId
		INNER JOIN [dbo].[ControlesMaestrosMultiples] AS Estatus ON Estatus.CMM_ControlId = OVC_CMM_EstatusId
		INNER JOIN [dbo].[Usuarios] AS UsuarioCreador ON UsuarioCreador.USU_UsuarioId = OVC_USU_CreadoPorId
		LEFT JOIN [dbo].[Usuarios] AS UsuarioModificador ON UsuarioModificador.USU_UsuarioId = OVC_USU_ModificadoPorId
		INNER JOIN [dbo].[OrdenesVentaCancelacionesDetalles] ON OVCD_OVC_OrdenVentaCancelacionId = OVC_OrdenVentaCancelacionId
		INNER JOIN [dbo].[OrdenesVentaDetalles] ON OVD_OrdenVentaDetalleId = OVCD_OVD_OrdenVentaDetalleId
		INNER JOIN [dbo].[OrdenesVenta] ON OV_OrdenVentaId = OVD_OV_OrdenVentaId
		INNER JOIN [dbo].[Sucursales] ON SUC_SucursalId = OV_SUC_SucursalId
		LEFT JOIN [dbo].[OrdenesVentaCancelacionesArchivos] ON OVCA_OVC_OrdenVentaCancelacionId = OVC_OrdenVentaCancelacionId
		GROUP BY
			OVC_OrdenVentaCancelacionId, OVC_Codigo, OVC_FechaCancelacion, OVC_CMM_MotivoCancelacionId, MotivoCancelacion.CMM_Valor, OVC_Banco, OVC_Beneficiario, OVC_NumeroCuenta,
			OVC_CLABE, OVC_TelefonoContacto, OVC_ImporteReembolsar, OVC_CMM_TipoCancelacionId, TipoCancelacion.CMM_Valor, OVC_CMM_EstatusId, Estatus.CMM_Valor, OV_OrdenVentaId,
			OV_Codigo, OVD_Cantidad, OVD_Precio, OVD_Descuento, OVD_IVAExento, OVD_IVA, OVD_IEPS, OVD_IEPSCuotaFija, SUC_SucursalId, SUC_Nombre, OVC_FechaCreacion,
			OVC_FechaModificacion, OVC_USU_CreadoPorId, UsuarioCreador.USU_Nombre, UsuarioCreador.USU_PrimerApellido, UsuarioCreador.USU_SegundoApellido, OVC_USU_ModificadoPorId,
			UsuarioModificador.USU_Nombre, UsuarioModificador.USU_PrimerApellido, UsuarioModificador.USU_SegundoApellido
	) AS MontosDetalles
	GROUP BY
		id, codigo, fechaCancelacion, motivoCancelacionId, motivoCancelacion, banco, beneficiario, numeroCuenta, clabe, telefonoContacto, importeReembolsar, tipoCancelacionId,
		tipoCancelacion, estatusId, estatus, ordenVentaId, ordenVentaCodigo, sucursalId, sucursalNombre, fechaCreacion, fechaModificacion, creadoPorId, creadoPorNombreCompleto,
		modificadoPorId, modificadoPorNombreCompleto, archivos
GO

/*****************************************************/
/***** VW_OrdenesVentaCancelaciones_ExcelListado *****/
/*****************************************************/

CREATE OR ALTER VIEW [dbo].[VW_OrdenesVentaCancelaciones_ExcelListado] WITH SCHEMABINDING AS
	SELECT
		codigo AS "Folio cancelación",
		ordenVentaCodigo AS "Nota de venta",
		sucursalNombre AS "Sede",
		fechaCancelacion AS "Fecha cancelación",
		importeReembolsar AS "Importe a reembolsar",
		creadoPorNombreCompleto AS "Usuario",
		estatus AS "Estatus"
	FROM [dbo].[VW_OrdenesVentaCancelaciones]
GO