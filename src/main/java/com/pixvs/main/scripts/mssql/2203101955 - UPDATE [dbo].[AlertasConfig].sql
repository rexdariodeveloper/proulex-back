UPDATE [dbo].[AlertasConfig] SET [ALC_URL_Alerta] = N'/app/programacion-academica/grupos/alerta/autorizacion/' WHERE ALC_AlertaCId = 12
GO

UPDATE [dbo].[AlertasConfig] SET [ALC_URL_Notificacion] = N'/app/programacion-academica/grupos/alerta/notificacion/' WHERE ALC_AlertaCId = 12
GO

UPDATE [dbo].[AlertasConfig] SET [ALC_URL_Documento] = N'/api/v1/pago-proveedores/pdf/' WHERE ALC_AlertaCId = 10
GO