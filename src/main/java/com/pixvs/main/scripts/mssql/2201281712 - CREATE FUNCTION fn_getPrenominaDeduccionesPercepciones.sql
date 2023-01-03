/**
 * Created by Angel Daniel Hernández Silva on 25/01/2021.
 * Object: CREATE FUNCTION [dbo].[fn_getPrenominaDeduccionesPercepciones]
 */
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getPrenominaDeduccionesPercepciones] (@fechaInicioQuincena Date, @fechaFinQuincena Date)
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
    SELECT
        idEmpleado,
        codigoEmpleado,
        idSucursal,
        sucursal,
        NULL AS idGrupo,
        codigoGrupo,
        empleado,
        deduccionPercepcionId,
        tabulador,
        nombreGrupo,
        horasTotales AS horasPagadas,
        FORMAT(percepcion, 'C', 'en-us') AS percepcion,
        FORMAT(deduccion, 'C', 'en-us') AS deduccion,
        '#N/A' AS idioma,
        NULL AS fechaDiaFestivo,
        NULL AS fechaDiaSuplida,
        categoria,
        FORMAT(sueldoPorHora, 'C', 'en-us') AS sueldoPorHora,
        NULL AS idSuplencia,
        EDP_Fecha AS fechaInicioPeriodo,
        EDP_Fecha AS fechaFinPeriodo,
        sueldoPorHora AS sueldoPorHoraDecimal,
        CASE
            WHEN EDP_CMM_TipoMovimientoId = 2000606 THEN 2000693 -- Tipo: percepción manual
            ELSE 2000692 -- Tipo: Deducción manual
        END AS tipoMovimientoId,
        NULL AS movimientoReferenciaId
    FROM(
        SELECT
            EMP_EmpleadoId AS idEmpleado,
            EMP_CodigoEmpleado AS codigoEmpleado,
            SUC_SucursalId AS idSucursal,
            SUC_Nombre AS sucursal,
            '#N/A' AS codigoGrupo,
            CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ' + EMP_SegundoApellido) AS empleado,
            DEDPER_Concepto AS nombreGrupo,
            CASE
                WHEN EDP_CMM_TipoMovimientoId = 2000606 THEN EDP_Total -- Tipo: Percepción
                ELSE 0
            END percepcion,
            CASE
                WHEN EDP_CMM_TipoMovimientoId = 2000605 THEN EDP_Total -- Tipo: Deducción
                ELSE 0
            END deduccion,
            EDP_CantidadHoras AS horasTotales,
            TAB_Codigo AS tabulador,
            EDP_EmpleadoDeduccionPercepcionId AS deduccionPercepcionId,
            PROGRU_CategoriaProfesor AS categoria,
            sueldo AS sueldoPorHora,
            EDP_CMM_TipoMovimientoId,
            EDP_Fecha
        FROM VW_ProgramasGruposProfesores
        INNER JOIN Empleados ON EMP_EmpleadoId = empleadoId
        LEFT JOIN ProgramasGrupos ON PROGRU_GrupoId = grupoId
        INNER JOIN EmpleadosDeduccionesPercepciones
            ON EDP_EMP_EmpleadoId = EMP_EmpleadoId
            AND EDP_Activo=1
        INNER JOIN DeduccionesPercepciones ON DEDPER_DeduccionPercepcionId = EDP_DEDPER_DeduccionPercepcionId
        INNER JOIN Sucursales ON SUC_SucursalId = EDP_SUC_SucursalId
        INNER JOIN Tabuladores
            ON TAB_TabuladorId = DEDPER_TAB_TabuladorId
            AND TAB_Activo=1
        LEFT JOIN Prenominas
            ON PRENO_EDP_EmpleadoDeduccionPercepcionId = EDP_EmpleadoDeduccionPercepcionId
            AND PRENO_FechaFinQuincena >= EDP_FechaCreacion
            AND PRENO_FechaInicioQuincena <= EDP_FechaCreacion
        WHERE
            PRENO_PrenominaId IS NULL
            AND EDP_FechaCreacion >= @fechaInicioQuincena
            AND EDP_FechaCreacion <= @fechaFinQuincena
        GROUP BY
            EMP_EmpleadoId, EMP_CodigoEmpleado, EMP_Nombre, EMP_PrimerApellido, EMP_SegundoApellido, 
            DEDPER_Concepto, EDP_CMM_TipoMovimientoId, EDP_CantidadHoras, EDP_EmpleadoDeduccionPercepcionId, EDP_Total,
            SUC_SucursalId, SUC_Nombre, TAB_Codigo, PROGRU_CategoriaProfesor, sueldo, EDP_CMM_TipoMovimientoId, EDP_Fecha
    ) PrenominaPercepcionesDeducciones
	ORDER BY idEmpleado,codigoGrupo ASC

    RETURN;
END