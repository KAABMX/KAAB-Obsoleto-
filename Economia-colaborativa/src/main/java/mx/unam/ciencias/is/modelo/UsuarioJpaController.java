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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import mx.unam.ciencias.is.controlador.exceptions.IllegalOrphanException;
import mx.unam.ciencias.is.controlador.exceptions.NonexistentEntityException;
import mx.unam.ciencias.is.controlador.exceptions.RollbackFailureException;
import mx.unam.ciencias.is.mapeobd.Usuario;

/**
 *
 * @author luis
 */
public class UsuarioJpaController implements Serializable {
    
    public UsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws RollbackFailureException, Exception {
        if (usuario.getProfesorList() == null) {
            usuario.setProfesorList(new ArrayList<Profesor>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Profesor> attachedProfesorList = new ArrayList<Profesor>();
            for (Profesor profesorListProfesorToAttach : usuario.getProfesorList()) {
                profesorListProfesorToAttach = em.getReference(profesorListProfesorToAttach.getClass(), profesorListProfesorToAttach.getPkIdProfesor());
                attachedProfesorList.add(profesorListProfesorToAttach);
            }
            usuario.setProfesorList(attachedProfesorList);
            em.persist(usuario);
            for (Profesor profesorListProfesor : usuario.getProfesorList()) {
                Usuario oldFkIdUsuarioOfProfesorListProfesor = profesorListProfesor.getFkIdUsuario();
                profesorListProfesor.setFkIdUsuario(usuario);
                profesorListProfesor = em.merge(profesorListProfesor);
                if (oldFkIdUsuarioOfProfesorListProfesor != null) {
                    oldFkIdUsuarioOfProfesorListProfesor.getProfesorList().remove(profesorListProfesor);
                    oldFkIdUsuarioOfProfesorListProfesor = em.merge(oldFkIdUsuarioOfProfesorListProfesor);
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

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getPkIdUsuario());
            List<Profesor> profesorListOld = persistentUsuario.getProfesorList();
            List<Profesor> profesorListNew = usuario.getProfesorList();
            List<String> illegalOrphanMessages = null;
            for (Profesor profesorListOldProfesor : profesorListOld) {
                if (!profesorListNew.contains(profesorListOldProfesor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Profesor " + profesorListOldProfesor + " since its fkIdUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Profesor> attachedProfesorListNew = new ArrayList<Profesor>();
            for (Profesor profesorListNewProfesorToAttach : profesorListNew) {
                profesorListNewProfesorToAttach = em.getReference(profesorListNewProfesorToAttach.getClass(), profesorListNewProfesorToAttach.getPkIdProfesor());
                attachedProfesorListNew.add(profesorListNewProfesorToAttach);
            }
            profesorListNew = attachedProfesorListNew;
            usuario.setProfesorList(profesorListNew);
            usuario = em.merge(usuario);
            for (Profesor profesorListNewProfesor : profesorListNew) {
                if (!profesorListOld.contains(profesorListNewProfesor)) {
                    Usuario oldFkIdUsuarioOfProfesorListNewProfesor = profesorListNewProfesor.getFkIdUsuario();
                    profesorListNewProfesor.setFkIdUsuario(usuario);
                    profesorListNewProfesor = em.merge(profesorListNewProfesor);
                    if (oldFkIdUsuarioOfProfesorListNewProfesor != null && !oldFkIdUsuarioOfProfesorListNewProfesor.equals(usuario)) {
                        oldFkIdUsuarioOfProfesorListNewProfesor.getProfesorList().remove(profesorListNewProfesor);
                        oldFkIdUsuarioOfProfesorListNewProfesor = em.merge(oldFkIdUsuarioOfProfesorListNewProfesor);
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
                Integer id = usuario.getPkIdUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getPkIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Profesor> profesorListOrphanCheck = usuario.getProfesorList();
            for (Profesor profesorListOrphanCheckProfesor : profesorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Profesor " + profesorListOrphanCheckProfesor + " in its profesorList field has a non-nullable fkIdUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
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

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
