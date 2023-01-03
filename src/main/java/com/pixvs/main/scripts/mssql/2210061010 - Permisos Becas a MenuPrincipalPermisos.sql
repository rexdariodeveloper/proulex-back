SET IDENTITY_INSERT MenuPrincipalPermisos ON
GO
INSERT INTO MenuPrincipalPermisos
(
    MPP_MenuPrincipalPermisoId, -- this column value is auto-generated
    MPP_MP_NodoId,
    MPP_Nombre,
    MPP_Activo
)
VALUES
(
    70, -- MPP_MenuPrincipalPermisoId - int
    ( SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/control-escolar/reportes/becas' ), -- MPP_MP_NodoId - int
    'Exportar a excel', -- MPP_Nombre - nvarchar
    1 -- MPP_Activo - bit
),
(
    71, -- MPP_MenuPrincipalPermisoId - int
    ( SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/control-escolar/reportes/becas' ), -- MPP_MP_NodoId - int
    'Descargar reportes', -- MPP_Nombre - nvarchar
    1 -- MPP_Activo - bit
)
GO
SET IDENTITY_INSERT MenuPrincipalPermisos OFF
GO