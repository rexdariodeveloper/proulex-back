SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/**
* Created by Angel Daniel Hernández Silva on 12/08/2022.
*/

CREATE OR ALTER FUNCTION [dbo].[fn_getAlumnoInterferenciaHorarioIgnorandoInscripcion] (@alumnoId int, @horarioId int, @inscripcionOmitirId int)
RETURNS bit
AS
BEGIN
    DECLARE @interferencia bit = 0;
    DECLARE @estatusAlumnoRegistrado INT = 2000670;
    DECLARE @estatusAlumnoActivo INT = 2000671;
    DECLARE @estatusAlumnoEnRiesgo INT = 2000672;


    SELECT
		@interferencia = CAST(CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END AS bit)
	FROM Alumnos
	INNER JOIN Inscripciones ON INS_ALU_AlumnoId = ALU_AlumnoId
	INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
	INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	INNER JOIN AlumnosGrupos ON
		ALUG_INS_InscripcionId = INS_InscripcionId
		AND ALUG_CMM_EstatusId IN (@estatusAlumnoRegistrado,@estatusAlumnoActivo,@estatusAlumnoEnRiesgo)
	LEFT JOIN OrdenesVentaCancelacionesDetalles ON OVCD_OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaDetalleId
	LEFT JOIN OrdenesVentaCancelaciones ON OVC_OrdenVentaCancelacionId = OVCD_OVC_OrdenVentaCancelacionId AND OVC_CMM_EstatusId = 2000720
	INNER JOIN PAModalidadesHorarios AS HorarioComparar ON HorarioComparar.PAMODH_PAModalidadHorarioId = @horarioId
	INNER JOIN PAModalidadesHorarios AS HorarioInscripcionActiva ON HorarioInscripcionActiva.PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
    INNER JOIN PAModalidades AS ModalidadComparar ON ModalidadComparar.PAMOD_ModalidadId = HorarioComparar.PAMODH_PAMOD_ModalidadId
	INNER JOIN PAModalidades AS ModalidadInscripcionActiva ON ModalidadInscripcionActiva.PAMOD_ModalidadId = HorarioInscripcionActiva.PAMODH_PAMOD_ModalidadId
	WHERE
		ALU_AlumnoId = @alumnoId
		AND OVC_OrdenVentaCancelacionId IS NULL
		AND HorarioComparar.PAMODH_HoraInicio < HorarioInscripcionActiva.PAMODH_HoraFin
		AND HorarioComparar.PAMODH_HoraFin > HorarioInscripcionActiva.PAMODH_HoraInicio
		AND (@inscripcionOmitirId IS NULL OR INS_InscripcionId != @inscripcionOmitirId)
		AND (
			(ModalidadComparar.PAMOD_Lunes = 1 AND ModalidadInscripcionActiva.PAMOD_Lunes = 1)
			OR (ModalidadComparar.PAMOD_Martes = 1 AND ModalidadInscripcionActiva.PAMOD_Martes = 1)
			OR (ModalidadComparar.PAMOD_Miercoles = 1 AND ModalidadInscripcionActiva.PAMOD_Miercoles = 1)
			OR (ModalidadComparar.PAMOD_Jueves = 1 AND ModalidadInscripcionActiva.PAMOD_Jueves = 1)
			OR (ModalidadComparar.PAMOD_Viernes = 1 AND ModalidadInscripcionActiva.PAMOD_Viernes = 1)
			OR (ModalidadComparar.PAMOD_Sabado = 1 AND ModalidadInscripcionActiva.PAMOD_Sabado = 1)
			OR (ModalidadComparar.PAMOD_Domingo = 1 AND ModalidadInscripcionActiva.PAMOD_Domingo = 1)
		)


    RETURN CAST(COALESCE(@interferencia,1) AS bit)
END
GO


CREATE OR ALTER FUNCTION [dbo].[fn_getAlumnoInterferenciaHorario] (@alumnoId int, @horarioId int)
RETURNS bit
AS
BEGIN
    DECLARE @interferencia bit = 0;
    SELECT @interferencia = [dbo].[fn_getAlumnoInterferenciaHorarioIgnorandoInscripcion](@alumnoId,@horarioId,NULL)
    RETURN CAST(COALESCE(@interferencia,1) AS bit)
END
GO