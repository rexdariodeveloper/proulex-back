package com.pixvs.main.models;

import com.pixvs.spring.models.*;

import javax.persistence.*;

@Entity
@Table(name = "DatosFacturacion")
public class DatosFacturacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DF_DatosFacturacionId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "DF_CMM_TipoPersonaId", nullable = false)
    private int tipoPersonaId;

    @OneToOne
    @JoinColumn(name = "DF_CMM_TipoPersonaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoPersona;

    @Column(name = "DF_RFC", nullable = false)
    private String rfc;

    @Column(name = "DF_Nombre")
    private String nombre;

    @Column(name = "DF_PrimerApellido")
    private String primerApellido;

    @Column(name = "DF_SegundoApellido")
    private String segundoApellido;

    @Column(name = "DF_RazonSocial")
    private String razonSocial;

    @Column(name = "DF_RegistroIdentidadFiscal")
    private String registroIdentidadFiscal;

    @Column(name = "DF_RF_RegimenFiscalId")
    private Integer regimenFiscalId;

    @OneToOne
    @JoinColumn(name = "DF_RF_RegimenFiscalId", insertable = false, updatable = false, referencedColumnName = "RF_RegimenFiscalId")
    private SATRegimenFiscal regimenFiscal;

    @Column(name = "DF_Calle")
    private String calle;

    @Column(name = "DF_NumeroExterior")
    private String numeroExterior;

    @Column(name = "DF_NumeroInterior")
    private String numeroInterior;

    @Column(name = "DF_Colonia")
    private String colonia;

    @Column(name = "DF_CP")
    private String cp;

    @Column(name = "DF_PAI_PaisId")
    private Integer paisId;

    @OneToOne
    @JoinColumn(name = "DF_PAI_PaisId", insertable = false, updatable = false, referencedColumnName = "PAI_PaisId")
    private Pais pais;

    @Column(name = "DF_EST_EstadoId")
    private Integer estadoId;

    @OneToOne
    @JoinColumn(name = "DF_EST_EstadoId", insertable = false, updatable = false, referencedColumnName = "EST_EstadoId")
    private Estado estado;

    @Column(name = "DF_MUN_MunicipioId")
    private Integer municipioId;

    @OneToOne
    @JoinColumn(name = "DF_MUN_MunicipioId", insertable = false, updatable = false, referencedColumnName = "MUN_MunicipioId")
    private Municipio municipio;

    @Column(name = "DF_Ciudad")
    private String ciudad;

    @Column(name = "DF_CorreoElectronico")
    private String correoElectronico;

    @Column(name = "DF_TelefonoFijo")
    private String telefonoFijo;

    @Column(name = "DF_TelefonoMovil")
    private String telefonoMovil;

    @Column(name = "DF_TelefonoTrabajo")
    private String telefonoTrabajo;

    @Column(name = "DF_TelefonoTrabajoExtension")
    private String telefonoTrabajoExtension;

    @Column(name = "DF_TelefonoMensajeriaInstantanea")
    private String telefonoMensajeriaInstantanea;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTipoPersonaId() {
        return tipoPersonaId;
    }

    public void setTipoPersonaId(int tipoPersonaId) {
        this.tipoPersonaId = tipoPersonaId;
    }

    public ControlMaestroMultiple getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(ControlMaestroMultiple tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRegistroIdentidadFiscal() {
        return registroIdentidadFiscal;
    }

    public void setRegistroIdentidadFiscal(String registroIdentidadFiscal) {
        this.registroIdentidadFiscal = registroIdentidadFiscal;
    }

    public Integer getRegimenFiscalId() {
        return regimenFiscalId;
    }

    public void setRegimenFiscalId(Integer regimenFiscalId) {
        this.regimenFiscalId = regimenFiscalId;
    }

    public SATRegimenFiscal getRegimenFiscal() {
        return regimenFiscal;
    }

    public void setRegimenFiscal(SATRegimenFiscal regimenFiscal) {
        this.regimenFiscal = regimenFiscal;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public Integer getPaisId() {
        return paisId;
    }

    public void setPaisId(Integer paisId) {
        this.paisId = paisId;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Integer getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Integer getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Integer municipioId) {
        this.municipioId = municipioId;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getTelefonoTrabajo() {
        return telefonoTrabajo;
    }

    public void setTelefonoTrabajo(String telefonoTrabajo) {
        this.telefonoTrabajo = telefonoTrabajo;
    }

    public String getTelefonoTrabajoExtension() {
        return telefonoTrabajoExtension;
    }

    public void setTelefonoTrabajoExtension(String telefonoTrabajoExtension) {
        this.telefonoTrabajoExtension = telefonoTrabajoExtension;
    }

    public String getTelefonoMensajeriaInstantanea() {
        return telefonoMensajeriaInstantanea;
    }

    public void setTelefonoMensajeriaInstantanea(String telefonoMensajeriaInstantanea) {
        this.telefonoMensajeriaInstantanea = telefonoMensajeriaInstantanea;
    }
}