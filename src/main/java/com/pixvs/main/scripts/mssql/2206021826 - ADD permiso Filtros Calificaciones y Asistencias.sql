SET IDENTITY_INSERT MenuPrincipalPermisos ON
INSERT INTO MenuPrincipalPermisos
(
    MPP_MenuPrincipalPermisoId,
    MPP_MP_NodoId,
    MPP_Nombre,
    MPP_Activo
)
VALUES
(
    43, -- MPP_MenuPrincipalPermisoId - int
    1077, -- MPP_MP_NodoId - int
    'Mostrar Filtros', -- MPP_Nombre - nvarchar
    1 -- MPP_Activo - bit
),
(
    44, -- MPP_MenuPrincipalPermisoId - int
    1078, -- MPP_MP_NodoId - int
    'Mostrar Filtros', -- MPP_Nombre - nvarchar
    1 -- MPP_Activo - bit
)
SET IDENTITY_INSERT MenuPrincipalPermisos OFF
GO