DECLARE @nodoIdModulos INT = ( SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId IS NULL AND MP_TituloEN = 'MODULES' AND MP_Activo = 1 )
DECLARE @nodoIdCatalogos INT = ( SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = @nodoIdModulos AND MP_TituloEN = 'Catalogs' AND MP_Activo = 1 )
DECLARE @orden INT = ( SELECT MAX(MP_Orden) FROM MenuPrincipal WHERE MP_NodoPadreId = @nodoIdCatalogos ) + 1

INSERT INTO MenuPrincipal
VALUES
(
    -- MP_NodoId - int
    @nodoIdCatalogos, -- MP_NodoPadreId - int
    'Bancos', -- MP_Titulo - varchar
    'Banks', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'monetization_on', -- MP_Icono - varchar
    @orden, -- MP_Orden - int
    'item', -- MP_Tipo - varchar
    '/app/catalogos/bancos', -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE(), -- MP_FechaCreacion - datetime2
    0, -- MP_Repetible - bit
    NULL -- MP_Personalizado - bit
)
GO