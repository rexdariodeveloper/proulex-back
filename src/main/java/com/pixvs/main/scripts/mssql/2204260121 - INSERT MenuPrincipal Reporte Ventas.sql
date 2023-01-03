/* ****************************************************************
 * Descripción: Insertamos reportes Ventas Globales
 * Autor: Rene Carrillo
 * Fecha: 20.04.2022
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
           (1089
           ,'Reporte Ventas'
           ,'Sells Reports'
           ,1
           ,'person'
           ,2
           ,'item'
           ,'/app/ventas/reportes/ventas'
           ,1000021
           ,GETDATE()
           ,0
           ,0)
GO