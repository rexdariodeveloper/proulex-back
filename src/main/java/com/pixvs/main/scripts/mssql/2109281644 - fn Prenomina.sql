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
	idioma varchar(250),
	fechaDiaFestivo Date,
	fechaDiaSuplida Date
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
			idioma,
			null as fechaDiaFestivo,
			null as fechaDiaSuplida
		FROM(
		
			select
				EMP_EmpleadoId as idEmpleado,
				EMP_CodigoEmpleado as codigoEmpleado,
				SUC_SucursalId as idSucursal,
				SUC_Nombre as sucursal,
				PROGRU_GrupoId as idGrupo,
				PROGRU_Codigo as codigoGrupo,
				CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ',EMP_SegundoApellido) as empleado,
				CONCAT(PROG_Codigo,' ',CMM_Valor,' ',PAMOD_Nombre,' Nivel ',FORMAT(PROGRU_Nivel,'00'),' Grupo ',FORMAT(PROGRU_Grupo,'00')) as nombreGrupo,
				--Empleados.*,
				CASE
					WHEN PROGRULC_ProgramaGrupoListadoClaseId IS NOT NULL THEN DATEDIFF(HOUR,PAMODH_HoraInicio,PAMODH_HoraFin)
					ELSE 0
				END horasSuplidas,
				CMM_Valor as idioma,
				PROGRU_SueldoProfesor as sueldo,
				0 as deduccion,
				(Select count(*) from dbo.fn_getDiaHabiles(CASE WHEN PROGRU_FechaInicio > @fechaInicioQuincena THEN PROGRU_FechaInicio ELSE @fechaInicioQuincena END,CASE WHEN PROGRU_FechaFin < @fechaFinQuincena THEN PROGRU_FechaFin ELSE @fechaFinQuincena END,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado) where fecha is not null)* DATEDIFF(HOUR,PAMODH_HoraInicio,PAMODH_HoraFin) as horasTotales,
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
				AND PROGRULC_CMM_FormaPagoId IN (2000520,2000523) AND PROGRULC_Fecha >=@fechaInicioQuincena AND PROGRULC_Fecha <= @fechaFinQuincena  -- pago a titular y con pago
			LEFT JOIN Prenominas on PRENO_PROGRU_GrupoId = PROGRU_GrupoId AND PRENO_FechaFinQuincena = @fechaFinQuincena AND PRENO_FechaInicioQuincena = @fechaInicioQuincena AND PRENO_EMP_EmpleadoId = EMP_EmpleadoId
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
			idioma,
			null as fechaDiaFestivo,
			diasSuplido as fechaDiaSuplida
		FROM(
				select
					EMP_EmpleadoId as idEmpleado,
					EMP_CodigoEmpleado as codigoEmpleado,
					SUC_SucursalId as idSucursal,
					SUC_Nombre as sucursal,
					PROGRU_GrupoId as idGrupo,
					PROGRU_Codigo as codigoGrupo,
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
					TAB_Codigo as tabulador,
					PROGRULC_Fecha as diasSuplido
				from Empleados
				INNER join ProgramasGruposListadoClases ON PROGRULC_CMM_FormaPagoId IN (2000520,2000522) AND PROGRULC_EMP_EmpleadoId = EMP_EmpleadoId AND PROGRULC_Fecha >=@fechaInicioQuincena AND PROGRULC_Fecha <= @fechaFinQuincena -- pago a suplente y con pago
				inner join ProgramasGrupos on PROGRU_GrupoId = PROGRULC_PROGRU_GrupoId
				left join Sucursales on SUC_SucursalId = PROGRU_SUC_SucursalId
				left join PAModalidades on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
				inner join PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
				left join ProgramasIdiomas on PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
				left join Tabuladores on TAB_TabuladorId = PROGI_TAB_TabuladorId
				left join Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId
				left join ControlesMaestrosMultiples CMM on CMM_ControlId = PROGI_CMM_Idioma
				left JOIN Prenominas on PRENO_PROGRU_GrupoId = PROGRU_GrupoId AND PRENO_EMP_EmpleadoId = EMP_EmpleadoId AND PRENO_FechaClaseSuplida = PROGRULC_Fecha
				WHERE PRENO_PrenominaId is null
		) Q2
		GROUP BY idEmpleado,codigoEmpleado,sucursal,codigoGrupo,empleado,empleado,nombreGrupo,sueldo,idioma,deduccion,tabulador,idGrupo,idSucursal,diasSuplido

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
			'#N/A' as idioma,
			null as fechaDiaFestivo,
			null as fechaDiaSuplida
		FROM(
			select
				EMP_EmpleadoId as idEmpleado,
				EMP_CodigoEmpleado as codigoEmpleado,
				SUC_SucursalId as idSucursal,
				SUC_Nombre as sucursal,
				'#N/A' as codigoGrupo,
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
			INNER join EmpleadosDeduccionesPercepciones ON EDP_EMP_EmpleadoId = EMP_EmpleadoId AND EDP_Activo=1
			INNER join DeduccionesPercepciones on DEDPER_DeduccionPercepcionId = EDP_DEDPER_DeduccionPercepcionId
			INNER join Sucursales on SUC_SucursalId = EDP_SUC_SucursalId
			INNER join Tabuladores on TAB_TabuladorId = DEDPER_TAB_TabuladorId
			LEFT JOIN Prenominas on PRENO_EDP_EmpleadoDeduccionPercepcionId = EDP_EmpleadoDeduccionPercepcionId AND PRENO_FechaFinQuincena >= EDP_FechaCreacion AND PRENO_FechaInicioQuincena <= EDP_FechaCreacion
			WHERE PRENO_PrenominaId is null AND EDP_FechaCreacion >= @fechaInicioQuincena AND EDP_FechaCreacion <= @fechaFinQuincena
		) Q3

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
			horasTotales AS horasPagadas,
			FORMAT(sueldo * horasSuplidas, 'C', 'en-us') as percepcion,
			FORMAT(deduccion, 'C', 'en-us') as deduccion,
			idioma,
			fechaDiaFestivo,
			null as fechaDiaSuplida
		FROM(
		SELECT 
		EMP_EmpleadoId as idEmpleado,
		EMP_CodigoEmpleado as codigoEmpleado,
		SUC_SucursalId as idSucursal,
		SUC_Nombre as sucursal,
		PROGRU_GrupoId as idGrupo,
		PROGRU_Codigo as codigoGrupo,
		CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ',EMP_SegundoApellido) as empleado,
		CONCAT('Día festivo', ' ',fecha) as nombreGrupo,
		--Empleados.*,
		DATEDIFF(HOUR,PAMODH_HoraInicio,PAMODH_HoraFin) as horasSuplidas,
		CMM_Valor as idioma,
		PROGRU_SueldoProfesor as sueldo,
		0 as deduccion,
		DATEDIFF(HOUR,PAMODH_HoraInicio,PAMODH_HoraFin) as horasTotales,
		TAB_Codigo as tabulador,
		fecha as fechaDiaFestivo
		from Empleados
		inner join ProgramasGrupos on PROGRU_EMP_EmpleadoId = EMP_EmpleadoId
		left join Sucursales on SUC_SucursalId = PROGRU_SUC_SucursalId
		left join PAModalidades modalidad on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
		inner join PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
		inner join ProgramasIdiomas on PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
		left join Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId
		left join ControlesMaestrosMultiples CMM on CMM_ControlId = PROGI_CMM_Idioma
		left join Tabuladores on TAB_TabuladorId = PROGI_TAB_TabuladorId
		outer apply dbo.fn_getDiasFestivos(@fechaInicioQuincena,@fechaFinQuincena,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado)--  fecha >= '2021-09-16' and fecha <= '2021-09-30'
		--inner join Prenominas on PRENO_DiasFestivo = fecha
		where fecha > PROGRU_FechaInicio AND fecha < PROGRU_FechaFin and EMP_FechaAlta <= fecha AND fecha is not null AND concat(PROGRU_GrupoId,fecha) not in (Select concat(PRENO_PROGRU_GrupoId,PRENO_DiasFestivo) from Prenominas where PRENO_DiasFestivo >= @fechaInicioQuincena and PRENO_DiasFestivo <=@fechaFinQuincena)
		)Q5
		

	) Q4

	ORDER BY idEmpleado,codigoGrupo ASC


RETURN;
END
GO