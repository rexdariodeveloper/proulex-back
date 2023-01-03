ALTER TABLE ProgramasIdiomasExamenes
ADD PROGIE_Titulo varchar(50) not null default('');

ALTER TABLE ProgramasIdiomas
DROP COLUMN PROGI_CMM_PlataformaId;

ALTER TABLE ProgramasIdiomas
ADD PROGI_CMM_PlataformaId int not null default(2000384);

ALTER TABLE [dbo].[ProgramasIdiomas]  WITH CHECK ADD  CONSTRAINT [FK_PROGI_CMM_PlataformaId] FOREIGN KEY([PROGI_CMM_PlataformaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProgramasIdiomas] CHECK CONSTRAINT [FK_PROGI_CMM_PlataformaId]
GO

ALTER TABLE ProgramasIdiomasNiveles
ADD PROGIN_Borrar bit not null default(0);

ALTER TABLE ProgramasIdiomasExamenes
ADD PROGIE_Borrar bit not null default(0);