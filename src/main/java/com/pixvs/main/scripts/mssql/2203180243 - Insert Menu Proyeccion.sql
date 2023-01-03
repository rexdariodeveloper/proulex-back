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
           ((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_TituloEN = N'Academic programs' )
           ,N'Proyección de Grupos'
           ,N'Groups Projection'
           ,1
           ,N'settings'
           ,8
           ,N'item'
           ,'/app/programacion-academica/proyeccion-grupos'
           ,1000021
           ,GETDATE())
GO