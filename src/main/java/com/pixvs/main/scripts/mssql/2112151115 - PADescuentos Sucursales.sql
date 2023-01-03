--------------------------------- Descuentos Sucursales ----------------------------------------------------------
CREATE TABLE [dbo].[PADescuentosSucursales](
	[PADESCS_DescuentoSucursalId] [int] IDENTITY(1,1) NOT NULL,
	[PADESCS_PADESC_DescuentoId] [int] NOT NULL,
	[PADESCS_SUC_SucursalId] [int] NOT NULL,
	[PADESCS_Activo] [bit] NOT NULL
 CONSTRAINT [PK_PADescuentosSucursales] PRIMARY KEY CLUSTERED 
(
	[PADESCS_DescuentoSucursalId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

---Descuentos
ALTER TABLE [dbo].[PADescuentosSucursales]  WITH CHECK ADD  CONSTRAINT [FK_PADESCS_PADESC_DescuentoId] FOREIGN KEY([PADESCS_PADESC_DescuentoId])
REFERENCES [dbo].[PADescuentos] ([PADESC_DescuentoId])
GO

ALTER TABLE [dbo].[PADescuentosSucursales] CHECK CONSTRAINT [FK_PADESCS_PADESC_DescuentoId]
GO

---Sucursales
ALTER TABLE [dbo].[PADescuentosSucursales]  WITH CHECK ADD  CONSTRAINT [FK_PADESCS_SUC_SucursalId] FOREIGN KEY([PADESCS_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO

ALTER TABLE [dbo].[PADescuentosSucursales] CHECK CONSTRAINT [FK_PADESCS_SUC_SucursalId]
GO

--------------------------------- Descuentos Articulos ----------------------------------------------------------
CREATE TABLE [dbo].[PADescuentosArticulos](
	[PADESCA_DescuentoArticuloId] [int] IDENTITY(1,1) NOT NULL,
	[PADESCA_PADESC_DescuentoId] [int] NOT NULL,
	[PADESCA_ART_ArticuloId] [int] NOT NULL,
	[PADESCA_Activo] [bit] NOT NULL
 CONSTRAINT [PK_PADescuentosArticulos] PRIMARY KEY CLUSTERED 
(
	[PADESCA_DescuentoArticuloId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

---Descuentos
ALTER TABLE [dbo].[PADescuentosArticulos]  WITH CHECK ADD  CONSTRAINT [FK_PADESCA_PADESC_DescuentoId] FOREIGN KEY([PADESCA_PADESC_DescuentoId])
REFERENCES [dbo].[PADescuentos] ([PADESC_DescuentoId])
GO

ALTER TABLE [dbo].[PADescuentosArticulos] CHECK CONSTRAINT [FK_PADESCA_PADESC_DescuentoId]
GO

---Articulos
ALTER TABLE [dbo].[PADescuentosArticulos]  WITH CHECK ADD  CONSTRAINT [FK_PADESCA_ART_ArticuloId] FOREIGN KEY([PADESCA_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[PADescuentosArticulos] CHECK CONSTRAINT [FK_PADESCA_ART_ArticuloId]
GO

--------------------------------- Descuentos Usuarios Autorizados ----------------------------------------------------------
CREATE TABLE [dbo].[PADescuentosUsuariosAutorizados](
	[PADESUA_DescuentoArticuloId] [int] IDENTITY(1,1) NOT NULL,
	[PADESUA_PADESC_DescuentoId] [int] NOT NULL,
	[PADESUA_USU_UsuarioId] [int] NOT NULL,
	[PADESUA_Activo] [bit] NOT NULL
 CONSTRAINT [PK_PADescuentosUsuariosAutorizados] PRIMARY KEY CLUSTERED 
(
	[PADESUA_DescuentoArticuloId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

---Descuentos
ALTER TABLE [dbo].[PADescuentosUsuariosAutorizados]  WITH CHECK ADD  CONSTRAINT [FK_PADESUA_PADESC_DescuentoId] FOREIGN KEY([PADESUA_PADESC_DescuentoId])
REFERENCES [dbo].[PADescuentos] ([PADESC_DescuentoId])
GO

ALTER TABLE [dbo].[PADescuentosUsuariosAutorizados] CHECK CONSTRAINT [FK_PADESUA_PADESC_DescuentoId]
GO

---Usuarios
ALTER TABLE [dbo].[PADescuentosUsuariosAutorizados]  WITH CHECK ADD  CONSTRAINT [FK_PADESUA_USU_UsuarioId] FOREIGN KEY([PADESUA_USU_UsuarioId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[PADescuentosUsuariosAutorizados] CHECK CONSTRAINT [FK_PADESUA_USU_UsuarioId]
GO

