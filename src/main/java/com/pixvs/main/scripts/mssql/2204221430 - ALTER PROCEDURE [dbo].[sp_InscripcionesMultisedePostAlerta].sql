SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER PROCEDURE [dbo].[sp_InscripcionesMultisedePostAlerta]  (@inscripcionId int, @estatusId int, @estatusProceso int OUT, @logTipo int OUT)
AS
BEGIN
	
	DECLARE @estatusInscripcionSinGrupoPagada int = 2000540;
	DECLARE @estatusRechazado int = 2000512;

	IF @estatusId = @estatusRechazado BEGIN
		INSERT INTO InscripcionesSinGrupo
			(INSSG_OVD_OrdenVentaDetalleId, 
			 INSSG_ALU_AlumnoId, 
			 INSSG_SUC_SucursalId, 
			 INSSG_PROG_ProgramaId, 
			 INSSG_CMM_IdiomaId,
			 INSSG_PAMOD_ModalidadId, 
			 INSSG_PAMODH_PAModalidadHorarioId, 
			 INSSG_Nivel, 
			 INSSG_Grupo, 
			 INSSG_Comentario, 
			 INSSG_USU_CreadoPorId, 
			 INSSG_FechaCreacion, 
			 INSSG_CMM_EstatusId,
			 INSSG_EntregaLibrosPendiente)
		select 
			INS_OVD_OrdenVentaDetalleId, 
			INS_ALU_AlumnoId, 
			PROGRU_SUC_SucursalId, 
			PROGI_PROG_ProgramaId, 
			PROGI_CMM_Idioma, 
			PROGRU_PAMOD_ModalidadId, 
			PROGRU_PAMODH_PAModalidadHorarioId, 
			PROGRU_Nivel, 
			PROGRU_Grupo, 
			PROGRU_Comentarios, 
			PROGRU_USU_CreadoPorId, 
			GETDATE(), 
			@estatusInscripcionSinGrupoPagada,
			INS_EntregaLibrosPendiente
		from Inscripciones
		inner join ProgramasGrupos on INS_PROGRU_GrupoId = PROGRU_GrupoId
		inner join ProgramasIdiomas on PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
		where INS_InscripcionId = @inscripcionId
		
		SELECT @estatusProceso = NULL, @logTipo = NULL
	END
END