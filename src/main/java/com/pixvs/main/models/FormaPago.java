package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.Archivo;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "FormasPago")
public class FormaPago {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "FP_FormaPagoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column( name="FP_Codigo", length = 10,  nullable=false, insertable=true, updatable=true)
    private String codigo;

    @Column( name="FP_Nombre", length = 255, nullable=false, insertable=true, updatable=true)
    private String nombre;

    @OneToOne
    @JoinColumn(name = "FP_ARC_ImagenId", referencedColumnName = "ARC_ArchivoId", nullable = true, insertable = false, updatable = false)
    private Archivo archivo;

    @Column( name="FP_ARC_ImagenId", nullable=true, insertable=true, updatable=true)
    private Integer archivoId;

    @Column( name="FP_Activo", nullable=false, insertable=true, updatable=true)
    private Boolean activo;

    @OneToOne
    @JoinColumn(name = "FP_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column( name="FP_USU_CreadoPorId", nullable=false, insertable=true, updatable=false)
    private Integer creadoPorId;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column( name="FP_FechaCreacion", nullable=false, insertable=true, updatable=false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "FP_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column( name="FP_USU_ModificadoPorId", nullable=true, insertable=false, updatable=true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column( name="FP_FechaModificacion", nullable=true, insertable=false, updatable=true)
    private Date fechaModificacion;

    @Transient
    private String img64;



    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Archivo getArchivo() { return archivo; }
    public void setArchivo(Archivo archivo) { this.archivo = archivo; }

    public Integer getArchivoId() { return archivoId; }
    public void setArchivoId(Integer archivoId) { this.archivoId = archivoId; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public Usuario getCreadoPor() { return creadoPor; }
    public void setCreadoPor(Usuario creadoPor) { this.creadoPor = creadoPor; }

    public Integer getCreadoPorId() { return creadoPorId; }
    public void setCreadoPorId(Integer creadoPorId) { this.creadoPorId = creadoPorId; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Usuario getModificadoPor() { return modificadoPor; }
    public void setModificadoPor(Usuario modificadoPor) { this.modificadoPor = modificadoPor; }

    public Integer getModificadoPorId() { return modificadoPorId; }
    public void setModificadoPorId(Integer modificadoPorId) { this.modificadoPorId = modificadoPorId; }

    public Date getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(Date fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    public String getImg64() { return img64; }
    public void setImg64(String img64) { this.img64 = img64; }
}
