/**
 * Created by Angel Daniel Hernández Silva on 09/12/2020.
 */


INSERT [dbo].[ControlesMaestros] (
	[CMA_Nombre],
	[CMA_Valor],
	[CMA_Sistema],
	[CMA_FechaModificacion]
) VALUES (
	/* [CMA_Nombre] */ 'CM_Permitir_Inventario_Negativo',
	/* [CMA_Valor] */ '0',
	/* [CMA_Sistema] */ 1,
	/* [CMA_FechaModificacion] */ GETDATE()
)
GO