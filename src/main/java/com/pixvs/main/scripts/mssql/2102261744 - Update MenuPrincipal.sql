UPDATE MenuPrincipal
SET MP_Orden = 3
WHERE MP_Titulo='Gesti�n de facturas' and MP_NodoPadreId=1027;

UPDATE MenuPrincipal
SET MP_Orden = 4
WHERE MP_Titulo='Programaci�n de pagos' and MP_NodoPadreId=1027;

UPDATE MenuPrincipal
SET MP_Orden = 5
WHERE MP_Titulo='Pago a proveedores' and MP_NodoPadreId=1027;

UPDATE MenuPrincipal
SET MP_Orden = 6
WHERE MP_Titulo='Reportes' and MP_NodoPadreId=1027;
