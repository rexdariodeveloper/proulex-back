UPDATE PA
SET PROGRU_FechaFinInscripciones=(Select top 1 fecha order by fecha desc)
FROM ProgramasGrupos PA
INNER JOIN PAModalidades on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
OUTER APPLY dbo.fn_getFechaPorModalidad(PROGRU_FechaInicio,PAMOD_DiasFinPeriodoInscripcion,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado) wh
WHERE PROGRU_CMM_EstatusId=2000620 AND PROGRU_Activo=1
GO

UPDATE PA
SET PROGRU_FechaFinInscripcionesBecas=(Select top 1 fecha order by fecha desc)
FROM ProgramasGrupos PA
INNER JOIN PAModalidades on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
OUTER APPLY dbo.fn_getFechaPorModalidad(PROGRU_FechaInicio,PAMOD_DiasFinPeriodoInscripcionBeca,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado) wh
WHERE PROGRU_CMM_EstatusId=2000620 AND PROGRU_Activo=1
GO