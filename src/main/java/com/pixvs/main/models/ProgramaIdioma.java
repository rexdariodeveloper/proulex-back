package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "ProgramasIdiomas")
public class ProgramaIdioma {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROGI_ProgramaIdiomaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PROGI_Codigo", nullable = true, insertable = true, updatable = false, length = 10)
    private String codigo;

    @Column(name = "PROGI_Nombre", nullable = true, insertable = true, updatable = false, length = 100)
    private String nombre;

    //Idioma
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGI_PROG_ProgramaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PROG_ProgramaId")
    private Programa programa;

    @Column(name = "PROGI_PROG_ProgramaId", nullable = true, insertable = false, updatable = false)
    private Integer programaId;

    //Idioma
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGI_CMM_Idioma", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple idioma;

    @Column(name = "PROGI_CMM_Idioma", nullable = true, insertable = true, updatable = false)
    private Integer idiomaId;

    //Objeto Impuesto Sat
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGI_CMM_ObjetoImpuestoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple objetoImpuesto;

    @Column(name = "PROGI_CMM_ObjetoImpuestoId")
    private Integer objetoImpuestoId;

    //Plataforma
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGI_CMM_PlataformaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple plataforma;

    @Column(name = "PROGI_CMM_PlataformaId", nullable = false, insertable = true, updatable = true)
    private Integer plataformaId;

    @Column(name = "PROGI_HorasTotales", nullable = false, insertable = true, updatable = true)
    private Integer horasTotales;

    @Column(name = "PROGI_NumeroNiveles", nullable = false, insertable = true, updatable = true)
    private Integer numeroNiveles;

    @Column(name = "PROGI_CalificacionMinima", nullable = false, insertable = true, updatable = true)
    private BigDecimal calificacionMinima;

    @Column(name = "PROGI_MCER", length = 50, nullable = false, insertable = true, updatable = true)
    private String mcer;

    @Column(name = "PROGI_IVA", nullable = true, insertable = true, updatable = true)
    private Integer iva;

    @Column(name = "PROGI_FaltasPermitidas", nullable = false, insertable = true, updatable = true)
    private BigDecimal faltasPermitidas;

    @Column(name = "PROGI_IVAExento", nullable = true, insertable = true, updatable = true)
    private Boolean ivaExento;

    @Column(name = "PROGI_IEPS", nullable = true, insertable = true, updatable = true)
    private Integer ieps;

    @Column(name = "PROGI_CuotaFija", nullable = true, insertable = true, updatable = true)
    private Boolean cuotaFija;

    //Unidad de medida
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGI_UM_UnidadMedidaId", insertable = false, updatable = false, referencedColumnName = "UM_UnidadMedidaId")
    private UnidadMedida unidadMedida;

    @Column(name = "PROGI_UM_UnidadMedidaId", insertable = true, updatable = true)
    private Integer unidadMedidaId;

    @Column(name = "PROGI_CLAVE", length = 8, nullable = true, insertable = true, updatable = true)
    private String clave;

    @Column(name = "PROGI_Descripcion", length = 500, nullable = false, insertable = true, updatable = true)
    private String descripcion;

    @Column(name = "PROGI_ExamenEvaluacion", nullable = false, insertable = true, updatable = true)
    private Boolean examenEvaluacion;

    @Column(name = "PROGI_AgruparListadosPreciosPorTipoGrupo", nullable = false, insertable = true, updatable = true)
    private Boolean agruparListadosPreciosPorTipoGrupo;

    @Column(name = "PROGI_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    //Tipo workshop
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGI_CMM_TipoWorkshopId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoWorkshop;

    @Column(name = "PROGI_CMM_TipoWorkshopId", nullable = true, insertable = true, updatable = false)
    private Integer tipoWorkshopId;

//    @CreationTimestamp
//    @Column(name = "PROGI_FechaCreacion", nullable = false, insertable = true, updatable = false)
//    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "PROGI_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PROGI_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PROGI_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    @Where(clause = "PROGIC_Borrado = 0")
    @JoinColumn(name = "PROGIC_PROGI_ProgramaIdiomaId", referencedColumnName = "PROGI_ProgramaIdiomaId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaIdiomaCertificacion> certificaciones;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    @Where(clause = "PROGILM_Borrado = 0")
    @JoinColumn(name = "PROGILM_PROGI_ProgramaIdiomaId", referencedColumnName = "PROGI_ProgramaIdiomaId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaIdiomaLibroMaterial> librosMateriales;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGIM_PROGI_ProgramaIdiomaId", referencedColumnName = "PROGI_ProgramaIdiomaId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaIdiomaModalidad> modalidades;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGIS_PROGI_ProgramaIdiomaId", referencedColumnName = "PROGI_ProgramaIdiomaId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaIdiomaSucursal> sucursales;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    @Where(clause = "PROGIN_Activo = 1")
    @JoinColumn(name = "PROGIN_PROGI_ProgramaIdiomaId", referencedColumnName = "PROGI_ProgramaIdiomaId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaIdiomaNivel> niveles;

    @Transient
    Boolean borrar;

    @Transient
    Boolean esJobs;

    @Transient
    Boolean esJobsSems;

    @Transient
    Boolean esAcademy;

    @Transient
    Integer modalidadesSize;

    @Transient
    Integer sucursalesSize;

    @Transient
    Boolean esNuevo;

    @Transient
    private Boolean actualizarGrupos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProgramaId() {
        return programaId;
    }

    public void setProgramaId(Integer programaId) {
        this.programaId = programaId;
    }

    public ControlMaestroMultiple getIdioma() {
        return idioma;
    }

    public void setIdioma(ControlMaestroMultiple idioma) {
        this.idioma = idioma;
    }

    public Integer getIdiomaId() {
        return idiomaId;
    }

    public void setIdiomaId(Integer idiomaId) {
        this.idiomaId = idiomaId;
    }

    public Integer getHorasTotales() {
        return horasTotales;
    }

    public void setHorasTotales(Integer horasTotales) {
        this.horasTotales = horasTotales;
    }

    public Integer getNumeroNiveles() {
        return numeroNiveles;
    }

    public void setNumeroNiveles(Integer numeroNiveles) {
        this.numeroNiveles = numeroNiveles;
    }

    public BigDecimal getCalificacionMinima() {
        return calificacionMinima;
    }

    public void setCalificacionMinima(BigDecimal calificacionMinima) {
        this.calificacionMinima = calificacionMinima;
    }

    public String getMcer() {
        return mcer;
    }

    public void setMcer(String mcer) {
        this.mcer = mcer;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Integer getUnidadMedidaId() {
        return unidadMedidaId;
    }

    public void setUnidadMedidaId(Integer unidadMedidaId) {
        this.unidadMedidaId = unidadMedidaId;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public Integer getModificadoPorId() {
        return modificadoPorId;
    }

    public void setModificadoPorId(Integer modificadoPorId) {
        this.modificadoPorId = modificadoPorId;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public List<ProgramaIdiomaCertificacion> getCertificaciones() {
        return certificaciones;
    }

    public void setCertificaciones(List<ProgramaIdiomaCertificacion> certificaciones) {
        this.certificaciones = certificaciones;
    }

    public List<ProgramaIdiomaLibroMaterial> getLibrosMateriales() {
        return librosMateriales;
    }

    public void setLibrosMateriales(List<ProgramaIdiomaLibroMaterial> librosMateriales) {
        this.librosMateriales = librosMateriales;
    }

    public List<ProgramaIdiomaModalidad> getModalidades() {
        return modalidades;
    }

    public void setModalidades(List<ProgramaIdiomaModalidad> modalidades) {
        this.modalidades = modalidades;
    }

    public List<ProgramaIdiomaSucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<ProgramaIdiomaSucursal> sucursales) {
        this.sucursales = sucursales;
    }

    public Boolean getExamenEvaluacion() {
        return examenEvaluacion;
    }

    public void setExamenEvaluacion(Boolean examenEvaluacion) {
        this.examenEvaluacion = examenEvaluacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public ControlMaestroMultiple getTipoWorkshop() { return tipoWorkshop; }
    public void setTipoWorkshop(ControlMaestroMultiple tipoWorkshop) { this.tipoWorkshop = tipoWorkshop; }

    public Integer getTipoWorkshopId() { return tipoWorkshopId; }
    public void setTipoWorkshopId(Integer tipoWorkshopId) { this.tipoWorkshopId = tipoWorkshopId; }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public ControlMaestroMultiple getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(ControlMaestroMultiple plataforma) {
        this.plataforma = plataforma;
    }

    public Integer getPlataformaId() {
        return plataformaId;
    }

    public void setPlataformaId(Integer plataformaId) {
        this.plataformaId = plataformaId;
    }

    public Boolean getBorrar() {
        return borrar;
    }

    public void setBorrar(Boolean borrar) {
        this.borrar = borrar;
    }

    public Integer getIva() {
        return iva;
    }

    public void setIva(Integer iva) {
        this.iva = iva;
    }

    public Boolean getIvaExento() {
        return ivaExento;
    }

    public void setIvaExento(Boolean ivaExento) {
        this.ivaExento = ivaExento;
    }

    public Integer getIeps() {
        return ieps;
    }

    public void setIeps(Integer ieps) {
        this.ieps = ieps;
    }

    public Boolean getCuotaFija() {
        return cuotaFija;
    }

    public void setCuotaFija(Boolean cuotaFija) {
        this.cuotaFija = cuotaFija;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getModalidadesSize() {
        return modalidadesSize;
    }

    public void setModalidadesSize(Integer modalidadesSize) {
        this.modalidadesSize = modalidadesSize;
    }

    public Integer getSucursalesSize() {
        return sucursalesSize;
    }

    public void setSucursalesSize(Integer sucursalesSize) {
        this.sucursalesSize = sucursalesSize;
    }

    public BigDecimal getFaltasPermitidas() {
        return faltasPermitidas;
    }

    public void setFaltasPermitidas(BigDecimal faltasPermitidas) {
        this.faltasPermitidas = faltasPermitidas;
    }

    public List<ProgramaIdiomaNivel> getNiveles() {
        return niveles;
    }

    public void setNiveles(List<ProgramaIdiomaNivel> niveles) {
        this.niveles = niveles;
    }

    public Boolean getEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(Boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    public Boolean getEsJobs() {
        return esJobs;
    }

    public void setEsJobs(Boolean esJobs) {
        this.esJobs = esJobs;
    }

    public Boolean getEsJobsSems() {
        return esJobsSems;
    }

    public void setEsJobsSems(Boolean esJobsSems) {
        this.esJobsSems = esJobsSems;
    }

    public Boolean getEsAcademy() {
        return esAcademy;
    }

    public void setEsAcademy(Boolean esAcademy) {
        this.esAcademy = esAcademy;
    }

    public ControlMaestroMultiple getObjetoImpuesto() {
        return objetoImpuesto;
    }

    public void setObjetoImpuesto(ControlMaestroMultiple objetoImpuesto) {
        this.objetoImpuesto = objetoImpuesto;
    }

    public Integer getObjetoImpuestoId() {
        return objetoImpuestoId;
    }

    public void setObjetoImpuestoId(Integer objetoImpuestoId) {
        this.objetoImpuestoId = objetoImpuestoId;
    }

    //    public Date getFechaCreacion() {
//        return fechaCreacion;
//    }
//
//    public void setFechaCreacion(Date fechaCreacion) {
//        this.fechaCreacion = fechaCreacion;
//    }


    public Boolean getActualizarGrupos() {
        return actualizarGrupos;
    }

    public void setActualizarGrupos(Boolean actualizarGrupos) {
        this.actualizarGrupos = actualizarGrupos;
    }

    public Boolean getAgruparListadosPreciosPorTipoGrupo() {
        return agruparListadosPreciosPorTipoGrupo;
    }

    public void setAgruparListadosPreciosPorTipoGrupo(Boolean agruparListadosPreciosPorTipoGrupo) {
        this.agruparListadosPreciosPorTipoGrupo = agruparListadosPreciosPorTipoGrupo;
    }
}
