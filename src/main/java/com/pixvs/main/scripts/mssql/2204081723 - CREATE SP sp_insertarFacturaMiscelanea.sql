SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ========================================================
-- Author:		Javier Elías
-- Create date: 07/04/2022
-- Modified date: 
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
						 INNER JOIN SATRegimenesFiscales AS emisorRegimenFiscalId ON emisorRegimenFiscal.CMA_Valor = emisorRegimenFiscalId.RF_Codigo AND RF_Activo = 1
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
					CXCF_CLIF_ClienteFacturacionId,
					CXCF_ALUF_AlumnoContactoId,
					CXCF_SUC_SucursalId,
					CXCF_CMM_EstatusId,
					CXCF_FechaCreacion,
					CXCF_USU_CreadoPorId
				)
				SELECT TOP 1 '4.0' AS Version,
						GETDATE() AS Fecha,
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
						ISNULL(ALUF_CP, CLIF_CP) AS ReceptorCP,
						ISNULL(ALUF_RFC, CLIF_RFC) AS ReceptorRFC,
						ISNULL(ISNULL(ALUF_RazonSocial, (ALUF_Nombre + ' ' + ALUF_PrimerApellido + ISNULL(' ' + ALUF_SegundoApellido, ''))), ISNULL(CLIF_RazonSocial, (CLIF_Nombre + ' ' + CLIF_PrimerApellido + ISNULL(' ' + CLIF_SegundoApellido, '')))) AS ReceptorNombre,
						ISNULL(ALUF_RF_RegimenFiscalId, CLIF_RF_RegimenFiscalId) AS ReceptorRegimenFiscalId,
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
							SELECT AUT_Prefijo+RIGHT('0000000000'+CONVERT(VARCHAR(10), AUT_Siguiente), AUT_Digitos) AS Autonumerico
							FROM Autonumericos
									INNER JOIN Sucursales ON AUT_ReferenciaId = SUC_SucursalId AND AUT_Nombre = 'CXCFactura '+SUC_Nombre
							WHERE Sucursales.SUC_SucursalId = @sucursalId
						) AS folio
						INNER JOIN Monedas ON MON_MonedaId = @monedaId
						LEFT JOIN AlumnosFacturacion ON ALUF_AlumnoContactoId = @alumnoContactoId
						LEFT JOIN ClientesFacturacion ON CLIF_ClienteFacturacionId = @clienteFacturacionId
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