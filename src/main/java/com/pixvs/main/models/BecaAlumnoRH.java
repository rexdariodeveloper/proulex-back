package com.pixvs.main.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "BECAS_ALUMNOS_RH")
public class BecaAlumnoRH {

    @Id
    @Column(name = "ID")
    private Integer ID;

    @Column(name = "CODIGOPLX")
    private String CODIGOPLX;

    @Column(name = "CODIGOBECAUDG")
    private String CODIGOBECAUDG;

    @Column(name = "CODIGOEMPLEADOUDG")
    private String CODIGOEMPLEADOUDG;

    @Column(name = "PATERNO")
    private String PATERNO;

    @Column(name = "MATERNO")
    private String MATERNO;

    @Column(name = "NOMBRE")
    private String NOMBRE;

    @Column(name = "DESCUENTOUDG")
    private Integer DESCUENTOUDG;

    @Column(name = "SEDEUDG")
    private String SEDEUDG;

    @Column(name = "NIVELUDG")
    private String NIVELUDG;

    @Column(name = "HORARIOUDG")
    private String HORARIOUDG;

    @Column(name = "FECHAALTABECAUDG")
    private Date FECHAALTABECAUDG;

    @Column(name = "HORAALTABECAUDG")
    private String HORAALTABECAUDG;

    @Column(name = "CODCURUDG")
    private String CODCURUDG;

    @Column(name = "PARENTESCOUDG")
    private String PARENTESCOUDG;

    @Column(name = "FIRMADIGITALUDG")
    private String FIRMADIGITALUDG;

    @Column(name = "ESTATUSUDG")
    private String ESTATUSUDG;

    @Column(name = "CODIGOESTATUSUDG")
    private String CODIGOESTATUSUDG;

    @Column(name = "SEDEPLX")
    private String SEDEPLX;

    @Column(name = "NIVELPLX")
    private String NIVELPLX;

    @Column(name = "HORARIOPLX")
    private String HORARIOPLX;

    @Column(name = "GRUPOPLX")
    private String GRUPOPLX;

    @Column(name = "CODCURPLX")
    private String CODCURPLX;

    @Column(name = "FECHAINIPLX")
    private Date FECHAINIPLX;

    @Column(name = "FECHAFINPLX")
    private Date FECHAFINPLX;

    @Column(name = "FECHAAPLICACIONPLX")
    private Date FECHAAPLICACIONPLX;

    @Column(name = "FECHAEXPIRACIONBECAUDG")
    private Date FECHAEXPIRACIONBECAUDG;

    @Column(name = "FOLIOSIAPPLX")
    private String FOLIOSIAPPLX;

    @Column(name = "CALPLX")
    private String CALPLX;

    @Column(name = "FECHACALPLX")
    private Date FECHACALPLX;

    @Column(name = "STATUSPLX")
    private String STATUSPLX;

    @Column(name = "CODIGOESTATUSPLX")
    private String CODIGOESTATUSPLX;

    @Column(name = "NOMBREDESCUENTOUDG")
    private String NOMBREDESCUENTOUDG;

    @Column(name = "ASISTENCIA")
    private String ASISTENCIA;

    @Column(name = "DIASEMANA")
    private String DIASEMANA;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getCODIGOPLX() {
        return CODIGOPLX;
    }

    public void setCODIGOPLX(String CODIGOPLX) {
        this.CODIGOPLX = CODIGOPLX;
    }

    public String getCODIGOBECAUDG() {
        return CODIGOBECAUDG;
    }

    public void setCODIGOBECAUDG(String CODIGOBECAUDG) {
        this.CODIGOBECAUDG = CODIGOBECAUDG;
    }

    public String getCODIGOEMPLEADOUDG() {
        return CODIGOEMPLEADOUDG;
    }

    public void setCODIGOEMPLEADOUDG(String CODIGOEMPLEADOUDG) {
        this.CODIGOEMPLEADOUDG = CODIGOEMPLEADOUDG;
    }

    public String getPATERNO() {
        return PATERNO;
    }

    public void setPATERNO(String PATERNO) {
        this.PATERNO = PATERNO;
    }

    public String getMATERNO() {
        return MATERNO;
    }

