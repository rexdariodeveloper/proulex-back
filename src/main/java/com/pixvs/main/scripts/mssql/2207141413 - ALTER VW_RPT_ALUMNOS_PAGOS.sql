SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER VIEW [dbo].[VW_RPT_ALUMNOS_PAGOS]
AS
     SELECT ALU_CodigoAlumnoUDG codigo,
            ALU_PrimerApellido primerApellido,
            ALU_SegundoApellido segundoApellido,
            ALU_Nombre nombre,
            COALESCE(centros.CMM_Valor, prepas.CMM_Valor) plantel,
            PROGRU_Codigo clave,
            CONCAT_WS(' ', PROG_Codigo, idiomas.CMM_Valor, PAMOD_Nombre, 'Nivel', FORMAT(PROGRU_Nivel, '00'), 'Grupo', FORMAT(PROGRU_Grupo, '00')) grupo,
            CONCAT_WS(' - ', CAST(PAMODH_HoraInicio AS NVARCHAR(5)), CAST(PAMODH_HoraFin AS NVARCHAR(5))) horario,
            CASE WHEN PROG_JOBSSEMS = 1 THEN NULL ELSE SedesGrupo.SUC_Nombre END sede,
            SP_Nombre escuela,
            ALU_Referencia referencia,
            CASE WHEN INS_CMM_EstatusId = 2000510 AND OV_CMM_EstatusId IN (2000507, 2000508) AND OV_CMM_MetodoPago IS NULL THEN OV_Codigo ELSE NULL END poliza, -- Solo mostrar Pagada o Facturada
			CASE WHEN INS_CMM_EstatusId = 2000510 AND OV_CMM_EstatusId IN (2000507, 2000508) AND OV_CMM_MetodoPago IS NULL THEN montos.Total ELSE NULL END precio,
            estatus.CMM_Valor estatus,
            Sedes.SUC_SucursalId sedeId,
            PROGRU_SP_SucursalPlantelId plantelId,
            PROGRU_PACIC_CicloId cicloId,
            PROGRU_PAC_ProgramacionAcademicaComercialId paId,
            CAST(PROGRU_FechaInicio AS DATE) fechaInicio,
            INS_CMM_EstatusId estatusId
     FROM Inscripciones
          INNER JOIN ProgramasGrupos ON INS_PROGRU_GrupoId = PROGRU_GrupoId
          INNER JOIN Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
          LEFT JOIN ControlesMaestrosMultiples centros ON ALU_CMM_CentroUniversitarioJOBSId = centros.CMM_ControlId
          LEFT JOIN ControlesMaestrosMultiples prepas ON ALU_CMM_PreparatoriaJOBSId = prepas.CMM_ControlId
          INNER JOIN ProgramasIdiomas ON PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
          INNER JOIN ControlesMaestrosMultiples idiomas ON PROGI_CMM_Idioma = idiomas.CMM_ControlId
          INNER JOIN Programas ON PROGI_PROG_ProgramaId = PROG_ProgramaId
          INNER JOIN PAModalidades ON PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
          INNER JOIN PAModalidadesHorarios ON PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
          LEFT JOIN SucursalesPlanteles ON PROGRU_SP_SucursalPlantelId = SP_SucursalPlantelId
          INNER JOIN OrdenesVentaDetalles ON INS_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
          INNER JOIN OrdenesVenta ON OVD_OV_OrdenVentaId = OV_OrdenVentaId
          LEFT JOIN
		  (
				SELECT OVD_OVD_DetallePadreId padreId,
					   SUM(OVD_Cantidad * OVD_Precio) precio
				FROM OrdenesVentaDetalles
				GROUP BY OVD_OVD_DetallePadreId
		  ) hijos ON OVD_OrdenVentaDetalleId = padreId
          LEFT JOIN Sucursales SedesGrupo ON PROGRU_SUC_SucursalId = SedesGrupo.SUC_SucursalId
          LEFT JOIN Sucursales Sedes ON OV_SUC_SucursalId = Sedes.SUC_SucursalId
          LEFT JOIN AlumnosGrupos ON ALUG_INS_InscripcionId = INS_InscripcionId
          LEFT JOIN ControlesMaestrosMultiples estatus ON ALUG_CMM_EstatusId = estatus.CMM_ControlId
		  OUTER APPLY dbo.fn_getDatosFacturacionOV(Sedes.SUC_SucursalId, OV_Codigo) AS montos
     WHERE INS_CMM_EstatusId != 2000512
GO