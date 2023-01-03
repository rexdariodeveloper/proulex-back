/**
* Created by Angel Daniel Hernández Silva on 27/07/2022.
*/

CREATE OR ALTER VIEW [dbo].[VW_InscripcionesPendientesPCP_Alumnos] WITH SCHEMABINDING AS

    SELECT
        ALU_AlumnoId as id,
        ALU_Codigo AS codigo,
        ALU_Nombre AS nombre,
        ALU_PrimerApellido AS primerApellido,
        ALU_SegundoApellido AS segundoApellido,
        ALU_Dependencia AS dependencia,
        CONCAT(PROG_Codigo,' ',idioma.CMM_Valor) AS curso,
        PROGRU_GrupoId AS grupoId,
        PROGRU_Codigo AS grupo,
        CAST((CASE WHEN ALU_CMM_TipoAlumnoId = 2000591 THEN 1 ELSE 0 END) AS bit) AS esCandidato,
        PROGI_CMM_Idioma AS  idiomaId,
        SUC_SucursalId AS sucursalId,
        ALU_Folio AS folio,

        CONCAT(ALU_Codigo,'|',ALU_Nombre,' ',ALU_PrimerApellido,' ' + ALU_SegundoApellido,' ',ALU_Nombre,'|',CONCAT(PROG_Codigo,' ',idioma.CMM_Valor),'|',COALESCE(ALU_Dependencia,''),'|',PROGRU_Codigo,'|',ALU_Folio) AS textoBusqueda
    FROM [dbo].[Alumnos]
    INNER JOIN [dbo].[Inscripciones] ON INS_ALU_AlumnoId = ALU_AlumnoId
    INNER JOIN [dbo].[ProgramasGrupos] ON PROGRU_GrupoId = INS_PROGRU_GrupoId
    INNER JOIN [dbo].[Sucursales] ON SUC_SucursalId = PROGRU_SUC_SucursalId
    INNER JOIN [dbo].[OrdenesVentaDetalles] ON OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaDetalleId
    INNER JOIN [dbo].[OrdenesVenta] ON OV_OrdenVentaId = OVD_OV_OrdenVentaId
    INNER JOIN [dbo].[ProgramasIdiomas] ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	INNER JOIN [dbo].[Programas] ON PROG_ProgramaId = PROGI_PROG_ProgramaId
    INNER JOIN [dbo].[ControlesMaestrosMultiples] idioma ON PROGI_CMM_Idioma = idioma.CMM_ControlId
    WHERE
		PROG_PCP = 1
        AND INS_CMM_EstatusId = 2000511 -- PENDIENTES DE PAGO
        AND OV_MPPV_MedioPagoPVId IS NULL

GO


IF EXISTS(SELECT * FROM sys.indexes WHERE name='id' AND object_id = OBJECT_ID('dbo.VW_InscripcionesPendientesPCP_Alumnos'))
    DROP INDEX id ON [dbo].[VW_InscripcionesPendientesPCP_Alumnos]
GO

CREATE UNIQUE CLUSTERED INDEX [id] ON [dbo].[VW_InscripcionesPendientesPCP_Alumnos]([id])
GO

CREATE FULLTEXT INDEX ON [dbo].[VW_InscripcionesPendientesPCP_Alumnos]([textoBusqueda])
KEY INDEX [id]
GO