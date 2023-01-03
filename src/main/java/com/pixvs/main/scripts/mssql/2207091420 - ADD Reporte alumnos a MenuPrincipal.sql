INSERT INTO MenuPrincipal
(
    --MP_NodoId - this column value is auto-generated
    MP_NodoPadreId,
    MP_Titulo,
    MP_TituloEN,
    MP_Activo,
    MP_Icono,
    MP_Orden,
    MP_Tipo,
    MP_URL,
    MP_CMM_SistemaAccesoId,
    MP_FechaCreacion,
    MP_Repetible,
    MP_Personalizado
)
VALUES
(
    -- MP_NodoId - int
    1084, -- MP_NodoPadreId - int
    'Reporte de alumnos', -- MP_Titulo - varchar
    'Student report', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'person', -- MP_Icono - varchar
    (SELECT MAX(MP_Orden) + 1 FROM MenuPrincipal WHERE MP_NodoPadreId = 1084), -- MP_Orden - int
    'item', -- MP_Tipo - varchar
    '/app/control-escolar/reportes/reporte-alumnos', -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE(), -- MP_FechaCreacion - datetime2
    0, -- MP_Repetible - bit
    0 -- MP_Personalizado - bit
)
GO