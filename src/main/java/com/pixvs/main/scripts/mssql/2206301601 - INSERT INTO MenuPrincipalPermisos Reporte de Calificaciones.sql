SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] ON
GO

INSERT INTO [dbo].[MenuPrincipalPermisos](
	MPP_MenuPrincipalPermisoId,
	MPP_MP_NodoId,
	MPP_Nombre,
	MPP_Activo
)
VALUES (
	45,
	(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_TituloEN = 'Qualifications Report'),
	'Exportar a excel',
	1
)
GO

SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] OFF
GO