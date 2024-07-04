/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Clase encargada de cargar los defectos de inspecion sensorial
 *
 * @requerimiento SART-11 Reporte de inspección sensorial
 * @author Luis Berna
 * @fecha 03/02/2016
 */
@Entity
public class DefectoSensorial implements Serializable {

    @Id
    @Column(name = "CODIGO")
    private String codigo;
    @Column(name = "DEFECTO")
    private String defecto;
    @Column(name = "CONSEPTO")
    private String consepto;

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the defecto
     */
    public String getDefecto() {
        return defecto;
    }

    /**
     * @param defecto the defecto to set
     */
    public void setDefecto(String defecto) {
        this.defecto = defecto;
    }

    /**
     * @return the consepto
     */
    public String getConsepto() {
        return consepto;
    }

    /**
     * @param consepto the consepto to set
     */
    public void setConsepto(String consepto) {
        this.consepto = consepto;
    }

    @Override
    public String toString() {
        return "DefectoSensorial{" + "codigo=" + codigo + ", defecto=" + defecto + ", consepto=" + consepto + '}';
    }

}
