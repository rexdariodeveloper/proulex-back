INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, GETDATE(), N'toc', (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Reportes' and MP_Orden = 4), 1, 1000021, N'Reporte ordenes compra', N'Purchase order report', N'item', N'/app/compras/reportes/ordenes-compra')
GO