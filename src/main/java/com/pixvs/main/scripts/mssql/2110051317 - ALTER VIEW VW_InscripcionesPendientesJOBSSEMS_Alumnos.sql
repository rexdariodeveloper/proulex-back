/**
* Created by Angel Daniel Hernández Silva on 05/10/2021.
* Object:  ALTER VIEW [dbo].[VW_InscripcionesPendientesJOBSSEMS_Alumnos]
*/

CREATE OR ALTER VIEW [dbo].[VW_InscripcionesPendientesJOBSSEMS_Alumnos] WITH SCHEMABINDING AS

    SELECT
        ALU_AlumnoId as id,
        ALU_Codigo AS codigo,
        ALU_CodigoAlumnoUDG AS codigoAlumnoUDG,
        ALU_Nombre AS nombre,
        ALU_PrimerApellido AS primerApellido,
        ALU_SegundoApellido AS segundoApellido,
        Preparatoria.CMM_Valor AS preparatoria,
        COALESCE(ALU_BachilleratoTecnologico,'') AS bachilleratoTecnologico,
        PROGRU_GrupoId AS grupoId,
        PROGRU_Codigo AS grupo,
        CAST((CASE WHEN ALU_CMM_TipoAlumnoId = 2000591 THEN 1 ELSE 0 END) AS bit) AS esCandidato,
        PROGI_CMM_Idioma AS  idiomaId,
        SUC_SucursalId AS sucursalId,

        CONCAT(ALU_Codigo,'|',ALU_CodigoAlumnoUDG,'|',ALU_Nombre,' ',ALU_PrimerApellido,' ' + ALU_SegundoApellido,' ',ALU_Nombre,'|',Preparatoria.CMM_Valor,'|',COALESCE(ALU_BachilleratoTecnologico,''),'|',PROGRU_Codigo) AS textoBusqueda
    FROM [dbo].[Alumnos]
    INNER JOIN [dbo].[Inscripciones] ON INS_ALU_AlumnoId = ALU_AlumnoId
    INNER JOIN [dbo].[ControlesMaestrosMultiples] AS Preparatoria ON Preparatoria.CMM_ControlId = ALU_CMM_PreparatoriaJOBSId
    INNER JOIN [dbo].[ProgramasGrupos] ON PROGRU_GrupoId = INS_PROGRU_GrupoId
    INNER JOIN [dbo].[Sucursales] ON SUC_SucursalId = PROGRU_SUC_SucursalId
    INNER JOIN [dbo].[OrdenesVentaDetalles] ON OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaDetalleId
    INNER JOIN [dbo].[OrdenesVenta] ON OV_OrdenVentaId = OVD_OV_OrdenVentaId
    INNER JOIN [dbo].[ProgramasIdiomas] ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
    WHERE
        ALU_AlumnoJOBS = 1
        AND ALU_CMM_ProgramaJOBSId = 2000531 -- JOBS SEMS
        AND INS_CMM_EstatusId = 2000511 -- PENDIENTES DE PAGO
        AND OV_MPPV_MedioPagoPVId IS NULL

GO