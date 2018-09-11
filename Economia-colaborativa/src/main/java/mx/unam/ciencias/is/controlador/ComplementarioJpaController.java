/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.ciencias.is.controlador;

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
import mx.unam.ciencias.is.entidad.Complementario;
import mx.unam.ciencias.is.entidad.Curriculum;

/**
 *
 * @author luis
 */
public class ComplementarioJpaController implements Serializable {

    public ComplementarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Complementario complementario) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Curriculum fkIdCv = complementario.getFkIdCv();
            if (fkIdCv != null) {
                fkIdCv = em.getReference(fkIdCv.getClass(), fkIdCv.getPkIdCv());
                complementario.setFkIdCv(fkIdCv);
            }
            em.persist(complementario);
            if (fkIdCv != null) {
                fkIdCv.getComplementarioList().add(complementario);
                fkIdCv = em.merge(fkIdCv);
            }
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

    public void edit(Complementario complementario) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Complementario persistentComplementario = em.find(Complementario.class, complementario.getPkIdComplementarios());
            Curriculum fkIdCvOld = persistentComplementario.getFkIdCv();
            Curriculum fkIdCvNew = complementario.getFkIdCv();
            if (fkIdCvNew != null) {
                fkIdCvNew = em.getReference(fkIdCvNew.getClass(), fkIdCvNew.getPkIdCv());
                complementario.setFkIdCv(fkIdCvNew);
            }
            complementario = em.merge(complementario);
            if (fkIdCvOld != null && !fkIdCvOld.equals(fkIdCvNew)) {
                fkIdCvOld.getComplementarioList().remove(complementario);
                fkIdCvOld = em.merge(fkIdCvOld);
            }
            if (fkIdCvNew != null && !fkIdCvNew.equals(fkIdCvOld)) {
                fkIdCvNew.getComplementarioList().add(complementario);
                fkIdCvNew = em.merge(fkIdCvNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = complementario.getPkIdComplementarios();
                if (findComplementario(id) == null) {
                    throw new NonexistentEntityException("The complementario with id " + id + " no longer exists.");
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
            Complementario complementario;
            try {
                complementario = em.getReference(Complementario.class, id);
                complementario.getPkIdComplementarios();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The complementario with id " + id + " no longer exists.", enfe);
            }
            Curriculum fkIdCv = complementario.getFkIdCv();
            if (fkIdCv != null) {
                fkIdCv.getComplementarioList().remove(complementario);
                fkIdCv = em.merge(fkIdCv);
            }
            em.remove(complementario);
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

    public List<Complementario> findComplementarioEntities() {
        return findComplementarioEntities(true, -1, -1);
    }

    public List<Complementario> findComplementarioEntities(int maxResults, int firstResult) {
        return findComplementarioEntities(false, maxResults, firstResult);
    }

    private List<Complementario> findComplementarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Complementario.class));
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

    public Complementario findComplementario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Complementario.class, id);
        } finally {
            em.close();
        }
    }

    public int getComplementarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Complementario> rt = cq.from(Complementario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
