

INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL])
VALUES ( 1, GETDATE(), N'print', (select MP_NodoId from MenuPrincipal where MP_NodoPadreId = 1100 AND MP_Titulo = 'Reportes'), 1, 1000021, N'Impresión de contratos', N'Contract printing', N'item', N'/app/ingreso-promocion/reportes/contratos-imprimir')
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Impresión de contratos' and MP_Icono = 'print' and MP_Orden = 1)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO


INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL])
VALUES ( 1, GETDATE(), N'folder_shared', (select MP_NodoId from MenuPrincipal where MP_NodoPadreId = 1100 AND MP_Titulo = 'Reportes'), 2, 1000021, N'Poliza seguro de vida', N'Life insurance policy', N'item', N'/app/ingreso-promocion/reportes/polizas-seguro-vida')
GO


INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Poliza seguro de vida' and MP_Icono = 'folder_shared' and MP_Orden = 2)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO



SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Puestos](
	[PUE_PuestoId] [int] IDENTITY(1,1) NOT NULL,
	[PUE_Codigo] [nvarchar](10) NOT NULL,
	[PUE_Nombre] [nvarchar](100) NULL,
	[PUE_Descripcion] [nvarchar](150) NULL,
	[PUE_Proposito] [nvarchar](150) NULL,
	[PUE_Observaciones] [nvarchar](150) NULL,
	[PUE_CMM_EstadoPuestoId] [int] NOT NULL,
	[PUE_FechaCreacion] [datetime] NOT NULL,
	[PUE_USU_CreadoPorId] [int] NOT NULL,
	[PUE_FechaModificacion] [datetime] NULL,
	[PUE_USU_ModificadoPorId] [int] NULL,
	CONSTRAINT [PK_Puestos] PRIMARY KEY CLUSTERED
(
	[PUE_PuestoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Puestos] WITH CHECK ADD CONSTRAINT [UNQ_PUE_Codigo] UNIQUE ([PUE_Codigo])
GO

ALTER TABLE [dbo].[Puestos]  WITH CHECK ADD  CONSTRAINT [FK_PUE_CMM_EstadoPuestoId] FOREIGN KEY([PUE_CMM_EstadoPuestoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Puestos]  WITH CHECK ADD  CONSTRAINT [FK_PUE_USU_CreadoPorId] FOREIGN KEY([PUE_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Puestos]  WITH CHECK ADD  CONSTRAINT [FK_PUE_USU_ModificadoPorId] FOREIGN KEY([PUE_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO


SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[PuestoHabilidadResponsabilidad](
    [PUEHR_PuestoHabilidadResponsabilidadId] [int] IDENTITY(1,1) NOT NULL,
    [PUEHR_PUE_PuestoId] [int] NOT NULL,
    [PUEHR_Descripcion] [nvarchar](250) NULL,
    [PUEHR_EsHabilidad] [BIT] NOT NULL,
CONSTRAINT [PK_PuestoHabilidadResponsabilidad] PRIMARY KEY CLUSTERED
(
	[PUEHR_PuestoHabilidadResponsabilidadId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[PuestoHabilidadResponsabilidad]  WITH CHECK ADD  CONSTRAINT [FK_PUEHR_PUE_PuestoId] FOREIGN KEY([PUEHR_PUE_PuestoId])
REFERENCES [dbo].[Puestos] ([PUE_PuestoId])
GO

INSERT [dbo].[MenuPrincipal] (
    [MP_Activo],
    [MP_FechaCreacion],
    [MP_Icono],
    [MP_NodoPadreId],
    [MP_Orden],
    [MP_CMM_SistemaAccesoId],
    [MP_Titulo],
    [MP_TituloEN],
    [MP_Tipo],
    [MP_URL]
)
VALUES (
    1, -- [MP_Activo]
    GETDATE(), -- [MP_FechaCreacion]
    N'work_outline', -- [MP_Icono]
    (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = 1 AND MP_Titulo = 'Catálogos' AND MP_Orden = 3), -- [MP_NodoPadreId]
    14, -- [MP_Orden]
    1000021, -- [MP_CMM_SistemaAccesoId]
    N'Puestos', -- [MP_Titulo]
    N'Job positions', -- [MP_TituloEN]
    N'item', -- [MP_Tipo]
    N'/app/catalogos/puestos' -- [MP_URL]
)
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Puestos' and MP_Icono = 'work_outline' and MP_Orden = 14)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO


ALTER TABLE Entidades ADD ENT_Nombre_BD VARCHAR(50)
GO




SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_LISTADO_PUESTOS]
AS
     SELECT
     	PUE_Nombre AS "Nombre",
     	PUE_Descripcion AS "Descripción",
     	PUE_Proposito AS "Propósito",
     	PUE_Observaciones AS "Observaciones",
        CMM_Valor AS "Estatus"
     FROM Puestos
     INNER JOIN ControlesMaestrosMultiples ON PUE_CMM_EstadoPuestoId = CMM_ControlId
GO


/****       Eliminamos la columna de departamento y agregamos el de puesto       ****/

ALTER TABLE EmpleadosContratos DROP CONSTRAINT FK_EMPCO_DEP_DepartamentoId
GO

ALTER TABLE EmpleadosContratos DROP COLUMN EMPCO_DEP_DepartamentoId
GO

ALTER TABLE EmpleadosContratos ADD EMPCO_PUE_PuestoId INT NOT NULL
GO

ALTER TABLE [dbo].[EmpleadosContratos]  WITH CHECK ADD  CONSTRAINT [FK_EMPCO_PUE_PuestoId] FOREIGN KEY([EMPCO_PUE_PuestoId])
REFERENCES [dbo].[Puestos] ([PUE_PuestoId])
GO

ALTER TABLE EmpleadosContratos ADD EMPCO_PropositoPuesto VARCHAR(2000) DEFAULT NULL
GO


-- Departamentos
ALTER TABLE Departamentos ADD DEP_PUE_PuestoId INT DEFAULT NULL
GO

ALTER TABLE [dbo].[Departamentos]  WITH CHECK ADD  CONSTRAINT [FK_DEP_PUE_PuestoId] FOREIGN KEY([DEP_PUE_PuestoId])
REFERENCES [dbo].[Puestos] ([PUE_PuestoId])
GO




CREATE TABLE [dbo].[EmpleadosContratosResponsabilidad](
    [EMPCORES_EmpleadosContratosResponsabilidadId] [int] IDENTITY(1,1) NOT NULL,
    [EMPCORES_EMPCO_EmpleadoContratoId] [int] NOT NULL,
    [EMPCORES_Descripcion] [nvarchar](250) NOT NULL
CONSTRAINT [PK_EMPCORES_EmpleadosContratosResponsabilidadId] PRIMARY KEY CLUSTERED
(
	[EMPCORES_EmpleadosContratosResponsabilidadId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[EmpleadosContratosResponsabilidad]  WITH CHECK ADD  CONSTRAINT [FK_EMPCORES_EMPCO_EmpleadoContratoId] FOREIGN KEY([EMPCORES_EMPCO_EmpleadoContratoId])
REFERENCES [dbo].[EmpleadosContratos] ([EMPCO_EmpleadoContratoId])
GO
-- Funcion de empleados para entidades
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER PROCEDURE [dbo].[sp_getEmpleadosEntidad] @entidadId INTEGER, @todosEmpleados INTEGER, @idsEmpleados VARCHAR(500)
AS

	DECLARE @stmt  nvarchar(max),
	        @nombreBD VARCHAR(50),
            @empleados VARCHAR(MAX)
    SELECT @nombreBD = ENT_Nombre_BD FROM Entidades WHERE ENT_EntidadId = @entidadId
    IF @todosEmpleados = 1
        BEGIN
            SET @empleados = 'EMP_EmpleadoId = EMP_EmpleadoId'
        END
    ELSE
        BEGIN
            SET @empleados = 'EMP_EmpleadoId IN ('+@idsEmpleados+')'
        END

	SELECT @stmt = '
		SELECT
            EMP_EmpleadoId As id,
            EMP_CodigoEmpleado codigoEmpleado,
            EMP_Nombre AS nombre,
            EMP_PrimerApellido AS primerApellido,
            EMP_SegundoApellido AS segundoApellido
        FROM ['+@nombreBD+'].[dbo].[Empleados] AS emp
        WHERE
            EMP_CMM_EstatusId = 2000950
            AND '+@empleados+'
        OPTION(RECOMPILE)'
     -- select @stmt;
EXEC sp_executesql @stmt = @stmt


-- Funcion para listado de contratos

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER PROCEDURE [dbo].[sp_getContratosListado] @entidadId INTEGER, @anio INTEGER, @todosEmpleados INTEGER, @idsEmpleados VARCHAR(500)
AS

	DECLARE @stmt nvarchar(max),
			@nombreBD VARCHAR(50),
			@entidadNombre VARCHAR(500),
			@empleados VARCHAR(MAX)
	SELECT @entidadNombre = ENT_Nombre, @nombreBD = ENT_Nombre_BD FROM Entidades WHERE ENT_EntidadId = @entidadId
	IF @todosEmpleados = 1
		BEGIN
			SET @empleados = 'EMP_EmpleadoId = EMP_EmpleadoId'
		END
	ELSE
		BEGIN
			SET @empleados = 'EMP_EmpleadoId IN ('+@idsEmpleados+')'
		END


	SELECT @stmt = '
		SELECT
        	EMPCO_EmpleadoContratoId AS id,
        	CAST('+CAST(@entidadId AS VARCHAR)+' AS INTEGER) AS entidadId,
			'''+@entidadNombre+''' AS entidadNombre,
			EMP_EmpleadoId AS empleadoId,
        	EMP_CodigoEmpleado AS codigoEmpleado,
        	CONCAT(EMP_Nombre, '' '', EMP_PrimerApellido, '' '', COALESCE(NULL, '''')) AS nombreApellidos,
        	EMP_FechaAlta AS fechaAlta,
        	tipoCon.CMM_Valor AS tipoContrato,
        	EMPCO_FechaInicio AS fechaInicio,
        	EMPCO_FechaFin AS fechaFin,
        	EMPCO_SueldoMensual AS sueldoMensual
        FROM ['+@nombreBD+'].[dbo].[EmpleadosContratos]
        INNER JOIN ['+@nombreBD+'].[dbo].[Empleados] ON EMPCO_EMP_EmpleadoId = EMP_EmpleadoId
        INNER JOIN ['+@nombreBD+'].[dbo].[ControlesMaestrosMultiples] AS tipoCon ON EMPCO_CMM_TipoContratoId = tipoCon.CMM_ControlId
        WHERE YEAR(EMPCO_FechaInicio) = '+CAST(@anio AS VARCHAR)+'
			AND '+@empleados+'
		OPTION(RECOMPILE)'
EXEC sp_executesql @stmt = @stmt





/****** Object:  UserDefinedFunction [dbo].[fn_getContrato]    Script Date: /08/2022 10:39:09 a. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE OR ALTER PROCEDURE [dbo].[sp_getContratoEmpleado] @idContrato INT, @idEntidad INT --, @dbName VARCHAR(50)
AS

	DECLARE @db VARCHAR(50),
			@stmt  NVARCHAR(max)

	SET @db = (SELECT CONCAT('[',ENT_Nombre_BD,'].') FROM Entidades WHERE ENT_EntidadId = @idEntidad)

	SELECT @stmt = '
		SELECT
		    EMPCO.EMPCO_EmpleadoContratoId AS id,
			EMP.EMP_EmpleadoId AS empleadoId,
			(SELECT '+@db+'[dbo].[fn_titleCast](ENT_Nombre, 0)) AS entidad,
			CONCAT(''Contrato '', tipoC.CMM_Valor) AS tipoContrato,
			EMPCO.EMPCO_Codigo AS folioContrato,
			UPPER(CONCAT(EMP.EMP_Nombre, '' '', EMP.EMP_PrimerApellido, '' '', NULLIF(EMP.EMP_SegundoApellido, ''''))) AS nombre,
			(SELECT '+@db+'[dbo].[fn_titleCast](nacionalidad.CMM_Valor, 0) ) AS nacionalidad,
			(SELECT '+@db+'[dbo].[getFechaLetra](EMP.EMP_FechaNacimiento, null)) as fechaNacimiento,
			(SELECT '+@db+'[dbo].[fn_titleCast](gen.CMM_Valor, 0)) AS genero,
			(SELECT '+@db+'[dbo].[fn_titleCast](estCiv.CMM_Valor, 0)) AS estadoCivil,
			(SELECT '+@db+'[dbo].[fn_titleCast](gradEst.CMM_Valor, 0)) AS gradoEstudio,
			EMP.EMP_CURP AS curp,
			EMP.EMP_RFC as rfc,
			CONCAT(EMP.EMP_Domicilio, '' '',EMP.EMP_Colonia, '' '', EMP.EMP_Municipio, '' '', EST_Nombre) AS domicilio,
			PUE.PUE_Nombre AS puesto,
			EMPCO.EMPCO_PropositoPuesto AS propositoPuesto,
			EMPCO.EMPCO_IngresosAdicionales AS ingresosAdicionales,
			CONCAT(FORMAT(EMPCO.EMPCO_SueldoMensual,''C'', ''es-MX''), '' ('', LOWER(CONCAT((dbo.[getNumeroLetra](EMPCO.EMPCO_SueldoMensual,  ''MONEDA'', ''Peso'', ''Pesos'', ''es'' )), '' M.N.'')), '')'') AS sueldo,
			(SELECT '+@db+'[dbo].[getFechaLetra](EMPCO.EMPCO_FechaInicio, 2)) AS fechaInicio,
			(SELECT '+@db+'[dbo].[getFechaLetra](EMPCO.EMPCO_FechaFin, 2)) AS fechaFin,
			(SELECT '+@db+'[dbo].[fn_titleCast]( CONCAT(EMP.EMP_Nombre, '' '', EMP.EMP_PrimerApellido, '' '', NULLIF(EMP.EMP_SegundoApellido, '''')) , 0) ) AS nombreFooter,
			CONCAT(''Vigencia: '', (SELECT '+@db+'[dbo].[getFechaLetra](EMPCO.EMPCO_FechaInicio, null)),'' al '', (SELECT '+@db+'[dbo].[getFechaLetra](EMPCO.EMPCO_FechaFin, null)))AS vigencia,
			(CASE WHEN EMPCO.EMPCO_CMM_TipoHorarioId = 2000940
				THEN CONCAT(''de '', EMPCO.EMPCO_CantidadHoraSemana, '' ('', '+@db+'[dbo].[getNumeroLetra](EMPCO.EMPCO_CantidadHoraSemana, ''ENTERO'', NULL, NULL, ''es''),'') horas semanales'')
				ELSE (SELECT CONCAT('' de '', STRING_AGG (EMPH_Dia,'', '') ,'' con un horario de '', MIN(EMPH_HoraInicio), '' a '', MAX(EMPH_HoraFin), '' horas'') FROM EmpleadosHorarios WHERE EMPH_EMP_EmpleadoId = EMP.EMP_EmpleadoId) END
			)AS cargaHoraria,
			UPPER(CONCAT(EMPCOOR.EMP_Nombre, '' '', EMPCOOR.EMP_PrimerApellido, '' '', NULLIF(EMPCOOR.EMP_SegundoApellido, ''''))) AS nombreCoordinador,
			UPPER(CONCAT(EMPJEFUNI.EMP_Nombre, '' '', EMPJEFUNI.EMP_PrimerApellido, '' '', NULLIF(EMPJEFUNI.EMP_SegundoApellido, ''''))) AS nombreJefeUnidad,
			UPPER(CONCAT(EMPDIR.EMP_Nombre, '' '', EMPDIR.EMP_PrimerApellido, '' '', NULLIF(EMPDIR.EMP_SegundoApellido, ''''))) AS nombreDirector
		FROM '+@db+'[dbo].EmpleadosContratos AS EMPCO
		INNER JOIN '+@db+'[dbo].Puestos AS PUE ON EMPCO.EMPCO_PUE_PuestoId = PUE.PUE_PuestoId
		INNER JOIN '+@db+'[dbo].Empleados AS EMP ON EMPCO.EMPCO_EMP_EmpleadoId = EMP.EMP_EmpleadoId
		INNER JOIN '+@db+'[dbo].ControlesMaestrosMultiples tipoC ON EMPCO.EMPCO_CMM_TipoContratoId = CMM_ControlId
		INNER JOIN '+@db+'[dbo].Entidades ON ENT_EntidadId = '+CAST(@idEntidad AS VARCHAR)+' AND ENT_Activo = 1
		LEFT JOIN '+@db+'[dbo].ControlesMaestrosMultiples nacionalidad ON EMP.EMP_CMM_NacionalidadId = nacionalidad.CMM_ControlId
		LEFT JOIN '+@db+'[dbo].ControlesMaestrosMultiples gen ON EMP.EMP_CMM_GeneroId = gen.CMM_ControlId
		LEFT JOIN '+@db+'[dbo].ControlesMaestrosMultiples estCiv ON EMP.EMP_CMM_EstadoCivilId = estCiv.CMM_ControlId
		LEFT JOIN '+@db+'[dbo].ControlesMaestrosMultiples gradEst ON EMP.EMP_CMM_GradoEstudiosId = gradEst.CMM_ControlId
		LEFT JOIN '+@db+'[dbo].Estados ON EMP.EMP_EST_EstadoId = EST_EstadoId
		LEFT JOIN '+@db+'[dbo].Empleados EMPCOOR ON ENT_EMP_Coordinador = EMPCOOR.EMP_EmpleadoId
		LEFT JOIN '+@db+'[dbo].Empleados EMPJEFUNI ON ENT_EMP_JefeUnidadAF = EMPJEFUNI.EMP_EmpleadoId
		LEFT JOIN '+@db+'[dbo].Empleados EMPDIR ON ENT_EMP_Director = EMPDIR.EMP_EmpleadoId
		WHERE EMPCO.EMPCO_EmpleadoContratoId = '+CAST(@idContrato AS VARCHAR)+' '

     -- select @stmt;
EXEC sp_executesql @stmt = @stmt