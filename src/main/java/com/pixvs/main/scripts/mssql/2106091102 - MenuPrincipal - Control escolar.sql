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
    N'assignment', -- [MP_Icono]
    (select MP_NodoId from MenuPrincipal where MP_Titulo = N'MÓDULOS'), -- [MP_NodoPadreId]
    10, -- [MP_Orden]
    1000021, -- [MP_CMM_SistemaAccesoId]
    N'Control escolar', -- [MP_Titulo]
    N'School control', -- [MP_TituloEN]
    N'collapsable', -- [MP_Tipo]
    null -- [MP_URL]
)
GO