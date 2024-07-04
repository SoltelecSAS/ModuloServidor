package com.soltelec.servidor.dtos.reportes_ntcs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultadosDieselNtc {
    private String
    tInicialMotor,
    tFinalMotor,
    rpmRal,
    rpmGob,
    rpmFuera,
    fugaTubo,
    salidasAd,
    fugaAceite,
    fugaComb,
    admicionNc,
    acDisp,
    accTubo,
    refrigNc,
    difTemp10,
    gobNc,
    funMotor,
    accSubita,
    fallaSubita,
    difAritm,
    ncEmisiones,
    rpmRalPre,
    rpmGobPre,
    rOpPre,
    rDenPre,
    rpmRalC1,
    rpmGobC1,
    rOpC1,
    rDenC1,
    rpmRalC2,
    rpmGobC2,
    rOpC2,
    rDenC2,
    rpmRalC3,
    rpmGobC3,
    rOpC3,
    rDenC3,
    rFinalOp,
    rFinalDen,
    resFinal;
}
