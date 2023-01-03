/**
 * Created by Angel Daniel Hernández Silva on 30/06/2021.
 * Object:  INSERT INTO [dbo].[MenuPrincipal] PuntoVenta
 */

/*********************************/
/***** UPDATE - Orden ventas *****/
/*********************************/

UPDATE [dbo].[MenuPrincipal] SET MP_Orden = 1
WHERE
	MP_NodoPadreId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId IS NULL AND MP_Titulo = 'Ventas' AND MP_Orden = 11)
	AND MP_Titulo = 'Listado de precios'
GO

UPDATE [dbo].[MenuPrincipal] SET MP_Orden = 2
WHERE
	MP_NodoPadreId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId IS NULL AND MP_Titulo = 'Ventas' AND MP_Orden = 11)
	AND MP_Titulo = 'Descuentos'
GO

/***********************************/
/***** INSERT - Punto de venta *****/
/***********************************/

INSERT [dbo].[MenuPrincipal] (
    [MP_Activo],
    [MP_FechaCreacion],
    [MP_Icono],
    [MP_NodoPadreId],
    [MP_Orden],
    [MP_CMM_SistemaAccesoId],
    [MP_Titulo],
    [MP_TituloEN],
    [MP_Tipo],
    [MP_URL]
) 
VALUES (
    1, -- [MP_Activo]
    GETDATE(), -- [MP_FechaCreacion]
    N'shopping_cart', -- [MP_Icono]
    (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId IS NULL AND MP_Titulo = 'Ventas' AND MP_Orden = 11), -- [MP_NodoPadreId]
    3, -- [MP_Orden]
    1000021, -- [MP_CMM_SistemaAccesoId]
    N'Punto de venta', -- [MP_Titulo]
    N'Sale point', -- [MP_TituloEN]
    N'item', -- [MP_Tipo]
    N'/app/ventas/punto-venta' -- [MP_URL]
)
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Punto de venta' and MP_Icono = 'shopping_cart' and MP_Orden = 3)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO

/********************************/
/***** Reubicar menú Ventas *****/
/********************************/

UPDATE MenuPrincipal SET
	MP_NodoPadreId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId IS NULL AND MP_Titulo = 'MÓDULOS' AND MP_Orden = 2),
	MP_Orden = 11
WHERE MP_NodoPadreId IS NULL AND MP_Titulo = 'Ventas' AND MP_Orden = 11
GO