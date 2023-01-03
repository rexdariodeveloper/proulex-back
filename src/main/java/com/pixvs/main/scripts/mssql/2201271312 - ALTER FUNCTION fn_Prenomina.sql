/**
 * Created by Angel Daniel Hernández Silva on 27/01/2021.
 * Object: ALTER FUNCTION [dbo].[fn_getPrenomina]
 */
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER FUNCTION [dbo].[fn_getPrenomina] (@fechaInicioQuincena Date, @fechaFinQuincena Date)
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
	fechaDiaSuplida Date,
	categoria varchar(10),
	sueldoPorHora varchar(250),
	idSuplencia int,
	fechaInicioPeriodo date,
	fechaFinPeriodo date,
	sueldoPorHoraDecimal decimal(10,2),
	tipoMovimientoId int,
	movimientoReferenciaId int
)

AS BEGIN
	INSERT @tablaTMP
	SELECT *
	FROM(

		/***********************************/
		/***** Clases profesor titular *****/
		/*****vvvvvvvvvvvvvvvvvvvvvvvvv*****/
		
		SELECT
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
			null as fechaDiaSuplida,
			categoria,
			FORMAT(sueldoPorHora, 'C', 'en-us') as sueldoPorHora,
			null as idSuplencia,
			
			fechaInicioPeriodo AS fechaInicioPeriodo,
			fechaFinPeriodo AS fechaFinPeriodo,
			sueldoPorHora AS sueldoPorHoraDecimal,
			2000690 AS tipoMovimientoId, -- Tipo: Pago a profesor titular
			NULL AS movimientoReferenciaId
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
				CASE
					WHEN PROGRULC_ProgramaGrupoListadoClaseId IS NOT NULL THEN DATEDIFF(HOUR,PAMODH_HoraInicio,PAMODH_HoraFin)
					ELSE 0
				END horasSuplidas,
				CMM_Valor as idioma,
				sueldo as sueldo,
				0 as deduccion,
				(Select count(*) from dbo.fn_getDiaHabiles(CASE WHEN fechaInicio > @fechaInicioQuincena THEN fechaInicio ELSE @fechaInicioQuincena END,CASE WHEN fechaFin < @fechaFinQuincena THEN fechaFin ELSE @fechaFinQuincena END,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado) where fecha is not null)* DATEDIFF(HOUR,PAMODH_HoraInicio,PAMODH_HoraFin) as horasTotales,
				TAB_Codigo as tabulador,
				PROGRU_CategoriaProfesor as categoria,
				sueldo as sueldoPorHora,
				CASE WHEN fechaInicio > @fechaInicioQuincena THEN fechaInicio ELSE @fechaInicioQuincena END AS fechaInicioPeriodo,
				CASE WHEN fechaFin < @fechaFinQuincena THEN fechaFin ELSE @fechaFinQuincena END AS fechaFinPeriodo
			from VW_ProgramasGruposProfesores
			INNER JOIN Empleados ON EMP_EmpleadoId = empleadoId
			inner join ProgramasGrupos on PROGRU_GrupoId = grupoId
			left join Sucursales on SUC_SucursalId = PROGRU_SUC_SucursalId
			left join PAModalidades on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
			inner join PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
			inner join ProgramasIdiomas on PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
			left join Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId
			left join ControlesMaestrosMultiples CMM on CMM_ControlId = PROGI_CMM_Idioma
			left join TabuladoresCursos on TABC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND TABC_PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId AND TABC_Activo=1
			left join Tabuladores on TAB_TabuladorId = TABC_TAB_TabuladorId
			left join ProgramasGruposListadoClases
				ON PROGRULC_PROGRU_GrupoId = PROGRU_GrupoId
				AND PROGRULC_Fecha >= fechaInicio AND PROGRULC_Fecha <= fechaFin
				AND PROGRULC_CMM_FormaPagoId IN (2000520,2000523) AND PROGRULC_Fecha >=@fechaInicioQuincena AND PROGRULC_Fecha <= @fechaFinQuincena  -- pago a titular y con pago
			LEFT JOIN Prenominas on PRENO_PROGRU_GrupoId = PROGRU_GrupoId AND PRENO_FechaFinQuincena = @fechaFinQuincena AND PRENO_FechaInicioQuincena = @fechaInicioQuincena AND PRENO_EMP_EmpleadoId = EMP_EmpleadoId
			WHERE activo = 1 AND PRENO_PrenominaId is null AND ((fechaFin >= @fechaInicioQuincena  AND fechaFin <= @fechaFinQuincena) OR (fechaInicio >= @fechaInicioQuincena  AND fechaInicio <= @fechaFinQuincena) OR (fechaInicio < @fechaInicioQuincena  AND fechaFin > @fechaFinQuincena)) AND PROGRU_Activo = 1

		) Q1
		WHERE horasTotales > 0
		GROUP BY idEmpleado,codigoEmpleado,sucursal,codigoGrupo,empleado,empleado,nombreGrupo,sueldo,idioma,horasTotales,deduccion,tabulador,idGrupo,idSucursal,categoria,sueldoPorHora, fechaInicioPeriodo, fechaFinPeriodo

		/*****^^^^^^^^^^^^^^^^^^^^^^^^^*****/
		/***** Clases profesor titular *****/
		/***********************************/

		UNION ALL

		/****************************/
		/***** Clases suplencia *****/
		/*****vvvvvvvvvvvvvvvvvv*****/

		SELECT
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
			diasSuplido as fechaDiaSuplida,
			categoria,
			FORMAT(sueldoPorHora, 'C', 'en-us') as sueldoPorHora,
			idSuplencia,

			diasSuplido AS fechaInicioPeriodo,
			diasSuplido AS fechaFinPeriodo,
			sueldoPorHora AS sueldoPorHoraDecimal,
			2000691 AS tipoMovimientoId, -- Tipo: Pago por sustitución
			NULL AS movimientoReferenciaId
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
                0 as deduccion,
                CASE
                    WHEN PROGRULC_ProgramaGrupoListadoClaseId IS NOT NULL THEN DATEDIFF(HOUR,PAMODH_HoraInicio,PAMODH_HoraFin)
                    ELSE 0
                END horasSuplidas,
                CMM_Valor as idioma,
                PROGRULC_SueldoProfesor as sueldo,
                TAB_Codigo as tabulador,
                PROGRULC_Fecha as diasSuplido,
                PROGRU_CategoriaProfesor as categoria,
                PROGRULC_SueldoProfesor as sueldoPorHora,
                PROGRULC_ProgramaGrupoListadoClaseId as idSuplencia
            from Empleados
            INNER join ProgramasGruposListadoClases ON PROGRULC_CMM_FormaPagoId IN (2000520,2000522) AND PROGRULC_EMP_EmpleadoId = EMP_EmpleadoId AND PROGRULC_FechaPago IS NULL AND PROGRULC_Fecha <= @fechaFinQuincena
            inner join ProgramasGrupos on PROGRU_GrupoId = PROGRULC_PROGRU_GrupoId
            left join Sucursales on SUC_SucursalId = PROGRU_SUC_SucursalId
            left join PAModalidades on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
            inner join PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
            left join ProgramasIdiomas on PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
            left join TabuladoresCursos on TABC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND TABC_PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId AND TABC_Activo=1
            left join Tabuladores on TAB_TabuladorId = TABC_TAB_TabuladorId
            left join Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId
            left join ControlesMaestrosMultiples CMM on CMM_ControlId = PROGI_CMM_Idioma
            WHERE PROGRU_Activo = 1
		) Q2
		GROUP BY idEmpleado,codigoEmpleado,sucursal,codigoGrupo,empleado,empleado,nombreGrupo,sueldo,idioma,deduccion,tabulador,idGrupo,idSucursal,diasSuplido,categoria,sueldoPorHora,idSuplencia

		/*****^^^^^^^^^^^^^^^^^^*****/
		/***** Clases suplencia *****/
		/****************************/

		UNION ALL

		/**************************************/
		/***** Deducciones y percepciones *****/
		/*****vvvvvvvvvvvvvvvvvvvvvvvvvvvv*****/

		SELECT
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
			null as fechaDiaSuplida,
			categoria,
			FORMAT(sueldoPorHora, 'C', 'en-us') as sueldoPorHora,
			null as idSuplencia,
			EDP_Fecha AS fechaInicioPeriodo,
			EDP_Fecha AS fechaFinPeriodo,
			sueldoPorHora AS sueldoPorHoraDecimal,
			CASE
				WHEN EDP_CMM_TipoMovimientoId = 2000606 THEN 2000693 -- Tipo: percepción manual
				ELSE 2000692 -- Tipo: Deducción manual
			END AS tipoMovimientoId,
			NULL AS movimientoReferenciaId
		FROM(
			select
				EMP_EmpleadoId as idEmpleado,
				EMP_CodigoEmpleado as codigoEmpleado,
				SUC_SucursalId as idSucursal,
				SUC_Nombre as sucursal,
				'#N/A' as codigoGrupo,
				CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ',EMP_SegundoApellido) as empleado,
				DEDPER_Concepto as nombreGrupo,
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
				EDP_EmpleadoDeduccionPercepcionId as deduccionPercepcionId,
				PROGRU_CategoriaProfesor as categoria,
				sueldo as sueldoPorHora,
				EDP_CMM_TipoMovimientoId,
				EDP_Fecha
			from VW_ProgramasGruposProfesores
			INNER JOIN Empleados ON EMP_EmpleadoId = empleadoId
			LEFT JOIN ProgramasGrupos on PROGRU_GrupoId = grupoId
			INNER join EmpleadosDeduccionesPercepciones ON EDP_EMP_EmpleadoId = EMP_EmpleadoId AND EDP_Activo=1
			INNER join DeduccionesPercepciones on DEDPER_DeduccionPercepcionId = EDP_DEDPER_DeduccionPercepcionId
			INNER join Sucursales on SUC_SucursalId = EDP_SUC_SucursalId
			INNER join Tabuladores on TAB_TabuladorId = DEDPER_TAB_TabuladorId AND TAB_Activo=1
			LEFT JOIN Prenominas on PRENO_EDP_EmpleadoDeduccionPercepcionId = EDP_EmpleadoDeduccionPercepcionId AND PRENO_FechaFinQuincena >= EDP_FechaCreacion AND PRENO_FechaInicioQuincena <= EDP_FechaCreacion
			WHERE PRENO_PrenominaId is null AND EDP_FechaCreacion >= @fechaInicioQuincena AND EDP_FechaCreacion <= @fechaFinQuincena
			GROUP BY
				EMP_EmpleadoId, EMP_CodigoEmpleado, EMP_Nombre, EMP_PrimerApellido, EMP_SegundoApellido, 
				DEDPER_Concepto, EDP_CMM_TipoMovimientoId, EDP_CantidadHoras, EDP_EmpleadoDeduccionPercepcionId, EDP_Total,
				SUC_SucursalId, SUC_Nombre, TAB_Codigo, PROGRU_CategoriaProfesor, sueldo, EDP_CMM_TipoMovimientoId, EDP_Fecha
		) Q3

		/*****^^^^^^^^^^^^^^^^^^^^^^^^^^^^*****/
		/***** Deducciones y percepciones *****/
		/**************************************/

		UNION ALL

		/*************************/
		/***** Días festivos *****/
		/*****vvvvvvvvvvvvvvv*****/

		SELECT
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
			null as fechaDiaSuplida,
			categoria,
			FORMAT(sueldoPorHora, 'C', 'en-us') as sueldoPorHora,
			null as idSuplencia,
			fechaDiaFestivo AS fechaInicioPeriodo,
			fechaDiaFestivo AS fechaFinPeriodo,
			sueldoPorHora AS sueldoPorHoraDecimal,
			2000694 AS tipoMovimientoId, -- Tipo: Pago por día festivo
			NULL AS movimientoReferenciaId
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
				DATEDIFF(HOUR,PAMODH_HoraInicio,PAMODH_HoraFin) as horasSuplidas,
				CMM_Valor as idioma,
				sueldo as sueldo,
				0 as deduccion,
				DATEDIFF(HOUR,PAMODH_HoraInicio,PAMODH_HoraFin) as horasTotales,
				TAB_Codigo as tabulador,
				fecha as fechaDiaFestivo,
				PROGRU_CategoriaProfesor as categoria,
				COALESCE(TABD_Sueldo,sueldo) as sueldoPorHora
			from VW_ProgramasGruposProfesores
			INNER JOIN Empleados ON EMP_EmpleadoId = empleadoId
			inner join ProgramasGrupos on PROGRU_GrupoId = grupoId
			left join EmpleadosCategorias on EMPCA_EMP_EmpleadoId = PROGRU_EMP_EmpleadoId
			left join Sucursales on SUC_SucursalId = PROGRU_SUC_SucursalId
			left join PAModalidades modalidad on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
			inner join PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
			inner join ProgramasIdiomas on PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
			left join Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId
			left join ControlesMaestrosMultiples CMM on CMM_ControlId = PROGI_CMM_Idioma
			left join TabuladoresCursos on TABC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND TABC_PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId AND TABC_Activo=1
			left join Tabuladores on TAB_TabuladorId = TABC_TAB_TabuladorId AND TAB_PagoDiasFestivos = 1
			left join TabuladoresDetalles on TABD_TAB_TabuladorId = TAB_TabuladorId AND TABD_PAPC_ProfesorCategoriaId = EMPCA_PAPC_ProfesorCategoriaId AND TABD_Activo=1
			outer apply dbo.fn_getDiasFestivos(@fechaInicioQuincena,@fechaFinQuincena,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado)
			where PROGRU_Activo = 1 and fecha > fechaInicio AND fecha < fechaFin and EMP_FechaAlta <= fecha AND fecha is not null AND concat(PROGRU_GrupoId,fecha) not in (Select concat(PRENO_PROGRU_GrupoId,PRENO_DiasFestivo) from Prenominas where PRENO_DiasFestivo >= @fechaInicioQuincena and PRENO_DiasFestivo <=@fechaFinQuincena)
		)Q5

		/*****^^^^^^^^^^^^^^^*****/
		/***** Días festivos *****/
		/*************************/

		UNION ALL

		/************************************/
		/***** Deducción días suplencia *****/
		/*****vvvvvvvvvvvvvvvvvvvvvvvvvv*****/
		
        SELECT
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
			SUM(horasSuplidas) * -1 AS horasPagadas,
			FORMAT(0, 'C', 'en-us') as percepcion,
			FORMAT((sueldo * SUM(horasSuplidas)), 'C', 'en-us') as deduccion,
			idioma,
			null as fechaDiaFestivo,
			diasSuplido as fechaDiaSuplida,
			categoria,
			FORMAT(sueldoPorHora, 'C', 'en-us') as sueldoPorHora,
			idSuplencia,
			diasSuplido AS fechaInicioPeriodo,
			diasSuplido AS fechaFinPeriodo,
			sueldoPorHora AS sueldoPorHoraDecimal,
			2000695 AS tipoMovimientoId, -- Tipo: Deducción por sustitución
			NULL AS movimientoReferenciaId
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
				0 as deduccion,
				CASE
					WHEN PROGRULC_ProgramaGrupoListadoClaseId IS NOT NULL THEN DATEDIFF(HOUR,PAMODH_HoraInicio,PAMODH_HoraFin)
					ELSE 0
				END horasSuplidas,
				CMM_Valor as idioma,
				COALESCE(PRENO_SueldoPorHora,sueldo) as sueldo,
				TAB_Codigo as tabulador,
				PROGRULC_FechaCreacion as diasSuplido,
				COALESCE(PRENO_SueldoPorHora,sueldo) as sueldoPorHora,
				PROGRU_CategoriaProfesor as categoria,
				PROGRULC_ProgramaGrupoListadoClaseId as idSuplencia
			from VW_ProgramasGruposProfesores
			INNER JOIN Empleados ON EMP_EmpleadoId = empleadoId
			inner join ProgramasGrupos on PROGRU_GrupoId = grupoId
			INNER join ProgramasGruposListadoClases ON PROGRULC_CMM_FormaPagoId IN (2000520,2000523) AND PROGRULC_PROGRU_GrupoId = PROGRU_GrupoId AND PROGRULC_FechaDeduccion IS NULL AND PROGRULC_Fecha < @fechaInicioQuincena AND (PROGRULC_Fecha >= fechaInicio AND PROGRULC_Fecha <= fechaFin)
			left join Sucursales on SUC_SucursalId = PROGRU_SUC_SucursalId
			left join PAModalidades on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
			inner join PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
			left join ProgramasIdiomas on PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
			left join TabuladoresCursos on TABC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND TABC_PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId AND TABC_Activo=1
			left join Tabuladores on TAB_TabuladorId = TABC_TAB_TabuladorId
			left join Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId
			left join ControlesMaestrosMultiples CMM on CMM_ControlId = PROGI_CMM_Idioma
			left JOIN Prenominas on PRENO_PROGRU_GrupoId = PROGRU_GrupoId AND PRENO_EMP_EmpleadoId = EMP_EmpleadoId AND PRENO_FechaClaseSuplida = PROGRULC_FechaCreacion
			WHERE PROGRU_Activo = 1
		) Q6
		GROUP BY idEmpleado,codigoEmpleado,sucursal,codigoGrupo,empleado,empleado,nombreGrupo,sueldo,idioma,deduccion,tabulador,idGrupo,idSucursal,diasSuplido,categoria,sueldoPorHora,idSuplencia

		/*****^^^^^^^^^^^^^^^^^^^^^^^^^^*****/
		/***** Deducción días suplencia *****/
		/************************************/

		UNION ALL

		/************************************************************/
		/***** Deducciones cambio de profesor titular (activos) *****/
		/*****vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv*****/

		SELECT
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
			SUM(horasDeduccion) * -1 AS horasPagadas,
			FORMAT(0, 'C', 'en-us') as percepcion,
			FORMAT(sueldoPorHora * SUM(horasDeduccion), 'C', 'en-us') as deduccion,
			idioma,
			null as fechaDiaFestivo,
			null as fechaDiaSuplida,
			categoria,
			FORMAT(sueldoPorHora, 'C', 'en-us') as sueldoPorHora,
			null as idSuplencia,
			fechaInicioPeriodo AS fechaInicioPeriodo,
			fechaFinPeriodo AS fechaFinPeriodo,
			sueldoPorHora AS sueldoPorHoraDecimal,
			2000696 AS tipoMovimientoId, -- Tipo: Deducción por cambio de profesor titular
			PRENOM_PrenominaMovimientoId AS movimientoReferenciaId
		FROM(
			SELECT
				EMP_EmpleadoId AS idEmpleado,
				EMP_CodigoEmpleado AS codigoEmpleado,
				SUC_SucursalId AS idSucursal,
				SUC_Nombre AS sucursal,
				PROGRU_GrupoId AS idGrupo,
				PROGRU_Codigo AS codigoGrupo,
				CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ',EMP_SegundoApellido) AS empleado,
				TAB_Codigo AS tabulador,
				PROGRU_Codigo AS nombreGrupo,
				((Select count(*) from dbo.fn_getDiaHabiles(ProfesorTitularNuevo.fechaInicio, CASE WHEN ProfesorTitularNuevo.fechaFin < MovimientoPercepcion.PRENOM_FechaFinQuincena THEN ProfesorTitularNuevo.fechaFin ELSE MovimientoPercepcion.PRENOM_FechaFinQuincena END,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado) where fecha is not null)* DATEDIFF(HOUR,PAMODH_HoraInicio,PAMODH_HoraFin) - COALESCE(SUM(MovimientoDeduccion.PRENOM_HorasDeduccion),0)) AS horasDeduccion,
				MovimientoPercepcion.PRENOM_SueldoPorHora AS sueldoPorHora,
				CMM_Valor AS idioma,
				PROGRU_CategoriaProfesor as categoria,
				ProfesorTitularNuevo.fechaInicio AS fechaInicioPeriodo,
				CASE WHEN ProfesorTitularNuevo.fechaFin < MovimientoPercepcion.PRENOM_FechaFinQuincena THEN ProfesorTitularNuevo.fechaFin ELSE MovimientoPercepcion.PRENOM_FechaFinQuincena END AS fechaFinPeriodo,
				MovimientoPercepcion.PRENOM_PrenominaMovimientoId AS PRENOM_PrenominaMovimientoId
			FROM VW_ProgramasGruposProfesores AS ProfesorTitularOriginal
			INNER JOIN Empleados ON EMP_EmpleadoId = ProfesorTitularOriginal.empleadoId
			INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = ProfesorTitularOriginal.grupoId
			INNER JOIN PrenominaMovimientos AS MovimientoPercepcion ON MovimientoPercepcion.PRENOM_EMP_EmpleadoId = EMP_EmpleadoId AND MovimientoPercepcion.PRENOM_FechaInicioQuincena = @fechaInicioQuincena AND MovimientoPercepcion.PRENOM_FechaFinQuincena = @fechaFinQuincena AND MovimientoPercepcion.PRENOM_CMM_TipoMovimientoId = 2000690 -- Tipo: pago a profesor titular
			LEFT JOIN PrenominaMovimientos AS MovimientoDeduccion ON MovimientoDeduccion.PRENOM_PRENOM_MovimientoReferenciaId = MovimientoPercepcion.PRENOM_PrenominaMovimientoId
			INNER JOIN VW_ProgramasGruposProfesores AS ProfesorTitularNuevo ON ProfesorTitularNuevo.fechaInicio >= MovimientoPercepcion.PRENOM_FechaInicioPeriodo AND ProfesorTitularNuevo.fechaInicio <= MovimientoPercepcion.PRENOM_FechaFinPeriodo AND ProfesorTitularNuevo.empleadoId != EMP_EmpleadoId AND ProfesorTitularNuevo.activo = 1 AND ProfesorTitularNuevo.grupoId = PROGRU_GrupoId
			INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
			INNER JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
			INNER JOIN Sucursales ON SUC_SucursalId = PROGRU_SUC_SucursalId
			INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
			INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = PROGI_CMM_Idioma
			LEFT JOIN TabuladoresCursos ON TABC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND TABC_PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId AND TABC_Activo=1
			LEFT JOIN Tabuladores ON TAB_TabuladorId = TABC_TAB_TabuladorId
			WHERE ProfesorTitularOriginal.activo = 1
			GROUP BY
				EMP_EmpleadoId, EMP_CodigoEmpleado, SUC_SucursalId, SUC_Nombre, PROGRU_GrupoId, PROGRU_Codigo, EMP_Nombre, EMP_PrimerApellido, EMP_SegundoApellido,
				TAB_Codigo, ProfesorTitularNuevo.fechaInicio, ProfesorTitularNuevo.fechaFin, MovimientoPercepcion.PRENOM_FechaFinQuincena, PAMOD_Domingo,
				PAMOD_Lunes, PAMOD_Martes, PAMOD_Miercoles, PAMOD_Jueves, PAMOD_Viernes, PAMOD_Sabado, PAMODH_HoraInicio, PAMODH_HoraFin, MovimientoPercepcion.PRENOM_SueldoPorHora,
				CMM_Valor, PROGRU_CategoriaProfesor, MovimientoPercepcion.PRENOM_PrenominaMovimientoId
			HAVING ((Select count(*) from dbo.fn_getDiaHabiles(ProfesorTitularNuevo.fechaInicio, CASE WHEN ProfesorTitularNuevo.fechaFin < MovimientoPercepcion.PRENOM_FechaFinQuincena THEN ProfesorTitularNuevo.fechaFin ELSE MovimientoPercepcion.PRENOM_FechaFinQuincena END,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado) where fecha is not null)* DATEDIFF(HOUR,PAMODH_HoraInicio,PAMODH_HoraFin) - COALESCE(SUM(MovimientoDeduccion.PRENOM_HorasDeduccion),0)) > 0
		) AS DeduccionesCambioProfesorTitular
		GROUP BY idEmpleado, codigoEmpleado, idSucursal, sucursal, idGrupo, codigoGrupo, empleado, tabulador, nombreGrupo, sueldoPorHora, idioma, categoria, fechaInicioPeriodo, fechaFinPeriodo, PRENOM_PrenominaMovimientoId

		/*****^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*****/
		/***** Deducciones cambio de profesor titular (activos) *****/
		/************************************************************/

		UNION ALL

		/**************************************************************/
		/***** Deducciones cambio de profesor titular (inactivos) *****/
		/*****vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv*****/

		SELECT
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
			SUM(horasDeduccion) * -1 AS horasPagadas,
			FORMAT(0, 'C', 'en-us') as percepcion,
			FORMAT(sueldoPorHora * SUM(horasDeduccion), 'C', 'en-us') as deduccion,
			idioma,
			null as fechaDiaFestivo,
			null as fechaDiaSuplida,
			categoria,
			FORMAT(sueldoPorHora, 'C', 'en-us') as sueldoPorHora,
			null as idSuplencia,
			fechaInicioPeriodo AS fechaInicioPeriodo,
			fechaFinPeriodo AS fechaFinPeriodo,
			sueldoPorHora AS sueldoPorHoraDecimal,
			2000696 AS tipoMovimientoId, -- Tipo: Deducción por cambio de profesor titular
			PRENOM_PrenominaMovimientoId AS movimientoReferenciaId
		FROM(
			SELECT
				EMP_EmpleadoId AS idEmpleado,
				EMP_CodigoEmpleado AS codigoEmpleado,
				SUC_SucursalId AS idSucursal,
				SUC_Nombre AS sucursal,
				PROGRU_GrupoId AS idGrupo,
				PROGRU_Codigo AS codigoGrupo,
				CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ',EMP_SegundoApellido) AS empleado,
				TAB_Codigo AS tabulador,
				PROGRU_Codigo AS nombreGrupo,
				CAST(((Select count(*) from dbo.fn_getDiaHabiles(MovimientoPercepcion.PRENOM_FechaInicioPeriodo, MovimientoPercepcion.PRENOM_FechaFinPeriodo,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado) where fecha is not null)* DATEDIFF(HOUR,PAMODH_HoraInicio,PAMODH_HoraFin) - COALESCE(SUM(MovimientoDeduccion.PRENOM_HorasDeduccion),0)) AS int) AS horasDeduccion,
				MovimientoPercepcion.PRENOM_SueldoPorHora AS sueldoPorHora,
				CMM_Valor AS idioma,
				PROGRU_CategoriaProfesor as categoria,
				MovimientoPercepcion.PRENOM_FechaInicioPeriodo AS fechaInicioPeriodo,
				MovimientoPercepcion.PRENOM_FechaFinPeriodo AS fechaFinPeriodo,
				MovimientoPercepcion.PRENOM_PrenominaMovimientoId AS PRENOM_PrenominaMovimientoId
			FROM VW_ProgramasGruposProfesores AS ProfesorTitularOriginal
			INNER JOIN Empleados ON EMP_EmpleadoId = ProfesorTitularOriginal.empleadoId
			INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = ProfesorTitularOriginal.grupoId
			INNER JOIN PrenominaMovimientos AS MovimientoPercepcion ON MovimientoPercepcion.PRENOM_EMP_EmpleadoId = EMP_EmpleadoId AND MovimientoPercepcion.PRENOM_FechaInicioQuincena = @fechaInicioQuincena AND MovimientoPercepcion.PRENOM_FechaFinQuincena = @fechaFinQuincena AND MovimientoPercepcion.PRENOM_CMM_TipoMovimientoId = 2000690 -- Tipo: pago a profesor titular
			INNER JOIN Prenominas
				ON PRENO_EMP_EmpleadoId = PRENOM_EMP_EmpleadoId
				AND PRENO_FechaInicioQuincena = PRENOM_FechaInicioQuincena
				AND PRENO_FechaFinQuincena = PRENOM_FechaFinQuincena
				AND PRENO_PROGRU_GrupoId = PROGRU_GrupoId
			LEFT JOIN PrenominaMovimientos AS MovimientoDeduccion ON MovimientoDeduccion.PRENOM_PRENOM_MovimientoReferenciaId = MovimientoPercepcion.PRENOM_PrenominaMovimientoId
			INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
			INNER JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
			INNER JOIN Sucursales ON SUC_SucursalId = PROGRU_SUC_SucursalId
			INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
			INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = PROGI_CMM_Idioma
			LEFT JOIN TabuladoresCursos ON TABC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND TABC_PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId AND TABC_Activo=1
			LEFT JOIN Tabuladores ON TAB_TabuladorId = TABC_TAB_TabuladorId
			WHERE ProfesorTitularOriginal.activo = 0
			GROUP BY
				EMP_EmpleadoId, EMP_CodigoEmpleado, SUC_SucursalId, SUC_Nombre, PROGRU_GrupoId, PROGRU_Codigo, EMP_Nombre, EMP_PrimerApellido, EMP_SegundoApellido,
				TAB_Codigo, MovimientoPercepcion.PRENOM_FechaFinQuincena, PAMOD_Domingo,
				PAMOD_Lunes, PAMOD_Martes, PAMOD_Miercoles, PAMOD_Jueves, PAMOD_Viernes, PAMOD_Sabado, PAMODH_HoraInicio, PAMODH_HoraFin, MovimientoPercepcion.PRENOM_SueldoPorHora,
				CMM_Valor, PROGRU_CategoriaProfesor, MovimientoPercepcion.PRENOM_PrenominaMovimientoId, MovimientoPercepcion.PRENOM_FechaInicioPeriodo, MovimientoPercepcion.PRENOM_FechaFinPeriodo
			HAVING ((Select count(*) from dbo.fn_getDiaHabiles(MovimientoPercepcion.PRENOM_FechaInicioPeriodo, MovimientoPercepcion.PRENOM_FechaFinPeriodo,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado) where fecha is not null)* DATEDIFF(HOUR,PAMODH_HoraInicio,PAMODH_HoraFin) - COALESCE(SUM(MovimientoDeduccion.PRENOM_HorasDeduccion),0)) > 0
		) AS DeduccionesCambioProfesorTitular
		GROUP BY idEmpleado, codigoEmpleado, idSucursal, sucursal, idGrupo, codigoGrupo, empleado, tabulador, nombreGrupo, sueldoPorHora, idioma, categoria, fechaInicioPeriodo, fechaFinPeriodo, PRENOM_PrenominaMovimientoId

		/*****^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*****/
		/***** Deducciones cambio de profesor titular (inactivos) *****/
		/**************************************************************/
	) Q4

	ORDER BY idEmpleado,codigoGrupo ASC


RETURN;
END