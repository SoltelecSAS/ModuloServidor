package com.soltelec.servidor.dtos.fugas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Fugas {
    String nombre,
    serial,
    pef,
    fecha,
    usuario,
    estado;
}
