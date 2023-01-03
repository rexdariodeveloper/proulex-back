/********************************************/
/***** Vista VW_LISTADO_PROGRAMAS_METAS *****/
/********************************************/

CREATE OR ALTER VIEW [dbo].[VW_Listado_ProgramasMetas] AS

    SELECT
        PM_ProgramaMetaId AS id,
        PM_Codigo AS codigo,
        PAC_Nombre AS nombre,
        PACIC_Nombre AS ciclo,
        COALESCE(SUM(PMD_Meta),0) AS meta,
		0 AS inscripciones,
		CASE WHEN PM_Activo = 1 THEN 'activo' ELSE 'borrado' END as activo
    FROM ProgramasMetas
    INNER JOIN ProgramacionAcademicaComercial ON PAC_ProgramacionAcademicaComercialId = PM_PAC_ProgramacionAcademicaComercialId
    INNER JOIN PACiclos ON PACIC_CicloId = PAC_PACIC_CicloId
    INNER JOIN ProgramasMetasDetalles ON PMD_PM_ProgramaMetaId = PM_ProgramaMetaId
    GROUP BY PM_ProgramaMetaId, PM_Codigo, PAC_Nombre, PACIC_Nombre, PM_Activo

GO