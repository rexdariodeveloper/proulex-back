SET IDENTITY_INSERT ArchivosEstructuraCarpetas ON
GO
INSERT INTO ArchivosEstructuraCarpetas
(
    AEC_EstructuraId, -- this column value is auto-generated
    AEC_AEC_EstructuraReferenciaId,
    AEC_Descripcion,
    AEC_NombreCarpeta,
    AEC_Activo,
    AEC_USU_CreadoPorId,
    AEC_FechaCreacion
)
VALUES
(
    17, -- AEC_EstructuraId - int
    NULL, -- AEC_AEC_EstructuraReferenciaId - int
    'Facturas CXC', -- AEC_Descripcion - varchar
    'facturasCXC', -- AEC_NombreCarpeta - varchar
    1, -- AEC_Activo - bit
    1, -- AEC_USU_CreadoPorId - int
    GETDATE() -- AEC_FechaCreacion - datetime2
)
SET IDENTITY_INSERT ArchivosEstructuraCarpetas OFF
GO