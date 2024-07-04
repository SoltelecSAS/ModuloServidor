/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.IllegalOrphanException;
import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.model.HojaPruebas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.soltelec.servidor.model.Vehiculos;
import com.soltelec.servidor.model.Usuarios;
import com.soltelec.servidor.model.Propietarios;
import com.soltelec.servidor.model.Pruebas;
import com.soltelec.servidor.conexion.PersistenceController;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class HojaPruebasJpaController {

     
    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(HojaPruebas hojaPruebas) {
        if (hojaPruebas.getPruebasCollection() == null) {
            hojaPruebas.setPruebasCollection(new ArrayList<Pruebas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehiculos vehiculos = hojaPruebas.getVehiculos();
            if (vehiculos != null) {
                vehiculos = em.getReference(vehiculos.getClass(), vehiculos.getCar());
                hojaPruebas.setVehiculos(vehiculos);
            }
            Usuarios usuarios = hojaPruebas.getUsuarios();
            if (usuarios != null) {
                usuarios = em.getReference(usuarios.getClass(), usuarios.getGeuser());
                hojaPruebas.setUsuarios(usuarios);
            }
            Propietarios propietarios = hojaPruebas.getPropietarios();
            if (propietarios != null) {
                propietarios = em.getReference(propietarios.getClass(), propietarios.getCarowner());
                hojaPruebas.setPropietarios(propietarios);
            }
            Collection<Pruebas> attachedPruebasCollection = new ArrayList<Pruebas>();
            for (Pruebas pruebasCollectionPruebasToAttach : hojaPruebas.getPruebasCollection()) {
                pruebasCollectionPruebasToAttach = em.getReference(pruebasCollectionPruebasToAttach.getClass(), pruebasCollectionPruebasToAttach.getIdPruebas());
                attachedPruebasCollection.add(pruebasCollectionPruebasToAttach);
            }
            hojaPruebas.setPruebasCollection(attachedPruebasCollection);
            em.persist(hojaPruebas);
            if (vehiculos != null) {
                vehiculos.getHojaPruebasCollection().add(hojaPruebas);
                vehiculos = em.merge(vehiculos);
            }
            if (propietarios != null) {
                propietarios.getHojaPruebasCollection().add(hojaPruebas);
                propietarios = em.merge(propietarios);
            }
            for (Pruebas pruebasCollectionPruebas : hojaPruebas.getPruebasCollection()) {
                HojaPruebas oldHojaPruebasOfPruebasCollectionPruebas = pruebasCollectionPruebas.getHojaPruebas();
                pruebasCollectionPruebas.setHojaPruebas(hojaPruebas);
                pruebasCollectionPruebas = em.merge(pruebasCollectionPruebas);
                if (oldHojaPruebasOfPruebasCollectionPruebas != null) {
                    oldHojaPruebasOfPruebasCollectionPruebas.getPruebasCollection().remove(pruebasCollectionPruebas);
                    oldHojaPruebasOfPruebasCollectionPruebas = em.merge(oldHojaPruebasOfPruebasCollectionPruebas);
                }
            }
            em.getTransaction().commit();
        } finally {           
        }
    }

    public void edit(HojaPruebas hojaPruebas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HojaPruebas persistentHojaPruebas = em.find(HojaPruebas.class, hojaPruebas.getTestsheet());
            Vehiculos vehiculosOld = persistentHojaPruebas.getVehiculos();
            Vehiculos vehiculosNew = hojaPruebas.getVehiculos();
            Usuarios usuariosOld = persistentHojaPruebas.getUsuarios();
            Usuarios usuariosNew = hojaPruebas.getUsuarios();
            Propietarios propietariosOld = persistentHojaPruebas.getPropietarios();
            Propietarios propietariosNew = hojaPruebas.getPropietarios();
            Collection<Pruebas> pruebasCollectionOld = persistentHojaPruebas.getPruebasCollection();
            Collection<Pruebas> pruebasCollectionNew = hojaPruebas.getPruebasCollection();
            List<String> illegalOrphanMessages = null;
            for (Pruebas pruebasCollectionOldPruebas : pruebasCollectionOld) {
                if (!pruebasCollectionNew.contains(pruebasCollectionOldPruebas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pruebas " + pruebasCollectionOldPruebas + " since its hojaPruebas field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (vehiculosNew != null) {
                vehiculosNew = em.getReference(vehiculosNew.getClass(), vehiculosNew.getCar());
                hojaPruebas.setVehiculos(vehiculosNew);
            }
            if (usuariosNew != null) {
                usuariosNew = em.getReference(usuariosNew.getClass(), usuariosNew.getGeuser());
                hojaPruebas.setUsuarios(usuariosNew);
            }
            if (propietariosNew != null) {
                propietariosNew = em.getReference(propietariosNew.getClass(), propietariosNew.getCarowner());
                hojaPruebas.setPropietarios(propietariosNew);
            }
            Collection<Pruebas> attachedPruebasCollectionNew = new ArrayList<Pruebas>();
            for (Pruebas pruebasCollectionNewPruebasToAttach : pruebasCollectionNew) {
                pruebasCollectionNewPruebasToAttach = em.getReference(pruebasCollectionNewPruebasToAttach.getClass(), pruebasCollectionNewPruebasToAttach.getIdPruebas());
                attachedPruebasCollectionNew.add(pruebasCollectionNewPruebasToAttach);
            }
            pruebasCollectionNew = attachedPruebasCollectionNew;
            hojaPruebas.setPruebasCollection(pruebasCollectionNew);
            hojaPruebas = em.merge(hojaPruebas);
            if (vehiculosOld != null && !vehiculosOld.equals(vehiculosNew)) {
                vehiculosOld.getHojaPruebasCollection().remove(hojaPruebas);
                vehiculosOld = em.merge(vehiculosOld);
            }
            if (vehiculosNew != null && !vehiculosNew.equals(vehiculosOld)) {
                vehiculosNew.getHojaPruebasCollection().add(hojaPruebas);
                vehiculosNew = em.merge(vehiculosNew);
            }
            if (propietariosOld != null && !propietariosOld.equals(propietariosNew)) {
                propietariosOld.getHojaPruebasCollection().remove(hojaPruebas);
                propietariosOld = em.merge(propietariosOld);
            }
            if (propietariosNew != null && !propietariosNew.equals(propietariosOld)) {
                propietariosNew.getHojaPruebasCollection().add(hojaPruebas);
                propietariosNew = em.merge(propietariosNew);
            }
            for (Pruebas pruebasCollectionNewPruebas : pruebasCollectionNew) {
                if (!pruebasCollectionOld.contains(pruebasCollectionNewPruebas)) {
                    HojaPruebas oldHojaPruebasOfPruebasCollectionNewPruebas = pruebasCollectionNewPruebas.getHojaPruebas();
                    pruebasCollectionNewPruebas.setHojaPruebas(hojaPruebas);
                    pruebasCollectionNewPruebas = em.merge(pruebasCollectionNewPruebas);
                    if (oldHojaPruebasOfPruebasCollectionNewPruebas != null && !oldHojaPruebasOfPruebasCollectionNewPruebas.equals(hojaPruebas)) {
                        oldHojaPruebasOfPruebasCollectionNewPruebas.getPruebasCollection().remove(pruebasCollectionNewPruebas);
                        oldHojaPruebasOfPruebasCollectionNewPruebas = em.merge(oldHojaPruebasOfPruebasCollectionNewPruebas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = hojaPruebas.getTestsheet();
                if (findHojaPruebas(id) == null) {
                    throw new NonexistentEntityException("The hojaPruebas with id " + id + " no longer exists.");
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
            HojaPruebas hojaPruebas;
            try {
                hojaPruebas = em.getReference(HojaPruebas.class, id);
                hojaPruebas.getTestsheet();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hojaPruebas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Pruebas> pruebasCollectionOrphanCheck = hojaPruebas.getPruebasCollection();
            for (Pruebas pruebasCollectionOrphanCheckPruebas : pruebasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This HojaPruebas (" + hojaPruebas + ") cannot be destroyed since the Pruebas " + pruebasCollectionOrphanCheckPruebas + " in its pruebasCollection field has a non-nullable hojaPruebas field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Vehiculos vehiculos = hojaPruebas.getVehiculos();
            if (vehiculos != null) {
                vehiculos.getHojaPruebasCollection().remove(hojaPruebas);
                vehiculos = em.merge(vehiculos);
            }
            Usuarios usuarios = hojaPruebas.getUsuarios();
            Propietarios propietarios = hojaPruebas.getPropietarios();
            if (propietarios != null) {
                propietarios.getHojaPruebasCollection().remove(hojaPruebas);
                propietarios = em.merge(propietarios);
            }
            em.remove(hojaPruebas);
            em.getTransaction().commit();
        } finally {
            
        }
    }

    public List<HojaPruebas> findHojaPruebasEntities() {
        return findHojaPruebasEntities(true, -1, -1);
    }

    public List<HojaPruebas> findHojaPruebasEntities(int maxResults, int firstResult) {
        return findHojaPruebasEntities(false, maxResults, firstResult);
    }

    private List<HojaPruebas> findHojaPruebasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HojaPruebas.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
          
        }
    }

    public HojaPruebas findHojaPruebas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HojaPruebas.class, id);
        } finally {
           
        }
    }

    public int getHojaPruebasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HojaPruebas> rt = cq.from(HojaPruebas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
           
        }
    }

    public List<HojaPruebas> BuscarCriterios(String consulta) {
        EntityManager em = getEntityManager();
        Query q = em.createNativeQuery(consulta, HojaPruebas.class);
        return q.getResultList();

    }

    public List<HojaPruebas> findHP_porFecha(Date com1, Date com2) {
        EntityManager em = getEntityManager();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String com = sdf.format(com1);

        java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String com12 = sdf2.format(com2);
        //String msql = "SELECT d  FROM Vehiculos d, Propietarios m WHERE d.propietarios = m  and m.carowner " + com + " " + mat1;
        String msql = "SELECT c FROM HojaPruebas AS c WHERE  c.fechaingresovehiculo >= '" + com + "' and c.fechaingresovehiculo <= '" + com12 + "'";
        Query query = em.createQuery(msql);
        return query.getResultList();
    }

    public List<HojaPruebas> findByDate(Date fechaInicial, Date fechaFinal) 
    {
        EntityManager em = getEntityManager();
        Query q = em. createNativeQuery("SELECT * FROM hoja_pruebas hp WHERE hp.preventiva='N' AND  DATE(hp.Fecha_ingreso_vehiculo) BETWEEN ? and ?", HojaPruebas.class);
        q.setParameter(1, fechaInicial);
        q.setParameter(2, fechaFinal);
        return q.getResultList();
    }
    public List<HojaPruebas> findByDateVigia(Date fechaInicial, Date fechaFinal) {
        EntityManager em = getEntityManager();
        Query q = em. createNativeQuery("SELECT * FROM hoja_pruebas hp WHERE hp.preventiva='N' AND  DATE(hp.Fecha_ingreso_vehiculo) BETWEEN ? and ?", HojaPruebas.class);
        q.setParameter(1, fechaInicial);
        q.setParameter(2, fechaFinal);
        return q.getResultList();
    }
    public List<Pruebas> findByVehiculo(Date fechaInicial, Date fechaFinal) {
        EntityManager em = getEntityManager();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaFinal);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        Query q = em.createQuery("SELECT p FROM Pruebas  p join fetch p.hojaPruebas   hp WHERE p.hojaPruebas.preventiva ='N' AND p.tipoPrueba.testtype=8 AND p.hojaPruebas.fechaingresovehiculo BETWEEN :fec1 and :fec2 ORDER BY p.hojaPruebas.fechaingresovehiculo  ", Pruebas.class);
        q.setParameter("fec1", fechaInicial);
        q.setParameter("fec2", cal.getTime());
        return q.getResultList();
    }
    
    public List<Pruebas> findByDatePruebas(Date fechaInicial, Date fechaFinal) {
        EntityManager em = getEntityManager();        
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaFinal);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        Query q = em.createQuery(
            "SELECT p FROM Pruebas p "+
            "join fetch p.hojaPruebas hp "+
            "WHERE p.hojaPruebas.preventiva ='N'"+
            "AND p.tipoPrueba.testtype=8 "+
            "AND p.fechaFinal BETWEEN :fec1 "+
            "AND :fec2 ORDER BY p.idPruebas", Pruebas.class);
        q.setParameter("fec1", fechaInicial);
        q.setParameter("fec2", cal.getTime());
        return q.getResultList();
    }
    

    
    
    public List<Pruebas> findByDatePruebasAll(Date fechaInicial, Date fechaFinal) {
        EntityManager em = getEntityManager();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaFinal);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        Query q = em.createQuery("SELECT p FROM Pruebas  p join fetch p.hojaPruebas   hp WHERE p.hojaPruebas.preventiva ='N' AND p.tipoPrueba.testtype in(2,4,5,6,8,9) AND p.abortada= 'N' AND  p.fechaFinal BETWEEN :fec1 and :fec2 ORDER BY p.fechaPrueba,p.hojaPruebas, p.tipoPrueba ASC", Pruebas.class);
        q.setParameter("fec1", fechaInicial);
        q.setParameter("fec2", cal.getTime());
        return q.getResultList();
    }
    
    public List<HojaPruebas> findByIdVehiculo(Integer Id){
         EntityManager em = getEntityManager();
         Query q = em.createQuery("SELECT h FROM HojaPruebas h WHERE h.vehiculos.car =:id ");
         
         q.setParameter("id",Id);
         return q.getResultList();
        
    }
    
    
        public List<Pruebas> BuscarDatosEquipos(Date fechaInicial, Date fechaFinal) {
        EntityManager em = getEntityManager();        
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaFinal);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        Query q = em.createQuery("SELECT p FROM Pruebas  p join fetch p.hojaPruebas   hp WHERE p.hojaPruebas.preventiva ='N' AND p.tipoPrueba.testtype=8 AND p.fechaFinal BETWEEN :fec1 and :fec2 ORDER BY p.idPruebas", Pruebas.class);// 
        q.setParameter("fec1", fechaInicial);
        q.setParameter("fec2", cal.getTime());
        return q.getResultList();
    }
}