/* ****************************************************************
 * Descripción: Insertamos los menus del Ingreso y Promoción...
 * Autor: Rene Carrillo
 * Fecha: 25.04.2022
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
           ,[MP_CMM_SistemaAccesoId]
           ,[MP_FechaCreacion]
           ,[MP_Repetible]
           ,[MP_Personalizado])
     VALUES
           (1
           ,'Ingreso y Promoción'
           ,'Income and Promotion'
           ,1
           ,'assignment'
           ,13
           ,'collapsable'
           ,1000021
           ,GETDATE()
           ,0
           ,0)
GO

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
           ((SELECT mp.MP_NodoId FROM MenuPrincipal mp WHERE mp.MP_TituloEN = 'Income and Promotion')
           ,'Nueva Contratación'
           ,'New Hire'
           ,1
           ,'person'
           ,1
           ,'item'
           ,'/app/ingreso-promocion/nuevas-contrataciones'
           ,1000021
           ,GETDATE()
           ,0
           ,0),
		    ((SELECT mp.MP_NodoId FROM MenuPrincipal mp WHERE mp.MP_TituloEN = 'Income and Promotion')
           ,'Renovación'
           ,'Renovation'
           ,1
           ,'school'
           ,2
           ,'item'
           ,'/app/ingreso-promocion/renovaciones'
           ,1000021
           ,GETDATE()
           ,0
           ,0),
		    ((SELECT mp.MP_NodoId FROM MenuPrincipal mp WHERE mp.MP_TituloEN = 'Income and Promotion')
           ,'Baja'
           ,'Low'
           ,1
           ,'assignment_turned_in'
           ,3
           ,'item'
           ,'/app/ingreso-promocion/bajas'
           ,1000021
           ,GETDATE()
           ,0
           ,0),
		    ((SELECT mp.MP_NodoId FROM MenuPrincipal mp WHERE mp.MP_TituloEN = 'Income and Promotion')
           ,'Cambio'
           ,'Change'
           ,1
           ,'attach_money'
           ,4
           ,'item'
           ,'/app/ingreso-promocion/cambios'
           ,1000021
           ,GETDATE()
           ,0
           ,0),
		    ((SELECT mp.MP_NodoId FROM MenuPrincipal mp WHERE mp.MP_TituloEN = 'Income and Promotion')
           ,'Reportes'
           ,'Reports'
           ,1
           ,'folder_open'
           ,5
           ,'collapsable'
           ,null
           ,1000021
           ,GETDATE()
           ,0
           ,0)
GO