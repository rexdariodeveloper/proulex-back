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
           ,N'Contratos'
           ,N'Contracts'
           ,1
           ,N'receipt'
           ,6
           ,N'item'
           ,'/app/programacion-academica/contratos'
           ,1000021
           ,GETDATE())
GO