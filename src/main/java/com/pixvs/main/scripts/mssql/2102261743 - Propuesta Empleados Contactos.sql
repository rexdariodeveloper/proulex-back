/****** Object:  Table [dbo].[EmpleadosPersonasContacto]    Script Date: 29/01/2021 10:35:24 a. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[EmpleadosContactos](
	[EC_EmpleadoContactoId] [int] IDENTITY(1,1) NOT NULL,
	[EC_EMP_EmpleadoId] [int] NOT NULL,
	[EC_Nombre] [nvarchar](50) NOT NULL,
	[EC_PrimerApellido] [nvarchar](50) NOT NULL,
	[EC_SegundoApellido] [nvarchar](50) NULL,
	[EC_Parentesco] [nvarchar] (15) NOT NULL,
	[EC_Telefono] [nvarchar](15) NULL,
	[EC_Movil] [nvarchar](15) NULL,
	[EC_CorreoElectronico] [nvarchar](50) NULL,
	[EC_Borrado] [bit] NOT NULL,
	[EC_FechaCreacion] [datetime] NOT NULL,
	[EC_USU_CreadoPorId] [int] NOT NULL,
	[EC_FechaUltimaModificacion] [datetime] NULL,
	[EC_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[EC_EmpleadoContactoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[EmpleadosContactos] ADD  CONSTRAINT [DF_EmpleadosPersonasContacto_EC_FechaCreacion]  DEFAULT (getdate()) FOR [EC_FechaCreacion]
GO

ALTER TABLE [dbo].[EmpleadosContactos]  WITH CHECK ADD  CONSTRAINT [FK_EC_EMP_EmpleadoId] FOREIGN KEY([EC_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[EmpleadosContactos] CHECK CONSTRAINT [FK_EC_EMP_EmpleadoId]
GO


