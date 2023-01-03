IF (NOT EXISTS(SELECT AFAM_FamiliaId FROM ArticulosFamilias WHERE AFAM_Nombre = 'Servicios'))
BEGIN
INSERT INTO [dbo].[ArticulosFamilias]
           ([AFAM_Nombre]
           ,[AFAM_Descripcion]
           ,[AFAM_Activo]
           ,[AFAM_FechaCreacion]
           ,[AFAM_FechaModificacion]
           ,[AFAM_USU_CreadoPorId]
           ,[AFAM_USU_ModificadoPorId]
           ,[AFAM_ARC_ImagenId])
     VALUES
           ('Servicios'--<AFAM_Nombre, varchar(50),>
           ,'Servicios'--<AFAM_Descripcion, varchar(255),>
           ,1--<AFAM_Activo, bit,>
           ,GETDATE()--<AFAM_FechaCreacion, datetime2(7),>
           ,NULL--<AFAM_FechaModificacion, datetime2(7),>
           ,1--<AFAM_USU_CreadoPorId, int,>
           ,NULL--<AFAM_USU_ModificadoPorId, int,>
           ,NULL--<AFAM_ARC_ImagenId, int,>
      )
END
GO

INSERT INTO [dbo].[UnidadesMedidas]
  ([UM_Nombre],[UM_Clave],[UM_ClaveSAT],[UM_Activo],[UM_FechaCreacion],[UM_FechaModificacion],[UM_USU_CreadoPorId],[UM_USU_ModificadoPorId],[UM_Decimales])
VALUES
  ('Kilowatt hora'  ,'KWH' ,'KWH' ,1,GETDATE(),null,1,null,0),
  ('Servicio'       ,'E48' ,'E48' ,1,GETDATE(),null,1,null,0)
