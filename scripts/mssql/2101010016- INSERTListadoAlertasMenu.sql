INSERT [dbo].[MenuPrincipal] (
	[MP_Activo], 
	[MP_FechaCreacion], 
	[MP_Icono], 
	[MP_NodoPadreId], 
	[MP_Orden], 
	[MP_CMM_SistemaAccesoId], 
	[MP_Titulo], 
	[MP_TituloEN], 
	[MP_Tipo], 
	[MP_URL]
) 
VALUES ( 
1, 
GETDATE(), 
'toc', 
(select MP_NodoId from MenuPrincipal where MP_Titulo = 'MÓDULOS'), 
(select MAX(MP_Orden) + 1 from MenuPrincipal where MP_NodoPadreId = (select MP_NodoId from MenuPrincipal where MP_Titulo = 'MÓDULOS')), 
1000021, 
'Listado de Alertas', 
'Alerts list', 
'item', 
'/app/compras/listado-alertas')
GO