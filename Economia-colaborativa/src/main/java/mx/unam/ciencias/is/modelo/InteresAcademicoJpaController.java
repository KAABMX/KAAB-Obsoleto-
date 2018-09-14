/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.ciencias.is.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import mx.unam.ciencias.is.controlador.exceptions.NonexistentEntityException;
import mx.unam.ciencias.is.controlador.exceptions.RollbackFailureException;
import mx.unam.ciencias.is.mapeobd.InteresAcademico;

/**
 *
 * @author luis
 */
public class InteresAcademicoJpaController implements Serializable {

    public InteresAcademicoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InteresAcademico interesAcademico) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(interesAcademico);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InteresAcademico interesAcademico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            interesAcademico = em.merge(interesAcademico);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = interesAcademico.getPkIdInteres();
                if (findInteresAcademico(id) == null) {
                    throw new NonexistentEntityException("The interesAcademico with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            InteresAcademico interesAcademico;
            try {
                interesAcademico = em.getReference(InteresAcademico.class, id);
                interesAcademico.getPkIdInteres();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The interesAcademico with id " + id + " no longer exists.", enfe);
            }
            em.remove(interesAcademico);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InteresAcademico> findInteresAcademicoEntities() {
        return findInteresAcademicoEntities(true, -1, -1);
    }

    public List<InteresAcademico> findInteresAcademicoEntities(int maxResults, int firstResult) {
        return findInteresAcademicoEntities(false, maxResults, firstResult);
    }

    private List<InteresAcademico> findInteresAcademicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InteresAcademico.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public InteresAcademico findInteresAcademico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InteresAcademico.class, id);
        } finally {
            em.close();
        }
    }

    public int getInteresAcademicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InteresAcademico> rt = cq.from(InteresAcademico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
