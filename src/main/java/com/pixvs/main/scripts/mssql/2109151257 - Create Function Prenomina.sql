/****** Object:  UserDefinedFunction [dbo].[fn_reporteAntiguedadSaldosResumen]    Script Date: 19/10/2020 12:32:36 p. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getPrenomina] (@fechaInicioQuincena Date, @fechaFinQuincena Date)
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
	horasPagadas int,
	percepcion varchar(250),
	deduccion varchar(250),
	idioma varchar(250)
)

AS BEGIN
	INSERT @tablaTMP
	SELECT *
	FROM(
		SELECT
		
			--Empleados.*,
			idEmpleado,
			codigoEmpleado,
			idSucursal,
			sucursal,
			idGrupo,
			codigoGrupo,
			empleado,
			null as deduccionPercepcionId,
			tabulador,
			nombreGrupo,
			horasTotales- SUM(horasSuplidas) AS horasPagadas,
			FORMAT((sueldo * (horasTotales- SUM(horasSuplidas)) ), 'C', 'en-us') as percepcion,
			FORMAT(deduccion, 'C', 'en-us') as deduccion,
			idioma
		FROM(
		
			select
				EMP_EmpleadoId as idEmpleado,
				EMP_CodigoEmpleado as codigoEmpleado,
				SUC_SucursalId as idSucursal,
				SUC_Nombre as sucursal,
				PROGRU_GrupoId as idGrupo,
				CONCAT(PROG_Codigo,'',CMM_Referencia) as codigoGrupo,
				CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ',EMP_SegundoApellido) as empleado,
				CONCAT(PROGI_CLAVE,CMM_Valor,'-',PAMOD_Nombre,' ',PROGRU_Nivel,' ',PROGRU_Grupo,' ',PAMODH_Horario) as nombreGrupo,
				--Empleados.*,
				CASE
					WHEN PROGRULC_ProgramaGrupoListadoClaseId IS NOT NULL THEN DATEDIFF(HOUR,PAMODH_HoraInicio,PAMODH_HoraFin)
					ELSE 0
				END horasSuplidas,
				CMM_Valor as idioma,
				PROGRU_SueldoProfesor as sueldo,
				0 as deduccion,
				(Select dbo.fn_getDiasClasesTotales(@fechaInicioQuincena,@fechaFinQuincena,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado)) * DATEDIFF(HOUR,PAMODH_HoraInicio,PAMODH_HoraFin)  as horasTotales,
				TAB_Codigo as tabulador
			from Empleados
			inner join ProgramasGrupos on PROGRU_EMP_EmpleadoId = EMP_EmpleadoId
			left join Sucursales on SUC_SucursalId = PROGRU_SUC_SucursalId
			left join PAModalidades on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
			inner join PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
			inner join ProgramasIdiomas on PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
			left join Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId
			left join ControlesMaestrosMultiples CMM on CMM_ControlId = PROGI_CMM_Idioma
			left join Tabuladores on TAB_TabuladorId = PROGI_TAB_TabuladorId
			left join ProgramasGruposListadoClases
				ON PROGRULC_PROGRU_GrupoId = PROGRU_GrupoId
				AND PROGRULC_CMM_FormaPagoId IN (2000521,2000522) -- pago a titular y con pago
			LEFT JOIN Prenominas on PRENO_PROGRU_GrupoId = PROGRU_GrupoId AND PRENO_FechaFinQuincena = @fechaFinQuincena AND PRENO_FechaInicioQuincena = @fechaInicioQuincena
			WHERE PRENO_PrenominaId is null

		) Q1
		GROUP BY idEmpleado,codigoEmpleado,sucursal,codigoGrupo,empleado,empleado,nombreGrupo,sueldo,idioma,horasTotales,deduccion,tabulador,idGrupo,idSucursal

		UNION ALL

		SELECT
			--Empleados.*,
			idEmpleado,
			codigoEmpleado,
			idSucursal,
			sucursal,
			idGrupo,
			codigoGrupo,
			empleado,
			null as deduccionPercepcionId,
			tabulador,
			nombreGrupo,
			SUM(horasSuplidas) AS horasPagadas,
			FORMAT((sueldo * SUM(horasSuplidas)), 'C', 'en-us') as percepcion,
			FORMAT(deduccion, 'C', 'en-us') as deduccion,
			idioma
		FROM(
			select
				EMP_EmpleadoId as idEmpleado,
				EMP_CodigoEmpleado as codigoEmpleado,
				SUC_SucursalId as idSucursal,
				SUC_Nombre as sucursal,
				PROGRU_GrupoId as idGrupo,
				CONCAT(PROG_Codigo,'',CMM_Referencia) as codigoGrupo,
				CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ',EMP_SegundoApellido) as empleado,
				CONCAT(PROGI_CLAVE,CMM_Valor,'-',PAMOD_Nombre,' ',PROGRU_Nivel,' ',PROGRU_Grupo,' ',PAMODH_Horario) as nombreGrupo,
				0 as deduccion,
				--Empleados.*,
				CASE
					WHEN PROGRULC_ProgramaGrupoListadoClaseId IS NOT NULL THEN DATEDIFF(HOUR,PAMODH_HoraInicio,PAMODH_HoraFin)
					ELSE 0
				END horasSuplidas,
				CMM_Valor as idioma,
				PROGRU_SueldoProfesor as sueldo,
				TAB_Codigo as tabulador
				--(Select dbo.fn_getDiasClasesTotales(PROGRU_FechaInicio,PROGRU_FechaFin,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado)) * DATEDIFF(HOUR,PAMODH_HoraInicio,PAMODH_HoraFin) as horasTotales
			from Empleados
			INNER join ProgramasGruposListadoClases ON PROGRULC_CMM_FormaPagoId IN (2000520,2000522) AND PROGRULC_EMP_EmpleadoId = EMP_EmpleadoId -- pago a suplente y con pago
			inner join ProgramasGrupos on PROGRU_GrupoId = PROGRULC_PROGRU_GrupoId
			left join Sucursales on SUC_SucursalId = PROGRU_SUC_SucursalId
			left join PAModalidades on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
			inner join PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
			left join ProgramasIdiomas on PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
			left join Tabuladores on TAB_TabuladorId = PROGI_TAB_TabuladorId
			left join Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId
			left join ControlesMaestrosMultiples CMM on CMM_ControlId = PROGI_CMM_Idioma
			LEFT JOIN Prenominas on PRENO_PROGRU_GrupoId = PROGRU_GrupoId AND PRENO_FechaFinQuincena >= PROGRULC_Fecha AND PRENO_FechaInicioQuincena <= PROGRULC_Fecha
			WHERE PRENO_PrenominaId is null
		) Q2
		GROUP BY idEmpleado,codigoEmpleado,sucursal,codigoGrupo,empleado,empleado,nombreGrupo,sueldo,idioma,deduccion,tabulador,idGrupo,idSucursal

		UNION ALL

		SELECT
			--Empleados.*,
			idEmpleado,
			codigoEmpleado,
			idSucursal,
			sucursal,
			null as idGrupo,
			codigoGrupo,
			empleado,
			deduccionPercepcionId,
			tabulador,
			nombreGrupo,
			horasTotales AS horasPagadas,
			FORMAT(percepcion, 'C', 'en-us') as percepcion,
			FORMAT(deduccion, 'C', 'en-us') as deduccion,
			'N/A' as idioma
		FROM(
			select
				EMP_EmpleadoId as idEmpleado,
				EMP_CodigoEmpleado as codigoEmpleado,
				SUC_SucursalId as idSucursal,
				SUC_Nombre as sucursal,
				'N/A' as codigoGrupo,
				CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ',EMP_SegundoApellido) as empleado,
				DEDPER_Concepto as nombreGrupo,
				--Empleados.*,
				CASE
					WHEN EDP_CMM_TipoMovimientoId = 2000606 THEN EDP_Total
					ELSE 0
				END percepcion,
				CASE
					WHEN EDP_CMM_TipoMovimientoId = 2000605 THEN EDP_Total
					ELSE 0
				END deduccion,
				EDP_CantidadHoras as horasTotales,
				TAB_Codigo as tabulador,
				EDP_EmpleadoDeduccionPercepcionId as deduccionPercepcionId
			from Empleados
			INNER join EmpleadosDeduccionesPercepciones ON EDP_EMP_EmpleadoId = EMP_EmpleadoId
			INNER join DeduccionesPercepciones on DEDPER_DeduccionPercepcionId = EDP_DEDPER_DeduccionPercepcionId
			INNER join Sucursales on SUC_SucursalId = EMP_SUC_SucursalId
			INNER join Tabuladores on TAB_TabuladorId = DEDPER_TAB_TabuladorId
			LEFT JOIN Prenominas on PRENO_EDP_EmpleadoDeduccionPercepcionId = EDP_EmpleadoDeduccionPercepcionId AND PRENO_FechaFinQuincena >= EDP_Fecha AND PRENO_FechaInicioQuincena <= EDP_Fecha
			WHERE PRENO_PrenominaId is null
		) Q3
		--GROUP BY idEmpleado,codigoEmpleado,sucursal,codigoGrupo,empleado,empleado,nombreGrupo,deduccion,percepcion

	) Q4

	ORDER BY idEmpleado,codigoGrupo ASC


RETURN;
END
GO
