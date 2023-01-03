/** 
	Author: Angel Daniel Hernández Silva
	Description: Se agrega el permiso "Visualizar todos los registros" a los menus de "Solicitud de pago" y "Solicitud de pago RH"
**/
SET IDENTITY_INSERT MenuPrincipalPermisos  ON
GO

INSERT INTO MenuPrincipalPermisos(MPP_MenuPrincipalPermisoId, MPP_MP_NodoId, MPP_Nombre, MPP_Activo)
VALUES(3, 1041, 'Visualizar todos los registros', 1)
GO

INSERT INTO MenuPrincipalPermisos(MPP_MenuPrincipalPermisoId, MPP_MP_NodoId, MPP_Nombre, MPP_Activo)
VALUES(4, 1051, 'Visualizar todos los registros', 1)
GO

SET IDENTITY_INSERT MenuPrincipalPermisos  OFF
GO