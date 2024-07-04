/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.IllegalOrphanException;
import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.Marcas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import com.soltelec.servidor.model.LineasVehiculos;
import com.soltelec.servidor.conexion.PersistenceController;

/**
 *
 * @author Administrador
 */
public class MarcasJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Marcas marcas) throws PreexistingEntityException, Exception {
        if (marcas.getLineasVehiculosCollection() == null) {
            marcas.setLineasVehiculosCollection(new ArrayList<LineasVehiculos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<LineasVehiculos> attachedLineasVehiculosCollection = new ArrayList<LineasVehiculos>();
            for (LineasVehiculos lineasVehiculosCollectionLineasVehiculosToAttach : marcas.getLineasVehiculosCollection()) {
                lineasVehiculosCollectionLineasVehiculosToAttach = em.getReference(lineasVehiculosCollectionLineasVehiculosToAttach.getClass(), lineasVehiculosCollectionLineasVehiculosToAttach.getCarline());
                attachedLineasVehiculosCollection.add(lineasVehiculosCollectionLineasVehiculosToAttach);
            }
            marcas.setLineasVehiculosCollection(attachedLineasVehiculosCollection);
            em.persist(marcas);
            for (LineasVehiculos lineasVehiculosCollectionLineasVehiculos : marcas.getLineasVehiculosCollection()) {
                Marcas oldMarcasOfLineasVehiculosCollectionLineasVehiculos = lineasVehiculosCollectionLineasVehiculos.getMarcas();
                lineasVehiculosCollectionLineasVehiculos.setMarcas(marcas);
                lineasVehiculosCollectionLineasVehiculos = em.merge(lineasVehiculosCollectionLineasVehiculos);
                if (oldMarcasOfLineasVehiculosCollectionLineasVehiculos != null) {
                    oldMarcasOfLineasVehiculosCollectionLineasVehiculos.getLineasVehiculosCollection().remove(lineasVehiculosCollectionLineasVehiculos);
                    oldMarcasOfLineasVehiculosCollectionLineasVehiculos = em.merge(oldMarcasOfLineasVehiculosCollectionLineasVehiculos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMarcas(marcas.getCarmark()) != null) {
                throw new PreexistingEntityException("Marcas " + marcas + " already exists.", ex);
            }
            throw ex;
        } finally {            
        }
    }

    public void edit(Marcas marcas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marcas persistentMarcas = em.find(Marcas.class, marcas.getCarmark());
            Collection<LineasVehiculos> lineasVehiculosCollectionOld = persistentMarcas.getLineasVehiculosCollection();
            Collection<LineasVehiculos> lineasVehiculosCollectionNew = marcas.getLineasVehiculosCollection();
            List<String> illegalOrphanMessages = null;
            for (LineasVehiculos lineasVehiculosCollectionOldLineasVehiculos : lineasVehiculosCollectionOld) {
                if (!lineasVehiculosCollectionNew.contains(lineasVehiculosCollectionOldLineasVehiculos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain LineasVehiculos " + lineasVehiculosCollectionOldLineasVehiculos + " since its marcas field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<LineasVehiculos> attachedLineasVehiculosCollectionNew = new ArrayList<LineasVehiculos>();
            for (LineasVehiculos lineasVehiculosCollectionNewLineasVehiculosToAttach : lineasVehiculosCollectionNew) {
                lineasVehiculosCollectionNewLineasVehiculosToAttach = em.getReference(lineasVehiculosCollectionNewLineasVehiculosToAttach.getClass(), lineasVehiculosCollectionNewLineasVehiculosToAttach.getCarline());
                attachedLineasVehiculosCollectionNew.add(lineasVehiculosCollectionNewLineasVehiculosToAttach);
            }
            lineasVehiculosCollectionNew = attachedLineasVehiculosCollectionNew;
            marcas.setLineasVehiculosCollection(lineasVehiculosCollectionNew);
            marcas = em.merge(marcas);
            for (LineasVehiculos lineasVehiculosCollectionNewLineasVehiculos : lineasVehiculosCollectionNew) {
                if (!lineasVehiculosCollectionOld.contains(lineasVehiculosCollectionNewLineasVehiculos)) {
                    Marcas oldMarcasOfLineasVehiculosCollectionNewLineasVehiculos = lineasVehiculosCollectionNewLineasVehiculos.getMarcas();
                    lineasVehiculosCollectionNewLineasVehiculos.setMarcas(marcas);
                    lineasVehiculosCollectionNewLineasVehiculos = em.merge(lineasVehiculosCollectionNewLineasVehiculos);
                    if (oldMarcasOfLineasVehiculosCollectionNewLineasVehiculos != null && !oldMarcasOfLineasVehiculosCollectionNewLineasVehiculos.equals(marcas)) {
                        oldMarcasOfLineasVehiculosCollectionNewLineasVehiculos.getLineasVehiculosCollection().remove(lineasVehiculosCollectionNewLineasVehiculos);
                        oldMarcasOfLineasVehiculosCollectionNewLineasVehiculos = em.merge(oldMarcasOfLineasVehiculosCollectionNewLineasVehiculos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = marcas.getCarmark();
                if (findMarcas(id) == null) {
                    throw new NonexistentEntityException("The marcas with id " + id + " no longer exists.");
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
            Marcas marcas;
            try {
                marcas = em.getReference(Marcas.class, id);
                marcas.getCarmark();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The marcas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<LineasVehiculos> lineasVehiculosCollectionOrphanCheck = marcas.getLineasVehiculosCollection();
            for (LineasVehiculos lineasVehiculosCollectionOrphanCheckLineasVehiculos : lineasVehiculosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Marcas (" + marcas + ") cannot be destroyed since the LineasVehiculos " + lineasVehiculosCollectionOrphanCheckLineasVehiculos + " in its lineasVehiculosCollection field has a non-nullable marcas field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(marcas);
            em.getTransaction().commit();
        } finally {
           
        }
    }

    public List<Marcas> findMarcasEntities() {
        return findMarcasEntities(true, -1, -1);
    }

    public List<Marcas> findMarcasEntities(int maxResults, int firstResult) {
        return findMarcasEntities(false, maxResults, firstResult);
    }

    private List<Marcas> findMarcasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Marcas.class));

            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
                  }
    }

    public Marcas findMarcas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Marcas.class, id);
        } finally {
           
        }
    }

    public int getMarcasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Marcas> rt = cq.from(Marcas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
           
        }
    }

    public int ultimoIdMarca() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery("select max(CARMARK) from marcas");
            return Integer.parseInt(q.getSingleResult().toString());
        } catch (Exception e) {
            System.out.println("Error " + e);
            return 0;
        }
    }

    public List<Marcas> listarMarcas(String marca) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT object(m) FROM Marcas m WHERE m.nombremarca like :marca");
            q.setParameter("marca", "%" + marca + "%");
            return (List<Marcas>) q.getResultList();
        } catch (Exception e) {
            System.out.println("Error " + e);
            return null;
        }
    }

    public List<Marcas> findAll() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Marcas.findAll");
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error " + e);
            return null;
        }
    }
}
