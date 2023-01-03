-- ============================================= 
-- Author:		Angel Daniel Hernández Silva
-- Create date: 2021/08/11
-- Description:	ALTER sp_InsertCMM
-- --------------------------------------------- 

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER PROCEDURE [dbo].[sp_InsertCMM] @control varchar(255), @valor varchar(255), @referencia varchar(255), @referenciaId int, @usuarioId int
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
		[CMM_CMM_ReferenciaId],
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
		/* [CMM_Referencia] */ @referencia,
		/* [CMM_CMM_ReferenciaId] */ @referenciaId,
		/* [CMM_Sistema] */ 0,
		/* [CMM_Valor] */ @valor
	)
	
	SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF

END
GO