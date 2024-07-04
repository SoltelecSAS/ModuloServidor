package com.soltelec.servidor.dtos.reportes_ntcs;

import java.util.List;

import com.soltelec.servidor.dtos.DatosCda;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReporteNtc {
    private List<DatosCda> cda;
    private List<PropietariosNtc> propietarios;
    private List<VehiculoNtc> vehiculos;
    private List<EquiposSoftOttoNtc> equiposOtto;
    private List<EquiposSoftDieselNtc> equiposDiesel;
    private List<DatosGeneralesNtc> generalesPrueba;
    private List<ResultadosOttoNtc> resultadosOtto;
    private List<ResultadosDieselNtc> resultadosDiesel;
}
