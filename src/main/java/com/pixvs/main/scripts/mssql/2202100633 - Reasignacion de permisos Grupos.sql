DELETE FROM [dbo].[RolesMenusPermisos] WHERE [ROLMPP_MPP_MenuPrincipalPermisoId] IN (13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27)
GO

DELETE FROM [dbo].[MenuPrincipalPermisos] WHERE [MPP_MenuPrincipalPermisoId] IN (13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27)
GO

SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] ON 
GO

/** Reasignación de permisos exportado a excel **/
INSERT INTO [dbo].[MenuPrincipalPermisos] (
	[MPP_MenuPrincipalPermisoId],
    [MPP_MP_NodoId],
    [MPP_Nombre],
    [MPP_Activo]) 
VALUES 
	(13, (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/control-escolar/calificaciones'), 'Exportar a excel', 1), 
	(14, (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/control-escolar/asistencias'), 'Exportar a excel', 1)
GO

/** Nueva secuencia de permisos para grupos **/
INSERT INTO [dbo].[MenuPrincipalPermisos] (
	[MPP_MenuPrincipalPermisoId],
    [MPP_MP_NodoId],
    [MPP_Nombre],
    [MPP_Activo]) 
VALUES
	(15,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),'Descargar plantilla alumnos SEMS',1),
	(16,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),'Descargar plantilla profesores SEMS',1),
	(17,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),'Descargar listado alumnos SEMS',1),
	(18,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),'Importar plantilla alumnos SEMS',1),
	(19,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),'Importar plantilla profesores SEMS',1),
	(20,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),'Descargar plantilla alumnos JOBS',1),
	(21,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),'Descargar plantilla profesores JOBS',1),
	(22,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),'Descargar listado alumnos JOBS',1),
	(23,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),'Importar plantilla alumnos JOBS',1),
	(24,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),'Importar plantilla profesores JOBS',1),
	(25,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),'Descargar plantilla alumnos PCP',1),
	(26,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),'Importar plantilla alumnos PCP',1),
	(27,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/programacion-academica/grupos'),'Exportar a excel',1)
GO