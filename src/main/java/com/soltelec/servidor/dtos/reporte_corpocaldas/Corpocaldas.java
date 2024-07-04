package com.soltelec.servidor.dtos.reporte_corpocaldas;

import java.util.List;

import com.soltelec.servidor.dtos.DatosCda;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Corpocaldas {
    List<DatosCda> datosCda;
    List<InicioPruebaCorpocaldas> datosInicioPrueba;
    List<AnalizadorCorpocaldas> datosAnalizador;
    List<DatosVehiculoCorpocaldas> datosVehiculo;
    List<DatosPruebaCorpocaldas> datosPrueba;
    List<EncendidoChispaCorpocaldas> datosEncendidoChispa;
    List<EncendidoCompresionCorpocaldas> datosEncendidoCompresion;
    List<CausasRechazoCorpocaldas> datosCausaRechazo;
    List<CausasAbortoCorpocaldas> datosCausaAborto;
    List<RuidoCorpocaldas> datosRuido;
}
