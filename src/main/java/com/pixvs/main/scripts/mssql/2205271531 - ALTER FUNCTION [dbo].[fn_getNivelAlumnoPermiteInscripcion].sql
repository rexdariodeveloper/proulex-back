SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER FUNCTION [dbo].[fn_getNivelAlumnoPermiteInscripcion] (@alumnoId int, @idiomaId int, @programaId int)
RETURNS int
AS
BEGIN
    DECLARE @nivelActual int;
    DECLARE @nivelMaxCurso int;
    DECLARE @estatusNivelActual int;
    DECLARE @fechaLimiteReinscripciones date;

	DECLARE @aprobado INT = 2000675;

    -- Se guardan el nivel actual, estatus, �ltimo nivel del curso y fecha l�mite de reinscripci�n del �ltimo nivel cursado
    SELECT TOP 1
        @nivelActual = nivel,
        @estatusNivelActual = ALUG_CMM_EstatusId,
        @nivelMaxCurso = PROGI_NumeroNiveles,
        @fechaLimiteReinscripciones = fechaLimiteReinscripcion
    FROM Inscripciones
    INNER JOIN VW_ProgramasGrupos ON id = INS_PROGRU_GrupoId
    INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = programaIdiomaId
    INNER JOIN AlumnosGrupos ON
        ALUG_INS_InscripcionId = INS_InscripcionId
    WHERE
        INS_ALU_AlumnoId = @alumnoId
        AND PROGI_PROG_ProgramaId = @programaId
        AND PROGI_CMM_Idioma = @idiomaId
    ORDER BY nivel DESC

    -- Si estatus = Aprobado => Validar fecha l�mite de reinscripci�n y nivel m�ximo del curso
    IF (@estatusNivelActual = @aprobado) BEGIN
        
        -- Si el �ltimo nivel cursado es el �ltimo nivel del curso => retornar nivel actual
        IF (@nivelMaxCurso = @nivelActual) BEGIN
            RETURN COALESCE(@nivelActual,1)
        END

        -- Si ya pas� la fecha l�mite de reinscripci�n => retornar nivel actual
        IF (@fechaLimiteReinscripciones < GETDATE()) BEGIN
            RETURN COALESCE(@nivelActual,1)
        END

        -- Si el alumno aprob�, no es el �ltimo nivel del curso y no ha pasado la fecha l�mite de reinscripci�n => retornar siguiente nivel
        RETURN @nivelActual+1
    END
    
    -- Si estatus != Aprobado => Nivel m�ximo permisible = nivel actual
    RETURN COALESCE(@nivelActual,1)
END
GO


