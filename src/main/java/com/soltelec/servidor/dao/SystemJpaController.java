/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.Systema;
import com.soltelec.servidor.conexion.PersistenceController;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Administrador
 */
public class SystemJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Systema system) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(system);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSystem(system.getId()) != null) {
                throw new PreexistingEntityException("System " + system + " already exists.", ex);
            }
            throw ex;
        } finally {
           
        }
    }

    public void edit(Systema system) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            system = em.merge(system);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = system.getId();
                if (findSystem(id) == null) {
                    throw new NonexistentEntityException("The system with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Systema system;
            try {
                system = em.getReference(Systema.class, id);
                system.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The system with id " + id + " no longer exists.", enfe);
            }
            em.remove(system);
            em.getTransaction().commit();
        } finally {
           
        }
    }

    public List<Systema> findSystemEntities() {
        return findSystemEntities(true, -1, -1);
    }

    public List<Systema> findSystemEntities(int maxResults, int firstResult) {
        return findSystemEntities(false, maxResults, firstResult);
    }

    private List<Systema> findSystemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Systema.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
           
        }
    }

    public Systema findSystem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Systema.class, id);
        } finally {
           
        }
    }

    public int getSystemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Systema> rt = cq.from(Systema.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
           
        }
    }

}
