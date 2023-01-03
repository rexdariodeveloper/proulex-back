/**
* Created by Angel Daniel Hernández Silva on 10/03/2022.
* Object: ALTER TABLE [dbo].[OrdenesVenta] ADD [OV_FechaPago] [date] NULL
*/

ALTER TABLE [dbo].[OrdenesVenta] ADD [OV_FechaPago] [date] NULL
GO

UPDATE OrdenesVenta SET OV_FechaPago = OV_FechaModificacion WHERE OV_CMM_EstatusId = 2000508
GO