SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ============================================
-- Author:		Javier Elías
-- Create date: 06/08/2022
-- Modified date: 
-- Description:	Procesador para actualiar el Estatus 
--						de Pago de una Factura
-- ============================================
CREATE OR ALTER PROCEDURE [dbo].[sp_actualizarEstatusPagoFactura]
	@facturaId INT,
	@usuarioId INT
AS
BEGIN
		SET NOCOUNT ON;

		BEGIN TRY
			BEGIN TRANSACTION

				UPDATE facturas
					SET
						facturas.CXCF_CMM_EstatusId = CASE WHEN saldo.Saldo = 0 THEN 2000495 ELSE 2000494 END,
						facturas.CXCF_FechaModificacion = GETDATE(),
						facturas.CXCF_USU_ModificadoPorId = @usuarioId
				FROM CXCFacturas AS facturas
						INNER JOIN dbo.fn_getSaldoPagosFacturacion() AS saldo ON facturas.CXCF_FacturaId = saldo.FacturaId
						INNER JOIN CXCPagosDetalles ON facturas.CXCF_FacturaId = CXCPD_CXCF_DoctoRelacionadoId
						INNER JOIN CXCPagos ON CXCPD_CXCP_PagoId = CXCP_PagoId AND CXCP_CXCF_FacturaId = @facturaId

		COMMIT 

		END TRY
		BEGIN CATCH
			ROLLBACK;
			THROW;
		END CATCH
END