/* Actualización de estatus de AlumnosGrupos */
UPDATE [dbo].[ControlesMaestrosMultiples] SET [CMM_Valor] = N'Registrado' WHERE [CMM_ControlId] = 2000670
UPDATE [dbo].[ControlesMaestrosMultiples] SET [CMM_Valor] = N'Activo' WHERE [CMM_ControlId] = 2000671
UPDATE [dbo].[ControlesMaestrosMultiples] SET [CMM_Valor] = N'En riesgo' WHERE [CMM_ControlId] = 2000672
UPDATE [dbo].[ControlesMaestrosMultiples] SET [CMM_Valor] = N'Sin derecho' WHERE [CMM_ControlId] = 2000673
UPDATE [dbo].[ControlesMaestrosMultiples] SET [CMM_Valor] = N'Desertor' WHERE [CMM_ControlId] = 2000674
UPDATE [dbo].[ControlesMaestrosMultiples] SET [CMM_Valor] = N'Aprobado' WHERE [CMM_ControlId] = 2000675
UPDATE [dbo].[ControlesMaestrosMultiples] SET [CMM_Valor] = N'Reprobado' WHERE [CMM_ControlId] = 2000676
UPDATE [dbo].[ControlesMaestrosMultiples] SET [CMM_Valor] = N'Baja' WHERE [CMM_ControlId] = 2000677
GO

-- Actualizar todos los estatus por evaluacion de asistencias
UPDATE 
	[dbo].[AlumnosGrupos] 
set 
	[ALUG_CMM_EstatusId] = (SELECT [dbo].[fn_getEstatusAlumno]([ALUG_ALU_AlumnoId], [ALUG_PROGRU_GrupoId])) 
FROM 
	[dbo].[AlumnosGrupos] 
	INNER JOIN [dbo].[ProgramasGrupos] ON [ALUG_PROGRU_GrupoId] = [PROGRU_GrupoId]
 WHERE [PROGRU_FechaFin] < GETDATE()
 GO

-- Solo si son evaluables, evaluar calificacion
UPDATE
	[dbo].[AlumnosGrupos]
SET
	[ALUG_CMM_EstatusId] = (CASE WHEN COALESCE(ALUG_CalificacionFinal,0) < PROGRU_CalificacionMinima THEN 2000676 ELSE 2000675 END)
FROM
	[dbo].[AlumnosGrupos] 
	INNER JOIN [dbo].[ProgramasGrupos] ON [ALUG_PROGRU_GrupoId] = [PROGRU_GrupoId]
WHERE
	[PROGRU_FechaFin] < GETDATE()
	AND [ALUG_CMM_EstatusId] IN (2000670, 2000671, 2000672)
GO

-- Actualizar los estatus de los grupos actuales
UPDATE 
	[dbo].[AlumnosGrupos] 
set 
	[ALUG_CMM_EstatusId] = (SELECT [dbo].[fn_getEstatusAlumno]([ALUG_ALU_AlumnoId], [ALUG_PROGRU_GrupoId])) 
FROM 
	[dbo].[AlumnosGrupos] 
	INNER JOIN [dbo].[ProgramasGrupos] ON [ALUG_PROGRU_GrupoId] = [PROGRU_GrupoId]
 WHERE [PROGRU_FechaFin] >= GETDATE()
 GO

 -- Borrar los estatus restantes
 DELETE [dbo].[ControlesMaestrosMultiples] WHERE [CMM_ControlId] = 2000678
 GO