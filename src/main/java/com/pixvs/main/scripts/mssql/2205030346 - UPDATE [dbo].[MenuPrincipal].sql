UPDATE 
	[dbo].[MenuPrincipal] 
SET 
	[MP_NodoPadreId] = (SELECT [MP_NodoId] FROM [dbo].[MenuPrincipal] WHERE [MP_NodoPadreId] = 
							(SELECT [MP_NodoId] FROM [dbo].[MenuPrincipal] WHERE [MP_Titulo] = N'Control escolar') 
					   AND [MP_Titulo] = N'Reportes') 
WHERE 
	[MP_Titulo] = N'Reporte de asistencias'
GO