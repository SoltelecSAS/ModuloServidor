/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Gerencia Desarrollo de Soluciones Tecnologicas
 */
@Entity
@Table(name = "hoja_pruebas")
@NamedQueries({
    @NamedQuery(name = "HojaPruebas.findAll", query = "SELECT h FROM HojaPruebas h"),
    @NamedQuery(name = "HojaPruebas.findByTestsheet", query = "SELECT h FROM HojaPruebas h WHERE h.testsheet = :testsheet"),
    @NamedQuery(name = "HojaPruebas.findByHojaactivaactiveflag", query = "SELECT h FROM HojaPruebas h WHERE h.hojaactivaactiveflag = :hojaactivaactiveflag"),
    @NamedQuery(name = "HojaPruebas.findByFinalizada", query = "SELECT h FROM HojaPruebas h WHERE h.finalizada = :finalizada"),
    @NamedQuery(name = "HojaPruebas.findByImpreso", query = "SELECT h FROM HojaPruebas h WHERE h.impreso = :impreso"),
    @NamedQuery(name = "HojaPruebas.findByFechaingresovehiculo", query = "SELECT h FROM HojaPruebas h WHERE h.fechaingresovehiculo = :fechaingresovehiculo"),
    @NamedQuery(name = "HojaPruebas.findByAnulado", query = "SELECT h FROM HojaPruebas h WHERE h.anulado = :anulado"),
    @NamedQuery(name = "HojaPruebas.findByAprobado", query = "SELECT h FROM HojaPruebas h WHERE h.aprobado = :aprobado"),
    @NamedQuery(name = "HojaPruebas.findByFechaexpiracionrevision", query = "SELECT h FROM HojaPruebas h WHERE h.fechaexpiracionrevision = :fechaexpiracionrevision"),
    @NamedQuery(name = "HojaPruebas.findByConductor", query = "SELECT h FROM HojaPruebas h WHERE h.conductor = :conductor"),
    @NamedQuery(name = "HojaPruebas.findByConsecutivoresolucion", query = "SELECT h FROM HojaPruebas h WHERE h.consecutivoresolucion = :consecutivoresolucion"),
    @NamedQuery(name = "HojaPruebas.findByCerrada", query = "SELECT h FROM HojaPruebas h WHERE h.cerrada = :cerrada"),
    @NamedQuery(name = "HojaPruebas.findByFechaexpedicioncertificados", query = "SELECT h FROM HojaPruebas h WHERE h.fechaexpedicioncertificados = :fechaexpedicioncertificados"),
    @NamedQuery(name = "HojaPruebas.findByNombrefoto", query = "SELECT h FROM HojaPruebas h WHERE h.nombrefoto = :nombrefoto"),
    @NamedQuery(name = "HojaPruebas.findByNumerointentos", query = "SELECT h FROM HojaPruebas h WHERE h.numerointentos = :numerointentos"),
    @NamedQuery(name = "HojaPruebas.findByIdFotosFor", query = "SELECT h FROM HojaPruebas h WHERE h.idFotosFor = :idFotosFor")})
