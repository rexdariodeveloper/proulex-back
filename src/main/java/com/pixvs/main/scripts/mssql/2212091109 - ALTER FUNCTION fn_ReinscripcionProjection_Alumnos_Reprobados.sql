/**
* Created by Angel Daniel Hernández Silva on 16/11/2022.
*/

DROP FUNCTION [dbo].[fn_ReinscripcionProjection_Alumnos]
GO

CREATE OR ALTER FUNCTION [dbo].[fn_ReinscripcionProjection_Alumnos_Aprobados](@sucursalId int, @textoBuscar varchar(255)) RETURNS TABLE WITH SCHEMABINDING AS RETURN(
	SELECT
		ALU_AlumnoId AS id,
		ALU_Codigo AS codigo,
		BECU_BecaId AS becaId,
		COALESCE(BECU_CodigoBeca,'') AS becaCodigo,
		ALU_CodigoUDG AS codigoUDG,
		ALU_Nombre AS nombre,
		ALU_PrimerApellido AS primerApellido,
		ALU_SegundoApellido AS segundoApellido,
		CONCAT(PROG_Codigo,' ',CMMIdioma.CMM_Valor) AS curso,
		PAMOD_Nombre AS modalidad,
		PAMODH_Horario AS horario,
		GrupoActual.PROGRU_Nivel AS nivelReinscripcion,
		ALUG_CalificacionFinal AS calificacion,
		GrupoAnterior.PROGRU_CalificacionMinima AS calificacionMinima,
		CAST(COALESCE(CAST(CASE WHEN ALUG_CMM_EstatusId NOT IN (2000673,2000674) THEN 1 ELSE 0 END AS bit),0) AS bit) AS limiteFaltasExcedido,
		PROGI_CMM_Idioma AS idiomaId,
		PROG_ProgramaId AS programaId,
		PAMOD_ModalidadId AS modalidadId,
		PAMODH_PAModalidadHorarioId AS horarioId,
		SUC_SucursalId AS sucursalId,
		ART_ArticuloId AS articuloId,
		GrupoActual.PROGRU_Grupo AS numeroGrupo,
		GrupoActual.PROGRU_GrupoId AS grupoReinscripcionId,
		CAST(GrupoActual.PROGRU_Codigo AS varchar(25)) AS grupoReinscripcionCodigo,
		CAST(1 AS bit) AS aprobado
	FROM [dbo].[Alumnos]
	INNER JOIN [dbo].[Inscripciones] ON INS_ALU_AlumnoId = ALU_AlumnoId
	INNER JOIN [dbo].[OrdenesVentaDetalles] ON OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaDetalleId
	INNER JOIN [dbo].[OrdenesVenta] ON OV_OrdenVentaId = OVD_OV_OrdenVentaId
	INNER JOIN [dbo].[ProgramasGrupos] AS GrupoActual ON GrupoActual.PROGRU_GrupoId = INS_PROGRU_GrupoId
	INNER JOIN [dbo].[ProgramasGrupos] AS GrupoAnterior ON GrupoAnterior.PROGRU_GrupoId = GrupoActual.PROGRU_GrupoReferenciaId
	INNER JOIN [dbo].[Sucursales] ON SUC_SucursalId = GrupoActual.PROGRU_SUC_SucursalId
	LEFT JOIN [dbo].[ControlesMaestrosMultiples] AS CMMSucursalJOBS ON CMMSucursalJOBS.CMM_Valor = CAST(SUC_SucursalId AS varchar(10))
	LEFT JOIN [dbo].[ControlesMaestrosMultiples] AS CMMSucursalJOBSSEMS ON CMMSucursalJOBSSEMS.CMM_Valor = CAST(SUC_SucursalId AS varchar(10))
	INNER JOIN [dbo].[ProgramasIdiomas] ON PROGI_ProgramaIdiomaId = GrupoActual.PROGRU_PROGI_ProgramaIdiomaId
	iNNER JOIN [dbo].[Programas] ON PROG_ProgramaId = PROGI_PROG_ProgramaId
	INNER JOIN [dbo].[PAModalidades] ON PAMOD_ModalidadId = GrupoActual.PROGRU_PAMOD_ModalidadId
	INNER JOIN [dbo].[AlumnosGrupos] ON ALUG_ALU_AlumnoId = ALU_AlumnoId AND ALUG_PROGRU_GrupoId = GrupoAnterior.PROGRU_GrupoId
	LEFT JOIN [dbo].[BecasUDG]
		ON (
			(
				BECU_CMM_TipoId IN (2000580,2000581)
				AND (
					BECU_CodigoEmpleado = ALU_CodigoUDG
					OR BECU_CodigoEmpleado = ALU_CodigoUDGAlterno
				) AND BECU_Nombre = ALU_Nombre
				AND BECU_PrimerApellido = BECU_PrimerApellido
				AND(
					(BECU_SegundoApellido IS NULL AND ALU_SegundoApellido IS NULL)
					OR BECU_SegundoApellido = ALU_SegundoApellido
				)
			) OR (
				BECU_CMM_TipoId = 2000582
				AND BECU_CodigoAlumno = ALU_Codigo
			)
		)
		AND BECU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
		AND BECU_PAMOD_ModalidadId = PAMOD_ModalidadId
		AND BECU_Nivel = GrupoActual.PROGRU_Nivel
		AND BECU_CMM_EstatusId = 2000570 -- Pendiente por aplicar
		AND ALUG_CalificacionFinal >= GrupoAnterior.PROGRU_CalificacionMinima
	INNER JOIN [dbo].[ControlesMaestrosMultiples] AS CMMIdioma ON CMMIdioma.CMM_ControlId = PROGI_CMM_Idioma
	INNER JOIN [dbo].[PAModalidadesHorarios] ON PAMODH_PAModalidadHorarioId = GrupoActual.PROGRU_PAMODH_PAModalidadHorarioId
	INNER JOIN [dbo].[Articulos]
		ON ART_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
		AND ART_PAMOD_ModalidadId = PAMOD_ModalidadId
		AND(
			(PROGI_AgruparListadosPreciosPorTipoGrupo = 0 AND ART_CMM_TipoGrupoId IS NULL)
			OR (PROGI_AgruparListadosPreciosPorTipoGrupo = 1 AND ART_CMM_TipoGrupoId = GrupoActual.PROGRU_CMM_TipoGrupoId)
		)
	WHERE
		INS_CMM_EstatusId = 2000511 -- PENDIENTES DE PAGO
		AND OV_MPPV_MedioPagoPVId IS NULL -- Al no tener medio de pago significa que no han pasado por el PV
		AND CMMSucursalJOBS.CMM_ControlId IS NULL -- Omitir sucursal JOBS
		AND CMMSucursalJOBSSEMS.CMM_ControlId IS NULL -- Omitir sucursal JOBS SEMS
		AND COALESCE(PROG_PCP,0) = 0 -- Omitir grupos PCP
		AND GrupoAnterior.PROGRU_PGINCG_ProgramaIncompanyId IS NULL -- Omitir grupos in company
		AND ALUG_CMM_EstatusId  = 2000675 -- Aprobado
		AND OV_SUC_SucursalId = @sucursalId
		AND CONCAT(ALU_Codigo,'|',ALU_CodigoUDG,'|',ALU_Nombre,' ',ALU_PrimerApellido,' ' + ALU_SegundoApellido,' ',ALU_Nombre,'|',COALESCE(BECU_CodigoBeca,''),'|',CONCAT(PROG_Codigo,' ',CMMIdioma.CMM_Valor),'|',PAMOD_Nombre,'|',PAMODH_Horario,'|Aprobado') LIKE @textoBuscar
)
GO

