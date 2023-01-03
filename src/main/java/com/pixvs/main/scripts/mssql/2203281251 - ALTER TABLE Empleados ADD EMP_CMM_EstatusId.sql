/**
* Created by Rene Carrillo on 22/03/2022.
*/

ALTER TABLE [dbo].[empleados] DROP CONSTRAINT [FK_EMP_CMM_TipoContratoId]
GO

ALTER TABLE [dbo].[empleados] DROP COLUMN [EMP_CMM_TipoContratoId]
GO

ALTER TABLE [dbo].[empleados] DROP CONSTRAINT [FK_EMP_CMM_PuestoId]
GO

ALTER TABLE [dbo].[empleados] DROP COLUMN [EMP_CMM_PuestoId]
GO

ALTER TABLE [dbo].[empleados] DROP COLUMN [EMP_SalarioDiario]
GO

ALTER TABLE [dbo].[empleados] DROP CONSTRAINT [FK_EMP_MON_MonedaId]
GO

ALTER TABLE [dbo].[empleados] DROP COLUMN [EMP_MON_MonedaId]
GO

ALTER TABLE [dbo].[Empleados] DROP COLUMN IF EXISTS [EMP_Activo]
GO

ALTER TABLE [dbo].[Empleados] ADD
	EMP_CMM_EstatusId INT NOT NULL DEFAULT 2000950
GO



ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_CMM_EstatusId] FOREIGN KEY([EMP_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_CMM_EstatusId]
GO