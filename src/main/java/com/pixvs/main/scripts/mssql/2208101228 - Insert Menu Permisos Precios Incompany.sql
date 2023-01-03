SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] ON
GO

INSERT INTO [dbo].[MenuPrincipalPermisos]
           ([MPP_MenuPrincipalPermisoId]
		   ,[MPP_MP_NodoId]
           ,[MPP_Nombre]
           ,[MPP_Activo])
     VALUES
           (60
		   ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo='Precios Incompany')
           ,'Permitir editar precio venta'
           ,1),
		   (61
		   ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo='Precios Incompany')
           ,'Permitir editar porcentaje transporte'
           ,1)
GO

SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] OFF
GO