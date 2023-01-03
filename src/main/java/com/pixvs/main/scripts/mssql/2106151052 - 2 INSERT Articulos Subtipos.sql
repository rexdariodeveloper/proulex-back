INSERT INTO [dbo].[ArticulosSubtipos]
           ([ARTST_ARTT_ArticuloTipoId]
           ,[ARTST_Descripcion]
           ,[ARTST_Activo])
     VALUES
           ((Select ARTT_ArticuloTipoId from ArticulosTipos where ARTT_Descripcion='Misceláneo')
           ,'General'
           ,1),
		   ((Select ARTT_ArticuloTipoId from ArticulosTipos where ARTT_Descripcion='Misceláneo')
           ,'Certificación'
           ,1),
		   ((Select ARTT_ArticuloTipoId from ArticulosTipos where ARTT_Descripcion='Misceláneo')
           ,'Examen'
           ,1)
GO


