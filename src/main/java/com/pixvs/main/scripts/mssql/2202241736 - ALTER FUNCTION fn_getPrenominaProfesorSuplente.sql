/**
 * Created by Angel Daniel Hernández Silva on 24/02/2022.
 * Object: CREATE FUNCTION [dbo].[fn_getPrenominaProfesorSuplente]
 */
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getPrenominaProfesorSuplente] (@fechaInicioQuincena Date, @fechaFinQuincena Date)
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
        idGrupo,
        codigoGrupo,
        empleado,
        NULL AS deduccionPercepcionId,
        tabulador,
        nombreGrupo,
        SUM(horasSuplidas) AS horasPagadas,
        FORMAT((sueldo * SUM(horasSuplidas)), 'C', 'en-us') AS percepcion,
        FORMAT(0, 'C', 'en-us') AS deduccion,
        idioma,
        NULL AS fechaDiaFestivo,
        diasSuplido AS fechaDiaSuplida,
        categoria,
        FORMAT(sueldo, 'C', 'en-us') AS sueldoPorHora,
        idSuplencia,
        diasSuplido AS fechaInicioPeriodo,
        diasSuplido AS fechaFinPeriodo,
        sueldo AS sueldoPorHoraDecimal,
        2000691 AS tipoMovimientoId, -- Tipo: Pago por sustitución
        NULL AS movimientoReferenciaId
    FROM(
        SELECT
            EMP_EmpleadoId AS idEmpleado,
            EMP_CodigoEmpleado AS codigoEmpleado,
            SUC_SucursalId AS idSucursal,
            SUC_Nombre AS sucursal,
            PROGRU_GrupoId AS idGrupo,
            PROGRU_Codigo AS codigoGrupo,
            CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ' + EMP_SegundoApellido) AS empleado,
            CONCAT(PROG_Codigo,' ',CMM_Valor,' ',PAMOD_Nombre,' Nivel ',FORMAT(PROGRU_Nivel,'00'),' Grupo ',FORMAT(PROGRU_Grupo,'00')) AS nombreGrupo,
            CASE
                WHEN PROGRULC_ProgramaGrupoListadoClaseId IS NOT NULL THEN (DATEDIFF(MINUTE,PAMODH_HoraInicio,PAMODH_HoraFin) / 60.0)
                ELSE 0
            END horasSuplidas,
            CMM_Valor AS idioma,
            PROGRULC_SueldoProfesor AS sueldo,
            TAB_Codigo AS tabulador,
            PROGRULC_Fecha AS diasSuplido,
            PROGRU_CategoriaProfesor AS categoria,
            PROGRULC_ProgramaGrupoListadoClaseId AS idSuplencia
        FROM Empleados
        INNER JOIN ProgramasGruposListadoClases
            ON PROGRULC_EMP_EmpleadoId = EMP_EmpleadoId
            AND PROGRULC_FechaPago IS NULL
            AND PROGRULC_CMM_FormaPagoId IN (2000520,2000522) -- Forma de pago: (Pago suplente,Con pago)
            AND PROGRULC_Fecha <= @fechaFinQuincena
        INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = PROGRULC_PROGRU_GrupoId
        LEFT JOIN Sucursales ON SUC_SucursalId = PROGRU_SUC_SucursalId
        LEFT JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
        INNER JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
        LEFT JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
        LEFT JOIN TabuladoresCursos
            ON TABC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
            AND TABC_PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
            AND TABC_Activo=1
        LEFT JOIN Tabuladores ON TAB_TabuladorId = TABC_TAB_TabuladorId
        LEFT JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
        LEFT JOIN ControlesMaestrosMultiples CMM ON CMM_ControlId = PROGI_CMM_Idioma
        WHERE PROGRU_Activo = 1
    ) PrenominaProfesorSuplenteDesglosado
    GROUP BY idEmpleado,codigoEmpleado,sucursal,codigoGrupo,empleado,empleado,nombreGrupo,sueldo,idioma,tabulador,idGrupo,idSucursal,diasSuplido,categoria,idSuplencia
	ORDER BY idEmpleado,codigoGrupo ASC

    RETURN;
END