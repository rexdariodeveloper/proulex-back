SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] ON 
GO

INSERT INTO [dbo].[MenuPrincipalPermisos] (
	[MPP_MenuPrincipalPermisoId],
    [MPP_MP_NodoId],
    [MPP_Nombre],
    [MPP_Activo]
) VALUES (
	19,
    (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/control-escolar/calificaciones'),
    'Exportar a excel',
    1
), (
	20,
    (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/control-escolar/asistencias'),
    'Exportar a excel',
    1
)
GO

SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] ON 
GO