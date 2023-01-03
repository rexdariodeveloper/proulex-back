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
    73, -- MPP_MenuPrincipalPermisoId - int
    ( SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos' ), -- MPP_MP_NodoId - int
    'Permitir baja de grupo JOBS y SEMS', -- MPP_Nombre - nvarchar
    1 -- MPP_Activo - bit
)
GO
SET IDENTITY_INSERT MenuPrincipalPermisos OFF
GO