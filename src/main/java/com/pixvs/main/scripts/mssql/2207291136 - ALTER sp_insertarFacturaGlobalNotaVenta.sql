SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ===================================================
-- Author:		Javier Elías
-- Create date: 28/01/2022
-- Modified date: 28/07/2022
-- Description:	Procesador para insertar una Factura Global
-- ===================================================
CREATE OR ALTER PROCEDURE [dbo].[sp_insertarFacturaGlobalNotaVenta]
	@sucursalId INT,
	@periodicidadId INT,
	@mesId INT,
	@anio SMALLINT,
	@ordenesVentaId VARCHAR(MAX),
	@usuarioId INT,
	@version VARCHAR(3),
	@id INT OUTPUT
AS
BEGIN
		SET NOCOUNT ON;

		-- Obtenemos los Ids de las Ordenes de Venta
		DECLARE @ordenesVenta TABLE ( Id INT NOT NULL )
		INSERT INTO @ordenesVenta
		SELECT OV_OrdenVentaId FROM OrdenesVenta AS ov
		WHERE @ordenesVentaId LIKE '%'+'|'+CONVERT(VARCHAR(MAX), ov.OV_OrdenVentaId)+'|'+'%'
		
		BEGIN TRY
			BEGIN TRANSACTION

				-- Variable para mostrar el mensaje de error
				DECLARE @mensaje VARCHAR(4000) = 'No es posible insertar la factura. '

				-- Verificamos que no se hayan modificado las OV
				DECLARE @codigoOV VARCHAR(100)=
				(
					SELECT TOP 1 OV_Codigo
					FROM OrdenesVenta
						 INNER JOIN @ordenesVenta ON OV_OrdenVentaId = Id
					WHERE OV_CMM_EstatusId != 2000508 -- Solo pagadas
				)

				IF(@codigoOV IS NULL)
					SET @mensaje += 'La nota de venta [' + @codigoOV + '] ha sido modificada por otro usuario.'

				-- Validamos la Periodicidad para el Régimen Fiscal
				DECLARE @periodicidadCodigo VARCHAR(5) = (SELECT PER_Codigo FROM SATPeriodicidad WHERE PER_PeriodicidadId = @periodicidadId AND PER_Activo = 1)

				-- Verificamos que los Parámetros Empresa
				DECLARE @emisorCP VARCHAR(4000)=
				(
					SELECT CMA_Valor
					FROM ControlesMaestros
					WHERE CMA_Nombre = 'CM_EMPRESA_CP' AND CMA_Valor != ''
				)

				IF(@emisorCP IS NULL)
					SET @mensaje += 'No se ha configurado el CP del Emisor.'

				DECLARE @emisorRFC VARCHAR(4000)=
				(
					SELECT CMA_Valor
					FROM ControlesMaestros
					WHERE CMA_Nombre = 'CM_RFC_EMPRESA' AND CMA_Valor != ''
				)

				IF(@emisorRFC IS NULL)
					SET @mensaje += 'No se ha configurado el RFC del Emisor.'

				DECLARE @emisorRazonSocial VARCHAR(4000)=
				(
					SELECT CMA_Valor
					FROM ControlesMaestros
					WHERE CMA_Nombre = 'CM_RAZON_SOCIAL' AND CMA_Valor != ''
				)

				IF(@version = '4.0' AND @emisorRazonSocial IS NULL)
					SET @mensaje += 'No se ha configurado la Razón Social del Emisor.'

				DECLARE @emisorRegimenFiscalId VARCHAR(4000)=
				(
					SELECT RF_RegimenFiscalId
					FROM ControlesMaestros AS emisorRegimenFiscal
						 INNER JOIN SATRegimenesFiscales AS emisorRegimenFiscalId ON (CASE WHEN @periodicidadCodigo = '05' THEN '621' ELSE emisorRegimenFiscal.CMA_Valor END) = emisorRegimenFiscalId.RF_Codigo AND RF_Activo = 1 -- Cuando el valor del campo Periodicidad sea "05 - Bimestral" el campo RegimenFiscal debe ser "621 - Incorporación Fiscal".
					WHERE emisorRegimenFiscal.CMA_Nombre = 'CM_REGIMEN_FISCAL'
				)

				IF(@emisorRegimenFiscalId IS NULL)
					SET @mensaje += 'No se ha configurado el Régimen Fiscal del Emisor.'

				IF(@mensaje IS NOT NULL)
					THROW 50000, @mensaje, 1;

				-- Insertamos los datos de la cabecera
				INSERT INTO CXCFacturas
				(
					--CXCF_FacturaId - this column value is auto-generated
					CXCF_Version,
					CXCF_Fecha,
					CXCF_Serie,
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
					CXCF_EmisorRegimenFiscalId,
					CXCF_ReceptorCP,
					CXCF_ReceptorRFC,
					CXCF_ReceptorNombre,
					CXCF_ReceptorRegimenFiscalId,
					CXCF_ReceptorUsoCFDIId,
					CXCF_CMM_TipoRegistroId,
					CXCF_SUC_SucursalId,
					CXCF_PER_PeriodicidadId,
					CXCF_MES_MesId,
					CXCF_Anio,
					CXCF_CMM_EstatusId,
					CXCF_FechaCreacion,
					CXCF_USU_CreadoPorId
				)
				SELECT TOP 1 @version AS Version,
						GETDATE() AS Fecha,
						folio.Serie AS Serie,
						folio.Autonumerico AS Folio,
						FP_FormaPagoId AS FormaPagoId,
						0 AS DiasCredito,
						NULL AS CondicionesPago,
						MON_MonedaId AS MonedaId,
						ISNULL(MONP_TipoCambioOficial, 1) AS TipoCambio,
						83 AS MetodoPagoId, --PUE - Pago en una sola exhibición
						@emisorCP AS EmisorCP,
						@emisorRFC AS EmisorRFC,
						@emisorRazonSocial AS EmisorRazonSocial,
						@emisorRegimenFiscalId AS EmisorRegimenFiscalId,
						NULL AS ReceptorCP,
						'XAXX010101000' AS ReceptorRFC,
						'PUBLICO EN GENERAL' AS ReceptorNombre,
						CASE WHEN @version = '4.0' THEN (SELECT RF_RegimenFiscalId FROM SATRegimenesFiscales WHERE RF_Codigo = '616' AND RF_Activo = 1) ELSE NULL END AS ReceptorRegimenFiscalId, -- 616 - Sin obligaciones fiscales
						(SELECT UCFDI_UsoCFDIId FROM SATUsosCFDI WHERE UCFDI_Codigo = (CASE WHEN @version = '4.0' THEN 'S01' ELSE 'P01' END) AND UCFDI_Activo = 1) AS ReceptorUsoCFDIId, -- S01 - Sin efectos fiscales o P01 - Por definir
						2000474 AS TipoRegistroId, -- CMM_CXCF_TipoRegistro - Factura Global
						@sucursalId AS SucursalId,
						CASE WHEN @version = '4.0' THEN @periodicidadId ELSE NULL END AS PeriodicidadId,
						CASE WHEN @version = '4.0' THEN @mesId ELSE NULL END AS MesId,
						CASE WHEN @version = '4.0' THEN @anio ELSE NULL END AS Anio,
						2000495 AS EstatusId, -- CMM_CXCF_EstatusFactura - Pagada
						GETDATE() AS FechaCreacion,
						@usuarioId AS CreadoPorId
				FROM OrdenesVenta AS ov
						INNER JOIN @ordenesVenta ON ov.OV_OrdenVentaId = Id
						INNER JOIN Monedas ON OV_MON_MonedaId = MON_MonedaId
						INNER JOIN MediosPagoPV ON OV_MPPV_MedioPagoPVId = MPPV_MedioPagoPVId
						INNER JOIN FormasPago ON MPPV_Codigo = FP_Codigo
						CROSS APPLY
						(
							SELECT SUC_Serie AS Serie, AUT_Prefijo+RIGHT('0000000000'+CONVERT(VARCHAR(10), AUT_Siguiente), AUT_Digitos) AS Autonumerico
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

				--Validamos el tipo de ObjetoImp
				DECLARE @ObjetoImpId_001 INT = (SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_SAT_ObjetoImp' AND CMM_Referencia = '01' AND CMM_Activo = 1)
				DECLARE @ObjetoImpId_002 INT = (SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_SAT_ObjetoImp' AND CMM_Referencia = '02' AND CMM_Activo = 1)

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
						CASE WHEN @version = '4.0' THEN CASE WHEN objetoImp.OVId IS NOT NULL THEN @ObjetoImpId_002 ELSE @ObjetoImpId_001 END ELSE NULL END AS ObjetoImpuestoId,
						OV_OrdenVentaId AS ReferenciaId
				FROM OrdenesVenta AS ov
						INNER JOIN @ordenesVenta ON ov.OV_OrdenVentaId = Id
						CROSS APPLY fn_getDatosFacturacionOV(ov.OV_SUC_SucursalId, ov.OV_Codigo) AS datosOV
						LEFT JOIN
						(
								SELECT ov.OV_OrdenVentaId AS OVId
								FROM OrdenesVenta AS ov
									 INNER JOIN @ordenesVenta ON ov.OV_OrdenVentaId = Id
									 INNER JOIN OrdenesVentaDetalles AS detalle ON ov.OV_OrdenVentaId = detalle.OVD_OV_OrdenVentaId AND detalle.OVD_OVD_DetallePadreId IS NULL
									 INNER JOIN Articulos AS articulo ON detalle.OVD_ART_ArticuloId = articulo.ART_ArticuloId
									 LEFT JOIN OrdenesVentaCancelacionesDetalles ON detalle.OVD_OrdenVentaDetalleId = OVCD_OVD_OrdenVentaDetalleId
								WHERE OVCD_OrdenVentaCancelacionDetalleId IS NULL
								GROUP BY ov.OV_OrdenVentaId
								HAVING SUM(CASE WHEN ART_CMM_ObjetoImpuestoId = @ObjetoImpId_002 THEN 1 ELSE 0 END) > 0
						) AS objetoImp ON ov.OV_OrdenVentaId = objetoImp.OVId
				ORDER BY NoIdentificacion,
						Descripcion,
						Cantidad

				-- Insertamos los impuestos para cada detalle
				INSERT INTO CXCFacturasDetallesImpuestos
				SELECT CXCFD_FacturaDetalleId AS DetalleId,
						impuestos.Clave,
						impuestos.Nombre,
						impuestos.TipoFactor,
						SUM(impuestos.Base),
						impuestos.TasaOCuota,
						SUM(impuestos.Importe)
				FROM CXCFacturasDetalles
					CROSS APPLY fn_getDatosFacturacionGlobalImpuestos(CXCFD_ReferenciaId) AS impuestos
				WHERE CXCFD_CXCF_FacturaId = @id
				GROUP BY CXCFD_FacturaDetalleId,
						 impuestos.Clave,
						 impuestos.Nombre,
						 impuestos.TipoFactor,
						 impuestos.TasaOCuota

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

		COMMIT 

		END TRY
		BEGIN CATCH
			ROLLBACK;
			THROW;
		END CATCH
END