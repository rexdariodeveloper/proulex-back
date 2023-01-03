USE [PixvsLog]
GO


SET IDENTITY_INSERT [dbo].[LogsProcesos] ON 
GO
INSERT [dbo].[LogsProcesos] (
	[LOGP_LogProcesoId],
	[LOGP_Nombre]
) VALUES (
	2,
	N'Requisiciones'
),(
	3,
	N'OrdenesCompra'
)
GO
SET IDENTITY_INSERT [dbo].[LogsProcesos] OFF
GO

SET IDENTITY_INSERT [dbo].[LogsTipos] ON 
GO
INSERT [dbo].[LogsTipos] (
	[LOGT_LogTipoId],
	[LOGT_Nombre],
	[LOGT_Icono],
	[LOGT_ColorFondo]
) VALUES
-------------------
-- Requisiciones --
-------------------
(
	9,
	N'Convertido',
	N'transform',
	N'#007bff'
),(
	10,
	N'Autorizado',
	N'playlist_add_check',
	N'#007bff'
),(
	11,
	N'Proceso alertas',
	N'add_alert',
	N'#007bff'
),(
	12,
	N'En revisión',
	N'notification_important',
	N'#007bff'
),(
	13,
	N'Enviado',
	N'send',
	N'#007bff'
),
-------------------
-- OrdenesCompra --
-------------------
(
	14,
	N'Cancelado',
	N'block',
	N'#dc3545'
),(
	15,
	N'Cerrado',
	N'lock',
	N'#dc3545'
),(
	16,
	N'Facturado',
	N'receipt',
	N'#007bff'
),(
	17,
	N'Recibido',
	N'input',
	N'#007bff'
),(
	18,
	N'Recibido parcial',
	N'arrow_right_alt',
	N'#007bff'
)
GO
SET IDENTITY_INSERT [dbo].[LogsTipos] OFF
GO