package com.soltelec.servidor.consultas;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.soltelec.servidor.conexion.Conexion;
import com.soltelec.servidor.dtos.DatosCda;
import com.soltelec.servidor.dtos.reporte_corantioquia_cornare.CorantioquiaCornare;
import com.soltelec.servidor.dtos.reporte_corantioquia_cornare.DatosPruebaCorantioquia;
import com.soltelec.servidor.dtos.reporte_corantioquia_cornare.DatosVehiculoCorantioquia;
import com.soltelec.servidor.dtos.reporte_corantioquia_cornare.EquipoAnalizadorCorantioquia;
import com.soltelec.servidor.dtos.reporte_corantioquia_cornare.ResultadoPruebaCorantioquia;
import com.soltelec.servidor.dtos.reporte_corpoboyaca.Corpoboyaca;
import com.soltelec.servidor.dtos.reporte_corpoboyaca.EquipoAnalizadorBoyaca;
import com.soltelec.servidor.dtos.reporte_corpoboyaca.PropietarioBoyaca;
import com.soltelec.servidor.dtos.reporte_corpoboyaca.PruebasBoyaca;
import com.soltelec.servidor.dtos.reporte_corpoboyaca.ResultadoDieselBoyaca;
import com.soltelec.servidor.dtos.reporte_corpoboyaca.ResultadoOttoBoyaca;
import com.soltelec.servidor.dtos.reporte_corpoboyaca.VehiculoBoyaca;
import com.soltelec.servidor.dtos.reporte_corpocaldas.AnalizadorCorpocaldas;
import com.soltelec.servidor.dtos.reporte_corpocaldas.CausasAbortoCorpocaldas;
import com.soltelec.servidor.dtos.reporte_corpocaldas.CausasRechazoCorpocaldas;
import com.soltelec.servidor.dtos.reporte_corpocaldas.Corpocaldas;
import com.soltelec.servidor.dtos.reporte_corpocaldas.DatosPruebaCorpocaldas;
import com.soltelec.servidor.dtos.reporte_corpocaldas.DatosVehiculoCorpocaldas;
import com.soltelec.servidor.dtos.reporte_corpocaldas.EncendidoChispaCorpocaldas;
import com.soltelec.servidor.dtos.reporte_corpocaldas.EncendidoCompresionCorpocaldas;
import com.soltelec.servidor.dtos.reporte_corpocaldas.InicioPruebaCorpocaldas;
import com.soltelec.servidor.dtos.reporte_corpocaldas.RuidoCorpocaldas;
import com.soltelec.servidor.dtos.reporte_corponor.Corponor;
import com.soltelec.servidor.dtos.reporte_dagma.Dagma;
import com.soltelec.servidor.dtos.reporte_ntc5365.DatosInspeccionNtc5365;
import com.soltelec.servidor.dtos.reporte_ntc5365.Ntc5365;
import com.soltelec.servidor.dtos.reporte_ntc5365.PropietarioNtC5365;
import com.soltelec.servidor.dtos.reporte_ntc5365.ResultadosInspeccionNtc5365;
import com.soltelec.servidor.dtos.reporte_ntc5365.SoftwareEquipoNtc5365;
import com.soltelec.servidor.dtos.reporte_ntc5365.VehiculoNtc5365;
import com.soltelec.servidor.dtos.reportes_ntcs.DatosGeneralesNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.EquiposSoftDieselNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.EquiposSoftOttoNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.PropietariosNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.ReporteNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.ResultadosDieselNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.ResultadosOttoNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.VehiculoNtc;

@SuppressWarnings("sonar")
public class Reportes {

    private Reportes() {
        throw new IllegalStateException("Utility class");
    }

    private static String url = Conexion.getUrl();
    private static String usuario = Conexion.getUsuario();
    private static String password = Conexion.getContrasena();

    public static List<Dagma> getDagma(Date fechaInicio, Date fechaFin) {

        List<Dagma> listaDatos = new ArrayList<>();
        String consulta = Consultas.getDagma();

        try (Connection conexion = DriverManager.getConnection(url, usuario, password);
            PreparedStatement consultaDagma = conexion.prepareStatement(consulta)) {

            //añadir parametros de la consulta (los que aparecen como ? en la consulta)
            consultaDagma.setDate(1, new java.sql.Date(fechaInicio.getTime()));//selecciona el primer ? que encuentra
            consultaDagma.setDate(2, new java.sql.Date(fechaFin.getTime()));//selecciona el segundo ? que encuentra

            //rc representa el resultado de la consulta
            try (ResultSet rc = consultaDagma.executeQuery()) {
                while (rc.next()) {
                    //Colocara datos segun cada columna que encuentre
                    Dagma dagma = crearDagmaDesdeResultSet(rc);
                    listaDatos.add(dagma);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Genera una lista con todos los datos
        return listaDatos;
    }

    public static CorantioquiaCornare getCorantioquiaOrCornare(Date fechaInicio, Date fechaFin) {

        List<DatosVehiculoCorantioquia> listaDatosVehiculo = new ArrayList<>();
        List<DatosPruebaCorantioquia> listaDatosPrueba = new ArrayList<>();
        List<ResultadoPruebaCorantioquia> listaDatosResultadoPrueba = new ArrayList<>();
        List<EquipoAnalizadorCorantioquia> listaDatosEquipoAnalizador = new ArrayList<>();

        String sql= Consultas.getSqlCorantioquia();
        String sqlCda = Consultas.getDatosCda();
        String sqlSoftware = Consultas.getSoftware();
        String sqlEquipo = Consultas.getEquipoCorantioquiaYCorpocaldas();

        try (Connection conexion = DriverManager.getConnection(url, usuario, password); //Se conecta con la base de datos
             PreparedStatement consultaPruebas = conexion.prepareStatement(sql); //Prepara la ejecucion de la consulta sql
             PreparedStatement consultaCda = conexion.prepareStatement(sqlCda); //Prepara la ejecucion de la consulta sqlCda
             PreparedStatement consultaSoftware = conexion.prepareStatement(sqlSoftware); //Prepara la ejecucion de la consulta sqlSoftware
             PreparedStatement consultaEquipo = conexion.prepareStatement(sqlEquipo) //Prepara la ejecucion de la consulta sqlEquipo
             ) {

            //añadir parametros de la consulta (los que aparecen como '?' en la consulta)
            consultaPruebas.setDate(1, new java.sql.Date(fechaInicio.getTime()));//selecciona el primer ? que encuentra
            consultaPruebas.setDate(2, new java.sql.Date(fechaFin.getTime()));//selecciona el segundo ? que encuentra

            String ciudad = "";
            String direccion = "";
            String software = "";
            String vSoftware="";

            //Obtiene los datos del cda
            try(ResultSet resultadoCda = consultaCda.executeQuery()){
                while (resultadoCda.next()) {
                    ciudad = resultadoCda.getString("ciudad");
                    direccion = resultadoCda.getString("direccion");
                }
            }

            //Obtiene los datos del software
            try(ResultSet resultadoSoft = consultaSoftware.executeQuery()){
                while (resultadoSoft.next()) {
                    software = resultadoSoft.getString("nombre");
                    vSoftware = resultadoSoft.getString("version");
                }
            }

            //rc representa el resultado de la consulta pruebas
            try (ResultSet rc = consultaPruebas.executeQuery()) {
                while (rc.next()) { //recorrera fila por fila el resultado de la consulta

                    //Añade una fila de los datos del vehiculo
                    DatosVehiculoCorantioquia filaDatosVehiculo = getVehiculosCorantioquia(rc);
                    listaDatosVehiculo.add(filaDatosVehiculo);

                    //Añade una fila de los los datos de la prueba
                    DatosPruebaCorantioquia filaDatosPrueba = getDatosPruebaCorantioquia(rc, ciudad, direccion);
                    listaDatosPrueba.add(filaDatosPrueba);

                    //Añade una fila de los los resultados de la prueba
                    ResultadoPruebaCorantioquia filaResultadosPrueba = getResultadoPruebaCorantioquia(rc);
                    listaDatosResultadoPrueba.add(filaResultadosPrueba);

                    //Añade una fila de los los datos del equipo analizador
                    EquipoAnalizadorCorantioquia filaAnalizador = getAnalizadorCorantioquia(rc, software, vSoftware, consultaEquipo);
                    listaDatosEquipoAnalizador.add(filaAnalizador);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Genera un unico objeto con todos los datos
        return new CorantioquiaCornare(
            listaDatosVehiculo, listaDatosPrueba, listaDatosResultadoPrueba, listaDatosEquipoAnalizador);
    }

    @SuppressWarnings("sonar")
    public static Corpocaldas getCorpocaldas(Date fechaInicio, Date fechaFin) {

        List<DatosCda> listaDatosCda = new ArrayList<>();
        List<InicioPruebaCorpocaldas> listaDatosInicioPruebas = new ArrayList<>();
        List<AnalizadorCorpocaldas> listaDatosAnalizador = new ArrayList<>();
        List<DatosVehiculoCorpocaldas> listaDatosVehiculos = new ArrayList<>();
        List<DatosPruebaCorpocaldas> listaDatosPruebas = new ArrayList<>();
        List<EncendidoChispaCorpocaldas> listaDatosChispa = new ArrayList<>();
        List<EncendidoCompresionCorpocaldas> listaDatosCompresion = new ArrayList<>();
        List<CausasRechazoCorpocaldas> listaCausasRechazo = new ArrayList<>();
        List<CausasAbortoCorpocaldas> listaCausasAborto = new ArrayList<>();
        List<RuidoCorpocaldas> listaRuido = new ArrayList<>();

        String sql= Consultas.getCorpocaldas();
        String sqlCda = Consultas.getDatosCda();
        String sqlSoftware = Consultas.getSoftware();
        String sqlEquipo = Consultas.getEquipoCorantioquiaYCorpocaldas();
        String getDepartamento = "SELECT departamento FROM cda WHERE id_cda=1";
        String getNormaAplicada = "SELECT norma_aplicada FROM cda WHERE id_cda=1";

        try (Connection conexion = DriverManager.getConnection(url, usuario, password);
             PreparedStatement consultaPruebas = conexion.prepareStatement(sql);
             PreparedStatement consultaCda = conexion.prepareStatement(sqlCda);
             PreparedStatement consultaSoftware = conexion.prepareStatement(sqlSoftware);
             PreparedStatement consultaEquipo = conexion.prepareStatement(sqlEquipo);
             PreparedStatement obtenerDepartamento = conexion.prepareStatement(getDepartamento);
             PreparedStatement obtenerNorma = conexion.prepareStatement(getNormaAplicada);
             Statement stmt = conexion.createStatement()) {

            //añadir parametros de la consulta (los que aparecen como ? en la consulta)
            consultaPruebas.setDate(1, new java.sql.Date(fechaInicio.getTime()));//selecciona el primer ? que encuentra
            consultaPruebas.setDate(2, new java.sql.Date(fechaFin.getTime()));//selecciona el segundo ? que encuentra

            DatosCda datosDelCda = getDatosCda(consultaCda, obtenerDepartamento, obtenerNorma, stmt);
            
            String software = "";
            String vSoftware="";

            try(ResultSet resultadoSoft = consultaSoftware.executeQuery()){
                while (resultadoSoft.next()) {
                    software = resultadoSoft.getString("nombre");
                    vSoftware = resultadoSoft.getString("version");
                }
            }

            //rc representa el resultado de la consulta pruebas
            try (ResultSet rc = consultaPruebas.executeQuery()) {
                while (rc.next()) {
                    //Colocara datos segun cada fila que encuentre

                    if(rc.getString("Fecha_prueba")==null) continue;

                    //Añade los datos del cda
                    listaDatosCda.add(datosDelCda);

                    //Añade los datos de inicio prueba
                    InicioPruebaCorpocaldas filaInicioPruebas = getInicioPruebaCorpocaldas(rc, datosDelCda.getCiudad(), datosDelCda.getDireccion(), software, vSoftware);
                    listaDatosInicioPruebas.add(filaInicioPruebas);

                    //Añade los datos del equipo analizador
                    AnalizadorCorpocaldas filaAnalizador = getAnalizadorCorpocaldas(rc,consultaEquipo);
                    listaDatosAnalizador.add(filaAnalizador);

                    //Añade los datos del vehiculo
                    DatosVehiculoCorpocaldas filaDatosVehiculo = getDatosVehiculoCorpocaldas(rc);
                    listaDatosVehiculos.add(filaDatosVehiculo);

                    //Añade los datos de la prueba
                    DatosPruebaCorpocaldas filaDatosPrueba = getDatosPruebaCorpocaldas(rc);
                    listaDatosPruebas.add(filaDatosPrueba);

                    //Añade los datos de encendido por chispa
                    EncendidoChispaCorpocaldas filaEncendidoChispa = getEncendidoChispaCorpocaldas(rc);
                    listaDatosChispa.add(filaEncendidoChispa);

                    /*Añade los datos de encendido por compresion
                    ¡NO TERMINADO! debido a que no se necesitaba en la solicitud
                    pero se deja aqui por si se necesita terminar en un futuro*/
                    EncendidoCompresionCorpocaldas listaCompresion = getCompresionCorpocaldas();
                    listaDatosCompresion.add(listaCompresion);

                    //Añade los datos de causas rechazo 
                    CausasRechazoCorpocaldas filaCausasRechazo = getCausasRechazoCorpocaldas(rc, filaDatosVehiculo.getTipoMotor());
                    listaCausasRechazo.add(filaCausasRechazo);

                    //Añade los datos de causas de aborto
                    CausasAbortoCorpocaldas causaAborto = getCausasAbortoCorpocaldas(rc, datosDelCda.getNormaAplicada(), filaInicioPruebas.getFechaFin());
                    listaCausasAborto.add(causaAborto);

                    //Añade los datos de ruido
                    listaRuido.add(
                        new RuidoCorpocaldas(redondeoSegunNorma(rc.getBigDecimal("ruido"))));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Genera un unico objeto con todos los datos
        return new Corpocaldas(
            listaDatosCda, listaDatosInicioPruebas, listaDatosAnalizador, listaDatosVehiculos, 
            listaDatosPruebas, listaDatosChispa, listaDatosCompresion, listaCausasRechazo, 
            listaCausasAborto, listaRuido);
    }

    @SuppressWarnings({"java:S3776", "java:S6541"})
    public static Ntc5365 getNtc5365(Date fechaInicio, Date fechaFin) {

        List<DatosCda> listaDatosCda = new ArrayList<>();
        List<PropietarioNtC5365> listaPropietarioVehiculo = new ArrayList<>();
        List<VehiculoNtc5365> listaDatosVehiculo = new ArrayList<>();
        List<SoftwareEquipoNtc5365> listaSoftEquipoVehiculo = new ArrayList<>();
        List<DatosInspeccionNtc5365> listaInspeccion = new ArrayList<>();
        List<ResultadosInspeccionNtc5365> listaResultados = new ArrayList<>();

        String sql= Consultas.getNtc5365();
        String sqlCda = Consultas.getDatosCda();
        String sqlSoftware = Consultas.getSoftware();
        String getDepartamento = "SELECT departamento FROM cda WHERE id_cda=1";
        String getNormaAplicada = "SELECT norma_aplicada FROM cda WHERE id_cda=1";

        try (Connection conexion = DriverManager.getConnection(url, usuario, password);
             PreparedStatement consultaPruebas = conexion.prepareStatement(sql);
             PreparedStatement consultaCda = conexion.prepareStatement(sqlCda);
             PreparedStatement consultaSoftware = conexion.prepareStatement(sqlSoftware);
             PreparedStatement obtenerDepartamento = conexion.prepareStatement(getDepartamento);
             PreparedStatement obtenerNorma = conexion.prepareStatement(getNormaAplicada);
             Statement stmt = conexion.createStatement()) {

            //añadir parametros de la consulta (los que aparecen como ? en la consulta)
            consultaPruebas.setDate(1, new java.sql.Date(fechaInicio.getTime()));//selecciona el primer ? que encuentra
            consultaPruebas.setDate(2, new java.sql.Date(fechaFin.getTime()));//selecciona el segundo ? que encuentra

            DatosCda datosDelCda = getDatosCda(consultaCda, obtenerDepartamento, obtenerNorma, stmt);
            
            String software = "";
            String vSoftware="";

            try(ResultSet resultadoSoft = consultaSoftware.executeQuery()){
                while (resultadoSoft.next()) {
                    software = resultadoSoft.getString("nombre");
                    vSoftware = resultadoSoft.getString("version");
                }
            }

            //rc representa el resultado de la consulta pruebas
            try (ResultSet rc = consultaPruebas.executeQuery()) {
                while (rc.next()) {
                    //Colocara datos segun cada fila que encuentre

                    //Añade los datos del cda
                    listaDatosCda.add(datosDelCda);

                    PropietarioNtC5365 propietario = new PropietarioNtC5365();
                    propietario.setTipoDocumento(rc.getString("t_iden_propietario"));
                    propietario.setNumeroIdentificacion(rc.getString("CAROWNER"));
                    propietario.setNombrePropietario(rc.getString("nombre_propietario"));
                    propietario.setDireccionPropietario(rc.getString("direccion_propietario"));
                    propietario.setTelefono1Propietario(rc.getString("num_propietario"));
                    propietario.setTelefono2Propietario("");
                    propietario.setMunicipioPropietario(rc.getString("ciudad_propietario"));
                    propietario.setEmailPropietario(rc.getString("email_propietario"));

                    listaPropietarioVehiculo.add(propietario);

                    VehiculoNtc5365 vehiculo = new VehiculoNtc5365();
                    vehiculo.setPlaca(rc.getString("placav"));
                    vehiculo.setModelo(rc.getString("modelov"));
                    vehiculo.setNumMotor(rc.getString("n_motorv"));
                    vehiculo.setVin(rc.getString("vinv"));
                    vehiculo.setCilindraje(rc.getString("cilindrajev"));
                    vehiculo.setLicencia(rc.getString("nlicenciav"));
                    vehiculo.setKilometraje(rc.getString("kmv"));
                    vehiculo.setConversionGnw(rc.getString("gnvv"));
                    vehiculo.setFechaVencimientoGnv(rc.getString("fgnvv"));
                    vehiculo.setMarca(rc.getString("marcav"));
                    vehiculo.setLinea(rc.getString("lineav"));
                    vehiculo.setClase(rc.getString("clasev"));
                    vehiculo.setServicio(rc.getString("serviciov"));
                    vehiculo.setTCombustible(rc.getString("tcombustiblev"));
                    vehiculo.setTMotor(rc.getString("tiemposMotorv")+'T');
                    vehiculo.setRpmConocidas("NO");
                    vehiculo.setRpmMinimasRalenti("");
                    vehiculo.setRpmMaximasRalenti("");
                    vehiculo.setDisenoMotor(rc.getString("diseñoMotor"));
                    vehiculo.setNTubosEscapes(rc.getString("tubos_escapev"));

                    listaDatosVehiculo.add(vehiculo);

                    SoftwareEquipoNtc5365 softEquipo = new SoftwareEquipoNtc5365();
                    softEquipo.setMarcaAnalizador(rc.getString("marca_analizador"));
                    softEquipo.setModeloAnalizador("AGPSP");
                    softEquipo.setSerialAnalizador(rc.getString("serial_analizador"));
                    softEquipo.setMarcaBanco(rc.getString("marca_banco"));
                    softEquipo.setModeloBanco(rc.getString("modelo_banco"));

                    String[] seriales = softEquipo.getSerialAnalizador().split(";"); //['Banco','kit','termohigrometro']

                    String[] serialRpm = seriales[1].split("/"); // ['kit','sonda']

                    softEquipo.setSerialBanco(seriales[0]);
                    softEquipo.setPefBanco("0,"+ rc.getString("pef"));
                    String[] serialElectronico = rc.getString("serial_electronico").split("-");
                    softEquipo.setSerialElectronico(serialElectronico[0]);
                    softEquipo.setMarcaCaptadorRpm(rc.getString("marca_kit_rpm_sensor_temp"));
                    softEquipo.setSerialCaptadorRpm(serialRpm[0]);

                    softEquipo.setMarcaSensorTempMotor(
                        vehiculo.getDisenoMotor().equalsIgnoreCase("scooter") ? "" : softEquipo.getMarcaCaptadorRpm()
                    );

                    softEquipo.setSerialSensorTempMotor(
                        vehiculo.getDisenoMotor().equalsIgnoreCase("scooter") ? "" : serialRpm[1] 
                    );

                    softEquipo.setMarcaSensorTempAmbiente(rc.getString("marca_termohigrometro"));
                    softEquipo.setSerialSensorTempAmbiente(rc.getString("serial_termohigrometro"));
                    softEquipo.setMarcaSensorhumedad(rc.getString("marca_termohigrometro"));
                    softEquipo.setSerialSensorHumedad(rc.getString("serial_termohigrometro"));
                    softEquipo.setNombreSoftware(software);
                    softEquipo.setVersionSoftware(vSoftware);
                    softEquipo.setDesarrolladorSoftware("Soltelec");

                    softEquipo.setTipoIdentificacionInspector("CC");
                    softEquipo.setNumeroIdentificacionInspector(rc.getString("cedula_calibrador"));
                    softEquipo.setNombreInspector(rc.getString("usuario_calibrador"));
                    softEquipo.setFechaFugas(rc.getString("verificacion_fugas"));
                    softEquipo.setFechaGasPatron(rc.getString("verificacion_gases"));
                    softEquipo.setLaboratioAlta(rc.getString("laboratorio_alta"));
                    softEquipo.setCilindroAlta(rc.getString("cilindro_alta"));
                    softEquipo.setCertificadorAlta(rc.getString("certificado_alta"));
                    softEquipo.setLaboratorioBaja(rc.getString("laboratorio_baja"));
                    softEquipo.setCilindroBaja(rc.getString("cilindro_baja"));
                    softEquipo.setCertificadoBaja(rc.getString("certificado_baja"));
                    
                    softEquipo.setValorHcAltaP(redondeoSegunNorma(rc.getBigDecimal("alta_hc_p")));
                    softEquipo.setValorHcAltaH(redondeoSegunNorma(rc.getBigDecimal("alta_hc")));
                    softEquipo.setValorHcBajaP(redondeoSegunNorma(rc.getBigDecimal("bm_hc_p")));
                    softEquipo.setValorHcBajaH(redondeoSegunNorma(rc.getBigDecimal("bm_hc")));
                    softEquipo.setValorCoAlto(redondeoSegunNorma(rc.getBigDecimal("alta_co")));
                    softEquipo.setValorCoBajo(redondeoSegunNorma(rc.getBigDecimal("bm_co")));
                    softEquipo.setValorCo2Alto(redondeoSegunNorma(rc.getBigDecimal("alta_co2")));
                    softEquipo.setValorCo2Bajo(redondeoSegunNorma(rc.getBigDecimal("bm_co2")));
                    softEquipo.setValorO2Alto("0");
                    softEquipo.setValorO2Bajo("0");
                    softEquipo.setResultadoHcAltaP(redondeoSegunNorma(rc.getBigDecimal("banco_alta_hc_p")));
                    softEquipo.setResultadoHcAltaH(redondeoSegunNorma(rc.getBigDecimal("banco_alta_hc")));
                    softEquipo.setResultadoHcBajaP(redondeoSegunNorma(rc.getBigDecimal("banco_bm_hc_p")));
                    softEquipo.setResultadoHcBajaP(redondeoSegunNorma(rc.getBigDecimal("banco_bm_hc")));
                    softEquipo.setResultadoCoAlto(redondeoSegunNorma(rc.getBigDecimal("banco_alta_co")));
                    softEquipo.setResultadoCoBajo(redondeoSegunNorma(rc.getBigDecimal("banco_bm_co")));
                    softEquipo.setResultadoCo2Alto(redondeoSegunNorma(rc.getBigDecimal("banco_alta_co2")));
                    softEquipo.setResultadoCo2Bajo(redondeoSegunNorma(rc.getBigDecimal("banco_bm_co2")));
                    softEquipo.setResultadoO2Alto(redondeoSegunNorma(rc.getBigDecimal("banco_alta_o2")));
                    softEquipo.setResultadoO2Bajo(redondeoSegunNorma(rc.getBigDecimal("banco_bm_o2")));
                    softEquipo.setCriterioGasPatron("");
                    softEquipo.setResultadoGasPatron(rc.getInt("calibracion_aprobada") == 1 ? "APROBADA" : "REPROBADA");

                    listaSoftEquipoVehiculo.add(softEquipo);

                    DatosInspeccionNtc5365 datosInspeccion = new DatosInspeccionNtc5365();
                    datosInspeccion.setTipoDocumentoDirector("CC");
                    datosInspeccion.setNumDocumentoDirector(rc.getString("cedula_director"));
                    datosInspeccion.setNombreDirector(rc.getString("nombre_director"));
                    datosInspeccion.setTipoDocumentoInspector("CC");
                    datosInspeccion.setNumDocumentoInspector(rc.getString("cedula_inspector"));
                    datosInspeccion.setNombreInspector(rc.getString("nombre_inspector"));
                    datosInspeccion.setNumeroFur(rc.getString("numero_fur"));
                    datosInspeccion.setFechaFur(rc.getString("fecha_fur"));
                    datosInspeccion.setConsecutivoFur(rc.getString("consecutivo_runt"));
                    datosInspeccion.setFurAsociado(rc.getString("numero_fur"));
                    datosInspeccion.setCertificadoEmitido(rc.getString("certificado_emitido"));
                    datosInspeccion.setFechaInicio(rc.getString("Fecha_prueba"));
                    datosInspeccion.setFechaFin(rc.getString("Fecha_final"));
                    datosInspeccion.setFechaAborto(rc.getString("Fecha_aborto"));
                    datosInspeccion.setCausalAborto(rc.getString("Comentario_aborto"));
                    datosInspeccion.setCatalizador(rc.getString("catalizador").contains("N") ? "NO" : "SI");
                    datosInspeccion.setTempAmbiente(redondeoSegunNorma(rc.getBigDecimal("temperatura_ambiente")));
                    datosInspeccion.setHumedadRelativa(redondeoSegunNorma(rc.getBigDecimal("humedad_relativa")));

                    listaInspeccion.add(datosInspeccion);

                    ResultadosInspeccionNtc5365 rInspeccion = new ResultadosInspeccionNtc5365();
                    rInspeccion.setTempMotor(rc.getString("temperatura_motor"));
                    rInspeccion.setRpmRalenti(rc.getString("rpm_ralenti"));
                    rInspeccion.setPresenciaHumo(rc.getString("presencia_humo_n_a").equals("1") ? "SI" : "NO");
                    rInspeccion.setCorreccionOxigeno("NO");
                    rInspeccion.setRpmFuera(rc.getString("rpm_fuera_rango").equals("1") ? "SI" : "NO");
                    rInspeccion.setFugaTubo(rc.getString("fugas_tubo_escape").equals("1") ? "SI" : "NO");
                    rInspeccion.setSalidasAdicionales(rc.getString("salidas_adicionales_diseño").equals("1") ? "SI" : "NO");
                    rInspeccion.setFugaAceite(rc.getString("ausencia_tapa_aceite").equals("1") ? "SI" : "NO");
                    rInspeccion.setFugaCombustible(rc.getString("ausencia_tapa_combustible").equals("1") ? "SI" : "NO");
                    rInspeccion.setRHcRalenti(redondeoSegunNorma(rc.getBigDecimal("hc_ralenti")));
                    rInspeccion.setRCoRalenti(redondeoSegunNorma(rc.getBigDecimal("co_ralenti")));
                    rInspeccion.setRCo2Ralenti(redondeoSegunNorma(rc.getBigDecimal("co2_ralenti")));
                    rInspeccion.setRO2Ralenti(redondeoSegunNorma(rc.getBigDecimal("O2_ralenti")));
                    rInspeccion.setRGasSinCorregir("");

                    String emisiones = "";
                    if (!rc.getString("es_prueba_aprobada").contains("Y")
                        && rc.getString("es_prueba_abortada").contains("Y")) {
                        emisiones = "SI";
                    }else emisiones = "NO";

                    rInspeccion.setNcEmisiones(emisiones);
                    rInspeccion.setResFinal(rc.getString("es_prueba_aprobada").contains("Y") ? "APROBADO" : "REPROBADO");

                    listaResultados.add(rInspeccion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Genera un unico objeto con todos los datos
        return new Ntc5365(listaDatosCda, listaPropietarioVehiculo, listaDatosVehiculo, listaSoftEquipoVehiculo, listaInspeccion, listaResultados);
    }

    @SuppressWarnings({"java:S3776", "java:S6541"})
    public static Corpoboyaca getCorpoboyaca(Date fechaInicio, Date fechaFin, int tipo){

        String[] datos = {"AND v.CLASS IN (10)", "AND v.CLASS NOT IN (10) AND (tg.Nombre_gasolina = 'GASOLINA' OR tg.Nombre_gasolina = 'GAS - GASOLINA')", "AND (tg.Nombre_gasolina = 'BIODIESEL' OR tg.Nombre_gasolina = 'DIESEL')"}; 

        List<DatosCda> listaDatosCda = new ArrayList<>();
        List<EquipoAnalizadorBoyaca> equipoAnalizador = new ArrayList<>();
        List<PruebasBoyaca> datosPruebas = new ArrayList<>();
        List<PropietarioBoyaca> datosPropietario = new ArrayList<>();
        List<VehiculoBoyaca> datosVehiculo = new ArrayList<>();
        List<ResultadoDieselBoyaca> listaResultadoDiesel = new ArrayList<>();
        List<ResultadoOttoBoyaca> listaResultadoOtto = new ArrayList<>();
        List<String> ruido = new ArrayList<>();

        String sql= Consultas.getCorpoboyaca(datos[tipo]);
        String sqlCda = Consultas.getDatosCda();
        String sqlSoftware = Consultas.getSoftware();
        String getDepartamento = "SELECT departamento FROM cda WHERE id_cda=1";
        String getNormaAplicada = "SELECT norma_aplicada FROM cda WHERE id_cda=1";

        try (Connection conexion = DriverManager.getConnection(url, usuario, password);
                PreparedStatement consultaPruebas = conexion.prepareStatement(sql);
                PreparedStatement consultaCda = conexion.prepareStatement(sqlCda);
                PreparedStatement consultaSoftware = conexion.prepareStatement(sqlSoftware);
                PreparedStatement obtenerDepartamento = conexion.prepareStatement(getDepartamento);
                PreparedStatement obtenerNorma = conexion.prepareStatement(getNormaAplicada);
                Statement stmt = conexion.createStatement()) {

            //añadir parametros de la consulta (los que aparecen como ? en la consulta)
            consultaPruebas.setDate(1, new java.sql.Date(fechaInicio.getTime()));//selecciona el primer ? que encuentra
            consultaPruebas.setDate(2, new java.sql.Date(fechaFin.getTime()));//selecciona el segundo ? que encuentra
            consultaPruebas.setDate(3, new java.sql.Date(fechaInicio.getTime()));//selecciona el tercer ? que encuentra
            consultaPruebas.setDate(4, new java.sql.Date(fechaFin.getTime()));//selecciona el cuarto ? que encuentra

            DatosCda datosDelCda = getDatosCda(consultaCda, obtenerDepartamento, obtenerNorma, stmt);
            
            String software = "";
            String vSoftware="";

            try(ResultSet resultadoSoft = consultaSoftware.executeQuery()){
                while (resultadoSoft.next()) {
                    software = resultadoSoft.getString("nombre");
                    vSoftware = resultadoSoft.getString("version");
                }
            }

            //rc representa el resultado de la consulta pruebas
            try (ResultSet rc = consultaPruebas.executeQuery()) {
                while (rc.next()) {
                    //Colocara datos segun cada fila que encuentre

                    //Añade los datos del cda
                    listaDatosCda.add(datosDelCda);

                    String[] seriales = rc.getString("serial_analizador").split(";"); //['Banco','kit','termohigrometro']

                    EquipoAnalizadorBoyaca equipo = new EquipoAnalizadorBoyaca();
                    equipo.setVrPef("0."+rc.getString("pef"));
                    equipo.setNoSerieBanco(seriales[0]);
                    equipo.setMarcaAnalizador(rc.getString("marca_analizador"));
                    equipo.setNoSerieAnalizador(rc.getString("serial_analizador"));
                    equipo.setHcBaja(redondeoSegunNorma(rc.getBigDecimal("bm_hc")));
                    equipo.setCoBaja(redondeoSegunNorma(rc.getBigDecimal("bm_co")));
                    equipo.setCo2Baja(redondeoSegunNorma(rc.getBigDecimal("bm_co2")));
                    equipo.setHcAlta(redondeoSegunNorma(rc.getBigDecimal("alta_hc")));
                    equipo.setCoAlta(redondeoSegunNorma(rc.getBigDecimal("alta_co")));
                    equipo.setCo2Alta(redondeoSegunNorma(rc.getBigDecimal("alta_co2")));
                    equipo.setFechaVerificacion(rc.getString("verificacion_gases"));
                    equipo.setSoftware(software);
                    equipo.setVSoftware(vSoftware);

                    equipoAnalizador.add(equipo);

                    PruebasBoyaca datoPrueba = new PruebasBoyaca();
                    datoPrueba.setNoPrueba(rc.getString("consecutivo_prueba"));
                    datoPrueba.setFechaInicio(rc.getString("fecha_inicio_prueba"));
                    datoPrueba.setFechaFin(rc.getString("fecha_fin_prueba"));
                    datoPrueba.setFechaAborto(rc.getString("fecha_aborto_prueba") == null ? "" : rc.getString("fecha_aborto_prueba"));
                    datoPrueba.setInspector(rc.getString("nombre_inspector"));
                    datoPrueba.setTemperaturaAmbiente(redondeoSegunNorma(rc.getBigDecimal("temperatura_ambiente")));
                    datoPrueba.setHumedadRelativa(redondeoSegunNorma(rc.getBigDecimal("humedad_relativa")));
                    datoPrueba.setCausaAborto(rc.getString("causal_aborto_prueba") == null ? "" : rc.getString("causal_aborto_prueba"));

                    datosPruebas.add(datoPrueba);

                    PropietarioBoyaca propietario = new PropietarioBoyaca();
                    propietario.setNombre(rc.getString("nombre_propietario"));
                    propietario.setTipoDocumento(rc.getString("t_iden_propietario"));
                    propietario.setNoDocumento(rc.getString("no_documento"));
                    propietario.setDireccion(rc.getString("direccion_propietario"));
                    propietario.setTelefono1("");
                    propietario.setTelefono2(rc.getString("num_propietario"));
                    propietario.setCiudad(rc.getString("ciudad_propietario"));

                    datosPropietario.add(propietario);

                    VehiculoBoyaca vehiculo = new VehiculoBoyaca();
                    vehiculo.setMarca(rc.getString("marca_vehiculo"));
                    vehiculo.setLinea(rc.getString("linea_vehiculo"));
                    vehiculo.setModelo(rc.getString("modelo_vehiculo"));
                    vehiculo.setPlaca(rc.getString("placa_vehiculo"));
                    vehiculo.setCilindraje(rc.getString("cilindraje_vehiculo"));
                    vehiculo.setClase(rc.getString("clase_vehiculo"));
                    vehiculo.setServicio(rc.getString("servicio_vehiculo"));
                    vehiculo.setCombustible(rc.getString("t_combustible_vehiculo"));
                    vehiculo.setNoMotor(rc.getString("n_motor_vehiculo"));
                    vehiculo.setVin(rc.getString("vin_vehiculo"));
                    vehiculo.setNoLicencia(rc.getString("n_licencia_vehiculo"));
                    vehiculo.setKm(rc.getString("km_vehiculo").equals("0") ? "NO FUNCIONAL" : rc.getString("km_vehiculo"));
                    vehiculo.setModificacionesMotor(rc.getString("modificacion_motor"));
                    vehiculo.setTipoMotor(rc.getString("tiemposMotor_vehiculo"));
                    vehiculo.setDesing(rc.getString("diseño_motor"));

                    datosVehiculo.add(vehiculo);

                    String fugasTuboEscape = rc.getString("fugas_tubo_escape").equals("1") ? "SI" : "NO";
                    String silenciador = rc.getString("fugas_silenciador").equals("1") ? "SI" : "NO";
                    String accesorios = rc.getString("salidas_adicionales_diseño").equals("1") ? "SI" : "NO";
                    String tapaCombustible = rc.getString("ausencia_tapa_combustible").equals("1") ? "SI" : "NO";
                    String tapaAceite = rc.getString("ausencia_tapa_aceite").equals("1") ? "SI" : "NO";
                    String sistemaAdquisicionAire = rc.getString("sistema_aire").equals("1") ? "SI" : "NO";
                    String salidasDiseno = rc.getString("salidas_adicionales_diseño").equals("1") ? "SI" : "NO";
                    String pcv = rc.getString("diseño_motor").equals("1") ? "SI" : "NO";
                    String presenciaHumo = rc.getString("diseño_motor").equals("1") ? "SI" : "NO";
                    String rpmFueraRango = rc.getString("diseño_motor").equals("1") ? "SI" : "NO";
                    String fallaRefrigeracion = rc.getString("diseño_motor").equals("1") ? "SI" : "NO";
                    String filtroAire = rc.getString("filtro_aire").equals("1") ? "SI" : "NO";
                    String malFuncionamiento = rc.getString("mal_funcionamiento").equals("1") ? "SI" : "NO";
                    String dispositivosAlteranRpm = rc.getString("dispositivo_altera_rpm").equals("1") ? "SI" : "NO";
                    String velocidadNo5s = rc.getString("velocidad_5_segundos").equals("1") ? "SI" : "NO";
                    String fallaSubitaMotor = rc.getString("falla_motor").equals("1") ? "SI" : "NO";
                    String fallaPorTempMotor = rc.getString("falla_motor").equals("1") ? "SI" : "NO";
                    String inestabilidadCiclos = rc.getString("inestabilidad_ciclos").equals("1") ? "SI" : "NO";
                    String incumplimientoNiveles = rc.getString("es_prueba_aprobada").equalsIgnoreCase("n") ? "SI" : "NO";

                    if(tipo != 2){
                        ResultadoOttoBoyaca otto = new ResultadoOttoBoyaca();
                        otto.setFugaExosto(fugasTuboEscape);
                        otto.setFugaSilenciador(silenciador);
                        otto.setAccesoriosTubo(accesorios);
                        otto.setTapaCombustible(tapaCombustible);
                        otto.setTapaAceite(tapaAceite);
                        otto.setSistemaAdqusicionAire(sistemaAdquisicionAire);
                        otto.setSalidasAdicionales(salidasDiseno);
                        otto.setPcv(pcv);
                        otto.setPresenciaHumo(presenciaHumo);
                        otto.setRpmFueraRango(rpmFueraRango);
                        otto.setFallaRefrigeracion(fallaRefrigeracion);
                        otto.setTempMotor(rc.getString("temperatura_motor") == null ? "" : rc.getString("temperatura_motor"));
                        otto.setRpmRalenti(redondeoSegunNorma( rc.getBigDecimal("rpm_ralenti")));
                        otto.setHcRalenti(redondeoSegunNorma( rc.getBigDecimal("hc_ralenti")));
                        otto.setCoRalenti(redondeoSegunNorma( rc.getBigDecimal("co_ralenti")));
                        otto.setCo2Ralenti(redondeoSegunNorma( rc.getBigDecimal("co2_ralenti")));
                        otto.setO2Ralenti(redondeoSegunNorma( rc.getBigDecimal("O2_ralenti")));
                        otto.setRpmCrucero(redondeoSegunNorma( rc.getBigDecimal("rpm_crucero")));
                        otto.setHcCrucero(redondeoSegunNorma( rc.getBigDecimal("hc_crucero")));
                        otto.setCoCrucero(redondeoSegunNorma( rc.getBigDecimal("co_crucero")));
                        otto.setCo2Crucero(redondeoSegunNorma( rc.getBigDecimal("co2_crucero")));
                        otto.setO2Crucero(redondeoSegunNorma( rc.getBigDecimal("o2_crucero")));
                        otto.setIncumplimientoEmision(incumplimientoNiveles);
                        otto.setConceptoFinal(rc.getString("es_prueba_aprobada").equalsIgnoreCase("n") ? "2" : "1");
                        otto.setRuido(redondeoSegunNorma(rc.getBigDecimal("ruido")));

                        listaResultadoOtto.add(otto);
                    }else {
                        ResultadoDieselBoyaca diesel = new ResultadoDieselBoyaca();
                        diesel.setFugaTubo(fugasTuboEscape);
                        diesel.setFugaSilenciador(silenciador);
                        diesel.setTapaCombustible(tapaCombustible);
                        diesel.setTapaAceite(tapaAceite);
                        diesel.setAccesorios(accesorios);
                        diesel.setSalidasAdicionales(salidasDiseno);
                        diesel.setFiltroAire(filtroAire);
                        diesel.setRefrigeracion(fallaRefrigeracion);
                        diesel.setRpmFueraRango(rpmFueraRango);
                        diesel.setMalFuncionamiento(malFuncionamiento);
                        diesel.setControlVelocidadMotor("NO");
                        diesel.setDispositivosRpm(dispositivosAlteranRpm);
                        diesel.setTempMotor(rc.getString("temperatura_motor") == null ? "" : rc.getString("temperatura_motor"));
                        diesel.setVelocidad5s(velocidadNo5s);
                        diesel.setFallaMotor(fallaSubitaMotor);
                        diesel.setFallaTempMotor(fallaPorTempMotor);

                        diesel.setRpmRalenti(redondeoSegunNorma(rc.getBigDecimal("rpm_ralenti_pre")));
                        diesel.setDensidadPreliminar(redondeoSegunNorma(rc.getBigDecimal("opacidad_preliminar")));
                        diesel.setRpmGobernadaPreliminar(redondeoSegunNorma(rc.getBigDecimal("rpm_gobernada_pre")));
                        diesel.setDencidadC1(redondeoSegunNorma(rc.getBigDecimal("opacidad_c1")));
                        diesel.setGobernadaC1(redondeoSegunNorma(rc.getBigDecimal("rpm_gobernada_c1")));
                        diesel.setDencidadC2(redondeoSegunNorma(rc.getBigDecimal("opacidad_c2")));
                        diesel.setGobernadaC2(redondeoSegunNorma(rc.getBigDecimal("rpm_gobernada_c2")));
                        diesel.setDencidadC3(redondeoSegunNorma(rc.getBigDecimal("opacidad_c3")));
                        diesel.setGobernadaC3(redondeoSegunNorma(rc.getBigDecimal("rpm_gobernada_c3")));

                        BigDecimal sumRpm = rc.getBigDecimal("rpm_gobernada_pre")
                        .add(rc.getBigDecimal("rpm_gobernada_c1"))
                        .add(rc.getBigDecimal("rpm_gobernada_c2"))
                        .add(rc.getBigDecimal("rpm_gobernada_c3"));

                        diesel.setRpmGovernadaMediana(redondeoSegunNorma(
                            sumRpm.divide(new BigDecimal("4"))
                        ));

                        diesel.setLtoe(rc.getString("pef"));
                        diesel.setTempFinalMotor(rc.getString("temperatura_final_motor") == null ? "" : rc.getString("temperatura_final_motor"));
                        diesel.setInstabilidadCiclos(inestabilidadCiclos);
                        diesel.setDiferenciaAritmetica("NO");
                        diesel.setPromedio(redondeoSegunNorma(rc.getBigDecimal("resultado_final_opacidad")));
                        diesel.setIncumplimientoNiveles(incumplimientoNiveles);
                        diesel.setResultadoPrueba(rc.getString("es_prueba_aprobada").equalsIgnoreCase("n") ? "2" : "1");
                        diesel.setRuido(redondeoSegunNorma(rc.getBigDecimal("ruido")));

                        listaResultadoDiesel.add(diesel);
                    }
                    

                    ruido.add(rc.getString("ruido"));
                }
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return new Corpoboyaca(listaDatosCda, equipoAnalizador, datosPruebas, datosPropietario, datosVehiculo, listaResultadoOtto, listaResultadoDiesel, ruido);
    }

    @SuppressWarnings({"java:S3776", "java:S6541"})
    public static List<Corponor> getCorpocor(Date fechaInicio, Date fechaFin){

        List<Corponor> corponor = new ArrayList<>();

        String sql= Consultas.getCorpoboyaca(" \n");
        String sqlCda = Consultas.getDatosCda();
        String sqlSoftware = Consultas.getSoftware();
        String getDepartamento = "SELECT departamento FROM cda WHERE id_cda=1";
        String getNormaAplicada = "SELECT norma_aplicada FROM cda WHERE id_cda=1";

        try (Connection conexion = DriverManager.getConnection(url, usuario, password);
                PreparedStatement consultaPruebas = conexion.prepareStatement(sql);
                PreparedStatement consultaCda = conexion.prepareStatement(sqlCda);
                PreparedStatement consultaSoftware = conexion.prepareStatement(sqlSoftware);
                PreparedStatement obtenerDepartamento = conexion.prepareStatement(getDepartamento);
                PreparedStatement obtenerNorma = conexion.prepareStatement(getNormaAplicada);
                Statement stmt = conexion.createStatement()) {

            //añadir parametros de la consulta (los que aparecen como ? en la consulta)
            consultaPruebas.setDate(1, new java.sql.Date(fechaInicio.getTime()));//selecciona el primer ? que encuentra
            consultaPruebas.setDate(2, new java.sql.Date(fechaFin.getTime()));//selecciona el segundo ? que encuentra
            consultaPruebas.setDate(3, new java.sql.Date(fechaInicio.getTime()));//selecciona el tercer ? que encuentra
            consultaPruebas.setDate(4, new java.sql.Date(fechaFin.getTime()));//selecciona el cuarto ? que encuentra

            DatosCda datosDelCda = getDatosCda(consultaCda, obtenerDepartamento, obtenerNorma, stmt);
            
            String software = "";
            String vSoftware="";

            try(ResultSet resultadoSoft = consultaSoftware.executeQuery()){
                while (resultadoSoft.next()) {
                    software = resultadoSoft.getString("nombre");
                    vSoftware = resultadoSoft.getString("version");
                }
            }

            //rc representa el resultado de la consulta pruebas
            try (ResultSet rc = consultaPruebas.executeQuery()) {
                while (rc.next()) {
                    Corponor dato = new Corponor();
                    dato.setFechaInicio(rc.getString("fecha_inicio_prueba"));
                    dato.setFechaFin(rc.getString("fecha_fin_prueba"));
                    dato.setMunicipio(datosDelCda.getCiudad());
                    dato.setLugar(datosDelCda.getCiudad());
                    dato.setNFur(rc.getString("con_hoja_prueba"));
                    dato.setNCertificado(rc.getString("CONSECUTIVE"));
                    dato.setSerialAnalizador(rc.getString("serial_analizador"));
                    dato.setPef(rc.getString("opacidad_preliminar") == null ? rc.getString("pef") : "");
                    dato.setMarcaSoftware("Soltelec");
                    dato.setVersionSoftware("1.7.3");
                    dato.setIdInspector(rc.getString("cedula"));
                    dato.setPlaca(rc.getString("placa_vehiculo"));
                    dato.setMarcaCarro(rc.getString("marca_vehiculo"));
                    dato.setModeloCarro(rc.getString("modelo_vehiculo"));
                    dato.setCilindraje(rc.getString("cilindraje_vehiculo"));
                    dato.setLinea(rc.getString("linea_vehiculo"));
                    dato.setClase(rc.getString("clase_vehiculo"));
                    dato.setServicio(rc.getString("servicio_vehiculo"));
                    dato.setTCombustible(rc.getString("t_combustible_vehiculo"));
                    dato.setTMotor(rc.getString("tiemposMotor_vehiculo"));
                    dato.setNExostos(rc.getString("Numero_exostos"));
                    dato.setDesign(rc.getString("diseño_motor"));
                    dato.setTempAmbiente(redondeoSegunNorma(rc.getBigDecimal("temperatura_ambiente")));
                    dato.setHumedadRelativa(redondeoSegunNorma(rc.getBigDecimal("humedad_relativa")));
                    dato.setLtoe(rc.getString("opacidad_preliminar") != null ? rc.getString("pef") : "");
                    dato.setMetodoMedicionTemp(rc.getString("forma_med_temp"));
                    dato.setTempMotor(redondeoSegunNorma(rc.getBigDecimal("temperatura_motor")));
                    dato.setTempFinal(redondeoSegunNorma(rc.getBigDecimal("temperatura_final_motor")));
                    dato.setRpmRalenti(redondeoSegunNorma(rc.getBigDecimal("rpm_ralenti")));
                    dato.setRpmCruceroOGobernada(rc.getString("opacidad_preliminar") != null ? redondeoSegunNorma(rc.getBigDecimal("rpm_gobernada_pre")) : redondeoSegunNorma(rc.getBigDecimal("rpm_crucero")));
                    dato.setHcRalenti(redondeoSegunNorma(rc.getBigDecimal("hc_ralenti")));
                    dato.setHcCrucero(redondeoSegunNorma(rc.getBigDecimal("hc_crucero")));
                    dato.setCoRalenti(redondeoSegunNorma(rc.getBigDecimal("co_ralenti")));
                    dato.setCoCrucero(redondeoSegunNorma(rc.getBigDecimal("co_crucero")));
                    dato.setCo2Ralenti(redondeoSegunNorma(rc.getBigDecimal("co2_ralenti")));
                    dato.setCo2Crucero(redondeoSegunNorma(rc.getBigDecimal("co2_crucero")));
                    dato.setO2Ralenti(redondeoSegunNorma(rc.getBigDecimal("O2_ralenti")));
                    dato.setO2Crucero(redondeoSegunNorma(rc.getBigDecimal("o2_crucero")));
                    dato.setCicloPreliminar(redondeoSegunNorma(rc.getBigDecimal("opacidad_preliminar")));
                    dato.setRpmGobernadaCicloPreliminar(redondeoSegunNorma(rc.getBigDecimal("rpm_gobernada_pre")));
                    dato.setRpmRalentiCicloPreliminar(redondeoSegunNorma(rc.getBigDecimal("rpm_ralenti_pre")));
                    dato.setOpacidadC1(redondeoSegunNorma(rc.getBigDecimal("opacidad_c1")));
                    dato.setGobernadaC1(redondeoSegunNorma(rc.getBigDecimal("rpm_gobernada_c1")));
                    dato.setRalentiC1(redondeoSegunNorma(rc.getBigDecimal("rpm_ralenti_c1")));
                    dato.setOpacidadC2(redondeoSegunNorma(rc.getBigDecimal("opacidad_c2")));
                    dato.setGobernadaC2(redondeoSegunNorma(rc.getBigDecimal("rpm_gobernada_c2")));
                    dato.setRalentiC2(redondeoSegunNorma(rc.getBigDecimal("rpm_ralenti_c2")));
                    dato.setOpacidadC3(redondeoSegunNorma(rc.getBigDecimal("opacidad_c3")));
                    dato.setGobernadaC3(redondeoSegunNorma(rc.getBigDecimal("rpm_gobernada_c3")));
                    dato.setRalentiC3(redondeoSegunNorma(rc.getBigDecimal("rpm_ralenti_c3")));
                    dato.setPromedioFinal(redondeoSegunNorma(rc.getBigDecimal("resultado_final_opacidad")));
                    dato.setConceptoFinal(rc.getString("es_prueba_aprobada").equalsIgnoreCase("n") ? "REPROBADO" : "APROBADO");
                    dato.setPresenciaHumo(rc.getString("presencia_humo_n_a").equals("1") ? "SI" : "NO");
                    dato.setDilucionMezcla("NO");
                    dato.setNivelEmisiones("35");
                    dato.setRpmFueraRango(rc.getString("presencia_humo_n_a").equals("1") ? "SI" : "NO");
                    dato.setFugasTubo(rc.getString("fugas_tubo_escape").equals("1") ? "SI" : "NO");
                    dato.setSalidasAdicionales(rc.getString("salidas_adicionales_diseño").equals("1") ? "SI" : "NO");
                    dato.setTapaAceite(rc.getString("ausencia_tapa_aceite").equals("1") ? "SI" : "NO");
                    dato.setTapaCombustible(rc.getString("ausencia_tapa_combustible").equals("1") ? "SI" : "NO");
                    dato.setFiltroAire(rc.getString("filtro_aire").equals("1") ? "SI" : "NO");
                    dato.setAccesorios(rc.getString("salidas_adicionales_diseño").equals("1") ? "SI" : "NO");
                    dato.setIncorrectaRefrigeracion(rc.getString("falla_sistema_refrigeracion").equals("1") ? "SI" : "NO");
                    dato.setEmisiones(rc.getString("presencia_humo_n_a").equals("1") ? "SI" : "NO");
                    dato.setIncorrectaOperacionGobernador(rc.getString("mal_funcionamiento").equals("1") ? "SI" : "NO");
                    dato.setDiferenciaAritmetica("NO");
                    dato.setDiferenciaTemperatura("NO");
                    dato.setInstalacionTubo(rc.getString("salidas_adicionales_diseño").equals("1") ? "SI" : "NO");
                    dato.setFiltroAire2(rc.getString("filtro_aire").equals("1") ? "SI" : "NO");
                    dato.setDispositivos(rc.getString("modificacion_motor").equals("1") ? "SI" : "NO");
                    dato.setFallaEquipoMedicion("NO");
                    dato.setFallaFluidoElectrico("NO");
                    dato.setBloqueoForzadoEquipoMedicion(rc.getString("fecha_aborto_prueba")=="" || rc.getString("fecha_aborto_prueba") == null ? "SI" : "NO");
                    dato.setEjecucionIncorrectaPrueba(rc.getString("fecha_aborto_prueba")=="" || rc.getString("fecha_aborto_prueba") == null ? "SI" : "NO");
                    dato.setFallaDesviacionCero("NO");
                    dato.setResolucionAplicada(datosDelCda.getNormaAplicada());
                    dato.setRuidos(redondeoSegunNorma(rc.getBigDecimal("ruido")));

                    corponor.add(dato);
                }
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }
        return corponor;
    }

    public static ReporteNtc getNtc(Date fechaInicio, Date fechaFin, int tipo){ //tipos: 0 -> NTC4983, 1 -> NTC5365, 2 -> NTC4231
        String[] condiciones = {"AND v.CLASS NOT IN (10) AND (tg.Nombre_gasolina = 'GASOLINA' OR tg.Nombre_gasolina = 'GAS - GASOLINA')", "AND v.CLASS IN (10)", "AND tg.Nombre_gasolina = 'DIESEL'"}; 

        List<DatosCda> listaDatosCda = new ArrayList<>();
        List<PropietariosNtc> listaPropietarios = new ArrayList<>();
        List<VehiculoNtc> listaDatosVehiculos = new ArrayList<>(); 
        List<EquiposSoftOttoNtc> listaEquiposOtto = new ArrayList<>(); 
        List<EquiposSoftDieselNtc> listaEquiposDiesel = new ArrayList<>(); 
        List<DatosGeneralesNtc> listaDatosGenerales = new ArrayList<>(); 
        List<ResultadosOttoNtc> listaResultadosOtto  = new ArrayList<>(); 
        List<ResultadosDieselNtc> listaResultadosDiesel = new ArrayList<>(); 


        String sql= Consultas.getNtc(condiciones[tipo]);
        String sqlCda = Consultas.getDatosCda();
        String sqlSoftware = Consultas.getSoftware();
        String getDepartamento = "SELECT departamento FROM cda WHERE id_cda=1";
        String getNormaAplicada = "SELECT norma_aplicada FROM cda WHERE id_cda=1";

        try (Connection conexion = DriverManager.getConnection(url, usuario, password);
                PreparedStatement consultaPruebas = conexion.prepareStatement(sql);
                PreparedStatement consultaCda = conexion.prepareStatement(sqlCda);
                PreparedStatement consultaSoftware = conexion.prepareStatement(sqlSoftware);
                PreparedStatement obtenerDepartamento = conexion.prepareStatement(getDepartamento);
                PreparedStatement obtenerNorma = conexion.prepareStatement(getNormaAplicada);
                Statement stmt = conexion.createStatement()) {

            //añadir parametros de la consulta (los que aparecen como ? en la consulta)
            consultaPruebas.setDate(1, new java.sql.Date(fechaInicio.getTime()));//selecciona el primer ? que encuentra
            consultaPruebas.setDate(2, new java.sql.Date(fechaFin.getTime()));//selecciona el segundo ? que encuentra
            consultaPruebas.setDate(3, new java.sql.Date(fechaInicio.getTime()));//selecciona el tercer ? que encuentra
            consultaPruebas.setDate(4, new java.sql.Date(fechaFin.getTime()));//selecciona el cuarto ? que encuentra

            DatosCda datosDelCda = getDatosCda(consultaCda, obtenerDepartamento, obtenerNorma, stmt);
            
            String software = "";
            String vSoftware="";

            try(ResultSet resultadoSoft = consultaSoftware.executeQuery()){
                while (resultadoSoft.next()) {
                    software = resultadoSoft.getString("nombre");
                    vSoftware = resultadoSoft.getString("version");
                }
            }

            //rc representa el resultado de la consulta pruebas
            try (ResultSet rc = consultaPruebas.executeQuery()) {
                while (rc.next()) {
                    //Añade los datos del cda
                    listaDatosCda.add(datosDelCda);

                    //Datos Propietarios
                    String tipIdeProp = rc.getString("TIP_IDE_PROP"),
                    numIdeProp = rc.getString("NUM_IDE_PROP"),
                    nomProp = rc.getString("NOM_PROP"),
                    dirProp = rc.getString("DIR_PROP"),
                    tel1Prop = rc.getString("TEL1_PROP"),
                    tel2Prop = "",
                    munProp = rc.getString("MUN_PROP"),
                    corrEProp = rc.getString("CORR_E_PROP");

                    //Datos vehiculo
                    String placa = rc.getString("PLACA"),
                    modelo = rc.getString("MODELO"),
                    numMotor = rc.getString("NUM_MOTOR"),
                    vin = rc.getString("VIN"),
                    modMotor = "NO",
                    diaEscape = rc.getString("DIA_ESCAPE"),
                    cilindraje = rc.getString("CILINDRAJE"),
                    licTrans = rc.getString("LIC_TRANS"),
                    km = rc.getString("KM"),
                    gnvConv = rc.getString("GNV_CONV").equals("Y") ? "SI" : "NO",
                    gnvConvV = gnvConv.equals("SI") ? rc.getString("GNV_CONV_V") : "",
                    marca = rc.getString("MARCA"),
                    linea = rc.getString("LINEA"),
                    clase = rc.getString("CLASE"),
                    servicio = rc.getString("SERVICIO"),
                    tipComb = rc.getString("TIP_COMB").equalsIgnoreCase("GAS - GASOLINA") ? "GAS GASOL" :  rc.getString("TIP_COMB"),
                    tipMotor = rc.getString("TIP_MOTOR")+"T",
                    rpmFab = "NO",
                    ralMinFab = "",
                    ralMaxFab = "",
                    gobMinFab = "",
                    gobMaxFab = "",
                    disMotor = rc.getString("DIS_MOTOR"),
                    numEscape = rc.getString("NUM_ESCAPE");

                    //catalizador
                    String catalizardor = rc.getString("LUGAR_TEMP").equals("C") ? "SI" : "NO";


                    String marcaTempM = catalizardor.equals("SI") ? rc.getString("MARCA_RPM") : "",
                    serialTempM = catalizardor.equals("SI") ? rc.getString("SERIAL_TEMP_M") : "";

                    //Datos del equipo otto utilizados para inspeccion
                    String
                    marcaAg = rc.getString("MARCA_AG"),
                    modAg = rc.getString("MOD_AG"),
                    serialAg = rc.getString("SERIAL_AG"),
                    marcaBg = rc.getString("MARCA_BG"),
                    modBg = rc.getString("MOD_BG"),
                    serialBg = rc.getString("SERIAL_BG"),
                    pef = "0."+rc.getString("PEF"),
                    serialE = rc.getString("SERIAL_E").split("-")[0],
                    marcaRpm = catalizardor.equals("SI") ? rc.getString("MARCA_RPM") : rc.getString("MARCA_RPM").replace("/"+serialTempM, ""),
                    serialRpm = rc.getString("SERIAL_RPM"),
                    marcaTempA = rc.getString("marca_termohigrometro"),
                    serialTempA = rc.getString("serial_termohigrometro"),
                    marcaHumR = rc.getString("marca_termohigrometro"),
                    serialHumR = rc.getString("serial_termohigrometro"),
                    nomSoft = software,
                    verSoft = vSoftware,
                    desSoft = "Soltelec",
                    tipIdeVgp = rc.getString("TIP_IDE_VGP"),
                    numIdeVgp = rc.getString("NUM_IDE_VGP"),
                    nomVgp = rc.getString("NOM_VGP"),
                    fFugas = rc.getString("F_FUGAS"),
                    fVgp = rc.getString("F_VGP"),
                    pAltaLab = rc.getString("P_ALTA_LAB"),
                    pAltaCil = rc.getString("P_ALTA_CIL"),
                    pAltaCer = rc.getString("P_ALTA_CER"),
                    pBajaLab = rc.getString("P_BAJA_LAB"),
                    pBajaCil = rc.getString("P_BAJA_CIL"),
                    pBajaCer = rc.getString("P_BAJA_CER"),
                    pHcAltoP = redondeoSegunNorma(rc.getBigDecimal("P_HC_ALTO_P")),
                    pHcAltoH = rc.getString("P_HC_ALTO_H"),
                    pHcBajoP = redondeoSegunNorma(rc.getBigDecimal("P_HC_BAJO_P")),
                    pHcBajoH = rc.getString("P_HC_BAJO_H"),
                    pCoAlto = rc.getString("P_CO_ALTO"),
                    pCoBajo = redondeoSegunNorma(rc.getBigDecimal("P_CO_BAJO")),
                    pCo2Alto = redondeoSegunNorma(rc.getBigDecimal("P_CO2_ALTO")),
                    pCo2Bajo = redondeoSegunNorma(rc.getBigDecimal("P_CO2_BAJO")),
                    pO2Alto = "0",
                    pO2Bajo = "0",
                    rHcAltoP = redondeoSegunNorma(rc.getBigDecimal("R_HC_ALTO_P")),
                    rHcAltoH = rc.getString("R_HC_ALTO_H"),
                    rHcBajoP = redondeoSegunNorma(rc.getBigDecimal("R_HC_BAJO_P")),
                    rHcBajoH = rc.getString("R_HC_BAJO_H"),
                    rCoAlto = redondeoSegunNorma(rc.getBigDecimal("R_CO_ALTO")),
                    rCoBajo = redondeoSegunNorma(rc.getBigDecimal("R_CO_BAJO")),
                    rCo2Alto = redondeoSegunNorma(rc.getBigDecimal("R_CO2_ALTO")),
                    rCo2Bajo = redondeoSegunNorma(rc.getBigDecimal("R_CO2_BAJO")),
                    rO2Alto = redondeoSegunNorma(rc.getBigDecimal("R_O2_ALTO")),
                    rO2Bajo = redondeoSegunNorma(rc.getBigDecimal("R_O2_BAJO")),
                    cVgp = "",
                    rVgp = rc.getString("R_VGP").equalsIgnoreCase("1") ? "Aprobada" : "Reprobada";

                    //equipos diesel, algunos datos de diesel ya se encuentran en otto(Los que comparten el mismo nombre)
                    String 
                    ltoe = pef,
                    tipIdeLin = tipIdeVgp,
                    numIdeLin = numIdeVgp,
                    nomLin = nomVgp,
                    fechaLin = rc.getString("FECHA_LIN"),
                    pAltoLab = rc.getString("P_ALTO_LAB"),
                    pAltoSerial = rc.getString("P_ALTO_SERIAL"),
                    pAltoCer = rc.getString("P_ALTO_CER"),
                    vFdnAlto = rc.getString("V_FDN_ALTO"),
                    pBajoLab = rc.getString("P_BAJO_LAB"),
                    pBajoSerial = rc.getString("P_BAJO_SERIAL"),
                    pBajoCer = rc.getString("P_BAJO_CER"),
                    vFdnBajo = rc.getString("V_FDN_BAJO"),
                    rFdnCero = rc.getString("R_FDN_CERO"),
                    rFdnBajo = rc.getString("R_FDN_BAJO"),
                    rFdnAlto = rc.getString("R_FDN_ALTO"),
                    rFndCien = rc.getString("R_FDN_CIEN"),
                    rLin = rc.getString("R_LIN");

                    //Datos generales de la inspeccion
                    String
                    tipIdeDt = "C",
                    numIdeDt = rc.getString("NUM_IDE_DT"),
                    nomDt = rc.getString("NOM_DT"),
                    tipIdeIt = "C",
                    numIdeIt = rc.getString("NUM_IDE_IT"),
                    nomIt = rc.getString("NOM_IT"),
                    numFur = rc.getString("NUM_FUR"),
                    fechaFur = rc.getString("FECHA_FUR"),
                    consRunt = rc.getString("CONS_RUNT"),
                    furAsoc = rc.getString("FUR_ASOC"),
                    certRtmyg = rc.getString("CERT_RTMYG"),
                    fIniInsp = rc.getString("F_INI_INSP"),
                    fFinInsp = rc.getString("F_FIN_INSP"),
                    fAborto = "",
                    cAborto = "",
                    lugarTemp = "" ,
                    tAmb = redondeoSegunNorma(rc.getBigDecimal("T_AMB")),
                    hRel = redondeoSegunNorma(rc.getBigDecimal("H_REL"));

                    if(rc.getString("LUGAR_TEMP").equals("B")) lugarTemp = "Bloque";
                    if(rc.getString("LUGAR_TEMP").equals("A")) lugarTemp = "Aceite";
                    if(rc.getString("LUGAR_TEMP").equals("I")) lugarTemp = "Método de tiempo";

                    //Resultados otto
                    String 
                    tMotor = rc.getString("LUGAR_TEMP").equals("C") ? "" : redondeoSegunNorma(rc.getBigDecimal("T_MOTOR")),
                    rpmRal = rc.getString("RPM_RAL"),
                    rpmCru = redondeoSegunNorma(rc.getBigDecimal("RPM_CRU")),
                    humo = rc.getString("HUMO").equals("1") ? "SI" : "NO",
                    corrO2 = rc.getString("CORR_O2").equals("1") ? "SI" : "NO",
                    dilucion = rc.getInt("R_O2_RAL") > 5 ? "SI" : "NO",
                    rpmFuera = rc.getString("RPM_FUERA").equals("1") ? "SI" : "NO",
                    fugaTubo = rc.getString("FUGA_TUBO").equals("1") ? "SI" : "NO",
                    salidasAd = rc.getString("SALIDAS_AD").equals("1") ? "SI" : "NO",
                    fugaAceite = rc.getString("FUGA_ACEITE").equals("1") ? "SI" : "NO",
                    fugaComb = rc.getString("FUGA_COMB").equals("1") ? "SI" : "NO",
                    admisionNc = rc.getString("ADMISION_NC").equals("1") ? "SI" : "NO",
                    recirculacion = rc.getString("RECIRCULACION").equals("1") ? "SI" : "NO",
                    accTubo = rc.getString("AC_DISP").equals("1") ? "SI" : "NO",
                    refrigNc = rc.getString("REFRIG_NC").equals("1") ? "SI" : "NO",
                    rHcRal = redondeoSegunNorma(rc.getBigDecimal("R_HC_RAL")),
                    rHcCru = redondeoSegunNorma(rc.getBigDecimal("R_HC_CRU")),
                    rCoRal = redondeoSegunNorma(rc.getBigDecimal("R_CO_RAL")),
                    rCoCru = redondeoSegunNorma(rc.getBigDecimal("R_CO_CRU")),
                    rCo2Ral = redondeoSegunNorma(rc.getBigDecimal("R_CO2_RAL")),
                    rCo2Cru = redondeoSegunNorma(rc.getBigDecimal("R_CO2_CRU")),
                    rO2Ral = redondeoSegunNorma(rc.getBigDecimal("R_O2_RAL")),
                    rO2Cru = redondeoSegunNorma(rc.getBigDecimal("R_O2_CRU")),
                    rGasScor = "",
                    ncEmisiones = rc.getString("NC_EMISIONES").equals("1") ? "SI" : "NO",
                    resFinal = rc.getString("RES_FINAL").equals("Y") ? "APROBADO" : "REPROBADO";


                    double o2 = !rO2Ral.equals("") ? Double.parseDouble(rO2Ral.replace(",", ".")) : 0;
                    if (o2 > 6) {
                        double co2 = Double.parseDouble(rCo2Ral.replace(",", "."));
                        double co = Double.parseDouble(rCoRal.replace(",", "."));
                        double hc = Double.parseDouble(rHcRal.replace(",", "."));
                        String datos = "";
                        double hcAnt = (hc / ((21 - 6) / (21 - o2)));
                        double coANT = (co / ((21 - 6) / (21 - o2)));
                        //double co2ANT = (co2 / ((21 - 6) / (21 - o2)));
                        datos += String.format("%.2f", hcAnt) + "HC";
                        datos += String.format("%.2f", coANT) + "CO";
                        datos += String.format("%.2f", co2) + "CO2";
                        datos += String.format("%.2f", o2) + "O2";
                        rGasScor = datos;
                    }

                    String
                    tInicialMotor = redondeoSegunNorma(rc.getBigDecimal("T_INICIAL_MOTOR")),
                    tFinalMotor = redondeoSegunNorma(rc.getBigDecimal("T_FINAL_MOTOR")),
                    rpmRal2 = rc.getString("RPM_RAL2"),
                    rpmGob = redondeoSegunNorma(rc.getBigDecimal("RPM_GOB")),
                    admicionNc= rc.getString("ADMISION_NC").equals("1") ? "SI" : "NO",
                    acDisp = rc.getString("AC_DISP").equals("1") ? "SI" : "NO",
                    difTemp10 = rc.getString("DIF_TEMP10").equals("1") ? "SI" : "NO",
                    gobNc = rc.getString("GOB_NC").equals("1") ? "SI" : "NO",
                    funMotor = rc.getString("FUN_MOTOR").equals("1") ? "SI" : "NO",
                    accSubita = rc.getString("ACC_SUBITA").equals("1") ? "SI" : "NO",
                    fallaSubita = rc.getString("FALLA_SUBITA").equals("1") ? "SI" : "NO",
                    difAritm = rc.getString("DIF_ARITM").equals("1") ? "SI" : "NO",
                    rpmRalPre = redondeoSegunNorma(rc.getBigDecimal("RPM_RAL_PRE")),
                    rpmGobPre = redondeoSegunNorma(rc.getBigDecimal("RPM_GOB_PRE")),
                    rOpPre = redondeoSegunNorma(calcularN(rc.getBigDecimal("R_DEN_PRE"))),
                    rDenPre = redondeoSegunNorma(rc.getBigDecimal("R_DEN_PRE")),
                    rpmRalC1 = redondeoSegunNorma(rc.getBigDecimal("RPM_RAL_C1")),
                    rpmGobC1 = redondeoSegunNorma(rc.getBigDecimal("RPM_GOB_C1")),
                    rOpC1 = redondeoSegunNorma(calcularN(rc.getBigDecimal("R_DEN_C1"))),
                    rDenC1 = redondeoSegunNorma(rc.getBigDecimal("R_DEN_C1")),
                    rpmRalC2 = redondeoSegunNorma(rc.getBigDecimal("RPM_RAL_C2")),
                    rpmGobC2 = redondeoSegunNorma(rc.getBigDecimal("RPM_GOB_C2")),
                    rOpC2 = redondeoSegunNorma(calcularN(rc.getBigDecimal("R_DEN_C2"))),
                    rDenC2 = redondeoSegunNorma(rc.getBigDecimal("R_DEN_C2")),
                    rpmRalC3 = redondeoSegunNorma(rc.getBigDecimal("RPM_RAL_C3")),
                    rpmGobC3 = redondeoSegunNorma(rc.getBigDecimal("RPM_GOB_C3")),
                    rOpC3 = redondeoSegunNorma(calcularN(rc.getBigDecimal("R_DEN_C3"))),
                    rDenC3 = redondeoSegunNorma(rc.getBigDecimal("R_DEN_C3")),
                    rFinalOp = redondeoSegunNorma(calcularN(rc.getBigDecimal("R_FINAL_DEN"))),
                    rFinalDen = redondeoSegunNorma(rc.getBigDecimal("R_FINAL_DEN"));

                    listaPropietarios.add(
                        new PropietariosNtc(
                            tipIdeProp, numIdeProp, nomProp, dirProp, tel1Prop, tel2Prop, munProp, corrEProp));

                    listaDatosVehiculos.add(
                        new VehiculoNtc(
                            placa, modelo, numMotor, vin, modMotor, diaEscape, cilindraje, licTrans, 
                            km, gnvConv, gnvConvV, marca, linea, clase, servicio, tipComb, tipMotor, 
                            rpmFab, ralMinFab, ralMaxFab, gobMinFab, gobMaxFab, disMotor, numEscape));

                    listaEquiposOtto.add(
                        new EquiposSoftOttoNtc(
                            marcaAg, modAg, serialAg, marcaBg, modBg, serialBg, pef, serialE, marcaRpm, 
                            serialRpm, marcaTempM, serialTempM, marcaTempA, serialTempA, marcaHumR, 
                            serialHumR, nomSoft, verSoft, desSoft, tipIdeVgp, numIdeVgp, nomVgp, fFugas, 
                            fVgp, pAltaLab, pAltaCil, pAltaCer, pBajaLab, pBajaCil, pBajaCer, pHcAltoP, 
                            pHcAltoH, pHcBajoP, pHcBajoH, pCoAlto, pCoBajo, pCo2Alto, pCo2Bajo, pO2Alto, 
                            pO2Bajo, rHcAltoP, rHcAltoH, rHcBajoP, rHcBajoH, rCoAlto, rCoBajo, rCo2Alto, 
                            rCo2Bajo, rO2Alto, rO2Bajo, cVgp, rVgp));

                    listaEquiposDiesel.add(
                        new EquiposSoftDieselNtc(
                            marcaAg, modAg, serialAg, marcaBg, modBg, serialBg, ltoe, serialE, marcaRpm, 
                            serialRpm, marcaTempM, serialTempM, marcaTempA, serialTempA, marcaHumR, 
                            serialHumR, nomSoft, verSoft, desSoft, tipIdeLin, numIdeLin, nomLin, fechaLin, 
                            pAltoLab, pAltoSerial, pAltoCer, vFdnAlto, pBajoLab, pBajoSerial, pBajoCer, 
                            vFdnBajo, rFdnCero, rFdnBajo, rFdnAlto, rFndCien, rLin));

                    listaDatosGenerales.add(
                        new DatosGeneralesNtc(
                            tipIdeDt, numIdeDt, nomDt, tipIdeIt, numIdeIt, nomIt, numFur, fechaFur, 
                            consRunt, furAsoc, certRtmyg, fIniInsp, fFinInsp, fAborto, cAborto, 
                            catalizardor, lugarTemp, tAmb, hRel));

                    listaResultadosOtto.add(
                        new ResultadosOttoNtc(
                            tMotor, rpmRal, rpmCru, humo, corrO2, dilucion, rpmFuera, fugaTubo, 
                            salidasAd, fugaAceite, fugaComb, admisionNc, recirculacion, accTubo, 
                            refrigNc, rHcRal, rHcCru, rCoRal, rCoCru, rCo2Ral, rCo2Cru, rO2Ral, 
                            rO2Cru, rGasScor, ncEmisiones, resFinal));

                    listaResultadosDiesel.add(
                        new ResultadosDieselNtc(
                            tInicialMotor, tFinalMotor, rpmRal2, rpmGob, rpmFuera, fugaTubo, salidasAd, 
                            fugaAceite, fugaComb, admicionNc, acDisp, accTubo, refrigNc, difTemp10, 
                            gobNc, funMotor, accSubita, fallaSubita, difAritm, ncEmisiones, rpmRalPre, 
                            rpmGobPre, rOpPre, rDenPre, rpmRalC1, rpmGobC1, rOpC1, rDenC1, rpmRalC2, rpmGobC2, rOpC2, 
                            rDenC2, rpmRalC3, rpmGobC3, rOpC3, rDenC3, rFinalOp, rFinalDen, resFinal));
                }
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }

        return new ReporteNtc(listaDatosCda, listaPropietarios, listaDatosVehiculos, listaEquiposOtto, listaEquiposDiesel, listaDatosGenerales, listaResultadosOtto, listaResultadosDiesel);
    }

    private static BigDecimal calcularN(BigDecimal k) {

        if (k == null) return BigDecimal.ZERO;
        
        // Definir el contexto matemático con la precisión deseada
        MathContext mc = new MathContext(10, RoundingMode.HALF_UP);

        // Calcular la constante
        BigDecimal factor = new BigDecimal("0.430", mc);

        // Calcular la expresión
        BigDecimal expTerm = k.multiply(factor, mc).negate();
        BigDecimal expValue = new BigDecimal(Math.exp(expTerm.doubleValue()), mc);

        // Calcular el resultado final
        BigDecimal result = new BigDecimal("100", mc).multiply(BigDecimal.ONE.subtract(expValue, mc), mc);

        return result;
    }

    private static String getResultado(String esAbortada, String esAprobada){
        if(esAbortada.equalsIgnoreCase("Y")) return "Abortada";
        return esAprobada.equalsIgnoreCase("Y") ? "Aprobada" : "Reprobada";
    }

    private static DatosVehiculoCorantioquia getVehiculosCorantioquia(ResultSet rc) throws SQLException {
        String aprobado = rc.getString("Aprobado");
        String consecutivoPrueba = rc.getString("Id_Pruebas");
        Long numeroCertificado = aprobado.equalsIgnoreCase("Y") ? rc.getLong("consecutivo_runt") : null;
        String marca = rc.getString("Nombre_marca");
        Integer modelo = rc.getInt("Modelo");
        String placa = rc.getString("CARPLATE");
        Integer cilindrajeCm3 = rc.getInt("Cinlindraje");
        String tipoMotor = rc.getString("Tiempos_motor") + "T";
        String design = rc.getString("diseño"); 

        return new DatosVehiculoCorantioquia(
            numeroCertificado, consecutivoPrueba, marca, modelo, placa, cilindrajeCm3, tipoMotor, design);
    }

    private static DatosPruebaCorantioquia getDatosPruebaCorantioquia(
        ResultSet rc, String ciudad, String direccion) throws SQLException{

        String fechaPruebaStr =  rc.getString("Fecha_prueba");
        String nombreUsuario = rc.getString("Nombre_usuario");
        String temperaturaAmbiente = redondeoSegunNorma(rc.getBigDecimal("temperatura_ambiente"));
        String humedadRelativa = redondeoSegunNorma(rc.getBigDecimal("humedad_relativa"));

        return new DatosPruebaCorantioquia(
            fechaPruebaStr, nombreUsuario, temperaturaAmbiente, 
            humedadRelativa, ciudad, direccion);
    }

    private static ResultadoPruebaCorantioquia getResultadoPruebaCorantioquia(ResultSet rc)throws SQLException{
        String temperaturaMotor = redondeoSegunNorma(rc.getBigDecimal("temperatura_motor"));
        String nCilindros = redondeoSegunNorma(rc.getBigDecimal("Numero_exostos"));
        String rpmRalenti = redondeoSegunNorma(rc.getBigDecimal("rpm_ralenti"));
        String hcRalenti = redondeoSegunNorma(rc.getBigDecimal("hc_ralenti"));
        String coRalenti = redondeoSegunNorma(rc.getBigDecimal("co_ralenti"));
        String co2Ralenti = redondeoSegunNorma(rc.getBigDecimal("co2_ralenti"));
        String o2Ralenti = redondeoSegunNorma(rc.getBigDecimal("O2_ralenti"));
        String presenciaDilucion = "";
        String conceptoFinal = getResultado(rc.getString("Abortada"), rc.getString("Aprobada"));

        if(rc.getString("Tiempos_motor").equalsIgnoreCase("4")) presenciaDilucion= rc.getDouble("O2_ralenti") >= 11.0 ? "SI":"NO"; //Dentro de este if esta otro if cardinal
        if(rc.getString("Tiempos_motor").equalsIgnoreCase("2")) presenciaDilucion= rc.getDouble("O2_ralenti") >= 6.0 ? "SI":"NO"; //Dentro de este if esta otro if cardinal

        return new ResultadoPruebaCorantioquia(
            temperaturaMotor, nCilindros, rpmRalenti, hcRalenti, coRalenti, co2Ralenti, 
            o2Ralenti, presenciaDilucion, conceptoFinal);
    }

    private static EquipoAnalizadorCorantioquia getAnalizadorCorantioquia(
        ResultSet rc, String software, String vSoftware, PreparedStatement consultaEquipo)throws SQLException{

        Date fechaPrueba = rc.getDate("Fecha_prueba");
        String pef = "0."+ rc.getString("pef") ;
        String marcaAnalizador  = rc.getString("marca");
        String serie = rc.getString("serialEquipo");

        String spanHcBaja = "",
        spanCoBaja = "",
        spanCo2Baja = "",
        valorLeidoHcBaja = "",
        valorLeidoCoBaja = "",
        valorLeidoCo2Baja = "",
        spanHcAlta = "",
        spanCoAlta = "",
        spanCo2Alta = "",
        valorLeidoHcAlta = "",
        valorLeidoCoAlta = "",
        valorLeidoCo2Alta = "",
        fechaVerificacion = "",
        resultadoVerificacion = "";

        String[] partesSerial = serie.split(";");

        String nSerieAnalizador = partesSerial[1].split("/")[0];
        String nSerieBanco = partesSerial[0];
        String nSerieElectronicoAnalizador = nSerieAnalizador+"PEF"+rc.getString("pef")+"-"+partesSerial[0];

        consultaEquipo.setDate(1, new java.sql.Date(fechaPrueba.getTime()));
        consultaEquipo.setString(2, ("%"+partesSerial[0]+"%"));

        try(ResultSet resultadoEquipo = consultaEquipo.executeQuery()){
            while (resultadoEquipo.next()) {
                
                spanHcBaja  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("span_hc_baja"));
                spanCoBaja  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("span_co_baja"));
                spanCo2Baja  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("span_co2_baja"));
                valorLeidoHcBaja  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("valor_leido_hc_baja"));
                valorLeidoCoBaja  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("valor_leido_co_baja"));
                valorLeidoCo2Baja  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("valor_leido_co2_baja"));
                spanHcAlta  =  redondeoSegunNorma(resultadoEquipo.getBigDecimal("span_hc_alta"));
                spanCoAlta  =  redondeoSegunNorma(resultadoEquipo.getBigDecimal("span_co_alta"));
                spanCo2Alta  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("span_co2_alta"));
                valorLeidoHcAlta  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("valor_leido_hc_alta"));
                valorLeidoCoAlta  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("valor_leido_co_alta"));
                valorLeidoCo2Alta  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("valor_leido_co2_alta"));
                fechaVerificacion  = resultadoEquipo.getString("fecha_verificacion");
                resultadoVerificacion  = resultadoEquipo.getInt("calibracion_aprobada") == 1 ? "Aprobada" : "Reprobada";
            }
        }

        return new EquipoAnalizadorCorantioquia(
            pef, marcaAnalizador, serie, nSerieAnalizador, nSerieBanco, nSerieElectronicoAnalizador,
            software, vSoftware, spanHcBaja, spanCoBaja, spanCo2Baja, valorLeidoHcBaja, valorLeidoCoBaja, 
            valorLeidoCo2Baja, spanHcAlta, spanCoAlta, spanCo2Alta, valorLeidoHcAlta, valorLeidoCoAlta, 
            valorLeidoCo2Alta, fechaVerificacion, resultadoVerificacion);
    }

    private static String getNorma(int modelo, int cilindraje){
        if(cilindraje < 5000){
            if (modelo <= 2000) return "6.0";
            if (modelo <= 2015 ) return "5.0";
            if (modelo >= 2016) return "4.0";
        }

        if (modelo <= 2000) return "5.5";
        if (modelo <= 2015 ) return "4.5";
        return "3.5";
    }

    private static String redondeoSegunNorma(BigDecimal numero) {
        String formato;
        if (numero == null) return "";
        if (numero.compareTo(BigDecimal.TEN) < 0) formato = "0.0#";
        else if (numero.compareTo(new BigDecimal("100")) < 0) formato = "0.#";
        else formato = "#";

        DecimalFormat convertidorAFormato = new DecimalFormat(formato);

        String resultado = convertidorAFormato.format(numero);

        if (!resultado.contains(",") && formato.length() == 3) resultado += ",0"; //agrega un 0 en caso de que termine 0 la decimal
        if (resultado.length() != formato.length() && formato.length() == 4) resultado += "0"; //agrega un 0 en caso de que termine 0 la decimal

        return resultado;
    }

    private static String esKmFuncional(int km){
        if(km == 0) return "No funcional";
        return String.valueOf(km);
    }

    @SuppressWarnings("sonar")
    private static Dagma crearDagmaDesdeResultSet(ResultSet rc) throws SQLException {
        String cda = rc.getString("Cda");
        String registro = rc.getString("registro");
        String fecha = rc.getString("fecha");
        String certificado = rc.getString("certificado");
        String tipoVehiculo = rc.getString("tipo_vehiculo");
        String tipoServicio = rc.getString("tipo_servicio");
        String placa = rc.getString("placa");
        Integer modelo = rc.getInt("modelo");
        String kilometraje = esKmFuncional(rc.getInt("kilometraje"));
        String tipoCombustible = rc.getString("tipo_combustible");
    
        String coRalenti = redondeoSegunNorma(rc.getBigDecimal("CO_ralenti"));
        String co2Ralenti = redondeoSegunNorma(rc.getBigDecimal("CO2_ralenti"));
        String o2Ralenti = redondeoSegunNorma(rc.getBigDecimal("O2_ralenti"));
        String hcRalenti = redondeoSegunNorma(rc.getBigDecimal("HC_ralenti"));
        String coCrucero = redondeoSegunNorma(rc.getBigDecimal("CO_crucero"));
        String co2Crucero = redondeoSegunNorma(rc.getBigDecimal("CO2_crucero"));
        String o2Crucero = redondeoSegunNorma(rc.getBigDecimal("O2_crucero"));
        String hcCrucero = redondeoSegunNorma(rc.getBigDecimal("HC_crucero"));
    
        String opacidad = redondeoSegunNorma(rc.getBigDecimal("Opacidad"));
        String resultado = getResultado(rc.getString("es_abortada"), rc.getString("resultado_prueba"));
        String ruido = redondeoSegunNorma(rc.getBigDecimal("ruido"));
        Integer cilindrada = rc.getInt("cilindrada");
        String cilindradaStr = String.valueOf(cilindrada);
    
        String norma = opacidad.isEmpty() ? "" : getNorma(modelo, cilindrada);
    
        return new Dagma(
            cda, registro, fecha, certificado, tipoVehiculo, tipoServicio, 
            placa, modelo, kilometraje, tipoCombustible, coRalenti, co2Ralenti, 
            o2Ralenti, hcRalenti, coCrucero, co2Crucero, o2Crucero, hcCrucero, 
            norma, opacidad, resultado, ruido, cilindradaStr);
    }

    

    private static DatosCda getDatosCda(
        PreparedStatement consultaCda, PreparedStatement obtenerDepartamento, 
        PreparedStatement obtenerNorma, Statement stmt ) throws SQLException { 

        String crearNormaAplicada = "ALTER TABLE cda ADD COLUMN norma_aplicada VARCHAR(50)";
        String crearDepartamento = "ALTER TABLE cda ADD COLUMN departamento VARCHAR(50)";

        String cm = "";
        String nombreCda = "";
        String tipoDocumentoCda ="NIT";
        String nitCda = "";
        String personaContactoCda = "";
        String emailCda = "";
        String telefonoCda = "";
        String departamentoCda = "";
        String ciudadCda = "";
        String nResoluAutoridadAmbientalCda = "";
        String fechaResoluCda = "";
        String claseCda = "";
        String nExpedienteAutoridadAmbientalCda ="";
        String nTotalOpacimetrosCda = "";
        String nTotalOttosCda = "";
        String nTotalMotos4t = "";
        String nTotalMotos2t = "";
        String direccionCda = "";
        String normaAplicada = "";

        try(ResultSet resultadoCda = consultaCda.executeQuery()){
            while (resultadoCda.next()) {
                cm = resultadoCda.getString("CM");
                nombreCda = resultadoCda.getString("nombre");
                nitCda = resultadoCda.getString("NIT");
                personaContactoCda = resultadoCda.getString("Nombre_usuario");
                emailCda = resultadoCda.getString("correo");
                telefonoCda = resultadoCda.getString("telefono");

                /* if (!resultadoCda.getBoolean("el_departamento_existe")) //Si el departamento no existe creara una columna llamada departamento
                    stmt.executeUpdate(crearDepartamento);

                if (!resultadoCda.getBoolean("norma_aplicada_existe")) //Si el departamento no existe creara una columna llamada departamento
                    stmt.executeUpdate(crearNormaAplicada); */
                
                try(ResultSet resultadoDepartamento = obtenerDepartamento.executeQuery()){
                    while (resultadoDepartamento.next()) {
                        departamentoCda = resultadoDepartamento.getString("departamento");
                        if (departamentoCda == null) 
                            departamentoCda = "Añada el departamento en la tabla cda en la columna departamento y vuelva a generar este reporte para que aparezca";
                    }
                }

                try(ResultSet resultadoNorma = obtenerNorma.executeQuery()){
                    while (resultadoNorma.next()) {
                        normaAplicada = resultadoNorma.getString("norma_aplicada");
                        if (normaAplicada == null) 
                            normaAplicada = "Añada la norma aplicada en la tabla cda en la columna norma_aplicada y vuelva a generar este reporte para que aparezca";
                    }
                }

                ciudadCda = resultadoCda.getString("ciudad");
                nResoluAutoridadAmbientalCda = resultadoCda.getString("Resolucion_Ambiental");
                fechaResoluCda = resultadoCda.getString("Fecha_Resolucion_Ambiental");
                claseCda = resultadoCda.getString("clase_cda");
                nExpedienteAutoridadAmbientalCda = resultadoCda.getString("Nro_Expediente_Autoridad_Ambiental");
                nTotalOpacimetrosCda = resultadoCda.getString("Total_Eq_Diesel");
                nTotalOttosCda = resultadoCda.getString("Total_Eq_Otto");
                nTotalMotos4t = resultadoCda.getString("Total_Eq_4T");
                nTotalMotos2t = resultadoCda.getString("Total_Eq_2T");

                direccionCda = resultadoCda.getString("direccion");
            }
        }

        return new DatosCda(
            cm, nombreCda, tipoDocumentoCda, nitCda, 
            personaContactoCda, emailCda, telefonoCda, departamentoCda, 
            ciudadCda, direccionCda, nResoluAutoridadAmbientalCda, fechaResoluCda, 
            claseCda, nExpedienteAutoridadAmbientalCda, 
            nTotalOpacimetrosCda, nTotalOttosCda, nTotalMotos4t, nTotalMotos2t, normaAplicada);
    }

    private static InicioPruebaCorpocaldas getInicioPruebaCorpocaldas(
        ResultSet rc, String municipio, String direccion, String nombrePrograma, String versionPrograma) throws SQLException{
        String fechaInicioPrueba = rc.getString("Fecha_prueba");
        fechaInicioPrueba = fechaInicioPrueba.replace('-', '/');
        String fechaFinPrueba = rc.getString("Fecha_final");
        fechaFinPrueba = fechaFinPrueba.replace('-', '/');
        String fur = rc.getString("n_fur");
        String nCertificado = rc.getString("n_certificado");
        String nSerieEquipo = rc.getString("serialEquipo");
        String marcaMedidor = rc.getString("marca_equipo");
        String nombreProveedor = "Soltelec";
        String idInspector = rc.getString("id_inspector");

        return new InicioPruebaCorpocaldas(
            fechaInicioPrueba, fechaFinPrueba, municipio, direccion, fur, nCertificado, 
            nSerieEquipo, marcaMedidor, nombreProveedor, nombrePrograma, versionPrograma, 
            idInspector);
    }

    private static DatosVehiculoCorpocaldas getDatosVehiculoCorpocaldas(ResultSet rc) throws SQLException{
        String placa = rc.getString("placa");
        String marca = rc.getString("marca");
        String modelo = rc.getString("modelo");
        String cilindraje = rc.getString("cilindraje");
        String kilometraje = rc.getString("km");
        String linea = rc.getString("CRLNAME");
        String clase = rc.getString("Nombre_clase");
        String servicio = rc.getString("Nombre_servicio");
        String combustible = rc.getString("Nombre_gasolina");
        String tipoMotor = rc.getString("tipo_motor") + "T";
        String nTubosEscape = rc.getString("Numero_exostos");
        String design = rc.getString("diseño");

        return new DatosVehiculoCorpocaldas(
            placa, marca, modelo, cilindraje, kilometraje, linea, clase, servicio, 
            combustible, tipoMotor, nTubosEscape, design);
    }

    private static DatosPruebaCorpocaldas getDatosPruebaCorpocaldas(ResultSet rc) throws SQLException{
        String tempAmbiente = redondeoSegunNorma(rc.getBigDecimal("temperatura_ambiente"));
        String humedadRelativa = redondeoSegunNorma(rc.getBigDecimal("humedad_relativa"));
        String ltoeOpacidad = redondeoSegunNorma(rc.getBigDecimal("Opacidad"));
        String ltoeDensidadHumo = "";
        String tempInicial = redondeoSegunNorma(rc.getBigDecimal("temperatura_inicial"));
        String tempFinal = redondeoSegunNorma(rc.getBigDecimal("temperatura_final"));

        return new DatosPruebaCorpocaldas(
            tempAmbiente, humedadRelativa, ltoeOpacidad, 
            ltoeDensidadHumo, tempInicial, tempFinal);
    }

    private static EncendidoChispaCorpocaldas getEncendidoChispaCorpocaldas(ResultSet rc) throws SQLException{
        String rpmRalenti = redondeoSegunNorma(rc.getBigDecimal("rpm_ralenti"));
        String hcRalenti = redondeoSegunNorma(rc.getBigDecimal("hc_ralenti"));
        String coRalenti = redondeoSegunNorma(rc.getBigDecimal("co_ralenti"));
        String co2Ralenti = redondeoSegunNorma(rc.getBigDecimal("co2_ralenti"));
        String o2Ralenti = redondeoSegunNorma(rc.getBigDecimal("O2_ralenti"));
        String rpmCrucero = redondeoSegunNorma(rc.getBigDecimal("rpm_crucero"));
        String hcCrucero = redondeoSegunNorma(rc.getBigDecimal("HC_crucero"));
        String coCrucero = redondeoSegunNorma(rc.getBigDecimal("CO_crucero"));
        String co2Crucero = redondeoSegunNorma(rc.getBigDecimal("CO2_crucero"));
        String o2Crucero = redondeoSegunNorma(rc.getBigDecimal("O2_crucero"));

        return new EncendidoChispaCorpocaldas(
            rpmRalenti, hcRalenti, coRalenti, co2Ralenti, o2Ralenti, rpmCrucero, 
            hcCrucero, coCrucero, co2Crucero, o2Crucero);
    }

    /*Añade los datos de encendido por compresion
    ¡NO TERMINADO! debido a que no se necesitaba en la solicitud
    pero se deja aqui por si se necesita terminar en un futuro*/
    private static EncendidoCompresionCorpocaldas getCompresionCorpocaldas(){
        String rpmRalentiCompresion = "";
        String rpmGobernadaCicloPreliminar = "";
        String resultadoCicloPreliminar = "";
        String rpmRalentoCiclo1 = "";
        String rpmGobernadaCiclo1 = "";
        String resultadoCiclo1 = "";
        String rpmRalentiCiclo2 = "";
        String rpmGobernadaCiclo2 = "";
        String resultadoCiclo2 = "";
        String rpmRalentiCiclo3 = "";
        String rpmGobernadaCiclo3 = "";
        String resultadoCiclo3 = "";
        String promedioFinal = "";
        String cicloPreliminarM1 = "";
        String ciclo1M1 = "";
        String ciclo2M1 = "";
        String ciclo3M1 = "";
        String promedioFinalM1 = "";
        String conceptoFinal = "";

        return new EncendidoCompresionCorpocaldas(
            rpmRalentiCompresion, rpmGobernadaCicloPreliminar, resultadoCicloPreliminar, 
            rpmRalentoCiclo1, rpmGobernadaCiclo1, resultadoCiclo1, rpmRalentiCiclo2, 
            rpmGobernadaCiclo2, resultadoCiclo2, rpmRalentiCiclo3, rpmGobernadaCiclo3, 
            resultadoCiclo3, promedioFinal, cicloPreliminarM1, ciclo1M1, ciclo2M1, 
            ciclo3M1, promedioFinalM1, conceptoFinal);
    }
    
    @SuppressWarnings({"java:S3776", "java:S6541"})
    private static CausasRechazoCorpocaldas getCausasRechazoCorpocaldas(ResultSet rc, String tipoMotor) throws SQLException{
        String presenciaHumo  = rc.getBoolean("presencia_humo_n_a") ? "SI" : "NO";

        String presenciaDilucion  = "";
        if(tipoMotor.equalsIgnoreCase("2T") && rc.getInt("modelo")<2010){ 
            presenciaDilucion= rc.getDouble("O2_ralenti") >= 11.0 ? "SI":"NO";

        } //Dentro de este if esta otro if cardinal
        else presenciaDilucion= rc.getDouble("O2_ralenti") >= 6.0 ? "SI":"NO"; //Dentro de este if esta otro if cardinal

        String nivelEmisionAplicable  = "";//Desconocido, no se entiende porque este campo por parte de ese formato, aun asi se tiene que tener en cuenta

        String rpmFueraRango  = rc.getInt("rpm_fuera_rango") == 1 ? "SI" : "NO";
        String fugasTuboEscape  = rc.getInt("fugas_tubo_escape") == 1 ? "SI" : "NO";
        String salidasAdicionales  = rc.getInt("salidas_adicionales_diseño") == 1 ? "SI" : "NO";
        String ausenciaTapaAceite  = rc.getInt("ausencia_tapa_aceite") == 1 ? "SI" : "NO";
        String ausenciaTapaCombustible  = rc.getInt("ausencia_tapa_combustible") == 1 ? "SI" : "NO";
        String ausenciaMalEstadoFiltroAire  = rc.getInt("ausencia_malEstado_filtro_aire") == 1 ? "SI" : "NO";
        String desconexionRecirculacion  = rc.getInt("desconexion_recirculacion") == 1 ? "SI" : "NO";
        String accesoriosDeformacionesTuboEscape  = rc.getInt("accesorios_deformaciones_tubo_escape") == 1 ? "SI" : "NO";
        String operacionIncorrectaRefrigeracion  = rc.getInt("operacion_incorrecta_refrigeracion") == 1 ? "SI" : "NO";
        String emisiones  = rc.getInt("emisiones") == 1 ? "SI" : "NO";
        String incorrectaOperacionGobernador  = rc.getInt("incorrecta_operacion_gobernador") == 1 ? "SI" : "NO";
        String fallaSubita  = rc.getInt("falla_subita") == 1 ? "SI" : "NO";
        String ejecucionIncorrecta  = rc.getInt("ejecucion_incorrecta") == 1 ? "SI" : "NO";
        String diferenciaAritmetica  = rc.getInt("diferencia_aritmetica") == 1 ? "SI" : "NO";
        String diferenciaTemp  = rc.getInt("diferencia_temperatura") == 1 ? "SI" : "NO";
        String activacionDispositivos  = rc.getInt("activacion_dispositivos") == 1 ? "SI" : "NO";

        return new CausasRechazoCorpocaldas(
            presenciaHumo, presenciaDilucion, nivelEmisionAplicable, rpmFueraRango, fugasTuboEscape, 
            salidasAdicionales, ausenciaTapaAceite, ausenciaTapaCombustible, ausenciaMalEstadoFiltroAire, 
            desconexionRecirculacion, accesoriosDeformacionesTuboEscape, operacionIncorrectaRefrigeracion, 
            emisiones, incorrectaOperacionGobernador, fallaSubita, ejecucionIncorrecta, diferenciaAritmetica, 
            diferenciaTemp, activacionDispositivos);
    }

    @SuppressWarnings({"java:S3776", "java:S6541"})
    private static CausasAbortoCorpocaldas getCausasAbortoCorpocaldas(
        ResultSet rc, String normaAplicada, String fechaFinPrueba) throws SQLException{

        String fechaAborto = "";
        String fallaEquipoMedicion = "";
        String fallaSubitaFluido = "";
        String bloqueoForzadoEquipo = "";
        String ejecucionIncorrectaAborto = "";
        String fallaDesviacion = "";

        CausasAbortoCorpocaldas causaAborto = new CausasAbortoCorpocaldas(
        fechaAborto, fallaEquipoMedicion, fallaSubitaFluido, bloqueoForzadoEquipo, 
        ejecucionIncorrectaAborto, fallaDesviacion, normaAplicada);

        List<Integer> listaCausasAborto = new ArrayList<>();
        listaCausasAborto.add(rc.getInt("falla_equipo_medicion"));
        listaCausasAborto.add(rc.getInt("falla_subita_fluido_electrico"));
        listaCausasAborto.add(rc.getInt("bloqueo_forzado_equipo_medicion"));
        listaCausasAborto.add(rc.getInt("ejecucion_incorrecta"));
        listaCausasAborto.add(rc.getInt("desviacion_cero"));

        for (Integer causa : listaCausasAborto) {
            if(causa == 1){

                fallaEquipoMedicion = listaCausasAborto.get(0) == 1 ? "X" : "";
                fallaSubitaFluido = listaCausasAborto.get(1) == 1 ? "X" : "";
                bloqueoForzadoEquipo = listaCausasAborto.get(2) == 1 ? "X" : "";
                ejecucionIncorrectaAborto = listaCausasAborto.get(3) == 1 ? "X" : "";
                fallaDesviacion = listaCausasAborto.get(4) == 1 ? "X" : "";

                causaAborto.setFechaAborto(fechaFinPrueba);
                causaAborto.setFallaEquipoMedicion(fallaEquipoMedicion);
                causaAborto.setFallaSubitaFluido(fallaSubitaFluido);
                causaAborto.setBloqueoForzadoEquipo(bloqueoForzadoEquipo);
                causaAborto.setEjecucionIncorrecta(ejecucionIncorrectaAborto);
                causaAborto.setFallaDesviacion(fallaDesviacion);
            }
        }
        return causaAborto;
    }

    private static AnalizadorCorpocaldas getAnalizadorCorpocaldas(ResultSet rc, PreparedStatement consultaEquipo)throws SQLException{
        //Formato de fechas
        Date fechaPrueba = rc.getDate("Fecha_prueba");
        
        String pef = "0."+ rc.getString("pef") ;
        String marcaAnalizador  = rc.getString("marca");
        String serie = rc.getString("serialEquipo") != null ? rc.getString("serialEquipo") : "NA;NA";

        String spanHcBaja = "",
        spanCoBaja = "",
        spanCo2Baja = "",
        valorLeidoHcBaja = "",
        valorLeidoCoBaja = "",
        valorLeidoCo2Baja = "",
        spanHcAlta = "",
        spanCoAlta = "",
        spanCo2Alta = "",
        valorLeidoHcAlta = "",
        valorLeidoCoAlta = "",
        valorLeidoCo2Alta = "",
        fechaVerificacion = "";

        String[] partesSerial = serie.split(";");

        consultaEquipo.setDate(1, new java.sql.Date(fechaPrueba.getTime()));
        consultaEquipo.setString(2, ("%"+partesSerial[0]+"%"));

        try(ResultSet resultadoEquipo = consultaEquipo.executeQuery()){
            while (resultadoEquipo.next()) {
                spanHcBaja  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("span_hc_baja"));
                spanCoBaja  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("span_co_baja"));
                spanCo2Baja  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("span_co2_baja"));
                valorLeidoHcBaja  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("valor_leido_hc_baja"));
                valorLeidoCoBaja  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("valor_leido_co_baja"));
                valorLeidoCo2Baja  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("valor_leido_co2_baja"));
                spanHcAlta  =  redondeoSegunNorma(resultadoEquipo.getBigDecimal("span_hc_alta"));
                spanCoAlta  =  redondeoSegunNorma(resultadoEquipo.getBigDecimal("span_co_alta"));
                spanCo2Alta  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("span_co2_alta"));
                valorLeidoHcAlta  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("valor_leido_hc_alta"));
                valorLeidoCoAlta  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("valor_leido_co_alta"));
                valorLeidoCo2Alta  = redondeoSegunNorma(resultadoEquipo.getBigDecimal("valor_leido_co2_alta"));
                fechaVerificacion  = resultadoEquipo.getString("fecha_verificacion");
            }
        }
        return new AnalizadorCorpocaldas(
            pef, serie, marcaAnalizador, spanHcBaja, valorLeidoHcBaja, spanCoBaja, 
            valorLeidoCoBaja, spanCo2Baja, valorLeidoCo2Baja, spanHcAlta, valorLeidoHcAlta, 
            spanCoAlta, valorLeidoCoAlta, spanCo2Alta, valorLeidoCo2Alta, 
            fechaVerificacion);
    }
}
