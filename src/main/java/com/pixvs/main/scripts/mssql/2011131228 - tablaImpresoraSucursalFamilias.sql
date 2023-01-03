/**
    Tabla de configuracion de impresoras por famiilia
*/
SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON
GO

INSERT [dbo].[ControlesMaestrosMultiples] (
	[CMM_ControlId],
	[CMM_Activo],
	[CMM_Control],
	[CMM_USU_CreadoPorId],
	[CMM_FechaCreacion],
	[CMM_FechaModificacion],
	[CMM_USU_ModificadoPorId],
	[CMM_Referencia],
	[CMM_Sistema],
	[CMM_Valor]
) VALUES (
	/* [CMM_ControlId] */ 2000231,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_IMP_TipoImpresion',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Local'
), (
	/* [CMM_ControlId] */ 2000232,
	/* [CMM_Activo] */ 1,
        /* [CMM_Control] */ N'CMM_IMP_TipoImpresion',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Compartida'
), (
	/* [CMM_ControlId] */ 2000233,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_IMP_TipoImpresion',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'IP'
);

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

    CREATE TABLE SucursalImpresoraFamilia(
    SIMF_SucursalImpresoraFamiliaId INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    SIMF_SucursalId INT NOT NULL,
    SIMF_FamiliaId SMALLINT NOT NULL,
    SIMF_CMM_TipoImpresora INT NOT NULL,
    SIMF_IP VARCHAR(15) DEFAULT NULL,
    SIMF_Activo BIT DEFAULT 1,
    SIMF_FechaCreacion DATETIME NOT NULL,
    SIMF_USU_CreadoPorId INT NULL,
    SIMF_FechaModificacion DATETIME NULL,
    SIMF_USU_ModificadoPorId INT NULL
);

ALTER TABLE SucursalImpresoraFamilia ADD FOREIGN KEY (SIMF_SucursalId) REFERENCES Sucursales(SUC_SucursalId);
ALTER TABLE SucursalImpresoraFamilia ADD FOREIGN KEY (SIMF_CMM_TipoImpresora) REFERENCES ControlesMaestrosMultiples(CMM_ControlId);
ALTER TABLE SucursalImpresoraFamilia ADD FOREIGN KEY (SIMF_FamiliaId) REFERENCES ArticulosFamilias(AFAM_FamiliaId);