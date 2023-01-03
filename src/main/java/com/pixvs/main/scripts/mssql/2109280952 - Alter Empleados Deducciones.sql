ALTER TABLE EmpleadosDeduccionesPercepciones
ADD EDP_SUC_SucursalId [int] null
GO

---
ALTER TABLE [dbo].[EmpleadosDeduccionesPercepciones]  WITH CHECK ADD  CONSTRAINT [FK_EDP_SUC_SucursalId] FOREIGN KEY([EDP_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO

ALTER TABLE [dbo].[EmpleadosDeduccionesPercepciones] CHECK CONSTRAINT [FK_EDP_SUC_SucursalId]
GO

UPDATE EmpleadosDeduccionesPercepciones
SET EDP_SUC_SucursalId=39
WHERE EDP_Activo = 1
GO