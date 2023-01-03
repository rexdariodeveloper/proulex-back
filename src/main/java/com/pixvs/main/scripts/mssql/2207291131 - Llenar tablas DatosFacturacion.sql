ALTER TABLE DatosFacturacion ADD AnteriorId INT, AlumnoId INT, ClienteId INT
GO

INSERT INTO DatosFacturacion
SELECT ALUF_CMM_TipoPersonaId,
       ALUF_RFC,
       ALUF_Nombre,
       ALUF_PrimerApellido,
       ALUF_SegundoApellido,
       ALUF_RazonSocial,
       ALUF_RegistroIdentidadFiscal,
       ALUF_RF_RegimenFiscalId,
       ALUF_Calle,
       ALUF_NumeroExterior,
       ALUF_NumeroInterior,
       ALUF_Colonia,
       ALUF_CP,
       ALUF_PAI_PaisId,
       ALUF_EST_EstadoId,
       ALUF_MUN_MunicipioId,
       ALUF_Ciudad,
       ALUF_CorreoElectronico,
       ALUF_TelefonoFijo,
       ALUF_TelefonoMovil,
       ALUF_TelefonoTrabajo,
       ALUF_TelefonoTrabajoExtension,
       ALUF_TelefonoMensajeriaInstantanea,
	   ALUF_AlumnoContactoId,
       ALUF_ALU_AlumnoId,
	   NULL
FROM AlumnosFacturacion
GO

INSERT INTO DatosFacturacion
SELECT CLIF_CMM_TipoPersonaId,
       CLIF_RFC,
       CLIF_Nombre,
       CLIF_PrimerApellido,
       CLIF_SegundoApellido,
       CLIF_RazonSocial,
       CLIF_RegistroIdentidadFiscal,
       CLIF_RF_RegimenFiscalId,
       CLIF_Calle,
       CLIF_NumeroExterior,
       CLIF_NumeroInterior,
       CLIF_Colonia,
       CLIF_CP,
       CLIF_PAI_PaisId,
       CLIF_EST_EstadoId,
       CLIF_MUN_MunicipioId,
       CLIF_Ciudad,
       CLIF_CorreoElectronico,
       CLIF_TelefonoFijo,
       CLIF_TelefonoMovil,
       CLIF_TelefonoTrabajo,
       CLIF_TelefonoTrabajoExtension,
       CLIF_TelefonoMensajeriaInstantanea,
	   CLIF_ClienteFacturacionId,
	   NULL,
       CLIF_CLI_ClienteId
FROM ClientesFacturacion
GO

INSERT INTO AlumnosDatosFacturacion
SELECT ALUF_ALU_AlumnoId,
       DF_DatosFacturacionId,
       ALUF_Predeterminado
FROM DatosFacturacion
     INNER JOIN AlumnosFacturacion ON ALUF_AlumnoContactoId = AnteriorId AND AlumnoId = ALUF_ALU_AlumnoId
GO

INSERT INTO ClientesDatosFacturacion
SELECT CLIF_CLI_ClienteId,
       DF_DatosFacturacionId,
       CLIF_Predeterminado
FROM DatosFacturacion
     INNER JOIN ClientesFacturacion ON CLIF_ClienteFacturacionId = AnteriorId AND ClienteId = CLIF_CLI_ClienteId
GO