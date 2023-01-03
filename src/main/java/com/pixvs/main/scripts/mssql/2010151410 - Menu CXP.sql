
INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, GETDATE(), N'payment', (select MP_NodoId from MenuPrincipal where MP_Titulo = 'MÓDULOS'), 6, 1000021, N'Cuentas por Pagar', N'Accounts payable', N'collapsable', NULL)
GO

UPDATE MenuPrincipal
SET MP_NodoPadreId = (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Cuentas por Pagar' and MP_Orden = 6)
WHERE
	(MP_Titulo = 'Gestión de facturas' and MP_Icono = 'view_list' and MP_Orden = 4)
	OR (MP_Titulo = 'Programación de pagos' and MP_Icono = 'calendar_today' and MP_Orden = 5)
	OR (MP_Titulo = 'Pago a proveedores' and MP_Icono = 'payment' and MP_Orden = 6)
GO