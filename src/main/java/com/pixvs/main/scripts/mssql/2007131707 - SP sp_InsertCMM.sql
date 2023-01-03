SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_InsertCMM] @control varchar(255), @valor varchar(255), @usuarioId int
AS
BEGIN

	DECLARE @siguienteCMMId int;

	SELECT @siguienteCMMId = [dbo].[getSiguienteCMMId](0)

	SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON
	
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
		/* [CMM_ControlId] */ @siguienteCMMId,
		/* [CMM_Activo] */ 1,
		/* [CMM_Control] */ @control,
		/* [CMM_USU_CreadoPorId] */ @usuarioId,
		/* [CMM_FechaCreacion] */ GETDATE(),
		/* [CMM_FechaModificacion] */ NULL,
		/* [CMM_USU_ModificadoPorId] */ NULL,
		/* [CMM_Referencia] */ NULL,
		/* [CMM_Sistema] */ 0,
		/* [CMM_Valor] */ @valor
	)
	
	SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF

END
GO