CREATE OR ALTER FUNCTION [dbo].[fn_ReinscripcionProjection_Alumnos_Reprobados](@sucursalId int, @textoBuscar varchar(255)) RETURNS TABLE WITH SCHEMABINDING AS RETURN(
	SELECT
		id,
		codigo,
		NULL AS becaId,
		'' AS becaCodigo,
		codigoUDG,
		nombre,
		primerApellido,
		segundoApellido,
		CONCAT(PROG_Codigo,' ',CMM_Valor) AS curso,
		PAMOD_Nombre AS modalidad,
		PAMODH_Horario AS horario,
		nivel AS nivelReinscripcion,
		ALUG_CalificacionFinal AS calificacion,
		calificacionMinima,
		CAST(COALESCE(CAST(CASE WHEN ALUG_CMM_EstatusId NOT IN (2000673,2000674) THEN 1 ELSE 0 END AS bit),0) AS bit) AS limiteFaltasExcedido,
		PROGI_CMM_Idioma AS idiomaId,
		PROG_ProgramaId AS programaId,
		modalidadId,
		horarioId,
		sucursalId,
		ART_ArticuloId AS articuloId,
		numeroGrupo,
		NULL AS grupoReinscripcionId,
		CAST(NULL AS varchar(25)) AS grupoReinscripcionCodigo,
		CAST(0 AS bit) AS aprobado
	FROM(
		SELECT
			ALU_AlumnoId AS id,
			ALU_Codigo AS codigo,
			ALU_CodigoUDG AS codigoUDG,
			ALU_Nombre AS nombre,
			ALU_PrimerApellido AS primerApellido,
			ALU_SegundoApellido AS segundoApellido,
			PROGRU_GrupoId AS grupoId,
			PROGRU_PROGI_ProgramaIdiomaId AS cursoId,
			PROGRU_Nivel AS nivel,
			INS_FechaCreacion AS fechaCreacionInscripcion,
			MAX(INS_FechaCreacion) OVER(PARTITION BY ALU_AlumnoId,PROGRU_PROGI_ProgramaIdiomaId) AS fechaCreacionUltimaInscripcion,
			PROGRU_PAMOD_ModalidadId AS modalidadId,
			PROGRU_PAMODH_PAModalidadHorarioId AS horarioId,
			OV_SUC_SucursalId AS sucursalId,
			PROGRU_Grupo AS numeroGrupo,
			PROGRU_CalificacionMinima AS calificacionMinima,
			PROGRU_CMM_EstatusId AS grupoEstatus
		FROM [dbo].[Alumnos]
		INNER JOIN [dbo].[Inscripciones] ON INS_ALU_AlumnoId = ALU_AlumnoId
		INNER JOIN [dbo].[ProgramasGrupos] ON PROGRU_GrupoId = INS_PROGRU_GrupoId
		INNER JOIN [dbo].[OrdenesVentaDetalles] ON OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaDetalleId
		INNER JOIN [dbo].[OrdenesVenta] ON OV_OrdenVentaId = OVD_OV_OrdenVentaId
		WHERE
			ALU_Activo = 1
			AND INS_CMM_EstatusId IN (2000510,2000511)
	) AS AlumnosCursos
	INNER JOIN [dbo].[AlumnosGrupos] ON ALUG_ALU_AlumnoId = id AND ALUG_PROGRU_GrupoId = grupoId
	INNER JOIN [dbo].[ProgramasGrupos] ON PROGRU_GrupoId = grupoId
	INNER JOIN [dbo].[ProgramasIdiomas] ON PROGI_ProgramaIdiomaId = cursoId
	LEFT JOIN [dbo].[InscripcionesSinGrupo]
		ON INSSG_ALU_AlumnoId = id
		AND INSSG_CMM_IdiomaId = INSSG_CMM_IdiomaId
		AND INSSG_CMM_EstatusId IN (2000540,2000541)
	INNER JOIN [dbo].[Programas] ON PROG_ProgramaId = PROGI_PROG_ProgramaId
	INNER JOIN [dbo].[ControlesMaestrosMultiples] ON CMM_ControlId = PROGI_CMM_Idioma
	INNER JOIN [dbo].[PAModalidades] ON PAMOD_ModalidadId = modalidadId
	INNER JOIN [dbo].[PAModalidadesHorarios] ON PAMODH_PAModalidadHorarioId = horarioId
	INNER JOIN [dbo].[Articulos]
		ON ART_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
		AND ART_PAMOD_ModalidadId = modalidadId
		AND(
			(PROGI_AgruparListadosPreciosPorTipoGrupo = 0 AND ART_CMM_TipoGrupoId IS NULL)
			OR (PROGI_AgruparListadosPreciosPorTipoGrupo = 1 AND ART_CMM_TipoGrupoId = PROGRU_CMM_TipoGrupoId)
		)
	WHERE
		fechaCreacionInscripcion = fechaCreacionUltimaInscripcion
		AND ALUG_CMM_EstatusId IN (2000673,2000674,2000676) -- (Sin derecho, Desertor o Reprobado)
		AND INSSG_InscripcionId IS NULL
		AND grupoEstatus = 2000621
		AND sucursalId = @sucursalId
		AND CONCAT(codigo,'|',codigoUDG,'|',nombre,' ',primerApellido,' ' + segundoApellido,' ',nombre,'|',CONCAT(PROG_Codigo,' ',CMM_Valor),'|',PAMOD_Nombre,'|',PAMODH_Horario,'|Reprobado') LIKE @textoBuscar
)
GO

