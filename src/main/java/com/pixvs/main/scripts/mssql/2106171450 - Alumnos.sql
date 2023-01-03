/**
 * Created by Angel Daniel Hernández Silva on 27/05/2021.
 * Object:  Table [dbo].[Alumnos]
 */


/*******************/
/***** Alumnos *****/
/*******************/

CREATE TABLE [dbo].[Alumnos](
	[ALU_AlumnoId] [int] IDENTITY(1,1) NOT NULL ,
	[ALU_Activo] [bit]  NOT NULL ,
	[ALU_Codigo] [varchar]  (150) NOT NULL ,
    [ALU_Nombre] [nvarchar](50) NOT NULL,
	[ALU_PrimerApellido] [nvarchar](50) NOT NULL,
	[ALU_SegundoApellido] [nvarchar](50) NULL,
	[ALU_FechaNacimiento] [date] NOT NULL,
    [ALU_PAI_PaisNacimientoId] [smallint] NOT NULL,
    [ALU_EST_EstadoNacimientoId] [int] NOT NULL,
    [ALU_CiudadNacimiento] [nvarchar](100) NOT NULL,
    [ALU_CMM_GeneroId] [int] NOT NULL,
    [ALU_CURP] [nvarchar](30) NOT NULL,
    [ALU_CMM_EscolaridadMaximaId] [int] NOT NULL,
    [ALU_LugarEstudios] [nvarchar](50) NOT NULL,
    [ALU_Ocupacion] [nvarchar](50) NOT NULL,
    [ALU_ARC_FotoId] [int] NULL,
    
    [ALU_Domicilio] [nvarchar](250) NOT NULL,
    [ALU_Colonia] [nvarchar](250) NOT NULL,
    [ALU_CP] [nvarchar](10) NOT NULL,
    [ALU_PAI_PaisId] [smallint] NOT NULL,
    [ALU_EST_EstadoId] [int] NOT NULL,
    [ALU_Ciudad] [nvarchar](100) NOT NULL,
    [ALU_CorreoElectronico] [nvarchar](50) NOT NULL,
    [ALU_TelefonoFijo] [nvarchar](50) NULL,
    [ALU_TelefonoMovil] [nvarchar](50) NULL,
    [ALU_TelefonoTrabajo] [nvarchar](50) NULL,
    [ALU_TelefonoTrabajoExtension] [nvarchar](10) NULL,
    [ALU_TelefonoMensajeriaInstantanea] [nvarchar](50) NULL,
    
    [ALU_FechaCreacion] [datetime2](7) NOT NULL,
	[ALU_USU_CreadoPorId] [int] NULL,
	[ALU_FechaModificacion] [datetime2](7) NULL,
	[ALU_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[ALU_AlumnoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_PAI_PaisNacimientoId] FOREIGN KEY([ALU_PAI_PaisNacimientoId])
REFERENCES [dbo].[Paises] ([PAI_PaisId])
GO

ALTER TABLE [dbo].[Alumnos] CHECK CONSTRAINT [FK_ALU_PAI_PaisNacimientoId]
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_EST_EstadoNacimientoId] FOREIGN KEY([ALU_EST_EstadoNacimientoId])
REFERENCES [dbo].[Estados] ([EST_EstadoId])
GO

ALTER TABLE [dbo].[Alumnos] CHECK CONSTRAINT [FK_ALU_EST_EstadoNacimientoId]
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_CMM_GeneroId] FOREIGN KEY([ALU_CMM_GeneroId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Alumnos] CHECK CONSTRAINT [FK_ALU_CMM_GeneroId]
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_CMM_EscolaridadMaximaId] FOREIGN KEY([ALU_CMM_EscolaridadMaximaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Alumnos] CHECK CONSTRAINT [FK_ALU_CMM_EscolaridadMaximaId]
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_ARC_FotoId] FOREIGN KEY([ALU_ARC_FotoId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[Alumnos] CHECK CONSTRAINT [FK_ALU_ARC_FotoId]
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_PAI_PaisId] FOREIGN KEY([ALU_PAI_PaisId])
REFERENCES [dbo].[Paises] ([PAI_PaisId])
GO

ALTER TABLE [dbo].[Alumnos] CHECK CONSTRAINT [FK_ALU_PAI_PaisId]
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_EST_EstadoId] FOREIGN KEY([ALU_EST_EstadoId])
REFERENCES [dbo].[Estados] ([EST_EstadoId])
GO

