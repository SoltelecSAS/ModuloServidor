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
import javax.persistence.Table;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "colores")
public class Colores implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COLOR")
    private Integer color;
    @Basic(optional = false)
    @Column(name = "Nombre_color")
    private String nombrecolor;
    
    
    public Colores() {
    }

    public Colores(Integer color) {
        this.color = color;
    }

    public Colores(Integer color, String nombrecolor) {
        this.color = color;
        this.nombrecolor = nombrecolor;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public String getNombrecolor() {
        return nombrecolor;
    }

    public void setNombrecolor(String nombrecolor) {
        this.nombrecolor = nombrecolor;
    }

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (color != null ? color.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Colores)) {
            return false;
        }
        Colores other = (Colores) object;
        if ((this.color == null && other.color != null) || (this.color != null && !this.color.equals(other.color))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.soltelec.model.Colores[color=" + color + "]";
    }

}
