SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_RPT_INSCRIPCIONES]
AS
     SELECT DISTINCT
			Sedes.SUC_SucursalId AS sedeInscripcionId,
			Sedes.SUC_Nombre AS sedeInscripcion,
			INS_InscripcionId AS inscripcionId,
			INS_Codigo AS inscripcion,
			CAST(ISNULL(INS_FechaModificacion, INS_FechaCreacion) AS DATE) AS fechaInscripcion,
			OV_OrdenVentaId AS notaVentaId,
			CASE WHEN OV_CMM_EstatusId IN (2000507, 2000508) AND OV_CMM_MetodoPago IS NOT NULL THEN OV_Codigo ELSE '' END AS notaVenta, -- Solo mostrar Pagada o Facturada
			estatusOV.CMM_Valor AS estatusNotaVenta,
			OV_LigaCentroPagos AS LigaPago,
			ALU_PrimerApellido AS primerApellido,
			ALU_SegundoApellido AS segundoApellido,
			ALU_Nombre AS nombre,
			ALU_AlumnoId AS alumnoId,
			ALU_Nombre + ' ' + ALU_PrimerApellido + ISNULL(' ' + ALU_SegundoApellido, '') AS alumno,
			SedesGrupo.SUC_SucursalId AS sedeGrupoId,
			SedesGrupo.SUC_Nombre AS sedeGrupo,
			PROGRU_GrupoId AS grupoId,
			PROGRU_Codigo AS grupo,
			tipoGrupo.CMM_Valor AS tipoGrupo,
			ART_CodigoArticulo + ' - '  + ART_NombreArticulo AS curso,
			PAMOD_ModalidadId AS modalidadId,
			PAMOD_Codigo + ' - ' + PAMOD_Nombre AS modalidad,
			FORMAT(PROGRU_Nivel, '00') AS nivel,
			idiomas.CMM_Valor AS idioma,
			CONCAT_WS(' - ', CAST(PAMODH_HoraInicio AS NVARCHAR(5)), CAST(PAMODH_HoraFin AS NVARCHAR(5))) AS horario,
			CAST(PROGRU_FechaInicio AS DATE) AS fechaInicio,
			CAST(PROGRU_FechaFin AS DATE) AS fechaFin,
			montos.Descuento * 100 / montos.Subtotal AS porcentajeDescuento,
			CONVERT(MONEY, montos.Subtotal) AS subtotal,
			CONVERT(MONEY, montos.Descuento) AS descuento,
			CONVERT(MONEY, (montos.ImporteIVA + ISNULL(montos.ImporteIEPS, 0))) AS impuestos,
			montos.Total AS total,
			ENBE_Nombre AS entidadBeca,
			BECU_CodigoBeca AS codigoBeca,
			BECU_Descuento * 100 AS porcentajeBeca,
			INS_CMM_EstatusId AS estatusId,
			estatus.CMM_Valor AS estatus,
			PROGRU_PACIC_CicloId AS cicloId,
			PROGRU_PAC_ProgramacionAcademicaComercialId AS paId,
			CASE WHEN INSSG_InscripcionId IS NOT NULL THEN 'CURSO APLICADO' END AS estatusInscripcion
		FROM Inscripciones
			INNER JOIN ProgramasGrupos ON INS_PROGRU_GrupoId = PROGRU_GrupoId
			INNER JOIN ControlesMaestrosMultiples AS tipoGrupo ON PROGRU_CMM_TipoGrupoId = tipoGrupo.CMM_ControlId
			INNER JOIN Sucursales AS SedesGrupo ON PROGRU_SUC_SucursalId = SedesGrupo.SUC_SucursalId
			INNER JOIN Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
			INNER JOIN ProgramasIdiomas ON PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
			INNER JOIN ControlesMaestrosMultiples idiomas ON PROGI_CMM_Idioma = idiomas.CMM_ControlId
			INNER JOIN PAModalidades ON PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
			INNER JOIN PAModalidadesHorarios ON PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
			INNER JOIN OrdenesVentaDetalles ON INS_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
			INNER JOIN OrdenesVenta ON OVD_OV_OrdenVentaId = OV_OrdenVentaId
			INNER JOIN Articulos ON OVD_ART_ArticuloId = ART_ArticuloId
			INNER JOIN ControlesMaestrosMultiples AS estatusOV ON OV_CMM_EstatusId = estatusOV.CMM_ControlId
			INNER JOIN Sucursales AS Sedes ON OV_SUC_SucursalId = Sedes.SUC_SucursalId
			INNER JOIN ControlesMaestrosMultiples AS estatus ON INS_CMM_EstatusId = estatus.CMM_ControlId
			CROSS APPLY dbo.fn_getMontosFacturacionDetalleOV(OVD_OrdenVentaDetalleId) AS montos
			LEFT JOIN BecasUDG ON INS_BECU_BecaId = BECU_BecaId
			LEFT JOIN EntidadesBecas ON BECU_ENBE_EntidadBecaId = ENBE_EntidadBecaId
			LEFT JOIN InscripcionesSinGrupo ON OVD_OrdenVentaDetalleId = INSSG_OVD_OrdenVentaDetalleId
		WHERE INS_CMM_EstatusId != 2000512 -- CMM_INS_Estatus Cancelada
			AND (PROGRU_GrupoReferenciaId IS NULL OR OV_MPPV_MedioPagoPVId IS NOT NULL) -- No incluir las Proyecciones
GO