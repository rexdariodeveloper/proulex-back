CREATE OR ALTER FUNCTION existeCXPFacturaUUID ( @uuid uniqueidentifier, @cxpFacturaValidarId int )
RETURNS bit
AS
BEGIN
	
	IF (@cxpFacturaValidarId IS NOT NULL) AND EXISTS (
		SELECT *
		FROM CXPFacturas
		WHERE
			CXPF_UUID = @uuid
			AND CXPF_CXPFacturaId != @cxpFacturaValidarId
			AND CXPF_CMM_EstatusId != 2000111
			AND CXPF_CMM_EstatusId != 2000114
	) return CAST(1 as bit)

	IF (@cxpFacturaValidarId IS NULL) AND EXISTS (
		SELECT *
		FROM CXPFacturas
		WHERE
			CXPF_UUID = @uuid
			AND CXPF_CMM_EstatusId != 2000111
			AND CXPF_CMM_EstatusId != 2000114
	) return CAST(1 as bit)
    
	return CAST(0 AS bit)
END
GO

ALTER TABLE [dbo].[CXPFacturas] WITH CHECK ADD CONSTRAINT [CHK_CXPF_UUID] CHECK (CXPF_CMM_EstatusId = 2000111 OR CXPF_CMM_EstatusId = 2000114 OR [dbo].[existeCXPFacturaUUID](CXPF_UUID,CXPF_CXPFacturaId) = CAST(0 AS bit))
GO