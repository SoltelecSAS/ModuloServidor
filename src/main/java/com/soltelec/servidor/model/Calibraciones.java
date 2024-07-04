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
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GerenciaDesarrollo
 */
@Entity
@Table(name = "calibraciones")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "id_tipo_calibracion", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue(value = "3")
@NamedQueries({
    @NamedQuery(name = "Calibraciones.findAllCalibracionesBySerialId", query = "SELECT c FROM Calibraciones c WHERE c.equipo.idEquipo = :equipo and c.tipoCalibracion.tipoCalibracion = 2")
})
public class Calibraciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CALIBRATION")
    private Integer calibration;
    @Basic(optional = false)
    @Column(name = "CURDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "VALOR0")
    private String valor0;
    @Column(name = "VALOR1")
    private String valor1;
    @Column(name = "VALOR2")
    private String valor2;
    @Column(name = "VALOR3")
    private String valor3;
    @Column(name = "VALOR4")
    private String valor4;
    @Column(name = "VALOR5")
    private String valor5;
    @Column(name = "VALOR6")
    private String valor6;
    @Column(name = "VALOR7")
    private String valor7;

    public String getValor7() {
        return valor7;
    }

    public void setValor7(String valor7) {
        this.valor7 = valor7;
    }
    
    

    public String getValor0() {
        return valor0;
    }

    public void setValor0(String valor0) {
        this.valor0 = valor0;
    }

    
    @Basic(optional = false)
    @Column(name = "aprobada")
    private boolean aprobada;
    @JoinColumn(name = "id_tipo_calibracion", referencedColumnName = "id_tipo_calibracion")
    @ManyToOne
    private TipoCalibracion tipoCalibracion;
    @JoinColumn(name = "GEUSER", referencedColumnName = "GEUSER")
    @ManyToOne(optional = false)
    private Usuarios usuario;
    @JoinColumn(name = "id_equipo", referencedColumnName = "id_equipo")
    @ManyToOne(optional = false)
    private Equipos equipo;

    public Calibraciones() {
    }

    public Calibraciones(Integer calibration) {
        this.calibration = calibration;
    }

    public Calibraciones(Integer calibration, Date curdate, boolean aprobada) {
        this.calibration = calibration;
        this.fecha = curdate;
        this.aprobada = aprobada;
    }

    public Integer getCalibration() {
        return calibration;
    }

    public void setCalibration(Integer calibration) {
        this.calibration = calibration;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isAprobada() {
        return aprobada;
    }

    public void setAprobada(boolean aprobada) {
        this.aprobada = aprobada;
    }

    public TipoCalibracion getTipoCalibracion() {
        return tipoCalibracion;
    }

    public void setTipoCalibracion(TipoCalibracion tipoCalibracion) {
        this.tipoCalibracion = tipoCalibracion;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Equipos getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipos equipo) {
        this.equipo = equipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calibration != null ? calibration.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calibraciones)) {
            return false;
        }
        Calibraciones other = (Calibraciones) object;
        return (this.calibration != null || other.calibration == null) && (this.calibration == null || this.calibration.equals(other.calibration));
    }

    @Override
    public String toString() {
        return "com.soltelec.model.Calibraciones[ calibration=" + calibration + " ]";
    }

    /**
     * @return the valor1
     */
    public String getValor1() {
        return valor1;
    }

    /**
     * @param valor1 the valor1 to set
     */
    public void setValor1(String valor1) {
        this.valor1 = valor1;
    }

    /**
     * @return the valor2
     */
    public String getValor2() {
        return valor2;
    }

    /**
     * @param valor2 the valor2 to set
     */
    public void setValor2(String valor2) {
        this.valor2 = valor2;
    }

    /**
     * @return the valor3
     */
    public String getValor3() {
        return valor3;
    }

    /**
     * @param valor3 the valor3 to set
     */
    public void setValor3(String valor3) {
        this.valor3 = valor3;
    }

    /**
     * @return the valor4
     */
    public String getValor4() {
        return valor4;
    }

    /**
     * @param valor4 the valor4 to set
     */
    public void setValor4(String valor4) {
        this.valor4 = valor4;
    }

    /**
     * @return the valor5
     */
    public String getValor5() {
        return valor5;
    }

    /**
     * @param valor5 the valor5 to set
     */
    public void setValor5(String valor5) {
        this.valor5 = valor5;
    }

    /**
     * @return the valor6
     */
    public String getValor6() {
        return valor6;
    }

    /**
     * @param valor6 the valor6 to set
     */
    public void setValor6(String valor6) {
        this.valor6 = valor6;
    }

    public Object getCurdate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
