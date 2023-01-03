/****** Object:  StoredProcedure [dbo].[sp_AlertasIniciar]    Script Date: 05/01/2021 07:56:38 a. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_ActualizaProcedimientoAlerta]  @alerta_c_id int, @referencia_proceso_id int,@valorEstatus varchar(150)  
AS
BEGIN

	SET NUMERIC_ROUNDABORT OFF;
	SET ANSI_PADDING, ANSI_WARNINGS, CONCAT_NULL_YIELDS_NULL, ARITHABORT, QUOTED_IDENTIFIER, ANSI_NULLS ON;
	IF EXISTS (SELECT * FROM tempdb..sysobjects WHERE id=OBJECT_ID('tempdb..#tmpErrors')) DROP TABLE #tmpErrors;
	CREATE TABLE #tmpErrors (Error int);
	SET XACT_ABORT ON;
	SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
	BEGIN TRANSACTION;

	DECLARE @tablaReferencia varchar(100);
	DECLARE @campoIdReferencia varchar(100);
	DECLARE @campoEstadoReferencia varchar(100);
	DECLARE @estadoId int;
	DECLARE @idReferencia int;
	DECLARE @campoController varchar(200);
	DECLARE @queryActualizar varchar(1000);

	SET @tablaReferencia = (SELECT ALC_TablaReferencia FROM AlertasConfig WHERE ALC_AlertaCId = @alerta_c_id);
	SET @campoIdReferencia =(SELECT ALC_CampoId FROM AlertasConfig WHERE ALC_AlertaCId = @alerta_c_id);
	SET @campoController = (SELECT ALC_CampoController FROM AlertasConfig WHERE ALC_AlertaCId = @alerta_c_id);
	SET @idReferencia = @referencia_proceso_id;
	SET  @campoEstadoReferencia = (SELECT ALC_CampoEstado FROM AlertasConfig WHERE ALC_AlertaCId = @alerta_c_id);
	 	
	DECLARE @ids table(id int);


	IF @@ERROR<>0 AND @@TRANCOUNT>0 ROLLBACK TRANSACTION;
	IF @@TRANCOUNT=0 BEGIN INSERT INTO #tmpErrors (Error) SELECT 1 BEGIN TRANSACTION END;
	
	select  
		@estadoId= CMM_ControlId 
	 from 
		ControlesMaestrosMultiples 
	 where 
		CMM_Control = @campoController 
	 and 
	    CMM_Valor = @valorEstatus;

		SET @queryActualizar = 'UPDATE ' + @tablaReferencia + ' SET ' + @campoEstadoReferencia + ' = ' + @estadoId + ' WHERE ' + @campoIdReferencia + ' = ' + @referencia_proceso_id ;

		-- Actualizar estado del trámite
		EXEC(@queryActualizar);
	
	IF EXISTS (SELECT * FROM #tmpErrors) ROLLBACK TRANSACTION;

	IF @@TRANCOUNT>0 BEGIN
	COMMIT TRANSACTION
	END;
	DROP TABLE #tmpErrors;

END;
GO