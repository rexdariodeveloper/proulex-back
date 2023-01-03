/****** Object:  Table [dbo].[Descuentos]    Script Date: 15/06/2021 13:34:41 a. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[PAProfesoresCategorias](
	[PAPC_ProfesorCategoriaId] [int] IDENTITY(1,1) NOT NULL,
	[PAPC_Categoria] [nvarchar](1) NOT NULL,
	[PAPC_SalarioDiario] [decimal](10,2) NOT NULL,
	[PAPC_Activo] [bit] NOT NULL,
	[PAPC_FechaCreacion] [datetime2](7) NOT NULL,
	[PAPC_USU_CreadoPorId] [int] NOT NULL,
	[PAPC_FechaUltimaModificacion] [datetime2](7) NULL,
	[PAPC_USU_ModificadoPorId] [int] NULL,
 CONSTRAINT [PK_PAProfesoresCategorias] PRIMARY KEY CLUSTERED 
(
	[PAPC_ProfesorCategoriaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[PAProfesoresCategorias] ADD  CONSTRAINT [DF_PAPC_FechaCreacion]  DEFAULT (getdate()) FOR [PAPC_FechaCreacion]
GO

ALTER TABLE [dbo].[PAProfesoresCategorias]  WITH CHECK ADD  CONSTRAINT [FK_PAPC_USU_CreadoPorId] FOREIGN KEY([PAPC_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[PAProfesoresCategorias] CHECK CONSTRAINT [FK_PAPC_USU_CreadoPorId]
GO
---
ALTER TABLE [dbo].[PAProfesoresCategorias]  WITH CHECK ADD  CONSTRAINT [FK_PAPC_USU_ModificadoPorId] FOREIGN KEY([PAPC_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[PAProfesoresCategorias] CHECK CONSTRAINT [FK_PAPC_USU_ModificadoPorId]
GO

----INSERT Listados Generales-------------
INSERT INTO [dbo].[MenuListadosGenerales]
           ([MLG_NodoPadreId],[MLG_Titulo],[MLG_TituloEN],[MLG_Activo],[MLG_Icono],[MLG_Orden]
           ,[MLG_CMM_TipoNodoId],[MLG_NombreTablaCatalogo],[MLG_CMM_ControlCatalogo]
           ,[MLG_PermiteBorrar],[MLG_UrlAPI],[MLG_FechaCreacion])
     VALUES
           ((Select MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='PROGRAMACIÓN ACADÉMICA'),
		   'Categorias','Categories',1,'backup_table',5,1000082,'PAProfesoresCategorias',null,1,'/api/v1/profesores-categorias',GETDATE())
GO

INSERT INTO [dbo].[MenuListadosGeneralesDetalles]
           ([MLGD_MLG_ListadoGeneralNodoId],[MLGD_JsonConfig],[MLGD_JsonListado]
           ,[MLGD_CampoTabla],[MLGD_CampoModelo])
     VALUES
           ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Categorias')
           ,'{"type": "input","label": "Categoria","inputType": "text","name": "categoria","validations": [{"name": "required","validator": "Validators.required","message": "Categoria requerida"},{"name": "maxlength","validator": "Validators.maxLength(1)","message": "La categoria debe ser de solo 1 caracter"}],"fxFlex": "100"}'
           ,'{"name": "categoria","title": "Categoria","class": "","centrado": false,"type": null,"tooltip": false}'
           ,'PAPC_Categoria'
           ,'categoria'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Categorias')
           ,'{"type": "input","label": "Salario Diario","inputType": "text","name": "salarioDiario", "prefix":"$", "mask":"separator.2", "thousandSeparator":"," ,"validations": [{"name": "required","validator": "Validators.required","message": "Test requerido"}],"fxFlex": "100"}'
           ,'{"name": "salarioDiario","title": "Salario diario","class": "","centrado": false,"type": null,"tooltip": false}'
           ,'PAPC_SalarioDiario'
           ,'salarioDiario')
GO
