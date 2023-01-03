package com.pixvs.main.models.mapeos;

/**
 * IMPORTANTE: Solo se pueden agregar los registros que son de sistema (en su
 * campo CMM_Sistema = 1), ya que la referencia a estos registros es por su
 * identificador unique que por ser de sietema no cambia su valor
 */
public class ControlesMaestrosMultiples {

    public static final class CMM_EMP_TipoSangre {
        public static final String NOMBRE = "CMM_EMP_TipoSangre";
    }

    public static final class CMM_AI_MotivoAjuste {
        public static final int DISCREPANCIA_CONTEO_FISICO = 1000091;
        public static final int ERROR_REGISTRO = 1000092;
        public static final int SPILL = 1000093;

        public static final String NOMBRE = "CMM_AI_MotivoAjuste";
    }

    public static final class CMM_IM_TipoMovimiento {
        public static final int AJUSTE_INVENTARIO = 2000011;
        public static final int TRANSFERENCIA = 2000012;
        public static final int RECIBO_OC = 2000013;
        public static final int DEVOLUCION_OC = 2000014;
        public static final int INVENTARIO_FISICO = 2000015;

        public static final int SURTIR_PEDIDO = 2000016;
        public static final int RECIBIR_PEDIDO = 2000017;
        public static final int DEVOLUCION_PEDIDO = 2000018;

        public static final int INSCRIPCION_SIAP = 2000019;

        public static final int REMISION = 2000430;
        public static final int DEVOLUCION_REMISION = 2000431;
        public static final int FACTURA_REMISION = 2000432;

        public static final int INSCRIPCION = 2000433;
        public static final int VENTA_DE_LIBRO = 2000434;
        public static final int CANCELACION_DE_INSCRIPCION = 2000435;
        public static final int VENTA_DE_EXAMEN_O_CERTIFICACION = 2000436;
        public static final int VENTA_DE_CONSTANCIA = 2000437;
        public static final int VENTA_DE_TUTORIA = 2000438;
        public static final int CANCELACION_DE_NOTA_DE_VENTA = 2000439;

        public static final String NOMBRE = "CMM_IM_TipoMovimiento";
    }

    public static final class CMM_TRA_EstatusTransferencia {
        public static final int EN_TRANSITO = 2000021;
        public static final int TRANSFERIDO_PARCIALMENTE = 2000022;
        public static final int TRANSFERIDO = 2000023;
        public static final int RECHAZADO = 2000024;

        public static final String NOMBRE = "CMM_TRA_EstatusTransferencia";
	}

    public static final class CMM_ARTT_TipoArticulo {
        public static final int COMPRADO = 2000031;
        public static final int FABRICADO = 2000032;
        public static final int SISTEMA = 2000033;

        public static final String NOMBRE = "CMM_ARTT_TipoArticulo";
    }

    public static final class CMM_ART_TipoCosto {
        public static final int ULTIMO = 2000041;
        public static final int PROMEDIO = 2000042;
        public static final int ESTANDAR = 2000043;

        public static final String NOMBRE = "CMM_ART_TipoCosto";
    }

    public static final class CMM_ALM_TipoAlmacen {
        public static final int NORMAL = 2000051;
        public static final int TRANSITO = 2000052;

        public static final String NOMBRE = "CMM_ALM_TipoAlmacen";
    }

    public static final class CMM_IF_EstatusInventarioFisico {
        public static final int EN_EDICION = 2000071;
        public static final int BORRADO = 2000072;
        public static final int AJUSTADO = 2000073;

        public static final String NOMBRE = "CMM_IF_EstatusInventarioFisico";
	}
	
	public static final class CMM_OC_EstatusOC {
		public static final int EN_EDICION = 2000061;
		public static final int BORRADA = 2000062;
		public static final int ABIERTA = 2000063;
		public static final int CERRADA = 2000064;
		public static final int CANCELADA = 2000065;
		public static final int RECIBO_PARCIAL = 2000066;
		public static final int RECIBIDA = 2000067;
		public static final int FACTURADA = 2000068;
        public static final int PROGRAMADA = 2000069;
        public static final int PAGADA = 2000070;
	
