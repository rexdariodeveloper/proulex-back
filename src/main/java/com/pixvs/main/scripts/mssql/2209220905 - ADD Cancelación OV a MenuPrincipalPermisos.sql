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
    64, -- MPP_MenuPrincipalPermisoId - int
    1106, -- MPP_MP_NodoId - int
    'Cancelar inscripción con beca después del periodo', -- MPP_Nombre - nvarchar
    1 -- MPP_Activo - bit
)
GO
SET IDENTITY_INSERT MenuPrincipalPermisos OFF
GO