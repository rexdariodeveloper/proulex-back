SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER VIEW [dbo].[VW_LISTADO_GRUPOS_CAPTURA]
AS
     SELECT PROGRU_GrupoId id,
            PROGRU_Codigo codigo,
            COALESCE(PAC_Codigo, PACIC_Codigo) programacion,
            SUC_Nombre sucursal,
            SP_Nombre plantel,
            CONCAT_WS(' ', t.EMP_Nombre, t.EMP_PrimerApellido, t.EMP_SegundoApellido) nombreProfesor,
            CONCAT_WS(' ', s.EMP_Nombre, s.EMP_PrimerApellido, s.EMP_SegundoApellido) nombreSuplente,
            PAMODH_Horario horario,
            PROGRU_FechaInicio fechaInicio,
            PROGRU_FechaFin fechaFin,
            PROG_Codigo programaCodigo,
            idioma.CMM_Valor idioma,
            PAMOD_Nombre modalidad,
            FORMAT(PROGRU_Nivel, '00') nivel,
            FORMAT(PROGRU_Grupo, '00') grupo,
            PROGRU_Cupo cupo,
            COALESCE(inscripciones.inscritos, 0) inscritos,
            PAMOD_Color colorModalidad,
            CASE WHEN idioma.CMM_Color IS NULL OR SUBSTRING(idioma.CMM_Color, 1, 7) = '' THEN '#4D6EB5' ELSE SUBSTRING(idioma.CMM_Color, 1, 7) END colorPrimario,
            CASE WHEN idioma.CMM_Color IS NULL OR SUBSTRING(idioma.CMM_Color, 9, 16) = '' THEN '#99CEB0' ELSE SUBSTRING(idioma.CMM_Color, 9, 16) END colorSecundario,
            CASE WHEN PROGRU_CMM_EstatusId = 2000620 THEN 1 ELSE 0 END activo,
            SUC_SucursalId sucursalId,
            t.EMP_EmpleadoId profesorId,
            s.EMP_EmpleadoId suplenteId,
            PROGRU_CMM_EstatusId estatusId,
            DATEADD(DAY,(select COALESCE(CMA_Valor,0) from ControlesMaestros where CMA_Nombre = N'CM_SUMA_DIAS_FECHA_FIN'), PROGRU_FechaFin) fechaFinTolerancia,
			PAMOD_ModalidadId AS modalidadId
     FROM ProgramasGrupos
          LEFT JOIN ProgramacionAcademicaComercial ON PROGRU_PAC_ProgramacionAcademicaComercialId = PAC_ProgramacionAcademicaComercialId
          LEFT JOIN PACiclos ON PROGRU_PACIC_CicloId = PACIC_CicloId
          INNER JOIN Sucursales ON PROGRU_SUC_SucursalId = SUC_SucursalId
          LEFT JOIN SucursalesPlanteles ON PROGRU_SP_SucursalPlantelId = SP_SucursalPlantelId
          LEFT JOIN Empleados t ON PROGRU_EMP_EmpleadoId = t.EMP_EmpleadoId
          INNER JOIN PAModalidadesHorarios ON PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
          INNER JOIN ProgramasIdiomas ON PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
          INNER JOIN Programas ON PROGI_PROG_ProgramaId = PROG_ProgramaId
          INNER JOIN ControlesMaestrosMultiples idioma ON PROGI_CMM_Idioma = idioma.CMM_ControlId
          INNER JOIN PAModalidades ON PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
          LEFT JOIN
		  (
				SELECT INS_PROGRU_GrupoId grupoId,
					   COUNT(INS_InscripcionId) inscritos
				FROM Inscripciones
				WHERE INS_CMM_EstatusId IN(2000510, 2000511)
				GROUP BY INS_PROGRU_GrupoId
		  ) inscripciones ON PROGRU_GrupoId = inscripciones.grupoId
          LEFT JOIN
		  (
				SELECT PROGRULC_PROGRU_GrupoId grupoId,
					   PROGRULC_EMP_EmpleadoId suplenteId
				FROM ProgramasGruposListadoClases
				WHERE CAST(PROGRULC_Fecha AS DATE) = CAST(GETDATE() AS DATE)
		  ) suplentes ON PROGRU_GrupoId = suplentes.grupoId
          LEFT JOIN Empleados s ON suplentes.suplenteId = s.EMP_EmpleadoId
GO