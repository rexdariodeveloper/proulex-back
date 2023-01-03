SET IDENTITY_INSERT ControlesMaestrosMultiples ON
INSERT INTO ControlesMaestrosMultiples
           (CMM_ControlId,CMM_Control,CMM_Valor,CMM_Activo,CMM_Sistema,CMM_FechaCreacion)
     VALUES
           (2000378,'CMM_PROGI_TestFormat','MyEnglishLab',1,1,GETDATE()),
		   (2000379,'CMM_PROGI_TestFormat','Online',1,1,GETDATE()),
		   (2000380,'CMM_PROGI_TestFormat','Email',1,1,GETDATE()),
		   (2000381,'CMM_PROGI_TestFormat','Zoom',1,1,GETDATE()),
		   (2000382,'CMM_PROGI_TestFormat','Google Forms',1,1,GETDATE()),
		   (2000383,'CMM_PROGI_TestFormat','MyELT',1,1,GETDATE()),
		   (2000384,'CMM_PROGI_Plataforma','MEL',1,1,GETDATE()),
		   (2000385,'CMM_PROGI_Plataforma','CANVAS',1,1,GETDATE())
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
