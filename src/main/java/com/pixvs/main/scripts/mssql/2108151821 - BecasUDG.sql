/**
* Created by David Arroyo Sánchez on 14/08/2021.
* Object:  CREATE TABLE [dbo].[BecasUDG]
*/
SET ANSI_NULLS ON
GO
​
SET QUOTED_IDENTIFIER ON
GO
​
CREATE TABLE [dbo].[BecasUDG](
	[BECU_BecaId] [int] IDENTITY(1,1) NOT NULL,
	[BECU_CodigoBeca] [varchar](20) NOT NULL,
	[BECU_CodigoEmpleado] [varchar](20) NOT NULL,
	[BECU_Nombre] [nvarchar](50) NOT NULL,
	[BECU_PrimerApellido] [nvarchar](50) NOT NULL,
	[BECU_SegundoApellido] [nvarchar](50) NULL,
	[BECU_Parentesco] [nvarchar](50) NULL,
	[BECU_Descuento] [decimal](3, 2) NOT NULL,
	[BECU_PROGI_ProgramaIdiomaId] [int] NOT NULL,
	[BECU_Nivel] [tinyint] NOT NULL,
	[BECU_PAMOD_ModalidadId] [int] NOT NULL,
	[BECU_FirmaDigital] [nvarchar](255) NULL,
	[BECU_FechaAlta] [datetime2](7) NOT NULL,
	[BECU_CMM_EstatusId] [int] NOT NULL,
	[BECU_CMM_TipoId] [int] NOT NULL,
 CONSTRAINT [PK_BecasUDG] PRIMARY KEY CLUSTERED 
(
	[BECU_BecaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
​
ALTER TABLE [dbo].[BecasUDG]  WITH CHECK ADD  CONSTRAINT [FK_BecasUDG_ControlesMaestrosMultiples] FOREIGN KEY([BECU_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
​
ALTER TABLE [dbo].[BecasUDG] CHECK CONSTRAINT [FK_BecasUDG_ControlesMaestrosMultiples]
GO
​
ALTER TABLE [dbo].[BecasUDG]  WITH CHECK ADD  CONSTRAINT [FK_BecasUDG_ControlesMaestrosMultiples1] FOREIGN KEY([BECU_CMM_TipoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
​
ALTER TABLE [dbo].[BecasUDG] CHECK CONSTRAINT [FK_BecasUDG_ControlesMaestrosMultiples1]
GO
​
ALTER TABLE [dbo].[BecasUDG]  WITH CHECK ADD  CONSTRAINT [FK_BecasUDG_PAModalidades] FOREIGN KEY([BECU_PAMOD_ModalidadId])
REFERENCES [dbo].[PAModalidades] ([PAMOD_ModalidadId])
GO
​
ALTER TABLE [dbo].[BecasUDG] CHECK CONSTRAINT [FK_BecasUDG_PAModalidades]
GO
​
ALTER TABLE [dbo].[BecasUDG]  WITH CHECK ADD  CONSTRAINT [FK_BecasUDG_ProgramasIdiomas] FOREIGN KEY([BECU_PROGI_ProgramaIdiomaId])
REFERENCES [dbo].[ProgramasIdiomas] ([PROGI_ProgramaIdiomaId])
GO
​
ALTER TABLE [dbo].[BecasUDG] CHECK CONSTRAINT [FK_BecasUDG_ProgramasIdiomas]
GO
​
​
​
SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON 
GO
​
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Control], [CMM_Valor], [CMM_Activo], [CMM_Referencia], [CMM_Sistema], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_USU_ModificadoPorId], [CMM_FechaModificacion], [CMM_Orden], [CMM_ARC_ImagenId], [CMM_CMM_ReferenciaId]) 
VALUES (2000570, N'CMM_BECU_Estatus', N'Pendiente por aplicar', 1, NULL, 1, NULL, GETDATE(), NULL, NULL, NULL, NULL, NULL)
GO
​
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Control], [CMM_Valor], [CMM_Activo], [CMM_Referencia], [CMM_Sistema], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_USU_ModificadoPorId], [CMM_FechaModificacion], [CMM_Orden], [CMM_ARC_ImagenId], [CMM_CMM_ReferenciaId]) 
VALUES (2000571, N'CMM_BECU_Estatus', N'Aplicada', 1, NULL, 1, NULL, GETDATE(), NULL, NULL, NULL, NULL, NULL)
GO
​
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Control], [CMM_Valor], [CMM_Activo], [CMM_Referencia], [CMM_Sistema], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_USU_ModificadoPorId], [CMM_FechaModificacion], [CMM_Orden], [CMM_ARC_ImagenId], [CMM_CMM_ReferenciaId]) 
VALUES (2000572, N'CMM_BECU_Estatus', N'Cancelada', 1, NULL, 1, NULL, GETDATE(), NULL, NULL, NULL, NULL, NULL)
GO
​
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Control], [CMM_Valor], [CMM_Activo], [CMM_Referencia], [CMM_Sistema], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_USU_ModificadoPorId], [CMM_FechaModificacion], [CMM_Orden], [CMM_ARC_ImagenId], [CMM_CMM_ReferenciaId]) 
VALUES (2000580, N'CMM_BECU_Tipo', N'SUTUDG', 1, NULL, 1, NULL, GETDATE(), NULL, NULL, NULL, NULL, NULL)
GO
​
INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Control], [CMM_Valor], [CMM_Activo], [CMM_Referencia], [CMM_Sistema], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_USU_ModificadoPorId], [CMM_FechaModificacion], [CMM_Orden], [CMM_ARC_ImagenId], [CMM_CMM_ReferenciaId]) 
VALUES (2000581, N'CMM_BECU_Tipo', N'STAUDG', 1, NULL, 1, NULL, GETDATE(), NULL, NULL, NULL, NULL, NULL)
GO
​
​
SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

ALTER TABLE [dbo].[Inscripciones] ADD [INS_BECU_BecaId] [int] NULL
GO

ALTER TABLE [dbo].[Inscripciones]  WITH CHECK ADD  CONSTRAINT [FK_INS_BECU_BecaId] FOREIGN KEY([INS_BECU_BecaId])
REFERENCES [dbo].[BecasUDG] ([BECU_BecaId])
GO
​
ALTER TABLE [dbo].[Inscripciones] CHECK CONSTRAINT [FK_INS_BECU_BecaId]
GO