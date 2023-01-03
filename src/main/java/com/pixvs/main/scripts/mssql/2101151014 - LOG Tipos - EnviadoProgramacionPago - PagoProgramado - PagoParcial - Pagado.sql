USE [PixvsLog]
GO

SET IDENTITY_INSERT [dbo].[LogsTipos] ON 
GO
INSERT [dbo].[LogsTipos] (
    [LOGT_LogTipoId],
    [LOGT_Nombre],
    [LOGT_Icono],
    [LOGT_ColorFondo]
) VALUES
(
    29,
    N'Enviado a programación de pago',
    N'send',
    N'#007bff'
),(
    30,
    N'Pago programado',
    N'av_timer',
    N'#007bff'
),(
    31,
    N'Pago parcial',
    N'attach_money',
    N'#007bff'
),(
    32,
    N'Pagado',
    N'attach_money',
    N'#007bff'
)
GO
SET IDENTITY_INSERT [dbo].[LogsTipos] OFF
GO