INSERT INTO [dbo].[Articulos]
           ([ART_CodigoArticulo]
           ,[ART_NombreArticulo]
           ,[ART_CodigoBarras]
           ,[ART_CodigoAlterno]
           ,[ART_NombreAlterno]
           ,[ART_Descripcion]
           ,[ART_DescripcionCorta]
           ,[ART_ClaveProductoSAT]
           ,[ART_IVA]
           ,[ART_IVAExento]
           ,[ART_IEPS]
           ,[ART_IEPSCuotaFija]
           ,[ART_MultiploPedido]
           ,[ART_PermitirCambioAlmacen]
           ,[ART_MaximoAlmacen]
           ,[ART_MinimoAlmacen]
           ,[ART_PlaneacionTemporadas]
           ,[ART_ARC_ImagenId]
           ,[ART_AFAM_FamiliaId]
           ,[ART_ACAT_CategoriaId]
           ,[ART_ASC_SubcategoriaId]
           ,[ART_CMM_Clasificacion1Id]
           ,[ART_CMM_Clasificacion2Id]
           ,[ART_CMM_Clasificacion3Id]
           ,[ART_CMM_Clasificacion4Id]
           ,[ART_CMM_Clasificacion5Id]
           ,[ART_CMM_Clasificacion6Id]
           ,[ART_ARTT_TipoArticuloId]
           ,[ART_ARTST_ArticuloSubtipoId]
           ,[ART_UM_UnidadMedidaInventarioId]
           ,[ART_UM_UnidadMedidaConversionVentasId]
           ,[ART_FactorConversionVentas]
           ,[ART_UM_UnidadMedidaConversionComprasId]
           ,[ART_FactorConversionCompras]
           ,[ART_CMM_TipoCostoId]
           ,[ART_CostoUltimo]
           ,[ART_CostoPromedio]
           ,[ART_CostoEstandar]
           ,[ART_Activo]
           ,[ART_FechaCreacion]
           ,[ART_FechaModificacion]
           ,[ART_USU_CreadoPorId]
           ,[ART_USU_ModificadoPorId]
           ,[ART_CuentaCompras])
     VALUES
           (N'SRV-RCC'
           ,N'Reposición de caja chica'
           , NULL, NULL, NULL
           ,N'Reposición de caja chica'
           ,N'Reposición de caja chica'
           ,01010101, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 0, NULL
           ,(SELECT AFAM_FamiliaId FROM ArticulosFamilias WHERE AFAM_Nombre = N'Servicios'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL
           ,(SELECT ARTT_ArticuloTipoId FROM ArticulosTipos WHERE ARTT_Descripcion = N'Misceláneo'), NULL
           ,(SELECT UM_UnidadMedidaId FROM UnidadesMedidas WHERE UM_ClaveSAT = N'E48')
           ,(SELECT UM_UnidadMedidaId FROM UnidadesMedidas WHERE UM_ClaveSAT = N'E48')
           ,1
           ,(SELECT UM_UnidadMedidaId FROM UnidadesMedidas WHERE UM_ClaveSAT = N'E48')
           ,1, 2000042, 0.00, 0.00, 0.00, 1, GETDATE(), NULL, 1, NULL, NULL
           ),
           (N'SRV-DPC'
           ,N'Devolución de pago de curso'
           , NULL, NULL, NULL
           ,N'Devolución de pago de curso'
           ,N'Devolución de pago de curso'
           ,01010101, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 0, NULL
           ,(SELECT AFAM_FamiliaId FROM ArticulosFamilias WHERE AFAM_Nombre = N'Servicios'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL
           ,(SELECT ARTT_ArticuloTipoId FROM ArticulosTipos WHERE ARTT_Descripcion = N'Misceláneo'), NULL
           ,(SELECT UM_UnidadMedidaId FROM UnidadesMedidas WHERE UM_ClaveSAT = N'E48')
           ,(SELECT UM_UnidadMedidaId FROM UnidadesMedidas WHERE UM_ClaveSAT = N'E48')
           ,1
           ,(SELECT UM_UnidadMedidaId FROM UnidadesMedidas WHERE UM_ClaveSAT = N'E48')
           ,1, 2000042, 0.00, 0.00, 0.00, 1, GETDATE(), NULL, 1, NULL, NULL
           ),
           (N'SRV-AGUA'
           ,N'Agua'
           , NULL, NULL, NULL
           ,N'Agua'
           ,N'Agua'
           ,01010101, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 0, NULL
           ,(SELECT AFAM_FamiliaId FROM ArticulosFamilias WHERE AFAM_Nombre = N'Servicios'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL
           ,(SELECT ARTT_ArticuloTipoId FROM ArticulosTipos WHERE ARTT_Descripcion = N'Misceláneo'), NULL
           ,(SELECT UM_UnidadMedidaId FROM UnidadesMedidas WHERE UM_ClaveSAT = N'E48')
           ,(SELECT UM_UnidadMedidaId FROM UnidadesMedidas WHERE UM_ClaveSAT = N'E48')
           ,1
           ,(SELECT UM_UnidadMedidaId FROM UnidadesMedidas WHERE UM_ClaveSAT = N'E48')
           ,1, 2000042, 0.00, 0.00, 0.00, 1, GETDATE(), NULL, 1, NULL, NULL
           )
GO

INSERT INTO [dbo].[Servicios]
           ([SRV_Concepto]
           ,[SRV_Descripcion]
           ,[SRV_CMM_TipoServicioId]
           ,[SRV_ART_ArticuloId]
           ,[SRV_RequiereXML]
           ,[SRV_RequierePDF]
           ,[SRV_Activo]
           ,[SRV_FechaCreacion]
           ,[SRV_USU_CreadoPorId]
           ,[SRV_FechaModificacion]
           ,[SRV_USU_ModificadoPorId])
     VALUES
           (N'Reposición de caja chica'--<SRV_Concepto, nvarchar(50),>
           ,''--<SRV_Descripcion, nvarchar(250),>
           ,2000221--<SRV_CMM_TipoServicioId, int,>
           ,(SELECT ART_ArticuloId FROM Articulos WHERE ART_CodigoArticulo = N'SRV-RCC')--<SRV_ART_ArticuloId, int,>
           ,0--<SRV_RequiereXML, bit,>
           ,0--<SRV_RequierePDF, bit,>
           ,1--<SRV_Activo, bit,>
           ,GETDATE()--<SRV_FechaCreacion, datetime2(7),>
           ,1--<SRV_USU_CreadoPorId, int,>
           ,NULL--<SRV_FechaModificacion, datetime2(7),>
           ,NULL--<SRV_USU_ModificadoPorId, int,>
           ),

           (N'Devolución de pago de curso'--<SRV_Concepto, nvarchar(50),>
           ,''--<SRV_Descripcion, nvarchar(250),>
           ,2000221--<SRV_CMM_TipoServicioId, int,>
           ,(SELECT ART_ArticuloId FROM Articulos WHERE ART_CodigoArticulo = N'SRV-DPC')--<SRV_ART_ArticuloId, int,>
           ,0--<SRV_RequiereXML, bit,>
           ,0--<SRV_RequierePDF, bit,>
           ,1--<SRV_Activo, bit,>
           ,GETDATE()--<SRV_FechaCreacion, datetime2(7),>
           ,1--<SRV_USU_CreadoPorId, int,>
           ,NULL--<SRV_FechaModificacion, datetime2(7),>
           ,NULL--<SRV_USU_ModificadoPorId, int,>
           ),

           (N'Agua'--<SRV_Concepto, nvarchar(50),>
           ,''--<SRV_Descripcion, nvarchar(250),>
           ,2000221--<SRV_CMM_TipoServicioId, int,>
           ,(SELECT ART_ArticuloId FROM Articulos WHERE ART_CodigoArticulo = N'SRV-AGUA')--<SRV_ART_ArticuloId, int,>
           ,0--<SRV_RequiereXML, bit,>
           ,0--<SRV_RequierePDF, bit,>
           ,1--<SRV_Activo, bit,>
           ,GETDATE()--<SRV_FechaCreacion, datetime2(7),>
           ,1--<SRV_USU_CreadoPorId, int,>
           ,NULL--<SRV_FechaModificacion, datetime2(7),>
           ,NULL--<SRV_USU_ModificadoPorId, int,>
           )
GO