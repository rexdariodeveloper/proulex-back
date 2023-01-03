IF NOT EXISTS (SELECT * FROM Autonumericos WHERE AUT_Prefijo = 'BPIX')
BEGIN
    INSERT INTO Autonumericos(AUT_Nombre, AUT_Prefijo, AUT_Siguiente, AUT_Digitos, AUT_Activo)
	VALUES('PIXVS Baja', 'BPIX', 1, 6, 1)
END
GO