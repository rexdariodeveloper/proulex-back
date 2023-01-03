/**
* Created by Angel Daniel Hernández Silva on 11/02/2022.
* Object: CREATE FUNCTION [dbo].[fn_getNivelAlumnoPermiteInscripcion]
* Description:  Funcion que retorna el nivel máximo que permite el sistema inscribir para un alumno teniendo
*               en cuenta el programa y el idioma.
*               Si el alumno terminó los niveles permitidos por el programa entonces regresa el nivel máximo permitible.
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
    DECLARE @siguienteNivel int;

    SELECT
        @siguienteNivel = CASE WHEN MAX(PROGRU_Nivel)+1 > PROGI_NumeroNiveles THEN PROGI_NumeroNiveles ELSE MAX(PROGRU_Nivel)+1 END
    FROM Inscripciones
    INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
    INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
    INNER JOIN AlumnosGrupos ON
        ALUG_ALU_AlumnoId = INS_ALU_AlumnoId
        AND ALUG_PROGRU_GrupoId = PROGRU_GrupoId
        AND ALUG_CMM_EstatusId = 2000676 -- APROBADO
    WHERE
        INS_ALU_AlumnoId = @alumnoId
        AND PROGI_PROG_ProgramaId = @programaId
        AND PROGI_CMM_Idioma = @idiomaId
    GROUP BY INS_ALU_AlumnoId, PROGI_PROG_ProgramaId, PROGI_CMM_Idioma, PROGI_NumeroNiveles

    RETURN COALESCE(@siguienteNivel,1)  
END