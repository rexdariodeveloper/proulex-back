SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER PROCEDURE [dbo].[sp_getReporteCalificaciones] @usuarioId INT, @programaIdiomaId INT, @grupoId INT
AS

    DECLARE
        @cols  nvarchar(max),
        @stmt  nvarchar(max),
        @where nvarchar(max),

        @rolId int,
        @empleadoId int,
        @permisoSede bit,
        @permisoExtemp bit,
        @diasTolerancia nvarchar(max),
        @sucursalJOBSId int,
        @sucursalSEMSId int

    SET @cols           = (SELECT N'[empty]');
    SET @where          = (SELECT N'AND PROGI_ProgramaIdiomaId = ' + CAST(@programaIdiomaId as nvarchar) + N' ');
    SET @rolId          = (select USU_ROL_RolId from Usuarios where USU_UsuarioId = @usuarioId);
    SET @empleadoId     = (select EMP_EmpleadoId from Empleados where EMP_USU_UsuarioId = @usuarioId);
    SET @permisoSede    = (select CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END from RolesMenusPermisos where ROLMPP_ROL_RolId = @rolId and ROLMPP_MPP_MenuPrincipalPermisoId = 5);
    SET @permisoExtemp  = (select CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END from RolesMenusPermisos where ROLMPP_ROL_RolId = @rolId and ROLMPP_MPP_MenuPrincipalPermisoId = 7);
    SET @diasTolerancia = (select CMA_Valor from ControlesMaestros where CMA_Nombre = N'CM_SUMA_DIAS_FECHA_FIN');
    SET @sucursalJOBSId = (select CMM_Valor from ControlesMaestrosMultiples where CMM_Control = N'CMM_SUC_SucursalJOBSId');
    SET @sucursalSEMSId = (select CMM_Valor from ControlesMaestrosMultiples where CMM_Control = N'CMM_SUC_SucursalJOBSSEMSId');

	-- select @cols, @where, @rolId, @empleadoId, @permisoSede, @permisoExtemp, @diasTolerancia, @sucursalJOBSId, @sucursalSEMSId

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
        SET @where = @where + N'AND PROGRU_SUC_SucursalId IN ( select ALM_SUC_SucursalId from UsuariosAlmacenes inner join Almacenes on USUA_ALM_AlmacenId = ALM_AlmacenId where USUA_USU_UsuarioId = ' + CAST(@usuarioId as nvarchar) + ') '
    END
	
    SELECT @cols = ISNULL(@cols + ', ', '') + '[' + PAAE_Actividad + ']'
    FROM(
        SELECT [PAAE_Actividad], MIN([PROGRUE_ProgramaGrupoExamenId]) AS PROGRUE_ProgramaGrupoExamenId
		FROM [dbo].[ProgramasGruposExamenes]
		INNER JOIN [dbo].[ProgramasGrupos] ON [PROGRUE_PROGRU_GrupoId] = [PROGRU_GrupoId]
		INNER JOIN [dbo].[ProgramasGruposExamenesDetalles] ON [PROGRUE_ProgramaGrupoExamenId] = [PROGRUED_PROGRUE_ProgramaGrupoExamenId]
		INNER JOIN [dbo].[PAActividadesEvaluacion] on [PROGRUED_PAAE_ActividadEvaluacionId] = [PAAE_ActividadEvaluacionId]
		WHERE [PROGRU_PROGI_ProgramaIdiomaId] = @programaIdiomaId
		GROUP BY [PAAE_Actividad]
    ) Q
    ORDER BY PROGRUE_ProgramaGrupoExamenId

	SELECT @stmt = '
    SELECT 
        ALU_CodigoAlumnoUDG codigo,
        ALU_PrimerApellido primerApellido,
        ALU_SegundoApellido segundoApellido,
        ALU_Nombre nombre,
        grado.CMM_Valor grado,
        ALU_Grupo grupo,
        turno.CMM_Valor turno,
        PROGRU_Nivel nivel,
        SP_Nombre plantel,
        CONCAT(PROG_Codigo,'' '',idioma.CMM_Valor,'' '',PAMOD_Nombre,'' Nivel '',FORMAT(PROGRU_Nivel,''00''),'' Grupo '',FORMAT(PROGRU_Grupo,''00'')) codigoGrupo,
        PAMODH_Horario horario,
        COALESCE(prepa.CMM_Valor, cu.CMM_Referencia) escuela,
		COALESCE(ALU_BachilleratoTecnologico, carrera.CMM_Valor) carrera,
        primeroCiclos.codigo cohorte,
        N''S�'' regular,
        N''S�'' carta,
        ALU_Codigo codigoProulex,
		estatusAlumno.CMM_Valor estatusAlumno,
        calificaciones.*,
		[dbo].[fn_redondearCalificaciones](ALUG_CalificacionFinal) calificacionJOBS,
		[dbo].[fn_redondearCalificaciones](ALUG_CalificacionFinal) calificacionFinal
    FROM
        ProgramasGrupos
        INNER JOIN Inscripciones ON PROGRU_GrupoId = INS_PROGRU_GrupoId
        INNER JOIN Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
		LEFT JOIN AlumnosGrupos ON ALU_AlumnoId = ALUG_ALU_AlumnoId AND PROGRU_GrupoId = ALUG_PROGRU_GrupoId 
		LEFT JOIN ControlesMaestrosMultiples estatusAlumno ON ALUG_CMM_EstatusId = estatusAlumno.CMM_ControlId 
        LEFT JOIN
        (
        SELECT * FROM
            (
                SELECT 
					PROGRU_PROGI_ProgramaIdiomaId programaIdiomaId,
					PROGRU_GrupoId grupoId,
					PAAE_Actividad actividad,
					AEC_ALU_AlumnoId alumnoId,
					CASE WHEN AEC_Puntaje IS NOT NULL THEN (CAST(PROGRUE_Porcentaje as decimal(28,2)) / CAST(PROGRUED_Puntaje as decimal(28,2))) * AEC_Puntaje ELSE 0.00 END puntaje
				FROM ProgramasGruposExamenes
				INNER JOIN ProgramasGruposExamenesDetalles ON PROGRUE_ProgramaGrupoExamenId = PROGRUED_PROGRUE_ProgramaGrupoExamenId
				INNER JOIN PAActividadesEvaluacion on PROGRUED_PAAE_ActividadEvaluacionId = PAAE_ActividadEvaluacionId
				LEFT JOIN AlumnosExamenesCalificaciones on PROGRUED_ProgramaGrupoExamenDetalleId = AEC_PROGRUED_ProgramaGrupoExamenDetalleId
				LEFT JOIN ProgramasGrupos on AEC_PROGRU_GrupoId = PROGRU_GrupoId
                WHERE PROGRU_PROGI_ProgramaIdiomaId = ' + CAST(@programaIdiomaId as nvarchar) + ' AND PROGRUE_PROGRU_GrupoId = '+ COALESCE(CAST(@grupoId as nvarchar),'PROGRUE_PROGRU_GrupoId') +'
            ) detalles
        ) t
        PIVOT(
            MAX(puntaje)
            FOR actividad IN (' + @cols + ')
        ) calificaciones on PROGRU_PROGI_ProgramaIdiomaId = programaIdiomaId and PROGRU_GrupoId = grupoId and ALU_AlumnoId = alumnoId
        INNER JOIN ProgramasIdiomas on PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
        INNER JOIN ControlesMaestrosMultiples idioma on PROGI_CMM_Idioma = idioma.CMM_ControlId
        INNER JOIN Programas on PROGI_PROG_ProgramaId = PROG_ProgramaId
        INNER JOIN PAModalidades on PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
        INNER JOIN PAModalidadesHorarios on PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
        LEFT JOIN ControlesMaestrosMultiples grado on ALU_CMM_GradoId = grado.CMM_ControlId
        LEFT JOIN ControlesMaestrosMultiples turno on ALU_CMM_TurnoId = turno.CMM_ControlId
        LEFT JOIN ControlesMaestrosMultiples prepa on ALU_CMM_PreparatoriaJOBSId = prepa.CMM_ControlId
		LEFT JOIN ControlesMaestrosMultiples cu on ALU_CMM_CentroUniversitarioJOBSId = cu.CMM_ControlId
		LEFT JOIN ControlesMaestrosMultiples carrera on ALU_CMM_CarreraJOBSId = carrera.CMM_ControlId
        LEFT JOIN SucursalesPlanteles on SP_SucursalPlantelId = PROGRU_SP_SucursalPlantelId
        LEFT JOIN (select ins.INS_ALU_AlumnoId alumnoId, p.PROGRU_GrupoId grupoId, pa.PACIC_Codigo codigo from 
        (
        SELECT i.INS_ALU_AlumnoId,  
            i.INS_PROGRU_GrupoId, 
            ROW_NUMBER() OVER(PARTITION BY i.INS_ALU_AlumnoId 
                                    ORDER BY i.INS_FechaCreacion ASC) AS rank
        FROM Inscripciones i) ins
        inner join ProgramasGrupos p on ins.INS_PROGRU_GrupoId = p.PROGRU_GrupoId
        left join PACiclos pa on p.PROGRU_PACIC_CicloId = pa.PACIC_CicloId
        where ins.rank = 1) primeroCiclos on primeroCiclos.alumnoId = ALU_AlumnoId
    WHERE 
        ALU_Activo = 1
        AND INS_CMM_EstatusId NOT IN(2000512, 2000513) '
    + @where +
    'ORDER BY
            PROGRU_GrupoId, ALU_PrimerApellido, ALU_SegundoApellido, ALU_Nombre';
    
    --select @stmt;
    EXEC sp_executesql @stmt = @stmt
