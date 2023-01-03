INSERT INTO [dbo].[MenuPrincipal]
           ([MP_NodoPadreId]
           ,[MP_Titulo]
           ,[MP_TituloEN]
           ,[MP_Activo]
           ,[MP_Icono]
           ,[MP_Orden]
           ,[MP_Tipo]
           ,[MP_URL]
           ,[MP_CMM_SistemaAccesoId]
           ,[MP_FechaCreacion])
     VALUES
           ((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = N'Control escolar')
           ,N'Captura de asistencia'
           ,N'Attendance register'
           ,1
           ,N'assignment_turned_in'
           ,(SELECT MAX(MP_Orden) + 1 FROM MenuPrincipal WHERE MP_NodoPadreId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = N'Control escolar'))
           ,N'item'
           ,N'/app/control-escolar/asistencias'
           ,1000021
           ,GETDATE())
GO

INSERT INTO [dbo].[RolesMenus](
	[ROLMP_Crear], 
	[ROLMP_Modificar], 
	[ROLMP_Eliminar], 
	[ROLMP_ROL_RolId], 
	[ROLMP_MP_NodoId],
	[ROLMP_FechaCreacion]
) VALUES (
	1,
	1,
	1,
	1,
	(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = N'/app/control-escolar/asistencias'),
	GETDATE()
)
GO