SET IDENTITY_INSERT MenuPrincipal ON
INSERT INTO MenuPrincipal
	( MP_NodoId, MP_NodoPadreId, MP_Titulo, MP_TituloEN, MP_Activo, MP_Icono, MP_Orden, MP_Tipo, MP_URL, MP_CMM_SistemaAccesoId, MP_FechaCreacion )
VALUES
	( 24, 11, 'Pedidos'       , 'Orders'       , 1, 'redo', 6, 'item', '/app/inventario/pedidos', 1000021, GETDATE() ),
	( 25, 11, 'Surtir pedido' , 'Supply order' , 1, 'redo', 7, 'item', '/app/inventario/surtir' , 1000021, GETDATE() ),
	( 26, 11, 'Recibir pedido', 'Receive order', 1, 'redo', 8, 'item', '/app/inventario/recibir', 1000021, GETDATE() )
SET IDENTITY_INSERT MenuPrincipal OFF
GO

DECLARE @ident INT = ( SELECT MAX(MP_NodoId ) FROM MenuPrincipal )
DBCC CHECKIDENT( MenuPrincipal, RESEED, @ident )
GO

INSERT INTO Autonumericos
	( AUT_Nombre, AUT_Prefijo, AUT_Siguiente, AUT_Digitos, AUT_Activo )
VALUES
	( 'Pedidos', 'PDD', 1, 6, 1 )
GO

INSERT INTO Autonumericos
	( AUT_Nombre, AUT_Prefijo, AUT_Siguiente, AUT_Digitos, AUT_Activo )
VALUES
	( 'Pedidos recibos', 'PRD', 1, 6, 1 )
GO