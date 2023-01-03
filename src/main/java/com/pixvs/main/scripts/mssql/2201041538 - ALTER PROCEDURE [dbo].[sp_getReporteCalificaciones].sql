SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER   PROCEDURE [dbo].[sp_getReporteCalificaciones] @usuarioId INT, @programaIdiomaId INT, @grupoId INT
AS

	DECLARE
		@cols  nvarchar(max),
		@stmt  nvarchar(max),
		@where nvarchar(max),

		@rolId int,
		@empleadoId int,
		@permisoSede bit,
		@permisoExtemp bit,
		@diasTolerancia nvarchar(max),
		@sucursalJOBSId int,
		@sucursalSEMSId int


	SET @cols           = N'[empty]'
	SET @where          = N'AND PROGI_ProgramaIdiomaId = ' + CAST(@programaIdiomaId as nvarchar) + N' '
	SET @rolId          = (select USU_ROL_RolId from Usuarios where USU_UsuarioId = @usuarioId)
	SET @empleadoId     = (select EMP_EmpleadoId from Empleados where EMP_USU_UsuarioId = @usuarioId)
	SET @permisoSede    = (select CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END from RolesMenusPermisos where ROLMPP_ROL_RolId = @rolId and ROLMPP_MPP_MenuPrincipalPermisoId = 5)
	SET @permisoExtemp  = (select CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END from RolesMenusPermisos where ROLMPP_ROL_RolId = @rolId and ROLMPP_MPP_MenuPrincipalPermisoId = 7)
	SET @diasTolerancia = (select CMA_Valor from ControlesMaestros where CMA_Nombre = N'CM_SUMA_DIAS_FECHA_FIN')
	SET @sucursalJOBSId = (select CMM_Valor from ControlesMaestrosMultiples where CMM_Control = N'CMM_SUC_SucursalJOBSId')
	SET @sucursalSEMSId = (select CMM_Valor from ControlesMaestrosMultiples where CMM_Control = N'CMM_SUC_SucursalJOBSSEMSId')

	IF @grupoId IS NOT NULL
	BEGIN
		SET @where = @where + N'AND PROGRU_GrupoId = ' + CAST(@grupoId as nvarchar) + N' '
	END

	IF @permisoExtemp = 0
	BEGIN
		SET @where = @where +  N'AND CAST(GETDATE() as date) <= (DATEADD(day,' + CAST(@diasTolerancia as nvarchar) + N', CAST(PROGRU_FechaFin as date))) '
	END

	IF @permisoSede = 0
	BEGIN
		SET @where = @where + N'AND (PROGRU_EMP_EmpleadoId IS NOT NULL and PROGRU_EMP_EmpleadoId = ' + CAST(@empleadoId as nvarchar) + ') '
	END
	ELSE
	BEGIN
		SET @where = @where + N'AND PROGRU_SUC_SucursalId IN ( select ALM_SUC_SucursalId from UsuariosAlmacenes inner join Almacenes on USUA_ALM_AlmacenId = ALM_AlmacenId where USUA_USU_UsuarioId = ' + CAST(@usuarioId as nvarchar) + ') '
	END

	SELECT @cols = ISNULL(@cols + ', ', '') + '[' + PAAE_Actividad + ']'
	FROM(
		SELECT
			PAAE_Actividad,
			MIN(PROGIE_ProgramaIdiomaExamenId) AS PROGIE_ProgramaIdiomaExamenId
		from ProgramasIdiomasNiveles
		INNER JOIN ProgramasIdiomasExamenes on PROGIN_ProgramaIdiomaNivelId = PROGIE_PROGIN_ProgramaIdiomaNivelId
		INNER JOIN ProgramasIdiomasExamenesDetalles on PROGIE_ProgramaIdiomaExamenId = PROGIED_PROGIE_ProgramaIdiomaExamenId
		INNER JOIN PAActividadesEvaluacion on PROGIED_PAAE_ActividadEvaluacionId = PAAE_ActividadEvaluacionId
		WHERE PROGIN_PROGI_ProgramaIdiomaId = @programaIdiomaId
		GROUP BY PAAE_Actividad
	) Q
	ORDER BY PROGIE_ProgramaIdiomaExamenId

	SELECT @stmt = '
	SELECT 
		ALU_CodigoAlumnoUDG codigo,
		ALU_PrimerApellido primerApellido,
		ALU_SegundoApellido segundoApellido,
		ALU_Nombre nombre,
		grado.CMM_Valor grado,
		ALU_Grupo grupo,
		turno.CMM_Valor turno,
		PROGRU_Nivel nivel,
		SP_Nombre plantel,
		CONCAT(PROG_Codigo,'' '',idioma.CMM_Valor,'' '',PAMOD_Nombre,'' Nivel '',FORMAT(PROGRU_Nivel,''00''),'' Grupo '',FORMAT(PROGRU_Grupo,''00'')) codigoGrupo,
		PAMODH_Horario horario,
		prepa.CMM_Valor escuela,
		ALU_BachilleratoTecnologico carrera,
		PACIC_Codigo cohorte,
		N''Sí'' regular,
		N''Sí'' carta,
		ALU_Codigo codigoProulex,
		calificaciones.*
	FROM
		ProgramasGrupos
		INNER JOIN Inscripciones ON PROGRU_GrupoId = INS_PROGRU_GrupoId
		INNER JOIN Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
		LEFT JOIN
		(
		SELECT * FROM
			(
				SELECT 
					PROGIN_PROGI_ProgramaIdiomaId programaIdiomaId,
					PROGIN_NivelInicial nivelInicial,
					PROGIN_NivelFinal nivelFinal,
					PAAE_Actividad actividad,
					AEC_ALU_AlumnoId alumnoId,
					(CAST(PROGIE_Porcentaje as decimal(28,2)) / CAST(PROGIED_Puntaje as decimal(28,2))) * AEC_Puntaje puntaje
				FROM ProgramasIdiomasExamenes
				INNER JOIN ProgramasIdiomasNiveles on PROGIE_PROGIN_ProgramaIdiomaNivelId = PROGIN_ProgramaIdiomaNivelId
				INNER JOIN ProgramasIdiomasExamenesDetalles on PROGIE_ProgramaIdiomaExamenId = PROGIED_PROGIE_ProgramaIdiomaExamenId
				INNER JOIN PAActividadesEvaluacion on PROGIED_PAAE_ActividadEvaluacionId = PAAE_ActividadEvaluacionId
				LEFT JOIN AlumnosExamenesCalificaciones on PROGIED_ProgramaIdiomaExamenDetalleId = AEC_PROGIED_ProgramaIdiomaExamenDetalleId
				WHERE PROGIN_PROGI_ProgramaIdiomaId = ' + CAST(@programaIdiomaId as nvarchar) + '
			) detalles
		) t
		PIVOT(
			MAX(puntaje)
			FOR actividad IN (' + @cols + ')
		) calificaciones on PROGRU_PROGI_ProgramaIdiomaId = programaIdiomaId and PROGRU_Nivel >= nivelInicial and PROGRU_Nivel <= nivelFinal and ALU_AlumnoId = alumnoId
		INNER JOIN ProgramasIdiomas on PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
		INNER JOIN ControlesMaestrosMultiples idioma on PROGI_CMM_Idioma = idioma.CMM_ControlId
		INNER JOIN Programas on PROGI_PROG_ProgramaId = PROG_ProgramaId
		INNER JOIN PAModalidades on PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
		INNER JOIN PAModalidadesHorarios on PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
		LEFT JOIN ControlesMaestrosMultiples grado on ALU_CMM_GradoId = grado.CMM_ControlId
		LEFT JOIN ControlesMaestrosMultiples turno on ALU_CMM_TurnoId = turno.CMM_ControlId
		LEFT JOIN ControlesMaestrosMultiples prepa on ALU_CMM_PreparatoriaJOBSId = prepa.CMM_ControlId
		LEFT JOIN PACiclos on PROGRU_PACIC_CicloId = PACIC_CicloId 
		LEFT JOIN SucursalesPlanteles on SP_SucursalPlantelId = PROGRU_SP_SucursalPlantelId
	WHERE
		PROGRU_Activo = 1 
		AND ALU_Activo = 1
		AND INS_CMM_EstatusId <> 2000604 '
	+ @where +
	'ORDER BY
			PROGRU_GrupoId, ALU_PrimerApellido, ALU_SegundoApellido, ALU_Nombre
	FOR
		JSON PATH'
	
	EXEC sp_executesql @stmt = @stmt
GO


