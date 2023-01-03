/**
* Created by Angel Daniel Hernández Silva on 11/03/2022.
* Object: CREATE FUNCTION [dbo].[fn_getNivelAlumnoPermiteInscripcion]
* Description:  Funcion que retorna el nivel máximo que permite el sistema inscribir para un alumno teniendo
*               en cuenta el programa y el idioma.
*               Si el alumno terminó los niveles permitidos por el programa entonces regresa el nivel máximo permitible.
*               Si el alumno tiene una inscripciónn anterior, pero ya pasó la fecha límite de reinscripción entonces regresa el nivel de esa inscripción.
*               Si el alumno nunca se ha aprobado un curso en el idioma y programa a validar entonces regresa 1.
*/

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER FUNCTION [dbo].[fn_getNivelAlumnoPermiteInscripcion] (@alumnoId int, @idiomaId int, @programaId int)
RETURNS int
AS
BEGIN
    DECLARE @nivelActual int;
    DECLARE @nivelMaxCurso int;
    DECLARE @estatusNivelActual int;
    DECLARE @fechaLimiteReinscripciones date;

    -- Se guardan el nivel actual, estatus, último nivel del curso y fecha límite de reinscripción del último nivel cursado
    SELECT TOP 1
        @nivelActual = nivel,
        @estatusNivelActual = ALUG_CMM_EstatusId,
        @nivelMaxCurso = PROGI_NumeroNiveles,
        @fechaLimiteReinscripciones = fechaLimiteReinscripcion
    FROM Inscripciones
    INNER JOIN VW_ProgramasGrupos ON id = INS_PROGRU_GrupoId
    INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = programaIdiomaId
    INNER JOIN AlumnosGrupos ON
        ALUG_ALU_AlumnoId = INS_ALU_AlumnoId
        AND ALUG_PROGRU_GrupoId = id
    WHERE
        INS_ALU_AlumnoId = @alumnoId
        AND PROGI_PROG_ProgramaId = @programaId
        AND PROGI_CMM_Idioma = @idiomaId
    ORDER BY nivel DESC

    -- Si estatus = Aprobado => Validar fecha límite de reinscripción y nivel máximo del curso
    IF (@estatusNivelActual = 2000676) BEGIN
        
        -- Si el último nivel cursado es el último nivel del curso => retornar nivel actual
        IF (@nivelMaxCurso = @nivelActual) BEGIN
            RETURN COALESCE(@nivelActual,1)
        END

        -- Si ya pasó la fecha límite de reinscripción => retornar nivel actual
        IF (@fechaLimiteReinscripciones < GETDATE()) BEGIN
            RETURN COALESCE(@nivelActual,1)
        END

        -- Si el alumno aprobó, no es el último nivel del curso y no ha pasado la fecha límite de reinscripción => retornar siguiente nivel
        RETURN @nivelActual+1
    END
    
    -- Si estatus != Aprobado => Nivel máximo permisible = nivel actual
    RETURN COALESCE(@nivelActual,1)
END