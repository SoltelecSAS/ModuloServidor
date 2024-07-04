/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "aseguradoras")
@NamedQueries({
    @NamedQuery(name = "Aseguradoras.findAll", query = "SELECT a FROM Aseguradoras a"),
    @NamedQuery(name = "Aseguradoras.findByInsuring", query = "SELECT a FROM Aseguradoras a WHERE a.insuring = :insuring"),
    @NamedQuery(name = "Aseguradoras.findByCodigoaseguradora", query = "SELECT a FROM Aseguradoras a WHERE a.codigoaseguradora = :codigoaseguradora"),
    @NamedQuery(name = "Aseguradoras.findByNombreaseguradora", query = "SELECT a FROM Aseguradoras a WHERE a.nombreaseguradora = :nombreaseguradora")})
public class Aseguradoras implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "INSURING")
    private Integer insuring;
    @Basic(optional = false)
    @Column(name = "Codigo_aseguradora")
    private String codigoaseguradora;
    @Basic(optional = false)
    @Column(name = "Nombre_aseguradora")
    private String nombreaseguradora;
   
    public Aseguradoras() {
    }

    public Aseguradoras(Integer insuring) {
        this.insuring = insuring;
    }

    public Aseguradoras(Integer insuring, String codigoaseguradora, String nombreaseguradora) {
        this.insuring = insuring;
        this.codigoaseguradora = codigoaseguradora;
        this.nombreaseguradora = nombreaseguradora;
    }

    public Integer getInsuring() {
        return insuring;
    }

    public void setInsuring(Integer insuring) {
        this.insuring = insuring;
    }

    public String getCodigoaseguradora() {
        return codigoaseguradora;
    }

    public void setCodigoaseguradora(String codigoaseguradora) {
        this.codigoaseguradora = codigoaseguradora;
    }

    public String getNombreaseguradora() {
        return nombreaseguradora;
    }

    public void setNombreaseguradora(String nombreaseguradora) {
        this.nombreaseguradora = nombreaseguradora;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (insuring != null ? insuring.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aseguradoras)) {
            return false;
        }
        Aseguradoras other = (Aseguradoras) object;
        if ((this.insuring == null && other.insuring != null) || (this.insuring != null && !this.insuring.equals(other.insuring))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.soltelec.model.Aseguradoras[insuring=" + insuring + "]";
    }

}
