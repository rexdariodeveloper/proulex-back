ALTER TABLE [dbo].[Inscripciones] ADD [INS_EntregaLibrosPendiente] [bit] NULL
GO

UPDATE Inscripciones SET INS_EntregaLibrosPendiente = CAST((CASE WHEN OV_MPPV_MedioPagoPVId = 5 THEN 1 ELSE 0 END) AS bit)
FROM Inscripciones
INNER JOIN OrdenesVentaDetalles ON OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaId
INNER JOIN OrdenesVenta ON OV_OrdenVentaId = OVD_OV_OrdenVentaId
GO

ALTER TABLE [dbo].[Inscripciones] ALTER COLUMN [INS_EntregaLibrosPendiente] [bit] NOT NULL
GO