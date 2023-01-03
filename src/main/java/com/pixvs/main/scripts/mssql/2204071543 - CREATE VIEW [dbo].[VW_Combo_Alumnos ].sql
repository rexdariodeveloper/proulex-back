SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW [dbo].[VW_Combo_Alumnos] AS

    SELECT
        ALU_AlumnoId AS id,
        ALU_Codigo AS codigo,
        ALU_Nombre AS nombre,
        ALU_PrimerApellido AS primerApellido,
        ALU_SegundoApellido AS segundoApellido
    FROM Alumnos
    WHERE ALU_Activo = 1

GO