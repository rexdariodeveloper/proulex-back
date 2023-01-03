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
           ,N'Captura de calificaciones'
           ,N'School grades'
           ,1
           ,N'school'
           ,2
           ,N'item'
           ,N'/app/control-escolar/calificaciones'
           ,1000021
           ,GETDATE())
GO