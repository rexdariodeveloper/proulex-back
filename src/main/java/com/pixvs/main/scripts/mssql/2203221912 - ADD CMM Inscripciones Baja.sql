SET IDENTITY_INSERT ControlesMaestrosMultiples ON
INSERT INTO ControlesMaestrosMultiples
(
    CMM_ControlId,
	CMM_Control,
    CMM_Activo,
    CMM_Referencia,
	CMM_Valor,
    CMM_Sistema,
    CMM_FechaCreacion
)
VALUES
( 2000513, 'CMM_INS_Estatus', 1, NULL,'Baja', 1, GETDATE() )
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO