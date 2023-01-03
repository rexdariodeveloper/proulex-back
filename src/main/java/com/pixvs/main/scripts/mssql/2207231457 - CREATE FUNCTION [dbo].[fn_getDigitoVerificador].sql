CREATE OR ALTER FUNCTION [dbo].[fn_getDigitoVerificador](@codigo NVARCHAR(50), @tipoSuma INT)
RETURNS NVARCHAR(50)
AS
BEGIN
	IF @codigo IS NULL
		RETURN NULL;
	IF LEN(@codigo) < 2 OR LEN(@codigo) > 30
		RETURN NULL
	IF @codigo LIKE '%[^a-zA-Z0-9]%'
		RETURN NULL
	DECLARE @referencia NVARCHAR(50) = NULL;
	DECLARE @equivalencias TABLE( caracter CHAR, valor INT);
	INSERT INTO @equivalencias VALUES 
	('A',1), ('B',2), ('C',3), ('D',4), ('E',5), ('F',6), ('G',7), ('H',8), ('I',9),
	('J',1), ('K',2), ('L',3), ('M',4), ('N',5), ('O',6), ('P',7), ('Q',8), ('R',9),
	('S',1), ('T',2), ('U',3), ('V',4), ('W',5), ('X',6), ('Y',7), ('Z',8),
	('1',1), ('2',2), ('3',3), ('4',4), ('5',5), ('6',6), ('7',7), ('8',8), ('9',9), ('0',0);
	DECLARE @iterator INT = 0;
	DECLARE @total INT = 0;
	DECLARE @reversed NVARCHAR(50) = UPPER(REVERSE(@codigo));
	WHILE @iterator < LEN(@reversed)
	BEGIN
		SET @iterator = @iterator + 1;
		DECLARE @c CHAR = substring(@codigo, @iterator, 1);
		DECLARE @mult INT = (2 - (@iterator % 2));
		IF @tipoSuma = 0
			SET @total = @total + (SELECT (valor * @mult) from @equivalencias WHERE caracter = @c);
		ELSE
			SET @total = @total + (SELECT (((valor * @mult) / 10) + ((valor * @mult) % 10)) from @equivalencias WHERE caracter = @c);
	END
	DECLARE @digito INT = (((@total / 10) + 1) * 10) - @total;
	RETURN CONCAT(@codigo, CASE WHEN @digito > 9 THEN 0 ELSE @digito END);
END