ALTER TABLE [dbo].[Alumnos] CHECK CONSTRAINT [FK_ALU_EST_EstadoId]
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_USU_CreadoPorId] FOREIGN KEY([ALU_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Alumnos] CHECK CONSTRAINT [FK_ALU_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_USU_ModificadoPorId] FOREIGN KEY([ALU_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Alumnos] CHECK CONSTRAINT [FK_ALU_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[Alumnos] WITH CHECK ADD CONSTRAINT [CHK_ALU_Telefono] CHECK (
    [ALU_TelefonoFijo] IS NOT NULL
    OR [ALU_TelefonoMovil] IS NOT NULL
    OR [ALU_TelefonoTrabajo] IS NOT NULL
    OR [ALU_TelefonoMensajeriaInstantanea] IS NOT NULL
)
GO

ALTER TABLE [dbo].[Alumnos] WITH CHECK ADD CONSTRAINT [UNQ_ALU_CURP] UNIQUE ([ALU_CURP])
GO

/****************************/
/***** AlumnosContactos *****/
/****************************/

CREATE TABLE [dbo].[AlumnosContactos](
	[ALUC_AlumnoContactoId] [int] IDENTITY(1,1) NOT NULL ,
	[ALUC_ALU_AlumnoId] [int]  NOT NULL ,
	[ALUC_Nombre] [nvarchar](50) NOT NULL,
	[ALUC_PrimerApellido] [nvarchar](50) NOT NULL,
	[ALUC_SegundoApellido] [nvarchar](50) NULL,
	[ALUC_CMM_ParentescoId] [int] NOT NULL,
	[ALUC_TelefonoFijo] [nvarchar](50) NULL,
    [ALUC_TelefonoMovil] [nvarchar](50) NULL,
    [ALUC_TelefonoTrabajo] [nvarchar](50) NULL,
    [ALUC_TelefonoTrabajoExtension] [nvarchar](10) NULL,
    [ALUC_TelefonoMensajeriaInstantanea] [nvarchar](50) NULL,
	[ALUC_CorreoElectronico] [nvarchar](50) NOT NULL
PRIMARY KEY CLUSTERED 
(
	[ALUC_AlumnoContactoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[AlumnosContactos]  WITH CHECK ADD  CONSTRAINT [FK_ALUC_ALU_AlumnoId] FOREIGN KEY([ALUC_ALU_AlumnoId])
REFERENCES [dbo].[Alumnos] ([ALU_AlumnoId])
GO

ALTER TABLE [dbo].[AlumnosContactos] CHECK CONSTRAINT [FK_ALUC_ALU_AlumnoId]
GO

ALTER TABLE [dbo].[AlumnosContactos]  WITH CHECK ADD  CONSTRAINT [FK_ALUC_CMM_ParentescoId] FOREIGN KEY([ALUC_CMM_ParentescoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[AlumnosContactos] CHECK CONSTRAINT [FK_ALUC_CMM_ParentescoId]
GO

ALTER TABLE [dbo].[AlumnosContactos] WITH CHECK ADD CONSTRAINT [CHK_ALUC_Telefono] CHECK (
    [ALUC_TelefonoFijo] IS NOT NULL
    OR [ALUC_TelefonoMovil] IS NOT NULL
    OR [ALUC_TelefonoTrabajo] IS NOT NULL
    OR [ALUC_TelefonoMensajeriaInstantanea] IS NOT NULL
)
GO

/******************************/
/***** AlumnosFacturacion *****/
/******************************/

CREATE TABLE [dbo].[AlumnosFacturacion](
	[ALUF_AlumnoContactoId] [int] IDENTITY(1,1) NOT NULL ,
	[ALUF_ALU_AlumnoId] [int]  NOT NULL,
    [ALUF_CMM_TipoPersonaId] [int] NOT NULL,
    [ALUF_Predeterminado] [bit] NOT NULL,
    [ALUF_RFC] [nvarchar](20) NOT NULL,
    [ALUF_Nombre] [nvarchar](50) NULL,
	[ALUF_PrimerApellido] [nvarchar](50) NULL,
	[ALUF_SegundoApellido] [nvarchar](50) NULL,
    [ALUF_RazonSocial] [nvarchar](50) NULL,
    [ALUF_RegistroIdentidadFiscal] [nvarchar](50) NULL,
    [ALUF_Calle] [nvarchar](250) NOT NULL,
    [ALUF_NumeroExterior] [nvarchar](10) NOT NULL,
    [ALUF_NumeroInterior] [nvarchar](10) NULL,
    [ALUF_Colonia] [nvarchar](250) NOT NULL,
    [ALUF_CP] [nvarchar](10) NOT NULL,
    [ALUF_PAI_PaisId] [smallint] NOT NULL,
    [ALUF_EST_EstadoId] [int] NOT NULL,
    [ALUF_Ciudad] [nvarchar](100) NOT NULL,
    [ALUF_CorreoElectronico] [nvarchar](50) NOT NULL,
    [ALUF_TelefonoFijo] [nvarchar](50) NULL,
    [ALUF_TelefonoMovil] [nvarchar](50) NULL,
    [ALUF_TelefonoTrabajo] [nvarchar](50) NULL,
    [ALUF_TelefonoTrabajoExtension] [nvarchar](10) NULL,
    [ALUF_TelefonoMensajeriaInstantanea] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[ALUF_AlumnoContactoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[AlumnosFacturacion]  WITH CHECK ADD  CONSTRAINT [FK_ALUF_ALU_AlumnoId] FOREIGN KEY([ALUF_ALU_AlumnoId])
REFERENCES [dbo].[Alumnos] ([ALU_AlumnoId])
GO

ALTER TABLE [dbo].[AlumnosFacturacion] CHECK CONSTRAINT [FK_ALUF_ALU_AlumnoId]
GO

ALTER TABLE [dbo].[AlumnosFacturacion]  WITH CHECK ADD  CONSTRAINT [FK_ALUF_CMM_TipoPersonaId] FOREIGN KEY([ALUF_CMM_TipoPersonaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[AlumnosFacturacion] CHECK CONSTRAINT [FK_ALUF_CMM_TipoPersonaId]
GO

ALTER TABLE [dbo].[AlumnosFacturacion]  WITH CHECK ADD  CONSTRAINT [FK_ALUF_PAI_PaisId] FOREIGN KEY([ALUF_PAI_PaisId])
REFERENCES [dbo].[Paises] ([PAI_PaisId])
GO

ALTER TABLE [dbo].[AlumnosFacturacion] CHECK CONSTRAINT [FK_ALUF_PAI_PaisId]
GO

ALTER TABLE [dbo].[AlumnosFacturacion]  WITH CHECK ADD  CONSTRAINT [FK_ALUF_EST_EstadoId] FOREIGN KEY([ALUF_EST_EstadoId])
REFERENCES [dbo].[Estados] ([EST_EstadoId])
GO

ALTER TABLE [dbo].[AlumnosFacturacion] CHECK CONSTRAINT [FK_ALUF_EST_EstadoId]
GO

ALTER TABLE [dbo].[AlumnosFacturacion] WITH CHECK ADD CONSTRAINT [CHK_ALUF_Telefono] CHECK (
    [ALUF_TelefonoFijo] IS NOT NULL
    OR [ALUF_TelefonoMovil] IS NOT NULL
    OR [ALUF_TelefonoTrabajo] IS NOT NULL
    OR [ALUF_TelefonoMensajeriaInstantanea] IS NOT NULL
)
GO

/******************************************/
/***** Vista VW_REPORTE_EXCEL_ALUMNOS *****/
/******************************************/

CREATE OR ALTER VIEW [dbo].[VW_REPORTE_EXCEL_ALUMNOS] AS

    SELECT
        ALU_Codigo AS "Código alumno",
        ALU_Nombre AS "Nombre",
        ALU_PrimerApellido + COALESCE(' ' + ALU_SegundoApellido,'') AS "Apellidos",
        CAST(DATEDIFF(YEAR,ALU_FechaNacimiento,GETDATE()) AS nvarchar(MAX)) + ' años, ' + CAST(DATEDIFF(MONTH,ALU_FechaNacimiento,DATEADD(YEAR,DATEDIFF(YEAR,GETDATE(),ALU_FechaNacimiento),GETDATE())) AS nvarchar(MAX)) + ' meses' AS "Edad",
		ALU_CorreoElectronico AS "Correo electrónico",
		COALESCE(ALU_TelefonoMovil,ALU_TelefonoFijo,ALU_TelefonoTrabajo + COALESCE(' (' + ALU_TelefonoTrabajoExtension + ')',''), ALU_TelefonoMensajeriaInstantanea) AS "Teléfono",
		'' AS "Sede",
		ALU_Activo AS "Estatus"
    FROM Alumnos

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
		'' AS sucursalNombre,
		ALU_Activo AS activo
    FROM Alumnos

GO

/*************************/
/***** MenuPrincipal *****/
/*************************/

INSERT [dbo].[MenuPrincipal] (
    [MP_Activo],
    [MP_FechaCreacion],
    [MP_Icono],
    [MP_NodoPadreId],
    [MP_Orden],
    [MP_CMM_SistemaAccesoId],
    [MP_Titulo],
    [MP_TituloEN],
    [MP_Tipo],
    [MP_URL]
) 
VALUES (
    1, -- [MP_Activo]
    GETDATE(), -- [MP_FechaCreacion]
    N'person', -- [MP_Icono]
    (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Control escolar'), -- [MP_NodoPadreId]
    1, -- [MP_Orden]
    1000021, -- [MP_CMM_SistemaAccesoId]
    N'Alumnos', -- [MP_Titulo]
    N'Students', -- [MP_TituloEN]
    N'item', -- [MP_Tipo]
    N'/app/control-escolar/alumnos' -- [MP_URL]
)
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Alumnos' and MP_Icono = 'person' and MP_Orden = 1)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO

/***********************************************/
/***** MenuListadosGenerales - Escolaridad *****/
/***********************************************/

INSERT [dbo].[MenuListadosGenerales] (
	-- [MLG_ListadoGeneralNodoId],
	-- [MLG_NodoPadreId],
	[MLG_Titulo],
	[MLG_TituloEN],
	[MLG_Activo],
	-- [MLG_Icono],
	[MLG_Orden],
	[MLG_CMM_TipoNodoId],
	-- [MLG_NombreTablaCatalogo],
	-- [MLG_CMM_ControlCatalogo],
	[MLG_PermiteBorrar],
	-- [MLG_UrlAPI],
	[MLG_FechaCreacion]
) VALUES (
	-- /* [MLG_ListadoGeneralNodoId] */ 8,
	-- /* [MLG_NodoPadreId] */ 1,
	/* [MLG_Titulo] */ 'CONTROL ESCOLAR',
	/* [MLG_TituloEN] */ 'SCHOOL CONTROL',
	/* [MLG_Activo] */ 1,
	-- /* [MLG_Icono] */ 'list',
	/* [MLG_Orden] */ 6,
	/* [MLG_CMM_TipoNodoId] */ 1000081,
	-- /* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	-- /* [MLG_CMM_ControlCatalogo] */ 'CMM_ART_Clasificacion1',
	/* [MLG_PermiteBorrar] */ 0,
	-- /* [MLG_UrlAPI] */ '/api/v1/cmm',
	/* [MLG_FechaCreacion] */ GETDATE()
)
GO

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
	/* [MLG_Titulo] */ 'Escolaridad',
	/* [MLG_TituloEN] */ 'Education level',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'school',
	/* [MLG_Orden] */ 1,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_CE_Escolaridad',
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
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Escolaridad' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Código",' +
    '"inputType": "text",' +
    '"name": "referencia",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Código requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(5)","message": "El código no debe sobrepasar los 5 caracteres"}' +
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
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Escolaridad' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Descripción",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Descripción requerida"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "La descripción no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Descripción",' +
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
   'CMM_CE_Escolaridad'
  ,'Primaria'
  ,'1'
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_Escolaridad'
  ,'Secundaria'
  ,'2'
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_Escolaridad'
  ,'Preparatoria'
  ,'3'
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_Escolaridad'
  ,'Licenciatura'
  ,'4'
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_Escolaridad'
  ,'Maestría'
  ,'5'
  ,NULL
GO

EXECUTE [dbo].[sp_InsertCMM] 
   'CMM_CE_Escolaridad'
  ,'Doctorado'
  ,'6'
  ,NULL
GO

/**********************************************/
/***** MenuListadosGenerales - Parentesco *****/
/**********************************************/

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
	/* [MLG_Titulo] */ 'Parentesco',
	/* [MLG_TituloEN] */ 'Kinship',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'people',
	/* [MLG_Orden] */ 2,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_CE_Parentesco',
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
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Parentesco' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Descripción",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Descripción requerida"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "La descripción no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Descripción",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
)
GO

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
	/* [CMM_ControlId] */ 2000400,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CE_Parentesco',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Madre'
),(
	/* [CMM_ControlId] */ 2000401,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CE_Parentesco',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Padre'
),(
	/* [CMM_ControlId] */ 2000402,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CE_Parentesco',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Tutor'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

/*****************************/
/***** CMM - TipoPersona *****/
/*****************************/

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
	/* [CMM_ControlId] */ 2000410,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_RFC_TipoPersona',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Física'
),(
	/* [CMM_ControlId] */ 2000411,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_RFC_TipoPersona',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Moral'
),(
	/* [CMM_ControlId] */ 2000412,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_RFC_TipoPersona',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Extranjero'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

/**************************************/
/***** ArchivosEstructuraCarpetas *****/
/**************************************/

SET IDENTITY_INSERT [dbo].[ArchivosEstructuraCarpetas] ON 
GO

INSERT [dbo].[ArchivosEstructuraCarpetas] 
	([AEC_EstructuraId],[AEC_AEC_EstructuraReferenciaId],[AEC_Descripcion],[AEC_NombreCarpeta],[AEC_Activo],[AEC_USU_CreadoPorId],[AEC_FechaCreacion]) 
VALUES 
	(15,	NULL,	'Alumnos',	'alumnos',	1,	(SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'pixvs.server@gmail.com'),	GETDATE()),
	(16,	15,		'Fotos',	'fotos',	1,	(SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'pixvs.server@gmail.com'),	GETDATE())
GO

SET IDENTITY_INSERT [dbo].[ArchivosEstructuraCarpetas] OFF
GO

/*************************/
/***** Autonumericos *****/
/*************************/

INSERT INTO [dbo].[Autonumericos]
           ([AUT_Nombre]
           ,[AUT_Prefijo]
           ,[AUT_Siguiente]
           ,[AUT_Digitos]
           ,[AUT_Activo])
     VALUES
           ('Alumnos'
           ,'PLX'
           ,1
           ,6
           ,1)
GO