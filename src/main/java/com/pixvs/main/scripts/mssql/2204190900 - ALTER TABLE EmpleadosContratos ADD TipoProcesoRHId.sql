-- EMPCO_CMM_TipoProcesoRHId en EmpleadosContratos
ALTER TABLE [dbo].[EmpleadosContratos]
ADD [EMPCO_CMM_TipoProcesoRHId][INT] DEFAULT 2000900 NOT NULL
GO

ALTER TABLE [dbo].[EmpleadosContratos]  WITH CHECK ADD  CONSTRAINT [FK_EMPCO_CMM_TipoProcesoRHId] FOREIGN KEY([EMPCO_CMM_TipoProcesoRHId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

-- EMPL_Codigo en EmpleadosLaborales
ALTER TABLE [dbo].[EmpleadosLaborales]
ADD [EMPL_Codigo][NVARCHAR](50) DEFAULT '' NOT NULL
GO

