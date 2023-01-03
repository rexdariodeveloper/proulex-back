/* INSERT Idioma Academy */
SET IDENTITY_INSERT [dbo].[ControlesmaestrosMultiples] ON
GO

INSERT INTO ControlesMaestrosMultiples(CMM_ControlId, CMM_Control, CMM_Valor, CMM_Activo, CMM_Referencia, CMM_Sistema, CMM_USU_CreadoPorId, CMM_FechaCreacion)
VALUES(2001050, 'CMM_ART_Idioma', 'Academy', 1, NULL, 1, 1, GETDATE())
GO

SET IDENTITY_INSERT [dbo].[ControlesmaestrosMultiples] OFF
GO