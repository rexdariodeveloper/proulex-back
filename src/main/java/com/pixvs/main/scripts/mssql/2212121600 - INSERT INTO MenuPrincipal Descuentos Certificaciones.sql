/* ****************************************************************
 * Descripción: Insertamos los menus del Descuuentos Certificaciones...
 * Autor: Rene Carrillo
 * Fecha: 25.11.2022
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
           (3
           ,'Descuentos Certificaciones'
           ,'Certifying Discounts'
           ,1
           ,'receipt'
           ,15
           ,'item'
           ,'/app/catalogos/descuentos-certificaciones'
           ,1000021
           ,GETDATE()
           ,0
           ,0)
GO