-- ============================================= 
-- Author:		Angel Daniel Hernández Silva
-- Create date: 2020/11/24
-- Description:	Adecuaciones a ficha de proveedores
-- --------------------------------------------- 
-- 
-- 
SET IDENTITY_INSERT ControlesMaestrosMultiples ON
INSERT INTO ControlesMaestrosMultiples
(
    CMM_ControlId,
	CMM_Control,
	CMM_Valor,
	CMM_Activo,
	CMM_Referencia,
	CMM_Sistema,
	CMM_USU_CreadoPorId,
	CMM_FechaCreacion,
	CMM_USU_ModificadoPorId,
	CMM_FechaModificacion
) VALUES(
    2000241,
	'CMM_PRO_TipoProveedor',
	'Persona física',
	1,
	NULL,
	1,
	NULL,
	GETDATE(),
	NULL,
	NULL
),(
    2000242,
	'CMM_PRO_TipoProveedor',
	'Persona moral',
	1,
	NULL,
	1,
	NULL,
	GETDATE(),
	NULL,
	NULL
),

(
    2000251,
	'CMM_PROC_TipoContacto',
	'Contacto de compras',
	1,
	NULL,
	1,
	NULL,
	GETDATE(),
	NULL,
	NULL
),(
    2000252,
	'CMM_PROC_TipoContacto',
	'Contacto de pagos',
	1,
	NULL,
	1,
	NULL,
	GETDATE(),
	NULL,
	NULL
)
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO

ALTER TABLE [dbo].[Proveedores] ADD [PRO_CMM_TipoProveedorId] int NULL
GO

ALTER TABLE [dbo].[Proveedores]  WITH CHECK ADD  CONSTRAINT [FK_PRO_CMM_TipoProveedorId] FOREIGN KEY([PRO_CMM_TipoProveedorId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Proveedores] CHECK CONSTRAINT [FK_PRO_CMM_TipoProveedorId]
GO

UPDATE [dbo].[Proveedores] SET [PRO_CMM_TipoProveedorId] = 2000242
GO

ALTER TABLE [dbo].[Proveedores] ALTER COLUMN [PRO_CMM_TipoProveedorId] int NOT NULL
GO






---------------------------------------------
---------------------------------------------
----- PRO_ProveedorId ADD IDENTITY(1,1) -----
---------------------------------------------
---------------------------------------------

/*

ALTER TABLE [dbo].[OrdenesCompra]  DROP CONSTRAINT [FK_OC_PRO_ProveedorId]
GO

ALTER TABLE [dbo].[CXPFacturas] DROP CONSTRAINT [FK_CXPF_PRO_ProveedorId]
GO

ALTER TABLE [dbo].[CXPPagos] DROP CONSTRAINT [FK_CXPP_PRO_ProveedorId]
GO

ALTER TABLE [dbo].[ProveedoresContactos]  DROP  CONSTRAINT [FK_ProveedoresContactos_Proveedores]
GO

ALTER TABLE [dbo].[ProveedoresFormasPagos]  DROP  CONSTRAINT [FK_ProveedoresFormasPagos_Proveedores]
GO

---------------------------------------------

ALTER TABLE [dbo].[Proveedores] ADD [_PRO_ProveedorId] [int] IDENTITY(1,1) NOT NULL
GO

UPDATE [dbo].[Proveedores] SET [_PRO_ProveedorId] = [PRO_ProveedorId]
GO

ALTER TABLE [dbo].[Proveedores] DROP CONSTRAINT [PK_Proveedor]
GO

ALTER TABLE [dbo].[Proveedores] DROP COLUMN [PRO_ProveedorId]
GO

EXEC sp_rename 'dbo.Proveedores._PRO_ProveedorId', 'PRO_ProveedorId', 'COLUMN'
GO

---------------------------------------------

ALTER TABLE [dbo].[Proveedores] ADD CONSTRAINT [PK_Proveedor] PRIMARY KEY CLUSTERED(
	[PRO_ProveedorId] ASC
) WITH (
	PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[OrdenesCompra]  WITH CHECK ADD  CONSTRAINT [FK_OC_PRO_ProveedorId] FOREIGN KEY([OC_PRO_ProveedorId])
REFERENCES [dbo].[Proveedores] ([PRO_ProveedorId])
GO

ALTER TABLE [dbo].[OrdenesCompra] CHECK CONSTRAINT [FK_OC_PRO_ProveedorId]
GO

ALTER TABLE [dbo].[CXPFacturas]  WITH CHECK ADD  CONSTRAINT [FK_CXPF_PRO_ProveedorId] FOREIGN KEY([CXPF_PRO_ProveedorId])
REFERENCES [dbo].[Proveedores] ([PRO_ProveedorId])
GO

ALTER TABLE [dbo].[CXPFacturas] CHECK CONSTRAINT [FK_CXPF_PRO_ProveedorId]
GO

ALTER TABLE [dbo].[CXPPagos]  WITH CHECK ADD  CONSTRAINT [FK_CXPP_PRO_ProveedorId] FOREIGN KEY([CXPP_PRO_ProveedorId])
REFERENCES [dbo].[Proveedores] ([PRO_ProveedorId])
GO

ALTER TABLE [dbo].[CXPPagos] CHECK CONSTRAINT [FK_CXPP_PRO_ProveedorId]
GO

ALTER TABLE [dbo].[ProveedoresContactos]  WITH CHECK ADD  CONSTRAINT [FK_ProveedoresContactos_Proveedores] FOREIGN KEY([PROC_PRO_ProveedorId])
REFERENCES [dbo].[Proveedores] ([PRO_ProveedorId])
GO

ALTER TABLE [dbo].[ProveedoresFormasPagos]  WITH CHECK ADD  CONSTRAINT [FK_ProveedoresFormasPagos_Proveedores] FOREIGN KEY([PROFP_PRO_ProveedorId])
REFERENCES [dbo].[Proveedores] ([PRO_ProveedorId])
GO

*/