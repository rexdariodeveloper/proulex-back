UPDATE PAModalidadesHorarios
SET PAMODH_HoraInicio = CAST(LEFT(PAMODH_Horario, CHARINDEX('-', PAMODH_Horario, CHARINDEX('-', PAMODH_Horario)) - 1) AS TIME),
	PAMODH_HoraFin = CAST(SUBSTRING(PAMODH_Horario, (PATINDEX('%-%',[PAMODH_Horario])+1),10) AS TIME)
FROM PAModalidadesHorarios;
