UPDATE MenuPrincipal SET MP_Orden = MP_Orden + 1 WHERE MP_NodoPadreId = 1084 AND MP_Orden >= 9
GO

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
    'Reporte eficiencia de grupos', -- MP_Titulo - varchar
    'Group efficiency report', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'supervisor_account', -- MP_Icono - varchar
    9, -- MP_Orden - int
    'item', -- MP_Tipo - varchar
    '/app/control-escolar/reportes/eficiencia-grupos', -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE(), -- MP_FechaCreacion - datetime2
    0, -- MP_Repetible - bit
    0 -- MP_Personalizado - bit
)
GO