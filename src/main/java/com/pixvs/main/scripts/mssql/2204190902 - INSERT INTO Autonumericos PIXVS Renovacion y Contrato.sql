UPDATE [dbo].[Autonumericos] SET AUT_Nombre = 'PIXVS Nueva Contratacion' WHERE AUT_Nombre = 'PIXVS'
GO

INSERT INTO [dbo].[Autonumericos]
           ([AUT_Nombre]
           ,[AUT_Prefijo]
           ,[AUT_Siguiente]
           ,[AUT_Digitos]
           ,[AUT_Activo])
     VALUES
	 ('PIXVS Contrato'
    ,'PIXVS'
    ,0
    ,6
    ,1),

    ('PIXVS Renovacion'
    ,'RPIX'
    ,0
    ,6
    ,1)
GO