UPDATE [dbo].[Almacenes] SET [ALM_Domicilio] = (SELECT [SUC_Domicilio] FROM [dbo].[Sucursales] WHERE [ALM_SUC_SucursalId] = [SUC_SucursalId]) WHERE [ALM_MismaDireccionSucursal] = 1
GO
UPDATE [dbo].[Almacenes] SET [ALM_Colonia] = (SELECT [SUC_Colonia] FROM [dbo].[Sucursales] WHERE [ALM_SUC_SucursalId] = [SUC_SucursalId]) WHERE [ALM_MismaDireccionSucursal] = 1
GO
UPDATE [dbo].[Almacenes] SET [ALM_PAI_PaisId] = (SELECT [SUC_PAI_PaisId] FROM [dbo].[Sucursales] WHERE [ALM_SUC_SucursalId] = [SUC_SucursalId]) WHERE [ALM_MismaDireccionSucursal] = 1
GO
UPDATE [dbo].[Almacenes] SET [ALM_EST_EstadoId] = (SELECT [SUC_EST_EstadoId] FROM [dbo].[Sucursales] WHERE [ALM_SUC_SucursalId] = [SUC_SucursalId]) WHERE [ALM_MismaDireccionSucursal] = 1
GO
UPDATE [dbo].[Almacenes] SET [ALM_Ciudad] = (SELECT [SUC_Ciudad] FROM [dbo].[Sucursales] WHERE [ALM_SUC_SucursalId] = [SUC_SucursalId]) WHERE [ALM_MismaDireccionSucursal] = 1
GO
UPDATE [dbo].[Almacenes] SET [ALM_CP] = (SELECT [SUC_CP] FROM [dbo].[Sucursales] WHERE [ALM_SUC_SucursalId] = [SUC_SucursalId]) WHERE [ALM_MismaDireccionSucursal] = 1
GO
UPDATE [dbo].[Almacenes] SET [ALM_Telefono] = (SELECT [SUC_Telefono] FROM [dbo].[Sucursales] WHERE [ALM_SUC_SucursalId] = [SUC_SucursalId]) WHERE [ALM_MismaDireccionSucursal] = 1
GO
UPDATE [dbo].[Almacenes] SET [ALM_Extension] = (SELECT [SUC_Extension] FROM [dbo].[Sucursales] WHERE [ALM_SUC_SucursalId] = [SUC_SucursalId]) WHERE [ALM_MismaDireccionSucursal] = 1
GO
UPDATE [dbo].[Almacenes] SET [ALM_MismaDireccionSucursal] = 0 WHERE [ALM_MismaDireccionSucursal] = 1
GO