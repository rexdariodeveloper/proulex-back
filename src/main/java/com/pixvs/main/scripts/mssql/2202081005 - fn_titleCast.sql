SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER FUNCTION [dbo].[fn_titleCast] (@inputString VARCHAR(1000), @castAll BIT)
RETURNS VARCHAR(1000)
AS
BEGIN
    DECLARE @outputString VARCHAR(255)
BEGIN
/**
    @inputString: valor a convertir
    @castAll: convertir monosilabos listados, se pueden agregar
*/
    SET @inputString = (SELECT TRIM(@inputString))
    IF @inputString IS NULL OR LEN(@inputString) = 0
        SELECT @outputString = ''
    ELSE
        IF @castAll = 0
            SET @outputString = (SELECT STRING_AGG(newWord, ' ') FROM
            (
			SELECT
			CASE WHEN word IN('de', 'del', 'el', 'la', 'las', 'los')
			THEN word
			WHEN LEN(word) = 1 THEN word
			ELSE ( UPPER(LEFT(word, 1)) + LOWER(RIGHT(word, LEN(word) - 1)) )
			END newWord
            FROM (SELECT LOWER(value) AS word from STRING_SPLIT(@inputString, ' ')) AS X ) AS c)
        ELSE
            SET  @outputString = (SELECT STRING_AGG(newWord, ' ') FROM
                        (SELECT
                        CASE WHEN LEN(word) = 1
                            THEN UPPER(word)
                            ELSE UPPER(LEFT(word, 1)) + LOWER(RIGHT(word, LEN(word) - 1))
                        END newWord
                        FROM (SELECT LOWER(value) AS word from STRING_SPLIT(@inputString, ' '))
                        AS X ) AS c)
        -- END
    END

    RETURN @outputString
END