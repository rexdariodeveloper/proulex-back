DROP VIEW [dbo].[VW_LISTADO_SUCURSALES]
GO

CREATE VIEW [dbo].[VW_LISTADO_SUCURSALES] AS

SELECT
	SUC_CodigoSucursal AS "Código",
	SUC_Nombre AS "Nombre",
	USU_Nombre + ' ' + USU_PrimerApellido + COALESCE(' ' + USU_SegundoApellido,'') AS "Responsable",
	SUC_PorcentajeComision AS "% Comisión",
	SUC_Telefono AS "Teléfono",
	SUC_Activo AS "Activo"
FROM Sucursales
INNER JOIN Usuarios ON USU_UsuarioId = SUC_USU_ResponsableId

GO