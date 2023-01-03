Delete from ProgramasIdiomasCertificacion where PROGIC_ProgramaIdiomaCertificacionId is not null;
ALTER TABLE ProgramasIdiomasCertificacion DROP COLUMN PROGIC_Certificacion;
ALTER TABLE ProgramasIdiomasCertificacion ADD PROGIC_ART_ArticuloId [int] not null;
ALTER TABLE [dbo].[ProgramasIdiomasCertificacion]  WITH CHECK ADD  CONSTRAINT [FK_PROGIC_ART_ArticuloId] FOREIGN KEY([PROGIC_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO