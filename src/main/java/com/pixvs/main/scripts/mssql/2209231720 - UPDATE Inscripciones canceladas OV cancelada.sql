DELETE AlumnosExamenesCalificaciones
FROM OrdenesVentaCancelacionesDetalles
     INNER JOIN Inscripciones ON OVCD_OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaDetalleId AND INS_CMM_EstatusId IN(2000510, 2000513) -- Pagada, Baja
     INNER JOIN AlumnosGrupos ON INS_InscripcionId = ALUG_INS_InscripcionId
     INNER JOIN ProgramasGrupos ON ALUG_PROGRU_GrupoId = PROGRU_GrupoId
     INNER JOIN AlumnosExamenesCalificaciones ON PROGRU_GrupoId = AEC_PROGRU_GrupoId AND ALUG_ALU_AlumnoId = AEC_ALU_AlumnoId
GO

DELETE AlumnosAsistencias
FROM OrdenesVentaCancelacionesDetalles
     INNER JOIN Inscripciones ON OVCD_OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaDetalleId AND INS_CMM_EstatusId IN(2000510, 2000513) -- Pagada, Baja
     INNER JOIN AlumnosGrupos ON INS_InscripcionId = ALUG_INS_InscripcionId
     INNER JOIN ProgramasGrupos ON ALUG_PROGRU_GrupoId = PROGRU_GrupoId
     INNER JOIN AlumnosAsistencias ON PROGRU_GrupoId = AA_PROGRU_GrupoId AND ALUG_ALU_AlumnoId = AA_ALU_AlumnoId
GO

DELETE AlumnosGrupos
FROM OrdenesVentaCancelacionesDetalles
     INNER JOIN Inscripciones ON OVCD_OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaDetalleId AND INS_CMM_EstatusId IN(2000510, 2000513) -- Pagada, Baja
     INNER JOIN AlumnosGrupos ON INS_InscripcionId = ALUG_INS_InscripcionId
GO

UPDATE Inscripciones
  SET
      INS_CMM_EstatusId = 2000512 -- Cancelada
FROM OrdenesVentaCancelacionesDetalles
     INNER JOIN Inscripciones ON OVCD_OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaDetalleId AND INS_CMM_EstatusId IN(2000510, 2000513) -- Pagada, Baja
GO