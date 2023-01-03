SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_RptEvidenciaFotografica] AS
	SELECT PROGRU_GrupoId AS GrupoId,
		   UPPER(PROG_Nombre) AS Programa,
		   'PROULEX '+UPPER(SUC_Nombre) AS Institucion,
		   UPPER(CONCAT(PROG_Codigo,' ',CMM_Valor)) AS Curso,
		   CONCAT_WS(' - ', CAST(PAMODH_HoraInicio AS NVARCHAR(5)), CAST(PAMODH_HoraFin AS NVARCHAR(5))) AS Horario,
		   CONCAT_WS(' ', EMP_Nombre, EMP_PrimerApellido, EMP_SegundoApellido) AS Profesor,
		   Nombre1,
		   Ruta1,
		   Nombre2,
		   Ruta2,
		   PROGRU_SUC_SucursalId AS SedeId,
		   PROGRU_PAMOD_ModalidadId AS ModalidadId,
		   PROGRU_FechaInicio AS FechaInicio,
		   PROG_ProgramaId AS ProgramaId,
		   ROW_NUMBER() OVER (ORDER BY PROG_Codigo, SUC_Nombre, CONCAT(PROG_Codigo,' ',CMM_Valor), CONCAT_WS(' - ', CAST(PAMODH_HoraInicio AS NVARCHAR(5)), CAST(PAMODH_HoraFin AS NVARCHAR(5))), UPPER(dbo.getNombreCompletoUsuario(EMP_USU_UsuarioId)), Evidencia1, Evidencia2) AS Orden
	FROM ProgramasGrupos
		 INNER JOIN ProgramasIdiomas ON PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND PROGI_Activo = 1
		 INNER JOIN Programas ON PROGI_PROG_ProgramaId = PROG_ProgramaId AND PROG_PCP = 1 AND PROG_Activo = 1
		 INNER JOIN Sucursales ON PROGRU_SUC_SucursalId = SUC_SucursalId
		 INNER JOIN PAModalidadesHorarios ON PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
		 LEFT JOIN Empleados ON PROGRU_EMP_EmpleadoId = EMP_EmpleadoId
		 INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = PROGI_CMM_Idioma
		 OUTER APPLY
		 (
			 SELECT *
			 FROM
			 (
				SELECT ROW_NUMBER() OVER(ORDER BY PROGRUE_ProgramaGrupoEvidenciaId) AS Id,
					   PROGRUE_Nombre AS Nombre1,
					   PROGRUE_ProgramaGrupoEvidenciaId AS Evidencia1,
					   ARC_RutaFisica + '/' + ARC_NombreFisico Ruta1
				FROM ProgramasGruposEvidencias
					INNER JOIN Archivos ON PROGRUE_ARC_ArchivoId = ARC_ArchivoId
				WHERE PROGRUE_PROGRU_GrupoId = PROGRU_GrupoId
					AND PROGRUE_Vigente = 1
			 ) AS t
			 WHERE Id % 2 != 0
		 ) AS nones
		 OUTER APPLY
		 (
			 SELECT *
			 FROM
			 (
				SELECT ROW_NUMBER() OVER(ORDER BY PROGRUE_ProgramaGrupoEvidenciaId) AS Id,
					   PROGRUE_Nombre AS Nombre2,
					   PROGRUE_ProgramaGrupoEvidenciaId AS Evidencia2,
					   ARC_RutaFisica + '/' + ARC_NombreFisico Ruta2
				FROM ProgramasGruposEvidencias
					INNER JOIN Archivos ON PROGRUE_ARC_ArchivoId = ARC_ArchivoId
				WHERE PROGRUE_PROGRU_GrupoId = PROGRU_GrupoId
					AND PROGRUE_Vigente = 1
			 ) AS t
			 WHERE (nones.Id + 1) = t.Id
		 ) AS pares
	WHERE PROGRU_CMM_EstatusId != 2000622 -- CMM_PROGRU_Estatus	Cancelado
		  AND PROGRU_Activo = 1
		  --AND (Evidencia1 IS NOT NULL OR Evidencia2 IS NOT NULL)
GO