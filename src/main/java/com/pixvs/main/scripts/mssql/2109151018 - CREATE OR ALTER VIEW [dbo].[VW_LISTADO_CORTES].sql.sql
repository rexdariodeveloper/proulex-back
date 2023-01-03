CREATE OR ALTER VIEW [dbo].[VW_LISTADO_CORTES]
AS
(
	select
		SCC_SucursalCorteCajaId id,
		SUC_Nombre sede,
		SP_Nombre plantel,
		TRIM(CONCAT(USU_Nombre,' ',USU_PrimerApellido,' ',USU_SegundoApellido)) usuario,
		SCC_FechaInicio fechaInicio,
		SCC_FechaFin fechaFin,
		SCC_MontoAbrirCaja montoAbrir,
		SCC_MontoCerrarCaja montoCerrar,
		SUC_SucursalId sedeId,
		USU_UsuarioId usuarioId
	from 
		SucursalesCortesCajas 
		inner join Sucursales on SUC_SucursalId = SCC_SUC_SucursalId
		inner join Usuarios on USU_UsuarioId = SCC_USU_UsuarioAbreId
		left join SucursalesPlanteles on SP_SucursalPlantelId = SCC_SP_SucursalPlantelId
)
GO