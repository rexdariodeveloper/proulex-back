---------------------------------------------------------- Empleados Beneficiarios ---------------------------------------------------------
CREATE TABLE [dbo].[EmpleadosBeneficiarios](
	[EMPBE_EmpleadoBeneficiarioId] [int] IDENTITY(1,1) NOT NULL,
	[EMPBE_EMP_EmpleadoId] [int] NOT NULL,
	[EMPBE_Nombre] [varchar](255) NOT NULL,
	[EMPBE_PrimerApellido] [varchar](255) NOT NULL,
	[EMPBE_SegundoApellido] [varchar](255) NOT NULL,
	[EMPBE_CMM_ParentescoId] [int] NOT NULL,
	[EMPBE_Porcentaje] [int] NOT NULL
 CONSTRAINT [PK_EmpleadosBeneficiarios] PRIMARY KEY CLUSTERED 
(
	[EMPBE_EmpleadoBeneficiarioId] ASC,
	[EMPBE_Nombre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[EmpleadosBeneficiarios]  WITH CHECK ADD  CONSTRAINT [FK_EMPBE_EMP_EmpleadoId] FOREIGN KEY([EMPBE_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO
​
ALTER TABLE [dbo].[EmpleadosBeneficiarios] CHECK CONSTRAINT [FK_EMPBE_EMP_EmpleadoId]
GO

ALTER TABLE [dbo].[EmpleadosBeneficiarios]  WITH CHECK ADD  CONSTRAINT [FK_EMPBE_CMM_ParentescoId] FOREIGN KEY([EMPBE_CMM_ParentescoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
​
ALTER TABLE [dbo].[EmpleadosBeneficiarios] CHECK CONSTRAINT [FK_EMPBE_CMM_ParentescoId]
GO

/********************************************************/
/***** MenuListadosGenerales - GradoEstudios *****/
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
	/* [MLG_NodoPadreId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='EMPLEADOS' AND MLG_NodoPadreId IS NULL),
	/* [MLG_Titulo] */ 'Grado de estudios',
	/* [MLG_TituloEN] */ 'Study Grade',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'list',
	/* [MLG_Orden] */ 6,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_EMP_GradoEstudios',
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
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Grado de estudios' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='EMPLEADOS' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Grado de estudios",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Grado de estudios requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El grado no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Grado de estudios",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
)
GO

/********************************************************/
/***** MenuListadosGenerales - Nacionalidad *****/
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
	/* [MLG_NodoPadreId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='EMPLEADOS' AND MLG_NodoPadreId IS NULL),
	/* [MLG_Titulo] */ 'Nacionalidad',
	/* [MLG_TituloEN] */ 'Nacionality',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'list',
	/* [MLG_Orden] */ 7,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_EMP_Nacionalidad',
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
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Nacionalidad' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='EMPLEADOS' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Nacionalidad",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Nacionalidad requerida"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "La nacionalidad no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Nacionalidad",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
)
GO

/********************************************************/
/***** MenuListadosGenerales - Parentesco *****/
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
	/* [MLG_NodoPadreId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='EMPLEADOS' AND MLG_NodoPadreId IS NULL),
	/* [MLG_Titulo] */ 'Parentesco',
	/* [MLG_TituloEN] */ 'Relationship',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'list',
	/* [MLG_Orden] */ 9,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_EMP_Parentesco',
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
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Parentesco' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='EMPLEADOS' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Parentesco",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Parentesco requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El parentesco no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Parentesco",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
)
GO

/********************************************************/
/***** MenuListadosGenerales - TipoSangre *****/
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
	/* [MLG_NodoPadreId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='EMPLEADOS' AND MLG_NodoPadreId IS NULL),
	/* [MLG_Titulo] */ 'Tipo de sangre',
	/* [MLG_TituloEN] */ 'Blood type',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'list',
	/* [MLG_Orden] */ 10,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_EMP_TipoSangre',
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
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Tipo de sangre' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='EMPLEADOS' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Tipo de sangre",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Tipo de sangre requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El tipo de sangre no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Tipo de sangre",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
)
GO

----------------------------------------------------------------------------------------------------
ALTER TABLE [dbo].[Empleados]
ADD [EMP_CMM_GradoEstudiosId] [INT] NULL
GO

ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_CMM_GradoEstudiosId] FOREIGN KEY([EMP_CMM_GradoEstudiosId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_CMM_GradoEstudiosId]
GO

ALTER TABLE [dbo].[Empleados]
ADD [EMP_CMM_NacionalidadId] [INT] NULL
GO

ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_CMM_NacionalidadId] FOREIGN KEY([EMP_CMM_NacionalidadId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_CMM_NacionalidadId]
GO