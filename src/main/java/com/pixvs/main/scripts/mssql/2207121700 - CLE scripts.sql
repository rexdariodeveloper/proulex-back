ALTER TABLE [dbo].[ProgramasGrupos]
ADD [PROGRU_CLE_FechaUltimaActualizacion] [datetime2](7) NULL

GO

ALTER TABLE [dbo].[ProgramasGrupos]
ADD [PROGRU_CLE_GrupoProfesorId] int NULL

GO

ALTER TABLE [dbo].[ProgramasGrupos]
ADD [PROGRU_CLE_ProfesorId] int NULL

GO


ALTER TABLE [dbo].[ProgramasGrupos]
ADD [PROGRU_CLE_GrupoEstudiantesId] int NULL

GO


ALTER TABLE [dbo].[ProgramasIdiomasLibrosMateriales]
ADD [PROGILM_CLE_ProfesorResourceId] varchar(100) NULL
GO

ALTER TABLE [dbo].[ProgramasIdiomasLibrosMateriales]
ADD [PROGILM_CLE_ProfesorResourceFechaFin] varchar(50)  NULL
GO


ALTER TABLE [dbo].[ProgramasIdiomasLibrosMateriales]
ADD [PROGILM_CLE_EstudianteResourceId] varchar(100) NULL
GO


ALTER TABLE [dbo].[ProgramasIdiomasLibrosMateriales]
ADD [PROGILM_CLE_EstudianteResourceFechaFin] varchar(50) NULL
GO

ALTER TABLE [dbo].[Empleados]
ADD [EMP_CLE_UsuarioId] [int] NULL
GO

ALTER TABLE [dbo].[Alumnos]
ADD [ALU_CLE_UsuarioId] [int] NULL
GO

ALTER TABLE [dbo].AlumnosGrupos
ADD [ALUG_CLE_FechaUltimaActualizacion] [datetime2](7) NULL
GO

UPDATE ProgramasIdiomasLibrosMateriales
SET
PROGILM_CLE_ProfesorResourceId =  '9782090347050',
PROGILM_CLE_ProfesorResourceFechaFin = '2023-05-06', 
PROGILM_CLE_EstudianteResourceId = '9782090347036',
PROGILM_CLE_EstudianteResourceFechaFin = '2023-05-06'
WHERE PROGILM_ProgramaIdiomaLibroMaterialId = 372
GO


CREATE OR ALTER VIEW
       dbo.VW_CLE_ALUMNOSGRUPOS_SYNC
WITH
       SCHEMABINDING
AS

select ALUG_AlumnoGrupoId as alumnoGrupoId, ALUG_ALU_AlumnoId as alumnoId, ALUG_CLE_FechaUltimaActualizacion as fechaUltimaActualizacionCle, ALUG_PROGRU_GrupoId as grupoId, ALU_CLE_UsuarioId as usuarioCleId, PROGRU_CLE_GrupoProfesorId as grupoProfesorId, 
		PROGRU_CLE_GrupoEstudiantesId as grupoEstudiantesId,
		ALU_Nombre as alumnoNombre, CONCAT(ALU_PrimerApellido, COALESCE(' '+ALU_SegundoApellido, '')) as alumnoApellidos,
		ALU_CorreoElectronico as alumnoCorreoElectronico,
		ALU_Codigo as alumnoCodigo,
		ALU_FechaNacimiento as alumnoFechaNacimiento, 
		ALUG_CMM_EstatusId as alumnoGrupoEstatusId, 
		PROGRU_CLE_GrupoEstudiantesId as programaGrupoEstudianteCleId
