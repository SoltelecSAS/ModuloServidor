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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soltelec
 */
@Entity
@Table(name = "software")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Software.findAll", query = "SELECT s FROM Software s"),
    @NamedQuery(name = "Software.findByIdsw", query = "SELECT s FROM Software s WHERE s.idsw = :idsw"),
    @NamedQuery(name = "Software.findByNombre", query = "SELECT s FROM Software s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "Software.findByVersion", query = "SELECT s FROM Software s WHERE s.version = :version"),
    @NamedQuery(name = "Software.findByFechaActualizacion", query = "SELECT s FROM Software s WHERE s.fechaActualizacion = :fechaActualizacion")})
public class Software implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_sw")
    private Integer idsw;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "version")
    private String version;
    @Column(name = "fecha_actualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;

    public Software() {
    }

    public Software(Integer idsw) {
        this.idsw = idsw;
    }

    public Software(Integer idsw, String nombre, String version) {
        this.idsw = idsw;
        this.nombre = nombre;
        this.version = version;
    }

    public Integer getIdsw() {
        return idsw;
    }

    public void setIdsw(Integer idsw) {
        this.idsw = idsw;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsw != null ? idsw.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Software)) {
            return false;
        }
        Software other = (Software) object;
        if ((this.idsw == null && other.idsw != null) || (this.idsw != null && !this.idsw.equals(other.idsw))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.soltelec.model.Software[ idsw=" + idsw + " ]";
    }
    
}
