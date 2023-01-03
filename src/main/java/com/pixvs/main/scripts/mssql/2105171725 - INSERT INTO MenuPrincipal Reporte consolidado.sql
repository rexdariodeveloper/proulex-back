SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, 
		 GETDATE(), 
		 N'toc', 
		 (SELECT MP_NodoPadreId FROM MenuPrincipal WHERE MP_URL = N'/app/inventario/kardex-articulos'), 
		 (SELECT MAX(MP_Orden) FROM MenuPrincipal WHERE MP_NodoPadreId = (SELECT MP_NodoPadreId FROM MenuPrincipal WHERE MP_URL = N'/app/inventario/kardex-articulos')), 
		 1000021, 
		 N'Consolidado entradas y salidas', 
		 N'Consolidated supply report', 
		 N'item', 
		 N'/app/inventario/consolidado-entradas-salidas'
	)
GO