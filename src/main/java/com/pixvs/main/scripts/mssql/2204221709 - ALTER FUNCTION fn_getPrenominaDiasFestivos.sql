/**
* Created by Angel Daniel Hernández Silva on 22/04/2022.
* Object: CREATE FUNCTION [dbo].[fn_getPrenominaDiasFestivos]
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
    referenciaProcesoId int
)

AS BEGIN
    INSERT @tablaTMP
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
        horasTotales AS horasPagadas,
        FORMAT(sueldo * horasSuplidas, 'C', 'en-us') AS percepcion,
        FORMAT(deduccion, 'C', 'en-us') AS deduccion,
        idioma,
        fechaDiaFestivo,
        NULL AS fechaDiaSuplida,
        categoria,
        FORMAT(sueldoPorHora, 'C', 'en-us') AS sueldoPorHora,
        NULL AS idSuplencia,
        fechaDiaFestivo AS fechaInicioPeriodo,
        fechaDiaFestivo AS fechaFinPeriodo,
        sueldoPorHora AS sueldoPorHoraDecimal,
        2000694 AS tipoMovimientoId, -- Tipo: Pago por día festivo
        NULL AS movimientoReferenciaId,
        'ProgramasGrupos' AS referenciaProcesoTabla,
        idGrupo AS referenciaProcesoId
    FROM(
        SELECT 
            EMP_EmpleadoId AS idEmpleado,
            EMP_CodigoEmpleado AS codigoEmpleado,
            SUC_SucursalId AS idSucursal,
            SUC_Nombre AS sucursal,
            PROGRU_GrupoId AS idGrupo,
            PROGRU_Codigo AS codigoGrupo,
            CONCAT(EMP_PrimerApellido,' ' + EMP_SegundoApellido, ' ' + EMP_Nombre) AS empleado,
            CONCAT('Día festivo', ' ',fecha) AS nombreGrupo,
            (DATEDIFF(MINUTE,PAMODH_HoraInicio,PAMODH_HoraFin) / 60.0) AS horasSuplidas,
            CMM_Valor AS idioma,
            TABD_Sueldo AS sueldo,
            0 AS deduccion,
            (DATEDIFF(MINUTE,PAMODH_HoraInicio,PAMODH_HoraFin) / 60.0) AS horasTotales,
            TAB_Codigo AS tabulador,
            fecha AS fechaDiaFestivo,
            PAPC_Categoria AS categoria,
            COALESCE(TABD_Sueldo,sueldo) AS sueldoPorHora
        FROM VW_ProgramasGruposProfesores
        INNER JOIN Empleados ON EMP_EmpleadoId = empleadoId
        INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = grupoId
        INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
        LEFT JOIN EmpleadosCategorias
            ON EMPCA_EMP_EmpleadoId = empleadoId
            AND EMPCA_CMM_IdiomaId = PROGI_CMM_Idioma
            AND EMPCA_Activo = 1
        LEFT JOIN PAProfesoresCategorias ON PAPC_ProfesorCategoriaId = EMPCA_PAPC_ProfesorCategoriaId
        LEFT JOIN Sucursales ON SUC_SucursalId = PROGRU_SUC_SucursalId
        LEFT JOIN PAModalidades modalidad ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
        OUTER APPLY dbo.fn_getDiasFestivos(
            @fechaInicioQuincena,
            @fechaFinQuincena,
            PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado
        )
        INNER JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
        LEFT JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
        LEFT JOIN ControlesMaestrosMultiples CMM ON CMM_ControlId = PROGI_CMM_Idioma
        LEFT JOIN TabuladoresCursos
            ON TABC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
            AND TABC_PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
            AND TABC_Activo=1
        LEFT JOIN Tabuladores
            ON TAB_TabuladorId = TABC_TAB_TabuladorId
            AND TAB_PagoDiasFestivos = 1
        LEFT JOIN TabuladoresDetalles
            ON TABD_TAB_TabuladorId = TAB_TabuladorId
            AND TABD_PAPC_ProfesorCategoriaId = EMPCA_PAPC_ProfesorCategoriaId
            AND TABD_Activo=1
        LEFT JOIN PrenominaMovimientos
            ON PRENOM_CMM_TipoMovimientoId = 2000694
            AND PRENOM_ReferenciaProcesoTabla = 'ProgramasGrupos'
            AND PRENOM_ReferenciaProcesoId = grupoId
            AND PRENOM_FechaInicioPeriodo = fecha
            AND PRENOM_FechaFinPeriodo = fecha
        WHERE
            activo = 1
            AND PROGRU_Activo = 1
            AND fecha > fechaInicio
            AND fecha < fechaFin
            AND EMP_FechaAlta <= fecha
            AND fecha IS NOT NULL
            AND PRENOM_PrenominaMovimientoId IS NULL
    ) PrenominaPercepcionesDiasFestivos
    ORDER BY idEmpleado,codigoGrupo ASC

    RETURN;
END