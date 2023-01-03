/**
 * Created by Angel Daniel Hernández Silva on 09/06/2022.
 */

CREATE OR ALTER VIEW [dbo].[VW_ProgramasGruposProfesores] AS
	SELECT
		ProfesorActual.PROGRUP_ProgramaGrupoProfesorId AS id,
		ProfesorActual.PROGRUP_PROGRU_GrupoId AS grupoId,
		ProfesorActual.PROGRUP_EMP_EmpleadoId AS empleadoId,
		ProfesorActual.PROGRUP_FechaInicio AS fechaInicio,
		COALESCE(DATEADD(DAY,-1,MIN(ProfesorSiguiente.PROGRUP_FechaInicio)),COALESCE(DATEADD(DAY,-1,PROGRU_FechaCancelacion),PROGRU_FechaFin)) AS fechaFin,
		ProfesorActual.PROGRUP_Sueldo AS sueldo,
		ProfesorActual.PROGRUP_FechaCreacion AS fechaCreacion,
		ProfesorActual.PROGRUP_Activo AS activo
	FROM ProgramasGruposProfesores AS ProfesorActual
	LEFT JOIN ProgramasGruposProfesores AS ProfesorSiguiente ON ProfesorSiguiente.PROGRUP_Activo = 1 AND ProfesorActual.PROGRUP_PROGRU_GrupoId = ProfesorSiguiente.PROGRUP_PROGRU_GrupoId AND ProfesorActual.PROGRUP_FechaInicio < ProfesorSiguiente.PROGRUP_FechaInicio
	INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = ProfesorActual.PROGRUP_PROGRU_GrupoId
	GROUP BY
		ProfesorActual.PROGRUP_ProgramaGrupoProfesorId, ProfesorActual.PROGRUP_PROGRU_GrupoId, ProfesorActual.PROGRUP_EMP_EmpleadoId,
		ProfesorActual.PROGRUP_FechaInicio, ProfesorActual.PROGRUP_FechaCreacion, PROGRU_FechaFin, ProfesorActual.PROGRUP_Sueldo,
		ProfesorActual.PROGRUP_Activo, PROGRU_FechaCancelacion
GO