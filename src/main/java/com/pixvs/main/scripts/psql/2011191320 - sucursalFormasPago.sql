CREATE SEQUENCE seq_sucursalformaspago INCREMENT 1 START 1;

CREATE TABLE SucursalFormasPago(
    SFP_SucursalFormaPagoId INTEGER NOT NULL DEFAULT nextval('seq_sucursalformaspago'),
    SFP_SucursalId INT NOT NULL,
    SFP_FPPV_FormaPagoId INTEGER NOT NULL,
    SFP_UsarEnPV BOOLEAN DEFAULT TRUE,
    SFP_Activo BOOLEAN DEFAULT TRUE,
    SFP_FechaCreacion TIMESTAMP NOT NULL DEFAULT NOW()::TIMESTAMP WITHOUT TIME ZONE,
    SFP_USU_CreadoPorId INT NULL,
    SFP_FechaModificacion TIMESTAMP NOT NULL DEFAULT NOW()::TIMESTAMP WITHOUT TIME ZONE,
    SFP_USU_ModificadoPorId INT NULL,
    CONSTRAINT SucursalFormasPago_pkey PRIMARY KEY (SFP_SucursalFormaPagoId),
    CONSTRAINT SFP_SucursalId_fk FOREIGN KEY (SFP_SucursalId)
        REFERENCES public.Sucursales(SUC_SucursalId) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT SFP_FPPV_FormaPagoId_fk FOREIGN KEY (SFP_FPPV_FormaPagoId)
        REFERENCES public.FormasPagoPV(FPPV_FormaPagoId) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT SFP_USU_CreadoPorId_fk FOREIGN KEY (SFP_USU_CreadoPorId)
        REFERENCES public.Usuarios(USU_UsuarioId) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT SFP_USU_ModificadoPorId_fk FOREIGN KEY (SFP_USU_ModificadoPorId)
        REFERENCES public.Usuarios(USU_UsuarioId) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)