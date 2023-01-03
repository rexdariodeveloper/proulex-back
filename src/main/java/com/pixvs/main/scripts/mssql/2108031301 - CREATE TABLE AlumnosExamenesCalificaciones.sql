CREATE TABLE AlumnosExamenesCalificaciones (
    AEC_AlumnoExamenCalificacionId INT IDENTITY(1,1) PRIMARY KEY,
    AEC_ALU_AlumnoId INT NOT NULL 
	FOREIGN KEY REFERENCES Alumnos(ALU_AlumnoId),
    AEC_PROGRU_GrupoId INT NOT NULL 
	FOREIGN KEY REFERENCES ProgramasGrupos(PROGRU_GrupoId),
    AEC_PROGIED_ProgramaIdiomaExamenDetalleId INT NOT NULL 
	FOREIGN KEY REFERENCES ProgramasIdiomasExamenesDetalles(PROGIED_ProgramaIdiomaExamenDetalleId),
    AEC_Puntaje DECIMAL(28,10) NOT NULL,
    AEC_USU_CreadoPorId INT NOT NULL 
	FOREIGN KEY REFERENCES Usuarios(USU_UsuarioId),
    AEC_FechaCreacion datetime2(7) DEFAULT GETDATE(),
	AEC_USU_ModificadoPorId int 
	FOREIGN KEY REFERENCES Usuarios(USU_UsuarioId),
	AEC_FechaModificacion datetime2(7)
)
GO