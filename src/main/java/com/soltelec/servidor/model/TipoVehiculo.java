/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "tipo_vehiculo")
@NamedQueries({
    @NamedQuery(name = "TipoVehiculo.findAll", query = "SELECT t FROM TipoVehiculo t"),
    @NamedQuery(name = "TipoVehiculo.findByCartype", query = "SELECT t FROM TipoVehiculo t WHERE t.cartype = :cartype"),
    @NamedQuery(name = "TipoVehiculo.findByNombre", query = "SELECT t FROM TipoVehiculo t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoVehiculo.findByDescripcion", query = "SELECT t FROM TipoVehiculo t WHERE t.descripcion = :descripcion")})
public class TipoVehiculo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CARTYPE")
    private Integer cartype;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Descripcion")
    private String descripcion;
   

    public TipoVehiculo() {
    }

    public TipoVehiculo(Integer cartype) {
        this.cartype = cartype;
    }

    public TipoVehiculo(Integer cartype, String nombre) {
        this.cartype = cartype;
        this.nombre = nombre;
    }

    public Integer getCartype() {
        return cartype;
    }

    public void setCartype(Integer cartype) {
        this.cartype = cartype;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cartype != null ? cartype.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoVehiculo)) {
            return false;
        }
        TipoVehiculo other = (TipoVehiculo) object;
        if ((this.cartype == null && other.cartype != null) || (this.cartype != null && !this.cartype.equals(other.cartype))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.soltelec.model.TipoVehiculo[cartype=" + cartype + "]";
    }

}
