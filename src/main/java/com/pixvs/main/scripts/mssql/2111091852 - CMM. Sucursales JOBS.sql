/**
* Created by Angel Daniel Hernández Silva on 09/11/2021.
* Object:  INSERT INTO [dbo].[ControlesMaestrosMultiples] - CMM_SUC_SucursalJOBSId, CMM_SUC_SucursalJOBSSEMSId
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
	/* [CMM_ControlId] */ 2000630,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_SUC_SucursalJOBSId',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ (SELECT CAST(SUC_SucursalId AS varchar(255)) FROM Sucursales WHERE SUC_CodigoSucursal = 'JOBS')
), (
	/* [CMM_ControlId] */ 2000631,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_SUC_SucursalJOBSSEMSId',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ (SELECT CAST(SUC_SucursalId AS varchar(255)) FROM Sucursales WHERE SUC_CodigoSucursal = 'JBS')
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO