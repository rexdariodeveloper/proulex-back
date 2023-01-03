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
           ,N'Deducciones y percepciones'
           ,N'Deductions and perceptions'
           ,1
           ,N'attach_money'
           ,4
           ,N'item'
           ,N'/app/control-escolar/deducciones-percepciones'
           ,1000021
           ,GETDATE())
GO