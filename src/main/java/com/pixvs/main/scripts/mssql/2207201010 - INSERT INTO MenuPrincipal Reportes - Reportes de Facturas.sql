INSERT INTO MenuPrincipal(MP_NodoPadreId, MP_Titulo, MP_TituloEN, MP_Activo, MP_Icono, MP_Orden, MP_Tipo, MP_CMM_SistemaAccesoId, MP_FechaCreacion, MP_Repetible, MP_Personalizado)
VALUES(1097, 'Reportes', 'Reports', 1, 'folder_open', 5, 'collapsable', 1000021, GETDATE(), 0, 0)
GO

INSERT INTO MenuPrincipal(MP_NodoPadreId, MP_Titulo, MP_TituloEN, MP_Activo, MP_Icono, MP_Orden, MP_Tipo, MP_URL, MP_CMM_SistemaAccesoId, MP_FechaCreacion, MP_Repetible, MP_Personalizado)
VALUES((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = 1097 AND MP_Titulo = 'Reportes'), 'Reportes de Facturas', 'Invoice Reports', 1, 'toc', 1, 'item', '/app/facturacion/reportes/reporte-facturas', 1000021, GETDATE(), 0, 0)
GO