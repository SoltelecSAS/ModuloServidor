/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.IllegalOrphanException;
import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.Llantas;
import com.soltelec.servidor.conexion.PersistenceController;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrador
 */
public class LlantasJpaController implements Serializable{
    

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public LlantasJpaController() {
    }

    public void create(Llantas llantas) throws PreexistingEntityException, Exception {
        
        EntityManager em = null;
        try {
            System.out.println("id que mando"+ llantas.getWheel());
            em = getEntityManager();
            if(em.getTransaction().isActive() ==false)
           {
            em.getTransaction().begin();   
           }                
            System.out.println("empiezo la transaccion, id de la llanta que envio: "+ llantas.getWheel());            
            em.persist(llantas);     
            em.getTransaction().commit();            
            System.out.println("llanta creada exitosamente");
        } catch (Exception ex) {
            if (findLlantas(llantas.getWheel()) != null) {
                throw new PreexistingEntityException("Llantas " + llantas + " already exists.", ex);
            }
            throw ex;
        } finally {           
        }
    }

    public void edit(Llantas llantas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
             if(em.getTransaction().isActive() ==false)
           {
            em.getTransaction().begin();   
           }  
            
            Llantas persistentLlantas = em.find(Llantas.class, llantas.getWheel());                      
            llantas = em.merge(llantas);            
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = llantas.getWheel();
                if (findLlantas(id) == null) {
                    throw new NonexistentEntityException("la llanta con " + id + " no existe");
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
             if(em.getTransaction().isActive() ==false)
           {
            em.getTransaction().begin();   
           }  
            
            Llantas llantas;
            try {
                llantas = em.getReference(Llantas.class, id);
                llantas.getWheel();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The llantas with id " + id + " no longer exists.", enfe);
            }
            em.remove(llantas);
            em.getTransaction().commit();
        } finally {
           
        }
    }

    public List<Llantas> findLlantasEntities() {
        return findLlantasEntities(true, -1, -1);
    }

    public List<Llantas> findLlantasEntities(int maxResults, int firstResult) {
        return findLlantasEntities(false, maxResults, firstResult);
    }

    private List<Llantas> findLlantasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Llantas.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
          
        }
    }

    public Llantas findLlantas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Llantas.class, id);
        } finally {
           
        }
    }

    public int getLlantasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Llantas> rt = cq.from(Llantas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
          
        }
    }
    
   

}
