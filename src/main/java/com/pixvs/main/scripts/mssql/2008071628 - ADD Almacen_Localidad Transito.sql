SET IDENTITY_INSERT Almacenes ON
INSERT INTO Almacenes
(
    ALM_AlmacenId, -- column value is auto-generated
    ALM_CodigoAlmacen,
    ALM_Nombre,
    ALM_SUC_SucursalId,
    ALM_USU_ResponsableId,
    ALM_MismaDireccionSucursal,
	ALM_Predeterminado,
    ALM_Activo,
    ALM_FechaCreacion,
    ALM_USU_CreadoPorId
)
VALUES
(
    0, -- int
    'TRA', -- ALM_CodigoAlmacen - nvarchar
    'Tránsito', -- ALM_Nombre - nvarchar
    1, -- ALM_SUC_SucursalId - int
    1, -- ALM_USU_ResponsableId - int
    0, -- ALM_MismaDireccionSucursal - bit
	0, -- ALM_Predeterminado - bit
	0, -- ALM_Activo - bit
    GETDATE(), -- ALM_FechaCreacion - datetime
    1 -- ALM_USU_CreadoPorId - int
)
SET IDENTITY_INSERT Almacenes OFF
GO

SET IDENTITY_INSERT Localidades ON
INSERT INTO Localidades
(
    LOC_LocalidadId, -- column value is auto-generated
    LOC_CodigoLocalidad,
    LOC_Nombre,
    LOC_ALM_AlmacenId,
    LOC_LocalidadGeneral,
    LOC_Activo,
    LOC_FechaCreacion,
    LOC_USU_CreadoPorId
)
VALUES
(
    0, -- int
    'TRA', -- LOC_CodigoLocalidad - nvarchar
    'Tránsito', -- LOC_Nombre - nvarchar
    0, -- LOC_ALM_AlmacenId - int
    1, -- LOC_LocalidadGeneral - bit
    1, -- LOC_Activo - int
    GETDATE(), -- LOC_FechaCreacion - datetime
    1 -- LOC_USU_CreadoPorId - int
)
SET IDENTITY_INSERT Localidades OFF
GO