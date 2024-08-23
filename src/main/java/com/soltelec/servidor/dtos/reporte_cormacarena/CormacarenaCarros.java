package com.soltelec.servidor.dtos.reporte_cormacarena;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CormacarenaCarros {
    private String marca;
    private String modelo;
    private String cilindraje;
    private String clase;
    private String servicio;
    private String hcRalenti;
    private String coRalenti;
    private String co2Ralenti;
    private String o2Ralenti;
    private String rpmCrucero;
    private String hcCrucero;
    private String coCrucero;
    private String co2Crucero;
    private String o2Crucero;
    private String resultado;
    private String nivelDeEmision;
    private String fechaPrueba;
    private String placa;
}
