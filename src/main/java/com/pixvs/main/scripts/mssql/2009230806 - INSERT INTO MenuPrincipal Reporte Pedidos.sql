SET IDENTITY_INSERT MenuPrincipal ON
INSERT INTO MenuPrincipal
(
    MP_NodoId,MP_NodoPadreId,MP_Titulo,MP_TituloEN,MP_Activo,MP_Icono,MP_Orden,MP_Tipo,MP_URL,MP_CMM_SistemaAccesoId,MP_FechaCreacion
)
VALUES
(
    13,11,'Reporte de Pedidos','Order report',1,'toc',7,'item','/app/inventario/reporte-pedidos',1000021,GETDATE()
)
SET IDENTITY_INSERT MenuPrincipal OFF
GO