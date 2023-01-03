SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 
	1, 
	GETDATE(), 
	N'toc', 
	(select MP_NodoPadreId from MenuPrincipal where MP_Titulo = N'Existencias'), 
	(select MAX(MP_Orden) + 1 from MenuPrincipal where MP_NodoPadreId = (select MP_NodoPadreId from MenuPrincipal where MP_Titulo = N'Existencias')), 
	1000021, 
	N'Artículos en tránsito', 
	N'Products in transit', 
	N'item', 
	N'/app/inventario/transito')
GO