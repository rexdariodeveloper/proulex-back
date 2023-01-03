SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER VIEW [dbo].[VW_RPT_ASISTENCIAS]
AS
   SELECT 
      [ALU_Codigo] codigo,
      [ALU_CodigoAlumnoUDG] codigoAlumno,
      [ALU_PrimerApellido] primerApellido,
      [ALU_SegundoApellido] segundoApellido,
      [ALU_Nombre] nombre,
      [ALUG_Faltas] faltas,
      [ALUG_Asistencias] asistencias,
      [CMM_Valor] estatus,
      [PROGRU_Codigo] codigoGrupo,
      [PROGRU_SUC_SucursalId] sedeId,
      [PROGRU_SP_SucursalPlantelId] plantelId,
      [PROGRU_PAC_ProgramacionAcademicaComercialId] paId,
      [PROGRU_PACIC_CicloId] cicloId,
      [PROGRU_PAMOD_ModalidadId] modalidadId,
      CAST([PROGRU_FechaInicio] AS DATE) fechaInicio,
      ALU_CMM_PreparatoriaJOBSId prepaId,
      ALU_CMM_CentroUniversitarioJOBSId cuId
   FROM 
      [AlumnosGrupos]
      INNER JOIN [ProgramasGrupos] ON [ALUG_PROGRU_GrupoId] = [PROGRU_GrupoId]
      INNER JOIN [Alumnos] ON [ALUG_ALU_AlumnoId] = [ALU_AlumnoId]
      INNER JOIN [ControlesMaestrosMultiples] ON [ALUG_CMM_EstatusId] = [CMM_ControlId]
      INNER JOIN [Inscripciones] ON [ALUG_ALU_AlumnoId] = [INS_ALU_AlumnoId] AND [ALUG_PROGRU_GrupoId] = [INS_PROGRU_GrupoId]
   WHERE
      [INS_CMM_EstatusId] <> 2000512
GO