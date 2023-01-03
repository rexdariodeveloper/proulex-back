INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 
	1, 
	GETDATE(), 
	N'store', 
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'MÓDULOS'),
	9, 
	1000021, 
	N'Programación académica', 
	N'Academic programs', 
	N'collapsable', 
	null)
GO

INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 
	1, 
	GETDATE(), 
	N'work_outline', 
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'PROGRAMACIÓN ACADÉMICA'), 
	1, 
	1000021, 
	N'Cursos', 
	N'Courses', 
	N'item', 
	N'/app/programacion-academica/cursos')
GO
