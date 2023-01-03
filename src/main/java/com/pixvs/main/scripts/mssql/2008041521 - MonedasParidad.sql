/**
 * Created by Angel Daniel Hernández Silva on 31/07/2020.
 * Object:  Table [dbo].[MonedasParidad]
 */


CREATE TABLE [dbo].[MonedasParidad](
	[MONP_MonedaParidadId] [int] IDENTITY(1,1) NOT NULL ,
	[MONP_MON_MonedaId] [smallint] NOT NULL ,
	[MONP_FechaInicio] [datetime2](7) NOT NULL,
	[MONP_FechaFin] [datetime2](7) NOT NULL,
	[MONP_TipoCambioOficial] [decimal](10,4) NOT NULL,
	[MONP_FechaCreacion] [datetime2](7) NOT NULL,
	[MONP_FechaModificacion] [datetime2](7) NULL,
	[MONP_USU_CreadoPorId] [int] NULL,
	[MONP_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[MONP_MonedaParidadId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

-- FKs

ALTER TABLE [dbo].[MonedasParidad]  WITH CHECK ADD  CONSTRAINT [FK_MONP_MON_MonedaId] FOREIGN KEY([MONP_MON_MonedaId])
REFERENCES [dbo].[Monedas] ([MON_MonedaId])
GO

ALTER TABLE [dbo].[MonedasParidad] CHECK CONSTRAINT [FK_MONP_MON_MonedaId]
GO