from dbo.AlumnosGrupos 
left join dbo.ProgramasGrupos on ALUG_PROGRU_GrupoId = PROGRU_GrupoId
left join dbo.Alumnos on ALU_AlumnoId =  ALUG_ALU_AlumnoId
where ALUG_PROGRU_GrupoId in(
	select PROGRU_GrupoId 
	from dbo.ProgramasGrupos 
	where 
	PROGRU_CMM_EstatusId != 2000622 /*No este Cancelado*/
	AND PROGRU_CLE_FechaUltimaActualizacion is not null
	AND PROGRU_CLE_GrupoEstudiantesId is not null
	AND PROGRU_CLE_GrupoProfesorId is not null
)
--AND ALUG_CMM_EstatusId not in (2000674	/*Desertor*/, 2000677 /*Baja*/)

GO

CREATE OR ALTER VIEW
       dbo.VW_CLE_PROGRAMASGRUPOS_SYNC
WITH
       SCHEMABINDING
AS

	select PROGRU_GrupoId as id,
	PROGRU_Codigo as codigoGrupo,
	PROGRU_FechaInicio as fechaInicio,
	PROGRU_FechaFin as fechaFin,
	FORMAT(PROGRU_Nivel,'00') as nivel,
	PAMODH_Horario as horario,
	PROGRU_Cupo as cupo,
	PROGRU_SUC_SucursalId sedeId,
	PROGRU_PAC_ProgramacionAcademicaComercialId paId,
	PROGRU_PAMOD_ModalidadId modalidadId,
	PROGRU_FechaCreacion as fechaCreacion,
	PROGRU_CLE_FechaUltimaActualizacion as fechaUltimaActualizacion,
	CASE WHEN PROGRU_PAMOD_ModalidadId = 4/*INT*/ 
			THEN 4
			ELSE 9 END as duracionSemanas,
	PROGRU_CLE_GrupoProfesorId as grupoProfesorId,
	PROGRU_CLE_GrupoEstudiantesId as grupoEstudiantesId,
	PROGILM_CLE_ProfesorResourceId as profesorResourceId,	
	PROGILM_CLE_ProfesorResourceFechaFin as profesorResourceFechaFin,
	PROGILM_CLE_EstudianteResourceId as estudianteResourceId,
	PROGILM_CLE_EstudianteResourceFechaFin as estudianteResourceFechaFin, 
	PROGRU_EMP_EmpleadoId as profesorId,
	EMP_CLE_UsuarioId as profesorCleId,
	EMP_CodigoEmpleado as profesorCodigo, 
	EMP_FechaNacimiento as profesorFechaNacimiento,
	EMP_Nombre as profesorNombre,
	CONCAT(EMP_PrimerApellido, COALESCE(' '+EMP_SegundoApellido, '')) as profesorApellidos,
	EMP_CorreoElectronico as profesorCorreoElectronico,
	PROGRU_CLE_ProfesorId as programGrupoProfesorCleId,
	PROGRU_CMM_EstatusId as programaGrupoEstatusId

	FROM dbo.ProgramasGrupos pg
	LEFT JOIN dbo.PAModalidades on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
	LEFT JOIN dbo.PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
	LEFT JOIN dbo.ProgramasIdiomasLibrosMateriales on PROGILM_PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
													and PROGILM_Nivel = PROGRU_Nivel
													and PROGILM_Borrado = 0

	LEFT JOIN dbo.Empleados on PROGRU_EMP_EmpleadoId = EMP_EmpleadoId

	WHERE PROGRU_PROGI_ProgramaIdiomaId in (

			select PROGI_ProgramaIdiomaId 
			from dbo.ProgramasIdiomas 
			where PROGI_CMM_Idioma		= 28 /*Fránces*/
			and PROGI_PROG_ProgramaId	= 1 /*PDU*/
		)
	AND PROGRU_PAMOD_ModalidadId in (	4	/*INT*/, 
										8	/*SAA*/, 
										9	/*SAB*/)
	AND PROGRU_Nivel				= 1
	--AND PROGRU_CMM_EstatusId not in (2000621	/*Finalizado */,2000622	/*Cancelado*/)
	-- AND PROGRU_Activo = 1
	-- AND PROGRU_FechaCreacion >= '2022-07-12'

GO