SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER VIEW [dbo].[VW_RPT_ASISTENCIAS_DETALLE]
AS
     SELECT ALU_CodigoAlumnoUDG codigo,
            ALU_PrimerApellido primerApellido,
            ALU_SegundoApellido segundoApellido,
            ALU_Nombre nombre,
            ALU_Correoelectronico correo,
            COALESCE(ALUG_Faltas, 0) AS faltas,
            COALESCE(ALUG_Asistencias, 0) AS asistencias,
            COALESCE(ALUG_MinutosRetardo, 0) AS retardos,
            COALESCE(estatus.CMM_Valor, 'Activo') AS estatus,
            COALESCE(grado.CMM_Valor, '') grado,
            COALESCE(ALU_Grupo, '') grupo,
            COALESCE(turno.CMM_Valor, '') turno,
            PROGRU_Nivel nivel,
            CONCAT_WS(' ', EMP_Nombre, EMP_PrimerApellido, EMP_SegundoApellido) AS profesor,
            SP_Nombre plantel,
            CONCAT_WS(' ', PROG_Codigo, idioma.CMM_Valor, PAMOD_Nombre, FORMAT(PROGRU_Nivel, 'Nivel 00'), FORMAT(PROGRU_Grupo, 'Grupo 00')) codigoGrupo,
            PROGRU_Codigo codigoGrupo2,
            PAMODH_Horario horario,
            COALESCE(prepa.CMM_Valor, cu.CMM_Referencia) escuela,
            COALESCE(ALU_BachilleratoTecnologico, carrera.CMM_Valor) carrera,
            primeroCiclos.codigo cohorte,
            N'Si' regular,
            N'Si' carta,
            ALU_Codigo codigoProulex,
            PROGRU_SUC_SucursalId sedeId,
            SP_SucursalPlantelId plantelId,
            PROGRU_PAC_ProgramacionAcademicaComercialId paId,
            PROGRU_PACIC_CicloId cicloId,
            PROGRU_PAMOD_ModalidadId modalidadId,
            PROGRU_FechaInicio fechaInicio,
            ALU_CMM_PreparatoriaJOBSId prepaId,
            ALU_CMM_CentroUniversitarioJOBSId cuId,
			PROGRU_Activo,
			ALU_Activo
     FROM AlumnosGrupos
          INNER JOIN ProgramasGrupos ON ALUG_PROGRU_GrupoId = PROGRU_GrupoId
          INNER JOIN Alumnos ON ALUG_ALU_AlumnoId = ALU_AlumnoId
          INNER JOIN ControlesMaestrosMultiples estatus ON ALUG_CMM_EstatusId = estatus.CMM_ControlId
          INNER JOIN Inscripciones ON ALUG_ALU_AlumnoId = INS_ALU_AlumnoId AND ALUG_PROGRU_GrupoId = INS_PROGRU_GrupoId AND INS_CMM_EstatusId != 2000512 -- Cancelada
          INNER JOIN ProgramasIdiomas ON PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
          INNER JOIN ControlesMaestrosMultiples idioma ON PROGI_CMM_Idioma = idioma.CMM_ControlId
          INNER JOIN Programas ON PROGI_PROG_ProgramaId = PROG_ProgramaId
          INNER JOIN PAModalidades ON PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
          INNER JOIN PAModalidadesHorarios ON PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
          LEFT JOIN ControlesMaestrosMultiples grado ON ALU_CMM_GradoId = grado.CMM_ControlId
          LEFT JOIN ControlesMaestrosMultiples turno ON ALU_CMM_TurnoId = turno.CMM_ControlId
          LEFT JOIN ControlesMaestrosMultiples prepa ON ALU_CMM_PreparatoriaJOBSId = prepa.CMM_ControlId
          LEFT JOIN ControlesMaestrosMultiples cu ON ALU_CMM_CentroUniversitarioJOBSId = cu.CMM_ControlId
          LEFT JOIN ControlesMaestrosMultiples carrera ON ALU_CMM_CarreraJOBSId = carrera.CMM_ControlId
          LEFT JOIN SucursalesPlanteles ON SP_SucursalPlantelId = PROGRU_SP_SucursalPlantelId
          LEFT JOIN Empleados ON PROGRU_EMP_EmpleadoId = EMP_EmpleadoId
          LEFT JOIN
		  (
				SELECT ins.INS_ALU_AlumnoId alumnoId,
					   p.PROGRU_GrupoId grupoId,
					   pa.PACIC_Codigo codigo
				FROM
				(
					SELECT i.INS_ALU_AlumnoId,
						   i.INS_PROGRU_GrupoId,
						   ROW_NUMBER() OVER(PARTITION BY i.INS_ALU_AlumnoId ORDER BY i.INS_FechaCreacion ASC) AS rank
					FROM Inscripciones i
				) ins
				INNER JOIN ProgramasGrupos p ON ins.INS_PROGRU_GrupoId = p.PROGRU_GrupoId
				LEFT JOIN PACiclos pa ON p.PROGRU_PACIC_CicloId = pa.PACIC_CicloId
				WHERE ins.rank = 1
		  ) primeroCiclos ON alumnoId = ALU_AlumnoId
GO