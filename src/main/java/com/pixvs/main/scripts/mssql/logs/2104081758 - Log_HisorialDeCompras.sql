SET IDENTITY_INSERT [dbo].[LogsTipos] ON
GO
INSERT [dbo].[LogsTipos] (
[LOGT_LogTipoId],
[LOGT_Nombre],
[LOGT_Icono],
[LOGT_ColorFondo]
) VALUES
-------------------
-- Historial de Compra --
-------------------
(
35,
N'Relacionado',
N'compare_arrows',
N'#007bff'
),(
36,
N'Relación borrada',
N'swap_horiz',
N'#dc3545'
),(
37,
N'Programado',
N'alarm_on',
N'#007bff'
),(
38,
N'Programación cancelado',
N'alarm_off',
N'#dc3545'
),(
39,
N'Pago rechazado',
N'money_off',
N'#dc3545'
),(
40,
N'Pago aceptado',
N'monetization_on',
N'#007bff'
)
GO
SET IDENTITY_INSERT [dbo].[LogsTipos] OFF
GO