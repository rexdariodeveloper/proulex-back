/**
 * Created by Angel Daniel Hernández Silva on 07/05/2021.
 * Object:  Table [dbo].[ProgramasMetas]
 */


/**************************/
/***** ProgramasMetas *****/
/**************************/

CREATE TABLE [dbo].[ProgramasMetas](
	[PM_ProgramaMetaId] [int] IDENTITY(1,1) NOT NULL ,
	[PM_Activo] [bit]  NOT NULL ,
	[PM_Codigo] [varchar]  (150) NOT NULL ,
	[PM_PAC_ProgramacionAcademicaComercialId] [int]  NOT NULL ,
	[PM_FechaCreacion] [datetime2](7) NOT NULL,
	[PM_USU_CreadoPorId] [int] NULL,
	[PM_FechaModificacion] [datetime2](7) NULL,
	[PM_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[PM_ProgramaMetaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ProgramasMetas]  WITH CHECK ADD  CONSTRAINT [FK_PM_PAC_ProgramacionAcademicaComercialId] FOREIGN KEY([PM_PAC_ProgramacionAcademicaComercialId])
REFERENCES [dbo].[ProgramacionAcademicaComercial] ([PAC_ProgramacionAcademicaComercialId])
GO

ALTER TABLE [dbo].[ProgramasMetas] CHECK CONSTRAINT [FK_PM_PAC_ProgramacionAcademicaComercialId]
GO

ALTER TABLE [dbo].[ProgramasMetas]  WITH CHECK ADD  CONSTRAINT [FK_PM_USU_ModificadoPorId] FOREIGN KEY([PM_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasMetas] CHECK CONSTRAINT [FK_PM_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[ProgramasMetas]  WITH CHECK ADD  CONSTRAINT [FK_PM_USU_CreadoPorId] FOREIGN KEY([PM_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasMetas] CHECK CONSTRAINT [FK_PM_USU_CreadoPorId]
GO


/**********************************/
/***** ProgramasMetasDetalles *****/
/**********************************/

CREATE TABLE [dbo].[ProgramasMetasDetalles](
	[PMD_ProgramaMetaDetalleId] [int] IDENTITY(1,1) NOT NULL ,
	[PMD_PM_ProgramaMetaId] [int]  NOT NULL ,
	[PMD_SUC_SucursalId] [int]  NOT NULL ,
	[PMD_PROG_ProgramaId] [int]  NOT NULL ,
	[PMD_PACD_ProgramacionAcademicaComercialDetalleId] [int]  NOT NULL ,
	[PMD_Meta] [int]  NOT NULL
PRIMARY KEY CLUSTERED 
(
	[PMD_ProgramaMetaDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ProgramasMetasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PMD_PM_ProgramaMetaId] FOREIGN KEY([PMD_PM_ProgramaMetaId])
REFERENCES [dbo].[ProgramasMetas] ([PM_ProgramaMetaId])
GO

ALTER TABLE [dbo].[ProgramasMetasDetalles] CHECK CONSTRAINT [FK_PMD_PM_ProgramaMetaId]
GO

ALTER TABLE [dbo].[ProgramasMetasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PMD_SUC_SucursalId] FOREIGN KEY([PMD_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO

ALTER TABLE [dbo].[ProgramasMetasDetalles] CHECK CONSTRAINT [FK_PMD_SUC_SucursalId]
GO

ALTER TABLE [dbo].[ProgramasMetasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PMD_PROG_ProgramaId] FOREIGN KEY([PMD_PROG_ProgramaId])
REFERENCES [dbo].[Programas] ([PROG_ProgramaId])
GO

ALTER TABLE [dbo].[ProgramasMetasDetalles] CHECK CONSTRAINT [FK_PMD_PROG_ProgramaId]
GO

ALTER TABLE [dbo].[ProgramasMetasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PMD_PACD_ProgramacionAcademicaComercialDetalleId] FOREIGN KEY([PMD_PACD_ProgramacionAcademicaComercialDetalleId])
REFERENCES [dbo].[ProgramacionAcademicaComercialDetalles] ([PACD_ProgramacionAcademicaComercialDetalleId])
GO

ALTER TABLE [dbo].[ProgramasMetasDetalles] CHECK CONSTRAINT [FK_PMD_PACD_ProgramacionAcademicaComercialDetalleId]
GO


/**************************************************/
/***** Vista VW_REPORTE_EXCEL_PROGRAMAS_METAS *****/
/**************************************************/

CREATE OR ALTER VIEW [dbo].[VW_REPORTE_EXCEL_PROGRAMAS_METAS] AS

    SELECT
        PM_Codigo AS "Código",
        PAC_Nombre AS "Nombre",
        PACIC_Nombre AS "Ciclo",
        COALESCE(SUM(PMD_Meta),0) AS "Meta",
		CAST(0 AS varchar) + '/' + CAST(COALESCE(SUM(PMD_Meta),0) AS varchar) AS "Avance"
    FROM ProgramasMetas
    INNER JOIN ProgramacionAcademicaComercial ON PAC_ProgramacionAcademicaComercialId = PM_PAC_ProgramacionAcademicaComercialId
    INNER JOIN PACiclos ON PACIC_CicloId = PAC_PACIC_CicloId
    INNER JOIN ProgramasMetasDetalles ON PMD_PM_ProgramaMetaId = PM_ProgramaMetaId
    GROUP BY PM_Codigo, PAC_Nombre, PACIC_Nombre

GO

/********************************************/
/***** Vista VW_LISTADO_PROGRAMAS_METAS *****/
/********************************************/

CREATE OR ALTER VIEW [dbo].[VW_Listado_ProgramasMetas] AS

    SELECT
        PM_ProgramaMetaId AS id,
        PM_Codigo AS codigo,
        PAC_Nombre AS nombre,
        PACIC_Nombre AS ciclo,
        COALESCE(SUM(PMD_Meta),0) AS meta,
		0 AS inscripciones
    FROM ProgramasMetas
    INNER JOIN ProgramacionAcademicaComercial ON PAC_ProgramacionAcademicaComercialId = PM_PAC_ProgramacionAcademicaComercialId
    INNER JOIN PACiclos ON PACIC_CicloId = PAC_PACIC_CicloId
    INNER JOIN ProgramasMetasDetalles ON PMD_PM_ProgramaMetaId = PM_ProgramaMetaId
    GROUP BY PM_ProgramaMetaId, PM_Codigo, PAC_Nombre, PACIC_Nombre

GO

/**********************************************************************/
/***** Vista VW_MetaListado_ProgramacionAcademicaComercialDetalle *****/
/**********************************************************************/

CREATE OR ALTER VIEW [dbo].[VW_MetaListado_ProgramacionAcademicaComercialDetalle] AS

	SELECT
		PACD_PAC_ProgramacionAcademicaComercialId AS programacionAcademicaComercialId,
		PACD_ProgramacionAcademicaComercialDetalleId AS id,
		PAMOD_ModalidadId AS paModalidadId,
		PAMOD_Nombre AS paModalidadNombre,
		PACD_FechaInicio AS fechaInicio,
		PACD_FechaFin AS fechaFin,
		PROG_ProgramaId AS programaId,
		PROG_Nombre AS programaNombre
	FROM ProgramacionAcademicaComercialDetalles
	INNER JOIN PAModalidades ON PAMOD_ModalidadId = PACD_PAMOD_ModalidadId
	INNER JOIN ProgramacionAcademicaComercialDetallesProgramas ON PACDP_PACD_ProgramacionAcademicaComercialDetalleId = PACD_ProgramacionAcademicaComercialDetalleId
	INNER JOIN Programas ON PROG_ProgramaId = PACD_PROG_ProgramaId

GO

/******************************************************/
/***** Vista VW_EditarJson_ProgramasMetasDetalles *****/
/******************************************************/

CREATE OR ALTER VIEW [dbo].[VW_EditarJson_ProgramasMetasDetalles] AS

	SELECT
		programaMetaId,
		COALESCE('{ ' + STRING_AGG('"' + CAST(sedeId AS varchar(max)) + '" : ' + metas,', ') + ' }','{}') AS metas
	FROM(
		SELECT
			programaMetaId,
			sedeId,
			'{ ' + STRING_AGG('"' + CAST(programacionAcademicaComercialDetalleId AS varchar(max)) + '" : ' + metas,', ') + ' }' AS metas
		FROM(
			SELECT
				PMD_PM_ProgramaMetaId AS programaMetaId,
				PMD_SUC_SucursalId AS sedeId,
				PMD_PACD_ProgramacionAcademicaComercialDetalleId AS programacionAcademicaComercialDetalleId,
				'{ ' + STRING_AGG('"' + CAST(PMD_PROG_ProgramaId AS varchar(max)) + '" : ' + CAST(PMD_Meta AS varchar),', ') + ' }' AS metas
			FROM ProgramasMetasDetalles
			GROUP BY PMD_PM_ProgramaMetaId, PMD_SUC_SucursalId, PMD_PACD_ProgramacionAcademicaComercialDetalleId
		) ProgramasMetasDetallesGroupProgramacionAcademicaComercialDetalle
		GROUP BY programaMetaId, sedeId
	) ProgramasMetasDetallesGroupProgramacionAcademicaComercialDetalleGroupSucursal
	GROUP BY programaMetaId

GO

/**********************************************/
/***** Vista VW_PLANTILLA_PROGRAMAS_METAS *****/
/**********************************************/

CREATE OR ALTER VIEW [dbo].[VW_PLANTILLA_PROGRAMAS_METAS] AS

	SELECT
		PAC_ProgramacionAcademicaComercialId AS programacionAcademicaComercialId,
		PAC_Codigo AS "CÓDIGO_PROGRAMACIÓN_COMERCIAL",
		PAC_Nombre AS "NOMBRE_PROGRAMACIÓN_COMERCIAL",
		SUC_CodigoSucursal AS "CÓDIGO_SEDE",
		SUC_Nombre AS "NOMBRE_SEDE",
		PAMOD_Codigo AS "CÓDIGO_MODALIDAD",
		PAMOD_Nombre AS "NOMBRE_MODALIDAD",
		PROG_Codigo AS "CÓDIGO_PROGRAMA",
		PROG_Nombre AS "NOMBRE_PROGRAMA",
		FORMAT(PACD_FechaInicio,'dd/MM/yyyy') AS "FECHA_INICIO_CURSO",
		'' AS "META"
	FROM ProgramacionAcademicaComercial
	INNER JOIN Sucursales ON SUC_Activo = 1
	INNER JOIN ProgramacionAcademicaComercialDetalles ON PACD_PAC_ProgramacionAcademicaComercialId = PAC_ProgramacionAcademicaComercialId
	INNER JOIN PAModalidades ON PAMOD_ModalidadId = PACD_PAMOD_ModalidadId
	INNER JOIN ProgramacionAcademicaComercialDetallesProgramas ON PACDP_PACD_ProgramacionAcademicaComercialDetalleId = PACD_ProgramacionAcademicaComercialDetalleId
	INNER JOIN Programas ON PROG_ProgramaId = PACD_PROG_ProgramaId

GO

/*************************/
/***** MenuPrincipal *****/
/*************************/

INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, GETDATE(), N'add_shopping_cart', (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Programación Académica'), 4, 1000021, N'Metas', N'Goals', N'item', N'/app/programacion-academica/metas')
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Metas' and MP_Icono = 'add_shopping_cart' and MP_Orden = 4)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO