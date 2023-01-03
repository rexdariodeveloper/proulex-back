CREATE OR ALTER FUNCTION [dbo].[fn_getReporteCriteriosProfesor](@empleadosId VARCHAR(MAX))RETURNS @tbl TABLE(
empleadoId int,
empleadoNombre varchar(250),
grupoCodigo varchar(150),
grupoFechaInicio varchar(100),
grupoFechaFin varchar(100),
grupoHorario varchar(250),
grupoNivel int,
grupoModalidad varchar(100),
grupoAula varchar(100),
grupoComentarios varchar(500),
grupoActividad varchar(100),
grupoFechaAplicacion varchar(100),
fechaActual varchar(100),
niveles varchar(20),
fechaSuplencia varchar(MAX)
) AS BEGIN
	INSERT INTO @tbl
	Select DISTINCT
	EMP_EmpleadoId as empleadoId,
	CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ',EMP_SegundoApellido) as nombreEmpleado,
	PROGRU_Codigo as codigoGrupo,
	PROGRU_FechaInicio as fechaInicio,
	PROGRU_FechaFin as fechaFin,
	CONCAT(CONVERT(varchar(15),CAST(PAMODH_HoraInicio AS TIME),100),'-',CONVERT(varchar(15),CAST(PAMODH_HoraFin AS TIME),100)) as horario,
	PROGRU_Nivel as nivel,
	PAMOD_Nombre as modalidad,
	PROGRU_Aula as aula,
	PROGRU_Comentarios as comentarios,
	PAAE_Actividad as actividad,
	(SELECT fecha
		FROM (
			Select fecha,ROW_NUMBER() OVER (ORDER BY fecha) AS RowNum from dbo.fn_getDiaHabiles(PROGRU_FechaInicio ,PROGRU_FechaFin,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado) where fecha is not null
		) as fechaAplicacion where RowNum = PROGIEM_Dias
	) as fechaHabil,
	GETDATE() as fechaActual,
	CONCAT(PROGIN_NivelInicial,' - ',PROGIN_NivelFinal) as nivel,
	STRING_AGG(fecha,',')
	from Empleados
	INNER JOIN ProgramasGrupos ON PROGRU_EMP_EmpleadoId = EMP_EmpleadoId
	INNER JOIN PAModalidadesHorarios on PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
	INNER JOIN PAModalidades on PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
	INNER JOIN ProgramasIdiomas on PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	INNER JOIN ProgramasIdiomasNiveles on PROGIN_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND PROGRU_Nivel >=PROGIN_NivelInicial AND PROGRU_Nivel <= PROGIN_NivelFinal AND PROGIN_Activo = 1
	INNER JOIN ProgramasIdiomasExamenes on PROGIE_PROGIN_ProgramaIdiomaNivelId = PROGIN_ProgramaIdiomaNivelId AND PROGIE_Activo = 1
	INNER JOIN ProgramasIdiomasExamenesDetalles on PROGIED_PROGIE_ProgramaIdiomaExamenId = PROGIE_ProgramaIdiomaExamenId AND PROGIED_Activo = 1
	INNER JOIN ProgramasIdiomasExamenesModalidades ON PROGIEM_PROGIED_ProgramaIdiomaExamenDetalleId = PROGIED_ProgramaIdiomaExamenDetalleId
	INNER JOIN PAActividadesEvaluacion ON PAAE_ActividadEvaluacionId = PROGIED_PAAE_ActividadEvaluacionId
	outer apply dbo.fn_getDiasFestivos(PROGRU_FechaInicio,PROGRU_FechaFin,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado)
	WHERE EMP_EmpleadoId IN (SELECT * FROM dbo.SplitInts(@empleadosId,',')) AND fecha > PROGRU_FechaInicio AND fecha < PROGRU_FechaFin AND fecha is not null
	GROUP BY PROGIN_NivelInicial,PROGIN_NivelFinal,EMP_EmpleadoId,EMP_Nombre,EMP_PrimerApellido,EMP_SegundoApellido,PROGRU_Codigo,PROGRU_FechaInicio,PROGRU_FechaFin,PAMODH_HoraInicio,PAMODH_HoraFin,PROGRU_Nivel,PAMOD_Nombre,PROGRU_Aula,PROGRU_Comentarios,PAAE_Actividad,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado,PROGIEM_Dias
RETURN
END