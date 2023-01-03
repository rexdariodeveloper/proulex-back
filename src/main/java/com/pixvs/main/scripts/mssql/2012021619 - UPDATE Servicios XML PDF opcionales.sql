UPDATE Servicios SET SRV_RequiereXML = 0, SRV_RequierePDF = 0;

ALTER TABLE [dbo].[CXPFacturas]
ALTER COLUMN [CXPF_UUID] [uniqueidentifier] NULL;
GO