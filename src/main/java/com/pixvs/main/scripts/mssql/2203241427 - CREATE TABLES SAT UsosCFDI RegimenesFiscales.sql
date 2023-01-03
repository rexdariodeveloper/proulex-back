UPDATE ControlesMaestrosMultiples
  SET
      CMM_Activo = 0,
      CMM_FechaModificacion = GETDATE()
WHERE CMM_Control IN('CMM_CXC_UsoCFDI', 'CMM_CXC_RegimenFiscal')
GO

DROP TABLE IF EXISTS SATUsosCFDIRegimenesFiscales
GO
DROP TABLE IF EXISTS SATUsosCFDI
GO
DROP TABLE IF EXISTS SATRegimenesFiscales
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE SATUsosCFDI(
	UCFDI_UsoCFDIId INT IDENTITY(1,1) NOT NULL,	
	UCFDI_Codigo NVARCHAR(5) NOT NULL,
	UFDI_Descripcion NVARCHAR(250) NOT NULL,
	UCFDI_Fisica BIT NOT NULL,
	UCFDI_Moral BIT NOT NULL,
	UCFDI_Activo BIT NOT NULL
 CONSTRAINT PK_SATUsosCFDI PRIMARY KEY CLUSTERED 
(
	UCFDI_UsoCFDIId ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE SATRegimenesFiscales(
	RF_RegimenFiscalId INT IDENTITY(1,1) NOT NULL,	
	RF_Codigo NVARCHAR(5) NOT NULL,
	RF_Descripcion NVARCHAR(250) NOT NULL,
	RF_Fisica BIT NOT NULL,
	RF_Moral BIT NOT NULL,
	RF_Activo BIT NOT NULL
 CONSTRAINT PK_SATRegimenesFiscales PRIMARY KEY CLUSTERED 
(
	RF_RegimenFiscalId ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE SATUsosCFDIRegimenesFiscales(
	UCFDIRF_UsoCFDIRegimenFiscalId INT IDENTITY(1,1) NOT NULL,	
	UCFDIRF_UCFDI_UsoCFDIId INT NOT NULL,
	UCFDIRF_RF_RegimenFiscalId INT NOT NULL,
	UCFDIRF_Activo BIT NOT NULL
 CONSTRAINT PK_SATUsosCFDIRegimenesFiscales PRIMARY KEY CLUSTERED 
(
	UCFDIRF_UsoCFDIRegimenFiscalId ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE SATUsosCFDIRegimenesFiscales  WITH CHECK ADD  CONSTRAINT FK_SATUsosCFDIRegimenesFiscales_UsoCFDI FOREIGN KEY(UCFDIRF_UCFDI_UsoCFDIId)
REFERENCES SATUsosCFDI (UCFDI_UsoCFDIId)
GO
ALTER TABLE SATUsosCFDIRegimenesFiscales CHECK CONSTRAINT FK_SATUsosCFDIRegimenesFiscales_UsoCFDI
GO

ALTER TABLE SATUsosCFDIRegimenesFiscales  WITH CHECK ADD  CONSTRAINT FK_SATUsosCFDIRegimenesFiscales_RegimenFiscal FOREIGN KEY(UCFDIRF_RF_RegimenFiscalId)
REFERENCES SATRegimenesFiscales (RF_RegimenFiscalId)
GO
ALTER TABLE SATUsosCFDIRegimenesFiscales CHECK CONSTRAINT FK_SATUsosCFDIRegimenesFiscales_RegimenFiscal
GO

INSERT INTO SATUsosCFDI
(
    --UCFDI_UsoCFDIId - this column value is auto-generated
    UCFDI_Codigo,
    UFDI_Descripcion,
    UCFDI_Fisica,
    UCFDI_Moral,
    UCFDI_Activo
)
VALUES
('G01', 'Adquisición de mercancías.', 1, 1, 1),
('G02', 'Devoluciones, descuentos o bonificaciones.', 1, 1, 1),
('G03', 'Gastos en general.', 1, 1, 1),
('I01', 'Construcciones.', 1, 1, 1),
('I02', 'Mobiliario y equipo de oficina por inversiones.', 1, 1, 1),
('I03', 'Equipo de transporte.', 1, 1, 1),
('I04', 'Equipo de computo y accesorios.', 1, 1, 1),
('I05', 'Dados, troqueles, moldes, matrices y herramental.', 1, 1, 1),
('I06', 'Comunicaciones telefónicas.', 1, 1, 1),
('I07', 'Comunicaciones satelitales.', 1, 1, 1),
('I08', 'Otra maquinaria y equipo.', 1, 1, 1),
('D01', 'Honorarios médicos, dentales y gastos hospitalarios.', 1, 0, 1),
('D02', 'Gastos médicos por incapacidad o discapacidad.', 1, 0, 1),
('D03', 'Gastos funerales.', 1, 0, 1),
('D04', 'Donativos.', 1, 0, 1),
('D05', 'Intereses reales efectivamente pagados por créditos hipotecarios (casa habitación).', 1, 0, 1),
('D06', 'Aportaciones voluntarias al SAR.', 1, 0, 1),
('D07', 'Primas por seguros de gastos médicos.', 1, 0, 1),
('D08', 'Gastos de transportación escolar obligatoria.', 1, 0, 1),
('D09', 'Depósitos en cuentas para el ahorro, primas que tengan como base planes de pensiones.', 1, 0, 1),
('D10', 'Pagos por servicios educativos (colegiaturas).', 1, 0, 1),
('S01', 'Sin efectos fiscales.  ', 1, 1, 1),
('CP01', 'Pagos', 1, 1, 1),
('CN01', 'Nómina', 1, 0, 1)
GO

INSERT INTO SATRegimenesFiscales
(
    --RF_RegimenFiscalId - this column value is auto-generated
    RF_Codigo,
    RF_Descripcion,
    RF_Fisica,
    RF_Moral,
    RF_Activo
)
VALUES
('601', 'General de Ley Personas Morales', 0, 1, 1),
('603', 'Personas Morales con Fines no Lucrativos', 0, 1, 1),
('605', 'Sueldos y Salarios e Ingresos Asimilados a Salarios', 1, 0, 1),
('606', 'Arrendamiento', 1, 0, 1),
('607', 'Régimen de Enajenación o Adquisición de Bienes', 1, 0, 1),
('608', 'Demás ingresos', 1, 0, 1),
('610', 'Residentes en el Extranjero sin Establecimiento Permanente en México', 1, 1, 1),
('611', 'Ingresos por Dividendos (socios y accionistas)', 1, 0, 1),
('612', 'Personas Físicas con Actividades Empresariales y Profesionales', 1, 0, 1),
('614', 'Ingresos por intereses', 1, 0, 1),
('615', 'Régimen de los ingresos por obtención de premios', 1, 0, 1),
('616', 'Sin obligaciones fiscales', 1, 0, 1),
('620', 'Sociedades Cooperativas de Producción que optan por diferir sus ingresos', 0, 1, 1),
('621', 'Incorporación Fiscal', 1, 0, 1),
('622', 'Actividades Agrícolas, Ganaderas, Silvícolas y Pesqueras', 0, 1, 1),
('623', 'Opcional para Grupos de Sociedades', 0, 1, 1),
('624', 'Coordinados', 0, 1, 1),
('625', 'Régimen de las Actividades Empresariales con ingresos a través de Plataformas Tecnológicas', 1, 0, 1),
('626', 'Régimen Simplificado de Confianza', 1, 1, 1)
GO

INSERT INTO SATUsosCFDIRegimenesFiscales
(
    --UCFDIRF_UsoCFDIRegimenFiscalId - this column value is auto-generated
    UCFDIRF_UCFDI_UsoCFDIId,
    UCFDIRF_RF_RegimenFiscalId,
    UCFDIRF_Activo
)
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('601', '603', '606', '612', '620', '621', '622', '623', '624', '625', '626') WHERE UCFDI_Codigo = 'G01' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('601', '603', '606', '612', '620', '621', '622', '623', '624', '625', '626') WHERE UCFDI_Codigo = 'G02' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('601', '603', '606', '612', '620', '621', '622', '623', '624', '625', '626') WHERE UCFDI_Codigo = 'G03' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('601', '603', '606', '612', '620', '621', '622', '623', '624', '625', '626') WHERE UCFDI_Codigo = 'I01' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('601', '603', '606', '612', '620', '621', '622', '623', '624', '625', '626') WHERE UCFDI_Codigo = 'I02' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('601', '603', '606', '612', '620', '621', '622', '623', '624', '625', '626') WHERE UCFDI_Codigo = 'I03' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('601', '603', '606', '612', '620', '621', '622', '623', '624', '625', '626') WHERE UCFDI_Codigo = 'I04' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('601', '603', '606', '612', '620', '621', '622', '623', '624', '625', '626') WHERE UCFDI_Codigo = 'I05' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('601', '603', '606', '612', '620', '621', '622', '623', '624', '625', '626') WHERE UCFDI_Codigo = 'I06' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('601', '603', '606', '612', '620', '621', '622', '623', '624', '625', '626') WHERE UCFDI_Codigo = 'I07' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('601', '603', '606', '612', '620', '621', '622', '623', '624', '625', '626') WHERE UCFDI_Codigo = 'I08' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('605', '606', '608', '611', '612', '614', '607', '615', '625') WHERE UCFDI_Codigo = 'D01' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('605', '606', '608', '611', '612', '614', '607', '615', '625') WHERE UCFDI_Codigo = 'D02' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('605', '606', '608', '611', '612', '614', '607', '615', '625') WHERE UCFDI_Codigo = 'D03' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('605', '606', '608', '611', '612', '614', '607', '615', '625') WHERE UCFDI_Codigo = 'D04' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('605', '606', '608', '611', '612', '614', '607', '615', '625') WHERE UCFDI_Codigo = 'D05' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('605', '606', '608', '611', '612', '614', '607', '615', '625') WHERE UCFDI_Codigo = 'D06' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('605', '606', '608', '611', '612', '614', '607', '615', '625') WHERE UCFDI_Codigo = 'D07' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('605', '606', '608', '611', '612', '614', '607', '615', '625') WHERE UCFDI_Codigo = 'D08' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('605', '606', '608', '611', '612', '614', '607', '615', '625') WHERE UCFDI_Codigo = 'D09' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('605', '606', '608', '611', '612', '614', '607', '615', '625') WHERE UCFDI_Codigo = 'D10' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('601', '603', '605', '606', '608', '610', '611', '612', '614', '616', '620', '621', '622', '623', '624', '607', '615', '625', '626') WHERE UCFDI_Codigo = 'S01' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('601', '603', '605', '606', '608', '610', '611', '612', '614', '616', '620', '621', '622', '623', '624', '607', '615', '625', '626') WHERE UCFDI_Codigo = 'CP01' UNION ALL
SELECT UCFDI_UsoCFDIId, RF_RegimenFiscalId, 1 FROM SATUsosCFDI INNER JOIN SATRegimenesFiscales ON RF_Codigo IN ('605') WHERE UCFDI_Codigo = 'CN01'
GO