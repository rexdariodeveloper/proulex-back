SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] ON
GO

INSERT INTO [dbo].[MenuPrincipalPermisos](
	[MPP_MenuPrincipalPermisoId],
	[MPP_MP_NodoId],
	[MPP_Nombre],
	[MPP_Activo]
) VALUES (
	40,
	(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = N'/app/control-escolar/calificaciones'),
	N'Finalizar curso',
	1
)

SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] OFF
GO