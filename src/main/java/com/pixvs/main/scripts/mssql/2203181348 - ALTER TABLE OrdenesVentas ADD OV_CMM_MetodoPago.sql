/**
* Created by Angel Daniel Hernández Silva on 17/03/2022.
* Object: Corregir OVs
*/

ALTER TABLE [dbo].[OrdenesVenta] ADD [OV_CMM_MetodoPago] int NULL
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
	/* [CMM_ControlId] */ 2000710,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OV_MetodoPago',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'PUE'
),(
	/* [CMM_ControlId] */ 2000711,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_OV_MetodoPago',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'PPD'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

UPDATE OrdenesVenta SET OV_CMM_MetodoPago = 2000710 WHERE OV_CMM_EstatusId = 2000508
GO