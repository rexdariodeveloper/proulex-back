/*
    Agregamos los datos de la ref wiffi
    Se mostrara en el ticket, siempre y cuando el check de mostrar wiffi este activado
*/
ALTER TABLE sucursales ADD SUC_MostrarRed BIT NOT NULL DEFAULT 0
GO

ALTER TABLE sucursales ADD SUC_NombreRed VARCHAR(100) DEFAULT NULL
GO

ALTER TABLE sucursales ADD SUC_ContraseniaRed VARCHAR(100) DEFAULT NULL
GO