INSERT INTO [dbo].[PAModalidades]
           ([PAMOD_Codigo]
           ,[PAMOD_Nombre]
           ,[PAMOD_HorasPorDia]
           ,[PAMOD_DiasPorSemana]
           ,[PAMOD_Lunes]
           ,[PAMOD_Martes]
           ,[PAMOD_Miercoles]
           ,[PAMOD_Jueves]
           ,[PAMOD_Viernes]
           ,[PAMOD_Sabado]
           ,[PAMOD_Domingo]
           ,[PAMOD_Activo]
           ,[PAMOD_FechaCreacion]
           ,[PAMOD_USU_CreadoPorId])
     VALUES
           ('PER','Personalizada',0,0,0,0,0,0,0,0,0,0,GETDATE(),1)
GO