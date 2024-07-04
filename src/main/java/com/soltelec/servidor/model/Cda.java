/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GerenciaDesarrollo
 */
@Entity
@Table(name = "cda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cda.findAll", query = "SELECT c FROM Cda c"),
    @NamedQuery(name = "Cda.findByIdCda", query = "SELECT c FROM Cda c WHERE c.idCda = :idCda"),
    @NamedQuery(name = "Cda.findByNombre", query = "SELECT c FROM Cda c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Cda.findByNit", query = "SELECT c FROM Cda c WHERE c.nit = :nit"),
    @NamedQuery(name = "Cda.findByDireccion", query = "SELECT c FROM Cda c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Cda.findByTelefono", query = "SELECT c FROM Cda c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "Cda.findByCelular", query = "SELECT c FROM Cda c WHERE c.celular = :celular"),
    @NamedQuery(name = "Cda.findByResolucion", query = "SELECT c FROM Cda c WHERE c.resolucion = :resolucion"),
    @NamedQuery(name = "Cda.findByFechaRes", query = "SELECT c FROM Cda c WHERE c.fechaResolucion = :fechaResolucion"),
    @NamedQuery(name = "Cda.findByNomRespCertificados", query = "SELECT c FROM Cda c WHERE c.nomRespCertificados = :nomRespCertificados"),
    @NamedQuery(name = "Cda.findByCertificadoconformidad", query = "SELECT c FROM Cda c WHERE c.certificadoconformidad = :certificadoconformidad"), //@NamedQuery(name ="Cda.findByClase_cda",query = "SELECT c FROM Cda c WHERE c.clase_cda = :clase_cda")
})
/*
    *
    *
 */

 /*
    @NamedQuery(name = "Cda.findAll", query = "SELECT c FROM Cda c"),
    @NamedQuery(name = "Cda.findByIdCda", query = "SELECT c FROM Cda c WHERE c.idCda = :idCda"),
    @NamedQuery(name = "Cda.findByNombre", query = "SELECT c FROM Cda c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Cda.findByNit", query = "SELECT c FROM Cda c WHERE c.nit = :nit"),
    @NamedQuery(name = "Cda.findByDireccion", query = "SELECT c FROM Cda c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Cda.findByTelefono", query = "SELECT c FROM Cda c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "Cda.findByCelular", query = "SELECT c FROM Cda c WHERE c.celular = :celular"),
    @NamedQuery(name = "Cda.findByResolucion", query = "SELECT c FROM Cda c WHERE c.resolucion = :resolucion"),
    @NamedQuery(name = "Cda.findByFechaResolucion", query = "SELECT c FROM Cda c WHERE c.fechaResolucion = :fechaResolucion"),
    @NamedQuery(name = "Cda.findByCodigo", query = "SELECT c FROM Cda c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Cda.findByNomRespCertificados", query = "SELECT c FROM Cda c WHERE c.nomRespCertificados = :nomRespCertificados"),
    @NamedQuery(name = "Cda.findByCertificadoconformidad", query = "SELECT c FROM Cda c WHERE c.certificadoconformidad = :certificadoconformidad"),
    @NamedQuery(name = "Cda.findByConsecutivoApr", query = "SELECT c FROM Cda c WHERE c.consecutivoApr = :consecutivoApr"),
    @NamedQuery(name = "Cda.findByConsecutivoRep", query = "SELECT c FROM Cda c WHERE c.consecutivoRep = :consecutivoRep"),
    @NamedQuery(name = "Cda.findByConsecutivoCert", query = "SELECT c FROM Cda c WHERE c.consecutivoCert = :consecutivoCert"),
    @NamedQuery(name = "Cda.findByUsuarioResp", query = "SELECT c FROM Cda c WHERE c.usuarioResp = :usuarioResp"),
    @NamedQuery(name = "Cda.findByContCda", query = "SELECT c FROM Cda c WHERE c.contCda = :contCda"),
    @NamedQuery(name = "Cda.findByProveedorSicov", query = "SELECT c FROM Cda c WHERE c.proveedorSicov = :proveedorSicov"),
    @NamedQuery(name = "Cda.findByUsuarioSicov", query = "SELECT c FROM Cda c WHERE c.usuarioSicov = :usuarioSicov"),
    @NamedQuery(name = "Cda.findByPasswordSicov", query = "SELECT c FROM Cda c WHERE c.passwordSicov = :passwordSicov"),
    @NamedQuery(name = "Cda.findByUrlServicioSicov", query = "SELECT c FROM Cda c WHERE c.urlServicioSicov = :urlServicioSicov"),
    @NamedQuery(name = "Cda.findByUrlServicioSicov2", query = "SELECT c FROM Cda c WHERE c.urlServicioSicov2 = :urlServicioSicov2"),
    @NamedQuery(name = "Cda.findByUrlServicioEncript", query = "SELECT c FROM Cda c WHERE c.urlServicioEncript = :urlServicioEncript"),
    @NamedQuery(name = "Cda.findByUnidadMovil", query = "SELECT c FROM Cda c WHERE c.unidadMovil = :unidadMovil")})
 */