		public static final String NOMBRE = "CMM_OC_EstatusOC";
	}

	public static final class CMM_IM_TipoSpill {
        public static final int ROBO  = 2000091;
        public static final int SPILL = 2000092;

        public static final String NOMBRE = "CMM_IM_TipoSpill";
    }
	
	public static final class CMM_OCR_TipoArchivo {
		public static final int EVIDENCIA = 2000101;
		public static final int FACTURA = 2000102;
	
		public static final String NOMBRE = "CMM_OCR_TipoArchivo";
	}

    public static final class CMM_PED_EstatusPedido {
        public static final int GUARDADO = 2000260;
        public static final int POR_SURTIR = 2000261;
        public static final int SURTIDO_PARCIAL = 2000262;
        public static final int SURTIDO = 2000263;
        public static final int COMPLETO = 2000264;
        public static final int CERRADO = 2000265;
        public static final int CANCELADO = 2000266;
        public static final int POR_AUTORIZAR = 2000267;
        public static final int EN_REVISION = 2000268;
        public static final int RECHAZADO = 2000269;
        public static final int BORRADO = 2000270;

        public static final String NOMBRE = "CMM_PED_EstatusPedido";
    }

    public static final class CMM_CXPF_EstatusFactura {
        public static final int BORRADO = 2000111;
        public static final int ABIERTA = 2000112;
        public static final int CERRADA = 2000113;
        public static final int CANCELADA = 2000114;
        public static final int PAGO_PROGRAMADO = 2000115;
        public static final int PAGO_PROGRAMADO_EN_PROCESO = 2000116;
        public static final int PAGO_PARCIAL = 2000117;
        public static final int PAGADA = 2000118;
        public static final int PAGO_PROGRAMADO_PARCIAL = 2000119;

        public static final String NOMBRE = "CMM_CXPF_EstatusFactura";
    }

    public static final class CMM_CXPF_TipoRegistro {
        public static final int NOTA_DE_DEBITO = 2000121;
        public static final int FACTURA_CXP = 2000122;

        public static final String NOMBRE = "CMM_CXPF_TipoRegistro";
    }

    public static final class CMM_CCXP_TipoPago {
        public static final int NO_PAGAR = 2000141;
        public static final int PAGO_DE_INMEDIATO = 2000142;
        public static final int PAGO_PROGRAMADO = 2000143;

        public static final String NOMBRE = "CMM_CCXP_TipoPago";
    }

    public static final class CMM_CXPF_TipoRetencion {
        public static final int ISR = 2000151;
        public static final int IVA = 2000152;

        public static final String NOMBRE = "CMM_CXPF_TipoRetencion";
    }

    public static final class CMM_CXPS_EstadoSolicitudPago {
        public static final int ACEPTADA = 2000161;
        public static final int PAGADA = 2000162;
        public static final int CANCELADA = 2000163;
        public static final int BORRADA = 2000164;
        public static final int POR_AUTORIZAR = 2000165;
        public static final int RECHAZADA = 2000166;

        public static final String NOMBRE = "CMM_CXPS_EstadoSolicitudPago";
    }

    public static final class CMM_CXPP_EstatusPago {
        public static final int PAGO_PARCIAL = 2000171;
        public static final int PAGADO = 2000172;
        public static final int PAGO_CANCELADO = 2000173;

        public static final String NOMBRE = "CMM_CXPP_EstatusPago";
    }

    public static final class CMM_CXPP_FormaPago {
        public static final int TRANSFERENCIA_ELECTRONICA_DE_FONDOS = 2000181;
        public static final int EFECTIVO = 2000182;
        public static final int TARJETA_DE_CREDITO = 2000183;
        public static final int TARJETA_DE_DEBITO = 2000184;
        public static final int POR_DEFINIR = 2000185;

