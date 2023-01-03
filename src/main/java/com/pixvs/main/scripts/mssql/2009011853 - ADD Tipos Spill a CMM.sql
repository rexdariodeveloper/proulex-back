SET IDENTITY_INSERT ControlesMaestrosMultiples ON
INSERT INTO ControlesMaestrosMultiples
(
    CMM_ControlId,CMM_Control,CMM_Valor,CMM_Activo,CMM_Referencia,CMM_Sistema,CMM_USU_CreadoPorId,CMM_FechaCreacion,CMM_USU_ModificadoPorId,CMM_FechaModificacion)
VALUES
(
    2000016,'CMM_IM_TipoMovimiento','Surtir pedido',1,NULL,1,NULL,GETDATE(),NULL,NULL
),
(
    2000017,'CMM_IM_TipoMovimiento','Recibir pedido',1,NULL,1,NULL,GETDATE(),NULL,NULL
),
(
    2000018,'CMM_IM_TipoMovimiento','Devolver pedido',1,NULL,1,NULL,GETDATE(),NULL,NULL
)
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO