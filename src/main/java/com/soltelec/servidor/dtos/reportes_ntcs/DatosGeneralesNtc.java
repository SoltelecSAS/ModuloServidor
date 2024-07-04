package com.soltelec.servidor.dtos.reportes_ntcs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatosGeneralesNtc {
    private String
    tipIdeDt,
    numIdeDt,
    nomDt,
    tipIdeIt,
    numIdeIt,
    nomIt,
    numFur,
    fechaFur,
    consRunt,
    furAsoc,
    certRtmyg,
    fIniInsp,
    fFinInsp,
    fAborto,
    cAborto,
    catalizardor,
    lugarTemp,
    tAmb,
    hRel;
}
