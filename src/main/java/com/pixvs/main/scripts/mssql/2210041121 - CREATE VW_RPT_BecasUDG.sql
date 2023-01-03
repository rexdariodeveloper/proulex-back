SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =================================================
-- Author:		Javier Elías
-- Create date: 04/10/2022
-- Modified date: 
-- Description:	Vista para obtener el reporte de Becas UDG
-- =================================================
CREATE OR ALTER VIEW [dbo].[VW_RPT_BecasUDG]
AS
	SELECT BECU_BecaId AS id,
		   ALU_Codigo AS codigoPlic,
		   BECU_CodigoBeca AS codigoBecaUdg,
		   BECU_CodigoEmpleado AS codigoEmpleado,
		   BECU_PrimerApellido AS primerApellido,
		   BECU_SegundoApellido AS segundoApellido,
		   BECU_Nombre AS nombre,
		   CONCAT(CONVERT(DECIMAL, BECU_Descuento * 100), '%') AS descuentoUDG,
		   BECU_Nivel AS nivelUDG,
		   BECU_FechaAlta AS fechaAlta,
		   CONCAT(PROG_Codigo, ' ', idioma.CMM_Valor) AS curso,
		   PAMOD_ModalidadId AS modalidadId,
		   PAMOD_Nombre AS modalidad,
		   BECU_Parentesco AS parentesco,
		   SUC_SucursalId AS sedeId,
		   SUC_Nombre AS sede,
		   PROGRU_Nivel AS nivel,
		   PROGRU_Grupo AS numeroGrupo,
		   PROGRU_Codigo AS codigoGrupo,
		   PROGRU_FechaInicio AS fechaInicio,
		   PROGRU_FechaFin AS fechaFin,
		   CONVERT(DATE, INS_FechaCreacion) AS fechaInscripcion,
		   CONVERT(VARCHAR(5), INS_FechaCreacion, 108) AS horaInscripcion,
		   OV_Codigo AS notaVenta,
		   ISNULL(ALUG_CalificacionFinal, 0) AS calificacion,
		   estatus.CMM_Valor AS estatusBeca,
		   ENBE_EntidadBecaId AS clienteId,
		   ISNULL(ENBE_Codigo, 'BECA SINDICATO') AS cliente,
		   Descuento AS descuentoCliente,
		   Total AS totalCliente,
		   BECU_Comentarios AS comentarios,
		   CASE WHEN BECU_Descuento = 1 THEN ISNULL(LIPRED_Precio, Descuento) ELSE Total END AS precio,
		   dbo.getNombreCompletoUsuario(SUC_USU_CoordinadorId) AS elaboro,
		   dbo.getNombreCompletoUsuario(SUC_USU_ResponsableId) AS autorizo,
		   BECU_CodigoBeca AS autorizacion,
		   FORMAT(PROGRU_FechaInicio, 'dd/MM/yyyy') AS fechaFormato,
		   CONVERT(BIT, CASE WHEN BECU_Descuento = 1 THEN 1 ELSE 0 END) AS cienPorciento
	FROM BecasUDG
		 INNER JOIN Inscripciones ON BECU_BecaId = INS_BECU_BecaId AND INS_CMM_EstatusId != 2000512	-- No Cancelada
		 INNER JOIN ProgramasIdiomas ON BECU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
		 INNER JOIN Programas ON PROGI_PROG_ProgramaId = PROG_ProgramaId
		 INNER JOIN ControlesMaestrosMultiples AS idioma ON PROGI_CMM_Idioma = idioma.CMM_ControlId
		 INNER JOIN PAModalidades ON BECU_PAMOD_ModalidadId = PAMOD_ModalidadId
		 INNER JOIN ProgramasGrupos ON INS_PROGRU_GrupoId = PROGRU_GrupoId AND BECU_Nivel = PROGRU_Nivel
		 INNER JOIN EntidadesBecas ON BECU_ENBE_EntidadBecaId = ENBE_EntidadBecaId
		 INNER JOIN ControlesMaestrosMultiples AS estatus ON BECU_CMM_EstatusId = estatus.CMM_ControlId
		 INNER JOIN OrdenesVentaDetalles ON INS_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId AND OVD_OVD_DetallePadreId IS NULL
		 INNER JOIN OrdenesVenta ON OVD_OV_OrdenVentaId = OV_OrdenVentaId AND LEN(OV_Codigo) != 36
		 INNER JOIN Sucursales ON OV_SUC_SucursalId = SUC_SucursalId
		 INNER JOIN Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
		 LEFT JOIN AlumnosGrupos ON ALU_AlumnoId = ALUG_ALU_AlumnoId AND PROGRU_GrupoId = ALUG_PROGRU_GrupoId
		 LEFT JOIN ListadosPrecios ON ENBE_LIPRE_ListadoPrecioId = LIPRE_ListadoPrecioId AND LIPRE_Activo = 1
		 LEFT JOIN ListadosPreciosDetalles ON LIPRE_ListadoPrecioId = LIPRED_LIPRE_ListadoPrecioId AND OVD_ART_ArticuloId = LIPRED_ART_ArticuloId
		 CROSS APPLY fn_getMontosDetalleOV(OVD_OrdenVentaDetalleId) AS montos
	WHERE BECU_CMM_EstatusId = 2000571 -- Aplicada
		  AND (PROGRU_GrupoReferenciaId IS NULL OR OV_MPPV_MedioPagoPVId IS NOT NULL) -- No incluir las Proyecciones
GO