

UPDATE ControlesMaestrosMultiples SET CMM_Valor = 'Transferencia electrónica de fondos' WHERE CMM_ControlId = 2000181
GO

UPDATE ControlesMaestrosMultiples SET CMM_Valor = 'Tarjeta de crédito' WHERE CMM_ControlId = 2000183
GO

UPDATE ControlesMaestrosMultiples SET CMM_Valor = 'Tarjeta de débito' WHERE CMM_ControlId = 2000184
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON 
GO

INSERT [dbo].[ControlesMaestrosMultiples] (
	[CMM_ControlId],
	[CMM_Activo],
	[CMM_Control],
	[CMM_FechaCreacion],
	[CMM_Referencia],
	[CMM_Sistema],
	[CMM_Valor]
) VALUES (
	/* [CMM_ControlId] */ 2000185,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPP_FormaPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Por definir'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

UPDATE ControlesMaestrosMultiples SET CMM_Activo = 1 WHERE CMM_Control = 'CMM_CXPP_FormaPago'
GO