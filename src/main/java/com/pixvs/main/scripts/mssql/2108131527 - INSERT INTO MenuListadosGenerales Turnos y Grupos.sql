INSERT INTO [dbo].[MenuListadosGenerales]
           ([MLG_NodoPadreId]
           ,[MLG_Titulo]
           ,[MLG_TituloEN]
           ,[MLG_Activo]
           ,[MLG_Icono]
           ,[MLG_Orden]
           ,[MLG_CMM_TipoNodoId]
           ,[MLG_NombreTablaCatalogo]
           ,[MLG_CMM_ControlCatalogo]
           ,[MLG_PermiteBorrar]
           ,[MLG_UrlAPI]
           ,[MLG_FechaCreacion]
           ,[MLG_FechaModificacion]
           ,[MLG_USU_CreadoPorId]
           ,[MLG_USU_ModificadoPorId])
     VALUES
           ((SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo = N'CONTROL ESCOLAR')
           ,N'Turnos'
           ,N'Shifts'
           ,1
           ,N'school'
           ,(SELECT MAX(MLG_Orden) + 1 FROM MenuListadosGenerales WHERE MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo = N'CONTROL ESCOLAR'))
           ,1000082
           ,N'ControlesMaestrosMultiples'
           ,N'CMM_ALU_Turnos'
           ,0
           ,N'/api/v1/cmm'
           ,GETDATE()
           ,NULL
           ,NULL
           ,NULL)
GO

INSERT INTO [dbo].[MenuListadosGenerales]
           ([MLG_NodoPadreId]
           ,[MLG_Titulo]
           ,[MLG_TituloEN]
           ,[MLG_Activo]
           ,[MLG_Icono]
           ,[MLG_Orden]
           ,[MLG_CMM_TipoNodoId]
           ,[MLG_NombreTablaCatalogo]
           ,[MLG_CMM_ControlCatalogo]
           ,[MLG_PermiteBorrar]
           ,[MLG_UrlAPI]
           ,[MLG_FechaCreacion]
           ,[MLG_FechaModificacion]
           ,[MLG_USU_CreadoPorId]
           ,[MLG_USU_ModificadoPorId])
     VALUES
           ((SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo = N'CONTROL ESCOLAR')
           ,N'Grados'
           ,N'Grades'
           ,1
           ,N'school'
           ,(SELECT MAX(MLG_Orden) + 1 FROM MenuListadosGenerales WHERE MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo = N'CONTROL ESCOLAR'))
           ,1000082
           ,N'ControlesMaestrosMultiples'
           ,N'CMM_ALU_Grados'
           ,0
           ,N'/api/v1/cmm'
           ,GETDATE()
           ,NULL
           ,NULL
           ,NULL)
GO

INSERT INTO [dbo].[MenuListadosGeneralesDetalles]
           ([MLGD_MLG_ListadoGeneralNodoId]
           ,[MLGD_JsonConfig]
           ,[MLGD_JsonListado]
           ,[MLGD_CampoTabla]
           ,[MLGD_CampoModelo])
     VALUES
           ((SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo = N'Turnos')
           ,N'{"type": "input","label": "Nombre","inputType": "text","name": "valor","validations": [{"name": "required","validator": "Validators.required","message": "Nombre requerida"},{"name": "maxlength","validator": "Validators.maxLength(255)","message": "La nombre no debe sobrepasar los 255 caracteres"}],"fxFlex": "100"}'
           ,N'{"name": "valor","title": "Nombre","class": "","centrado": false,"type": null,"tooltip": false}'
           ,N'CMM_Valor'
           ,N'valor')
GO

INSERT INTO [dbo].[MenuListadosGeneralesDetalles]
           ([MLGD_MLG_ListadoGeneralNodoId]
           ,[MLGD_JsonConfig]
           ,[MLGD_JsonListado]
           ,[MLGD_CampoTabla]
           ,[MLGD_CampoModelo])
     VALUES
           ((SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo = N'Grados')
           ,N'{"type": "input","label": "# grado","inputType": "number","name": "valor","validations": [{"name": "required","validator": "Validators.required","message": "Nombre requerida"},{"name": "min","validator": "Validators.min(1)","message": "El grupo debe ser mayor que cero."}],"fxFlex": "100"}'
           ,N'{"name": "valor","title": "# grado","class": "","centrado": false,"type": null,"tooltip": false}'
           ,N'CMM_Valor'
           ,N'valor')
GO