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
           ,N'Reportes de becas'
           ,N'Scholarships reports'
           ,1
           ,N'account_balance'
           ,5
           ,N'item'
           ,'/app/control-escolar/reportes/becas'
           ,1000021
           ,GETDATE())
GO