GO

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
           ('SRV-LUZ'--<ART_CodigoArticulo, varchar(30),>
           ,'Luz'--<ART_NombreArticulo, varchar(100),>
           ,NULL--<ART_CodigoBarras, varchar(50),>
           ,NULL--<ART_CodigoAlterno, varchar(30),>
           ,NULL--<ART_NombreAlterno, varchar(100),>
           ,'Luz'--<ART_Descripcion, varchar(1000),>
           ,'Luz'--<ART_DescripcionCorta, varchar(30),>
           ,83101800--<ART_ClaveProductoSAT, varchar(8),>
           ,NULL--<ART_IVA, decimal(3,2),>
           ,NULL--<ART_IVAExento, bit,>
           ,NULL--<ART_IEPS, decimal(3,2),>
           ,NULL--<ART_IEPSCuotaFija, decimal(28,2),>
           ,NULL--<ART_MultiploPedido, decimal(28,2),>
           ,0--<ART_PermitirCambioAlmacen, bit,>
           ,NULL--<ART_MaximoAlmacen, decimal(28,2),>
           ,NULL--<ART_MinimoAlmacen, decimal(28,2),>
           ,0--<ART_PlaneacionTemporadas, bit,>
           ,NULL--<ART_ARC_ImagenId, int,>
           ,(SELECT AFAM_FamiliaId FROM ArticulosFamilias WHERE AFAM_Nombre = 'Servicios')--<ART_AFAM_FamiliaId, smallint,>
           ,NULL--<ART_ACAT_CategoriaId, int,>
           ,NULL--<ART_ASC_SubcategoriaId, int,>
           ,NULL--<ART_CMM_Clasificacion1Id, int,>
           ,NULL--<ART_CMM_Clasificacion2Id, int,>
           ,NULL--<ART_CMM_Clasificacion3Id, int,>
           ,NULL--<ART_CMM_Clasificacion4Id, int,>
           ,NULL--<ART_CMM_Clasificacion5Id, int,>
           ,NULL--<ART_CMM_Clasificacion6Id, int,>
           ,(SELECT ARTT_ArticuloTipoId FROM ArticulosTipos WHERE ARTT_Descripcion = 'Misceláneo')--<ART_ARTT_TipoArticuloId, int,>
           ,NULL--<ART_ARTST_ArticuloSubtipoId, int,>
           ,(SELECT UM_UnidadMedidaId FROM UnidadesMedidas WHERE UM_ClaveSAT = 'KWH')--<ART_UM_UnidadMedidaInventarioId, smallint,>
           ,(SELECT UM_UnidadMedidaId FROM UnidadesMedidas WHERE UM_ClaveSAT = 'KWH')--<ART_UM_UnidadMedidaConversionVentasId, smallint,>
           ,1--<ART_FactorConversionVentas, decimal(28,6),>
           ,(SELECT UM_UnidadMedidaId FROM UnidadesMedidas WHERE UM_ClaveSAT = 'KWH')--<ART_UM_UnidadMedidaConversionComprasId, smallint,>
           ,1--<ART_FactorConversionCompras, decimal(28,6),>
           ,2000042--<ART_CMM_TipoCostoId, int,>
           ,0.00--<ART_CostoUltimo, decimal(28,2),>
           ,0.00--<ART_CostoPromedio, decimal(28,2),>
           ,0.00--<ART_CostoEstandar, decimal(28,2),>
           ,1--<ART_Activo, bit,>
           ,GETDATE()--<ART_FechaCreacion, datetime2(7),>
           ,NULL--<ART_FechaModificacion, datetime2(7),>
           ,1--<ART_USU_CreadoPorId, int,>
           ,NULL--<ART_USU_ModificadoPorId, int,>
           ,NULL--<ART_CuentaCompras, varchar(18),>
           ),
           ('SRV-RENTA'--<ART_CodigoArticulo, varchar(30),>
           ,'Renta'--<ART_NombreArticulo, varchar(100),>
           ,NULL--<ART_CodigoBarras, varchar(50),>
           ,NULL--<ART_CodigoAlterno, varchar(30),>
           ,NULL--<ART_NombreAlterno, varchar(100),>
           ,'Renta'--<ART_Descripcion, varchar(1000),>
           ,'Renta'--<ART_DescripcionCorta, varchar(30),>
           ,80131500--<ART_ClaveProductoSAT, varchar(8),>
           ,NULL--<ART_IVA, decimal(3,2),>
           ,NULL--<ART_IVAExento, bit,>
           ,NULL--<ART_IEPS, decimal(3,2),>
           ,NULL--<ART_IEPSCuotaFija, decimal(28,2),>
           ,NULL--<ART_MultiploPedido, decimal(28,2),>
           ,0--<ART_PermitirCambioAlmacen, bit,>
           ,NULL--<ART_MaximoAlmacen, decimal(28,2),>
           ,NULL--<ART_MinimoAlmacen, decimal(28,2),>
           ,0--<ART_PlaneacionTemporadas, bit,>
           ,NULL--<ART_ARC_ImagenId, int,>
           ,(SELECT AFAM_FamiliaId FROM ArticulosFamilias WHERE AFAM_Nombre = 'Servicios')--<ART_AFAM_FamiliaId, smallint,>
           ,NULL--<ART_ACAT_CategoriaId, int,>
           ,NULL--<ART_ASC_SubcategoriaId, int,>
           ,NULL--<ART_CMM_Clasificacion1Id, int,>
           ,NULL--<ART_CMM_Clasificacion2Id, int,>
           ,NULL--<ART_CMM_Clasificacion3Id, int,>
           ,NULL--<ART_CMM_Clasificacion4Id, int,>
           ,NULL--<ART_CMM_Clasificacion5Id, int,>
           ,NULL--<ART_CMM_Clasificacion6Id, int,>
           ,(SELECT ARTT_ArticuloTipoId FROM ArticulosTipos WHERE ARTT_Descripcion = 'Misceláneo')--<ART_ARTT_TipoArticuloId, int,>
           ,NULL--<ART_ARTST_ArticuloSubtipoId, int,>
           ,(SELECT UM_UnidadMedidaId FROM UnidadesMedidas WHERE UM_ClaveSAT = 'E48')--<ART_UM_UnidadMedidaInventarioId, smallint,>
           ,(SELECT UM_UnidadMedidaId FROM UnidadesMedidas WHERE UM_ClaveSAT = 'E48')--<ART_UM_UnidadMedidaConversionVentasId, smallint,>
           ,1--<ART_FactorConversionVentas, decimal(28,6),>
           ,(SELECT UM_UnidadMedidaId FROM UnidadesMedidas WHERE UM_ClaveSAT = 'E48')--<ART_UM_UnidadMedidaConversionComprasId, smallint,>
           ,1--<ART_FactorConversionCompras, decimal(28,6),>
           ,2000042--<ART_CMM_TipoCostoId, int,>
           ,0.00--<ART_CostoUltimo, decimal(28,2),>
           ,0.00--<ART_CostoPromedio, decimal(28,2),>
           ,0.00--<ART_CostoEstandar, decimal(28,2),>
           ,1--<ART_Activo, bit,>
           ,GETDATE()--<ART_FechaCreacion, datetime2(7),>
           ,NULL--<ART_FechaModificacion, datetime2(7),>
           ,1--<ART_USU_CreadoPorId, int,>
           ,NULL--<ART_USU_ModificadoPorId, int,>
           ,NULL--<ART_CuentaCompras, varchar(18),>
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
           ('Energía'--<SRV_Concepto, nvarchar(50),>
           ,''--<SRV_Descripcion, nvarchar(250),>
           ,2000221--<SRV_CMM_TipoServicioId, int,>
           ,(SELECT ART_ArticuloId FROM Articulos WHERE ART_CodigoArticulo = 'SRV-LUZ')--<SRV_ART_ArticuloId, int,>
           ,0--<SRV_RequiereXML, bit,>
           ,1--<SRV_RequierePDF, bit,>
           ,1--<SRV_Activo, bit,>
           ,GETDATE()--<SRV_FechaCreacion, datetime2(7),>
           ,1--<SRV_USU_CreadoPorId, int,>
           ,NULL--<SRV_FechaModificacion, datetime2(7),>
           ,NULL--<SRV_USU_ModificadoPorId, int,>
           ),
           ('Arrendamiento'--<SRV_Concepto, nvarchar(50),>
           ,''--<SRV_Descripcion, nvarchar(250),>
           ,2000222--<SRV_CMM_TipoServicioId, int,>
           ,(SELECT ART_ArticuloId FROM Articulos WHERE ART_CodigoArticulo = 'SRV-RENTA')--<SRV_ART_ArticuloId, int,>
           ,1--<SRV_RequiereXML, bit,>
           ,1--<SRV_RequierePDF, bit,>
           ,1--<SRV_Activo, bit,>
           ,GETDATE()--<SRV_FechaCreacion, datetime2(7),>
           ,1--<SRV_USU_CreadoPorId, int,>
           ,NULL--<SRV_FechaModificacion, datetime2(7),>
           ,NULL--<SRV_USU_ModificadoPorId, int,>
           )
GO