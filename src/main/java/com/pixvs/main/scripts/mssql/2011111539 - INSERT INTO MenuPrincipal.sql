INSERT INTO MenuPrincipal
(
   MP_NodoPadreId,MP_Titulo,MP_TituloEN,MP_Activo,MP_Icono,MP_Orden,MP_Tipo,MP_URL,MP_CMM_SistemaAccesoId,MP_FechaCreacion
)
VALUES
(
	1,
	'Solicitudes de pago',
	'Payment requests',
	1,
	'input',
	(select MAX(MP_Orden) +1 from MenuPrincipal where MP_NodoPadreId = 1),
	'item',
	'/app/compras/solicitud-pago',
	1000021,
	GETDATE()
)