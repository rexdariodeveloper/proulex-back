INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 
	1, 
	GETDATE(), 
	N'toc', 
	(select MP_NodoPadreId from MenuPrincipal where MP_Titulo = N'Reporte antigŁedad saldos'), 
	(select MAX(MP_Orden) + 1 from MenuPrincipal where MP_NodoPadreId = (select MP_NodoPadreId from MenuPrincipal where MP_Titulo = N'Reporte antigŁedad saldos')), 
	1000021, 
	N'Reporte pago a proveedores', 
	N'Provider payment report', 
	N'item', 
	N'/app/compras/reportes/pago-proveedores')
GO