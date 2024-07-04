/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "consolas")
public class Consolas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
   // protected ConsolasPK consolasPK;
    @Column(name = "IP")
    private String ip;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "ROAD", referencedColumnName = "ROAD", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Roads roads;

    public Consolas() {
    }

   /* public Consolas(ConsolasPK consolasPK) {
        this.consolasPK = consolasPK;
    }*/

    public Consolas(int console, int road) {
     //   this.consolasPK = new ConsolasPK(console, road);
    }

   /* public ConsolasPK getConsolasPK() {
        return consolasPK;
    }

    public void setConsolasPK(ConsolasPK consolasPK) {
        this.consolasPK = consolasPK;
    }*/

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Roads getRoads() {
        return roads;
    }

    public void setRoads(Roads roads) {
        this.roads = roads;
    }

    @Override
    public int hashCode() {
        int hash = 0;
      //  hash += (consolasPK != null ? consolasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consolas)) {
            return false;
        }
        Consolas other = (Consolas) object;
      /*  if ((this.consolasPK == null && other.consolasPK != null) || (this.consolasPK != null && !this.consolasPK.equals(other.consolasPK))) {
            return false;
        }*/
        return true;
    }

    @Override
    public String toString() {
        return "com.soltelec.model.Consolas[consolasPK=" + "]";
    }

}
