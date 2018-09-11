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
import mx.unam.ciencias.is.entidad.Curriculum;
import mx.unam.ciencias.is.entidad.Experiencia;

/**
 *
 * @author luis
 */
public class ExperienciaJpaController implements Serializable {

    public ExperienciaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Experiencia experiencia) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Curriculum fkIdCv = experiencia.getFkIdCv();
            if (fkIdCv != null) {
                fkIdCv = em.getReference(fkIdCv.getClass(), fkIdCv.getPkIdCv());
                experiencia.setFkIdCv(fkIdCv);
            }
            em.persist(experiencia);
            if (fkIdCv != null) {
                fkIdCv.getExperienciaList().add(experiencia);
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

    public void edit(Experiencia experiencia) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Experiencia persistentExperiencia = em.find(Experiencia.class, experiencia.getPkIdExperiencia());
            Curriculum fkIdCvOld = persistentExperiencia.getFkIdCv();
            Curriculum fkIdCvNew = experiencia.getFkIdCv();
            if (fkIdCvNew != null) {
                fkIdCvNew = em.getReference(fkIdCvNew.getClass(), fkIdCvNew.getPkIdCv());
                experiencia.setFkIdCv(fkIdCvNew);
            }
            experiencia = em.merge(experiencia);
            if (fkIdCvOld != null && !fkIdCvOld.equals(fkIdCvNew)) {
                fkIdCvOld.getExperienciaList().remove(experiencia);
                fkIdCvOld = em.merge(fkIdCvOld);
            }
            if (fkIdCvNew != null && !fkIdCvNew.equals(fkIdCvOld)) {
                fkIdCvNew.getExperienciaList().add(experiencia);
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
                Integer id = experiencia.getPkIdExperiencia();
                if (findExperiencia(id) == null) {
                    throw new NonexistentEntityException("The experiencia with id " + id + " no longer exists.");
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
            Experiencia experiencia;
            try {
                experiencia = em.getReference(Experiencia.class, id);
                experiencia.getPkIdExperiencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The experiencia with id " + id + " no longer exists.", enfe);
            }
            Curriculum fkIdCv = experiencia.getFkIdCv();
            if (fkIdCv != null) {
                fkIdCv.getExperienciaList().remove(experiencia);
                fkIdCv = em.merge(fkIdCv);
            }
            em.remove(experiencia);
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

    public List<Experiencia> findExperienciaEntities() {
        return findExperienciaEntities(true, -1, -1);
    }

    public List<Experiencia> findExperienciaEntities(int maxResults, int firstResult) {
        return findExperienciaEntities(false, maxResults, firstResult);
    }

    private List<Experiencia> findExperienciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Experiencia.class));
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

    public Experiencia findExperiencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Experiencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getExperienciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Experiencia> rt = cq.from(Experiencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
