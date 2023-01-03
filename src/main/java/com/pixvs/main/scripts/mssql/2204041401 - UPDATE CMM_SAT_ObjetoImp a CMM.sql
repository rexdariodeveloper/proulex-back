DELETE FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_SAT_ObjetoImp' AND CMM_ControlId > 2000000
GO
SET IDENTITY_INSERT ControlesMaestrosMultiples ON
GO
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
( 2000970, 'CMM_SAT_ObjetoImp', 1, '01', 'No objeto de impuesto.', 0, GETDATE() ),
( 2000971, 'CMM_SAT_ObjetoImp', 1, '02', 'Sí objeto de impuesto.', 0, GETDATE() ),
( 2000972, 'CMM_SAT_ObjetoImp', 1, '03', 'Sí objeto del impuesto y no obligado al desglose.', 0, GETDATE() )
GO
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO

DECLARE @reseed INT = (SELECT MAX(CMM_ControlId) FROM ControlesMaestrosMultiples WHERE CMM_ControlId <= 1000000)

DBCC checkident ('ControlesMaestrosMultiples', reseed, @reseed)
GO

UPDATE art SET art.ART_CMM_ObjetoImpuestoId = d.CMM_ControlId
FROM Articulos AS art
     INNER JOIN ControlesMaestrosMultiples AS o ON art.ART_CMM_ObjetoImpuestoId = o.CMM_ControlId
     INNER JOIN ControlesMaestrosMultiples AS d ON o.CMM_Referencia = d.CMM_Referencia AND d.CMM_ControlId > 2000000
GO

UPDATE art SET art.CXCFD_CMM_ObjetoImpuestoId = d.CMM_ControlId
FROM CXCFacturasDetalles AS art
     INNER JOIN ControlesMaestrosMultiples AS o ON art.CXCFD_CMM_ObjetoImpuestoId = o.CMM_ControlId
     INNER JOIN ControlesMaestrosMultiples AS d ON o.CMM_Referencia = d.CMM_Referencia AND d.CMM_ControlId > 2000000
GO

DELETE FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_SAT_ObjetoImp' AND CMM_ControlId < 2000000
GO