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
@Table(name = "grupos")
@NamedQueries({
    @NamedQuery(name = "Grupos.findAll", query = "SELECT g FROM Grupos g"),
    @NamedQuery(name = "Grupos.findByDefgroup", query = "SELECT g FROM Grupos g WHERE g.defgroup = :defgroup"),
    @NamedQuery(name = "Grupos.findByNombregrupo", query = "SELECT g FROM Grupos g WHERE g.nombregrupo = :nombregrupo")})
public class Grupos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DEFGROUP")
    private Integer defgroup;
    @Basic(optional = false)
    @Column(name = "Nombre_grupo")
    private String nombregrupo;
    
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupos",fetch= FetchType.LAZY)
   private Collection<SubGrupos> subGruposCollection;    
    
///    @OneToMany(mappedBy = "grupos",fetch= FetchType.LAZY)
///    private Collection<SubGrupos> subGruposCollection;
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupos",fetch= FetchType.LAZY)
//    private Collection<Defectos> defectosCollection;

    public Grupos() {
    }

    public Grupos(Integer defgroup) {
        this.defgroup = defgroup;
    }

    public Grupos(Integer defgroup, String nombregrupo) {
        this.defgroup = defgroup;
        this.nombregrupo = nombregrupo;
    }

    public Integer getDefgroup() {
        return defgroup;
    }

    public void setDefgroup(Integer defgroup) {
        this.defgroup = defgroup;
    }

    public String getNombregrupo() {
        return nombregrupo;
    }

    public void setNombregrupo(String nombregrupo) {
        this.nombregrupo = nombregrupo;
    }

    

    public Collection<SubGrupos> getSubGruposCollection() {
        return subGruposCollection;
    }

    public void setSubGruposCollection(Collection<SubGrupos> subGruposCollection) {
        this.subGruposCollection = subGruposCollection;
    }
//
//    public Collection<Defectos> getDefectosCollection() {
//        return defectosCollection;
//    }
//
//    public void setDefectosCollection(Collection<Defectos> defectosCollection) {
//        this.defectosCollection = defectosCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (defgroup != null ? defgroup.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupos)) {
            return false;
        }
        Grupos other = (Grupos) object;
        if ((this.defgroup == null && other.defgroup != null) || (this.defgroup != null && !this.defgroup.equals(other.defgroup))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombregrupo;
        //return "com.soltelec.model.Grupos[defgroup=" + defgroup + "]";
    }

}
