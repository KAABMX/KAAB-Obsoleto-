/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.ciencias.is.controlador;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import mx.unam.ciencias.is.entidad.Usuario;
import mx.unam.ciencias.is.entidad.Curriculum;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import mx.unam.ciencias.is.controlador.exceptions.IllegalOrphanException;
import mx.unam.ciencias.is.controlador.exceptions.NonexistentEntityException;
import mx.unam.ciencias.is.controlador.exceptions.RollbackFailureException;
import mx.unam.ciencias.is.entidad.Profesor;

/**
 *
 * @author luis
 */
public class ProfesorJpaController implements Serializable {

    public ProfesorJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Profesor profesor) throws RollbackFailureException, Exception {
        if (profesor.getCurriculumList() == null) {
            profesor.setCurriculumList(new ArrayList<Curriculum>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario fkIdUsuario = profesor.getFkIdUsuario();
            if (fkIdUsuario != null) {
                fkIdUsuario = em.getReference(fkIdUsuario.getClass(), fkIdUsuario.getPkIdUsuario());
                profesor.setFkIdUsuario(fkIdUsuario);
            }
            List<Curriculum> attachedCurriculumList = new ArrayList<Curriculum>();
            for (Curriculum curriculumListCurriculumToAttach : profesor.getCurriculumList()) {
                curriculumListCurriculumToAttach = em.getReference(curriculumListCurriculumToAttach.getClass(), curriculumListCurriculumToAttach.getPkIdCv());
                attachedCurriculumList.add(curriculumListCurriculumToAttach);
            }
            profesor.setCurriculumList(attachedCurriculumList);
            em.persist(profesor);
            if (fkIdUsuario != null) {
                fkIdUsuario.getProfesorList().add(profesor);
                fkIdUsuario = em.merge(fkIdUsuario);
            }
            for (Curriculum curriculumListCurriculum : profesor.getCurriculumList()) {
                Profesor oldFkIdProfesorOfCurriculumListCurriculum = curriculumListCurriculum.getFkIdProfesor();
                curriculumListCurriculum.setFkIdProfesor(profesor);
                curriculumListCurriculum = em.merge(curriculumListCurriculum);
                if (oldFkIdProfesorOfCurriculumListCurriculum != null) {
                    oldFkIdProfesorOfCurriculumListCurriculum.getCurriculumList().remove(curriculumListCurriculum);
                    oldFkIdProfesorOfCurriculumListCurriculum = em.merge(oldFkIdProfesorOfCurriculumListCurriculum);
                }
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

    public void edit(Profesor profesor) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Profesor persistentProfesor = em.find(Profesor.class, profesor.getPkIdProfesor());
            Usuario fkIdUsuarioOld = persistentProfesor.getFkIdUsuario();
            Usuario fkIdUsuarioNew = profesor.getFkIdUsuario();
            List<Curriculum> curriculumListOld = persistentProfesor.getCurriculumList();
            List<Curriculum> curriculumListNew = profesor.getCurriculumList();
            List<String> illegalOrphanMessages = null;
            for (Curriculum curriculumListOldCurriculum : curriculumListOld) {
                if (!curriculumListNew.contains(curriculumListOldCurriculum)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Curriculum " + curriculumListOldCurriculum + " since its fkIdProfesor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkIdUsuarioNew != null) {
                fkIdUsuarioNew = em.getReference(fkIdUsuarioNew.getClass(), fkIdUsuarioNew.getPkIdUsuario());
                profesor.setFkIdUsuario(fkIdUsuarioNew);
            }
            List<Curriculum> attachedCurriculumListNew = new ArrayList<Curriculum>();
            for (Curriculum curriculumListNewCurriculumToAttach : curriculumListNew) {
                curriculumListNewCurriculumToAttach = em.getReference(curriculumListNewCurriculumToAttach.getClass(), curriculumListNewCurriculumToAttach.getPkIdCv());
                attachedCurriculumListNew.add(curriculumListNewCurriculumToAttach);
            }
            curriculumListNew = attachedCurriculumListNew;
            profesor.setCurriculumList(curriculumListNew);
            profesor = em.merge(profesor);
            if (fkIdUsuarioOld != null && !fkIdUsuarioOld.equals(fkIdUsuarioNew)) {
                fkIdUsuarioOld.getProfesorList().remove(profesor);
                fkIdUsuarioOld = em.merge(fkIdUsuarioOld);
            }
            if (fkIdUsuarioNew != null && !fkIdUsuarioNew.equals(fkIdUsuarioOld)) {
                fkIdUsuarioNew.getProfesorList().add(profesor);
                fkIdUsuarioNew = em.merge(fkIdUsuarioNew);
            }
            for (Curriculum curriculumListNewCurriculum : curriculumListNew) {
                if (!curriculumListOld.contains(curriculumListNewCurriculum)) {
                    Profesor oldFkIdProfesorOfCurriculumListNewCurriculum = curriculumListNewCurriculum.getFkIdProfesor();
                    curriculumListNewCurriculum.setFkIdProfesor(profesor);
                    curriculumListNewCurriculum = em.merge(curriculumListNewCurriculum);
                    if (oldFkIdProfesorOfCurriculumListNewCurriculum != null && !oldFkIdProfesorOfCurriculumListNewCurriculum.equals(profesor)) {
                        oldFkIdProfesorOfCurriculumListNewCurriculum.getCurriculumList().remove(curriculumListNewCurriculum);
                        oldFkIdProfesorOfCurriculumListNewCurriculum = em.merge(oldFkIdProfesorOfCurriculumListNewCurriculum);
                    }
                }
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
                Integer id = profesor.getPkIdProfesor();
                if (findProfesor(id) == null) {
                    throw new NonexistentEntityException("The profesor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Profesor profesor;
            try {
                profesor = em.getReference(Profesor.class, id);
                profesor.getPkIdProfesor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The profesor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Curriculum> curriculumListOrphanCheck = profesor.getCurriculumList();
            for (Curriculum curriculumListOrphanCheckCurriculum : curriculumListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Profesor (" + profesor + ") cannot be destroyed since the Curriculum " + curriculumListOrphanCheckCurriculum + " in its curriculumList field has a non-nullable fkIdProfesor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario fkIdUsuario = profesor.getFkIdUsuario();
            if (fkIdUsuario != null) {
                fkIdUsuario.getProfesorList().remove(profesor);
                fkIdUsuario = em.merge(fkIdUsuario);
            }
            em.remove(profesor);
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

    public List<Profesor> findProfesorEntities() {
        return findProfesorEntities(true, -1, -1);
    }

    public List<Profesor> findProfesorEntities(int maxResults, int firstResult) {
        return findProfesorEntities(false, maxResults, firstResult);
    }

    private List<Profesor> findProfesorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Profesor.class));
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

    public Profesor findProfesor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Profesor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProfesorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Profesor> rt = cq.from(Profesor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
