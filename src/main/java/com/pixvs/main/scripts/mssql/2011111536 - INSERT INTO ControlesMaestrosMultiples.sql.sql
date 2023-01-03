-- ============================================= 
-- Author:		PIXVS-FRANCISCO 
-- Create date: 2020/11/11 
-- Description:	 2 INSERT INTO ControlesMaestrosMultiples.sql"" 
-- --------------------------------------------- 
-- 
-- 
SET IDENTITY_INSERT ControlesMaestrosMultiples ON
INSERT INTO ControlesMaestrosMultiples
(
    CMM_ControlId,CMM_Control,CMM_Valor,CMM_Activo,CMM_Referencia,CMM_Sistema,CMM_USU_CreadoPorId,CMM_FechaCreacion,CMM_USU_ModificadoPorId,CMM_FechaModificacion)
VALUES
(
    2000221,'CMM_CXPS_TipoSolicitud','Servicio',1,NULL,1,NULL,GETDATE(),NULL,NULL
),
(
    2000222,'CMM_CXPS_TipoSolicitud','Arrendamiento',1,NULL,1,NULL,GETDATE(),NULL,NULL
)
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO