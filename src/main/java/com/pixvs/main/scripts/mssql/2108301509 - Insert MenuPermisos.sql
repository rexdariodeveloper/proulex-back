INSERT INTO [dbo].[MenuPrincipalPermisos]
           ([MPP_MP_NodoId]
           ,[MPP_Nombre]
           ,[MPP_Activo])
     VALUES
           ((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo='Grupos')
           ,'Permitir agregar grupo multisede'
           ,1),
		   ((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo='Grupos')
           ,'Permitir modificar el sueldo del profesor'
           ,1)
GO