public class Cda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cda")
    private Integer idCda;
    @Column(name = "CM")
    private String CM;
    @Column(name = "Total_Eq_2T")
    private Integer Total_Eq_2T;
    @Column(name = "Total_Eq_4T")
    private Integer Total_Eq_4T;
    @Basic(optional = false)
    @Column(name = "DIVIPOLA")
    private Integer divipola;
    @Column(name = "cont_cda")
    private boolean simulacion;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "NIT")
    private String nit;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "celular")
    private String celular;
    @Basic(optional = false)
    @Column(name = "RESOLUCION")
    private String resolucion;
    @Basic(optional = false)
    @Column(name = "fecha_resolucion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaResolucion;
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "nom_resp_certificados")
    private String nomRespCertificados;
    @Basic(optional = false)
    @Column(name = "Certificado_conformidad")
    private String certificadoconformidad;
    private String Correo;

    @Column(name = "Laboratorio_Pbaja")
    private String Laboratorio_Pbaja;
    
    @Column(name = "Laboratorio_Palta")
    private String Laboratorio_Palta;
    @Column(name = "Certificado_Pbaja")
    private String Certificado_Pbaja;
    @Column(name = "Certificado_Palta")
    private String Certificado_Palta;
    @Column(name = "Certificado_Palta2T")
    private String Certificado_Palta2T;
    
    @Column(name = "Nro_Expediente_Autoridad_Ambiental")
    private String Nro_Expediente_Autoridad_Ambiental;
    @Column(name = "Razon_Social")
    private String Razon_Social;
    @Column(name = "Resolucion_Ambiental")
    private String Resolucion_Ambiental;
    @Column(name = "Fecha_Resolucion_Ambiental")
    private String Fecha_Resolucion_Ambiental;
    @Column(name = "Total_Eq_Otto")
    private String Total_Eq_Otto;
    @Column(name = "Total_Eq_Diesel")
    private String Total_Eq_Diesel;

    public String getRazon_Social() {
        return Razon_Social;
    }

    public void setRazon_Social(String Razon_Social) {
        this.Razon_Social = Razon_Social;
    }

    public String getResolucion_Ambiental() {
        return Resolucion_Ambiental;
    }

    public void setResolucion_Ambiental(String Resolucion_Ambiental) {
        this.Resolucion_Ambiental = Resolucion_Ambiental;
    }

    public String getFecha_Resolucion_Ambiental() {
        return Fecha_Resolucion_Ambiental;
    }

    public void setFecha_Resolucion_Ambiental(String Fecha_Resolucion_Ambiental) {
        this.Fecha_Resolucion_Ambiental = Fecha_Resolucion_Ambiental;
    }

    public String getTotal_Eq_Otto() {
        return Total_Eq_Otto;
    }

    public void setTotal_Eq_Otto(String Total_Eq_Otto) {
        this.Total_Eq_Otto = Total_Eq_Otto;
    }

    public String getTotal_Eq_Diesel() {
        return Total_Eq_Diesel;
    }

    public void setTotal_Eq_Diesel(String Total_Eq_Diesel) {
        this.Total_Eq_Diesel = Total_Eq_Diesel;
    }

    
    
    
    
    public String getNro_Expediente_Autoridad_Ambiental() {
        return Nro_Expediente_Autoridad_Ambiental;
    }

    public void setNro_Expediente_Autoridad_Ambiental(String Nro_Expediente_Autoridad_Ambiental) {
        this.Nro_Expediente_Autoridad_Ambiental = Nro_Expediente_Autoridad_Ambiental;
    }

    public String getLaboratorio_Pbaja() {
        return Laboratorio_Pbaja;
    }

    public void setLaboratorio_Pbaja(String Laboratorio_Pbaja) {
        this.Laboratorio_Pbaja = Laboratorio_Pbaja;
    }

    public String getLaboratorio_Palta() {
        return Laboratorio_Palta;
    }

    public void setLaboratorio_Palta(String Laboratorio_Palta) {
        this.Laboratorio_Palta = Laboratorio_Palta;
    }

    public String getCertificado_Pbaja() {
        return Certificado_Pbaja;
    }

    public void setCertificado_Pbaja(String Certificado_Pbaja) {
        this.Certificado_Pbaja = Certificado_Pbaja;
    }

    public String getCertificado_Palta() {
        return Certificado_Palta;
    }

    public void setCertificado_Palta(String Certificado_Palta) {
        this.Certificado_Palta = Certificado_Palta;
    }

    public String getCertificado_Palta2T() {
        return Certificado_Palta2T;
    }

    public void setCertificado_Palta2T(String Certificado_Palta2T) {
        this.Certificado_Palta2T = Certificado_Palta2T;
    }
    
    
    
    
    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }
    /*
    *
    * Crear una variable String clase y mysql varchar
    *
     */
    @Column(name = "clase_cda")
    private char clase;
    @Column(name = "consecutivo_apr")
    private String consecutivoApr;
    @Column(name = "consecutivo_rep")
    private String consecutivoRep;
    @Column(name = "consecutivo_cert")
    private String consecutivoCert;
    /*@Column(name = "usuario_resp")
    private Integer usuarioResp;*/
 /*@Column(name = "cont_cda")
    private Short contCda;*/
    @Column(name = "proveedor_sicov")
    private String proveedorSicov;
    @Column(name = "usuario_sicov")
    private String usuarioSicov;
    @Column(name = "password_sicov")
    private String passwordSicov;
    @Column(name = "url_servicio_sicov")
    private String urlServicioSicov;
    @Column(name = "url_servicio_sicov2")
    private String urlServicioSicov2;
    @Column(name = "url_servicio_encript")
    private String urlServicioEncript;
    @Basic(optional = false)

    @Column(name = "ciudad")
    private String ciudad;
    @Basic(optional = true)
    @Column(name = "usuario_resp")
    private Integer idUsuarioResponsable;

    public Cda() {
    }

    public Cda(Integer idCda) {
        this.idCda = idCda;
    }

    public Cda(Integer idCda, String nombre, String nit, String direccion, String telefono, String resolucion, Date fechaResolucion, String nomRespCertificados, String certificadoconformidad, char clase) {
        this.idCda = idCda;
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.resolucion = resolucion;
        this.fechaResolucion = fechaResolucion;
        this.nomRespCertificados = nomRespCertificados;
        this.certificadoconformidad = certificadoconformidad;
        // Crear nuevas filas por Diego Garzon 
        this.clase = clase;
    }

    public void setSimulacion(boolean simulacion) {
        this.simulacion = simulacion;
    }

    public boolean isSimulacion() {
        return simulacion;
    }

    public Integer getIdCda() {
        return idCda;
    }

    public void setIdCda(Integer idCda) {
        this.idCda = idCda;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getDivipola() {
        return divipola;
    }

    public void setDivipola(Integer divipola) {
        this.divipola = divipola;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public Date getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(Date fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNomRespCertificados() {
        return nomRespCertificados;
    }

    public void setNomRespCertificados(String nomRespCertificados) {
        this.nomRespCertificados = nomRespCertificados;
    }

    public String getCertificadoconformidad() {
        return certificadoconformidad;
    }

    public void setCertificadoconformidad(String certificadoconformidad) {
        this.certificadoconformidad = certificadoconformidad;
    }

    /*
    *
    *
    * Creamos los get y set de Clase editdo por Diego Garzon 
    *
    *
     */
    public char getClase() {
        return clase;
    }

    public void setClase(char clase) {
        this.clase = clase;
    }

    public String getConsecutivoApr() {
        return consecutivoApr;
    }

    public void setConsecutivoApr(String consecutivoApr) {
        this.consecutivoApr = consecutivoApr;
    }

    public String getConsecutivoRep() {
        return consecutivoRep;
    }

    public void setConsecutivoRep(String consecutivoRep) {
        this.consecutivoRep = consecutivoRep;
    }

    public String getConsecutivoCert() {
        return consecutivoCert;
    }

    public void setConsecutivoCert(String consecutivoCert) {
        this.consecutivoCert = consecutivoCert;
    }

    /*public Integer getUsuarioResp() {
        return usuarioResp;
    }

    public void setUsuarioResp(Integer usuarioResp) {
        this.usuarioResp = usuarioResp;
    }

    public Short getContCda() {
        return contCda;
    }

    public void setContCda(Short contCda) {
        this.contCda = contCda;
    }*/
    public String getProveedorSicov() {
        return proveedorSicov;
    }

    public void setProveedorSicov(String proveedorSicov) {
        this.proveedorSicov = proveedorSicov;
    }

    public String getUsuarioSicov() {
        return usuarioSicov;
    }

    public void setUsuarioSicov(String usuarioSicov) {
        this.usuarioSicov = usuarioSicov;
    }

    public String getPasswordSicov() {
        return passwordSicov;
    }

    public void setPasswordSicov(String passwordSicov) {
        this.passwordSicov = passwordSicov;
    }

    public String getUrlServicioSicov() {
        return urlServicioSicov;
    }

    public void setUrlServicioSicov(String urlServicioSicov) {
        this.urlServicioSicov = urlServicioSicov;
    }

    public String getUrlServicioSicov2() {
        return urlServicioSicov2;
    }

    public void setUrlServicioSicov2(String urlServicioSicov2) {
        this.urlServicioSicov2 = urlServicioSicov2;
    }

    public String getUrlServicioEncript() {
        return urlServicioEncript;
    }

    public void setUrlServicioEncript(String urlServicioEncript) {
        this.urlServicioEncript = urlServicioEncript;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getIdUsuarioResponsable() {
        return idUsuarioResponsable;
    }

    public void setIdUsuarioResponsable(Integer idUsuarioResponsable) {
        this.idUsuarioResponsable = idUsuarioResponsable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCda != null ? idCda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cda)) {
            return false;
        }
        Cda other = (Cda) object;
        if ((this.idCda == null && other.idCda != null) || (this.idCda != null && !this.idCda.equals(other.idCda))) {
            return false;
        }
        return true;
    }

    public String getCM() {
        return CM;
    }

    public void setCM(String CM) {
        this.CM = CM;
    }

    public Integer getTotal_Eq_2T() {
        return Total_Eq_2T;
    }

    public void setTotal_Eq_2T(Integer Total_Eq_2T) {
        this.Total_Eq_2T = Total_Eq_2T;
    }

    public Integer getTotal_Eq_4T() {
        return Total_Eq_4T;
    }

    public void setTotal_Eq_4T(Integer Total_Eq_4T) {
        this.Total_Eq_4T = Total_Eq_4T;
    }

    @Override
    public String toString() {
        return "com.soltelec.servidor.model.Cda[ idCda=" + idCda + " ]";
    }

}
