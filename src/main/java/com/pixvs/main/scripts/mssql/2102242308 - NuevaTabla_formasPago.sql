/*Nueva tabla de formas de pago, con el fin de generalizar las formas de pago*/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

DROP TABLE IF EXISTS FormasPago
  GO
CREATE TABLE FormasPago(
   FP_FormaPagoId SMALLINT IDENTITY(1, 1),
   FP_Codigo VARCHAR(10) NOT NULL,
   FP_Nombre VARCHAR(255) NOT NULL,
   FP_ARC_ImagenId INTEGER DEFAULT NULL, -- FK
   FP_Activo BIT NOT NULL,
   FP_USU_CreadoPorId INTEGER NOT NULL, -- FK
   FP_FechaCreacion DATETIME NOT NULL,
   FP_USU_ModificadoPorId INTEGER DEFAULT NULL, --FK
   FP_FechaModificacion DATETIME DEFAULT NULL,
   PRIMARY KEY(FP_FormaPagoId),
   FOREIGN KEY (FP_ARC_ImagenId) REFERENCES Archivos(ARC_ArchivoId),
   FOREIGN KEY(FP_USU_CreadoPorId) REFERENCES Usuarios(USU_UsuarioId),
   FOREIGN KEY(FP_USU_ModificadoPorId) REFERENCES Usuarios(USU_UsuarioId)
)
GO

-- Eliminamos el constraint y eliminamos la columna
ALTER TABLE CXPPagos DROP CONSTRAINT FK_CXPP_CMM_FormaPagoId
GO
-- Eliminamos la columna
ALTER TABLE CXPPagos DROP COLUMN CXPP_CMM_FormaPagoId
GO
-- Agregamos la columna
ALTER TABLE CXPPagos ADD CXPP_FP_FormaPagoId SMALLINT DEFAULT NULL
GO
-- Agregamos al constraint
ALTER TABLE CXPPagos ADD CONSTRAINT FK_CXPP_FP_FormaPagoId FOREIGN KEY (CXPP_FP_FormaPagoId) REFERENCES FormasPago(FP_FormaPagoId)
GO

-- Insertar formas de pago que existian en ControlesMaestrosMultiples
INSERT INTO FormasPago
   (FP_Codigo, FP_Nombre, FP_Activo, FP_USU_CreadoPorId, FP_FechaCreacion, FP_USU_ModificadoPorId, FP_FechaModificacion)
VALUES
   ('01', 'Efectivo', 1, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP),
   ('02', 'Cheque nominativo', 1, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP),
   ('03', 'Transferencia electrónica de fondos', 1, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP),
   ('04', 'Tarjeta de crédito', 1, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP),
   ('28', 'Tarjeta de débito', 1, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP),
   ('99', 'Por definir', 1, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP)
GO


/*Proveedores Formas de Pago*/
ALTER TABLE ProveedoresFormasPagos ADD PROFP_FP_FormaPagoId SMALLINT
GO

-- Actualizamos los ID de formas de pago
UPDATE ProveedoresFormasPagos
SET PROFP_FP_FormaPagoId = FP_FormaPagoId
    FROM(
    SELECT PROFP_ProveedorFormaPagoId AS id, PROFP_CMM_FormaPagoId,CMM_ControlId, FP_FormaPagoId, FP_Codigo, FP_Nombre FROM ProveedoresFormasPagos
    INNER JOIN ControlesMaestrosMultiples ON PROFP_CMM_FormaPagoId = CMM_ControlId
    INNER JOIN  FormasPago ON CMM_Valor = FP_Nombre
   )AS datos_actualizada
WHERE PROFP_ProveedorFormaPagoId = id
GO

ALTER TABLE ProveedoresFormasPagos ALTER COLUMN PROFP_FP_FormaPagoId SMALLINT NOT NULL
GO

ALTER TABLE ProveedoresFormasPagos ADD CONSTRAINT FK_PROFP_FP_FormaPagoId FOREIGN KEY (PROFP_FP_FormaPagoId) REFERENCES FormasPago(FP_FormaPagoId)
   GO

ALTER TABLE ProveedoresFormasPagos DROP CONSTRAINT FK_ProveedoresFormasPagos_ControlesMaestrosMultiples
GO
ALTER TABLE ProveedoresFormasPagos DROP COLUMN PROFP_CMM_FormaPagoId
GO

-- Formas de pago Sucursales
ALTER TABLE SucursalFormasPago DROP CONSTRAINT FK__SucursalF__SFP_F__75C27486
GO

sp_RENAME 'SucursalFormasPago.SFP_FPPV_FormaPagoId', 'SFP_FP_FormaPagoId' , 'COLUMN'
GO

-- Eliminamos las Formas de Pago de Controles Maestros Multiples
DELETE FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_CXPP_FormaPago'
GO


UPDATE MenuListadosGenerales SET MLG_NodoPadreId = 6, MLG_NombreTablaCatalogo = 'FormasPago'
WHERE MLG_Titulo = 'Formas de pago' AND MLG_TituloEN = 'Payment methods'
GO