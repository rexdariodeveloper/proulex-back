/**
* Created by Angel Daniel Hernández Silva on 19/09/2022.
*/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getPrenominaDeduccionesCancelacionClases] (@fechaInicioQuincena Date, @fechaFinQuincena Date)
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
        CONCAT(PROG_Codigo,' ',CMMIdioma.CMM_Valor,' ',PAMOD_Nombre,' Nivel ',FORMAT(PROGRU_Nivel,'00'),' Grupo ',FORMAT(PROGRU_Grupo,'00')) AS nombreGrupo,
        horas * -1 AS horasPagadas,
        FORMAT(0, 'C', 'en-us') AS percepcion,
        FORMAT(COALESCE(MovimientoPagoTitular.PRENOM_SueldoPorHora,sueldo) * horas, 'C', 'en-us') AS deduccion,
        CMMIdioma.CMM_Valor AS idioma,
        NULL AS fechaDiaFestivo,
        NULL AS fechaDiaSuplida,
        PAPC_Categoria AS categoria,
        FORMAT(COALESCE(MovimientoPagoTitular.PRENOM_SueldoPorHora,sueldo), 'C', 'en-us') AS sueldoPorHora,
        NULL AS idSuplencia,
        PGINCCL_FechaCancelar AS fechaInicioPeriodo,
        PGINCCL_FechaCancelar AS fechaFinPeriodo,
        COALESCE(MovimientoPagoTitular.PRENOM_SueldoPorHora,sueldo) AS sueldoPorHoraDecimal,
        2000697 AS tipoMovimientoId, -- Tipo: Deducción por cancelación de clase
        NULL AS movimientoReferenciaId,
        'ProgramasGruposIncompanyClasesCanceladas' AS referenciaProcesoTabla,
        PGINCCL_ProgramaIncompanyClaseCanceladaId AS referenciaProcesoId,
        CMMTipoGrupo.CMM_Valor AS modalidad,
        PROGRU_FechaInicio AS grupoFechaInicio,
        PROGRU_FechaFin AS grupoFechaFin
    FROM ProgramasGruposIncompanyClasesCanceladas
    INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = PGINCCL_PROGRU_GrupoId
    INNER JOIN VW_ProgramasGruposProfesores AS GrupoProfesores
        ON GrupoProfesores.grupoId = PROGRU_GrupoId
        AND FORMAT(GrupoProfesores.fechaInicio,'yyyy-MM-dd') <= FORMAT(PGINCCL_FechaCancelar,'yyyy-MM-dd')
        AND FORMAT(GrupoProfesores.fechaFin,'yyyy-MM-dd') >= FORMAT(PGINCCL_FechaCancelar,'yyyy-MM-dd')
    INNER JOIN Empleados ON EMP_EmpleadoId = GrupoProfesores.empleadoId
    INNER JOIN Sucursales ON SUC_SucursalId = PROGRU_SUC_SucursalId
    INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
    INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
    INNER JOIN ControlesMaestrosMultiples AS CMMIdioma ON CMMIdioma.CMM_ControlId = PROGI_CMM_Idioma
    INNER JOIN ControlesMaestrosMultiples AS CMMTipoGrupo ON CMMTipoGrupo.CMM_ControlId = PROGRU_CMM_TipoGrupoId
    INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
    INNER JOIN VW_GRUPOS_HORARIOS ON VW_GRUPOS_HORARIOS.grupoId = PROGRU_GrupoId AND dia = DATEPART(WEEKDAY,PGINCCL_FechaCancelar)
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
    LEFT JOIN PrenominaMovimientos AS MovimientoPagoTitular
        ON MovimientoPagoTitular.PRENOM_EMP_EmpleadoId = EMP_EmpleadoId
        AND MovimientoPagoTitular.PRENOM_CMM_TipoMovimientoId = 2000690 -- Pago a profesor titular
        AND FORMAT(MovimientoPagoTitular.PRENOM_FechaInicioPeriodo,'yyyy-MM-dd') <= FORMAT(PGINCCL_FechaCancelar,'yyyy-MM-dd')
        AND FORMAT(MovimientoPagoTitular.PRENOM_FechaFinPeriodo,'yyyy-MM-dd') >= FORMAT(PGINCCL_FechaCancelar,'yyyy-MM-dd')
    LEFT JOIN PrenominaMovimientos AS MovimientoDeduccionCambioProfesor -- Omitir casos donde ya se hizo una deducción por cambio de profesor titular
        ON MovimientoDeduccionCambioProfesor.PRENOM_EMP_EmpleadoId = EMP_EmpleadoId
        AND MovimientoDeduccionCambioProfesor.PRENOM_CMM_TipoMovimientoId = 2000696 -- Pago a profesor titular
        AND FORMAT(MovimientoDeduccionCambioProfesor.PRENOM_FechaInicioPeriodo,'yyyy-MM-dd') <= FORMAT(PGINCCL_FechaCancelar,'yyyy-MM-dd')
        AND FORMAT(MovimientoDeduccionCambioProfesor.PRENOM_FechaFinPeriodo,'yyyy-MM-dd') >= FORMAT(PGINCCL_FechaCancelar,'yyyy-MM-dd')
    LEFT JOIN ProgramasGruposListadoClases -- Omitir casos donde la clase se debe pagar a un profesor sustituto
        ON PROGRULC_PROGRU_GrupoId = PROGRU_GrupoId
        AND FORMAT(PROGRULC_Fecha,'yyyy-MM-dd') = FORMAT(PGINCCL_FechaCancelar,'yyyy-MM-dd')
        AND PROGRULC_CMM_FormaPagoId IN (2000520,2000523)
    LEFT JOIN PrenominaMovimientos AS MovimientoDeduccionPrevia -- Omitir casos donde ya se hizo una deducción por el mismo concepto
        ON MovimientoDeduccionPrevia.PRENOM_EMP_EmpleadoId = EMP_EmpleadoId
        AND MovimientoDeduccionPrevia.PRENOM_CMM_TipoMovimientoId = 2000697 -- Deducción por cancelación de clase
        AND MovimientoDeduccionPrevia.PRENOM_ReferenciaProcesoId = PGINCCL_ProgramaIncompanyClaseCanceladaId
    WHERE
        MovimientoDeduccionCambioProfesor.PRENOM_PrenominaMovimientoId IS NULL
        AND PROGRULC_ProgramaGrupoListadoClaseId IS NULL
        AND MovimientoDeduccionPrevia.PRENOM_PrenominaMovimientoId IS NULL

    UNION ALL

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
        CONCAT(PROG_Codigo,' ',CMMIdioma.CMM_Valor,' ',PAMOD_Nombre,' Nivel ',FORMAT(PROGRU_Nivel,'00'),' Grupo ',FORMAT(PROGRU_Grupo,'00')) AS nombreGrupo,
        horas * -1 AS horasPagadas,
        FORMAT(0, 'C', 'en-us') AS percepcion,
        FORMAT(COALESCE(MovimientoPagoSuplente.PRENOM_SueldoPorHora,PROGRULC_SueldoProfesor) * horas, 'C', 'en-us') AS deduccion,
        CMMIdioma.CMM_Valor AS idioma,
        NULL AS fechaDiaFestivo,
        NULL AS fechaDiaSuplida,
        PAPC_Categoria AS categoria,
        FORMAT(COALESCE(MovimientoPagoSuplente.PRENOM_SueldoPorHora,PROGRULC_SueldoProfesor), 'C', 'en-us') AS sueldoPorHora,
        NULL AS idSuplencia,
        PGINCCL_FechaCancelar AS fechaInicioPeriodo,
        PGINCCL_FechaCancelar AS fechaFinPeriodo,
        COALESCE(MovimientoPagoSuplente.PRENOM_SueldoPorHora,PROGRULC_SueldoProfesor) AS sueldoPorHoraDecimal,
        2000697 AS tipoMovimientoId, -- Tipo: Deducción por cancelación de clase
        NULL AS movimientoReferenciaId,
        'ProgramasGruposIncompanyClasesCanceladas' AS referenciaProcesoTabla,
        PGINCCL_ProgramaIncompanyClaseCanceladaId AS referenciaProcesoId,
        CMMTipoGrupo.CMM_Valor AS modalidad,
        PROGRU_FechaInicio AS grupoFechaInicio,
        PROGRU_FechaFin AS grupoFechaFin
    FROM ProgramasGruposIncompanyClasesCanceladas
    INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = PGINCCL_PROGRU_GrupoId
    LEFT JOIN ProgramasGruposListadoClases
        ON PROGRULC_PROGRU_GrupoId = PROGRU_GrupoId
        AND FORMAT(PROGRULC_Fecha,'yyyy-MM-dd') = FORMAT(PGINCCL_FechaCancelar,'yyyy-MM-dd')
        AND PROGRULC_CMM_FormaPagoId IN (2000520,2000523)
    INNER JOIN Empleados ON EMP_EmpleadoId = PROGRULC_EMP_EmpleadoId
    INNER JOIN Sucursales ON SUC_SucursalId = PROGRU_SUC_SucursalId
    INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
    INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
    INNER JOIN ControlesMaestrosMultiples AS CMMIdioma ON CMMIdioma.CMM_ControlId = PROGI_CMM_Idioma
    INNER JOIN ControlesMaestrosMultiples AS CMMTipoGrupo ON CMMTipoGrupo.CMM_ControlId = PROGRU_CMM_TipoGrupoId
    INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
    INNER JOIN VW_GRUPOS_HORARIOS ON VW_GRUPOS_HORARIOS.grupoId = PROGRU_GrupoId AND dia = DATEPART(WEEKDAY,PGINCCL_FechaCancelar)
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
    LEFT JOIN PrenominaMovimientos AS MovimientoPagoSuplente
        ON MovimientoPagoSuplente.PRENOM_EMP_EmpleadoId = EMP_EmpleadoId
        AND MovimientoPagoSuplente.PRENOM_CMM_TipoMovimientoId = 2000691 -- Pago por sustitución
        AND FORMAT(MovimientoPagoSuplente.PRENOM_FechaInicioPeriodo,'yyyy-MM-dd') <= FORMAT(PGINCCL_FechaCancelar,'yyyy-MM-dd')
        AND FORMAT(MovimientoPagoSuplente.PRENOM_FechaFinPeriodo,'yyyy-MM-dd') >= FORMAT(PGINCCL_FechaCancelar,'yyyy-MM-dd')
    LEFT JOIN PrenominaMovimientos AS MovimientoDeduccionPrevia -- Omitir casos donde ya se hizo una deducción por el mismo concepto
        ON MovimientoDeduccionPrevia.PRENOM_EMP_EmpleadoId = EMP_EmpleadoId
        AND MovimientoDeduccionPrevia.PRENOM_CMM_TipoMovimientoId = 2000697 -- Deducción por cancelación de clase
        AND MovimientoDeduccionPrevia.PRENOM_ReferenciaProcesoId = PGINCCL_ProgramaIncompanyClaseCanceladaId
    WHERE MovimientoDeduccionPrevia.PRENOM_PrenominaMovimientoId IS NULL

    ORDER BY EMP_EmpleadoId,PROGRU_Codigo ASC

    RETURN;
END