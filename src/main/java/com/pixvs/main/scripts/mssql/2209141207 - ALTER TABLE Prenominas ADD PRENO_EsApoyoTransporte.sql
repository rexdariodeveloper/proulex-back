/**
 * Created by Angel Daniel Hernández Silva on 30/08/2022.
 */

ALTER TABLE [dbo].[Prenominas] ADD [PRENO_EsApoyoTransporte] bit NULL
GO

UPDATE Prenominas SET PRENO_EsApoyoTransporte = 0
GO

ALTER TABLE [dbo].[Prenominas] ALTER COLUMN [PRENO_EsApoyoTransporte] bit NOT NULL
GO