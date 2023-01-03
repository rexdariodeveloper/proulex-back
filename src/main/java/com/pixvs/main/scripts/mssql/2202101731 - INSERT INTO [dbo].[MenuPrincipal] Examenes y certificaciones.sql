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
           ,N'Exámenes de ubicación y certificaciones'
           ,N'Placement exams and certifications'
           ,1
           ,N'view_list'
           ,7
           ,N'item'
           ,N'/app/control-escolar/examenes-certificaciones'
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
	(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = N'/app/control-escolar/examenes-certificaciones'),
	GETDATE()
)
GO