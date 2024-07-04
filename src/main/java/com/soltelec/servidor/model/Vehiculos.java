/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Gerencia TIC
 */
@Entity
@Table(name = "vehiculos")
@NamedQueries({
    @NamedQuery(name = "Vehiculos.findAll", query = "SELECT v FROM Vehiculos v"),
    @NamedQuery(name = "Vehiculos.findByCar", query = "SELECT v FROM Vehiculos v WHERE v.car = :car"),
    @NamedQuery(name = "Vehiculos.findByCarplate", query = "SELECT v FROM Vehiculos v WHERE v.carplate = :carplate"),
    @NamedQuery(name = "Vehiculos.findByModelo", query = "SELECT v FROM Vehiculos v WHERE v.modelo = :modelo"),
    @NamedQuery(name = "Vehiculos.findByCinlindraje", query = "SELECT v FROM Vehiculos v WHERE v.cilindraje = :cinlindraje"),
    @NamedQuery(name = "Vehiculos.findByNumerolicencia", query = "SELECT v FROM Vehiculos v WHERE v.numerolicencia = :numerolicencia"),
    @NamedQuery(name = "Vehiculos.findByNumeroejes", query = "SELECT v FROM Vehiculos v WHERE v.numeroejes = :numeroejes"),
    @NamedQuery(name = "Vehiculos.findByIndate", query = "SELECT v FROM Vehiculos v WHERE v.indate = :indate"),
    @NamedQuery(name = "Vehiculos.findByNumeroexostos", query = "SELECT v FROM Vehiculos v WHERE v.numeroexostos = :numeroexostos"),
    @NamedQuery(name = "Vehiculos.findByDiametro", query = "SELECT v FROM Vehiculos v WHERE v.diametro = :diametro"),
    @NamedQuery(name = "Vehiculos.findByTiemposmotor", query = "SELECT v FROM Vehiculos v WHERE v.tiemposmotor = :tiemposmotor"),
    @NamedQuery(name = "Vehiculos.findByVelocidad", query = "SELECT v FROM Vehiculos v WHERE v.velocidad = :velocidad"),
    @NamedQuery(name = "Vehiculos.findByNumeroSOAT", query = "SELECT v FROM Vehiculos v WHERE v.numeroSOAT = :numeroSOAT"),
    @NamedQuery(name = "Vehiculos.findByFechasoat", query = "SELECT v FROM Vehiculos v WHERE v.fechasoat = :fechasoat"),
    @NamedQuery(name = "Vehiculos.findByFechaexpsoat", query = "SELECT v FROM Vehiculos v WHERE v.fechaexpsoat = :fechaexpsoat"),
    @NamedQuery(name = "Vehiculos.findByNacionalidad", query = "SELECT v FROM Vehiculos v WHERE v.nacionalidad = :nacionalidad"),
    @NamedQuery(name = "Vehiculos.findBySpservice", query = "SELECT v FROM Vehiculos v WHERE v.spservice = :spservice"),
    @NamedQuery(name = "Vehiculos.findByNumeromotor", query = "SELECT v FROM Vehiculos v WHERE v.numeromotor = :numeromotor"),
    @NamedQuery(name = "Vehiculos.findByVin", query = "SELECT v FROM Vehiculos v WHERE v.vin = :vin"),
    @NamedQuery(name = "Vehiculos.findByPais", query = "SELECT v FROM Vehiculos v WHERE v.pais = :pais"),
    @NamedQuery(name = "Vehiculos.findByFecharegistro", query = "SELECT v FROM Vehiculos v WHERE v.fecharegistro = :fecharegistro")})
