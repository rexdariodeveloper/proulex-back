/**
 * Created by David Arroyo Sánchez on 05/11/2020.
 * Object:  Table [dbo].[ProveedoresFormasPagos]
 */


CREATE TABLE [dbo].[ProveedoresFormasPagos](
	[PROFP_ProveedorFormaPagoId] [int] IDENTITY(1,1) NOT NULL,
	[PROFP_Activo] [bit] NOT NULL,
	[PROFP_PRO_ProveedorId] [int] NOT NULL,
	[PROFP_CMM_FormaPagoId] [int] NOT NULL,
	[PROFP_MON_MonedaId] [smallint] NOT NULL,
	[PROFP_Banco] [varchar](150) NULL,
	[PROFP_Referencia] [varchar](100) NULL,
	[PROFP_NumeroCuenta] [varchar](50) NULL,
	[PROFP_CuentaClabe] [varchar](50) NULL,
	[PROFP_BicSwift] [varchar](50) NULL,
	[PROFP_Iban] [varchar](50) NULL,
	[PROFP_Predeterminado] [bit] NOT NULL,
	[PROFP_FechaCreacion] [datetime2](7) NOT NULL,
	[PROFP_FechaModificacion] [datetime2](7) NULL,
	[PROFP_USU_CreadoPorId] [int] NULL,
	[PROFP_USU_ModificadoPorId] [int] NULL,
 CONSTRAINT [PK__Proveedo__FA7552F703BED6C2] PRIMARY KEY CLUSTERED 
(
	[PROFP_ProveedorFormaPagoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ProveedoresFormasPagos]  WITH CHECK ADD  CONSTRAINT [FK_PROFP_USU_CreadoPorId] FOREIGN KEY([PROFP_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProveedoresFormasPagos] CHECK CONSTRAINT [FK_PROFP_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[ProveedoresFormasPagos]  WITH CHECK ADD  CONSTRAINT [FK_PROFP_USU_ModificadoPorId] FOREIGN KEY([PROFP_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProveedoresFormasPagos] CHECK CONSTRAINT [FK_PROFP_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[ProveedoresFormasPagos]  WITH CHECK ADD  CONSTRAINT [FK_ProveedoresFormasPagos_ControlesMaestrosMultiples] FOREIGN KEY([PROFP_CMM_FormaPagoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProveedoresFormasPagos] CHECK CONSTRAINT [FK_ProveedoresFormasPagos_ControlesMaestrosMultiples]
GO

ALTER TABLE [dbo].[ProveedoresFormasPagos]  WITH CHECK ADD  CONSTRAINT [FK_ProveedoresFormasPagos_Monedas] FOREIGN KEY([PROFP_MON_MonedaId])
REFERENCES [dbo].[Monedas] ([MON_MonedaId])
GO

ALTER TABLE [dbo].[ProveedoresFormasPagos] CHECK CONSTRAINT [FK_ProveedoresFormasPagos_Monedas]
GO

ALTER TABLE [dbo].[ProveedoresFormasPagos]  WITH CHECK ADD  CONSTRAINT [FK_ProveedoresFormasPagos_Proveedores] FOREIGN KEY([PROFP_PRO_ProveedorId])
REFERENCES [dbo].[Proveedores] ([PRO_ProveedorId])
GO

ALTER TABLE [dbo].[ProveedoresFormasPagos] CHECK CONSTRAINT [FK_ProveedoresFormasPagos_Proveedores]
GO
