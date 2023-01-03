UPDATE PAModalidades
SET PAMOD_Activo = 0
WHERE PAMOD_Activo=1;

INSERT INTO PAModalidades
(PAMOD_Codigo,PAMOD_Nombre,PAMOD_HorasPorDia,PAMOD_DiasPorSemana,PAMOD_Lunes,PAMOD_Martes,
 PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado,PAMOD_Domingo,PAMOD_Activo,
 PAMOD_USU_CreadoPorId)
VALUES
('INT','Intensivo',2,6,1,1,1,1,1,1,0,1,1),
('SEMIA','Semi-Intensivo A',2,2,1,0,1,0,0,0,0,1,1),
('SEMIB','Semi-Intensivo B',2,2,0,1,0,1,0,0,0,1,1),
('SEMIC','Semi-Intensivo C',2,2,0,1,0,1,0,0,0,1,1),
('SABA','Sabatino A',5,1,0,0,0,0,0,1,0,1,1),
('SABB','Sabatino B',5,1,0,0,0,0,0,1,0,1,1),
('PETTD','Intensivo PETTD',5,1,1,1,0,1,1,0,0,1,1);

INSERT INTO PAModalidadesHorarios
(PAMODH_PAMOD_ModalidadId,PAMODH_Horario)
VALUES
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Intensivo' AND PAMOD_Activo=1),'7:00 - 9:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Intensivo' AND PAMOD_Activo=1),'9:00 - 11:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Intensivo' AND PAMOD_Activo=1),'11:00 - 13:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Intensivo' AND PAMOD_Activo=1),'13:00 - 15:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Intensivo' AND PAMOD_Activo=1),'15:00 - 17:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Intensivo' AND PAMOD_Activo=1),'17:00 - 19:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Intensivo' AND PAMOD_Activo=1),'19:00 - 21:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Semi-Intensivo A' AND PAMOD_Activo=1),'15:00 - 17:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Semi-Intensivo A' AND PAMOD_Activo=1),'17:00 - 19:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Semi-Intensivo B' AND PAMOD_Activo=1),'15:00 - 17:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Semi-Intensivo B' AND PAMOD_Activo=1),'17:00 - 19:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Semi-Intensivo C' AND PAMOD_Activo=1),'15:00 - 17:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Semi-Intensivo C' AND PAMOD_Activo=1),'17:00 - 19:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Sabatino A' AND PAMOD_Activo=1),'9:00 - 14:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Sabatino A' AND PAMOD_Activo=1),'15:00 - 20:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Sabatino B' AND PAMOD_Activo=1),'9:00 - 14:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Sabatino B' AND PAMOD_Activo=1),'15:00 - 20:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Intensivo PETTD' AND PAMOD_Activo=1),'9:00 - 13:00'),
((Select PAMOD_ModalidadId from PAModalidades where PAMOD_Nombre='Intensivo PETTD' AND PAMOD_Activo=1),'17:00 - 21:00');
