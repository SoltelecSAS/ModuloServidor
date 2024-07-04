package com.soltelec.servidor.dtos.reporte_corpocaldas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EncendidoCompresionCorpocaldas {
    private String rpmRalenti;
    private String rpmGobernadaCicloPreliminar;
    private String resultadoCicloPreliminar;
    private String rpmRalentoCiclo1;
    private String rpmGobernadaCiclo1;
    private String resultadoCiclo1;
    private String rpmRalentiCiclo2;
    private String rpmGobernadaCiclo2;
    private String resultadoCiclo2;
    private String rpmRalentiCiclo3;
    private String rpmGobernadaCiclo3;
    private String resultadoCiclo3;
    private String promedioFinal;
    private String cicloPreliminarM_1;
    private String ciclo1M_1;
    private String ciclo2M_1;
    private String ciclo3M_1;
    private String promedioFinalM_1;
    private String conceptoFinal;
}
