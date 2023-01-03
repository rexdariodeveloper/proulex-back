UPDATE MenuPrincipalPermisos SET MPP_Activo = 0 WHERE MPP_MP_NodoId = 1061
GO

INSERT INTO MenuPrincipalPermisos (MPP_MP_NodoId, MPP_Nombre, MPP_Activo)
VALUES (1061, 'Permitir modificar el sueldo del profesor', 1)
GO

UPDATE MenuPrincipalPermisos SET MPP_Activo = 1 WHERE MPP_MenuPrincipalPermisoId = 27
GO