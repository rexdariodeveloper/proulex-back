ALTER TABLE ArticulosFamilias
DROP CONSTRAINT [UNQ_AFAM_Prefijo]
GO

CREATE UNIQUE INDEX [UNQ_AFAM_Prefijo]
ON [dbo].[ArticulosFamilias](AFAM_Prefijo)
WHERE AFAM_Activo = 1
GO