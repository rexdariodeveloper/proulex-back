DECLARE @reseed INT = (SELECT MAX(CMM_ControlId) FROM ControlesMaestrosMultiples WHERE CMM_ControlId <= 1000000)

DBCC checkident ('ControlesMaestrosMultiples', reseed, @reseed)
GO

INSERT INTO ControlesMaestrosMultiples
(
    CMM_Control,
    CMM_Activo,
    CMM_Referencia,
	CMM_Valor,
    CMM_Sistema,
    CMM_FechaCreacion
)
VALUES
( 'CMM_CXC_RegimenFiscal', 1, '601','General de Ley Personas Morales', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '603','Personas Morales con Fines no Lucrativos', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '605','Sueldos y Salarios e Ingresos Asimilados a Salarios', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '606','Arrendamiento', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '608','Dem�s ingresos', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '609','Consolidaci�n', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '610','Residentes en el Extranjero sin Establecimiento Permanente en M�xico', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '611','Ingresos por Dividendos (socios y accionistas)', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '612','Personas F�sicas con Actividades Empresariales y Profesionales', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '614','Ingresos por intereses', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '616','Sin obligaciones fiscales', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '620','Sociedades Cooperativas de Producci�n que optan por diferir sus ingresos', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '621','Incorporaci�n Fiscal', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '622','Actividades Agr�colas, Ganaderas, Silv�colas y Pesqueras', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '623','Opcional para Grupos de Sociedades', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '624','Coordinados', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '628','Hidrocarburos', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '607','R�gimen de Enajenaci�n o Adquisici�n de Bienes', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '629','De los Reg�menes Fiscales Preferentes y de las Empresas Multinacionales', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '630','Enajenaci�n de acciones en bolsa de valores', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '615','R�gimen de los ingresos por obtenci�n de premios', 0, GETDATE() )
GO