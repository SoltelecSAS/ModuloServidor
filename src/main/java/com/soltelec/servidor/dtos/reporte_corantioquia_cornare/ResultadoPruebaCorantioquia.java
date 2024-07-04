package com.soltelec.servidor.dtos.reporte_corantioquia_cornare;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultadoPruebaCorantioquia {
    private String temperaturaMotor;
    private String nCilindros;
    private String rpmRalenti;
    private String hcRalenti;
    private String coRalenti;
    private String co2Ralenti;
    private String o2Ralenti;
    private String presenciaDilucion;
    private String conceptoFinal;
}
