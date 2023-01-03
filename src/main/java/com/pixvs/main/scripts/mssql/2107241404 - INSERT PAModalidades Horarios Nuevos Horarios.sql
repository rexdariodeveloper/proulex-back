INSERT INTO PAModalidadesHorarios
(PAMODH_PAMOD_ModalidadId,PAMODH_Horario,PAMODH_Codigo)
VALUES
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Codigo='SEJ' AND PAMOD_Activo=1),'08:00 - 10:30',12),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Codigo='SEJ' AND PAMOD_Activo=1),'10:30 - 13:00',13),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Codigo='SEJ' AND PAMOD_Activo=1),'13:30 - 16:00',14),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Codigo='SEJ' AND PAMOD_Activo=1),'16:00 - 18:30',15),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Codigo='SEJ' AND PAMOD_Activo=1),'18:30 - 21:00',16),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Codigo='SIK' AND PAMOD_Activo=1),'14:00 - 19:00',17),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Codigo='SIL' AND PAMOD_Activo=1),'14:00 - 19:00',17),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Codigo='SAJ' AND PAMOD_Activo=1),'08:00 - 14:00',18),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Codigo='SAJ' AND PAMOD_Activo=1),'13:00 - 19:00',19),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Codigo='SIS' AND PAMOD_Activo=1),'09:00 - 12:00',20),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Codigo='SIS' AND PAMOD_Activo=1),'15:00 - 18:00',21),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Codigo='UTG' AND PAMOD_Activo=1),'08:00 - 10:00',22),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Codigo='UTG' AND PAMOD_Activo=1),'10:00 - 12:00',23),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Codigo='UTG' AND PAMOD_Activo=1),'12:00 - 14:00',24)
GO

UPDATE PAModalidadesHorarios
SET PAMODH_HoraInicio = CAST(LEFT(PAMODH_Horario, CHARINDEX('-', PAMODH_Horario, CHARINDEX('-', PAMODH_Horario)) - 1) AS TIME),
	PAMODH_HoraFin = CAST(SUBSTRING(PAMODH_Horario, (PATINDEX('%-%',[PAMODH_Horario])+1),10) AS TIME)
FROM PAModalidadesHorarios
GO