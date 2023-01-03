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
           ((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = N'Ventas')
           ,N'Reportes'
           ,N'Reports'
           ,1
           ,N'folder_open'
           ,99
           ,N'collapsable'
           ,NULL
           ,1000021
           ,GETDATE())
GO

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
           ((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = N'Reportes' AND MP_NodoPadreId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = N'Ventas'))
           ,N'Pagos alumnos'
           ,N'Student payments'
           ,1
           ,N'monetization_on'
           ,1
           ,N'item'
           ,N'/app/ventas/reportes/pagos-alumnos'
           ,1000021
           ,GETDATE())
GO