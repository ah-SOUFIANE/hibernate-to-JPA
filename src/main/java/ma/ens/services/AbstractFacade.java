/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.ens.services;

import java.util.List;
import ma.ens.dao.IDao;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import util.HibernateUtil;

public abstract class AbstractFacade<T> implements IDao<T> {

    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public boolean create(T o) {
        EntityManager em = null;
        EntityTransaction tx = null;
        boolean etat = false;

        try {
            em = HibernateUtil.getEntityManagerFactory().createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            em.persist(o);

            tx.commit();
            etat = true;
        } catch (PersistenceException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace(); // utile pour voir l'erreur
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return etat;
    }

    @Override
    public boolean delete(T o) {
        EntityManager em = null;
        EntityTransaction tx = null;
        boolean etat = false;

        try {
            em = HibernateUtil.getEntityManagerFactory().createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            T managed = em.contains(o) ? o : em.merge(o);
            em.remove(managed);

            tx.commit();
            etat = true;
        } catch (PersistenceException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return etat;
    }

    @Override
    public boolean update(T o) {
        EntityManager em = null;
        EntityTransaction tx = null;
        boolean etat = false;

        try {
            em = HibernateUtil.getEntityManagerFactory().createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            em.merge(o);

            tx.commit();
            etat = true;
        } catch (PersistenceException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return etat;
    }

    public T findById(long id) {
        EntityManager em = null;
        T obj = null;

        try {
            em = HibernateUtil.getEntityManagerFactory().createEntityManager();
            obj = em.find(entityClass, id);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return obj;
    }

    public List<T> findAll() {
        EntityManager em = null;
        List<T> list = null;

        try {
            em = HibernateUtil.getEntityManagerFactory().createEntityManager();
            list = em.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return list;
    }
}
