/**
 * Created by Angel Daniel Hernández Silva on 25/11/2021.
 * Object:  Table [dbo].[ProgramasGruposProfesores]
 */

CREATE TABLE [dbo].[ProgramasGruposProfesores](
    [PROGRUP_ProgramaGrupoProfesorId] [int] IDENTITY(1,1) NOT NULL,
    [PROGRUP_PROGRU_GrupoId] [int] NOT NULL,
    [PROGRUP_EMP_EmpleadoId] [int] NOT NULL,
    [PROGRUP_FechaInicio] [date] NOT NULL,
    [PROGRUP_Motivo] [varchar](255) NULL,
    [PROGRUP_Sueldo] [decimal](10,2) NOT NULL,
    [PROGRUP_FechaCreacion] [datetime2](7) NOT NULL,
	[PROGRUP_Activo] [bit] NOT NULL,
    CONSTRAINT [PK_ProgramasGruposProfesores] PRIMARY KEY CLUSTERED (
        [PROGRUP_ProgramaGrupoProfesorId] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ProgramasGruposProfesores]  WITH CHECK ADD  CONSTRAINT [FK_PROGRUP_PROGRU_GrupoId] FOREIGN KEY([PROGRUP_PROGRU_GrupoId])
REFERENCES [dbo].[ProgramasGrupos] ([PROGRU_GrupoId])
GO

ALTER TABLE [dbo].[ProgramasGruposProfesores] CHECK CONSTRAINT [FK_PROGRUP_PROGRU_GrupoId]
GO

ALTER TABLE [dbo].[ProgramasGruposProfesores]  WITH CHECK ADD  CONSTRAINT [FK_PROGRUP_EMP_EmpleadoId] FOREIGN KEY([PROGRUP_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[ProgramasGruposProfesores] CHECK CONSTRAINT [FK_PROGRUP_EMP_EmpleadoId]
GO





INSERT INTO [dbo].[ProgramasGruposProfesores](
    [PROGRUP_PROGRU_GrupoId],
    [PROGRUP_EMP_EmpleadoId],
    [PROGRUP_FechaInicio],
    [PROGRUP_Sueldo],
    [PROGRUP_FechaCreacion],
	[PROGRUP_Activo]
)
SELECT
	PROGRU_GrupoId,
	PROGRU_EMP_EmpleadoId,
	PROGRU_FechaInicio,
	PROGRU_SueldoProfesor,
	GETDATE(),
	1
FROM ProgramasGrupos
WHERE PROGRU_EMP_EmpleadoId IS NOT NULL
GO



ALTER TABLE [dbo].[Prenominas] ADD [PRENO_SueldoPorHora] [decimal](10,2) NULL
GO










ALTER TABLE [dbo].[ProgramasGrupos] ADD [PROGRU_FechaCancelacion] [date] NULL
GO







CREATE OR ALTER VIEW [dbo].[VW_ProgramasGruposProfesores] AS
	SELECT
		ProfesorActual.PROGRUP_ProgramaGrupoProfesorId AS id,
		ProfesorActual.PROGRUP_PROGRU_GrupoId AS grupoId,
		ProfesorActual.PROGRUP_EMP_EmpleadoId AS empleadoId,
		ProfesorActual.PROGRUP_FechaInicio AS fechaInicio,
		COALESCE(DATEADD(DAY,-1,MIN(ProfesorSiguiente.PROGRUP_FechaInicio)),COALESCE(PROGRU_FechaCancelacion,PROGRU_FechaFin)) AS fechaFin,
		ProfesorActual.PROGRUP_Sueldo AS sueldo,
		ProfesorActual.PROGRUP_FechaCreacion AS fechaCreacion,
		ProfesorActual.PROGRUP_Activo AS activo
	FROM ProgramasGruposProfesores AS ProfesorActual
	LEFT JOIN ProgramasGruposProfesores AS ProfesorSiguiente ON ProfesorSiguiente.PROGRUP_Activo = 1 AND ProfesorActual.PROGRUP_PROGRU_GrupoId = ProfesorSiguiente.PROGRUP_PROGRU_GrupoId AND ProfesorActual.PROGRUP_FechaInicio < ProfesorSiguiente.PROGRUP_FechaInicio
	INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = ProfesorActual.PROGRUP_PROGRU_GrupoId
	GROUP BY
		ProfesorActual.PROGRUP_ProgramaGrupoProfesorId, ProfesorActual.PROGRUP_PROGRU_GrupoId, ProfesorActual.PROGRUP_EMP_EmpleadoId,
		ProfesorActual.PROGRUP_FechaInicio, ProfesorActual.PROGRUP_FechaCreacion, PROGRU_FechaFin, ProfesorActual.PROGRUP_Sueldo,
		ProfesorActual.PROGRUP_Activo, PROGRU_FechaCancelacion
GO