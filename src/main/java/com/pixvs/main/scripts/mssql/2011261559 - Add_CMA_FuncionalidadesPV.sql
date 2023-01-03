/**
 * Created by Benjamin Osorio on 2020/11/26.
 */
INSERT [dbo].[ControlesMaestros] (
	[CMA_Nombre],
	[CMA_Valor],
	[CMA_Sistema],
	[CMA_FechaModificacion]
) VALUES (
	/* [CMA_Nombre] */ 'CMA_FuncionalidadesPV',
	/* [CMA_Valor] */ '0',
	/* [CMA_Sistema] */ 1,
	/* [CMA_FechaModificacion] */ GETDATE()
)
GO