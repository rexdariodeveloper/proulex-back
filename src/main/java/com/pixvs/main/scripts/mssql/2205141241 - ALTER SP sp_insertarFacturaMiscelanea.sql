SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ========================================================
-- Author:		Javier Elías
-- Create date: 07/04/2022
-- Modified date: 14/05/2022
-- Description:	Procesador para insertar una Factura Miscelanea
-- ========================================================
CREATE OR ALTER PROCEDURE [dbo].[sp_insertarFacturaMiscelanea]
	@formaPagoId INT,
	@diasCredito INT,
	@monedaId INT,
	@metodoPagoId INT,
	@usoCFDIId INT,
	@clienteFacturacionId INT,
	@alumnoContactoId INT,
	@sucursalId INT,
	@usuarioId INT,
	@id INT OUTPUT
AS
BEGIN
		SET NOCOUNT ON;

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
						@formaPagoId AS FormaPagoId,
						@diasCredito AS DiasCredito,
						CASE WHEN @diasCredito > 0 THEN CONVERT(VARCHAR(10), @diasCredito) + ' días de crédito' ELSE 'De contado' END AS CondicionesPago,
						MON_MonedaId AS MonedaId,
						ISNULL(MONP_TipoCambioOficial, 1) AS TipoCambio,
						@metodoPagoId AS MetodoPagoId,
						@emisorCP AS EmisorCP,
						@emisorRFC AS EmisorRFC,
						@emisorRazonSocial AS EmisorRazonSocial,
						@emisorRegimenFiscalId AS EmisorRegimenFiscalId,
						@receptorCP AS ReceptorCP,
						@receptorRFC AS ReceptorRFC,
						@receptorNombre AS ReceptorNombre,
						@receptorRegimenFiscalId AS ReceptorRegimenFiscalId,
						@usoCFDIId AS ReceptorUsoCFDIId,
						2000475 AS TipoRegistroId, -- CMM_CXCF_TipoRegistro - Factura Miscelánea
						@clienteFacturacionId AS ClienteFacturacionId,
						@alumnoContactoId AS AlumnoContactoId,
						@sucursalId AS SucursalId,
						2000491 AS EstatusId, -- CMM_CXCF_EstatusFactura - Abierta
						GETDATE() AS FechaCreacion,
						@usuarioId AS CreadoPorId
				FROM
						(
							SELECT SUC_Serie AS Serie, AUT_Prefijo+RIGHT('0000000000'+CONVERT(VARCHAR(10), AUT_Siguiente), AUT_Digitos) AS Autonumerico
							FROM Autonumericos
									INNER JOIN Sucursales ON AUT_ReferenciaId = SUC_SucursalId AND AUT_Nombre = 'CXCFactura '+SUC_Nombre
							WHERE Sucursales.SUC_SucursalId = @sucursalId
						) AS folio
						INNER JOIN Monedas ON MON_MonedaId = @monedaId
						OUTER APPLY
						(
							SELECT TOP 1 MONP_TipoCambioOficial FROM MonedasParidad WHERE MONP_MON_MonedaId = MON_MonedaId AND MONP_Fecha <= CONVERT(DATE, GETDATE()) ORDER BY MONP_Fecha DESC
						) AS tipoCambio
				WHERE MON_MonedaId = @monedaId

				--Recuperamos el Id de la Factura que se generó
				SET @id = (SELECT SCOPE_IDENTITY())

				-- Actualizamos el Autonumerico de la sucursal
				UPDATE aut SET AUT_Siguiente = AUT_Siguiente + 1
				FROM Autonumericos AS aut
						INNER JOIN Sucursales ON aut.AUT_ReferenciaId = SUC_SucursalId AND aut.AUT_Nombre = 'CXCFactura '+SUC_Nombre
				WHERE SUC_SucursalId = @sucursalId

		COMMIT 

		END TRY
		BEGIN CATCH
			ROLLBACK;
			THROW;
		END CATCH
END