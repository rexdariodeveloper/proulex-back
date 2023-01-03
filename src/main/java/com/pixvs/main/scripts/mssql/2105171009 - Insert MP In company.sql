INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 
	1, 
	GETDATE(), 
	N'calendar_today', 
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'PROGRAMACIÓN ACADÉMICA'), 
	4, 
	1000021, 
	N'In company', 
	N'In company', 
	N'item', 
	N'/app/programacion-academica/incompany')
GO