SET IDENTITY_INSERT BancosCuentas ON
GO
INSERT INTO [dbo].[BancosCuentas]
           ([BAC_CuentaId]
		   ,[BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           (1
		   ,'00-000000000'
           ,'CUENTA DEFAULT'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO
SET IDENTITY_INSERT BancosCuentas OFF
GO



INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-500750863'
           ,'SISTEMA CORPORATIVO'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-500750877'
           ,'NOMINA'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-500750880'
           ,'PROULEX - ACADEMY'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-500779223'
           ,'PROULEX LA PAZ UDEG'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-500779206'
           ,'PROULEX SOCIALES'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-500779194'
           ,'PROULEX TECNOLOGICO'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-500753552'
           ,'PROULEX C.U.C.E.A.'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-500758211'
           ,'PROULEX UNIVERSIDAD'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-500779990'
           ,'PROULEX TEPEYAC'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-500821026'
           ,'PROULEX ZAPOTLAN'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-500816425'
           ,'PROULEX PTO. VALLARTA'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-500808021'
           ,'PROULEX TEPATITLAN'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-500837147'
           ,'PROULEX OCOTLAN'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-502203533'
           ,'PROULEX LAGOS'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-503006641'
           ,'PROULEX FIID'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-503285899'
           ,'PROULEX SAN ISIDRO'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-503285959'
           ,'PROULEX C E I'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-503286084'
           ,'PROULEX C I P'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-503286130'
           ,'PROULEX PROULEX SUR'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-504401729'
           ,'PROULEX UTEG'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-504894340'
           ,'PROULEX-JOBS'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-505565846'
           ,'PROULEX F2F'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO


INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-506758008'
           ,'PROULEX ALCALDE'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-507144373'
           ,'PROULEX STA.ANITA'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-500751292'
           ,'PROULEX INTERACT'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('65-507497723'
           ,'PROULEX ACCESS'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('82-500166271'
           ,'PROULEX  DOLARES'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'USD')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'SANTANDER')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('04021735311'
           ,'PROULEX AUTLAN'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'HSBC')
           ,1
           ,1
           ,GETDATE()
           )
GO

INSERT INTO [dbo].[BancosCuentas]
           ([BAC_Codigo]
           ,[BAC_Descripcion]
           ,[BAC_MON_MonedaId]
           ,[BAC_BAN_BancoId]
           ,[BAC_Activo]
           ,[BAC_USU_CreadoPorId]
           ,[BAC_FechaCreacion]
           )
     VALUES
           ('0191578189'
           ,'PROULEX COLOTLAN'
           ,(select MON_MonedaId from Monedas where MON_Codigo = 'MXN')
           ,(select BAN_BancoId from bancos where BAN_Nombre = 'BBVA/BANCOMER')
           ,1
           ,1
           ,GETDATE()
           )
GO