<%-- 
    Document   : opcion
    Created on : 14/09/2018, 11:34:09 PM
    Author     : hectorsama
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet"  type = "text/css" href="<c:url value="/css/opcion.css"/>"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"> 

        <title>JSP Page</title>
    </head>
    <body>

        <section class="border-btn">
    <div class="container">
       <div class="row text-center">
    
            <div class="btn-heading">
                 <h1>Registrarse como:</h1>
            </div>
           <form action="${pageContext.request.contextPath}/registrarAlumno" method="GET">
                        <input type="submit" class="btn3" value="Alumno"/>
           </form>
           

           <form action="${pageContext.request.contextPath}/registrarProfesor" method="GET">
               <input type="submit" class="btn3" value="Profesor"/>
            </form>
           </div> 
   </div>
</section>
   
    </body>
</html>