public class HojaPruebas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TESTSHEET")
    private Integer testsheet;
    @Column(name = "Hoja_activa_activeflag")
    private String hojaactivaactiveflag;
    @Basic(optional = false)
    @Column(name = "Finalizada")
    private String finalizada;
    @Column(name = "Impreso")
    private String impreso;
    @Basic(optional = false)
    @Column(name = "Fecha_ingreso_vehiculo")
    @Temporal(TemporalType.TIMESTAMP)
    public Date fechaingresovehiculo;
    @Column(name = "Anulado")
    private String anulado;
    @Column(name = "Aprobado")
    private String aprobado;
    @Basic(optional = false)
    @Column(name = "Fecha_expiracion_revision")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaexpiracionrevision;
    @JoinColumn(name = "Conductor", referencedColumnName = "CAROWNER")
    @ManyToOne(optional = false)
    private Propietarios conductor;
    @Column(name = "ubicacion_municipio")
    private String ubicacionMunicipio;
    @Column(name = "forma_med_temp")
    private String formaMedTemperatura;
    @Column(name = "Consecutivo_resolucion")
    private String consecutivoresolucion;
    @Column(name = "Cerrada")
    private String cerrada;
    @Basic(optional = false)
    @Column(name = "Fecha_expedicion_certificados")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaexpedicioncertificados;
    @Lob
    @Column(name = "Comentarios_cda")
    private String comentarioscda;
    @Column(name = "Nombre_foto")
    private String nombrefoto;
    @Column(name = "estado_sicov")
    private String estado_sicov;
    @Column(name = "preventiva")
    private String preventiva;
    @Basic(optional = false)
    @Column(name = "Numero_intentos")
    private int numerointentos;
    @Basic(optional = false)
    @Column(name = "con_hoja_prueba")
    private int conHojaPrueba;
    @Column(name = "id_fotos_for")
    private Integer idFotosFor;
    @JoinColumn(name = "Vehiculo_for", referencedColumnName = "CAR")
    @ManyToOne(optional = false)
    public Vehiculos vehiculos;
    @JoinColumn(name = "Usuario_for", referencedColumnName = "GEUSER")
    @ManyToOne(optional = false)
    private Usuarios usuarios;
    @JoinColumn(name = "usuario_resp", referencedColumnName = "GEUSER")
    @ManyToOne(optional = false)
    private Usuarios usuario_resp;
    @JoinColumn(name = "Propietario_for", referencedColumnName = "CAROWNER")
    @ManyToOne(optional = false)
    private Propietarios propietarios;

    @Column(name = "consecutivo_runt")
    private String consecutivoRunt;
    @Column(name = "fecha_venc_gnv")
    private String fecha_venc_gnv;

    public String getFecha_venc_gnv() {
        return fecha_venc_gnv;
    }

    public void setFecha_venc_gnv(String fecha_venc_gnv) {
        this.fecha_venc_gnv = fecha_venc_gnv;
    }
    
    

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hojaPruebas", fetch= FetchType.LAZY)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hojaPruebas", fetch = FetchType.EAGER)
    private Collection<Pruebas> pruebasCollection;
    private List<Pruebas> pruebasList;
    @OneToMany(mappedBy = "hojaPruebas", fetch = FetchType.LAZY)
    private List<Reinspeccion> reinspeccionList;
///---
    @Column(name = "Kilometraje_rtm")
    private String kilometrajeRtm;

    public String getKilometrajeRtm() {
        return kilometrajeRtm;
    }

    public void setKilometrajeRtm(String kilometrajeRtm) {
        this.kilometrajeRtm = kilometrajeRtm;
    }
///---    

    public String getConsecutivoRunt() {
        return consecutivoRunt;
    }

    public void setConsecutivoRunt(String consecutivoRunt) {
        this.consecutivoRunt = consecutivoRunt;
    }
