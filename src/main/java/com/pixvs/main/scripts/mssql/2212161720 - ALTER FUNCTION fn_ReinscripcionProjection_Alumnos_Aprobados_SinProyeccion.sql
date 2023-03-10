/**
* Created by Angel Daniel Hernández Silva on 18/07/2022.
*/

CREATE OR ALTER FUNCTION [dbo].[fn_ReinscripcionProjection_Alumnos_Aprobados_SinProyeccion](@alumnoId int, @programaId int, @idiomaId int, @sucursalId int) RETURNS TABLE WITH SCHEMABINDING AS RETURN(
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
            AND INS_CMM_EstatusId IN (2000510,2000511) -- (Pagada, Pendiente de pago)
            AND (@alumnoId IS NULL OR ALU_AlumnoId = @alumnoId)
    ) AS AlumnosCursos
    INNER JOIN [dbo].[AlumnosGrupos] ON ALUG_ALU_AlumnoId = id AND ALUG_PROGRU_GrupoId = grupoId
    INNER JOIN [dbo].[ProgramasIdiomas] ON PROGI_ProgramaIdiomaId = cursoId
    INNER JOIN [dbo].[Programas] ON PROG_ProgramaId = PROGI_PROG_ProgramaId
    INNER JOIN [dbo].[ControlesMaestrosMultiples] ON CMM_ControlId = PROGI_CMM_Idioma
    INNER JOIN [dbo].[PAModalidades] ON PAMOD_ModalidadId = modalidadId
    INNER JOIN [dbo].[PAModalidadesHorarios] ON PAMODH_PAModalidadHorarioId = horarioId
    INNER JOIN [dbo].[ProgramasGrupos] ON PROGRU_GrupoId = grupoId
    INNER JOIN [dbo].[Articulos]
        ON ART_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
		AND ART_PAMOD_ModalidadId = modalidadId
		AND(
			(PROGI_AgruparListadosPreciosPorTipoGrupo = 0 AND ART_CMM_TipoGrupoId IS NULL)
			OR (PROGI_AgruparListadosPreciosPorTipoGrupo = 1 AND ART_CMM_TipoGrupoId = PROGRU_CMM_TipoGrupoId)
		)
    WHERE
        fechaCreacionInscripcion = fechaCreacionUltimaInscripcion
        AND ALUG_CMM_EstatusId = 2000675 -- Aprobado
        AND grupoEstatus = 2000621
        AND (@programaId IS NULL OR PROG_ProgramaId = @programaId)
        AND (@idiomaId IS NULL OR PROGI_CMM_Idioma = @idiomaId)
)
GO