/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Soltelec
 */
@Entity
@Table(name = "ciudades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ciudades.findAll", query = "SELECT c FROM Ciudades c"),
    @NamedQuery(name = "Ciudades.findByCity", query = "SELECT c FROM Ciudades c WHERE c.city = :city"),
    @NamedQuery(name = "Ciudades.findByCodigo", query = "SELECT c FROM Ciudades c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Ciudades.findByNombreciudad", query = "SELECT c FROM Ciudades c WHERE c.nombreciudad = :nombreciudad"),
    @NamedQuery(name = "Ciudades.findByCiudadprincipal", query = "SELECT c FROM Ciudades c WHERE c.ciudadprincipal = :ciudadprincipal")})
public class Ciudades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CITY")
    private Integer city;
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "Nombre_ciudad")
    private String nombreciudad;
    @Basic(optional = false)
    @Column(name = "Ciudad_principal")
    private String ciudadprincipal;
    @JoinColumn(name = "STATE", referencedColumnName = "id_departamento")
    @ManyToOne(optional = false)
    private Departamentos state;
    


    public Ciudades() {
    }

    public Ciudades(Integer city) {
        this.city = city;
    }

    public Ciudades(Integer city, String codigo, String nombreciudad, String ciudadprincipal) {
        this.city = city;
        this.codigo = codigo;
        this.nombreciudad = nombreciudad;
        this.ciudadprincipal = ciudadprincipal;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreciudad() {
        return nombreciudad;
    }

    public void setNombreciudad(String nombreciudad) {
        this.nombreciudad = nombreciudad;
    }

    public String getCiudadprincipal() {
        return ciudadprincipal;
    }

    public void setCiudadprincipal(String ciudadprincipal) {
        this.ciudadprincipal = ciudadprincipal;
    }

    public Departamentos getState() {
        return state;
    }

    public void setState(Departamentos state) {
        this.state = state;
    }   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (city != null ? city.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciudades)) {
            return false;
        }
        Ciudades other = (Ciudades) object;
        if ((this.city == null && other.city != null) || (this.city != null && !this.city.equals(other.city))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "com.soltelec.servidor.model.Ciudades[ city=" + city + " ]";
        return nombreciudad;
    }

}
