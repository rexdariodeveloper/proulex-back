ALTER TABLE Articulos ADD ART_CMM_ObjetoImpuestoId INT NULL
GO

ALTER TABLE Articulos WITH CHECK ADD  CONSTRAINT FK_Articulos_ObjetosImpuestoSAT FOREIGN KEY(ART_CMM_ObjetoImpuestoId)
REFERENCES ControlesMaestrosMultiples (CMM_ControlId)
GO

ALTER TABLE Articulos CHECK CONSTRAINT FK_Articulos_ObjetosImpuestoSAT
GO