DELETE FROM EmpleadosDatosSalud WHERE EMPDS_EMP_EmpleadoId IN (SELECT EMP_EmpleadoId FROM Empleados WHERE EMP_EMPCO_EmpleadoContratoId IS NOT NULL)
GO

DELETE FROM EmpleadosBeneficiarios WHERE EMPBE_EMP_EmpleadoId IN (SELECT EMP_EmpleadoId FROM Empleados WHERE EMP_EMPCO_EmpleadoContratoId IS NOT NULL)
GO

DELETE FROM EmpleadosContactos WHERE EC_EMP_EmpleadoId IN (SELECT EMP_EmpleadoId FROM Empleados WHERE EMP_EMPCO_EmpleadoContratoId IS NOT NULL)
GO

DELETE FROM EmpleadosDocumentos WHERE EMPD_EMP_EmpleadoId IN (SELECT EMP_EmpleadoId FROM Empleados WHERE EMP_EMPCO_EmpleadoContratoId IS NOT NULL)
GO


DELETE FROM Empleados WHERE EMP_EMPCO_EmpleadoContratoId IS NOT NULL
GO

ALTER TABLE Empleados DROP CONSTRAINT IF EXISTS FK_EMP_EMPCO_EmpleadoContratoId
GO

ALTER TABLE Empleados DROP COLUMN IF EXISTS EMP_EMPCO_EmpleadoContratoId
GO

DROP TABLE IF EXISTS EmpleadosContratos
GO

DROP TABLE IF EXISTS EmpleadosLaborales
GO