        public static final String NOMBRE = "CMM_CXPP_FormaPago";
    }

    public static final class CMM_REQ_EstatusRequisicion {
        public static final int GUARDADO = 2000191;
        public static final int POR_AUTORIZAR = 2000192;
        public static final int AUTORIZADA = 2000193;
        public static final int EN_REVISION = 2000194;
        public static final int RECHAZADA = 2000195;
        public static final int EN_PROCESO = 2000196;
        public static final int CONVERTIDA = 2000197;
        public static final int BORRADA = 2000198;

        public static final String NOMBRE = "CMM_REQ_EstatusRequisicion";
    }

    public static final class CMM_REQP_EstatusRequisicionPartida {
        public static final int ABIERTA = 2000201;
        public static final int REVISION = 2000202;
        public static final int RECHAZADO = 2000203;
        public static final int CONVERTIDA = 2000204;

        public static final String NOMBRE = "CMM_REQP_EstatusRequisicionPartida";
    }

    public static final class CMM_SUC_TipoSucursal {
        public static final int ZMG = 2000211;
        public static final int FORANEA = 2000212;
        public static final int ALLIANCE = 2000213;

        public static final String NOMBRE = "CMM_SUC_TipoSucursal";
    }

    public static final class CMM_IMP_TipoImpresion {
        public static final int LOCAL = 2000231;
        public static final int COMPARTIDA = 2000232;
        public static final int IP = 2000233;

        public static final String NOMBRE = "CMM_IMP_TipoImpresion";
    }

    public static final class CMM_PRO_TipoProveedor {
        public static final int PERSONA_FISICA = 2000241;
        public static final int PERSONA_MORAL = 2000242;

        public static final String NOMBRE = "CMM_PRO_TipoProveedor";
    }

    public static final class CMM_PROC_TipoContacto {
        public static final int CONTACTO_COMPRAS = 2000251;
        public static final int CONTACTO_PAGOS = 2000252;

        public static final String NOMBRE = "CMM_PROC_TipoContacto ";
    }

    public static final class CMM_ART_Idioma {
        public static final String NOMBRE = "CMM_ART_Idioma ";
    }

    public static final class CMM_PROGRU_TipoGrupo {
        public static final String NOMBRE = "CMM_PROGRU_TipoGrupo ";
    }

    public static final class CMM_ART_Programa {
        public static final String NOMBRE = "CMM_ART_Programa ";
    }

    public static final class CMM_ART_Editorial {
        public static final String NOMBRE = "CMM_ART_Editorial ";
    }

    public static final class CMM_CXPSPS_EstadoSolicitudPago {
        public static final int ACEPTADA = 2000281;
        public static final int PAGADA = 2000282;
        public static final int CANCELADA = 2000283;
        public static final int BORRADA = 2000284;
        public static final int POR_AUTORIZAR = 2000285;
        public static final int RECHAZADA = 2000286;

        public static final String NOMBRE = "CMM_CXPSPS_EstadoSolicitudPago";
    }

    public static final class CMM_EMP_GeneroId {
        public static final int MASCULINO = 2000290;
        public static final int FEMENINO = 2000291;

        public static final String NOMBRE = "CMM_EMP_GeneroId";
    }

    public static final class CMM_EMP_TipoEmpleadoId {
        public static final int ADMINISTRATIVO = 2000293;
        public static final int ACADEMICO = 2000294;
        public static final int BECARIO = 2000295;

        public static final String NOMBRE = "CMM_EMP_TipoEmpleadoId";
    }

    public static final class CMM_EMP_TipoContratoId {
        public static final int HONORARIOS = 2000296;
        public static final int TEMPORAL = 2000297;
        public static final int INDEFINIDO = 2000298;

        public static final String NOMBRE = "CMM_EMP_TipoContratoId";
    }

