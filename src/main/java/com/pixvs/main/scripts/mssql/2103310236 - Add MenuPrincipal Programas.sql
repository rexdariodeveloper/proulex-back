INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 
	1, 
	GETDATE(), 
	N'book', 
	(select MP_NodoId from MenuPrincipal where MP_TituloEN = N'Catalogs'), 
	9, 
	1000021, 
	N'Programas', 
	N'Programs', 
	N'item', 
	N'/app/catalogos/programas')
GO




