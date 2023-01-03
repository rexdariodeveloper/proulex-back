SET IDENTITY_INSERT ControlesMaestrosMultiples ON
INSERT INTO ControlesMaestrosMultiples
(
    CMM_ControlId,CMM_Control,CMM_Valor,CMM_Activo,CMM_Referencia,CMM_Sistema,CMM_USU_CreadoPorId,CMM_FechaCreacion,CMM_USU_ModificadoPorId,CMM_FechaModificacion)
VALUES
(
    2000080,'CMM_PED_EstatusPedido','Guardado',1,NULL,1,NULL,GETDATE(),NULL,NULL
),
(
    2000081,'CMM_PED_EstatusPedido','Por autorizar',1,NULL,1,NULL,GETDATE(),NULL,NULL
),
(
    2000082,'CMM_PED_EstatusPedido','En revisión',1,NULL,1,NULL,GETDATE(),NULL,NULL
),
(
    2000083,'CMM_PED_EstatusPedido','Rechazado',1,NULL,1,NULL,GETDATE(),NULL,NULL
),
(
    2000084,'CMM_PED_EstatusPedido','Cancelado',1,NULL,1,NULL,GETDATE(),NULL,NULL
),
(
    2000085,'CMM_PED_EstatusPedido','Por surtir',1,NULL,1,NULL,GETDATE(),NULL,NULL
),
(
    2000086,'CMM_PED_EstatusPedido','Surtido parcial',1,NULL,1,NULL,GETDATE(),NULL,NULL
),
(
    2000087,'CMM_PED_EstatusPedido','Surtido',1,NULL,1,NULL,GETDATE(),NULL,NULL
),
(
    2000088,'CMM_PED_EstatusPedido','Cerrado',1,NULL,1,NULL,GETDATE(),NULL,NULL
),
(
    2000089,'CMM_PED_EstatusPedido','Borrado',1,NULL,1,NULL,GETDATE(),NULL,NULL
)
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO