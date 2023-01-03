SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER FUNCTION [dbo].[NumeroLetra]( @numero decimal(18,2) )
RETURNS varchar(180)
AS
BEGIN
  DECLARE @unidades TABLE (id int, value nvarchar(50));
  INSERT INTO @unidades VALUES (0,N'UN'),(1,N'DOS'),(2,N'TRES'),(3,N'CUATRO'),(4,N'CINCO'),(5,N'SEIS'),(6,N'SIETE'),(7,N'OCHO'),(8,N'NUEVE');
  DECLARE @diez TABLE (id int, value nvarchar(50));
  INSERT INTO @diez VALUES (0,N'DIEZ'),(1,N'ONCE'),(2,N'DOCE'),(3,N'TRECE'),(4,N'CATORCE'),(5,N'QUINCE');
  DECLARE @decenas TABLE (id int, value nvarchar(50));
  INSERT INTO @decenas VALUES (0,N'DIECI'),(1,N'VEINTI'),(2,N'TREINTA'),(3,N'CUARENTA'),(4,N'CINCUENTA'),(5,N'SESENTA'),(6,N'SETENTA'),(7,N'OCHENTA'),(8,N'NOVENTA');
  DECLARE @centenas TABLE (id int, value nvarchar(50));
  INSERT INTO @centenas VALUES (0,N'CIENTO'),(1,N'DOSCIENTOS'),(2,N'TRESCIENTOS'),(3,N'CUATROCIENTOS'),(4,N'QUINIENTOS'),(5,N'SEISCIENTOS'),(6,N'SETECIENTOS'),(7,N'OCHOCIENTOS'),(8,N'NOVECIENTOS');
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
        SET @texto = (select concat(value,@texto) from @decenas where id = @decena - 1)
    END
    ELSE IF(@decena = 2)
    BEGIN
      IF(@unidad = 0)
        SET @texto = 'VEINTE'
      ELSE
        SET @texto = (select concat(value,@texto) from @decenas where id = @decena - 1)
    END
    ELSE IF(@decena > 2)
      SET @texto = (select concat(value,case when @texto != '' then ' Y ' else '' end,@texto) from @decenas where id = @decena - 1)
    IF(@centena > 0)
    BEGIN
      IF(@centena = 1 AND @decena = 0 AND @unidad = 0)
        SET @texto = 'CIEN'
      ELSE
        SET @texto = (select concat(value,' ',@texto) from @centenas where id = @centena - 1)
    END
    IF((@terna = 1 OR @terna = 3) AND @texto != '')
      SET @texto = concat(@texto,' MIL ')
    IF(@terna = 2)
    BEGIN
      IF(@texto = 'UN' AND @entero = 0 AND @texto != '')
        SET @texto = concat(@texto,' MILLON ')
      ELSE
        SET @texto = concat(@texto,' MILLONES ')
    END
    SET @letras = concat(@texto,@letras);
    SET @terna = @terna + 1;
  END
  IF(@letras = 'UN MILLON ')
	SET @letras = concat(@letras,'DE')
  IF(@terna = 0)
    SET @letras = concat('CERO PESOS ',cast(@decimal as nvarchar(2)),'/100');
  ELSE IF(@letras = 'UN')
    SET @letras = concat('UN PESO ',cast(@decimal as nvarchar(2)),'/100');
  ELSE
    SET @letras = concat(@letras,' PESOS ',cast(@decimal as nvarchar(2)),'/100');
   return cast(@letras as varchar(180))
END