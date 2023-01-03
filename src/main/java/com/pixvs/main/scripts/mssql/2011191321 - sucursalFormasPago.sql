CREATE TABLE SucursalFormasPago(
    SFP_SucursalFormaPagoId INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    SFP_SucursalId INT NOT NULL,
    SFP_FPPV_FormaPagoId SMALLINT NOT NULL,
    SFP_UsarEnPV BIT DEFAULT 1,
    SFP_Activo BIT DEFAULT 1,
    SFP_FechaCreacion DATETIME NOT NULL,
    SFP_USU_CreadoPorId INT NULL,
    SFP_FechaModificacion DATETIME NULL,
    SFP_USU_ModificadoPorId INT NULL
)
GO

ALTER TABLE SucursalFormasPago ADD FOREIGN KEY (SFP_SucursalId) REFERENCES Sucursales(SUC_SucursalId)
GO

ALTER TABLE SucursalFormasPago ADD FOREIGN KEY (SFP_FPPV_FormaPagoId) REFERENCES FormasPagoPV(FPPV_FormaPagoId)
GO

ALTER TABLE SucursalFormasPago ADD FOREIGN KEY (SFP_USU_CreadoPorId) REFERENCES Usuarios(USU_UsuarioId)
GO

ALTER TABLE SucursalFormasPago ADD FOREIGN KEY (SFP_USU_ModificadoPorId) REFERENCES Usuarios(USU_UsuarioId)
GO