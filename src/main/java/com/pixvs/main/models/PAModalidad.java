package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.Archivo;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PAModalidades")
public class PAModalidad {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "PAMOD_ModalidadId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column( name="PAMOD_Codigo", length = 10,  nullable=false, insertable=true, updatable=true)
    private String codigo;

    @Column( name="PAMOD_Nombre", length = 255, nullable=false, insertable=true, updatable=true)
    private String nombre;

    @Column( name="PAMOD_HorasPorDia", nullable=false, insertable=true, updatable=true)
    private BigDecimal horasPorDia;

    @Column( name="PAMOD_DiasPorSemana", nullable=false, insertable=true, updatable=true)
    private Integer diasPorSemana;

    @Column( name="PAMOD_Lunes", insertable=true, updatable=true)
    private Boolean lunes;
    @Column( name="PAMOD_Martes", insertable=true, updatable=true)
    private Boolean martes;
    @Column( name="PAMOD_Miercoles", insertable=true, updatable=true)
    private Boolean miercoles;
    @Column( name="PAMOD_Jueves", insertable=true, updatable=true)
    private Boolean jueves;
    @Column( name="PAMOD_Viernes", insertable=true, updatable=true)
    private Boolean viernes;
    @Column( name="PAMOD_Sabado",  insertable=true, updatable=true)
    private Boolean sabado;
    @Column( name="PAMOD_Domingo", insertable=true, updatable=true)
    private Boolean domingo;

    @Column( name="PAMOD_Color", nullable=true, insertable=false, updatable=false)
    private String color;

    @Column( name="PAMOD_Activo", nullable=false, insertable=true, updatable=true)
    private Boolean activo;

    @OneToOne
    @JoinColumn(name = "PAMOD_ARC_ImagenId", insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo imagen;

    @Column(name = "PAMOD_ARC_ImagenId", updatable = false, insertable = false)
    private Integer imagenId;

    @Column(name = "PAMOD_CentroPagosId", updatable = false, insertable = false)
    private Integer centroPagosId;

    @OneToOne
    @JoinColumn(name = "PAMOD_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column( name="PAMOD_USU_CreadoPorId", nullable=false, insertable=true, updatable=false)
    private Integer creadoPorId;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column( name="PAMOD_FechaCreacion", nullable=false, insertable=true, updatable=false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "PAMOD_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column( name="PAMOD_USU_ModificadoPorId", nullable=true, insertable=false, updatable=true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column( name="PAMOD_FechaModificacion", nullable=true, insertable=false, updatable=true)
    private Date fechaModificacion;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "PAMODH_PAMOD_ModalidadId", referencedColumnName = "PAMOD_ModalidadId", nullable = false, insertable = true, updatable = true)
    private List<PAModalidadHorario> horarios;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BigDecimal getHorasPorDia() {
        return horasPorDia;
    }

    public void setHorasPorDia(BigDecimal horasPorDia) {
        this.horasPorDia = horasPorDia;
    }

    public Integer getDiasPorSemana() {
        return diasPorSemana;
    }

    public void setDiasPorSemana(Integer diasPorSemana) {
        this.diasPorSemana = diasPorSemana;
    }

    public Boolean getLunes() {
        return lunes;
    }

    public void setLunes(Boolean lunes) {
        this.lunes = lunes;
    }

    public Boolean getMartes() {
        return martes;
    }

    public void setMartes(Boolean martes) {
        this.martes = martes;
    }

    public Boolean getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(Boolean miercoles) {
        this.miercoles = miercoles;
    }

    public Boolean getJueves() {
        return jueves;
    }

    public void setJueves(Boolean jueves) {
        this.jueves = jueves;
    }

    public Boolean getViernes() {
        return viernes;
    }

    public void setViernes(Boolean viernes) {
        this.viernes = viernes;
    }

    public Boolean getSabado() {
        return sabado;
    }

    public void setSabado(Boolean sabado) {
        this.sabado = sabado;
    }

    public Boolean getDomingo() {
        return domingo;
    }

    public void setDomingo(Boolean domingo) {
        this.domingo = domingo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

    public List<PAModalidadHorario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<PAModalidadHorario> horarios) {
        this.horarios = horarios;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public Integer getCentroPagosId() {
        return centroPagosId;
    }

    public void setCentroPagosId(Integer centroPagosId) {
        this.centroPagosId = centroPagosId;
    }
}
