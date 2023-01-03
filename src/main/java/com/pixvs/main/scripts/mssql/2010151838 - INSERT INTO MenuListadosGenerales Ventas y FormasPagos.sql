SET IDENTITY_INSERT [dbo].[MenuListadosGenerales] ON 
GO

INSERT INTO [dbo].[MenuListadosGenerales]
           ([MLG_ListadoGeneralNodoId],[MLG_NodoPadreId],[MLG_Titulo],[MLG_TituloEN],[MLG_Activo],[MLG_Icono],[MLG_Orden],[MLG_CMM_TipoNodoId],[MLG_NombreTablaCatalogo],[MLG_CMM_ControlCatalogo],[MLG_PermiteBorrar],[MLG_UrlAPI],[MLG_FechaCreacion],[MLG_FechaModificacion],[MLG_USU_CreadoPorId],[MLG_USU_ModificadoPorId])
     VALUES
           ( 14, NULL, 'VENTAS'        , 'SALES'          , 1, NULL  , 3, 1000081, NULL          , NULL, 0, NULL                 , GETDATE(), NULL, NULL, NULL),
		   ( 15,   14, 'Formas de pago', 'Payment methods', 1, 'list', 1, 1000082, 'FormasPagoPV', NULL, 1, '/api/v1/formas-pago', GETDATE(), NULL, NULL, NULL)
GO

SET IDENTITY_INSERT [dbo].[MenuListadosGenerales] OFF
GO
