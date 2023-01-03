ALTER TABLE CXCFacturas ALTER COLUMN CXCF_EmisorRazonSocial VARCHAR(4000) NULL
GO

ALTER TABLE CXCFacturas ALTER COLUMN CXCF_ReceptorRegimenFiscalId INT NULL
GO

ALTER TABLE CXCFacturasDetalles ALTER COLUMN CXCFD_CMM_ObjetoImpuestoId INT NULL
GO

ALTER TABLE AlumnosFacturacion ALTER COLUMN ALUF_CP VARCHAR(10) NULL
GO

ALTER TABLE AlumnosFacturacion DROP CONSTRAINT IF EXISTS CHK_ALUF_Telefono
GO

ALTER TABLE AlumnosFacturacion DROP CONSTRAINT IF EXISTS CHK_ALUF_Municipio
GO

ALTER TABLE ClientesFacturacion ALTER COLUMN CLIF_CP VARCHAR(10) NULL
GO

ALTER TABLE ClientesFacturacion DROP CONSTRAINT IF EXISTS CHK_CLIF_Telefono
GO

INSERT INTO SATUsosCFDI
VALUES
(
    -- UCFDI_UsoCFDIId - int
    'P01', -- UCFDI_Codigo - nvarchar
    'Por definir', -- UCFDI_Descripcion - nvarchar
    1, -- UCFDI_Fisica - bit
    1, -- UCFDI_Moral - bit
    1 -- UCFDI_Activo - bit
)
GO