SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Description:	Devuelve verdado o falso si los movimientos cumplen con los requisitos para disparar la alerta
-- =============================================
CREATE FUNCTION verificarCriteriosEconomicosAlertas
(
	-- Add the parameters for the function here
	@tipoMonto int,
	@idConfigAlerta int,
	@montoMaximo money,
	@montoMinimo money,
	@idMovimiento int

)
RETURNS bit
AS
BEGIN
	-- Declare the return variable here
	DECLARE @resultado bit,
	        @monto money,
			@tablaReferencia varchar(100),
			@columnaEstadoMovimiento varchar(100),
			@existeTabla smallint

	/*1.- Obtener id de la configuración de la alerta */
	Select @tablaReferencia = ALC_TablaReferencia,
		    @columnaEstadoMovimiento = ALC_CampoController
	from AlertasConfig
	where ALC_AlertaCId = @idConfigAlerta;

	/*2.- Verificar si la tabla existe */
	select  @existeTabla = count(*)
	FROM INFORMATION_SCHEMA.TABLES 
	where TABLE_TYPE = 'BASE TABLE'
	and TABLE_NAME = @tablaReferencia;

	if @existeTabla > 0/* Si la tabla existe entonces se  procede a revisar los criterios económicos*/
	begin
		if @tipoMonto=1000131 /* Diario*/
		begin
			if @tablaReferencia = 'OrdenesCompra'
			 BEGIN
					select  @monto =
							SUM(monto_partida) 
						from
						(
							select 
								(select Total from fn_getImpuestosArticulo(OCD_Cantidad,OCD_Precio,OCD_Descuento,OCD_IVA,OCD_IEPS,OCD_IEPSCuotaFija)) monto_partida,
								cast(OC_FechaOC as date) fecha
							from 
								OrdenesCompra 
								inner join OrdenesCompraDetalles on OC_OrdenCompraId = OCD_OC_OrdenCompraId
							where
								cast(OC_FechaOC as date) = GETDATE()
							and OC_CMM_EstatusId NOT IN (select CMM_ControlId
												 		 from ControlesMaestrosMultiples
														 where CMM_Valor in ('Borrado','Borrada','Guardado','Cancelada','Cancelado','Rechazada','Rechazado')
														 and CMM_Control = @columnaEstadoMovimiento)-- No toma encuenta las OC que esten estatus de Cancelada o borrada
							) t
							group by t.fecha;
			 END
			 if @tablaReferencia = 'Requisiciones'
			 BEGIN
				select  @monto =
							SUM(monto_partida) 
					from
						(
							select 
								(select Total from fn_getImpuestosArticulo(REQP_CantidadRequerida,ART_CostoPromedio,0,ART_IVA,ART_IEPS,ART_IEPSCuotaFija)) monto_partida,
								cast(REQ_Fecha as date) fecha
							from 
								Requisiciones 
								inner join RequisicionesPartidas on REQP_REQ_RequisicionId = REQ_RequisicionId
								inner join Articulos on ART_ArticuloId = REQP_ART_ArticuloId
							where
								cast(REQ_Fecha as date) = GETDATE()
							and REQ_CMM_EstadoRequisicionId NOT IN (select CMM_ControlId
												 		 from ControlesMaestrosMultiples
														 where CMM_Valor in ('Borrado','Borrada','Guardado','Cancelada','Cancelado','Rechazada','Rechazado')
														 and CMM_Control = @columnaEstadoMovimiento)-- No toma encuenta las OC que esten estatus de Cancelada o borrada
							) t
							group by t.fecha;	
			 END
			 if @tablaReferencia = 'CXPSolicitudesPagos'
			 BEGIN
				
                select  @monto =
							SUM(monto_partida) 
					from
						(
							select 
								CXPF_MontoRegistro monto_partida,
								cast(CXPS_FechaCreacion as date) fecha
							from 
								CXPSolicitudesPagos 
								inner join CXPSolicitudesPagosDetalles on CXPSD_CXPS_CXPSolicitudPagoId = CXPS_CXPSolicitudPagoId
								inner join CXPFacturas on CXPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
							where
								cast(CXPS_FechaCreacion as date) = GETDATE()
							and CXPS_CMM_EstatusId NOT IN (select CMM_ControlId
												 		 from ControlesMaestrosMultiples
														 where CMM_Valor in ('Borrado','Borrada','Guardado','Cancelada','Cancelado','Rechazada','Rechazado')
														 and CMM_Control = @columnaEstadoMovimiento)-- No toma encuenta las OC que esten estatus de Cancelada o borrada
							) t
							group by t.fecha;		
			 END
			 if @tablaReferencia = 'Pedidos'
			 BEGIN
			 select  @monto =
							SUM(monto_partida) 
					from (
			    SELECT 
					CAST(PED_Fecha AS DATE) as fecha,
					CAST(PEDD_CantidadPedida * ART_CostoPromedio AS DECIMAL(10,2)) as monto_partida
				FROM 
					Pedidos 
					INNER JOIN PedidosDetalles ON PED_PedidoId = PEDD_PED_PedidoId
					INNER JOIN Articulos ON PEDD_ART_ArticuloId = ART_ArticuloId
				WHERE
					CAST(PED_Fecha AS DATE) = GETDATE()
				and	PED_CMM_EstatusId NOT IN (select CMM_ControlId
												 		 from ControlesMaestrosMultiples
														 where CMM_Valor in ('Borrado','Borrada','Guardado','Cancelada','Cancelado','Rechazada','Rechazado')
														 and CMM_Control = @columnaEstadoMovimiento)-- No toma encuenta las OC que esten estatus de Cancelada o borrada;
			 ) t
							group by t.fecha;
			 END
			 if @tablaReferencia = 'CXPSolicitudesPagosServicios'
			 BEGIN
			  select  @monto =
							SUM(monto_partida)
					from
						(
							select 
								CXPF_MontoRegistro monto_partida,
								cast(CXPSPS_FechaCreacion as date) fecha
							from 
								CXPSolicitudesPagosServicios 
								inner join CXPSolicitudesPagosServiciosDetalles on CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId = CXPSPS_CXPSolicitudPagoServicioId
								inner join CXPFacturas on CXPSPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
							where
								cast(CXPSPS_FechaCreacion as date) = GETDATE()
							and CXPSPS_CMM_EstatusId NOT IN (select CMM_ControlId
												 		 from ControlesMaestrosMultiples
														 where CMM_Valor in ('Borrado','Borrada','Guardado','Cancelada','Cancelado','Rechazada','Rechazado')
														 and CMM_Control = @columnaEstadoMovimiento)-- No toma encuenta las OC que esten estatus de Cancelada o borrada
							) t
							group by t.fecha;
			 END
		end
		if @tipoMonto = 1000132/*Mensual*/
		begin
		   if @tablaReferencia = 'OrdenesCompra'
			 BEGIN
					select  @monto =
							SUM(monto_partida) 
					from
						(
							select 
								(select Total from fn_getImpuestosArticulo(OCD_Cantidad,OCD_Precio,OCD_Descuento,OCD_IVA,OCD_IEPS,OCD_IEPSCuotaFija)) monto_partida,
								MONTH(cast(OC_FechaOC as date)) fecha
							from 
								OrdenesCompra 
								inner join OrdenesCompraDetalles on OC_OrdenCompraId = OCD_OC_OrdenCompraId
							where
								MONTH(cast(OC_FechaOC as date)) = MONTH( GETDATE())
							and OC_CMM_EstatusId NOT IN (select CMM_ControlId
												 		 from ControlesMaestrosMultiples
														 where CMM_Valor in ('Borrado','Borrada','Guardado','Cancelada','Cancelado','Rechazada','Rechazado')
														 and CMM_Control = @columnaEstadoMovimiento)-- No toma encuenta las OC que esten estatus de Cancelada o borrada
							) t
							group by t.fecha;
			 END
			 if @tablaReferencia = 'Requisiciones'
			 BEGIN
				select  @monto =
							SUM(monto_partida) 
					from
						(
							select 
								(select Total from fn_getImpuestosArticulo(REQP_CantidadRequerida,ART_CostoPromedio,0,ART_IVA,ART_IEPS,ART_IEPSCuotaFija)) monto_partida,
								MONTH(cast(REQ_Fecha as date)) fecha
							from 
								Requisiciones 
								inner join RequisicionesPartidas on REQP_REQ_RequisicionId = REQ_RequisicionId
								inner join Articulos on ART_ArticuloId = REQP_ART_ArticuloId
							where
								MONTH(cast(REQ_Fecha as date)) = MONTH(GETDATE())
							and REQ_CMM_EstadoRequisicionId NOT IN (select CMM_ControlId
												 		 from ControlesMaestrosMultiples
														 where CMM_Valor in ('Borrado','Borrada','Guardado','Cancelada','Cancelado','Rechazada','Rechazado')
														 and CMM_Control = @columnaEstadoMovimiento)-- No toma encuenta las OC que esten estatus de Cancelada o borrada
							) t
							group by t.fecha;	
			 END
			 if @tablaReferencia = 'CXPSolicitudesPagos'
			 BEGIN
				
                select @monto =
							SUM(monto_partida) 
					from
						(
							select 
								CXPF_MontoRegistro monto_partida,
								MONTH(cast(CXPS_FechaCreacion as date)) fecha
							from 
								CXPSolicitudesPagos 
								inner join CXPSolicitudesPagosDetalles on CXPSD_CXPS_CXPSolicitudPagoId = CXPS_CXPSolicitudPagoId
								inner join CXPFacturas on CXPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
							where
								MONTH(cast(CXPS_FechaCreacion as date)) = MONTH(GETDATE())
							and CXPS_CMM_EstatusId NOT IN (select CMM_ControlId
												 		 from ControlesMaestrosMultiples
														 where CMM_Valor in ('Borrado','Borrada','Guardado','Cancelada','Cancelado','Rechazada','Rechazado')
														 and CMM_Control = @columnaEstadoMovimiento)-- No toma encuenta las OC que esten estatus de Cancelada o borrada
							) t
							group by t.fecha;		
			 END
			 if @tablaReferencia = 'Pedidos'
			 BEGIN
			 select  @monto =
							SUM(monto_partida) 
					from (
			    SELECT 
					MONTH(CAST(PED_Fecha AS DATE)) as fecha,
					CAST(PEDD_CantidadPedida * ART_CostoPromedio AS DECIMAL(10,2)) as monto_partida
				FROM 
					Pedidos 
					INNER JOIN PedidosDetalles ON PED_PedidoId = PEDD_PED_PedidoId
					INNER JOIN Articulos ON PEDD_ART_ArticuloId = ART_ArticuloId
				WHERE
					MONTH(CAST(PED_Fecha AS DATE)) = MONTH(GETDATE())
				and	PED_CMM_EstatusId NOT IN (select CMM_ControlId
												 		 from ControlesMaestrosMultiples
														 where CMM_Valor in ('Borrado','Borrada','Guardado','Cancelada','Cancelado','Rechazada','Rechazado')
														 and CMM_Control = @columnaEstadoMovimiento)-- No toma encuenta las OC que esten estatus de Cancelada o borrada;
			 ) t
							group by t.fecha;
			 END
			 if @tablaReferencia = 'CXPSolicitudesPagosServicios'
			 BEGIN
			  select  @monto =
							SUM(monto_partida)
					from
						(
							select 
								CXPF_MontoRegistro monto_partida,
								MONTH(cast(CXPSPS_FechaCreacion as date)) fecha
							from 
								CXPSolicitudesPagosServicios 
								inner join CXPSolicitudesPagosServiciosDetalles on CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId = CXPSPS_CXPSolicitudPagoServicioId
								inner join CXPFacturas on CXPSPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
							where
								MONTH(cast(CXPSPS_FechaCreacion as date)) = MONTH(GETDATE())
							and CXPSPS_CMM_EstatusId NOT IN (select CMM_ControlId
												 		 from ControlesMaestrosMultiples
														 where CMM_Valor in ('Borrado','Borrada','Guardado','Cancelada','Cancelado','Rechazada','Rechazado')
														 and CMM_Control = @columnaEstadoMovimiento)-- No toma encuenta las OC que esten estatus de Cancelada o borrada
							) t
							group by t.fecha;
			 END		   
		end
		if @tipoMonto = 1000133 /* Por exhibición*/
		begin
			if @tablaReferencia = 'OrdenesCompra'
			 BEGIN
					select  @monto =
							SUM(monto_partida) 
					from
						(
							select 
								(select Total from fn_getImpuestosArticulo(OCD_Cantidad,OCD_Precio,OCD_Descuento,OCD_IVA,OCD_IEPS,OCD_IEPSCuotaFija)) monto_partida,
								cast(OC_FechaOC as date) fecha
							from 
								OrdenesCompra 
								inner join OrdenesCompraDetalles on OC_OrdenCompraId = OCD_OC_OrdenCompraId
							where
								OC_OrdenCompraId = @idMovimiento
							and OC_CMM_EstatusId NOT IN (select CMM_ControlId
												 		 from ControlesMaestrosMultiples
														 where CMM_Valor in ('Borrado','Borrada','Guardado','Cancelada','Cancelado','Rechazada','Rechazado')
														 and CMM_Control = @columnaEstadoMovimiento)-- No toma encuenta las OC que esten estatus de Cancelada o borrada
							) t
							group by t.fecha;
			 END
			 if @tablaReferencia = 'Requisiciones'
			 BEGIN
				select @monto =
							SUM(monto_partida) 
					from
						(
							select 
								(select Total from fn_getImpuestosArticulo(REQP_CantidadRequerida,ART_CostoPromedio,0,ART_IVA,ART_IEPS,ART_IEPSCuotaFija)) monto_partida,
								cast(REQ_Fecha as date) fecha
							from 
								Requisiciones 
								inner join RequisicionesPartidas on REQP_REQ_RequisicionId = REQ_RequisicionId
								inner join Articulos on ART_ArticuloId = REQP_ART_ArticuloId
							where
								REQ_RequisicionId = @idMovimiento
							and REQ_CMM_EstadoRequisicionId NOT IN (select CMM_ControlId
												 		 from ControlesMaestrosMultiples
														 where CMM_Valor in ('Borrado','Borrada','Guardado','Cancelada','Cancelado','Rechazada','Rechazado')
														 and CMM_Control = @columnaEstadoMovimiento)-- No toma encuenta las OC que esten estatus de Cancelada o borrada
							) t
							group by t.fecha;	
			 END
			 if @tablaReferencia = 'CXPSolicitudesPagos'
			 BEGIN
				
                select  @monto =
							SUM(monto_partida)
					from
						(
							select 
								CXPF_MontoRegistro monto_partida,
								cast(CXPS_FechaCreacion as date) fecha
							from 
								CXPSolicitudesPagos 
								inner join CXPSolicitudesPagosDetalles on CXPSD_CXPS_CXPSolicitudPagoId = CXPS_CXPSolicitudPagoId
								inner join CXPFacturas on CXPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
							where
								CXPS_CXPSolicitudPagoId = @idMovimiento
							and CXPS_CMM_EstatusId NOT IN (select CMM_ControlId
												 		 from ControlesMaestrosMultiples
														 where CMM_Valor in ('Borrado','Borrada','Guardado','Cancelada','Cancelado','Rechazada','Rechazado')
														 and CMM_Control = @columnaEstadoMovimiento)-- No toma encuenta las OC que esten estatus de Cancelada o borrada
							) t
							group by t.fecha;		
			 END
			 if @tablaReferencia = 'Pedidos'
			 BEGIN
			 select  @monto =
							SUM(monto_partida) 
					from (
			    SELECT 
					CAST(PED_Fecha AS DATE) as fecha,
					CAST(PEDD_CantidadPedida * ART_CostoPromedio AS DECIMAL(10,2)) as monto_partida
				FROM 
					Pedidos 
					INNER JOIN PedidosDetalles ON PED_PedidoId = PEDD_PED_PedidoId
					INNER JOIN Articulos ON PEDD_ART_ArticuloId = ART_ArticuloId
				WHERE
					PED_PedidoId = @idMovimiento
				and	PED_CMM_EstatusId NOT IN (select CMM_ControlId
												 		 from ControlesMaestrosMultiples
														 where CMM_Valor in ('Borrado','Borrada','Guardado','Cancelada','Cancelado','Rechazada','Rechazado')
														 and CMM_Control = @columnaEstadoMovimiento)-- No toma encuenta las OC que esten estatus de Cancelada o borrada;
			 ) t
							group by t.fecha;
			 END
			  if @tablaReferencia = 'Pagos'
			 BEGIN
			  select  @monto =
							SUM(monto_partida)
					from
						(
							select 
								CXPF_MontoRegistro monto_partida,
								cast(CXPSPS_FechaCreacion as date) fecha
							from 
								CXPSolicitudesPagosServicios 
								inner join CXPSolicitudesPagosServiciosDetalles on CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId = CXPSPS_CXPSolicitudPagoServicioId
								inner join CXPFacturas on CXPSPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
							where
								CXPSPS_CXPSolicitudPagoServicioId = @idMovimiento
							and CXPSPS_CMM_EstatusId NOT IN (select CMM_ControlId
												 		 from ControlesMaestrosMultiples
														 where CMM_Valor in ('Borrado','Borrada','Guardado','Cancelada','Cancelado','Rechazada','Rechazado')
														 and CMM_Control = @columnaEstadoMovimiento)-- No toma encuenta las OC que esten estatus de Cancelada o borrada
							) t
							group by t.fecha;
			 END
		end
	end
	else
	begin
	/* En caso de la tabla no exista se manda un false para no se creen los detalles de la alerta */
		set @resultado = 0;
	end
	if @monto >= @montoMinimo and @monto <= @montoMaximo
	begin
		set @resultado = 1;
	end
	else
	begin
		set @resultado = 0;
	end
	-- Return the result of the function
	RETURN @resultado 
END
GO