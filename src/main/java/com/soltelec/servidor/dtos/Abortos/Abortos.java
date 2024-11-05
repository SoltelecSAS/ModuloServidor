package com.soltelec.servidor.dtos.Abortos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Abortos {
    private String tipoPrueba;
    private String fechaAborto;
    private String motivoAborto;
    private String detallesAborto;
    private String operario;
}
