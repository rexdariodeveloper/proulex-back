ALTER TABLE Empleados
ADD EMP_PAPC_ProfesorCategoriaId [int] null;

ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_PAPC_ProfesorCategoriaId] FOREIGN KEY([EMP_PAPC_ProfesorCategoriaId])
REFERENCES [dbo].[PAProfesoresCategorias] ([PAPC_ProfesorCategoriaId])
GO

ALTER TABLE Empleados
ADD [EMP_PAI_PaisNacimientoId] [smallint]  NOT NULL DEFAULT(1);

ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_PAI_PaisNacimientoId] FOREIGN KEY([EMP_PAI_PaisNacimientoId])
REFERENCES [dbo].[Paises] ([PAI_PaisId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_PAI_PaisNacimientoId]
GO

ALTER TABLE Empleados ALTER COLUMN [EMP_MON_MonedaId] smallint NULL;
ALTER TABLE Empleados ALTER COLUMN [EMP_SalarioDiario] decimal(10,2) NULL;