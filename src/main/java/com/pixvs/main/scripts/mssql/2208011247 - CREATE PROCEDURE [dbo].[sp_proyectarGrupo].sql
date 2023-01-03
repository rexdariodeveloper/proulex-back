CREATE OR ALTER PROCEDURE [dbo].[sp_proyectarGrupo] (@grupoId INT, @creadoPorId INT, @grupoCreadoId INT = 0 OUTPUT)
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @sedeId INT = NULL;
	DECLARE @programaIdiomaId INT = NULL;
	DECLARE @idiomaId INT = NULL;
	DECLARE @modalidadId INT = NULL;
	DECLARE @nivel INT = NULL;
	DECLARE @nivelFinal INT = NULL;
	DECLARE @fecha DATE = NULL;
	DECLARE @msg NVARCHAR(500) = NULL;

	SELECT
		@sedeId = [PROGRU_SUC_SucursalId],
		@programaIdiomaId = [PROGRU_PROGI_ProgramaIdiomaId],
		@idiomaId = [PROGI_CMM_Idioma],
		@modalidadId = [PROGRU_PAMOD_ModalidadId],
		@nivel = [PROGRU_Nivel],
		@fecha = [PROGRU_FechaInicio],
		@nivelFinal = [PROGI_NumeroNiveles]
	FROM
		[dbo].[ProgramasGrupos]
		INNER JOIN [dbo].[ProgramasIdiomas] ON [PROGRU_PROGI_ProgramaIdiomaId] = [PROGI_ProgramaIdiomaId] 
	WHERE
		[PROGRU_GrupoId] = @grupoId;
	/* Verificar que el grupo no sea de un nivel final */
	IF @nivel = @nivelFinal
	BEGIN
		SET @msg = N'No se puede proyectar un grupo de nivel final';
		THROW 50000, @msg, 1
	END
	DECLARE @programacionDetalleId INT = NULL;
	DECLARE @fechaInicio DATE = NULL;
	DECLARE @fechaFin DATE = NULL; 
	/* Obtener programacion siguiente */
	SELECT @programacionDetalleId = siguiente FROM
	(
		SELECT 
			*, LEAD([PACD_ProgramacionAcademicaComercialDetalleId]) OVER (ORDER BY [PACD_FechaInicio]) siguiente 
		FROM 
			[dbo].[ProgramacionAcademicaComercialDetalles]
		WHERE
			[PACD_CMM_IdiomaId] = @idiomaId
			AND [PACD_PAMOD_ModalidadId] = @modalidadId
	) fechas WHERE fechas.[PACD_FechaInicio] = @fecha;
	/* Obtener las siguientes fechas de inicio */
	SELECT
		@fechaInicio = [PACD_FechaInicio],
		@fechaFin = [PACD_FechaFin]
	FROM
		[dbo].[ProgramacionAcademicaComercialDetalles]
	WHERE
		[PACD_ProgramacionAcademicaComercialDetalleId] = @programacionDetalleId;
	/* Obtener el ultimo grupo creado */
	DECLARE @maxGrupo INT = NULL;
	SELECT @maxGrupo = MAX([PROGRU_Grupo]) FROM [dbo].[ProgramasGrupos]
	WHERE 
		[PROGRU_SUC_SucursalId] = @sedeId 
		AND [PROGRU_PROGI_ProgramaIdiomaId] = @programaIdiomaId 
		AND [PROGRU_PAMOD_ModalidadId] = @modalidadId 
		AND [PROGRU_Nivel] = @nivel + 1
		AND [PROGRU_FechaInicio] = @fechaInicio
		AND [PROGRU_CMM_EstatusId] <> 2000622;

	DECLARE @codigo NVARCHAR(50) = NULL;
	DECLARE @diasFinInscripciones INT = 0;
	DECLARE @diasFinInscripcionesBecas INT = 0;
	SELECT
		@codigo = CONCAT(LEFT([SUC_Prefijo],3),
				LEFT([SP_Codigo],3),
				LEFT([PROG_Codigo],3),
				LEFT([CMM_Referencia],3),
				LEFT([PAMOD_Codigo],3),
				FORMAT(@nivel + 1,'00'),
				LEFT([PAMODH_Codigo],3),
				FORMAT(COALESCE(@maxGrupo, 0) + 1,'00')),
		@diasFinInscripciones = [PAMOD_DiasFinPeriodoInscripcion],
		@diasFinInscripcionesBecas = [PAMOD_DiasFinPeriodoInscripcionBeca]
	FROM [dbo].[ProgramasGrupos] 
	INNER JOIN [dbo].[Sucursales] ON [PROGRU_SUC_SucursalId] = [SUC_SucursalId]
	LEFT JOIN [dbo].[SucursalesPlanteles] ON [PROGRU_SP_SucursalPlantelId] = [SP_SucursalPlantelId]
	INNER JOIN [dbo].[ProgramasIdiomas] ON [PROGRU_PROGI_ProgramaIdiomaId] = [PROGI_ProgramaIdiomaId]
	INNER JOIN [dbo].[Programas] ON [PROGI_PROG_ProgramaId] = [PROG_ProgramaId]
	INNER JOIN [dbo].[ControlesMaestrosMultiples] ON [PROGI_CMM_Idioma] = [CMM_ControlId]
	INNER JOIN [dbo].[PAModalidades] ON [PROGRU_PAMOD_ModalidadId] = [PAMOD_ModalidadId]
	INNER JOIN [dbo].[PAModalidadesHorarios] ON [PROGRU_PAMODH_PAModalidadHorarioId] = [PAMODH_PAModalidadHorarioId]
	WHERE [PROGRU_GrupoId] = @grupoId
	/* fecha fin inscripciones */
	DECLARE @fechaFinInscripciones DATE = @fechaFin;
	SELECT TOP 1
		@fechaFinInscripciones = fecha
	FROM
		PAModalidades OUTER APPLY fn_getFechaPorModalidad(@fechaInicio, @diasFinInscripciones, PAMOD_Domingo, PAMOD_Lunes, PAMOD_Martes, PAMOD_Miercoles, PAMOD_Jueves, PAMOD_Viernes, PAMOD_Sabado)
	WHERE
		PAMOD_ModalidadId = @modalidadId AND fecha IS NOT NULL
	ORDER BY fecha DESC
	/* fecha fin de inscripciones con beca */
	DECLARE @fechaFinInscripcionesBecas DATE = @fechaFin;
	SELECT TOP 1
		@fechaFinInscripcionesBecas = fecha
	FROM
		PAModalidades OUTER APPLY fn_getFechaPorModalidad(@fechaInicio, @diasFinInscripcionesBecas, PAMOD_Domingo, PAMOD_Lunes, PAMOD_Martes, PAMOD_Miercoles, PAMOD_Jueves, PAMOD_Viernes, PAMOD_Sabado)
	WHERE
		PAMOD_ModalidadId = @modalidadId AND fecha IS NOT NULL
	ORDER BY fecha DESC;

	INSERT INTO ProgramasGrupos
		([PROGRU_SUC_SucursalId],
			[PROGRU_PROGI_ProgramaIdiomaId],
			[PROGRU_PAMOD_ModalidadId],
			[PROGRU_PAMODH_PAModalidadHorarioId],
			[PROGRU_PAC_ProgramacionAcademicaComercialId],
			[PROGRU_FechaInicio],
			[PROGRU_FechaFin],
			[PROGRU_FechaFinInscripciones],
			[PROGRU_FechaFinInscripcionesBecas],
			[PROGRU_Nivel],
			[PROGRU_Grupo],
			[PROGRU_CMM_PlataformaId],
			[PROGRU_CMM_TipoGrupoId],
			[PROGRU_EMP_EmpleadoId],
			[PROGRU_Multisede],
			[PROGRU_CalificacionMinima],
			[PROGRU_FaltasPermitidas],
			[PROGRU_Cupo],
			[PROGRU_CategoriaProfesor],
			[PROGRU_SueldoProfesor],
			[PROGRU_Codigo],
			[PROGRU_Aula],
			[PROGRU_GrupoReferenciaId],
			[PROGRU_CMM_EstatusId],
			[PROGRU_USU_CreadoPorId])
	SELECT
			[PROGRU_SUC_SucursalId],
			[PROGRU_PROGI_ProgramaIdiomaId],
			[PROGRU_PAMOD_ModalidadId],
			[PROGRU_PAMODH_PAModalidadHorarioId],
			[PROGRU_PAC_ProgramacionAcademicaComercialId],
			@fechaInicio,
			@fechaFin,
			@fechaFinInscripciones,
			@fechaFinInscripcionesBecas,
			@nivel + 1,
			COALESCE(@maxGrupo, 0) + 1,
			[PROGRU_CMM_PlataformaId],
			[PROGRU_CMM_TipoGrupoId],
			NULL,
			[PROGRU_Multisede],
			[PROGRU_CalificacionMinima],
			[PROGRU_FaltasPermitidas],
			[PROGRU_Cupo],
			NULL,
			NULL,
			@codigo,
			[PROGRU_Aula],
			[PROGRU_GrupoId],
			2000620,
			@creadoPorId
	FROM ProgramasGrupos WHERE PROGRU_GrupoId = @grupoId;
	SET @grupoCreadoId = SCOPE_IDENTITY();
END