    public static final class CMM_EMP_EstadoCivilId {
        public static final String NOMBRE = "CMM_EMP_EstadoCivilId";
    }

    public static final class CMM_EMP_PuestoId {
        public static final String NOMBRE = "CMM_EMP_PuestoId";
    }

    public static final class CMM_CPXSPRH_EstatusId {
        public static final String NOMBRE = "CMM_CPXSPRH_EstatusId";
        public static final int ACEPTADA = 2000350;
        public static final int PAGADA = 2000351;
        public static final int CANCELADA = 2000352;
        public static final int BORRADA = 2000353;
        public static final int POR_AUTORIZAR = 2000354;
        public static final int RECHAZADA = 2000355;
    }

    public static final class CMM_PROGI_Plataforma{
        public static final String NOMBRE = "CMM_PROGI_Plataforma";
    }

    public static final class CMM_PROGI_TestFormat{
        public static final String NOMBRE = "CMM_PROGI_TestFormat";
    }

    public static final class CMM_CE_Escolaridad {
        public static final String NOMBRE = "CMM_CE_Escolaridad";
    }

    public static final class CMM_CE_Parentesco {
        public static final String NOMBRE = "CMM_CE_Parentesco";
        public static final int MADRE = 2000400;
        public static final int PADRE = 2000401;
        public static final int TUTOR = 2000402;
    }

    public static final class CMM_RFC_TipoPersona {
        public static final String NOMBRE = "CMM_RFC_TipoPersona";
        public static final int FISICA = 2000410;
        public static final int MORAL = 2000411;
        public static final int EXTRANJERO = 2000412;
    }

    public static final class CMM_CLIR_EstatusRemision {
        public static final String NOMBRE = "CMM_CLIR_EstatusRemision";
        public static final int ENVIADA = 2000420;
        public static final int FACTURADA_PARCIAL = 2000421;
        public static final int FACTURADA = 2000422;
        public static final int DEVUELTA_PARCIAL = 2000423;
        public static final int DEVUELTA = 2000424;
        public static final int CANCELADA = 2000425;
    }

    public static final class CMM_CXCF_TipoRegistro {
        public static final int NOTA_DEBITO = 2000470;
        public static final int FACTURA_CXC = 2000471;
        public static final int FACTURA_SERVICIO_CXC = 2000472;
        public static final int FACTURA_NOTA_VENTA = 2000473;
        public static final int FACTURA_GLOBAL = 2000474;
        public static final int FACTURA_MISCELANEA = 2000475;

        public static final String NOMBRE = "CMM_CXCF_TipoRegistro";
    }

    public static final class CMM_CXCF_EstatusFactura {
        public static final int BORRADO = 2000490;
        public static final int ABIERTA = 2000491;
        public static final int CERRADA = 2000492;
        public static final int CANCELADA = 2000493;
        public static final int PAGO_PARCIAL = 2000494;
        public static final int PAGADA = 2000495;

        public static final String NOMBRE = "CMM_CXCF_EstatusFactura";
    }

    public static final class CMM_CXC_MetodoPago {
        public static final String NOMBRE = "CMM_CXC_MetodoPago";
    }

    public static final class CMM_CXC_UsoCFDI {
        public static final String NOMBRE = "CMM_CXC_UsoCFDI";
    }

    public static final class CMM_OV_Estatus {
        public static final int EN_EDICION = 2000500;
        public static final int BORRADA = 2000501;
        public static final int ABIERTA = 2000502;
        public static final int CERRADA = 2000503;
        public static final int CANCELADA = 2000504;
        public static final int EMBARQUE_PARCIAL = 2000505;
        public static final int EMBARCADA = 2000506;
        public static final int FACTURADA = 2000507;
        public static final int PAGADA = 2000508;

        public static final String NOMBRE = "CMM_OV_Estatus";
    }

