/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author User
 */
@Entity
@Table(name = "reporte_sonometro")
public class ReporteSonometro implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    @Basic(optional = false)
    @Column(name = "NIT_CDA")
    private String nitCda;
    @Basic(optional = false)
    @Column(name = "NOMBRE_CDA")
    private String nombreCda;
    @Basic(optional = false)
    @Column(name = "NO_CDA")
    private String noCda;
    @Basic(optional = false)
    @Column(name = "DIRECCION_CDA")
    private String direccionCda;
    @Basic(optional = false)
    @Column(name = "MARCA_SONOMETRO")
    private String marcaSonometro;
    @Basic(optional = false)
    @Column(name = "MODELO_SONOMETRO")
    private String modeloSonometro;
    @Basic(optional = false)
    @Column(name = "SERIE_SONOMETRO")
    private String serieSonometro;
    @Basic(optional = false)
    @Column(name = "TIPO_SONOMETRO")
    private String tipoSonometro;
    @Basic(optional = false)
    @Column(name = "FECHA_PRUEBA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPrueba;
    @Id
    @Basic(optional = false)
    @Column(name = "PLACA_VEHICULO")
    private String placaVehiculo;
    @Basic(optional = false)
    @Column(name = "TIPOVEHICULO")
    private String tipoVehiculo;
    @Basic(optional = false)
    @Column(name = "MODELO")
    private Integer modeloVehiculo;
    @Basic(optional = false)
    @Column(name = "TIPO_CICLO")
    private String tipoCiclo;
    @Basic(optional = false)
    @Column(name = "CICLINDRAJE")
    private Integer cilindraje;
    @Basic(optional = false)
    @Column(name = "RESULTADO_ESCAPE")
    private String resultadoEscape;

    @Override
    public String toString() {
        return "ReporteSonometro{" + "nombreCda=" + nombreCda + ", noCda=" + noCda + ", direccionCda=" + direccionCda + ", marcaSonometro=" + marcaSonometro + ", modeloSonometro=" + modeloSonometro + ", serieSonometro=" + serieSonometro + ", tipoSonometro=" + tipoSonometro + ", fechaPrueba=" + fechaPrueba + ", placaVehiculo=" + placaVehiculo + ", tipoVehiculo=" + tipoVehiculo + ", modeloVehiculo=" + modeloVehiculo + ", tipoCiclo=" + tipoCiclo + ", cilindraje=" + cilindraje + ", resultadoEscape=" + resultadoEscape + '}';
    }

    
    
    
    
    /**
     * @return the nombreCda
     */
    public String getNombreCda() {
        return nombreCda;
    }

    /**
     * @param nombreCda the nombreCda to set
     */
    public void setNombreCda(String nombreCda) {
        this.nombreCda = nombreCda;
    }

    /**
     * @return the noCda
     */
    public String getNoCda() {
        return noCda;
    }

    /**
     * @param noCda the noCda to set
     */
    public void setNoCda(String noCda) {
        this.noCda = noCda;
    }

    /**
     * @return the direccionCda
     */
    public String getDireccionCda() {
        return direccionCda;
    }

    /**
     * @param direccionCda the direccionCda to set
     */
    public void setDireccionCda(String direccionCda) {
        this.direccionCda = direccionCda;
    }

    /**
     * @return the marcaSonometro
     */
    public String getMarcaSonometro() {
        return marcaSonometro;
    }

    /**
     * @param marcaSonometro the marcaSonometro to set
     */
    public void setMarcaSonometro(String marcaSonometro) {
        this.marcaSonometro = marcaSonometro;
    }

    /**
     * @return the modeloSonometro
     */
    public String getModeloSonometro() {
        return modeloSonometro;
    }

    /**
     * @param modeloSonometro the modeloSonometro to set
     */
    public void setModeloSonometro(String modeloSonometro) {
        this.modeloSonometro = modeloSonometro;
    }

    /**
     * @return the serieSonometro
     */
    public String getSerieSonometro() {
        return serieSonometro;
    }

    /**
     * @param serieSonometro the serieSonometro to set
     */
    public void setSerieSonometro(String serieSonometro) {
        this.serieSonometro = serieSonometro;
    }

    /**
     * @return the tipoSonometro
     */
    public String getTipoSonometro() {
        return tipoSonometro;
    }

    /**
     * @param tipoSonometro the tipoSonometro to set
     */
    public void setTipoSonometro(String tipoSonometro) {
        this.tipoSonometro = tipoSonometro;
    }

    /**
     * @return the fechaPrueba
     */
    public Date getFechaPrueba() {
        return fechaPrueba;
    }

    /**
     * @param fechaPrueba the fechaPrueba to set
     */
    public void setFechaPrueba(Date fechaPrueba) {
        this.fechaPrueba = fechaPrueba;
    }

    /**
     * @return the placaVehiculo
     */
    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    /**
     * @param placaVehiculo the placaVehiculo to set
     */
    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    /**
     * @return the tipoVehiculo
     */
    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    /**
     * @param tipoVehiculo the tipoVehiculo to set
     */
    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    /**
     * @return the modeloVehiculo
     */
    public Integer getModeloVehiculo() {
        return modeloVehiculo;
    }

    /**
     * @param modeloVehiculo the modeloVehiculo to set
     */
    public void setModeloVehiculo(Integer modeloVehiculo) {
        this.modeloVehiculo = modeloVehiculo;
    }

    /**
     * @return the tipoCiclo
     */
    public String getTipoCiclo() {
        return tipoCiclo;
    }

    /**
     * @param tipoCiclo the tipoCiclo to set
     */
    public void setTipoCiclo(String tipoCiclo) {
        this.tipoCiclo = tipoCiclo;
    }

    /**
     * @return the cilindraje
     */
    public Integer getCilindraje() {
        return cilindraje;
    }

    /**
     * @param cilindraje the cilindraje to set
     */
    public void setCilindraje(Integer cilindraje) {
        this.cilindraje = cilindraje;
    }

    /**
     * @return the resultadoEscape
     */
    public String getResultadoEscape() {
        return resultadoEscape;
    }

    /**
     * @param resultadoEscape the resultadoEscape to set
     */
    public void setResultadoEscape(String resultadoEscape) {
        this.resultadoEscape = resultadoEscape;
    }

    /**
     * @return the nitCda
     */
    public String getNitCda() {
        return nitCda;
    }

    /**
     * @param nitCda the nitCda to set
     */
    public void setNitCda(String nitCda) {
        this.nitCda = nitCda;
    }

}
