/****** Object:  UserDefinedFunction [dbo].[fn_getContrato]    Script Date: 15/03/2022 04:34:45 a. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO




ALTER   FUNCTION [dbo].[fn_getContrato] (
    @idEmpleado INTEGER, @fechaInicio DATE, @fechaFin DATE, @idModalidad INTEGER, @idCiclo INTEGER, @idCmmIdioma INTEGER, @idPlantel INTEGER, @idIdioma INTEGER, @idSucursal INTEGER
    )
RETURNS TABLE
AS
RETURN
(
    SELECT
        EMP_EmpleadoId as profesorId,
        tipoEmp.CMM_Valor AS tipoEmpleado,
        CONCAT(UPPER(EMP_Nombre),' ',UPPER(EMP_PrimerApellido),' ',UPPER(EMP_SegundoApellido)) as nombreProfesor,
        (SELECT [dbo].[fn_titleCast](nacionalidad.CMM_Valor, 0) ) AS nacionalidad,
        (SELECT [dbo].[getFechaLetra](EMP_FechaNacimiento, null)) as fechaNacimiento,
        (SELECT [dbo].[fn_titleCast](gen.CMM_Valor, 0)) AS genero,
        (SELECT [dbo].[fn_titleCast](estCiv.CMM_Valor, 0)) AS estadoCivil,
        (SELECT [dbo].[fn_titleCast](gradEst.CMM_Valor, 0)) AS gradoEstudio,
        EMP_CURP AS curp,
        EMP_RFC as rfc,
        CONCAT(EMP_Domicilio, ' ',EMP_Colonia, ' ', EMP_Municipio, ' ', EST_Nombre) AS domicilio,
        EMP_CodigoEmpleado as codigoProfesor,
        (CASE WHEN PROGRU_CMM_TipoGrupoId = 2000390 THEN CONCAT(SUC_Nombre, ' -', SP_Nombre) ELSE SP_Nombre END
        ) as plantel,
        CONCAT(SP_Direccion,' Colonia ',SP_Colonia,' ',SP_Municipio,',',' Jalisco, México') as domicilioPlantel,
        PROGRU_Nivel as nivel,
        PROGRU_SueldoProfesor as salario,
        CONCAT(CASE WHEN PAMOD_Lunes=1 THEN 'Lun,' ELSE '' END,
        CASE WHEN PAMOD_Martes=1 THEN 'Mar,' ELSE '' END,CASE WHEN PAMOD_Miercoles=1 THEN 'Mie,' ELSE '' END,
        CASE WHEN PAMOD_Jueves=1 THEN 'Jue,' ELSE '' END,CASE WHEN PAMOD_Viernes=1 THEN 'Vie,' ELSE '' END,
        CASE WHEN PAMOD_Sabado=1 THEN 'Sab,' ELSE '' END,CASE WHEN PAMOD_Domingo=1 THEN 'Dom,' ELSE '' END) as modalidad,
        FORMAT(CAST(@fechaInicio AS DATE), 'dd ''del mes'' MMMM ''del año'' yyyy', 'es-MX') fechaInicioContrato,
        FORMAT(CAST(@fechaFin AS DATE), 'dd ''del mes'' MMMM ''del año'' yyyy', 'es-MX') fechaFinContrato,
        FORMAT(CAST(GETDATE() AS DATE), 'dd ''del mes'' MMMM ''del año'' yyyy', 'es-MX') fechaImpresion,
        CONCAT(programa.CMM_Valor,' ',PROGRU_CategoriaProfesor) as programa,
        (Select UPPER(right(CMA_Valor, charindex('-', reverse(CMA_Valor)) - 1)) from ControlesMaestros where CMA_Nombre='CM_DIRECTOR_GENERAL') as directorGeneral,
        (Select UPPER(right(CMA_Valor, charindex('-', reverse(CMA_Valor)) - 1)) from ControlesMaestros where CMA_Nombre='CM_DIRECTOR_RECURSOS_HUMANOS') as directorRecursosHumanos,
        (SELECT UPPER(CONCAT(USU_Nombre,' ',USU_PrimerApellido,' ',USU_SegundoApellido))
        FROM Usuarios
        INNER JOIN Sucursales on SUC_USU_ResponsableId = USU_UsuarioId WHERE SUC_SucursalId = PROGRU_SUC_SucursalId) as directorSede,
        (SELECT UPPER(CONCAT(USU_Nombre,' ',USU_PrimerApellido,' ',USU_SegundoApellido))
    FROM Usuarios
    INNER JOIN Sucursales on SUC_USU_CoordinadorId = USU_UsuarioId WHERE SUC_SucursalId = PROGRU_SUC_SucursalId) as coordinadorSede
    from ProgramasGrupos
    INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
    INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
    INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
    INNER JOIN VW_ProgramasGruposProfesores ON grupoId = PROGRU_GrupoId
    INNER JOIN Empleados ON EMP_EmpleadoId = empleadoId
    LEFT JOIN SucursalesPlanteles ON SP_SucursalPlantelId = PROGRU_SP_SucursalPlantelId
    INNER JOIN ControlesMaestrosMultiples programa on programa.CMM_ControlId = PROGI_CMM_Idioma
    INNER JOIN Sucursales on SUC_SucursalId = PROGRU_SUC_Sucursalid
    INNER JOIN Usuarios on USU_UsuarioId = SUC_USU_ResponsableId OR USU_UsuarioId = SUC_USU_CoordinadorId
    LEFT JOIN ControlesMaestrosMultiples nacionalidad ON EMP_CMM_NacionalidadId = nacionalidad.CMM_ControlId
    LEFT JOIN ControlesMaestrosMultiples gen ON EMP_CMM_GeneroId = gen.CMM_ControlId
    LEFT JOIN ControlesMaestrosMultiples estCiv ON EMP_CMM_EstadoCivilId = estCiv.CMM_ControlId
    LEFT JOIN ControlesMaestrosMultiples gradEst ON EMP_CMM_GradoEstudiosId = gradEst.CMM_ControlId
    LEFT JOIN ControlesMaestrosMultiples tipoEmp ON EMP_CMM_TipoEmpleadoId = tipoEmp.CMM_ControlId
    LEFT JOIN Estados ON EMP_EST_EstadoId = EST_EstadoId
    WHERE EMP_EmpleadoId= @idEmpleado
    AND PROGRU_PROGI_ProgramaIdiomaId = COALESCE(@idIdioma, PROGRU_PROGI_ProgramaIdiomaId)
    AND PROGRU_FechaInicio = @fechaInicio
    AND PROGRU_FechaFin = @fechaFin
    AND PAMOD_ModalidadId =  @idModalidad
    AND (@idCiclo IS NULL OR PROGRU_PACIC_CicloId =  @idCiclo)
    AND programa.CMM_ControlId =  @idCmmIdioma
    AND (@idPlantel IS NULL OR PROGRU_SP_SucursalPlantelId = @idPlantel)
    AND PROGRU_SUC_SucursalId =  @idSucursal
    GROUP BY PROGRU_PROGI_ProgramaIdiomaId,PROGRU_SP_SucursalPlantelId,PROGRU_FechaInicio,EMP_EmpleadoId,
    PROGRU_FechaFin,PROGRU_PAMOD_ModalidadId,EMP_Nombre,EMP_PrimerApellido,EMP_SegundoApellido, EMP_FechaNacimiento,EMP_CURP, EMP_RFC,
    EMP_Domicilio, EMP_Colonia, EMP_Municipio, EST_Nombre,EMP_CodigoEmpleado,SP_Nombre,SP_Direccion,SP_Colonia,PROGRU_Nivel,
    PROGRU_SueldoProfesor,PAMOD_Nombre, PROG_Codigo,programa.CMM_Valor, nacionalidad.CMM_Valor, gen.CMM_Valor, estCiv.CMM_Valor, gradEst.CMM_Valor,
    tipoEmp.CMM_Valor ,PROGRU_SUC_SucursalId,SP_Municipio,PROGRU_CategoriaProfesor,PAMOD_Lunes,
    PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado,PAMOD_Domingo, fechaInicio, FechaFin, PROGRU_CMM_TipoGrupoId,
    SUC_Nombre
)
GO


