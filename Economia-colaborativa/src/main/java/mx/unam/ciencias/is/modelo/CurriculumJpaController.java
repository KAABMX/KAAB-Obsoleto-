/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.ciencias.is.modelo;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import mx.unam.ciencias.is.mapeobd.Profesor;
import mx.unam.ciencias.is.mapeobd.Complementario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import mx.unam.ciencias.is.controlador.exceptions.NonexistentEntityException;
import mx.unam.ciencias.is.controlador.exceptions.RollbackFailureException;
import mx.unam.ciencias.is.mapeobd.Curriculum;
import mx.unam.ciencias.is.mapeobd.Estudio;
import mx.unam.ciencias.is.mapeobd.Experiencia;

/**
 *
 * @author luis
 */
public class CurriculumJpaController implements Serializable {

    public CurriculumJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Curriculum curriculum) throws RollbackFailureException, Exception {
        if (curriculum.getComplementarioList() == null) {
            curriculum.setComplementarioList(new ArrayList<Complementario>());
        }
        if (curriculum.getEstudioList() == null) {
            curriculum.setEstudioList(new ArrayList<Estudio>());
        }
        if (curriculum.getExperienciaList() == null) {
            curriculum.setExperienciaList(new ArrayList<Experiencia>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Profesor fkIdProfesor = curriculum.getFkIdProfesor();
            if (fkIdProfesor != null) {
                fkIdProfesor = em.getReference(fkIdProfesor.getClass(), fkIdProfesor.getPkIdProfesor());
                curriculum.setFkIdProfesor(fkIdProfesor);
            }
            List<Complementario> attachedComplementarioList = new ArrayList<Complementario>();
            for (Complementario complementarioListComplementarioToAttach : curriculum.getComplementarioList()) {
                complementarioListComplementarioToAttach = em.getReference(complementarioListComplementarioToAttach.getClass(), complementarioListComplementarioToAttach.getPkIdComplementarios());
                attachedComplementarioList.add(complementarioListComplementarioToAttach);
            }
            curriculum.setComplementarioList(attachedComplementarioList);
            List<Estudio> attachedEstudioList = new ArrayList<Estudio>();
            for (Estudio estudioListEstudioToAttach : curriculum.getEstudioList()) {
                estudioListEstudioToAttach = em.getReference(estudioListEstudioToAttach.getClass(), estudioListEstudioToAttach.getPkIdEstudios());
                attachedEstudioList.add(estudioListEstudioToAttach);
            }
            curriculum.setEstudioList(attachedEstudioList);
            List<Experiencia> attachedExperienciaList = new ArrayList<Experiencia>();
            for (Experiencia experienciaListExperienciaToAttach : curriculum.getExperienciaList()) {
                experienciaListExperienciaToAttach = em.getReference(experienciaListExperienciaToAttach.getClass(), experienciaListExperienciaToAttach.getPkIdExperiencia());
                attachedExperienciaList.add(experienciaListExperienciaToAttach);
            }
            curriculum.setExperienciaList(attachedExperienciaList);
            em.persist(curriculum);
            if (fkIdProfesor != null) {
                fkIdProfesor.getCurriculumList().add(curriculum);
                fkIdProfesor = em.merge(fkIdProfesor);
            }
            for (Complementario complementarioListComplementario : curriculum.getComplementarioList()) {
                Curriculum oldFkIdCvOfComplementarioListComplementario = complementarioListComplementario.getFkIdCv();
                complementarioListComplementario.setFkIdCv(curriculum);
                complementarioListComplementario = em.merge(complementarioListComplementario);
                if (oldFkIdCvOfComplementarioListComplementario != null) {
                    oldFkIdCvOfComplementarioListComplementario.getComplementarioList().remove(complementarioListComplementario);
                    oldFkIdCvOfComplementarioListComplementario = em.merge(oldFkIdCvOfComplementarioListComplementario);
                }
            }
            for (Estudio estudioListEstudio : curriculum.getEstudioList()) {
                Curriculum oldFkIdCvOfEstudioListEstudio = estudioListEstudio.getFkIdCv();
                estudioListEstudio.setFkIdCv(curriculum);
                estudioListEstudio = em.merge(estudioListEstudio);
                if (oldFkIdCvOfEstudioListEstudio != null) {
                    oldFkIdCvOfEstudioListEstudio.getEstudioList().remove(estudioListEstudio);
                    oldFkIdCvOfEstudioListEstudio = em.merge(oldFkIdCvOfEstudioListEstudio);
                }
            }
            for (Experiencia experienciaListExperiencia : curriculum.getExperienciaList()) {
                Curriculum oldFkIdCvOfExperienciaListExperiencia = experienciaListExperiencia.getFkIdCv();
                experienciaListExperiencia.setFkIdCv(curriculum);
                experienciaListExperiencia = em.merge(experienciaListExperiencia);
                if (oldFkIdCvOfExperienciaListExperiencia != null) {
                    oldFkIdCvOfExperienciaListExperiencia.getExperienciaList().remove(experienciaListExperiencia);
                    oldFkIdCvOfExperienciaListExperiencia = em.merge(oldFkIdCvOfExperienciaListExperiencia);
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

    public void edit(Curriculum curriculum) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Curriculum persistentCurriculum = em.find(Curriculum.class, curriculum.getPkIdCv());
            Profesor fkIdProfesorOld = persistentCurriculum.getFkIdProfesor();
            Profesor fkIdProfesorNew = curriculum.getFkIdProfesor();
            List<Complementario> complementarioListOld = persistentCurriculum.getComplementarioList();
            List<Complementario> complementarioListNew = curriculum.getComplementarioList();
            List<Estudio> estudioListOld = persistentCurriculum.getEstudioList();
            List<Estudio> estudioListNew = curriculum.getEstudioList();
            List<Experiencia> experienciaListOld = persistentCurriculum.getExperienciaList();
            List<Experiencia> experienciaListNew = curriculum.getExperienciaList();
            if (fkIdProfesorNew != null) {
                fkIdProfesorNew = em.getReference(fkIdProfesorNew.getClass(), fkIdProfesorNew.getPkIdProfesor());
                curriculum.setFkIdProfesor(fkIdProfesorNew);
            }
            List<Complementario> attachedComplementarioListNew = new ArrayList<Complementario>();
            for (Complementario complementarioListNewComplementarioToAttach : complementarioListNew) {
                complementarioListNewComplementarioToAttach = em.getReference(complementarioListNewComplementarioToAttach.getClass(), complementarioListNewComplementarioToAttach.getPkIdComplementarios());
                attachedComplementarioListNew.add(complementarioListNewComplementarioToAttach);
            }
            complementarioListNew = attachedComplementarioListNew;
            curriculum.setComplementarioList(complementarioListNew);
            List<Estudio> attachedEstudioListNew = new ArrayList<Estudio>();
            for (Estudio estudioListNewEstudioToAttach : estudioListNew) {
                estudioListNewEstudioToAttach = em.getReference(estudioListNewEstudioToAttach.getClass(), estudioListNewEstudioToAttach.getPkIdEstudios());
                attachedEstudioListNew.add(estudioListNewEstudioToAttach);
            }
            estudioListNew = attachedEstudioListNew;
            curriculum.setEstudioList(estudioListNew);
            List<Experiencia> attachedExperienciaListNew = new ArrayList<Experiencia>();
            for (Experiencia experienciaListNewExperienciaToAttach : experienciaListNew) {
                experienciaListNewExperienciaToAttach = em.getReference(experienciaListNewExperienciaToAttach.getClass(), experienciaListNewExperienciaToAttach.getPkIdExperiencia());
                attachedExperienciaListNew.add(experienciaListNewExperienciaToAttach);
            }
            experienciaListNew = attachedExperienciaListNew;
            curriculum.setExperienciaList(experienciaListNew);
            curriculum = em.merge(curriculum);
            if (fkIdProfesorOld != null && !fkIdProfesorOld.equals(fkIdProfesorNew)) {
                fkIdProfesorOld.getCurriculumList().remove(curriculum);
                fkIdProfesorOld = em.merge(fkIdProfesorOld);
            }
            if (fkIdProfesorNew != null && !fkIdProfesorNew.equals(fkIdProfesorOld)) {
                fkIdProfesorNew.getCurriculumList().add(curriculum);
                fkIdProfesorNew = em.merge(fkIdProfesorNew);
            }
            for (Complementario complementarioListOldComplementario : complementarioListOld) {
                if (!complementarioListNew.contains(complementarioListOldComplementario)) {
                    complementarioListOldComplementario.setFkIdCv(null);
                    complementarioListOldComplementario = em.merge(complementarioListOldComplementario);
                }
            }
            for (Complementario complementarioListNewComplementario : complementarioListNew) {
                if (!complementarioListOld.contains(complementarioListNewComplementario)) {
                    Curriculum oldFkIdCvOfComplementarioListNewComplementario = complementarioListNewComplementario.getFkIdCv();
                    complementarioListNewComplementario.setFkIdCv(curriculum);
                    complementarioListNewComplementario = em.merge(complementarioListNewComplementario);
                    if (oldFkIdCvOfComplementarioListNewComplementario != null && !oldFkIdCvOfComplementarioListNewComplementario.equals(curriculum)) {
                        oldFkIdCvOfComplementarioListNewComplementario.getComplementarioList().remove(complementarioListNewComplementario);
                        oldFkIdCvOfComplementarioListNewComplementario = em.merge(oldFkIdCvOfComplementarioListNewComplementario);
                    }
                }
            }
            for (Estudio estudioListOldEstudio : estudioListOld) {
                if (!estudioListNew.contains(estudioListOldEstudio)) {
                    estudioListOldEstudio.setFkIdCv(null);
                    estudioListOldEstudio = em.merge(estudioListOldEstudio);
                }
            }
            for (Estudio estudioListNewEstudio : estudioListNew) {
                if (!estudioListOld.contains(estudioListNewEstudio)) {
                    Curriculum oldFkIdCvOfEstudioListNewEstudio = estudioListNewEstudio.getFkIdCv();
                    estudioListNewEstudio.setFkIdCv(curriculum);
                    estudioListNewEstudio = em.merge(estudioListNewEstudio);
                    if (oldFkIdCvOfEstudioListNewEstudio != null && !oldFkIdCvOfEstudioListNewEstudio.equals(curriculum)) {
                        oldFkIdCvOfEstudioListNewEstudio.getEstudioList().remove(estudioListNewEstudio);
                        oldFkIdCvOfEstudioListNewEstudio = em.merge(oldFkIdCvOfEstudioListNewEstudio);
                    }
                }
            }
            for (Experiencia experienciaListOldExperiencia : experienciaListOld) {
                if (!experienciaListNew.contains(experienciaListOldExperiencia)) {
                    experienciaListOldExperiencia.setFkIdCv(null);
                    experienciaListOldExperiencia = em.merge(experienciaListOldExperiencia);
                }
            }
            for (Experiencia experienciaListNewExperiencia : experienciaListNew) {
                if (!experienciaListOld.contains(experienciaListNewExperiencia)) {
                    Curriculum oldFkIdCvOfExperienciaListNewExperiencia = experienciaListNewExperiencia.getFkIdCv();
                    experienciaListNewExperiencia.setFkIdCv(curriculum);
                    experienciaListNewExperiencia = em.merge(experienciaListNewExperiencia);
                    if (oldFkIdCvOfExperienciaListNewExperiencia != null && !oldFkIdCvOfExperienciaListNewExperiencia.equals(curriculum)) {
                        oldFkIdCvOfExperienciaListNewExperiencia.getExperienciaList().remove(experienciaListNewExperiencia);
                        oldFkIdCvOfExperienciaListNewExperiencia = em.merge(oldFkIdCvOfExperienciaListNewExperiencia);
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
                Integer id = curriculum.getPkIdCv();
                if (findCurriculum(id) == null) {
                    throw new NonexistentEntityException("The curriculum with id " + id + " no longer exists.");
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
            Curriculum curriculum;
            try {
                curriculum = em.getReference(Curriculum.class, id);
                curriculum.getPkIdCv();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The curriculum with id " + id + " no longer exists.", enfe);
            }
            Profesor fkIdProfesor = curriculum.getFkIdProfesor();
            if (fkIdProfesor != null) {
                fkIdProfesor.getCurriculumList().remove(curriculum);
                fkIdProfesor = em.merge(fkIdProfesor);
            }
            List<Complementario> complementarioList = curriculum.getComplementarioList();
            for (Complementario complementarioListComplementario : complementarioList) {
                complementarioListComplementario.setFkIdCv(null);
                complementarioListComplementario = em.merge(complementarioListComplementario);
            }
            List<Estudio> estudioList = curriculum.getEstudioList();
            for (Estudio estudioListEstudio : estudioList) {
                estudioListEstudio.setFkIdCv(null);
                estudioListEstudio = em.merge(estudioListEstudio);
            }
            List<Experiencia> experienciaList = curriculum.getExperienciaList();
            for (Experiencia experienciaListExperiencia : experienciaList) {
                experienciaListExperiencia.setFkIdCv(null);
                experienciaListExperiencia = em.merge(experienciaListExperiencia);
            }
            em.remove(curriculum);
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

    public List<Curriculum> findCurriculumEntities() {
        return findCurriculumEntities(true, -1, -1);
    }

    public List<Curriculum> findCurriculumEntities(int maxResults, int firstResult) {
        return findCurriculumEntities(false, maxResults, firstResult);
    }

    private List<Curriculum> findCurriculumEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Curriculum.class));
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

    public Curriculum findCurriculum(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Curriculum.class, id);
        } finally {
            em.close();
        }
    }

    public int getCurriculumCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Curriculum> rt = cq.from(Curriculum.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
