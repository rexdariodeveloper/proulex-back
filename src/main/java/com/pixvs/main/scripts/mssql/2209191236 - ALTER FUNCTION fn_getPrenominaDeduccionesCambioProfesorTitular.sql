/**
* Created by Angel Daniel Hernández Silva on 19/09/2022.
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
    horasPagadas decimal(5,2),
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
    movimientoReferenciaId int,
    referenciaProcesoTabla varchar(100),
    referenciaProcesoId int,
    modalidad varchar(40),
    grupoFechaInicio date,
    grupoFechaFin date
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
            EMP_CodigoEmpleado AS codigoEmpleado,
            idSucursal,
            SUC_Nombre AS sucursal,
            idGrupo,
            codigoGrupo,
            CONCAT(EMP_PrimerApellido,' ' + EMP_SegundoApellido, ' ' + EMP_Nombre) AS empleado,
            NULL AS deduccionPercepcionId,
            TAB_Codigo AS tabulador,
            PROGRU_Codigo AS nombreGrupo,
            horas * -1 AS horasPagadas,
            FORMAT(0, 'C', 'en-us') AS percepcion,
            FORMAT(sueldo * horas, 'C', 'en-us') AS deduccion,
            CMMIdioma.CMM_Valor AS idioma,
            NULL AS fechaDiaFestivo,
            NULL AS fechaDiaSuplida,
            PROGRU_CategoriaProfesor AS categoria,
            FORMAT(sueldo, 'C', 'en-us') AS sueldoPorHora,
            NULL AS idSuplencia,
            fechaInicioPeriodo AS fechaInicioPeriodo,
            fechaFinPeriodo AS fechaFinPeriodo,
            sueldo AS sueldoPorHoraDecimal,
            2000696 AS tipoMovimientoId, -- Tipo: Deducción por cambio de profesor titular
            prenominaMovimientoId AS movimientoReferenciaId,
            'PrenominaMovimientos' AS referenciaProcesoTabla,
            prenominaMovimientoId AS referenciaProcesoId,
            CMMTipoGrupo.CMM_Valor AS modalidad,
            PROGRU_FechaInicio AS grupoFechaInicio,
            COALESCE(PROGRU_FechaCancelacion,PROGRU_FechaFin) AS grupoFechaFin
        FROM(
            SELECT
                idEmpleado,
                idSucursal,
                idGrupo,
                codigoGrupo,
                sueldo,
                fechaInicioPeriodo,
                fechaFinPeriodo,
                SUM(horasDomingo + horasLunes + horasMartes + horasMiercoles + horasJueves + horasViernes + horasSabado)-horasDeduccion AS horas,
                horasDeduccion,
                prenominaMovimientoId
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
                        ProfesorTitularOriginal.empleadoId AS idEmpleado,
                        PROGRU_SUC_SucursalId AS idSucursal,
                        ProfesorTitularOriginal.grupoId AS idGrupo,
                        PROGRU_Codigo AS codigoGrupo,
                        MovimientoPercepcion.PRENOM_SueldoPorHora AS sueldo,
                        ProfesorTitularNuevo.fechaInicio AS fechaInicioPeriodo,
                        CASE -- Si el profesor titular terminó su periodo antes de terminar la quincena a evaluar entonces se toma esa fecha como fin del periodo, de lo contrario se toma la fecha fin de la quincena
                            WHEN ProfesorTitularNuevo.fechaFin < MovimientoPercepcion.PRENOM_FechaFinQuincena THEN ProfesorTitularNuevo.fechaFin
                            ELSE MovimientoPercepcion.PRENOM_FechaFinQuincena
                        END AS fechaFinPeriodo,
                        COALESCE(MovimientoDeduccion.PRENOM_HorasDeduccion,0) AS horasDeduccion,
                        MovimientoPercepcion.PRENOM_PrenominaMovimientoId AS prenominaMovimientoId,
                        (
                            SELECT COUNT(*)
                            FROM [dbo].[fn_getDiaHabiles](
                                ProfesorTitularNuevo.fechaInicio,
                                CASE WHEN ProfesorTitularNuevo.fechaFin < MovimientoPercepcion.PRENOM_FechaFinQuincena THEN ProfesorTitularNuevo.fechaFin ELSE MovimientoPercepcion.PRENOM_FechaFinQuincena END,
                                PAMOD_Domingo,0,0,0,0,0,0
                            ) WHERE fecha IS NOT NULL
                        ) AS diasDomingo,
                        (
                            SELECT COUNT(*)
                            FROM [dbo].[fn_getDiaHabiles](
                                ProfesorTitularNuevo.fechaInicio,
                                CASE WHEN ProfesorTitularNuevo.fechaFin < MovimientoPercepcion.PRENOM_FechaFinQuincena THEN ProfesorTitularNuevo.fechaFin ELSE MovimientoPercepcion.PRENOM_FechaFinQuincena END,
                                0,PAMOD_Lunes,0,0,0,0,0
                            ) WHERE fecha IS NOT NULL
                        ) AS diasLunes,
                        (
                            SELECT COUNT(*)
                            FROM [dbo].[fn_getDiaHabiles](
                                ProfesorTitularNuevo.fechaInicio,
                                CASE WHEN ProfesorTitularNuevo.fechaFin < MovimientoPercepcion.PRENOM_FechaFinQuincena THEN ProfesorTitularNuevo.fechaFin ELSE MovimientoPercepcion.PRENOM_FechaFinQuincena END,
                                0,0,PAMOD_Martes,0,0,0,0
                            ) WHERE fecha IS NOT NULL
                        ) AS diasMartes,
                        (
                            SELECT COUNT(*)
                            FROM [dbo].[fn_getDiaHabiles](
                                ProfesorTitularNuevo.fechaInicio,
                                CASE WHEN ProfesorTitularNuevo.fechaFin < MovimientoPercepcion.PRENOM_FechaFinQuincena THEN ProfesorTitularNuevo.fechaFin ELSE MovimientoPercepcion.PRENOM_FechaFinQuincena END,
                                0,0,0,PAMOD_Miercoles,0,0,0
                            ) WHERE fecha IS NOT NULL
                        ) AS diasMiercoles,
                        (
                            SELECT COUNT(*)
                            FROM [dbo].[fn_getDiaHabiles](
                                ProfesorTitularNuevo.fechaInicio,
                                CASE WHEN ProfesorTitularNuevo.fechaFin < MovimientoPercepcion.PRENOM_FechaFinQuincena THEN ProfesorTitularNuevo.fechaFin ELSE MovimientoPercepcion.PRENOM_FechaFinQuincena END,
                                0,0,0,0,PAMOD_Jueves,0,0
                            ) WHERE fecha IS NOT NULL
                        ) AS diasJueves,
                        (
                            SELECT COUNT(*)
                            FROM [dbo].[fn_getDiaHabiles](
                                ProfesorTitularNuevo.fechaInicio,
                                CASE WHEN ProfesorTitularNuevo.fechaFin < MovimientoPercepcion.PRENOM_FechaFinQuincena THEN ProfesorTitularNuevo.fechaFin ELSE MovimientoPercepcion.PRENOM_FechaFinQuincena END,
                                0,0,0,0,0,PAMOD_Viernes,0
                            ) WHERE fecha IS NOT NULL
                        ) AS diasViernes,
                        (
                            SELECT COUNT(*)
                            FROM [dbo].[fn_getDiaHabiles](
                                ProfesorTitularNuevo.fechaInicio,
                                CASE WHEN ProfesorTitularNuevo.fechaFin < MovimientoPercepcion.PRENOM_FechaFinQuincena THEN ProfesorTitularNuevo.fechaFin ELSE MovimientoPercepcion.PRENOM_FechaFinQuincena END,
                                0,0,0,0,0,0,PAMOD_Sabado
                            ) WHERE fecha IS NOT NULL
                        ) AS diasSabado
                    FROM VW_ProgramasGruposProfesores AS ProfesorTitularOriginal
                    INNER JOIN PrenominaMovimientos AS MovimientoPercepcion
                        ON MovimientoPercepcion.PRENOM_EMP_EmpleadoId = ProfesorTitularOriginal.empleadoId
                        AND MovimientoPercepcion.PRENOM_FechaInicioQuincena = @fechaInicioQuincena
                        AND MovimientoPercepcion.PRENOM_FechaFinQuincena = @fechaFinQuincena
                        AND MovimientoPercepcion.PRENOM_CMM_TipoMovimientoId = 2000690 -- Tipo: pago a profesor titular
                        AND MovimientoPercepcion.PRENOM_ReferenciaProcesoTabla = 'ProgramasGrupos'
                        AND MovimientoPercepcion.PRENOM_ReferenciaProcesoId = ProfesorTitularOriginal.grupoId
                    LEFT JOIN PrenominaMovimientos AS MovimientoDeduccion
                        ON MovimientoDeduccion.PRENOM_PRENOM_MovimientoReferenciaId = MovimientoPercepcion.PRENOM_PrenominaMovimientoId
                        AND MovimientoDeduccion.PRENOM_CMM_TipoMovimientoId = 2000696 -- Tipo: Deducción por cambio de profesor titular
                        AND MovimientoDeduccion.PRENOM_ReferenciaProcesoTabla = 'PrenominaMovimientos'
                        AND MovimientoDeduccion.PRENOM_ReferenciaProcesoId = MovimientoPercepcion.PRENOM_PrenominaMovimientoId
                    INNER JOIN VW_ProgramasGruposProfesores AS ProfesorTitularNuevo
                        ON ProfesorTitularNuevo.fechaInicio >= MovimientoPercepcion.PRENOM_FechaInicioPeriodo
                        AND ProfesorTitularNuevo.fechaInicio <= MovimientoPercepcion.PRENOM_FechaFinPeriodo
                        AND ProfesorTitularNuevo.empleadoId != ProfesorTitularOriginal.empleadoId
                        AND ProfesorTitularNuevo.grupoId = ProfesorTitularOriginal.grupoId
                        AND ProfesorTitularNuevo.activo = 1
                    INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = ProfesorTitularOriginal.grupoId
                    INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
                    WHERE ProfesorTitularOriginal.activo = 1
                ) AS GruposDiasSemana
                INNER JOIN VW_GRUPOS_HORARIOS ON grupoId = idGrupo
            ) AS GruposHoras
            GROUP BY idEmpleado, idSucursal, idGrupo, codigoGrupo, sueldo, fechaInicioPeriodo, fechaFinPeriodo, horasDeduccion, prenominaMovimientoId
            HAVING SUM(horasDomingo + horasLunes + horasMartes + horasMiercoles + horasJueves + horasViernes + horasSabado)-horasDeduccion > 0
        ) AS GruposHorasAgrupado
        INNER JOIN Empleados ON EMP_EmpleadoId = idEmpleado
        INNER JOIN Sucursales ON SUC_SucursalId = idSucursal
        INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = idGrupo
        INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
        INNER JOIN ControlesMaestrosMultiples AS CMMIdioma ON CMMIdioma.CMM_ControlId = PROGI_CMM_Idioma
        INNER JOIN ControlesMaestrosMultiples AS CMMTipoGrupo ON CMMTipoGrupo.CMM_ControlId = PROGRU_CMM_TipoGrupoId
        LEFT JOIN TabuladoresCursos
            ON TABC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
            AND TABC_PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
            AND TABC_Activo=1
        LEFT JOIN Tabuladores ON TAB_TabuladorId = TABC_TAB_TabuladorId

        /*****^^^^^^^^^*****/
        /***** Activos *****/
        /*******************/

        UNION ALL

        /********************************************************************************************************************************************************************************************************/
        /***** Profesores titulares que ya fueron pagados pero después hubo un cambio de profesor titular que sobreescribió por completo el periodo que trabajó el profesor ya pagado (queda como inactivo) *****/
        /*****vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv*****/

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
            PROGRU_Codigo AS nombreGrupo,
            horas * -1 AS horasPagadas,
            FORMAT(0, 'C', 'en-us') AS percepcion,
            FORMAT(sueldo * horas, 'C', 'en-us') AS deduccion,
            CMMIdioma.CMM_Valor AS idioma,
            NULL AS fechaDiaFestivo,
            NULL AS fechaDiaSuplida,
            PROGRU_CategoriaProfesor AS categoria,
            FORMAT(sueldo, 'C', 'en-us') AS sueldoPorHora,
            NULL AS idSuplencia,
            fechaInicioPeriodo AS fechaInicioPeriodo,
            fechaFinPeriodo AS fechaFinPeriodo,
            sueldo AS sueldoPorHoraDecimal,
            2000696 AS tipoMovimientoId, -- Tipo: Deducción por cambio de profesor titular
            prenominaMovimientoId AS movimientoReferenciaId,
            'PrenominaMovimientos' AS referenciaProcesoTabla,
            prenominaMovimientoId AS referenciaProcesoId,
            CMMTipoGrupo.CMM_Valor AS modalidad,
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
                SUM(horasDomingo + horasLunes + horasMartes + horasMiercoles + horasJueves + horasViernes + horasSabado)-horasDeduccion AS horas,
                horasDeduccion,
                prenominaMovimientoId
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
                        ProfesorTitularOriginal.empleadoId AS idEmpleado,
                        PROGRU_SUC_SucursalId AS idSucursal,
                        ProfesorTitularOriginal.grupoId AS idGrupo,
                        PROGRU_Codigo AS codigoGrupo,
                        MovimientoPercepcion.PRENOM_SueldoPorHora AS sueldo,
                        MovimientoPercepcion.PRENOM_FechaInicioPeriodo AS fechaInicioPeriodo,
                        MovimientoPercepcion.PRENOM_FechaFinPeriodo AS fechaFinPeriodo,
                        COALESCE(MovimientoDeduccion.PRENOM_HorasDeduccion,0) AS horasDeduccion,
                        MovimientoPercepcion.PRENOM_PrenominaMovimientoId AS prenominaMovimientoId,
                        (
                            SELECT COUNT(*)
                            FROM [dbo].[fn_getDiaHabiles](
                                MovimientoPercepcion.PRENOM_FechaInicioPeriodo,
                                MovimientoPercepcion.PRENOM_FechaFinPeriodo,
                                PAMOD_Domingo,0,0,0,0,0,0
                            ) WHERE fecha IS NOT NULL
                        ) AS diasDomingo,
                        (
                            SELECT COUNT(*)
                            FROM [dbo].[fn_getDiaHabiles](
                                MovimientoPercepcion.PRENOM_FechaInicioPeriodo,
                                MovimientoPercepcion.PRENOM_FechaFinPeriodo,
                                0,PAMOD_Lunes,0,0,0,0,0
                            ) WHERE fecha IS NOT NULL
                        ) AS diasLunes,
                        (
                            SELECT COUNT(*)
                            FROM [dbo].[fn_getDiaHabiles](
                                MovimientoPercepcion.PRENOM_FechaInicioPeriodo,
                                MovimientoPercepcion.PRENOM_FechaFinPeriodo,
                                0,0,PAMOD_Martes,0,0,0,0
                            ) WHERE fecha IS NOT NULL
                        ) AS diasMartes,
                        (
                            SELECT COUNT(*)
                            FROM [dbo].[fn_getDiaHabiles](
                                MovimientoPercepcion.PRENOM_FechaInicioPeriodo,
                                MovimientoPercepcion.PRENOM_FechaFinPeriodo,
                                0,0,0,PAMOD_Miercoles,0,0,0
                            ) WHERE fecha IS NOT NULL
                        ) AS diasMiercoles,
                        (
                            SELECT COUNT(*)
                            FROM [dbo].[fn_getDiaHabiles](
                                MovimientoPercepcion.PRENOM_FechaInicioPeriodo,
                                MovimientoPercepcion.PRENOM_FechaFinPeriodo,
                                0,0,0,0,PAMOD_Jueves,0,0
                            ) WHERE fecha IS NOT NULL
                        ) AS diasJueves,
                        (
                            SELECT COUNT(*)
                            FROM [dbo].[fn_getDiaHabiles](
                                MovimientoPercepcion.PRENOM_FechaInicioPeriodo,
                                MovimientoPercepcion.PRENOM_FechaFinPeriodo,
                                0,0,0,0,0,PAMOD_Viernes,0
                            ) WHERE fecha IS NOT NULL
                        ) AS diasViernes,
                        (
                            SELECT COUNT(*)
                            FROM [dbo].[fn_getDiaHabiles](
                                MovimientoPercepcion.PRENOM_FechaInicioPeriodo,
                                MovimientoPercepcion.PRENOM_FechaFinPeriodo,
                                0,0,0,0,0,0,PAMOD_Sabado
                            ) WHERE fecha IS NOT NULL
                        ) AS diasSabado
                    FROM VW_ProgramasGruposProfesores AS ProfesorTitularOriginal
                    INNER JOIN PrenominaMovimientos AS MovimientoPercepcion
                        ON MovimientoPercepcion.PRENOM_EMP_EmpleadoId = ProfesorTitularOriginal.empleadoId
                        AND MovimientoPercepcion.PRENOM_FechaInicioQuincena = @fechaInicioQuincena
                        AND MovimientoPercepcion.PRENOM_FechaFinQuincena = @fechaFinQuincena
                        AND MovimientoPercepcion.PRENOM_CMM_TipoMovimientoId = 2000690 -- Tipo: pago a profesor titular
                        AND MovimientoPercepcion.PRENOM_ReferenciaProcesoTabla = 'ProgramasGrupos'
                        AND MovimientoPercepcion.PRENOM_ReferenciaProcesoId = ProfesorTitularOriginal.grupoId
                    LEFT JOIN PrenominaMovimientos AS MovimientoDeduccion
                        ON MovimientoDeduccion.PRENOM_PRENOM_MovimientoReferenciaId = MovimientoPercepcion.PRENOM_PrenominaMovimientoId
                        AND MovimientoDeduccion.PRENOM_CMM_TipoMovimientoId = 2000696 -- Tipo: Deducción por cambio de profesor titular
                        AND MovimientoDeduccion.PRENOM_ReferenciaProcesoTabla = 'PrenominaMovimientos'
                        AND MovimientoDeduccion.PRENOM_ReferenciaProcesoId = MovimientoPercepcion.PRENOM_PrenominaMovimientoId
                    INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = ProfesorTitularOriginal.grupoId
                    INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
                    WHERE ProfesorTitularOriginal.activo = 0
                ) AS GruposDiasSemana
                INNER JOIN VW_GRUPOS_HORARIOS ON grupoId = idGrupo
            ) AS GruposHoras
            GROUP BY idEmpleado, idSucursal, idGrupo, codigoGrupo, sueldo, fechaInicioPeriodo, fechaFinPeriodo, horasDeduccion, prenominaMovimientoId
            HAVING SUM(horasDomingo + horasLunes + horasMartes + horasMiercoles + horasJueves + horasViernes + horasSabado)-horasDeduccion > 0
        ) AS GruposHorasAgrupado
        INNER JOIN Empleados ON EMP_EmpleadoId = idEmpleado
        INNER JOIN Sucursales ON SUC_SucursalId = idSucursal
        INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = idGrupo
        INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
        INNER JOIN ControlesMaestrosMultiples AS CMMIdioma ON CMMIdioma.CMM_ControlId = PROGI_CMM_Idioma
        INNER JOIN ControlesMaestrosMultiples AS CMMTipoGrupo ON CMMTipoGrupo.CMM_ControlId = PROGRU_CMM_TipoGrupoId
        LEFT JOIN TabuladoresCursos
            ON TABC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
            AND TABC_PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
            AND TABC_Activo=1
        LEFT JOIN Tabuladores ON TAB_TabuladorId = TABC_TAB_TabuladorId

        /*****^^^^^^^^^^^*****/
        /***** Inactivos *****/
        /*********************/
    ) PrenominaDeduccionesCambioProfesorTitular
    ORDER BY idEmpleado,codigoGrupo ASC

    RETURN;
END
