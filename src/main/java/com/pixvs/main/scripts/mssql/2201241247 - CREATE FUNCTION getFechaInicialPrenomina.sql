/**
 * Created by Angel Daniel Hernández Silva on 21/01/2022.
 * Object:  ALTER FUNCTION [dbo].[getFechaInicialPrenomina]
 */
 
CREATE OR ALTER FUNCTION [dbo].[getFechaInicialPrenomina] ()
RETURNS date
AS BEGIN
	DECLARE @fechaInicio date;

    SELECT @fechaInicio = MIN(fechaInicio)
    FROM(
        SELECT MIN(PROGRUP_FechaInicio) AS fechaInicio
        FROM ProgramasGruposProfesores

        UNION ALL

        SELECT MIN(PROGRULC_Fecha) AS fechaInicio
        FROM ProgramasGruposListadoClases

        UNION ALL

        SELECT MIN(EDP_Fecha) AS fechaInicio
        FROM EmpleadosDeduccionesPercepciones
    ) AS FechasInicio

    RETURN @fechaInicio;
END
GO