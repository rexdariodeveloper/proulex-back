SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/**
* Created by Angel Daniel Hernández Silva on 23/05/2022.
*/

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
        aprobado
    FROM [dbo].[fn_ReinscripcionProjection_Alumnos_Reprobados](@sucursalId, @textoBuscar)

)
