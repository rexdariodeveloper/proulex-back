UPDATE MenuPrincipal
SET MP_URL='/app/ventas/lista-precios'
WHERE MP_TituloEN='Price list';

INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 
	1, 
	GETDATE(), 
	N'strikethrough_s', 
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'Ventas'), 
	10, 
	1000021, 
	N'Descuentos', 
	N'Discounts', 
	N'item', 
	N'/app/ventas/descuentos')
GO