    public void setMATERNO(String MATERNO) {
        this.MATERNO = MATERNO;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public Integer getDESCUENTOUDG() {
        return DESCUENTOUDG;
    }

    public void setDESCUENTOUDG(Integer DESCUENTOUDG) {
        this.DESCUENTOUDG = DESCUENTOUDG;
    }

    public String getSEDEUDG() {
        return SEDEUDG;
    }

    public void setSEDEUDG(String SEDEUDG) {
        this.SEDEUDG = SEDEUDG;
    }

    public String getNIVELUDG() {
        return NIVELUDG;
    }

    public void setNIVELUDG(String NIVELUDG) {
        this.NIVELUDG = NIVELUDG;
    }

    public String getHORARIOUDG() {
        return HORARIOUDG;
    }

    public void setHORARIOUDG(String HORARIOUDG) {
        this.HORARIOUDG = HORARIOUDG;
    }

    public Date getFECHAALTABECAUDG() {
        return FECHAALTABECAUDG;
    }

    public void setFECHAALTABECAUDG(Date FECHAALTABECAUDG) {
        this.FECHAALTABECAUDG = FECHAALTABECAUDG;
    }

    public String getHORAALTABECAUDG() {
        return HORAALTABECAUDG;
    }

    public void setHORAALTABECAUDG(String HORAALTABECAUDG) {
        this.HORAALTABECAUDG = HORAALTABECAUDG;
    }

    public String getCODCURUDG() {
        return CODCURUDG;
    }

    public void setCODCURUDG(String CODCURUDG) {
        this.CODCURUDG = CODCURUDG;
    }

    public String getPARENTESCOUDG() {
        return PARENTESCOUDG;
    }

    public void setPARENTESCOUDG(String PARENTESCOUDG) {
        this.PARENTESCOUDG = PARENTESCOUDG;
    }

    public String getFIRMADIGITALUDG() {
        return FIRMADIGITALUDG;
    }

    public void setFIRMADIGITALUDG(String FIRMADIGITALUDG) {
        this.FIRMADIGITALUDG = FIRMADIGITALUDG;
    }

    public String getESTATUSUDG() {
        return ESTATUSUDG;
    }

    public void setESTATUSUDG(String ESTATUSUDG) {
        this.ESTATUSUDG = ESTATUSUDG;
    }

    public String getCODIGOESTATUSUDG() {
        return CODIGOESTATUSUDG;
    }

    public void setCODIGOESTATUSUDG(String CODIGOESTATUSUDG) {
        this.CODIGOESTATUSUDG = CODIGOESTATUSUDG;
    }

    public String getSEDEPLX() {
        return SEDEPLX;
    }

    public void setSEDEPLX(String SEDEPLX) {
        this.SEDEPLX = SEDEPLX;
    }

    public String getNIVELPLX() {
        return NIVELPLX;
    }

    public void setNIVELPLX(String NIVELPLX) {
        this.NIVELPLX = NIVELPLX;
    }

    public String getHORARIOPLX() {
        return HORARIOPLX;
    }

    public void setHORARIOPLX(String HORARIOPLX) {
        this.HORARIOPLX = HORARIOPLX;
    }

    public String getGRUPOPLX() {
        return GRUPOPLX;
    }

    public void setGRUPOPLX(String GRUPOPLX) {
        this.GRUPOPLX = GRUPOPLX;
    }

    public String getCODCURPLX() {
        return CODCURPLX;
    }

    public void setCODCURPLX(String CODCURPLX) {
        this.CODCURPLX = CODCURPLX;
    }

    public Date getFECHAINIPLX() {
        return FECHAINIPLX;
    }

    public void setFECHAINIPLX(Date FECHAINIPLX) {
        this.FECHAINIPLX = FECHAINIPLX;
    }

    public Date getFECHAFINPLX() {
        return FECHAFINPLX;
    }

    public void setFECHAFINPLX(Date FECHAFINPLX) {
        this.FECHAFINPLX = FECHAFINPLX;
    }

    public Date getFECHAAPLICACIONPLX() {
        return FECHAAPLICACIONPLX;
    }

    public void setFECHAAPLICACIONPLX(Date FECHAAPLICACIONPLX) {
        this.FECHAAPLICACIONPLX = FECHAAPLICACIONPLX;
    }

    public Date getFECHAEXPIRACIONBECAUDG() {
        return FECHAEXPIRACIONBECAUDG;
    }

    public void setFECHAEXPIRACIONBECAUDG(Date FECHAEXPIRACIONBECAUDG) {
        this.FECHAEXPIRACIONBECAUDG = FECHAEXPIRACIONBECAUDG;
    }

    public String getFOLIOSIAPPLX() {
        return FOLIOSIAPPLX;
    }

    public void setFOLIOSIAPPLX(String FOLIOSIAPPLX) {
        this.FOLIOSIAPPLX = FOLIOSIAPPLX;
    }

    public String getCALPLX() {
        return CALPLX;
    }

    public void setCALPLX(String CALPLX) {
        this.CALPLX = CALPLX;
    }

    public Date getFECHACALPLX() {
        return FECHACALPLX;
    }

    public void setFECHACALPLX(Date FECHACALPLX) {
        this.FECHACALPLX = FECHACALPLX;
    }

    public String getSTATUSPLX() {
        return STATUSPLX;
    }

    public void setSTATUSPLX(String STATUSPLX) {
        this.STATUSPLX = STATUSPLX;
    }

    public String getCODIGOESTATUSPLX() {
        return CODIGOESTATUSPLX;
    }

    public void setCODIGOESTATUSPLX(String CODIGOESTATUSPLX) {
        this.CODIGOESTATUSPLX = CODIGOESTATUSPLX;
    }

    public String getNOMBREDESCUENTOUDG() {
        return NOMBREDESCUENTOUDG;
    }

    public void setNOMBREDESCUENTOUDG(String NOMBREDESCUENTOUDG) {
        this.NOMBREDESCUENTOUDG = NOMBREDESCUENTOUDG;
    }

    public String getASISTENCIA() {
        return ASISTENCIA;
    }

    public void setASISTENCIA(String ASISTENCIA) {
        this.ASISTENCIA = ASISTENCIA;
    }

    public String getDIASEMANA() {
        return DIASEMANA;
    }

    public void setDIASEMANA(String DIASEMANA) {
        this.DIASEMANA = DIASEMANA;
    }
}