/**
* Created by Angel Daniel Hernández Silva on 10/01/2022.
* Object:  Vistas para consumo de catálogos en becas sindicato
*/

ALTER TABLE [dbo].[ProgramasGruposListadoClases] ADD [PROGRULC_FechaDeduccion] [datetime] NULL
GO

UPDATE ProgramasGruposListadoClases SET PROGRULC_FechaDeduccion = PROGRULC_FechaPago
GO