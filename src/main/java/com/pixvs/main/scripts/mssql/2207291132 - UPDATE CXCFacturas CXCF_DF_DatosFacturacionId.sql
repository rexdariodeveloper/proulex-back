ALTER TABLE CXCFacturas ADD CXCF_DF_DatosFacturacionId INT NULL
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCF_DF_DatosFacturacionId FOREIGN KEY(CXCF_DF_DatosFacturacionId)
REFERENCES DatosFacturacion (DF_DatosFacturacionId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCF_DF_DatosFacturacionId
GO

UPDATE CXCFacturas
  SET
      CXCF_DF_DatosFacturacionId = DF_DatosFacturacionId
FROM CXCFacturas
     INNER JOIN DatosFacturacion ON CXCF_ALUF_AlumnoContactoId = AnteriorId AND AlumnoId IS NOT NULL
GO

UPDATE CXCFacturas
  SET
      CXCF_DF_DatosFacturacionId = DF_DatosFacturacionId
FROM CXCFacturas
     INNER JOIN DatosFacturacion ON CXCF_CLIF_ClienteFacturacionId = AnteriorId AND ClienteId IS NOT NULL
GO

UPDATE CXCFacturas SET CXCF_ReceptorNombre = ISNULL(DF_RazonSocial, (DF_Nombre + ISNULL(' ' + DF_PrimerApellido, '') + ISNULL(' ' + DF_SegundoApellido, '')))
FROM CXCFacturas
     INNER JOIN DatosFacturacion ON CXCF_DF_DatosFacturacionId = DF_DatosFacturacionId
WHERE CXCF_ReceptorNombre IS NULL
GO

ALTER TABLE CXCFacturas DROP CONSTRAINT IF EXISTS FK_CXCFacturas_AlumnosFacturacion
GO

ALTER TABLE CXCFacturas DROP CONSTRAINT IF EXISTS FK_CXCFacturas_ClientesFacturacion
GO

ALTER TABLE CXCFacturas DROP COLUMN CXCF_ALUF_AlumnoContactoId
GO

ALTER TABLE CXCFacturas DROP COLUMN CXCF_CLIF_ClienteFacturacionId
GO

ALTER TABLE DatosFacturacion DROP COLUMN AnteriorId
GO
ALTER TABLE DatosFacturacion DROP COLUMN AlumnoId
GO
ALTER TABLE DatosFacturacion DROP COLUMN ClienteId
GO

DROP TABLE AlumnosFacturacion
GO
DROP TABLE ClientesFacturacion
GO