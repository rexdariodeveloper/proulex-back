/*
    Indice para SCC_SUC_SucursalId, SCC_USU_UsuarioAbreId, siempre y cuando el la fecha fin este como null
*/
CREATE UNIQUE NONCLUSTERED INDEX [IX_SCC_SUC_SucursalId_SCC_USU_UsuarioAbreId] ON [dbo].[SucursalesCortesCajas]
(
	[SCC_SUC_SucursalId] ASC,
	[SCC_USU_UsuarioAbreId] ASC
)
WHERE ([SCC_FechaFin] IS NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO




ALTER TABLE [dbo].[SucursalesCortesCajas] WITH CHECK ADD CONSTRAINT [UNQ_SCC_Codigo] UNIQUE ([SCC_Codigo])
GO