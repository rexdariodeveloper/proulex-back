/**
    Agregamos un nuevo campo a configuracion etapa para enviar correo electronico
*/
ALTER TABLE AlertasConfigEtapa ADD ACE_NotificacionCorreo BIT DEFAULT 0;

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON
GO
    INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor])
    VALUES (1000085, 1, N'CMM_ALE_MostrarCriteriosEconomicos', NULL, CAST(N'2020-03-29T05:46:26.1833333' AS DateTime2), NULL, NULL, NULL, 1, N'1')
    GO
    INSERT [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId], [CMM_Activo], [CMM_Control], [CMM_USU_CreadoPorId], [CMM_FechaCreacion], [CMM_FechaModificacion], [CMM_USU_ModificadoPorId], [CMM_Referencia], [CMM_Sistema], [CMM_Valor])
    VALUES (1000086, 1, N'CMM_ALE_MostrarUsuarioNotificacion', NULL, CAST(N'2020-03-29T05:46:26.1833333' AS DateTime2), NULL, NULL, NULL, 1, N'1')
    GO
SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO