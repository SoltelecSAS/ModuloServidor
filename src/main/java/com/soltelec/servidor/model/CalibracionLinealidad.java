/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Dany
 */
@Entity
@Table(name = "calibraciones")
@DiscriminatorValue(value = "1")
public class CalibracionLinealidad extends Calibraciones{
    
}
