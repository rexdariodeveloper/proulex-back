CREATE OR ALTER VIEW [dbo].[VW_ALUMNOS_EXAMENES_CERTIFICACIONES]
AS
	select 
		ALUEC_AlumnoExamenCertificacionId id,
		ALUEC_FechaCreacion fecha,
		OV_Codigo codigo,
		CONCAT_WS(' ', ALU_Codigo,'-',ALU_Nombre,ALU_PrimerApellido,ALU_SegundoApellido) alumno,
		CONCAT_WS('-',ART_CodigoArticulo,ART_NombreArticulo) articulo,
		ALUEC_Nivel nivel,
		ALUEC_Calificacion calificacion,

		PROGI_NumeroNiveles nivelMaximo,
	
		ALUEC_ALU_AlumnoId alumnoId,
		ALUEC_CMM_TipoId tipoId,
		ALUEC_CMM_EstatusId estatusId
	from 
		AlumnosExamenesCertificaciones
		inner join Alumnos on ALUEC_ALU_AlumnoId = ALU_AlumnoId
		inner join Articulos on ALUEC_ART_ArticuloId = ART_ArticuloId
		inner join OrdenesVentaDetalles on ALUEC_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
		inner join OrdenesVenta on OVD_OV_OrdenVentaId = OV_OrdenVentaId
		left join ProgramasIdiomasCertificacion on ALUEC_ART_ArticuloId = PROGIC_ART_ArticuloId
		left join ProgramasIdiomas on PROGIC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
GO