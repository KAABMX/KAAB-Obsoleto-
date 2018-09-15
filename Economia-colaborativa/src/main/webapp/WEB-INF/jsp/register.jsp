<%-- 
    Document   : register
    Created on : 13/09/2018, 06:07:15 PM
    Author     : hectorsama
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/register.css"/>"> 
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio</title>
    </head>
    <body>

        <!------ Include the above in your HEAD tag ---------->

        <form action="#" name="myForm" method="post" onsubmit="return(validate());">
            <div class="container-fluid">
                <div class="row">
                    <div class="well center-block">
                        <div class="well-header">
                            <h3 class="text-center text-success"> Registro Alumno </h3>
                            <hr>
                        </div>

                        <div class="row">
                            <div class="col-md-12 col-sm-12 col-xs-12">
                                <div class="form-group">
                                    <div class="input-group">
                                        <div class="input-group-addon">
                                            <i class="glyphicon glyphicon-user"></i>
                                        </div>
                                        <input type="text" placeholder="Nombre" name="txtfname" class="form-control">

                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-12">
                                <div class="form-group">
                                    <div class="input-group">
                                        <div class="input-group-addon">
                                            <i class="glyphicon glyphicon-user"></i>
                                        </div>
                                        <input type="text" placeholder="Apellido Paterno" name="txtlname" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                                                <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-12">
                                <div class="form-group">
                                    <div class="input-group">
                                        <div class="input-group-addon">
                                            <i class="glyphicon glyphicon-user"></i>
                                        </div>
                                        <input type="text" placeholder="Apellido Materno" name="txtlname" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>

                                                <div class="row">
                            <div class="col-md-12 col-xs-12 col-sm-12">
                                <div class="form-group">
                                    <div class="input-group">
                                        <div class="input-group-addon">
                                            <i class="glyphicon glyphicon-envelope"></i>
                                        </div>
                                        <input type="email" class="form-control" name="txtmail" placeholder="E-Mail">
                                    </div>
                                </div>
                            </div>
                        </div>
        
                        <div class="row">
                            <div class="col-md-12 col-xs-12 col-sm-12">
                                <div class="form-group">
                                    <div class="input-group">
                                        <div class="input-group-addon">
                                            <i class="glyphicon glyphicon-phone"></i>
                                        </div>
                                        <input type="number" minlength="10" maxlength="12" class="form-control" name="txtmobile" placeholder="Mobile No.">
                                    </div>
                                </div>
                            </div>
                        </div>

                         <div class="row">
                             <div class="col-md-12 col-xs-12 col-sm-12">
                                 <div class="form-group">
                                      <div class="input-group">
                                        <div class="input-group-addon">
                                            <i class="glyphicon glyphicon-user"></i>
                                        </div>
                            <label for="sexo">Sexo:</label>
                            <select class="form-control" id="sexo" name="sexo">
                                <option>Femenino</option>
                                <option>Masculino</option>
                            </select>
                            </div>
                                 </div>
                        </div>
                         </div>

                        <div class="row">
                            <div class="col-md-12 col-xs-12 col-sm-12">
                                <div class="form-group">
                                    <div class="input-group">
                                        <div class="input-group-addon">
                                            <i class="glyphicon glyphicon-list-alt"></i>
                                        </div>
                                        <textarea class="form-control" name="txtadd" placeholder="Address"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12 col-xs-12 col-sm-12">
                                <div class="form-group">
                                    <div class="input-group">
                                        <div class="input-group-addon">
                                            <i class="glyphicon glyphicon-calendar"></i>
                                        </div>
                                        <input type="text" name="dob" placeholder="Date Of Birth" class="form-control" id="datepicker">
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row widget">
                            <div class="col-md-12 col-xs-12 col-sm-12">
                                 <button id="button"  class="btn btn-primary btn-lg btn-block login-button">Registrate</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>
