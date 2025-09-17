package com.soltelec.servidor.dtos.reporte_corantioquia_cornare;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EquipoAnalizadorCorantioquia {
    private String pef;
    private String marca;
    private String serie;
    private String nSerieAnalizador;
    private String nSerieBanco;
    private String nSerieElectronicoAnalizador;
    private String softAplicacion;
    private String softVersion;
    private String spanHcBaja;
    private String spanCoBaja;
    private String spanCo2Baja;
    private String valorLeidoHcBaja;
    private String valorLeidoCoBaja;
    private String valorLeidoCo2Baja;
    private String spanHcAlta;
    private String spanCoAlta;
    private String spanCo2Alta;
    private String valorLeidoHcAlta;
    private String valorLeidoCoAlta;
    private String valorLeidoCo2Alta;
    private LocalDateTime fechaVerificacion;
    private String resultadoVerificacion;
    
	@Override
	public String toString() {
		return "DatosEquipoAnalizador [pef=" + pef + ", marca=" + marca + ", serie=" + serie + ", softAplicacion="
				+ softAplicacion + ", softVersion=" + softVersion + ", spanHcBaja=" + spanHcBaja + ", spanCoBaja="
				+ spanCoBaja + ", spanCo2Baja=" + spanCo2Baja + ", valorLeidoHcBaja=" + valorLeidoHcBaja
				+ ", valorLeidoCoBaja=" + valorLeidoCoBaja + ", valorLeidoCo2Baja=" + valorLeidoCo2Baja
				+ ", spanHcAlta=" + spanHcAlta + ", spanCoAlta=" + spanCoAlta + ", spanCo2Alta=" + spanCo2Alta
				+ ", valorLeidoHcAlta=" + valorLeidoHcAlta + ", valorLeidoCoAlta=" + valorLeidoCoAlta
				+ ", valorLeidoCo2Alta=" + valorLeidoCo2Alta + ", fechaVerificacion=" + fechaVerificacion
				+ ", resultadoVerificacion=" + resultadoVerificacion + "]";
	}

    
}
