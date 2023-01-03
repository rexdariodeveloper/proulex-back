SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] ON 
GO

INSERT INTO [dbo].[MenuPrincipalPermisos]
	([MPP_MenuPrincipalPermisoId], [MPP_MP_NodoId], [MPP_Nombre], [MPP_Activo])
VALUES(
	8,
	(SELECT [MP_NodoId] FROM [dbo].[MenuPrincipal] WHERE [MP_URL] = N'/app/control-escolar/asistencias'),
	N'Permitir captura extemporánea',
	1
)
GO

SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] OFF
GO