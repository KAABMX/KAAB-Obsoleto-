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
import mx.unam.ciencias.is.mapeobd.Curriculum;
import mx.unam.ciencias.is.mapeobd.Estudio;

/**
 *
 * @author luis
 */
public class EstudioJpaController implements Serializable {

    public EstudioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudio estudio) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Curriculum fkIdCv = estudio.getFkIdCv();
            if (fkIdCv != null) {
                fkIdCv = em.getReference(fkIdCv.getClass(), fkIdCv.getPkIdCv());
                estudio.setFkIdCv(fkIdCv);
            }
            em.persist(estudio);
            if (fkIdCv != null) {
                fkIdCv.getEstudioList().add(estudio);
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

    public void edit(Estudio estudio) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Estudio persistentEstudio = em.find(Estudio.class, estudio.getPkIdEstudios());
            Curriculum fkIdCvOld = persistentEstudio.getFkIdCv();
            Curriculum fkIdCvNew = estudio.getFkIdCv();
            if (fkIdCvNew != null) {
                fkIdCvNew = em.getReference(fkIdCvNew.getClass(), fkIdCvNew.getPkIdCv());
                estudio.setFkIdCv(fkIdCvNew);
            }
            estudio = em.merge(estudio);
            if (fkIdCvOld != null && !fkIdCvOld.equals(fkIdCvNew)) {
                fkIdCvOld.getEstudioList().remove(estudio);
                fkIdCvOld = em.merge(fkIdCvOld);
            }
            if (fkIdCvNew != null && !fkIdCvNew.equals(fkIdCvOld)) {
                fkIdCvNew.getEstudioList().add(estudio);
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
                Integer id = estudio.getPkIdEstudios();
                if (findEstudio(id) == null) {
                    throw new NonexistentEntityException("The estudio with id " + id + " no longer exists.");
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
            Estudio estudio;
            try {
                estudio = em.getReference(Estudio.class, id);
                estudio.getPkIdEstudios();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudio with id " + id + " no longer exists.", enfe);
            }
            Curriculum fkIdCv = estudio.getFkIdCv();
            if (fkIdCv != null) {
                fkIdCv.getEstudioList().remove(estudio);
                fkIdCv = em.merge(fkIdCv);
            }
            em.remove(estudio);
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

    public List<Estudio> findEstudioEntities() {
        return findEstudioEntities(true, -1, -1);
    }

    public List<Estudio> findEstudioEntities(int maxResults, int firstResult) {
        return findEstudioEntities(false, maxResults, firstResult);
    }

    private List<Estudio> findEstudioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estudio.class));
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

    public Estudio findEstudio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudio.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudio> rt = cq.from(Estudio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
