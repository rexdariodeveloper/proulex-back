INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 
	1, 
	GETDATE(), 
	N'settings', 
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'CONFIGURACIÓN'), 
	3, 
	1000021, 
	N'Parámetros Empresa', 
	N'Company Parameters', 
	N'item', 
	N'/config/parametros-empresa')
GO




