SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, 
		 GETDATE(), 
		 N'toc', 
		 (SELECT MP_NodoPadreId FROM MenuPrincipal WHERE MP_URL = N'/app/compras/reportes/antiguedad-saldos'), 
		 (SELECT MAX(MP_Orden) FROM MenuPrincipal WHERE MP_NodoPadreId = (SELECT MP_NodoPadreId FROM MenuPrincipal WHERE MP_URL = N'/app/compras/reportes/antiguedad-saldos')), 
		 1000021, 
		 N'Reporte de confirming', 
		 N'Confirming report', 
		 N'item', 
		 N'/app/compras/reportes/confirming'
	)
GO