SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ========================================================
-- Author:		Javier Elías
-- Create date: 06/08/2022
-- Modified date: 
-- Description:	Procesador para insertar una Complemento de Pago
-- ========================================================
CREATE OR ALTER PROCEDURE [dbo].[sp_insertarComplementoPago]
	@datosFacturacionId INT,
	@sucursalId INT,
	@doctosRelacionadosId VARCHAR(MAX),
	@usuarioId INT,
	@version VARCHAR(3),
	@id INT OUTPUT
AS
BEGIN
		SET NOCOUNT ON;

		BEGIN TRY
			BEGIN TRANSACTION

				-- Variable para mostrar el mensaje de error
				DECLARE @tituloMensaje VARCHAR(4000) = 'No es posible insertar el complemento de pago. '
				DECLARE @mensaje VARCHAR(4000)

				-- Verificamos que no se hayan modificado las Facturas a pagar
				DECLARE @folioFacturaIntegridad VARCHAR(4000)=
				(
					SELECT TOP 1 ISNULL(CXCF_Serie + ' ', '') + CXCF_Folio
					FROM CXCFacturas AS factura
					WHERE @doctosRelacionadosId LIKE '%'+'|'+CONVERT(VARCHAR(MAX), factura.CXCF_FacturaId)+'|'+'%'
						  AND ISNULL(CXCF_FechaModificacion, '19000101') > GETDATE()
				)

				IF(@folioFacturaIntegridad IS NOT NULL)
					SET @mensaje = @tituloMensaje + 'La factura [' + @folioFacturaIntegridad + '] ha sido modificada por otro usuario.'

				IF(@mensaje IS NOT NULL)
					THROW 50000, @mensaje, 1;

				-- Verificamos que los Parámetros Empresa
				DECLARE @emisorCP VARCHAR(4000)=
				(
					SELECT CMA_Valor
					FROM ControlesMaestros
					WHERE CMA_Nombre = 'CM_EMPRESA_CP' AND CMA_Valor != ''
				)

				IF(@emisorCP IS NULL)
					SET @mensaje = @tituloMensaje + 'No se ha configurado el CP del Emisor.'

				DECLARE @emisorRFC VARCHAR(4000)=
				(
					SELECT CMA_Valor
					FROM ControlesMaestros
					WHERE CMA_Nombre = 'CM_RFC_EMPRESA' AND CMA_Valor != ''
				)

				IF(@emisorRFC IS NULL)
					SET @mensaje = @tituloMensaje + 'No se ha configurado el RFC del Emisor.'

				DECLARE @emisorRazonSocial VARCHAR(4000)=
				(
					SELECT CMA_Valor
					FROM ControlesMaestros
					WHERE CMA_Nombre = 'CM_RAZON_SOCIAL' AND CMA_Valor != ''
				)

				IF(@version = '4.0' AND @emisorRazonSocial IS NULL)
					SET @mensaje = @tituloMensaje + 'No se ha configurado la Razón Social del Emisor.'

				DECLARE @emisorRegimenFiscalId VARCHAR(4000)=
				(
					SELECT RF_RegimenFiscalId
					FROM ControlesMaestros AS emisorRegimenFiscal
						 INNER JOIN SATRegimenesFiscales AS emisorRegimenFiscalId ON emisorRegimenFiscal.CMA_Valor = emisorRegimenFiscalId.RF_Codigo AND RF_Activo = 1
					WHERE emisorRegimenFiscal.CMA_Nombre = 'CM_REGIMEN_FISCAL'
				)

				IF(@emisorRegimenFiscalId IS NULL)
					SET @mensaje = @tituloMensaje + 'No se ha configurado el Régimen Fiscal del Emisor.'

				IF(@mensaje IS NOT NULL)
					THROW 50000, @mensaje, 1;

				-- Verificamos que los Parámetros del Receptor
				DECLARE @receptorCP VARCHAR(4000)
				DECLARE @receptorRFC VARCHAR(4000)
				DECLARE @receptorNombre VARCHAR (4000)
				DECLARE @receptorRegimenFiscalId INT

				IF (@datosFacturacionId IS NOT NULL)
				BEGIN
						SELECT @receptorCP = DF_CP,
							   @receptorRFC = DF_RFC,
							   @receptorNombre = ISNULL(DF_RazonSocial, (DF_Nombre + ISNULL(' ' + DF_PrimerApellido, '') + ISNULL(' ' + DF_SegundoApellido, ''))),
							   @receptorRegimenFiscalId = DF_RF_RegimenFiscalId
						FROM DatosFacturacion 
						WHERE DF_DatosFacturacionId = @datosFacturacionId
				END

				IF (@version = '4.0' AND (@receptorCP IS NULL OR @receptorCP = ''))
					SET @mensaje = @tituloMensaje + 'No se ha configurado el CP del Receptor.'

				IF (@receptorRFC IS NULL OR @receptorRFC = '')
					SET @mensaje = @tituloMensaje + 'No se ha configurado el RFC del Receptor.'

				IF (@version = '4.0' AND (@receptorNombre IS NULL OR @receptorNombre = ''))
					SET @mensaje = @tituloMensaje + 'No se ha configurado el Nombre del Receptor.'
					
				IF (@version = '4.0' AND (@receptorRegimenFiscalId IS NULL))
					SET @mensaje = @tituloMensaje + 'No se ha configurado el Régimen Fiscal del Receptor.'

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
					CXCF_DF_DatosFacturacionId,
					CXCF_SUC_SucursalId,
					CXCF_CMM_EstatusId,
					CXCF_FechaCreacion,
					CXCF_USU_CreadoPorId
				)
				SELECT TOP 1 @version AS Version,
						GETDATE() AS Fecha,
						folio.Serie AS Serie,
						folio.Autonumerico AS Folio,
						@emisorCP AS EmisorCP,
						@emisorRFC AS EmisorRFC,
						@emisorRazonSocial AS EmisorRazonSocial,
						@emisorRegimenFiscalId AS EmisorRegimenFiscalId,
						@receptorCP AS ReceptorCP,
						@receptorRFC AS ReceptorRFC,
						@receptorNombre AS ReceptorNombre,
						CASE WHEN @version = '4.0' THEN @receptorRegimenFiscalId ELSE NULL END AS ReceptorRegimenFiscalId,
						( SELECT UCFDI_UsoCFDIId FROM SATUsosCFDI WHERE UCFDI_Codigo = CASE WHEN @version = '4.0' THEN 'CP01' ELSE 'P01' END AND UCFDI_Activo = 1 ) AS ReceptorUsoCFDIId, -- Pagos
						2000471 AS TipoRegistroId, -- CMM_CXCF_TipoRegistro - Factura CXC
						@datosFacturacionId AS DatosFacturacionId,
						@sucursalId AS SucursalId,
						2000495 AS EstatusId, -- CMM_CXCF_EstatusFactura - Pagada
						GETDATE() AS FechaCreacion,
						@usuarioId AS CreadoPorId
				FROM
						(
							SELECT SUC_Serie AS Serie, AUT_Prefijo+RIGHT('0000000000'+CONVERT(VARCHAR(10), AUT_Siguiente), AUT_Digitos) AS Autonumerico
							FROM Autonumericos
									INNER JOIN Sucursales ON AUT_ReferenciaId = SUC_SucursalId AND AUT_Nombre LIKE 'CXCPago%' AND SUC_SucursalId = @sucursalId
							WHERE AUT_Activo = 1
						) AS folio

				--Recuperamos el Id de la Factura que se generó
				SET @id = (SELECT SCOPE_IDENTITY())

				-- Insertamos los detalles
				INSERT INTO CXCFacturasDetalles
				SELECT @id AS FacturaId,
						'84111506' AS ClaveProdServ,
						NULL AS NoIdentificacion,
						'Pago' AS Descripcion,
						( SELECT UM_UnidadMedidaId FROM UnidadesMedidas WHERE UM_ClaveSAT = 'ACT' AND UM_Activo = 1 ) AS UnidadMedidaId,
						1 AS Cantidad,
						0 AS ValorUnitario,
						0 AS Importe,
						NULL AS Descuento,
						CASE WHEN @version = '4.0' THEN ( SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control  = 'CMM_SAT_ObjetoImp' AND CMM_Referencia = '01' AND CMM_Activo = 1 ) ELSE NULL END AS ObjetoImpuestoId,
						NULL AS ReferenciaId

				-- Actualizamos el Autonumerico de la sucursal
				UPDATE aut SET AUT_Siguiente = AUT_Siguiente + 1
				FROM Autonumericos AS aut
						INNER JOIN Sucursales ON aut.AUT_ReferenciaId = SUC_SucursalId AND aut.AUT_Nombre LIKE 'CXCPago%' AND SUC_SucursalId = @sucursalId
				WHERE AUT_Activo = 1

		COMMIT 

		END TRY
		BEGIN CATCH
			ROLLBACK;
			THROW;
		END CATCH
END