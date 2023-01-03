INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 
	1, 
	GETDATE(), 
	N'groups', 
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'Ventas'), 
	10, 
	1000021, 
	N'Listado de precios', 
	N'Price list', 
	N'item', 
	N'/app/catalogos/lista-precios')
GO