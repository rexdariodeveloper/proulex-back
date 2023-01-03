CREATE OR ALTER   FUNCTION [dbo].[getNumeroLetra]( @numero decimal(18,2),  @tipo varchar(20), @umSingular varchar(50), @umPlural varchar(50), @idioma varchar(3) )
RETURNS varchar(1000)
AS
BEGIN
  --declare @numero decimal(18,2) = 0.10
  --declare @tipo varchar(20)  = 'CANTIDAD'  --MONEDA, CANTIDAD, ENTERO, DECIMAL, FECHA
  --declare @umSingular varchar(50) = 'Kilo'  --PESO, DOLAR, EURO
  --declare @umPlural varchar(50)  = 'Kilos'  --PESOS, DOLARES, EUROS
  --declare @idioma varchar(50)  = 'es'  --PESOS, DOLARES, EUROS

  DECLARE @unidades TABLE (id int, value nvarchar(50));
  INSERT INTO @unidades VALUES (0,N'Uno'),(1,N'Dos'),(2,N'Tres'),(3,N'Cuatro'),(4,N'Cinco'),(5,N'Seis'),(6,N'Siete'),(7,N'Ocho'),(8,N'Nueve');
  DECLARE @diez TABLE (id int, value nvarchar(50));
  INSERT INTO @diez VALUES (0,N'Diez'),(1,N'Once'),(2,N'Doce'),(3,N'Trece'),(4,N'Catorce'),(5,N'Quince');
  DECLARE @decenas TABLE (id int, value nvarchar(50));
  INSERT INTO @decenas VALUES (0,N'Dieci'),(1,N'Veinti'),(2,N'Treinta'),(3,N'Cuarenta'),(4,N'Cincuenta'),(5,N'Sesenta'),(6,N'Setenta'),(7,N'Ochenta'),(8,N'Noventa');
  DECLARE @centenas TABLE (id int, value nvarchar(50));
  INSERT INTO @centenas VALUES (0,N'Ciento'),(1,N'Doscientos'),(2,N'Trescientos'),(3,N'Cuatrocientos'),(4,N'Quinientos'),(5,N'Seiscientos'),(6,N'Setecientos'),(7,N'Ochocientos'),(8,N'Novecientos');
  DECLARE @entero int = CAST(@numero as int);
  DECLARE @decimal int = (@numero - @entero) * 100;
    DECLARE @letras nvarchar(180) = '';
  DECLARE @terna int = 0;
  WHILE @entero > 0
  BEGIN
    DECLARE @texto nvarchar(180) = '';
    DECLARE @unidad int = @entero % 10;
    SET @entero = CAST(@entero / 10 as int);
    DECLARE @decena int = @entero % 10;
    SET @entero = CAST(@entero / 10 as int);
    DECLARE @centena int = @entero % 10;
    SET @entero = CAST(@entero / 10 as int);
    IF(@unidad > 0)
      SET @texto = (select value from @unidades where id = @unidad - 1)
    IF(@decena = 1)
    BEGIN
      IF(@unidad < 6)
        SET @texto = (select value from @diez where id = @unidad)
      ELSE
        SET @texto = (select concat(value,(CASE WHEN RIGHT(value, 1) = ' ' THEN @texto ELSE LOWER(@texto) END)) from @decenas where id = @decena - 1)
    END
    ELSE IF(@decena = 2)
    BEGIN
      IF(@unidad = 0)
        SET @texto = 'Veinte'
      ELSE
        SET @texto = (select concat(value,(CASE WHEN RIGHT(value, 1) = ' ' THEN @texto ELSE LOWER(@texto) END)) from @decenas where id = @decena - 1)
    END
    ELSE IF(@decena > 2)
      SET @texto = (select concat(value,case when @texto != '' then ' Y ' else '' end,@texto) from @decenas where id = @decena - 1)
    IF(@centena > 0)
    BEGIN
      IF(@centena = 1 AND @decena = 0 AND @unidad = 0)
        SET @texto = 'Cien'
      ELSE
        SET @texto = (select concat(value,' ',@texto) from @centenas where id = @centena - 1)
    END
    IF((@terna = 1 OR @terna = 3) AND @texto != '')
      SET @texto = concat(@texto,' Mil ')
    IF(@terna = 2)
    BEGIN
      IF(@texto = 'Uno' AND @entero = 0 AND @texto != '')
        SET @texto = concat('Un',' Mill�n ')
      ELSE
        SET @texto = concat(@texto,' Millones ')
    END
    SET @letras = concat(@texto,@letras);
    SET @terna = @terna + 1;
  END

  /*FECHA*/
  IF(@letras = 'Uno' AND @tipo = 'FECHA')
	SET @letras = 'Primero'


  /*MONEDA - CANTIDAD*/
  IF(@letras = 'Un Mill�n ' AND @tipo in ('MONEDA', 'CANTIDAD'))
	SET @letras = concat(@letras,'de')

  /*MONEDA*/
  IF(@terna = 0 AND @tipo = 'MONEDA')
    SET @letras = concat('Cero '+COALESCE(@umPlural,'')+' ',cast(@decimal as nvarchar(2)),'/100');
  ELSE IF(@letras = 'Uno' AND @tipo = 'MONEDA')
    SET @letras = concat('UN '+COALESCE(@umSingular,'')+' ',cast(@decimal as nvarchar(2)),'/100');
  ELSE IF(@tipo = 'MONEDA')
    SET @letras = concat(@letras,' '+COALESCE(@umPlural,'')+' ',cast(@decimal as nvarchar(2)),'/100');

  /*CANTIDAD*/
  IF(@terna = 0 AND @tipo = 'CANTIDAD') BEGIN
    SET @letras = 'Cero ';
	IF(@decimal > 0) BEGIN
		SET @letras += concat('punto ',dbo.[getNumeroLetra](@decimal, 'ENTERO', null, null , @idioma),' ')
	END
	SET @letras += COALESCE(@umPlural,'')
  END
  ELSE IF(@letras = 'Uno' AND @tipo = 'CANTIDAD') BEGIN
    SET @letras = 'UN ';
	IF(@decimal > 0) BEGIN
		SET @letras += concat('punto ',dbo.[getNumeroLetra](@decimal, 'ENTERO', null, null , @idioma),' ')
	END
	SET @letras += COALESCE(@umSingular,'')
  END
  ELSE IF(@tipo = 'CANTIDAD') BEGIN
    SET @letras = concat(@letras,' ');
	IF(@decimal > 0) BEGIN
		SET @letras += concat('punto ',dbo.[getNumeroLetra](@decimal, 'ENTERO', null, null , @idioma),' ')
	END
	SET @letras += COALESCE(@umPlural,'')
  END

  SET @letras = REPLACE(@letras,'Dieciseis', 'Dieciséis')
  SET @letras = REPLACE(@letras,'Veintidos', 'Veintidós')
  SET @letras = REPLACE(@letras,'Veintitres', 'Veintitrés')
  SET @letras = REPLACE(@letras,'Veintiséis', 'Veintiséis')

  RETURN RTRIM(LTRIM(cast(@letras as varchar(1000))))

END
GO