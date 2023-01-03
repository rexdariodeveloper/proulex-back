SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Rene Carrillo
-- Create date: 08/12/2022
-- Modify date:
-- Description:	La vista de el listado de  vale de certificacion
-- Version 1.0.0
-- =============================================

CREATE OR ALTER VIEW [dbo].[VW_LTO_ListadoValeCertificacion]
AS
	SELECT COALESCE(cv.Id, agcv.Id) AS Id,
		agcv.Codigo AS Codigo,
		COALESCE(cv.Alumno, agcv.Alumno) AS Alumno,
		COALESCE(cv.Sede, agcv.Sede) AS Sede,
		COALESCE(cv.Curso, agcv.Curso) AS Curso,
		COALESCE(cv.Nivel, agcv.Nivel) AS Nivel,
		COALESCE(cv.Certificacion, agcv.Certificacion) AS Certificacion,
		COALESCE(cv.Descuento, agcv.Descuento) AS Descuento,
		COALESCE(cv.Vigencia, agcv.Vigencia) AS Vigencia,
		COALESCE(cv.CostoFinal, agcv.CostoFinal) AS CostoFinal,
		COALESCE(cv.Estatus, agcv.Estatus) AS Estatus,
		COALESCE(cv.SucursalId, agcv.SucursalId) AS SucursalId,
		COALESCE(cv.CursoId, agcv.CursoId) AS CursoId,
		COALESCE(cv.ModalidadId, agcv.ModalidadId) AS ModalidadId,
		COALESCE(cv.FechaInicio, agcv.FechaInicio) AS FechaInicio
	FROM VW_AlumnosGruposValesCertificaciones agcv
		LEFT JOIN VW_ValesCertificaciones cv ON agcv.Id = cv.Id
GO