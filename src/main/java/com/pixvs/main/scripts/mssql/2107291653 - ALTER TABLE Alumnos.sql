/**
* Created by Angel Daniel Hernández Silva on 28/07/2021.
* Object:  ALTER TABLE [dbo].[Alumnos]
*/

/******************************/
/***** ALU_SUC_SucursalId *****/
/******************************/

ALTER TABLE [dbo].[Alumnos] ADD [ALU_SUC_SucursalId] int NULL
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_SUC_SucursalId] FOREIGN KEY([ALU_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO

ALTER TABLE [dbo].[Alumnos] CHECK CONSTRAINT [FK_ALU_SUC_SucursalId]
GO

UPDATE [dbo].[Alumnos] SET ALU_SUC_SucursalId = (SELECT TOP 1 SUC_SucursalId FROM Sucursales WHERE SUC_Predeterminada = 1)
GO

ALTER TABLE [dbo].[Alumnos] ALTER COLUMN [ALU_SUC_SucursalId] int NOT NULL
GO

/*************************/
/***** ALU_CodigoUDG *****/
/*************************/

ALTER TABLE [dbo].[Alumnos] ADD [ALU_CodigoUDG] varchar(150) NULL
GO

/***********************/
/***** Campos JOBS *****/
/***********************/

ALTER TABLE [dbo].[Alumnos] ADD [ALU_AlumnoJOBS] bit NOT NULL DEFAULT(0)
GO

ALTER TABLE [dbo].[Alumnos] ADD [ALU_CMM_ProgramaJOBSId] int NULL
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_CMM_ProgramaJOBSId] FOREIGN KEY([ALU_CMM_ProgramaJOBSId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Alumnos] ADD [ALU_CMM_CentroUniversitarioJOBSId] int NULL
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_CMM_CentroUniversitarioJOBSId] FOREIGN KEY([ALU_CMM_CentroUniversitarioJOBSId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [CHK_ALU_CMM_CentroUniversitarioJOBSId] CHECK ([ALU_CMM_ProgramaJOBSId] IS NULL OR [ALU_CMM_ProgramaJOBSId] <> 2000530 OR ([ALU_CMM_ProgramaJOBSId] = 2000530 AND [ALU_CMM_CentroUniversitarioJOBSId] IS NOT NULL))
GO

ALTER TABLE [dbo].[Alumnos] ADD [ALU_CMM_PreparatoriaJOBSId] int NULL
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_CMM_PreparatoriaJOBSId] FOREIGN KEY([ALU_CMM_PreparatoriaJOBSId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [CHK_ALU_CMM_PreparatoriaJOBSId] CHECK ([ALU_CMM_ProgramaJOBSId] IS NULL OR [ALU_CMM_ProgramaJOBSId] <> 2000531 OR ([ALU_CMM_ProgramaJOBSId] = 2000531 AND [ALU_CMM_PreparatoriaJOBSId] IS NOT NULL))
GO

ALTER TABLE [dbo].[Alumnos] ADD [ALU_CMM_CarreraJOBSId] int NULL
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_CMM_CarreraJOBSId] FOREIGN KEY([ALU_CMM_CarreraJOBSId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [CHK_ALU_CMM_CarreraJOBSId] CHECK ([ALU_CMM_ProgramaJOBSId] IS NULL OR [ALU_CMM_ProgramaJOBSId] <> 2000530 OR ([ALU_CMM_ProgramaJOBSId] = 2000530 AND [ALU_CMM_CarreraJOBSId] IS NOT NULL))
GO

ALTER TABLE [dbo].[Alumnos] ADD [ALU_BachilleratoTecnologico] varchar(255) NULL
GO

/******************************/
/***** CMM - ProgramaJOBS *****/
/******************************/

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON
GO

INSERT [dbo].[ControlesMaestrosMultiples] (
	[CMM_ControlId],
	[CMM_Activo],
	[CMM_Control],
	[CMM_USU_CreadoPorId],
	[CMM_FechaCreacion],
	[CMM_Referencia],
	[CMM_Sistema],
	[CMM_Valor]
) VALUES (
	/* [CMM_ControlId] */ 2000530,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_ALU_ProgramaJOBS',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'JOBS'
),(
	/* [CMM_ControlId] */ 2000531,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_ALU_ProgramaJOBS',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'JOBS SEMS'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

/*********************************************************/
/***** MenuListadosGenerales - CentrosUniversitarios *****/
/*********************************************************/

INSERT [dbo].[MenuListadosGenerales] (
	-- [MLG_ListadoGeneralNodoId],
	[MLG_NodoPadreId],
	[MLG_Titulo],
	[MLG_TituloEN],
	[MLG_Activo],
	[MLG_Icono],
	[MLG_Orden],
	[MLG_CMM_TipoNodoId],
	[MLG_NombreTablaCatalogo],
	[MLG_CMM_ControlCatalogo],
	[MLG_PermiteBorrar],
	[MLG_UrlAPI],
	[MLG_FechaCreacion]
) VALUES (
	-- /* [MLG_ListadoGeneralNodoId] */ 8,
	/* [MLG_NodoPadreId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL),
	/* [MLG_Titulo] */ 'Centros universitarios',
	/* [MLG_TituloEN] */ 'University center',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'school',
	/* [MLG_Orden] */ 1,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_ALU_CentrosUniversitarios',
	/* [MLG_PermiteBorrar] */ 0,
	/* [MLG_UrlAPI] */ '/api/v1/cmm',
	/* [MLG_FechaCreacion] */ GETDATE()
)
GO

INSERT [dbo].[MenuListadosGeneralesDetalles] (
	[MLGD_MLG_ListadoGeneralNodoId],
	[MLGD_JsonConfig],
	[MLGD_JsonListado],
	[MLGD_CampoTabla],
	[MLGD_CampoModelo]
) VALUES (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Centros universitarios' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Código",' +
    '"inputType": "text",' +
    '"name": "referencia",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Código requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(10)","message": "El código no debe sobrepasar los 10 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "referencia",' +
    '"title": "Código",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Referencia',
	/* [MLGD_CampoModelo] */ 'referencia'
), (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Centros universitarios' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Nombre",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Nombre requerida"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "La nombre no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Nombre",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
)
GO

/*************************************************/
/***** MenuListadosGenerales - Preparatorias *****/
/*************************************************/

INSERT [dbo].[MenuListadosGenerales] (
	-- [MLG_ListadoGeneralNodoId],
	[MLG_NodoPadreId],
	[MLG_Titulo],
	[MLG_TituloEN],
	[MLG_Activo],
	[MLG_Icono],
	[MLG_Orden],
	[MLG_CMM_TipoNodoId],
	[MLG_NombreTablaCatalogo],
	[MLG_CMM_ControlCatalogo],
	[MLG_PermiteBorrar],
	[MLG_UrlAPI],
	[MLG_FechaCreacion]
) VALUES (
	-- /* [MLG_ListadoGeneralNodoId] */ 8,
	/* [MLG_NodoPadreId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL),
	/* [MLG_Titulo] */ 'Preparatorias',
	/* [MLG_TituloEN] */ 'High school',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'school',
	/* [MLG_Orden] */ 1,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_ALU_Preparatorias',
	/* [MLG_PermiteBorrar] */ 0,
	/* [MLG_UrlAPI] */ '/api/v1/cmm',
	/* [MLG_FechaCreacion] */ GETDATE()
)
GO

INSERT [dbo].[MenuListadosGeneralesDetalles] (
	[MLGD_MLG_ListadoGeneralNodoId],
	[MLGD_JsonConfig],
	[MLGD_JsonListado],
	[MLGD_CampoTabla],
	[MLGD_CampoModelo]
) VALUES (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Preparatorias' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Código",' +
    '"inputType": "text",' +
    '"name": "referencia",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Código requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(10)","message": "El código no debe sobrepasar los 10 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "referencia",' +
    '"title": "Código",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Referencia',
	/* [MLGD_CampoModelo] */ 'referencia'
), (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Preparatorias' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Nombre",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Nombre requerida"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "La nombre no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Nombre",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
)
GO

/********************************************/
/***** MenuListadosGenerales - Carreras *****/
/********************************************/

INSERT [dbo].[MenuListadosGenerales] (
	-- [MLG_ListadoGeneralNodoId],
	[MLG_NodoPadreId],
	[MLG_Titulo],
	[MLG_TituloEN],
	[MLG_Activo],
	[MLG_Icono],
	[MLG_Orden],
	[MLG_CMM_TipoNodoId],
	[MLG_NombreTablaCatalogo],
	[MLG_CMM_ControlCatalogo],
	[MLG_PermiteBorrar],
	[MLG_UrlAPI],
	[MLG_FechaCreacion]
) VALUES (
	-- /* [MLG_ListadoGeneralNodoId] */ 8,
	/* [MLG_NodoPadreId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL),
	/* [MLG_Titulo] */ 'Carreras',
	/* [MLG_TituloEN] */ 'Career',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'school',
	/* [MLG_Orden] */ 1,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_ALU_Carreras',
	/* [MLG_PermiteBorrar] */ 0,
	/* [MLG_UrlAPI] */ '/api/v1/cmm',
	/* [MLG_FechaCreacion] */ GETDATE()
)
GO

INSERT [dbo].[MenuListadosGeneralesDetalles] (
	[MLGD_MLG_ListadoGeneralNodoId],
	[MLGD_JsonConfig],
	[MLGD_JsonListado],
	[MLGD_CampoTabla],
	[MLGD_CampoModelo]
) VALUES (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Carreras' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "pixvsMatSelect",' +
    '"label": "Centro universitario",' +
    '"name": "cmmReferencia",' +
    '"formControl": "new FormControl(null,[Validators.required])",' +
    '"validations": [],' +
    '"multiple": false,' +
    '"selectAll": false,' +
    '"list": [],' +
    '"campoValor": "valor",' +
    '"fxFlex": "100"' +
  '}',
	/* [MLGD_JsonListado] */ '{' +
    '"name": "cmmReferencia.valor",' +
    '"title": "Centro universitario",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_CMM_ReferenciaId',
	/* [MLGD_CampoModelo] */ 'cmmReferencia'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Carreras' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Código",' +
    '"inputType": "text",' +
    '"name": "referencia",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Código requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(10)","message": "El código no debe sobrepasar los 10 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "referencia",' +
    '"title": "Código",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Referencia',
	/* [MLGD_CampoModelo] */ 'referencia'
), (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Carreras' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Nombre",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Nombre requerida"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "La nombre no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Nombre",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
)
GO

/***************************************/
/***** FUNCTION - getCMMReferencia *****/
/***************************************/

CREATE OR ALTER FUNCTION [dbo].[getCMMReferencia] (@control varchar(255))

RETURNS varchar(255)

AS BEGIN
	
	DECLARE @controlReferencia varchar(255) = NULL;

	SELECT @controlReferencia = CASE
		WHEN @control = 'CMM_ALU_Carreras' THEN 'CMM_ALU_CentrosUniversitarios'
		ELSE NULL
	END

	return @controlReferencia
END	
GO

/************************************/
/***** Vista VW_Listado_Alumnos *****/
/************************************/

CREATE OR ALTER VIEW [dbo].[VW_Listado_Alumnos ] AS

    SELECT
        ALU_AlumnoId AS id,
		ALU_Codigo AS codigo,
        ALU_Nombre AS nombre,
        ALU_PrimerApellido + COALESCE(' ' + ALU_SegundoApellido,'') AS apellidos,
        CAST(DATEDIFF(YEAR,ALU_FechaNacimiento,GETDATE()) AS nvarchar(MAX)) + ' años, ' + CAST(DATEDIFF(MONTH,ALU_FechaNacimiento,DATEADD(YEAR,DATEDIFF(YEAR,GETDATE(),ALU_FechaNacimiento),GETDATE())) AS nvarchar(MAX)) + ' meses' AS edad,
		ALU_CorreoElectronico AS correoElectronico,
		COALESCE(ALU_TelefonoMovil,ALU_TelefonoFijo,ALU_TelefonoTrabajo + COALESCE(' (' + ALU_TelefonoTrabajoExtension + ')',''), ALU_TelefonoMensajeriaInstantanea) AS telefono,
		SUC_Nombre AS sucursalNombre,
		ALU_Activo AS activo
    FROM Alumnos
	INNER JOIN Sucursales ON SUC_SucursalId = ALU_SUC_SucursalId

GO