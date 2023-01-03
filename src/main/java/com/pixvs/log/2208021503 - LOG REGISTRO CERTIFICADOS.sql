SET IDENTITY_INSERT LogsProcesos ON
GO
INSERT INTO [dbo].[LogsProcesos]
           ([LOGP_LogProcesoId]
		   ,[LOGP_Nombre]
           ,[LOGP_Icono])
     VALUES
           (12
		   ,'Registro Certificados'
           ,'school')
GO
SET IDENTITY_INSERT LogsProcesos OFF
GO
