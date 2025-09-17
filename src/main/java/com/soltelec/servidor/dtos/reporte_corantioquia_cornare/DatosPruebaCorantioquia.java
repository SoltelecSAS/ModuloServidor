package com.soltelec.servidor.dtos.reporte_corantioquia_cornare;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatosPruebaCorantioquia {
    private LocalDateTime fechaInicioPrueba;
    private LocalDateTime fechaFinPrueba;
    private String nombreUsuario;
    private String temperaturaAmbiente;
    private String humedadRelativa;
    private String ciudad;
    private String direccion;
}
