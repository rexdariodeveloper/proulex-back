-- ==========================================================
-- Author: Rene Carrillo
-- Create date: 05/04/2022
-- Modified date:
-- Description: Actualizamos los campos y	agregar un campo en la tabla de Empleados
-- ==========================================================
ALTER TABLE [Empleados] ALTER COLUMN [EMP_DEP_DepartamentoId] [int] NULL
GO

ALTER TABLE [Empleados] ALTER COLUMN [EMP_FechaAlta] [date] NULL
GO

ALTER TABLE [Empleados] ALTER COLUMN [EMP_CMM_TipoEmpleadoId] [int] NULL
GO

ALTER TABLE [Empleados] ALTER COLUMN [EMP_SUC_SucursalId] [int] NULL
GO

ALTER TABLE [dbo].[Empleados] ADD
	[EMP_EMPCO_EmpleadoContratoId] [int] NULL
GO

ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_EMPCO_EmpleadoContratoId] FOREIGN KEY([EMP_EMPCO_EmpleadoContratoId])
REFERENCES [dbo].[EmpleadosContratos] ([EMPCO_EmpleadoContratoId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_EMPCO_EmpleadoContratoId]
GO