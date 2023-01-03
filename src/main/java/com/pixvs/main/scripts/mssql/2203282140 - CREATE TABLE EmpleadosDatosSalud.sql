/**
 * Created by Rene Carrillo on 28/03/2022.
 */

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[EmpleadosDatosSalud](
	[EMPDS_EmpleadoDatoSaludId] [int] IDENTITY(1,1) NOT NULL,
	[EMPDS_EMP_EmpleadoId] [int] NOT NULL,
	[EMPDS_Alergias] [nvarchar](255) NULL,
	[EMPDS_Padecimientos] [nvarchar](255) NULL,
	[EMPDS_InformacionAdicional] [nvarchar](255) NULL,
	[EMPDS_CMM_TipoSangreId] [int] NOT NULL,
 CONSTRAINT [PK_EmpleadosDatosSalud] PRIMARY KEY CLUSTERED
(
	[EMPDS_EmpleadoDatoSaludId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[EmpleadosDatosSalud]  WITH CHECK ADD  CONSTRAINT [FK_EMPDS_CMM_TipoSangreId] FOREIGN KEY([EMPDS_CMM_TipoSangreId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[EmpleadosDatosSalud] CHECK CONSTRAINT [FK_EMPDS_CMM_TipoSangreId]
GO

ALTER TABLE [dbo].[EmpleadosDatosSalud]  WITH CHECK ADD  CONSTRAINT [FK_EMPDS_EMP_EmpleadoId] FOREIGN KEY([EMPDS_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[EmpleadosDatosSalud] CHECK CONSTRAINT [FK_EMPDS_EMP_EmpleadoId]
GO
