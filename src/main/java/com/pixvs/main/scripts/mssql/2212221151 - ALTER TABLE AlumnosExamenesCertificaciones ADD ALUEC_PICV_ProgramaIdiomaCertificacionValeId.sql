
-- =============================================
-- Author:		Ángeel Daniel Hernández Silva
-- Create date: 21/12/2022
-- Modify date:
-- Description:	Agregar columna ALUEC_PICV_ProgramaIdiomaCertificacionValeId a la tabla AlumnosExamenesCertificaciones
-- Version 1.0.0
-- =============================================

ALTER TABLE [dbo].[AlumnosExamenesCertificaciones] ADD [ALUEC_PICV_ProgramaIdiomaCertificacionValeId] int NULL
GO

ALTER TABLE [dbo].[AlumnosExamenesCertificaciones]  WITH CHECK ADD  CONSTRAINT [FK_ALUEC_PICV_ProgramaIdiomaCertificacionValeId] FOREIGN KEY([ALUEC_PICV_ProgramaIdiomaCertificacionValeId])
REFERENCES [dbo].[ProgramasIdiomasCertificacionesVales] ([PICV_ProgramaIdiomaCertificacionValeId])
GO

ALTER TABLE [dbo].[AlumnosExamenesCertificaciones] CHECK CONSTRAINT [FK_ALUEC_PICV_ProgramaIdiomaCertificacionValeId]
GO