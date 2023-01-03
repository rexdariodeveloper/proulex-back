INSERT INTO PAModalidadesHorarios
(PAMODH_PAMOD_ModalidadId,PAMODH_Horario,PAMODH_HoraInicio,PAMODH_HoraFin)
VALUES
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Semi-Intensivo SEMS' AND PAMOD_Activo=1),'09:00 - 12:00','09:00','12:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Semi-Intensivo SEMS' AND PAMOD_Activo=1),'15:00 - 18:00','15:00','18:00')