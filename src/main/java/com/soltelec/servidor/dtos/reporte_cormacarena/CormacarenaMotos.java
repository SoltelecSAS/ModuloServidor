package com.soltelec.servidor.dtos.reporte_cormacarena;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CormacarenaMotos {
    private String marca;
    private String modelo;
    private String tiempos;
    private String coRalenti;
    private String co2Ralenti;
    private String o2Ralenti;
    private String hcRalenti;
    private String tempAmbiente;
    private String humedadRelativa;
    private String resultado;
    private String nivelDeEmisionRuido;
    private String fechaPrueba;
    private String placa;
}
