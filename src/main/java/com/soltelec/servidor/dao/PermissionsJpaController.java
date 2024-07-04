/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.Permissions;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.soltelec.servidor.model.Entities;
import com.soltelec.servidor.conexion.PersistenceController;

/**
 *
 * @author Administrador
 */
public class PermissionsJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Permissions permissions) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entities entities = permissions.getEntities();
            if (entities != null) {
                entities = em.getReference(entities.getClass(), entities.getEntity());
                permissions.setEntities(entities);
            }
            em.persist(permissions);
            if (entities != null) {
                entities.getPermissionsCollection().add(permissions);
                entities = em.merge(entities);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPermissions(permissions.getPermission()) != null) {
                throw new PreexistingEntityException("Permissions " + permissions + " already exists.", ex);
            }
            throw ex;
        } finally {
         }
    }

    public void edit(Permissions permissions) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Permissions persistentPermissions = em.find(Permissions.class, permissions.getPermission());
            Entities entitiesOld = persistentPermissions.getEntities();
            Entities entitiesNew = permissions.getEntities();
            if (entitiesNew != null) {
                entitiesNew = em.getReference(entitiesNew.getClass(), entitiesNew.getEntity());
                permissions.setEntities(entitiesNew);
            }
            permissions = em.merge(permissions);
            if (entitiesOld != null && !entitiesOld.equals(entitiesNew)) {
                entitiesOld.getPermissionsCollection().remove(permissions);
                entitiesOld = em.merge(entitiesOld);
            }
            if (entitiesNew != null && !entitiesNew.equals(entitiesOld)) {
                entitiesNew.getPermissionsCollection().add(permissions);
                entitiesNew = em.merge(entitiesNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = permissions.getPermission();
                if (findPermissions(id) == null) {
                    throw new NonexistentEntityException("The permissions with id " + id + " no longer exists.");
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
            Permissions permissions;
            try {
                permissions = em.getReference(Permissions.class, id);
                permissions.getPermission();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The permissions with id " + id + " no longer exists.", enfe);
            }
            Entities entities = permissions.getEntities();
            if (entities != null) {
                entities.getPermissionsCollection().remove(permissions);
                entities = em.merge(entities);
            }
            em.remove(permissions);
            em.getTransaction().commit();
        } finally {
           
        }
    }

    public List<Permissions> findPermissionsEntities() {
        return findPermissionsEntities(true, -1, -1);
    }

    public List<Permissions> findPermissionsEntities(int maxResults, int firstResult) {
        return findPermissionsEntities(false, maxResults, firstResult);
    }

    private List<Permissions> findPermissionsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Permissions.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
           
        }
    }

    public Permissions findPermissions(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Permissions.class, id);
        } finally {
            
        }
    }

    public int getPermissionsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Permissions> rt = cq.from(Permissions.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
           
        }
    }

}
