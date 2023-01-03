INSERT ControlesMaestrosMultiples (
	CMM_ControlId,
	CMM_Activo,
	CMM_Control,
	CMM_USU_CreadoPorId,
	CMM_FechaCreacion,
	CMM_FechaModificacion,
	CMM_USU_ModificadoPorId,
	CMM_Referencia,
	CMM_Sistema,
	CMM_Valor
) VALUES (
	/* [CMM_ControlId] */ 2000231,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ 'CMM_IMP_TipoImpresion',
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
        /* [CMM_Control] */ 'CMM_IMP_TipoImpresion',
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
	/* [CMM_Control] */ 'CMM_IMP_TipoImpresion',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'IP'
);

CREATE SEQUENCE seq_sucursalimpresorafamilia INCREMENT 1 START 1;
CREATE TABLE sucursalimpresorafamilia(
    SIMF_SucursalImpresoraFamiliaId     INTEGER NOT NULL DEFAULT nextval('seq_sucursalimpresorafamilia'),
    SIMF_SucursalId             		INTEGER NOT NULL,
    SIMF_FamiliaId     					INTEGER NOT NULL,
	SIMF_CMM_TipoImpresora				INTEGER NOT NULL,
	SIMF_IP 							VARCHAR(15) DEFAULT NULL,
	SIMF_Activo							BOOLEAN DEFAULT TRUE,
    SIMF_FechaCreacion					TIMESTAMP NOT NULL DEFAULT NOW()::TIMESTAMP WITHOUT TIME ZONE,
	SIMF_USU_CreadoPorId 				INT NOT NULL,
	SIMF_FechaModificacion 				TIMESTAMP DEFAULT NOW()::TIMESTAMP WITHOUT TIME ZONE,,
    SIMF_USU_ModificadoPorId 			INT NULL,
    CONSTRAINT sucursalimpresorafamilia_pkey PRIMARY KEY (SIMF_SucursalImpresoraFamiliaId),
    CONSTRAINT SIMF_SucursalId_fk FOREIGN KEY (SIMF_SucursalId)
        REFERENCES public.Sucursales(SUC_SucursalId) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT SIMF_FamiliaId_fk FOREIGN KEY (SIMF_FamiliaId)
        REFERENCES public.ArticulosFamilias(AFAM_FamiliaId) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT SIMF_CMM_TipoImpresora_fk FOREIGN KEY (SIMF_CMM_TipoImpresora)
        REFERENCES public.ControlesMaestrosMultiples(CMM_ControlId) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
