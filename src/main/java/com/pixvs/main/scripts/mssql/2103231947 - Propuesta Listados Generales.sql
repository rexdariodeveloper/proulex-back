/****** Object:  Table [dbo].[Listados Generales]    Script Date: 08/03/2021 10:34:41 a. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


----------------------------------------- Ciclos -------------------------------------------------------------

CREATE TABLE [dbo].[PACiclos](
	[PACIC_CicloId] [int] IDENTITY(1,1) NOT NULL,
	[PACIC_Codigo] [nvarchar](20) NOT NULL,
	[PACIC_Nombre] [nvarchar](40) NOT NULL,
	[PACIC_FechaInicio] [date] NOT NULL,
	[PACIC_FechaFin] [date] NOT NULL,
	[PACIC_Activo] [bit] NOT NULL,
	[PACIC_FechaCreacion] [datetime2](7) NOT NULL,
	[PACIC_USU_CreadoPorId] [int] NOT NULL,
	[PACIC_FechaModificacion] [datetime2](7) NULL,
	[PACIC_USU_ModificadoPorId] [int] NULL 
	CONSTRAINT [PK_PACiclos] PRIMARY KEY CLUSTERED 
(
	[PACIC_CicloId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[PACiclos] ADD  CONSTRAINT [DF_PACIC_FechaCreacion]  DEFAULT (getdate()) FOR [PACIC_FechaCreacion]
GO

----------------------------------------- [Modalidades] -------------------------------------------------------------
CREATE TABLE [dbo].[PAModalidades](
	[PAMOD_ModalidadId] [int] IDENTITY(1,1) NOT NULL,
	[PAMOD_Codigo] [nvarchar](20) NOT NULL,
	[PAMOD_Nombre] [nvarchar](40) NOT NULL,
	[PAMOD_HorasPorDia] [int] NOT NULL,
	[PAMOD_DiasPorSemana] [int] NOT NULL,
	[PAMOD_Lunes] [bit] NULL,
	[PAMOD_Martes] [bit] NULL,
	[PAMOD_Miercoles] [bit] NULL,
	[PAMOD_Jueves] [bit] NULL,
	[PAMOD_Viernes] [bit] NULL,
	[PAMOD_Sabado] [bit] NULL,
	[PAMOD_Domingo] [bit] NULL,
	[PAMOD_Activo] [bit] NULL,
	[PAMOD_FechaCreacion] [datetime2](7) NOT NULL,
	[PAMOD_USU_CreadoPorId] [int] NOT NULL,
	[PAMOD_FechaModificacion] [datetime2](7) NULL,
	[PAMOD_USU_ModificadoPorId] [int] NULL 
	CONSTRAINT [PK_Modalidades] PRIMARY KEY CLUSTERED 
(
	[PAMOD_ModalidadId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[PAModalidades] ADD  CONSTRAINT [DF_PAMOD_FechaCreacion]  DEFAULT (getdate()) FOR [PAMOD_FechaCreacion]
GO

----------------------------------------- Actividades Evaluación -------------------------------------------------------------
CREATE TABLE [dbo].[PAActividadesEvaluacion](
	[PAAE_ActividadEvaluacionId] [int] IDENTITY(1,1) NOT NULL,
	[PAAE_Codigo] [nvarchar](20) NOT NULL,
	[PAAE_Actividad] [nvarchar](40) NOT NULL,
	[PAAE_Activo] [bit] NULL,
	[PAAE_FechaCreacion] [datetime2](7) NOT NULL,
	[PAAE_USU_CreadoPorId] [int] NOT NULL,
	[PAAE_FechaModificacion] [datetime2](7) NULL,
	[PAAE_USU_ModificadoPorId] [int] NULL 
	CONSTRAINT [PK_PAActividadesEvaluacion] PRIMARY KEY CLUSTERED 
(
	[PAAE_ActividadEvaluacionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[PAActividadesEvaluacion] ADD  CONSTRAINT [DF_PAAE_FechaModificacion]  DEFAULT (getdate()) FOR [PAAE_FechaModificacion]
GO
