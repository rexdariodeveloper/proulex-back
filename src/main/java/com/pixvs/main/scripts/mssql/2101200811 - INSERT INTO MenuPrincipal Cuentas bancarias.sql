INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 
	1, 
	GETDATE(), 
	N'credit_card', 
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'Catálogos'), 
	(select MAX(MP_Orden) + 1 from MenuPrincipal where MP_NodoPadreId = (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Catálogos')), 
	1000021, 
	N'Cuentas bancarias', 
	N'Bank accounts', 
	N'item', 
	N'/app/catalogos/cuentas-bancarias')
GO