SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] ON
GO

INSERT INTO MenuPrincipalPermisos(MPP_MenuPrincipalPermisoId, MPP_MP_NodoId, MPP_Nombre, MPP_Activo)
VALUES(65, (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Reporte de Alumnos Sin Grupo'), 'Exportar a excel', 1)
GO

SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] OFF