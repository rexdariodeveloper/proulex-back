SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER PROCEDURE [dbo].[sp_ProgramacionPagosPostAlerta]  (@cxpSolicitudPagoId int, @estatusId int, @estatusProceso int OUT, @logTipo int OUT)
AS
BEGIN

	DECLARE @cxpspEstatusRechazado int = 2000166;
	DECLARE @cxpfEstatusPagoProgramadoEnProceso int = 2000116;

	IF @estatusId = @cxpspEstatusRechazado BEGIN
		UPDATE CXPFacturas SET CXPF_CMM_EstatusId = @cxpfEstatusPagoProgramadoEnProceso
		FROM CXPFacturas
		INNER JOIN CXPSolicitudesPagosDetalles ON CXPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		WHERE CXPSD_CXPS_CXPSolicitudPagoId = @cxpSolicitudPagoId;
	END

	SELECT @estatusProceso = NULL, @logTipo = NULL

END
GO