///---    

    public HojaPruebas() {
    }

    public HojaPruebas(Integer testsheet) {
        this.testsheet = testsheet;
    }

    public HojaPruebas(Integer testsheet, String finalizada, Date fechaingresovehiculo, Date fechaexpiracionrevision, Propietarios conductor, Date fechaexpedicioncertificados, int numerointentos) {
        this.testsheet = testsheet;
        this.finalizada = finalizada;
        this.fechaingresovehiculo = fechaingresovehiculo;
        this.fechaexpiracionrevision = fechaexpiracionrevision;
        this.conductor = conductor;
        this.fechaexpedicioncertificados = fechaexpedicioncertificados;
        this.numerointentos = numerointentos;
    }

    public Integer getTestsheet() {
        return testsheet;
    }

    public void setTestsheet(Integer testsheet) {
        this.testsheet = testsheet;
    }

    public String getHojaactivaactiveflag() {
        return hojaactivaactiveflag;
    }

    public void setHojaactivaactiveflag(String hojaactivaactiveflag) {
        this.hojaactivaactiveflag = hojaactivaactiveflag;
    }

    public String getPreventiva() {
        return preventiva;
    }

    public void setPreventiva(String preventiva) {
        this.preventiva = preventiva;
    }

    public String getUbicacionMunicipio() {
        return ubicacionMunicipio;
    }

    public void setUbicacionMunicipio(String ubicacionMunicipio) {
        this.ubicacionMunicipio = ubicacionMunicipio;
    }

    public String getFinalizada() {
        return finalizada;
    }

    public void setFinalizada(String finalizada) {
        this.finalizada = finalizada;
    }

    public String getImpreso() {
        return impreso;
    }

    public void setImpreso(String impreso) {
        this.impreso = impreso;
    }

    public Date getFechaingresovehiculo() {
        return fechaingresovehiculo;
    }

    public void setFechaingresovehiculo(Date fechaingresovehiculo) {
        this.fechaingresovehiculo = fechaingresovehiculo;
    }

    public String getAnulado() {
        return anulado;
    }

    public void setAnulado(String anulado) {
        this.anulado = anulado;
    }

    public String getAprobado() {
        return aprobado;
    }

    public void setAprobado(String aprobado) {
        this.aprobado = aprobado;
    }

    public Date getFechaexpiracionrevision() {
        return fechaexpiracionrevision;
    }

    public void setFechaexpiracionrevision(Date fechaexpiracionrevision) {
        this.fechaexpiracionrevision = fechaexpiracionrevision;
    }

    public String getConsecutivoresolucion() {
        return consecutivoresolucion;
    }

    public void setConsecutivoresolucion(String consecutivoresolucion) {
        this.consecutivoresolucion = consecutivoresolucion;
    }

    public String getFormaMedTemperatura() {
        return formaMedTemperatura;
    }

    public void setFormaMedTemperatura(String formaMedTemperatura) {
        this.formaMedTemperatura = formaMedTemperatura;
    }

    public String getCerrada() {
        return cerrada;
    }

    public void setCerrada(String cerrada) {
        this.cerrada = cerrada;
    }

    public Date getFechaexpedicioncertificados() {
        return fechaexpedicioncertificados;
    }

    public void setFechaexpedicioncertificados(Date fechaexpedicioncertificados) {
        this.fechaexpedicioncertificados = fechaexpedicioncertificados;
    }

    public String getComentarioscda() {
        return comentarioscda;
    }

    public void setComentarioscda(String comentarioscda) {
        this.comentarioscda = comentarioscda;
    }

    public String getNombrefoto() {
        return nombrefoto;
    }

    public void setNombrefoto(String nombrefoto) {
        this.nombrefoto = nombrefoto;
    }

    public int getNumerointentos() {
        return numerointentos;
    }

    public void setNumerointentos(int numerointentos) {
        this.numerointentos = numerointentos;
    }

    public Integer getIdFotosFor() {
        return idFotosFor;
    }

    public void setIdFotosFor(Integer idFotosFor) {
        this.idFotosFor = idFotosFor;
    }

    public Vehiculos getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(Vehiculos vehiculos) {
        this.vehiculos = vehiculos;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public Propietarios getPropietarios() {
        return propietarios;
    }

    public void setPropietarios(Propietarios propietarios) {
        this.propietarios = propietarios;
    }

    public Propietarios getConductor() {
        return conductor;
    }

    public void setConductor(Propietarios conductor) {
        this.conductor = conductor;
    }

    public Collection<Pruebas> getPruebasCollection() {
        return pruebasCollection;
    }

    public void setPruebasCollection(Collection<Pruebas> pruebasCollection) {
        this.pruebasCollection = pruebasCollection;
    }

    public List<Pruebas> getPruebasList() {
        return pruebasList;
    }

    public void setPruebasList(List<Pruebas> pruebasList) {
        this.pruebasList = pruebasList;
    }

    public List<Reinspeccion> getReinspeccionList2() {

        return reinspeccionList;
    }

    public Usuarios getUsuario_resp() {
        return usuario_resp;
    }

    public void setUsuario_resp(Usuarios usuario_resp) {
        this.usuario_resp = usuario_resp;
    }
    
    

    /*public List<Reinspeccion> getReinspeccionList() {
        return reinspeccionList;
    }*/

    public void setReinspeccionList(List<Reinspeccion> reinspeccionList) {
        this.reinspeccionList = reinspeccionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (testsheet != null ? testsheet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HojaPruebas)) {
            return false;
        }
        HojaPruebas other = (HojaPruebas) object;
        if ((this.testsheet == null && other.testsheet != null) || (this.testsheet != null && !this.testsheet.equals(other.testsheet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.soltelec.model.HojaPruebas[testsheet=" + testsheet + "]";
    }

    /**
     * @return the conHojaPrueba
     */
    public int getConHojaPrueba() {
        return conHojaPrueba;
    }

    /**
     * @param conHojaPrueba the conHojaPrueba to set
     */
    public void setConHojaPrueba(int conHojaPrueba) {
        this.conHojaPrueba = conHojaPrueba;
    }

    public String getEstado_sicov() {
        return estado_sicov;
    }

    public void setEstado_sicov(String estado_sicov) {
        this.estado_sicov = estado_sicov;
    }

}
