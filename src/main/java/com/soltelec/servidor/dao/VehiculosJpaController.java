/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.dao;

import com.soltelec.servidor.conexion.PersistenceController;
import com.soltelec.servidor.exceptions.IllegalOrphanException;
import com.soltelec.servidor.exceptions.NonexistentEntityException;
import com.soltelec.servidor.model.Aseguradoras;
import com.soltelec.servidor.model.ClasesVehiculo;
import com.soltelec.servidor.model.Colores;
import com.soltelec.servidor.model.HojaPruebas;
import com.soltelec.servidor.model.LineasVehiculos;
import com.soltelec.servidor.model.Llantas;
import com.soltelec.servidor.model.Marcas;
import com.soltelec.servidor.model.NativoVehiculosSuperIntendencia;
import com.soltelec.servidor.model.Propietarios;
import com.soltelec.servidor.model.Servicios;
import com.soltelec.servidor.model.TipoVehiculo;
import com.soltelec.servidor.model.TiposGasolina;
import com.soltelec.servidor.model.Usuarios;
import com.soltelec.servidor.model.Vehiculos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author SOLTELEC
 */
public class VehiculosJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public List<Marcas> obtenerMarcas() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Marcas.class));
        Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }

    public void create(Vehiculos vehiculos) {
        if (vehiculos.getHojaPruebasCollection() == null) {
            vehiculos.setHojaPruebasCollection(new ArrayList<HojaPruebas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Llantas llantas = vehiculos.getLlantas();
            if (llantas != null) {
                llantas = em.getReference(llantas.getClass(), llantas.getWheel());
                vehiculos.setLlantas(llantas);
            }
            Servicios servicios = vehiculos.getServicios();
            if (servicios != null) {
                servicios = em.getReference(servicios.getClass(), servicios.getService());
                vehiculos.setServicios(servicios);
            }
            Aseguradoras aseguradoras = vehiculos.getAseguradoras();
            if (aseguradoras != null) {
                aseguradoras = em.getReference(aseguradoras.getClass(), aseguradoras.getInsuring());
                vehiculos.setAseguradoras(aseguradoras);
            }
            Usuarios usuarios = vehiculos.getUsuarios();
            if (usuarios != null) {
                usuarios = em.getReference(usuarios.getClass(), usuarios.getGeuser());
                vehiculos.setUsuarios(usuarios);
            }
            TiposGasolina tiposGasolina = vehiculos.getTiposGasolina();
            if (tiposGasolina != null) {
                tiposGasolina = em.getReference(tiposGasolina.getClass(), tiposGasolina.getFueltype());
                vehiculos.setTiposGasolina(tiposGasolina);
            }
            Colores colores = vehiculos.getColores();
            if (colores != null) {
                colores = em.getReference(colores.getClass(), colores.getColor());
                vehiculos.setColores(colores);
            }
            ClasesVehiculo clasesVehiculo = vehiculos.getClasesVehiculo();
            if (clasesVehiculo != null) {
                clasesVehiculo = em.getReference(clasesVehiculo.getClass(), clasesVehiculo.getClass1());
                vehiculos.setClasesVehiculo(clasesVehiculo);
            }
            TipoVehiculo tipoVehiculo = vehiculos.getTipoVehiculo();
            if (tipoVehiculo != null) {
                tipoVehiculo = em.getReference(tipoVehiculo.getClass(), tipoVehiculo.getCartype());
                vehiculos.setTipoVehiculo(tipoVehiculo);
            }
            Propietarios propietarios = vehiculos.getPropietarios();
            if (propietarios != null) {
                propietarios = em.getReference(propietarios.getClass(), propietarios.getCarowner());
                vehiculos.setPropietarios(propietarios);
            }
            Marcas marcas = vehiculos.getMarcas();
            if (marcas != null) {
                marcas = em.getReference(marcas.getClass(), marcas.getCarmark());
                vehiculos.setMarcas(marcas);
            }
            LineasVehiculos lineasVehiculos = vehiculos.getLineasVehiculos();
            if (lineasVehiculos != null) {
                lineasVehiculos = em.getReference(lineasVehiculos.getClass(), lineasVehiculos.getCarline());
                vehiculos.setLineasVehiculos(lineasVehiculos);
            }
            Collection<HojaPruebas> attachedHojaPruebasCollection = new ArrayList<>();
            for (HojaPruebas hojaPruebasCollectionHojaPruebasToAttach : vehiculos.getHojaPruebasCollection()) {
                hojaPruebasCollectionHojaPruebasToAttach = em.getReference(hojaPruebasCollectionHojaPruebasToAttach.getClass(), hojaPruebasCollectionHojaPruebasToAttach.getTestsheet());
                attachedHojaPruebasCollection.add(hojaPruebasCollectionHojaPruebasToAttach);
            }
            vehiculos.setHojaPruebasCollection(attachedHojaPruebasCollection);
            em.persist(vehiculos);

            if (propietarios != null) {
                propietarios.getVehiculosCollection().add(vehiculos);
                propietarios = em.merge(propietarios);
            }
            for (HojaPruebas hojaPruebasCollectionHojaPruebas : vehiculos.getHojaPruebasCollection()) {
                Vehiculos oldVehiculosOfHojaPruebasCollectionHojaPruebas = hojaPruebasCollectionHojaPruebas.getVehiculos();
                hojaPruebasCollectionHojaPruebas.setVehiculos(vehiculos);
                hojaPruebasCollectionHojaPruebas = em.merge(hojaPruebasCollectionHojaPruebas);
                if (oldVehiculosOfHojaPruebasCollectionHojaPruebas != null) {
                    oldVehiculosOfHojaPruebasCollectionHojaPruebas.getHojaPruebasCollection().remove(hojaPruebasCollectionHojaPruebas);
                    oldVehiculosOfHojaPruebasCollectionHojaPruebas = em.merge(oldVehiculosOfHojaPruebasCollectionHojaPruebas);
                }
            }
            em.getTransaction().commit();
        } finally {
            
        }
    }

    public void edit(Vehiculos vehiculos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehiculos persistentVehiculos = em.find(Vehiculos.class, vehiculos.getCar());
            Llantas llantasOld = persistentVehiculos.getLlantas();
            Llantas llantasNew = vehiculos.getLlantas();
            Servicios serviciosOld = persistentVehiculos.getServicios();
            Servicios serviciosNew = vehiculos.getServicios();
            Aseguradoras aseguradorasOld = persistentVehiculos.getAseguradoras();
            Aseguradoras aseguradorasNew = vehiculos.getAseguradoras();
            Usuarios usuariosOld = persistentVehiculos.getUsuarios();
            Usuarios usuariosNew = vehiculos.getUsuarios();
            TiposGasolina tiposGasolinaOld = persistentVehiculos.getTiposGasolina();
            TiposGasolina tiposGasolinaNew = vehiculos.getTiposGasolina();
            Colores coloresOld = persistentVehiculos.getColores();
            Colores coloresNew = vehiculos.getColores();
            ClasesVehiculo clasesVehiculoOld = persistentVehiculos.getClasesVehiculo();
            ClasesVehiculo clasesVehiculoNew = vehiculos.getClasesVehiculo();
            TipoVehiculo tipoVehiculoOld = persistentVehiculos.getTipoVehiculo();
            TipoVehiculo tipoVehiculoNew = vehiculos.getTipoVehiculo();
            Propietarios propietariosOld = persistentVehiculos.getPropietarios();
            Propietarios propietariosNew = vehiculos.getPropietarios();
            Marcas marcasOld = persistentVehiculos.getMarcas();
            Marcas marcasNew = vehiculos.getMarcas();
            LineasVehiculos lineasVehiculosOld = persistentVehiculos.getLineasVehiculos();
            LineasVehiculos lineasVehiculosNew = vehiculos.getLineasVehiculos();
            Collection<HojaPruebas> hojaPruebasCollectionOld = persistentVehiculos.getHojaPruebasCollection();
            Collection<HojaPruebas> hojaPruebasCollectionNew = vehiculos.getHojaPruebasCollection();
            List<String> illegalOrphanMessages = null;
            for (HojaPruebas hojaPruebasCollectionOldHojaPruebas : hojaPruebasCollectionOld) {
                if (!hojaPruebasCollectionNew.contains(hojaPruebasCollectionOldHojaPruebas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HojaPruebas " + hojaPruebasCollectionOldHojaPruebas + " since its vehiculos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (llantasNew != null) {
                llantasNew = em.getReference(llantasNew.getClass(), llantasNew.getWheel());
                vehiculos.setLlantas(llantasNew);
            }
            if (serviciosNew != null) {
                serviciosNew = em.getReference(serviciosNew.getClass(), serviciosNew.getService());
                vehiculos.setServicios(serviciosNew);
            }
            if (aseguradorasNew != null) {
                aseguradorasNew = em.getReference(aseguradorasNew.getClass(), aseguradorasNew.getInsuring());
                vehiculos.setAseguradoras(aseguradorasNew);
            }
            if (usuariosNew != null) {
                usuariosNew = em.getReference(usuariosNew.getClass(), usuariosNew.getGeuser());
                vehiculos.setUsuarios(usuariosNew);
            }
            if (tiposGasolinaNew != null) {
                tiposGasolinaNew = em.getReference(tiposGasolinaNew.getClass(), tiposGasolinaNew.getFueltype());
                vehiculos.setTiposGasolina(tiposGasolinaNew);
            }
            if (coloresNew != null) {
                coloresNew = em.getReference(coloresNew.getClass(), coloresNew.getColor());
                vehiculos.setColores(coloresNew);
            }
            if (clasesVehiculoNew != null) {
                clasesVehiculoNew = em.getReference(clasesVehiculoNew.getClass(), clasesVehiculoNew.getClass1());
                vehiculos.setClasesVehiculo(clasesVehiculoNew);
            }
            if (tipoVehiculoNew != null) {
                tipoVehiculoNew = em.getReference(tipoVehiculoNew.getClass(), tipoVehiculoNew.getCartype());
                vehiculos.setTipoVehiculo(tipoVehiculoNew);
            }
            if (propietariosNew != null) {
                propietariosNew = em.getReference(propietariosNew.getClass(), propietariosNew.getCarowner());
                vehiculos.setPropietarios(propietariosNew);
            }
            if (marcasNew != null) {
                marcasNew = em.getReference(marcasNew.getClass(), marcasNew.getCarmark());
                vehiculos.setMarcas(marcasNew);
            }
            if (lineasVehiculosNew != null) {
                lineasVehiculosNew = em.getReference(lineasVehiculosNew.getClass(), lineasVehiculosNew.getCarline());
                vehiculos.setLineasVehiculos(lineasVehiculosNew);
            }
            Collection<HojaPruebas> attachedHojaPruebasCollectionNew = new ArrayList<HojaPruebas>();
            for (HojaPruebas hojaPruebasCollectionNewHojaPruebasToAttach : hojaPruebasCollectionNew) {
                hojaPruebasCollectionNewHojaPruebasToAttach = em.getReference(hojaPruebasCollectionNewHojaPruebasToAttach.getClass(), hojaPruebasCollectionNewHojaPruebasToAttach.getTestsheet());
                attachedHojaPruebasCollectionNew.add(hojaPruebasCollectionNewHojaPruebasToAttach);
            }
            hojaPruebasCollectionNew = attachedHojaPruebasCollectionNew;
            vehiculos.setHojaPruebasCollection(hojaPruebasCollectionNew);
            vehiculos = em.merge(vehiculos);
            if (propietariosOld != null && !propietariosOld.equals(propietariosNew)) {
                propietariosOld.getVehiculosCollection().remove(vehiculos);
                propietariosOld = em.merge(propietariosOld);
            }
            if (propietariosNew != null && !propietariosNew.equals(propietariosOld)) {
                propietariosNew.getVehiculosCollection().add(vehiculos);
                propietariosNew = em.merge(propietariosNew);
            }

            for (HojaPruebas hojaPruebasCollectionNewHojaPruebas : hojaPruebasCollectionNew) {
                if (!hojaPruebasCollectionOld.contains(hojaPruebasCollectionNewHojaPruebas)) {
                    Vehiculos oldVehiculosOfHojaPruebasCollectionNewHojaPruebas = hojaPruebasCollectionNewHojaPruebas.getVehiculos();
                    hojaPruebasCollectionNewHojaPruebas.setVehiculos(vehiculos);
                    hojaPruebasCollectionNewHojaPruebas = em.merge(hojaPruebasCollectionNewHojaPruebas);
                    if (oldVehiculosOfHojaPruebasCollectionNewHojaPruebas != null && !oldVehiculosOfHojaPruebasCollectionNewHojaPruebas.equals(vehiculos)) {
                        oldVehiculosOfHojaPruebasCollectionNewHojaPruebas.getHojaPruebasCollection().remove(hojaPruebasCollectionNewHojaPruebas);
                        oldVehiculosOfHojaPruebasCollectionNewHojaPruebas = em.merge(oldVehiculosOfHojaPruebasCollectionNewHojaPruebas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vehiculos.getCar();
                if (findVehiculos(id) == null) {
                    throw new NonexistentEntityException("The vehiculos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehiculos vehiculos;
            try {
                vehiculos = em.getReference(Vehiculos.class, id);
                vehiculos.getCar();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vehiculos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<HojaPruebas> hojaPruebasCollectionOrphanCheck = vehiculos.getHojaPruebasCollection();
            for (HojaPruebas hojaPruebasCollectionOrphanCheckHojaPruebas : hojaPruebasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vehiculos (" + vehiculos + ") cannot be destroyed since the HojaPruebas " + hojaPruebasCollectionOrphanCheckHojaPruebas + " in its hojaPruebasCollection field has a non-nullable vehiculos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }

            Propietarios propietarios = vehiculos.getPropietarios();
            if (propietarios != null) {
                propietarios.getVehiculosCollection().remove(vehiculos);
                propietarios = em.merge(propietarios);
            }
            em.remove(vehiculos);
            em.getTransaction().commit();
        } finally {
           
        }
    }

    public List<Vehiculos> findVehiculosEntities() {
        return findVehiculosEntities(true, -1, -1);
    }

    public List<Vehiculos> findVehiculosEntities(int maxResults, int firstResult) {
        return findVehiculosEntities(false, maxResults, firstResult);
    }

    private List<Vehiculos> findVehiculosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vehiculos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
           
        }
    }

    public Vehiculos findVehiculos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vehiculos.class, id);
        } finally {
           
        }
    }

 
    
    public int getVehiculosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vehiculos> rt = cq.from(Vehiculos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
         
        }
    }

    public List<Object[]> findVehiculos1() {
        EntityManager em = getEntityManager();
        //Query query = em.createQuery("SELECT c FROM Vehiculos AS c,  Marcas m WHERE c.marcas = m");
        Query query = em.createQuery("SELECT c.car, c.carplate, m.nombremarca, c.modelo, c.cilindraje,c.numerolicencia, c.numeroSOAT FROM Vehiculos AS c,  Marcas m WHERE c.marcas = m");
        return query.getResultList();
    }
    
    
     public List<Vehiculos> findVehiculosByPlaca( String placa) {
        EntityManager em = getEntityManager();
        String queryexc = "SELECT * FROM vehiculos v WHERE v.CARPLATE =' :placa'";
        //Query query = em.createQuery("SELECT c FROM Vehiculos AS c,  Marcas m WHERE c.marcas = m");
        //Query q = em.createQuery("SELECT * FROM vehiculos v WHERE v.CARPLATE = '"+placa +"'");
        
        Query query = em.createNamedQuery("Vehiculos.findByCarplate").setParameter("carplate", placa);
        return query.getResultList();
    }

    public List<Object[]> findVehiculos_porCedula(String mat, String campo, String com) {
        EntityManager em = getEntityManager();
        //String msql = "SELECT i FROM Vehiculos i WHERE UPPER(i.carplate) = :keyword";
        // String msql = "SELECT i FROM Vehiculos i WHERE i." + campo + " " + com + " '" + mat + "'";
        String msql = "SELECT c.car, c.carplate, m.nombremarca, c.modelo, c.cinlindraje,c.numerolicencia, c.numeroSOAT FROM Vehiculos AS c,  Marcas m WHERE c." + campo + " " + com + " '" + mat + "' AND c.marcas = m";
        String keyword = mat;
        Query query = em.createQuery(msql);
//query.setParameter("keyword", "'" + keyword + "'");

        return query.getResultList();
    }

    public List<Object[]> findVehiculos_porCedPropietario(String mat, String campo, String com) {
        EntityManager em = getEntityManager();
        int mat1 = Integer.parseInt(mat);
        //String msql = "SELECT d  FROM Vehiculos d, Propietarios m WHERE d.propietarios = m  and m.carowner " + com + " " + mat1;
        String msql = "SELECT c.car, c.carplate, m.nombremarca, c.modelo, c.cinlindraje,c.numerolicencia, c.numeroSOAT FROM Vehiculos AS c,  Marcas m, Propietarios p WHERE  c.marcas = m and c.propietarios = p  and p.carowner " + com + " " + mat1;
        Query query = em.createQuery(msql);

        return query.getResultList();
    }

    public List<Object[]> findVehiculosAndPropietario(String fechaInicio, String fechaFin) {
        EntityManager em = getEntityManager();
        String sql = "SELECT v.carplate, m.nombremarca, p.nombres, p.apellidos, c.nombreCiudad, p.numerotelefono,  h.fechaingresovehiculo FROM HojaPruebas AS h "
                + "JOIN h.vehiculos as v "
                + "JOIN v.marcas m "
                + "JOIN v.propietarios p "
                + "JOIN p.ciudades c "
                + "WHERE h.fechaingresovehiculo BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' ORDER BY h.fechaingresovehiculo ASC";
        System.out.println("sql = " + sql);
        //System.out.println("SQL: " + sql);
        Query query = em.createQuery(sql);

        return query.getResultList();
    }

    public List<Vehiculos> findVehiculosByParameter(String campo, String valor, String condicion) {
        EntityManager em = getEntityManager();
        String sql;
        sql = "SELECT * FROM vehiculos v WHERE " + campo + " " + condicion + "'" + valor + "'";
        
        System.out.println("queryyyy: " + sql);
        Query q = em.createNativeQuery(sql);
        return q.getResultList();
    }

    public List<Vehiculos> findVehiculos() {
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("Vehiculos.findAll");
        return q.getResultList();
    }

    public List<NativoVehiculosSuperIntendencia> findNativoVehiculos(Date fechaInicial, Date fechaFinal) {
        EntityManager em = getEntityManager();
        Query q = em.createNativeQuery("SELECT @rownum := @rownum + 1 AS ID, "
                + "v.CARPLATE PLACA,  "
                + "m.Nombre_marca MARCA, "
                + "v.Modelo MODELO, "
                + "CASE WHEN s.SERVICE = 3 THEN 1 ELSE NULL END AS PARTICULAR, "
                + "CASE WHEN s.SERVICE = 2 THEN 1 ELSE NULL END AS PUBLICO, "
                + "CASE WHEN cv.CLASS = 10 THEN 1 ELSE NULL END AS MOTOS, "
                + "CASE WHEN cv.CLASS = 1 THEN 1 ELSE NULL END AS AUTOMOVIL, "
                + "CASE WHEN cv.CLASS = 6 THEN 1 ELSE NULL END AS CAMPERO, "
                + "CASE WHEN cv.CLASS = 5 THEN 1 ELSE NULL END AS CAMIONETA, "
                + "CASE WHEN cv.CLASS = 7 THEN 1 ELSE NULL END AS MICROBUS, "
                + "CASE WHEN cv.CLASS = 2 THEN 1 ELSE NULL END AS BUS, "
                + "CASE WHEN cv.CLASS = 3 THEN 1 ELSE NULL END AS BUSETA, "
                + "CASE WHEN cv.CLASS = 4 THEN 1 ELSE NULL END AS CAMION, "
                + "CASE WHEN cv.CLASS = 9 OR cv.CLASS = 42 THEN 1 ELSE NULL END AS VOLQUETA, "
                + "CASE WHEN cv.CLASS = 8 THEN 1 ELSE NULL END AS TRACTO_CAMION,"
                + "CASE WHEN hp.Aprobado = 'Y' THEN 1 ELSE NULL END AS R, "
                + "CASE WHEN hp.Aprobado = 'N' THEN 1 ELSE NULL END AS A, "
                + "hp.Fecha_ingreso_vehiculo FECHA, "
                + "hp.consecutivo_runt NO_CERTIFICADO, "
                + "hp.Comentarios_cda OBSERVACIONES, "
                + "CASE WHEN hp.Numero_intentos = 1 THEN 1 ELSE NULL END AS \"1°\", "
                + "CASE WHEN hp.Numero_intentos = 2 THEN 1 ELSE NULL END AS \"2°\", "
                + "CASE WHEN hp.Numero_intentos = 3 THEN 1 ELSE NULL END AS \"3°\" "
                + "FROM hoja_pruebas hp "
                + "INNER JOIN vehiculos v ON hp.Vehiculo_for = v.CAR "
                + "INNER JOIN clases_vehiculo cv ON v.CLASS = cv.CLASS "
                + "INNER JOIN servicios s ON v.SERVICE = s.SERVICE "
                + "INNER JOIN marcas m ON v.CARMARK = m.CARMARK, "
                + "(SELECT @rownum := 0) COUNT "
                + "WHERE DATE(hp.Fecha_ingreso_vehiculo) BETWEEN ? AND ? "
                + "AND v.SERVICE IN (3,2) "
                + "AND v.CLASS IN (10,1,6,5,7,2,3,4,9,42,8)", NativoVehiculosSuperIntendencia.class);
        q.setParameter(1, fechaInicial);
        q.setParameter(2, fechaFinal);

        return q.getResultList();
    }

    public static void main(String args[]) {
        VehiculosJpaController uno = new VehiculosJpaController();

        Date inicial = new Date();
        inicial.setTime(1420070400000L);

        Date ffinal = new Date();
        ffinal.setTime(1420761600000L);
        
        List<NativoVehiculosSuperIntendencia> datos = uno.findNativoVehiculos(inicial, ffinal);
        
        for (NativoVehiculosSuperIntendencia nativoVehiculosSuperIntendencia : datos) {
            System.out.println(nativoVehiculosSuperIntendencia);
        }

    }
}
