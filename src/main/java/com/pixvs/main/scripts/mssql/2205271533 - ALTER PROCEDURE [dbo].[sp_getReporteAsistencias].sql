SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER PROCEDURE [dbo].[sp_getReporteAsistencias] @sedeId INT, @plantelId INT, @pacId INT, @cicloId INT, @modalidadId INT, @fechaInicio DATE, @codigoGrupo NVARCHAR(25)
AS
	DECLARE @cols  nvarchar(max);
	DECLARE @stmt  nvarchar(max);
	DECLARE @where nvarchar(max) = '';

	DECLARE @fechaFin DATE;
	SELECT 
		@fechaFin = MAX(PROGRU_FechaFin)
	FROM 
		ProgramasGrupos
	WHERE 
		PROGRU_SUC_SucursalId = @sedeId 
		and (@plantelId IS NULL OR PROGRU_SP_SucursalPlantelId = @plantelId)
		and (@pacId IS NULL OR PROGRU_PAC_ProgramacionAcademicaComercialId = @pacId)
		and (@cicloId IS NULL OR PROGRU_PACIC_CicloId = @cicloId)
		and PROGRU_PAMOD_ModalidadId = @modalidadId 
		and PROGRU_FechaInicio = @fechaInicio
		and (@codigoGrupo IS NULL OR PROGRU_Codigo = @codigoGrupo);

	IF @fechaFin > GETDATE()
	BEGIN
		SET @fechaFin = GETDATE()
	END

	-- Consultamos las fechas habiles del curso usando @fechaInicio y @modalidadId
	DECLARE @d BIT, @l BIT, @m BIT, @mi BIT, @j BIT, @v BIT, @s BIT;
	SELECT @d = PAMOD_Domingo, @l = PAMOD_Lunes, @m = PAMOD_Martes,@mi = PAMOD_Miercoles,@j = PAMOD_Jueves,@v = PAMOD_Viernes,@s = PAMOD_Sabado FROM [dbo].[PAModalidades] WHERE PAMOD_ModalidadId = @modalidadId;
	-- Agregamos las fechas con el formato para las columnas (pivot)
	SELECT @cols = ISNULL(@cols + ', ', '') + '[' + FORMAT(T.fecha,'yyyy-MM-dd') + ']' FROM (SELECT * FROM [dbo].[fn_getDiaHabiles](@fechaInicio,@fechaFin,@d,@l,@m,@mi,@j,@v,@s) WHERE fecha IS NOT NULL) AS T

	-- Construimos el where
	IF @sedeId IS NOT NULL
	BEGIN
		SET @where = @where + N'AND PROGRU_SUC_SucursalId = ' + CAST(@sedeId as nvarchar) + N' '
	END

	IF @plantelId IS NOT NULL
	BEGIN
		SET @where = @where + N'AND PROGRU_SP_SucursalPlantelId = ' + CAST(@plantelId as nvarchar) + N' '
	END

	IF @pacId IS NOT NULL AND @pacId > 0
	BEGIN
		SET @where = @where + N'AND PROGRU_PAC_ProgramacionAcademicaComercialId = ' + CAST(@pacId as nvarchar) + N' '
	END

	IF @cicloId IS NOT NULL AND @cicloId > 0
	BEGIN
		SET @where = @where + N'AND PROGRU_PACIC_CicloId = ' + CAST(@cicloId as nvarchar) + N' '
	END

	IF @modalidadId IS NOT NULL
	BEGIN
		SET @where = @where + N'AND PROGRU_PAMOD_ModalidadId = ' + CAST(@modalidadId as nvarchar) + N' '
	END

	IF @fechaInicio IS NOT NULL
	BEGIN
		SET @where = @where + N'AND PROGRU_FechaInicio = ''' + FORMAT(@fechaInicio,'yyyy-MM-dd') + N''' '
	END

	IF @codigoGrupo IS NOT NULL AND TRIM(@codigoGrupo) <> ''
	BEGIN
		SET @where = @where + N'AND PROGRU_Codigo = ''' + CAST(@codigoGrupo as nvarchar) + N''' '
	END

	SELECT @stmt = '
	   SELECT 
		  ALU_CodigoAlumnoUDG codigo,
		  ALU_PrimerApellido primerApellido,
		  ALU_SegundoApellido segundoApellido,
		  ALU_Nombre nombre,
		  coalesce(ALUG_Faltas, 0) as faltas, 
		  coalesce(ALUG_Asistencias, 0) as asistencias,
		  coalesce(estatus.CMM_Valor, ''Activo'') as estatus,   
		  COALESCE(grado.CMM_Valor, '''') grado,
		  COALESCE(ALU_Grupo,'''') grupo,
		  COALESCE(turno.CMM_Valor,'''') turno,
		  PROGRU_Nivel nivel,
		  CONCAT_WS('' '',EMP_Nombre,EMP_PrimerApellido,EMP_SegundoApellido) AS profesor,
		  SP_Nombre plantel,
		  CONCAT_WS('' '',PROG_Codigo,idioma.CMM_Valor,PAMOD_Nombre,FORMAT(PROGRU_Nivel,''Nivel 00''),FORMAT(PROGRU_Grupo,''Grupo 00'')) codigoGrupo,
		  PROGRU_Codigo codigoGrupo2,
		  PAMODH_Horario horario,
		  COALESCE(prepa.CMM_Valor, cu.CMM_Referencia) escuela,
		  COALESCE(ALU_BachilleratoTecnologico, carrera.CMM_Valor) carrera,
		  primeroCiclos.codigo cohorte,
		  N''Si'' regular,
		  N''Si'' carta,
		  ALU_Codigo codigoProulex,
		  asistencias.*,
		  '''+@cols+''' fechas
	   FROM ProgramasGrupos
	   INNER JOIN Inscripciones ON PROGRU_GrupoId = INS_PROGRU_GrupoId
	   INNER JOIN Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
	   LEFT JOIN
	   (
	   SELECT AA_ALU_AlumnoId, AA_PROGRU_GrupoId,  CAST(AA_Fecha AS DATE) fecha,
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
	   LEFT JOIN AlumnosGrupos on ALUG_INS_InscripcionId = INS_InscripcionId
	   LEFT JOIN ControlesMaestrosMultiples grado on ALU_CMM_GradoId = grado.CMM_ControlId
	   LEFT JOIN ControlesMaestrosMultiples turno on ALU_CMM_TurnoId = turno.CMM_ControlId
	   LEFT JOIN ControlesMaestrosMultiples prepa on ALU_CMM_PreparatoriaJOBSId = prepa.CMM_ControlId
	   LEFT JOIN ControlesMaestrosMultiples cu on ALU_CMM_CentroUniversitarioJOBSId = cu.CMM_ControlId
	   LEFT JOIN ControlesMaestrosMultiples carrera on ALU_CMM_CarreraJOBSId = carrera.CMM_ControlId
	   LEFT JOIN ControlesMaestrosMultiples estatus on ALUG_CMM_EstatusId = estatus.CMM_ControlId 
	   LEFT JOIN SucursalesPlanteles on SP_SucursalPlantelId = PROGRU_SP_SucursalPlantelId
	   LEFT JOIN Empleados on PROGRU_EMP_EmpleadoId = EMP_EmpleadoId
	   LEFT JOIN (select ins.INS_ALU_AlumnoId alumnoId, p.PROGRU_GrupoId grupoId, pa.PACIC_Codigo codigo from 
		  (
		  SELECT i.INS_ALU_AlumnoId,  
			 i.INS_PROGRU_GrupoId, 
			 ROW_NUMBER() OVER(PARTITION BY i.INS_ALU_AlumnoId 
							   ORDER BY i.INS_FechaCreacion ASC) AS rank
		  FROM Inscripciones i) ins
		  inner join ProgramasGrupos p on ins.INS_PROGRU_GrupoId = p.PROGRU_GrupoId
		  left join PACiclos pa on p.PROGRU_PACIC_CicloId = pa.PACIC_CicloId
		  where ins.rank = 1) primeroCiclos on alumnoId = ALU_AlumnoId
	   WHERE
		  PROGRU_Activo = 1 
		  AND ALU_Activo = 1
		  AND INS_CMM_EstatusId <> 2000512 '
	   + @where +
	   'ORDER BY
		  PROGRU_GrupoId, ALU_PrimerApellido, ALU_SegundoApellido, ALU_Nombre';

	--SELECT @stmt
	EXEC sp_executesql @stmt = @stmt;
GO


