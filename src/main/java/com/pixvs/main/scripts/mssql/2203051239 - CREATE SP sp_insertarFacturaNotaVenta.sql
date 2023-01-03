SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ==========================================================
-- Author:		Javier Elías
-- Create date: 25/01/2022
-- Modified date: 
-- Description:	Procesador para insertar una Factura de Nota de Venta
-- ==========================================================
CREATE OR ALTER PROCEDURE [dbo].[sp_insertarFacturaNotaVenta]
	@alumnoContactoId INT,
	@usoCFDIId INT,
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
			CXCF_ALUF_AlumnoContactoId,
			CXCF_SUC_SucursalId,
			CXCF_CMM_EstatusId,
			CXCF_FechaCreacion,
			CXCF_USU_CreadoPorId
		)
		SELECT TOP 1 '3.3' AS Version,
				GETDATE() AS Fecha,
				folio.Autonumerico AS Folio,
				FP_FormaPagoId AS FormaPagoId,
				OV_DiazCredito AS DiasCredito,
				CASE WHEN OV_DiazCredito > 0 THEN CONVERT(VARCHAR(10), OV_DiazCredito) + ' días de crédito' ELSE 'De contado' END AS CondicionesPago,
				MON_MonedaId AS MonedaId,
				ISNULL(MONP_TipoCambioOficial, 1) AS TipoCambio,
				83 AS MetodoPagoId, --PUE - Pago en una sola exhibición
				emisorCP.CMA_Valor AS EmisorCP,
				emisorRFC.CMA_Valor AS EmisorRFC,
				emisorRazonSocial.CMA_Valor AS EmisorRazonSocial,
				regimenFiscalId.CMM_ControlId AS EmisorRegimenFiscalId,
				ALUF_RFC AS ReceptorRFC,
				ISNULL(ALUF_RazonSocial, (ALUF_Nombre + ' ' + ALUF_PrimerApellido + ISNULL(' ' + ALUF_SegundoApellido, ''))) AS ReceptorNombre,
				@usoCFDIId AS ReceptorUsoCFDIId,
				2000473 AS TipoRegistroId, -- CMM_CXCF_TipoRegistro - Factura Nota de Venta
				@alumnoContactoId AS AlumnoContactoId,
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
				INNER JOIN AlumnosFacturacion ON ALUF_AlumnoContactoId = @alumnoContactoId
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
				ISNULL(ART_ClaveProductoSAT, '') AS ClaveProdServ,
				ISNULL(ART_CodigoArticulo, 'S/C') AS NoIdentificacion,
				ART_NombreArticulo AS Descripcion,
				padre.OVD_UM_UnidadMedidaId AS UnidadMedidaId,
				montos.Cantidad AS Cantidad,
				montos.ValorUnitario AS ValorUnitario,
				montos.Subtotal AS Importe,
				montos.Descuento AS Descuento,
				padre.OVD_OrdenVentaDetalleId AS ReferenciaId
		FROM OrdenesVenta AS ov
				INNER JOIN @ordenesVenta ON ov.OV_OrdenVentaId = Id
				INNER JOIN OrdenesVentaDetalles AS padre ON ov.OV_OrdenVentaId = padre.OVD_OV_OrdenVentaId AND padre.OVD_OVD_DetallePadreId IS NULL
				CROSS APPLY dbo.fn_getMontosFacturacionDetalleOV(padre.OVD_OrdenVentaDetalleId) AS montos
				INNER JOIN Articulos ON padre.OVD_ART_ArticuloId = ART_ArticuloId
		ORDER BY NoIdentificacion,
				Descripcion,
				Cantidad

		-- Insertamos los impuestos para cada detalle
		INSERT INTO CXCFacturasDetallesImpuestos
		SELECT CXCFD_FacturaDetalleId AS DetalleId,
			   ClaveIVA AS Clave,
			   NombreIVA AS Nombre,
			   TipoFactorIVA AS TipoFactor,
			   BaseIVA AS Base,
			   TasaOCuotaIVA AS TasaOCuota,
			   ImporteIVA AS Importe
		FROM CXCFacturasDetalles
			 CROSS APPLY dbo.fn_getMontosFacturacionDetalleOV(CXCFD_ReferenciaId) AS montos
		WHERE CXCFD_CXCF_FacturaId = @id

		INSERT INTO CXCFacturasDetallesImpuestos
		SELECT CXCFD_FacturaDetalleId AS DetalleId,
			   ClaveIEPS AS Clave,
			   NombreIEPS AS Nombre,
			   TipoFactorIEPS AS TipoFactor,
			   BaseIEPS AS Base,
			   TasaOCuotaIEPS AS TasaOCuota,
			   ImporteIVA AS Importe
		FROM CXCFacturasDetalles
			 CROSS APPLY dbo.fn_getMontosFacturacionDetalleOV(CXCFD_ReferenciaId) AS montos
		WHERE CXCFD_CXCF_FacturaId = @id
			  AND montos.ClaveIEPS IS NOT NULL 

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