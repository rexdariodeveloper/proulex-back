/**
 * Created by Angel Daniel Hernández Silva on 02/09/2022.
 */

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
	/* [CMM_ControlId] */ 2000439,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_IM_TipoMovimiento',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Cancelación de nota de venta'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO