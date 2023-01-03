SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ClientesContactos](
	[CLIC_ClienteContactoId] [int] IDENTITY(1,1) NOT NULL,
	[CLIC_CLI_ClienteId] [int] NOT NULL,
	[CLIC_Nombre] [varchar](100) NOT NULL,
	[CLIC_PrimerApellido] [varchar](50) NOT NULL,
	[CLIC_SegundoApellido] [varchar](50) NOT NULL,
	[CLIC_Departamento] [varchar](100) NOT NULL,
	[CLIC_Telefono] [varchar](25) NOT NULL,
	[CLIC_Extension] [varchar](3) NULL,
	[CLIC_CorreoElectronico] [varchar](50) NOT NULL,
	[CLIC_HorarioAtencion] [varchar](250) NOT NULL,
	[CLIC_Predeterminado] [bit] NOT NULL,
	[CLIC_Activo] [bit] NOT NULL,
	[CLIC_FechaCreacion] [datetime2](7) NOT NULL,
	[CLIC_FechaModificacion] [datetime2](7) NULL,
	[CLIC_USU_CreadoPorId] [int] NULL,
	[CLIC_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[CLIC_ClienteContactoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ClientesContactos]  WITH CHECK ADD  CONSTRAINT [FK_CLIC_USU_CreadoPorId] FOREIGN KEY([CLIC_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ClientesContactos] CHECK CONSTRAINT [FK_CLIC_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[ClientesContactos]  WITH CHECK ADD  CONSTRAINT [FK_CLIC_USU_ModificadoPorId] FOREIGN KEY([CLIC_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ClientesContactos] CHECK CONSTRAINT [FK_CLIC_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[ClientesContactos]  WITH CHECK ADD  CONSTRAINT [FK_ClientesContactos_Clientes] FOREIGN KEY([CLIC_CLI_ClienteId])
REFERENCES [dbo].[Clientes] ([CLI_ClienteId])
GO

ALTER TABLE [dbo].[ClientesContactos] CHECK CONSTRAINT [FK_ClientesContactos_Clientes]
GO


