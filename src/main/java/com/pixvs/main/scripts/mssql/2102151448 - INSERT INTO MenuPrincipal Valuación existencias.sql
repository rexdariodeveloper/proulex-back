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
           ((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = N'Reportes' AND MP_NodoPadreId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = N'Inventario'))
           ,N'Valuación de inventario'
           ,N'Inventory valuation'
           ,1
           ,N'toc'
           ,(SELECT MAX(MP_Orden) + 1 FROM MenuPrincipal WHERE MP_NodoPadreId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = N'Reportes' AND MP_NodoPadreId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = N'Inventario')))
           ,N'item'
           ,N'/app/inventario/valuacion'
           ,1000021
           ,GETDATE())
GO