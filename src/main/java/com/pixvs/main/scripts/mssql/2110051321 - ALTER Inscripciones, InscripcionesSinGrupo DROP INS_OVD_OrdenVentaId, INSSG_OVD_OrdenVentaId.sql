/**
* Created by Angel Daniel Hernández Silva on 05/10/2021.
* Object: ALTER Inscripciones, InscripcionesSinGrupo DROP INS_OVD_OrdenVentaId, INSSG_OVD_OrdenVentaId
*/

/*******************************************************/
/***** Inscripciones - INS_OVD_OrdenVentaDetalleId *****/
/*******************************************************/

ALTER TABLE [dbo].[Inscripciones] DROP CONSTRAINT [FK_INS_OVD_OrdenVentaId]
GO

ALTER TABLE [dbo].[Inscripciones] DROP COLUMN [INS_OVD_OrdenVentaId]
GO

/*****************************************************************/
/***** InscripcionesSinGrupo - INSSG_OVD_OrdenVentaDetalleId *****/
/*****************************************************************/

ALTER TABLE [dbo].[InscripcionesSinGrupo] DROP CONSTRAINT [FK_INSSG_OVD_OrdenVentaId]
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo] DROP COLUMN [INSSG_OVD_OrdenVentaId]
GO