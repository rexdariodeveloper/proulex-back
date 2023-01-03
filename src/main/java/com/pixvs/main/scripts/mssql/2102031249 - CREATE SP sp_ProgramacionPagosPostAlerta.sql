/****** Object:  StoredProcedure [dbo].[sp_ProgramacionPagosPostAlerta]    Script Date: 02/02/2021 11:33:16 a. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER PROCEDURE [dbo].[sp_ProgramacionPagosPostAlerta]  @cxpSolicitudPagoId int, @estatusId int  
AS
BEGIN

	DECLARE @cxpspEstatusRechazado int = 2000166;
	DECLARE @cxpfEstatusPagoProgramadoEnProceso int = 2000116;

	IF @estatusId = @cxpspEstatusRechazado BEGIN
		UPDATE CXPFacturas SET CXPF_CMM_EstatusId = @cxpfEstatusPagoProgramadoEnProceso
		FROM CXPFacturas
		INNER JOIN CXPSolicitudesPagosDetalles ON CXPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		where CXPSD_CXPS_CXPSolicitudPagoId = @cxpSolicitudPagoId
	END

END
GO


UPDATE AlertasConfig SET ALC_SPFinal = 'sp_ProgramacionPagosPostAlerta' WHERE ALC_AlertaCId = 10
GO