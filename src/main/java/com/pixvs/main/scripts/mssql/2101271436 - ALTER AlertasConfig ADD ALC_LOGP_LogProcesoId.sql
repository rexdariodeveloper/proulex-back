ALTER TABLE [dbo].[AlertasConfig] ADD [ALC_LOGP_LogProcesoId] [int]
GO


UPDATE AlertasConfig SET ALC_LOGP_LogProcesoId = 3 WHERE ALC_AlertaCId = 3
GO

UPDATE AlertasConfig SET ALC_LOGP_LogProcesoId = 2 WHERE ALC_AlertaCId = 4
GO

UPDATE AlertasConfig SET ALC_LOGP_LogProcesoId = 4 WHERE ALC_AlertaCId = 7
GO

UPDATE AlertasConfig SET ALC_LOGP_LogProcesoId = 5 WHERE ALC_AlertaCId IN (8,9)
GO

UPDATE AlertasConfig SET ALC_CMM_EstadoAutorizado = 2000261 WHERE ALC_AlertaCId = 9
GO

UPDATE AlertasConfig SET ALC_CMM_EstadoAutorizado = 2000161 WHERE ALC_AlertaCId = 7
GO

UPDATE AlertasConfig SET ALC_CMM_EstadoAutorizado = 2000196 WHERE ALC_AlertaCId = 4
GO

SET IDENTITY_INSERT ControlesMaestrosMultiples ON
INSERT INTO ControlesMaestrosMultiples
	(CMM_ControlId, CMM_Control, CMM_Valor, CMM_Activo, CMM_Referencia, CMM_Sistema, CMM_USU_CreadoPorId, CMM_FechaCreacion, CMM_USU_ModificadoPorId, CMM_FechaModificacion)
VALUES
	(2000165, 'CMM_CXPS_EstadoSolicitudPago', 'Por autorizar', 1, NULL, 1, NULL, GETDATE(), NULL, NULL),
	(2000166, 'CMM_CXPS_EstadoSolicitudPago', 'Rechazada', 1, NULL, 1, NULL, GETDATE(), NULL, NULL)
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO

UPDATE AlertasConfig SET
	ALC_AplicaSucursales = 0,
	ALC_CMM_EstadoAutorizado = 2000161,
	ALC_CMM_EstadoEnProceso = 2000165,
	ALC_CMM_EstadoRechazado = 2000166
WHERE ALC_AlertaCId = 10
GO