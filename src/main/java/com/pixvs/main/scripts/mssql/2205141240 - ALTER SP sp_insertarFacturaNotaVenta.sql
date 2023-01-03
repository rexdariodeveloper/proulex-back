SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ==========================================================
-- Author:		Javier Elías
-- Create date: 25/01/2022
-- Modified date: 14/05/2022
-- Description:	Procesador para insertar una Factura de Nota de Venta
-- ==========================================================
CREATE OR ALTER PROCEDURE [dbo].[sp_insertarFacturaNotaVenta]
	@clienteFacturacionId INT,
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

		BEGIN TRY
			BEGIN TRANSACTION

				-- Variable para mostrar el mensaje de error
				DECLARE @mensaje VARCHAR(4000)

				-- Verificamos que los Parámetros Empresa
				DECLARE @emisorCP VARCHAR(4000)=
				(
					SELECT CMA_Valor
					FROM ControlesMaestros
					WHERE CMA_Nombre = 'CM_EMPRESA_CP' AND CMA_Valor != ''
				)

				IF(@emisorCP IS NULL)
					SET @mensaje = 'No es posible insertar la factura. No se ha configurado el CP del Emisor.'

				DECLARE @emisorRFC VARCHAR(4000)=
				(
					SELECT CMA_Valor
					FROM ControlesMaestros
					WHERE CMA_Nombre = 'CM_RFC_EMPRESA' AND CMA_Valor != ''
				)

				IF(@emisorRFC IS NULL)
					SET @mensaje = 'No es posible insertar la factura. No se ha configurado el RFC del Emisor.'

				DECLARE @emisorRazonSocial VARCHAR(4000)=
				(
					SELECT CMA_Valor
					FROM ControlesMaestros
					WHERE CMA_Nombre = 'CM_RAZON_SOCIAL' AND CMA_Valor != ''
				)

				IF(@emisorRazonSocial IS NULL)
					SET @mensaje = 'No es posible insertar la factura. No se ha configurado la Razón Social del Emisor.'

				DECLARE @emisorRegimenFiscalId VARCHAR(4000)=
				(
					SELECT RF_RegimenFiscalId
					FROM ControlesMaestros AS emisorRegimenFiscal
						 INNER JOIN SATRegimenesFiscales AS emisorRegimenFiscalId ON emisorRegimenFiscal.CMA_Valor = emisorRegimenFiscalId.RF_Codigo AND RF_Activo = 1
					WHERE emisorRegimenFiscal.CMA_Nombre = 'CM_REGIMEN_FISCAL'
				)

				IF(@emisorRegimenFiscalId IS NULL)
					SET @mensaje = 'No es posible insertar la factura. No se ha configurado el Régimen Fiscal del Emisorr.'

				IF(@mensaje IS NOT NULL)
					THROW 50000, @mensaje, 1;

				-- Verificamos que los Parámetros del Receptor
				DECLARE @receptorCP VARCHAR(4000)
				DECLARE @receptorRFC VARCHAR(4000)
				DECLARE @receptorNombre VARCHAR (4000)
				DECLARE @receptorRegimenFiscalId INT

				IF (@alumnoContactoId IS NOT NULL)
				BEGIN
						SELECT @receptorCP = ALUF_CP,
							   @receptorRFC = ALUF_RFC,
							   @receptorNombre = ISNULL(ALUF_RazonSocial, (ALUF_Nombre + ' ' + ALUF_PrimerApellido + ISNULL(' ' + ALUF_SegundoApellido, ''))),
							   @receptorRegimenFiscalId = ALUF_RF_RegimenFiscalId
						FROM AlumnosFacturacion
						WHERE ALUF_AlumnoContactoId = @alumnoContactoId
				END

				ELSE IF (@clienteFacturacionId IS NOT NULL)
				BEGIN
						SELECT @receptorCP = CLIF_CP,
							   @receptorRFC = CLIF_RFC,
							   @receptorNombre = ISNULL(CLIF_RazonSocial, (CLIF_Nombre + ' ' + CLIF_PrimerApellido + ISNULL(' ' + CLIF_SegundoApellido, ''))),
							   @receptorRegimenFiscalId = CLIF_RF_RegimenFiscalId
						FROM ClientesFacturacion
						WHERE CLIF_ClienteFacturacionId = @clienteFacturacionId
				END

				IF (@receptorCP IS NULL OR @receptorCP = '')
					SET @mensaje = 'No es posible insertar la factura. No se ha configurado el Régimen Fiscal del Receptor.'

				IF (@receptorRFC IS NULL OR @receptorRFC = '')
					SET @mensaje = 'No es posible insertar la factura. No se ha configurado el RFC del Receptor.'

				IF (@receptorNombre IS NULL OR @receptorNombre = '')
					SET @mensaje = 'No es posible insertar la factura. No se ha configurado el Nombre del Receptor.'
					
				IF (@receptorRegimenFiscalId IS NULL)
					SET @mensaje = 'No es posible insertar la factura. No se ha configurado el Régimen Fiscal del Receptor.'						

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
					CXCF_CLIF_ClienteFacturacionId,
					CXCF_ALUF_AlumnoContactoId,
					CXCF_SUC_SucursalId,
					CXCF_CMM_EstatusId,
					CXCF_FechaCreacion,
					CXCF_USU_CreadoPorId
				)
				SELECT TOP 1 '4.0' AS Version,
						GETDATE() AS Fecha,
						folio.Serie AS Serie,
						folio.Autonumerico AS Folio,
						FP_FormaPagoId AS FormaPagoId,
						OV_DiazCredito AS DiasCredito,
						CASE WHEN OV_DiazCredito > 0 THEN CONVERT(VARCHAR(10), OV_DiazCredito) + ' días de crédito' ELSE 'De contado' END AS CondicionesPago,
						MON_MonedaId AS MonedaId,
						ISNULL(MONP_TipoCambioOficial, 1) AS TipoCambio,
						83 AS MetodoPagoId, --PUE - Pago en una sola exhibición
						@emisorCP AS EmisorCP,
						@emisorRFC AS EmisorRFC,
						@emisorRazonSocial AS EmisorRazonSocial,
						@emisorRegimenFiscalId AS EmisorRegimenFiscalId,
						@receptorCP AS ReceptorCP,
						@receptorRFC AS ReceptorRFC,
						@receptorNombre AS ReceptorNombre,
						@receptorRegimenFiscalId AS ReceptorRegimenFiscalId,
						@usoCFDIId AS ReceptorUsoCFDIId,
						2000473 AS TipoRegistroId, -- CMM_CXCF_TipoRegistro - Factura Nota de Venta
						@clienteFacturacionId AS ClienteFacturacionId,
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

				-- Recuperamos el Id de la Factura que se generó
				SET @id = (SELECT SCOPE_IDENTITY())

				-- Validamos los datos de cadad detalle
				DECLARE @articulo VARCHAR(4000)
				DECLARE @claveProducto VARCHAR(4000)
				DECLARE @objetoImpuestoId INT
				DECLARE @um VARCHAR(4000)
				DECLARE @claveUM VARCHAR(4000)

				SELECT TOP 1 @articulo = ART_NombreArticulo,
							 @claveProducto = ART_ClaveProductoSAT,
							 @objetoImpuestoId = ART_CMM_ObjetoImpuestoId,
							 @um = UM_Nombre,
							 @claveUM = UM_ClaveSAT
				FROM OrdenesVenta AS ov
					 INNER JOIN @ordenesVenta ON ov.OV_OrdenVentaId = Id
					 INNER JOIN OrdenesVentaDetalles AS padre ON ov.OV_OrdenVentaId = padre.OVD_OV_OrdenVentaId AND padre.OVD_OVD_DetallePadreId IS NULL
					 INNER JOIN Articulos ON padre.OVD_ART_ArticuloId = ART_ArticuloId
					 INNER JOIN UnidadesMedidas AS um ON ART_UM_UnidadMedidaInventarioId = UM_UnidadMedidaId
				WHERE ART_ClaveProductoSAT IS NULL
					  OR ART_ClaveProductoSAT = ''
					  OR ART_CMM_ObjetoImpuestoId IS NULL
					  OR UM_ClaveSAT IS NULL
					  OR UM_ClaveSAT = ''

				IF(@claveProducto IS NULL OR @claveProducto = '')
					SET @mensaje = 'No es posible insertar la factura. El artículo [' + @articulo + '] no tiene Clave de Producto.'

				IF(@objetoImpuestoId IS NULL)
					SET @mensaje = 'No es posible insertar la factura. No se ha configurado el Objeto de Impuesto para el artículo [' + @articulo + '].'

				IF(@claveUM IS NULL OR @claveUM = '')
					SET @mensaje = 'No es posible insertar la factura. No se ha configurado la clave para la UM [' + @um + '].'

				IF (@mensaje IS NOT NULL)
					THROW 50000, @mensaje, 1;
					
				-- Insertamos los detalles
				INSERT INTO CXCFacturasDetalles
						SELECT @id AS FacturaId,
						ART_ClaveProductoSAT AS ClaveProdServ,
						ISNULL(ART_CodigoArticulo, 'S/C') AS NoIdentificacion,
						ART_NombreArticulo AS Descripcion,
						padre.OVD_UM_UnidadMedidaId AS UnidadMedidaId,
						montos.Cantidad AS Cantidad,
						montos.ValorUnitario AS ValorUnitario,
						montos.Subtotal AS Importe,
						CASE WHEN montos.Subtotal - montos.Descuento = 0 THEN montos.Subtotal - 0.01 ELSE montos.Descuento END AS Descuento,
						ART_CMM_ObjetoImpuestoId AS ObjetoImpuestoId,
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
					   SUM(BaseIVA) AS Base,
					   TasaOCuotaIVA AS TasaOCuota,
					   SUM(ImporteIVA) AS Importe
				FROM CXCFacturasDetalles
					 CROSS APPLY dbo.fn_getMontosFacturacionDetalleOV(CXCFD_ReferenciaId) AS montos
				WHERE CXCFD_CXCF_FacturaId = @id
				GROUP BY CXCFD_FacturaDetalleId,
						 ClaveIVA,
						 NombreIVA,
						 TipoFactorIVA,
						 TasaOCuotaIVA

				INSERT INTO CXCFacturasDetallesImpuestos
				SELECT CXCFD_FacturaDetalleId AS DetalleId,
					   ClaveIEPS AS Clave,
					   NombreIEPS AS Nombre,
					   TipoFactorIEPS AS TipoFactor,
					   SUM(BaseIEPS) AS Base,
					   TasaOCuotaIEPS AS TasaOCuota,
					   SUM(ImporteIEPS) AS Importe
				FROM CXCFacturasDetalles
					 CROSS APPLY dbo.fn_getMontosFacturacionDetalleOV(CXCFD_ReferenciaId) AS montos
				WHERE CXCFD_CXCF_FacturaId = @id
					  AND montos.ClaveIEPS IS NOT NULL
				GROUP BY CXCFD_FacturaDetalleId,
						 ClaveIEPS,
						 NombreIEPS,
						 TipoFactorIEPS,
						 TasaOCuotaIEPS

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