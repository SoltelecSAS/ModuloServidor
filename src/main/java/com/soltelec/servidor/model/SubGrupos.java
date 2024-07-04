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
@Table(name = "sub_grupos")
@NamedQueries({
    @NamedQuery(name = "SubGrupos.findAll", query = "SELECT s FROM SubGrupos s"),
    @NamedQuery(name = "SubGrupos.findByScdefgroupsub", query = "SELECT s FROM SubGrupos s WHERE s.scdefgroupsub = :scdefgroupsub"),
    @NamedQuery(name = "SubGrupos.findByNombresubgrupo", query = "SELECT s FROM SubGrupos s WHERE s.nombresubgrupo = :nombresubgrupo")})
public class SubGrupos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SCDEFGROUPSUB")
    private Integer scdefgroupsub;
    @Column(name = "Nombre_subgrupo")
    private String nombresubgrupo;
    @JoinColumn(name = "SCDEFGROUPPRIN", referencedColumnName = "DEFGROUP")
    @ManyToOne(fetch= FetchType.LAZY)
    private Grupos grupos;
    

    public SubGrupos() {
    }

    public SubGrupos(Integer scdefgroupsub) {
        this.scdefgroupsub = scdefgroupsub;
    }

    public Integer getScdefgroupsub() {
        return scdefgroupsub;
    }

    public void setScdefgroupsub(Integer scdefgroupsub) {
        this.scdefgroupsub = scdefgroupsub;
    }

    public String getNombresubgrupo() {
        return nombresubgrupo;
    }

    public void setNombresubgrupo(String nombresubgrupo) {
        this.nombresubgrupo = nombresubgrupo;
    }

    public Grupos getGrupos() {
        return grupos;
    }

    public void setGrupos(Grupos grupos) {
        this.grupos = grupos;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scdefgroupsub != null ? scdefgroupsub.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubGrupos)) {
            return false;
        }
        SubGrupos other = (SubGrupos) object;
        if ((this.scdefgroupsub == null && other.scdefgroupsub != null) || (this.scdefgroupsub != null && !this.scdefgroupsub.equals(other.scdefgroupsub))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "com.soltelec.model.SubGrupos[scdefgroupsub=" + scdefgroupsub + "]";
        return nombresubgrupo;
    }

}
