CREATE OR ALTER PROCEDURE sp_getReporteAsistencias @usuarioId INT, @grupoId INT
AS
	DECLARE
		@cols  nvarchar(max),
		@stmt  nvarchar(max),
		@where nvarchar(max),

		@rolId int,
		@empleadoId int,
		@permisoSede bit

	SET @rolId = (select USU_ROL_RolId from Usuarios where USU_UsuarioId = @usuarioId)
	SET @empleadoId = (select EMP_EmpleadoId from Empleados where EMP_USU_UsuarioId = @usuarioId)
	SET @permisoSede = (select CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END from RolesMenusPermisos where ROLMPP_ROL_RolId = @rolId and ROLMPP_MPP_MenuPrincipalPermisoId = 6)

	IF @permisoSede = 0
	BEGIN
		SET @where = 'WHERE ' + (CASE WHEN @grupoId IS NULL THEN '1 = 1' ELSE 'PROGRU_GrupoId = ' + (CAST(@grupoId as nvarchar)) END) + N' AND (PROGRU_EMP_EmpleadoId IS NOT NULL and PROGRU_EMP_EmpleadoId = ' + CAST(@empleadoId as nvarchar) + ')'
	END
	ELSE
	BEGIN
		SET @where = 'WHERE ' + (CASE WHEN @grupoId IS NULL THEN '1 = 1' ELSE 'PROGRU_GrupoId = ' + (CAST(@grupoId as nvarchar)) END) + N' AND PROGRU_SUC_SucursalId IN (select ALM_SUC_SucursalId from UsuariosAlmacenes inner join Almacenes on USUA_ALM_AlmacenId = ALM_AlmacenId where USUA_USU_UsuarioId = ' + CAST(@usuarioId as nvarchar) + ')'
	END

	SELECT @cols = ISNULL(@cols + ', ', '') + '[' + T.fecha + ']' FROM (SELECT DISTINCT FORMAT(AA_Fecha,'yyyy-MM-dd') fecha FROM AlumnosAsistencias) AS T

	SELECT @stmt = 'select 
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
						pt.*
					from
					(
					select AA_ALU_AlumnoId, AA_PROGRU_GrupoId, FORMAT(AA_Fecha, ''yyyy-MM-dd'') fecha,
						case when AA_CMM_TipoAsistenciaId = 2000550 THEN AA_CMM_TipoAsistenciaId
							 when AA_CMM_TipoAsistenciaId = 2000551 THEN AA_CMM_TipoAsistenciaId
							 when AA_CMM_TipoAsistenciaId = 2000552 THEN AA_CMM_TipoAsistenciaId
							 when AA_CMM_TipoAsistenciaId = 2000553 THEN AA_MinutosRetardo
						end tipo 
					from
					AlumnosAsistencias 
					) t
					PIVOT(
						MAX(tipo)
						FOR fecha IN ('+ @cols +', [a])
					) pt
					inner join Alumnos on pt.AA_ALU_AlumnoId = ALU_AlumnoId
					left join ControlesMaestrosMultiples grado on ALU_CMM_GradoId = grado.CMM_ControlId
					left join ControlesMaestrosMultiples turno on ALU_CMM_TurnoId = turno.CMM_ControlId
					inner join ProgramasGrupos on pt.AA_PROGRU_GrupoId = PROGRU_GrupoId
					inner join ProgramasIdiomas on PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
					inner join ControlesMaestrosMultiples idioma on PROGI_CMM_Idioma = idioma.CMM_ControlId
					inner join Programas on PROGI_PROG_ProgramaId = PROG_ProgramaId
					inner join PAModalidades on PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
					inner join PAModalidadesHorarios on PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
					left join ControlesMaestrosMultiples prepa on ALU_CMM_PreparatoriaJOBSId = prepa.CMM_ControlId
					left join PACiclos on PROGRU_PACIC_CicloId = PACIC_CicloId 
					left join SucursalesPlanteles on SP_SucursalPlantelId = PROGRU_SP_SucursalPlantelId ' + @where + '
					order by
						PROGRU_GrupoId, ALU_PrimerApellido, ALU_SegundoApellido, ALU_Nombre
					for
						JSON PATH'

EXEC sp_executesql @stmt = @stmt
GO