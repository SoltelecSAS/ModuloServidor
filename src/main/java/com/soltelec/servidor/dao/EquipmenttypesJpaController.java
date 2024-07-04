/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.Equipmenttypes;
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
public class EquipmenttypesJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Equipmenttypes equipmenttypes) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(equipmenttypes);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEquipmenttypes(equipmenttypes.getEquipmentid()) != null) {
                throw new PreexistingEntityException("Equipmenttypes " + equipmenttypes + " already exists.", ex);
            }
            throw ex;
        } finally {           
        }
    }

    public void edit(Equipmenttypes equipmenttypes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            equipmenttypes = em.merge(equipmenttypes);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equipmenttypes.getEquipmentid();
                if (findEquipmenttypes(id) == null) {
                    throw new NonexistentEntityException("The equipmenttypes with id " + id + " no longer exists.");
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
            Equipmenttypes equipmenttypes;
            try {
                equipmenttypes = em.getReference(Equipmenttypes.class, id);
                equipmenttypes.getEquipmentid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipmenttypes with id " + id + " no longer exists.", enfe);
            }
            em.remove(equipmenttypes);
            em.getTransaction().commit();
        } finally {           
        }
    }

    public List<Equipmenttypes> findEquipmenttypesEntities() {
        return findEquipmenttypesEntities(true, -1, -1);
    }

    public List<Equipmenttypes> findEquipmenttypesEntities(int maxResults, int firstResult) {
        return findEquipmenttypesEntities(false, maxResults, firstResult);
    }

    private List<Equipmenttypes> findEquipmenttypesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipmenttypes.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {          
        }
    }

    public Equipmenttypes findEquipmenttypes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipmenttypes.class, id);
        } finally {           
        }
    }

    public int getEquipmenttypesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipmenttypes> rt = cq.from(Equipmenttypes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {         
        }
    }

}
