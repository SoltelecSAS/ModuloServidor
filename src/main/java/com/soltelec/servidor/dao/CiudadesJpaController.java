/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.dao;

import com.soltelec.servidor.conexion.PersistenceController;
import com.soltelec.servidor.conexion.exceptions.IllegalOrphanException;
import com.soltelec.servidor.conexion.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.soltelec.servidor.model.Departamentos;
import com.soltelec.servidor.model.Cda;
import com.soltelec.servidor.model.Ciudades;
import com.soltelec.servidor.model.Usuarios;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author GerenciaDesarrollo
 */
public class CiudadesJpaController implements Serializable {

    /*public CiudadesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }*/
    private EntityManagerFactory emf = null;

    /*public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }*/
    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Ciudades ciudades) {
       
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamentos state = ciudades.getState();
            if (state != null) {
                state = em.getReference(state.getClass(), state.getIdDepartamento());
                ciudades.setState(state);
            }
            Collection<Cda> attachedCdaCollection = new ArrayList<Cda>();
           
            em.persist(ciudades);
            if (state != null) {
                state.getCiudadesCollection().add(ciudades);
                state = em.merge(state);
            }
           
            em.getTransaction().commit();
        } finally {
           
        }
    }

    public void edit(Ciudades ciudades) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudades persistentCiudades = em.find(Ciudades.class, ciudades.getCity());
            Departamentos stateOld = persistentCiudades.getState();
            Departamentos stateNew = ciudades.getState();
          
            List<String> illegalOrphanMessages = null;
            
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (stateNew != null) {
                stateNew = em.getReference(stateNew.getClass(), stateNew.getIdDepartamento());
                ciudades.setState(stateNew);
            }
            Collection<Cda> attachedCdaCollectionNew = new ArrayList<Cda>();
            
            ciudades = em.merge(ciudades);
            if (stateOld != null && !stateOld.equals(stateNew)) {
                stateOld.getCiudadesCollection().remove(ciudades);
                stateOld = em.merge(stateOld);
            }
            if (stateNew != null && !stateNew.equals(stateOld)) {
                stateNew.getCiudadesCollection().add(ciudades);
                stateNew = em.merge(stateNew);
            }
            
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ciudades.getCity();
                if (findCiudades(id) == null) {
                    throw new NonexistentEntityException("The ciudades with id " + id + " no longer exists.");
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
            Ciudades ciudades;
            try {
                ciudades = em.getReference(Ciudades.class, id);
                ciudades.getCity();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ciudades with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
         
           
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamentos state = ciudades.getState();
            if (state != null) {
                state.getCiudadesCollection().remove(ciudades);
                state = em.merge(state);
            }
            em.remove(ciudades);
            em.getTransaction().commit();
        } finally {
            
        }
    }

    public List<Ciudades> findCiudadesEntities() {
        return findCiudadesEntities(true, -1, -1);
    }

    public List<Ciudades> findCiudadesEntities(int maxResults, int firstResult) {
        return findCiudadesEntities(false, maxResults, firstResult);
    }

    private List<Ciudades> findCiudadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ciudades.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public Ciudades findCiudades(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ciudades.class, id);
        } finally {
           
        }
    }

    public int getCiudadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ciudades> rt = cq.from(Ciudades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            
        }
    }

    public List<Ciudades> listaCiudades() {
        EntityManager em = getEntityManager();
        try {
            String msql = "SELECT c FROM Ciudades AS c ORDER BY c.ciudadprincipal";
            Query query = em.createQuery(msql);
            return query.getResultList();
        } finally {
         
        }
    }

    public List<Ciudades> listaCiudadesObj() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ciudades> root = cq.from(Ciudades.class);
            TypedQuery<Ciudades> consulta = em.createQuery(cq);
            return consulta.getResultList();
        } finally {
           
        }
    }

}
