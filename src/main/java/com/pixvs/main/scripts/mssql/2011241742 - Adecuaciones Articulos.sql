-- ============================================= 
-- Author:		Angel Daniel Hernández Silva
-- Create date: 2020/11/24
-- Description:	Adecuaciones artículos
-- --------------------------------------------- 

ALTER TABLE [dbo].[Articulos] ADD [ART_CMM_IdiomaId] [int] NULL
GO
ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_CMM_IdiomaId] FOREIGN KEY([ART_CMM_IdiomaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_CMM_IdiomaId]
GO

ALTER TABLE [dbo].[Articulos] ADD [ART_CMM_ProgramaId] [int] NULL
GO
ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_CMM_ProgramaId] FOREIGN KEY([ART_CMM_ProgramaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_CMM_ProgramaId]
GO

ALTER TABLE [dbo].[Articulos] ADD [ART_CMM_EditorialId] [int] NULL
GO
ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_CMM_EditorialId] FOREIGN KEY([ART_CMM_EditorialId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_CMM_EditorialId]
GO

ALTER TABLE [dbo].[Articulos] ADD [ART_MarcoCertificacion] [varchar](50) NULL
GO






ALTER TABLE [dbo].[ArticulosFamilias] ADD [AFAM_Prefijo] [varchar](5) NULL
GO
UPDATE [dbo].[ArticulosFamilias] SET [AFAM_Prefijo] = [AFAM_FamiliaId]
GO
ALTER TABLE [dbo].[ArticulosFamilias] ALTER COLUMN [AFAM_Prefijo] [varchar](5) NOT NULL
GO
ALTER TABLE [dbo].[ArticulosFamilias] WITH CHECK ADD CONSTRAINT [UNQ_AFAM_Prefijo] UNIQUE ([AFAM_Prefijo])
GO

ALTER TABLE [dbo].[ArticulosCategorias] ADD [ACAT_Prefijo] [varchar](5) NULL
GO
UPDATE [dbo].[ArticulosCategorias] SET [ACAT_Prefijo] = [ACAT_CategoriaId]
GO
ALTER TABLE [dbo].[ArticulosCategorias] ALTER COLUMN [ACAT_Prefijo] [varchar](5) NOT NULL
GO
ALTER TABLE [dbo].[ArticulosCategorias] WITH CHECK ADD CONSTRAINT [UNQ_ACAT_Prefijo] UNIQUE ([ACAT_Prefijo])
GO


INSERT [dbo].[MenuListadosGeneralesDetalles] (
	[MLGD_MLG_ListadoGeneralNodoId],
	[MLGD_JsonConfig],
	[MLGD_JsonListado],
	[MLGD_CampoTabla],
	[MLGD_CampoModelo]
) VALUES (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 2,
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Prefijo",' +
    '"inputType": "text",' +
    '"name": "prefijo",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Prefijo requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(5)","message": "El prefijo no debe sobrepasar los 5 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "prefijo",' +
    '"title": "Prefijo",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'AFAM_Prefijo',
	/* [MLGD_CampoModelo] */ 'prefijo'
), (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 3,
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Prefijo",' +
    '"inputType": "text",' +
    '"name": "prefijo",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Prefijo requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(5)","message": "El prefijo no debe sobrepasar los 5 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "prefijo",' +
    '"title": "Prefijo",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'ACAT_Prefijo',
	/* [MLGD_CampoModelo] */ 'prefijo'
)
GO