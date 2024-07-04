/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.IllegalOrphanException;
import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.Aseguradoras;
import com.soltelec.servidor.conexion.PersistenceController;
import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Administrador
 */
public class AseguradorasJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Aseguradoras aseguradoras) throws PreexistingEntityException, Exception {
        
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            em.persist(aseguradoras);
           
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAseguradoras(aseguradoras.getInsuring()) != null) {
                throw new PreexistingEntityException("Aseguradoras " + aseguradoras + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
             
            }
        }
    }

    public void edit(Aseguradoras aseguradoras) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();           
            aseguradoras = em.merge(aseguradoras);            
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = aseguradoras.getInsuring();
                if (findAseguradoras(id) == null) {
                    throw new NonexistentEntityException("The aseguradoras with id " + id + " no longer exists.");
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
            Aseguradoras aseguradoras;
            try {
                aseguradoras = em.getReference(Aseguradoras.class, id);
                aseguradoras.getInsuring();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aseguradoras with id " + id + " no longer exists.", enfe);
            }
            em.remove(aseguradoras);
            em.getTransaction().commit();
        } finally {           
        }
    }

    public List<Aseguradoras> findAseguradorasEntities() {
        return findAseguradorasEntities(true, -1, -1);
    }

    public List<Aseguradoras> findAseguradorasEntities(int maxResults, int firstResult) {
        return findAseguradorasEntities(false, maxResults, firstResult);
    }

    /* private List<Aseguradoras> findAseguradorasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Aseguradoras.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
          
        }
    } */

    //esta es una refactorizacion de la funcion comentada de arriba
    private List<Aseguradoras> findAseguradorasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Aseguradoras> cq = cb.createQuery(Aseguradoras.class);
        Root<Aseguradoras> rootEntry = cq.from(Aseguradoras.class);
        CriteriaQuery<Aseguradoras> allQuery = cq.select(rootEntry);

        if (!all) {
            TypedQuery<Aseguradoras> typedQuery = em.createQuery(allQuery);
            typedQuery.setMaxResults(maxResults);
            typedQuery.setFirstResult(firstResult);
            return typedQuery.getResultList();
        } else {
            return em.createQuery(allQuery).getResultList();
        }
    }

    public Aseguradoras findAseguradoras(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Aseguradoras.class, id);
        } finally {
           
        }
    }

    /* public int getAseguradorasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Aseguradoras> rt = cq.from(Aseguradoras.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {           
        }
    } */

    //esta es una refactorizacion de la funcion comentada de arriba
    public int getAseguradorasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Aseguradoras> rootEntry = cq.from(Aseguradoras.class);
            
            cq.select(cb.count(rootEntry));
            TypedQuery<Long> query = em.createQuery(cq);
            
            return query.getSingleResult().intValue();
        } finally {
            // No es necesario realizar ninguna operación de limpieza aquí
            // ya que el EntityManager se maneja automáticamente
            // y se cerrará cuando sea apropiado.
        }
    }

}
