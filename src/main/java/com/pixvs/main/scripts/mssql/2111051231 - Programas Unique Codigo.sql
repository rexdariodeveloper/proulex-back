UPDATE ProgramasGrupos
SET PROGRU_Codigo='JBSP19JSMIINT030703'
WHERE PROGRU_GrupoId=199
GO

UPDATE ProgramasGrupos
SET PROGRU_Codigo='JBSP11JSMIINT030599'
WHERE PROGRU_GrupoId=322
GO

UPDATE ProgramasGrupos
SET PROGRU_Codigo='JBSPPGJSMIINT040599'
WHERE PROGRU_GrupoId=393
GO

UPDATE ProgramasGrupos
SET PROGRU_Codigo='ALCPDUIINT020399'
WHERE PROGRU_GrupoId=445
GO

UPDATE ProgramasGrupos
SET PROGRU_Codigo='JBSP06JSMIINT010402'
WHERE PROGRU_GrupoId=430
GO

UPDATE ProgramasGrupos
SET PROGRU_Codigo='JBSP06JSMIINT010401'
WHERE PROGRU_GrupoId=285
GO

UPDATE ProgramasGrupos
SET PROGRU_Codigo='JBSP06JSMIINT010403'
WHERE PROGRU_GrupoId=290
GO

UPDATE ProgramasGrupos
SET PROGRU_Codigo='JBSP19JSMIINT030702'
WHERE PROGRU_GrupoId=359
GO

UPDATE ProgramasGrupos
SET PROGRU_Codigo='JBSP20JSMIINT030502'
WHERE PROGRU_GrupoId=432
GO

ALTER TABLE ProgramasGrupos
ADD UNIQUE (PROGRU_Codigo)
GO