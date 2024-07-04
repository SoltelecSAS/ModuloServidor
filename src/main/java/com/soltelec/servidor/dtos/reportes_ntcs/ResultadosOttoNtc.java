package com.soltelec.servidor.dtos.reportes_ntcs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultadosOttoNtc {
    private String 
    tMotor,
    rpmRal,
    rpmCru,
    humo,
    corrO2,
    dilucion,
    rpmFuera,
    fugaTubo,
    salidasAd,
    fugaAceite,
    fugaComb,
    admisionNc,
    recirculacion,
    accTubo,
    refrigNc,
    rHcRal,
    rHcCru,
    rCoRal,
    rCoCru,
    rCo2Ral,
    rCo2Cru,
    rO2Ral,
    rO2Cru,
    rGasScor,
    ncEmisiones,
    resFinal;
}
