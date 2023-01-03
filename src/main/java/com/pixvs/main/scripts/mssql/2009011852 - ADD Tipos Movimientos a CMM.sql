SET IDENTITY_INSERT ControlesMaestrosMultiples ON
INSERT INTO ControlesMaestrosMultiples
(
    CMM_ControlId,CMM_Control,CMM_Valor,CMM_Activo,CMM_Referencia,CMM_Sistema,CMM_USU_CreadoPorId,CMM_FechaCreacion,CMM_USU_ModificadoPorId,CMM_FechaModificacion)
VALUES
(
    2000091,'CMM_IM_TipoSpill','Robo',1,NULL,1,NULL,GETDATE(),NULL,NULL
),
(
    2000092,'CMM_IM_TipoSpill','Spill',1,NULL,1,NULL,GETDATE(),NULL,NULL
)
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO