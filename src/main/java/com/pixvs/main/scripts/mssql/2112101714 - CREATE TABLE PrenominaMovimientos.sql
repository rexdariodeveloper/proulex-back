/**
 * Created by Angel Daniel Hernández Silva on 06/12/2021.
 * Object:  Table [dbo].[PrenominaMovimientos]
 */

CREATE TABLE [dbo].[PrenominaMovimientos](
    [PRENOM_PrenominaMovimientoId] [int] IDENTITY(1,1) NOT NULL,
    [PRENOM_EMP_EmpleadoId] [int] NOT NULL,
    [PRENOM_FechaInicioPeriodo] [date] NOT NULL,
    [PRENOM_FechaFinPeriodo] [date] NOT NULL,
    [PRENOM_FechaInicioQuincena] [date] NOT NULL,
    [PRENOM_FechaFinQuincena] [date] NOT NULL,
    [PRENOM_HorasDeduccion] [decimal](10,2) NULL,
    [PRENOM_HorasPercepcion] [decimal](10,2) NULL,
    [PRENOM_SueldoPorHora] [decimal](28,2) NULL,
    [PRENOM_CMM_TipoMovimientoId] [int] NOT NULL,
    [PRENOM_PRENOM_MovimientoReferenciaId] [int] NULL,
    [PRENOM_FechaCorte] [datetime2](7) NOT NULL,
    CONSTRAINT [PK_PrenominaMovimientos] PRIMARY KEY CLUSTERED (
        [PRENOM_PrenominaMovimientoId] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[PrenominaMovimientos]  WITH CHECK ADD  CONSTRAINT [FK_PRENOM_EMP_EmpleadoId] FOREIGN KEY([PRENOM_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[PrenominaMovimientos] CHECK CONSTRAINT [FK_PRENOM_EMP_EmpleadoId]
GO

ALTER TABLE [dbo].[PrenominaMovimientos]  WITH CHECK ADD  CONSTRAINT [FK_PRENOM_CMM_TipoMovimientoId] FOREIGN KEY([PRENOM_CMM_TipoMovimientoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[PrenominaMovimientos] CHECK CONSTRAINT [FK_PRENOM_CMM_TipoMovimientoId]
GO

ALTER TABLE [dbo].[PrenominaMovimientos]  WITH CHECK ADD  CONSTRAINT [FK_PRENOM_PRENOM_MovimientoReferenciaId] FOREIGN KEY([PRENOM_PRENOM_MovimientoReferenciaId])
REFERENCES [dbo].[PrenominaMovimientos] ([PRENOM_PrenominaMovimientoId])
GO

ALTER TABLE [dbo].[PrenominaMovimientos] CHECK CONSTRAINT [FK_PRENOM_PRENOM_MovimientoReferenciaId]
GO

ALTER TABLE [dbo].[PrenominaMovimientos] WITH CHECK ADD CONSTRAINT [CHK_PRENOM_Monto] CHECK (
    (
        [PRENOM_HorasDeduccion] IS NOT NULL
        AND [PRENOM_HorasPercepcion] IS NULL
    ) OR (
        [PRENOM_HorasDeduccion] IS NULL
        AND [PRENOM_HorasPercepcion] IS NOT NULL
    )
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
	[CMM_FechaModificacion],
	[CMM_USU_ModificadoPorId],
	[CMM_Referencia],
	[CMM_Sistema],
	[CMM_Valor]
) VALUES (
	/* [CMM_ControlId] */ 2000690,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_PRENOM_TipoMovimiento',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pago a profesor titular'
),(
	/* [CMM_ControlId] */ 2000691,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_PRENOM_TipoMovimiento',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pago por sustitución'
),(
	/* [CMM_ControlId] */ 2000692,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_PRENOM_TipoMovimiento',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Deducción manual'
),(
	/* [CMM_ControlId] */ 2000693,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_PRENOM_TipoMovimiento',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Percepción manual'
),(
	/* [CMM_ControlId] */ 2000694,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_PRENOM_TipoMovimiento',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pago por día festivo'
),(
	/* [CMM_ControlId] */ 2000695,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_PRENOM_TipoMovimiento',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Deducción por sustitución'
),(
	/* [CMM_ControlId] */ 2000696,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_PRENOM_TipoMovimiento',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Deducción por cambio de profesor titular'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO