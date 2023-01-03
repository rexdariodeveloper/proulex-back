UPDATE MenuListadosGenerales SET MLG_Activo = 0 WHERE MLG_CMM_ControlCatalogo IN ('CMM_CXC_RegimenFiscal', 'CMM_CXC_UsoCFDI')
GO


UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Adquisición de mercancías.' WHERE UCFDI_Codigo = 'G01'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Devoluciones, descuentos o bonificaciones.' WHERE UCFDI_Codigo = 'G02'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Gastos en general.' WHERE UCFDI_Codigo = 'G03'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Construcciones.' WHERE UCFDI_Codigo = 'I01'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Mobiliario y equipo de oficina por inversiones.' WHERE UCFDI_Codigo = 'I02'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Equipo de transporte.' WHERE UCFDI_Codigo = 'I03'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Equipo de computo y accesorios.' WHERE UCFDI_Codigo = 'I04'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Dados, troqueles, moldes, matrices y herramental.' WHERE UCFDI_Codigo = 'I05'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Comunicaciones telefónicas.' WHERE UCFDI_Codigo = 'I06'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Comunicaciones satelitales.' WHERE UCFDI_Codigo = 'I07'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Otra maquinaria y equipo.' WHERE UCFDI_Codigo = 'I08'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Honorarios médicos, dentales y gastos hospitalarios.' WHERE UCFDI_Codigo = 'D01'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Gastos médicos por incapacidad o discapacidad.' WHERE UCFDI_Codigo = 'D02'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Gastos funerales.' WHERE UCFDI_Codigo = 'D03'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Donativos.' WHERE UCFDI_Codigo = 'D04'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Intereses reales efectivamente pagados por créditos hipotecarios (casa habitación).' WHERE UCFDI_Codigo = 'D05'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Aportaciones voluntarias al SAR.' WHERE UCFDI_Codigo = 'D06'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Primas por seguros de gastos médicos.' WHERE UCFDI_Codigo = 'D07'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Gastos de transportación escolar obligatoria.' WHERE UCFDI_Codigo = 'D08'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Depósitos en cuentas para el ahorro, primas que tengan como base planes de pensiones.' WHERE UCFDI_Codigo = 'D09'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Pagos por servicios educativos (colegiaturas).' WHERE UCFDI_Codigo = 'D10'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Sin efectos fiscales.  ' WHERE UCFDI_Codigo = 'S01'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Pagos' WHERE UCFDI_Codigo = 'CP01'
GO
UPDATE SATUsosCFDI SET UCFDI_Descripcion = 'Nómina' WHERE UCFDI_Codigo = 'CN01'
GO


UPDATE SATRegimenesFiscales SET RF_Descripcion = 'General de Ley Personas Morales' WHERE RF_Codigo = '601'
GO
UPDATE SATRegimenesFiscales SET RF_Descripcion = 'Personas Morales con Fines no Lucrativos' WHERE RF_Codigo = '603'
GO
UPDATE SATRegimenesFiscales SET RF_Descripcion = 'Sueldos y Salarios e Ingresos Asimilados a Salarios' WHERE RF_Codigo = '605'
GO
UPDATE SATRegimenesFiscales SET RF_Descripcion = 'Arrendamiento' WHERE RF_Codigo = '606'
GO
UPDATE SATRegimenesFiscales SET RF_Descripcion = 'Régimen de Enajenación o Adquisición de Bienes' WHERE RF_Codigo = '607'
GO
UPDATE SATRegimenesFiscales SET RF_Descripcion = 'Demás ingresos' WHERE RF_Codigo = '608'
GO
UPDATE SATRegimenesFiscales SET RF_Descripcion = 'Residentes en el Extranjero sin Establecimiento Permanente en México' WHERE RF_Codigo = '610'
GO
UPDATE SATRegimenesFiscales SET RF_Descripcion = 'Ingresos por Dividendos (socios y accionistas)' WHERE RF_Codigo = '611'
GO
UPDATE SATRegimenesFiscales SET RF_Descripcion = 'Personas Físicas con Actividades Empresariales y Profesionales' WHERE RF_Codigo = '612'
GO
UPDATE SATRegimenesFiscales SET RF_Descripcion = 'Ingresos por intereses' WHERE RF_Codigo = '614'
GO
UPDATE SATRegimenesFiscales SET RF_Descripcion = 'Régimen de los ingresos por obtención de premios' WHERE RF_Codigo = '615'
GO
UPDATE SATRegimenesFiscales SET RF_Descripcion = 'Sin obligaciones fiscales' WHERE RF_Codigo = '616'
GO
UPDATE SATRegimenesFiscales SET RF_Descripcion = 'Sociedades Cooperativas de Producción que optan por diferir sus ingresos' WHERE RF_Codigo = '620'
GO
UPDATE SATRegimenesFiscales SET RF_Descripcion = 'Incorporación Fiscal' WHERE RF_Codigo = '621'
GO
UPDATE SATRegimenesFiscales SET RF_Descripcion = 'Actividades Agrícolas, Ganaderas, Silvícolas y Pesqueras' WHERE RF_Codigo = '622'
GO
UPDATE SATRegimenesFiscales SET RF_Descripcion = 'Opcional para Grupos de Sociedades' WHERE RF_Codigo = '623'
GO
UPDATE SATRegimenesFiscales SET RF_Descripcion = 'Coordinados' WHERE RF_Codigo = '624'
GO
UPDATE SATRegimenesFiscales SET RF_Descripcion = 'Régimen de las Actividades Empresariales con ingresos a través de Plataformas Tecnológicas' WHERE RF_Codigo = '625'
GO
UPDATE SATRegimenesFiscales SET RF_Descripcion = 'Régimen Simplificado de Confianza' WHERE RF_Codigo = '626'
GO