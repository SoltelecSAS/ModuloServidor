/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.IllegalOrphanException;
import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.Usuarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.soltelec.servidor.model.Vehiculos;
import com.soltelec.servidor.conexion.PersistenceController;
import java.io.Serializable;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Administrador
 */
public class UsuariosJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }
    
    public void create(Usuarios usuarios) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {            
            System.out.println("id que mando en el create"+ usuarios.getGeuser());
            em = getEntityManager();         
            if(em.getTransaction().isActive() ==false)
           {
            em.getTransaction().begin();   
           }  
            System.out.println("empiezo la transaccion");
            System.out.println("id del usuario1 "+usuarios.getGeuser());
            em.persist(usuarios);            
            em.getTransaction().commit();
            System.out.println("id del usuario2 "+usuarios.getGeuser());
        } catch (Exception ex) {
            if (findUsuarios(usuarios.getGeuser()) != null) {
                System.out.println("excepcion generada "+ex);
                throw new PreexistingEntityException("Usuario " + usuarios + " ya existe, intente con un nombre de usuario diferente.", ex);
            }
            throw ex;
         } finally {
            /*if(em !=null){
                System.out.println("cierro el entity create");
                em.close();           
            }
           */
        }
    }

    public void edit(Usuarios usuarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.clear();
           if(em.getTransaction().isActive() ==false)
           {
            em.getTransaction().begin();   
           }
            usuarios = em.merge(usuarios);
            
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getGeuser();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if(em != null){
                System.out.println("cierro el entity en edit");
               // em.close();
            }
            
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
           if(em.getTransaction().isActive() ==false)
           {
            em.getTransaction().begin();   
           }
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getGeuser();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }            
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
          
                
           
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
           // em.close();
           
        }
    }

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
         // em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
           
        }
    }

    public List<Usuarios> findUsuarios1() {
        EntityManager em = getEntityManager();
        //Query query = em.createQuery("SELECT c FROM Vehiculos AS c,  Marcas m WHERE c.marcas = m");
        Query query = em.createQuery("SELECT c FROM Usuarios AS c");
        
        return query.getResultList();
    }

    public List<Usuarios> buscarUsuarios(String nombre, String nick) {
        EntityManager em = getEntityManager();
        Query q = em.createQuery("SELECT i FROM Usuarios i WHERE i.nombreusuario like :nombre OR i.nickusuario like :nick");
        q.setParameter("nombre", "%" + nombre + "%");
        q.setParameter("nick", "%" + nick + "%");
        return q.getResultList();
    }

    public boolean findUsuariosByNick(String nick) {
        EntityManager em = getEntityManager();
        Query q = em.createQuery("SELECT u FROM Usuarios u WHERE u.nickusuario LIKE '" + nick + "'");
        try {
            q.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public Usuarios findUsuarioByCedula(String cedula) {
        EntityManager em = getEntityManager();
        TypedQuery<Usuarios> q = em.createQuery("SELECT u FROM Usuarios u WHERE u.cedula LIKE '" + cedula + "'", Usuarios.class);
        try {
            return q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<Vehiculos> findUsuarioByTipo(String mat, String campo, String com) {
        EntityManager em = getEntityManager();
        int mat1 = Integer.parseInt(mat);
        String msql = "SELECT d  FROM Vehiculos d, Propietarios m WHERE d.propietarios = m  and m.carowner " + com + " " + mat1;

        Query query = em.createQuery(msql);

        return query.getResultList();
    }
}
