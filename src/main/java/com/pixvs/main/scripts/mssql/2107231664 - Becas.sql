/**
 * Created by Angel Daniel Hernández Silva on 12/07/2021.
 * Object:  Table [dbo].[Becas]
 */

/*****************/
/***** Becas *****/
/*****************/

CREATE TABLE [dbo].[Becas](
	[BEC_BecaId] [int] IDENTITY(1,1) NOT NULL,
	[BEC_CodigoBecaUDG] [varchar](150) NOT NULL,
	[BEC_CodigoEmpleadoUDG] [varchar](150) NOT NULL,
    [BEC_Nombre] [nvarchar](50) NOT NULL,
	[BEC_PrimerApellido] [nvarchar](50) NOT NULL,
	[BEC_SegundoApellido] [nvarchar](50) NULL,
	[BEC_Parentesco] [nvarchar](50) NULL,
	[BEC_Descuento] [decimal](3,2) NOT NULL,
    [BEC_PROG_ProgramaId] [int] NOT NULL,
    [BEC_CMM_IdiomaId] [int] NOT NULL,
    [BEC_Nivel] [int] NOT NULL,
    [BEC_PAMOD_ModalidadId] [int] NOT NULL,
	[BEC_FirmaDigitalUDG] [nvarchar](255) NULL,
    [BEC_FechaAlta] [datetime2](7) NOT NULL,
    [BEC_CMM_EstatusId] [int] NOT NULL
PRIMARY KEY CLUSTERED 
(
	[BEC_BecaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

-- Constraints FK

ALTER TABLE [dbo].[Becas]  WITH CHECK ADD  CONSTRAINT [FK_BEC_PROG_ProgramaId] FOREIGN KEY([BEC_PROG_ProgramaId])
REFERENCES [dbo].[Programas] ([PROG_ProgramaId])
GO

ALTER TABLE [dbo].[Becas] CHECK CONSTRAINT [FK_BEC_PROG_ProgramaId]
GO

ALTER TABLE [dbo].[Becas]  WITH CHECK ADD  CONSTRAINT [FK_BEC_CMM_IdiomaId] FOREIGN KEY([BEC_CMM_IdiomaId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[Becas] CHECK CONSTRAINT [FK_BEC_CMM_IdiomaId]
GO