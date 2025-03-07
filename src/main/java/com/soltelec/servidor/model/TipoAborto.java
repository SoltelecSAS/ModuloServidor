/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "tipo_aborto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoAborto.findAll", query = "SELECT t FROM TipoAborto t"),
    @NamedQuery(name = "TipoAborto.findById", query = "SELECT t FROM TipoAborto t WHERE t.id = :id"),
    @NamedQuery(name = "TipoAborto.findByDescripcion", query = "SELECT t FROM TipoAborto t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TipoAborto.findByCodigo", query = "SELECT t FROM TipoAborto t WHERE t.codigo = :codigo")})
public class TipoAborto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "codigo")
    private int codigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoAborto")
    private List<Pruebas> pruebasList;

    public TipoAborto() {
    }

    public TipoAborto(Integer id) {
        this.id = id;
    }

    public TipoAborto(Integer id, String descripcion, int codigo) {
        this.id = id;
        this.descripcion = descripcion;
        this.codigo = codigo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public List<Pruebas> getPruebasList() {
        return pruebasList;
    }

    public void setPruebasList(List<Pruebas> pruebasList) {
        this.pruebasList = pruebasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoAborto)) {
            return false;
        }
        TipoAborto other = (TipoAborto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jdbc.TipoAborto[ id=" + id + " ]";
    }
    
}
