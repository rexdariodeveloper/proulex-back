SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Rene Carrillo
-- Create date: 05/10/2022
-- Modify date:
-- Description:	Funcion para obtener el combo de zona (Precio de Incompany)
-- Version 1.0.0
-- =============================================

CREATE FUNCTION [dbo].[fn_getComboZona] (@sedeId INTEGER, @programaId INTEGER, @idiomaId INTEGER, @modalidadId INTEGER, @modalidadHorarioId INTEGER)
RETURNS TABLE
AS
RETURN
(
	SELECT pi.PREINC_PrecioIncompanyId AS Id,
		pi.PREINC_Codigo + ' - ' + pi.PREINC_Nombre AS Nombre,
		pid.PREINCD_PrecioVenta AS PrecioVenta,
		pid.PREINCD_PorcentajeTransporte AS PorcentajeTransporte
	FROM PreciosIncompany pi
		INNER JOIN PreciosIncompanySucursales pis ON pi.PREINC_PrecioIncompanyId = pis.PREINCS_PREINC_PrecioIncompanyId
		INNER JOIN PreciosIncompanyDetalles pid ON pi.PREINC_PrecioIncompanyId = pid.PREINCD_PREINC_PrecioIncompanyId
	WHERE pi.PREINC_CMM_EstatusId = 2001000
		AND pis.PREINCD_SUC_SucursalId = @sedeId
		AND pid.PREINCD_PROG_ProgramaId = @programaId
		AND pid.PREINCD_CMM_IdiomaId = @idiomaId
		AND pid.PREINCD_PAMOD_ModalidadId = @modalidadId
		AND pid.PREINCD_PAMODH_PAModalidadHorarioId = @modalidadHorarioId
)