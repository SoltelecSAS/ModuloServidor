/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.IllegalOrphanException;
import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.Permisibles;
import com.soltelec.servidor.model.TipoPrueba;
import com.soltelec.servidor.conexion.PersistenceController;
import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Administrador
 */
public class TipoPruebaJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(TipoPrueba tipoPrueba) throws PreexistingEntityException, Exception {
       
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoPrueba);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoPrueba(tipoPrueba.getTesttype()) != null) {
                throw new PreexistingEntityException("TipoPrueba " + tipoPrueba + " already exists.", ex);
            }
            throw ex;
        } finally {
            
        }
    }

    public void edit(TipoPrueba tipoPrueba) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoPrueba persistentTipoPrueba = em.find(TipoPrueba.class, tipoPrueba.getTesttype());
            tipoPrueba = em.merge(tipoPrueba);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoPrueba.getTesttype();
                if (findTipoPrueba(id) == null) {
                    throw new NonexistentEntityException("The tipoPrueba with id " + id + " no longer exists.");
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
            TipoPrueba tipoPrueba;
            try {
                tipoPrueba = em.getReference(TipoPrueba.class, id);
                tipoPrueba.getTesttype();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoPrueba with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoPrueba);
            em.getTransaction().commit();
        } finally {
            
        }
    }

    public List<TipoPrueba> findTipoPruebaEntities() {
        return findTipoPruebaEntities(true, -1, -1);
    }

    public List<TipoPrueba> findTipoPruebaEntities(int maxResults, int firstResult) {
        return findTipoPruebaEntities(false, maxResults, firstResult);
    }

    private List<TipoPrueba> findTipoPruebaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoPrueba.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
           
        }
    }

    public TipoPrueba findTipoPrueba(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoPrueba.class, id);
        } finally {
          
        }
    }

    public int getTipoPruebaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoPrueba> rt = cq.from(TipoPrueba.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
          
        }
    }


    public List<TipoPrueba> findTipoPrueba1() {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT c FROM TipoPrueba AS c");
        return query.getResultList();
    }

    public List<Object[]> findPrueba(int hprueba,int tprueba) {

        EntityManager em = getEntityManager();
        String msql = "SELECT p.idPruebas, h.testsheet, c.testtype, p.pista,u.nickusuario, p.autorizada, p.aprobada,p.abortada, p.fechaPrueba  FROM HojaPruebas h, Pruebas p, TipoPrueba AS c , Usuarios u WHERE  p.usuarios = u and p.tipoPrueba = c and p.hojaPruebas = h and h.testsheet ="+hprueba+" and c.testtype = "+tprueba+" ";
        Query query = em.createQuery(msql);
        System.out.println(""+msql);
        //query.setParameter("keyword", "'" + keyword + "'");

        return query.getResultList();
    }

    public List<Object[]> findPruebaVehiculo(int id,int tprueba) {
        EntityManager em = getEntityManager();
//        String msql = "SELECT p.idPruebas, h.testsheet, c.testtype, p.pista ,u.nickusuario, p.autorizada, p.aprobada, p.abortada, p.fechaprueba  FROM HojaPruebas h, Pruebas p, TipoPrueba AS c , Usuarios u, Vehiculos v WHERE  p.usuarios = u and  h.vehiculos = v  and p.tipoPrueba = c and p.hojaPruebas = h and v.car = "+id+" and c.testtype = "+tprueba+" ";
//        System.out.println(""+msql);
        Query query = em.createQuery("SELECT p.idPruebas, h.testsheet, c.testtype, p.pista ,u.nickusuario, p.autorizada, p.aprobada, p.abortada, p.fechaPrueba  FROM HojaPruebas h, Pruebas p, TipoPrueba AS c , Usuarios u, Vehiculos v WHERE  p.usuarios = u and  h.vehiculos = v  and p.tipoPrueba = c and p.hojaPruebas = h and v.car = "+id+" and c.testtype = "+tprueba+" ");
        //query.setParameter("keyword", "'" + keyword + "'");
        return query.getResultList();
    }

    public List<Object[]> findMedida(int prueba, int tp) {
        EntityManager em = getEntityManager();

        String msql = "SELECT m.measure, c.measuretype, c.nombremedida,c.descripcionmedida, m.valormedida,c.unidad FROM Pruebas p, Medidas m, TiposMedida AS c WHERE  m.pruebas = p and m.tiposMedida = c and p.idPruebas ="+prueba+" and c.testtype ="+tp+" ";
        Query query = em.createQuery(msql);
        //query.setParameter("keyword", "'" + keyword + "'");

        return query.getResultList();
    }


    public List<Object[]> findMedida2(int prueba) {
        EntityManager em = getEntityManager();

        String msql = "SELECT c.measuretype, c.nombremedida,c.descripcionmedida, c.unidad FROM TiposMedida AS c WHERE c.testtype ="+prueba+" order by c.measuretype";
        Query query = em.createQuery(msql);
        //query.setParameter("keyword", "'" + keyword + "'");

        return query.getResultList();
    }


    public List<Object[]> findDefectos(int prueba) {
        EntityManager em = getEntityManager();

        String msql = "SELECT p.idPruebas, d2.cardefault, d2.nombreproblema, d2.tipodefecto FROM Defectos d2, Pruebas p, Defxprueba d WHERE  d.pruebas = p and d.defectos = d2 and p.idPruebas ="+prueba+" ";
        Query query = em.createQuery(msql);
        //query.setParameter("keyword", "'" + keyword + "'");

        return query.getResultList();
    }



    public List<Object[]> findVehiculos_porCedula(String mat, String campo, String com) {
        EntityManager em = getEntityManager();
        //String msql = "SELECT i FROM Vehiculos i WHERE UPPER(i.carplate) = :keyword";
       // String msql = "SELECT i FROM Vehiculos i WHERE i." + campo + " " + com + " '" + mat + "'";
        String msql = "SELECT c.carplate, m.nombremarca, c.modelo, c.cinlindraje,c.numerolicencia, c.numeroSOAT FROM Vehiculos AS c,  Marcas m WHERE c." + campo + " " + com + " '" + mat + "' AND c.marcas = m";
        String keyword = mat;
        Query query = em.createQuery(msql);
        //query.setParameter("keyword", "'" + keyword + "'");

        return query.getResultList();
    }


    public List<Object[]> findPermisibles(int prueba) {
        EntityManager em = getEntityManager();

        String msql = "SELECT p.idpermisible ,t.measuretype,p.valorminimo, p.valormaximo, p.condicionalminimo, p.condicionalmaximo, p.descripciooncondicion, v.nombre FROM Permisibles p, TiposMedida t, TipoVehiculo v WHERE  p.tiposMedida = t and t.measuretype = "+prueba+" and p.tipoVehiculo = v ";
        System.out.println("msql: " + msql);
        Query query = em.createQuery(msql);

        return query.getResultList();
    }

    public List<Object[]> findPermisibles2(int prueba) {
        EntityManager em = getEntityManager();

        String msql = "SELECT p.idpermisible , t.measuretype,p.valorminimo, p.valormaximo, p.condicionalminimo, p.condicionalmaximo, p.descripciooncondicion, v.nombre FROM Permisibles p, TiposMedida t, TipoVehiculo v WHERE  p.tiposMedida = t and t.testtype = "+prueba+" and p.tipoVehiculo = v ";
        Query query = em.createQuery(msql);
        //query.setParameter("keyword", "'" + keyword + "'");

        return query.getResultList();
    }


    public Permisibles findPermisibles3(int prueba) {
        EntityManager em = getEntityManager();

        String msql = "SELECT p  FROM Permisibles p, TiposMedida t, TipoVehiculo v WHERE  p.tiposMedida = t and p.idpermisible = "+prueba+" and p.tipoVehiculo = v ";
        Query query = em.createQuery(msql);
        //query.setParameter("keyword", "'" + keyword + "'");

        return (Permisibles)query.getSingleResult();
    }

}
