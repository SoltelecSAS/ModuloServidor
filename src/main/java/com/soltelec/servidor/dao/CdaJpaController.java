/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.dao;

import com.soltelec.servidor.conexion.PersistenceController;
import com.soltelec.servidor.conexion.exceptions.NonexistentEntityException;
import com.soltelec.servidor.model.Cda;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.soltelec.servidor.model.Ciudades;
import com.soltelec.servidor.model.Usuarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author GerenciaDesarrollo
 */
public class CdaJpaController implements Serializable {

    /*public CdaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }*/
    private EntityManagerFactory emf = null;

    /*public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }*/
    
    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Cda cda) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();           
            em.persist(cda);           
            em.getTransaction().commit();
        } finally {
           
        }
    }

    public void edit(Cda cda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.clear();
            if(em.getTransaction().isActive()==false){
                 em.getTransaction().begin();
            }
           
            Cda persistentCda = em.find(Cda.class, cda.getIdCda());           
            cda = em.merge(cda);            
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cda.getIdCda();
                if (findCda(id) == null) {
                    throw new NonexistentEntityException("The cda with id " + id + " no longer exists.");
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
            Cda cda;
            try {
                cda = em.getReference(Cda.class, id);
                cda.getIdCda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cda with id " + id + " no longer exists.", enfe);
            }
           
            em.remove(cda);
            em.getTransaction().commit();
        } finally {
            
        }
    }

    public List<Cda> findCdaEntities() {
        return findCdaEntities(true, -1, -1);
    }

    public List<Cda> findCdaEntities(int maxResults, int firstResult) {
        return findCdaEntities(false, maxResults, firstResult);
    }

    private List<Cda> findCdaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cda.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
           
        }
    }

    public Cda findCda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cda.class, id);
        } finally {
          
        }
    }

    public int getCdaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cda> rt = cq.from(Cda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
           
        }
    }

    public List<Usuarios> listaUsuariosAdministradores() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> root = cq.from(Usuarios.class);
            //Predicate equal = em.getCriteriaBuilder().equal(root.get(Usuarios.esAdministrador), "Y");
            //cq.where(equal);
            TypedQuery<Usuarios> consulta = em.createQuery(cq);
            return consulta.getResultList();

        } finally {
          
        }
    }    

    public Usuarios findUsuarioResponsable(int idUsuario) {
        EntityManager em = getEntityManager();
        try {
            Usuarios find = em.find(Usuarios.class, idUsuario);
            return find;
        } finally {
          
        }
    }
    
       public Object[] findNombre () {
        EntityManager em = getEntityManager();
        String sql = "SELECT * FROM usuarios u INNER JOIN cda c ON c.nom_resp_certificados = u.Nombre_usuario; ";

        Query query = em.createNativeQuery(sql);
        //query.setParameter(1, nombre);

        return (Object[]) query.getSingleResult();
    }
       public Object[] findmedellin () {
        EntityManager em = getEntityManager();
        String sql = "SELECT c.Laboratorio_Palta,c.Certificado_Palta,c.Laboratorio_Pbaja,c.Certificado_Pbaja FROM cda c;";

        Query query = em.createNativeQuery(sql);

        return (Object[]) query.getSingleResult();
    }

}
