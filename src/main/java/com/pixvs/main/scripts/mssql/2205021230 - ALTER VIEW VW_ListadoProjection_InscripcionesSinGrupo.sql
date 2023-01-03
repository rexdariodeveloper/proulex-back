/**
* Created by Angel Daniel Hernández Silva on 05/10/2021.
* Object:  CREATE VIEW [dbo].[VW_ListadoProjection_InscripcionesSinGrupo]
*/

CREATE OR ALTER VIEW [dbo].[VW_ListadoProjection_InscripcionesSinGrupo] AS

	SELECT
		--Campos proyección
		INSSG_InscripcionId AS id,
		ALU_AlumnoId AS alumnoId,
		INSSG_OVD_OrdenVentaDetalleId AS ordenVentaDetalleId,
		ALU_Codigo AS alumnoCodigo,
		COALESCE(ALU_CodigoUDG,'') AS alumnoCodigoUDG,
		ALU_Nombre AS alumnoNombre,
		ALU_PrimerApellido AS alumnoPrimerApellido,
		ALU_SegundoApellido AS alumnoSegundoApellido,
		CONCAT(PROG_Codigo,' ',Idioma.CMM_Valor) AS curso,
		INSSG_Nivel AS nivel,
		PAMOD_Nombre AS paModalidadNombre,
		(SELECT Total FROM [dbo].[fn_getImpuestosArticulo](OVD_Cantidad,OVD_Precio,OVD_Descuento,OVD_IVA,OVD_IEPS,OVD_IEPSCuotaFija)) AS montoPago,
		MPPV_Nombre AS medioPago,
		EstatusINSSG.CMM_ControlId AS estatusId,
		EstatusINSSG.CMM_Valor AS estatus,

		-- Campos búsqueda query
		OV_SUC_SucursalId AS sucursalId
	
	FROM InscripcionesSinGrupo
	INNER JOIN Alumnos ON ALU_AlumnoId = INSSG_ALU_AlumnoId
	INNER JOIN Programas ON PROG_ProgramaId = INSSG_PROG_ProgramaId
	INNER JOIN ControlesMaestrosMultiples AS Idioma ON Idioma.CMM_ControlId = INSSG_CMM_IdiomaId
	INNER JOIN PAModalidades ON PAMOD_ModalidadId = INSSG_PAMOD_ModalidadId
	INNER JOIN OrdenesVentaDetalles ON OVD_OrdenVentaDetalleId = INSSG_OVD_OrdenVentaDetalleId
	INNER JOIN OrdenesVenta ON OV_OrdenVentaId = OVD_OV_OrdenVentaId
	INNER JOIN MediosPagoPV ON MPPV_MedioPagoPVId = OV_MPPV_MedioPagoPVId
	INNER JOIN ControlesMaestrosMultiples AS EstatusINSSG ON EstatusINSSG.CMM_ControlId = INSSG_CMM_EstatusId

GO