SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ===================================================
-- Author:		Javier Elías
-- Create date: 28/01/2022
-- Modified date: 
-- Description:	Procesador para insertar una Factura Global
-- ===================================================
CREATE OR ALTER PROCEDURE [dbo].[sp_insertarFacturaGlobalNotaVenta]
	@sucursalId INT,
	@ordenesVentaId VARCHAR(MAX),
	@usuarioId INT,
	@id INT OUTPUT
AS
BEGIN
		SET NOCOUNT ON;

		-- Obtenemos los Ids de las Ordenes de Venta
		DECLARE @ordenesVenta TABLE ( Id INT NOT NULL )
		INSERT INTO @ordenesVenta
		SELECT OV_OrdenVentaId FROM OrdenesVenta AS ov
		WHERE @ordenesVentaId LIKE '%'+'|'+CONVERT(VARCHAR(MAX), ov.OV_OrdenVentaId)+'|'+'%'

		-- Insertamos los datos de la cabecera
		INSERT INTO CXCFacturas
		(
			--CXCF_FacturaId - this column value is auto-generated
			CXCF_Version,
			CXCF_Fecha,
			CXCF_Folio,
			CXCF_FP_FormaPagoId,
			CXCF_DiasCredito,
			CXCF_CondicionesPago,
			CXCF_MON_MonedaId,
			CXCF_TipoCambio,
			CXCF_CMM_MetodoPagoId,
			CXCF_EmisorCP,
			CXCF_EmisorRFC,
			CXCF_EmisorRazonSocial,
			CXCF_CMM_RegimenFiscalId,
			CXCF_ReceptorRFC,
			CXCF_ReceptorNombre,
			CXCF_CMM_UsoCFDIId,
			CXCF_CMM_TipoRegistroId,
			CXCF_SUC_SucursalId,
			CXCF_CMM_EstatusId,
			CXCF_FechaCreacion,
			CXCF_USU_CreadoPorId
		)
		SELECT TOP 1 '3.3' AS Version,
				GETDATE() AS Fecha,
				folio.Autonumerico AS Folio,
				FP_FormaPagoId AS FormaPagoId,
				0 AS DiasCredito,
				NULL AS CondicionesPago,
				MON_MonedaId AS MonedaId,
				ISNULL(MONP_TipoCambioOficial, 1) AS TipoCambio,
				83 AS MetodoPagoId, --PUE - Pago en una sola exhibición
				emisorCP.CMA_Valor AS EmisorCP,
				emisorRFC.CMA_Valor AS EmisorRFC,
				emisorRazonSocial.CMA_Valor AS EmisorRazonSocial,
				regimenFiscalId.CMM_ControlId AS EmisorRegimenFiscalId,
				'XAXX010101000' AS ReceptorRFC,
				NULL AS ReceptorNombre,
				106 AS ReceptorUsoCFDIId, -- P01 - Por definir
				2000474 AS TipoRegistroId, -- CMM_CXCF_TipoRegistro - Factura Global
				@sucursalId AS SucursalId,
				2000491 AS EstatusId, -- CMM_CXCF_EstatusFactur - Abierta
				GETDATE() AS FechaCreacion,
				@usuarioId AS CreadoPorId
		FROM OrdenesVenta AS ov
				INNER JOIN @ordenesVenta ON ov.OV_OrdenVentaId = Id
				INNER JOIN Monedas ON OV_MON_MonedaId = MON_MonedaId
				INNER JOIN MediosPagoPV ON OV_MPPV_MedioPagoPVId = MPPV_MedioPagoPVId
				INNER JOIN FormasPago ON MPPV_Codigo = FP_Codigo
				INNER JOIN ControlesMaestros AS emisorCP ON emisorCP.CMA_Nombre = 'CM_EMPRESA_CP'
				INNER JOIN ControlesMaestros AS emisorRFC ON emisorRFC.CMA_Nombre = 'CM_RFC_EMPRESA'
				INNER JOIN ControlesMaestros AS emisorRazonSocial ON emisorRazonSocial.CMA_Nombre = 'CM_RAZON_SOCIAL'
				INNER JOIN ControlesMaestros AS emisorRegimenFiscal ON emisorRegimenFiscal.CMA_Nombre = 'CM_REGIMEN_FISCAL'
				INNER JOIN ControlesMaestrosMultiples AS regimenFiscalId ON emisorRegimenFiscal.CMA_Valor = regimenFiscalId.CMM_Referencia
				CROSS APPLY
				(
					SELECT AUT_Prefijo+RIGHT('0000000000'+CONVERT(VARCHAR(10), AUT_Siguiente), AUT_Digitos) AS Autonumerico
					FROM Autonumericos
							INNER JOIN Sucursales ON AUT_ReferenciaId = SUC_SucursalId AND AUT_Nombre = 'CXCFactura '+SUC_Nombre
					WHERE Sucursales.SUC_SucursalId = @sucursalId
				) AS folio
				OUTER APPLY
				(
					SELECT TOP 1 MONP_TipoCambioOficial FROM MonedasParidad WHERE MONP_MON_MonedaId = MON_MonedaId AND MONP_Fecha <= CONVERT(DATE, ISNULL(OV_FechaModificacion, OV_FechaCreacion)) ORDER BY MONP_Fecha DESC
				) AS tipoCambio
				CROSS APPLY dbo.fn_getDatosFacturacionOV(OV_SUC_SucursalId, OV_Codigo) AS maxTotal
		ORDER BY maxTotal.Total DESC

		--Recuperamos el Id de la Factura que se generó
		SET @id = (SELECT SCOPE_IDENTITY())

		-- Insertamos los detalles
		INSERT INTO CXCFacturasDetalles
				SELECT @id AS FacturaId,
				'01010101' AS ClaveProdServ,
				OV_Codigo AS NoIdentificacion,
				'Venta' AS Descripcion,
				(SELECT TOP 1 UM_UnidadMedidaId FROM UnidadesMedidas WHERE UM_ClaveSAT = 'ACT' AND UM_Activo = 1) AS UnidadMedidaId, -- ACT - Actividad
				1 AS Cantidad,
				Subtotal AS ValorUnitario,
				Subtotal AS Importe,
				Descuento,
				OV_OrdenVentaId AS ReferenciaId
		FROM OrdenesVenta AS ov
				INNER JOIN @ordenesVenta ON ov.OV_OrdenVentaId = Id
				CROSS APPLY fn_getDatosFacturacionOV(ov.OV_SUC_SucursalId, ov.OV_Codigo) AS datosOV
		ORDER BY NoIdentificacion,
				Descripcion,
				Cantidad

		-- Insertamos los impuestos para cada detalle
		INSERT INTO CXCFacturasDetallesImpuestos
		SELECT CXCFD_FacturaDetalleId AS DetalleId,
				impuestos.Clave,
				impuestos.Nombre,
				impuestos.TipoFactor,
				impuestos.Base,
				impuestos.TasaOCuota,
				impuestos.Importe
		FROM CXCFacturasDetalles
			CROSS APPLY fn_getDatosFacturacionGlobalImpuestos(CXCFD_ReferenciaId) AS impuestos
		WHERE CXCFD_CXCF_FacturaId = @id

		-- Actualizamos el Autonumerico de la sucursal
		UPDATE aut SET AUT_Siguiente = AUT_Siguiente + 1
		FROM Autonumericos AS aut
				INNER JOIN Sucursales ON aut.AUT_ReferenciaId = SUC_SucursalId AND aut.AUT_Nombre = 'CXCFactura '+SUC_Nombre
		WHERE SUC_SucursalId = @sucursalId

		-- Actualizamos el estatus de las OV
		UPDATE ov SET OV_CMM_EstatusId = 2000507, -- Facturada
								 OV_USU_ModificadoPorId = @usuarioId,
								 OV_CXCF_FacturaId = @id
		FROM OrdenesVenta AS ov
		INNER JOIN @ordenesVenta ON ov.OV_OrdenVentaId = Id
END
GO