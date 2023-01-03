/**
 * Created by Angel Daniel Hernández Silva on 24/02/2022.
 * Object: CREATE FUNCTION [dbo].[fn_getPrenominaDeduccionesCambioProfesorTitular]
 */
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getPrenominaDeduccionesCambioProfesorTitular] (@fechaInicioQuincena Date, @fechaFinQuincena Date)
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

		/********************************************************************************************************************************************/
		/***** Profesores activos que ya fueron pagados pero después se dió de alta otro profesor titular que se sobrepone a la fecha ya pagada *****/
		/*****vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv*****/

		SELECT
			idEmpleado,
			codigoEmpleado,
			idSucursal,
			sucursal,
			idGrupo,
			codigoGrupo,
			empleado,
			NULL AS deduccionPercepcionId,
			tabulador,
			nombreGrupo,
			SUM(horasDeduccion) * -1 AS horasPagadas,
			FORMAT(0, 'C', 'en-us') AS percepcion,
			FORMAT(sueldoPorHora * SUM(horasDeduccion), 'C', 'en-us') AS deduccion,
			idioma,
			NULL AS fechaDiaFestivo,
			NULL AS fechaDiaSuplida,
			categoria,
			FORMAT(sueldoPorHora, 'C', 'en-us') AS sueldoPorHora,
			NULL AS idSuplencia,
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
				( -- <Días_hábiles> * <Horas_trabajadas_al_día> - <Horas_que_ya_se_dedujeron>
                    (
                        SELECT COUNT(*)
                        FROM dbo.fn_getDiaHabiles( -- <Días_hábiles>
                            ProfesorTitularNuevo.fechaInicio,
                            CASE
                                WHEN ProfesorTitularNuevo.fechaFin < MovimientoPercepcion.PRENOM_FechaFinQuincena THEN ProfesorTitularNuevo.fechaFin
                                ELSE MovimientoPercepcion.PRENOM_FechaFinQuincena
                            END,
                            PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado
                        ) WHERE fecha IS NOT NULL
                    ) * (DATEDIFF(MINUTE,PAMODH_HoraInicio,PAMODH_HoraFin) / 60.0) -- <Horas_trabajadas_al_día>
                    - COALESCE(SUM(MovimientoDeduccion.PRENOM_HorasDeduccion),0) -- <Horas_que_ya_se_dedujeron>
                ) AS horasDeduccion,
				MovimientoPercepcion.PRENOM_SueldoPorHora AS sueldoPorHora,
				CMM_Valor AS idioma,
				PROGRU_CategoriaProfesor AS categoria,
				ProfesorTitularNuevo.fechaInicio AS fechaInicioPeriodo,
				CASE -- Si el profesor titular terminó su periodo antes de terminar la quincena a evaluar entonces se toma esa fecha como fin del periodo, de lo contrario se toma la fecha fin de la quincena
                    WHEN ProfesorTitularNuevo.fechaFin < MovimientoPercepcion.PRENOM_FechaFinQuincena THEN ProfesorTitularNuevo.fechaFin
                    ELSE MovimientoPercepcion.PRENOM_FechaFinQuincena
                END AS fechaFinPeriodo,
				MovimientoPercepcion.PRENOM_PrenominaMovimientoId AS PRENOM_PrenominaMovimientoId
			FROM VW_ProgramasGruposProfesores AS ProfesorTitularOriginal
			INNER JOIN Empleados ON EMP_EmpleadoId = ProfesorTitularOriginal.empleadoId
			INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = ProfesorTitularOriginal.grupoId
			INNER JOIN PrenominaMovimientos AS MovimientoPercepcion
                ON MovimientoPercepcion.PRENOM_EMP_EmpleadoId = EMP_EmpleadoId
                AND MovimientoPercepcion.PRENOM_FechaInicioQuincena = @fechaInicioQuincena
                AND MovimientoPercepcion.PRENOM_FechaFinQuincena = @fechaFinQuincena
                AND MovimientoPercepcion.PRENOM_CMM_TipoMovimientoId = 2000690 -- Tipo: pago a profesor titular
			LEFT JOIN PrenominaMovimientos AS MovimientoDeduccion ON MovimientoDeduccion.PRENOM_PRENOM_MovimientoReferenciaId = MovimientoPercepcion.PRENOM_PrenominaMovimientoId
			INNER JOIN VW_ProgramasGruposProfesores AS ProfesorTitularNuevo
                ON ProfesorTitularNuevo.fechaInicio >= MovimientoPercepcion.PRENOM_FechaInicioPeriodo
                AND ProfesorTitularNuevo.fechaInicio <= MovimientoPercepcion.PRENOM_FechaFinPeriodo
                AND ProfesorTitularNuevo.empleadoId != EMP_EmpleadoId
                AND ProfesorTitularNuevo.grupoId = PROGRU_GrupoId
                AND ProfesorTitularNuevo.activo = 1
			INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
			INNER JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
			INNER JOIN Sucursales ON SUC_SucursalId = PROGRU_SUC_SucursalId
			INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
			INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = PROGI_CMM_Idioma
			LEFT JOIN TabuladoresCursos
                ON TABC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
                AND TABC_PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
                AND TABC_Activo=1
			LEFT JOIN Tabuladores ON TAB_TabuladorId = TABC_TAB_TabuladorId
			WHERE ProfesorTitularOriginal.activo = 1
			GROUP BY
				EMP_EmpleadoId, EMP_CodigoEmpleado, SUC_SucursalId, SUC_Nombre, PROGRU_GrupoId, PROGRU_Codigo, EMP_Nombre, EMP_PrimerApellido, EMP_SegundoApellido,
				TAB_Codigo, ProfesorTitularNuevo.fechaInicio, ProfesorTitularNuevo.fechaFin, MovimientoPercepcion.PRENOM_FechaFinQuincena, PAMOD_Domingo,
				PAMOD_Lunes, PAMOD_Martes, PAMOD_Miercoles, PAMOD_Jueves, PAMOD_Viernes, PAMOD_Sabado, PAMODH_HoraInicio, PAMODH_HoraFin, MovimientoPercepcion.PRENOM_SueldoPorHora,
				CMM_Valor, PROGRU_CategoriaProfesor, MovimientoPercepcion.PRENOM_PrenominaMovimientoId
			HAVING ( -- Verificar que haya horas por deducir (es mas ópttimo verificarlo en el having que llevar el valor al query exterior y verificar que sea mayor a cero)
                ( -- <Días_hábiles> * <Horas_trabajadas_al_día> - <Horas_que_ya_se_dedujeron>
                    SELECT COUNT(*)
                    FROM dbo.fn_getDiaHabiles( -- <Días_hábiles>
                        ProfesorTitularNuevo.fechaInicio,
                        CASE
                            WHEN ProfesorTitularNuevo.fechaFin < MovimientoPercepcion.PRENOM_FechaFinQuincena THEN ProfesorTitularNuevo.fechaFin
                            ELSE MovimientoPercepcion.PRENOM_FechaFinQuincena
                        END,
                        PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado
                    ) WHERE fecha IS NOT NULL
                ) * (DATEDIFF(MINUTE,PAMODH_HoraInicio,PAMODH_HoraFin) / 60.0) -- <Horas_trabajadas_al_día>
                - COALESCE(SUM(MovimientoDeduccion.PRENOM_HorasDeduccion),0) -- <Horas_que_ya_se_dedujeron>
            ) > 0
		) AS DeduccionesCambioProfesorTitular
		GROUP BY idEmpleado, codigoEmpleado, idSucursal, sucursal, idGrupo, codigoGrupo, empleado, tabulador, nombreGrupo, sueldoPorHora, idioma, categoria, fechaInicioPeriodo, fechaFinPeriodo, PRENOM_PrenominaMovimientoId

		/*****^^^^^^^^^*****/
		/***** Activos *****/
		/*******************/

		UNION ALL

		/********************************************************************************************************************************************************************************************************/
		/***** Profesores titulares que ya fueron pagados pero después hubo un cambio de profesor titular que sobreescribió por completo el periodo que trabajó el profesor ya pagado (queda como inactivo) *****/
		/*****vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv*****/

		SELECT
			idEmpleado,
			codigoEmpleado,
			idSucursal,
			sucursal,
			idGrupo,
			codigoGrupo,
			empleado,
			NULL AS deduccionPercepcionId,
			tabulador,
			nombreGrupo,
			SUM(horasDeduccion) * -1 AS horasPagadas,
			FORMAT(0, 'C', 'en-us') AS percepcion,
			FORMAT(sueldoPorHora * SUM(horasDeduccion), 'C', 'en-us') AS deduccion,
			idioma,
			NULL AS fechaDiaFestivo,
			NULL AS fechaDiaSuplida,
			categoria,
			FORMAT(sueldoPorHora, 'C', 'en-us') AS sueldoPorHora,
			NULL AS idSuplencia,
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
				CAST(
                    (
                        ( -- <Días_hábiles> * <Horas_trabajadas_al_día> - <Horas_que_ya_se_dedujeron>
                            SELECT COUNT(*)
                            FROM dbo.fn_getDiaHabiles( -- <Días_hábiles>
                                MovimientoPercepcion.PRENOM_FechaInicioPeriodo,
                                MovimientoPercepcion.PRENOM_FechaFinPeriodo,
                                PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado
                            ) WHERE fecha IS NOT NULL
                        ) * (DATEDIFF(MINUTE,PAMODH_HoraInicio,PAMODH_HoraFin) / 60.0) -- <Horas_trabajadas_al_día>
                        - COALESCE(SUM(MovimientoDeduccion.PRENOM_HorasDeduccion),0) -- <Horas_que_ya_se_dedujeron>
                    ) AS int
                ) AS horasDeduccion,
				MovimientoPercepcion.PRENOM_SueldoPorHora AS sueldoPorHora,
				CMM_Valor AS idioma,
				PROGRU_CategoriaProfesor AS categoria,
				MovimientoPercepcion.PRENOM_FechaInicioPeriodo AS fechaInicioPeriodo,
				MovimientoPercepcion.PRENOM_FechaFinPeriodo AS fechaFinPeriodo,
				MovimientoPercepcion.PRENOM_PrenominaMovimientoId AS PRENOM_PrenominaMovimientoId
			FROM VW_ProgramasGruposProfesores AS ProfesorTitularOriginal
			INNER JOIN Empleados ON EMP_EmpleadoId = ProfesorTitularOriginal.empleadoId
			INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = ProfesorTitularOriginal.grupoId
			INNER JOIN PrenominaMovimientos AS MovimientoPercepcion
                ON MovimientoPercepcion.PRENOM_EMP_EmpleadoId = EMP_EmpleadoId
                AND MovimientoPercepcion.PRENOM_FechaInicioQuincena = @fechaInicioQuincena
                AND MovimientoPercepcion.PRENOM_FechaFinQuincena = @fechaFinQuincena
                AND MovimientoPercepcion.PRENOM_CMM_TipoMovimientoId = 2000690 -- Tipo: pago a profesor titular
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
			LEFT JOIN TabuladoresCursos
                ON TABC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
                AND TABC_PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
                AND TABC_Activo = 1
			LEFT JOIN Tabuladores ON TAB_TabuladorId = TABC_TAB_TabuladorId
			WHERE ProfesorTitularOriginal.activo = 0
			GROUP BY
				EMP_EmpleadoId, EMP_CodigoEmpleado, SUC_SucursalId, SUC_Nombre, PROGRU_GrupoId, PROGRU_Codigo, EMP_Nombre, EMP_PrimerApellido, EMP_SegundoApellido,
				TAB_Codigo, MovimientoPercepcion.PRENOM_FechaFinQuincena, PAMOD_Domingo,
				PAMOD_Lunes, PAMOD_Martes, PAMOD_Miercoles, PAMOD_Jueves, PAMOD_Viernes, PAMOD_Sabado, PAMODH_HoraInicio, PAMODH_HoraFin, MovimientoPercepcion.PRENOM_SueldoPorHora,
				CMM_Valor, PROGRU_CategoriaProfesor, MovimientoPercepcion.PRENOM_PrenominaMovimientoId, MovimientoPercepcion.PRENOM_FechaInicioPeriodo, MovimientoPercepcion.PRENOM_FechaFinPeriodo
			HAVING ( -- Verificar que haya horas por deducir (es mas ópttimo verificarlo en el having que llevar el valor al query exterior y verificar que sea mayor a cero)
                ( -- <Días_hábiles> * <Horas_trabajadas_al_día> - <Horas_que_ya_se_dedujeron>
                    SELECT COUNT(*)
                    FROM dbo.fn_getDiaHabiles( -- <Días_hábiles>
                        MovimientoPercepcion.PRENOM_FechaInicioPeriodo,
                        MovimientoPercepcion.PRENOM_FechaFinPeriodo,
                        PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado
                    ) WHERE fecha IS NOT NULL
                ) * (DATEDIFF(MINUTE,PAMODH_HoraInicio,PAMODH_HoraFin) / 60.0) -- <Horas_trabajadas_al_día>
                - COALESCE(SUM(MovimientoDeduccion.PRENOM_HorasDeduccion),0) -- <Horas_que_ya_se_dedujeron>
            ) > 0
		) AS DeduccionesCambioProfesorTitular
		GROUP BY idEmpleado, codigoEmpleado, idSucursal, sucursal, idGrupo, codigoGrupo, empleado, tabulador, nombreGrupo, sueldoPorHora, idioma, categoria, fechaInicioPeriodo, fechaFinPeriodo, PRENOM_PrenominaMovimientoId

		/*****^^^^^^^^^^^*****/
		/***** Inactivos *****/
		/*********************/
	) PrenominaDeduccionesCambioProfesorTitular
	ORDER BY idEmpleado,codigoGrupo ASC

    RETURN;
END