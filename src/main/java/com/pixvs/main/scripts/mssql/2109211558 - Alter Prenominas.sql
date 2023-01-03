ALTER TABLE Prenominas
ADD PRENO_EMP_EmpleadoId [INT] null
GO

---
ALTER TABLE [dbo].[Prenominas]  WITH CHECK ADD  CONSTRAINT [FK_PRENO_EMP_EmpleadoId] FOREIGN KEY([PRENO_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[Prenominas] CHECK CONSTRAINT [FK_PRENO_EMP_EmpleadoId]
GO