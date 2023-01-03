SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER PROCEDURE [dbo].[sp_EliminarProyeccionGrupoCancelado]  (@ovCodigo varchar(100), @alumnoCodigo varchar(100))
AS
BEGIN

	DECLARE @codigoOV NVARCHAR(100) = @ovCodigo;
	DECLARE @codigoAlumno NVARCHAR(100) = @alumnoCodigo;

	DECLARE @tblBorrar TABLE(
	ovId INT,
	inscripcionId INT,
	alumnoGrupoId INT,
	alumnoAsistenciaId INT,
	alumnoExamenCalificacionId INT
	)

	INSERT INTO @tblBorrar
	SELECT
	OV_OrdenVentaId,
	INS_InscripcionId,
	ALUG_AlumnoGrupoId,
	AA_AlumnoAsistenciaId,
	AEC_AlumnoExamenCalificacionId
	FROM
	Alumnos
	inner join Inscripciones on INS_ALU_AlumnoId = ALU_AlumnoId
	inner join ProgramasGrupos on PROGRU_GrupoId = INS_PROGRU_GrupoId
	inner join OrdenesVentaDetalles on OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaDetalleId
	inner join OrdenesVenta on OV_OrdenVentaId = OVD_OV_OrdenVentaId
	inner join AlumnosGrupos on ALUG_INS_InscripcionId = INS_InscripcionId
	left join AlumnosAsistencias on AA_ALU_AlumnoId = ALU_AlumnoId and AA_PROGRU_GrupoId = INS_PROGRU_GrupoId
	left join AlumnosExamenesCalificaciones on AEC_ALU_AlumnoId = ALU_AlumnoId and AEC_PROGRU_GrupoId = INS_PROGRU_GrupoId
	WHERE
	OV_MPPV_MedioPagoPVId IS NULL
	AND (OV_Codigo = @codigoOV AND ALU_Codigo = @codigoAlumno)

	DELETE FROM AlumnosGrupos WHERE ALUG_AlumnoGrupoId IN (select alumnoGrupoId from @tblBorrar)
	DELETE FROM ProgramasGruposHistorial WHERE PROGRUH_INS_InscripcionId IN (select inscripcionId from @tblBorrar)
	DELETE FROM Inscripciones WHERE INS_InscripcionId IN (select inscripcionId from @tblBorrar)
	DELETE FROM OrdenesVentaDetalles WHERE OVD_OV_OrdenVentaId IN (select ovId from @tblBorrar)
	DELETE FROM OrdenesVenta WHERE OV_OrdenVentaId IN (select ovId from @tblBorrar)
	DELETE FROM AlumnosAsistencias WHERE AA_AlumnoAsistenciaId IN (select alumnoAsistenciaId from @tblBorrar)
	DELETE FROM AlumnosExamenesCalificaciones WHERE AEC_AlumnoExamenCalificacionId IN (select alumnoExamenCalificacionId from @tblBorrar)

END
GO