/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.IllegalOrphanException;
import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.model.Colores;
import com.soltelec.servidor.conexion.PersistenceController;
import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Administrador
 */
public class ColoresJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Colores colores) {
       EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(colores);
            em.getTransaction().commit();
        } finally {
            
        }
    }

    public void edit(Colores colores) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            colores = em.merge(colores);            
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = colores.getColor();
                if (findColores(id) == null) {
                    throw new NonexistentEntityException("The colores with id " + id + " no longer exists.");
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
            Colores colores;
            try {
                colores = em.getReference(Colores.class, id);
                colores.getColor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The colores with id " + id + " no longer exists.", enfe);
            }
            em.remove(colores);
            em.getTransaction().commit();
        } finally {
            
        }
    }

    public List<Colores> findColoresEntities() {
        return findColoresEntities(true, -1, -1);
    }

    public List<Colores> findColoresEntities(int maxResults, int firstResult) {
        return findColoresEntities(false, maxResults, firstResult);
    }

    private List<Colores> findColoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Colores.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
           
        }
    }

    public Colores findColores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Colores.class, id);
        } finally {
          
        }
    }

    public int getColoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Colores> rt = cq.from(Colores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
           
        }
    }

}
