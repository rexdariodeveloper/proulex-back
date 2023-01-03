SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER PROCEDURE [dbo].[sp_ProgramacionPagosPostAlerta]  (@cxpSolicitudPagoId int, @estatusId int, @estatusProceso int OUT, @logTipo int OUT)
AS
BEGIN

	DECLARE @cxpspEstatusRechazado int = 2000166;
	DECLARE @cxpfEstatusPagoProgramadoEnProceso int = 2000116;
	DECLARE @logTipoAceptada int = 34;

	IF @estatusId = @cxpspEstatusRechazado BEGIN
		UPDATE CXPFacturas SET CXPF_CMM_EstatusId = @cxpfEstatusPagoProgramadoEnProceso
		FROM CXPFacturas
		INNER JOIN CXPSolicitudesPagosDetalles ON CXPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		WHERE CXPSD_CXPS_CXPSolicitudPagoId = @cxpSolicitudPagoId;
		
		SELECT @estatusProceso = NULL, @logTipo = NULL
	END ELSE BEGIN
		SELECT @estatusProceso = NULL, @logTipo = @logTipoAceptada
	END

END
GO

UPDATE AlertasConfig SET
	ALC_LOGP_LogProcesoId = 6
WHERE ALC_AlertaCId = 10
GO