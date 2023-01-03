CREATE OR ALTER VIEW VW_OrdenesVentaCancelaciones_ExcelListado
AS
SELECT 1 AS a
GO

CREATE OR ALTER VIEW VW_OrdenesVentaCancelaciones
AS
SELECT 1 AS a
GO

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
( 2000080, 'CMM_OVC_TipoMovimiento', 1, NULL,'Devolución', 1, GETDATE() ),
( 2000081, 'CMM_OVC_TipoMovimiento', 1, NULL,'Cancelación', 1, GETDATE() )
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO

DECLARE @reseed INT = (SELECT MAX(CMM_ControlId) FROM ControlesMaestrosMultiples WHERE CMM_ControlId <= 1000000)

DBCC checkident ('ControlesMaestrosMultiples', reseed, @reseed)
GO

ALTER TABLE OrdenesVentaCancelaciones ADD OVC_CMM_TipoMovimientoId INT
GO

ALTER TABLE OrdenesVentaCancelaciones WITH CHECK ADD CONSTRAINT FK_OVC_CMM_TipoMovimientoId FOREIGN KEY(OVC_CMM_TipoMovimientoId) 
REFERENCES ControlesMaestrosMultiples(CMM_ControlId)
GO
ALTER TABLE OrdenesVentaCancelaciones CHECK CONSTRAINT FK_OVC_CMM_TipoMovimientoId
GO

ALTER TABLE OrdenesVentaCancelaciones ADD OVC_FechaDevolucion DATE
GO

ALTER TABLE OrdenesVentaCancelaciones ADD OVC_CMM_MotivoDevolucionId INT
GO

ALTER TABLE OrdenesVentaCancelaciones WITH CHECK ADD CONSTRAINT FK_OVC_CMM_MotivoDevolucionId FOREIGN KEY(OVC_CMM_MotivoDevolucionId) 
REFERENCES ControlesMaestrosMultiples(CMM_ControlId)
GO
ALTER TABLE OrdenesVentaCancelaciones CHECK CONSTRAINT FK_OVC_CMM_MotivoDevolucionId
GO

ALTER TABLE OrdenesVentaCancelaciones ALTER COLUMN OVC_FechaCancelacion DATE NULL
GO

ALTER TABLE OrdenesVentaCancelaciones ALTER COLUMN OVC_CMM_MotivoCancelacionId INT NULL
GO

UPDATE OrdenesVentaCancelaciones
  SET
      OVC_FechaDevolucion = OVC_FechaCancelacion,
      OVC_FechaCancelacion = NULL,
      OVC_CMM_MotivoDevolucionId = OVC_CMM_MotivoCancelacionId,
      OVC_CMM_MotivoCancelacionId = NULL,
	  OVC_CMM_TipoMovimientoId = 2000080
GO

ALTER TABLE OrdenesVentaCancelaciones ALTER COLUMN OVC_CMM_TipoMovimientoId INT NOT NULL
GO

ALTER TABLE OrdenesVentaCancelaciones ALTER COLUMN OVC_Banco VARCHAR(255) NULL
GO

ALTER TABLE OrdenesVentaCancelaciones ALTER COLUMN OVC_Beneficiario VARCHAR(255) NULL
GO

ALTER TABLE OrdenesVentaCancelaciones ALTER COLUMN OVC_NumeroCuenta VARCHAR(255) NULL
GO

ALTER TABLE OrdenesVentaCancelaciones ALTER COLUMN OVC_CLABE VARCHAR(255) NULL
GO

ALTER TABLE OrdenesVentaCancelaciones ALTER COLUMN OVC_TelefonoContacto VARCHAR(255) NULL
GO

ALTER TABLE OrdenesVentaCancelaciones ADD CONSTRAINT DF_OVC_ImporteReembolsar DEFAULT(0) FOR OVC_ImporteReembolsar
GO