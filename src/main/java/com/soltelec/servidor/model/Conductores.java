/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author user
*/ 
@Entity
@Table(name = "propietarios")
@NamedQueries({
    @NamedQuery(name = "Conductores.findAll", query = "SELECT p FROM Propietarios p"),
    @NamedQuery(name = "Conductores.findByCarowner", query = "SELECT p FROM Propietarios p WHERE p.carowner = :conductor"),
    @NamedQuery(name = "Conductores.findByTipoidentificacion", query = "SELECT p FROM Propietarios p WHERE p.tipoIdentificacion = :tipoidentificacion"),
    @NamedQuery(name = "Conductores.findByApellidos", query = "SELECT p FROM Propietarios p WHERE p.apellidos = :apellidos"),
    @NamedQuery(name = "Conductores.findByNombres", query = "SELECT p FROM Propietarios p WHERE p.nombres = :nombres"),
    @NamedQuery(name = "Conductores.findByGeuser", query = "SELECT p FROM Propietarios p WHERE p.geuser = :geuser"),
    @NamedQuery(name = "Conductores.findByFechaRegistro", query = "SELECT p FROM Propietarios p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Conductores.findByNumerotelefono", query = "SELECT p FROM Propietarios p WHERE p.numerotelefono = :numerotelefono"),
    @NamedQuery(name = "Conductores.findByEmail", query= "SELECT p FROM Propietarios p WHERE p.email = :email"),
    @NamedQuery(name = "Conductores.findByCelular", query = "SELECT p FROM Propietarios p WHERE p.celular = :celular"),
    @NamedQuery(name = "Conductores.findByDireccion", query = "SELECT p FROM Propietarios p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "Conductores.findByCity", query = "SELECT P FROM Propietarios p WHERE p.ciudades = :ciudades"),
    @NamedQuery(name = "Conductores.findByNumerolicencia", query = "SELECT p FROM Propietarios p WHERE p.numerolicencia = :numerolicencia"),
    @NamedQuery(name = "Conductores.findByTipolicencia", query = "SELECT p FROM Propietarios p WHERE p.tipolicencia = :tipolicencia")})
public class Conductores implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Conductor")
    private Long conductor;
    @Column(name = "Tipo_identificacion")
    @Enumerated(EnumType.STRING)
    private TipoIdentificacion tipoIdentificacion;
    @Column(name = "Apellidos")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "Nombres")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "GEUSER")
    private int geuser;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Basic(optional = false)
    @Column(name = "Numero_telefono")
    private String numerotelefono;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "celular")
    private String celular;
    @Basic(optional = false)
    @Column(name = "Direccion")
    private String direccion;
    @JoinColumn(name = "CITY", referencedColumnName = "CITY")
    @ManyToOne(optional = false,fetch= FetchType.LAZY)
    private Ciudades ciudades;
    @Column(name = "Numero_licencia")
    private String numerolicencia;
    @Column(name = "Tipo_licencia")
    @Enumerated(EnumType.STRING)
    private TipoLicencia tipolicencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propietarios",fetch= FetchType.LAZY)
    private Collection<Vehiculos> vehiculosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propietarios",fetch= FetchType.LAZY)
    private Collection<HojaPruebas> hojaPruebasCollection;

    public Conductores() {
    }

    public Conductores(Long conductor) {
        this.conductor = conductor;
    }

    public Conductores(Long conductor, String nombres, int geuser, Date fechaRegistro, String numerotelefono, String celular, String direccion) {
        this.conductor = conductor;
        this.nombres = nombres;
        this.geuser = geuser;
        this.fechaRegistro = fechaRegistro;
        this.numerotelefono = numerotelefono;
        this.celular = celular;
        this.direccion = direccion;
    }

    public Long getConductor() {
        return conductor;
    }

    public void setConductor(Long conductor) {
        this.conductor = conductor;
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public int getGeuser() {
        return geuser;
    }

    public void setGeuser(int geuser) {
        this.geuser = geuser;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getNumerotelefono() {
        return numerotelefono;
    }

    public void setNumerotelefono(String numerotelefono) {
        this.numerotelefono = numerotelefono;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Ciudades getCiudadades() {
        return ciudades;
    }

    public void setCiudadades(Ciudades ciudades) {
        this.ciudades = ciudades;
    }

    public String getNumerolicencia() {
        return numerolicencia;
    }

    public void setNumerolicencia(String numerolicencia) {
        this.numerolicencia = numerolicencia;
    }

    public TipoLicencia getTipolicencia() {
        return tipolicencia;
    }

    public void setTipolicencia(TipoLicencia tipolicencia) {
        this.tipolicencia = tipolicencia;
    }

    public Collection<Vehiculos> getVehiculosCollection() {
        return vehiculosCollection;
    }

    public void setVehiculosCollection(Collection<Vehiculos> vehiculosCollection) {
        this.vehiculosCollection = vehiculosCollection;
    }

    public Collection<HojaPruebas> getHojaPruebasCollection() {
        return hojaPruebasCollection;
    }

    public void setHojaPruebasCollection(Collection<HojaPruebas> hojaPruebasCollection) {
        this.hojaPruebasCollection = hojaPruebasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conductor != null ? conductor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Propietarios)) {
            return false;
        }
        Propietarios other = (Propietarios) object;
        //if ((this.conductor == null && other.carowner != null ) || (this.conductor != null && !this.conductor.equals(other.conductor))) {

        if ((this.conductor == null ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.soltelec.model.Propietarios[carowner=" + conductor + "]";
    }

}
