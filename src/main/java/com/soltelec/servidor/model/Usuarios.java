/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Dany
 */
@Entity
@Table(name = "usuarios")
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByNickusuario", query = "SELECT u FROM Usuarios u WHERE u.nickusuario = :nickusuario"),
    @NamedQuery(name = "Usuarios.findByGeuser", query = "SELECT u FROM Usuarios u WHERE u.geuser = :geuser"),
    @NamedQuery(name = "Usuarios.findByNombreusuario", query = "SELECT u FROM Usuarios u WHERE u.nombreusuario = :nombreusuario"),
    @NamedQuery(name = "Usuarios.findByEsAdministrador", query = "SELECT u FROM Usuarios u WHERE u.esAdministrador = :esAdministrador"),
    @NamedQuery(name = "Usuarios.findByContrasenia", query = "SELECT u FROM Usuarios u WHERE u.contrasenia = :contrasenia"),
    @NamedQuery(name = "Usuarios.findByFechavalidacion", query = "SELECT u FROM Usuarios u WHERE u.fechavalidacion = :fechavalidacion")})
public class Usuarios implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @Column(name = "Nick_usuario")
    private String nickusuario;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GEUSER")
    private Integer geuser;
    
    @Basic(optional = false)
    @Column(name = "Nombre_usuario")
    private String nombreusuario;
    @Column(name = "es_administrador")
    public String esAdministrador;
    @Column(name = "Contrasenia")
    private String contrasenia;
    @Basic(optional = false)
    @Column(name = "cedula")
    private String cedula;
    @Basic(optional = false)
    @Column(name = "Fecha_validacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavalidacion;
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "usuario")
    private List<Calibraciones> calibracionesList;

    public Usuarios() {
    }

    public Usuarios(Integer geuser) {
        this.geuser = geuser;
    }

    public Usuarios(Integer geuser, String nickusuario, String nombreusuario, Date fechavalidacion) {
        this.geuser = geuser;
        this.nickusuario = nickusuario;
        this.nombreusuario = nombreusuario;
        this.fechavalidacion = fechavalidacion;
    }

    public String getNickusuario() {
        return nickusuario;
    }

    public void setNickusuario(String nickusuario) {
        this.nickusuario = nickusuario;
    }

    public Integer getGeuser() {
        return geuser;
    }

    public void setGeuser(Integer geuser) {
        this.geuser = geuser;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getEsAdministrador() {
        return esAdministrador;
    }

    public void setEsAdministrador(String esAdministrador) {
        this.esAdministrador = esAdministrador;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Date getFechavalidacion() {
        return fechavalidacion;
    }

    public void setFechavalidacion(Date fechavalidacion) {
        this.fechavalidacion = fechavalidacion;
    }

    
    public List<Calibraciones> getCalibracionesList() {
        return calibracionesList;
    }

    public void setCalibracionesList(List<Calibraciones> calibracionesList) {
        this.calibracionesList = calibracionesList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (geuser != null ? geuser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.geuser == null && other.geuser != null) || (this.geuser != null && !this.geuser.equals(other.geuser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreusuario;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
}
