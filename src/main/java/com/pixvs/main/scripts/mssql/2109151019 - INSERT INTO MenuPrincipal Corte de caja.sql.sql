INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 
	1, 
	GETDATE(), 
	N'strikethrough_s', 
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'Ventas'), 
	(select MAX(MP_Orden) + 1 from MenuPrincipal where MP_NodoPadreId = (select MP_NodoId from MenuPrincipal where MP_Titulo = N'Ventas')), 
	1000021, 
	N'Corte de caja', 
	N'Cash closing', 
	N'item', 
	N'/app/ventas/cortes')
GO