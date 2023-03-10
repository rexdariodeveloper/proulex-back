SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER FUNCTION [dbo].[fn_getAlumnoCursandoIdioma] (@alumnoId int, @idiomaId int, @programaId int)
RETURNS bit
AS
BEGIN
    DECLARE @alumnoCursandoIdioma bit = 0;
	DECLARE @registrado INT = 2000670;
	DECLARE @activo INT = 2000671;
	DECLARE @en_riesgo INT = 2000672;

    IF @programaId IS NULL BEGIN
        SELECT
            @alumnoCursandoIdioma = CAST(CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END AS bit)
        FROM Inscripciones
        INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
        INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
        INNER JOIN AlumnosGrupos ON
            ALUG_ALU_AlumnoId = INS_ALU_AlumnoId
            AND ALUG_PROGRU_GrupoId = PROGRU_GrupoId
            AND ALUG_CMM_EstatusId IN (@registrado,@activo,@en_riesgo) -- APROBADO
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
            AND ALUG_CMM_EstatusId IN (@registrado,@activo,@en_riesgo) -- APROBADO
        WHERE
            INS_ALU_AlumnoId = @alumnoId
            AND PROGI_PROG_ProgramaId = @programaId
            AND PROGI_CMM_Idioma = @idiomaId
        GROUP BY INS_ALU_AlumnoId, PROGI_PROG_ProgramaId, PROGI_CMM_Idioma
    END

    RETURN CAST(COALESCE(@alumnoCursandoIdioma,1) AS bit)
END