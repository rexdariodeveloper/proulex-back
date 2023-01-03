SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[BECAS_ALUMNOS_RH](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[CODIGOPLX] [varchar](10),
	[CODIGOBECAUDG] [varchar](36),
	[CODIGOEMPLEADOUDG] [varchar](8),
	[PATERNO] [varchar](80),
	[MATERNO] [varchar](80),
	[NOMBRE] [varchar](80),
	[DESCUENTOUDG] [int],
	[SEDEUDG] [varchar](6),
	[NIVELUDG] [varchar](2),
	[HORARIOUDG] [varchar](11),
	[FECHAALTABECAUDG] [date],
	[HORAALTABECAUDG] [varchar](5),
	[CODCURUDG] [varchar](10),
	[PARENTESCOUDG] [varchar](20),
	[FIRMADIGITALUDG] [varchar](150),
	[ESTATUSUDG] [varchar](2),
	[CODIGOESTATUSUDG] [varchar](2),
	[SEDEPLX] [varchar](6),
	[NIVELPLX] [varchar](2),
	[HORARIOPLX] [varchar](11),
	[GRUPOPLX] [varchar](2),
	[CODCURPLX] [varchar](20),
	[FECHAINIPLX] [date],
	[FECHAFINPLX] [date],
	[FECHAAPLICACIONPLX] [date],
	[FECHAEXPIRACIONBECAUDG] [date],
	[FOLIOSIAPPLX] [varchar](10),
	[CALPLX] [varchar](3),
	[FECHACALPLX] [date],
	[STATUSPLX] [varchar](2),
	[CODIGOESTATUSPLX] [varchar](2),
	[NOMBREDESCUENTOUDG] [varchar](50),
	[ASISTENCIA] [varchar](600),
	[DIASEMANA] [varchar](7),
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[_SIAP_CURSOS] ADD [PAMOD_ModalidadId] [int]
GO

UPDATE [dbo].[_SIAP_CURSOS] SET [PAMOD_ModalidadId] = 1 WHERE [PAMOD_ModalidadId] IS NULL
GO

CREATE TRIGGER [trg_migracionBecas] ON [dbo].[BECAS_ALUMNOS_RH] AFTER INSERT
AS
BEGIN
	DECLARE @ID [int]
	DECLARE @CODCURUDG [varchar](10)
	DECLARE @STATUSPLX [varchar](2)
	DECLARE cur CURSOR FOR SELECT ID, CODCURUDG, STATUSPLX FROM INSERTED
    OPEN cur
    FETCH NEXT FROM cur INTO @ID, @CODCURUDG, @STATUSPLX
    WHILE @@FETCH_STATUS = 0
    BEGIN

		DECLARE @estatusId [int]
		DECLARE @programaIdiomaId [int]
		DECLARE @modalidadId [int]

		IF @STATUSPLX IS NULL BEGIN SET @estatusId = 2000570 END
		IF @STATUSPLX = N'AP' BEGIN SET @estatusId = 2000571 END
		IF @STATUSPLX = N'CA' BEGIN SET @estatusId = 2000572 END

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
      
		INSERT INTO [dbo].[BecasUDG]
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
			NULL --BECU_CMM_EntidadId
		FROM INSERTED WHERE ID = @ID;
	  
      FETCH NEXT FROM cur INTO @ID, @CODCURUDG, @STATUSPLX
    END
    CLOSE cur
    DEALLOCATE cur
END
GO

CREATE TRIGGER [trg_actualizacionBecas] ON [dbo].[BECAS_ALUMNOS_RH] FOR UPDATE
AS
BEGIN
	DECLARE @ID [int]
	DECLARE @CODCURUDG [varchar](10)
	DECLARE @STATUSPLX [varchar](2)
	DECLARE cur CURSOR FOR SELECT ID, CODCURUDG, STATUSPLX FROM INSERTED
    OPEN cur
    FETCH NEXT FROM cur INTO @ID, @CODCURUDG, @STATUSPLX
    WHILE @@FETCH_STATUS = 0
    BEGIN

		DECLARE @estatusId [int]

		IF @STATUSPLX IS NULL BEGIN SET @estatusId = 2000570 END
		IF @STATUSPLX = N'AP' BEGIN SET @estatusId = 2000571 END
		IF @STATUSPLX = N'CA' BEGIN SET @estatusId = 2000572 END

		UPDATE [dbo].[BecasUDG] 
			SET BECU_CMM_EstatusId = @estatusId 
		FROM 
			[dbo].[BecasUDG] 
			INNER JOIN INSERTED ON BECU_CodigoBeca = CODIGOBECAUDG
		WHERE
			ID = @ID;
	  
      FETCH NEXT FROM cur INTO @ID, @CODCURUDG, @STATUSPLX
    END
    CLOSE cur
    DEALLOCATE cur
END