/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.IllegalOrphanException;
import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.Permisibles;
import com.soltelec.servidor.model.TiposMedida;
import com.soltelec.servidor.conexion.PersistenceController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Administrador
 */
public class TiposMedidaJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(TiposMedida tiposMedida) throws PreexistingEntityException, Exception {
        if (tiposMedida.getPermisiblesCollection() == null) {
            tiposMedida.setPermisiblesCollection(new ArrayList<Permisibles>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Permisibles> attachedPermisiblesCollection = new ArrayList<>();
            for (Permisibles permisiblesCollectionPermisiblesToAttach : tiposMedida.getPermisiblesCollection()) {
                permisiblesCollectionPermisiblesToAttach = em.getReference(permisiblesCollectionPermisiblesToAttach.getClass(), permisiblesCollectionPermisiblesToAttach.getIdpermisible());
                attachedPermisiblesCollection.add(permisiblesCollectionPermisiblesToAttach);
            }
            tiposMedida.setPermisiblesCollection(attachedPermisiblesCollection);
            em.persist(tiposMedida);
            for (Permisibles permisiblesCollectionPermisibles : tiposMedida.getPermisiblesCollection()) {
                TiposMedida oldTiposMedidaOfPermisiblesCollectionPermisibles = permisiblesCollectionPermisibles.getTiposMedida();
                permisiblesCollectionPermisibles.setTiposMedida(tiposMedida);
                permisiblesCollectionPermisibles = em.merge(permisiblesCollectionPermisibles);
                if (oldTiposMedidaOfPermisiblesCollectionPermisibles != null) {
                    oldTiposMedidaOfPermisiblesCollectionPermisibles.getPermisiblesCollection().remove(permisiblesCollectionPermisibles);
                    oldTiposMedidaOfPermisiblesCollectionPermisibles = em.merge(oldTiposMedidaOfPermisiblesCollectionPermisibles);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTiposMedida(tiposMedida.getMeasuretype()) != null) {
                throw new PreexistingEntityException("TiposMedida " + tiposMedida + " already exists.", ex);
            }
            throw ex;
        } finally {
            
        }
    }

    public void edit(TiposMedida tiposMedida) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tiposMedida = em.merge(tiposMedida);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tiposMedida.getMeasuretype();
                if (findTiposMedida(id) == null) {
                    throw new NonexistentEntityException("The tiposMedida with id " + id + " no longer exists.");
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
            TiposMedida tiposMedida;
            try {
                tiposMedida = em.getReference(TiposMedida.class, id);
                tiposMedida.getMeasuretype();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tiposMedida with id " + id + " no longer exists.", enfe);
            }
            em.remove(tiposMedida);
            em.getTransaction().commit();
        } finally {
            
        }
    }

    public List<TiposMedida> findTiposMedidaEntities() {
        return findTiposMedidaEntities(true, -1, -1);
    }

    public List<TiposMedida> findTiposMedidaEntities(int maxResults, int firstResult) {
        return findTiposMedidaEntities(false, maxResults, firstResult);
    }

    private List<TiposMedida> findTiposMedidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TiposMedida.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
           
        }
    }

    public TiposMedida findTiposMedida(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TiposMedida.class, id);
        } finally {
          
        }
    }

    public int getTiposMedidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TiposMedida> rt = cq.from(TiposMedida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
           
        }
    }


    public List<TiposMedida> findTMedida1() {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT c FROM TiposMedida AS c");
        return query.getResultList();
    }

    public List<TiposMedida> findListTiposMedida(int idPrueba) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT t FROM TiposMedida t WHERE t.testtype = " + idPrueba);
        return query.getResultList();
    }
}