CREATE OR ALTER FUNCTION [dbo].[fn_ReinscripcionProjection_Alumnos](@sucursalId int, @textoBuscar varchar(255)) RETURNS TABLE WITH SCHEMABINDING AS RETURN(

	SELECT
		id,
		codigo,
		becaId,
		becaCodigo,
		codigoUDG,
		nombre,
		primerApellido,
		segundoApellido,
		curso,
		modalidad,
		horario,
		nivelReinscripcion,
		calificacion,
		calificacionMinima,
		limiteFaltasExcedido,
		idiomaId,
		programaId,
		modalidadId,
		horarioId,
		sucursalId,
		articuloId,
		numeroGrupo,
		grupoReinscripcionId,
		grupoReinscripcionCodigo,
		aprobado
	FROM [dbo].[fn_ReinscripcionProjection_Alumnos_Aprobados](@sucursalId, @textoBuscar)

	UNION ALL

	SELECT
		id,
		codigo,
		becaId,
		becaCodigo,
		codigoUDG,
		nombre,
		primerApellido,
		segundoApellido,
		curso,
		modalidad,
		horario,
		nivelReinscripcion,
		calificacion,
		calificacionMinima,
		limiteFaltasExcedido,
		idiomaId,
		programaId,
		modalidadId,
		horarioId,
		sucursalId,
		articuloId,
		numeroGrupo,
		grupoReinscripcionId,
		grupoReinscripcionCodigo,
		aprobado
	FROM [dbo].[fn_ReinscripcionProjection_Alumnos_Reprobados](@sucursalId, @textoBuscar)

)
GO