UPDATE ControlesMaestrosMultiples 
SET CMM_Valor='(01) Contrato de trabajo por tiempo indeterminado'
where CMM_ControlId=2000296;

UPDATE ControlesMaestrosMultiples 
SET CMM_Valor='(02) Contrato de trabajo para obra determinada'
where CMM_ControlId=2000297;

UPDATE ControlesMaestrosMultiples 
SET CMM_Valor='(03) Contrato de trabajo por tiempo determinado'
where CMM_ControlId=2000298;

SET IDENTITY_INSERT ControlesMaestrosMultiples ON
INSERT INTO ControlesMaestrosMultiples
           (CMM_ControlId,CMM_Control,CMM_Valor,CMM_Activo,CMM_Sistema,CMM_FechaCreacion)
     VALUES
		   (2000370,'CMM_EMP_TipoContratoId','(04) Contrato de trabajo por temporada',1,1,GETDATE()),
		   (2000371,'CMM_EMP_TipoContratoId','(05) Contrato de trabajo sujeto a prueba',1,1,GETDATE()),
		   (2000372,'CMM_EMP_TipoContratoId','(06) Contrato de trabajo con capacitación inicial',1,1,GETDATE()),
		   (2000373,'CMM_EMP_TipoContratoId','(07) Modalidad de contratación por pago de hora laborada',1,1,GETDATE()),
		   (2000374,'CMM_EMP_TipoContratoId','(08) Modalidad de trabajo por comisión laboral',1,1,GETDATE()),
		   (2000375,'CMM_EMP_TipoContratoId','(09) Modalidades de contratación donde no existe relación de trabajo',1,1,GETDATE()),
		   (2000376,'CMM_EMP_TipoContratoId','(10) Jubilación, pensión, retiro',1,1,GETDATE()),
		   (2000377,'CMM_EMP_TipoContratoId','(99) Otro contrato',1,1,GETDATE())
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF

Select * from ControlesMaestrosMultiples