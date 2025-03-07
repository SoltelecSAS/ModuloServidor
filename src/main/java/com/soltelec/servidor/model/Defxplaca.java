/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "defxplaca")
@NamedQueries({
    @NamedQuery(name = "Defxplaca.findAll", query = "SELECT d FROM Defxplaca d"),
    @NamedQuery(name = "Defxplaca.findByIdVehiculoFor", query = "SELECT d FROM Defxplaca d WHERE d.defxplacaPK.idVehiculoFor = :idVehiculoFor"),
    @NamedQuery(name = "Defxplaca.findByIdDefecto", query = "SELECT d FROM Defxplaca d WHERE d.defxplacaPK.idDefecto = :idDefecto"),
    @NamedQuery(name = "Defxplaca.findByIdhojapruebafor", query = "SELECT d FROM Defxplaca d WHERE d.defxplacaPK.idhojapruebafor = :idhojapruebafor"),
    @NamedQuery(name = "Defxplaca.findByTipodefecto", query = "SELECT d FROM Defxplaca d WHERE d.tipodefecto = :tipodefecto")})
public class Defxplaca implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DefxplacaPK defxplacaPK;
    @Basic(optional = false)
    @Column(name = "Tipo_defecto")
    private String tipodefecto;
    @JoinColumn(name = "id_vehiculo_for", referencedColumnName = "CAR", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Vehiculos vehiculos;
    @JoinColumn(name = "Id_hojaprueba_for", referencedColumnName = "TESTSHEET", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private HojaPruebas hojaPruebas;
    @JoinColumn(name = "id_defecto", referencedColumnName = "CARDEFAULT", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Defectos defectos;

    public Defxplaca() {
    }

    public Defxplaca(DefxplacaPK defxplacaPK) {
        this.defxplacaPK = defxplacaPK;
    }

    public Defxplaca(DefxplacaPK defxplacaPK, String tipodefecto) {
        this.defxplacaPK = defxplacaPK;
        this.tipodefecto = tipodefecto;
    }

    public Defxplaca(int idVehiculoFor, int idDefecto, int idhojapruebafor) {
        this.defxplacaPK = new DefxplacaPK(idVehiculoFor, idDefecto, idhojapruebafor);
    }

    public DefxplacaPK getDefxplacaPK() {
        return defxplacaPK;
    }

    public void setDefxplacaPK(DefxplacaPK defxplacaPK) {
        this.defxplacaPK = defxplacaPK;
    }

    public String getTipodefecto() {
        return tipodefecto;
    }

    public void setTipodefecto(String tipodefecto) {
        this.tipodefecto = tipodefecto;
    }

    public Vehiculos getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(Vehiculos vehiculos) {
        this.vehiculos = vehiculos;
    }

    public HojaPruebas getHojaPruebas() {
        return hojaPruebas;
    }

    public void setHojaPruebas(HojaPruebas hojaPruebas) {
        this.hojaPruebas = hojaPruebas;
    }

    public Defectos getDefectos() {
        return defectos;
    }

    public void setDefectos(Defectos defectos) {
        this.defectos = defectos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (defxplacaPK != null ? defxplacaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Defxplaca)) {
            return false;
        }
        Defxplaca other = (Defxplaca) object;
        if ((this.defxplacaPK == null && other.defxplacaPK != null) || (this.defxplacaPK != null && !this.defxplacaPK.equals(other.defxplacaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.soltelec.model.Defxplaca[defxplacaPK=" + defxplacaPK + "]";
    }
}