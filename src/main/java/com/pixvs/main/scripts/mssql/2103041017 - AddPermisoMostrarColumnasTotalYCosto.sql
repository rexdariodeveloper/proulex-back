/*
  Agregamos el permiso al menu
*/
SET IDENTITY_INSERT MenuPrincipalPermisos  ON
GO

INSERT INTO MenuPrincipalPermisos(MPP_MenuPrincipalPermisoId, MPP_MP_NodoId, MPP_Nombre, MPP_Activo)
VALUES(2, 27, 'Mostrar Costo y total', 1)
GO

SET IDENTITY_INSERT MenuPrincipalPermisos  OFF
GO