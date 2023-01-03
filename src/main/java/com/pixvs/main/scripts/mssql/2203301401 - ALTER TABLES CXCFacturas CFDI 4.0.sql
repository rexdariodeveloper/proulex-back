EXEC sp_rename 'dbo.SATUsosCFDI.UFDI_Descripcion', 'UCFDI_Descripcion', 'COLUMN';

ALTER TABLE OrdenesVenta DROP CONSTRAINT IF EXISTS FK_OrdenVenta_Factura
GO
ALTER TABLE OrdenesVenta DROP COLUMN IF EXISTS OV_CXCF_FacturaId
GO
DROP TABLE IF EXISTS CXCFacturasDetallesImpuestos
GO
DROP TABLE IF EXISTS CXCFacturasDetalles
GO
DROP TABLE IF EXISTS CXCFacturas
GO

-- Creamos la tabla para la cabecera
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE CXCFacturas(
	CXCF_FacturaId INT IDENTITY(1,1) NOT NULL,	
	
	CXCF_Version VARCHAR(10) NOT NULL,
	CXCF_Fecha DATETIME NOT NULL,
	CXCF_Folio VARCHAR (100) NOT NULL, 
	CXCF_FP_FormaPagoId SMALLINT NOT NULL,
	CXCF_DiasCredito INT NOT NULL, 
	CXCF_CondicionesPago VARCHAR(4000) NULL,
	CXCF_MON_MonedaId SMALLINT NOT NULL,
	CXCF_TipoCambio DECIMAL(28, 4) NOT NULL,
	CXCF_CMM_MetodoPagoId INT NOT NULL,
	
	CXCF_EmisorCP VARCHAR(4000) NOT NULL,
	CXCF_EmisorRFC VARCHAR(4000) NOT NULL,
	CXCF_EmisorRazonSocial VARCHAR(4000) NOT NULL,
	CXCF_EmisorRegimenFiscalId INT NOT NULL,
	
	CXCF_ReceptorCP VARCHAR(4000) NULL,
	CXCF_ReceptorRFC VARCHAR(4000) NOT NULL,
	CXCF_ReceptorNombre VARCHAR(4000) NULL,
	CXCF_ReceptorRegimenFiscalId INT NOT NULL,
	CXCF_ReceptorUsoCFDIId INT NOT NULL,

	CXCF_UUID UNIQUEIDENTIFIER NULL,
	CXCF_ARC_FacturaXMLId INT NULL,
	CXCF_ARC_FacturaPDFId INT NULL,
	
	CXCF_CMM_TipoRegistroId INT NOT NULL, 
	CXCF_CLIF_ClienteFacturacionId INT NULL,
	CXCF_ALUF_AlumnoContactoId INT NULL,
	CXCF_SUC_SucursalId INT NULL,

	CXCF_PER_PeriodicidadId INT NULL,
	CXCF_MES_MesId INT NULL,
	CXCF_Anio SMALLINT NULL,

	CXCF_FechaCancelacion DATETIME NULL,
	CXCF_MotivoCancelacion VARCHAR (1000) NULL,

	CXCF_CMM_EstatusId INT NOT NULL,
	CXCF_FechaCreacion DATETIME NOT NULL,
	CXCF_USU_CreadoPorId INT NOT NULL,
	CXCF_FechaModificacion DATETIME NULL,
	CXCF_USU_ModificadoPorId INT NULL
 CONSTRAINT PK_CXCFacturas PRIMARY KEY CLUSTERED 
(
	CXCF_FacturaId ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE CXCFacturas ADD  CONSTRAINT DF_CXCFacturas_FechaCreacion  DEFAULT (GETDATE()) FOR CXCF_FechaCreacion
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_FormasPago FOREIGN KEY(CXCF_FP_FormaPagoId)
REFERENCES FormasPago (FP_FormaPagoId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_FormasPago
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_Monedas FOREIGN KEY(CXCF_MON_MonedaId)
REFERENCES Monedas (MON_MonedaId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_Monedas
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_MetodoPago FOREIGN KEY(CXCF_CMM_MetodoPagoId)
REFERENCES ControlesMaestrosMultiples (CMM_ControlId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_MetodoPago
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_EmisorRegimenFiscal FOREIGN KEY(CXCF_EmisorRegimenFiscalId)
REFERENCES SATRegimenesFiscales (RF_RegimenFiscalId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_EmisorRegimenFiscal
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_ReceptorRegimenFiscal FOREIGN KEY(CXCF_ReceptorRegimenFiscalId)
REFERENCES SATRegimenesFiscales (RF_RegimenFiscalId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_ReceptorRegimenFiscal
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_UsoCFDI FOREIGN KEY(CXCF_ReceptorUsoCFDIId)
REFERENCES SATUsosCFDI (UCFDI_UsoCFDIId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_UsoCFDI
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_FacturaXMLId FOREIGN KEY(CXCF_ARC_FacturaXMLId)
REFERENCES Archivos (ARC_ArchivoId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_FacturaXMLId
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_FacturaPDFId FOREIGN KEY(CXCF_ARC_FacturaPDFId)
REFERENCES Archivos (ARC_ArchivoId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_FacturaPDFId
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_TipoRegistro FOREIGN KEY(CXCF_CMM_TipoRegistroId)
REFERENCES ControlesMaestrosMultiples (CMM_ControlId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_TipoRegistro
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_ClientesFacturacion FOREIGN KEY(CXCF_CLIF_ClienteFacturacionId)
REFERENCES ClientesFacturacion (CLIF_ClienteFacturacionId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_ClientesFacturacion
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_AlumnosFacturacion FOREIGN KEY(CXCF_ALUF_AlumnoContactoId)
REFERENCES AlumnosFacturacion (ALUF_AlumnoContactoId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_AlumnosFacturacion
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_Sucursales FOREIGN KEY(CXCF_SUC_SucursalId)
REFERENCES Sucursales (SUC_SucursalId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_Sucursales
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_SATPeriodicidad FOREIGN KEY(CXCF_PER_PeriodicidadId)
REFERENCES SATPeriodicidad (PER_PeriodicidadId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_SATPeriodicidad
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_SATMeses FOREIGN KEY(CXCF_MES_MesId)
REFERENCES SATMeses (MES_MesId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_SATMeses
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_Estatus FOREIGN KEY(CXCF_CMM_EstatusId)
REFERENCES ControlesMaestrosMultiples (CMM_ControlId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_Estatus
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_CreadoPor FOREIGN KEY(CXCF_USU_CreadoPorId)
REFERENCES Usuarios (USU_UsuarioId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_CreadoPor
GO

ALTER TABLE CXCFacturas  WITH CHECK ADD  CONSTRAINT FK_CXCFacturas_ModificadoPor FOREIGN KEY(CXCF_USU_ModificadoPorId)
REFERENCES Usuarios (USU_UsuarioId)
GO
ALTER TABLE CXCFacturas CHECK CONSTRAINT FK_CXCFacturas_ModificadoPor
GO

-- Creamos la tabla para los detalles
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE CXCFacturasDetalles(
	CXCFD_FacturaDetalleId INT IDENTITY(1,1) NOT NULL,	
	CXCFD_CXCF_FacturaId INT NOT NULL, 
	CXCFD_ClaveProdServ VARCHAR (100) NOT NULL,
	CXCFD_NoIdentificacion VARCHAR (100) NOT NULL,
	CXCFD_Descripcion VARCHAR (4000) NOT NULL,
	CXCFD_UM_UnidadMedidaId SMALLINT NOT NULL,
	CXCFD_Cantidad DECIMAL (28, 6) NOT NULL,
	CXCFD_ValorUnitario DECIMAL (28, 6) NOT NULL, 
	CXCFD_Importe DECIMAL (28, 6) NOT NULL, 
	CXCFD_Descuento DECIMAL (28, 6) NOT NULL,
	CXCFD_CMM_ObjetoImpuestoId INT NOT NULL,
	CXCFD_ReferenciaId INT NULL
 CONSTRAINT PK_CXCFacturasDetalles PRIMARY KEY CLUSTERED 
(
	CXCFD_FacturaDetalleId ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE CXCFacturasDetalles  WITH CHECK ADD  CONSTRAINT FK_CXCFacturasDetalles_FacturaId FOREIGN KEY(CXCFD_CXCF_FacturaId)
REFERENCES CXCFacturas (CXCF_FacturaId)
GO
ALTER TABLE CXCFacturasDetalles CHECK CONSTRAINT FK_CXCFacturasDetalles_FacturaId
GO

ALTER TABLE CXCFacturasDetalles  WITH CHECK ADD  CONSTRAINT FK_CXCFacturasDetalles_UnidadMedida FOREIGN KEY(CXCFD_UM_UnidadMedidaId)
REFERENCES UnidadesMedidas (UM_UnidadMedidaId)
GO
ALTER TABLE CXCFacturasDetalles CHECK CONSTRAINT FK_CXCFacturasDetalles_UnidadMedida
GO

ALTER TABLE CXCFacturasDetalles  WITH CHECK ADD  CONSTRAINT FK_CXCFacturasDetalles_ObjetosImpuesto FOREIGN KEY(CXCFD_CMM_ObjetoImpuestoId)
REFERENCES ControlesMaestrosMultiples (CMM_ControlId)
GO
ALTER TABLE CXCFacturasDetalles CHECK CONSTRAINT FK_CXCFacturasDetalles_ObjetosImpuesto
GO

-- Creamos la tabla para los detalles impuestos
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE CXCFacturasDetallesImpuestos(
	CXCFDI_FacturaDetalleImpuestoId INT IDENTITY(1,1) NOT NULL,	
	CXCFDI_CXCFD_FacturaDetalleId INT NOT NULL,
	CXCFDI_Clave VARCHAR(5) NOT NULL,
	CXCFDI_Nombre VARCHAR(100) NOT NULL,
	CXCFDI_TipoFactor VARCHAR(100) NOT NULL,
	CXCFDI_Base DECIMAL (28, 6) NOT NULL,
	CXCFDI_TasaOCuota DECIMAL (28, 6) NOT NULL,
	CXCFDI_Importe DECIMAL (28, 6) NOT NULL
 CONSTRAINT PK_CXCFacturasDetallesImpuestos PRIMARY KEY CLUSTERED 
(
	CXCFDI_FacturaDetalleImpuestoId ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE CXCFacturasDetallesImpuestos  WITH CHECK ADD  CONSTRAINT FK_CXCFacturasDetalles_Impuestos FOREIGN KEY(CXCFDI_CXCFD_FacturaDetalleId)
REFERENCES CXCFacturasDetalles (CXCFD_FacturaDetalleId)
GO
ALTER TABLE CXCFacturasDetallesImpuestos CHECK CONSTRAINT FK_CXCFacturasDetalles_Impuestos
GO

-- Agregamos la referencia a la Factura en la tabla de OV
ALTER TABLE OrdenesVenta ADD OV_CXCF_FacturaId INT NULL
GO

ALTER TABLE OrdenesVenta  WITH CHECK ADD  CONSTRAINT FK_OrdenVenta_Factura FOREIGN KEY(OV_CXCF_FacturaId)
REFERENCES CXCFacturas (CXCF_FacturaId)
GO
ALTER TABLE OrdenesVenta CHECK CONSTRAINT FK_OrdenVenta_Factura
GO