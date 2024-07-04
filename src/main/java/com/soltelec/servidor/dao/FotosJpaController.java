/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.model.Fotos;
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
public class FotosJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Fotos fotos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(fotos);
            em.getTransaction().commit();
        } finally {            
        }
    }

    public void edit(Fotos fotos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            fotos = em.merge(fotos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fotos.getIdfotos();
                if (findFotos(id) == null) {
                    throw new NonexistentEntityException("The fotos with id " + id + " no longer exists.");
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
            Fotos fotos;
            try {
                fotos = em.getReference(Fotos.class, id);
                fotos.getIdfotos();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fotos with id " + id + " no longer exists.", enfe);
            }
            em.remove(fotos);
            em.getTransaction().commit();
        } finally {            
        }
    }

    public List<Fotos> findFotosEntities() {
        return findFotosEntities(true, -1, -1);
    }

    public List<Fotos> findFotosEntities(int maxResults, int firstResult) {
        return findFotosEntities(false, maxResults, firstResult);
    }

    private List<Fotos> findFotosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fotos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {          
        }
    }

    public Fotos findFotos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fotos.class, id);
        } finally {
          
        }
    }

    public int getFotosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fotos> rt = cq.from(Fotos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
          
        }
    }

}
