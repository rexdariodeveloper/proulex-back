/** 
	Author: Angel Daniel Hernández Silva
	Description: Se agrega el permiso "Visualizar órdenes de venta por permisos de sede" al menu de "Punto de venta"
**/

INSERT INTO [dbo].[MenuPrincipalPermisos] (
    [MPP_MP_NodoId],
    [MPP_Nombre],
    [MPP_Activo]
) VALUES(
    (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Punto de venta' and MP_Icono = 'shopping_cart' and MP_Orden = 3),
    'Visualizar órdenes de venta por permisos de sede',
    1
)
GO