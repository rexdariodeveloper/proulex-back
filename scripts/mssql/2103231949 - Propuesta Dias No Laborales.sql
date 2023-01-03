----------------------------------------- Dias No laborales -------------------------------------------------------------
​
CREATE TABLE [dbo].[EmpresaDiasNoLaborales](
	[EMPDNL_EmpresaDiaNoLaboralId] [int] IDENTITY(1,1) NOT NULL,
	[EMPDNL_Fecha] [date] NOT NULL,
	[EMPDNL_Descripcion] [nvarchar](300) NOT NULL,
	[EMPDNL_Activo] [bit] NOT NULL,
	[EMPDNL_FechaCreacion] [datetime2](7) NOT NULL,
	[EMPDNL_USU_CreadoPorId] [int] NOT NULL,
	[EMPDNL_FechaModificacion] [datetime2](7) NULL,
	[EMPDNL_USU_ModificadoPorId] [int] NULL 
	CONSTRAINT [PK_EmpresaDiasNoLaborales] PRIMARY KEY CLUSTERED 
(
	[EMPDNL_EmpresaDiaNoLaboralId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
​
ALTER TABLE [dbo].[EmpresaDiasNoLaborales] ADD  CONSTRAINT [DF_EMPDNL_FechaCreacion]  DEFAULT (getdate()) FOR [EMPDNL_FechaCreacion]
GO

​
----------------------------------------- Dias No laborales Fijos -------------------------------------------------------------
​
CREATE TABLE [dbo].[EmpresaDiasNoLaboralesFijos](
	[EMPDNLF_EmpresaDiaNoLaboralId] [int] IDENTITY(1,1) NOT NULL,
	[EMPDNLF_Dia] [int] NOT NULL,
	[EMPDNLF_Mes] [int] NOT NULL,
	[EMPDNLF_Descripcion] [nvarchar](300) NOT NULL,
	[EMPDNLF_Activo] [bit] NOT NULL,
	[EMPDNLF_FechaCreacion] [datetime2](7) NOT NULL,
	[EMPDNLF_USU_CreadoPorId] [int] NOT NULL,
	[EMPDNLF_FechaModificacion] [datetime2](7) NULL,
	[EMPDNLF_USU_ModificadoPorId] [int] NULL 
	CONSTRAINT [PK_EmpresaDiasNoLaboralesFijos] PRIMARY KEY CLUSTERED 
(
	[EMPDNLF_EmpresaDiaNoLaboralId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
​
ALTER TABLE [dbo].[EmpresaDiasNoLaboralesFijos] ADD  CONSTRAINT [DF_EMPDNLF_FechaCreacion]  DEFAULT (getdate()) FOR [EMPDNLF_FechaCreacion]
GO
​