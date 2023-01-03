SET IDENTITY_INSERT ControlesMaestrosMultiples ON
INSERT INTO ControlesMaestrosMultiples
           (CMM_ControlId,CMM_Control,CMM_Valor,CMM_Activo,CMM_Sistema,CMM_FechaCreacion)
     VALUES
           (2000350,'CMM_CPXSPRH_EstatusId','Aceptada',1,1,GETDATE()),
		   (2000351,'CMM_CPXSPRH_EstatusId','Pagada',1,1,GETDATE()),
		   (2000352,'CMM_CPXSPRH_EstatusId','Cancelada',1,1,GETDATE()),
		   (2000353,'CMM_CPXSPRH_EstatusId','Borrada',1,1,GETDATE()),
		   (2000354,'CMM_CPXSPRH_EstatusId','Por autorizar',1,1,GETDATE()),
		   (2000355,'CMM_CPXSPRH_EstatusId','Rechazada',1,1,GETDATE()),
		   (2000356,'CMM_CPXSPRH_TipoPagoId','Retiro de caja de ahorro',1,1,GETDATE()),
		   (2000357,'CMM_CPXSPRH_TipoPagoId','Pensión alimenticia',1,1,GETDATE()),
		   (2000358,'CMM_CPXSPRH_TipoPagoId','Incapacidad de personal',1,1,GETDATE()),
		   (2000359,'CMM_CPXSPRH_TipoPagoId','Pago a becario',1,1,GETDATE()),
		   (2000360,'CMM_CPXSPRHID_TipoRetiroId','Parcial',1,1,GETDATE()),
		   (2000361,'CMM_CPXSPRHID_TipoRetiroId','Total',1,1,GETDATE()),
		   (2000362,'CMM_CPXSPRHID_TipoMovimientoId','Caja de ahorro',1,1,GETDATE()),
		   (2000363,'CMM_CPXSPRHID_TipoMovimientoId','Préstamo caja de ahorro',1,1,GETDATE()),
		   (2000364,'CMM_CPXSPRHID_TipoMovimientoId','Préstamo personal',1,1,GETDATE()),
		   (2000365,'CMM_CPXSPRHID_TipoMovimientoId','Préstamo Fonacot',1,1,GETDATE()),
		   (2000366,'CMM_CPXSPRHID_TipoMovimientoId','Otras deducciones',1,1,GETDATE()),
		   (2000368,'CMM_CPXSPRHID_TipoId','Enfermedad General (Proulex)',1,1,GETDATE()),
		   (2000369,'CMM_CPXSPRHID_TipoId','Enfermedad Generar (IMSS)',1,1,GETDATE()),
		   (2000367,'CMM_PRO_TipoProveedor','Solicitudes RH',1,1,GETDATE()),
		   (1000167,'CMM_CALC_TipoMovimiento','Solicitud de pago rh',1,1,GETDATE())
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
