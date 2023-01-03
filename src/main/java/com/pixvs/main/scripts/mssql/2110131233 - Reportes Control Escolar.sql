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
           ((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = N'Reportes' AND MP_NodoPadreId=(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = N'Control escolar'))
           ,N'Reportes de grupos'
           ,N'Groups reports'
           ,1
           ,N'supervisor_account'
           ,99
           ,N'item'
           ,'/app/control-escolar/reportes/grupos'
           ,1000021
           ,GETDATE())
GO