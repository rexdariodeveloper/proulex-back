SET IDENTITY_INSERT ControlesMaestrosMultiples ON
INSERT INTO ControlesMaestrosMultiples
(
    CMM_ControlId,
	CMM_Control,
    CMM_Activo,
    CMM_Referencia,
	CMM_Valor,
    CMM_Sistema,
    CMM_FechaCreacion
)
VALUES
( 2000440, 'CMM_SUC_TipoFacturaGlobal', 1, NULL,'Por sede', 1, GETDATE() ),
( 2000441, 'CMM_SUC_TipoFacturaGlobal', 1, NULL,'Por usuario', 1, GETDATE() )
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO

DECLARE @reseed INT = (SELECT MAX(CMM_ControlId) FROM ControlesMaestrosMultiples WHERE CMM_ControlId <= 1000000)

DBCC checkident ('ControlesMaestrosMultiples', reseed, @reseed)
GO

ALTER TABLE Sucursales ADD SUC_CMM_TipoFacturaGlobalId INT NULL
GO

ALTER TABLE Sucursales  WITH CHECK ADD  CONSTRAINT FK_Sucursales_TipoFacturaGlobal FOREIGN KEY(SUC_CMM_TipoFacturaGlobalId)
REFERENCES ControlesMaestrosMultiples (CMM_ControlId)
GO
ALTER TABLE Sucursales CHECK CONSTRAINT FK_Sucursales_TipoFacturaGlobal
GO

UPDATE Sucursales SET SUC_CMM_TipoFacturaGlobalId = 2000440
GO

ALTER TABLE Sucursales ALTER COLUMN SUC_CMM_TipoFacturaGlobalId INT NOT NULL
GO