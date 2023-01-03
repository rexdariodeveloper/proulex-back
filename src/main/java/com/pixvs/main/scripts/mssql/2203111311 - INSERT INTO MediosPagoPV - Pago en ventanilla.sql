/**
 * Created by Angel Daniel Hernández Silva on 20/08/2021.
 * Object:  INSERT [dbo].[MediosPagoPV] - "Pago en ventanilla"
 */

SET IDENTITY_INSERT [dbo].[MediosPagoPV] ON
GO

INSERT [dbo].[MediosPagoPV] (
	[MPPV_MedioPagoPVId],
	[MPPV_Codigo],
	[MPPV_Nombre],
	[MPPV_Activo],
	[MPPV_FechaCreacion],
	[MPPV_USU_CreadoPorId]
) VALUES (
	/* [MPPV_MedioPagoPVId] */ 7,
	/* [MPPV_Codigo] */ '01',
	/* [MPPV_Nombre] */ 'Pago en ventanilla',
	/* [MPPV_Activo] */ 1,
	/* [MPPV_FechaCreacion] */ GETDATE(),
	/* [MPPV_USU_CreadoPorId] */ 1
)
GO

SET IDENTITY_INSERT [dbo].[MediosPagoPV] OFF
GO