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
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Luis Berna Clase entidad encargada de contener los datos de la
 * consulta de la super intendencia
 * @since 29/01/2016
 * @Requerimiento SART-5
 */
@Entity
public class NativoVehiculosSuperIntendencia implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "PLACA")
    private String placa;
    @Column(name = "MARCA")
    private String marca;
    @Column(name = "MODELO")
    private String modelo;
    @Column(name = "PARTICULAR")
    private String particular;
    @Column(name = "PUBLICO")
    private String publico;
    @Column(name = "MOTOS")
    private String motos;
    @Column(name = "AUTOMOVIL")
    private String automovil;
    @Column(name = "CAMPERO")
    private String campero;
    @Column(name = "CAMIONETA")
    private String camioneta;
    @Column(name = "MICROBUS")
    private String microbus;
    @Column(name = "BUS")
    private String bus;
    @Column(name = "BUSETA")
    private String buseta;
    @Column(name = "CAMION")
    private String camion;
    @Column(name = "VOLQUETA")
    private String volqueta;
    @Column(name = "TRACTO_CAMION")
    private String tractoCamion;
    @Column(name = "R")
    private String r;
    @Column(name = "A")
    private String a;
    @Column(name = "1°")
    private String uno;
    @Column(name = "2°")
    private String dos;
    @Column(name = "3°")
    private String tres;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "NO_CERTIFICADO")
    private String noCertificado;
    @Column(name = "OBSERVACIONES")
    private String observaciones;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the particular
     */
    public String getParticular() {
        return particular;
    }

    /**
     * @param particular the particular to set
     */
    public void setParticular(String particular) {
        this.particular = particular;
    }

    /**
     * @return the publico
     */
    public String getPublico() {
        return publico;
    }

    /**
     * @param publico the publico to set
     */
    public void setPublico(String publico) {
        this.publico = publico;
    }

    /**
     * @return the motos
     */
    public String getMotos() {
        return motos;
    }

    /**
     * @param motos the motos to set
     */
    public void setMotos(String motos) {
        this.motos = motos;
    }

    /**
     * @return the automovil
     */
    public String getAutomovil() {
        return automovil;
    }

    /**
     * @param automovil the automovil to set
     */
    public void setAutomovil(String automovil) {
        this.automovil = automovil;
    }

    /**
     * @return the campero
     */
    public String getCampero() {
        return campero;
    }

    /**
     * @param campero the campero to set
     */
    public void setCampero(String campero) {
        this.campero = campero;
    }

    /**
     * @return the camioneta
     */
    public String getCamioneta() {
        return camioneta;
    }

    /**
     * @param camioneta the camioneta to set
     */
    public void setCamioneta(String camioneta) {
        this.camioneta = camioneta;
    }

    /**
     * @return the microbus
     */
    public String getMicrobus() {
        return microbus;
    }

    /**
     * @param microbus the microbus to set
     */
    public void setMicrobus(String microbus) {
        this.microbus = microbus;
    }

    /**
     * @return the bus
     */
    public String getBus() {
        return bus;
    }

    /**
     * @param bus the bus to set
     */
    public void setBus(String bus) {
        this.bus = bus;
    }

    /**
     * @return the buseta
     */
    public String getBuseta() {
        return buseta;
    }

    /**
     * @param buseta the buseta to set
     */
    public void setBuseta(String buseta) {
        this.buseta = buseta;
    }

    /**
     * @return the camion
     */
    public String getCamion() {
        return camion;
    }

    /**
     * @param camion the camion to set
     */
    public void setCamion(String camion) {
        this.camion = camion;
    }

    /**
     * @return the volqueta
     */
    public String getVolqueta() {
        return volqueta;
    }

    /**
     * @param volqueta the volqueta to set
     */
    public void setVolqueta(String volqueta) {
        this.volqueta = volqueta;
    }

    /**
     * @return the tractoCamion
     */
    public String getTractoCamion() {
        return tractoCamion;
    }

    /**
     * @param tractoCamion the tractoCamion to set
     */
    public void setTractoCamion(String tractoCamion) {
        this.tractoCamion = tractoCamion;
    }

    /**
     * @return the r
     */
    public String getR() {
        return r;
    }

    /**
     * @param r the r to set
     */
    public void setR(String r) {
        this.r = r;
    }

    /**
     * @return the a
     */
    public String getA() {
        return a;
    }

    /**
     * @param a the a to set
     */
    public void setA(String a) {
        this.a = a;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the noCertificado
     */
    public String getNoCertificado() {
        return noCertificado;
    }

    /**
     * @param noCertificado the noCertificado to set
     */
    public void setNoCertificado(String noCertificado) {
        this.noCertificado = noCertificado;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the uno
     */
    public String getUno() {
        return uno;
    }

    /**
     * @param uno the uno to set
     */
    public void setUno(String uno) {
        this.uno = uno;
    }

    /**
     * @return the dos
     */
    public String getDos() {
        return dos;
    }

    /**
     * @param dos the dos to set
     */
    public void setDos(String dos) {
        this.dos = dos;
    }

    /**
     * @return the tres
     */
    public String getTres() {
        return tres;
    }

    /**
     * @param tres the tres to set
     */
    public void setTres(String tres) {
        this.tres = tres;
    }

    @Override
    public String toString() {
        return "NativoVehiculosSuperIntendencia{" + "id=" + id + ", placa=" + placa + ", marca=" + marca + ", modelo=" + modelo + ", particular=" + particular + ", publico=" + publico + ", motos=" + motos + ", automovil=" + automovil + ", campero=" + campero + ", camioneta=" + camioneta + ", microbus=" + microbus + ", bus=" + bus + ", buseta=" + buseta + ", camion=" + camion + ", volqueta=" + volqueta + ", tractoCamion=" + tractoCamion + ", r=" + r + ", a=" + a + ", fecha=" + fecha + ", noCertificado=" + noCertificado + ", observaciones=" + observaciones + '}';
    }

}
