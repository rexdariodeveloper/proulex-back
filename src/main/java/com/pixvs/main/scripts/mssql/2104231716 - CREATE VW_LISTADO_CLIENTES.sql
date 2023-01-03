SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_LISTADO_CLIENTES]
AS
     SELECT CLI_Activo AS "Activo",
            CLI_Codigo AS "C�digo",
            CLI_Nombre AS "Nombre",
            CLI_Rfc AS "RFC",
            CLI_FechaCreacion AS "Fecha Creaci�n"
     FROM Clientes
GO