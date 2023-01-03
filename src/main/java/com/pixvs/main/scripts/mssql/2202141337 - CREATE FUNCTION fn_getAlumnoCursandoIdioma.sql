/**
* Created by Angel Daniel Hernández Silva on 14/02/2022.
* Object: CREATE FUNCTION [dbo].[fn_getAlumnoCursandoIdioma]
* Description:  Funcion que retorna si el alumno está cursando el idioma indicado ya sea en un programa en específico o en general.
*               Si se indica el programa como parámetro, se regresa si el alumno está cursando el idioma en ese programa.
*               Si no se indica el programa como parámetro (se manda NULL), se regresa si el alumno está cursando el idioma en cualquier programa.
*/

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER FUNCTION [dbo].[fn_getAlumnoCursandoIdioma] (@alumnoId int, @idiomaId int, @programaId int)
RETURNS bit
AS
BEGIN
    DECLARE @alumnoCursandoIdioma bit = 0;

    IF @programaId IS NULL BEGIN
        SELECT
            @alumnoCursandoIdioma = CAST(CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END AS bit)
        FROM Inscripciones
        INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
        INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
        INNER JOIN AlumnosGrupos ON
            ALUG_ALU_AlumnoId = INS_ALU_AlumnoId
            AND ALUG_PROGRU_GrupoId = PROGRU_GrupoId
            AND ALUG_CMM_EstatusId IN (2000670,2000674) -- APROBADO
        WHERE
            INS_ALU_AlumnoId = @alumnoId
            AND PROGI_CMM_Idioma = @idiomaId
        GROUP BY INS_ALU_AlumnoId, PROGI_CMM_Idioma
    END ELSE BEGIN
        SELECT
            @alumnoCursandoIdioma = CAST(CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END AS bit)
        FROM Inscripciones
        INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
        INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
        INNER JOIN AlumnosGrupos ON
            ALUG_ALU_AlumnoId = INS_ALU_AlumnoId
            AND ALUG_PROGRU_GrupoId = PROGRU_GrupoId
            AND ALUG_CMM_EstatusId IN (2000670,2000674) -- APROBADO
        WHERE
            INS_ALU_AlumnoId = @alumnoId
            AND PROGI_PROG_ProgramaId = @programaId
            AND PROGI_CMM_Idioma = @idiomaId
        GROUP BY INS_ALU_AlumnoId, PROGI_PROG_ProgramaId, PROGI_CMM_Idioma
    END

    RETURN CAST(COALESCE(@alumnoCursandoIdioma,1) AS bit)
END