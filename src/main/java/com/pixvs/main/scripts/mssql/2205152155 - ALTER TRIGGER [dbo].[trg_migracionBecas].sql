SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER TRIGGER [dbo].[trg_migracionBecas] ON [dbo].[BECAS_ALUMNOS_RH] AFTER INSERT
AS
BEGIN
	DECLARE @ID [int]
	DECLARE @CODCURUDG [varchar](10)
	DECLARE @STATUSPLX [varchar](2)
	DECLARE @FECHAEXPIRACIONBECAUDG [date]
	DECLARE @NOMBREDESCUENTOUDG [varchar](50)
	DECLARE cur CURSOR FOR SELECT ID, CODCURUDG, STATUSPLX, FECHAEXPIRACIONBECAUDG, NOMBREDESCUENTOUDG FROM INSERTED
    OPEN cur
    FETCH NEXT FROM cur INTO @ID, @CODCURUDG, @STATUSPLX, @FECHAEXPIRACIONBECAUDG, @NOMBREDESCUENTOUDG
    WHILE @@FETCH_STATUS = 0
    BEGIN

		DECLARE @estatusId [int]
		DECLARE @programaIdiomaId [int]
		DECLARE @modalidadId [int]
		DECLARE @entidadBecaId [int]

		IF @STATUSPLX IS NULL BEGIN SET @estatusId = 2000570 END
		IF @STATUSPLX = N'AP' BEGIN SET @estatusId = 2000571 END
		IF @STATUSPLX = N'CA' BEGIN SET @estatusId = 2000572 END

		IF @NOMBREDESCUENTOUDG LIKE '%SUTUDG%'  BEGIN SET @entidadBecaId = 1 END
		IF @NOMBREDESCUENTOUDG LIKE '%STAUDEG%' BEGIN SET @entidadBecaId = 2 END

		SELECT @programaIdiomaId = PROGI_ProgramaIdiomaId, @modalidadId = modalidadId FROM
		(
			SELECT CODIGO_CURSO codigo, idioma.CMM_Valor idioma, programa.CMM_Valor programa, PAMOD_ModalidadId modalidadId  FROM _SIAP_CURSOS
			INNER JOIN ControlesMaestrosMultiples programa ON CMM_IdiomaId = programa.CMM_ControlId
			INNER JOIN ControlesMaestrosMultiples idioma ON CMM_ProgramaId = idioma.CMM_ControlId
		) CURSOS_SIAP
		LEFT JOIN 
		(
			select PROGI_ProgramaIdiomaId, idioma.CMM_Valor idioma, PROG_Codigo programa  from ProgramasIdiomas 
			INNER JOIN ControlesMaestrosMultiples idioma on PROGI_CMM_Idioma = CMM_ControlId
			INNER JOIN Programas on PROGI_PROG_ProgramaId = PROG_ProgramaId
		) PROGI ON CURSOS_SIAP.idioma = PROGI.idioma AND CURSOS_SIAP.programa = PROGI.programa
		WHERE
			CURSOS_SIAP.codigo = @CODCURUDG;

		IF @programaIdiomaId IS NOT NULL AND @modalidadId IS NOT NULL
		BEGIN
			INSERT INTO [dbo].[BecasUDG](
				BECU_CodigoBeca,
				BECU_CodigoEmpleado,
				BECU_Nombre,
				BECU_PrimerApellido,
				BECU_SegundoApellido,
				BECU_Parentesco,
				BECU_Descuento,
				BECU_PROGI_ProgramaIdiomaId,
				BECU_Nivel,
				BECU_PAMOD_ModalidadId,
				BECU_FirmaDigital,
				BECU_FechaAlta,
				BECU_CMM_EstatusId,
				BECU_CMM_TipoId,
				BECU_CodigoAlumno,
				BECU_BECS_BecaSolicitudId,
				BECU_Comentarios,
				BECU_CMM_EntidadId,
				BECU_FechaExpiracion,
				BECU_SIAPId,
				BECU_ENBE_EntidadBecaId
			)
			SELECT 
				CODIGOBECAUDG, --BECU_CodigoBeca
				CODIGOEMPLEADOUDG, --BECU_CodigoEmpleado
				NOMBRE,--BECU_Nombre
				PATERNO,--BECU_PrimerApellido
				MATERNO,--BECU_SegundoApellido
				PARENTESCOUDG,--BECU_Parentesco
				DESCUENTOUDG / 100.0,--BECU_Descuento
				@programaIdiomaId, --BECU_PROGI_ProgramaIdiomaId
				NIVELPLX, --BECU_Nivel
				@modalidadId, --BECU_PAMOD_ModalidadId
				FIRMADIGITALUDG, --BECU_FirmaDigital
				CAST(CONCAT_WS(' ',FORMAT(FECHAALTABECAUDG, 'yyyy-MM-dd'),HORAALTABECAUDG) AS datetime2), --BECU_FechaAlta
				@estatusId, --BECU_CMM_EstatusId
				2000581, --BECU_CMM_TipoId
				NULL, --BECU_CodigoAlumno
				NULL, --BECU_BECS_BecaSolicitudId
				NULL, --BECU_Comentarios
				NULL, --BECU_CMM_EntidadId
				@FECHAEXPIRACIONBECAUDG, --BECU_FechaExpiracion
				ID, --BECU_SIAPId
				@entidadBecaId --BECU_ENBE_EntidadBecaId
			FROM INSERTED WHERE ID = @ID;
		END
	  
	  FETCH NEXT FROM cur INTO @ID, @CODCURUDG, @STATUSPLX, @FECHAEXPIRACIONBECAUDG, @NOMBREDESCUENTOUDG
    END
    CLOSE cur
    DEALLOCATE cur
END
