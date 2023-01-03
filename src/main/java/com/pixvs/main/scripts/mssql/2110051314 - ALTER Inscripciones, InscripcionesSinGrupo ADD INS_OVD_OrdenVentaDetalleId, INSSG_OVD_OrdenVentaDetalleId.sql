/**
* Created by Angel Daniel Hernández Silva on 05/10/2021.
* Object: ALTER Inscripciones, InscripcionesSinGrupo ADD INS_OVD_OrdenVentaDetalleId, INSSG_OVD_OrdenVentaDetalleId
*/

/*******************************************************/
/***** Inscripciones - INS_OVD_OrdenVentaDetalleId *****/
/*******************************************************/

ALTER TABLE [dbo].[Inscripciones] ADD [INS_OVD_OrdenVentaDetalleId] [int] NULL
GO

ALTER TABLE [dbo].[Inscripciones]  WITH CHECK ADD  CONSTRAINT [FK_INS_OVD_OrdenVentaDetalleId] FOREIGN KEY([INS_OVD_OrdenVentaDetalleId])
REFERENCES [dbo].[OrdenesVentaDetalles] ([OVD_OrdenVentaDetalleId])
GO

ALTER TABLE [dbo].[Inscripciones] CHECK CONSTRAINT [FK_INS_OVD_OrdenVentaDetalleId]
GO

UPDATE Inscripciones SET INS_OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaId
GO

ALTER TABLE [dbo].[Inscripciones] ALTER COLUMN [INS_OVD_OrdenVentaDetalleId] [int] NOT NULL
GO

/*****************************************************************/
/***** InscripcionesSinGrupo - INSSG_OVD_OrdenVentaDetalleId *****/
/*****************************************************************/

ALTER TABLE [dbo].[InscripcionesSinGrupo] ADD [INSSG_OVD_OrdenVentaDetalleId] [int] NULL
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo]  WITH CHECK ADD  CONSTRAINT [FK_INSSG_OVD_OrdenVentaDetalleId] FOREIGN KEY([INSSG_OVD_OrdenVentaDetalleId])
REFERENCES [dbo].[OrdenesVentaDetalles] ([OVD_OrdenVentaDetalleId])
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo] CHECK CONSTRAINT [FK_INSSG_OVD_OrdenVentaDetalleId]
GO

UPDATE InscripcionesSinGrupo SET INSSG_OVD_OrdenVentaDetalleId = INSSG_OVD_OrdenVentaId
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo] ALTER COLUMN [INSSG_OVD_OrdenVentaDetalleId] [int] NOT NULL
GO