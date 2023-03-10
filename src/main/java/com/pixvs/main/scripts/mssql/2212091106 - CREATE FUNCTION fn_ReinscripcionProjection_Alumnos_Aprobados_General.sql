/**
* Created by Angel Daniel Hernández Silva on 04/11/2022.
*/

CREATE OR ALTER FUNCTION [dbo].[fn_ReinscripcionProjection_Alumnos_Aprobados_General](@alumnoId int, @programaId int, @idiomaId int) RETURNS TABLE WITH SCHEMABINDING AS RETURN(
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
        AND (
            (PROGI_AgruparListadosPreciosPorTipoGrupo = 1 AND ART_CMM_TipoGrupoId = GrupoActual.PROGRU_CMM_TipoGrupoId)
            OR (PROGI_AgruparListadosPreciosPorTipoGrupo = 0 AND ART_CMM_TipoGrupoId IS NULL)
        )
    WHERE
        INS_CMM_EstatusId = 2000511 -- PENDIENTES DE PAGO
        AND OV_MPPV_MedioPagoPVId IS NULL -- Al no tener medio de pago significa que no han pasado por el PV
        AND CMMSucursalJOBS.CMM_ControlId IS NULL -- Omitir sucursal JOBS
        AND CMMSucursalJOBSSEMS.CMM_ControlId IS NULL -- Omitir sucursal JOBS SEMS
        AND COALESCE(PROG_PCP,0) = 0 -- Omitir grupos PCP
        AND GrupoAnterior.PROGRU_PGINCG_ProgramaIncompanyId IS NULL -- Omitir grupos in company
        AND ALUG_CMM_EstatusId  = 2000675 -- Aprobado
        AND (@alumnoId IS NULL OR ALU_AlumnoId = @alumnoId)
        AND (@programaId IS NULL OR PROG_ProgramaId = @programaId)
        AND (@idiomaId IS NULL OR PROGI_CMM_Idioma = @idiomaId)
)
GO