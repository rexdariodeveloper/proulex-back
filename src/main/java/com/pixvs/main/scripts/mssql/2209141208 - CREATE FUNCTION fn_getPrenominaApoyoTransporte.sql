
/**
* Created by Angel Daniel Hernández Silva on 30/08/2022.
*/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getPrenominaApoyoTransporte] (@fechaInicioQuincena date, @fechaFinQuincena date)
RETURNS @tablaTMP table(
    idEmpleado int,
    codigoEmpleado varchar(80),
    idSucursal int,
    sucursal varchar(250),
    idGrupo int,
    codigoGrupo varchar(80),
    empleado varchar(250),
    deduccionPercepcionId int,
    tabulador varchar(80),
    nombreGrupo varchar(250),
    horasPagadas decimal(5,2),
    percepcion varchar(250),
    deduccion varchar(250),
    idioma varchar(250),
    fechaDiaFestivo date,
    fechaDiaSuplida date,
    categoria varchar(10),
    sueldoPorHora varchar(250),
    idSuplencia int,
    fechaInicioPeriodo date,
    fechaFinPeriodo date,
    sueldoPorHoraDecimal decimal(10,2),
    tipoMovimientoId int,
    movimientoReferenciaId int,
    referenciaProcesoTabla varchar(100),
    referenciaProcesoId int,
    modalidad varchar(40),
    grupoFechaInicio date,
    grupoFechaFin date
)

