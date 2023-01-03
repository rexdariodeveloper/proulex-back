/* ****************************************************************
 * Descripción: Insertamos Validacion de boletas
 * Autor: Rene Carrillo
 * Fecha: 11.05.2022
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
           (1068
           ,'Validación de Boletas'
           ,'Ticket Validation'
           ,1
           ,'person'
           ,8
           ,'item'
           ,'/app/control-escolar/validacion-boletas'
           ,1000021
           ,GETDATE()
           ,0
           ,0)
GO