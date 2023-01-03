SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[sp_getReporteAsistencias] @usuarioId INT, @grupoId INT
AS
	DECLARE
		@cols  nvarchar(max),
		@stmt  nvarchar(max),
		@where nvarchar(max),

		@rolId int,
		@empleadoId int,
		@permisoSede bit,
		@diasTolerancia nvarchar(max)

	SET @where          = ''
	SET @rolId          = (select USU_ROL_RolId from Usuarios where USU_UsuarioId = @usuarioId)
	SET @empleadoId     = (select EMP_EmpleadoId from Empleados where EMP_USU_UsuarioId = @usuarioId)
	SET @permisoSede    = (select CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END from RolesMenusPermisos where ROLMPP_ROL_RolId = @rolId and ROLMPP_MPP_MenuPrincipalPermisoId = 6)
	SET @diasTolerancia = (select CMA_Valor from ControlesMaestros where CMA_Nombre = N'CM_SUMA_DIAS_FECHA_FIN')

	IF @grupoId IS NOT NULL
	BEGIN
		SET @where = N'AND PROGRU_GrupoId = ' + CAST(@grupoId as nvarchar) + N' '
	END

	IF @permisoSede = 0
	BEGIN
		SET @where = @where + N'AND (PROGRU_EMP_EmpleadoId IS NOT NULL and PROGRU_EMP_EmpleadoId = ' + CAST(@empleadoId as nvarchar) + ') '
	END
	ELSE
	BEGIN
		SET @where = @where + N'AND PROGRU_SUC_SucursalId IN (select ALM_SUC_SucursalId from UsuariosAlmacenes inner join Almacenes on USUA_ALM_AlmacenId = ALM_AlmacenId where USUA_USU_UsuarioId = ' + CAST(@usuarioId as nvarchar) + ') '
	END

	SELECT @cols = ISNULL(@cols + ', ', '') + '[' + T.fecha + ']' FROM (SELECT DISTINCT FORMAT(AA_Fecha,'yyyy-MM-dd') fecha FROM AlumnosAsistencias) AS T

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
		asistencias.*
	FROM ProgramasGrupos
	INNER JOIN Inscripciones ON PROGRU_GrupoId = INS_PROGRU_GrupoId
	INNER JOIN Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
	LEFT JOIN
	(
	SELECT AA_ALU_AlumnoId, AA_PROGRU_GrupoId, FORMAT(AA_Fecha, ''yyyy-MM-dd'') fecha,
		CASE 
			WHEN AA_CMM_TipoAsistenciaId = 2000550 THEN AA_CMM_TipoAsistenciaId
			WHEN AA_CMM_TipoAsistenciaId = 2000551 THEN AA_CMM_TipoAsistenciaId
			WHEN AA_CMM_TipoAsistenciaId = 2000552 THEN AA_CMM_TipoAsistenciaId
			WHEN AA_CMM_TipoAsistenciaId = 2000553 THEN AA_MinutosRetardo
		END tipo 
	FROM
	AlumnosAsistencias 
	) t
	PIVOT(
		MAX(tipo)
		FOR fecha IN ('+@cols+')
	) asistencias ON AA_ALU_AlumnoId = ALU_AlumnoId AND AA_PROGRU_GrupoId = PROGRU_GrupoId
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
		AND CAST(GETDATE() as date) <= (DATEADD(day,' + @diasTolerancia + ', CAST(PROGRU_FechaFin as date)))
		AND ALU_Activo = 1
		AND INS_CMM_EstatusId <> 2000604 '
	+ @where +
	'ORDER BY
		PROGRU_GrupoId, ALU_PrimerApellido, ALU_SegundoApellido, ALU_Nombre
	FOR
		JSON PATH'
	
	EXEC sp_executesql @stmt = @stmt
GO