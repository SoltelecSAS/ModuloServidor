/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.model;

import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author user
@entity
*/

public class CalibracionDTO {

    /**
     * @return the alarma
     */
    public int getAlarma() {
        return alarma;
    }

    /**
     * @param alarma the alarma to set
     */
    public void setAlarma(int alarma) {
        this.alarma = alarma;
    }

    /**
     * @return the curdate
     */
    public Date getCurdate() {
        return curdate;
    }

    /**
     * @param curdate the curdate to set
     */
    public void setCurdate(Date curdate) {
        this.curdate = curdate;
    }

    /**
     * @return the calibration
     */
    public int getCalibration() {
        return calibration;
    }

    /**
     * @param calibration the calibration to set
     */
    public void setCalibration(int calibration) {
        this.calibration = calibration;
    }

    /**
     * @return the bmCo
     */
    public double getBmCo() {
        return bmCo;
    }

    /**
     * @param bmCo the bmCo to set
     */
    public void setBmCo(double bmCo) {
        this.bmCo = bmCo;
    }

    /**
     * @return the bmCo2
     */
    public double getBmCo2() {
        return bmCo2;
    }

    /**
     * @param bmCo2 the bmCo2 to set
     */
    public void setBmCo2(double bmCo2) {
        this.bmCo2 = bmCo2;
    }

    /**
     * @return the bmHc
     */
    public double getBmHc() {
        return bmHc;
    }

    /**
     * @param bmHc the bmHc to set
     */
    public void setBmHc(double bmHc) {
        this.bmHc = bmHc;
    }

    /**
     * @return the altaCo
     */
    public double getAltaCo() {
        return altaCo;
    }

    /**
     * @param altaCo the altaCo to set
     */
    public void setAltaCo(double altaCo) {
        this.altaCo = altaCo;
    }

    /**
     * @return the altaCo2
     */
    public double getAltaCo2() {
        return altaCo2;
    }

    /**
     * @param altaCo2 the altaCo2 to set
     */
    public void setAltaCo2(double altaCo2) {
        this.altaCo2 = altaCo2;
    }

    /**
     * @return the altaHc
     */
    public double getAltaHc() {
        return altaHc;
    }

    /**
     * @param altaHc the altaHc to set
     */
    public void setAltaHc(double altaHc) {
        this.altaHc = altaHc;
    }

    /**
     * @return the bancoBmCo
     */
    public double getBancoBmCo() {
        return bancoBmCo;
    }

    /**
     * @param bancoBmCo the bancoBmCo to set
     */
    public void setBancoBmCo(double bancoBmCo) {
        this.bancoBmCo = bancoBmCo;
    }

    /**
     * @return the bancoBmO2
     */
    public double getBancoBmO2() {
        return bancoBmO2;
    }

    /**
     * @param bancoBmO2 the bancoBmO2 to set
     */
    public void setBancoBmO2(double bancoBmO2) {
        this.bancoBmO2 = bancoBmO2;
    }

    /**
     * @return the bancoBmCo2
     */
    public double getBancoBmCo2() {
        return bancoBmCo2;
    }

    /**
     * @param bancoBmCo2 the bancoBmCo2 to set
     */
    public void setBancoBmCo2(double bancoBmCo2) {
        this.bancoBmCo2 = bancoBmCo2;
    }

    /**
     * @return the bancoBmHc
     */
    public double getBancoBmHc() {
        return bancoBmHc;
    }

    /**
     * @param bancoBmHc the bancoBmHc to set
     */
    public void setBancoBmHc(double bancoBmHc) {
        this.bancoBmHc = bancoBmHc;
    }

    /**
     * @return the bancoAltaCo
     */
    public double getBancoAltaCo() {
        return bancoAltaCo;
    }

    /**
     * @param bancoAltaCo the bancoAltaCo to set
     */
    public void setBancoAltaCo(double bancoAltaCo) {
        this.bancoAltaCo = bancoAltaCo;
    }

    /**
     * @return the bancoAltaCo2
     */
    public double getBancoAltaCo2() {
        return bancoAltaCo2;
    }

    /**
     * @param bancoAltaCo2 the bancoAltaCo2 to set
     */
    public void setBancoAltaCo2(double bancoAltaCo2) {
        this.bancoAltaCo2 = bancoAltaCo2;
    }

    /**
     * @return the bancoAltaO2
     */
    public double getBancoAltaO2() {
        return bancoAltaO2;
    }

    /**
     * @param bancoAltaO2 the bancoAltaO2 to set
     */
    public void setBancoAltaO2(double bancoAltaO2) {
        this.bancoAltaO2 = bancoAltaO2;
    }

    /**
     * @return the bancoAltaHc
     */
    public double getBancoAltaHc() {
        return bancoAltaHc;
    }

    /**
     * @param bancoAltaHc the bancoAltaHc to set
     */
    public void setBancoAltaHc(double bancoAltaHc) {
        this.bancoAltaHc = bancoAltaHc;
    }
    
    private int alarma;
    private Date curdate;
    private int calibration;
    private double bmCo;
    private double bmCo2;
    private double bmHc;
    private double altaCo;
    private double altaCo2;
    private double altaHc;
    private double bancoBmCo;
    private double bancoBmO2;
    private double bancoBmCo2;
    private double bancoBmHc;
    private double bancoAltaCo;
    private double bancoAltaCo2;
    private double bancoAltaO2;
    private double bancoAltaHc;
    
}
