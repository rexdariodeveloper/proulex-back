UPDATE BECAS_ALUMNOS_RH
SET SEDEPLX = SUC_CodigoSucursal,
    NIVELPLX = PROGRU_Nivel,
    HORARIOPLX = REPLACE(PAMODH_Horario,' ',''),
    GRUPOPLX = CAST(PROGRU_Grupo AS NVARCHAR(2)),
    CODCURPLX = PROGRU_Codigo,
    FECHAINIPLX = PROGRU_FechaInicio,
    FECHAFINPLX = PROGRU_FechaFin,
    CALPLX = FLOOR(ALUG_CalificacionFinal),
    FECHACALPLX = GETDATE()
FROM BECAS_ALUMNOS_RH
INNER JOIN BecasUDG ON ID = BECU_SIAPId
INNER JOIN Inscripciones ON INS_BECU_BecaId = BECU_BecaId
INNER JOIN ProgramasGrupos ON INS_PROGRU_GrupoId = PROGRU_GrupoId
INNER JOIN PAModalidadesHorarios ON PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
INNER JOIN Sucursales ON PROGRU_SUC_SucursalId = SUC_SucursalId
INNER JOIN AlumnosGrupos ON INS_InscripcionId = ALUG_INS_InscripcionId
WHERE
	BECU_CMM_EstatusId = 2000571 -- Becas aplicadas
	AND INS_CMM_EstatusId IN (2000510, 2000511) -- Inscripciones pagadas y pendientes de pago
	AND PROGRU_CMM_EstatusId = 2000621 -- Grupo finalizado