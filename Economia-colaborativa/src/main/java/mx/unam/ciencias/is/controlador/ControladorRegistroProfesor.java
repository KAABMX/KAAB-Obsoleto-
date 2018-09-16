/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.ciencias.is.controlador;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;
import mx.unam.ciencias.is.mapeobd.Alumno;
import mx.unam.ciencias.is.mapeobd.Complementario;
import mx.unam.ciencias.is.mapeobd.Curriculum;
import mx.unam.ciencias.is.mapeobd.Estudio;
import mx.unam.ciencias.is.mapeobd.Experiencia;
import mx.unam.ciencias.is.mapeobd.InteresAcademico;
import mx.unam.ciencias.is.mapeobd.Profesor;
import mx.unam.ciencias.is.mapeobd.Usuario;
import mx.unam.ciencias.is.modelo.ComplementarioJpaController;
import mx.unam.ciencias.is.modelo.CurriculumJpaController;
import mx.unam.ciencias.is.modelo.EstudioJpaController;
import mx.unam.ciencias.is.modelo.ExperienciaJpaController;
import mx.unam.ciencias.is.modelo.ProfesorJpaController;
import mx.unam.ciencias.is.modelo.UsuarioJpaController;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hectorsama
 */
public class ControladorRegistroProfesor {
    
    @RequestMapping(value = "/registraProfesor", method = RequestMethod.POST)
    public ModelAndView peticion(HttpServletRequest request, ModelMap model) {
        try {
            Context context = new InitialContext();
            UserTransaction utx = (UserTransaction)context.lookup("java:comp/UserTransaction");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("mx.unam.ciencias.is_kaab_war_1.0-SNAPSHOTPU");
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController(utx,emf);
            ProfesorJpaController profesorJpaController = new ProfesorJpaController(utx,emf);
            CurriculumJpaController cvJpaController = new CurriculumJpaController(utx,emf);
            ExperienciaJpaController exJpaController = new ExperienciaJpaController(utx,emf);
            EstudioJpaController esJpaController = new EstudioJpaController(utx,emf);
            ComplementarioJpaController comJpaController = new ComplementarioJpaController(utx,emf);
            //crear jpaControllers para las entidades.
            Usuario usuario = new Usuario();
            usuario.setCorreo(request.getParameter("correo"));//previamente se revisa
            usuario.setNombre(request.getParameter("nombre"));
            usuario.setApellidoMaterno(request.getParameter("materno"));
            usuario.setApellidoPaterno(request.getParameter("paterno"));
            usuario.setTelefono(request.getParameter("telefono"));
            //InputStream foto = new FileInputStream(request.getParameter("foto"));
            //convertir la foto a bytes y agregarlo al usuario
            usuario.setSexo(request.getParameter("sexo"));

            //hasta aqui se crea el usuario
            //agregar a la base
            usuarioJpaController.create(usuario);
    
                Profesor p = new Profesor();
                p.setCostoXHora(request.getParameter("costo"));
                p.setFkIdUsuario(usuario);
                /*
                InputStream ident = new FileInputStream(request.getParameter("foto"));
                String costo = reques.getParameter("foto");
                p.set...
                 */
                p.setHabilidades(request.getParameter("habilidades"));
                p.setNivelesEducativos(request.getParameter("niveles"));
                //agregar a la base
                profesorJpaController.create(p);
                
                Curriculum cv = new Curriculum();
                cv.setFkIdProfesor(p);
                cv.setLugarDeNacimiento(request.getParameter("nacimiento"));
                //agregar a la base
                cvJpaController.create(cv);
                
                Experiencia exp = new Experiencia();
                exp.setEmpresa(request.getParameter("empresa"));
                //exp.setFechaFin(fechaFin);
                //exp.setFechaInicio(fechaInicio);
                exp.setFkIdCv(cv);
                exp.setFuncionTrabajo(request.getParameter("trabajo"));
                exp.setTareaTrabajo(request.getParameter("tarea"));
                //agregar a la base
                exJpaController.create(exp);
                
                Estudio es = new Estudio();
                es.setEstudio(request.getParameter("estudio"));
                //es.getFechaFin(fin);
                //es.getFechaInicio(inicio);
                es.setFkIdCv(cv);
                es.setUniversidad(request.getParameter("universidad"));
                //agregar a la base
                esJpaController.create(es);
                
                Complementario com = new Complementario();
                com.setCentro(request.getParameter("centro"));
                com.setEstudio(request.getParameter("estudiob"));
                //com.setFechaFin(fechaFin);
                //com.setFechaInicio(fechaInicio);
                com.setFkIdCv(cv);
                com.setLugar(request.getParameter("lugar"));
                //agregar a la base
                comJpaController.create(com);
        } catch (Exception e) {

        }
        return new ModelAndView("index", model);
    }
}