AS BEGIN
    INSERT @tablaTMP
	SELECT
		idEmpleado,
        EMP_CodigoEmpleado AS codigoEmpleado,
        idSucursal,
        SUC_Nombre AS sucursal,
        idGrupo,
        codigoGrupo,
        CONCAT(EMP_PrimerApellido,' ' + EMP_SegundoApellido, ' ' + EMP_Nombre) AS empleado,
        NULL AS deduccionPercepcionId,
        TAB_Codigo AS tabulador,
        CONCAT(PROG_Codigo,' ',CMM_Valor,' ',PAMOD_Nombre,' Nivel ',FORMAT(PROGRU_Nivel,'00'),' Grupo ',FORMAT(PROGRU_Grupo,'00')) AS nombreGrupo,
        horas AS horasPagadas,
        FORMAT((sueldo * horas * PROGRU_PorcentajeApoyoTransporte / 100), 'C', 'en-us') AS percepcion,
        FORMAT(0, 'C', 'en-us') AS deduccion,
        CMM_Valor AS idioma,
        NULL AS fechaDiaFestivo,
        NULL AS fechaDiaSuplida,
        PROGRU_CategoriaProfesor AS categoria,
        FORMAT(sueldo, 'C', 'en-us') AS sueldoPorHora,
        NULL AS idSuplencia,
        fechaInicioPeriodo AS fechaInicioPeriodo,
        fechaFinPeriodo AS fechaFinPeriodo,
        sueldo AS sueldoPorHoraDecimal,
        2000698 AS tipoMovimientoId, -- Tipo: Apoyo para transporte
        NULL AS movimientoReferenciaId,
        'ProgramasGrupos' AS referenciaProcesoTabla,
        idGrupo AS referenciaProcesoId,
		PAMOD_Nombre AS modalidad,
		PROGRU_FechaInicio AS grupoFechaInicio,
		PROGRU_FechaFin AS grupoFechaFin
	FROM(
		SELECT
			idEmpleado,
			idSucursal,
			idGrupo,
			codigoGrupo,
			sueldo,
			fechaInicioPeriodo,
			fechaFinPeriodo,
			SUM(horasDomingo + horasLunes + horasMartes + horasMiercoles + horasJueves + horasViernes + horasSabado) AS horas
		FROM(
			SELECT *,
				CASE WHEN dia = 1 THEN diasDomingo * horas ELSE 0 END AS horasDomingo,
				CASE WHEN dia = 2 THEN diasLunes * horas ELSE 0 END AS horasLunes,
				CASE WHEN dia = 3 THEN diasMartes * horas ELSE 0 END AS horasMartes,
				CASE WHEN dia = 4 THEN diasMiercoles * horas ELSE 0 END AS horasMiercoles,
				CASE WHEN dia = 5 THEN diasJueves * horas ELSE 0 END AS horasJueves,
				CASE WHEN dia = 6 THEN diasViernes * horas ELSE 0 END AS horasViernes,
				CASE WHEN dia = 7 THEN diasSabado * horas ELSE 0 END AS horasSabado
			FROM(
				SELECT
					empleadoId AS idEmpleado,
					PROGRU_SUC_SucursalId AS idSucursal,
					grupoId AS idGrupo,
					PROGRU_Codigo AS codigoGrupo,
					sueldo,
					CASE
						WHEN fechaInicio > @fechaInicioQuincena THEN fechaInicio
						ELSE @fechaInicioQuincena
					END AS fechaInicioPeriodo,
					CASE
						WHEN fechaFin < @fechaFinQuincena THEN fechaFin
						ELSE @fechaFinQuincena
					END AS fechaFinPeriodo,
					(
						SELECT COUNT(*)
						FROM [dbo].[fn_getDiaHabiles](
							CASE WHEN fechaInicio > @fechaInicioQuincena THEN fechaInicio ELSE @fechaInicioQuincena END,
							CASE WHEN fechaFin < @fechaFinQuincena THEN fechaFin ELSE @fechaFinQuincena END,
							PAMOD_Domingo,0,0,0,0,0,0
						) WHERE fecha IS NOT NULL
					) AS diasDomingo,
					(
						SELECT COUNT(*)
						FROM [dbo].[fn_getDiaHabiles](
							CASE WHEN fechaInicio > @fechaInicioQuincena THEN fechaInicio ELSE @fechaInicioQuincena END,
							CASE WHEN fechaFin < @fechaFinQuincena THEN fechaFin ELSE @fechaFinQuincena END,
							0,PAMOD_Lunes,0,0,0,0,0
						) WHERE fecha IS NOT NULL
					) AS diasLunes,
					(
						SELECT COUNT(*)
						FROM [dbo].[fn_getDiaHabiles](
							CASE WHEN fechaInicio > @fechaInicioQuincena THEN fechaInicio ELSE @fechaInicioQuincena END,
							CASE WHEN fechaFin < @fechaFinQuincena THEN fechaFin ELSE @fechaFinQuincena END,
							0,0,PAMOD_Martes,0,0,0,0
						) WHERE fecha IS NOT NULL
					) AS diasMartes,
					(
						SELECT COUNT(*)
						FROM [dbo].[fn_getDiaHabiles](
							CASE WHEN fechaInicio > @fechaInicioQuincena THEN fechaInicio ELSE @fechaInicioQuincena END,
							CASE WHEN fechaFin < @fechaFinQuincena THEN fechaFin ELSE @fechaFinQuincena END,
							0,0,0,PAMOD_Miercoles,0,0,0
						) WHERE fecha IS NOT NULL
					) AS diasMiercoles,
					(
						SELECT COUNT(*)
						FROM [dbo].[fn_getDiaHabiles](
							CASE WHEN fechaInicio > @fechaInicioQuincena THEN fechaInicio ELSE @fechaInicioQuincena END,
							CASE WHEN fechaFin < @fechaFinQuincena THEN fechaFin ELSE @fechaFinQuincena END,
							0,0,0,0,PAMOD_Jueves,0,0
						) WHERE fecha IS NOT NULL
					) AS diasJueves,
					(
						SELECT COUNT(*)
						FROM [dbo].[fn_getDiaHabiles](
							CASE WHEN fechaInicio > @fechaInicioQuincena THEN fechaInicio ELSE @fechaInicioQuincena END,
							CASE WHEN fechaFin < @fechaFinQuincena THEN fechaFin ELSE @fechaFinQuincena END,
							0,0,0,0,0,PAMOD_Viernes,0
						) WHERE fecha IS NOT NULL
					) AS diasViernes,
					(
						SELECT COUNT(*)
						FROM [dbo].[fn_getDiaHabiles](
							CASE WHEN fechaInicio > @fechaInicioQuincena THEN fechaInicio ELSE @fechaInicioQuincena END,
							CASE WHEN fechaFin < @fechaFinQuincena THEN fechaFin ELSE @fechaFinQuincena END,
							0,0,0,0,0,0,PAMOD_Sabado
						) WHERE fecha IS NOT NULL
					) AS diasSabado
				FROM VW_ProgramasGruposProfesores
				INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = grupoId
				INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
				LEFT JOIN PrenominaMovimientos
					ON PRENOM_EMP_EmpleadoId = empleadoId
					AND PRENOM_CMM_TipoMovimientoId = 2000698
					AND PRENOM_ReferenciaProcesoTabla = 'ProgramasGrupos'
					AND PRENOM_ReferenciaProcesoId = grupoId
					AND PRENOM_FechaInicioPeriodo = CASE
							WHEN fechaInicio > @fechaInicioQuincena THEN fechaInicio
							ELSE @fechaInicioQuincena
						END
					AND PRENOM_FechaFinPeriodo >= CASE
							WHEN fechaFin < @fechaFinQuincena THEN fechaFin
							ELSE @fechaFinQuincena
						END
				WHERE
					activo = 1
					AND COALESCE(PROGRU_PorcentajeApoyoTransporte,0.00) > 0
					AND PRENOM_PrenominaMovimientoId IS NULL
					AND (
						(
							fechaInicio >= @fechaInicioQuincena
							AND fechaInicio <= @fechaFinQuincena
						) OR (
							fechaFin >= @fechaInicioQuincena
							AND fechaFin <= @fechaFinQuincena
						) OR (
							fechaInicio < @fechaInicioQuincena
							AND fechaFin > @fechaFinQuincena
						)
					)
			) AS GruposDiasSemana
			INNER JOIN VW_GRUPOS_HORARIOS ON grupoId = idGrupo
		) AS GruposHoras
		GROUP BY idEmpleado, idSucursal, idGrupo, codigoGrupo, sueldo, fechaInicioPeriodo, fechaFinPeriodo
		HAVING SUM(horasDomingo + horasLunes + horasMartes + horasMiercoles + horasJueves + horasViernes + horasSabado) > 0
	) AS GruposHorasAcumulado
	INNER JOIN Empleados ON EMP_EmpleadoId = idEmpleado
	INNER JOIN Sucursales ON SUC_SucursalId = idSucursal
	INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = idGrupo
	INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	LEFT JOIN TabuladoresCursos
        ON TABC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
        AND TABC_PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
        AND TABC_Activo=1
    LEFT JOIN Tabuladores ON TAB_TabuladorId = TABC_TAB_TabuladorId
	INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
	INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = PROGI_CMM_Idioma
	INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
    ORDER BY idEmpleado,codigoGrupo ASC

    RETURN;
END