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
    N'attach_money', -- [MP_Icono]
    (select MP_NodoId from MenuPrincipal where MP_Titulo = N'MÓDULOS'), -- [MP_NodoPadreId]
    11, -- [MP_Orden]
    1000021, -- [MP_CMM_SistemaAccesoId]
    N'Ventas', -- [MP_Titulo]
    N'Sells', -- [MP_TituloEN]
    N'collapsable', -- [MP_Tipo]
    null -- [MP_URL]
)
GO