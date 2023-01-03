/**
* Created by Angel Daniel Hernández Silva on 05/07/2022.
*/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getPrenominaDeduccionesDiasSuplencia] (@fechaInicioQuincena Date, @fechaFinQuincena Date)
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
        CONCAT(PROG_Codigo,' ',CMM_Valor,' ',PAMOD_Nombre,' Nivel ',FORMAT(PROGRU_Nivel,'00'),' Grupo ',FORMAT(PROGRU_Grupo,'00')) AS nombreGrupo,
        horas * -1 AS horasPagadas,
        FORMAT(0, 'C', 'en-us') AS percepcion,
        FORMAT(COALESCE(PRENO_SueldoPorHora,sueldo) * horas, 'C', 'en-us') AS deduccion,
        CMM_Valor AS idioma,
        NULL AS fechaDiaFestivo,
        PROGRULC_Fecha AS fechaDiaSuplida,
        PAPC_Categoria AS categoria,
        FORMAT(COALESCE(PRENO_SueldoPorHora,sueldo), 'C', 'en-us') AS sueldoPorHora,
        PROGRULC_ProgramaGrupoListadoClaseId AS idSuplencia,
        PROGRULC_Fecha AS fechaInicioPeriodo,
        PROGRULC_Fecha AS fechaFinPeriodo,
        COALESCE(PRENO_SueldoPorHora,sueldo) AS sueldoPorHoraDecimal,
        2000695 AS tipoMovimientoId, -- Tipo: Pago por sustitución
        NULL AS movimientoReferenciaId,
        'ProgramasGruposListadoClases' AS referenciaProcesoTabla,
        PROGRULC_ProgramaGrupoListadoClaseId AS referenciaProcesoId,
		PAMOD_Nombre AS modalidad,
		PROGRU_FechaInicio AS grupoFechaInicio,
		PROGRU_FechaFin AS grupoFechaFin
	FROM VW_ProgramasGruposProfesores
	INNER JOIN ProgramasGruposListadoClases
		ON PROGRULC_PROGRU_GrupoId = grupoId
		AND PROGRULC_FechaDeduccion IS NULL
		AND PROGRULC_CMM_FormaPagoId IN (2000520,2000523) -- Formas de pago: (Pago suplente,Sin pago)
        AND PROGRULC_FechaDeduccion IS NULL
        AND PROGRULC_FechaCreacion < @fechaFinQuincena
        AND PROGRULC_Fecha >= fechaInicio
        AND PROGRULC_Fecha <= fechaFin
	INNER JOIN VW_GRUPOS_HORARIOS ON VW_GRUPOS_HORARIOS.grupoId = PROGRULC_PROGRU_GrupoId AND dia = DATEPART(WEEKDAY,PROGRULC_Fecha)
	INNER JOIN Empleados ON EMP_EmpleadoId = empleadoId
	INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = PROGRULC_PROGRU_GrupoId
	INNER JOIN Sucursales ON SUC_SucursalId = PROGRU_SUC_SucursalId
	INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
	INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = PROGI_CMM_Idioma
	INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
	LEFT JOIN TabuladoresCursos
        ON TABC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
        AND TABC_PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
        AND TABC_Activo=1
    LEFT JOIN Tabuladores ON TAB_TabuladorId = TABC_TAB_TabuladorId
	LEFT JOIN EmpleadosCategorias
        ON EMPCA_EMP_EmpleadoId = EMP_EmpleadoId
        AND EMPCA_CMM_IdiomaId = PROGI_CMM_Idioma
        AND EMPCA_Activo = 1
	LEFT JOIN PAProfesoresCategorias ON PAPC_ProfesorCategoriaId = EMPCA_PAPC_ProfesorCategoriaId
    LEFT JOIN Prenominas
        ON PRENO_PROGRU_GrupoId = PROGRU_GrupoId
        AND PRENO_EMP_EmpleadoId = EMP_EmpleadoId
        AND PRENO_FechaClaseSuplida = PROGRULC_Fecha
    ORDER BY EMP_EmpleadoId,PROGRU_Codigo ASC

    RETURN;
END