/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.model;

import static com.soltelec.servidor.conexion.PersistenceController.getEntityManager;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author GerenciaDesarrollo
 */
@Entity
@Table(name = "equipos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipos.findAll", query = "SELECT e FROM Equipos e"),
    @NamedQuery(name = "Equipos.findByIdEquipo", query = "SELECT e FROM Equipos e WHERE e.idEquipo = :idEquipo"),
    @NamedQuery(name = "Equipos.findByPef", query = "SELECT e FROM Equipos e WHERE e.pef = :pef"),
    @NamedQuery(name = "Equipos.findBySerial", query = "SELECT e FROM Equipos e WHERE e.serial = :serial"),
    @NamedQuery(name = "Equipos.findBySerialResolucion", query = "SELECT e FROM Equipos e WHERE e.reolucionserial = :serialresolucion"),
    @NamedQuery(name = "Equipos.findByMarca", query = "SELECT e FROM Equipos e WHERE e.marca = :marca"),
    @NamedQuery(name = "Equipos.findByNumAnalizador", query = "SELECT e FROM Equipos e WHERE e.numAnalizador = :numAnalizador")})
public class Equipos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_equipo")
    private Integer idEquipo;
    @Column(name = "serialresolucion")
    private String reolucionserial;
    @Column(name = "resolucionambiental")
    private String resolucionAmbiental;
    @Basic(optional = false)
    @Column(name = "pef")
    private int pef;
    @Basic(optional = false)
    @Column(name = "serial")
    private String serial;
    @Basic(optional = false)
    @Column(name = "serial2")
    private String serial2;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "Cod_Interno")
    private String Cod_Interno;
    @Column(name = "num_serial_bench")
    private String num_serial_bench;

    public String getCod_interno() {
        return Cod_Interno;
    }

    public void setCod_interno(String cod_interno) {
        this.Cod_Interno = cod_interno;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSerial2() {
        return serial2;
    }

    public String getCod_Interno() {
        return Cod_Interno;
    }

    public void setCod_Interno(String Cod_Interno) {
        this.Cod_Interno = Cod_Interno;
    }

    public String getNum_serial_bench() {
        return num_serial_bench;
    }

    public void setNum_serial_bench(String num_serial_bench) {
        this.num_serial_bench = num_serial_bench;
    }

    @Column(name = "marca")
    private String marca;
    @Basic(optional = false)
    @Column(name = "num_analizador")
    private String numAnalizador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipo")
    private List<Calibraciones> calibracionesList;

    public Equipos() {
    }

    public Equipos(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public Equipos(Integer idEquipo, int pef, String serial, String marca, String numAnalizador, String resulucionserial) {
        this.idEquipo = idEquipo;
        this.pef = pef;
        this.serial = serial;
        this.marca = marca;
        this.numAnalizador = numAnalizador;
        this.reolucionserial = resulucionserial;
    }

    public String getResolucionAmbiental() {
        return resolucionAmbiental;
    }

    public void setResolucionAmbiental(String resolucionAmbiental) {
        this.resolucionAmbiental = resolucionAmbiental;
    }

    public void setReolucionserial(String reolucionserial) {
        this.reolucionserial = reolucionserial;
    }

    public String getReolucionserial() {
        return reolucionserial;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public int getPef() {
        return pef;
    }

    public void setPef(int pef) {
        this.pef = pef;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNumAnalizador() {
        return numAnalizador;
    }

    public void setNumAnalizador(String numAnalizador) {
        this.numAnalizador = numAnalizador;
    }

    @XmlTransient
    public List<Calibraciones> getCalibracionesList() {
        return calibracionesList;
    }

    public void setCalibracionesList(List<Calibraciones> calibracionesList) {
        this.calibracionesList = calibracionesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEquipo != null ? idEquipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipos)) {
            return false;
        }
        Equipos other = (Equipos) object;
        if ((this.idEquipo == null && other.idEquipo != null) || (this.idEquipo != null && !this.idEquipo.equals(other.idEquipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.soltelec.model.Equipos[ idEquipo=" + idEquipo + " ]";
    }

    public List<Object> findequipos() {
        EntityManager em = getEntityManager();
        String sql = "SELECT e.id_equipo FROM equipos e INNER JOIN calibraciones c ON c.id_equipo = e.id_equipo\n"
                + "WHERE c.id_tipo_calibracion = 2";
        Query query = em.createNativeQuery(sql);
        return query.getResultList();
    }

//    public List<Object> findObs(String Prueba, String Condicion) {
//        EntityManager em = getEntityManager();
//        String sql = "SELECT * FROM pruebas p WHERE p.observaciones LIKE '%?1%' AND p.Id_Pruebas=?2";
//        Query query = em.createNativeQuery(sql);
//        query.setParameter(1, Condicion);
//        query.setParameter(2, Prueba);
//        return query.getResultList();
//    }
    public String findObs(Integer Prueba, String Condicion) {
        EntityManager em = getEntityManager();
        String sql = "SELECT * FROM pruebas p WHERE p.observaciones LIKE ?1 AND p.Id_Pruebas = ?2";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, "%" + Condicion + "%");
        query.setParameter(2, Prueba);

        List<Object> resultList = query.getResultList();

        // Si hay resultados, devolver "SI"; de lo contrario, devolver "NO".
        return resultList.isEmpty() ? "NO" : "SI";
    }

    public Object[] findDosPuntos(Date fechaInicial, Integer IdPrueba) {
        System.out.println("-------------------------------");
        System.out.println(fechaInicial);
        System.out.println(IdPrueba);
        EntityManager em = getEntityManager();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fecha = dateFormat.format(fechaInicial);
        String sql = "SELECT \n"
                + "    c.*,\n"
                + "    e.*,\n"
                + "    c2p.*\n"
                + "    \n"
                + "FROM\n"
                + "    calibraciones c\n"
                + "    INNER JOIN calibracion_dos_puntos c2p ON c.CALIBRATION = c2p.CALIBRATION\n"
                + "    INNER JOIN equipos e ON c.id_equipo = e.id_equipo\n"
                + "WHERE\n"
                + "    c.CURDATE <= ?1\n"
                + "    AND c.id_tipo_calibracion = 2\n"
                + "    AND ABS(TIME_TO_SEC(TIMEDIFF(c.CURDATE, ?1)) / 3600) <= 72\n"
                + "    AND c.id_equipo = (\n"
                + "        SELECT e.id_equipo_calibracion\n"
                + "        FROM equipos e\n"
                + "        WHERE e.serialresolucion = (\n"
                + "            SELECT p.serialEquipo\n"
                + "            FROM pruebas p\n"
                + "            WHERE p.Id_Pruebas = ?2\n"
                + "        )\n"
                + "    )\n"
                + "ORDER BY c.CURDATE DESC\n"
                + "LIMIT 1;";
        Query query = em.createNativeQuery(sql);
        System.out.println("FECHA----------------------------------------------" + fecha);
        System.out.println("PRUEBA----------------------------------------------" + IdPrueba);
        query.setParameter(1, fecha);
        query.setParameter(2, IdPrueba);
        System.out.println("");
        return (Object[]) query.getSingleResult();

    }

    public Object[] findLienalidad(Date fechaInicial, Integer IdPrueba) {
        System.out.println("-------------------------------");
        System.out.println(fechaInicial);
        System.out.println(IdPrueba);
        EntityManager em = getEntityManager();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fecha = dateFormat.format(fechaInicial);
        String sql = "SELECT \n"
                + "    c.*,\n"
                + "    e.*\n"
                + "    \n"
                + "FROM\n"
                + "    calibraciones c\n"
                + "    INNER JOIN equipos e ON c.id_equipo = e.id_equipo\n"
                + "WHERE\n"
                + "    c.CURDATE <= ?1\n"
                + "    AND c.id_tipo_calibracion = 1\n"
                + "    AND ABS(TIME_TO_SEC(TIMEDIFF(c.CURDATE, ?1)) / 3600) <= 24\n"
                + "    AND c.id_equipo = (\n"
                + "        SELECT e.id_equipo_calibracion\n"
                + "        FROM equipos e\n"
                + "        WHERE e.serialresolucion = (\n"
                + "            SELECT p.serialEquipo\n"
                + "            FROM pruebas p\n"
                + "            WHERE p.Id_Pruebas = ?2\n"
                + "        )\n"
                + "    )\n"
                + "ORDER BY c.CURDATE DESC\n"
                + "LIMIT 1;";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, fecha);
        query.setParameter(2, IdPrueba);
        System.out.println("");
        return (Object[]) query.getSingleResult();

    }

    public String findmarca(String Marca) {
        EntityManager em = getEntityManager();
        String sql = "SELECT e.marca FROM equipos e WHERE e.serialresolucion=?1;";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, Marca);
        return (String) query.getSingleResult();
    }

    public Timestamp findFugas(Date Fugas, String IdEquipo) {
        EntityManager em = getEntityManager();
        String sql = "SELECT c.CURDATE FROM calibraciones c \n"
                + "WHERE c.aprobada = 1 AND  c.id_tipo_calibracion = 3 AND c.CURDATE < ?1 AND c.aprobada = 1 AND c.id_equipo = ?2\n"
                + "ORDER BY c.CURDATE DESC LIMIT 1;";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, Fugas);
        query.setParameter(2, IdEquipo);
        return (Timestamp) query.getSingleResult();
    }

    public String findIdEquipo(String serial) {
        EntityManager em = getEntityManager();
        String sql = "SELECT e.serial2 FROM equipos e WHERE e.serialresolucion= ?1;";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, serial);
        return (String) query.getSingleResult();
    }

