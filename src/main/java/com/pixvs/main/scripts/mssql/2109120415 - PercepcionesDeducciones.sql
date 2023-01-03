--------------------- Listados de Precios ---------------------------------------------
CREATE TABLE [dbo].[DeduccionesPercepciones](
	[DEDPER_DeduccionPercepcionId] [int] IDENTITY(1,1) NOT NULL,
	[DEDPER_Codigo] [nvarchar](1) NOT NULL,
	[DEDPER_CMM_TipoId] [int] NOT NULL,
	[DEDPER_Concepto] [nvarchar](50) NOT NULL,
	[DEDPER_TAB_TabuladorId] [int] NOT NULL,
	[DEDPER_Porcentaje] [decimal](10,2) NOT NULL,
	[DEDPER_Activo] [bit] NOT NULL,
	[DEDPER_FechaCreacion] [datetime2](7) NOT NULL,
	[DEDPER_USU_CreadoPorId] [int] NOT NULL,
	[DEDPER_FechaUltimaModificacion] [datetime2](7) NULL,
	[DEDPER_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[DEDPER_DeduccionPercepcionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[DeduccionesPercepciones] ADD  CONSTRAINT [DF_DEDPER_FechaCreacion]  DEFAULT (getdate()) FOR [DEDPER_FechaCreacion]
GO

ALTER TABLE [dbo].[DeduccionesPercepciones]  WITH CHECK ADD  CONSTRAINT [FK_DEDPER_CMM_TipoId] FOREIGN KEY([DEDPER_CMM_TipoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[DeduccionesPercepciones] CHECK CONSTRAINT [FK_DEDPER_CMM_TipoId]
GO

---
ALTER TABLE [dbo].[DeduccionesPercepciones]  WITH CHECK ADD  CONSTRAINT [FK_DEDPER_TAB_TabuladorId] FOREIGN KEY([DEDPER_TAB_TabuladorId])
REFERENCES [dbo].[Tabuladores] ([TAB_TabuladorId])
GO

ALTER TABLE [dbo].[DeduccionesPercepciones] CHECK CONSTRAINT [FK_DEDPER_TAB_TabuladorId]
GO

---
ALTER TABLE [dbo].[DeduccionesPercepciones]  WITH CHECK ADD  CONSTRAINT [FK_DEDPER_USU_CreadoPorId] FOREIGN KEY([DEDPER_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[DeduccionesPercepciones] CHECK CONSTRAINT [FK_DEDPER_USU_CreadoPorId]
GO
---
ALTER TABLE [dbo].[DeduccionesPercepciones]  WITH CHECK ADD  CONSTRAINT [FK_DEDPER_USU_ModificadoPorId] FOREIGN KEY([DEDPER_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[DeduccionesPercepciones] CHECK CONSTRAINT [FK_DEDPER_USU_ModificadoPorId]
GO

/*****************************************/
/***** CMM - PercepcionesDeducciones *****/
/*****************************************/

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
	/* [CMM_ControlId] */ 2000605,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_DEDPER_Tipo',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Deducción'
),(
	/* [CMM_ControlId] */ 2000606,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_DEDPER_Tipo',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Percepción'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO