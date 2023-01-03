package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Angel Daniel Hernández Silva on 06/07/2020.
 */
@Entity
@Table(name = "Articulos")
public class Articulo {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ART_ArticuloId", nullable = false, insertable = false, updatable = false)
	private Integer id;

	@Column(name = "ART_CodigoArticulo", length = 30, nullable = false, insertable = true, updatable = true)
	private String codigoArticulo;

	@Column(name = "ART_NombreArticulo", length = 100, nullable = false, insertable = true, updatable = true)
	private String nombreArticulo;

	@Column(name = "ART_CodigoBarras", length = 50, nullable = true, insertable = true, updatable = true)
	private String codigoBarras;

	@Column(name = "ART_CodigoAlterno", length = 30, nullable = true, insertable = true, updatable = true)
	private String codigoAlterno;

	@Column(name = "ART_NombreAlterno", length = 100, nullable = true, insertable = true, updatable = true)
	private String nombreAlterno;

	@Column(name = "ART_Descripcion", length = 1000, insertable = true, updatable = true)
	private String descripcion;

	@Column(name = "ART_DescripcionCorta", length = 30, insertable = true, updatable = true)
	private String descripcionCorta;

	@OneToOne
	@JoinColumn(name = "ART_CMM_ObjetoImpuestoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
	private ControlMaestroMultiple objetoImpuesto;

	@Column(name = "ART_CMM_ObjetoImpuestoId")
	private Integer objetoImpuestoId;

	@Column(name = "ART_ClaveProductoSAT", length = 8, nullable = true, insertable = true, updatable = true)
	private String claveProductoSAT;

	@Column(name = "ART_IVA", nullable = true, insertable = true, updatable = true)
	private BigDecimal iva;

	@Column(name = "ART_IVAExento", nullable = true, insertable = true, updatable = true)
	private Boolean ivaExento;

	@Column(name = "ART_IEPS", nullable = true, insertable = true, updatable = true)
	private BigDecimal ieps;

	@Column(name = "ART_IEPSCuotaFija", nullable = true, insertable = true, updatable = true)
	private BigDecimal iepsCuotaFija;

	@Column(name = "ART_MultiploPedido", nullable = true, insertable = true, updatable = true)
	private BigDecimal multiploPedido;

	@Column(name = "ART_PermitirCambioAlmacen", nullable = false, insertable = true, updatable = true)
	private Boolean permitirCambioAlmacen;

	@Column(name = "ART_ArticuloParaVenta", nullable = false, insertable = true, updatable = true)
	private Boolean articuloParaVenta;

	@Column(name = "ART_MaximoAlmacen", nullable = true, insertable = true, updatable = true)
	private BigDecimal maximoAlmacen;

	@Column(name = "ART_MinimoAlmacen", nullable = true, insertable = true, updatable = true)
	private BigDecimal minimoAlmacen;

	@Column(name = "ART_PlaneacionTemporadas", nullable = false, insertable = true, updatable = true)
	private Boolean planeacionTemporadas;

	@OneToOne
	@JoinColumn(name = "ART_ARC_ImagenId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
	private Archivo imagen;

	@Column(name = "ART_ARC_ImagenId", nullable = true, insertable = true, updatable = true)
	private Integer imagenId;

	@OneToOne
	@JoinColumn(name = "ART_AFAM_FamiliaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "AFAM_FamiliaId")
	private ArticuloFamilia familia;

	@Column(name = "ART_AFAM_FamiliaId", nullable = false, insertable = true, updatable = true)
	private Integer familiaId;

	@OneToOne
	@JoinColumn(name = "ART_ACAT_CategoriaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ACAT_CategoriaId")
	private ArticuloCategoria categoria;

	@Column(name = "ART_ACAT_CategoriaId", nullable = true, insertable = true, updatable = true)
	private Integer categoriaId;

	@OneToOne
	@JoinColumn(name = "ART_ASC_SubcategoriaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ASC_SubcategoriaId")
	private ArticuloSubcategoria subcategoria;

	@Column(name = "ART_ASC_SubcategoriaId", nullable = true, insertable = true, updatable = true)
	private Integer subcategoriaId;

	@OneToOne
	@JoinColumn(name = "ART_CMM_Clasificacion1Id", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
	private ControlMaestroMultiple clasificacion1;

	@Column(name = "ART_CMM_Clasificacion1Id", nullable = true, insertable = true, updatable = true)
	private Integer clasificacion1Id;

	@OneToOne
	@JoinColumn(name = "ART_CMM_Clasificacion2Id", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
	private ControlMaestroMultiple clasificacion2;

	@Column(name = "ART_CMM_Clasificacion2Id", nullable = true, insertable = true, updatable = true)
	private Integer clasificacion2Id;

	@OneToOne
	@JoinColumn(name = "ART_CMM_Clasificacion3Id", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
	private ControlMaestroMultiple clasificacion3;

	@Column(name = "ART_CMM_Clasificacion3Id", nullable = true, insertable = true, updatable = true)
	private Integer clasificacion3Id;

	@OneToOne
	@JoinColumn(name = "ART_CMM_Clasificacion4Id", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
	private ControlMaestroMultiple clasificacion4;

	@Column(name = "ART_CMM_Clasificacion4Id", nullable = true, insertable = true, updatable = true)
	private Integer clasificacion4Id;

	@OneToOne
	@JoinColumn(name = "ART_CMM_Clasificacion5Id", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
	private ControlMaestroMultiple clasificacion5;

	@Column(name = "ART_CMM_Clasificacion5Id", nullable = true, insertable = true, updatable = true)
	private Integer clasificacion5Id;

	@OneToOne
	@JoinColumn(name = "ART_CMM_Clasificacion6Id", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
	private ControlMaestroMultiple clasificacion6;

	@Column(name = "ART_CMM_Clasificacion6Id", nullable = true, insertable = true, updatable = true)
	private Integer clasificacion6Id;

	@OneToOne
	@JoinColumn(name = "ART_ARTT_TipoArticuloId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ARTT_ArticuloTipoId")
	private ArticuloTipo tipoArticulo;

	@Column(name = "ART_ARTT_TipoArticuloId", nullable = false, insertable = true, updatable = true)
	private Integer tipoArticuloId;

	@OneToOne
	@JoinColumn(name = "ART_ARTST_ArticuloSubtipoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ARTST_ArticuloSubtipoId")
	private ArticuloSubtipo articuloSubtipo;

	@Column(name = "ART_ARTST_ArticuloSubtipoId", nullable = true, insertable = true, updatable = true)
	private Integer articuloSubtipoId;

	@OneToOne
	@JoinColumn(name = "ART_UM_UnidadMedidaInventarioId", nullable = true, insertable = false, updatable = false, referencedColumnName = "UM_UnidadMedidaId")
	private UnidadMedida unidadMedidaInventario;

	@Column(name = "ART_UM_UnidadMedidaInventarioId", nullable = false, insertable = true, updatable = true)
	private Integer unidadMedidaInventarioId;

	@OneToOne
	@JoinColumn(name = "ART_UM_UnidadMedidaConversionVentasId", nullable = true, insertable = false, updatable = false, referencedColumnName = "UM_UnidadMedidaId")
	private UnidadMedida unidadMedidaConversionVentas;

	@Column(name = "ART_UM_UnidadMedidaConversionVentasId", nullable = true, insertable = true, updatable = true)
	private Integer unidadMedidaConversionVentasId;

	@Column(name = "ART_FactorConversionVentas", nullable = true, insertable = true, updatable = true)
	private BigDecimal factorConversionVentas;

	@OneToOne
	@JoinColumn(name = "ART_UM_UnidadMedidaConversionComprasId", nullable = true, insertable = false, updatable = false, referencedColumnName = "UM_UnidadMedidaId")
	private UnidadMedida unidadMedidaConversionCompras;

	@Column(name = "ART_UM_UnidadMedidaConversionComprasId", nullable = true, insertable = true, updatable = true)
	private Integer unidadMedidaConversionComprasId;

	@Column(name = "ART_FactorConversionCompras", nullable = true, insertable = true, updatable = true)
	private BigDecimal factorConversionCompras;

	@OneToOne
	@JoinColumn(name = "ART_CMM_TipoCostoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
	private ControlMaestroMultiple tipoCosto;

	@Column(name = "ART_CMM_TipoCostoId", nullable = false, insertable = true, updatable = true)
	private Integer tipoCostoId;

	@Column(name = "ART_CostoUltimo", nullable = false, insertable = true, updatable = true)
	private BigDecimal costoUltimo;

	@Column(name = "ART_CostoPromedio", nullable = false, insertable = true, updatable = true)
	private BigDecimal costoPromedio;

	@Column(name = "ART_CostoEstandar", nullable = false, insertable = true, updatable = true)
	private BigDecimal costoEstandar;

	@Column(name = "ART_Activo", nullable = false, insertable = true, updatable = true)
	private Boolean activo;

	@Column(name = "ART_CuentaCompras", nullable = true, length = 18, insertable = true, updatable = true)
	private String cuentaCompras;

	@Column(name = "ART_Inventariable", nullable = false, insertable = true, updatable = true)
	private Boolean inventariable;

	@OneToOne
	@JoinColumn(name = "ART_PROGI_ProgramaIdiomaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PROGI_ProgramaIdiomaId")
	private ProgramaIdioma programaIdioma;

	@Column(name = "ART_PROGI_ProgramaIdiomaId", nullable = true, insertable = true, updatable = true)
	private Integer programaIdiomaId;

	@OneToOne
	@JoinColumn(name = "ART_CMM_TipoGrupoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
	private ControlMaestroMultiple tipoGrupo;

	@Column(name = "ART_CMM_TipoGrupoId", nullable = true, insertable = true, updatable = true)
	private Integer tipoGrupoId;


	@CreationTimestamp
	@Column(name = "ART_FechaCreacion", nullable = false, insertable = true, updatable = false)
	private Date fechaCreacion;

	@OneToOne
	@JoinColumn(name = "ART_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario creadoPor;

	@Column(name = "ART_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
	private Integer creadoPorId;

	@OneToOne
	@JoinColumn(name = "ART_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario modificadoPor;

	@Column(name = "ART_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
	private Integer modificadoPorId;

	@UpdateTimestamp
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
	@Column(name = "ART_FechaModificacion", nullable = true, insertable = false, updatable = true)
	private Date fechaModificacion;

	@ManyToMany
	@JoinTable(name = "ArticulosSucursales", joinColumns = {@JoinColumn(name = "ASUC_ART_ArticuloId")}, inverseJoinColumns = {@JoinColumn(name = "ASUC_SUC_SucursalId")})
	private Set<Sucursal> mostrarSucursales = new HashSet<>();

	@OneToOne
	@JoinColumn(name = "ART_CMM_IdiomaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
	private ControlMaestroMultiple idioma;

	@Column(name = "ART_CMM_IdiomaId", nullable = true, insertable = true, updatable = true)
	private Integer idiomaId;

	@OneToOne
	@JoinColumn(name = "ART_PROG_ProgramaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PROG_ProgramaId")
	private Programa programa;

	@Column(name = "ART_PROG_ProgramaId", nullable = true, insertable = true, updatable = true)
	private Integer programaId;

	@OneToOne
	@JoinColumn(name = "ART_PAMOD_ModalidadId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAMOD_ModalidadId")
	private PAModalidad modalidad;

	@Column(name = "ART_PAMOD_ModalidadId", nullable = true, insertable = true, updatable = true)
	private Integer modalidadId;

	@OneToOne
	@JoinColumn(name = "ART_CMM_EditorialId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
	private ControlMaestroMultiple editorial;

	@Column(name = "ART_CMM_EditorialId", nullable = true, insertable = true, updatable = true)
	private Integer editorialId;

	@Column(name = "ART_MarcoCertificacion", length = 50, insertable = true, updatable = true)
	private String marcoCertificacion;

	@Column(name = "ART_PedirCantidadPV", nullable = false, insertable = true, updatable = true)
	private Boolean pedirCantidadPV;

	@ManyToMany
	@JoinTable(name = "ArticulosSucursalesPV", joinColumns = {@JoinColumn(name = "ASUCPV_ART_ArticuloId")}, inverseJoinColumns = {@JoinColumn(name = "ASUCPV_SUC_SucursalId")})
	private Set<Sucursal> mostrarSucursalesPV = new HashSet<>();

	@OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
	@JoinColumn(name="OCD_ART_ArticuloId", referencedColumnName = "ART_ArticuloId", nullable = false, insertable = false, updatable = false)
	private List<OrdenCompraDetalle> detallesOC;

	@OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name="ARTC_ART_ArticuloId", referencedColumnName = "ART_ArticuloId", nullable = false, insertable = true, updatable = true)
	private List<ArticuloComponente> componentes;

	@Transient
	private String img64;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigoArticulo() {
		return codigoArticulo;
	}

	public void setCodigoArticulo(String codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}

	public String getNombreArticulo() {
		return nombreArticulo;
	}

	public void setNombreArticulo(String nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getCodigoAlterno() {
		return codigoAlterno;
	}

	public void setCodigoAlterno(String codigoAlterno) {
		this.codigoAlterno = codigoAlterno;
	}

	public String getNombreAlterno() {
		return nombreAlterno;
	}

	public void setNombreAlterno(String nombreAlterno) {
		this.nombreAlterno = nombreAlterno;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
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

	public String getClaveProductoSAT() {
		return claveProductoSAT;
	}

	public void setClaveProductoSAT(String claveProductoSAT) {
		this.claveProductoSAT = claveProductoSAT;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public Boolean getIvaExento() {
		return ivaExento;
	}

	public void setIvaExento(Boolean ivaExento) {
		this.ivaExento = ivaExento;
	}

	public BigDecimal getIeps() {
		return ieps;
	}

	public void setIeps(BigDecimal ieps) {
		this.ieps = ieps;
	}

	public BigDecimal getIepsCuotaFija() {
		return iepsCuotaFija;
	}

	public void setIepsCuotaFija(BigDecimal iepsCuotaFija) {
		this.iepsCuotaFija = iepsCuotaFija;
	}

	public BigDecimal getMultiploPedido() {
		return multiploPedido;
	}

	public void setMultiploPedido(BigDecimal multiploPedido) {
		this.multiploPedido = multiploPedido;
	}

	public Boolean getPermitirCambioAlmacen() {
		return permitirCambioAlmacen;
	}

	public void setPermitirCambioAlmacen(Boolean permitirCambioAlmacen) {
		this.permitirCambioAlmacen = permitirCambioAlmacen;
	}

	public BigDecimal getMaximoAlmacen() {
		return maximoAlmacen;
	}

	public void setMaximoAlmacen(BigDecimal maximoAlmacen) {
		this.maximoAlmacen = maximoAlmacen;
	}

	public BigDecimal getMinimoAlmacen() {
		return minimoAlmacen;
	}

	public void setMinimoAlmacen(BigDecimal minimoAlmacen) {
		this.minimoAlmacen = minimoAlmacen;
	}

	public Boolean getPlaneacionTemporadas() {
		return planeacionTemporadas;
	}

	public void setPlaneacionTemporadas(Boolean planeacionTemporadas) {
		this.planeacionTemporadas = planeacionTemporadas;
	}

	public Archivo getImagen() {
		return imagen;
	}

	public void setImagen(Archivo imagen) {
		this.imagen = imagen;
	}

	public Integer getImagenId() {
		return imagenId;
	}

	public void setImagenId(Integer imagenId) {
		this.imagenId = imagenId;
	}

	public ArticuloFamilia getFamilia() {
		return familia;
	}

	public void setFamilia(ArticuloFamilia familia) {
		this.familia = familia;
	}

	public Integer getFamiliaId() {
		return familiaId;
	}

	public void setFamiliaId(Integer familiaId) {
		this.familiaId = familiaId;
	}

	public ArticuloCategoria getCategoria() {
		return categoria;
	}

	public void setCategoria(ArticuloCategoria categoria) {
		this.categoria = categoria;
	}

	public Integer getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}

	public ArticuloSubcategoria getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(ArticuloSubcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}

	public Integer getSubcategoriaId() {
		return subcategoriaId;
	}

	public void setSubcategoriaId(Integer subcategoriaId) {
		this.subcategoriaId = subcategoriaId;
	}

	public ControlMaestroMultiple getClasificacion1() {
		return clasificacion1;
	}

	public void setClasificacion1(ControlMaestroMultiple clasificacion1) {
		this.clasificacion1 = clasificacion1;
	}

	public Integer getClasificacion1Id() {
		return clasificacion1Id;
	}

	public void setClasificacion1Id(Integer clasificacion1Id) {
		this.clasificacion1Id = clasificacion1Id;
	}

	public ControlMaestroMultiple getClasificacion2() {
		return clasificacion2;
	}

	public void setClasificacion2(ControlMaestroMultiple clasificacion2) {
		this.clasificacion2 = clasificacion2;
	}

	public Integer getClasificacion2Id() {
		return clasificacion2Id;
	}

	public void setClasificacion2Id(Integer clasificacion2Id) {
		this.clasificacion2Id = clasificacion2Id;
	}

	public ControlMaestroMultiple getClasificacion3() {
		return clasificacion3;
	}

	public void setClasificacion3(ControlMaestroMultiple clasificacion3) {
		this.clasificacion3 = clasificacion3;
	}

	public Integer getClasificacion3Id() {
		return clasificacion3Id;
	}

	public void setClasificacion3Id(Integer clasificacion3Id) {
		this.clasificacion3Id = clasificacion3Id;
	}

	public ControlMaestroMultiple getClasificacion4() {
		return clasificacion4;
	}

	public void setClasificacion4(ControlMaestroMultiple clasificacion4) {
		this.clasificacion4 = clasificacion4;
	}

	public Integer getClasificacion4Id() {
		return clasificacion4Id;
	}

	public void setClasificacion4Id(Integer clasificacion4Id) {
		this.clasificacion4Id = clasificacion4Id;
	}

	public ControlMaestroMultiple getClasificacion5() {
		return clasificacion5;
	}

	public void setClasificacion5(ControlMaestroMultiple clasificacion5) {
		this.clasificacion5 = clasificacion5;
	}

	public Integer getClasificacion5Id() {
		return clasificacion5Id;
	}

	public void setClasificacion5Id(Integer clasificacion5Id) {
		this.clasificacion5Id = clasificacion5Id;
	}

	public ControlMaestroMultiple getClasificacion6() {
		return clasificacion6;
	}

	public void setClasificacion6(ControlMaestroMultiple clasificacion6) {
		this.clasificacion6 = clasificacion6;
	}

	public Integer getClasificacion6Id() {
		return clasificacion6Id;
	}

	public void setClasificacion6Id(Integer clasificacion6Id) {
		this.clasificacion6Id = clasificacion6Id;
	}

	public ArticuloTipo getTipoArticulo() {
		return tipoArticulo;
	}

	public void setTipoArticulo(ArticuloTipo tipoArticulo) {
		this.tipoArticulo = tipoArticulo;
	}

	public Integer getTipoArticuloId() {
		return tipoArticuloId;
	}

	public void setTipoArticuloId(Integer tipoArticuloId) {
		this.tipoArticuloId = tipoArticuloId;
	}

	public ArticuloSubtipo getArticuloSubtipo() {
		return articuloSubtipo;
	}

	public void setArticuloSubtipo(ArticuloSubtipo articuloSubtipo) {
		this.articuloSubtipo = articuloSubtipo;
	}

	public Integer getArticuloSubtipoId() {
		return articuloSubtipoId;
	}

	public void setArticuloSubtipoId(Integer articuloSubtipoId) {
		this.articuloSubtipoId = articuloSubtipoId;
	}

	public UnidadMedida getUnidadMedidaInventario() {
		return unidadMedidaInventario;
	}

	public void setUnidadMedidaInventario(UnidadMedida unidadMedidaInventario) {
		this.unidadMedidaInventario = unidadMedidaInventario;
	}

	public Integer getUnidadMedidaInventarioId() {
		return unidadMedidaInventarioId;
	}

	public void setUnidadMedidaInventarioId(Integer unidadMedidaInventarioId) {
		this.unidadMedidaInventarioId = unidadMedidaInventarioId;
	}

	public UnidadMedida getUnidadMedidaConversionVentas() {
		return unidadMedidaConversionVentas;
	}

	public void setUnidadMedidaConversionVentas(UnidadMedida unidadMedidaConversionVentas) {
		this.unidadMedidaConversionVentas = unidadMedidaConversionVentas;
	}

	public Integer getUnidadMedidaConversionVentasId() {
		return unidadMedidaConversionVentasId;
	}

	public void setUnidadMedidaConversionVentasId(Integer unidadMedidaConversionVentasId) {
		this.unidadMedidaConversionVentasId = unidadMedidaConversionVentasId;
	}

	public BigDecimal getFactorConversionVentas() {
		return factorConversionVentas;
	}

	public void setFactorConversionVentas(BigDecimal factorConversionVentas) {
		this.factorConversionVentas = factorConversionVentas;
	}

	public UnidadMedida getUnidadMedidaConversionCompras() {
		return unidadMedidaConversionCompras;
	}

	public void setUnidadMedidaConversionCompras(UnidadMedida unidadMedidaConversionCompras) {
		this.unidadMedidaConversionCompras = unidadMedidaConversionCompras;
	}

	public Integer getUnidadMedidaConversionComprasId() {
		return unidadMedidaConversionComprasId;
	}

	public void setUnidadMedidaConversionComprasId(Integer unidadMedidaConversionComprasId) {
		this.unidadMedidaConversionComprasId = unidadMedidaConversionComprasId;
	}

	public BigDecimal getFactorConversionCompras() {
		return factorConversionCompras;
	}

	public void setFactorConversionCompras(BigDecimal factorConversionCompras) {
		this.factorConversionCompras = factorConversionCompras;
	}

	public ControlMaestroMultiple getTipoCosto() {
		return tipoCosto;
	}

	public void setTipoCosto(ControlMaestroMultiple tipoCosto) {
		this.tipoCosto = tipoCosto;
	}

	public Integer getTipoCostoId() {
		return tipoCostoId;
	}

	public void setTipoCostoId(Integer tipoCostoId) {
		this.tipoCostoId = tipoCostoId;
	}

	public BigDecimal getCostoUltimo() {
		return costoUltimo;
	}

	public void setCostoUltimo(BigDecimal costoUltimo) {
		this.costoUltimo = costoUltimo;
	}

	public BigDecimal getCostoPromedio() {
		return costoPromedio;
	}

	public void setCostoPromedio(BigDecimal costoPromedio) {
		this.costoPromedio = costoPromedio;
	}

	public BigDecimal getCostoEstandar() {
		return costoEstandar;
	}

	public void setCostoEstandar(BigDecimal costoEstandar) {
		this.costoEstandar = costoEstandar;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Usuario getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(Usuario creadoPor) {
		this.creadoPor = creadoPor;
	}

	public Integer getCreadoPorId() {
		return creadoPorId;
	}

	public void setCreadoPorId(Integer creadoPorId) {
		this.creadoPorId = creadoPorId;
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

	public Set<Sucursal> getMostrarSucursales() {
		return mostrarSucursales;
	}

	public void setMostrarSucursales(Set<Sucursal> mostrarSucursales) {
		this.mostrarSucursales = mostrarSucursales;
	}

	public Set<Sucursal> getMostrarSucursalesPV() {
		return mostrarSucursalesPV;
	}

	public void setMostrarSucursalesPV(Set<Sucursal> mostrarSucursalesPV) {
		this.mostrarSucursalesPV = mostrarSucursalesPV;
	}

	public String getImg64() {
		return img64;
	}

	public void setImg64(String img64) {
		this.img64 = img64;
	}

	public List<OrdenCompraDetalle> getDetallesOC() {
		return detallesOC;
	}

	public void setDetallesOC(List<OrdenCompraDetalle> detallesOC) {
		this.detallesOC = detallesOC;
	}

	public String getCuentaCompras() {
		return cuentaCompras;
	}

	public void setCuentaCompras(String cuentaCompras) {
		this.cuentaCompras = cuentaCompras;
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

	public Programa getPrograma() {
		return programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	public Integer getProgramaId() {
		return programaId;
	}

	public void setProgramaId(Integer programaId) {
		this.programaId = programaId;
	}

	public ControlMaestroMultiple getEditorial() {
		return editorial;
	}

	public void setEditorial(ControlMaestroMultiple editorial) {
		this.editorial = editorial;
	}

	public Integer getEditorialId() {
		return editorialId;
	}

	public void setEditorialId(Integer editorialId) {
		this.editorialId = editorialId;
	}

	public String getMarcoCertificacion() {
		return marcoCertificacion;
	}

	public void setMarcoCertificacion(String marcoCertificacion) {
		this.marcoCertificacion = marcoCertificacion;
	}

	public Boolean getInventariable() {
		return inventariable;
	}

	public void setInventariable(Boolean inventariable) {
		this.inventariable = inventariable;
	}

	public List<ArticuloComponente> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<ArticuloComponente> componentes) {
		this.componentes = componentes;
	}

	public Boolean getArticuloParaVenta() {
		return articuloParaVenta;
	}

	public void setArticuloParaVenta(Boolean articuloParaVenta) {
		this.articuloParaVenta = articuloParaVenta;
	}

	public ProgramaIdioma getProgramaIdioma() {
		return programaIdioma;
	}

	public void setProgramaIdioma(ProgramaIdioma programaIdioma) {
		this.programaIdioma = programaIdioma;
	}

	public Integer getProgramaIdiomaId() {
		return programaIdiomaId;
	}

	public void setProgramaIdiomaId(Integer programaIdiomaId) {
		this.programaIdiomaId = programaIdiomaId;
	}

	public PAModalidad getModalidad() {
		return modalidad;
	}

	public void setModalidad(PAModalidad modalidad) {
		this.modalidad = modalidad;
	}

	public Integer getModalidadId() {
		return modalidadId;
	}

	public void setModalidadId(Integer modalidadId) {
		this.modalidadId = modalidadId;
	}

	public ControlMaestroMultiple getTipoGrupo() {
		return tipoGrupo;
	}

	public void setTipoGrupo(ControlMaestroMultiple tipoGrupo) {
		this.tipoGrupo = tipoGrupo;
	}

	public Integer getTipoGrupoId() {
		return tipoGrupoId;
	}

	public void setTipoGrupoId(Integer tipoGrupoId) {
		this.tipoGrupoId = tipoGrupoId;
	}

	public Boolean getPedirCantidadPV() {
		return pedirCantidadPV;
	}

	public void setPedirCantidadPV(Boolean pedirCantidadPV) {
		this.pedirCantidadPV = pedirCantidadPV;
	}
}
