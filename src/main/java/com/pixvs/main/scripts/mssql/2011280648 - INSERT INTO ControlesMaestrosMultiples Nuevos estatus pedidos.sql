SET IDENTITY_INSERT ControlesMaestrosMultiples ON
INSERT INTO ControlesMaestrosMultiples
	(CMM_ControlId, CMM_Control, CMM_Valor, CMM_Activo, CMM_Referencia, CMM_Sistema, CMM_USU_CreadoPorId, CMM_FechaCreacion, CMM_USU_ModificadoPorId, CMM_FechaModificacion)
VALUES
	(2000260, 'CMM_PED_EstatusPedido', 'Guardado', 1, NULL, 1, NULL, GETDATE(), NULL, NULL),
	(2000261, 'CMM_PED_EstatusPedido', 'Por surtir', 1, NULL, 1, NULL, GETDATE(), NULL, NULL),
	(2000262, 'CMM_PED_EstatusPedido', 'Surtido parcial', 1, NULL, 1, NULL, GETDATE(), NULL, NULL),
	(2000263, 'CMM_PED_EstatusPedido', 'Surtido', 1, NULL, 1, NULL, GETDATE(), NULL, NULL),
	(2000264, 'CMM_PED_EstatusPedido', 'Completo', 1, NULL, 1, NULL, GETDATE(), NULL, NULL),
	(2000265, 'CMM_PED_EstatusPedido', 'Cerrado', 1, NULL, 1, NULL, GETDATE(), NULL, NULL),
	(2000266, 'CMM_PED_EstatusPedido', 'Cancelado', 1, NULL, 1, NULL, GETDATE(), NULL, NULL),
	(2000267, 'CMM_PED_EstatusPedido', 'En proceso', 1, NULL, 1, NULL, GETDATE(), NULL, NULL),
	(2000268, 'CMM_PED_EstatusPedido', 'En revisión', 1, NULL, 1, NULL, GETDATE(), NULL, NULL),
	(2000269, 'CMM_PED_EstatusPedido', 'Rechazado', 1, NULL, 1, NULL, GETDATE(), NULL, NULL),
	(2000270, 'CMM_PED_EstatusPedido', 'Borrado', 1, NULL, 1, NULL, GETDATE(), NULL, NULL)
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO