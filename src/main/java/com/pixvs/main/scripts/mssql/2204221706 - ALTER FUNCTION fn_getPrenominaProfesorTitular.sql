/**
* Created by Angel Daniel Hernández Silva on 22/04/2022.
* Object: ALTER FUNCTION [dbo].[fn_getPrenominaProfesorTitular]
*/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getPrenominaProfesorTitular] (@fechaInicioQuincena date, @fechaFinQuincena date)
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
        horasTotales - SUM(horasSuplidas) AS horasPagadas,
        FORMAT((sueldo * (horasTotales- SUM(horasSuplidas)) ), 'C', 'en-us') AS percepcion,
        FORMAT(0, 'C', 'en-us') AS deduccion,
        idioma,
        NULL AS fechaDiaFestivo,
        NULL AS fechaDiaSuplida,
        categoria,
        FORMAT(sueldo, 'C', 'en-us') AS sueldoPorHora,
        NULL AS idSuplencia,
        fechaInicioPeriodo AS fechaInicioPeriodo,
        fechaFinPeriodo AS fechaFinPeriodo,
        sueldo AS sueldoPorHoraDecimal,
        2000690 AS tipoMovimientoId, -- Tipo: Pago a profesor titular
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
            CONCAT(PROG_Codigo,' ',CMM_Valor,' ',PAMOD_Nombre,' Nivel ',FORMAT(PROGRU_Nivel,'00'),' Grupo ',FORMAT(PROGRU_Grupo,'00')) AS nombreGrupo,
            0 AS horasSuplidas,
            CMM_Valor AS idioma,
            sueldo AS sueldo,
            (
                SELECT COUNT(*)
                FROM [dbo].[fn_getDiaHabiles](
                    CASE WHEN fechaInicio > @fechaInicioQuincena THEN fechaInicio ELSE @fechaInicioQuincena END,
                    CASE WHEN fechaFin < @fechaFinQuincena THEN fechaFin ELSE @fechaFinQuincena END,
                    PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado
                ) WHERE fecha IS NOT NULL
            ) * (DATEDIFF(MINUTE,PAMODH_HoraInicio,PAMODH_HoraFin) / 60.0) AS horasTotales,
            TAB_Codigo AS tabulador,
            PROGRU_CategoriaProfesor AS categoria,
            CASE
                WHEN fechaInicio > @fechaInicioQuincena THEN fechaInicio
                ELSE @fechaInicioQuincena
            END AS fechaInicioPeriodo,
            CASE
                WHEN fechaFin < @fechaFinQuincena THEN fechaFin
                ELSE @fechaFinQuincena
            END AS fechaFinPeriodo
        FROM VW_ProgramasGruposProfesores
        INNER JOIN Empleados ON EMP_EmpleadoId = empleadoId
        INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = grupoId
        LEFT JOIN Sucursales ON SUC_SucursalId = PROGRU_SUC_SucursalId
        LEFT JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
        INNER JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
        INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
        LEFT JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
        LEFT JOIN ControlesMaestrosMultiples CMM ON CMM_ControlId = PROGI_CMM_Idioma
        LEFT JOIN TabuladoresCursos
            ON TABC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
            AND TABC_PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
            AND TABC_Activo=1
        LEFT JOIN Tabuladores ON TAB_TabuladorId = TABC_TAB_TabuladorId
        LEFT JOIN PrenominaMovimientos
            ON PRENOM_EMP_EmpleadoId = EMP_EmpleadoId
            AND PRENOM_CMM_TipoMovimientoId = 2000690
            AND PRENOM_ReferenciaProcesoTabla = 'ProgramasGrupos'
            AND PRENOM_ReferenciaProcesoId = grupoId
            AND PRENOM_FechaInicioPeriodo = CASE
                    WHEN fechaInicio > @fechaInicioQuincena THEN fechaInicio
                    ELSE @fechaInicioQuincena
                END
            AND PRENOM_FechaFinPeriodo = CASE
                    WHEN fechaFin < @fechaFinQuincena THEN fechaFin
                    ELSE @fechaFinQuincena
                END
        WHERE
            activo = 1
            AND PROGRU_Activo = 1
            AND PRENOM_PrenominaMovimientoId IS NULL
            AND (
                (fechaFin >= @fechaInicioQuincena AND fechaFin <= @fechaFinQuincena)
                OR (fechaInicio >= @fechaInicioQuincena AND fechaInicio <= @fechaFinQuincena)
                OR (fechaInicio < @fechaInicioQuincena  AND fechaFin > @fechaFinQuincena)
            )

    ) PrenominaProfesorTitularDesglosado
    WHERE horasTotales > 0
    GROUP BY idEmpleado,codigoEmpleado,sucursal,codigoGrupo,empleado,empleado,nombreGrupo,sueldo,idioma,horasTotales,tabulador,idGrupo,idSucursal,categoria,fechaInicioPeriodo,fechaFinPeriodo
    ORDER BY idEmpleado,codigoGrupo ASC

    RETURN;
END