//    public String findLin(Date Fugas) {
//        SimpleDateFormat sdfH = new SimpleDateFormat();
//        sdfH = new SimpleDateFormat("YYYY-MM-dd HH:mm");
//        String Fecha = sdfH.format(Fugas);
//        EntityManager em = getEntityManager();
//        String sql = "SELECT c.CURDATE FROM calibraciones c \n"
//                + "WHERE c.aprobada = 1 AND  c.id_tipo_calibracion = 1 AND c.CURDATE < ?1 \n"
//                + "ORDER BY c.CURDATE DESC LIMIT 1;";
//        Query query = em.createNativeQuery(sql);
//        query.setParameter(1, Fecha);
//        return (String) query.getSingleResult();
//    }
    public Timestamp findLin(Date Fugas) {
        SimpleDateFormat sdfH = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String Fecha = sdfH.format(Fugas);
        EntityManager em = getEntityManager();
        String sql = "SELECT c.CURDATE FROM calibraciones c \n"
                + "WHERE c.aprobada = 1 AND  c.id_tipo_calibracion = 1 AND c.CURDATE < ?1 \n"
                + "ORDER BY c.CURDATE DESC LIMIT 1;";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, Fecha);
        return (Timestamp) query.getSingleResult();
    }

    public Object[] findVal(Date currentDate) {
        EntityManager em = getEntityManager();
        String sql = "SELECT c.VALOR1,c.VALOR2,c.VALOR7,c.VALOR4,c.VALOR5,c.VALOR6,c.aprobada FROM calibraciones c \n"
                + "WHERE c.aprobada = 1 AND  c.id_tipo_calibracion = 1 AND c.CURDATE < ?1 \n"
                + "ORDER BY c.CURDATE DESC LIMIT 1;";

        try {
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, currentDate);

            Object[] result = (Object[]) query.getSingleResult();
            return result;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Object[]> findInspector(int IdInspector) {
        EntityManager em = getEntityManager();
        String sql = "SELECT * FROM usuarios u WHERE u.GEUSER = ?1;";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, IdInspector);
        List<Object[]> resultList = query.getResultList();

        return resultList;
    }

    public static List<String> listaSerial(String input) {
        List<String> lista = new ArrayList<>();

        // Dividir la cadena por el carácter ";"
        String[] elementos = input.split(";");

        // Agregar los elementos a la lista
        for (String elemento : elementos) {
            lista.add(elemento);
        }

        return lista;
    }

    public static List<String> listaRPM(String input) {
        List<String> lista = new ArrayList<>();

        // Dividir la cadena por el carácter "/"
        String[] elementos = input.split("/");

        // Agregar los elementos a la lista
        for (String elemento : elementos) {
            lista.add(elemento);
        }

        return lista;
    }

}
