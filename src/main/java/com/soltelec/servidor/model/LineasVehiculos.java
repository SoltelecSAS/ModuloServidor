/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "lineas_vehiculos")
@NamedQueries({
    @NamedQuery(name = "LineasVehiculos.findAll", query = "SELECT l FROM LineasVehiculos l"),
    @NamedQuery(name = "LineasVehiculos.findByCarline", query = "SELECT l FROM LineasVehiculos l WHERE l.carline = :carline"),
    @NamedQuery(name = "LineasVehiculos.findByCrlcod", query = "SELECT l FROM LineasVehiculos l WHERE l.crlcod = :crlcod"),
    @NamedQuery(name = "LineasVehiculos.findByCrlname", query = "SELECT l FROM LineasVehiculos l WHERE l.crlname = :crlname")})
public class LineasVehiculos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CARLINE")
    private Integer carline;
    @Basic(optional = false)
    @Column(name = "CRLCOD")
    private String crlcod;
    @Basic(optional = false)
    @Column(name = "CRLNAME")
    private String crlname;
    @Column(name = "rpm_ini")
    private String rpm_ini;
    @Column(name = "rpm_fin")
    private String rpm_fin;
    
    @JoinColumn(name = "CARMARK", referencedColumnName = "CARMARK")
    @ManyToOne(optional = false,fetch= FetchType.LAZY)
    private Marcas marcas;

    public LineasVehiculos() {
    }

    public LineasVehiculos(Integer carline) {
        this.carline = carline;
    }

    public LineasVehiculos(Integer carline, String crlcod, String crlname) {
        this.carline = carline;
        this.crlcod = crlcod;
        this.crlname = crlname;
    }

    public Integer getCarline() {
        return carline;
    }

    public void setCarline(Integer carline) {
        this.carline = carline;
    }

    public String getCrlcod() {
        return crlcod;
    }

    public void setCrlcod(String crlcod) {
        this.crlcod = crlcod;
    }

    public String getCrlname() {
        return crlname;
    }

    public void setCrlname(String crlname) {
        this.crlname = crlname;
    }

    
    public Marcas getMarcas() {
        return marcas;
    }

    public void setMarcas(Marcas marcas) {
        this.marcas = marcas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (carline != null ? carline.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LineasVehiculos)) {
            return false;
        }
        LineasVehiculos other = (LineasVehiculos) object;
        if ((this.carline == null && other.carline != null) || (this.carline != null && !this.carline.equals(other.carline))) {
            return false;
        }
        return true;
    }

    public String getRpm_ini() {
        return rpm_ini;
    }

    public void setRpm_ini(String rpm_ini) {
        this.rpm_ini = rpm_ini;
    }

    public String getRpm_fin() {
        return rpm_fin;
    }

    public void setRpm_fin(String rpm_fin) {
        this.rpm_fin = rpm_fin;
    }
    
    

    @Override
    public String toString() {
        return "com.soltelec.model.LineasVehiculos[carline=" + carline + "]";
    }
}
