package com.soltelec.servidor.dtos.reportes_ntcs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EquiposSoftDieselNtc {
    private String 
    marcaAg,
    modAg,
    serialAg,
    marcaBg,
    modBg,
    serialBg,
    ltoe,
    serialE,
    marcaRpm,
    serialRpm,
    marcaTempM,
    serialTempM,
    marcaTempA,
    serialTempA,
    marcaHumR,
    serialHumR,
    nomSoft,
    verSoft,
    desSoft,
    tipIdeLin,
    numIdeLin,
    nomLin,
    fechaLin,
    pAltoLab,
    pAltoSerial,
    pAltoCer,
    vFdnAlto,
    pBajoLab,
    pBajoSerial,
    pBajoCer,
    vFdnBajo,
    rFdnCero,
    rFdnBajo,
    rFdnAlto,
    rFndCien,
    rLin;
}
