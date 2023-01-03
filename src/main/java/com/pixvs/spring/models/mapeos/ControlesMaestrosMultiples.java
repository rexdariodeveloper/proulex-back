package com.pixvs.spring.models.mapeos;

/**
 * IMPORTANTE: Solo se pueden agregar los registros que son de sistema (en su
 * campo CMM_Sistema = 1), ya que la referencia a estos registros es por su
 * identificador unique que por ser de sietema no cambia su valor
 */
public class ControlesMaestrosMultiples {

    public static final class CMM_Estatus {
        public static final Integer ACTIVO = 1000001;
        public static final Integer INACTIVO = 1000002;
        public static final Integer BORRADO = 1000003;
    }

    public static final class CMM_MP_TipoNodo {
        public static final Integer GRUPO = 1000010;
        public static final Integer NODO = 1000011;
        public static final Integer FICHA = 1000012;
    }

    public static final class CMM_SYS_SistemaAcceso {
        public static final Integer WEB = 1000021;
        public static final Integer APP = 1000022;
    }

    public static final class CMM_USUR_Estatus {
        public static final Integer SOLICITADO = 1000031;
        public static final Integer EXPIRADO = 1000032;
        public static final Integer USADO = 1000033;
    }

    public static final class CMM_USUR_EstatusUsuarioRecuperacion {
        public static final int SOLICITADO = 1000041;
        public static final int EXPIRADO = 1000042;
        public static final int USADO = 1000043;
    }

    public static final class CMM_EP_TipoEstadisticaPagina {
        public static final int SECCION = 1000051;
        public static final int CONTENIDO = 1000052;
        public static final int ENLACE = 1000053;
    }

    public static final class CMM_ACE_TipoAprobacion {
        public static final int USUARIO      = 1000101;
        public static final int DEPARTAMENTO = 1000102;
    }
    public static final class CMM_ACE_TipoOrden {
        public static final int PARALELA   = 1000111;
        public static final int SECUENCIAL = 1000112;
    }
    public static final class CMM_ACE_TipoCondicionAprobacion {
        public static final int UNA_APROBACION     = 1000121;
        public static final int TODAS_APROBACIONES = 1000122;
        public static final int NOTIFICAR_CREADOR  = 1000123;
    }
    public static final class CMM_ACE_TipoMonto {
        public static final int DIARIO     = 1000131;
        public static final int MENSUAL    = 1000132;
        public static final int EXHIBICION = 1000133;
    }
    public static final class CMM_CALC_TipoConfigAlerta {
        public static final int NOTIFICACION = 1000141;
        public static final int AMBOS        = 1000142;
        public static final int AUTORIZACION = 1000143;
    }
    public static final class CMM_CALE_EstatusAlerta {
        public static final int EN_PROCESO  = 1000151;
        public static final int RECHAZADA   = 1000152;
        public static final int EN_REVISION = 1000153;
        public static final int AUTORIZADA  = 1000154;
        public static final int CANCELADA   = 1000155;
        public static final int VISTA   = 1000156;
        public static final int ARCHIVADA   = 1000157;
    }
    public static final class CMM_CALC_TipoMovimiento {
        public static final int SOLICITUD_PAGO = 1000161;
        public static final int REQUISICIONES  = 1000162;
        public static final int ORDENES_COMPRA = 1000163;
        public static final int PEDIDOS        = 1000164;
        public static final int SURTIR_PEDIDOS = 1000165;
        public static final int PAGOS          = 1000166;
    }
    public static final class CMM_CALRD_TipoAlerta {
        public static final int NOTIFICACION = 1000171;
        public static final int AUTORIZACION = 1000172;
    }
    public static final class CMM_ACEN_TipoNotificacion {
        public static final int INICIAL = 1000181;
        public static final int FINAL   = 1000182;
    }

    public static final class CMM_USU_TipoId {
        public static final int SUPER_ADMIN   = 1000191;
        public static final int ESTUDIANTE_SIIAU = 1000192;
        public static final int PLATAFORMA = 1000193;
        public static final int USUARIO_SIIAU = 1000194;
    }
}
