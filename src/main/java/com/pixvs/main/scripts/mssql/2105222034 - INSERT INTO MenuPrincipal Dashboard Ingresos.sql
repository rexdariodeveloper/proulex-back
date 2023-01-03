SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, 
		 GETDATE(), 
		 N'bar_chart', 
		 null, 
		 1, 
		 1000021, 
		 N'Ingresos', 
		 N'Income', 
		 N'item', 
		 N'/app/dashboard'
	)
GO