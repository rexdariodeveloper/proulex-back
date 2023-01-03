CREATE OR ALTER VIEW [dbo].[VW_LISTADO_PROGRAMAS_EXCEL]
AS
Select PROG_Codigo as codigo,PROG_Nombre as nombre,COUNT(ProgramasIdiomas.PROGI_PROG_ProgramaId) as idiomas
from Programas
left join ProgramasIdiomas on PROGI_PROG_ProgramaId = PROG_ProgramaId
GROUP BY PROGI_PROG_ProgramaId,PROG_Codigo,PROG_Nombre
GO