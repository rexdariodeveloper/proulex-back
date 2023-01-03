SET IDENTITY_INSERT ControlesMaestrosMultiples ON
INSERT INTO ControlesMaestrosMultiples
           (CMM_ControlId,CMM_Control,CMM_Valor,CMM_Activo,CMM_Sistema,CMM_FechaCreacion)
     VALUES
           (2000390,'CMM_PROGRU_TipoGrupo','Virtual',1,1,GETDATE()),
		   (2000391,'CMM_PROGRU_TipoGrupo','Presencial',1,1,GETDATE()),
		   (2000392,'CMM_PROGRU_TipoGrupo','Hibrido',1,1,GETDATE())
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
