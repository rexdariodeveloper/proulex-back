SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER   PROCEDURE [dbo].[sp_getReporteAsistencias] @usuarioId INT, @grupoId INT
AS


   DECLARE
      @cols  nvarchar(max),
      @stmt  nvarchar(max),
      @where nvarchar(max),

      @rolId int,
      @empleadoId int,
      @permisoSede bit,
      @permisoExtemp bit,
      @diasTolerancia nvarchar(max)

   SET @where          = ''
   SET @rolId          = (select USU_ROL_RolId from Usuarios where USU_UsuarioId = @usuarioId)
   SET @empleadoId     = (select EMP_EmpleadoId from Empleados where EMP_USU_UsuarioId = @usuarioId)
   SET @permisoSede    = (select CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END from RolesMenusPermisos where ROLMPP_ROL_RolId = @rolId and ROLMPP_MPP_MenuPrincipalPermisoId = 6)
   SET @permisoExtemp  = (select CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END from RolesMenusPermisos where ROLMPP_ROL_RolId = @rolId and ROLMPP_MPP_MenuPrincipalPermisoId = 8)
   SET @diasTolerancia = (select CMA_Valor from ControlesMaestros where CMA_Nombre = N'CM_SUMA_DIAS_FECHA_FIN')

   IF @grupoId IS NOT NULL
   BEGIN
      SET @where = @where + N'AND PROGRU_GrupoId = ' + CAST(@grupoId as nvarchar) + N' '
   END
   
   IF @permisoExtemp = 0
   BEGIN
      SET @where = @where +  N'AND CAST(GETDATE() as date) <= (DATEADD(day,' + CAST(@diasTolerancia as nvarchar) + N', CAST(PROGRU_FechaFin as date))) '
   END

   IF @permisoSede = 0
   BEGIN
      SET @where = @where + N'AND (PROGRU_EMP_EmpleadoId IS NOT NULL and PROGRU_EMP_EmpleadoId = ' + CAST(@empleadoId as nvarchar) + ') '
   END
   ELSE
   BEGIN
      SET @where = @where + N'AND PROGRU_SUC_SucursalId IN (select ALM_SUC_SucursalId from UsuariosAlmacenes inner join Almacenes on USUA_ALM_AlmacenId = ALM_AlmacenId where USUA_USU_UsuarioId = ' + CAST(@usuarioId as nvarchar) + ') '
   END

   SELECT @cols = ISNULL(@cols + ', ', '') + '[' + FORMAT(T.fecha,'yyyy-MM-dd') + ']' FROM (select DISTINCT CAST(AA_Fecha AS DATE) fecha from AlumnosAsistencias INNER JOIN ProgramasGrupos on AA_PROGRU_GrupoId = PROGRU_GrupoId where PROGRU_CMM_EstatusId = 2000620) AS T

   SELECT @stmt = '
   SELECT 
      ALU_CodigoAlumnoUDG codigo,
      ALU_PrimerApellido primerApellido,
      ALU_SegundoApellido segundoApellido,
      ALU_Nombre nombre,
      coalesce(ALUG_Faltas, 0) as faltas, 
      coalesce(ALUG_Asistencias, 0) as asistencias,
      estatus.CMM_Valor as estatus,   
      grado.CMM_Valor grado,
      ALU_Grupo grupo,
      turno.CMM_Valor turno,
      PROGRU_Nivel nivel,
      CONCAT(EMP_Nombre, '' '', EMP_PrimerApellido, COALESCE('' ''+ EMP_SegundoApellido, '''')) AS profesor,
      SP_Nombre plantel,
      CONCAT(PROG_Codigo,'' '',idioma.CMM_Valor,'' '',PAMOD_Nombre,'' Nivel '',FORMAT(PROGRU_Nivel,''00''),'' Grupo '',FORMAT(PROGRU_Grupo,''00'')) codigoGrupo,
      PAMODH_Horario horario,
      prepa.CMM_Valor escuela,
      ALU_BachilleratoTecnologico carrera,
      primeroCiclos.codigo cohorte,
      N''Sí'' regular,
      N''Sí'' carta,
      ALU_Codigo codigoProulex,
      asistencias.*
   FROM ProgramasGrupos
   INNER JOIN Inscripciones ON PROGRU_GrupoId = INS_PROGRU_GrupoId
   INNER JOIN Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
   LEFT JOIN
   (
   SELECT AA_ALU_AlumnoId, AA_PROGRU_GrupoId,  CAST(AA_Fecha AS DATE) fecha,
      CASE 
         WHEN AA_CMM_TipoAsistenciaId = 2000550 THEN AA_CMM_TipoAsistenciaId
         WHEN AA_CMM_TipoAsistenciaId = 2000551 THEN AA_CMM_TipoAsistenciaId
         WHEN AA_CMM_TipoAsistenciaId = 2000552 THEN AA_CMM_TipoAsistenciaId
         WHEN AA_CMM_TipoAsistenciaId = 2000553 THEN AA_MinutosRetardo
      END tipo 
   FROM
   AlumnosAsistencias 
   ) t
   PIVOT(
      MAX(tipo)
      FOR fecha IN ('+@cols+')
   ) asistencias ON AA_ALU_AlumnoId = ALU_AlumnoId AND AA_PROGRU_GrupoId = PROGRU_GrupoId
   INNER JOIN ProgramasIdiomas on PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
   INNER JOIN ControlesMaestrosMultiples idioma on PROGI_CMM_Idioma = idioma.CMM_ControlId
   INNER JOIN Programas on PROGI_PROG_ProgramaId = PROG_ProgramaId
   INNER JOIN PAModalidades on PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
   INNER JOIN PAModalidadesHorarios on PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
   LEFT JOIN AlumnosGrupos  on ALUG_PROGRU_GrupoId = PROGRU_GrupoId and ALUG_ALU_AlumnoId = ALU_AlumnoId
   LEFT JOIN ControlesMaestrosMultiples grado on ALU_CMM_GradoId = grado.CMM_ControlId
   LEFT JOIN ControlesMaestrosMultiples turno on ALU_CMM_TurnoId = turno.CMM_ControlId
   LEFT JOIN ControlesMaestrosMultiples prepa on ALU_CMM_PreparatoriaJOBSId = prepa.CMM_ControlId
   LEFT JOIN ControlesMaestrosMultiples estatus on ALUG_CMM_EstatusId = estatus.CMM_ControlId 
   LEFT JOIN SucursalesPlanteles on SP_SucursalPlantelId = PROGRU_SP_SucursalPlantelId
   LEFT JOIN Empleados on PROGRU_EMP_EmpleadoId = EMP_EmpleadoId
   LEFT JOIN (select ins.INS_ALU_AlumnoId alumnoId, p.PROGRU_GrupoId grupoId, pa.PACIC_Codigo codigo from 
      (
      SELECT i.INS_ALU_AlumnoId,  
         i.INS_PROGRU_GrupoId, 
         ROW_NUMBER() OVER(PARTITION BY i.INS_ALU_AlumnoId 
                           ORDER BY i.INS_FechaCreacion DESC) AS rank
      FROM Inscripciones i) ins
      inner join ProgramasGrupos p on ins.INS_PROGRU_GrupoId = p.PROGRU_GrupoId
      left join PACiclos pa on p.PROGRU_PACIC_CicloId = pa.PACIC_CicloId
      where ins.rank = 1) primeroCiclos on grupoId = PROGRU_GrupoId and alumnoId = ALU_AlumnoId
   WHERE
      PROGRU_Activo = 1 
      AND ALU_Activo = 1
      AND INS_CMM_EstatusId <> 2000604 '
   + @where +
   'ORDER BY
      PROGRU_GrupoId, ALU_PrimerApellido, ALU_SegundoApellido, ALU_Nombre
   FOR
      JSON PATH'
   -- select @stmt
   EXEC sp_executesql @stmt = @stmt
