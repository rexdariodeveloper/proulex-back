SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/**
* Created by Angel Daniel Hernández Silva on 22/07/2022.
*/

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
            ALUG_INS_InscripcionId = INS_InscripcionId
            AND ALUG_CMM_EstatusId IN (@registrado,@activo,@en_riesgo)
        INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
        LEFT JOIN OrdenesVentaCancelacionesDetalles ON OVCD_OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaDetalleId
        LEFT JOIN OrdenesVentaCancelaciones ON OVC_OrdenVentaCancelacionId = OVCD_OVC_OrdenVentaCancelacionId AND OVC_CMM_EstatusId = 2000720
        LEFT JOIN ControlesMaestrosMultiples AS CMMJOBS ON CMMJOBS.CMM_Control = 'CMM_SUC_SucursalJOBSId'
        LEFT JOIN ControlesMaestrosMultiples AS CMMJOBSSEMS ON CMMJOBSSEMS.CMM_Control = 'CMM_SUC_SucursalJOBSSEMSId'
        WHERE
            OVC_OrdenVentaCancelacionId IS NULL
            AND PROGRU_SUC_SucursalId != CAST(COALESCE(CMMJOBS.CMM_Valor,'0') AS int)
            AND PROGRU_SUC_SucursalId != CAST(COALESCE(CMMJOBSSEMS.CMM_Valor,'0') AS int)
            AND COALESCE(PROG_PCP,0) = 0
            AND INS_ALU_AlumnoId = @alumnoId
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
        LEFT JOIN OrdenesVentaCancelacionesDetalles ON OVCD_OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaDetalleId
        LEFT JOIN OrdenesVentaCancelaciones ON OVC_OrdenVentaCancelacionId = OVCD_OVC_OrdenVentaCancelacionId AND OVC_CMM_EstatusId = 2000720
        WHERE
            OVC_OrdenVentaCancelacionId IS NULL
            AND INS_ALU_AlumnoId = @alumnoId
            AND PROGI_PROG_ProgramaId = @programaId
            AND PROGI_CMM_Idioma = @idiomaId
        GROUP BY INS_ALU_AlumnoId, PROGI_PROG_ProgramaId, PROGI_CMM_Idioma
    END

    RETURN CAST(COALESCE(@alumnoCursandoIdioma,1) AS bit)
END
GO


