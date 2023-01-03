INSERT INTO [dbo].[MenuPrincipal]
           ([MP_NodoPadreId]
           ,[MP_Titulo]
           ,[MP_TituloEN]
           ,[MP_Activo]
           ,[MP_Icono]
           ,[MP_Orden]
           ,[MP_Tipo]
           ,[MP_URL]
           ,[MP_CMM_SistemaAccesoId]
           ,[MP_FechaCreacion])
     VALUES
           ((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = N'Reportes' AND MP_NodoPadreId=(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = N'Control escolar'))
           ,N'Reporte de inasistencias'
           ,N'Absence report'
           ,1
           ,N'assignment_turned_in'
           ,3
           ,N'item'
           ,'/app/control-escolar/reportes/inasistencias'
           ,1000021
           ,GETDATE())
GO

INSERT INTO [dbo].[MenuPrincipalPermisos] (
    [MPP_MP_NodoId],
    [MPP_Nombre],
    [MPP_Activo]
) VALUES(
    (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/control-escolar/reportes/inasistencias'),
    'Exportar a excel',
    1
)
GO