/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "llantas")
@NamedQueries({
    @NamedQuery(name = "Llantas.findAll", query = "SELECT l FROM Llantas l"),
    @NamedQuery(name = "Llantas.findByWheel", query = "SELECT l FROM Llantas l WHERE l.wheel = :wheel"),
    @NamedQuery(name = "Llantas.findByNombrellanta", query = "SELECT l FROM Llantas l WHERE l.nombrellanta = :nombrellanta"),
    @NamedQuery(name = "Llantas.findByRadiollanta", query = "SELECT l FROM Llantas l WHERE l.radiollanta = :radiollanta")})
public class Llantas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "WHEEL")
    private Integer wheel;
    
    @Column(name = "Nombre_llanta")
    private String nombrellanta;
    @Column(name = "Radio_llanta")
    private Integer radiollanta;
    

    public Llantas() {
    }

    public Llantas(Integer wheel) {
        this.wheel = wheel;
    }

    public Integer getWheel() {
        return wheel;
    }

    public void setWheel(Integer wheel) {
        this.wheel = wheel;
    }

    public String getNombrellanta() {
        return nombrellanta;
    }

    public void setNombrellanta(String nombrellanta) {
        this.nombrellanta = nombrellanta;
    }

    public Integer getRadiollanta() {
        return radiollanta;
    }

    public void setRadiollanta(Integer radiollanta) {
        this.radiollanta = radiollanta;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wheel != null ? wheel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Llantas)) {
            return false;
        }
        Llantas other = (Llantas) object;
        if ((this.wheel == null && other.wheel != null) || (this.wheel != null && !this.wheel.equals(other.wheel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.soltelec.model.Llantas[wheel=" + wheel + "]";
    }

}