    public static final class CMM_INS_Estatus {
        public static final int PAGADA = 2000510;
        public static final int PENDIENTE_DE_PAGO = 2000511;
        public static final int CANCELADA = 2000512;
        public static final int BAJA = 2000513;

        public static final String NOMBRE = "CMM_INS_Estatus";
    }

    public static final class CMM_ALU_ProgramaJOBS {
        public static final int JOBS = 2000530;
        public static final int JOBS_SEMS = 2000531;

        public static final String NOMBRE = "CMM_ALU_ProgramaJOBS";
    }

    public static final class CMM_ALU_CentrosUniversitarios {

        public static final String NOMBRE = "CMM_ALU_CentrosUniversitarios";
    }

    public static final class CMM_ALU_Preparatorias {

        public static final String NOMBRE = "CMM_ALU_Preparatorias";
    }

    public static final class CMM_ALU_Carreras {

        public static final String NOMBRE = "CMM_ALU_Carreras";
    }

    public static final class CMM_INSSG_Estatus {
        public static final int PAGADA = 2000540;
        public static final int PENDIENTE_DE_PAGO = 2000541;
        public static final int ASIGNADA = 2000542;
        public static final int CANCELADA = 2000543;

        public static final String NOMBRE = "CMM_INSSG_Estatus";
    }

    public static final class CMM_ALU_Turnos {

        public static final String NOMBRE = "CMM_ALU_Turnos";
    }

    public static final class CMM_ALU_Grados {

        public static final String NOMBRE = "CMM_ALU_Grados";
    }

    public static final class CMM_BECU_Estatus {
        public static final int PENDIENTE_POR_APLICAR = 2000570;
        public static final int APLICADA = 2000571;
        public static final int CANCELADA = 2000572;
        public static final int EXPIRADA = 2000573;

        public static final String NOMBRE = "CMM_BECU_Estatus";
    }

    public static final class CMM_BECU_Tipo {
        public static final int SUTUDG = 2000580;
        public static final int STAUDG = 2000581;
        public static final int PROULEX = 2000582;

        public static final String NOMBRE = "CMM_BECU_Tipo";
    }

    public static final class CMM_ALU_TipoAlumno {
    public static final int ALUMNO = 2000590;
    public static final int CANDIDATO = 2000591;

        public static final String NOMBRE = "CMM_ALU_TipoAlumno";
    }

    public static final class CMM_DEDPER_Tipo {
        public static final int DEDUCCION = 2000605;
        public static final int PERCEPCION = 2000606;

        public static final String NOMBRE = "CMM_DEDPER_Tipo";
    }

    public static final class CMM_PROGRU_Estatus {
        public static final int ACTIVO = 2000620;
        public static final int FINALIZADO = 2000621;
        public static final int CANCELADO = 2000622;

        public static final String NOMBRE = "CMM_PROGRU_Estatus";
    }

    public static final class CMM_CE_MedioEnteradoProulex {

        public static final String NOMBRE = "CMM_CE_MedioEnteradoProulex";
    }

    public static final class CMM_CE_RazonEleccionProulex {

        public static final String NOMBRE = "CMM_CE_RazonEleccionProulex";
    }

    public static final class CMM_SUC_SucursalJOBSId {
        public static final int CMM_SUC_SucursalJOBSId = 2000630;

        public static final String NOMBRE = "CMM_SUC_SucursalJOBSId";
    }

    public static final class CMM_SUC_SucursalJOBSSEMSId {
        public static final int CMM_SUC_SucursalJOBSSEMSId = 2000631;

        public static final String NOMBRE = "CMM_SUC_SucursalJOBSSEMSId";
    }

    public static final class CMM_ALUEC_Estatus {
        public static final int EN_PROCESO = 2000640;
        public static final int FINALIZADO = 2000641;
        public static final int CANCELADO = 2000642;
        public static final int RELACIONADO = 2000643;

        public static final String NOMBRE = "CMM_ALUEC_Estatus";
    }

