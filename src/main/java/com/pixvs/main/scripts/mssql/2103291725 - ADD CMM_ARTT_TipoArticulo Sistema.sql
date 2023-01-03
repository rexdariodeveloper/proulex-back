SET IDENTITY_INSERT ControlesMaestrosMultiples ON
GO
INSERT INTO ControlesMaestrosMultiples
(
    CMM_ControlId,
    CMM_Control,
    CMM_Valor,
    CMM_Activo,
    CMM_Referencia,
    CMM_Sistema,
    CMM_USU_CreadoPorId,
    CMM_FechaCreacion,
    CMM_USU_ModificadoPorId,
    CMM_FechaModificacion
)
VALUES
(
    2000033, -- CMM_ControlId - int
    'CMM_ARTT_TipoArticulo', -- CMM_Control - varchar
    'Sistema', -- CMM_Valor - varchar
    1, -- CMM_Activo - bit
    NULL, -- CMM_Referencia - varchar
    1, -- CMM_Sistema - bit
    NULL, -- CMM_USU_CreadoPorId - int
    GETDATE(), -- CMM_FechaCreacion - datetime2
    NULL, -- CMM_USU_ModificadoPorId - int
    NULL -- CMM_FechaModificacion - datetime2
)
GO
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO

SET IDENTITY_INSERT ArticulosTipos ON
GO
INSERT INTO ArticulosTipos
(
    ARTT_ArticuloTipoId,
    ARTT_CMM_TipoId,
    ARTT_Descripcion,
    ARTT_Activo
)
VALUES
(
    5, -- ARTT_ArticuloTipoId - int
    2000033, -- ARTT_CMM_TipoId - int
    'Sistema', -- ARTT_Descripcion - varchar
    1 -- ARTT_Activo - bit
)
GO
SET IDENTITY_INSERT ArticulosTipos OFF
GO

UPDATE Articulos
  SET
      ART_ARTT_TipoArticuloId = 5,
      ART_ARTST_ArticuloSubtipoId = NULL,
	  ART_Inventariable = 0
WHERE ART_CodigoArticulo LIKE 'SRV-%'
GO