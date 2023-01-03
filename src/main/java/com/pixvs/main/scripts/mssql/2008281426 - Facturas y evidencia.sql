/**
 * Created by Angel Daniel Hernández Silva on 25/08/2020.
 */


INSERT [dbo].[ControlesMaestros] (
	[CMA_Nombre],
	[CMA_Valor],
	[CMA_Sistema],
	[CMA_FechaModificacion]
) VALUES (
	/* [CMA_Nombre] */ 'CM_RECIBOS_FACTURA_OBLIGATORIA',
	/* [CMA_Valor] */ '0',
	/* [CMA_Sistema] */ 1,
	/* [CMA_FechaModificacion] */ GETDATE()
),(
	/* [CMA_Nombre] */ 'CM_RECIBOS_EVIDENCIA_OBLIGATORIA',
	/* [CMA_Valor] */ '0',
	/* [CMA_Sistema] */ 1,
	/* [CMA_FechaModificacion] */ GETDATE()
)
GO









ALTER TABLE [dbo].[OrdenesCompraRecibos] ADD 
	[OCR_ARC_FacturaPDF] [int] NULL ,
	[OCR_ARC_FacturaXML] [int] NULL
GO

ALTER TABLE [dbo].[OrdenesCompraRecibos]  WITH CHECK ADD  CONSTRAINT [FK_OCR_ARC_FacturaPDF] FOREIGN KEY([OCR_ARC_FacturaPDF])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[OrdenesCompraRecibos] CHECK CONSTRAINT [FK_OCR_ARC_FacturaPDF]
GO

ALTER TABLE [dbo].[OrdenesCompraRecibos]  WITH CHECK ADD  CONSTRAINT [FK_OCR_ARC_FacturaXML] FOREIGN KEY([OCR_ARC_FacturaXML])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[OrdenesCompraRecibos] CHECK CONSTRAINT [FK_OCR_ARC_FacturaXML]
GO










CREATE TABLE [dbo].[OrdenesCompraRecibosEvidencia](
	[OCRE_OCR_OrdenCompraReciboId] [int] NOT NULL,
	[OCRE_ARC_ArchivoId] [int] NOT NULL
PRIMARY KEY CLUSTERED 
(
	[OCRE_OCR_OrdenCompraReciboId] ASC,
	[OCRE_ARC_ArchivoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[OrdenesCompraRecibosEvidencia]  WITH CHECK ADD  CONSTRAINT [FK_OCRE_OCR_OrdenCompraReciboId] FOREIGN KEY([OCRE_OCR_OrdenCompraReciboId])
REFERENCES [dbo].[OrdenesCompraRecibos] ([OCR_OrdenCompraReciboId])
GO

ALTER TABLE [dbo].[OrdenesCompraRecibosEvidencia] CHECK CONSTRAINT [FK_OCRE_OCR_OrdenCompraReciboId]
GO

ALTER TABLE [dbo].[OrdenesCompraRecibosEvidencia]  WITH CHECK ADD  CONSTRAINT [FK_OCRE_ARC_ArchivoId] FOREIGN KEY([OCRE_ARC_ArchivoId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[OrdenesCompraRecibosEvidencia] CHECK CONSTRAINT [FK_OCRE_ARC_ArchivoId]
GO







SET IDENTITY_INSERT [dbo].[ArchivosEstructuraCarpetas] ON 
GO

INSERT [dbo].[ArchivosEstructuraCarpetas] (
	[AEC_EstructuraId],
	[AEC_AEC_EstructuraReferenciaId],
	[AEC_Descripcion],
	[AEC_NombreCarpeta],
	[AEC_Activo],
	[AEC_USU_CreadoPorId],
	[AEC_FechaCreacion]
) VALUES (
	/* [AEC_EstructuraId] */ 5,
	/* [AEC_AEC_EstructuraReferenciaId] */ NULL,
	/* [AEC_Descripcion] */ 'Recibos',
	/* [AEC_NombreCarpeta] */ 'recibos',
	/* [AEC_Activo] */ 1,
	/* [AEC_USU_CreadoPorId] */ 1,
	/* [AEC_FechaCreacion] */ GETDATE()
), (
	/* [AEC_EstructuraId] */ 6,
	/* [AEC_AEC_EstructuraReferenciaId] */ 5,
	/* [AEC_Descripcion] */ 'Evidencia',
	/* [AEC_NombreCarpeta] */ 'evidencia',
	/* [AEC_Activo] */ 1,
	/* [AEC_USU_CreadoPorId] */ 1,
	/* [AEC_FechaCreacion] */ GETDATE()
), (
	/* [AEC_EstructuraId] */ 7,
	/* [AEC_AEC_EstructuraReferenciaId] */ 5,
	/* [AEC_Descripcion] */ 'Facturas',
	/* [AEC_NombreCarpeta] */ 'facturas',
	/* [AEC_Activo] */ 1,
	/* [AEC_USU_CreadoPorId] */ 1,
	/* [AEC_FechaCreacion] */ GETDATE()
)
GO

SET IDENTITY_INSERT [dbo].[ArchivosEstructuraCarpetas] OFF
GO





	


SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON 
GO

INSERT [dbo].[ControlesMaestrosMultiples] (
	[CMM_ControlId],
	[CMM_Activo],
	[CMM_Control],
	[CMM_USU_CreadoPorId],
	[CMM_FechaCreacion],
	[CMM_FechaModificacion],
	[CMM_USU_ModificadoPorId],
	[CMM_Referencia],
	[CMM_Sistema],
	[CMM_Valor]
) VALUES (
	/* [CMM_ControlId] */ 2000101,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OCR_TipoArchivo',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Evidencia'
),(
	/* [CMM_ControlId] */ 2000102,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OCR_TipoArchivo',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Factura'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO