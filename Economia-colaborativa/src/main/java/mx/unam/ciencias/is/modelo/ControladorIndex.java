/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.ciencias.is.modelo;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author jonh
 */
@Controller 
public class ControladorIndex {
    
     /**
      * Metodo que responde a la peticion raiz
      * @return el nombre del jsp de inicio
      */
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String index(){
        return "header";
    
    }/*
    @RequestMapping(value="/peticion1", method = RequestMethod.GET)
    public void peticion1(HttpServletRequest request){
        String parametro = request.getParameter("parametro");
        System.out.println("La peticion1 es: =================>"+parametro);
    
    }
    
    @RequestMapping(value="/peticion2", method = RequestMethod.GET)
    public ModelAndView peticion2(HttpServletRequest request,ModelMap model){
        String parametro = request.getParameter("parametro");
        System.out.println("La peticion2 es: =================>"+parametro);
        model.addAttribute("parametro", parametro);
        return new ModelAndView("index",model);
    
    }
    
    @RequestMapping(value="/peticion3", method = RequestMethod.GET)
    public ModelAndView peticion3(HttpServletRequest request,ModelMap model){
        String parametro = request.getParameter("parametro");
        System.out.println("La peticion2 es: =================>"+parametro);
        model.addAttribute("parametro", parametro);
        return new ModelAndView("index",model);
    
    }*/
}
