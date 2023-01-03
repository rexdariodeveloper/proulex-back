/**
* Created by Angel Daniel Hernández Silva on 31/08/2021.
* Object: ALTER TABLE [dbo].[Alumnos] - ISSUE 692
*/

/***************************/
/***** Quitar columnas *****/
/***************************/

ALTER TABLE [dbo].[Alumnos] DROP CONSTRAINT [FK_ALU_CMM_EscolaridadMaximaId]
GO

ALTER TABLE [dbo].[Alumnos] DROP COLUMN [ALU_CMM_EscolaridadMaximaId]
GO

ALTER TABLE [dbo].[Alumnos] DROP COLUMN [ALU_LugarEstudios]
GO

ALTER TABLE [dbo].[Alumnos] DROP COLUMN [ALU_Ocupacion]
GO

/***************************/
/***** Nuevas columnas *****/
/***************************/

ALTER TABLE [dbo].[Alumnos] ADD [ALU_EmpresaOEscuela] varchar(255) NULL
GO

ALTER TABLE [dbo].[Alumnos] ADD [ALU_MUN_MunicipioNacimientoId] int NULL
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_MUN_MunicipioNacimientoId] FOREIGN KEY([ALU_MUN_MunicipioNacimientoId])
REFERENCES [dbo].[Municipios] (MUN_MunicipioId)
GO

ALTER TABLE [dbo].[Alumnos] CHECK CONSTRAINT [FK_ALU_MUN_MunicipioNacimientoId]
GO

ALTER TABLE [dbo].[Alumnos] ADD [ALU_MUN_MunicipioId] int NULL
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_MUN_MunicipioId] FOREIGN KEY([ALU_MUN_MunicipioId])
REFERENCES [dbo].[Municipios] (MUN_MunicipioId)
GO

ALTER TABLE [dbo].[Alumnos] CHECK CONSTRAINT [FK_ALU_MUN_MunicipioId]
GO

ALTER TABLE [dbo].[Alumnos] ADD [ALU_ProblemaSaludOLimitante] varchar(255) NULL
GO

ALTER TABLE [dbo].[Alumnos] ADD [ALU_CMM_MedioEnteradoProulex] int NULL
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_CMM_MedioEnteradoProulex] FOREIGN KEY([ALU_CMM_MedioEnteradoProulex])
REFERENCES [dbo].[ControlesMaestrosMultiples] (CMM_ControlId)
GO

ALTER TABLE [dbo].[Alumnos] CHECK CONSTRAINT [FK_ALU_CMM_MedioEnteradoProulex]
GO

ALTER TABLE [dbo].[Alumnos] ADD [ALU_CMM_RazonEleccionProulex] int NULL
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_CMM_RazonEleccionProulex] FOREIGN KEY([ALU_CMM_RazonEleccionProulex])
REFERENCES [dbo].[ControlesMaestrosMultiples] (CMM_ControlId)
GO

ALTER TABLE [dbo].[Alumnos] CHECK CONSTRAINT [FK_ALU_CMM_RazonEleccionProulex]
GO

ALTER TABLE [dbo].[AlumnosFacturacion] ADD [ALUF_MUN_MunicipioId] int NULL
GO

ALTER TABLE [dbo].[AlumnosFacturacion]  WITH CHECK ADD  CONSTRAINT [FK_ALUF_MUN_MunicipioId] FOREIGN KEY([ALUF_MUN_MunicipioId])
REFERENCES [dbo].[Municipios] (MUN_MunicipioId)
GO

ALTER TABLE [dbo].[AlumnosFacturacion] CHECK CONSTRAINT [FK_ALUF_MUN_MunicipioId]
GO

ALTER TABLE [dbo].[AlumnosFacturacion] ALTER COLUMN [ALUF_Ciudad] nvarchar(100) NULL
GO

ALTER TABLE [dbo].[AlumnosFacturacion] WITH CHECK ADD CONSTRAINT [CHK_ALUF_Municipio] CHECK (
    (
        [ALUF_MUN_MunicipioId] IS NOT NULL
        AND [ALUF_Ciudad] IS NULL
    ) OR (
        [ALUF_MUN_MunicipioId] IS NULL
        AND [ALUF_Ciudad] IS NOT NULL
    )
)
GO

/********************************************************/
/***** MenuListadosGenerales - MedioEnteradoProulex *****/
/********************************************************/

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
	/* [MLG_Titulo] */ 'Medio de enterado',
	/* [MLG_TituloEN] */ 'Means to find out',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'help_outline',
	/* [MLG_Orden] */ 8,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_CE_MedioEnteradoProulex',
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
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Medio de enterado' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Medio",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Medio requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El medio no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Medio",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
)
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_MedioEnteradoProulex'
  ,'Facebook'
  ,NULL
  ,NULL
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_MedioEnteradoProulex'
  ,'Instagram'
  ,NULL
  ,NULL
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_MedioEnteradoProulex'
  ,'Tiktok'
  ,NULL
  ,NULL
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_MedioEnteradoProulex'
  ,'Google'
  ,NULL
  ,NULL
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_MedioEnteradoProulex'
  ,'Sindicato UDG'
  ,NULL
  ,NULL
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_MedioEnteradoProulex'
  ,'Volante impreso'
  ,NULL
  ,NULL
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_MedioEnteradoProulex'
  ,'Por recomendación'
  ,NULL
  ,NULL
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_MedioEnteradoProulex'
  ,'Me llamaron'
  ,NULL
  ,NULL
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_MedioEnteradoProulex'
  ,'Por el convenio con mi empresa/escuela'
  ,NULL
  ,NULL
  ,NULL
GO

/********************************************************/
/***** MenuListadosGenerales - RazonEleccionProulex *****/
/********************************************************/

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
	/* [MLG_Titulo] */ 'Razón de elección',
	/* [MLG_TituloEN] */ 'Reason for choice',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'help_outline',
	/* [MLG_Orden] */ 9,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_CE_RazonEleccionProulex',
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
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Razón de elección' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Razón",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Razón requerida"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "La razón no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Razón",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
)
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_RazonEleccionProulex'
  ,'Excelencia académica'
  ,NULL
  ,NULL
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_RazonEleccionProulex'
  ,'Equipamiento de aulas'
  ,NULL
  ,NULL
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_RazonEleccionProulex'
  ,'Ambiente'
  ,NULL
  ,NULL
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_RazonEleccionProulex'
  ,'Ubicación'
  ,NULL
  ,NULL
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_RazonEleccionProulex'
  ,'Precios'
  ,NULL
  ,NULL
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_RazonEleccionProulex'
  ,'Ya había estudiado antes aquí'
  ,NULL
  ,NULL
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_RazonEleccionProulex'
  ,'Para estudiar junto con mi amigo'
  ,NULL
  ,NULL
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_RazonEleccionProulex'
  ,'El sindicato UDG me apoya'
  ,NULL
  ,NULL
  ,NULL
GO