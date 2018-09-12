/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.ciencias.is.controlador;

import mx.unam.ciencias.is.entidad.*;
import java.util.regex.Pattern;

/**
 * Bean para manejar el registro.
 * @author Moctezuma19
 */
public class Registro {
    
    private Usuario usuario;
    private Alumno alumno;
    private Profesor profesor;
    private Complementario complementario;
    private Curriculum cv;
    private Estudio estudio;
    private Experiencia exp;
    private InteresAcademico interes;
    
    public Usuario getUsuario(){
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }
    
    public Alumno getAlumno(){
        return this.alumno;
    }
    public void setAlumno(Alumno alumno){
        this.alumno = alumno;
    }
    
    public Profesor getProfesor(){
        return this.profesor;
    }
    
    public void setProfesor(Profesor profesor){
        this.profesor = profesor;
    }
    
    public Complementario getComplementario(){
        return this.complementario;
    }
    
    public void setComplementario(Complementario c){
        this.complementario = c;
    }
    
    public Estudio getEstudio(){
        return this.estudio;
    }
    
    public void setEstudio(Estudio estudio){
        this.estudio = estudio;
    }
    
    public Curriculum getCv(){
        return this.cv;
    }
    
    public void setCv(Curriculum cv){
        this.cv = cv;
    }
    
    public Experiencia getExp(){
        return this.exp;
    }
    
    public void setExp(Experiencia exp){
        this.exp = exp;
    }
    /**
     * Metodo para validar un correo
     * @return true si es valido.
     */
    public boolean validaCorreo(){
        return false;
    }
    
    public void registra(){
        
    }
    
}
