/**
* Created by Angel Daniel Hernández Silva on 07/11/2022.
*/

-- OVD_PrecioSinConvertir

ALTER TABLE [dbo].[OrdenesVentaDetalles] ADD [OVD_PrecioSinConvertir] decimal(10,2) NULL
GO

UPDATE OrdenesVentaDetalles SET OVD_PrecioSinConvertir = OVD_Precio
GO

ALTER TABLE [dbo].[OrdenesVentaDetalles] ALTER COLUMN [OVD_PrecioSinConvertir] decimal(10,2) NOT NULL
GO

-- OVD_DescuentoSinConvertir

ALTER TABLE [dbo].[OrdenesVentaDetalles] ADD [OVD_DescuentoSinConvertir] decimal(10,2) NULL
GO

UPDATE OrdenesVentaDetalles SET OVD_DescuentoSinConvertir = OVD_Descuento
GO

-- OV_TipoCambio

ALTER TABLE [dbo].[OrdenesVenta] ADD [OV_TipoCambio] decimal(12,6) NULL
GO

UPDATE OrdenesVenta SET OV_TipoCambio = 1
GO

ALTER TABLE [dbo].[OrdenesVenta] ALTER COLUMN [OV_TipoCambio] decimal(12,6) NOT NULL
GO

-- OV_MON_MonedaSinConvertirId

ALTER TABLE [dbo].[OrdenesVenta] ADD [OV_MON_MonedaSinConvertirId] int NULL
GO

UPDATE OrdenesVenta SET OV_MON_MonedaSinConvertirId = OV_MON_MonedaId
GO

ALTER TABLE [dbo].[OrdenesVenta] ALTER COLUMN [OV_MON_MonedaSinConvertirId] int NOT NULL
GO