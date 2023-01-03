SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER   VIEW [dbo].[VW_LISTADO_ALERTAS]
AS
select
	  ALD_AlertaDetalleId as id,
      ALE_ReferenciaProcesoId as procesoId,
	  ALE_FechaInicio as fecha,
	  ALE_CodigoTramite as codigo,
	  (select SUC_Nombre from Sucursales where SUC_SucursalId = ACE_SUC_SucursalId) as sede,
	  ACE_SUC_SucursalId as sedeId,
	  (select CMM_Valor from ControlesMaestrosMultiples where CMM_ControlId = ACE_CMM_TipoAlertaId) as tipo,
	  ACE_CMM_TipoAlertaId as tipoId,
	  CONCAT(USU_Nombre,' ',USU_PrimerApellido,' ',USU_SegundoApellido) as inicadaPor,
	  ALD_USU_UsuarioId as usuarioId,
	  (select CMM_Valor from ControlesMaestrosMultiples where CMM_ControlId = ALD_CMM_EstatusId) as estatus,
	  ALD_CMM_EstatusId as estatusId,
	  ALC_TablaReferencia as tabla
from Alertas
INNER JOIN AlertasDetalles ON ALE_AlertaId = ALD_ALE_AlertaId
INNER JOIN AlertasConfigEtapa ON ACE_AlertaConfiguracionEtapaId = ALD_ACE_AlertaConfiguracionEtapaId
inner join AlertasConfig ON ACE_ALC_AlertaCId = ALC_AlertaCId
INNER JOIN Usuarios ON USU_UsuarioId = ALE_USU_CreadoPorId;
GO

