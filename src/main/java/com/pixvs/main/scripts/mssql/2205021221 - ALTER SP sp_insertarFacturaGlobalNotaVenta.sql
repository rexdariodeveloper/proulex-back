SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ===================================================
-- Author:		Javier Elías
-- Create date: 28/01/2022
-- Modified date: 28/04/2022
-- Description:	Procesador para insertar una Factura Global
-- ===================================================
CREATE OR ALTER PROCEDURE [dbo].[sp_insertarFacturaGlobalNotaVenta]
	@sucursalId INT,
	@periodicidadId INT,
	@mesId INT,
	@anio SMALLINT,
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
		
		BEGIN TRY
			BEGIN TRANSACTION

				-- Validamos la Periodicidad para el Régimen Fiscal
				DECLARE @periodicidadCodigo VARCHAR(5) = (SELECT PER_Codigo FROM SATPeriodicidad WHERE PER_PeriodicidadId = @periodicidadId AND PER_Activo = 1)

				-- Verificamos que los Parámetros Empresa
				DECLARE @emisorCP VARCHAR(4000)=
				(
					SELECT CMA_Valor
					FROM ControlesMaestros
					WHERE CMA_Nombre = 'CM_EMPRESA_CP'
				)

				IF(@emisorCP IS NULL)
					THROW 50000, 'No es posible insertar la factura. No se ha configurado el CP del Emisor', 1;

				DECLARE @emisorRFC VARCHAR(4000)=
				(
					SELECT CMA_Valor
					FROM ControlesMaestros
					WHERE CMA_Nombre = 'CM_RFC_EMPRESA'
				)

				IF(@emisorRFC IS NULL)
					THROW 50000, 'No es posible insertar la factura. No se ha configurado el RFC del Emisor', 1;

				DECLARE @emisorRazonSocial VARCHAR(4000)=
				(
					SELECT CMA_Valor
					FROM ControlesMaestros
					WHERE CMA_Nombre = 'CM_RAZON_SOCIAL'
				)

				IF(@emisorRazonSocial IS NULL)
					THROW 50000, 'No es posible insertar la factura. No se ha configurado la Razón Social del Emisor', 1;

				DECLARE @emisorRegimenFiscalId VARCHAR(4000)=
				(
					SELECT RF_RegimenFiscalId
					FROM ControlesMaestros AS emisorRegimenFiscal
						 INNER JOIN SATRegimenesFiscales AS emisorRegimenFiscalId ON (CASE WHEN @periodicidadCodigo = '05' THEN '621' ELSE emisorRegimenFiscal.CMA_Valor END) = emisorRegimenFiscalId.RF_Codigo AND RF_Activo = 1 -- Cuando el valor del campo Periodicidad sea "05 - Bimestral" el campo RegimenFiscal debe ser "621 - Incorporación Fiscal".
					WHERE emisorRegimenFiscal.CMA_Nombre = 'CM_REGIMEN_FISCAL'
				)

				IF(@emisorRegimenFiscalId IS NULL)
					THROW 50000, 'No es posible insertar la factura. No se ha configurado el Régimen Fiscal del Emisor', 1;

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
				SELECT TOP 1 '4.0' AS Version,
						GETDATE() AS Fecha,
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
						(SELECT RF_RegimenFiscalId FROM SATRegimenesFiscales WHERE RF_Codigo = '616' AND RF_Activo = 1) AS ReceptorRegimenFiscalId, -- 616 - Sin obligaciones fiscales o 
						(SELECT UCFDI_UsoCFDIId FROM SATUsosCFDI WHERE UCFDI_Codigo = 'S01' AND UCFDI_Activo = 1) AS ReceptorUsoCFDIId, -- S01 - Sin efectos fiscales.
						2000474 AS TipoRegistroId, -- CMM_CXCF_TipoRegistro - Factura Global
						@sucursalId AS SucursalId,
						@periodicidadId AS PeriodicidadId,
						@mesId AS MesId,
						@anio AS Anio,
						2000491 AS EstatusId, -- CMM_CXCF_EstatusFactur - Abierta
						GETDATE() AS FechaCreacion,
						@usuarioId AS CreadoPorId
				FROM OrdenesVenta AS ov
						INNER JOIN @ordenesVenta ON ov.OV_OrdenVentaId = Id
						INNER JOIN Monedas ON OV_MON_MonedaId = MON_MonedaId
						INNER JOIN MediosPagoPV ON OV_MPPV_MedioPagoPVId = MPPV_MedioPagoPVId
						INNER JOIN FormasPago ON MPPV_Codigo = FP_Codigo
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
						(SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_SAT_ObjetoImp' AND CMM_Referencia = '02' AND CMM_Activo = 1) AS ObjetoImpuestoId, -- 02 - Sí objeto de impuesto.
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