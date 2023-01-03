CREATE FUNCTION [dbo].[fn_departamentosDescendientes] (@idDepartamentoRaiz int )
RETURNS @tablaTMP table(
	DEP_DepartamentoId int,
	DEP_DEP_DepartamentoPadreId int,
	DEP_Prefijo varchar(20),
	DEP_Nombre varchar(150),
	DEP_USU_ResponsableId int,
	DEP_Autoriza bit,
	DEP_Activo bit,
	DEP_FechaCreacion datetime,
	DEP_FechaModificacion datetime,
	DEP_USU_CreadoPorId int,
	DEP_USU_ModificadoPorId int
)

AS BEGIN

	WITH Dptos
	AS(
		SELECT DptoRaiz.*
		FROM Departamentos DptoRaiz
		WHERE DptoRaiz.DEP_DepartamentoId = @idDepartamentoRaiz

		UNION ALL

		SELECT DptosHijos.*
		FROM Departamentos DptosHijos
		INNER JOIN Dptos ON Dptos.DEP_DepartamentoId = DptosHijos.DEP_DEP_DepartamentoPadreId
		WHERE DptosHijos.DEP_Activo = 1
	)
	INSERT @tablaTMP
	SELECT * FROM Dptos WHERE DEP_DepartamentoId != @idDepartamentoRaiz

RETURN;
END