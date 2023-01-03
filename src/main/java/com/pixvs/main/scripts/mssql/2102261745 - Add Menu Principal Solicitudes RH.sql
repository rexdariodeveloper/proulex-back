INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 
	1, 
	GETDATE(), 
	N'how_to_reg', 
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'Cuentas por Pagar'), 
	2, 
	1000021, 
	N'Solicitudes de pago rh', 
	N'Payment requests hr', 
	N'item', 
	N'/app/compras/solicitud-pago-rh')
GO



