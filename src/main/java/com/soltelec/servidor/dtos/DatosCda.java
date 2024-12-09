package com.soltelec.servidor.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatosCda {
    private String cm;
    private String nombreCda;
    private String tipoDocumento;
    private String NIT;
    private String personaContacto;
    private String correoElectronico;
    private String telefono;
    private String celular;
    private String departamento;
    private String ciudad;
    private String direccion;
    private String NoResolucionAuthAmbiental;
    private String fechaResolucionAuthAmbiental;
    private String claseCda;
    private String noExpedienteAuthAmbiental;
    private String nTotalOpacimetros;
    private String nTotalOtto;
    private String nTotal4t;
    private String nTotal2t;
    private String normaAplicada;
}
