package com.soltelec.servidor.dtos.reportes_ntcs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PropietariosNtc {
    private String tipIdeProp,
    numIdeProp,
    nomProp,
    dirProp,
    tel1Prop,
    tel2Prop,
    munProp,
    corrEProp;
}
