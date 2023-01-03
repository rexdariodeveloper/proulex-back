/**
* Created by Angel Daniel Hernández Silva on 04/02/2022.
* Object:  ALTER VIEW [dbo].[VW_InscripcionesPendientesPCP_Alumnos]
*/

CREATE OR ALTER VIEW [dbo].[VW_InscripcionesPendientesPCP_Alumnos] WITH SCHEMABINDING AS

    SELECT
        ALU_AlumnoId as id,
        ALU_Codigo AS codigo,
        ALU_Nombre AS nombre,
        ALU_PrimerApellido AS primerApellido,
        ALU_SegundoApellido AS segundoApellido,
        '' AS dependencia,
        '' AS curso,
        PROGRU_GrupoId AS grupoId,
        PROGRU_Codigo AS grupo,
        CAST((CASE WHEN ALU_CMM_TipoAlumnoId = 2000591 THEN 1 ELSE 0 END) AS bit) AS esCandidato,
        PROGI_CMM_Idioma AS  idiomaId,
        SUC_SucursalId AS sucursalId,

        CONCAT(ALU_Codigo,'|',ALU_Nombre,' ',ALU_PrimerApellido,' ' + ALU_SegundoApellido,' ',ALU_Nombre/*,'|',Preparatoria.CMM_Valor,'|',COALESCE(ALU_BachilleratoTecnologico,'')*/,'|',PROGRU_Codigo) AS textoBusqueda
    FROM [dbo].[Alumnos]
    INNER JOIN [dbo].[Inscripciones] ON INS_ALU_AlumnoId = ALU_AlumnoId
    INNER JOIN [dbo].[ProgramasGrupos] ON PROGRU_GrupoId = INS_PROGRU_GrupoId
    INNER JOIN [dbo].[Sucursales] ON SUC_SucursalId = PROGRU_SUC_SucursalId
    INNER JOIN [dbo].[OrdenesVentaDetalles] ON OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaDetalleId
    INNER JOIN [dbo].[OrdenesVenta] ON OV_OrdenVentaId = OVD_OV_OrdenVentaId
    INNER JOIN [dbo].[ProgramasIdiomas] ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	INNER JOIN [dbo].[Programas] ON PROG_ProgramaId = PROGI_PROG_ProgramaId
    WHERE
		PROG_PCP = 1
        AND INS_CMM_EstatusId = 2000511 -- PENDIENTES DE PAGO
        AND OV_MPPV_MedioPagoPVId IS NULL

GO

create unique clustered index id on dbo.VW_InscripcionesPendientesPCP_Alumnos (id)
GO
​
create fulltext index on VW_InscripcionesPendientesPCP_Alumnos(textoBusqueda)
key index id
GO