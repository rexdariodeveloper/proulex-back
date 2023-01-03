DROP TABLE IF EXISTS ClientesDatosFacturacion
GO

DROP TABLE IF EXISTS AlumnosDatosFacturacion
GO

DROP TABLE IF EXISTS DatosFacturacion
GO

CREATE TABLE DatosFacturacion(
	DF_DatosFacturacionId INT IDENTITY(1,1) NOT NULL,
    DF_CMM_TipoPersonaId INT NOT NULL,
    DF_RFC NVARCHAR(20) NOT NULL,
    DF_Nombre NVARCHAR(50) NULL,
	DF_PrimerApellido NVARCHAR(50) NULL,
	DF_SegundoApellido NVARCHAR(50) NULL,
    DF_RazonSocial NVARCHAR(250) NULL,
    DF_RegistroIdentidadFiscal NVARCHAR(250) NULL,
	DF_RF_RegimenFiscalId INT NULL,
    DF_Calle NVARCHAR(250) NULL,
    DF_NumeroExterior NVARCHAR(10) NULL,
    DF_NumeroInterior NVARCHAR(10) NULL,
    DF_Colonia NVARCHAR(250) NULL,
    DF_CP NVARCHAR(10) NULL,
    DF_PAI_PaisId SMALLINT NULL,
    DF_EST_EstadoId INT NULL,
	DF_MUN_MunicipioId	INT NULL,
    DF_Ciudad NVARCHAR(100) NULL,
    DF_CorreoElectronico NVARCHAR(50) NULL,
    DF_TelefonoFijo NVARCHAR(50) NULL,
    DF_TelefonoMovil NVARCHAR(50) NULL,
    DF_TelefonoTrabajo NVARCHAR(50) NULL,
    DF_TelefonoTrabajoExtension NVARCHAR(10) NULL,
    DF_TelefonoMensajeriaInstantanea NVARCHAR(50) NULL
PRIMARY KEY CLUSTERED 
(
	DF_DatosFacturacionId ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE DatosFacturacion  WITH CHECK ADD  CONSTRAINT FK_DF_CMM_TipoPersonaId FOREIGN KEY(DF_CMM_TipoPersonaId)
REFERENCES ControlesMaestrosMultiples (CMM_ControlId)
GO
ALTER TABLE DatosFacturacion CHECK CONSTRAINT FK_DF_CMM_TipoPersonaId
GO

ALTER TABLE DatosFacturacion  WITH CHECK ADD  CONSTRAINT FK_DF_PAI_PaisId FOREIGN KEY(DF_PAI_PaisId)
REFERENCES Paises (PAI_PaisId)
GO
ALTER TABLE DatosFacturacion CHECK CONSTRAINT FK_DF_PAI_PaisId
GO

ALTER TABLE DatosFacturacion  WITH CHECK ADD  CONSTRAINT FK_DF_EST_EstadoId FOREIGN KEY(DF_EST_EstadoId)
REFERENCES Estados (EST_EstadoId)
GO
ALTER TABLE DatosFacturacion CHECK CONSTRAINT FK_DF_EST_EstadoId
GO

ALTER TABLE DatosFacturacion  WITH CHECK ADD  CONSTRAINT FK_DF_MUN_MunicipioId FOREIGN KEY(DF_MUN_MunicipioId)
REFERENCES Municipios (MUN_MunicipioId)
GO
ALTER TABLE DatosFacturacion CHECK CONSTRAINT FK_DF_MUN_MunicipioId
GO

ALTER TABLE DatosFacturacion  WITH CHECK ADD  CONSTRAINT FK_DF_RF_RegimenFiscalId FOREIGN KEY(DF_RF_RegimenFiscalId)
REFERENCES SATRegimenesFiscales (RF_RegimenFiscalId)
GO
ALTER TABLE DatosFacturacion CHECK CONSTRAINT FK_DF_RF_RegimenFiscalId
GO

--ALTER TABLE DatosFacturacion WITH CHECK ADD CONSTRAINT CHK_DF_Telefono CHECK (
--    DF_TelefonoFijo IS NOT NULL
--    OR DF_TelefonoMovil IS NOT NULL
--    OR DF_TelefonoTrabajo IS NOT NULL
--    OR DF_TelefonoMensajeriaInstantanea IS NOT NULL
--)
--GO



CREATE TABLE ClientesDatosFacturacion(
	CDF_ClienteDatosFacturacionId INT IDENTITY(1,1) NOT NULL,
	CDF_CLI_ClienteId INT NOT NULL,
	CDF_DF_DatosFacturacionId INT NOT NULL,
    CDF_Predeterminado BIT NOT NULL
PRIMARY KEY CLUSTERED 
(
	CDF_ClienteDatosFacturacionId ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE ClientesDatosFacturacion  WITH CHECK ADD  CONSTRAINT FK_CDF_CLI_ClienteId FOREIGN KEY(CDF_CLI_ClienteId)
REFERENCES Clientes (CLI_ClienteId)
GO
ALTER TABLE ClientesDatosFacturacion CHECK CONSTRAINT FK_CDF_CLI_ClienteId
GO

ALTER TABLE ClientesDatosFacturacion  WITH CHECK ADD  CONSTRAINT FK_CDF_DF_DatosFacturacionId FOREIGN KEY(CDF_DF_DatosFacturacionId)
REFERENCES DatosFacturacion (DF_DatosFacturacionId)
GO
ALTER TABLE ClientesDatosFacturacion CHECK CONSTRAINT FK_CDF_DF_DatosFacturacionId
GO



CREATE TABLE AlumnosDatosFacturacion(
	ADF_AlumnoDatosFacturacionId INT IDENTITY(1,1) NOT NULL,
	ADF_ALU_AlumnoId INT NOT NULL,
	ADF_DF_DatosFacturacionId INT NOT NULL,
    ADF_Predeterminado BIT NOT NULL
PRIMARY KEY CLUSTERED 
(
	ADF_AlumnoDatosFacturacionId ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE AlumnosDatosFacturacion  WITH CHECK ADD  CONSTRAINT FK_ADF_ALU_AlumnoId FOREIGN KEY(ADF_ALU_AlumnoId)
REFERENCES Alumnos (ALU_AlumnoId)
GO
ALTER TABLE AlumnosDatosFacturacion CHECK CONSTRAINT FK_ADF_ALU_AlumnoId
GO

ALTER TABLE AlumnosDatosFacturacion  WITH CHECK ADD  CONSTRAINT FK_ADF_DF_DatosFacturacionId FOREIGN KEY(ADF_DF_DatosFacturacionId)
REFERENCES DatosFacturacion (DF_DatosFacturacionId)
GO
ALTER TABLE AlumnosDatosFacturacion CHECK CONSTRAINT FK_ADF_DF_DatosFacturacionId
GO