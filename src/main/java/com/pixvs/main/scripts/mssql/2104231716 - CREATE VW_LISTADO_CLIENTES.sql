SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_LISTADO_CLIENTES]
AS
     SELECT CLI_Activo AS "Activo",
            CLI_Codigo AS "Código",
            CLI_Nombre AS "Nombre",
            CLI_Rfc AS "RFC",
            CLI_FechaCreacion AS "Fecha Creación"
     FROM Clientes
GO