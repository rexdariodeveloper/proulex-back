DROP TABLE IF EXISTS EntidadesPermisos
GO

DELETE FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ENT_TipoPermiso'
GO

INSERT INTO EmpresasPermisosComponentes
(
    --EMPC_EmpresaPermisoComponenteId - this column value is auto-generated
    EMPC_EM_EmpresaId,
    EMPC_MP_NodoId,
    EMPC_CMM_TipoPermisoId,
    EMPC_Componente
)
VALUES
(
    -- EMPC_EmpresaPermisoComponenteId - int
    ( SELECT EM_EmpresaId FROM Empresas WHERE EM_Codigo = 'PIXVS' ), -- EMPC_EM_EmpresaId - int
    ( SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/catalogos/empleados' ), -- EMPC_MP_NodoId - int
    1000030, -- Ocultar -- EMPC_CMM_TipoPermisoId - int
    'TAB Contratos' -- EMPC_Componente - varchar
)
GO