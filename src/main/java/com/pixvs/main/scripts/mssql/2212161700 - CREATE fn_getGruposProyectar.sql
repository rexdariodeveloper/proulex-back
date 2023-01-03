SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ========================================================
-- Author:		Javier Elías
-- Create date: 16/12/2022
-- Modified date: 
-- Description:	Función para obtener los Grupos a Proyectar
-- ========================================================
CREATE OR ALTER FUNCTION [dbo].[fn_getGruposProyectar]( @grupos VARCHAR(4000) )
RETURNS TABLE WITH SCHEMABINDING
AS
RETURN
(
	SELECT PROGRU_GrupoId AS id,
		   PROGRU_Codigo AS actual,
		   dbo.fn_getProyeccionGrupoCodigo(PROGRU_GrupoId, (PROGRU_Grupo - 1) + COALESCE(dbo.fn_getProyeccionUltimoGrupoCreado(PROGRU_SUC_SucursalId, PROGI_ProgramaIdiomaId, PAMOD_ModalidadId, PROGRU_Nivel, inicio), 0), PROGRU_Nivel) AS siguiente,
		   CONCAT_WS(' ', PROG_Codigo, CMM_Valor) AS curso,
		   PAMOD_Nombre modalidad,
		   inicio AS fechaInicio,
		   fin AS fechaFin,
		   (PROGRU_Nivel + 1) AS nivel,
		   PAMODH_Horario AS horario,
		   PROGRU_Cupo AS cupo,
		   COALESCE(t.inscritos, 0) AS inscritos,
		   CONCAT_WS(' ', EMP_Nombre, EMP_PrimerApellido, EMP_SegundoApellido) AS profesor
	FROM dbo.ProgramasGrupos
		 INNER JOIN dbo.ProgramasIdiomas ON PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
		 INNER JOIN dbo.Programas ON PROGI_PROG_ProgramaId = PROG_ProgramaId
		 INNER JOIN dbo.ControlesMaestrosMultiples ON PROGI_CMM_Idioma = CMM_ControlId
		 INNER JOIN dbo.PAModalidadesHorarios ON PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
		 CROSS APPLY
		 (
			SELECT TOP 1
					PACD_FechaInicio AS inicio,
					PACD_FechaFin AS fin
			FROM dbo.ProgramacionAcademicaComercialDetalles
			WHERE PACD_PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
				  AND PACD_CMM_IdiomaId = PROGI_CMM_Idioma
				  AND PACD_FechaInicio > PROGRU_FechaFin
			ORDER BY PACD_FechaInicio
		 ) AS fechas
		 INNER JOIN dbo.PAModalidades ON PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
		 LEFT JOIN
		 (
			SELECT PROGRU_GrupoReferenciaId referenciaId
			FROM dbo.ProgramasGrupos
			WHERE PROGRU_GrupoReferenciaId IS NOT NULL
		 ) AS proyectados ON referenciaId = PROGRU_GrupoId
		 LEFT JOIN dbo.Empleados ON EMP_EmpleadoId = PROGRU_EMP_EmpleadoId
		 LEFT JOIN
		 (
			SELECT INS_PROGRU_GrupoId grupoId,
				   COUNT(INS_InscripcionId) inscritos
			FROM dbo.Inscripciones
			WHERE INS_CMM_EstatusId != 2000512
			GROUP BY INS_PROGRU_GrupoId
		 ) AS t ON t.grupoId = PROGRU_GrupoId
	WHERE referenciaId IS NULL
		  AND (PROGRU_Nivel + 1) <= PROGI_NumeroNiveles
		  AND t.inscritos > 0
		  AND PROGRU_GrupoId IN ( SELECT VALUE FROM STRING_SPLIT(@grupos, ',') )
)
GO