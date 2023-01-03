SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] ON 
GO

INSERT INTO [dbo].[MenuPrincipalPermisos] (
	[MPP_MenuPrincipalPermisoId],
    [MPP_MP_NodoId],
    [MPP_Nombre],
    [MPP_Activo]
) VALUES(
	13,
    (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),
    'Plantilla alumnos SEMS',
    1
), (
	14,
    (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),
    'Plantilla profesores SEMS',
    1
), (
	15,
    (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),
    'Listado alumnos SEMS',
    1
), (
	16,
    (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),
    'Plantilla alumnos JOBS',
    1
), (
	17,
    (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),
    'Plantilla profesores JOBS',
    1
), (
	18,
    (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),
    'Listado alumnos JOBS',
    1
)
GO

SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] ON 
GO