    public static final class CMM_ALUEC_Tipo {
        public static final int EXAMEN = 2000650;
        public static final int CERTIFICACION = 2000651;

        public static final String NOMBRE = "CMM_ALUEC_Tipo";
    }

    public static final class CMM_PADES_Tipo {
        public static final String NOMBRE = "CMM_PADES_Tipo";
    }

    public static final class CMM_ALUG_Estatus {
        public static final String NOMBRE = "CMM_ALUG_Estatus";
        public static final int REGISTRADO = 2000670;
        public static final int ACTIVO = 2000671;
        public static final int EN_RIESGO = 2000672;
        public static final int SIN_DERECHO = 2000673;
        public static final int DESERTOR = 2000674;
        public static final int APROBADO = 2000675;
        public static final int REPROBADO = 2000676;
        public static final int BAJA = 2000677;
    }

    public static final class CMM_EMP_Parentesco {

        public static final String NOMBRE = "CMM_EMP_Parentesco";
    }

    public static final class CMM_EMP_GradoEstudios {

        public static final String NOMBRE = "CMM_EMP_GradoEstudios";
    }

    public static final class CMM_EMP_Nacionalidad {

        public static final String NOMBRE = "CMM_EMP_Nacionalidad";
    }

    public static final class CMM_BECS_Estatus {

        public static final String NOMBRE = "CMM_BECS_Estatus";
        public static final int PENDIENTE = 2000800;
        public static final int APROBADA = 2000801;
        public static final int RECHAZADA = 2000802;
        public static final int CANCELADA = 2000806;
    }

    public static final class CMM_BECU_EstatusBecaProulex {

        public static final String NOMBRE = "CMM_BECU_EstatusBecaProulex";
        public static final int PROCESO = 2000803;
        public static final int AUTORIZADO = 2000804;
        public static final int RECHAZADA = 2000805;
        public static final int CANCELADA = 2000807;
    }

    public static final class CMM_ENT_TipoContrato {

        public static final String NOMBRE = "CMM_ENT_TipoContrato";
    }

    public static final class CMM_PRENOM_TipoMovimiento {

        public static final String NOMBRE = "CMM_PRENOM_TipoMovimiento";
        public static final int PAGO_A_PROFESOR_TITULAR = 2000690;
        public static final int PAGO_POR_SUSTITUCION = 2000691;
        public static final int DEDUCCION_MANUAL = 2000692;
        public static final int PERCEPCION_MANUAL = 2000693;
        public static final int PAGO_POR_DIA_FESTIVO = 2000694;
        public static final int DEDUCCION_POR_SUSTITUCION = 2000695;
        public static final int DEDUCCION_POR_CAMBIO_DE_PROFESOR_TITULAR = 2000696;
        public static final int DEDUCCION_POR_CANCELACION_DE_CLASE = 2000697;
        public static final int APOYO_PARA_TRANSPORTE = 2000698;
    }

    public static final class CMM_RH_TipoProcesoRH {
        public static final int NUEVA_CONTRATACION = 2000900;
        public static final int RENOVACION = 2000901;
        public static final int BAJA = 2000902;
        public static final int CAMBIO = 2000903;
        public static final String NOMBRE = "CMM_RH_TipoProcesoRH";
    }

    public static final class CMM_GEN_TipoDocumento {
        public static final String NOMBRE = "CMM_GEN_TipoDocumento";
    }

    public static final class CMM_GEN_TipoOpcion {
        public static final String NOMBRE = "CMM_GEN_TipoOpcion";
    }

    public static final class CMM_GEN_TipoVigencia {
        public static final String NOMBRE = "CMM_GEN_TipoVigencia";
    }

    public static final class CMM_UMT_TipoTiempo {
        public static final String NOMBRE = "CMM_UMT_TipoTiempo";
    }

    public static final class CMM_OV_MetodoPago {
        public static final int PUE = 2000710;
        public static final int PPD = 2000710;

