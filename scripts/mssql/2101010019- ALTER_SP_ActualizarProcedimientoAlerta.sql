SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER PROCEDURE [dbo].[sp_ActualizaProcedimientoAlerta]  @alerta_c_id int, @referencia_proceso_id int,@valorEstatus int  
AS
BEGIN

	SET NUMERIC_ROUNDABORT OFF;
	SET ANSI_PADDING, ANSI_WARNINGS, CONCAT_NULL_YIELDS_NULL, ARITHABORT, QUOTED_IDENTIFIER, ANSI_NULLS ON;
	IF EXISTS (SELECT * FROM tempdb..sysobjects WHERE id=OBJECT_ID('tempdb..#tmpErrors')) DROP TABLE #tmpErrors;
	CREATE TABLE #tmpErrors (Error int);
	SET XACT_ABORT ON;
	SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
	BEGIN TRANSACTION;

	DECLARE @configAlertaId int;
	DECLARE @tablaReferencia varchar(100);--TABLA REFERENCIA
	DECLARE @CampoId varchar(100)--CAMPO DE LA TABLA QUE GUARDA EL ID DE LA TABLA
	DECLARE @CampoEstatus varchar(100)--nombre del campo del la tabla donde se guarda el estatus
	DECLARE @estadoIdAutorizado int;--valor de estatus de rechazado
	DECLARE @estadoIdRechazado int;-- valor de estatus de aprobado
	DECLARE @idReferencia int;
	DECLARE @queryActualizar varchar(1000);

	SET @configAlertaId = (select ACE_ALC_AlertaCId from AlertasConfigEtapa where ACE_AlertaConfiguracionEtapaId =@alerta_c_id);

	SET @tablaReferencia = (SELECT ALC_TablaReferencia FROM AlertasConfig WHERE ALC_AlertaCId = @configAlertaId);
	SET @CampoId =(SELECT ALC_CampoId FROM AlertasConfig WHERE ALC_AlertaCId = @configAlertaId);
	SET @CampoEstatus = (SELECT ALC_CampoEstado FROM AlertasConfig WHERE ALC_AlertaCId = @configAlertaId);
	SET @estadoIdAutorizado = (SELECT ALC_CMM_EstadoAutorizado from AlertasConfig where ALC_AlertaCId =@configAlertaId);
	SET @estadoIdRechazado = (SELECT ALC_CMM_EstadoRechazado from AlertasConfig where ALC_AlertaCId = @configAlertaId);
	SET @idReferencia = @referencia_proceso_id;

	DECLARE @ids table(id int);


	IF @@ERROR<>0 AND @@TRANCOUNT>0 ROLLBACK TRANSACTION;
	IF @@TRANCOUNT=0 BEGIN INSERT INTO #tmpErrors (Error) SELECT 1 BEGIN TRANSACTION END;
	
		if(@valorEstatus = 1000154)/*AUTORIZADA*/
	    BEGIN
		   SET @queryActualizar = (select CONCAT('UPDATE',' ',@tablaReferencia,' ','SET',' ',@CampoEstatus,'=',@estadoIdAutorizado,' ','WHERE',' ',@CampoId,'=',@referencia_proceso_id))--'UPDATE ' + @tablaReferencia + ' SET ' + @CampoEstatus + ' = ' + @estadoIdAutorizado + ' WHERE ' + @CampoId + ' = ' + @referencia_proceso_id ;
	    END;
	
	    if(@valorEstatus=1000152) /*RECHAZADA*/
	    BEGIN
		  SET @queryActualizar = (select CONCAT('UPDATE',' ',@tablaReferencia,' ','SET',' ',@CampoEstatus,'=',@estadoIdRechazado,' ','WHERE',' ',@CampoId,'=',@referencia_proceso_id)) ---'UPDATE ' + @tablaReferencia + ' SET ' + @CampoEstatus + ' = ' + @estadoIdRechazado + ' WHERE ' + @CampoId + ' = ' + @referencia_proceso_id ;
	    END;

		-- Actualizar estado del trámite
		EXEC(@queryActualizar);
	
	IF EXISTS (SELECT * FROM #tmpErrors) ROLLBACK TRANSACTION;

	IF @@TRANCOUNT>0 BEGIN
	COMMIT TRANSACTION
	END;
	DROP TABLE #tmpErrors;

END;
