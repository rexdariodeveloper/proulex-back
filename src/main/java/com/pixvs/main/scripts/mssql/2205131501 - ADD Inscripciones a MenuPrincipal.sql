DECLARE @nodoIdModulos INT = ( SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId IS NULL AND MP_TituloEN = 'MODULES' AND MP_Activo = 1 )
DECLARE @nodoIdVentas INT = ( SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = @nodoIdModulos AND MP_TituloEN = 'Sells' AND MP_Activo = 1 )
DECLARE @nodoIdReportes INT = ( SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = @nodoIdVentas AND MP_TituloEN = 'Reports' AND MP_Activo = 1 )
DECLARE @orden INT = ( SELECT MAX(MP_Orden) FROM MenuPrincipal WHERE MP_NodoPadreId = @nodoIdReportes ) + 1

INSERT INTO MenuPrincipal
VALUES
(
    -- MP_NodoId - int
    @nodoIdReportes, -- MP_NodoPadreId - int
    'Inscripciones', -- MP_Titulo - varchar
    'Enrollments', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'book', -- MP_Icono - varchar
    @orden, -- MP_Orden - int
    'item', -- MP_Tipo - varchar
    '/app/ventas/reportes/inscripciones', -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE(), -- MP_FechaCreacion - datetime2
    0, -- MP_Repetible - bit
    NULL -- MP_Personalizado - bit
)
GO