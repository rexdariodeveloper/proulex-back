/* ****************************************************************
 * Descripción: Insertamos Reporte de Calificaciones
 * Autor: Rene Carrillo
 * Fecha: 28.06.2022
 *****************************************************************
*/

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
           ,[MP_FechaCreacion]
           ,[MP_Repetible]
           ,[MP_Personalizado])
     VALUES
           (1084
           ,'Reporte de Calificaciones'
           ,'Qualifications Report'
           ,1
           ,'view_list'
           ,5
           ,'item'
           ,'/app/control-escolar/reportes/calificaciones'
           ,1000021
           ,GETDATE()
           ,0
           ,0)
GO

UPDATE MenuPrincipal SET MP_Orden = 6 WHERE MP_NodoId = 1108 /* Reprotes de becas */
GO

UPDATE MenuPrincipal SET MP_Orden = 7 WHERE MP_NodoId = 1085 /* Reprote de grupos */
GO

UPDATE MenuPrincipal SET MP_Orden = 8 WHERE MP_NodoId = 1112 /* Reprotes PCP */
GO