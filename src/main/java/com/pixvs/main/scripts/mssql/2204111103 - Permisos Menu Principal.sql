SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] ON
GO

INSERT INTO [dbo].[MenuPrincipalPermisos](
	MPP_MenuPrincipalPermisoId,
	MPP_MP_NodoId,
	MPP_Nombre,
	MPP_Activo
)
VALUES (
	41,
	(select MP_NodoId from MenuPrincipal where MP_URL = N'/app/programacion-academica/incompany'),
	N'Cambio Sueldo Profesor',
	1
)
GO

SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] OFF
GO
