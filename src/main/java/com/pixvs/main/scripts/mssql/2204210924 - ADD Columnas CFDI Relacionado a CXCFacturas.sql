EXEC sp_rename 'dbo.CXCFacturas.CXCF_MotivoCancelacion', 'CXCF_CMM_MotivoCancelacionId', 'COLUMN';

ALTER TABLE CXCFacturas ALTER COLUMN CXCF_CMM_MotivoCancelacionId INT NULL
GO

ALTER TABLE CXCFacturas ADD CXCF_FacturaRelacionadaId INT
GO

ALTER TABLE CXCFacturas ADD CXCF_CMM_TipoRelacionId INT
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_MotivoCancelacion FOREIGN KEY(CXCF_CMM_MotivoCancelacionId)
REFERENCES ControlesMaestrosMultiples (CMM_ControlId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_MotivoCancelacion
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_FacturaRelacionada FOREIGN KEY(CXCF_FacturaRelacionadaId)
REFERENCES CXCFacturas (CXCF_FacturaId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_FacturaRelacionada
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_TipoRelacion FOREIGN KEY(CXCF_CMM_TipoRelacionId)
REFERENCES ControlesMaestrosMultiples (CMM_ControlId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_TipoRelacion
GO