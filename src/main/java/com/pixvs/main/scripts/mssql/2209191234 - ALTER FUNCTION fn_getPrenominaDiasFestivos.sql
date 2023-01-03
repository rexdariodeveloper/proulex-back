/**
* Created by Angel Daniel Hernández Silva on 19/09/2022.
*/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getPrenominaDiasFestivos] (@fechaInicioQuincena Date, @fechaFinQuincena Date)
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
    SELECT
        EMP_EmpleadoId AS idEmpleado,
        EMP_CodigoEmpleado AS codigoEmpleado,
        SUC_SucursalId AS idSucursal,
        SUC_Nombre AS sucursal,
        PROGRU_GrupoId AS idGrupo,
        PROGRU_Codigo AS codigoGrupo,
        CONCAT(EMP_PrimerApellido,' ' + EMP_SegundoApellido, ' ' + EMP_Nombre) AS empleado,
        NULL AS deduccionPercepcionId,
        TAB_Codigo AS tabulador,
        CONCAT('Día festivo', ' ',fecha) AS nombreGrupo,
        horas AS horasPagadas,
        FORMAT(TABD_Sueldo * horas, 'C', 'en-us') AS percepcion,
        FORMAT(0, 'C', 'en-us') AS deduccion,
        CMMIdioma.CMM_Valor AS idioma,
        fecha AS fechaDiaFestivo,
        NULL AS fechaDiaSuplida,
        PAPC_Categoria AS categoria,
        FORMAT(TABD_Sueldo, 'C', 'en-us') AS sueldoPorHora,
        NULL AS idSuplencia,
        fecha AS fechaInicioPeriodo,
        fecha AS fechaFinPeriodo,
        TABD_Sueldo AS sueldoPorHoraDecimal,
        2000694 AS tipoMovimientoId, -- Tipo: Pago por día festivo
        NULL AS movimientoReferenciaId,
        'ProgramasGrupos' AS referenciaProcesoTabla,
        PROGRU_GrupoId AS referenciaProcesoId,
        CMMTipoGrupo.CMM_Valor AS modalidad,
        PROGRU_FechaInicio AS grupoFechaInicio,
        PROGRU_FechaFin AS grupoFechaFin
    FROM dbo.fn_getDiasFestivos(@fechaInicioQuincena,@fechaFinQuincena,1,1,1,1,1,1,1)
    INNER JOIN VW_ProgramasGruposProfesores
        ON fechaInicio <= fecha
        AND fechaFin >= fecha
    INNER JOIN VW_GRUPOS_HORARIOS ON VW_GRUPOS_HORARIOS.grupoId = VW_ProgramasGruposProfesores.grupoId AND dia = DATEPART(WEEKDAY,fecha)
    INNER JOIN Empleados ON EMP_EmpleadoId = empleadoId
    INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = VW_ProgramasGruposProfesores.grupoId
    INNER JOIN Sucursales ON SUC_SucursalId = PROGRU_SUC_SucursalId
    INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
    INNER JOIN ControlesMaestrosMultiples AS CMMIdioma ON CMMIdioma.CMM_ControlId = PROGI_CMM_Idioma
    INNER JOIN ControlesMaestrosMultiples AS CMMTipoGrupo ON CMMTipoGrupo.CMM_ControlId = PROGRU_CMM_TipoGrupoId
    INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
    LEFT JOIN Tabuladores ON TAB_PagoDiasFestivos = 1
    LEFT JOIN EmpleadosCategorias
            ON EMPCA_EMP_EmpleadoId = empleadoId
            AND EMPCA_CMM_IdiomaId = PROGI_CMM_Idioma
            AND EMPCA_Activo = 1
    LEFT JOIN PAProfesoresCategorias ON PAPC_ProfesorCategoriaId = EMPCA_PAPC_ProfesorCategoriaId
    LEFT JOIN TabuladoresDetalles
        ON TABD_TAB_TabuladorId = TAB_TabuladorId
        AND TABD_PAPC_ProfesorCategoriaId = EMPCA_PAPC_ProfesorCategoriaId
        AND TABD_Activo=1
    LEFT JOIN PrenominaMovimientos
        ON PRENOM_CMM_TipoMovimientoId = 2000694
        AND PRENOM_ReferenciaProcesoTabla = 'ProgramasGrupos'
        AND PRENOM_ReferenciaProcesoId = PROGRU_GrupoId
        AND PRENOM_FechaInicioPeriodo = fecha
        AND PRENOM_FechaFinPeriodo = fecha
    WHERE
            activo = 1
            AND fecha >= fechaInicio
            AND fecha <= fechaFin
            AND EMP_FechaAlta <= fecha
            AND fecha IS NOT NULL
            AND PRENOM_PrenominaMovimientoId IS NULL
    ORDER BY EMP_EmpleadoId,PROGRU_Codigo ASC

    RETURN;
END