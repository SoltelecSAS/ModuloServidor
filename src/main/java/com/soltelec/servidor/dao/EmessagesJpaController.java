/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.Emessages;
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
public class EmessagesJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Emessages emessages) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(emessages);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmessages(emessages.getConstrainname()) != null) {
                throw new PreexistingEntityException("Emessages " + emessages + " already exists.", ex);
            }
            throw ex;
        } finally {          
        }
    }

    public void edit(Emessages emessages) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            emessages = em.merge(emessages);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = emessages.getConstrainname();
                if (findEmessages(id) == null) {
                    throw new NonexistentEntityException("The emessages with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {           
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Emessages emessages;
            try {
                emessages = em.getReference(Emessages.class, id);
                emessages.getConstrainname();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The emessages with id " + id + " no longer exists.", enfe);
            }
            em.remove(emessages);
            em.getTransaction().commit();
        } finally {            
        }
    }

    public List<Emessages> findEmessagesEntities() {
        return findEmessagesEntities(true, -1, -1);
    }

    public List<Emessages> findEmessagesEntities(int maxResults, int firstResult) {
        return findEmessagesEntities(false, maxResults, firstResult);
    }

    private List<Emessages> findEmessagesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Emessages.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {            
        }
    }

    public Emessages findEmessages(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Emessages.class, id);
        } finally {            
        }
    }

    public int getEmessagesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Emessages> rt = cq.from(Emessages.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {           
        }
    }

}