        public static final String NOMBRE = "CMM_OV_MetodoPago";
    }

    public static final class CMM_ENT_Justificacion {
        public static final String NOMBRE = "CMM_ENT_Justificacion";
    }

    public static final class CMM_ENT_TipoHorario {
        public static final String NOMBRE = "CMM_ENT_TipoHorario";
    }

    public static final class CMM_EMP_Estatus {
        public static final int ACTIVO = 2000950;
        public static final int BORRADO = 2000951;
        public static final int GUARDADO = 2000952;
        public static final int PROSPECTO = 2000953;
        public static final int BAJA = 2000954;
        public static final int RENOVADO = 2000955;
        public static final String NOMBRE = "CMM_EMP_Estatus";
    }

    public static final class CMM_SAT_ObjetoImp {
        public static final int NO_OBJETO_IMPUESTO = 2000970;
        public static final int SI_OBJETO_IMPUESTO = 2000971;
        public static final int SI_OBJETO_DEL_IMPUESTO_Y_NO_OBLIGADO_AL_DESGLOSE = 2000972;

        public static final String NOMBRE = "CMM_SAT_ObjetoImp";
    }

    public static final class CMM_INC_Zona {
        public static final String NOMBRE = "CMM_INC_Zona";
    }

    public static final class CMM_OVC_MotivoDevolucion {
        public static final String NOMBRE = "CMM_OVC_MotivoDevolucion";
    }

    public static final class CMM_OVC_MotivoCancelacion {
        public static final String NOMBRE = "CMM_OVC_MotivoCancelacion";
    }

    public static final class CMM_OVC_TipoMovimiento {
        public static final int CANCELACION = 2000081;
        public static final int DEVOLUCION = 2000080;
    }

    public static final class CMM_OVC_Estatus {
        public static final int APROBADA = 2000720;
        public static final int BORRADA = 2000721;

        public static final String NOMBRE = "CMM_OVC_Estatus";
    }

    public static final class CMM_OVC_TiposCancelacion {
        public static final int _100 = 2000730;

        public static final String NOMBRE = "CMM_OVC_TiposCancelacion";
    }

    public static final class CMM_OVC_TiposDocumento {
        public static final int NOTA_DE_VENTA_O_FACTURA = 2000740;
        public static final int NUMERO_DE_CORTE = 2000741;
        public static final int COMPROBANTE_DE_PAGO = 2000742;
        public static final int IDENTIFICACION_OFICIAL_DE_LA_PERSONA_QUE_RECIBE_LA_TRANSFERENCIA = 2000743;

        public static final String NOMBRE = "CMM_OVC_TiposDocumento";
    }

    public static final class CMM_INO_InscripcionOrigen {
        public static final int PROYECCION = 2000980;
        public static final int PLANTILLA = 2000981;
        public static final int PUNTO_DE_VENTA = 2000982;

        public static final String NOMBRE = "CMM_OVC_TiposDocumento";
    }

    public static final class CMM_PREINC_Estatus {
        public static final int ACTIVO = 2001000;
        public static final int INACTIVO = 2001001;
        public static final int CANCELADO = 2001002;

        public static final String NOMBRE = "CMM_PREINC_Estatus";
    }

    public static final class CMM_RH_TipoMotivo {
        public static final String NOMBRE = "CMM_RH_TipoMotivo";
    }

    public static final class CMM_VC_Estatus {
        public static final int NO_GENERADO = 2001030;
        public static final int GENERADO = 2001031;
        public static final int APLICADO = 2001032;
        public static final int CANCELADO = 2001033;
        public static final int VENCIDO = 2001034;
        public static final int BORRADO = 2001035;
        public static final String NOMBRE = "CMM_VD_Estatus";
    }

    public static final class CMM_PUESTO_Estatus {
        public static final int ACTIVO = 2001040;
        public static final int INACTIVO = 2001041;
        public static final String NOMBRE = "CMM_PUESTO_Estatus";
    }

}