public class Vehiculos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CAR")
    private Integer car;
    @Basic(optional = false)
    @Column(name = "CARPLATE")
    private String carplate;
    @Basic(optional = false)
    @Column(name = "Modelo")
    private int modelo;
    @Basic(optional = false)
    @Column(name = "diseño")
    @Enumerated(EnumType.STRING)
    private Diseno diseno;
    @Column(name = "Cinlindraje")
    private Integer cilindraje;
    @Column(name = "Numero_licencia")
    private String numerolicencia;
    @Column(name = "Numero_ejes")
    private Integer numeroejes;
    @Basic(optional = false)
    @Column(name = "INDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date indate;
    @Column(name = "Numero_exostos")
    private Integer numeroexostos;
    @Column(name = "Diametro")
    private Integer diametro;
    @Column(name = "Tiempos_motor")
    private Integer tiemposmotor;
    /*
    *
    * Crear Varibale del Pais como aparece en la BD Mysql por Diego Garzon
    *
    */
    //@Column (name = "pais")
    @JoinColumn(name = "pais", referencedColumnName = "codigo")
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private Pais pais;  
    @Column (name = "vidrios_polarizados")
    private String vidriosPolarizados;
    @Column (name = "blindaje")
    private String blindaje;
    @Column(name = "Velocidad")
    private Integer velocidad;
    @Column(name = "Numero_SOAT")
    private String numeroSOAT;
    @Basic(optional = false)
    @Column(name = "Fecha_soat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechasoat;
    @Basic(optional = false)
    @Column(name = "Fecha_exp_soat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaexpsoat;
    @Column(name = "Nacionalidad")
    private String nacionalidad;
    @Basic(optional = false)
    @Column(name = "SPSERVICE")
    private int spservice;
    @Column(name = "Numero_motor")
    private String numeromotor;
    @Column(name = "VIN")
    private String vin;
    @Column( name = "kilometraje")
    private Integer kilometraje;
    @Basic(optional = false)
    @Column(name = "Fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecharegistro;
    @JoinColumn(name = "WHEEL", referencedColumnName = "WHEEL")
    @ManyToOne(optional = false,fetch= FetchType.LAZY)
    private Llantas llantas;
    @JoinColumn(name = "SERVICE", referencedColumnName = "SERVICE")
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private Servicios servicios;
    @JoinColumn(name = "INSURING", referencedColumnName = "INSURING")
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private Aseguradoras aseguradoras;
    @JoinColumn(name = "GEUSER", referencedColumnName = "GEUSER")
    @ManyToOne(optional = false, fetch= FetchType.LAZY)
    private Usuarios usuarios;
    @JoinColumn(name = "FUELTYPE", referencedColumnName = "FUELTYPE")
    @ManyToOne(optional = false, fetch= FetchType.LAZY)
    private TiposGasolina tiposGasolina;
    @JoinColumn(name = "Color", referencedColumnName = "COLOR")
    @ManyToOne(optional = false,fetch= FetchType.LAZY)
    private Colores colores;
    @JoinColumn(name = "CLASS", referencedColumnName = "CLASS")
    @ManyToOne(optional = false,fetch= FetchType.LAZY)
    private ClasesVehiculo clasesVehiculo;
    @JoinColumn(name = "CARTYPE", referencedColumnName = "CARTYPE")
    @ManyToOne(optional = false,fetch= FetchType.LAZY)
    private TipoVehiculo tipoVehiculo;
    @JoinColumn(name = "CAROWNER", referencedColumnName = "CAROWNER")
    @ManyToOne(optional = false,fetch= FetchType.LAZY)
    private Propietarios propietarios;
    @JoinColumn(name = "CARMARK", referencedColumnName = "CARMARK")
    @ManyToOne
    private Marcas marcas;
    @JoinColumn(name = "CARLINE", referencedColumnName = "CARLINE")
    @ManyToOne(optional = false,fetch= FetchType.LAZY)
    private LineasVehiculos lineasVehiculos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehiculos",fetch= FetchType.LAZY)
    private Collection<HojaPruebas> hojaPruebasCollection;
    
    @Column(name = "conversion_gnv")
    private String conversion_GNV;
    @Column(name = "fecha_vencimiento_gnv")
    @Temporal(TemporalType.TIMESTAMP)
    private Date Fecha_GNV;
        @Column(name = "catalizador")
    private String catalizador;

    public String getCatalizador() {
        return catalizador;
    }

    public void setCatalizador(String catalizador) {
        this.catalizador = catalizador;
    }
    


    public Vehiculos() {
    }

    public Vehiculos(Integer car) {
        this.car = car;
    }

    public Vehiculos(Integer car, String carplate, int modelo, Date indate, Date fechasoat, Date fechaexpsoat, int spservice, Date fecharegistro, int Pais, int tipoCombustible, String vidriosPolarizados, String blindaje) {
        this.car = car;
        this.carplate = carplate;
        this.modelo = modelo;
        this.diseno = diseno;
        this.indate = indate;
        this.fechasoat = fechasoat;
        this.fechaexpsoat = fechaexpsoat;
        this.spservice = spservice;
        this.fecharegistro = fecharegistro;
        
        /*
        * Constructor this modificado por Diego Garzon
        */
        this.pais = pais;
       
        this.vidriosPolarizados = vidriosPolarizados;
        this.blindaje = blindaje;
    }

    public Integer getCar() {
        return car;
    }

    public void setCar(Integer car) {
        this.car = car;
    }

    public String getCarplate() {
        return carplate;
    }

    public void setCarplate(String carplate) {
        this.carplate = carplate;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public Diseno getDiseno() {
        return diseno;
    }

    public void setDiseno(Diseno diseno) {
        this.diseno = diseno;
    }
    
    public Integer getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(Integer cilindraje) {
        this.cilindraje = cilindraje;
    }
    /*
    *
    *
    * Crear Getter y Setter de Pais y tipo de Combustibles por Diego Garzon
    *
    */
    
   public Pais getPais() {
        return pais;
    }
    public void setPais(Pais pais) {
        this.pais = pais;
    }
    
    public String getVidriosPolarizados() {
        return vidriosPolarizados;
    }
    public void setVidriosPolarizados() {
        this.vidriosPolarizados = vidriosPolarizados;
    }
    public String getBlindaje() {
        return blindaje;
    }
    public void setBlindaje() {
        this.blindaje = blindaje;
    }
    
    /*
    *
    *
    *
    */
    
    
    public String getNumerolicencia() {
        return numerolicencia;
    }

    public void setNumerolicencia(String numerolicencia) {
        this.numerolicencia = numerolicencia;
    }

    public Integer getNumeroejes() {
        return numeroejes;
    }

    public void setNumeroejes(Integer numeroejes) {
        this.numeroejes = numeroejes;
    }

    public Date getIndate() {
        return indate;
    }

    public void setIndate(Date indate) {
        this.indate = indate;
    }

    public Integer getNumeroexostos() {
        return numeroexostos;
    }

    public void setNumeroexostos(Integer numeroexostos) {
        this.numeroexostos = numeroexostos;
    }

    public Integer getDiametro() {
        return diametro;
    }

    public void setDiametro(Integer diametro) {
        this.diametro = diametro;
    }

    public Integer getTiemposmotor() {
        return tiemposmotor;
    }

    public void setTiemposmotor(Integer tiemposmotor) {
        this.tiemposmotor = tiemposmotor;
    }

    public Integer getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Integer velocidad) {
        this.velocidad = velocidad;
    }

    public String getNumeroSOAT() {
        return numeroSOAT;
    }

    public void setNumeroSOAT(String numeroSOAT) {
        this.numeroSOAT = numeroSOAT;
    }

    public Date getFechasoat() {
        return fechasoat;
    }

    public void setFechasoat(Date fechasoat) {
        this.fechasoat = fechasoat;
    }

    public Date getFechaexpsoat() {
        return fechaexpsoat;
    }

    public void setFechaexpsoat(Date fechaexpsoat) {
        this.fechaexpsoat = fechaexpsoat;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getSpservice() {
        return spservice;
    }

    public void setSpservice(int spservice) {
        this.spservice = spservice;
    }

    public String getNumeromotor() {
        return numeromotor;
    }

    public void setNumeromotor(String numeromotor) {
        this.numeromotor = numeromotor;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(Integer kilometraje) {
        this.kilometraje = kilometraje;
    }

    
    public Date getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(Date fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public Llantas getLlantas() {
        return llantas;
    }

    public void setLlantas(Llantas llantas) {
        this.llantas = llantas;
    }

    public Servicios getServicios() {
        return servicios;
    }

    public void setServicios(Servicios servicios) {
        this.servicios = servicios;
    }

    public Aseguradoras getAseguradoras() {
        return aseguradoras;
    }

    public void setAseguradoras(Aseguradoras aseguradoras) {
        this.aseguradoras = aseguradoras;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public TiposGasolina getTiposGasolina() {
        return tiposGasolina;
    }

    public void setTiposGasolina(TiposGasolina tiposGasolina) {
        this.tiposGasolina = tiposGasolina;
    }

    public Colores getColores() {
        return colores;
    }

    public void setColores(Colores colores) {
        this.colores = colores;
    }

    public ClasesVehiculo getClasesVehiculo() {
        return clasesVehiculo;
    }

    public void setClasesVehiculo(ClasesVehiculo clasesVehiculo) {
        this.clasesVehiculo = clasesVehiculo;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public Propietarios getPropietarios() {
        return propietarios;
    }

    public void setPropietarios(Propietarios propietarios) {
        this.propietarios = propietarios;
    }

    public Marcas getMarcas() {
        return marcas;
    }

    public void setMarcas(Marcas marcas) {
        this.marcas = marcas;
    }

    public LineasVehiculos getLineasVehiculos() {
        return lineasVehiculos;
    }

    public void setLineasVehiculos(LineasVehiculos lineasVehiculos) {
        this.lineasVehiculos = lineasVehiculos;
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
        hash += (car != null ? car.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehiculos)) {
            return false;
        }
        Vehiculos other = (Vehiculos) object;
        if ((this.car == null && other.car != null) || (this.car != null && !this.car.equals(other.car))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.soltelec.model.Vehiculos[car=" + car + "]";
    }
    
    public String getConversion_GNV() {
        return conversion_GNV;
    }

    public void setConversion_GNV(String conversion_GNV) {
        this.conversion_GNV = conversion_GNV;
    }

    public Date getFecha_GNV() {
        return Fecha_GNV;
    }

    public void setFecha_GNV(Date Fecha_GNV) {
        this.Fecha_GNV = Fecha_GNV;
    }

}
