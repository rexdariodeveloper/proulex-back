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
( 'CMM_CXC_RegimenFiscal', 1, '608','Demás ingresos', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '609','Consolidación', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '610','Residentes en el Extranjero sin Establecimiento Permanente en México', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '611','Ingresos por Dividendos (socios y accionistas)', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '612','Personas Físicas con Actividades Empresariales y Profesionales', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '614','Ingresos por intereses', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '616','Sin obligaciones fiscales', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '620','Sociedades Cooperativas de Producción que optan por diferir sus ingresos', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '621','Incorporación Fiscal', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '622','Actividades Agrícolas, Ganaderas, Silvícolas y Pesqueras', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '623','Opcional para Grupos de Sociedades', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '624','Coordinados', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '628','Hidrocarburos', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '607','Régimen de Enajenación o Adquisición de Bienes', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '629','De los Regímenes Fiscales Preferentes y de las Empresas Multinacionales', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '630','Enajenación de acciones en bolsa de valores', 0, GETDATE() ),
( 'CMM_CXC_RegimenFiscal', 1, '615','Régimen de los ingresos por obtención de premios', 0, GETDATE() )
GO