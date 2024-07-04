package com.soltelec.servidor.dtos.reporte_corpoboyaca;

import java.util.List;

import com.soltelec.servidor.dtos.DatosCda;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Corpoboyaca {
    private List<DatosCda> cda;
    private List<EquipoAnalizadorBoyaca> analizador;
    private List<PruebasBoyaca> datosPrueba;
    private List<PropietarioBoyaca> propietarioBoyacas;
    private List<VehiculoBoyaca> vehiculoBoyaca;
    private List<ResultadoOttoBoyaca> resultadoOtto; 
    private List<ResultadoDieselBoyaca> resultadoDiesel;
    private List<String> ruido;
}
