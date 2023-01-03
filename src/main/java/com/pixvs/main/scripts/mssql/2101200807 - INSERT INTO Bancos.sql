INSERT INTO [dbo].[Bancos]
           ([BAN_Nombre]
           ,[BAN_Activo]
           ,[BAN_USU_CreadoPorId]
           ,[BAN_USU_ModificadoPorId]
           ,[BAN_FechaCreacion]
           ,[BAN_FechaModificacion])
     VALUES
           (N'SANTANDER',1,1,NULL,GETDATE(),NULL),
		   (N'HSBC',1,1,NULL,GETDATE(),NULL),
		   (N'BBVA/BANCOMER',1,1,NULL,GETDATE(),NULL)
GO